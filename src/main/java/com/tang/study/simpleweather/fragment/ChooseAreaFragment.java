package com.tang.study.simpleweather.fragment;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tangshuiming99 on 2017/3/22.
 */
public class ChooseAreaFragment extends Fragment {

    public static final int PROVINCE_LEVEL = 0;
    public static final int CITY_LEVEL = 1;
    public static final int COUNTY_LEVEL = 2;

    private TextView titleText;
    private Button btnBack;
    private ArrayAdapter<String> adapter;
    private ListView listView;
    private List<String> dataList = new ArrayList<>();
    private ProgressDialog progressDialog;

    private List<Province> provinceList;
    private List<City> cityList;
    private List<County> countyList;

    private Province selectedProvince;
    private City selectedCity;

    private int currentLevel;

    private Handler mainHandler;
    private SimpleWeatherDB simpleWeatherDB;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.choose_area_fragment, container, false);
        titleText = (TextView)view.findViewById(R.id.title_text);
        btnBack = (Button)view.findViewById(R.id.back_button);
        listView = (ListView)view.findViewById(R.id.list_view);
        mainHandler = new Handler(Looper.getMainLooper());
        simpleWeatherDB = SimpleWeatherDB.getInstance(getActivity());
        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, dataList);
        listView.setAdapter(adapter);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentLevel == COUNTY_LEVEL){
                    queryCities();
                    currentLevel = CITY_LEVEL;
                }
                else if(currentLevel == CITY_LEVEL){
                    queryProvinces();
                }
                else {
                    getActivity().finish();
                }
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //加载省份数据
        queryProvinces();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LogUtil.e("TAG", "item click currentLevel:" + currentLevel);
                /*if(currentLevel == PROVINCE_LEVEL){
                    if(provinceList != null && !provinceList.isEmpty()){
                        selectedProvince = provinceList.get(position);
                    }
                    queryProvinces();
                }
                else */
                if(currentLevel == CITY_LEVEL){
                    if(provinceList != null && !provinceList.isEmpty()){
                        selectedProvince = provinceList.get(position);
                    }

                    queryCities();
                }
                else if(currentLevel == COUNTY_LEVEL){
                    if(cityList != null && !cityList.isEmpty()){
                        selectedCity = cityList.get(position);
                    }
                    queryCounties();
                }
            }
        });
    }

    public static final String BASE_URL = "http://guolin.tech/api/china";

    //查询省份数据，并显示（首先查看数据库中是否已经存在数据，存在，直接查询，不存在，发送网络请求获取）
    private void queryProvinces(){
        titleText.setText("中国");
        btnBack.setVisibility(View.GONE);
        provinceList = simpleWeatherDB.loadProvinces();
        if(provinceList != null && !provinceList.isEmpty()){
            //清空上次数据源数据
            dataList.clear();
            for(Province province : provinceList){
                dataList.add(province.getProvinceName());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            currentLevel = CITY_LEVEL;
        }
        else {
            queryFromServer(BASE_URL, "province");
        }
    }

    private void queryCities(){
        titleText.setText(selectedProvince.getProvinceName());
        btnBack.setVisibility(View.VISIBLE);
        cityList = simpleWeatherDB.loadCities(selectedProvince.getProvinceCode());
        if(cityList != null && !cityList.isEmpty()){
            dataList.clear();
            for(City city : cityList){
                dataList.add(city.getCityName());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            currentLevel = COUNTY_LEVEL;
        }
        else {
            String adress = BASE_URL + "/" + (selectedProvince.getProvinceCode());
            LogUtil.e("TAG", "city adress:" + adress);
            queryFromServer(adress, "city");
        }
    }

    private void queryCounties(){
        titleText.setText(selectedCity.getCityName());
        btnBack.setVisibility(View.VISIBLE);
        countyList = simpleWeatherDB.loadCounties(selectedCity.getCityCode());
        if(countyList != null && !countyList.isEmpty()){
            dataList.clear();
            for(County county : countyList){
                dataList.add(county.getCountyName());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            currentLevel = COUNTY_LEVEL;
        }
        else {
            int provinceCode = selectedProvince.getProvinceCode();
            int cityCode = selectedCity.getCityCode();
            String adress = BASE_URL + "/" + provinceCode + "/" + cityCode;
            LogUtil.e("TAG", "county adress:" + adress);
            queryFromServer(adress, "county");
        }
    }

    //发送网络请求，获取请求数据
    private void queryFromServer(String address, final String type){
        //弹出进度加载框
        showProgressDialog();
        HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                try{
                    //child thread
                    boolean result = false;
                    if("province".equals(type)){
                        result = ParserUtil.handleProvinceResponse(simpleWeatherDB, response);
                    }
                    else if("city".equals(type)){
                        result = ParserUtil.handleCitiesResponse(simpleWeatherDB, response, selectedProvince.getProvinceCode());
                    }
                    else if("county".equals(type)){
                        result = ParserUtil.handleCountiesResponse(simpleWeatherDB, response, selectedCity.getCityCode());
                    }
                    if(result){
                        mainHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                //取消进度框显示
                                closeProgressDialog();
                                if("province".equals(type)){
                                    queryProvinces();
                                }
                                else if("city".equals(type)){
                                    queryCities();
                                }
                                else if("county".equals(type)){
                                    queryCounties();
                                }
                            }
                        });
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), "加载失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private synchronized void showProgressDialog(){
        if(progressDialog == null){
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("正在加载...");
            progressDialog.setCanceledOnTouchOutside(false);
        }
        progressDialog.show();
    }

    private synchronized void closeProgressDialog(){
        if(progressDialog != null){
            progressDialog.dismiss();
        }
    }

}
