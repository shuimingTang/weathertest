package com.tang.study.simpleweather.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

/**
 * Created by Tangshuiming99 on 2017/3/22.
 */
public class BaseActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
    }
}
