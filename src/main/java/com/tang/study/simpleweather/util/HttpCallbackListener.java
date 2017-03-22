package com.tang.study.simpleweather.util;

/**
 * 网络回调接口
 */
public interface HttpCallbackListener {

    void onFinish(String response);
    void onError(Exception e);
}
