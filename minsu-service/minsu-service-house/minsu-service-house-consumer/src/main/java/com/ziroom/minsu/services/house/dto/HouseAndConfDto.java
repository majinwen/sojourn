/**
 * @FileName: HouseAndConfDto.java
 * @Package com.ziroom.minsu.services.house.dto
 * 
 * @author zl
 * @created 2017年7月5日 上午11:41:38
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.dto;

import java.io.Serializable;
import java.util.List;

import com.ziroom.minsu.entity.house.HouseConfMsgEntity;

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
public class HouseAndConfDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -72065869257970092L;
	
	private HouseBaseExtDto houseBaseExtDto;
	
	
	private List<HouseConfMsgEntity> houseConfMsgList;


	public HouseBaseExtDto getHouseBaseExtDto() {
		return houseBaseExtDto;
	}


	public List<HouseConfMsgEntity> getHouseConfMsgList() {
		return houseConfMsgList;
	}


	public void setHouseBaseExtDto(HouseBaseExtDto houseBaseExtDto) {
		this.houseBaseExtDto = houseBaseExtDto;
	}


	public void setHouseConfMsgList(List<HouseConfMsgEntity> houseConfMsgList) {
		this.houseConfMsgList = houseConfMsgList;
	} 

}
