package com.ziroom.minsu.api.search.common.header;


import com.asura.framework.base.entity.BaseEntity;
import sun.misc.BASE64Decoder;

/**
 * <p>头信息</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/4/14.
 * @version 1.0
 * @since 1.0
 */
public class Header extends BaseEntity{


    /**
     * 客户端app名
     */
    private String appName;
    /**
     * 渠道名称
     */
    private String channelName;
    /**
     * 智能设备唯一编号
     */
    private String deviceId;
    /**
     * imei号
     */
    private String imei;
    /**
     * 国际移动用户识别码
     */
    private String imsi;
    /**
     * mac地址
     */
    private String macAddress;
    /**
     * 当前网络类型. (1. 2G,  2.3G,  3.4G  4.Wifi)
     */
    private String netWork;
    /**
     * 客户端类型 (1.安卓 2.iOS )
     */
    private String osType;
    /**
     * 手机系统版本号
     */
    private String osVersion;
    /**
     * 手机品牌
     */
    private String phoneBrand;
    /**
     * 手机型号
     */
    private String phoneModel;
    /**
     * 手机号
     */
    private String tel;
    /**
     * 客户端版本Code(这个判断版本)
     */
    private String versionCode;
    /**
     * 客户端版本名称
     */
    private String versionName;


    /**
     * 设备ip
     */
    private String deviceIP;

    /**
     * 经纬度
     */
    private String locationCoordinate;


    private String locationCityCode;


    private String locationCityName;


    // 解密
    public static String getFromBase64(String s) {
        byte[] b = null;
        String result = null;
        if (s != null) {
            BASE64Decoder decoder = new BASE64Decoder();
            try {
                b = decoder.decodeBuffer(s);
                result = new String(b, "utf-8");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println(getFromBase64("5YyX5Lqs"));
    }


    public String getLocationCityName() {

        return getFromBase64(locationCityName);

    }

    public void setLocationCityName(String locationCityName) {
        this.locationCityName = locationCityName;
    }

    public String getLocationCityCode() {
        return locationCityCode;
    }

    public void setLocationCityCode(String locationCityCode) {
        this.locationCityCode = locationCityCode;
    }

    public String getLocationCoordinate() {
        return locationCoordinate;
    }

    public void setLocationCoordinate(String locationCoordinate) {
        this.locationCoordinate = locationCoordinate;
    }

    /**
     * @return the appName
     */
    public String getAppName() {
        return appName;
    }

    /**
     * @param appName the appName to set
     */
    public void setAppName(String appName) {
        this.appName = appName;
    }

    /**
     * @return the channelName
     */
    public String getChannelName() {
        return channelName;
    }

    /**
     * @param channelName the channelName to set
     */
    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    /**
     * @return the deviceId
     */
    public String getDeviceId() {
        return deviceId;
    }

    /**
     * @param deviceId the deviceId to set
     */
    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    /**
     * @return the imei
     */
    public String getImei() {
        return imei;
    }

    /**
     * @param imei the imei to set
     */
    public void setImei(String imei) {
        this.imei = imei;
    }

    /**
     * @return the imsi
     */
    public String getImsi() {
        return imsi;
    }

    /**
     * @param imsi the imsi to set
     */
    public void setImsi(String imsi) {
        this.imsi = imsi;
    }

    /**
     * @return the macAddress
     */
    public String getMacAddress() {
        return macAddress;
    }

    /**
     * @param macAddress the macAddress to set
     */
    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    /**
     * @return the netWork
     */
    public String getNetWork() {
        return netWork;
    }

    /**
     * @param netWork the netWork to set
     */
    public void setNetWork(String netWork) {
        this.netWork = netWork;
    }

    /**
     * @return the osType
     */
    public String getOsType() {
        return osType;
    }

    /**
     * @param osType the osType to set
     */
    public void setOsType(String osType) {
        this.osType = osType;
    }

    /**
     * @return the osVersion
     */
    public String getOsVersion() {
        return osVersion;
    }

    /**
     * @param osVersion the osVersion to set
     */
    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    /**
     * @return the phoneBrand
     */
    public String getPhoneBrand() {
        return phoneBrand;
    }

    /**
     * @param phoneBrand the phoneBrand to set
     */
    public void setPhoneBrand(String phoneBrand) {
        this.phoneBrand = phoneBrand;
    }

    /**
     * @return the phoneModel
     */
    public String getPhoneModel() {
        return phoneModel;
    }

    /**
     * @param phoneModel the phoneModel to set
     */
    public void setPhoneModel(String phoneModel) {
        this.phoneModel = phoneModel;
    }

    /**
     * @return the tel
     */
    public String getTel() {
        return tel;
    }

    /**
     * @param tel the tel to set
     */
    public void setTel(String tel) {
        this.tel = tel;
    }

    /**
     * @return the versionCode
     */
    public String getVersionCode() {
        return versionCode;
    }

    /**
     * @param versionCode the versionCode to set
     */
    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

    /**
     * @return the versionName
     */
    public String getVersionName() {
        return versionName;
    }

    /**
     * @param versionName the versionName to set
     */
    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }


    public String getDeviceIP() {
        return deviceIP;
    }

    public void setDeviceIP(String deviceIP) {
        this.deviceIP = deviceIP;
    }
}
