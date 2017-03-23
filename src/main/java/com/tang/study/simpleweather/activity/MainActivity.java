package com.tang.study.simpleweather.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.tang.study.simpleweather.R;
import com.tang.study.simpleweather.util.LogUtil;

/**
 * Created by Tangshuiming99 on 2017/3/23.
 */
public class MainActivity extends BaseActivity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        String weatherResponse = sp.getString("weather", "");
        LogUtil.e("TAG", "data:" + weatherResponse);
        if(!"".equals(weatherResponse)){
            Intent intent = new Intent(this, WeatherActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
