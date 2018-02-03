package com.ziroom.minsu.services.house.dto;

import com.ziroom.minsu.entity.house.HouseBaseMsgEntity;

/**
 * 
 * <p>房源基本信息dto</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author liujun
 * @since 1.0
 * @version 1.0
 */
public class HouseBaseMsgDto extends HouseBaseMsgEntity {
	
	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = 6809162793638115404L;
	
	private String houseDesc;
	
	private String houseDescFid;

	public String getHouseDesc() {
		return houseDesc;
	}

	public void setHouseDesc(String houseDesc) {
		this.houseDesc = houseDesc;
	}

	public String getHouseDescFid() {
		return houseDescFid;
	}

	public void setHouseDescFid(String houseDescFid) {
		this.houseDescFid = houseDescFid;
	}

}
