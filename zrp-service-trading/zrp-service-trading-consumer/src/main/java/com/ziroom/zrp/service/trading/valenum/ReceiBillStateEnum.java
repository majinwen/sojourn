package com.ziroom.zrp.service.trading.valenum;

/**
 * <p>应收账单状态枚举</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author lusp
 * @version 1.0
 * @Date Created in 2017年10月13日 20:33
 * @since 1.0
 */

public enum ReceiBillStateEnum {

    WSK(0,"未收款"),
    BFSK(1,"部分收款"),
    YSK(2,"已收款"),
    YZF(3,"已作废");

    ReceiBillStateEnum(int code, String name){
        this.code = code;
        this.name = name;
    }

    private int code;

    private String name;

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
