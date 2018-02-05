package com.ziroom.minsu.api.order.entity;

import com.ziroom.minsu.api.order.entity.base.*;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 订单详情 api使用
 *
 * @author jixd
 * @created 2017年07月27日 18:28:26
 */
public class LandlordOrderDetailApiVo implements Serializable {

    private static final long serialVersionUID = -2158149594330639503L;
    /**
     * 订单编号
     */
    private String orderSn;
    /**
     * 订单标题
     */
    private String orderTitle;
    /**
     * 订单状态
     */
    private Integer orderStatus;
    /**
     * 支付状态
     */
    private Integer payStatus;
    /**
     * 订单状态名称
     */
    private String orderStatusName;
    /**
     * 房东Uid
     */
    private String landlordUid;
    /**
     * 预订人UId
     */
    private String userUid;
    /**
     * 预订人名称
     */
    private String userName;
    /**
     * 预订人电话
     */
    private String userTel;
    /**
     * 预订人头像
     */
    private String userHeadPic;
    /**
     * 预订人信息页 url
     */
    private String userInfoUrl;

    /**
     * 预订人收到评价数量
     */
    private Integer userEvaTotal;
    /**
     * 评价评价分值
     */
    private Float evaAva;

    /**
     * 入住人
     */
    private ItemSubListItem contacts;
    /**
     * 预定时间
     */
    private String createTime;
    /**
     * 房源
     */
    private String houseFid;
    /**
     * 房间fid
     */
    private String roomFid;
    /**
     * 出租方式
     */
    private Integer rentWay;
    /**
     * 房源 或房间名称
     */
    private String houseName;
    /**
     * 入住时间 name date week
     */
    private Map<String, String> startTime;
    /**
     * 退房时间 name date week
     */
    private Map<String, String> endTime;
    /**
     * 离开时间
     */
    private String realEndTime;

    /**
     * 本单收入 （预计和实际）
     */
    private MoneyDescTipsMsgItem orderIncome;
    /**
     * 本单罚款
     */
    private MoneyItem orderPenalty;
    /**
     * 金额项
     */
    private List<Item> moneyItems;
    /**
     * 退订政策
     */
    private DescItem checkOutRule;
    /**
     * 额外消费
     */
    private Map<String, Object> otherMoney;
    /**
     * 出行目地
     */
    private Item tripPurpose;

    /**
     * 备注列表 fid content
     */
    private Map<String, Object> remarks;


    /**
     * 按钮名称
     */
    private List<String> buttonNames;

    /**
     * 二次确认提示信息
     */
    private String acceptConfirmMsg;
    /**
     * 额外消费有金额提示信息
     */
    private String otherHasMoneyConfirmMsg;
    /**
     * 额外消费没有金额提示信息
     */
    private String otherNoMoneyConfirmMsg;
    
    /**
     * 押金
     */
    private MoneyItem depositMoney;

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public String getOrderTitle() {
        return orderTitle;
    }

    public void setOrderTitle(String orderTitle) {
        this.orderTitle = orderTitle;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Integer getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }

    public String getOrderStatusName() {
        return orderStatusName;
    }

    public void setOrderStatusName(String orderStatusName) {
        this.orderStatusName = orderStatusName;
    }

    public String getLandlordUid() {
        return landlordUid;
    }

    public void setLandlordUid(String landlordUid) {
        this.landlordUid = landlordUid;
    }

    public String getUserUid() {
        return userUid;
    }

