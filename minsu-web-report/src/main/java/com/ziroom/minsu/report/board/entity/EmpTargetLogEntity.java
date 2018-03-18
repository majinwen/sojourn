package com.ziroom.minsu.report.board.entity;

import com.asura.framework.base.entity.BaseEntity;

import java.util.Date;

public class EmpTargetLogEntity extends BaseEntity{
    private static final long serialVersionUID = -106708127996298667L;
    /**
     * 主键id
     */
    private Integer id;

    /**
     * 关联编号
     */
    private String targetFid;

    /**
     * 名称
     */
    private Integer targetHouseNum;

    /**
     * 员工编号
     */
    private String createEmpCode;

    /**
     * 员工名字
     */
    private String createEmpName;

    /**
     * 创建时间
     */
    private Date createDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTargetFid() {
        return targetFid;
    }

    public void setTargetFid(String targetFid) {
        this.targetFid = targetFid == null ? null : targetFid.trim();
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
}