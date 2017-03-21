package com.tang.study.simpleweather.util;

import android.util.Log;

/**
 * Created by Tangshuiming99 on 2017/3/21.
 */
public class LogUtil {

    private static final boolean isDev = true;

    private LogUtil(){

    }
    public static void v(String tag, String msg){
        if(isDev){
            Log.v(tag, msg);
        }
    }
    public static void i(String tag, String msg){
        if(isDev){
            Log.i(tag, msg);
        }
    }
    public static void w(String tag, String msg){
        if(isDev){
            Log.w(tag, msg);
        }
    }
    public static void e(String tag, String msg){
        if(isDev){
            Log.e(tag, msg);
        }
    }
    public static void d(String tag, String msg){
        if(isDev){
            Log.d(tag, msg);
        }
    }
}
