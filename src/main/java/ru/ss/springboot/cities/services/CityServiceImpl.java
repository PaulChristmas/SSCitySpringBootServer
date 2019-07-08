package ru.ss.springboot.cities.services;

import java.util.*;

import org.ss.server.lib.JSONTemplateResponse;
import ru.ss.springboot.cities.models.City;
import ru.ss.springboot.cities.models.CityRepository;
import ru.ss.springboot.cities.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CityServiceImpl implements CityService {
    @Autowired
    private CityRepository cityRepository;

    private Utils utils = new Utils();

    @Override
    public JSONTemplateResponse createCity(String name, boolean lock) {

        String result = "Error occured on server database.";
        City saved = null;
        JSONTemplateResponse response = new JSONTemplateResponse();
        try {
            saved = cityRepository.save(new City(name, lock));
        } catch (Exception e) {
            result += " Or city '" + name + "' already exists.";
        }
        if (saved != null) {
            result = saved.toString() + " successfully saved.";
            response.setRequestOk(true);
        }
        response.setResponseContent(result);
        return response;
    }

    @Override
    public JSONTemplateResponse updateCity(String oldName, String newName) {

        List<City> cities;
        try {
            cities = cityRepository.findByName(oldName);
        } catch (Exception e) {
            return new JSONTemplateResponse(false, "Database problem on server occured.");
        }

        if (cities.isEmpty()) {
            return new JSONTemplateResponse(false, "No such city: " + oldName);
        }

        City cityToChange = cities.get(0);
        if (cityToChange.isLocked()) {
            return new JSONTemplateResponse(false, oldName + " prohibited to change and delete.");
        }
        cityToChange.setName(newName);
        City saved = null;
        String result = "Error occured on server database.";
        JSONTemplateResponse response = new JSONTemplateResponse();

        try {
            saved = cityRepository.save(cityToChange);
        } catch (Exception e) {
            result += " Or city '" + newName + "' already exists.";
        }
        if (saved != null) {
            result = oldName + " successfully changed to " + newName;
            response.setRequestOk(true);
        }
        response.setResponseContent(result);
        return response;
    }

    @Override
    public JSONTemplateResponse deleteCity(String name) {
        List<City> cities;
        try {
            cities = cityRepository.findByName(name);
        } catch (Exception e) {
            return new JSONTemplateResponse(false, "Database problem on server occured.");
        }

        if (cities.isEmpty()) {
            return new JSONTemplateResponse(false, "No such city: " + name);
        }

        City cityToDelete = cities.get(0);
        if (cityToDelete.isLocked()) {
            return new JSONTemplateResponse(false, name + " prohibited to change and delete.");
        }

        cityRepository.delete(cityToDelete);
        return new JSONTemplateResponse(true, name + " successfully deleted.");
    }

    @Override
    public JSONTemplateResponse getCities() {
        Iterable<City> cities;
        try {
            cities = cityRepository.findAll();
        } catch (Exception e) {
            System.out.println(e.toString());
            return new JSONTemplateResponse(false, "Database problem on server occured.");
        }
        List<Object> listCities = new LinkedList<Object>();
        String result;
        for(City city : cities){
            listCities.add(city);
        }
        result = listCities.isEmpty()? "City list is empty." : " Table contains " + listCities.size() + " cities";
        JSONTemplateResponse resp = new JSONTemplateResponse(true, result);
        resp.setRecordList(listCities);
        return resp;
    }
}