package com.tom.forecast.bean;

import java.io.Serializable;

public class Weather implements Serializable {

    String cityId;
    String month;
    String day;
    String weekday;
    String maxTemp;
    String minTemp;
    String weatherDescription;
    String iconId;
    String humidity;
    String windDir;
    String windSpeed;
    String pressure;

    public Weather(String cityId, String month, String day, String weekday, String maxTemp, String minTmep, String weatherDescription, String iconId, String humidity, String windDir, String windSpeed, String pressure) {
        this.cityId = cityId;
        this.month = month;
        this.day = day;
        this.weekday = weekday;
        this.maxTemp = maxTemp;
        this.minTemp = minTmep;
        this.weatherDescription = weatherDescription;
        this.iconId = iconId;
        this.humidity = humidity;
        this.windDir = windDir;
        this.windSpeed = windSpeed;
        this.pressure = pressure;
    }

    public Weather() {
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getWeekday() {
        return weekday;
    }

    public void setWeekday(String weekday) {
        this.weekday = weekday;
    }

    public String getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(String maxTemp) {
        this.maxTemp = maxTemp;
    }

    public String getMinTmep() {
        return minTemp;
    }

    public void setMinTmep(String minTmep) {
        this.minTemp = minTmep;
    }

    public String getWeatherDescription() {
        return weatherDescription;
    }

    public void setWeatherDescription(String weatherDescription) {
        this.weatherDescription = weatherDescription;
    }

    public String getIconId() {
        return iconId;
    }

    public void setIconId(String iconId) {
        this.iconId = iconId;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getWindDir() {
        return windDir;
    }

    public void setWindDir(String windDir) {
        this.windDir = windDir;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(String windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    @Override
    public String toString() {
        return "Weather{" +
                "cityId='" + cityId + '\'' +
                ", month='" + month + '\'' +
                ", day='" + day + '\'' +
                ", weekday='" + weekday + '\'' +
                ", maxTemp='" + maxTemp + '\'' +
                ", minTemp='" + minTemp + '\'' +
                ", weatherDescription='" + weatherDescription + '\'' +
                ", iconId='" + iconId + '\'' +
                ", humidity='" + humidity + '\'' +
                ", windDir='" + windDir + '\'' +
                ", windSpeed='" + windSpeed + '\'' +
                ", pressure='" + pressure + '\'' +
                '}';
    }
}
