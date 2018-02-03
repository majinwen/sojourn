/**
 * @FileName: HouseDescVo.java
 * @Package com.ziroom.minsu.services.house.issue.vo
 * 
 * @author bushujie
 * @created 2017年6月14日 下午9:00:49
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.issue.vo;

import com.ziroom.minsu.services.common.entity.FieldTextValueVo;

/**
 * <p>房源描述信息</p>
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
public class HouseDescVo {
	
	/**
	 * 房源名称
	 */
	private FieldTextValueVo<String> houseName;
	/**
	 * 房源描述
	 */
	private FieldTextValueVo<String> houseDesc;
	/**
	 * 房源周边情况
	 */
	private FieldTextValueVo<String> houseAroundDesc;
	/**
	 * @return the houseName
	 */
	public FieldTextValueVo<String> getHouseName() {
		return houseName;
	}
	/**
	 * @param houseName the houseName to set
	 */
	public void setHouseName(FieldTextValueVo<String> houseName) {
		this.houseName = houseName;
	}
	/**
	 * @return the houseDesc
	 */
	public FieldTextValueVo<String> getHouseDesc() {
		return houseDesc;
	}
	/**
	 * @param houseDesc the houseDesc to set
	 */
	public void setHouseDesc(FieldTextValueVo<String> houseDesc) {
		this.houseDesc = houseDesc;
	}
	/**
	 * @return the houseAroundDesc
	 */
	public FieldTextValueVo<String> getHouseAroundDesc() {
		return houseAroundDesc;
	}
	/**
	 * @param houseAroundDesc the houseAroundDesc to set
	 */
	public void setHouseAroundDesc(FieldTextValueVo<String> houseAroundDesc) {
		this.houseAroundDesc = houseAroundDesc;
	}
}
