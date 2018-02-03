package com.ziroom.minsu.valenum.cms;

/**
 * <p>申请表的扩展表类型</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/6/29.
 * @version 1.0
 * @since 1.0
 */
public enum  ApplyExtType {

    URL(1, "链接");

    ApplyExtType(int code, String name) {
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
