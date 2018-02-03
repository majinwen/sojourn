package com.ziroom.minsu.services.customer.dao;

import com.asura.framework.base.entity.BaseEntity;
import org.apache.solr.client.solrj.beans.Field;

import java.util.Date;

/**
 * <p>用户的视图</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/5/5.
 * @version 1.0
 * @since 1.0
 */
public class CustomerDbInfoVo1 extends BaseEntity {

    /**
     * 序列化
     */
    private static final long serialVersionUID = -525645645623114545L;

    @Field
    private String realName;

    @Field
    private String customerMobile;

    @Field
    private Date createDate;

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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
