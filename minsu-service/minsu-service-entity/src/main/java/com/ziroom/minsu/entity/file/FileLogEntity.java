package com.ziroom.minsu.entity.file;

import com.asura.framework.base.entity.BaseEntity;

import java.util.Date;

/**
 * Created with IntelliJ IDEA
 * User: lunan
 * Date: 2016/11/15
 */
public class FileLogEntity  extends BaseEntity {
    /**
     * 自增id
     */
    private Integer id;

    /**
     * 逻辑fid
     */
    private String fid;

    /**
     * 城市code
     */
    private String cityCode;

    /**
     * 档案日志类型 0：城市档案，1：商圈景点档案，2：推荐项目
     */
    private Integer logType;

    /**
     * 操作类型fid
     * @Author lunan【lun14@ziroom.com】
     * @Date 2016/11/16 11:08
     */
    private String typeFid;

    /**
     * 操作人fid
     */
    private String operatorFid;

    /**
     * 操作时间
     */
    private Date operatorDate;

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

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

    public Integer getLogType() {
        return logType;
    }

    public void setLogType(Integer logType) {
        this.logType = logType;
    }

    public String getTypeFid() {
        return typeFid;
    }

    public void setTypeFid(String typeFid) {
        this.typeFid = typeFid;
    }

    public String getOperatorFid() {
        return operatorFid;
    }

    public void setOperatorFid(String operatorFid) {
        this.operatorFid = operatorFid == null ? null : operatorFid.trim();
    }

    public Date getOperatorDate() {
        return operatorDate;
    }

    public void setOperatorDate(Date operatorDate) {
        this.operatorDate = operatorDate;
    }
}
