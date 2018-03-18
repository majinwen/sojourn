package com.zra.common.dto.kanban;

import java.math.BigDecimal;

import io.swagger.annotations.ApiModelProperty;

/**
 * 二级数据精确到管家Dto
 * @author tianxf9
 *
 */
public class SecondDataDetailDto {
	

	@ApiModelProperty(value = "项目id")
	private String projectId;
	
	@ApiModelProperty(value = "zoId")
	private String zoId;

	@ApiModelProperty(value = "zoName")
	private String zoName;

	@ApiModelProperty(value = "商机约看平均处理时长")
	private BigDecimal ykDealAvglong;

	@ApiModelProperty(value = "退租量")
	private Integer quitCount;

	@ApiModelProperty(value = "新签量")
	private Integer signNewCount;

	@ApiModelProperty(value = "续约量")
	private Integer renewCount;
	
	@ApiModelProperty(value="0~5天空置房源数量")
    private Integer emptyRoomRange1;
	
    @ApiModelProperty(value="6~10天空置房源数量")
    private Integer emptyRoomRange2;

    @ApiModelProperty(value="11～15天空置房源数量")
    private Integer emptyRoomRange3;

    @ApiModelProperty(value="15天以上空置房源数量")
    private Integer emptyRoomRange4;
    
    @ApiModelProperty(value="平均提前回款天数")
    private BigDecimal earlyPaymentAvgdays;

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getZoId() {
		return zoId;
	}

	public void setZoId(String zoId) {
		this.zoId = zoId;
	}

	
	public String getZoName() {
		return zoName;
	}

	public void setZoName(String zoName) {
		this.zoName = zoName;
	}

	public BigDecimal getYkDealAvglong() {
		return ykDealAvglong;
	}

	public void setYkDealAvglong(BigDecimal ykDealAvglong) {
		this.ykDealAvglong = ykDealAvglong;
	}

	public Integer getQuitCount() {
		return quitCount;
	}

	public void setQuitCount(Integer quitCount) {
		this.quitCount = quitCount;
	}

	public Integer getSignNewCount() {
		return signNewCount;
	}

	public void setSignNewCount(Integer signNewCount) {
		this.signNewCount = signNewCount;
	}

	public Integer getRenewCount() {
		return renewCount;
	}

	public void setRenewCount(Integer renewCount) {
		this.renewCount = renewCount;
	}

	public Integer getEmptyRoomRange1() {
		return emptyRoomRange1;
	}

	public void setEmptyRoomRange1(Integer emptyRoomRange1) {
		this.emptyRoomRange1 = emptyRoomRange1;
	}

	public Integer getEmptyRoomRange2() {
		return emptyRoomRange2;
	}

	public void setEmptyRoomRange2(Integer emptyRoomRange2) {
		this.emptyRoomRange2 = emptyRoomRange2;
	}

	public Integer getEmptyRoomRange3() {
		return emptyRoomRange3;
	}

	public void setEmptyRoomRange3(Integer emptyRoomRange3) {
		this.emptyRoomRange3 = emptyRoomRange3;
	}

	public Integer getEmptyRoomRange4() {
		return emptyRoomRange4;
	}

	public void setEmptyRoomRange4(Integer emptyRoomRange4) {
		this.emptyRoomRange4 = emptyRoomRange4;
	}

	public BigDecimal getEarlyPaymentAvgdays() {
		return earlyPaymentAvgdays;
	}

	public void setEarlyPaymentAvgdays(BigDecimal earlyPaymentAvgdays) {
		this.earlyPaymentAvgdays = earlyPaymentAvgdays;
	}

}
