package com.zra.house.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;

/**
 * 价格范围实体
 * @author tianxf9
 *
 */
@ApiModel(value="")
public class PriceRangeEntity {
    /**
     * 主键
     * 表字段 : price_range.id
     * 
     */
    @ApiModelProperty(value="主键")
    private Integer id;

    /**
     * 业务id
     * 表字段 : price_range.price_range_bid
     * 
     */
    @ApiModelProperty(value="业务id")
    private String priceRangeBid;

    /**
     * 价格范围描述
     * 表字段 : price_range.range_describe
     * 
     */
    @ApiModelProperty(value="价格范围描述")
    private String rangeDescribe;

    /**
     * 价格范围-最小价格
     * 表字段 : price_range.min_price
     * 
     */
    @ApiModelProperty(value="价格范围-最小价格")
    private Short minPrice;

    /**
     * 价格范围-最大价格
     * 表字段 : price_range.max_price
     * 
     */
    @ApiModelProperty(value="价格范围-最大价格")
    private Short maxPrice;

    /**
     * 是否删除   0：未删除；1：删除
     * 表字段 : price_range.is_del
     * 
     */
    @ApiModelProperty(value="是否删除   0：未删除；1：删除")
    private Boolean isDel;

    /**
     * 创建时间
     * 表字段 : price_range.create_time
     * 
     */
    @ApiModelProperty(value="创建时间")
    private Date createTime;

    /**
     * 删除时间
     * 表字段 : price_range.delete_time
     * 
     */
    @ApiModelProperty(value="删除时间")
    private Date deleteTime;

    /**
     * 更新时间
     * 表字段 : price_range.update_time
     * 
     */
    @ApiModelProperty(value="更新时间")
    private Date updateTime;

    /**
     * 创建人id(employee的fid)
     * 表字段 : price_range.create_id
     * 
     */
    @ApiModelProperty(value="创建人id(employee的fid)")
    private String createId;

    /**
     * 更新人
     * 表字段 : price_range.update_id
     * 
     */
    @ApiModelProperty(value="更新人")
    private String updateId;

    /**
     * 删除人
     * 表字段 : price_range.delete_id
     * 
     */
    @ApiModelProperty(value="删除人")
    private String deleteId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPriceRangeBid() {
        return priceRangeBid;
    }

    public void setPriceRangeBid(String priceRangeBid) {
        this.priceRangeBid = priceRangeBid == null ? null : priceRangeBid.trim();
    }

    public String getRangeDescribe() {
        return rangeDescribe;
    }

    public void setRangeDescribe(String rangeDescribe) {
        this.rangeDescribe = rangeDescribe == null ? null : rangeDescribe.trim();
    }

    public Short getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Short minPrice) {
        this.minPrice = minPrice;
    }

    public Short getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(Short maxPrice) {
        this.maxPrice = maxPrice;
    }

    public Boolean getIsDel() {
        return isDel;
    }

    public void setIsDel(Boolean isDel) {
        this.isDel = isDel;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getDeleteTime() {
        return deleteTime;
    }

    public void setDeleteTime(Date deleteTime) {
        this.deleteTime = deleteTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreateId() {
        return createId;
    }

    public void setCreateId(String createId) {
        this.createId = createId == null ? null : createId.trim();
    }

    public String getUpdateId() {
        return updateId;
    }

    public void setUpdateId(String updateId) {
        this.updateId = updateId == null ? null : updateId.trim();
    }

    public String getDeleteId() {
        return deleteId;
    }

    public void setDeleteId(String deleteId) {
        this.deleteId = deleteId == null ? null : deleteId.trim();
    }
}