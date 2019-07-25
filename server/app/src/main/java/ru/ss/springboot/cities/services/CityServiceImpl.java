package ru.ss.springboot.cities.services;

import java.util.*;

import org.ss.server.lib.JSONTemplateResponse;
import ru.ss.springboot.cities.models.City;
import ru.ss.springboot.cities.models.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CityServiceImpl {
    @Autowired
    private CityRepository cityRepository;

    public JSONTemplateResponse createCity(String name, boolean lock) {
        String result = "Error occured on server database.";
        JSONTemplateResponse response = new JSONTemplateResponse();
        try {
            City saved = cityRepository.save(new City(name, lock));
            result = saved.toString() + " successfully saved.";
            response.setRequestOk(true);
        } catch (Exception e) {
            result += " Or city '" + name + "' already exists.";
        }
        response.setResponseContent(result);
        return response;
    }

    public JSONTemplateResponse updateCity(String oldName, String newName) {
        City city;
        try {
            city = cityRepository.findByName(oldName);
        } catch (Exception e) {
            return new JSONTemplateResponse(false, "Database problem on server occured.");
        }

        if (city == null) {
            return new JSONTemplateResponse(false, "No such city: " + oldName);
        }

        if (city.isLocked()) {
            return new JSONTemplateResponse(false, oldName + " prohibited to change and delete.");
        }
        city.setName(newName);
        City saved = null;
        String result = "Error occured on server database.";
        JSONTemplateResponse response = new JSONTemplateResponse();

        try {
            saved = cityRepository.save(city);
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

    public JSONTemplateResponse search(String name) {
        List<City> city = cityRepository.findByNameLikeIgnoreCase(name);
        JSONTemplateResponse resp = new JSONTemplateResponse(true, "");
        List<Object> res = new ArrayList<>();
        res.addAll(city);
        resp.setRecordList(res);
        return resp;
    }

    public JSONTemplateResponse deleteCity(String name) {
        City city;
        try {
            city = cityRepository.findByName(name);
        } catch (Exception e) {
            return new JSONTemplateResponse(false, "Database problem on server occured.");
        }

        if (city == null) {
            return new JSONTemplateResponse(false, "No such city: " + name);
        }

        if (city.isLocked()) {
            return new JSONTemplateResponse(false, name + " prohibited to change and delete.");
        }

        cityRepository.delete(city);
        return new JSONTemplateResponse(true, name + " successfully deleted.");
    }

    public JSONTemplateResponse getCities() {
        Iterable<City> cities;
        try {
            cities = cityRepository.findAll();
        } catch (Exception e) {
            return new JSONTemplateResponse(false, "Database problem on server occured.");
        }
        List<Object> listCities = new LinkedList<Object>();
        String result;
        for(City city : cities) {
            listCities.add(city);
        }
        result = listCities.isEmpty()? "City list is empty." : " Table contains " + listCities.size() + " cities";
        JSONTemplateResponse resp = new JSONTemplateResponse(true, result);
        resp.setRecordList(listCities);
        return resp;
    }
}