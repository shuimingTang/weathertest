package com.tang.study.simpleweather.activity;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.tang.study.simpleweather.R;
import com.tang.study.simpleweather.common.GlobalConast;
import com.tang.study.simpleweather.gson.Forecast;
import com.tang.study.simpleweather.gson.Weather;
import com.tang.study.simpleweather.util.HttpCallbackListener;
import com.tang.study.simpleweather.util.HttpUtil;
import com.tang.study.simpleweather.util.LogUtil;
import com.tang.study.simpleweather.util.ParserUtil;
import com.tang.study.simpleweather.util.StringUtil;

/**
 * 天气Activity
 * @author shuiming.tang
 * @date 2017-03-23
 */
public class WeatherActivity extends BaseActivity{

    private ScrollView weatherScro;
    private TextView titleCity;
    private TextView titleUpdateTime;
    private TextView degreeText;
    private TextView weatherInfoText;
    private LinearLayout forecastLayout;
    private TextView aqiText;
    private TextView pm25Text;
    private TextView comfortText;
    private TextView carWashText;
    private TextView sportText;
    private String TAG;
    private Handler mainHandler;
    private ImageView imageView;

    private void setupView(){
        weatherScro = (ScrollView)findViewById(R.id.weather_scrollView);
        titleCity = (TextView)findViewById(R.id.title_city);
        titleUpdateTime = (TextView)findViewById(R.id.title_update_time);
        degreeText = (TextView)findViewById(R.id.degree_text);
        weatherInfoText = (TextView)findViewById(R.id.weather_info_text);
        forecastLayout = (LinearLayout)findViewById(R.id.forecast_layout);
        aqiText = (TextView)findViewById(R.id.aqi_text);
        pm25Text = (TextView)findViewById(R.id.pm25_text);
        comfortText = (TextView)findViewById(R.id.comfort_text);
        carWashText = (TextView)findViewById(R.id.car_wash_text);
        sportText = (TextView)findViewById(R.id.sport_text);
        imageView = (ImageView)findViewById(R.id.bing_pic_img);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //将背景图和状态栏融合到一起
        if(Build.VERSION.SDK_INT >= 21){//5.0和以上支持该实现方式
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            |View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.weather_activity);
        TAG = getClass().getSimpleName();
        mainHandler = new Handler();
        setupView();
        init();
    }

    private void init(){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        String weatherResponse = sp.getString("weather", "");
        //显示天气
        if(!"".equals(weatherResponse)){
            Weather weather = null;
            try {
                weather = ParserUtil.handleWeatherResponse(weatherResponse);
                showWeather(weather);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {
            String weatherId = getIntent().getStringExtra("weatherId");
            if(!StringUtil.isEmpty(weatherId)){
                weatherScro.setVisibility(View.INVISIBLE);
                requestWeather(weatherId);
            }
        }
        //显示必应背景图片
        String bingPic = sp.getString("bing_pic", "");
        if(!"".equals(bingPic)){
            Glide.with(this).load(bingPic).into(imageView);
        }
        else {
            loadBingPic();
        }
    }
    //请求天气数据
    private void requestWeather(final String weatherId){
        String address = GlobalConast.WEATHER_URL +
                "cityid=" + weatherId + "&key=" + GlobalConast.KEY;
        HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                try{
                    if(!StringUtil.isEmpty(response)){
                        final Weather weather = ParserUtil.handleWeatherResponse(response);
                        LogUtil.e(TAG, "weather:" + weather);
                        if(weather != null && "ok".equals(weather.status)){
                            //child thread
                            //成功获取到天气数据
                            //存储天气数据
                            SharedPreferences sp = PreferenceManager
                                    .getDefaultSharedPreferences(WeatherActivity.this);
                            SharedPreferences.Editor editor = sp.edit();
                            editor.putString("weather", response);
                            editor.apply();
                            mainHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    //显示天气数据
                                    showWeather(weather);
                                }
                            });
                        }
                        else {
                            mainHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(WeatherActivity.this,
                                            "获取天气数据失败", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(WeatherActivity.this,
                                "获取天气数据失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        //每次刷新天气的时候，同是刷新背景图片
        loadBingPic();
    }
    //显示天气
    private void showWeather(Weather weather){
        String cityName = weather.basic.cityName;
        String updateTime = weather.basic.update.updateTime.split(" ")[1];
        LogUtil.e(TAG, "weather.basic.update.updateTime:" + (weather.basic.update.updateTime));
        LogUtil.e(TAG, "updateTime:" + updateTime);
        String degree = weather.now.temperature + "℃";
        String weatherInfo = weather.now.more.info;
        titleCity.setText(cityName);
        titleUpdateTime.setText(updateTime);
        degreeText.setText(degree);
        weatherInfoText.setText(weatherInfo);
        forecastLayout.removeAllViews();//每次清空上一次界面数据
        for(Forecast forecast : weather.forecastList){
            View view = LayoutInflater.from(this).inflate(
                    R.layout.forecast_item, forecastLayout, false);
            TextView dateText = (TextView)view.findViewById(R.id.date_text);
            TextView infoText = (TextView)view.findViewById(R.id.info_text);
            TextView maxText = (TextView)view.findViewById(R.id.max_text);
            TextView minText = (TextView)view.findViewById(R.id.min_text);
            dateText.setText(forecast.date);
            infoText.setText(forecast.more.info);
            maxText.setText(forecast.temperature.max);
            minText.setText(forecast.temperature.min);
            forecastLayout.addView(view);
        }
        if(weather.aqi != null){
            aqiText.setText(weather.aqi.aqiCity.aqi);
            pm25Text.setText(weather.aqi.aqiCity.pm25);
        }
        String comfort = "舒适度：" + weather.suggestion.comfort.info;
        String carWash = "洗车指数：" + weather.suggestion.carWash.into;
        String sport = "运动建议：" + weather.suggestion.sport.info;
        comfortText.setText(comfort);
        carWashText.setText(carWash);
        sportText.setText(sport);
        weatherScro.setVisibility(View.VISIBLE);//
    }

    private void loadBingPic(){
        //加载必应每日一图
        HttpUtil.sendHttpRequest(GlobalConast.BING_PIC, new HttpCallbackListener() {
            @Override
            public void onFinish(final String response) {
                if(!StringUtil.isEmpty(response)){
                    SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(WeatherActivity.this);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("bing_pic", response);
                    editor.apply();
                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            Glide.with(WeatherActivity.this).load(response).into(imageView);
                        }
                    });
                }
                else {
                    Toast.makeText(WeatherActivity.this, "加载每日必应图片地址失败", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onError(Exception e) {
                e.printStackTrace();
                Toast.makeText(WeatherActivity.this, "加载每日必应图片地址失败", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
