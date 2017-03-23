package com.tang.study.simpleweather.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Tangshuiming99 on 2017/3/22.
 */
public class Forecast {

    public String date;

    @SerializedName("tmp")
    public Temperature temperature;

    @SerializedName("cond")
    public More more;

    public class Temperature{
        public String min;
        public String max;
    }

    public class More{
        @SerializedName("txt_d")
        public String info;
    }
}
