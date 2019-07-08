package ru.ss.springboot.cities.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;
import org.ss.server.lib.JSONTemplateResponse;
import ru.ss.springboot.cities.services.CityServiceImpl;


@RestController
@EnableAutoConfiguration
public class CityController {

    @Autowired
    private CityServiceImpl cityService;

    @GetMapping("/cities")
    public JSONTemplateResponse findAll(){
        return cityService.getCities();
    }

    @RequestMapping("/save")
    public JSONTemplateResponse post(@RequestBody JsonNode jsonNode) {
        boolean lock = Boolean.parseBoolean(jsonNode.get("locked").toString());
        String name = jsonNode.get("cityName").textValue();
        return cityService.createCity(name, lock);
    }

    @RequestMapping("/delete")
    public JSONTemplateResponse delete(@RequestBody JsonNode jsonNode) {
        String name = jsonNode.get("cityName").textValue();
        return cityService.deleteCity(name);
    }

    @RequestMapping("/update")
    public JSONTemplateResponse update(@RequestBody JsonNode jsonNode) {
        String oldName = jsonNode.get("cityName").textValue();
        String newName = jsonNode.get("updateName").textValue();
        return cityService.updateCity(oldName, newName);
    }
}
