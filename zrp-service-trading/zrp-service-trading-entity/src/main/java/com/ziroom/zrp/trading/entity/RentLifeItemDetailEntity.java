package com.ziroom.zrp.trading.entity;

import com.asura.framework.base.entity.BaseEntity;

import java.util.Date;
/**
 * <p>生活费用项</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd
 * @version 1.0
 * @Date Created in 2017年9月15日
 * @since 1.0
 */
public class RentLifeItemDetailEntity extends BaseEntity{
    private static final long serialVersionUID = 7866850694956327583L;
    /**
     * id，默认自增
     */
    private String id;

    /**
     * 生活费用记录bid
     */
    private String lifeitemBid;

    /**
     * 合同id
     */
    private String contractId;

    /**
     * 费用id
     */
    private String expenseitemId;

    /**
     * 房间id
     */
    private String roomId;

    /**
     * 充值金额
     */
    private Double paymentAmount;

    /**
     * 是否删除，默认0不删除，1删除
     */
    private Integer isDeleted;

    /**
     * 删除者id
     */
    private String deleterId;

    /**
     * 删除时间
     */
    private Date deletedTime;

    /**
     * 创建者id
     */
    private String createrId;

    /**
     * 创建时间
     */
    private Date createdTime;

    /**
     * 更新者id
     */
    private String updaterId;

    private Integer fvalid;

    /**
     * 更新时间
     */
    private Date updatedTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getLifeitemBid() {
        return lifeitemBid;
    }

    public void setLifeitemBid(String lifeitemBid) {
        this.lifeitemBid = lifeitemBid == null ? null : lifeitemBid.trim();
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId == null ? null : contractId.trim();
    }

    public String getExpenseitemId() {
        return expenseitemId;
    }

    public void setExpenseitemId(String expenseitemId) {
        this.expenseitemId = expenseitemId == null ? null : expenseitemId.trim();
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId == null ? null : roomId.trim();
    }

    public Double getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(Double paymentAmount) {
        this.paymentAmount = paymentAmount;
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

    public Integer getFvalid() {
        return fvalid;
    }

    public void setFvalid(Integer fvalid) {
        this.fvalid = fvalid;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }
}