    public void setUserUid(String userUid) {
        this.userUid = userUid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserTel() {
        return userTel;
    }

    public void setUserTel(String userTel) {
        this.userTel = userTel;
    }

    public String getUserHeadPic() {
        return userHeadPic;
    }

    public void setUserHeadPic(String userHeadPic) {
        this.userHeadPic = userHeadPic;
    }

    public Integer getUserEvaTotal() {
        return userEvaTotal;
    }

    public void setUserEvaTotal(Integer userEvaTotal) {
        this.userEvaTotal = userEvaTotal;
    }

    public Float getEvaAva() {
        return evaAva;
    }

    public void setEvaAva(Float evaAva) {
        this.evaAva = evaAva;
    }

    public ItemSubListItem getContacts() {
        return contacts;
    }

    public void setContacts(ItemSubListItem contacts) {
        this.contacts = contacts;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getHouseFid() {
        return houseFid;
    }

    public void setHouseFid(String houseFid) {
        this.houseFid = houseFid;
    }

    public String getRoomFid() {
        return roomFid;
    }

    public void setRoomFid(String roomFid) {
        this.roomFid = roomFid;
    }

    public Integer getRentWay() {
        return rentWay;
    }

    public void setRentWay(Integer rentWay) {
        this.rentWay = rentWay;
    }

    public String getHouseName() {
        return houseName;
    }

    public void setHouseName(String houseName) {
        this.houseName = houseName;
    }

    public Map<String, String> getStartTime() {
        return startTime;
    }

    public void setStartTime(Map<String, String> startTime) {
        this.startTime = startTime;
    }

    public Map<String, String> getEndTime() {
        return endTime;
    }

    public void setEndTime(Map<String, String> endTime) {
        this.endTime = endTime;
    }

    public MoneyDescTipsMsgItem getOrderIncome() {
        return orderIncome;
    }

    public void setOrderIncome(MoneyDescTipsMsgItem orderIncome) {
        this.orderIncome = orderIncome;
    }

    public MoneyItem getOrderPenalty() {
        return orderPenalty;
    }

    public void setOrderPenalty(MoneyItem orderPenalty) {
        this.orderPenalty = orderPenalty;
    }

    public List<Item> getMoneyItems() {
        return moneyItems;
    }

    public void setMoneyItems(List<Item> moneyItems) {
        this.moneyItems = moneyItems;
    }

    public DescItem getCheckOutRule() {
        return checkOutRule;
    }

    public void setCheckOutRule(DescItem checkOutRule) {
        this.checkOutRule = checkOutRule;
    }

    public Map<String, Object> getOtherMoney() {
        return otherMoney;
    }

    public void setOtherMoney(Map<String, Object> otherMoney) {
        this.otherMoney = otherMoney;
    }

    public Item getTripPurpose() {
        return tripPurpose;
    }

    public void setTripPurpose(Item tripPurpose) {
        this.tripPurpose = tripPurpose;
    }

    public Map<String, Object> getRemarks() {
        return remarks;
    }

    public void setRemarks(Map<String, Object> remarks) {
        this.remarks = remarks;
    }

    public List<String> getButtonNames() {
        return buttonNames;
    }

    public void setButtonNames(List<String> buttonNames) {
        this.buttonNames = buttonNames;
    }

    public String getUserInfoUrl() {
        return userInfoUrl;
    }

    public void setUserInfoUrl(String userInfoUrl) {
        this.userInfoUrl = userInfoUrl;
    }

    public String getRealEndTime() {
        return realEndTime;
    }

    public void setRealEndTime(String realEndTime) {
        this.realEndTime = realEndTime;
    }

    public String getAcceptConfirmMsg() {
        return acceptConfirmMsg;
    }

    public void setAcceptConfirmMsg(String acceptConfirmMsg) {
        this.acceptConfirmMsg = acceptConfirmMsg;
    }

    public String getOtherHasMoneyConfirmMsg() {
        return otherHasMoneyConfirmMsg;
    }

    public void setOtherHasMoneyConfirmMsg(String otherHasMoneyConfirmMsg) {
        this.otherHasMoneyConfirmMsg = otherHasMoneyConfirmMsg;
    }

    public String getOtherNoMoneyConfirmMsg() {
        return otherNoMoneyConfirmMsg;
    }

    public void setOtherNoMoneyConfirmMsg(String otherNoMoneyConfirmMsg) {
        this.otherNoMoneyConfirmMsg = otherNoMoneyConfirmMsg;
    }

	public MoneyItem getDepositMoney() {
		return depositMoney;
	}

	public void setDepositMoney(MoneyItem depositMoney) {
		this.depositMoney = depositMoney;
	}

}
