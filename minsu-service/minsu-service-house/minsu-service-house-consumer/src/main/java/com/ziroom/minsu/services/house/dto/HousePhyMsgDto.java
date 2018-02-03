package com.ziroom.minsu.services.house.dto;

import org.hibernate.validator.constraints.NotEmpty;

import com.ziroom.minsu.entity.house.HousePhyMsgEntity;

/**
 * 
 * <p>房源物理信息dto</p>
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
public class HousePhyMsgDto extends HousePhyMsgEntity {

	/**
	 * 序列化字段
	 */
	private static final long serialVersionUID = 5590165605062926252L;
	
	/** 房源基础信息begin */
	// 房源基本信息表逻辑id
	@NotEmpty(message="{house.base.fid.null}")
	private String houseBaseFid;

	// 房东uid
	private String landlordUid;
	
	// 操作步骤
	private Integer operateSeq;
	
	// 信息完整率
	private Double intactRate;
	/** 房源基础信息end */
	
	public String getHouseBaseFid() {
		return houseBaseFid;
	}
	
	public void setHouseBaseFid(String houseBaseFid) {
		this.houseBaseFid = houseBaseFid;
	}

	public String getLandlordUid() {
		return landlordUid;
	}

	public void setLandlordUid(String landlordUid) {
		this.landlordUid = landlordUid;
	}

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
