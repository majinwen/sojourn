package com.ziroom.zrp.service.trading.dto.finance;

import java.io.Serializable;

/**
 * <p>租金卡查询出参</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author lusp
 * @version 1.0
 * @Date Created in 2017年11月27日 10:22
 * @since 1.0
 */

public class RentCardResponseDto implements Serializable{

    private static final long serialVersionUID = -519257637458265273L;

    /**
     * 优惠券优惠码
     */
    private String code;

    /**
     * 优惠券名字
     */
    private String name;

    /**
     * 优惠券描述
     */
    private String desc;

    /**
     * 基础卡券类型（租金卡为2）
     */
    private Integer base;

    /**
     * 优惠券使用规则
     */
    private String rule;

    /**
     * 	金额，单位分
     */
    private Integer money;

    /**
     * 业务线ID
     */
    private Integer service_line_id;

    /**
     * 使用状态 0未使用 10已使用
     */
    private Integer use_status;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Integer getBase() {
        return base;
    }

    public void setBase(Integer base) {
        this.base = base;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    public Integer getService_line_id() {
        return service_line_id;
    }

    public void setService_line_id(Integer service_line_id) {
        this.service_line_id = service_line_id;
    }

    public Integer getUse_status() {
        return use_status;
    }

    public void setUse_status(Integer use_status) {
        this.use_status = use_status;
    }
}
