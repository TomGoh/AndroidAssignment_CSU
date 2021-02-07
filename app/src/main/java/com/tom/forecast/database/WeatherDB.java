package com.tom.forecast.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.tom.forecast.bean.Weather;
import com.tom.forecast.bean.WeatherInfor;

import java.util.ArrayList;
import java.util.List;

public class WeatherDB extends SQLiteOpenHelper {

    private static final String TAG="WEATHER_DB";

    private static final String CREATE_WEATHER_DB="create table weather("+
            "cityId varchar(30),"+
            "month varchar(10),"+
            "day varchar(10),"+
            "weekday varchar(10),"+
            "maxTemp number,"+
            "minTemp number,"+
            "weatherDescription varchar(20),"+
            "iconId number,"+
            "humidity number,"+
            "windDir varchar(10),"+
            "windSpeed number,"+
            "pressure number)";

    private static final String INSERT_WEATHER="insert into weather values(?,?,?,?,?,?,?,?,?,?,?,?)";

    private static final String SELECT_ALL="select * from weather";

    private static final String queryByAll="select * from weather";

    public WeatherDB(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(SELECT_ALL);

        } catch (Exception e) {
            Log.d(TAG, "no table found, do init");
            db.execSQL(CREATE_WEATHER_DB);
            db.execSQL(INSERT_WEATHER,new String[]{"ABC","Jan","1","Mon","100","0","Clear","999","100","WN","5","10000"});
            testAll(db);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private void testAll(SQLiteDatabase db){
        Cursor cursor=db.rawQuery(queryByAll,null);
        List<WeatherInfor> weatherInforList=new ArrayList<>();
        while (cursor.moveToNext()){
            String cityId=cursor.getString(0);
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
            Log.i(TAG,new Weather(cityId,month,day,weedDay,maxTemp,minTemp,
                    weatherDescription,iconId,humidity,windDir,windSpeed,pressure).toString());
        }
    }
}
