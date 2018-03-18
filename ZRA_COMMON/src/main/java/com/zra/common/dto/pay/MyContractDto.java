package com.zra.common.dto.pay;

import com.zra.common.dto.appbase.AppBaseDto;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by cuigh6 on 2016/12/22.
 */
public class MyContractDto extends AppBaseDto {

    @ApiModelProperty(value = "合同标识")
    private String contractId;

    @ApiModelProperty(value = "合同号")
    private String contractCode;

    @ApiModelProperty(value = "合同状态")
    private String status;

    @ApiModelProperty(value = "项目名称")
    private String projectName;

    @ApiModelProperty(value = "房间号")
    private String roomCode;

    @ApiModelProperty(value = "开始时间")
    private String startDateStr;
    private Date startDate;

    @ApiModelProperty(value = "结束时间")
    private String endDateStr;
    private Date endDate;

    @ApiModelProperty(value = "付款方式")
    private Integer payType;

    @ApiModelProperty(value = "房租")
    private String roomPriceStr;
    private BigDecimal roomPrice;

    @ApiModelProperty(value = "联系电话")
    private String contactTel;

    @ApiModelProperty(value = "项目图片")
    private String projectImg;

    private String uid;

    public String getProjectImg() {
        return projectImg;
    }

    public void setProjectImg(String projectImg) {
        this.projectImg = projectImg;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getContactTel() {
        return contactTel;
    }

    public void setContactTel(String contactTel) {
        this.contactTel = contactTel;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public String getContractCode() {
        return contractCode;
    }

    public void setContractCode(String contractCode) {
        this.contractCode = contractCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getRoomCode() {
        return roomCode;
    }

    public void setRoomCode(String roomCode) {
        this.roomCode = roomCode;
    }

    public String getStartDateStr() {
        return startDateStr;
    }

    public void setStartDateStr(String startDateStr) {
        this.startDateStr = startDateStr;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getEndDateStr() {
        return endDateStr;
    }

    public void setEndDateStr(String endDateStr) {
        this.endDateStr = endDateStr;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public String getRoomPriceStr() {
        return roomPriceStr;
    }

    public void setRoomPriceStr(String roomPriceStr) {
        this.roomPriceStr = roomPriceStr;
    }

    public BigDecimal getRoomPrice() {
        return roomPrice;
    }

    public void setRoomPrice(BigDecimal roomPrice) {
        this.roomPrice = roomPrice;
    }

}
