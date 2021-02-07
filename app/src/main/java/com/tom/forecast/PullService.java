package com.tom.forecast;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.ListActivity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.os.SystemClock;
import android.util.Log;
import android.util.TimeUtils;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.tom.forecast.bean.Weather;
import com.tom.forecast.bean.WeatherInfor;
import com.tom.forecast.utils.SettingUpdate;
import com.tom.forecast.utils.WeatherAPI;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class PullService extends IntentService {

    private static final String TAG="PullService";

    private static final long PULL_INTERVAL_MS= TimeUnit.MINUTES.toMillis(30);

    public PullService(){
        super(TAG);
    }



    public static Intent newIntent(Context context){
        return new Intent(context, PullService.class);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if(!isNetWorkAvaiableAndConnected()){
            return;
        }
        Log.i(TAG,"Received an intent: "+intent);
        WeatherAPI weatherAPI=new WeatherAPI();
        SettingUpdate settingUpdate=new SettingUpdate(this.getApplicationContext());
        Log.i(TAG,"Current city:"+settingUpdate.getCurrentCity());
        List<WeatherInfor> rawInfor = null;
        boolean isMeric=settingUpdate.isCelsius();
        try {
            if(isMeric){
                rawInfor=weatherAPI.getWeather10Days(settingUpdate.getCurrentCity());
            }else{
                rawInfor=weatherAPI.getWeather10DaysInF(settingUpdate.getCurrentCity());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if(rawInfor!=null) {
            Log.i(TAG,rawInfor.get(0).toString());
        }

        String iconID=rawInfor.get(0).getIconDay();
        int resID = getResources().getIdentifier("icon"+iconID, "drawable", "com.tom.forecast");
        Drawable image = getResources().getDrawable(resID);
        String content;
        if(isMeric){
            content=rawInfor.get(0).getTextDay()+", High "+rawInfor.get(0).getTempMax()+"℃ Low "+rawInfor.get(0).getTempMin()+"℃";
        }else{
            content=rawInfor.get(0).getTextDay()+", High "+rawInfor.get(0).getTempMax()+"℉ Low "+rawInfor.get(0).getTempMin()+"℉";
        }
        Intent intent1 = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        String channelId = createNotificationChannel("my_channel_ID", "my_channel_NAME", NotificationManager.IMPORTANCE_HIGH);
        NotificationCompat.Builder notification = new NotificationCompat.Builder(this, channelId)
                .setContentTitle("Weather Forecast")
                .setContentText(content)
                .setContentIntent(pendingIntent)
                .setSmallIcon(resID)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(100, notification.build());
    }

    private boolean isNetWorkAvaiableAndConnected(){
        ConnectivityManager connectivityManager=(ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        boolean isNetworkAvailable=connectivityManager.getActiveNetworkInfo()!=null;
        //boolean isNetworkConnected =isNetworkAvailable && connectivityManager.getActiveNetworkInfo().isConnected();
        //return  isNetworkConnected;
        return isNetworkAvailable;
    }

    public static void setServiceAlarm(Context context,boolean isOn){

        Intent intent=PullService.newIntent(context);
        PendingIntent pendingIntent=PendingIntent.getService(context,0,intent,0);
        AlarmManager alarmManager=(AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        if(isOn){
            alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime(),PULL_INTERVAL_MS,pendingIntent);
        }else{
            alarmManager.cancel(pendingIntent);
            pendingIntent.cancel();
        }

    }

    private String createNotificationChannel(String channelID, String channelNAME, int level) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            NotificationChannel channel = new NotificationChannel(channelID, channelNAME, level);
            manager.createNotificationChannel(channel);
            return channelID;
        } else {
            return null;
        }
    }
}
