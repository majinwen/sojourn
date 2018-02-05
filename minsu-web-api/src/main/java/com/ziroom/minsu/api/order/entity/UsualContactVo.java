/**
 * @FileName: UsualContactVo.java
 * @Package com.ziroom.minsu.api.order.entity
 * 
 * @author yd
 * @created 2016年4月30日 下午5:24:45
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.api.order.entity;

import java.io.Serializable;

/**
 * <p>常用联系人实体</p>
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
public class UsualContactVo implements Serializable{

	
	/**
	 * 序列id
	 */
	private static final long serialVersionUID = 4118753738450970954L;

	/** 联系人名称 */
    private String conName;

    /** 证件类型 1：身份证 2：护照 这个需要产品提供  */
    private Integer cardType;

    /** 证件编号 */
    private String cardValue;
    
    /**
     * 用户uid
     */
    private String userUid;
    /**
     *联系人电话
     */
    private String conTel;

	public String getConName() {
		return conName;
	}

	public void setConName(String conName) {
		this.conName = conName;
	}

	public Integer getCardType() {
		return cardType;
	}

	public void setCardType(Integer cardType) {
		this.cardType = cardType;
	}

	public String getCardValue() {
		return cardValue;
	}

	public void setCardValue(String cardValue) {
		this.cardValue = cardValue;
	}

	public String getUserUid() {
		return userUid;
	}

	public void setUserUid(String userUid) {
		this.userUid = userUid;
	}

	public String getConTel() {
		return conTel;
	}

	public void setConTel(String conTel) {
		this.conTel = conTel;
	}
    
    
}
