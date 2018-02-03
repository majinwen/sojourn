package com.ziroom.minsu.valenum.traderules;

import com.asura.framework.base.entity.BaseEntity;

import java.util.Date;

/**
 * <p>退订政策</p>
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
public class CheckOutStrategy extends BaseEntity{

    /**
     * 序列化id
     */
    private static final long serialVersionUID = 943567258192157707L;


    /**
     * 退房类型
     * @see com.ziroom.minsu.valenum.order.CheckOutMoneyTypeEnum
     */
    private Integer moneyType;


    /** 免费提前退订最小天数 */
    private Integer preFreeDayCount;


    /** 无责任取消需扣房租天数 */
    private Integer freeCost;

    /** 入住前违约需扣房租 */
    private Integer preCost;

    /** 入住后违约需扣房租 */
    private Integer suffixCost;

    /** 无责任退房扣除全部房租百分比 */
    private Double cancelFreePercent = 0.0;

    /** 取消订单剩余房租扣除百分比 */
    private Double cancelLastPercent = 0.0;

    /** 退房剩余房租扣除百分比 */
    private Double checkOutLastPercent = 0.0;

    /** 真实的房租 */
    private Integer realRentalMoney;


    /** 长租天数 */
    private Integer changzuCount;

    /** 所有的退房时间都走这个 */
    private Date  dealTime = new Date();

    /** 是否是长租 */
    private Boolean changzuFlag = false;

    public Boolean getChangzuFlag() {
        return changzuFlag;
    }

    public void setChangzuFlag(Boolean changzuFlag) {
        this.changzuFlag = changzuFlag;
    }

    public Date getDealTime() {
        return dealTime;
    }

    public void setDealTime(Date dealTime) {
        this.dealTime = dealTime;
    }

    public Integer getRealRentalMoney() {
        return realRentalMoney;
    }

    public void setRealRentalMoney(Integer realRentalMoney) {
        this.realRentalMoney = realRentalMoney;
    }

    public Double getCancelLastPercent() {
        return cancelLastPercent;
    }

    public void setCancelLastPercent(Double cancelLastPercent) {
        this.cancelLastPercent = cancelLastPercent;
    }

    public Double getCheckOutLastPercent() {
        return checkOutLastPercent;
    }

    public void setCheckOutLastPercent(Double checkOutLastPercent) {
        this.checkOutLastPercent = checkOutLastPercent;
    }

    public Integer getFreeCost() {
        return freeCost;
    }

    public void setFreeCost(Integer freeCost) {
        this.freeCost = freeCost;
    }

    public Double getCancelFreePercent() {
        return cancelFreePercent;
    }

    public void setCancelFreePercent(Double cancelFreePercent) {
        this.cancelFreePercent = cancelFreePercent;
    }

    public Integer getMoneyType() {
        return moneyType;
    }

    public void setMoneyType(Integer moneyType) {
        this.moneyType = moneyType;
    }

    public Integer getPreFreeDayCount() {
        return preFreeDayCount;
    }

    public void setPreFreeDayCount(Integer preFreeDayCount) {
        this.preFreeDayCount = preFreeDayCount;
    }

    public Integer getPreCost() {
        return preCost;
    }

    public void setPreCost(Integer preCost) {
        this.preCost = preCost;
    }

    public Integer getSuffixCost() {
        return suffixCost;
    }

    public void setSuffixCost(Integer suffixCost) {
        this.suffixCost = suffixCost;
    }

    public Integer getChangzuCount() {
        return changzuCount;
    }

    public void setChangzuCount(Integer changzuCount) {
        this.changzuCount = changzuCount;
    }
}
