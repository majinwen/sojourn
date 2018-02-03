package com.ziroom.minsu.services.order.dto;

/**
 * <p>有规则的项</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd
 * @version 1.0
 * @Date Created in 2017年08月02日 16:13
 * @since 1.0
 */
public class NeedPayFeeRuleResponse extends NeedPayFeeItemResponse {

    private Integer ruleCode;

    private String ruleName;

    public Integer getRuleCode() {
        return ruleCode;
    }

    public void setRuleCode(Integer ruleCode) {
        this.ruleCode = ruleCode;
    }

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }
}
