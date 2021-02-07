package com.tom.forecast.bean;


import com.tom.forecast.utils.GenerateDate;

import java.io.Serializable;

public class WeatherInfor implements Serializable {

    String fxDate;
    String sunrise;
    String sunset;
    String moonrise;
    String moonset;
    String moonPhase;
    String tempMax;
    String tempMin;
    String iconDay;
    String textDay;
    String iconNight;
    String textNight;
    String wind360Day;
    String windDirDay;
    String windScaleDay;
    String windSpeedDay;
    String wind360Night;
    String windDirNight;
    String windScaleNight;
    String windSpeedNight;
    String humidity;
    String precip;
    String pressure;
    String vis;
    String cloud;
    String uvIndex;

    public String getFxDate() {
        return fxDate;
    }

    public String getSunrise() {
        return sunrise;
    }

    public String getSunset() {
        return sunset;
    }

    public String getMoonrise() {
        return moonrise;
    }

    public String getMoonset() {
        return moonset;
    }

    public String getMoonPhase() {
        return moonPhase;
    }

    public String getTempMax() {
        return tempMax;
    }

    public String getTempMin() {
        return tempMin;
    }

    public String getIconDay() {
        return iconDay;
    }

    public String getTextDay() {
        return textDay;
    }

    public String getIconNight() {
        return iconNight;
    }

    public String getTextNight() {
        return textNight;
    }

    public String getWind360Day() {
        return wind360Day;
    }

    public String getWindDirDay() {
        return windDirDay;
    }

    public String getWindScaleDay() {
        return windScaleDay;
    }

    public String getWindSpeedDay() {
        return windSpeedDay;
    }

    public String getWind360Night() {
        return wind360Night;
    }

    public String getWindDirNight() {
        return windDirNight;
    }

    public String getWindScaleNight() {
        return windScaleNight;
    }

    public String getWindSpeedNight() {
        return windSpeedNight;
    }

    public String getHumidity() {
        return humidity;
    }

    public String getPrecip() {
        return precip;
    }

    public String getPressure() {
        return pressure;
    }

    public String getVis() {
        return vis;
    }

    public String getCloud() {
        return cloud;
    }

    public String getUvIndex() {
        return uvIndex;
    }

    public void setFxDate(String fxDate) {
        this.fxDate = fxDate;
    }

    public void setSunrise(String sunrise) {
        this.sunrise = sunrise;
    }

    public void setSunset(String sunset) {
        this.sunset = sunset;
    }

    public void setMoonrise(String moonrise) {
        this.moonrise = moonrise;
    }

    public void setMoonset(String moonset) {
        this.moonset = moonset;
    }

    public void setMoonPhase(String moonPhase) {
        this.moonPhase = moonPhase;
    }

    public void setTempMax(String tempMax) {
        this.tempMax = tempMax;
    }

    public void setTempMin(String tempMin) {
        this.tempMin = tempMin;
    }

    public void setIconDay(String iconDay) {
        this.iconDay = iconDay;
    }

    public void setTextDay(String textDay) {
        this.textDay = textDay;
    }

    public void setIconNight(String iconNight) {
        this.iconNight = iconNight;
    }

    public void setTextNight(String textNight) {
        this.textNight = textNight;
    }

    public void setWind360Day(String wind360Day) {
        this.wind360Day = wind360Day;
    }

    public void setWindDirDay(String windDirDay) {
        this.windDirDay = windDirDay;
    }

    public void setWindScaleDay(String windScaleDay) {
        this.windScaleDay = windScaleDay;
    }

    public void setWindSpeedDay(String windSpeedDay) {
        this.windSpeedDay = windSpeedDay;
    }

    public void setWind360Night(String wind360Night) {
        this.wind360Night = wind360Night;
    }

    public void setWindDirNight(String windDirNight) {
        this.windDirNight = windDirNight;
    }

    public void setWindScaleNight(String windScaleNight) {
        this.windScaleNight = windScaleNight;
    }

    public void setWindSpeedNight(String windSpeedNight) {
        this.windSpeedNight = windSpeedNight;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public void setPrecip(String precip) {
        this.precip = precip;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public void setVis(String vis) {
        this.vis = vis;
    }

    public void setCloud(String cloud) {
        this.cloud = cloud;
    }

    public void setUvIndex(String uvIndex) {
        this.uvIndex = uvIndex;
    }

    @Override
    public String toString() {
        return "WeatherInfor{" +
                "fxDate='" + fxDate + '\'' +
                ", sunrise='" + sunrise + '\'' +
                ", sunset='" + sunset + '\'' +
                ", moonrise='" + moonrise + '\'' +
                ", moonset='" + moonset + '\'' +
                ", moonPhase='" + moonPhase + '\'' +
                ", tempMax='" + tempMax + '\'' +
                ", tempMin='" + tempMin + '\'' +
                ", iconDay='" + iconDay + '\'' +
                ", textDay='" + textDay + '\'' +
                ", iconNight='" + iconNight + '\'' +
                ", textNight='" + textNight + '\'' +
                ", wind360Day='" + wind360Day + '\'' +
                ", windDirDay='" + windDirDay + '\'' +
                ", windScaleDay='" + windScaleDay + '\'' +
                ", windSpeedDay='" + windSpeedDay + '\'' +
                ", wind360Night='" + wind360Night + '\'' +
                ", windDirNight='" + windDirNight + '\'' +
                ", windScaleNight='" + windScaleNight + '\'' +
                ", windSpeedNight='" + windSpeedNight + '\'' +
                ", humidity='" + humidity + '\'' +
                ", precip='" + precip + '\'' +
                ", pressure='" + pressure + '\'' +
                ", vis='" + vis + '\'' +
                ", cloud='" + cloud + '\'' +
                ", uvIndex='" + uvIndex + '\'' +
                '}';
    }

    public WeatherInfor(String fxDate, String sunrise, String sunset, String moonrise, String moonset, String moonPhase, String tempMax, String tempMin, String iconDay, String textDay, String iconNight, String textNight, String wind360Day, String windDirDay, String windScaleDay, String windSpeedDay, String wind360Night, String windDirNight, String windScaleNight, String windSpeedNight, String humidity, String precip, String pressure, String vis, String cloud, String uvIndex) {
        this.fxDate = fxDate;
        this.sunrise = sunrise;
        this.sunset = sunset;
        this.moonrise = moonrise;
        this.moonset = moonset;
        this.moonPhase = moonPhase;
        this.tempMax = tempMax;
        this.tempMin = tempMin;
        this.iconDay = iconDay;
        this.textDay = textDay;
        this.iconNight = iconNight;
        this.textNight = textNight;
        this.wind360Day = wind360Day;
        this.windDirDay = windDirDay;
        this.windScaleDay = windScaleDay;
        this.windSpeedDay = windSpeedDay;
        this.wind360Night = wind360Night;
        this.windDirNight = windDirNight;
        this.windScaleNight = windScaleNight;
        this.windSpeedNight = windSpeedNight;
        this.humidity = humidity;
        this.precip = precip;
        this.pressure = pressure;
        this.vis = vis;
        this.cloud = cloud;
        this.uvIndex = uvIndex;
    }

    public WeatherInfor() {
    }

    public Weather toWeather(String cityId){
        return new Weather(cityId, GenerateDate.getMonth(this.fxDate),GenerateDate.getRealDate(this.fxDate),
                GenerateDate.getWeekDay(this.fxDate),this.tempMax,this.tempMin,this.textDay,this.iconDay,
                this.humidity,this.windDirDay,this.windSpeedDay,this.pressure);
    }
}
