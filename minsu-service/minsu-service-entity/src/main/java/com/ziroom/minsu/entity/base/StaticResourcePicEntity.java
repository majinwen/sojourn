package com.ziroom.minsu.entity.base;

import java.util.Date;

import com.asura.framework.base.entity.BaseEntity;

public class StaticResourcePicEntity extends BaseEntity{
    /**
	 * 序列化id
	 */
	private static final long serialVersionUID = 1685151664008670776L;

	/**
     * 自增id
     */
    private Integer id;

    /**
     * 逻辑fid
     */
    private String fid;

    /**
     * 静态资源fid
     */
    private String resFid;

    /**
     * 图片房源基础地址
     */
    private String picBaseUrl;

    /**
     * 图片后缀
     */
    private String picSuffix;

    /**
     * 图片服务器唯一标示
     */
    private String picServerUuid;

    /**
     * 最后更新时间
     */
    private Date lastModifyDate;

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

    public String getResFid() {
        return resFid;
    }

    public void setResFid(String resFid) {
        this.resFid = resFid == null ? null : resFid.trim();
    }

    public String getPicBaseUrl() {
        return picBaseUrl;
    }

    public void setPicBaseUrl(String picBaseUrl) {
        this.picBaseUrl = picBaseUrl == null ? null : picBaseUrl.trim();
    }

    public String getPicSuffix() {
        return picSuffix;
    }

    public void setPicSuffix(String picSuffix) {
        this.picSuffix = picSuffix == null ? null : picSuffix.trim();
    }

    public String getPicServerUuid() {
        return picServerUuid;
    }

    public void setPicServerUuid(String picServerUuid) {
        this.picServerUuid = picServerUuid == null ? null : picServerUuid.trim();
    }

    public Date getLastModifyDate() {
        return lastModifyDate;
    }

    public void setLastModifyDate(Date lastModifyDate) {
        this.lastModifyDate = lastModifyDate;
    }
}