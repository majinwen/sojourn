/**
 * @FileName: MsgFirstDdvisoryRequest.java
 * @Package com.ziroom.minsu.services.message.dto
 * 
 * @author yd
 * @created 2017年4月8日 下午2:22:42
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.message.dto;

import java.io.Serializable;
import java.util.List;

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
 * @author yd
 * @since 1.0
 * @version 1.0
 */
public class MsgFirstDdvisoryRequest extends PageRequest{

	/**
	 * 序列id
	 */
	private static final long serialVersionUID = -4557744782813608188L;

	/**
	 * 执行状态
	 */
	private List<Integer> listStatus;
	/**
	 * 执行时间
	 */
	private String runTime;
	/**
	 * @return the listStatus
	 */
	public List<Integer> getListStatus() {
		return listStatus;
	}
	/**
	 * @param listStatus the listStatus to set
	 */
	public void setListStatus(List<Integer> listStatus) {
		this.listStatus = listStatus;
	}
	/**
	 * @return the runTime
	 */
	public String getRunTime() {
		return runTime;
	}
	/**
	 * @param runTime the runTime to set
	 */
	public void setRunTime(String runTime) {
		this.runTime = runTime;
	}
	
	
	
}
