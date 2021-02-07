package com.tom.forecast.utils;

import android.content.Context;
import android.util.Log;

import com.tom.forecast.bean.City;
import com.tom.forecast.bean.Setting;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

public class SettingUpdate {

    private static final String TAG="UPGRADE SETTING";

    private Context context;

    private List<Setting> settingList;

    private boolean isCelsius;

    private boolean isChecked;

    private String currentCity;

    public List<Setting> getSettingObject(){
        settingList=FileManage.loadSettingConfig(context,settingList);
        List<String> currentCity=null;
        currentCity=FileManage.setCityIdList(context,currentCity);
        List<City> cityList=null;
        cityList=FileManage.getCityList(context,cityList);
        int index=0;
        for(int i=0;i<cityList.size();i++){
            if(cityList.get(i).getCityId().equals(currentCity.get(0))){
                index=i;
            }
        }

        if(settingList.isEmpty()){
            settingList.add(new Setting("Location","Changsha",false));
            settingList.add(new Setting("Temperature Unit",false));
            settingList.add(new Setting("Weather Notification",true,false));
            List<City> temp=new ArrayList<>();
            temp.add(new City("Changsha","101250101"));
            FileManage.saveSettingConfig(context,settingList);
            FileManage.saveIsChecked(context,false);
            FileManage.saveisCelsius(context,true);
            FileManage.saveLocationCity(context,new City("Changsha","101250101"));
            FileManage.saveCityList(context,temp);
        }else{
            settingList=new ArrayList<>();
            settingList.add(new Setting("Location",cityList.get(index).getCityName(),false));
            settingList.add(new Setting("Temperature Unit",false));
            settingList.add(new Setting("Weather Notification",true,FileManage.getIsChecked(context)));
            FileManage.saveSettingConfig(context,settingList);
        }
        FileManage.saveSettingConfig(context,settingList);
        return settingList;
    }

    public void setLocation(City city){
        this.currentCity=city.getCityId();
    }

    public SettingUpdate(Context context) {
        this.context = context;
        isCelsius=FileManage.getIsCelsius(context);
        isChecked=FileManage.getIsChecked(context);
        List<String> tempList = null;
        tempList= FileManage.setCityIdList(context,tempList);
        if(!tempList.isEmpty() && tempList!=null){
            this.currentCity=tempList.get(0);
        }else{
            this.currentCity="Changsha";
        }
    }

    public void changeCity(String cityId){
        this.currentCity=cityId;
    }

    public String getCurrentCity(){
        return this.currentCity;
    }

    public boolean isCelsius() {
        return isCelsius;
    }

    public void setCelsius(boolean celsius) {
        isCelsius = celsius;
        FileManage.saveisCelsius(context,celsius);
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
        FileManage.saveIsChecked(context,checked);
    }
}

/**
 * 思路：从本地读取三个SettingItem的配置文件，而后根据不同情况读取，而后从City文件中读取已经存在的city，默认摄氏度
 */
