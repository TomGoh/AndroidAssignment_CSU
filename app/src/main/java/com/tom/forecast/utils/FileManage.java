package com.tom.forecast.utils;

import android.content.Context;
import android.util.Log;

import com.tom.forecast.bean.City;
import com.tom.forecast.bean.Setting;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class FileManage {

    public static List<String> setCityIdList(Context context, List<String> cityIdList)  {
        FileInputStream fis= null;
        City currentCity;
        try {
            fis = context.openFileInput("CurrentCity");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            cityIdList=new ArrayList<>();
            cityIdList.add("101250101");
            saveLocationCity(context,new City("Changsha",cityIdList.get(0)));
            return cityIdList;
        }
        ObjectInputStream objectInputStream= null;
        try {
            objectInputStream = new ObjectInputStream(fis);
        } catch (IOException e) {
            e.printStackTrace();
            cityIdList=new ArrayList<>();
            cityIdList.add("101250101");
            saveLocationCity(context,new City("Changsha",cityIdList.get(0)));
            return cityIdList;
        }
        try {
            currentCity=(City)objectInputStream.readObject();
            if(currentCity!=null){
                if (cityIdList == null) {
                    cityIdList = new ArrayList<>();
                }
                cityIdList.add(currentCity.getCityId());
                return cityIdList;
            }
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
            cityIdList=new ArrayList<>();
            cityIdList.add("101250101");
            return cityIdList;
        }finally {
            try {
                objectInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return cityIdList;
    }

    public static void saveLocationCity(Context context,City city){
        FileOutputStream fos = null;
        ObjectOutputStream objectOutput=null;
        try {
            fos=context.openFileOutput("CurrentCity",Context.MODE_PRIVATE);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            objectOutput=new ObjectOutputStream(fos);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            objectOutput.writeObject(city);
            objectOutput.flush();
            objectOutput.close();
            fos.close();
//            Log.i(TAG,"CityList saved.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Setting> loadSettingConfig(Context context, List<Setting> settingList){
        FileInputStream fis= null;
        try {
            fis = context.openFileInput("SettingConfig");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            settingList=new ArrayList<>();
            return settingList;
        }
        ObjectInputStream objectInputStream= null;
        try {
            objectInputStream = new ObjectInputStream(fis);
        } catch (IOException e) {
            e.printStackTrace();
            settingList=new ArrayList<>();
            return settingList;
        }
        try {
            settingList=(List<Setting>)objectInputStream.readObject();
            return settingList;
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
            settingList=new ArrayList<>();
            return settingList;
        }finally {
            try {
                objectInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void saveSettingConfig(Context context,List<Setting> settingList){
        FileOutputStream fos = null;
        ObjectOutputStream objectOutput=null;
        try {
            fos=context.openFileOutput("SettingConfig",Context.MODE_PRIVATE);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            objectOutput=new ObjectOutputStream(fos);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            objectOutput.writeObject(settingList);
            objectOutput.flush();
            objectOutput.close();
            fos.close();
//            Log.i(TAG,"CityList saved.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<City> getCityList(Context context, List<City> cityList){
        FileInputStream fis= null;
        try {
            fis = context.openFileInput("CityList");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            cityList=new ArrayList<>();
            return cityList;
        }
        ObjectInputStream objectInputStream= null;
        try {
            objectInputStream = new ObjectInputStream(fis);
        } catch (IOException e) {
            e.printStackTrace();
            cityList=new ArrayList<>();
            return cityList;
        }
        try {
            cityList=(List<City>)objectInputStream.readObject();
            return cityList;
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
            cityList=new ArrayList<>();
            return cityList;
        }finally {
            try {
                objectInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void saveCityList(Context context,List<City> cityList){
        FileOutputStream fos = null;
        ObjectOutputStream objectOutput=null;
        try {
            fos=context.openFileOutput("CityList",Context.MODE_PRIVATE);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            objectOutput=new ObjectOutputStream(fos);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            objectOutput.writeObject(cityList);
            objectOutput.flush();
            objectOutput.close();
            fos.close();
//            Log.i(TAG,"CityList saved.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean getIsCelsius(Context context){
        FileInputStream fis= null;
        boolean isCelsius;
        try {
            fis = context.openFileInput("IsCelsius");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return true;
        }
        ObjectInputStream objectInputStream= null;
        try {
            objectInputStream = new ObjectInputStream(fis);
        } catch (IOException e) {
            e.printStackTrace();
            return true;
        }
        try {
            isCelsius=(boolean)objectInputStream.readObject();
            return isCelsius;
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
            return true;
        }finally {
            try {
                objectInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void saveisCelsius(Context context,boolean option){
        FileOutputStream fos = null;
        ObjectOutputStream objectOutput=null;
        try {
            fos=context.openFileOutput("IsCelsius",Context.MODE_PRIVATE);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            objectOutput=new ObjectOutputStream(fos);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            objectOutput.writeObject(option);
            objectOutput.flush();
            objectOutput.close();
            fos.close();
//            Log.i(TAG,"CityList saved.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean getIsChecked(Context context){
        FileInputStream fis= null;
        boolean isChecked;
        try {
            fis = context.openFileInput("IsChecked");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        ObjectInputStream objectInputStream= null;
        try {
            objectInputStream = new ObjectInputStream(fis);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        try {
            isChecked=(boolean)objectInputStream.readObject();
            return isChecked;
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
            return false;
        }finally {
            try {
                objectInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void saveIsChecked(Context context,boolean option){
        FileOutputStream fos = null;
        ObjectOutputStream objectOutput=null;
        try {
            fos=context.openFileOutput("IsChecked",Context.MODE_PRIVATE);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            objectOutput=new ObjectOutputStream(fos);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            objectOutput.writeObject(option);
            objectOutput.flush();
            objectOutput.close();
            fos.close();
//            Log.i(TAG,"CityList saved.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
