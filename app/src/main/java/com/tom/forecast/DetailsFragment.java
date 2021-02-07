package com.tom.forecast;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.DeadObjectException;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.tom.forecast.bean.Weather;
import com.tom.forecast.bean.WeatherInfor;
import com.tom.forecast.utils.GenerateDate;
import com.tom.forecast.utils.SettingUpdate;
import com.tom.forecast.utils.WeatherAPI;

public class DetailsFragment extends Fragment {

    private Weather weatherInfor;

    private TextView detailsDate, detailsDay, detailsMax, detailsMin,detailsWeather, detailsPressure,
                    detailsHumidity,detailsWind;
    private ImageView detailsIcon;
    private LinearLayout layout;

    private final static String WEATHER_TOKEN="weatherInfor Instance";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.weatherInfor= (Weather) getArguments().getSerializable(WEATHER_TOKEN);
        System.out.println(this.weatherInfor.toString());
    }

    public static DetailsFragment newInstance(Weather weatherInfor){
        Bundle bundle = new Bundle();//存放数据
        bundle.putSerializable(WEATHER_TOKEN, weatherInfor);
        Log.i(WEATHER_TOKEN,weatherInfor.toString());
        DetailsFragment fragment = new DetailsFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.weather_details,container,false);
        layout=view.findViewById(R.id.weather_layout);
        detailsDate=view.findViewById(R.id.details_date);
        detailsDay=view.findViewById(R.id.details_day);
        detailsMax=view.findViewById(R.id.details_max);
        detailsMin=view.findViewById(R.id.details_min);
        detailsWeather=view.findViewById(R.id.details_weather);
        detailsPressure=view.findViewById(R.id.details_pressure);
        detailsHumidity=view.findViewById(R.id.details_humidity);
        detailsWind=view.findViewById(R.id.details_wind);
        detailsIcon=view.findViewById(R.id.details_icon);

        return view;
    }

    @SuppressLint("SetTextI18n")
    private void updateUI(Weather weatherInfor){
        this.detailsDate.setText(GenerateDate.generateTime(weatherInfor.getDay()));
        this.detailsDay.setText(weatherInfor.getMonth()+" "+weatherInfor.getDay());
        this.detailsMax.setText(weatherInfor.getMaxTemp()+"℃");
        this.detailsMin.setText(weatherInfor.getMinTmep()+"℃");
        this.detailsWeather.setText(weatherInfor.getWeatherDescription());
        this.detailsPressure.setText("Pressure: "+weatherInfor.getPressure()+" hPa");
        this.detailsHumidity.setText("Humudity: "+weatherInfor.getHumidity()+" %");
        this.detailsWind.setText("Wind: "+weatherInfor.getWindSpeed()+"km/h "+weatherInfor.getWindDir());
        String iconID=weatherInfor.getIconId();
        int resID = getResources().getIdentifier("icon"+iconID, "drawable", "com.tom.forecast");
        Drawable image = getResources().getDrawable(resID);
        this.detailsIcon.setImageDrawable(image);
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI(weatherInfor);
    }




}
