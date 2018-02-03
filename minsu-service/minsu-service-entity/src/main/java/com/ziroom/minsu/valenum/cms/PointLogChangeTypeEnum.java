/**
 * @FileName: PointLogChangeTypeEnum.java
 * @Package com.ziroom.minsu.valenum.cms
 * 
 * @author loushuai
 * @created 2017年12月6日 上午10:51:32
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.valenum.cms;

/**
 * <p>积分日志表变化类型枚举</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author loushuai
 * @since 1.0
 * @version 1.0
 */
public enum PointLogChangeTypeEnum {
	INVITE_CREATE_ORDER(1, "邀请好友下单获得积分"),
	EXCHANGE_COUPON(2, "兑换优惠券消耗积分");

    private PointLogChangeTypeEnum(int code, String name) {
		this.code = code;
		this.name = name;
	}

    /**
     * 获取
     * @param code
     * @return
     */
    public static PointLogChangeTypeEnum getByCode(int code){
        for(final PointLogChangeTypeEnum temp : PointLogChangeTypeEnum.values()){
            if(temp.getCode() == code){
                return temp;
            }
        }
        return null;
    }
    
	private int code;

    private String name;

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

}
