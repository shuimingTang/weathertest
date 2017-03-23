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
import com.tang.study.simpleweather.gson.Weather;
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
        setContentView(R.layout.test_activity);
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
                    /*String address = "http://guolin.tech/api/china";
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
                    });*/
                    String str = "{\"HeWeather\": [{\"aqi\":{\"city\":{\"aqi\":\"49\",\"co\":\"1\",\"no2\":\"42\",\"o3\":\"31\",\"pm10\":\"48\",\"pm25\":\"34\",\"qlty\":\"优\",\"so2\":\"11\"}},\"basic\":{\"city\":\"杭州\",\"cnty\":\"中国\",\"id\":\"CN101210101\",\"lat\":\"30.319000\",\"lon\":\"120.165000\",\"update\":{\"loc\":\"2017-03-23 09:58\",\"utc\":\"2017-03-23 01:58\"}},\"daily_forecast\":[{\"astro\":{\"mr\":\"02:30\",\"ms\":\"13:27\",\"sr\":\"05:59\",\"ss\":\"18:12\"},\"cond\":{\"code_d\":\"104\",\"code_n\":\"305\",\"txt_d\":\"阴\",\"txt_n\":\"小雨\"},\"date\":\"2017-03-23\",\"hum\":\"88\",\"pcpn\":\"0.4\",\"pop\":\"93\",\"pres\":\"1020\",\"tmp\":{\"max\":\"13\",\"min\":\"9\"},\"uv\":\"5\",\"vis\":\"17\",\"wind\":{\"deg\":\"146\",\"dir\":\"北风\",\"sc\":\"微风\",\"spd\":\"4\"}},{\"astro\":{\"mr\":\"03:15\",\"ms\":\"14:22\",\"sr\":\"05:58\",\"ss\":\"18:13\"},\"cond\":{\"code_d\":\"306\",\"code_n\":\"305\",\"txt_d\":\"中雨\",\"txt_n\":\"小雨\"},\"date\":\"2017-03-24\",\"hum\":\"88\",\"pcpn\":\"6.9\",\"pop\":\"100\",\"pres\":\"1021\",\"tmp\":{\"max\":\"12\",\"min\":\"8\"},\"uv\":\"4\",\"vis\":\"14\",\"wind\":{\"deg\":\"73\",\"dir\":\"北风\",\"sc\":\"微风\",\"spd\":\"9\"}},{\"astro\":{\"mr\":\"03:58\",\"ms\":\"15:21\",\"sr\":\"05:57\",\"ss\":\"18:13\"},\"cond\":{\"code_d\":\"104\",\"code_n\":\"101\",\"txt_d\":\"阴\",\"txt_n\":\"多云\"},\"date\":\"2017-03-25\",\"hum\":\"73\",\"pcpn\":\"1.3\",\"pop\":\"100\",\"pres\":\"1021\",\"tmp\":{\"max\":\"15\",\"min\":\"8\"},\"uv\":\"6\",\"vis\":\"17\",\"wind\":{\"deg\":\"266\",\"dir\":\"北风\",\"sc\":\"微风\",\"spd\":\"5\"}}],\"hourly_forecast\":[{\"cond\":{\"code\":\"103\",\"txt\":\"晴间多云\"},\"date\":\"2017-03-23 10:00\",\"hum\":\"78\",\"pop\":\"4\",\"pres\":\"1020\",\"tmp\":\"13\",\"wind\":{\"deg\":\"120\",\"dir\":\"东南风\",\"sc\":\"微风\",\"spd\":\"13\"}},{\"cond\":{\"code\":\"104\",\"txt\":\"阴\"},\"date\":\"2017-03-23 13:00\",\"hum\":\"76\",\"pop\":\"23\",\"pres\":\"1020\",\"tmp\":\"14\",\"wind\":{\"deg\":\"11\",\"dir\":\"东北风\",\"sc\":\"微风\",\"spd\":\"13\"}},{\"cond\":{\"code\":\"305\",\"txt\":\"小雨\"},\"date\":\"2017-03-23 16:00\",\"hum\":\"83\",\"pop\":\"73\",\"pres\":\"1020\",\"tmp\":\"13\",\"wind\":{\"deg\":\"40\",\"dir\":\"东北风\",\"sc\":\"微风\",\"spd\":\"13\"}},{\"cond\":{\"code\":\"300\",\"txt\":\"阵雨\"},\"date\":\"2017-03-23 19:00\",\"hum\":\"91\",\"pop\":\"50\",\"pres\":\"1021\",\"tmp\":\"11\",\"wind\":{\"deg\":\"71\",\"dir\":\"东北风\",\"sc\":\"微风\",\"spd\":\"11\"}},{\"cond\":{\"code\":\"305\",\"txt\":\"小雨\"},\"date\":\"2017-03-23 22:00\",\"hum\":\"93\",\"pop\":\"46\",\"pres\":\"1022\",\"tmp\":\"10\",\"wind\":{\"deg\":\"76\",\"dir\":\"东北风\",\"sc\":\"微风\",\"spd\":\"9\"}}],\"now\":{\"cond\":{\"code\":\"101\",\"txt\":\"多云\"},\"fl\":\"9\",\"hum\":\"90\",\"pcpn\":\"0\",\"pres\":\"1022\",\"tmp\":\"9\",\"vis\":\"10\",\"wind\":{\"deg\":\"350\",\"dir\":\"北风\",\"sc\":\"4-5\",\"spd\":\"24\"}},\"status\":\"ok\",\"suggestion\":{\"air\":{\"brf\":\"中\",\"txt\":\"气象条件对空气污染物稀释、扩散和清除无明显影响，易感人群应适当减少室外活动时间。\"},\"comf\":{\"brf\":\"较舒适\",\"txt\":\"白天天气阴沉，会感到有点儿凉，但大部分人完全可以接受。\"},\"cw\":{\"brf\":\"不宜\",\"txt\":\"不宜洗车，未来24小时内有雨，如果在此期间洗车，雨水和路上的泥水可能会再次弄脏您的爱车。\"},\"drsg\":{\"brf\":\"较冷\",\"txt\":\"建议着厚外套加毛衣等服装。年老体弱者宜着大衣、呢外套加羊毛衫。\"},\"flu\":{\"brf\":\"少发\",\"txt\":\"各项气象条件适宜，无明显降温过程，发生感冒机率较低。\"},\"sport\":{\"brf\":\"较适宜\",\"txt\":\"阴天，较适宜进行各种户内外运动。\"},\"trav\":{\"brf\":\"适宜\",\"txt\":\"天气较好，温度适宜，总体来说还是好天气哦，这样的天气适宜旅游，您可以尽情地享受大自然的风光。\"},\"uv\":{\"brf\":\"最弱\",\"txt\":\"属弱紫外线辐射天气，无需特别防护。若长期在户外，建议涂擦SPF在8-12之间的防晒护肤品。\"}}}]}";
                    Weather weather = ParserUtil.handleWeatherResponse(str);
                    LogUtil.e(TAG, "weather:" + weather);
                    LogUtil.e(TAG, "status:" + weather.status);
                    break;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
