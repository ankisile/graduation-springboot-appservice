package com.oasis.springboot.service;

import com.oasis.springboot.dto.weather.WeatherApiResponseDTO;
import com.oasis.springboot.dto.weather.WeatherDTO;
import com.oasis.springboot.dto.weather.WeatherItemDTO;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class WeatherService {

    @Value("${weather.api.key}")
    private String serviceKey;

    public List<WeatherDTO> getWeather(String x, String y) throws IOException, ParseException, URISyntaxException {

        URI uri = makeRequestURI(x, y);

        RestTemplate template = new RestTemplate();
        HttpEntity<String> entity = new HttpEntity<>(this.getHeaders());

        ResponseEntity<WeatherApiResponseDTO> responseDto = template.exchange(uri, HttpMethod.GET, entity, WeatherApiResponseDTO.class);

        List<WeatherItemDTO> itemList = responseDto.getBody().getResponse().getBody().getItems().getItem();

        List<WeatherDTO> weatherDTOList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            String category = itemList.get(i * 6).getCategory();
            String value = itemList.get(i * 6).getFcstValue();

            WeatherDTO weatherDTO = new WeatherDTO(category, value);
            weatherDTOList.add(weatherDTO);
        }

        return weatherDTOList;
    }

    private URI makeRequestURI(String x, String y) throws UnsupportedEncodingException, URISyntaxException {
        String apiUrl = "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getUltraSrtFcst";

        String nx = x;
        String ny = y;
        String baseDate = setBaseDate();
        String baseTime = setBaseTime();
        String dataType = "JSON";
        String numOfRows = "250";

        StringBuilder urlBuilder = new StringBuilder(apiUrl);
        urlBuilder.append("?" + URLEncoder.encode("ServiceKey", "UTF-8") + "=" + serviceKey);
        urlBuilder.append("&" + URLEncoder.encode("nx", "UTF-8") + "=" + URLEncoder.encode(nx, "UTF-8")); //경도
        urlBuilder.append("&" + URLEncoder.encode("ny", "UTF-8") + "=" + URLEncoder.encode(ny, "UTF-8")); //위도
        urlBuilder.append("&" + URLEncoder.encode("base_date", "UTF-8") + "=" + URLEncoder.encode(baseDate, "UTF-8")); /* 조회하고싶은 날짜*/
        urlBuilder.append("&" + URLEncoder.encode("base_time", "UTF-8") + "=" + URLEncoder.encode(baseTime, "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("dataType", "UTF-8") + "=" + URLEncoder.encode(dataType, "UTF-8"));    /* 타입 */
        urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode(numOfRows, "UTF-8"));    /* 한 페이지 결과 수 */

        return new URI(urlBuilder.toString());

    }


    private String setBaseDate() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        String baseDate = currentDateTime.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        if (currentDateTime.getMinute() < 30) {
            if (currentDateTime.getHour() == 0) {
                baseDate = LocalDateTime.now().minusDays(1).format(DateTimeFormatter.ofPattern("yyyyMMdd"));
            }
        }
        return baseDate;
    }

    private String setBaseTime() {
        LocalTime currentTime = LocalTime.now();
        String baseTime = String.format("%02d", currentTime.getHour()) + "30";
        if (currentTime.getMinute() < 30) {
            baseTime = String.format("%02d", currentTime.getHour() - 1) + "30";
            if (currentTime.getHour() == 0) {
                baseTime = "2330";
            }
        }
        return baseTime;
    }

    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "JSON", Charset.forName("UTF-8")));

        return headers;
    }
}
