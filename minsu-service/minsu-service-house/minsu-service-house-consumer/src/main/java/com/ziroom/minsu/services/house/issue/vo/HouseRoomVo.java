/**
 * @FileName: HouseRoomVo.java
 * @Package com.ziroom.minsu.services.house.issue.vo
 * 
 * @author bushujie
 * @created 2017年6月15日 下午7:32:16
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.issue.vo;

/**
 * <p>TODO</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author bushujie
 * @since 1.0
 * @version 1.0
 */
public class HouseRoomVo {
	
	/**
	 * 房间fid
	 */
	private String fid;
	
	/**
	 * 床类型和数量  床类型_床数量,床类型_床数量
	 */
	private String bedMsg;

	
	/**
	 * @return the fid
	 */
	public String getFid() {
		return fid;
	}

	/**
	 * @param fid the fid to set
	 */
	public void setFid(String fid) {
		this.fid = fid;
	}

	/**
	 * @return the bedMsg
	 */
	public String getBedMsg() {
		return bedMsg;
	}

	/**
	 * @param bedMsg the bedMsg to set
	 */
	public void setBedMsg(String bedMsg) {
		this.bedMsg = bedMsg;
	}
}
