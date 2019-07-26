package ru.ss.springboot.cities.services;

import java.util.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.ss.server.lib.JSONTemplateResponse;
import ru.ss.springboot.cities.models.City;
import ru.ss.springboot.cities.models.CityDTO;
import ru.ss.springboot.cities.models.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CityServiceImpl {
    @Autowired
    private CityRepository cityRepository;
    private ObjectMapper mapper = new ObjectMapper();

    public ResponseEntity<CityDTO> createCity(String name, boolean lock) throws Exception {
        City saved = cityRepository.save(new City(name, lock));
        return new ResponseEntity<>(cityToDTO(saved), HttpStatus.OK);
    }

    public ResponseEntity<CityDTO> updateCity(String oldName, String newName) throws Exception {
        City city = cityRepository.findByName(oldName);
        if (city == null) {
            throw new Exception("No such city");
        }
        if (city.isLocked()) {
            throw new Exception("Change not allowed");
        }

        city.setName(newName);
        city = cityRepository.save(city);
        return new ResponseEntity<>(cityToDTO(city), HttpStatus.OK);
    }

    public ResponseEntity<CityDTO> search(String name) {
        List<City> city = cityRepository.findByNameLikeIgnoreCase(name);
        List<Object> res = new ArrayList<>();
        res.addAll(city);
        return new ResponseEntity(res, HttpStatus.OK);
    }

    public ResponseEntity<CityDTO> deleteCity(String name) throws Exception {
        City city = cityRepository.findByName(name);
        if (city == null) {
            throw new Exception("No such city");
        }
        if (city.isLocked()) {
            throw new Exception("Change not allowed");
        }

        cityRepository.delete(city);
        return new ResponseEntity("deleted", HttpStatus.OK);
    }

    public ResponseEntity<CityDTO> getCities() throws Exception {
        Iterable<City> cities = cityRepository.findAll();
        List<Object> listCities = new LinkedList<Object>();
        String result;
        for(City city : cities) {
            listCities.add(city);
        }
        return new ResponseEntity(listCities, HttpStatus.OK);
    }

    private CityDTO cityToDTO(City city) {
        CityDTO dto = new CityDTO();
        dto.setId(city.getId());
        dto.setName(city.getName());
        dto.setLocked(city.isLocked());
        return dto;
    }
}