/**
 * @FileName: AmenitiesEnum.java
 * @Package com.ziroom.minsu.spider.airbnb.entity.enums
 * 
 * @author zl
 * @created 2016年10月9日 下午9:36:43
 * 
 * Copyright 2016-2025 ziroom
 */
package com.ziroom.minsu.spider.airbnb.entity.enums;

/**
 * <p>TODO</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author zl
 * @since 1.0
 * @version 1.0
 */
public enum AmenitiesEnum {
	
	Television(1,"电视"),
	CableTtelevision(2,"有线电视"),
	Network(3,"网络"),
	WirelessNetwork(4,"无线网络"),
	AirCondition(5,"空调"),
	AccessibilityFacilities(6,"无障碍设施"),
	SwimmingPool(7,"游泳池"),
	Kitchen(8,"厨房"),
	IndoorFreeParking(9,"建筑物內有免费停车位"),
	AroundChargeParking(10,"住所范围外的收费停车点"),
	Allowsmoke(11,"允许吸烟"),
	AllowPet(12,"允许携带宠物"),
	EntranceGguard(14,"门卫"),
	Gym(15,"健身房"),
	Breakfast(16,"早餐"),
	Elevator(21,"位于建筑物内的电梯"),
	FrontageFreeParking(23,"临街免费停车"),
	Bath(25,"按摩浴缸"),
	Fireplace(27,"室内壁炉"),
	BuzzerOrInterphone(28,"蜂鸣器/无线对讲机"),
	CentralHeating(30,"暖气"),
	FamilyOrChildren(31,"欢迎家庭/儿童入住"),
	SuitEvent(32,"适合举办活动"),
	WashingMachine(33,"洗衣机"),
	Dryer(34,"烘干机"),
	SmokeAlarm(35,"烟雾报警器"),
	MonoxideAlarm(36,"一氧化碳报警器"),
	FirstAidPacket(37,"急救包"),
	SecurityCard(38,"安全卡"),
	FireExtinguisher(39,"灭火器"),
	Necessities(40,"生活必需品"),
	LiquidShampoo(41,"洗发水"),
	BedroomLock(42,"卧室门装锁"),
	CheckInAtAnytime(43,"24小时随时入住"),
	Hanger(44,"衣架"),
	Blower(45,"吹风机"),
	Iron(46,"熨斗"),
	DeskOrWorkingArea(47,"书桌／工作区域");
    
    
	
	AmenitiesEnum(Integer code,String name) {
		this.code = code;
		this.name = name; 
	}

	/** code */
	private Integer code;

	/** 名称 */
	private String name;

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	} 
	
    
}
