package com.ziroom.minsu.services.order.entity;

import java.util.*;

import com.asura.framework.base.entity.BaseEntity;
import com.ziroom.minsu.entity.cms.ActCouponUserEntity;
import com.ziroom.minsu.entity.order.HouseLockEntity;
import com.ziroom.minsu.entity.order.OrderActivityEntity;
import com.ziroom.minsu.entity.order.OrderConfigEntity;
import com.ziroom.minsu.entity.order.OrderContactEntity;
import com.ziroom.minsu.entity.order.OrderEntity;
import com.ziroom.minsu.entity.order.OrderHouseSnapshotEntity;
import com.ziroom.minsu.entity.order.OrderMoneyEntity;
import com.ziroom.minsu.entity.order.OrderSpecialPriceEntity;
import com.ziroom.minsu.valenum.common.YesOrNoEnum;

/**
 * <p>保存订单需要的信息</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/4/2.
 * @version 1.0
 * @since 1.0
 */
public class OrderSaveVo extends BaseEntity{

    /**
     * 序列化id
     */
    private static final long serialVersionUID = -8665446546509673L;

    /** 支付时限 */
    private String payTime;

    /** 支付时限 */
    private String payTimeStr;

    /** 审核时限 */
    private String checkTime;

    /** 是否是长租 */
    private Boolean changzuFlag = false;

    /** 退订政策 */
    private String checkOutRulesCode ;

    /**
     * 服务费信息
     */
    private String commissionInfo = "为了更好地服务用户,平台将收取房费的{1}作为信息服务费。若预订天数达{2}天，将享受服务费优惠费率，仅为{3}";
    
    /**
     * 房东 服务费信息
     */
    private String lanCommissionInfo = "为了更好地服务用户,平台将收取房费的{1}作为信息服务费。";



    /**  订单信息  */
    private OrderEntity order = new OrderEntity();


    /**  订单金额  */
    private OrderMoneyEntity orderMoney = new OrderMoneyEntity();

    /** 优惠券  */
    private ActCouponUserEntity couponEntity;

    /**  房源快照  */
    private OrderHouseSnapshotEntity orderHouse = new OrderHouseSnapshotEntity();

    /**  特殊价格  */
    private List<OrderSpecialPriceEntity> orderPrices= new ArrayList<>();

    /**  订单的配置信息 */
    private List<OrderConfigEntity> orderConfigs= new ArrayList<>();

    /**  订单的活动信息 */
    private List<OrderActivityEntity> orderActivitys= new ArrayList<>();

    /**  订单的锁天数 */
    private List<HouseLockEntity> houseLocks = new ArrayList<>();

    /**  订单的入住人信息 */
    private List<OrderContactEntity> orderContacts= new ArrayList<>();

    /**  订单的特殊价格表 */
    private Map<String,Integer> priceMap = new HashMap<>();

    /**  订单的每天的价格表 */
    Map<String,Integer> allPriceMap = new HashMap<>();

    /**  优惠券信息 */
    OrderActivityEntity couponAc;

    /**  实际房租(优惠前) */
    private Integer cost = 0;

    /** 居住天数 */
    private Integer dayCount;

    /** 房租折扣 */
    private Double rentCut = 1.0;

    /** 长租天数 */
    private Integer changzuDay = 0;

    /** 长租节省佣金金额 */
    private Integer saveUserComm = 0;


    /** 长租节省佣金金额 */
    private Integer saveLanComm = 0;

    /** 创建时间 */
    private Date createTime = new Date();

    /** 当前的房东已经免佣金 */
    private Integer landHasFree = YesOrNoEnum.NO.getCode();
    /**
     *  特殊折扣枚举字典
     */
    private Map<String,Object> priceEnumDicMap = new HashMap<>();
  
	/**
	 * @return the saveLanComm
	 */
	public Integer getSaveLanComm() {
		return saveLanComm;
	}

	/**
	 * @param saveLanComm the saveLanComm to set
	 */
	public void setSaveLanComm(Integer saveLanComm) {
		this.saveLanComm = saveLanComm;
	}

	/**
	 * @return the lanCommissionInfo
	 */
	public String getLanCommissionInfo() {
		return lanCommissionInfo;
	}

	/**
	 * @param lanCommissionInfo the lanCommissionInfo to set
	 */
	public void setLanCommissionInfo(String lanCommissionInfo) {
		this.lanCommissionInfo = lanCommissionInfo;
	}

	/**  只包含基础价格，特殊价格及周末价格等优惠之前的总价格   */
    private Integer originalPrice = 0;
    
    
    public Integer getOriginalPrice() {
		return originalPrice;
	}

