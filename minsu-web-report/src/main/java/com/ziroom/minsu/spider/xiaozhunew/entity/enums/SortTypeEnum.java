/**
 * @FileName: SortTypeEnum.java
 * @Package com.ziroom.minsu.spider.xiaozhunew.entity.enums
 * 
 * @author zl
 * @created 2016年10月14日 下午6:09:53
 * 
 * Copyright 2016-2025 ziroom
 */
package com.ziroom.minsu.spider.xiaozhunew.entity.enums;

/**
 * <p></p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author zl
 * @since 1.0
 * @version 1.0
 */
public enum SortTypeEnum {
	
	zuihaoping("zuihaoping","好评排序"),
	zuipianyi("zuipianyi","价格升序"),
	zuigui("zuigui","价格降序");
	
	SortTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    /** code */
    private String code;

    /** 名称 */
    private String name;

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

}
