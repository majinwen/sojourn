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
 * @Date 2017年10月17日 17时59分
 * @Version 1.0
 * @Since 1.0
 */
public enum ReceiBillTypeEnum {
    //单据类型(0.合同计划收款；1.其它收款)
    JHSK(0, "合同计划收款"),
    QTSK(1, "其它收款"),
    INTELLECT_WATT(2, "智能电费:客户充值 5分钟内有效");

    private int code;
    private String name;

    ReceiBillTypeEnum(int code, String name) {
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
