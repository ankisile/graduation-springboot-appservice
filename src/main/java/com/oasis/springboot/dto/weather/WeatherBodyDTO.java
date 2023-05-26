package com.oasis.springboot.dto.weather;

import lombok.Data;

@Data
public class WeatherBodyDTO
{
    private String          dataType;

    private WeatherItemsDTO items;
}