package com.ziroom.minsu.troy.message.vo;

import com.asura.framework.base.entity.BaseEntity;

import java.util.Date;

/**
 * <p>展示的状态</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on on 2016/12/14.
 * @version 1.0
 * @since 1.0
 */
public class FirstAdvisoryFollowVO extends BaseEntity {

    private static final long serialVersionUID = 4544640717442925020L;
    private Date startDate;
    private Date endDate;
    private Integer personNum;
    private String houseName;

    private String cityName;
    private String cityCode;
    private Date createDate;

    private String tenantName;
    private String tenantMobile;

    private String landlordName;
    private String landlordMobile;

    private Integer price;

    private String msgAdvisoryFid;
    private String msgBaseFid;

    private Integer isFollowEnd;

    private Integer showButton;

    private String houseFid;

    private Integer rentWay;

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Integer getPersonNum() {
        return personNum;
    }

    public void setPersonNum(Integer personNum) {
        this.personNum = personNum;
    }

    public String getHouseName() {
        return houseName;
    }

    public void setHouseName(String houseName) {
        this.houseName = houseName;
    }

    public String getTenantName() {
        return tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    public String getTenantMobile() {
        return tenantMobile;
    }

    public void setTenantMobile(String tenantMobile) {
        this.tenantMobile = tenantMobile;
    }

    public String getLandlordName() {
        return landlordName;
    }

    public void setLandlordName(String landlordName) {
        this.landlordName = landlordName;
    }

    public String getLandlordMobile() {
        return landlordMobile;
    }

    public void setLandlordMobile(String landlordMobile) {
        this.landlordMobile = landlordMobile;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getMsgAdvisoryFid() {
        return msgAdvisoryFid;
    }

    public void setMsgAdvisoryFid(String msgAdvisoryFid) {
        this.msgAdvisoryFid = msgAdvisoryFid;
    }

    public String getMsgBaseFid() {
        return msgBaseFid;
    }

    public void setMsgBaseFid(String msgBaseFid) {
        this.msgBaseFid = msgBaseFid;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getIsFollowEnd() {
        return isFollowEnd;
    }

    public void setIsFollowEnd(Integer isFollowEnd) {
        this.isFollowEnd = isFollowEnd;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public Integer getShowButton() {
        return showButton;
    }

    public void setShowButton(Integer showButton) {
        this.showButton = showButton;
    }

    public String getHouseFid() {
        return houseFid;
    }

    public void setHouseFid(String houseFid) {
        this.houseFid = houseFid;
    }

    public Integer getRentWay() {
        return rentWay;
    }

    public void setRentWay(Integer rentWay) {
        this.rentWay = rentWay;
    }
}
