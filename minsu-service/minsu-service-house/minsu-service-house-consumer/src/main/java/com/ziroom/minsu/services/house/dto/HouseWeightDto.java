package com.ziroom.minsu.services.house.dto;

import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * <p>房源权重dto</p>
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
public class HouseWeightDto{
	
	// 房源出租类型
	@NotNull(message="{house.rentway.null}")
	private Integer rentWay;

	// 房源权重
	@NotNull(message="{house.weight.null}")
	@Min(value=-100)
	private Integer weight;
	
	// 房源逻辑id集合
	@NotEmpty(message="{house.and.room.fid.null}")
	private List<String> houseFidList;

	public Integer getRentWay() {
		return rentWay;
	}

	public void setRentWay(Integer rentWay) {
		this.rentWay = rentWay;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public List<String> getHouseFidList() {
		return houseFidList;
	}

	public void setHouseFidList(List<String> houseFidList) {
		this.houseFidList = houseFidList;
	}

}
