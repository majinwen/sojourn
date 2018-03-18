package com.ziroom.zrp.service.trading.valenum.delivery;

/**
 * <p>物业交割来源</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd
 * @version 1.0
 * @Date Created in 2017年11月10日 14:21
 * @since 1.0
 */
public enum DeliveryFromEnum {
    APP(1,"APP录入物业交割"),
    PERSON(2,"个人签约录入物业交割"),
    ENTERPRISE(3,"企业签约录入物业交割"),
    CONTRACTDETAIL(4, "合同详情查看");


    DeliveryFromEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public int code;

    public String name;

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
