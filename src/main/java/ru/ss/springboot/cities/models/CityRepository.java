package ru.ss.springboot.cities.models;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CityRepository extends CrudRepository<City, Long> {
    City findByName(String name);

    List<City> findByNameLikeIgnoreCase(String name);
}
