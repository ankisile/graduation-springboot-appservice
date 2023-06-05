package com.oasis.springboot.weather;

import com.oasis.springboot.dto.weather.WeatherDTO;
import com.oasis.springboot.service.WeatherService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@Transactional
public class WeatherServiceTest {

    @Autowired
    WeatherService weatherService;

    @Test
    void 날씨_가져오기() throws Exception {
        //given
        String x = "60";
        String y = "127";

        //when
        List<WeatherDTO> list = weatherService.getWeather(x, y);

        //then
        assertThat(list.get(0).getCategory()).isEqualTo("LGT");
        assertThat(list.get(3).getCategory()).isEqualTo("SKY");

    }
}
