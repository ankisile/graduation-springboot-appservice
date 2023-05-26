package com.oasis.springboot.dto.weather;

import lombok.Data;

@Data
public class WeatherResponseDTO
{
    private WeatherHeaderDTO header;

    private WeatherBodyDTO   body;

}