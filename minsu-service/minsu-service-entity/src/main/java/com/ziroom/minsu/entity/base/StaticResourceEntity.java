package com.ziroom.minsu.entity.base;

import java.util.Date;

import com.asura.framework.base.entity.BaseEntity;

public class StaticResourceEntity extends BaseEntity{
    /**
	 * 序列化id
	 */
	private static final long serialVersionUID = 706357224432523989L;

	/**
     * 自增id
     */
    private Integer id;

    /**
     * 逻辑fid
     */
    private String fid;

    /**
     * 资源标题
     */
    private String resTitle;

    /**
     * 资源code
     */
    private String resCode;
    /**
     * 父code
     */
    private String parentCode;

    /**
     * 资源类型 1：文本，2：图片
     */
    private Integer resType;
    
    /**
     * 资源内容
     */
    private String resContent;

    /**
     * 资源备注
     */
    private String resRemark;
    /**
     * 排序编号
     */
    private Integer orderCode;

    /**
     * 创建人fid
     */
    private String createFid;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 最后更新时间
     */
    private Date lastModifyDate;

    /**
     * 是否删除 0:否，1：是
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

    public String getResTitle() {
        return resTitle;
    }

    public void setResTitle(String resTitle) {
        this.resTitle = resTitle == null ? null : resTitle.trim();
    }

    public String getResCode() {
        return resCode;
    }

    public void setResCode(String resCode) {
        this.resCode = resCode == null ? null : resCode.trim();
    }

    public Integer getResType() {
        return resType;
    }

    public void setResType(Integer resType) {
        this.resType = resType;
    }
    
    public String getResContent() {
    	return resContent;
    }
    
    public void setResContent(String resContent) {
    	this.resContent = resContent == null ? null : resContent.trim();
    }

    public String getResRemark() {
        return resRemark;
    }

    public void setResRemark(String resRemark) {
        this.resRemark = resRemark == null ? null : resRemark.trim();
    }

    public String getCreateFid() {
        return createFid;
    }

    public void setCreateFid(String createFid) {
        this.createFid = createFid == null ? null : createFid.trim();
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

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public Integer getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(Integer orderCode) {
        this.orderCode = orderCode;
    }
}