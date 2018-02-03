/**
 * @FileName: ImMsgListVo.java
 * @Package com.ziroom.minsu.services.message.entity
 * 
 * @author yd
 * @created 2017年4月1日 下午3:48:18
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.message.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

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
 * @author yd
 * @since 1.0
 * @version 1.0
 */
public class ImMsgListVo extends BaseEntity{

	/**
	 * 序列id
	 */
	private static final long serialVersionUID = -4522179372999101179L;

	/**
	 * 消息体（即：消息内容）
	 *//*
	private String msgContent;

	*//**
	 * 消息发送人类型（1=房东 2=房客）
	 *//*
	private Integer msgSenderType;*/
	/**
	 * 创建时间 字符串形式
	 */
	private Date imLastmodifyTime;
	
	/**
	 * 发送人uid
	 */
	private String tenantUid;
	
	/**
	 * 接收人uid
	 */
	private String landlordUid;
	
	/**
	 * 当前会话 最新10条记录
	 */
	private List<ImMsgBaseVo> listImMsgBaseVo = new LinkedList<ImMsgBaseVo>();

	/**
	 * 整条记录是否删除 默认0 (0：不删除 1：房东删除 2：房客删除 3：全部删除)
	 */
	private Integer isDel;
	
	/**
	 * @return the isDel
	 */
	public Integer getIsDel() {
		return isDel;
	}

	/**
	 * @param isDel the isDel to set
	 */
	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}

	/**
	 * @return the imLastmodifyTime
	 */
	public Date getImLastmodifyTime() {
		return imLastmodifyTime;
	}

	/**
	 * @param imLastmodifyTime the imLastmodifyTime to set
	 */
	public void setImLastmodifyTime(Date imLastmodifyTime) {
		this.imLastmodifyTime = imLastmodifyTime;
	}

	/**
	 * @return the tenantUid
	 */
	public String getTenantUid() {
		return tenantUid;
	}

	/**
	 * @param tenantUid the tenantUid to set
	 */
	public void setTenantUid(String tenantUid) {
		this.tenantUid = tenantUid;
	}

	/**
	 * @return the landlordUid
	 */
	public String getLandlordUid() {
		return landlordUid;
	}

	/**
	 * @param landlordUid the landlordUid to set
	 */
	public void setLandlordUid(String landlordUid) {
		this.landlordUid = landlordUid;
	}

	/**
	 * @return the listImMsgBaseVo
	 */
	public List<ImMsgBaseVo> getListImMsgBaseVo() {
		return listImMsgBaseVo;
	}

	/**
	 * @param listImMsgBaseVo the listImMsgBaseVo to set
	 */
	public void setListImMsgBaseVo(List<ImMsgBaseVo> listImMsgBaseVo) {
		this.listImMsgBaseVo = listImMsgBaseVo;
	}
	
	
}
