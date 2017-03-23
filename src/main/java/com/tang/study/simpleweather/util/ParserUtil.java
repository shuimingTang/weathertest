package com.tang.study.simpleweather.util;

import com.google.gson.Gson;
import com.tang.study.simpleweather.db.SimpleWeatherDB;
import com.tang.study.simpleweather.entry.City;
import com.tang.study.simpleweather.entry.County;
import com.tang.study.simpleweather.entry.Province;
import com.tang.study.simpleweather.gson.Weather;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 解析类
 * @author shuiming.tang
 * @date 2017-03-22
 */
public class ParserUtil {

    private ParserUtil(){

    }
    //解析省份数据
    public static boolean handleProvinceResponse(SimpleWeatherDB simpleWeatherDB, String response) throws Exception {
        JSONArray provinces = new JSONArray(response);
        if(provinces != null && provinces.length() > 0){
            Province province;
            JSONObject provinceObject;
            for(int i = 0; i < provinces.length(); i++){
                provinceObject = provinces.getJSONObject(i);
                province = new Province();
                LogUtil.e("TAG","province:" + province);
                province.setProvinceName(provinceObject.getString("name"));
                province.setProvinceCode(provinceObject.getInt("id"));
                simpleWeatherDB.saveProvince(province);
                LogUtil.e("TAG","province:" + province);
            }
            return true;
        }
        return false;
    }
    //解析城市数据
    public static boolean handleCitiesResponse(SimpleWeatherDB simpleWeatherDB, String response, int provinceId) throws Exception {
        JSONArray cities = new JSONArray(response);
        if(cities != null && cities.length() > 0){
            City city;
            JSONObject cityObject;
            for(int i = 0; i < cities.length(); i++){
                cityObject = cities.getJSONObject(i);
                city = new City();
                LogUtil.e("TAG","city:" + city);
                city.setCityName(cityObject.getString("name"));
                city.setCityCode(cityObject.getInt("id"));
                city.setProvinceId(provinceId);
                simpleWeatherDB.saveCity(city);
                LogUtil.e("TAG","city:" + city);
            }
            return true;
        }
        return false;
    }
    //解析县/区数据
    public static boolean handleCountiesResponse(SimpleWeatherDB simpleWeatherDB, String response, int cityId) throws Exception {
        JSONArray counties = new JSONArray(response);
        if(counties != null && counties.length() > 0){
            County county;
            JSONObject countyObject;
            for(int i = 0; i < counties.length(); i++){
                countyObject = counties.getJSONObject(i);
                county = new County();
                LogUtil.e("TAG","county:" + county);
                county.setCountyName(countyObject.getString("name"));
                county.setWeatherId(countyObject.getString("weather_id"));
                county.setCityId(cityId);
                simpleWeatherDB.saveCounty(county);
                LogUtil.e("TAG","county:" + county);
            }
            return true;
        }
        return false;
    }
    //解析天气数据（通过Gson）
    public static Weather handleWeatherResponse(String response) throws Exception {
        Weather weather = null;
        JSONObject jsonObject = new JSONObject(response);
        if(jsonObject != null){
            JSONArray weatherArray = jsonObject.getJSONArray("HeWeather");
            String weatherContent = weatherArray.getJSONObject(0).toString();
            weather = new Gson().fromJson(weatherContent, Weather.class);
        }
        return weather;
    }
}
