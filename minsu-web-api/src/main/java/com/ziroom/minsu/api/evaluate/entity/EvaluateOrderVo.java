/**
 * @FileName: EvaluateOrderVo.java
 * @Package com.ziroom.minsu.api.evaluate.entity
 * 
 * @author yd
 * @created 2017年1月20日 下午3:21:45
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.api.evaluate.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>评价订单实体关系</p>
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
public class EvaluateOrderVo implements Serializable{


    /**
	 * 序列id
	 */
	private static final long serialVersionUID = -5807053943691095734L;

	/**
     * 订单编号
     */
    private String orderSn;

    /**
     * 评价状态(1=待审核 2=已下线 3=已发布 4=已举报)
     */
    private Integer evaStatu;

    /**
     * 评价人uid(即创建人uid)
     */
    private String evaUserUid;

    /**
     * 被评人uid
     */
    private String ratedUserUid;

    /**
     * 评价人类型（房东=1 房客=2）
     */
    private Integer evaUserType;

    /**
     * 房源fid
     */
    private String houseFid;

    /**
     * 房间fid
     */
    private String roomFid;

	/**
	 * @return the orderSn
	 */
	public String getOrderSn() {
		return orderSn;
	}

	/**
	 * @param orderSn the orderSn to set
	 */
	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
	}

	/**
	 * @return the evaStatu
	 */
	public Integer getEvaStatu() {
		return evaStatu;
	}

	/**
	 * @param evaStatu the evaStatu to set
	 */
	public void setEvaStatu(Integer evaStatu) {
		this.evaStatu = evaStatu;
	}

	/**
	 * @return the evaUserUid
	 */
	public String getEvaUserUid() {
		return evaUserUid;
	}

	/**
	 * @param evaUserUid the evaUserUid to set
	 */
	public void setEvaUserUid(String evaUserUid) {
		this.evaUserUid = evaUserUid;
	}

	/**
	 * @return the ratedUserUid
	 */
	public String getRatedUserUid() {
		return ratedUserUid;
	}

	/**
	 * @param ratedUserUid the ratedUserUid to set
	 */
	public void setRatedUserUid(String ratedUserUid) {
		this.ratedUserUid = ratedUserUid;
	}

	/**
	 * @return the evaUserType
	 */
	public Integer getEvaUserType() {
		return evaUserType;
	}

	/**
	 * @param evaUserType the evaUserType to set
	 */
	public void setEvaUserType(Integer evaUserType) {
		this.evaUserType = evaUserType;
	}

	/**
	 * @return the houseFid
	 */
	public String getHouseFid() {
		return houseFid;
	}

	/**
	 * @param houseFid the houseFid to set
	 */
	public void setHouseFid(String houseFid) {
		this.houseFid = houseFid;
	}

	/**
	 * @return the roomFid
	 */
	public String getRoomFid() {
		return roomFid;
	}

	/**
	 * @param roomFid the roomFid to set
	 */
	public void setRoomFid(String roomFid) {
		this.roomFid = roomFid;
	}
    
    

    
}