	public void setOriginalPrice(Integer originalPrice) {
		this.originalPrice = originalPrice;
	}

	public Map<String, Object> getPriceEnumDicMap() {
		return priceEnumDicMap;
	}

	public void setPriceEnumDicMap(Map<String, Object> priceEnumDicMap) {
		this.priceEnumDicMap = priceEnumDicMap;
	}

	public Integer getChangzuDay() {
        return changzuDay;
    }

    public void setChangzuDay(Integer changzuDay) {
        this.changzuDay = changzuDay;
    }

    public Integer getSaveUserComm() {
        return saveUserComm;
    }

    public void setSaveUserComm(Integer saveUserComm) {
        this.saveUserComm = saveUserComm;
    }

    public Double getRentCut() {
        return rentCut;
    }

    public void setRentCut(Double rentCut) {
        this.rentCut = rentCut;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getLandHasFree() {
        return landHasFree;
    }

    public void setLandHasFree(Integer landHasFree) {
        this.landHasFree = landHasFree;
    }

    public Integer getDayCount() {
        return dayCount;
    }

    public void setDayCount(Integer dayCount) {
        this.dayCount = dayCount;
    }

    public Map<String, Integer> getAllPriceMap() {
        return allPriceMap;
    }

    public void setAllPriceMap(Map<String, Integer> allPriceMap) {
        this.allPriceMap = allPriceMap;
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    public OrderActivityEntity getCouponAc() {
        return couponAc;
    }

    public void setCouponAc(OrderActivityEntity couponAc) {
        this.couponAc = couponAc;
    }

    public OrderMoneyEntity getOrderMoney() {
        return orderMoney;
    }

    public void setOrderMoney(OrderMoneyEntity orderMoney) {
        this.orderMoney = orderMoney;
    }

    public Map<String, Integer> getPriceMap() {
        return priceMap;
    }

    public void setPriceMap(Map<String, Integer> priceMap) {
        this.priceMap = priceMap;
    }

    public OrderEntity getOrder() {
        return order;
    }

    public void setOrder(OrderEntity order) {
        this.order = order;
    }

    public OrderHouseSnapshotEntity getOrderHouse() {
        return orderHouse;
    }

    public void setOrderHouse(OrderHouseSnapshotEntity orderHouse) {
        this.orderHouse = orderHouse;
    }

    public List<OrderSpecialPriceEntity> getOrderPrices() {
        return orderPrices;
    }

    public void setOrderPrices(List<OrderSpecialPriceEntity> orderPrices) {
        this.orderPrices = orderPrices;
    }

    public List<OrderConfigEntity> getOrderConfigs() {
        return orderConfigs;
    }

    public void setOrderConfigs(List<OrderConfigEntity> orderConfigs) {
        this.orderConfigs = orderConfigs;
    }

    public List<OrderActivityEntity> getOrderActivitys() {
        return orderActivitys;
    }

    public void setOrderActivitys(List<OrderActivityEntity> orderActivitys) {
        this.orderActivitys = orderActivitys;
    }

    public List<HouseLockEntity> getHouseLocks() {
        return houseLocks;
    }

    public void setHouseLocks(List<HouseLockEntity> houseLocks) {
        this.houseLocks = houseLocks;
    }

    public List<OrderContactEntity> getOrderContacts() {
        return orderContacts;
    }

    public void setOrderContacts(List<OrderContactEntity> orderContacts) {
        this.orderContacts = orderContacts;
    }

    public ActCouponUserEntity getCouponEntity() {
        return couponEntity;
    }

    public void setCouponEntity(ActCouponUserEntity couponEntity) {
        this.couponEntity = couponEntity;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public String getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(String checkTime) {
        this.checkTime = checkTime;
    }

    public String getPayTimeStr() {
        return payTimeStr;
    }

    public void setPayTimeStr(String payTimeStr) {
        this.payTimeStr = payTimeStr;
    }

    public Boolean getChangzuFlag() {
        return changzuFlag;
    }

    public void setChangzuFlag(Boolean changzuFlag) {
        this.changzuFlag = changzuFlag;
    }

    public String getCheckOutRulesCode() {
        return checkOutRulesCode;
    }

    public void setCheckOutRulesCode(String checkOutRulesCode) {
        this.checkOutRulesCode = checkOutRulesCode;
    }

    public String getCommissionInfo() {
        return commissionInfo;
    }

    public void setCommissionInfo(String commissionInfo) {
        this.commissionInfo = commissionInfo;
    }
}
