/**
 * @FileName: HouseConfVo.java
 * @Package com.ziroom.minsu.services.house.entity
 * 
 * @author bushujie
 * @created 2016年5月5日 下午10:45:05
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.entity;

import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * <p>房源配置信息</p>
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
public class HouseConfVo {
	
	/**
	 * 配置code
	 */
	private String dicCode;
	
	/**
	 * 配置值
	 */
	private String dicValue;
	
	/**
	 * 配置名称
	 */
	private String dicName;
	
	/**
	 * fid
	 */
	private String fid;
	
	
	/**
	 * 折扣天数 (例：满7天9折  这里代表7)
	 */
	private String dicDayNum;
	
	
	/**
	 * @return the dicDayNum
	 */
	public String getDicDayNum() {
		return dicDayNum;
	}

	/**
	 * @param dicDayNum the dicDayNum to set
	 */
	public void setDicDayNum(String dicDayNum) {
		this.dicDayNum = dicDayNum;
	}

	/**
	 * @return the fid
	 */
	public String getFid() {
		return fid;
	}

	/**
	 * @param fid the fid to set
	 */
	public void setFid(String fid) {
		this.fid = fid;
	}

	/**
	 * @return the dicCode
	 */
	public String getDicCode() {
		return dicCode;
	}

	/**
	 * @param dicCode the dicCode to set
	 */
	public void setDicCode(String dicCode) {
		this.dicCode = dicCode;
	}

	/**
	 * @return the dicValue
	 */
	public String getDicValue() {
		return dicValue;
	}

	/**
	 * @param dicValue the dicValue to set
	 */
	public void setDicValue(String dicValue) {
		this.dicValue = dicValue;
	}

	/**
	 * @return the dicName
	 */
	public String getDicName() {
		return dicName;
	}

	/**
	 * @param dicName the dicName to set
	 */
	public void setDicName(String dicName) {
		this.dicName = dicName;
	}
	
	@JsonIgnore
	public String getDicPic() {
		return dicCode + dicValue;
	}
}
