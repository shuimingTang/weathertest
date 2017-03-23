package com.tang.study.simpleweather.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Tangshuiming99 on 2017/3/22.
 */
public class AQI {

    @SerializedName("city")
    public AQICity aqiCity;
    public class AQICity{
        public String aqi;
        public String pm25;
    }
}
