/**
 * @FileName: HouseBaseList.java
 * @Package com.ziroom.minsu.services.house.entity
 * 
 * @author bushujie
 * @created 2016年4月2日 上午11:27:43
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.entity;

import com.ziroom.minsu.entity.house.HouseBaseMsgEntity;

/**
 * <p>房东端房源列表Vo</p>
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
public class HouseBaseListVo extends HouseBaseMsgEntity{

	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = -580904582547745494L;	
	//默认图片地址
	private String defaultPic;
	//房源未来30天预订率
	private Double houseBookRate;
	/**
	 * @return the houseBookRate
	 */
	public Double getHouseBookRate() {
		return houseBookRate;
	}
	/**
	 * @param houseBookRate the houseBookRate to set
	 */
	public void setHouseBookRate(Double houseBookRate) {
		this.houseBookRate = houseBookRate;
	}
	/**
	 * @return the defaultPic
	 */
	public String getDefaultPic() {
		return defaultPic;
	}
	/**
	 * @param defaultPic the defaultPic to set
	 */
	public void setDefaultPic(String defaultPic) {
		this.defaultPic = defaultPic;
	}
}
