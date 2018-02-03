package com.ziroom.minsu.services.cms.dto;

import com.asura.framework.base.entity.BaseEntity;

/**
 * <p>根据手机号绑定优惠券参数</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author lishaochuan on 2016/10/11 11:20
 * @version 1.0
 * @since 1.0
 */
public class BindCouponMobileRequest extends BaseEntity {

    /**
     * 序列化ID
     */
    private static final long serialVersionUID = 2356563798714449346L;

    private String uid;

    private String mobile;


    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
