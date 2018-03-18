package com.ziroom.zrp.trading.entity;

import java.util.Date;

import com.asura.framework.base.entity.BaseEntity;

/**
 * 
 * <p>水费应收账单生成记录</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author bushujie
 * @since 1.0
 * @version 1.0
 */
public class IntellectWaterMeterBillLogEntity extends BaseEntity {
    /**
	 * 
	 */
	private static final long serialVersionUID = 8274711826228098843L;

	/**
     * 主键
     */
    private Integer id;

    /**
     * 业务标识
     */
    private String fid;

    /**
     * 应收账单fid
     */
    private String billFid;

    /**
     * 开始计算账单的示数
     */
    private Double startReading;

    /**
     * 计算账单的当前示数
     */
    private Double endReading;

    /**
     * 个人使用示数
     */
    private Double useReading;
    
    /**
     * 最后一个清算单的分摊因子（规则是产品定的）
     */
    private Integer shareFactor;

	/**
     * 账单周期开始时间
     */
    private Date startDate;

    /**
     * 账单周期结束时间
     */
    private Date endDate;

    /**
     * 单价 (单位：分)
     */
    private Integer price;

    /**
     * 创建类型   0=系统定时创建 1=管家 2=解约触发创建
     */
    private Integer type;

    /**
     * 创建人id  客户uid/如果是管家 存员工号
     */
    private String createId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 是否删除  0=不删除  1=删除
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

    public String getBillFid() {
        return billFid;
    }

    public void setBillFid(String billFid) {
        this.billFid = billFid == null ? null : billFid.trim();
    }

    public Double getStartReading() {
        return startReading;
    }

    public void setStartReading(Double startReading) {
        this.startReading = startReading;
    }

    public Double getEndReading() {
        return endReading;
    }

    public void setEndReading(Double endReading) {
        this.endReading = endReading;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getCreateId() {
        return createId;
    }

    public void setCreateId(String createId) {
        this.createId = createId == null ? null : createId.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    public Double getUseReading() {
        return useReading;
    }

    public void setUseReading(Double useReading) {
        this.useReading = useReading;
    }
    
    /**
   	 * @return the shareFactor
   	 */
   	public Integer getShareFactor() {
   		return shareFactor;
   	}

   	/**
   	 * @param shareFactor the shareFactor to set
   	 */
   	public void setShareFactor(Integer shareFactor) {
   		this.shareFactor = shareFactor;
   	}
}