package ru.ss.springboot.cities.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
public class CityDTO{

    private int id;
    private String name;
    private boolean locked;
}