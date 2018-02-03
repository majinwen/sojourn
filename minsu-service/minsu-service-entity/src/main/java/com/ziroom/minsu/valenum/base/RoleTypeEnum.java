/**
 * @FileName: RoleTypeEnum.java
 * @Package com.ziroom.minsu.valenum.base
 * 
 * @author yd
 * @created 2016年10月31日 下午3:34:52
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.valenum.base;

/**
 * <p>角色类型枚举</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author yd
 * @since 1.0
 * @version 1.0
 */
public enum RoleTypeEnum {

    ADMIN(0,"普通角色"),
    DATA_AND_AREA(1,"数据区域角色"),
    DATA(2,"区域角色"),
    AREA(3,"数据角色");
    
    RoleTypeEnum(int code, String name) {
        this.code = code;
        this.name = name;
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
