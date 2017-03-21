package com.tang.study.simpleweather.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.tang.study.simpleweather.util.LogUtil;

/**
 * Created by Tangshuiming99 on 2017/3/21.
 */
public class SimpleWeatherOpenHelper extends SQLiteOpenHelper{

    private static final String CREATE_PROVINCE =
            "create table province(id integer primary key autoincrement, " +
                    "province_name text, province_code integer);";
    private static final String CREATE_CITY =
            "create table city(id integer primary key autoincrement," +
                    "city_name text,city_code integer,province_id integer);";
    private static final String CREATE_COUNTY =
            "create table county(id integer primary key autoincrement," +
                    "county_name text,weather_id text,city_id integer);";


    public SimpleWeatherOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String tag = getClass().getSimpleName();
        LogUtil.e(tag, "数据库Helper onCreate");
        db.execSQL(CREATE_PROVINCE);
        db.execSQL(CREATE_CITY);
        db.execSQL(CREATE_COUNTY);
        LogUtil.e(tag, "数据库Helper 表创建完成");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
