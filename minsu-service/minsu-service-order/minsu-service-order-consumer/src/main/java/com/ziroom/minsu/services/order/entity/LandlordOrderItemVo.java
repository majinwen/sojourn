package com.ziroom.minsu.services.order.entity;

import java.io.Serializable;

import com.ziroom.minsu.valenum.evaluate.EvaluateClientBtnStatuEnum;
/**
 * |
 * <p>房东订单列表</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author jixd
 * @since 1.0
 * @version 1.0
 */
public class LandlordOrderItemVo implements Serializable{

	/**
	 * 序列id
	 */
	private static final long serialVersionUID = -1230730542171102739L;
	/**
	 * 订单编号
	 */
	private String orderSn;
	/**
	 * 房客uid
	 */
	private String userUid;
	/**
	 * 房东uid
	 */
	private String landlordUid;
	/**
	 * 支付金额
	 */
	private Double needMoney;
	/**
	 * 房源名称
	 */
	private String houseName;
    /**
     * 房源地址
     */
    private String houseAddr;
    /**
     * 出租方式
     * 0=整租  1=合租
     */
	private Integer rentWay;
	/**
	 * 房客头像
	 */
	private String  headPicUrl;
    /** 
     * 起始时间字符串格式  yyyy-MM-dd
     */
	private String startTimeStr;
	/**
	 * 结束时间字符串格式  yyyy-MM-dd
	 */
	private String endTimeStr;
	/**
	 * 入住人数
	 */
	private Integer contactsNum;
	/**
	 * 订单状态
	 */
    private Integer orderStatus;
    /**
     * 订单状态名称
     */
    private String orderStatusShowName;
    /** 
     * 用户名称
     */
    private String userName;
    /**
     * 入住时长
     */
    private Integer housingDay;
    
    /**
	 * 评价状态200：不可评价 100 待评价 101 用户已评价 110 房东已评价 111 都已经评价
	 *  返回给客户端，只需要 是否显示<去评价> 0:未评价   1：已评价
	 */
	private Integer evaStatus;

	/**
	 * 房源图片
	 */
	private String housePicUrl;
	
	
    /**
     * 评价状态
     */
    private Integer pjStatus  = EvaluateClientBtnStatuEnum.N_EVAL.getEvaStatuCode();


    /**
     * 评价显示按钮
     */
    private String pjButton = "";
    
    /**
     * “给Ta发消息”按钮
     * 说明：11.27号房东首页改版，列表页增加“给Ta发消息功能” ,
     */
    private String imToTaButton = "给Ta发消息";
    
    /**
     * 待处理订单“”按钮
     * 说明：11.27号房东首页改版，列表页增加“给Ta发消息功能” ,
     */
    private String waitDealOrderButton = "";
    
    /**
     * 说明：11.27号房东首页改版，列表页增加“给Ta发消息功能”，需要房源或房间fid
     * 如果是rentWay=0(整租)houseFid=houseBaseFid， 如果是rentWay=1(分租)houseFid=houseRoomFid
     */
    private String houseFid;

	public Integer getPjStatus() {
		return pjStatus;
	}
	public String getPjButton() {
		return pjButton;
	}
	public void setPjStatus(Integer pjStatus) {
		this.pjStatus = pjStatus;
	}
	public void setPjButton(String pjButton) {
		this.pjButton = pjButton;
	}
	public String getHousePicUrl() {
		return housePicUrl;
	}
	public void setHousePicUrl(String housePicUrl) {
		this.housePicUrl = housePicUrl;
	}
	public String getOrderSn() {
		return orderSn;
	}
	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
	}
	public Double getNeedMoney() {
		return needMoney;
	}
	public void setNeedMoney(Double needMoney) {
		this.needMoney = needMoney;
	}
	public String getHouseName() {
		return houseName;
	}
	public void setHouseName(String houseName) {
		this.houseName = houseName;
	}
	public String getHouseAddr() {
		return houseAddr;
	}
	public void setHouseAddr(String houseAddr) {
		this.houseAddr = houseAddr;
	}
	public Integer getRentWay() {
		return rentWay;
	}
	public void setRentWay(Integer rentWay) {
		this.rentWay = rentWay;
	}
	public String getHeadPicUrl() {
		return headPicUrl;
	}
	public void setHeadPicUrl(String headPicUrl) {
		this.headPicUrl = headPicUrl;
	}
	public String getStartTimeStr() {
		return startTimeStr;
	}
	public void setStartTimeStr(String startTimeStr) {
		this.startTimeStr = startTimeStr;
	}
	public String getEndTimeStr() {
		return endTimeStr;
	}
	public void setEndTimeStr(String endTimeStr) {
		this.endTimeStr = endTimeStr;
	}
	public Integer getContactsNum() {
		return contactsNum;
	}
	public void setContactsNum(Integer contactsNum) {
		this.contactsNum = contactsNum;
	}
	public Integer getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getOrderStatusShowName() {
		return orderStatusShowName;
	}
	public void setOrderStatusShowName(String orderStatusShowName) {
		this.orderStatusShowName = orderStatusShowName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Integer getHousingDay() {
		return housingDay;
	}
	public void setHousingDay(Integer housingDay) {
		this.housingDay = housingDay;
	}
	public Integer getEvaStatus() {
		return evaStatus;
	}
	public void setEvaStatus(Integer evaStatus) {
		this.evaStatus = evaStatus;
	}
	public String getUserUid() {
		return userUid;
	}
	public void setUserUid(String userUid) {
		this.userUid = userUid;
	}
	public String getLandlordUid() {
		return landlordUid;
	}
	public void setLandlordUid(String landlordUid) {
		this.landlordUid = landlordUid;
	}
	public String getImToTaButton() {
		return imToTaButton;
	}
	public void setImToTaButton(String imToTaButton) {
		this.imToTaButton = imToTaButton;
	}
	public String getHouseFid() {
		return houseFid;
	}
	public void setHouseFid(String houseFid) {
		this.houseFid = houseFid;
	}
	public String getWaitDealOrderButton() {
		return waitDealOrderButton;
	}
	public void setWaitDealOrderButton(String waitDealOrderButton) {
		this.waitDealOrderButton = waitDealOrderButton;
	}
	
}
