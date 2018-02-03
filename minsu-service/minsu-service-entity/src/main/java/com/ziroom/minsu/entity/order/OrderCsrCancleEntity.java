package com.ziroom.minsu.entity.order;

import com.asura.framework.base.entity.BaseEntity;

import java.util.Date;
/**
 * 客服取消订单记录
 * @author jixd
 * @created 2017年05月10日 10:41:41
 * @param
 * @return
 */
public class OrderCsrCancleEntity extends BaseEntity{
    private static final long serialVersionUID = 3245546642877255547L;
    /**
     * 编号
     */
    private Integer id;

    /**
     * 业务编号
     */
    private String fid;

    /**
     * 订单编号
     */
    private String orderSn;

    /**
     * 取消类型 37=协商取消 38=房东申请取消
     */
    private Integer cancleType;

    /**
     * 取消原因 0=其他  10=房源维修升级 20=日历冲突 
     */
    private Integer cancleReason;

    /**
     * 取消备注
     */
    private String remark;

    /**
     * 员工编号 （操作人）
     */
    private String empCode;

    /**
     * 员工姓名
     */
    private String empName;

    /**
     * 处罚结果的总状态 10=处理未完成  11=处理完成 备注： 处罚的各项状态在订单配置表中
     */
    private Integer punishStatu;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date lastModifyDate;

    /**
     * 是否删除  0：否，1：是
     */
    private Integer isDel;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid == null ? null : fid.trim();
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn == null ? null : orderSn.trim();
    }

    public Integer getCancleType() {
        return cancleType;
    }

    public void setCancleType(Integer cancleType) {
        this.cancleType = cancleType;
    }

    public Integer getCancleReason() {
        return cancleReason;
    }

    public void setCancleReason(Integer cancleReason) {
        this.cancleReason = cancleReason;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getEmpCode() {
        return empCode;
    }

    public void setEmpCode(String empCode) {
        this.empCode = empCode == null ? null : empCode.trim();
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName == null ? null : empName.trim();
    }

    public Integer getPunishStatu() {
        return punishStatu;
    }

    public void setPunishStatu(Integer punishStatu) {
        this.punishStatu = punishStatu;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastModifyDate() {
        return lastModifyDate;
    }

    public void setLastModifyDate(Date lastModifyDate) {
        this.lastModifyDate = lastModifyDate;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    @Override
    public String toString() {
        return "OrderCsrCancleEntity{" +
                "id=" + id +
                ", fid='" + fid + '\'' +
                ", orderSn='" + orderSn + '\'' +
                ", cancleType=" + cancleType +
                ", cancleReason=" + cancleReason +
                ", remark='" + remark + '\'' +
                ", empCode='" + empCode + '\'' +
                ", empName='" + empName + '\'' +
                ", punishStatu=" + punishStatu +
                ", createTime=" + createTime +
                ", lastModifyDate=" + lastModifyDate +
                ", isDel=" + isDel +
                '}';
    }
}