package com.tang.study.simpleweather.util;

import java.io.Closeable;
import java.io.IOException;

/**
 * 流关闭类
 * @author shuiming.tang
 * @date 2017-03-22
 */
public class CloseUtil {

    private CloseUtil(){

    }

    public static void close(Closeable closeable){
        if(closeable != null){
            try {
                closeable.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
