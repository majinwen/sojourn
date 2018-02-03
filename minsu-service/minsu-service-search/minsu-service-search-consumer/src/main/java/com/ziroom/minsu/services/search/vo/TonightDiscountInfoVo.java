/**
 * @FileName: TonightDiscountInfoVo.java
 * @Package com.ziroom.minsu.services.search.vo
 * 
 * @author zl
 * @created 2017年5月11日 下午2:15:28
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.search.vo;

import com.asura.framework.base.entity.BaseEntity;

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
public class TonightDiscountInfoVo extends BaseEntity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 9207560799344545636L;
	/**
	 * 开抢时间
	 */
	private String openTime ="";
	
	/**
	 * 开抢时间提示文案
	 */
	private String openTimeTips =""; 
	
	/**
	 * 列表开抢时间提示文案
	 */
	private String openTimeListTips =""; 
	
	/**
	 * 开抢倒计时 :单位毫秒，当前时间超过开抢时间返回为0
	 */
	private Long remainTime=0L;
	
	
	/**
	 * 截止时间
	 */
	private String deadlineTime="";
	
	/**
	 * 截止时间提示文案
	 */
	private String deadlineTimeTips="";
	
	/**
	 * 列表截止时间提示文案
	 */
	private String deadlineTimeListTips="";
	
	/**
	 * 结束倒计时 :单位毫秒，当前时间超过截止时间返回为0
	 */
	private Long deadlineRemainTime=0L;
	
	/**
	 * 今夜特价价格
	 */
	private Integer tonightPrice;

	/**
	 * 今夜特价折扣值
	 */
	private Double tonightDiscount;
	
	/**
	 * 提示名称
	 */
	private String tipsNname="";
	
	
	public String getOpenTimeListTips() {
		return openTimeListTips;
	}

	public String getDeadlineTimeListTips() {
		return deadlineTimeListTips;
	}

	public void setOpenTimeListTips(String openTimeListTips) {
		this.openTimeListTips = openTimeListTips;
	}

	public void setDeadlineTimeListTips(String deadlineTimeListTips) {
		this.deadlineTimeListTips = deadlineTimeListTips;
	}

	public String getDeadlineTime() {
		return deadlineTime;
	}

	public String getDeadlineTimeTips() {
		return deadlineTimeTips;
	}

	public Long getDeadlineRemainTime() {
		return deadlineRemainTime;
	}

	public void setDeadlineTime(String deadlineTime) {
		this.deadlineTime = deadlineTime;
	}

	public void setDeadlineTimeTips(String deadlineTimeTips) {
		this.deadlineTimeTips = deadlineTimeTips;
	}

	public void setDeadlineRemainTime(Long deadlineRemainTime) {
		this.deadlineRemainTime = deadlineRemainTime;
	}

	public String getTipsNname() {
		return tipsNname;
	}

	public void setTipsNname(String tipsNname) {
		this.tipsNname = tipsNname;
	}

	public String getOpenTimeTips() {
		return openTimeTips;
	}

	public void setOpenTimeTips(String openTimeTips) {
		this.openTimeTips = openTimeTips;
	}

	public String getOpenTime() {
		return openTime;
	}

	public Long getRemainTime() {
		return remainTime;
	}

	public Integer getTonightPrice() {
		return tonightPrice;
	}

	public void setOpenTime(String openTime) {
		this.openTime = openTime;
	}

	public void setRemainTime(Long remainTime) {
		this.remainTime = remainTime;
	}

	public void setTonightPrice(Integer tonightPrice) {
		this.tonightPrice = tonightPrice;
	}

	public Double getTonightDiscount() {
		return tonightDiscount;
	}

	public void setTonightDiscount(Double tonightDiscount) {
		this.tonightDiscount = tonightDiscount;
	}
}
