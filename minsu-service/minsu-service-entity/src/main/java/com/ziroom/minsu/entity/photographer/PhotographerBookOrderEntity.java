package com.ziroom.minsu.entity.photographer;

import java.util.Date;

import com.asura.framework.base.entity.BaseEntity;

/**
 * 
 * <p>摄影师预约订单实体</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author yd
 * @since 1.0
 * @version 1.0
 */
public class PhotographerBookOrderEntity extends BaseEntity{
    //序列id
	private static final long serialVersionUID = -1574213015537426816L;

	//编号
    private Integer id;

    //关联编号
    private String fid;

    //城市code
    private String cityCode;

    //预约摄影师订单编号
    private String bookOrderSn;

    //预约人uid
    private String bookerUid;

    //预约人姓名（取真实姓名）
    private String bookerName;

    //预约人手机号
    private String bookerMobile;

    //客户人uid
    private String customerUid;

    //客户人真实姓名
    private String customerName;

    //客户人手机号
    private String customerMobile;

    //房源编号
    private String houseSn;

    //摄影师uid(给出缺省值  是由于索引的问题)
    private String photographerUid;

    //摄影师手机号(当时预约快照值)
    private String photographerMobile;

    //摄影师真实姓名(当时预约快照值)
    private String photographerName;

    //拍摄地址
    private String shotAddr;

    //预约订单状态（10=预约中  11=预约成功   12.完成）
    private Integer bookOrderStatu;

    //预定开始时间
    private Date bookStartTime;

    //预定结束时间
    private Date bookEndTime;

    //预约备注
    private String bookOrderRemark;

    //预约订单来源(1=民宿后台)
    private Integer bookOrderSource;

    //业务类型(1=民宿业务)
    private Integer businessType;

    //创建时间
    private Date createDate;

    //最后修改时间
    private Date lastModifyDate;
    
    //房源名称
    private String houseName;
    
    //房源fid
    private String houseFid;
    
    //指定摄影师时间
    private Date appointPhotogDate;

    //实际上门时间
    private Date doorHomeTime;

    //收图时间
    private Date receivePictureTime;

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public Date getDoorHomeTime() {
        return doorHomeTime;
    }

    public void setDoorHomeTime(Date doorHomeTime) {
        this.doorHomeTime = doorHomeTime;
    }

    public Date getReceivePictureTime() {
        return receivePictureTime;
    }

    public void setReceivePictureTime(Date receivePictureTime) {
        this.receivePictureTime = receivePictureTime;
    }

    public Date getAppointPhotogDate() {
		return appointPhotogDate;
	}

	public void setAppointPhotogDate(Date appointPhotogDate) {
		this.appointPhotogDate = appointPhotogDate;
	}

	public String getHouseFid() {
		return houseFid;
	}

	public void setHouseFid(String houseFid) {
		this.houseFid = houseFid;
	}

	public String getHouseName() {
		return houseName;
	}

	public void setHouseName(String houseName) {
		this.houseName = houseName;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid == null ? null : fid.trim();
    }

    public String getBookOrderSn() {
        return bookOrderSn;
    }

    public void setBookOrderSn(String bookOrderSn) {
        this.bookOrderSn = bookOrderSn == null ? null : bookOrderSn.trim();
    }

    public String getBookerUid() {
        return bookerUid;
    }

    public void setBookerUid(String bookerUid) {
        this.bookerUid = bookerUid == null ? null : bookerUid.trim();
    }

    public String getBookerName() {
        return bookerName;
    }

    public void setBookerName(String bookerName) {
        this.bookerName = bookerName == null ? null : bookerName.trim();
    }

    public String getBookerMobile() {
        return bookerMobile;
    }

    public void setBookerMobile(String bookerMobile) {
        this.bookerMobile = bookerMobile == null ? null : bookerMobile.trim();
    }

    public String getCustomerUid() {
        return customerUid;
    }

    public void setCustomerUid(String customerUid) {
        this.customerUid = customerUid == null ? null : customerUid.trim();
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName == null ? null : customerName.trim();
    }

    public String getCustomerMobile() {
        return customerMobile;
    }

    public void setCustomerMobile(String customerMobile) {
        this.customerMobile = customerMobile == null ? null : customerMobile.trim();
    }

    public String getHouseSn() {
        return houseSn;
    }

    public void setHouseSn(String houseSn) {
        this.houseSn = houseSn == null ? null : houseSn.trim();
    }

    public String getPhotographerUid() {
        return photographerUid;
    }

    public void setPhotographerUid(String photographerUid) {
        this.photographerUid = photographerUid == null ? null : photographerUid.trim();
    }

    public String getPhotographerMobile() {
        return photographerMobile;
    }

    public void setPhotographerMobile(String photographerMobile) {
        this.photographerMobile = photographerMobile == null ? null : photographerMobile.trim();
    }

    public String getPhotographerName() {
        return photographerName;
    }

    public void setPhotographerName(String photographerName) {
        this.photographerName = photographerName == null ? null : photographerName.trim();
    }

    public String getShotAddr() {
        return shotAddr;
    }

    public void setShotAddr(String shotAddr) {
        this.shotAddr = shotAddr == null ? null : shotAddr.trim();
    }

    public Integer getBookOrderStatu() {
        return bookOrderStatu;
    }

    public void setBookOrderStatu(Integer bookOrderStatu) {
        this.bookOrderStatu = bookOrderStatu;
    }

    public Date getBookStartTime() {
        return bookStartTime;
    }

    public void setBookStartTime(Date bookStartTime) {
        this.bookStartTime = bookStartTime;
    }

    public Date getBookEndTime() {
        return bookEndTime;
    }

    public void setBookEndTime(Date bookEndTime) {
        this.bookEndTime = bookEndTime;
    }

    public String getBookOrderRemark() {
        return bookOrderRemark;
    }

    public void setBookOrderRemark(String bookOrderRemark) {
        this.bookOrderRemark = bookOrderRemark == null ? null : bookOrderRemark.trim();
    }

    public Integer getBookOrderSource() {
        return bookOrderSource;
    }

    public void setBookOrderSource(Integer bookOrderSource) {
        this.bookOrderSource = bookOrderSource;
    }

    public Integer getBusinessType() {
        return businessType;
    }

    public void setBusinessType(Integer businessType) {
        this.businessType = businessType;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getLastModifyDate() {
        return lastModifyDate;
    }

    public void setLastModifyDate(Date lastModifyDate) {
        this.lastModifyDate = lastModifyDate;
    }
}