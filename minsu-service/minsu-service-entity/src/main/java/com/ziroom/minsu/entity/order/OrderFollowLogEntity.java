package com.ziroom.minsu.entity.order;

import com.asura.framework.base.entity.BaseEntity;

import java.util.Date;

/**
 * <p>根进log</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on on 2016/12/14.
 * @version 1.0
 * @since 1.0
 */
public class OrderFollowLogEntity extends BaseEntity {


    private static final long serialVersionUID = 3096908001446703L;

    /**
     * 主键id
     */
    private Integer id;

    /**
     * 逻辑id
     */
    private String fid;

    /**
     * 跟进主表fid
     */
    private String followFid;

    /**
     * 订单sn
     */
    private String orderSn;

    /**
     * 订单状态
     */
    private Integer orderStatus;

    /**
     * 跟进状态
     * @see com.ziroom.minsu.valenum.order.FollowStatusEnum
     *
     */
    private Integer followStatus;

    /**
     * 跟进记录
     */
    private String content;

    /**
     * 创建人
     */
    private String createFid;

    /**
     * 创建人名称
     */
    private String createName;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 是否删除 0：否，1：是 默认0 不删除 1：删除
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
        this.fid = fid;
    }

    public String getFollowFid() {
        return followFid;
    }

    public void setFollowFid(String followFid) {
        this.followFid = followFid;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Integer getFollowStatus() {
        return followStatus;
    }

    public void setFollowStatus(Integer followStatus) {
        this.followStatus = followStatus;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreateFid() {
        return createFid;
    }

    public void setCreateFid(String createFid) {
        this.createFid = createFid;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }
}
