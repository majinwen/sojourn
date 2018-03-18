package com.ziroom.zrp.service.trading.valenum;

/**
 * <p></p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @Author wangxm113
 * @Date 2017年10月17日 18时01分
 * @Version 1.0
 * @Since 1.0
 */
public enum ReceiBillGenWayEnum {
    //生成方式(0.自动生成；1.手工录入)
    ZDSC(0, "自动生成"),
    SGLR(1, "手工录入");

    private int code;
    private String name;

    ReceiBillGenWayEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}


