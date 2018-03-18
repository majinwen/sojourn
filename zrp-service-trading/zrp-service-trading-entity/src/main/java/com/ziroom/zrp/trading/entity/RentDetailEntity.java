package com.ziroom.zrp.trading.entity;

import com.asura.framework.base.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
/**
 * 合同详情  包含物业交割
 * @author jixd
 * @created 2017年09月22日 15:57:10
 * @param
 * @return
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RentDetailEntity extends BaseEntity{

    private static final long serialVersionUID = 2592429125403883431L;
    /**
     * id
     */
    private String id;

    /**
     * 合同id
     */
    private String contractId;

    /**
     * 房间id
     */
    private String roomId;

    /**
     * 物业交割状态；0：未交割；1:已交割
     */
    private Integer deliveryState;

    /**
     * 应收服务费
     */
    private Double mustCommission;

    /**
     * 折扣服务费
     */
    private Double discountCommission;

    /**
     * 应收押金
     */
    private Double mustDeposit;

    /**
     * 折扣押金
     */
    private Double discountDeposit;

    /**
     * 标准出房价格
     */
    private Double basePrice;

    /**
     * 实际出房价格
     */
    private Double actualPrice;

    /**
     * 折扣出房价格
     */
    private Double discountPrice;

    /**
     * 房间面积
     */
    private Double roomArea;

    /**
     * 解约申请日期
     */
    private Date applicationDate;

    /**
     * 解约期望日期
     */
    private Date expectedDate;

    /**
     * 实际解约日期
     */
    private Date actualDate;

    /**
     * 在当前合同中的状态值:以后备用的状态值
     */
    private Integer currentState;

    /**
     * 房屋编码
     */
    private String roomCode;

    /**
     * 是否删除；0：未删除；1:删除
     */
    private Integer isDeleted;

    /**
     * 删除人
     */
    private String deleterId;

    /**
     * 删除时间
     */
    private Date deletedTime;

    /**
     * 创建者
     */
    private String createrId;

    /**
     * 创建时间
     */
    private Date createdTime;

    /**
     * 更新者
     */
    private String updaterId;

    /**
     * 更新时间
     */
    private Date updatedTime;

    /**
     * 入住人唯一标识
     */
    private String personUid;

    /**
     * 入住人信息录入状态 1-未录入；2-已录入
     */
    private Integer personDataStatus;

    /**
     * 类型：0:-房间；1-床位;
     */
    private Integer ftype;

    /**
     * 是否有效 0--无效 1-有效
     */
    private Integer fvalid;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId == null ? null : contractId.trim();
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId == null ? null : roomId.trim();
    }

    public Integer getDeliveryState() {
        return deliveryState;
    }

    public void setDeliveryState(Integer deliveryState) {
        this.deliveryState = deliveryState;
    }

    public Double getMustCommission() {
        return mustCommission;
    }

    public void setMustCommission(Double mustCommission) {
        this.mustCommission = mustCommission;
    }

    public Double getDiscountCommission() {
        return discountCommission;
    }

    public void setDiscountCommission(Double discountCommission) {
        this.discountCommission = discountCommission;
    }

    public Double getMustDeposit() {
        return mustDeposit;
    }

    public void setMustDeposit(Double mustDeposit) {
        this.mustDeposit = mustDeposit;
    }

    public Double getDiscountDeposit() {
        return discountDeposit;
    }

    public void setDiscountDeposit(Double discountDeposit) {
        this.discountDeposit = discountDeposit;
    }

    public Double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(Double basePrice) {
        this.basePrice = basePrice;
    }

    public Double getActualPrice() {
        return actualPrice;
    }

    public void setActualPrice(Double actualPrice) {
        this.actualPrice = actualPrice;
    }

    public Double getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(Double discountPrice) {
        this.discountPrice = discountPrice;
    }

    public Double getRoomArea() {
        return roomArea;
    }

    public void setRoomArea(Double roomArea) {
        this.roomArea = roomArea;
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

    public Date getActualDate() {
        return actualDate;
    }

    public void setActualDate(Date actualDate) {
        this.actualDate = actualDate;
    }

    public Integer getCurrentState() {
        return currentState;
    }

    public void setCurrentState(Integer currentState) {
        this.currentState = currentState;
    }

    public String getRoomCode() {
        return roomCode;
    }

    public void setRoomCode(String roomCode) {
        this.roomCode = roomCode == null ? null : roomCode.trim();
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getDeleterId() {
        return deleterId;
    }

    public void setDeleterId(String deleterId) {
        this.deleterId = deleterId == null ? null : deleterId.trim();
    }

    public Date getDeletedTime() {
        return deletedTime;
    }

    public void setDeletedTime(Date deletedTime) {
        this.deletedTime = deletedTime;
    }

    public String getCreaterId() {
        return createrId;
    }

    public void setCreaterId(String createrId) {
        this.createrId = createrId == null ? null : createrId.trim();
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getUpdaterId() {
        return updaterId;
    }

    public void setUpdaterId(String updaterId) {
        this.updaterId = updaterId == null ? null : updaterId.trim();
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    public String getPersonUid() {
        return personUid;
    }

    public void setPersonUid(String personUid) {
        this.personUid = personUid == null ? null : personUid.trim();
    }

    public Integer getPersonDataStatus() {
        return personDataStatus;
    }

    public void setPersonDataStatus(Integer personDataStatus) {
        this.personDataStatus = personDataStatus;
    }

    public Integer getFtype() {
        return ftype;
    }

    public void setFtype(Integer ftype) {
        this.ftype = ftype;
    }

    public Integer getFvalid() {
        return fvalid;
    }

    public void setFvalid(Integer fvalid) {
        this.fvalid = fvalid;
    }

    @JsonIgnore
    public void putInPersonData(Boolean put) {
        if (put){
            this.personDataStatus = 2;// 已录入
        } else {
            this.personDataStatus = 1;// 未录入
        }
    }
}