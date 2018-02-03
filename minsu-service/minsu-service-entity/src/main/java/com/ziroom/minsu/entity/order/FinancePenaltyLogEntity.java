package com.ziroom.minsu.entity.order;

import com.asura.framework.base.entity.BaseEntity;

import java.util.Date;
/**
 * 罚款单日志表
 * @author jixd
 * @created 2017年05月10日 15:03:14
 * @param
 * @return
 */
public class FinancePenaltyLogEntity extends BaseEntity{
    private static final long serialVersionUID = -1329634706168255680L;
    /**
     * 主键
     */
    private Integer id;

    /**
     * 逻辑主键
     */
    private String fid;

    /**
     * 罚款单编号
     */
    private String penaltySn;

    /**
     * 之前状态
     */
    private Integer fromStatus;

    /**
     * 之后状态
     */
    private Integer toStatus;

    /**
     * 之前金额
     */
    private Integer fromFee;

    /**
     * 之后金额
     */
    private Integer toFee;

    /**
     * 罚款状态变化备注
     */
    private String remark;

    /**
     * 创建人名字
     */
    private String empName;

    /**
     * 创建人编号
     */
    private String empCode;

    /**
     * 创建时间
     */
    private Date createTime;

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

    public String getPenaltySn() {
        return penaltySn;
    }

    public void setPenaltySn(String penaltySn) {
        this.penaltySn = penaltySn == null ? null : penaltySn.trim();
    }

    public Integer getFromStatus() {
        return fromStatus;
    }

    public void setFromStatus(Integer fromStatus) {
        this.fromStatus = fromStatus;
    }

    public Integer getToStatus() {
        return toStatus;
    }

    public void setToStatus(Integer toStatus) {
        this.toStatus = toStatus;
    }

    public Integer getFromFee() {
        return fromFee;
    }

    public void setFromFee(Integer fromFee) {
        this.fromFee = fromFee;
    }

    public Integer getToFee() {
        return toFee;
    }

    public void setToFee(Integer toFee) {
        this.toFee = toFee;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName == null ? null : empName.trim();
    }

    public String getEmpCode() {
        return empCode;
    }

    public void setEmpCode(String empCode) {
        this.empCode = empCode == null ? null : empCode.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "FinancePenaltyLogEntity{" +
                "id=" + id +
                ", fid='" + fid + '\'' +
                ", penaltySn='" + penaltySn + '\'' +
                ", fromStatus=" + fromStatus +
                ", toStatus=" + toStatus +
                ", fromFee=" + fromFee +
                ", toFee=" + toFee +
                ", remark='" + remark + '\'' +
                ", empName='" + empName + '\'' +
                ", empCode='" + empCode + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}