package com.ziroom.minsu.entity.cms;

import com.asura.framework.base.entity.BaseEntity;

import java.util.Date;

/**
 * <p>手机号领取记录</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/8/25.
 * @version 1.0
 * @since 1.0
 */
public class CouponMobileLogEntity  extends BaseEntity{

    private static final long serialVersionUID = 3324234703L;

    /** id*/
    private Integer id;

    /** 编号 */
    private String fid;

    /** 手机号 */
    private String customerMobile;

    /** 优惠券编号 */
    private String couponSn;
    /**订单号 （目前是服务订单）*/
    private String orderSn;

    /** 活动编号 */
    private String actSn;

    /** 组编号 */
    private String groupSn;

    /** 创建时间 */
    private Date createTime;

    /** 来源 0:其他 1:m站 2:分享 3:app 4:pc */
    private Integer sourceType;


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

    public String getCustomerMobile() {
        return customerMobile;
    }

    public void setCustomerMobile(String customerMobile) {
        this.customerMobile = customerMobile;
    }

    public String getCouponSn() {
        return couponSn;
    }

    public void setCouponSn(String couponSn) {
        this.couponSn = couponSn;
    }

    public String getActSn() {
        return actSn;
    }

    public void setActSn(String actSn) {
        this.actSn = actSn;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getSourceType() {
        return sourceType;
    }

    public void setSourceType(Integer sourceType) {
        this.sourceType = sourceType;
    }

    public String getGroupSn() {
        return groupSn;
    }

    public void setGroupSn(String groupSn) {
        this.groupSn = groupSn;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }
}
