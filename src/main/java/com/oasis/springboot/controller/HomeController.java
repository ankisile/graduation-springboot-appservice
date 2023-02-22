package com.oasis.springboot.controller;

import com.oasis.springboot.service.WeatherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Tag(name = "home", description = "메인화면 API")
@RestController
@RequestMapping("/api/home")
@RequiredArgsConstructor
public class HomeController {

    private final WeatherService weatherService;

    @Operation(summary = "get weather", description = "지역에 대한 날씨를 가져오기")
    @Parameters({
            @Parameter(name = "x", description = "x좌표", example = "60"),
            @Parameter(name = "y", description = "y좌표", example = "127"),
    })
    @GetMapping("")
    public JSONArray getWeather(@RequestParam("x") String x, @RequestParam("y") String y) throws IOException, ParseException {
        return weatherService.getWeather(x, y);
    }

}
