package com.ziroom.minsu.services.order.entity;

import com.asura.framework.base.util.Check;
import com.ziroom.minsu.valenum.house.RentWayEnum;

import org.springframework.beans.BeanUtils;

import com.ziroom.minsu.entity.order.OrderEntity;
import com.ziroom.minsu.valenum.order.OrderStatusEnum;

public class OrderHouseVo extends OrderEntity{
	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = -4285986794226309673L;

	/** 房源编号 */
	private String houseSn;
    /** 房源 */
    private String houseFid;
    /**房间fid*/
    private String roomFid;
    /**床铺fid*/
    private String bedFid;
	/** 城市 名称 */
	private String cityName;
	/**房间名称*/
	private String roomName;
	/** 房间 名称 */
	private String houseName;
    /** 房间地址 */
    private String houseAddr;
    /**
     * 出租方式
     * @see RentWayEnum
     */
	private Integer rentWay;

	/** 图片地址 */
	private String picUrl;

    /** 价格 */
    private Integer price;
    /**
     * 订单类型
     * @see com.ziroom.minsu.valenum.order.OrderTypeEnum
     */
    private Integer orderType;


    /** 优惠规则 */
    private String discountRulesCode;

    /** 押金规则 */
    private String depositRulesCode;

    /** 退订政策 */
    private String checkOutRulesCode;

    /** 入住时间 值必须是标准的时间格式 12:00:00 */
    private String checkInTime;

    /** 退订时间 值必须是标准的时间格式 12:00:00 */
    private String checkOutTime;
	/** 起始时间字符串格式  yyyy-MM-dd*/
	private String startTimeStr;
	
	/**结束时间字符串格式  yyyy-MM-dd*/
	private String endTimeStr;
	/**入住人数量*/
	private Integer contactsNum;

    /**是否智能锁*/
    private Integer isLock;

    //一下的字段打算删掉，先暂时放一放

	/**订单状态的中文名称*/
	private String orderStatuChineseName;

	/**订单状态的枚举*/
	private OrderStatusEnum orderStatusEnum;

	/**住房实际时长（单位：晚）*/
	private Integer housingDay;
	
	/**
	 * 房客图片地址
	 */
	private String tenantPicUrl;	
	
	/**
	 * 房东图片地址
	 */
	private String landPicUrl;
	
	/**
	 * 房东 今夜特价标识 
	 */
	private boolean lanTonightDiscount = false;
	

	/**
	 * @return the lanTonightDiscount
	 */
	public boolean isLanTonightDiscount() {
		return lanTonightDiscount;
	}

	/**
	 * @param lanTonightDiscount the lanTonightDiscount to set
	 */
	public void setLanTonightDiscount(boolean lanTonightDiscount) {
		this.lanTonightDiscount = lanTonightDiscount;
	}

	public String getTenantPicUrl() {
		return tenantPicUrl;
	}

	public String getLandPicUrl() {
		return landPicUrl;
	}

	public void setTenantPicUrl(String tenantPicUrl) {
		this.tenantPicUrl = tenantPicUrl;
	}

	public void setLandPicUrl(String landPicUrl) {
		this.landPicUrl = landPicUrl;
	}

	public Integer getIsLock() {
        return isLock;
    }

    public void setIsLock(Integer isLock) {
        this.isLock = isLock;
    }

    /**
	 * 根据租住方式获取房子FID
	 * @author lishaochuan
	 * @create 2016年5月29日上午2:11:18
	 * @return
	 */
	public String returnFid() {
		if (rentWay == RentWayEnum.HOUSE.getCode()) {
			return houseFid;
		} else if (rentWay == RentWayEnum.ROOM.getCode()) {
			return roomFid;
		} else if (rentWay == RentWayEnum.BED.getCode()) {
			return bedFid;
		}
		return "";
	}

    public Integer getContactsNum() {
		return contactsNum;
	}

	public void setContactsNum(Integer contactsNum) {
		this.contactsNum = contactsNum;
	}

	public String getHouseFid() {
        return houseFid;
    }

    public void setHouseFid(String houseFid) {
        this.houseFid = houseFid;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public String getDiscountRulesCode() {
        return discountRulesCode;
    }

    public void setDiscountRulesCode(String discountRulesCode) {
        this.discountRulesCode = discountRulesCode;
    }

    public String getDepositRulesCode() {
        return depositRulesCode;
    }

    public void setDepositRulesCode(String depositRulesCode) {
        this.depositRulesCode = depositRulesCode;
    }

    public String getCheckOutRulesCode() {
        return checkOutRulesCode;
    }

    public void setCheckOutRulesCode(String checkOutRulesCode) {
        this.checkOutRulesCode = checkOutRulesCode;
    }

    public String getCheckInTime() {
        return checkInTime;
    }

    public void setCheckInTime(String checkInTime) {
        this.checkInTime = checkInTime;
    }

    public String getCheckOutTime() {
        return checkOutTime;
    }

    public void setCheckOutTime(String checkOutTime) {
        this.checkOutTime = checkOutTime;
    }

    public OrderHouseVo(){
	}
	
	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getHouseName() {
		return houseName;
	}


	/**
	 * 获取当前的房源或者房间名称
	 * @return
     */
	public String transName() {
		if (Check.NuNObj(this.getRentWay())){
			return "";
		}
		if (RentWayEnum.HOUSE.getCode() == this.getRentWay()){
			return getHouseName();
		}else {
			return getRoomName();
		}
	}

	public void setHouseName(String houseName) {
		this.houseName = houseName;
	}

    public Integer getRentWay() {
        return rentWay;
    }

    public void setRentWay(Integer rentWay) {
        this.rentWay = rentWay;
    }

    public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public String getOrderStatuChineseName() {
		return orderStatuChineseName;
	}
	public void setOrderStatuChineseName(String orderStatuChineseName) {
		this.orderStatuChineseName = orderStatuChineseName;
	}
	public OrderStatusEnum getOrderStatusEnum() {
		return orderStatusEnum;
	}

	public void setOrderStatusEnum(OrderStatusEnum orderStatusEnum) {
		this.orderStatusEnum = orderStatusEnum;
	}

	public Integer getHousingDay() {
		return housingDay;
	}

	public void setHousingDay(Integer housingDay) {
		this.housingDay = housingDay;
	}


    public String getHouseAddr() {
        return houseAddr;
    }

    public void setHouseAddr(String houseAddr) {
        this.houseAddr = houseAddr;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
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

	/**
     * 获取基础的order
     * @return
     */
    public OrderEntity transOrderEntity(){
        OrderEntity orderEntity = new OrderEntity();
        BeanUtils.copyProperties(this,orderEntity);
        return orderEntity;
    }

	public String getRoomFid() {
		return roomFid;
	}

	public void setRoomFid(String roomFid) {
		this.roomFid = roomFid;
	}

	public String getBedFid() {
		return bedFid;
	}

	public void setBedFid(String bedFid) {
		this.bedFid = bedFid;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public String getHouseSn() {
		return houseSn;
	}

	public void setHouseSn(String houseSn) {
		this.houseSn = houseSn;
	}
}
