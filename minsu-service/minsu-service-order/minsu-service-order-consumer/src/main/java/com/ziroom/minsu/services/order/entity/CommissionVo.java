package com.ziroom.minsu.services.order.entity;

import com.asura.framework.base.entity.BaseEntity;

/**
 * <p>佣金</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/4/3.
 * @version 1.0
 * @since 1.0
 */
public class CommissionVo extends BaseEntity {

    /**
     * 序列化id
     */
    private static final long serialVersionUID = 4564232557707L;


    /**
     * 退房类型
     * @see com.ziroom.minsu.valenum.order.MoneyTypeEnum
     */
    private Integer moneyType;


    private Double moneyCost;

    public Integer getMoneyType() {
        return moneyType;
    }

    public void setMoneyType(Integer moneyType) {
        this.moneyType = moneyType;
    }

    public Double getMoneyCost() {
        return moneyCost;
    }

    public void setMoneyCost(Double moneyCost) {
        this.moneyCost = moneyCost;
    }
}
