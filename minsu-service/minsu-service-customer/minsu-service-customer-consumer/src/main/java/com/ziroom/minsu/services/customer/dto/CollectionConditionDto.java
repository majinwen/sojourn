package com.ziroom.minsu.services.customer.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

/**
 * 
 * <p>客户房源收藏查询参数</p>
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
public class CollectionConditionDto implements Serializable{
	
	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = -6772215301306459L;

    /**
     * 房源逻辑id(必传)
     */
	@NotNull(message = "{house.fid.null}")
    private String houseFid;

    /**
     * 出租方式 0：整租，1：合租(必传)
     */
    @NotNull(message = "{house.rentway.null}")
    private Integer rentWay;
    
    /**
     * 用户uid
     */
    @NotNull(message = "{uid.null}")
    private String uid;

	public String getHouseFid() {
		return houseFid;
	}

	public void setHouseFid(String houseFid) {
		this.houseFid = houseFid;
	}

	public Integer getRentWay() {
		return rentWay;
	}

	public void setRentWay(Integer rentWay) {
		this.rentWay = rentWay;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}
	
}
