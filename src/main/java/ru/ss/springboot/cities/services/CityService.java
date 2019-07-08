package ru.ss.springboot.cities.services;

import org.ss.server.lib.JSONTemplateResponse;

public interface CityService {
    public abstract JSONTemplateResponse createCity(String name, boolean lock);
    public abstract JSONTemplateResponse updateCity(String oldName, String newName);
    public abstract JSONTemplateResponse deleteCity(String name);
    public abstract JSONTemplateResponse getCities();
}