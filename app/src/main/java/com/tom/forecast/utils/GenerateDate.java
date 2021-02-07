package com.tom.forecast.utils;

import android.content.Intent;

import com.tom.forecast.bean.WeatherInfor;

import java.util.Date;

public class GenerateDate {

    private static final String[] months={"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sept","Oct","Nov","Dec"};
    private static final String[] days={"Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday"};
    private static final String[] addDays={"Mon","Tues","Wed","Thur","Fri","Sat","Sun"};
    static Date date;
    public static String getDate(){
        date=new Date();
        String[] dates=date.toString().split(" ");
        System.out.println(date.toString());
        for (String month:months){
            if(month.endsWith(dates[1])){
                return month+" "+dates[2];
            }
        }
        return null;
    }

    public static String getTime(){
        return date.toString().split(" ")[3];
    }

    GenerateDate(){
        date=new Date();
    }

    public static String generateTime(String time){
        date=new Date();
        String[] dates=date.toString().split(" ");
        if(Integer.parseInt(dates[2])==(Integer.parseInt(time)-1)){
            return "Tomorrow";
        }else{
            String today=dates[0];
            int temp=0;
            for(int i=0;i<=6;i++){
                if(addDays[i].equals(today)){
                    temp=i;
                }
            }
            int temp2=(Integer.parseInt(time))-Integer.parseInt(dates[2]);
            return addDays[(7+temp+temp2)%7];
        }
    }

    public static String parseDay(WeatherInfor weatherInfor){
        String[] timeInInfor=weatherInfor.getFxDate().split("-");
        int index=Integer.parseInt(timeInInfor[1])-1;
        return months[index]+" "+timeInInfor[2];
    }

    public static String getMonth(String fxDate){
        String[] timeInInfor=fxDate.split("-");
        int index=Integer.parseInt(timeInInfor[1])-1;
        return months[index];
    }

    public static String getRealDate(String fxDate){
        return fxDate.split("-")[2];
    }

    public static String getWeekDay(String time){
        date=new Date();
        String[] times=time.split("-");
        String[] dates=date.toString().split(" ");
        String today=dates[0];
        int temp=0;
        for(int i=0;i<=6;i++){
            if(addDays[i].equals(today)){
                temp=i;
            }
        }
        int temp2=(Integer.parseInt(times[2]))-Integer.parseInt(dates[2]);
        return addDays[(7+temp+temp2)%7];
    }
}
