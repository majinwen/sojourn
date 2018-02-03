package com.ziroom.minsu.services.house.dto;

import com.ziroom.minsu.entity.house.HouseBaseExtEntity;
import com.ziroom.minsu.entity.house.HouseBaseMsgEntity;

/**
 * 
 * <p>房源基本信息扩展dto</p>
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
public class HouseBaseExtDto extends HouseBaseMsgEntity {
	
	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = -7463094863059166545L;
	
	private HouseBaseExtEntity houseBaseExt;

	public HouseBaseExtEntity getHouseBaseExt() {
		return houseBaseExt;
	}

	public void setHouseBaseExt(HouseBaseExtEntity houseBaseExt) {
		this.houseBaseExt = houseBaseExt;
	}

}
