package ru.ss.springboot.cities.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.ss.server.lib.JSONTemplateResponse;
import ru.ss.springboot.cities.models.City;
import ru.ss.springboot.cities.models.CityClientRequest;
import ru.ss.springboot.cities.models.CityDTO;
import ru.ss.springboot.cities.services.CityServiceImpl;

@RestController
@EnableAutoConfiguration
@Api(value = "/api")
public class CityController {

    @Autowired
    private CityServiceImpl cityService;

    @RequestMapping(value = "/cities", method = RequestMethod.GET)
    @ApiOperation(value = "Список заданных городов")
    public ResponseEntity<CityDTO> findAll() throws Exception{
        return cityService.getCities();
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ApiOperation(value = "Добавление нового города")
    public ResponseEntity<CityDTO> post(@RequestBody CityClientRequest request) throws Exception {
        return cityService.createCity(request.getCityName(), request.isLocked());
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    @ApiOperation(value = "Удаление города по имени (если он существует и не закрыт для изменений)")
    public ResponseEntity<CityDTO> delete(@RequestBody CityClientRequest request) throws Exception {
        return cityService.deleteCity(request.getCityName());
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    @ApiOperation(value = "Поиск города по имени")
    public ResponseEntity<CityDTO> search(@RequestBody CityClientRequest request) throws Exception {
        return cityService.search(request.getCityName());
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    @ApiOperation(value = "Изменение имени города (если он существует и не закрыт для изменений)")
    public ResponseEntity<CityDTO> update(@RequestBody CityClientRequest request) throws Exception {
        return cityService.updateCity(request.getCityName(), request.getNewCityName());
    }
}
