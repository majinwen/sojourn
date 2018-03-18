package com.ziroom.zrp.service.trading.valenum.surrender;

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
 * @Date 2017年10月15日 14时15分
 * @Version 1.0
 * @Since 1.0
 */
public enum SurTypeEnum {
    NORMAL(0, "正常退租"),
    ABNORMAL(1, "非正常退租"),
    ONE_SIDED(2, "客户单方面解约"),
    THREE_DAYS_NOT_SATISFIED(3, "三天不满意退租"),
    IN_RENT(4, "换租"),
    SUBLET(5, "转租"),
    SHORT_UNRENT(6, "短租退租"),
    ORDER_CANCEL(7, "取消订单");

    private int code;
    private String name;

    SurTypeEnum(int code, String name) {
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
