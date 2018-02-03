/**
 * @FileName: TiersTypeEnum.java
 * @Package com.ziroom.minsu.valenum.cms
 * 
 * @author loushuai
 * @created 2017年12月7日 下午4:35:58
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.valenum.cms;

/**
 * <p>积分阶梯-阶梯规则的活动类型枚举</p>
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
public enum TiersTypeEnum {

	INVITE_CREATE_ORDER(1, "邀请下单活动");

    private TiersTypeEnum(int code, String name) {
		this.code = code;
		this.name = name;
	}

    /**
     * 获取
     * @param code
     * @return
     */
    public static TiersTypeEnum getByCode(int code){
        for(final TiersTypeEnum temp : TiersTypeEnum.values()){
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
