package com.tang.study.simpleweather.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Toast;

import com.tang.study.simpleweather.R;
import com.tang.study.simpleweather.db.SimpleWeatherDB;
import com.tang.study.simpleweather.entry.City;
import com.tang.study.simpleweather.entry.County;
import com.tang.study.simpleweather.entry.Province;
import com.tang.study.simpleweather.util.HttpCallbackListener;
import com.tang.study.simpleweather.util.HttpUtil;
import com.tang.study.simpleweather.util.LogUtil;
import com.tang.study.simpleweather.util.ParserUtil;

import java.util.List;

/**
 * Created by Tangshuiming99 on 2017/3/22.
 */
public class TestActivity extends Activity implements View.OnClickListener{

    private SimpleWeatherDB simpleWeatherDB;
    private String TAG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.test_activity);
        setContentView(R.layout.choose_area_fragment);
        simpleWeatherDB = SimpleWeatherDB.getInstance(this);
        TAG = getClass().getSimpleName();
    }

    @Override
    public void onClick(View v) {
        try{
            switch (v.getId()){
                case R.id.province_test:
                    String provinceResponse = "[{\"id\":1,\"name\":\"北京\"},{\"id\":2,\"name\":\"上海\"},{\"id\":3,\"name\":\"天津\"},{\"id\":4,\"name\":\"重庆\"},{\"id\":5,\"name\":\"香港\"},{\"id\":6,\"name\":\"澳门\"},{\"id\":7,\"name\":\"台湾\"},{\"id\":8,\"name\":\"黑龙江\"},{\"id\":9,\"name\":\"吉林\"},{\"id\":10,\"name\":\"辽宁\"},{\"id\":11,\"name\":\"内蒙古\"},{\"id\":12,\"name\":\"河北\"},{\"id\":13,\"name\":\"河南\"},{\"id\":14,\"name\":\"山西\"},{\"id\":15,\"name\":\"山东\"},{\"id\":16,\"name\":\"江苏\"},{\"id\":17,\"name\":\"浙江\"},{\"id\":18,\"name\":\"福建\"},{\"id\":19,\"name\":\"江西\"},{\"id\":20,\"name\":\"安徽\"},{\"id\":21,\"name\":\"湖北\"},{\"id\":22,\"name\":\"湖南\"},{\"id\":23,\"name\":\"广东\"},{\"id\":24,\"name\":\"广西\"},{\"id\":25,\"name\":\"海南\"},{\"id\":26,\"name\":\"贵州\"},{\"id\":27,\"name\":\"云南\"},{\"id\":28,\"name\":\"四川\"},{\"id\":29,\"name\":\"西藏\"},{\"id\":30,\"name\":\"陕西\"},{\"id\":31,\"name\":\"宁夏\"},{\"id\":32,\"name\":\"甘肃\"},{\"id\":33,\"name\":\"青海\"},{\"id\":34,\"name\":\"新疆\"}]";
                    boolean provinceFlag = ParserUtil.handleProvinceResponse(simpleWeatherDB, provinceResponse);
                    LogUtil.e(TAG, "provinceFlag" + provinceFlag);
                    break;
                case R.id.load_province_test:
                    List<Province> provinceList = simpleWeatherDB.loadProvinces();
                    LogUtil.e(TAG, "provinceList:" + provinceList);
                    break;
                case R.id.city_test:
                    String cityResponse = "[{\"id\":126,\"name\":\"杭州\"},{\"id\":127,\"name\":\"湖州\"},{\"id\":128,\"name\":\"嘉兴\"},{\"id\":129,\"name\":\"宁波\"},{\"id\":130,\"name\":\"绍兴\"},{\"id\":131,\"name\":\"台州\"},{\"id\":132,\"name\":\"温州\"},{\"id\":133,\"name\":\"丽水\"},{\"id\":134,\"name\":\"金华\"},{\"id\":135,\"name\":\"衢州\"},{\"id\":136,\"name\":\"舟山\"}]";
                    boolean cityFlag = ParserUtil.handleCitiesResponse(simpleWeatherDB, cityResponse, 17);
                    LogUtil.e(TAG, "cityFlag" + cityFlag);
                    break;
                case R.id.load_city_test:
                    List<City> cityList = simpleWeatherDB.loadCities(17);
                    LogUtil.e(TAG, "cityList:" + cityList);
                    break;
                case R.id.county_test:
                    String countyResponse = "[{\"id\":999,\"name\":\"杭州\",\"weather_id\":\"CN101210101\"},{\"id\":1000,\"name\":\"萧山\",\"weather_id\":\"CN101210102\"},{\"id\":1001,\"name\":\"桐庐\",\"weather_id\":\"CN101210103\"},{\"id\":1002,\"name\":\"淳安\",\"weather_id\":\"CN101210104\"},{\"id\":1003,\"name\":\"建德\",\"weather_id\":\"CN101210105\"},{\"id\":1004,\"name\":\"余杭\",\"weather_id\":\"CN101210106\"},{\"id\":1005,\"name\":\"临安\",\"weather_id\":\"CN101210107\"},{\"id\":1006,\"name\":\"富阳\",\"weather_id\":\"CN101210108\"}]";
                    boolean countyFlag = ParserUtil.handleCountiesResponse(simpleWeatherDB, countyResponse, 126);
                    LogUtil.e(TAG, "countyFlag" + countyFlag);
                    break;
                case R.id.load_county_test:
                    List<County> countyList = simpleWeatherDB.loadCounties(126);
                    LogUtil.e(TAG, "countyList:" + countyList);
                    break;
                case R.id.http_test:
                    String address = "http://guolin.tech/api/china";
                    HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {
                        @Override
                        public void onFinish(final String response) {
                            //child thread
                            LogUtil.e(getClass().getSimpleName(), "发送网络请求成功，返回数据：" + response);
                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(TestActivity.this, "返回数据：" + response, Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                        @Override
                        public void onError(Exception e) {
                            LogUtil.e(getClass().getSimpleName(), "请求网络失败！e:" + e);
                            e.printStackTrace();
                        }
                    });

                    break;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
