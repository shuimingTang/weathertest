package com.tang.study.simpleweather.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.tang.study.simpleweather.R;
import com.tang.study.simpleweather.common.GlobalConast;
import com.tang.study.simpleweather.util.HttpCallbackListener;
import com.tang.study.simpleweather.util.HttpUtil;
import com.tang.study.simpleweather.util.LogUtil;
import com.tang.study.simpleweather.util.StringUtil;

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
