package com.tang.study.simpleweather.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.tang.study.simpleweather.entry.City;
import com.tang.study.simpleweather.entry.County;
import com.tang.study.simpleweather.entry.Province;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tangshuiming99 on 2017/3/21.
 */
public class SimpleWeatherDB {

    public static final String DB_NAME = "simple_weather";
    public static final int VERSION = 1;
    private static SimpleWeatherDB simpleWeatherDB;
    private static SQLiteDatabase db;
    private static final String PROVINCE_TABLE = "province";
    private static final String CITY_TABLE = "city";
    private static final String COUNTY_TABLE = "county";

    private static final String ID = "id";
    private static final String PROVINCE_NAME = "province_name";
    private static final String PROVINCE_CODE = "province_code";
    private static final String CITY_NAME = "city_name";
    private static final String CITY_CODE = "city_code";
    private static final String PROVINCE_ID = "province_id";
    private static final String COUNTY_NAME = "county_name";
    private static final String WEATHER_ID = "weather_id";
    private static final String CITY_ID = "city_id";

    private SimpleWeatherDB(Context context){
        SimpleWeatherOpenHelper simpleWeatherOpenHelper =
                new SimpleWeatherOpenHelper(context, DB_NAME, null, VERSION);
        db = simpleWeatherOpenHelper.getWritableDatabase();
    }

    public static SimpleWeatherDB getInstance(Context context){
        if(simpleWeatherDB == null){
            synchronized (SimpleWeatherDB.class){
                if(simpleWeatherDB == null){
                    simpleWeatherDB = new SimpleWeatherDB(context);
                }
            }
        }
        return simpleWeatherDB;
    }

    //save province data
    public static void saveProvince(Province province){
        ContentValues contentValues = new ContentValues();
        contentValues.put(PROVINCE_NAME, province.getProvinceName());
        contentValues.put(PROVINCE_CODE, province.getProvinceCode());
        db.insert(PROVINCE_TABLE, null, contentValues);
    }
    //get all provices data
    public static List<Province> loadProvinces(){
        List<Province> provinces = null;
        Cursor cursor = db.query(PROVINCE_TABLE, null, null, null, null, null, null);
        if(cursor != null){
            provinces = new ArrayList<>();
            if(cursor.moveToFirst()){
                Province province;
                do{
                    int id = cursor.getInt(cursor.getColumnIndex(ID));
                    String provinceName = cursor.getString(cursor.getColumnIndex(PROVINCE_NAME));
                    int provinceCode = cursor.getInt(cursor.getColumnIndex(PROVINCE_CODE));
                    province = new Province(id, provinceName, provinceCode);
                    provinces.add(province);
                }while (cursor.moveToNext());
            }
            cursor.close();
        }
        return provinces;
    }

    public static void saveCity(City city){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CITY_NAME, city.getCityName());
        contentValues.put(CITY_CODE, city.getCityCode());
        contentValues.put(PROVINCE_ID, city.getProvinceId());
        db.insert(CITY_TABLE, null, contentValues);
    }

    public static List<City> loadCities(int provinceId){
        List<City> cities = null;
        Cursor cursor = db.query(CITY_TABLE, null, PROVINCE_ID + " = ?",
                new String[]{String.valueOf(provinceId)}, null, null, null);
        if(cursor != null){
            cities = new ArrayList<>();
            if(cursor.moveToFirst()){
                City city;
                do{
                    int id = cursor.getInt(cursor.getColumnIndex(ID));
                    String cityName = cursor.getString(cursor.getColumnIndex(CITY_NAME));
                    int cityCode = cursor.getInt(cursor.getColumnIndex(CITY_CODE));
                    city = new City(id, cityName, cityCode, provinceId);
                    cities.add(city);
                }while (cursor.moveToNext());
            }
            cursor.close();
        }
        return cities;
    }

    public static void saveCounty(County county){
        ContentValues contentValues = new ContentValues();
        contentValues.put(COUNTY_NAME, county.getCountyName());
        contentValues.put(WEATHER_ID, county.getWeatherId());
        contentValues.put(CITY_ID, county.getCityId());
        db.insert(COUNTY_TABLE, null, contentValues);
    }

    public static List<County> loadCounties(int cityId){
        List<County> counties = null;
        Cursor cursor = db.query(COUNTY_TABLE, null, CITY_ID + " = ?",
                new String[]{String.valueOf(cityId)}, null, null, null);
        if(cursor != null){
            counties = new ArrayList<>();
            if(cursor.moveToFirst()){
                County county;
                do{
                    int id = cursor.getInt(cursor.getColumnIndex(ID));
                    String countyName = cursor.getString(cursor.getColumnIndex(COUNTY_NAME));
                    String weatherId = cursor.getString(cursor.getColumnIndex(WEATHER_ID));
                    county = new County(id, countyName, weatherId, cityId);
                    counties.add(county);
                }while (cursor.moveToNext());
            }
            cursor.close();
        }
        return counties;
    }
}
