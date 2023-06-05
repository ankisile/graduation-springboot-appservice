package com.oasis.springboot.service;

import com.oasis.springboot.dto.place.KakaoPlaceResponseDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Service
public class PlaceService {

    @Value("${kakao.api.key}")
    private String KakaoApiKey;

    public KakaoPlaceResponseDto getPlantStores(String x, String y) {
        String apiUrl = "https://dapi.kakao.com/v2/local/search/keyword.json";
        URI urlTemplate = UriComponentsBuilder.fromHttpUrl(apiUrl)
                .queryParam("query", "꽃집")
                .queryParam("x", x)
                .queryParam("y", y)
                .queryParam("radius", "500")
                .queryParam("sort", "distance")
                .build()
                .encode()
                .toUri();

        RestTemplate template = new RestTemplate();
        HttpEntity<String> entity = new HttpEntity<>(this.getHeaders());

        KakaoPlaceResponseDto responseDto = template.exchange(urlTemplate, HttpMethod.GET, entity, KakaoPlaceResponseDto.class).getBody();

        return responseDto;

    }

    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "KakaoAK " + KakaoApiKey);
        headers.set("Content-type", "application/json");

        return headers;
    }
}
