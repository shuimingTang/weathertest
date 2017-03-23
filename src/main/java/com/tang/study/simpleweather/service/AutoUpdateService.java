package com.tang.study.simpleweather.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.tang.study.simpleweather.common.GlobalConast;
import com.tang.study.simpleweather.gson.Weather;
import com.tang.study.simpleweather.util.HttpCallbackListener;
import com.tang.study.simpleweather.util.HttpUtil;
import com.tang.study.simpleweather.util.LogUtil;
import com.tang.study.simpleweather.util.ParserUtil;
import com.tang.study.simpleweather.util.StringUtil;

/**
 * 后台自动更新天气
 * @author shuiming.tang
 * @date 2017-03-23
 */
public class AutoUpdateService extends Service{

    private Handler mainHandler;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        LogUtil.e(getClass().getSimpleName(), "启动自动更新启动...");
        mainHandler = new Handler(Looper.getMainLooper());
        updateWeather();
        updateBingPic();
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        long anHour = 8*60*60*1000;//8小时后台自动更新
        long triggerAtTime = SystemClock.elapsedRealtime() + anHour;
        Intent i = new Intent(this, AutoUpdateService.class);
        PendingIntent pi = PendingIntent.getService(
                this, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.cancel(pi);//移除上一次，设置的定时
        if(Build.VERSION.SDK_INT >= 19){
            alarmManager.setExact(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pi);
        }
            else {
            alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pi);
        }
        return /*super.onStartCommand(intent, flags, startId)*/START_STICKY;
    }

    //请求天气数据
    public void updateWeather() {
        final SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(AutoUpdateService.this);
        String weatherId = sp.getString("", "");
        if (!"".equals(weatherId)) {
            String address = GlobalConast.WEATHER_URL +
                    "cityid=" + weatherId + "&key=" + GlobalConast.KEY;
            HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {
                @Override
                public void onFinish(String response) {
                    //child thread
                    try{
                        if(!StringUtil.isEmpty(response)){
                            final Weather weather = ParserUtil.handleWeatherResponse(response);
                            if(weather != null && "ok".equals(weather.status)){
                                SharedPreferences.Editor editor = sp.edit();
                                editor.putString("weather", response);
                                editor.apply();
                            }
                            else {
                                LogUtil.e(getClass().getSimpleName(), "更新天气失败");
                            }
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                @Override
                public void onError(Exception e) {
                    e.printStackTrace();
                    LogUtil.e(getClass().getSimpleName(), "更新天气失败");
                }
            });
        }
    }

    private void updateBingPic(){
        //加载必应每日一图
        HttpUtil.sendHttpRequest(GlobalConast.BING_PIC, new HttpCallbackListener() {
            @Override
            public void onFinish(final String response) {
                if(!StringUtil.isEmpty(response)){
                    SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(AutoUpdateService.this);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("bing_pic", response);
                    editor.apply();
                }
                else {
                    LogUtil.e(getClass().getSimpleName(), "更新必应图片失败");
                }
            }
            @Override
            public void onError(Exception e) {
                e.printStackTrace();
                LogUtil.e(getClass().getSimpleName(), "更新必应图片失败");
            }
        });
    }
}
