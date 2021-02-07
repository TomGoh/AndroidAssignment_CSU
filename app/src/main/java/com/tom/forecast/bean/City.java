package com.tom.forecast.bean;

import java.io.Serializable;

public class City implements Serializable {

    String mCityName;
    public String mCityId;

    public City(String cityName, String cityId) {

        this.mCityName = cityName;
        this.mCityId=cityId;
    }

    public String getCityName() {
        return mCityName;
    }

    public void setCityName(String cityName) {
        mCityName = cityName;
    }

    public String getCityId() {
        return mCityId;
    }

    public void setCityId(String cityId) {
        mCityId = cityId;
    }
}
