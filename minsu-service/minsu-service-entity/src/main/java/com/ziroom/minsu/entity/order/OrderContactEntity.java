package com.ziroom.minsu.entity.order;

import com.asura.framework.base.entity.BaseEntity;

/**
 * <p>订单入住人关系表</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/3/31.
 * @version 1.0
 * @since 1.0
 */
public class OrderContactEntity extends BaseEntity {

    /** 序列化id  */
    private static final long serialVersionUID = -256312937998L;


    /** id */
    private Integer id;

    /** 订单编号 */
    private String orderSn;

    /** 联系人fid */
    private String contactFid;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public String getContactFid() {
        return contactFid;
    }

    public void setContactFid(String contactFid) {
        this.contactFid = contactFid;
    }
}
