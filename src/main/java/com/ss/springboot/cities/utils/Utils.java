package com.ss.springboot.cities.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ss.springboot.cities.City;

import java.util.Collection;
import java.util.List;

public class Utils {
    public String wrapToHtml(Iterable<City> content) {
        StringBuilder result = new StringBuilder("<html>");
        for(Object element : content){
            result.append("<div>" + element.toString() + "</div>");
        }
        result.append("</html>");
        return result.toString();
    }

    public JsonNode getJson(Object objectToMap) {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode result = mapper.valueToTree(objectToMap);
        return result;
    }
}
