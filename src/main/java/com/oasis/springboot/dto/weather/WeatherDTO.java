package com.oasis.springboot.dto.weather;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WeatherDTO {
    private String category;
    private String value;
}
