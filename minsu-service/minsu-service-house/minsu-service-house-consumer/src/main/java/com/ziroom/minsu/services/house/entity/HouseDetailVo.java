/**
 * @FileName: HouseDetailVo.java
 * @Package com.ziroom.minsu.services.house.entity
 * 
 * @author bushujie
 * @created 2016年4月5日 下午11:05:32
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.entity;

import com.ziroom.minsu.entity.house.HouseBaseMsgEntity;
import com.ziroom.minsu.entity.house.HousePicMsgEntity;

/**
 * <p>房源详情</p>
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
public class HouseDetailVo extends HouseBaseMsgEntity{
	
	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = 7848435194845708290L;
	/**
	 * 房屋类型名称
	 */
	private String rentWayName;
	/**
	 * 房源默认图片
	 */
	private HousePicMsgEntity houseDefaultPic;
	
	/**
	 * @return the houseDefaultPic
	 */
	public HousePicMsgEntity getHouseDefaultPic() {
		return houseDefaultPic;
	}
	/**
	 * @param houseDefaultPic the houseDefaultPic to set
	 */
	public void setHouseDefaultPic(HousePicMsgEntity houseDefaultPic) {
		this.houseDefaultPic = houseDefaultPic;
	}

    public String getRentWayName() {
        return rentWayName;
    }

    public void setRentWayName(String rentWayName) {
        this.rentWayName = rentWayName;
    }
}
