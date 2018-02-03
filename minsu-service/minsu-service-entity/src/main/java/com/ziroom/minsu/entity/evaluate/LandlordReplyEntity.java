package com.ziroom.minsu.entity.evaluate;

import com.asura.framework.base.entity.BaseEntity;

import java.util.Date;

public class LandlordReplyEntity extends BaseEntity{
    private static final long serialVersionUID = -7777916510983056027L;
    /**
     * 主键
     */
    private Integer id;

    /**
     * 逻辑主键
     */
    private String fid;

    /**
     * 评价fid
     */
    private String evaOrderFid;

    /**
     * 评价内容
     */
    private String content;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 最后更新时间
     */
    private Date lastModifyDate;

    /**
     * 是否删除：0=不删除 1=删除
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

    public String getEvaOrderFid() {
        return evaOrderFid;
    }

    public void setEvaOrderFid(String evaOrderFid) {
        this.evaOrderFid = evaOrderFid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
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