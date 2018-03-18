package com.zra.evaluate.entity;

import java.util.Date;

/**
 * Author: wangxm113
 * CreateDate: 2017/3/14.
 */
public class ZoEvaluationEntity {
    private String id;//'评价表单头id'
    private String projectZoId;//'projectzo id'
    private String projectId;//'项目id'
    private String evaContent;//'评价内容'
    private String cusName;//'客户姓名',
    private String cusPhone;//'客户手机号',
    private String createrId;//'评价人id',
    private Date createTime;//'创建时间',
    private String updaterId;//'修改人id',
    private Date updateTime;//'修改时间',
    private String valid = "1";//'是否有效 1是 0否',
    private String isdel = "0";//'是否删除 1是 0否',
    private String cityId;//'城市(city)Id',

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProjectZoId() {
        return projectZoId;
    }

    public void setProjectZoId(String projectZoId) {
        this.projectZoId = projectZoId;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getEvaContent() {
        return evaContent;
    }

    public void setEvaContent(String evaContent) {
        this.evaContent = evaContent;
    }

    public String getCusName() {
        return cusName;
    }

    public void setCusName(String cusName) {
        this.cusName = cusName;
    }

    public String getCusPhone() {
        return cusPhone;
    }

    public void setCusPhone(String cusPhone) {
        this.cusPhone = cusPhone;
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

    public String getIsdel() {
        return isdel;
    }

    public void setIsdel(String isdel) {
        this.isdel = isdel;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }
}
