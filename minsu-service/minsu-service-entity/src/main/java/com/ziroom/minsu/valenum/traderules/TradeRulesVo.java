/**
 * @FileName: TradeRulesVo.java
 * @Package com.ziroom.minsu.valenum.traderules
 * 
 * @author yd
 * @created 2016年11月21日 上午11:19:00
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.valenum.traderules;

import com.asura.framework.base.entity.BaseEntity;

/**
 * <p>退订政策 vo实体</p>
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
public class TradeRulesVo  extends BaseEntity{

	
	/**
	 * 序列id
	 */
	private static final long serialVersionUID = 8271386592327866884L;

	/**
	 * 免费提前退订最小天数
	 */
	private Integer unregText1;
	
	/**
	 * 违约需扣房租描述
	 */
	private String unregText2;
	
	/**
	 * 入住后提前退房需扣房租描述
	 */
	private String unregText3;
	
	/**
	 * 无责任退房扣除全部房租百分比
	 */
	private String unregText4;
	
	/**
	 * 取消订单剩余房租扣除百分比
	 */
	private String unregText5;
	
	/**
	 * 退房剩余房租扣除百分比
	 */
	private String unregText6;


	private String unregText7;
	
	/**
	 * M站展示： 入住前X天文字展示
	 */
	private String checkInPreNameM;
	
	/**
	 * 站展示： 入住前到前X天文字展示
	 */
	private String checkInOnNameM;
	
	/**
	 * 入住后 提前退房 退订政策文字展示 
	 */
	private String checkOutEarlyNameM;
	
	/**
	 * 退订政策 公用展示
	 */
	private String commonShowName;
	
	/**
	 * 退订政策说明
	 */
	private String explain;
	
	/**
	 * 满足长租最小入住天数
	 */
	private Integer longTermLimit;
	
	public Integer getLongTermLimit() {
		return longTermLimit;
	}

	public void setLongTermLimit(Integer longTermLimit) {
		this.longTermLimit = longTermLimit;
	}

	public String getExplain() {
		return explain;
	}

	public void setExplain(String explain) {
		this.explain = explain;
	}

	/**
	 * @return the commonShowName
	 */
	public String getCommonShowName() {
		return commonShowName;
	}

	/**
	 * @param commonShowName the commonShowName to set
	 */
	public void setCommonShowName(String commonShowName) {
		this.commonShowName = commonShowName;
	}

	/**
	 * @return the checkOutEarlyNameM
	 */
	public String getCheckOutEarlyNameM() {
		return checkOutEarlyNameM;
	}

	/**
	 * @param checkOutEarlyNameM the checkOutEarlyNameM to set
	 */
	public void setCheckOutEarlyNameM(String checkOutEarlyNameM) {
		this.checkOutEarlyNameM = checkOutEarlyNameM;
	}

	/**
	 * @return the checkInOnNameM
	 */
	public String getCheckInOnNameM() {
		return checkInOnNameM;
	}

	/**
	 * @param checkInOnNameM the checkInOnNameM to set
	 */
	public void setCheckInOnNameM(String checkInOnNameM) {
		this.checkInOnNameM = checkInOnNameM;
	}

	/**
	 * @return the checkInPreNameM
	 */
	public String getCheckInPreNameM() {
		return checkInPreNameM;
	}

	/**
	 * @param checkInPreNameM the checkInPreNameM to set
	 */
	public void setCheckInPreNameM(String checkInPreNameM) {
		this.checkInPreNameM = checkInPreNameM;
	}

	/**
	 * @return the unregText1
	 */
	public Integer getUnregText1() {
		return unregText1;
	}

	/**
	 * @param unregText1 the unregText1 to set
	 */
	public void setUnregText1(Integer unregText1) {
		this.unregText1 = unregText1;
	}

	/**
	 * @return the unregText2
	 */
	public String getUnregText2() {
		return unregText2;
	}

	/**
	 * @param unregText2 the unregText2 to set
	 */
	public void setUnregText2(String unregText2) {
		this.unregText2 = unregText2;
	}

	/**
	 * @return the unregText3
	 */
	public String getUnregText3() {
		return unregText3;
	}

	/**
	 * @param unregText3 the unregText3 to set
	 */
	public void setUnregText3(String unregText3) {
		this.unregText3 = unregText3;
	}

	/**
	 * @return the unregText4
	 */
	public String getUnregText4() {
		return unregText4;
	}

	/**
	 * @param unregText4 the unregText4 to set
	 */
	public void setUnregText4(String unregText4) {
		this.unregText4 = unregText4;
	}

	/**
	 * @return the unregText5
	 */
	public String getUnregText5() {
		return unregText5;
	}

	/**
	 * @param unregText5 the unregText5 to set
	 */
	public void setUnregText5(String unregText5) {
		this.unregText5 = unregText5;
	}

	/**
	 * @return the unregText6
	 */
	public String getUnregText6() {
		return unregText6;
	}

	/**
	 * @param unregText6 the unregText6 to set
	 */
	public void setUnregText6(String unregText6) {
		this.unregText6 = unregText6;
	}


	public String getUnregText7() {
		return unregText7;
	}

	public void setUnregText7(String unregText7) {
		this.unregText7 = unregText7;
	}
}
