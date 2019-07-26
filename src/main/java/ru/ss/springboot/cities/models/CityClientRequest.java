package ru.ss.springboot.cities.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
public class CityClientRequest {

    private String cityName;
    private String newCityName;
    private boolean locked;
}
