package com.oasis.springboot.controller;

import com.oasis.springboot.common.response.ListResponse;
import com.oasis.springboot.common.response.ResponseService;
import com.oasis.springboot.dto.weather.WeatherDTO;
import com.oasis.springboot.service.WeatherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URISyntaxException;

@Tag(name = "Weather", description = "메인화면 날씨 API")
@RestController
@RequestMapping("/api/weathers")
@RequiredArgsConstructor
public class WeatherController {

    private final WeatherService weatherService;
    private final ResponseService responseService;

    @Operation(summary = "get weather", description = "지역에 대한 날씨를 가져오기")
    @Parameters({
            @Parameter(name = "x", description = "x좌표", example = "60"),
            @Parameter(name = "y", description = "y좌표", example = "127"),
    })
    @GetMapping("")
    public ListResponse<WeatherDTO> getWeather(@RequestParam("x") String x, @RequestParam("y") String y) throws IOException, ParseException, URISyntaxException {
        return responseService.getListResponse(weatherService.getWeather(x, y));
    }

}
