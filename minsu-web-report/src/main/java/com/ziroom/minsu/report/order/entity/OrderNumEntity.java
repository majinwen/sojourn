package com.ziroom.minsu.report.order.entity;

import com.asura.framework.base.entity.BaseEntity;

/**
 * <p>订饭统计</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on on 2017/2/14.
 * @version 1.0
 * @since 1.0
 */
public class OrderNumEntity extends BaseEntity{

    private static final long serialVersionUID = 3024236703L;

    /**
     * 城市code
     */
    private String cityCode;

    /**
     * 申请数量
     */
    private Integer applyNum = 0;


    /**
     * 支付数量
     */
    private Integer payNum = 0;

    /**
     * 接受数量
     */
    private Integer acceptNum = 0;


    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public Integer getApplyNum() {
        return applyNum;
    }

    public void setApplyNum(Integer applyNum) {
        this.applyNum = applyNum;
    }

    public Integer getPayNum() {
        return payNum;
    }

    public void setPayNum(Integer payNum) {
        this.payNum = payNum;
    }

    public Integer getAcceptNum() {
        return acceptNum;
    }

    public void setAcceptNum(Integer acceptNum) {
        this.acceptNum = acceptNum;
    }
}
