package com.ziroom.zrp.trading.entity;

import com.asura.framework.base.entity.BaseEntity;

import java.util.Date;
/**
 * 这个类是系统服务的.
 * 管家分机号实体类
 * @author cuiyuhui
 * @created
 * @param
 * @return
 */
public class BindPhoneEntity extends BaseEntity{
    /**
     * 主键
     */
    private Integer id;

    /**
     * 业务id
     */
    private String fid;

    /**
     * 400电话
     */
    private Integer fourooTel;

    /**
     * 创建时间
     */
    private Date creatTime;

    /**
     * 修改时间
     */
    private Date lastModifyTime;

    /**
     * 1删除0未删除
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

    public Integer getFourooTel() {
        return fourooTel;
    }

    public void setFourooTel(Integer fourooTel) {
        this.fourooTel = fourooTel;
    }

    public Date getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(Date creatTime) {
        this.creatTime = creatTime;
    }

    public Date getLastModifyTime() {
        return lastModifyTime;
    }

    public void setLastModifyTime(Date lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }
}