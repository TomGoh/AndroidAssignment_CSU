package com.tom.forecast;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.tom.forecast.bean.City;
import com.tom.forecast.bean.Weather;
import com.tom.forecast.bean.WeatherInfor;
import com.tom.forecast.utils.FileManage;
import com.tom.forecast.utils.SettingUpdate;
import com.tom.forecast.utils.WeatherAPI;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

public class DetailsActivity extends SingleFragmentActivity{

    private Weather weatherInfor;

    private static final String TAG="DETAILS ACTIVITY";

    private final static String WEATHER_TOKEN="weatherInfor Instance";

    @Override
    protected Fragment createFragment() {
        return DetailsFragment.newInstance(this.weatherInfor);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Intent intent=getIntent();
        this.weatherInfor= (Weather) intent.getSerializableExtra(WEATHER_TOKEN);
        //this.weatherInfor= (WeatherInfor) savedInstanceState.getSerializable(WEATHER_TOKEN);
        Log.i(WEATHER_TOKEN,"1 "+weatherInfor.toString());
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.details_menu,menu);
        setTitle("Details");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_map_location:
                try {
                    openMap();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.menu_settings:
                openSettings();
                break;
            case R.id.share_button:
                sharedApp();
                break;
            case R.id.message_share_button:
                sharedMessage();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    private void sharedApp(){
        Intent intent=new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT,weatherInfor.toString());
        startActivity(Intent.createChooser(intent,"Share Weather"));
    }

    private void sharedMessage(){
        Uri sendToSMS=Uri.parse("smsto:");
        Intent intent=new Intent(Intent.ACTION_SENDTO,sendToSMS);
        intent.putExtra("sms_body",weatherInfor.toString());
        startActivity(intent);
    }

    private void openSettings(){
        Intent intent = new Intent(getApplicationContext(), SettingActivity.class);
        startActivity(intent);
    }

    private void openMap() throws IOException, JSONException {
        List<City> cityList= null;
        cityList=FileManage.getCityList(this.getApplicationContext(),cityList);
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
        Log.i(TAG,location.toString());
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(location);
        startActivity(Intent.createChooser(intent, "Open with the third-part Map Application"));
    }

}
