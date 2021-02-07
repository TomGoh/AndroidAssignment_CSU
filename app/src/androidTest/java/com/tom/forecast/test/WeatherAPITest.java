package com.tom.forecast.test;

import com.tom.forecast.utils.WeatherAPI;

import org.json.JSONException;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

public class WeatherAPITest {


    WeatherAPI mWeatherAPI=new WeatherAPI();
    String locationID="101230501";
    @Test
    public void getLocationID() throws IOException, JSONException {
        String newlocationID=mWeatherAPI.getLocationID("Quanzhou");
        System.out.println(newlocationID);
    }

    @Test
    public void getCurrentWeatherInformation() throws IOException, JSONException {
        System.out.println("Weather:"+mWeatherAPI.getCurrentWeatherInformation(locationID).toString());
    }

    @Test
    public void getWeather10Days() throws IOException, JSONException {
        System.out.println("10 Days Weather:"+mWeatherAPI.getWeather10Days(locationID));
    }

    @Test
    public void getGeoInformationByName() throws IOException, JSONException {
        List<String> geo=mWeatherAPI.getGeoInformationByName("Changsha");
        for(String item:geo){
            System.out.println(item);
        }
    }

}