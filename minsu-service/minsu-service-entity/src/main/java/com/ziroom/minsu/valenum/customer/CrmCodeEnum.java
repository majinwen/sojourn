package com.ziroom.minsu.valenum.customer;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * <p>分机绑定状态</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/5/6.
 * @version 1.0
 * @since 1.0
 */
public enum CrmCodeEnum {


    OK(0,"成功"),
    WEIZHI(100,"未知错误"),
    GUOQI(108,"时间戳过期"),
    SIGN(109,"Sign验证失败"),
    DIANHUA(518001,"管家电话为空"),
    BIANHAO(518002,"管家编号为空"),
    XINGMING(518003,"管家姓名为空"),
    FENji(518004,"分机号为空"),
    DOUBLE_OK(518005,"重复分机数据"),
    NO(518006,"无此分机数据"),
    NULL(518007,"管家号和分机号不能同时为空");


    /** code */
    private int code;

    /** 名称 */
    private String name;

    private static final Map<Integer,String> enumMap = new LinkedHashMap<Integer,String>();

    static {
        for (CrmCodeEnum crmCodeEnum : CrmCodeEnum.values()) {
            enumMap.put(crmCodeEnum.getCode(), crmCodeEnum.getName());
        }
    }

    CrmCodeEnum(int code, String name){
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static Map<Integer,String> getEnumMap() {
        return enumMap;
    }
}
