/**
 * @FileName: HouseUpdateFieldAuditDto.java
 * @Package com.ziroom.minsu.services.house.dto
 * 
 * @author yd
 * @created 2017年9月22日 上午10:41:47
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.dto;

import java.io.Serializable;
import java.util.List;

import com.ziroom.minsu.entity.house.HouseUpdateFieldAuditNewlogEntity;

/**
 * <p>房源信息审核参数</p>
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
public class HouseUpdateFieldAuditDto implements Serializable{

	/**
	 * 序列id
	 */
	private static final long serialVersionUID = -1363527660564023210L;
	
	/**
	 * 信息审核记录
	 */
	private HouseUpdateFieldAuditNewlogEntity houseUpdateFieldAuditNewlog;
	
	
	/**
	 * 图片fids
	 */
	private  List<String> picFids;


	/**
	 * @return the houseUpdateFieldAuditNewlog
	 */
	public HouseUpdateFieldAuditNewlogEntity getHouseUpdateFieldAuditNewlog() {
		return houseUpdateFieldAuditNewlog;
	}


	/**
	 * @param houseUpdateFieldAuditNewlog the houseUpdateFieldAuditNewlog to set
	 */
	public void setHouseUpdateFieldAuditNewlog(
			HouseUpdateFieldAuditNewlogEntity houseUpdateFieldAuditNewlog) {
		this.houseUpdateFieldAuditNewlog = houseUpdateFieldAuditNewlog;
	}


	/**
	 * @return the picFids
	 */
	public List<String> getPicFids() {
		return picFids;
	}


	/**
	 * @param picFids the picFids to set
	 */
	public void setPicFids(List<String> picFids) {
		this.picFids = picFids;
	}
	
	

}
