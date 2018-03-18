package com.zra.common.dto.appbase;

/**
 * Created by cuigh6 on 2016/4/27.
 */
public class AppBaseDto {
    private String imei;//设备唯一标识
    private Long timestamp;//时间戳
    private String cityCode;//城市编码
    private String phoneModel;//手机型号
    private String l;//语言版本
    private String appVersionStr;//应用版本号
    private String sysVersionStr;//系统版本号
    private String source;//来源 ios android

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getPhoneModel() {
        return phoneModel;
    }

    public void setPhoneModel(String phoneModel) {
        this.phoneModel = phoneModel;
    }

    public String getL() {
        return l;
    }

    public void setL(String l) {
        this.l = l;
    }

    public String getAppVersionStr() {
        return appVersionStr;
    }

    public void setAppVersionStr(String appVersionStr) {
        this.appVersionStr = appVersionStr;
    }

    public String getSysVersionStr() {
        return sysVersionStr;
    }

    public void setSysVersionStr(String sysVersionStr) {
        this.sysVersionStr = sysVersionStr;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

}
