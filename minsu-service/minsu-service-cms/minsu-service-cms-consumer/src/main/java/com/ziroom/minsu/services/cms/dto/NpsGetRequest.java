package com.ziroom.minsu.services.cms.dto;

import com.asura.framework.base.entity.BaseEntity;

/**
 * <p></p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author lishaochuan on 2016/11/11 15:41
 * @version 1.0
 * @since 1.0
 */
public class NpsGetRequest extends BaseEntity{

    private static final long serialVersionUID = 928349213001446703L;

    String uid;

    Integer userType;

    String npsCode;

    private String orderSn;

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public String getNpsCode() {
        return npsCode;
    }

    public void setNpsCode(String npsCode) {
        this.npsCode = npsCode;
    }
}
