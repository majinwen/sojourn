package com.ziroom.minsu.report.report.entity;

import java.util.Date;

import com.asura.framework.base.entity.BaseEntity;

public class ReportLogEntity extends BaseEntity{
   
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     *自增id
     */
    private Integer id;

    /**
     *逻辑fid
     */
    private String fid;

    /**
     *日志类型
     */
    private Integer type;

    /**
     *日志内容
     */
    private String content;

    /**
     *统计的是哪一天的日志
     */
    private Date statisticsDate;

    /**
     *创建日期
     */
    private Date createDate;

    /**
     *是否删除
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

    
    public Integer getType() {
        return type;
    }

   
    public void setType(Integer type) {
        this.type = type;
    }

    
    public String getContent() {
        return content;
    }

    
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    
    public Date getStatisticsDate() {
        return statisticsDate;
    }

   
    public void setStatisticsDate(Date statisticsDate) {
        this.statisticsDate = statisticsDate;
    }

    
    public Date getCreateDate() {
        return createDate;
    }

   
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    
    public Integer getIsDel() {
        return isDel;
    }

   
    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }
}