package com.ziroom.minsu.entity.evaluate;

import com.asura.framework.base.entity.BaseEntity;

import java.util.Date;

public class EvaluateShowEntity extends BaseEntity{
    private static final long serialVersionUID = -7718790789242066154L;
    /**
     * 编号
     */
    private Integer id;

    /**
     * 关联编号
     */
    private String evaOrderFid;

    /**
     * 是否删除 0=否 1=是
     */
    private Integer isDel;
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

    public String getEvaOrderFid() {
        return evaOrderFid;
    }

    public void setEvaOrderFid(String evaOrderFid) {
        this.evaOrderFid = evaOrderFid == null ? null : evaOrderFid.trim();
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}