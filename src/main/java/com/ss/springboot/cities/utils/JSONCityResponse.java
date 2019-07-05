package com.ss.springboot.cities.utils;

import com.ss.springboot.cities.City;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class JSONCityResponse {

    public JSONCityResponse() {

    }

    private boolean requestOk;
    private String responseContent;

    public List<City> getCities() {
        return cities;
    }

    public void setCities(List<City> cities) {
        this.cities = cities;
    }

    private List<City> cities = new LinkedList<City>();

    public boolean isRequestOk() {
        return requestOk;
    }

    public void setRequestOk(boolean requestOk) {
        this.requestOk = requestOk;
    }

    public JSONCityResponse(boolean status, String responseContent) {
        this.requestOk = status;
        this.responseContent = responseContent;
    }


    public String getResponseContent() {
        return responseContent;
    }

    public void setResponseContent(String responseContent) {
        this.responseContent = responseContent;
    }

}
