package com.ziroom.minsu.report.house.vo;

import com.asura.framework.base.entity.BaseEntity;
import com.ziroom.minsu.report.common.annotation.FieldMeta;

/**
 * <p></p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author wangwentao 2017/5/5
 * @version 1.0
 * @since 1.0
 */
public class HouseGrapherVo extends BaseEntity {

    @FieldMeta(skip = true)
    private static final long serialVersionUID = -4788237814944186959L;

    @FieldMeta(name = "国家", order = 1)
    private String nationName;

    @FieldMeta(name = "大区", order = 2)
    private String regionName;

    @FieldMeta(name = "城市", order = 3)
    private String cityName;

    @FieldMeta(skip = true)
    private String cityCode;

    @FieldMeta(name = "房源编号", order = 4)
    private String houseSn;

    @FieldMeta(name = "房源/房间状态", order = 5)
    private String houseStatusName;

    @FieldMeta(name = "首次发布时间", order = 6)
    private String firstDeployDate;

    @FieldMeta(name = "首次上架时间", order = 7)
    private String firstUpDate;

    @FieldMeta(name = "预约摄影时间", order = 8)
    private String bookStartTime;

    @FieldMeta(name = "实际拍摄时间", order = 9)
    private String doorHomeTime;

    @FieldMeta(name = "收图时间", order = 10)
    private String receivePictureTime;

    @FieldMeta(name = "照片上传时间", order = 11)
    private String uploadPictureTime;

    @FieldMeta(name = "摄影师姓名", order = 12)
    private String photoGrapherName;

    @FieldMeta(name = "摄影师电话", order = 13)
    private String photoGrapherPhone;

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getNationName() {
        return nationName;
    }

    public void setNationName(String nationName) {
        this.nationName = nationName;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getHouseSn() {
        return houseSn;
    }

    public void setHouseSn(String houseSn) {
        this.houseSn = houseSn;
    }

    public String getHouseStatusName() {
        return houseStatusName;
    }

    public void setHouseStatusName(String houseStatusName) {
        this.houseStatusName = houseStatusName;
    }

    public String getFirstDeployDate() {
        return firstDeployDate;
    }

    public void setFirstDeployDate(String firstDeployDate) {
        this.firstDeployDate = firstDeployDate;
    }

    public String getFirstUpDate() {
        return firstUpDate;
    }

    public void setFirstUpDate(String firstUpDate) {
        this.firstUpDate = firstUpDate;
    }

    public String getBookStartTime() {
        return bookStartTime;
    }

    public void setBookStartTime(String bookStartTime) {
        this.bookStartTime = bookStartTime;
    }

    public String getDoorHomeTime() {
        return doorHomeTime;
    }

    public void setDoorHomeTime(String doorHomeTime) {
        this.doorHomeTime = doorHomeTime;
    }

    public String getReceivePictureTime() {
        return receivePictureTime;
    }

    public void setReceivePictureTime(String receivePictureTime) {
        this.receivePictureTime = receivePictureTime;
    }

    public String getUploadPictureTime() {
        return uploadPictureTime;
    }

    public void setUploadPictureTime(String uploadPictureTime) {
        this.uploadPictureTime = uploadPictureTime;
    }

    public String getPhotoGrapherName() {
        return photoGrapherName;
    }

    public void setPhotoGrapherName(String photoGrapherName) {
        this.photoGrapherName = photoGrapherName;
    }

    public String getPhotoGrapherPhone() {
        return photoGrapherPhone;
    }

    public void setPhotoGrapherPhone(String photoGrapherPhone) {
        this.photoGrapherPhone = photoGrapherPhone;
    }
}
