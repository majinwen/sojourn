/**
 * @FileName: HouseBedNumVo.java
 * @Package com.ziroom.minsu.services.house.entity
 * 
 * @author bushujie
 * @created 2016年5月5日 下午9:33:49
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.entity;

/**
 * <p>床类型数量</p>
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
public class HouseBedNumVo {
	
	/**
	 * 床类型
	 */
	private Integer bedType;
	/**
	 * 床规格
	 */
	private Integer bedSize;
	
	/**
	 * 床数量
	 */
	private Integer bedNum;
	
	/**
	 * 床类型名称
	 */
	private String bedTypeName;
	
	/**
	 * 床规格名称
	 */
	private String bedSizeName;
	/**
	 * @return the bedTypeName
	 */
	public String getBedTypeName() {
		return bedTypeName;
	}
	/**
	 * @param bedTypeName the bedTypeName to set
	 */
	public void setBedTypeName(String bedTypeName) {
		this.bedTypeName = bedTypeName;
	}
	/**
	 * @return the bedSizeName
	 */
	public String getBedSizeName() {
		return bedSizeName;
	}
	/**
	 * @param bedSizeName the bedSizeName to set
	 */
	public void setBedSizeName(String bedSizeName) {
		this.bedSizeName = bedSizeName;
	}
	/**
	 * @return the bedType
	 */
	public Integer getBedType() {
		return bedType;
	}
	/**
	 * @param bedType the bedType to set
	 */
	public void setBedType(Integer bedType) {
		this.bedType = bedType;
	}
	/**
	 * @return the bedSize
	 */
	public Integer getBedSize() {
		return bedSize;
	}
	/**
	 * @param bedSize the bedSize to set
	 */
	public void setBedSize(Integer bedSize) {
		this.bedSize = bedSize;
	}
	/**
	 * @return the bedNum
	 */
	public Integer getBedNum() {
		return bedNum;
	}
	/**
	 * @param bedNum the bedNum to set
	 */
	public void setBedNum(Integer bedNum) {
		this.bedNum = bedNum;
	}
}
