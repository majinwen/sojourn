package com.ziroom.minsu.services.cms.dto;

import com.ziroom.minsu.entity.customer.CustomerRoleEntity;

import java.util.List;

/**
 * <p>TODO</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on on 16/10/11.
 * @version 1.0
 * @since 1.0
 */
public class BindActivityRequest  extends BindCouponRequest {


    /**
     * 序列化ID
     */
    private static final long serialVersionUID = 2175946071448453607L;


    /** 组 */
    private String recordSn;

    /**
     * 当前的角色
     */
    private List<CustomerRoleEntity> roles;


    public String getRecordSn() {
        return recordSn;
    }

    public void setRecordSn(String recordSn) {
        this.recordSn = recordSn;
    }

    public List<CustomerRoleEntity> getRoles() {
        return roles;
    }

    public void setRoles(List<CustomerRoleEntity> roles) {
        this.roles = roles;
    }
}
