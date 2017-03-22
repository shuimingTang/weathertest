package com.tang.study.simpleweather.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 *
 */
public class HttpUtil{

    private HttpUtil(){

    }

    public static void sendHttpRequest(final String address, final HttpCallbackListener listener){
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection httpURLConnection = null;
                BufferedReader reader = null;
                try {
                    URL url = new URL(address);
                    httpURLConnection = (HttpURLConnection)url.openConnection();
                    httpURLConnection.setRequestMethod("GET");
                    httpURLConnection.setConnectTimeout(12*1000);
                    httpURLConnection.setReadTimeout(12*1000);
                    if(httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK){
                        StringBuilder response = new StringBuilder();
                        InputStream in = httpURLConnection.getInputStream();
                        reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
                        String line = null;
                        while((line = reader.readLine()) != null){
                            response.append(line);
                        }
                        if(listener != null){
                            listener.onFinish(response.toString());
                        }
                    }
                    else {
                        LogUtil.e(getClass().getSimpleName(), "连接网络失败");
                    }
                } catch (Exception e) {
                    if(listener != null){
                        listener.onError(e);
                    }
                } finally {
                    CloseUtil.close(reader);
                    if(httpURLConnection != null){
                        httpURLConnection.disconnect();
                    }
                }

            }
        }).start();
    }
}
