package com.tang.study.simpleweather.util;

/**
 * Created by Tangshuiming99 on 2017/3/22.
 */
public class StringUtil {

    private StringUtil(){

    }

    public static boolean isEmpty(String str){
        boolean isEmpty = false;
        if(str == null || "".equals(str)){
            isEmpty = true;
        }
        return isEmpty;
    }
}
