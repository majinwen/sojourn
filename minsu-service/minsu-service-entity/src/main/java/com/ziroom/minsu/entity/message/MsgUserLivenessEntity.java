package com.ziroom.minsu.entity.message;

import java.util.Date;

import com.asura.framework.base.entity.BaseEntity;

public class MsgUserLivenessEntity extends BaseEntity{
    /**
	 * 
	 */
	private static final long serialVersionUID = 3568874876703640619L;

	/**
     * 编号
     */
    private Integer id;

    /**
     * 用户id
     */
    private String uid;

    /**
     * 最后活跃时间
     */
    private Date lastLiveTime;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 最后修改时间
     */
    private Date lastModifyDate;

    /**
     * 是否删除  0=不删除  1=删除  默认0 
     */
    private Integer isDel;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid == null ? null : uid.trim();
    }

    public Date getLastLiveTime() {
        return lastLiveTime;
    }

    public void setLastLiveTime(Date lastLiveTime) {
        this.lastLiveTime = lastLiveTime;
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