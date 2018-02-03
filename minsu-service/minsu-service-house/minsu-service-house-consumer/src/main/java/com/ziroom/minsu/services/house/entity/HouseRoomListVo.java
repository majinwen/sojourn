/**
 * @FileName: HouseRoomListVo.java
 * @Package com.ziroom.minsu.services.house.entity
 * 
 * @author bushujie
 * @created 2016年4月2日 下午3:31:32
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.entity;

import com.ziroom.minsu.entity.house.HouseRoomMsgEntity;

/**
 * <p>房东端房间列表vo</p>
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
public class HouseRoomListVo extends HouseRoomMsgEntity{

	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = 3903834415058607921L;
	//房源名称
	private String houseName;
	//默认图片地址
	private String defaultPic;
	//房源未来30天预订率
	private Double houseBookRate;
    
	public String getHouseName() {
		return houseName;
	}

	public void setHouseName(String houseName) {
		this.houseName = houseName;
	}

	public String getDefaultPic() {
		return defaultPic;
	}

	public void setDefaultPic(String defaultPic) {
		this.defaultPic = defaultPic;
	}

	public Double getHouseBookRate() {
		return houseBookRate;
	}

	public void setHouseBookRate(Double houseBookRate) {
		this.houseBookRate = houseBookRate;
	}
}
