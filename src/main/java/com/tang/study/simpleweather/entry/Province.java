package com.tang.study.simpleweather.entry;

import java.io.Serializable;

/**
 * Created by Tangshuiming99 on 2017/3/21.
 */
public class Province implements Serializable{

    private int id;
    private String provinceName;
    private int provinceCode;
    public Province(){

    }

    public Province(int id, String provinceName, int provinceCode){
        this.id = id;
        this.provinceName = provinceName;
        this.provinceCode = provinceCode;
    }

    public int getId() {
        return id;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public int getProvinceCode() {
        return provinceCode;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public void setProvinceCode(int provinceCode) {
        this.provinceCode = provinceCode;
    }

    @Override
    public String toString() {
        return "province name:" + provinceName + ",province code:" + provinceCode;
    }
}
