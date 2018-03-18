package com.zra.evaluate.entity.mapper;

import java.util.Date;

/**
 * Author: wangxm113
 * CreateDate: 2017/3/14.
 */
public class ZoEvaluationItemEntity {
    private String id;//'评价子标签id',
    private String zoEvaId;//'评价单头id',
    private String labelId;//'标签id',
    private String createrId;//'评价人ID',
    private Date createTime;//'创建时间',
    private String updaterId;//'修改者ID',
    private Date updateTime;//'修改时间',
    private String valid;//'有效 1是0否',
    private String isDel;//'是否删除',
    private String cityId;//'城市Id'

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getZoEvaId() {
        return zoEvaId;
    }

    public void setZoEvaId(String zoEvaId) {
        this.zoEvaId = zoEvaId;
    }

    public String getLabelId() {
        return labelId;
    }

    public void setLabelId(String labelId) {
        this.labelId = labelId;
    }

    public String getCreaterId() {
        return createrId;
    }

    public void setCreaterId(String createrId) {
        this.createrId = createrId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdaterId() {
        return updaterId;
    }

    public void setUpdaterId(String updaterId) {
        this.updaterId = updaterId;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getValid() {
        return valid;
    }

    public void setValid(String valid) {
        this.valid = valid;
    }

    public String getIsDel() {
        return isDel;
    }

    public void setIsDel(String isDel) {
        this.isDel = isDel;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }
}
