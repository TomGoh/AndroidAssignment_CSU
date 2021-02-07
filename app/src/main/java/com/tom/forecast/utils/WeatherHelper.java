package com.tom.forecast.utils;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.tom.forecast.bean.Weather;
import com.tom.forecast.database.WeatherDB;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据库工具类，链接天气数据库WeatherDB，执行存取操作
 */
public class WeatherHelper {

    private WeatherDB db;
    private Activity activity;


    private static final String INSERT_WEATHER="insert into weather values(?,?,?,?,?,?,?,?,?,?,?,?)";

    private static final String DATABASE_NAME="weatherHelper.database";

    private static final String QUERY_BY_CITY_ID="select * from weather where cityId=?";

    private static final String DELETE_ALL_BY_ID="delete from weather where cityId=?";

    private static final String TAG="WEATHER HELPER";
    /**
     * 初始化类，传入唤起Activity的上下文
     * @param activity 唤起该工具类的上下文
     */
    public WeatherHelper( Activity activity) {

        this.activity=activity;
        this.db = new WeatherDB(activity,DATABASE_NAME, null,1);
    }

    /**
     * 执行向WeatherDB中插入新的单条天气数据
     * @param weather 单条天气数据
     * @return 插入的结果，出错则返回false
     */
    public boolean addNewWeatherRecord(Weather weather){
        SQLiteDatabase database=db.getWritableDatabase();
        try{
            database.execSQL(INSERT_WEATHER,new String[]{
                    weather.getCityId(),
                    weather.getMonth(),
                    weather.getDay(),
                    weather.getWeekday(),
                    weather.getMaxTemp(),
                    weather.getMinTmep(),
                    weather.getWeatherDescription(),
                    weather.getIconId(),
                    weather.getHumidity(),
                    weather.getWindDir(),
                    weather.getWindSpeed(),
                    weather.getPressure()
            });
        }catch (Exception e){
            return false;
        }
        return true;
    }

    public List<Weather> queryCityWeatherById(String cityId){
        SQLiteDatabase database=db.getReadableDatabase();
        List<Weather> weatherList=new ArrayList<>();
        try{
            Cursor cursor=database.rawQuery(QUERY_BY_CITY_ID,new String[]{cityId});
            while(cursor.moveToNext()){
                String queriedCityId=cursor.getString(0);
                String month=cursor.getString(1);
                String day=cursor.getString(2);
                String weedDay=cursor.getString(3);
                String maxTemp=cursor.getString(4);
                String minTemp=cursor.getString(5);
                String weatherDescription=cursor.getString(6);
                String iconId=cursor.getString(7);
                String humidity=cursor.getString(8);
                String windDir=cursor.getString(9);
                String windSpeed=cursor.getString(10);
                String pressure=cursor.getString(11);
                Weather weather=new Weather(queriedCityId,month,day,weedDay,maxTemp,minTemp,
                        weatherDescription,iconId,humidity,windDir,windSpeed,pressure);
                weatherList.add(weather);
            }
        }catch (Exception e){
            Log.i(TAG,"Query single city weather error.");
        }
        return weatherList;
    }

    public void clearAll(String cityId){
        SQLiteDatabase database=db.getReadableDatabase();
        database.rawQuery(DELETE_ALL_BY_ID,new String[]{cityId});
        database.delete("weather","cityId=?",new String[]{cityId});
    }
}
