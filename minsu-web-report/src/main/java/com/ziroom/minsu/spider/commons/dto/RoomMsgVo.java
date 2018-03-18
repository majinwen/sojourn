package com.ziroom.minsu.spider.commons.dto;

import java.math.BigDecimal;
import java.util.List;

import com.ziroom.minsu.entity.house.HouseBedMsgEntity;
import com.ziroom.minsu.entity.house.HouseRoomMsgEntity;

/**
 * 
 * <p>房源房间信息vo</p>
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
public class RoomMsgVo extends HouseRoomMsgEntity {

	/**
	 * 序列化字段
	 */
	private static final long serialVersionUID = 2127382004433267389L;
	
	/**
	 * 房间日价格(元)
	 */
	private BigDecimal leasePrice;
	
	/**
	 * 房间床位集合
	 */
	private List<HouseBedMsgEntity> bedList;
	
	public BigDecimal getLeasePrice() {
		return leasePrice;
	}
	
	public void setLeasePrice(BigDecimal leasePrice) {
		this.leasePrice = leasePrice;
	}

	public List<HouseBedMsgEntity> getBedList() {
		return bedList;
	}

	public void setBedList(List<HouseBedMsgEntity> bedList) {
		this.bedList = bedList;
	}
	
}
