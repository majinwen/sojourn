package com.zra.common.dto.pay;

import com.zra.common.dto.appbase.AppBaseDto;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by cuigh6 on 2016/12/20.
 */
public class ToPayDto  extends AppBaseDto {
    @ApiModelProperty(value = "账单标识")
    private String billFid;

    @ApiModelProperty(value = "项目名称")
    private String projectName;

    @ApiModelProperty(value = "项目图片")
    private String projectImg;

    @ApiModelProperty(value = "房租金额")
    private String amountStr;

    private BigDecimal amount;

    @ApiModelProperty(value = "期数")
    private Integer payNum;

    @ApiModelProperty(value = "开始日期")
    private String startDateStr;

    @ApiModelProperty(value = "结束日期")
    private String endDateStr;

    @ApiModelProperty(value = "账单类型")
    private String billTypeStr;
    private Integer billType;

    @ApiModelProperty(value = "房间号")
    private String roomCode;

    @ApiModelProperty(value = "费用项")
    private String costItems;

    private Date startDate;
    private Date endDate;
    private String uid;
    private Date planGatherDate;

    public Date getPlanGatherDate() {
        return planGatherDate;
    }

    public void setPlanGatherDate(Date planGatherDate) {
        this.planGatherDate = planGatherDate;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getCostItems() {
        return costItems;
    }

    public void setCostItems(String costItems) {
        this.costItems = costItems;
    }
    public String getBillTypeStr() {
        return billTypeStr;
    }

    public void setBillTypeStr(String billTypeStr) {
        this.billTypeStr = billTypeStr;
    }

    public String getAmountStr() {
        return amountStr;
    }

    public void setAmountStr(String amountStr) {
        this.amountStr = amountStr;
    }

    public String getBillFid() {
        return billFid;
    }

    public void setBillFid(String billFid) {
        this.billFid = billFid;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectImg() {
        return projectImg;
    }

    public void setProjectImg(String projectImg) {
        this.projectImg = projectImg;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getPayNum() {
        return payNum;
    }

    public void setPayNum(Integer payNum) {
        this.payNum = payNum;
    }

    public String getStartDateStr() {
        return startDateStr;
    }

    public void setStartDateStr(String startDateStr) {
        this.startDateStr = startDateStr;
    }

    public String getEndDateStr() {
        return endDateStr;
    }

    public void setEndDateStr(String endDateStr) {
        this.endDateStr = endDateStr;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Integer getBillType() {
        return billType;
    }

    public void setBillType(Integer billType) {
        this.billType = billType;
    }

    public String getRoomCode() {
        return roomCode;
    }

    public void setRoomCode(String roomCode) {
        this.roomCode = roomCode;
    }
}

