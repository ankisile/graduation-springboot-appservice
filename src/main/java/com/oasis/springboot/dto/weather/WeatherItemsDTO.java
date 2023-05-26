package com.oasis.springboot.dto.weather;

import lombok.Data;

import java.util.List;

@Data
public class WeatherItemsDTO {
    private List<WeatherItemDTO> item;

}
