package com.ziroom.minsu.report.board.entity;

import com.asura.framework.base.entity.BaseEntity;

import java.util.Date;

public class CityTargetLogEntity extends BaseEntity{
    private static final long serialVersionUID = -5876545735383755882L;
    /**
     * 主键id
     */
    private Integer id;

    /**
     * 关联编号
     */
    private String targetFid;

    /**
     * 房源上架目标
     */
    private Integer targetHouseNum;

    /**
     * 地推上架目标
     */
    private Integer targetPushHouseNum;

    /**
     * 房源自主上架目标
     */
    private Integer targetSelfHouseNum;

    /**
     * 订单目标
     */
    private Integer targetOrderNum;

    /**
     * 出租间夜目标
     */
    private Integer targetRentNum;

    /**
     * 创建人员code
     */
    private String createEmpCode;

    /**
     * 创建人员名字
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
        this.targetFid = targetFid;
    }

    public Integer getTargetHouseNum() {
        return targetHouseNum;
    }

    public void setTargetHouseNum(Integer targetHouseNum) {
        this.targetHouseNum = targetHouseNum;
    }

    public Integer getTargetPushHouseNum() {
        return targetPushHouseNum;
    }

    public void setTargetPushHouseNum(Integer targetPushHouseNum) {
        this.targetPushHouseNum = targetPushHouseNum;
    }

    public Integer getTargetSelfHouseNum() {
        return targetSelfHouseNum;
    }

    public void setTargetSelfHouseNum(Integer targetSelfHouseNum) {
        this.targetSelfHouseNum = targetSelfHouseNum;
    }

    public Integer getTargetOrderNum() {
        return targetOrderNum;
    }

    public void setTargetOrderNum(Integer targetOrderNum) {
        this.targetOrderNum = targetOrderNum;
    }

    public Integer getTargetRentNum() {
        return targetRentNum;
    }

    public void setTargetRentNum(Integer targetRentNum) {
        this.targetRentNum = targetRentNum;
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