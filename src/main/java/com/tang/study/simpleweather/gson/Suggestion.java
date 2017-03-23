package com.tang.study.simpleweather.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Tangshuiming99 on 2017/3/22.
 */
public class Suggestion {

    @SerializedName("comf")
    public Comfort comfort;

    @SerializedName("cw")
    public CarWash carWash;

    public Sport sport;

    public class Comfort{
        @SerializedName("txt")
        public String info;
    }

    public class CarWash{
        @SerializedName("txt")
        public String into;
    }

    public class Sport{
        @SerializedName("txt")
        public String info;
    }
}
