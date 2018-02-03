package com.ziroom.minsu.valenum.cms;

/**
 * <p>优惠券的活动类型</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/6/15.
 * @version 1.0
 * @since 1.0
 */
public enum  ActTypeEnum {
    //1：优惠券抵现金活动 2：折扣券活动 3：随机现金券活动 4：免天优惠券活动
    CACHE(1,"现金券"),
    CUT(2,"折扣券"),
    RANDOM(3,"随机现金券"),
    FREE(4,"免单券"),
    GIFT_AC(30,"礼品活动");


    ActTypeEnum( int code, String name) {
        this.code = code;
        this.name = name;

    }

    /**
     * 获取优惠券活动类型
     * @param code
     * @return
     */
    public static ActTypeEnum getByCode(int code){
        for(final ActTypeEnum actTypeEnum : ActTypeEnum.values()){
            if(actTypeEnum.getCode() == code){
                return actTypeEnum;
            }
        }
        return null;
    }

    /** code */
    private int code;

    /** 名称 */
    private String name;

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
