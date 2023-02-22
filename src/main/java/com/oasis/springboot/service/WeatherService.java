package com.oasis.springboot.service;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Service
public class WeatherService {

    public JSONArray getWeather(String x, String y) throws IOException, ParseException, ParseException {
        String apiUrl = "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getUltraSrtFcst";

        String serviceKey = "UdJoe0RU%2BfA4noQhiQupw5F43s9H%2BGaTyKVFX9guM4wsuoWR9SWXPtQ4pHzWpcOpP%2FBo6enHnmibSfN55xy7tQ%3D%3D";

        //변경 geolocation -> 좌표
        String nx = x;
        String ny = y;
        String baseDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String baseTime = String.format("%02d", LocalTime.now().getHour())+"30";
        if(LocalTime.now().getMinute()<30) {
            baseTime =  String.format("%02d", LocalTime.now().getHour() - 1) + "30";
            if(LocalTime.now().getHour() == 0)
                baseTime = "2330";
        }

        String dataType = "json";
        String numOfRows = "250";

        StringBuilder urlBuilder = new StringBuilder(apiUrl);
        urlBuilder.append("?" + URLEncoder.encode("ServiceKey","UTF-8") + "="+serviceKey);
        urlBuilder.append("&" + URLEncoder.encode("nx","UTF-8") + "=" + URLEncoder.encode(nx, "UTF-8")); //경도
        urlBuilder.append("&" + URLEncoder.encode("ny","UTF-8") + "=" + URLEncoder.encode(ny, "UTF-8")); //위도
        urlBuilder.append("&" + URLEncoder.encode("base_date","UTF-8") + "=" + URLEncoder.encode(baseDate, "UTF-8")); /* 조회하고싶은 날짜*/
        urlBuilder.append("&" + URLEncoder.encode("base_time","UTF-8") + "=" + URLEncoder.encode(baseTime, "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("dataType","UTF-8") + "=" + URLEncoder.encode(dataType, "UTF-8"));	/* 타입 */
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode(numOfRows, "UTF-8"));	/* 한 페이지 결과 수 */

        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");

        BufferedReader rd;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();
        String data= sb.toString();

        // Json parser를 만들어 만들어진 문자열 데이터를 객체화
        JSONParser parser = new JSONParser();
        JSONObject obj = (JSONObject) parser.parse(data);
        // response 키를 가지고 데이터를 파싱
        JSONObject parse_response = (JSONObject) obj.get("response");
        // response 로 부터 body 찾기
        JSONObject parse_body = (JSONObject) parse_response.get("body");
        // body 로 부터 items 찾기
        JSONObject parse_items = (JSONObject) parse_body.get("items");
        JSONArray parse_item = (JSONArray) parse_items.get("item");


        JSONArray result = new JSONArray();
        Object fcstTime;
        Object category;
        Object value;

        for(int k=0;k<10;k++) {
            JSONObject weather = new JSONObject();
            weather = (JSONObject) parse_item.get(k*6);
            JSONObject object = new JSONObject();
            category = weather.get("category");
            value = weather.get("fcstValue");

            object.put("Category", category);
            object.put("Value", value);
            result.add(object);
        }

        return result;
    }
}
