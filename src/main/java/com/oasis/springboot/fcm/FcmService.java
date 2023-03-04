package com.oasis.springboot.fcm;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.oasis.springboot.domain.pushAlarm.PushAlarm;
import com.oasis.springboot.domain.pushAlarm.PushAlarmRepository;
import com.oasis.springboot.service.WeatherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FcmService {

    @Value("${project.properties.firebase-create-scoped}")
    String fireBaseCreateScoped;

    @Value("${project.properties.firebase-topic}")
    String topic;

    private FirebaseMessaging instance;

    private final WeatherService weatherService;
    private final PushAlarmRepository pushAlarmRepository;

    //벡엔드에서 firebase 접속
    @PostConstruct
    public void firebaseSetting() throws IOException {
        GoogleCredentials googleCredentials =
                GoogleCredentials.fromStream(new ClassPathResource("firebase/plant-5bedc-firebase-adminsdk-9xou6-99b99d329f.json")
                                .getInputStream())
                .createScoped((Arrays.asList(fireBaseCreateScoped)));
        FirebaseOptions secondaryAppConfig = FirebaseOptions.builder()
                .setCredentials(googleCredentials)
                .build();
        FirebaseApp app = FirebaseApp.initializeApp(secondaryAppConfig);
        this.instance = FirebaseMessaging.getInstance(app);

    }

    @Scheduled(cron = "0 0 10 * * ?")
    public void pushWeatherAlarm() throws FirebaseMessagingException {
        String title = "오늘은 좋은 날씨~";
        String message = "오늘도 반려식물과 좋은 하루 되세요~";
        try {
            JSONArray jsonArray = weatherService.getWeather("60", "127");

            JSONObject jsonObject = (JSONObject) jsonArray.get(4);
            Object object = jsonObject.get("Value");
            String temperature = object.toString();
            if(Integer.parseInt(temperature) < 0){
                title = "오늘은 추운 날씨";
                message = "오늘의 기온은 "+temperature+" 입니다. 반려식물이 얼지 않게 조심해주세요";
            }
            else if(Integer.parseInt(temperature)  > 30) {
                title = "오늘은 더운 날씨";
                message = "오늘의 기온은 "+temperature+" 입니다. 반려식물이 타지 않게 조심해주세요";
            }

            jsonObject = (JSONObject) jsonArray.get(9);
            object = jsonObject.get("Value");
            String wind = object.toString();
            if(Integer.parseInt(wind) >= 9) {
                title = "바람이 강한 날씨";
                message = "바람이 강하니 밖에 있는 반려식물 가지가 부러지지 않게 조심하세요";
            }

            jsonObject = (JSONObject) jsonArray.get(2);
            object = jsonObject.get("Value");
            String rain = object.toString();
            if(!rain.equals("강수없음") && Float.parseFloat(rain) >= 30.0f)  {
                title = "비(눈)가 많이 와요";
                message = "비가 많이 오니 밖에 있는 반려식물이 잠기지 않게 조심하세요";
            }
            else if(!rain.equals("강수없음")){
                title = "우산이 필요한 날씨";
                message = "우산이 필요하지만 반려식물이 좋아하겠네요!";
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

        this.sendTopicMessage(title, message);
    }

    public void sendTopicMessage(String title, String message) throws FirebaseMessagingException {
        Notification notification = Notification.builder().setTitle(title).setBody(message).build();
        Message msg = Message.builder().setTopic(topic).setNotification(notification).build();
        sendMessage(msg);
    }

    @Scheduled(cron = "0 00 10 * * ?")
    public void pushWaterNotification() throws FirebaseMessagingException {
        List<PushAlarm> pushAlarmList = pushAlarmRepository.findAllByDate(LocalDate.now());
        for(PushAlarm pushAlarm : pushAlarmList){
            String token = pushAlarm.getUser().getFcmToken();
            String title = "오늘은 물 주는 날~🎍";
            String body = pushAlarm.getUser().getName()+"님. 물이 필요한 식물이 있어요. 물 주러 가볼까요~?";

            sendTokenMessage(token, title, body);
            pushAlarmRepository.delete(pushAlarm);
        }
    }

    public void sendTokenMessage(String targetToken, String title, String body) throws FirebaseMessagingException {
        Notification notification = Notification.builder().setTitle(title).setBody(body).build();
        Message msg = Message.builder().setToken(targetToken).setNotification(notification).build();
        sendMessage(msg);
    }

    public String sendMessage(Message message) throws FirebaseMessagingException {
        return this.instance.send(message);
    }
}
