/**
 * @FileName: TradeRulesEnum.java
 * @Package com.ziroom.minsu.services.basedata.valenum.traderules
 * 
 * @author bushujie
 * @created 2016年3月26日 下午9:36:16
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.valenum.traderules;

/**
 * <p>交易规则属性配置</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author bushujie
 * @since 1.0
 * @version 1.0
 */
public enum TradeRulesEnum {


    TradeRulesEnum001("TradeRulesEnum001","支付类型"),
    TradeRulesEnum002("TradeRulesEnum002","订单支付时限(分钟)"),
    TradeRulesEnum003("TradeRulesEnum003","无效字段"),
    TradeRulesEnum004("TradeRulesEnum004","无效字段"),
    TradeRulesEnum005("TradeRulesEnum005","退订政策"),
    TradeRulesEnum006("TradeRulesEnum006","允许租客同时拥有的已提交但未支付的最大订单数"){
        @Override
        public String getDefaultValue() {
            return "5";
        }
    },
    TradeRulesEnum007("TradeRulesEnum007","结算方式"),
    TradeRulesEnum008("TradeRulesEnum008","收取房东佣金类型"),
    TradeRulesEnum009("TradeRulesEnum009","收取租客佣金类型"),
    TradeRulesEnum0010("TradeRulesEnum0010","强制取消房东无责任时限"),
    TradeRulesEnum0011("TradeRulesEnum0011","罚金交付时限"),
    TradeRulesEnum0012("TradeRulesEnum0012","房东租金支付方式"),
    TradeRulesEnum0013("TradeRulesEnum0013","罚金天数"),
    TradeRulesEnum0014("TradeRulesEnum0014","订单审核时限（分钟）"),
    TradeRulesEnum0015("TradeRulesEnum0015","房东额外消费时限（小时）"),
    TradeRulesEnum0016("TradeRulesEnum0016","房客额外消费时限（小时）"),
    TradeRulesEnum0017("TradeRulesEnum0017","申请预定提醒时限（分钟）"),
    EvaluateAuditEnum004("EvaluateAuditEnum004","评价是否审核"),
    TradeRulesEnum0018("TradeRulesEnum0018","拒绝原因"),
    TradeRulesEnum0019("TradeRulesEnum0019", "清洁费最大百分比"),
    TradeRulesEnum0021("TradeRulesEnum0021", "房客电话展示时长")
    ;
    private final String value;

    private final String name;

    TradeRulesEnum(String value,String name) {
        this.value = value;
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public String getDefaultValue(){
        return null;
    }
}
