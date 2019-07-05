package com.ss.springboot.cities;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ss.springboot.cities.utils.JSONCityResponse;
import com.ss.springboot.cities.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

@RestController
@EnableAutoConfiguration
public class CityController {

    @Autowired
    private CityRepository cityRepository;

    private Utils utils = new Utils();

    @GetMapping("/cities")
    public JsonNode findAll(){
        Iterable<City> cities;
        try {
            cities = cityRepository.findAll();
        } catch (Exception e) {
            return utils.getJson(new JSONCityResponse(false, "Database problem on server occured."));
        }
        List<City> listCities = new LinkedList<City>();
        String result;
        for(City city : cityRepository.findAll()){
            listCities.add(city);

        }
        result = listCities.isEmpty()? "City list is empty." : " Table contains " + listCities.size() + " cities";
        JSONCityResponse resp = new JSONCityResponse(true, result);
        resp.setCities(listCities);
        return utils.getJson(resp);
    }

    @RequestMapping("/save")
    public JsonNode post(@RequestBody JsonNode jsonNode) {
        boolean lock = Boolean.parseBoolean(jsonNode.get("locked").toString());
        String name = jsonNode.get("cityName").textValue();
        String result = "Error occured on server database.";
        City saved = null;
        JSONCityResponse response = new JSONCityResponse();
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
        return utils.getJson(response);
    }

    @RequestMapping("/delete")
    public JsonNode delete(@RequestBody JsonNode jsonNode) {
        String name = jsonNode.get("cityName").textValue();
        List<City> cities;
        try {
            cities = cityRepository.findByName(name);
        } catch (Exception e) {
            return utils.getJson(new JSONCityResponse(false, "Database problem on server occured."));
        }

        if (cities.isEmpty()) {
            return utils.getJson(new JSONCityResponse(false, "No such city: " + name));
        }

        City cityToDelete = cities.get(0);
        if (cityToDelete.isLocked()) {
            return utils.getJson(new JSONCityResponse(false, name + " prohibited to change and delete."));
        }

        cityRepository.delete(cityToDelete);
        return utils.getJson(new JSONCityResponse(true, name + " successfully deleted."));
    }

    @RequestMapping("/update")
    public JsonNode update(@RequestBody JsonNode jsonNode) {
        String oldName = jsonNode.get("cityName").textValue();

        List<City> cities;
        try {
            cities = cityRepository.findByName(oldName);
        } catch (Exception e) {
            return utils.getJson(new JSONCityResponse(false, "Database problem on server occured."));
        }

        if (cities.isEmpty()) {
            return utils.getJson(new JSONCityResponse(false, "No such city: " + oldName));
        }

        City cityToChange = cities.get(0);
        if (cityToChange.isLocked()) {
            return utils.getJson(new JSONCityResponse(false, oldName + " prohibited to change and delete."));
        }

        String newName = jsonNode.get("updateName").textValue();
        cityToChange.setName(newName);
        City saved = null;
        String result = "Error occured on server database.";
        JSONCityResponse response = new JSONCityResponse();

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
        return utils.getJson(response);
    }
}
