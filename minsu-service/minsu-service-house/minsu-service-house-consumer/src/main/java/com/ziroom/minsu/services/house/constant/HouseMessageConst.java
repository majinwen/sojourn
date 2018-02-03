/**
 * @FileName: HouseMessageConst.java
 * @Package com.ziroom.minsu.services.house.constant
 * 
 * @author bushujie
 * @created 2016年4月3日 上午11:53:36
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.constant;

import com.ziroom.minsu.services.common.constant.MessageConst;

/**
 * <p>房源相关静态配置常量</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author bushujie
 * @since 1.0
 * @version 1.0
 */
public class HouseMessageConst extends MessageConst{
	
	public static final String HOUSE_BASE_FID_NULL="house.base.fid.null";
	
	public static final String HOUSE_BASE_FID_MUST_NULL="house.base.fid.must.null";
	
	public static final String HOUSE_BASE_NULL="house.base.null";
	
	public static final String HOUSE_REPEAT_REFRESH="house.repeat.refresh";
	
	public static final String LANDLORD_UID_NULL="landlordUid.null";
	
	public static final String HOUSE_ILLEGAL_OPERATION ="house.illegal.operation";
	
	public static final String HOUSE_ROOM_FID_NULL="house.room.fid.null";
	
	public static final String HOUSE_ROOM_NULL="house.room.null";
	
	public static final String HOUSE_AND_ROOM_FID_NULL="house.and.room.fid.null";
	
	public static final String HOUSE_PRICE_SETTIME_NULL="house.price.settime.null";
	
	public static final String HOUSE_PRICE_VALUE_NULL="house.price.value.null";
	
	public static final String HOUSE_NON_LEASE_TIME_NULL="house.non.lease.time.null";
	
	public static final String HOUSE_OPERATER_FID_NULL="house.operater.fid.null";
	
	public static final String HOUSE_RENTWAY_NULL="house.rentway.null";

	public static final String HOUSE_OPERATE_TYPE_NULL="house.operate.type.null";
	
	public static final String HOUSE_PIC_TYPE_OVER_LIMIT="house.pic.type.over.limit";
	
	public static final String HOUSE_PIC_TYPE_MIN_LIMIT="house.pic.type.min.limit";
	
	public static final String HOUSE_OPERATE_SEQ_NULL="house.operate.seq.null";
	
	public static final String HOUSE_PHY_COMMUNITY_NAME_NULL="house.phy.community.name.null";
	
	public static final String HOUSE_ROOM_NUM_OVER_LIMIT="house.room.num.over.limit";
	
	public static final String HOUSE_ENUM_CODE_NULL="house.enum.code.null";
	
	public static final String HOUSE_PHY_BUILDINGNUM_NULL="house.phy.buildingNum.null";
	
	public static final String HOUSE_PHY_UNITNUM_NULL="house.phy.unitNum.null";
	
	public static final String HOUSE_PHY_FLOORNUM_NULL="house.phy.floorNum.null";
	
	public static final String HOUSE_PHY_HOUSENUM_NULL="house.phy.houseNum.null";
	
	public static final String HOUSE_PHY_HOUSESTREET_NULL="house.phy.houseStreet.null";
	
	public static final String HOUSE_PHY_NATION_NULL="house.phy.nation.null";
	
	public static final String HOUSE_PHY_NATION_CODE_NULL="house.phy.nation.code.null";
	
	public static final String HOUSE_PHY_PROVINCE_NULL="house.phy.province.null";
	
	public static final String HOUSE_PHY_PROVINCE_CODE_NULL="house.phy.province.code.null";
	
	public static final String HOUSE_PHY_CITY_NULL="house.phy.city.null";
	
	public static final String HOUSE_PHY_CITY_CODE_NULL="house.phy.city.code.null";
	
	public static final String HOUSE_PHY_AREA_NULL="house.phy.area.null";
	
	public static final String HOUSE_PHY_AREA_CODE_NULL="house.phy.area.code.null";
	
	public static final String HOUSE_TYPE_NULL="house.type.null";
	
	public static final String HOUSE_DESC_NULL="house.desc.null";
	
	public static final String HOUSE_DESC_FID_NULL="house.desc.fid.null";
	
	public static final String HOUSE_SOURCE_NULL="house.source.null";
	
	public static final String HOUSE_INTACTRATE_NULL="house.intactRate.null";
	
	/**
	 * 床位唯一标示不能为空
	 */
	public static final String HOUSE_BED_FID_NULL="house.bed.fid.null";
	
	/**
	 * 床位信息不存在
	 */
	public static final String HOUSE_BED_NULL="house.bed.null";
	
	/**
	 * 房源扩展信息不存在
	 */
	public static final String HOUSE_BASE_EXT_NULL="house.base.ext.null";
	
	/**
	 * 房源照片唯一标示不能为空
	 */
	public static final String HOUSE_PIC_FID_NULL="house.pic.fid.null";
	
	/**
	 * 房东uid不能为空
	 */
	public static final String HOUSE_LANDLORDUID_NULL="house.landlordUid.null";

    /**
     * 至少一个房间
     */
    public static final String HOUSE_ROOM_MUST="house.room.must";
    
    /**
     * 房源出租方式错误
     */
    public static final String HOUSE_RENTWAY_ERROR="house.rentway.error";
    
    /**
	 * 房源唯一标示和房间唯一标示集合不能同时为空
	 */
    public static final String HOUSEFID_AND_ROOMFID_LIST_NULL="houseFid.and.roomFid.list.null";
    
    /**
     * 房源管家关系唯一标示不能为空
     */
    public static final String HOUSE_GUARD_FID_NULL="house.guard.fid.null";
    
    /**
     * 请设置一张默认图片
     */
    public static final String HOUSE_DEFAULT_PICFID_NULL="house.default.picfid.null";

	/**
	 * 私有构造方法
	 */
	private HouseMessageConst(){
		
	}

}
