package com.ziroom.zrp.service.trading.pojo;

import com.asura.framework.base.entity.BaseEntity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p></p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @Author wangxm113
 * @Date 2017年10月11日 20时55分
 * @Version 1.0
 * @Since 1.0
 */
public class CalSurrenderPojo extends BaseEntity {
    /**
     * 主键
     */
    private String surrenderId;

    /**
     * 合同ID
     */
    private String contractId;

    /**
     * 出房合同号
     */
    private String conRentCode;

    /**
     * 退租类型  [0 正常退租,1 非正常退租,2 客户单方面解约, 3 三天不满意退租,4 换租,5 转租,6 短租退租]
     */
    private String surType;

    /**
     * 退租申请日期
     */
    private Date applicationDate;

    /**
     * 预计退租日期
     */
    private Date expectedDate;

    /**
     * 物业交割单ID
     */
    private String surrenderCostId;

    /**
     * 退租类型  [0 正常退租,1 非正常退租,2 客户单方面解约, 3 三天不满意退租,4 换租,5 转租,6 短租退租]
     */
    private String costSurType;

    /**
     * 解约责任方 [0 公司，1 租客]
     */
    private String responsibility;

    /**
     * 客户类型：1 普通个人客户 2 企悦会员工 3 企业客户
     */
    private Integer customerType;

    /**
     * 指定合同在解约费用明细表（tsurrendercostitem）中的有效数量
     */
    private Integer itemCount;

    /**
     * 房间id
     */
    private String roomId;

    /**
     * 门牌号 房间号
     */
    private String houseRoomNo;

    /**
     * 赔付费用
     */
    private BigDecimal needPayTZJGPCF;

    /**
     * 解约日期：实际退租日期
     */
    private Date releaseDate;

    /**
     * 退租水电交个表中的应缴金额
     */
    private BigDecimal standardSF = BigDecimal.ZERO;

    /**
     * 退租水电交个表中的应缴金额
     */
    private BigDecimal standardDF = BigDecimal.ZERO;

    public String getConRentCode() {
        return conRentCode;
    }

    public void setConRentCode(String conRentCode) {
        this.conRentCode = conRentCode;
    }

    public Integer getItemCount() {
        return itemCount;
    }

    public void setItemCount(Integer itemCount) {
        this.itemCount = itemCount;
    }

    public String getSurrenderId() {
        return surrenderId;
    }

    public void setSurrenderId(String surrenderId) {
        this.surrenderId = surrenderId;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public String getSurType() {
        return surType;
    }

    public void setSurType(String surType) {
        this.surType = surType;
    }

    public Date getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(Date applicationDate) {
        this.applicationDate = applicationDate;
    }

    public Date getExpectedDate() {
        return expectedDate;
    }

    public void setExpectedDate(Date expectedDate) {
        this.expectedDate = expectedDate;
    }

    public String getSurrenderCostId() {
        return surrenderCostId;
    }

    public void setSurrenderCostId(String surrenderCostId) {
        this.surrenderCostId = surrenderCostId;
    }

    public String getCostSurType() {
        return costSurType;
    }

    public void setCostSurType(String costSurType) {
        this.costSurType = costSurType;
    }

    public String getResponsibility() {
        return responsibility;
    }

    public void setResponsibility(String responsibility) {
        this.responsibility = responsibility;
    }

    public Integer getCustomerType() {
        return customerType;
    }

    public void setCustomerType(Integer customerType) {
        this.customerType = customerType;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getHouseRoomNo() {
        return houseRoomNo;
    }

    public void setHouseRoomNo(String houseRoomNo) {
        this.houseRoomNo = houseRoomNo;
    }

    public BigDecimal getNeedPayTZJGPCF() {
        return needPayTZJGPCF;
    }

    public void setNeedPayTZJGPCF(BigDecimal needPayTZJGPCF) {
        this.needPayTZJGPCF = needPayTZJGPCF;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public BigDecimal getStandardSF() {
        return standardSF;
    }

    public void setStandardSF(BigDecimal standardSF) {
        this.standardSF = standardSF;
    }

    public BigDecimal getStandardDF() {
        return standardDF;
    }

    public void setStandardDF(BigDecimal standardDF) {
        this.standardDF = standardDF;
    }

    @Override
    public String toString() {
        return "CalSurrenderPojo{" +
                "surrenderId='" + surrenderId + '\'' +
                ", contractId='" + contractId + '\'' +
                ", conRentCode='" + conRentCode + '\'' +
                ", surType='" + surType + '\'' +
                ", applicationDate=" + applicationDate +
                ", expectedDate=" + expectedDate +
                ", surrenderCostId='" + surrenderCostId + '\'' +
                ", costSurType='" + costSurType + '\'' +
                ", responsibility='" + responsibility + '\'' +
                ", customerType=" + customerType +
                ", itemCount=" + itemCount +
                ", roomId='" + roomId + '\'' +
                ", houseRoomNo='" + houseRoomNo + '\'' +
                ", needPayTZJGPCF=" + needPayTZJGPCF +
                ", releaseDate=" + releaseDate +
                ", standardSF=" + standardSF +
                ", standardDF=" + standardDF +
                '}';
    }
}
