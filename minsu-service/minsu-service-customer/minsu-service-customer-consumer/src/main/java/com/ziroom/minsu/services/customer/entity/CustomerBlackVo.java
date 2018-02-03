package com.ziroom.minsu.services.customer.entity;

import com.ziroom.minsu.entity.customer.CustomerBlackEntity;

/**
 * <p>TODO</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on on 2017/1/6.
 * @version 1.0
 * @since 1.0
 */
public class CustomerBlackVo  extends CustomerBlackEntity {


    private static final long serialVersionUID = 8323432194535L;


    /**
     * 用户真实姓名
     */
    private String realName;

    /**
     * 用户电话
     */
    private String customerMobile;


    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getCustomerMobile() {
        return customerMobile;
    }

    public void setCustomerMobile(String customerMobile) {
        this.customerMobile = customerMobile;
    }
}
