package com.ziroom.minsu.report.order.vo;

import com.asura.framework.base.entity.BaseEntity;
import com.ziroom.minsu.report.common.annotation.FieldMeta;

/**
 * <p></p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author lishaochuan on 2016/12/15 10:28
 * @version 1.0
 * @since 1.0
 */
public class OrderFollowVo extends BaseEntity {

    @FieldMeta(skip = true)
    private static final long serialVersionUID = 2095085116546939080L;

    @FieldMeta(skip = true)
    private Integer sort;

    @FieldMeta(name="城市",order=20)
    private String city;

    @FieldMeta(name="提交订单人数",order=30)
    private Integer commitPeopleNum = 0;

    @FieldMeta(name="预订成功人数",order=40)
    private Integer commitSucPeopleNum = 0;

    @FieldMeta(name="提交订单数量",order=50)
    private Integer commitOrderNum = 0;

    @FieldMeta(name="支付成功订单数量",order=60)
    private Integer payOrderSucNum = 0;

    @FieldMeta(name="自主支付成功订单",order=70)
    private Integer payOrderSucSelfNum = 0;

    @FieldMeta(name="自主首次支付成功订单",order=80)
    private Integer payOrderSucSelfOneNum = 0;

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getCommitPeopleNum() {
        return commitPeopleNum;
    }

    public void setCommitPeopleNum(Integer commitPeopleNum) {
        this.commitPeopleNum = commitPeopleNum;
    }

    public Integer getCommitSucPeopleNum() {
        return commitSucPeopleNum;
    }

    public void setCommitSucPeopleNum(Integer commitSucPeopleNum) {
        this.commitSucPeopleNum = commitSucPeopleNum;
    }

    public Integer getCommitOrderNum() {
        return commitOrderNum;
    }

    public void setCommitOrderNum(Integer commitOrderNum) {
        this.commitOrderNum = commitOrderNum;
    }

    public Integer getPayOrderSucNum() {
        return payOrderSucNum;
    }

    public void setPayOrderSucNum(Integer payOrderSucNum) {
        this.payOrderSucNum = payOrderSucNum;
    }

    public Integer getPayOrderSucSelfOneNum() {
        return payOrderSucSelfOneNum;
    }

    public void setPayOrderSucSelfOneNum(Integer payOrderSucSelfOneNum) {
        this.payOrderSucSelfOneNum = payOrderSucSelfOneNum;
    }

    public Integer getPayOrderSucSelfNum() {
        return payOrderSucSelfNum;
    }

    public void setPayOrderSucSelfNum(Integer payOrderSucSelfNum) {
        this.payOrderSucSelfNum = payOrderSucSelfNum;
    }
}
