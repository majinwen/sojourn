package com.ziroom.minsu.spider.failurls.entity;

import java.util.Date;

public class FailUrlRecordsEntity {
    /**
     * 自增id
     */
    private Integer id;

    /**
     * 失败url
     */
    private String url;

    /**
     * url类型 11：airbnb房源，21：airbnb房东，31:airbnb列表，12：小猪房源，22：小猪房东，32:小猪列表
     */
    private Integer urlType;

    /**
     * 创建日期
     */
    private Date createDate;

    /**
     * 异常详情
     */
    private String failReason;
    
    /**
     * 尝试次数
     */
    private Integer tryCount;
    
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public Integer getUrlType() {
        return urlType;
    }

    public void setUrlType(Integer urlType) {
        this.urlType = urlType;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getFailReason() {
        return failReason;
    }

    public void setFailReason(String failReason) {
        this.failReason = failReason == null ? null : failReason.trim();
    }

	public Integer getTryCount() {
		return tryCount;
	}

	public void setTryCount(Integer tryCount) {
		this.tryCount = tryCount;
	}

	public Date getLastModifyDate() {
		return lastModifyDate;
	}

	public void setLastModifyDate(Date lastModifyDate) {
		this.lastModifyDate = lastModifyDate;
	}
	
}