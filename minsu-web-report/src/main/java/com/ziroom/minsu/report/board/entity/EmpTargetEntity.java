package com.ziroom.minsu.report.board.entity;

import com.asura.framework.base.entity.BaseEntity;

import java.util.Date;

public class EmpTargetEntity extends BaseEntity{
    private static final long serialVersionUID = 2294443764888258096L;
    /**
     * 主键id
     */
    private Integer id;

    /**
     * 逻辑id
     */
    private String fid;

    /**
     * 目标月份
     */
    private String targetMonth;

    /**
     * 员工编号
     */
    private String empCode;

    /**
     * 员工名字
     */
    private String empName;

    /**
     * 房源上架目标
     */
    private Integer targetHouseNum;

    /**
     * 创建人员工号
     */
    private String createEmpCode;

    /**
     * 创建人名字
     */
    private String createEmpName;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 最后修改时间
     */
    private Date lastModifyDate;

    /**
     * 是否删除 0=未删除  1=删除
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

    public String getTargetMonth() {
        return targetMonth;
    }

    public void setTargetMonth(String targetMonth) {
        this.targetMonth = targetMonth;
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

    public Integer getTargetHouseNum() {
        return targetHouseNum;
    }

    public void setTargetHouseNum(Integer targetHouseNum) {
        this.targetHouseNum = targetHouseNum;
    }

    public String getCreateEmpCode() {
        return createEmpCode;
    }

    public void setCreateEmpCode(String createEmpCode) {
        this.createEmpCode = createEmpCode == null ? null : createEmpCode.trim();
    }

    public String getCreateEmpName() {
        return createEmpName;
    }

    public void setCreateEmpName(String createEmpName) {
        this.createEmpName = createEmpName == null ? null : createEmpName.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
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
}