package com.ziroom.minsu.entity.order;

import com.asura.framework.base.entity.BaseEntity;


/**
 * <p>订单配置项实体类</p>
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
public class OrderConfigEntity extends BaseEntity {


    /** 序列化id  */
    private static final long serialVersionUID = -456478928264L;


    /**  */
    private Integer id;

    /** 订单编号 */
    private String orderSn;

    /** 配置编码 */
    private String configCode;

    /** 配置值 */
    private String configValue;

    /** 是否有效 */
    private Integer isValid = 1;

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

    public String getConfigCode() {
        return configCode;
    }

    public void setConfigCode(String configCode) {
        this.configCode = configCode;
    }

    public String getConfigValue() {
        return configValue;
    }

    public void setConfigValue(String configValue) {
        this.configValue = configValue;
    }

    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }
}
