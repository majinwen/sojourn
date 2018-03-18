package com.ziroom.zrp.trading.entity;

import java.util.Date;

import com.asura.framework.base.entity.BaseEntity;
/**
 * <p>收款单单身实体</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author xiangb
 * @version 1.0
 * @Date Created in 2017年10月16日
 * @since 1.0
 */
public class RentVoucherItemEntity extends BaseEntity{
	
	private static final long serialVersionUID = 5871370561133078850L;

	/**
     * 主键:
     */
    private Integer fid;

    /**
     * [FK]收款单ID:
     */
    private Integer voucherId;

    /**
     * [FK]合同付款计划明细ID:
     */
    private Integer paymentId;

    /**
     * [FK]费用单明细ID:
     */
    private Integer costId;

    private Integer parentItemId;

    /**
     * [FK]费用项目ID:
     */
    private Integer expenseItemId;

    /**
     * 应收日期:
     */
    private Date oughtPaymentDate;

    /**
     * 应收金额:
     */
    private Double totalAmount;

    /**
     * 实收金额:
     */
    private Double actualAmount;

    /**
     * 备注:
     */
    private String remark;

    private Integer fvalid;

    private Date fcreatetime;

    private String createrid;

    private Date fupdatetime;

    private String updaterid;

    private Integer fisdel;

    /**
     * 关联城市表
     */
    private String cityid;

    /**
     * 传AFA状态  0:未传递 1:传递中 2:已传递
     */
    private Integer fstatus;

    /**
     * AFA关联号
     */
    private String fafanum;

    /**
     * 房间id
     */
    private String roomId;

    /**
     * 应收账单的业务id
     */
    private String billFid;

    public Integer getFid() {
        return fid;
    }

    public void setFid(Integer fid) {
        this.fid = fid;
    }

    public Integer getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(Integer voucherId) {
        this.voucherId = voucherId;
    }

    public Integer getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Integer paymentId) {
        this.paymentId = paymentId;
    }

    public Integer getCostId() {
        return costId;
    }

    public void setCostId(Integer costId) {
        this.costId = costId;
    }

    public Integer getParentItemId() {
        return parentItemId;
    }

    public void setParentItemId(Integer parentItemId) {
        this.parentItemId = parentItemId;
    }

    public Integer getExpenseItemId() {
        return expenseItemId;
    }

    public void setExpenseItemId(Integer expenseItemId) {
        this.expenseItemId = expenseItemId;
    }

    public Date getOughtPaymentDate() {
        return oughtPaymentDate;
    }

    public void setOughtPaymentDate(Date oughtPaymentDate) {
        this.oughtPaymentDate = oughtPaymentDate;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Double getActualAmount() {
        return actualAmount;
    }

    public void setActualAmount(Double actualAmount) {
        this.actualAmount = actualAmount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getFvalid() {
        return fvalid;
    }

    public void setFvalid(Integer fvalid) {
        this.fvalid = fvalid;
    }

    public Date getFcreatetime() {
        return fcreatetime;
    }

    public void setFcreatetime(Date fcreatetime) {
        this.fcreatetime = fcreatetime;
    }

    public String getCreaterid() {
        return createrid;
    }

    public void setCreaterid(String createrid) {
        this.createrid = createrid == null ? null : createrid.trim();
    }

    public Date getFupdatetime() {
        return fupdatetime;
    }

    public void setFupdatetime(Date fupdatetime) {
        this.fupdatetime = fupdatetime;
    }

    public String getUpdaterid() {
        return updaterid;
    }

    public void setUpdaterid(String updaterid) {
        this.updaterid = updaterid == null ? null : updaterid.trim();
    }

    public Integer getFisdel() {
        return fisdel;
    }

    public void setFisdel(Integer fisdel) {
        this.fisdel = fisdel;
    }

    public String getCityid() {
        return cityid;
    }

    public void setCityid(String cityid) {
        this.cityid = cityid == null ? null : cityid.trim();
    }

    public Integer getFstatus() {
        return fstatus;
    }

    public void setFstatus(Integer fstatus) {
        this.fstatus = fstatus;
    }

    public String getFafanum() {
        return fafanum;
    }

    public void setFafanum(String fafanum) {
        this.fafanum = fafanum == null ? null : fafanum.trim();
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId == null ? null : roomId.trim();
    }

    public String getBillFid() {
        return billFid;
    }

    public void setBillFid(String billFid) {
        this.billFid = billFid == null ? null : billFid.trim();
    }
}