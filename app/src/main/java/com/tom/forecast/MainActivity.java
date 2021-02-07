package com.tom.forecast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tom.forecast.bean.City;
import com.tom.forecast.bean.Weather;
import com.tom.forecast.bean.WeatherInfor;
import com.tom.forecast.utils.FileManage;
import com.tom.forecast.utils.GenerateDate;
import com.tom.forecast.utils.SettingUpdate;
import com.tom.forecast.utils.WeatherAPI;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;


public class MainActivity extends SingleFragmentActivity implements ListFragment.Callbacks{

    private LinearLayout topLayout;
    private TextView topLeftDate;
    private TextView topLeftHighTemperature;
    private TextView topLeftLowTemperature;
    private ImageView topRightIcon;
    private TextView topWeather;
    private SettingUpdate settingUpdate;

    private final static String WEATHER_TOKEN="weatherInfor Instance";

    private List<Weather> weatherList;

    @Override
    protected Fragment createFragment() {
        ListFragment listFragment=new ListFragment();
        listFragment.setContext(getApplicationContext());
        return listFragment;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    private boolean isLanded(){
        return this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        if(!isLanded()){
            topLayout=findViewById(R.id.top_layout);
            topLeftDate=findViewById(R.id.top_left_date);
            topLeftHighTemperature=findViewById(R.id.top_left_high_temperature);
            topLeftLowTemperature=findViewById(R.id.top_left_low_temperature);
            topRightIcon=findViewById(R.id.top_right_icon);
            topWeather=findViewById(R.id.top_weather);

            topLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentManager fm=getSupportFragmentManager();
                    ListFragment listFragment= (ListFragment) fm.findFragmentById(R.id.fragment_container);
                }
            });
        }
    }

    @Override
    public void updateWeather() {
        FragmentManager fm = getSupportFragmentManager();
        ListFragment fragment = (ListFragment) fm.findFragmentById(R.id.fragment_container);
        weatherList = fragment.weathers;
        Weather weather = fragment.currentWeather;
        if(isLanded()){
            updateDetails(weather);
        }else{
            updateUiTop(weather);
        }

    }

    @Override
    public void onListItemClick(int position) {
        Intent intent = new Intent(this, DetailsActivity.class);
        Weather weatherInfor= weatherList.get(position);
        if(isLanded()){
            updateDetails(weatherInfor);
        }else{
            intent.putExtra(WEATHER_TOKEN, weatherInfor);
            startActivity(intent);
        }

    }

    void updateUiTop(Weather weatherInfor) {
        topLeftDate.setText(GenerateDate.getDate());
        settingUpdate=new SettingUpdate(getApplicationContext());
        if(settingUpdate.isCelsius()){
            topLeftHighTemperature.setText(weatherInfor.getMaxTemp()+"℃");
            topLeftLowTemperature.setText(weatherInfor.getMinTmep()+"℃");
        }else{
            topLeftHighTemperature.setText(weatherInfor.getMaxTemp()+"℉");
            topLeftLowTemperature.setText(weatherInfor.getMinTmep()+"℉");
        }

        String iconID=weatherInfor.getIconId();
        int resID = getResources().getIdentifier("icon"+iconID, "drawable", "com.tom.forecast");
        Drawable image = getResources().getDrawable(resID);
        topRightIcon.setImageDrawable(image);
        topWeather.setText(weatherInfor.getWeatherDescription());
    }

    void updateDetails(Weather weatherInfor) {
        Fragment fragment = DetailsFragment.newInstance(weatherInfor);

        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .replace(R.id.fragment_detail, fragment)
                .commit();
    }

    /**
     * 在该界面中设置显示顶栏的菜单为真
     * @param menu 菜单界面
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(R.menu.main_menu, menu);
            return true;
    }

    /**
     * 针对菜单点击的不同事件的响应
     * @param item 菜单中的被点击的具体项目
     * @return 执行结果
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.main_menu_location:
                try {
                    openMap();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.main_menu_settings:
                openSettings();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    private void openSettings() {
        Intent intent = new Intent(this.getApplicationContext(), SettingActivity.class);
        startActivity(intent);
    }

    private void openMap() throws IOException, JSONException {
        List<City> cityList= null;
        cityList= FileManage.getCityList(this.getApplicationContext(),cityList);
        SettingUpdate settingUpdate=new SettingUpdate(this.getApplicationContext());
        String currentCityId=settingUpdate.getCurrentCity();
        int index=0;
        for(int i=0;i<cityList.size();i++){
            if(cityList.get(i).getCityId().equals(currentCityId)){
                index=i;
            }
        }
        String cityName=cityList.get(index).getCityName();
        List<String> geoInfor=new WeatherAPI().getGeoInformationByName(cityName);
        cityName = Uri.encode(cityName);
        Uri location = Uri.parse("geo:" + geoInfor.get(0) + "," + geoInfor.get(1) + "?" +
                "q=" + cityName);
        //Log.i(TAG,location.toString());
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(location);
        startActivity(Intent.createChooser(intent, "Open with the third-part Map Application"));
    }
}