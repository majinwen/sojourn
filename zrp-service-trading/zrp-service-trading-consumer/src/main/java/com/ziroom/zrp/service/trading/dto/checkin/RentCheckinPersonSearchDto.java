/**
 * @FileName: RentCheckinPersonSearchDto.java
 * @Package com.ziroom.zrp.service.trading.dto.checkin
 * 
 * @author bushujie
 * @created 2017年12月4日 下午8:34:26
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.zrp.service.trading.dto.checkin;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.zra.common.dto.base.BasePageParamDto;


/**
 * <p>入住人列表查询条件</p>
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
public class RentCheckinPersonSearchDto extends BasePageParamDto implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4882953287771532247L;
	/**
	 * 项目id
	 */
	private String projectId;
	/**
	 * 录入状态 1：未录入，2：已录入
	 */
	private Integer intoState;
	/**
	 * 入住人姓名
	 */
	private String checkinPersonName;
	/**
	 * 合同编号
	 */
	private String contractSn;
	/**
	 * 签约状态
	 */
	private String conTractStatus;
	/**
	 * 签约开始时间
	 */
	private String signingStartDate;
	/**
	 * 签约结束时间
	 */
	private String signingEndDate;
	/**
	 * 入住人手机号
	 */
	private String checkinPersonPhone;
	/**
	 * 房间编号
	 */
	private String roomSn;
	/**
	 * 出租方式
	 */
	private Integer rentType;
	
	/**
	 * 合同id
	 */
	private String contractId;
	
    /**
     * 项目id列表
     */
    private List<String> projectIdList=new ArrayList<String>();

    /**
	 * @return the projectIdList
	 */
	public List<String> getProjectIdList() {
		return projectIdList;
	}

	/**
	 * @param projectIdList the projectIdList to set
	 */
	public void setProjectIdList(List<String> projectIdList) {
		this.projectIdList = projectIdList;
	}
	
	/**
	 * @return the contractId
	 */
	public String getContractId() {
		return contractId;
	}

	/**
	 * @param contractId the contractId to set
	 */
	public void setContractId(String contractId) {
		this.contractId = contractId;
	}

	
	/**
	 * @return the projectId
	 */
	public String getProjectId() {
		return projectId;
	}

	/**
	 * @param projectId the projectId to set
	 */
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	/**
	 * @return the intoState
	 */
	public Integer getIntoState() {
		return intoState;
	}

	/**
	 * @param intoState the intoState to set
	 */
	public void setIntoState(Integer intoState) {
		this.intoState = intoState;
	}

	/**
	 * @return the checkinPersonName
	 */
	public String getCheckinPersonName() {
		return checkinPersonName;
	}

	/**
	 * @param checkinPersonName the checkinPersonName to set
	 */
	public void setCheckinPersonName(String checkinPersonName) {
		this.checkinPersonName = checkinPersonName;
	}
	/**
	 * @return the contractSn
	 */
	public String getContractSn() {
		return contractSn;
	}

	/**
	 * @param contractSn the contractSn to set
	 */
	public void setContractSn(String contractSn) {
		this.contractSn = contractSn;
	}

	/**
	 * @return the conTractStatus
	 */
	public String getConTractStatus() {
		return conTractStatus;
	}

	/**
	 * @param conTractStatus the conTractStatus to set
	 */
	public void setConTractStatus(String conTractStatus) {
		this.conTractStatus = conTractStatus;
	}

	/**
	 * @return the signingStartDate
	 */
	public String getSigningStartDate() {
		return signingStartDate;
	}

	/**
	 * @param signingStartDate the signingStartDate to set
	 */
	public void setSigningStartDate(String signingStartDate) {
		this.signingStartDate = signingStartDate;
	}

	/**
	 * @return the signingEndDate
	 */
	public String getSigningEndDate() {
		return signingEndDate;
	}

	/**
	 * @param signingEndDate the signingEndDate to set
	 */
	public void setSigningEndDate(String signingEndDate) {
		this.signingEndDate = signingEndDate;
	}

	/**
	 * @return the checkinPersonPhone
	 */
	public String getCheckinPersonPhone() {
		return checkinPersonPhone;
	}

	/**
	 * @param checkinPersonPhone the checkinPersonPhone to set
	 */
	public void setCheckinPersonPhone(String checkinPersonPhone) {
		this.checkinPersonPhone = checkinPersonPhone;
	}

	/**
	 * @return the roomSn
	 */
	public String getRoomSn() {
		return roomSn;
	}

	/**
	 * @param roomSn the roomSn to set
	 */
	public void setRoomSn(String roomSn) {
		this.roomSn = roomSn;
	}

	/**
	 * @return the rentType
	 */
	public Integer getRentType() {
		return rentType;
	}

	/**
	 * @param rentType the rentType to set
	 */
	public void setRentType(Integer rentType) {
		this.rentType = rentType;
	}
}
