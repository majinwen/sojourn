/**
 * @FileName: HouseFollowUpdateDto.java
 * @Package com.ziroom.minsu.services.house.dto
 * 
 * @author yd
 * @created 2017年4月19日 下午2:29:25
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.dto;

import com.asura.framework.base.entity.BaseEntity;

/**
 * <p>房源跟进修改的dto</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author yd
 * @since 1.0
 * @version 1.0
 */
public class HouseFollowUpdateDto extends BaseEntity{

	/**
	 * 序列id
	 */
	private static final long serialVersionUID = 8197369250479484152L;

	
	/**
	 * 跟进fid
	 */
	private String fid;
	
	/**
	 * 修改前:跟进状态
	 */
	private Integer followStatusOld;
	
	/**
	 * 修改后状态
	 */
	private Integer followStatus;
	
	/**
	 * 跟新日志
	 */
	private HouseFollowLogDto houseFollowLogDto = new HouseFollowLogDto();

	/**
	 * @return the fid
	 */
	public String getFid() {
		return fid;
	}

	/**
	 * @param fid the fid to set
	 */
	public void setFid(String fid) {
		this.fid = fid;
	}

	/**
	 * @return the followStatusOld
	 */
	public Integer getFollowStatusOld() {
		return followStatusOld;
	}

	/**
	 * @param followStatusOld the followStatusOld to set
	 */
	public void setFollowStatusOld(Integer followStatusOld) {
		this.followStatusOld = followStatusOld;
	}

	/**
	 * @return the followStatus
	 */
	public Integer getFollowStatus() {
		return followStatus;
	}

	/**
	 * @param followStatus the followStatus to set
	 */
	public void setFollowStatus(Integer followStatus) {
		this.followStatus = followStatus;
	}

	/**
	 * @return the houseFollowLogDto
	 */
	public HouseFollowLogDto getHouseFollowLogDto() {
		return houseFollowLogDto;
	}

	/**
	 * @param houseFollowLogDto the houseFollowLogDto to set
	 */
	public void setHouseFollowLogDto(HouseFollowLogDto houseFollowLogDto) {
		this.houseFollowLogDto = houseFollowLogDto;
	}
	
	
}
