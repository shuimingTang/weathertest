package com.tang.study.simpleweather.entry;

import java.io.Serializable;

/**
 * Created by Tangshuiming99 on 2017/3/21.
 */
public class City implements Serializable{
    private int id;
    private String cityName;
    private int cityCode;
    private int provinceId;

    public City(){

    }

    public City(int id, String cityName, int cityCode, int provinceId){
        this.id = id;
        this.cityName = cityName;
        this.cityCode = cityCode;
        this.provinceId = provinceId;
    }

    public int getId() {
        return id;
    }

    public String getCityName() {
        return cityName;
    }

    public int getProvinceId() {
        return provinceId;
    }

    public int getCityCode() {
        return cityCode;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }

    public void setCityCode(int cityCode) {
        this.cityCode = cityCode;
    }

    @Override
    public String toString() {
        return "city name:" + cityName + ",city code;" + cityCode + ",province id:" + provinceId;
    }
}
