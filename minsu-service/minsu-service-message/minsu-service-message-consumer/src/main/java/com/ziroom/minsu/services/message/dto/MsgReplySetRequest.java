/**
 * @FileName: MsgReplySetRequest.java
 * @Package com.ziroom.minsu.services.message.dto
 * 
 * @author yd
 * @created 2016年4月18日 上午9:55:39
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.message.dto;

import com.ziroom.minsu.services.common.dto.PageRequest;

/**
 * <p>房东设置自动回复 的查询条件</p>
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
public class MsgReplySetRequest extends PageRequest{
	

    /**
	 * 序列id
	 */
	private static final long serialVersionUID = -5578410223331193933L;
	/**
     * 业务编号
     */
    private String fid;
    /**
     * 房源fid
     */
    private String houseFid;

    /**
     * 房东uid
     */
    private String landlordUid;
    
    /**
     * 标签类型 （1=房源标签）
     */
    private Integer lableType;

	public String getHouseFid() {
		return houseFid;
	}

	public void setHouseFid(String houseFid) {
		this.houseFid = houseFid;
	}

	public String getLandlordUid() {
		return landlordUid;
	}

	public void setLandlordUid(String landlordUid) {
		this.landlordUid = landlordUid;
	}

	public String getFid() {
		return fid;
	}

	public void setFid(String fid) {
		this.fid = fid;
	}

	public Integer getLableType() {
		return lableType;
	}

	public void setLableType(Integer lableType) {
		this.lableType = lableType;
	}
    
    

}
