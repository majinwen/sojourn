package com.ziroom.minsu.services.order.entity;

import com.ziroom.minsu.entity.customer.CustomerBaseMsgEntity;
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
 * @author afi on 2016/6/23.
 * @version 1.0
 * @since 1.0
 */
public class CustomerRoleVo extends CustomerBaseMsgEntity {

    /**
     * 当前用户的角色信息
     */
    private List<CustomerRoleEntity> roles;

    public List<CustomerRoleEntity> getRoles() {
        return roles;
    }

    public void setRoles(List<CustomerRoleEntity> roles) {
        this.roles = roles;
    }
}
