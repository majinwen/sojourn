/**
 * @FileName: GroupCouponTypeEnum.java
 * @Package com.ziroom.minsu.valenum.cms
 * 
 * @author yd
 * @created 2017年5月27日 上午10:20:46
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.valenum.cms;

/**
 * <p>活动组 领取优惠券 类型 ： uid领取   mobile领取</p>
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
public enum GroupCouponTypeEnum {

	UID(1," uid领取"),
	MOBILE(2," mobile领取");
	
    GroupCouponTypeEnum(int typeCode,String typeName){
		
		this.typeCode = typeCode;
		this.typeName = typeName;
	}
	
	/**
	 * 领取类型 
	 */
	private int typeCode;
	
	/**
	 * 领取类型 名称
	 */
	private String typeName;

	/**
	 * @return the typeCode
	 */
	public int getTypeCode() {
		return typeCode;
	}

	/**
	 * @param typeCode the typeCode to set
	 */
	public void setTypeCode(int typeCode) {
		this.typeCode = typeCode;
	}

	/**
	 * @return the typeName
	 */
	public String getTypeName() {
		return typeName;
	}

	/**
	 * @param typeName the typeName to set
	 */
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	
	/**
	 * 
	 * 根据 typeCode获取值
	 *
	 * @author yd
	 * @created 2017年5月27日 上午10:26:43
	 *
	 * @param typeCode
	 * @return
	 */
	public static GroupCouponTypeEnum getGroupCouponTypeEnumByCode(int typeCode){
		
		for (GroupCouponTypeEnum groupCouponTypeEnum : GroupCouponTypeEnum.values()) {
			if(typeCode == groupCouponTypeEnum.getTypeCode()){
				return groupCouponTypeEnum;
			}
		}
		return null;
	}
	
	
}
