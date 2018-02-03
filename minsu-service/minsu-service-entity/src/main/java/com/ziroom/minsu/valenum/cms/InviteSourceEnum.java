/**
 * @FileName: InviteSourceEnum.java
 * @Package com.ziroom.minsu.valenum.cms
 * 
 * @author loushuai
 * @created 2017年12月4日 下午4:45:27
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.valenum.cms;

/**
 * <p>邀请码来源</p>
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
public enum InviteSourceEnum {

	OLD_INVITE(0, "老版邀请下单活动"),
	NEW_INVITE(1, "新版邀请好友下单");

    private InviteSourceEnum(int code, String name) {
		this.code = code;
		this.name = name;
	}

    /**
     * 获取
     * @param code
     * @return
     */
    public static InviteSourceEnum getByCode(int code){
        for(final InviteSourceEnum temp : InviteSourceEnum.values()){
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
