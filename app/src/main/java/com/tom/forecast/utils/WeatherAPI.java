package com.tom.forecast.utils;

import com.alibaba.fastjson.JSON;
import com.tom.forecast.bean.CurrentWeather;
import com.tom.forecast.bean.WeatherInfor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class WeatherAPI {

    private static  final String key="406a0a4573cd46cba2f0df6e51cfea79";
    private static final String publicId="HE2012141646581709";

    public byte[] getUrlBytes(String urlSpec) throws IOException{
        URL url=new URL(urlSpec);
        HttpURLConnection connection =(HttpURLConnection) url.openConnection();

        try{
            ByteArrayOutputStream out=new ByteArrayOutputStream();
            InputStream in=connection.getInputStream();

            if(connection.getResponseCode()!=HttpURLConnection.HTTP_OK){
                throw new IOException(connection.getResponseMessage()+": with"+urlSpec);
            }

            int bytesRead=0;
            byte[] buffer=new byte[1024000];

            while((bytesRead=in.read(buffer))>0){
                out.write(buffer,0,bytesRead);
            }
            out.close();
            return out.toByteArray();
        }finally {
            connection.disconnect();
        }
    }

    public String getUrlString(String urlSpec) throws IOException{
        return new String (getUrlBytes(urlSpec));
    }

    public CurrentWeather getCurrentWeatherInformation(String locationId) throws IOException, JSONException {
        String urlSpace="https://devapi.qweather.com/v7/weather/now?location="+locationId+"&key="+key;
        String result= getUrlString(urlSpace);
        JSONObject jsonObject=new JSONObject(result);
        String nowInfor=jsonObject.get("now").toString();
        return com.alibaba.fastjson.JSONObject.parseObject(nowInfor,CurrentWeather.class);
    }

    public List<String> getGeoInformationByName(String cityName) throws IOException, JSONException {
        final String urlSpace="https://geoapi.qweather.com/v2/city/lookup?location="+cityName+"&key="+key+"&number=1&gzip=n";
        System.out.println(urlSpace);
        JSONObject jsonObject=new JSONObject(getUrlString(urlSpace));
        JSONArray locationJson=jsonObject.getJSONArray("location");
        System.out.println("locationJson:"+locationJson);
        String locationArray= locationJson.getString(0);
        System.out.println("location:"+locationArray);
        JSONObject jsonObject1 = new JSONObject(locationArray);
        String lat=jsonObject1.getString("lat");
        String lon=jsonObject1.getString("lon");
        System.out.println("Lat:"+lat+" Lon:"+lon);
        List<String> geoInfor=new ArrayList<>();
        geoInfor.add(lat);
        geoInfor.add(lon);
        return geoInfor;
    }
    //返回一个城市名搜索的首个结果的ID
    public String getLocationID(String cityName) throws IOException, JSONException {
        final String urlSpace="https://geoapi.qweather.com/v2/city/lookup?location="+cityName+"&key="+key+"&number=1&gzip=n";
        System.out.println(urlSpace);
        JSONObject jsonObject=new JSONObject(getUrlString(urlSpace));
        JSONArray locationJson=jsonObject.getJSONArray("location");
        System.out.println("locationJson:"+locationJson);
        String locationArray= locationJson.getString(0);
        System.out.println("location:"+locationArray);
        JSONObject jsonObject1 = new JSONObject(locationArray);
        String locationId=jsonObject1.getString("id");
        System.out.println("Location ID:"+locationId);
        return locationId;
    }

    //返回十天预报的JSON格式字符串，若要7天预报更改url中10d为7d即可
    public List<WeatherInfor> getWeather10Days(String cityName) throws IOException, JSONException {
        final String urlSpace="https://devapi.qweather.com/v7/weather/10d?location="+cityName+"&key="+key+"&gzip=n&lang=en";
        String result=getUrlString(urlSpace);
        JSONObject jsonObject=new JSONObject(result);
        String jsonList=jsonObject.get("daily").toString();
        System.out.println("jsonList:"+jsonList);
        jsonList=jsonList.replace("\"","'");
        System.out.println("After changed jsonList:"+jsonList);
        List<WeatherInfor> tenDaysWeather= JSON.parseArray(jsonList,WeatherInfor.class);
        System.out.println("After transed List:" + tenDaysWeather.toString());
        return tenDaysWeather;
    }

    public List<WeatherInfor> getWeather10DaysInF(String cityName) throws IOException, JSONException {
        final String urlSpace="https://devapi.qweather.com/v7/weather/10d?location="+cityName+"&key="+key+"&gzip=n&lang=en&unit=i";
        String result=getUrlString(urlSpace);
        JSONObject jsonObject=new JSONObject(result);
        String jsonList=jsonObject.get("daily").toString();
        System.out.println("jsonList:"+jsonList);
        jsonList=jsonList.replace("\"","'");
        System.out.println("After changed jsonList:"+jsonList);
        List<WeatherInfor> tenDaysWeather= JSON.parseArray(jsonList,WeatherInfor.class);
        System.out.println("After parsed List:" + tenDaysWeather.toString());
        return tenDaysWeather;
    }
}
