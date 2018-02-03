/**
 * @FileName: HouseDescAndBaseInfoDto.java
 * @Package com.ziroom.minsu.services.house.issue.dto
 * 
 * @author lusp
 * @created 2017年6月29日 上午10:22:43
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.issue.dto;

import com.ziroom.minsu.services.house.dto.HouseBaseParamsDto;
import com.ziroom.minsu.services.house.issue.vo.HouseRoomVo;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * <p>房源价格保存Dto</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author lusp
 * @since 1.0
 * @version 1.0
 */
public class HouseDescAndBaseInfoDto extends HouseBaseParamsDto {

	/**
	 * 面积
	 */
	@NotNull(message="房源面积不能为空")
	private Double houseArea;
	
	/**
	 * 选择的配套设施
	 */
	@NotNull(message="配套设施不能为空")
	private String houseFacility;
	
	/**
	 * 入住人数限制
	 */
	@NotNull(message="入住人数限制不能为空")
	private Integer checkInLimit;
	
	/**
	 * 户型
	 */
	@NotNull(message="房源户型不能为空")
	private String houseModel;
	
	/**
	 * 房间信息和床信息
	 */
	@NotNull(message="房源列表不能为空")
	private List<HouseRoomVo> houseRoomList;
	
	/**
	 * 创建人fid
	 */
	private String createFid;

	/**
	 * 房源名称
	 */
	@NotNull(message="房源名称不能为空")
	private String houseName;

	/**
	 * 房源描述
	 */
	@NotNull(message="房源描述不能为空")
	private String houseDesc;

	/**
	 * 周边状况
	 */
	@NotNull(message="房源周边情况不能为空")
	private String houseAroundDesc;

	/**
	 * 删除房间的FID
	 */
	private List<String> delRoomFidList;

	/**
	 * 是否与房东同住 0：否，1：是
	 */
	private Integer isTogetherLandlord;

	/**
	 * 是否发布房源流程 0：否，1：是
	 */
	private Integer isIssue=0;

	public Double getHouseArea() {
		return houseArea;
	}

	public void setHouseArea(Double houseArea) {
		this.houseArea = houseArea;
	}

	public String getHouseFacility() {
		return houseFacility;
	}

	public void setHouseFacility(String houseFacility) {
		this.houseFacility = houseFacility;
	}

	public Integer getCheckInLimit() {
		return checkInLimit;
	}

	public void setCheckInLimit(Integer checkInLimit) {
		this.checkInLimit = checkInLimit;
	}

	public String getHouseModel() {
		return houseModel;
	}

	public void setHouseModel(String houseModel) {
		this.houseModel = houseModel;
	}

	public List<HouseRoomVo> getHouseRoomList() {
		return houseRoomList;
	}

	public void setHouseRoomList(List<HouseRoomVo> houseRoomList) {
		this.houseRoomList = houseRoomList;
	}

	public String getCreateFid() {
		return createFid;
	}

	public void setCreateFid(String createFid) {
		this.createFid = createFid;
	}

	public String getHouseDesc() {
		return houseDesc;
	}

	public void setHouseDesc(String houseDesc) {
		this.houseDesc = houseDesc;
	}

	public Integer getIsTogetherLandlord() {
		return isTogetherLandlord;
	}

	public void setIsTogetherLandlord(Integer isTogetherLandlord) {
		this.isTogetherLandlord = isTogetherLandlord;
	}

	public Integer getIsIssue() {
		return isIssue;
	}

	public void setIsIssue(Integer isIssue) {
		this.isIssue = isIssue;
	}

	public String getHouseName() {
		return houseName;
	}

	public void setHouseName(String houseName) {
		this.houseName = houseName;
	}

	public String getHouseAroundDesc() {
		return houseAroundDesc;
	}

	public void setHouseAroundDesc(String houseAroundDesc) {
		this.houseAroundDesc = houseAroundDesc;
	}

	public List<String> getDelRoomFidList() {
		return delRoomFidList;
	}

	public void setDelRoomFidList(List<String> delRoomFidList) {
		this.delRoomFidList = delRoomFidList;
	}
}
