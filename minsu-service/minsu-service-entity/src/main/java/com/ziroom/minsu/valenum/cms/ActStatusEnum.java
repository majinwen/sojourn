package com.ziroom.minsu.valenum.cms;

import com.asura.framework.base.util.Check;

/**
 * <p>活动状态</p>
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
public enum ActStatusEnum {
	
	DISABLE(1,"未启用"),
	ABLE(2,"已启用"){
        @Override
        public boolean checkExt() {
            return true;
        }
    },
	END(3,"已终止");

    ActStatusEnum( int code, String name) {
        this.code = code;
        this.name = name;

    }

    /**
     * 获取优惠券活动状态
     * @param code
     * @return
     */
    public static ActStatusEnum getByCode(Integer code){
        if (Check.NuNObj(code)){
            return null;
        }
        for(final ActStatusEnum actStatusEnum : ActStatusEnum.values()){
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

    /**
     * 默认当前状态不能追加优惠券
     * @return
     */
    public boolean checkExt(){
        return false;
    }
}
