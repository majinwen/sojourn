package com.zra.common.dto.pay;

import com.zra.common.dto.appbase.AppBaseDto;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by cuigh6 on 2016/12/23.
 */
public class MyContractDetailDto extends AppBaseDto {
    @ApiModelProperty(value = "合同标识")
    private String contractId;
    @ApiModelProperty(value = "项目地址")
    private String projectAddress;
    @ApiModelProperty(value = "合同号")
    private String contractCode;
    @ApiModelProperty(value = "合同状态")
    private String status;
    @ApiModelProperty(value = "项目名称")
    private String ProjectName;
    @ApiModelProperty(value = "项目经度")
    private Double lon;
    @ApiModelProperty(value = "项目纬度")
    private Double lat;
    @ApiModelProperty(value = "电话号码")
    private String contactTel;
    @ApiModelProperty(value = "查看合同的URL，如果为null，则不展示")
    private String viewUrl;
    @ApiModelProperty(value = "付款方式")
    private String payType;
    @ApiModelProperty(value = "房型名称")
    private String houseTypeName;
    @ApiModelProperty(value = "房间价格")
    private String roomPriceStr;
    private BigDecimal roomPrice;
    @ApiModelProperty(value = "房间号")
    private String roomNum;

    @ApiModelProperty(value = "项目图片")
    private String projectImg;

    @ApiModelProperty(value = "账单列表")
    private List<BillDetailDto> billList;

    private String projectId;
    private String customerName;
    private String projectCode;

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public List<BillDetailDto> getBillList() {
        return billList;
    }

    public String getProjectImg() {
        return projectImg;
    }

    public void setProjectImg(String projectImg) {
        this.projectImg = projectImg;
    }

    public void setBillList(List<BillDetailDto> billList) {
        this.billList = billList;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public String getProjectAddress() {
        return projectAddress;
    }

    public void setProjectAddress(String projectAddress) {
        this.projectAddress = projectAddress;
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
        return ProjectName;
    }

    public void setProjectName(String projectName) {
        ProjectName = projectName;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getHouseTypeName() {
        return houseTypeName;
    }

    public void setHouseTypeName(String houseTypeName) {
        this.houseTypeName = houseTypeName;
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

    public String getRoomNum() {
        return roomNum;
    }

    public void setRoomNum(String roomNum) {
        this.roomNum = roomNum;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public String getContactTel() {
        return contactTel;
    }

    public void setContactTel(String contactTel) {
        this.contactTel = contactTel;
    }

    public String getViewUrl() {
        return viewUrl;
    }

    public void setViewUrl(String viewUrl) {
        this.viewUrl = viewUrl;
    }
}
