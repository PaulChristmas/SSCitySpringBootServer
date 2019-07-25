package ru.ss.springboot.cities.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;
import org.ss.server.lib.JSONTemplateResponse;
import ru.ss.springboot.cities.services.CityServiceImpl;

@RestController
@EnableAutoConfiguration
@Api(value = "/api")
public class CityController {

    @Autowired
    private CityServiceImpl cityService;

    @GetMapping("/cities")
    @ApiOperation(value = "Список заданных городов")
    public JSONTemplateResponse findAll(){
        return cityService.getCities();
    }

    @RequestMapping("/save")
    @ApiOperation(value = "Добавление нового города")
    public JSONTemplateResponse post(@RequestBody JsonNode jsonNode) {
        boolean lock = Boolean.parseBoolean(jsonNode.get("locked").toString());
        String name = jsonNode.get("cityName").textValue();
        return cityService.createCity(name, lock);
    }

    @RequestMapping("/delete")
    @ApiOperation(value = "Удаление города по имени (если он существует и не закрыт для изменений)")
    public JSONTemplateResponse delete(@RequestBody JsonNode jsonNode) {
        String name = jsonNode.get("cityName").textValue();
        return cityService.deleteCity(name);
    }

    @RequestMapping("/search")
    @ApiOperation(value = "Поиск города по имени")
    public JSONTemplateResponse search(@RequestBody JsonNode jsonNode) {
        String name = jsonNode.get("cityName").textValue();
        return cityService.search(name);
    }

    @RequestMapping("/update")
    @ApiOperation(value = "Изменение имени города (если он существует и не закрыт для изменений)")
    public JSONTemplateResponse update(@RequestBody JsonNode jsonNode) {
        String oldName = jsonNode.get("cityName").textValue();
        String newName = jsonNode.get("updateName").textValue();
        return cityService.updateCity(oldName, newName);
    }
}
