package com.ziroom.minsu.services.house.dto;

import com.ziroom.minsu.entity.house.HouseRoomMsgEntity;

/**
 * 
 * <p>房源房间信息dto</p>
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
public class HouseRoomMsgDto extends HouseRoomMsgEntity {

	/**
	 * 序列化字段
	 */
	private static final long serialVersionUID = 1051660133938286986L;
	
	// 操作步骤
	private Integer operateSeq;
	
	// 信息完成率
	private Double intactRate;
	
	public Integer getOperateSeq() {
		return operateSeq;
	}

	public void setOperateSeq(Integer operateSeq) {
		this.operateSeq = operateSeq;
	}

	public Double getIntactRate() {
		return intactRate;
	}

	public void setIntactRate(Double intactRate) {
		this.intactRate = intactRate;
	}

}
