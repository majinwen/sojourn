package com.ziroom.minsu.mapp.house.vo;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.ziroom.minsu.mapp.common.dto.BaseParamDto;

/**
 * <p>房源评价列表参数</p>
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
public class HouseEvaluate extends BaseParamDto implements Serializable{
	

	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = -2009656581388112161L;
	
	/**
	 * 房源或者房间fid
	 */
	@NotEmpty(message="{house.fid.null}")
	private String fid;
	
	/**
	 * 出租方式
	 */
	@NotNull(message = "house.rentway.null")
	private Integer rentWay;

	public String getFid() {
		return fid;
	}

	public void setFid(String fid) {
		this.fid = fid;
	}

	public Integer getRentWay() {
		return rentWay;
	}

	public void setRentWay(Integer rentWay) {
		this.rentWay = rentWay;
	}
}
