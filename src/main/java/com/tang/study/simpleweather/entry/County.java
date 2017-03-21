package com.tang.study.simpleweather.entry;

import java.io.Serializable;

/**
 * Created by Tangshuiming99 on 2017/3/21.
 */
public class County implements Serializable{
    private int id;
    private String countyName;
    private String weatherId;
    private int cityId;

    public County(){

    }

    public County(int id, String countyName, String weatherId, int cityId){
        this.id = id;
        this.countyName = countyName;
        this.weatherId = weatherId;
        this.cityId = cityId;
    }

    public int getId() {
        return id;
    }

    public String getCountyName() {
        return countyName;
    }

    public String getWeatherId() {
        return weatherId;
    }

    public int getCityId() {
        return cityId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }

    public void setWeatherId(String weatherId) {
        this.weatherId = weatherId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    @Override
    public String toString() {
        return "county name:" + countyName + ",weather id:" + weatherId + ",city id:" + cityId;
    }
}
