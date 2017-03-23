package com.tang.study.simpleweather.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Tangshuiming99 on 2017/3/22.
 */
public class Basic {

    @SerializedName("city")
    public String cityName;//使用注解，使得json字段和Java字段建立映射
    @SerializedName("id")
    public String weatherId;//天气Id

    public Update update;

    public class Update {
        @SerializedName("loc")
        public String updateTime;//更新时间
    }
}
