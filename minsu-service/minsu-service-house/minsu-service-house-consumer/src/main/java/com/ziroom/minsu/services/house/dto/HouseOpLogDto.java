package com.ziroom.minsu.services.house.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.ziroom.minsu.services.common.dto.PageRequest;


/**
 * 
 * <p>房源审核记录dto</p>
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
public class HouseOpLogDto extends PageRequest{
	
	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = 6599317569660421354L;

	@NotNull(message="{house.fid.null}")
	private String houseFid;
	
	@NotNull(message="{house.rentWay.null}")
	private Integer rentWay;
	
	private List<Integer> fromList = new ArrayList<Integer>();

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

	public List<Integer> getFromList() {
		return fromList;
	}

	public void setFromList(List<Integer> fromList) {
		this.fromList = fromList;
	}
	
}
