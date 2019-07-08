package ru.ss.springboot.cities.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Utils {

    public JsonNode getJson(Object objectToMap) {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode result = mapper.valueToTree(objectToMap);
        return result;
    }
}
