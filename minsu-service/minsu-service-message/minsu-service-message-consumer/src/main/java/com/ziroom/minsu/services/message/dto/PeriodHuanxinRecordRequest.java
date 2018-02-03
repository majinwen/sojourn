/**
 * @FileName: PeriodHuanxinRecordRequest.java
 * @Package com.ziroom.minsu.services.message.dto
 * 
 * @author loushuai
 * @created 2017年9月5日 下午5:50:40
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.message.dto;

import java.util.Date;

import com.ziroom.minsu.services.common.dto.PageRequest;

/**
 * <p>TODO</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author loushuai
 * @since 1.0
 * @version 1.0
 */
public class PeriodHuanxinRecordRequest extends PageRequest{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4484807825328071562L;
	
	 /**
     * 登陆用户
     */
    private String uid;
    
    /**
     * 消息发送方
     */
    private String fromUid;
    
    /**
     * 消息接收uid
     */
    private String toUid;
    
    /**
     * 开始时间
     */
    private Date beginDate;
    
    /**
     * 截止时间
     */
    private Date tillDate;
    
    /**
     * 截止时间
     */
    private String tillDateStr;
    
    /**
     * 自如网标识 ZIROOM_MINSU_IM= 代表民宿 ZIROOM_ZRY_IM= 自如驿  ZIROOM_CHANGZU_IM= 自如长租
     */
    private String ziroomFlag;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getFromUid() {
		return fromUid;
	}

	public void setFromUid(String fromUid) {
		this.fromUid = fromUid;
	}

	public String getToUid() {
		return toUid;
	}

	public void setToUid(String toUid) {
		this.toUid = toUid;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getTillDate() {
		return tillDate;
	}

	public void setTillDate(Date tillDate) {
		this.tillDate = tillDate;
	}

	public String getZiroomFlag() {
		return ziroomFlag;
	}

	public void setZiroomFlag(String ziroomFlag) {
		this.ziroomFlag = ziroomFlag;
	}

	public String getTillDateStr() {
		return tillDateStr;
	}

	public void setTillDateStr(String tillDateStr) {
		this.tillDateStr = tillDateStr;
	}
	
    
}
