package com.ziroom.minsu.entity.photographer;

import java.util.Date;

import com.asura.framework.base.entity.BaseEntity;

/**
 * 
 * <p>摄影师日志实体</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author yd
 * @since 1.0
 * @version 1.0
 */
public class PhotographerBookLogEntity extends BaseEntity{
    /**
	 * 序列id
	 */
	private static final long serialVersionUID = 4998572957900733899L;

	/**
     * 编号
     */
    private Integer id;

    /**
     * 业务编号
     */
    private String fid;

    /**
     * 预约摄影师订单编号
     */
    private String bookOrderSn;

    /**
     * 预约状态更改之前状态
     */
    private Integer fromStatu;

    /**
     * 预约订单状态更改之后状态
     */
    private Integer toStatu;

    /**
     * 创建人fid
     */
    private String createrFid;

    /**
     * 创建人类型（1=民宿管家）
     */
    private Integer createrType;

    /**
     * 是否删除(0=不删除 1=删除)
     */
    private Integer isDel;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 修改时间
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

    public String getBookOrderSn() {
        return bookOrderSn;
    }

    public void setBookOrderSn(String bookOrderSn) {
        this.bookOrderSn = bookOrderSn == null ? null : bookOrderSn.trim();
    }

    public Integer getFromStatu() {
        return fromStatu;
    }

    public void setFromStatu(Integer fromStatu) {
        this.fromStatu = fromStatu;
    }

    public Integer getToStatu() {
        return toStatu;
    }

    public void setToStatu(Integer toStatu) {
        this.toStatu = toStatu;
    }

    public String getCreaterFid() {
        return createrFid;
    }

    public void setCreaterFid(String createrFid) {
        this.createrFid = createrFid == null ? null : createrFid.trim();
    }

    public Integer getCreaterType() {
        return createrType;
    }

    public void setCreaterType(Integer createrType) {
        this.createrType = createrType;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
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
}