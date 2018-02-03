package com.ziroom.minsu.valenum.customer;

/**
 * <p>用户行为(成长体系)-房东</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author zhangyl2
 * @version 1.0
 * @Date Created in 2017年10月09日
 * @since 1.0
 */
public enum LandlordBehaviorEnum {

    /**
     * 限制行为-系统生成
     */
    REFUSE_APPLY(1, 1, 1101, "拒绝申请/次", -0.5),
    APPLY_CANCEL_ORDER(1, 1, 1102, "接单后房东申请取消", -3.0),
    ORDER_COMPOSITE_EVALUATE(1, 1, 1103, "评价综合评分≤3分/条", -3.0),
    ORDER_SINGLE_EVALUATE(1, 1, 1104, "评价存在单一评价维度评分≤2分/条", -3.0),

    /**
     * 限制行为-人工录入
     */
    IM_COMMUNICATE_ILLEGAL(1, 2, 1001, "IM沟通违规(引流、提供联系方式、恶意引导等)", -4.0),
    ABUSE_TENANT(1, 2, 1002, "辱骂客户", -5.0),
    SCALPING_CONVERTCASH(1, 2, 1003, "刷单、优惠券套现", -4.0),
    MARKETING_CHEAT(1, 2, 1004, "营销活动作弊", -4.0),
    NEGATIVE_CONTACT(1, 2, 1005, "联系不上、不愿管理", -4.0),
    RELEASE_FAKE_HOUSE(1, 2, 1006, "发布虚假房源", -5.0),

    /**
     * 激励行为-系统生成
     */
    accept_Reserve_apply(2, 1, 2101, "接受预定申请/次", 0.2);

    LandlordBehaviorEnum(Integer attribute, Integer createType, Integer type, String desc, Double score) {
        this.attribute = attribute;
        this.createType = createType;
        this.type = type;
        this.desc = desc;
        this.score = score;
    }

    public static String getNameByType(int type){
        for (LandlordBehaviorEnum landlordBehaviorEnum : LandlordBehaviorEnum.values()) {
            if (landlordBehaviorEnum.getType() == type) {
                return landlordBehaviorEnum.getDesc();
            }
        }
        return "";
    }

    public static LandlordBehaviorEnum getEnumByType(int type){
        for (LandlordBehaviorEnum landlordBehaviorEnum : LandlordBehaviorEnum.values()) {
            if (landlordBehaviorEnum.getType() == type) {
                return landlordBehaviorEnum;
            }
        }
        return null;
    }

    /**
     * 行为属性(1-限制行为 2-激励行为 3-中性行为)
     */
    private Integer attribute;

    /**
     * 创建类型(1-系统生成 2-人工录入)
     */
    private Integer createType;

    /**
     * 行为类型
     * (1***-限制行为：10**-人工录入，11**-系统生成；
     * 2***-激励行为：20**-人工录入，21**-系统生成；
     * 3***-中性行为：30**-人工录入，31**-系统生成)
     * <p>
     * 1001-IM沟通违规(引流、提供联系方式、恶意引导等)
     * 1002-辱骂客户
     * 1003-刷单、优惠券套现
     * 1004-营销活动作弊
     * 1005-联系不上、不愿管理
     * 1006-发布虚假房源
     * 1101-拒绝申请/次
     * 1102-接单后房东申请取消
     * 1103-评价综合评分≤3分/条
     * 1104-评价存在单一评价维度评分≤2分/条
     * 2101-接受预定申请/次
     */
    private Integer type;

    /**
     * type对应的说明
     */
    private String desc;

    /**
     * 得分
     */
    private Double score;

    public Integer getAttribute() {
        return attribute;
    }

    public Integer getCreateType() {
        return createType;
    }

    public Integer getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }

    public Double getScore() {
        return score;
    }
}
