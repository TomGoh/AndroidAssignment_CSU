package com.tom.forecast;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tom.forecast.bean.City;
import com.tom.forecast.bean.Weather;
import com.tom.forecast.bean.WeatherInfor;
import com.tom.forecast.utils.FileManage;
import com.tom.forecast.utils.GenerateDate;
import com.tom.forecast.utils.SettingUpdate;
import com.tom.forecast.utils.WeatherAPI;
import com.tom.forecast.utils.WeatherHelper;

import org.json.JSONException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ListFragment extends Fragment {

    public List<Weather> weathers;

    public Weather currentWeather;

    private RecyclerView recyclerView;

    private Context context;

    private List<String> cityIdList;

    WeatherHelper weatherHelper;

    SettingUpdate settingUpdate;

    private static final String TAG="LIST FRAGMENT TAG";

    //callback
    private Callbacks Callbacks;
    //定义接口，让其子类实现，可以获得onPostExecute中的数值
    public interface Callbacks {

        void updateWeather();

        void onListItemClick(int position);

    }

    public void onAttach(Context context) {
        super.onAttach(context);
        Callbacks = (Callbacks) context;
    }

    public void setContext(Context context){
        this.context=context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.weather_list,container,false);
        recyclerView=view.findViewById(R.id.weather_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        weatherHelper=new WeatherHelper(ListFragment.this.getActivity());
        GetWeather getWeather1=new GetWeather();
        getWeather1.execute();
        settingUpdate= new SettingUpdate(this.getContext());
        PullService.setServiceAlarm(this.getActivity(),settingUpdate.isChecked());
    }

    @Override
    public void onResume() {
        super.onResume();
        GetWeather getWeather=new GetWeather();
        getWeather.execute();
        settingUpdate= new SettingUpdate(this.getContext());
        PullService.setServiceAlarm(this.getActivity(),settingUpdate.isChecked());
    }

    private class GetWeather extends AsyncTask<Void,Void,List<Weather>>{

        @Override
        protected List<Weather> doInBackground(Void... voids) {
            WeatherAPI weatherAPI=new WeatherAPI();
            List<WeatherInfor> weatherInforList=null;
            List<Weather> weatherList=null;
            String locationId=null;
            cityIdList= FileManage.setCityIdList(getContext(),cityIdList);
            if(context==null){
                context=getContext();
            }
            if(cityIdList.isEmpty() && isNetworkConnected(context)){
                locationId="101250101";
                FileManage.saveLocationCity(context,new City("Changsha",locationId));
            }else{
                locationId=new SettingUpdate(context).getCurrentCity();
            }

            if(!isNetworkConnected(context)){
                weatherList=weatherHelper.queryCityWeatherById(locationId);
            }

            if(weatherList==null||weatherList.isEmpty()||isNetworkConnected(context)){
                try {
                    Log.i(TAG,"current city:"+locationId);
                    settingUpdate=new SettingUpdate(context);
                    if(settingUpdate.isCelsius()){
                        weatherInforList=weatherAPI.getWeather10Days(locationId);
                    }else{
                        weatherInforList=weatherAPI.getWeather10DaysInF(locationId);
                    }

                    weatherList=new ArrayList<>();
                    for(WeatherInfor weatherInfor:weatherInforList){
                        weatherList.add(weatherInfor.toWeather(locationId));

                    }
                    weatherHelper.clearAll(weatherList.get(0).getCityId());
                    for(Weather weather:weatherList){
                        Log.i(TAG,weather.toString());
                        weatherHelper.addNewWeatherRecord(weather);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return weatherList;
        }

        @Override
        protected void onPostExecute(List<Weather> weatherList) {
            super.onPostExecute(weatherList);
            if(weatherList==null){
                Log.i("Async ","Fetching Failed.");
            }else{
                ListFragment.this.weathers=weatherList;
                currentWeather=weatherList.get(0);
                for(int i=0;i<1;i++){
                    weatherList.remove(i);
                }
                ListAdapter adapter = new ListAdapter(weathers);
                recyclerView.setAdapter(adapter);
                Callbacks.updateWeather();
            }
        }
    }

    class ListHolder extends RecyclerView.ViewHolder{
        public TextView dayItem, tempMinItem,tempMaxItem,weatherItem;
        public ImageView weatherIconItem;

        public ListHolder(View view){
            super(view);
            dayItem=view.findViewById(R.id.weather_item_day);
            tempMinItem=view.findViewById(R.id.weather_item_temp_min);
            tempMaxItem=view.findViewById(R.id.weather_item_temp_max);
            weatherIconItem=view.findViewById(R.id.weather_item_icon);
            weatherItem=view.findViewById(R.id.weather_item_weather);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Callbacks.onListItemClick(getAdapterPosition());
                }
            });
        }
    }

    class ListAdapter extends RecyclerView.Adapter<ListHolder>{

        private List<Weather> weatherInforList;

        public ListAdapter(List<Weather> weatherInforList) {
            this.weatherInforList = weatherInforList;
        }

        @NonNull
        @Override
        public ListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater=LayoutInflater.from(getActivity());
            View view=inflater.inflate(R.layout.weather_list_item,parent,false);
            return new ListHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ListHolder holder, int position) {
            Weather weatherInfor=weatherInforList.get(position);
            String iconID=weatherInfor.getIconId();
            int resID = getResources().getIdentifier("icon"+iconID, "drawable", "com.tom.forecast");
            Drawable image = getResources().getDrawable(resID);
            holder.weatherIconItem.setImageDrawable(image);
            if(settingUpdate.isCelsius()){
                holder.tempMaxItem.setText(weatherInfor.getMaxTemp()+"℃");
                holder.tempMinItem.setText(weatherInfor.getMinTmep()+"℃");
            }else{
                holder.tempMaxItem.setText(weatherInfor.getMaxTemp()+"℉");
                holder.tempMinItem.setText(weatherInfor.getMinTmep()+"℉");
            }

            holder.weatherItem.setText(weatherInfor.getWeatherDescription());
            holder.dayItem.setText(GenerateDate.generateTime(weatherInfor.getDay()));
        }

        @Override
        public int getItemCount() {
            return weatherInforList.size();
        }
    }

    public boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

}
