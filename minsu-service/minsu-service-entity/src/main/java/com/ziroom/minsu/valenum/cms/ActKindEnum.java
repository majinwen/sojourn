package com.ziroom.minsu.valenum.cms;

/**
 * <p>活动形式</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * @author lishaochuan on 2016年6月16日
 * @since 1.0
 * @version 1.0
 */
public enum ActKindEnum {
	
	COUPON(1,"优惠券活动"),
	NORMAL(2,"普通活动");

    ActKindEnum( int code, String name) {
        this.code = code;
        this.name = name;

    }

    /**
     * 获取优惠券活动状态
     * @param code
     * @return
     */
    public static ActKindEnum getByCode(int code){
        for(final ActKindEnum actStatusEnum : ActKindEnum.values()){
            if(actStatusEnum.getCode() == code){
                return actStatusEnum;
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
