package com.ziroom.zrp.service.trading.valenum;

import java.util.Arrays;
import java.util.LinkedHashMap;

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
 * @Date 2017年11月13日 13时12分
 * @Version 1.0
 * @Since 1.0
 */
public enum DebtReasonEnum {
    GYTY(1, "故意拖延"),
    TZWBL(2, "退租未办理"),
    JYWTJ(3, "解约未提交"),
    KH(4, "空号"),
    TJ(5, "停机"),
    GJ(6, "关机"),
    WFJT(7, "无法接通"),
    LDTX(8, "来电提醒"),
    BZFWQ(9, "不在服务区"),
    HJZY(10, "呼叫转移"),
    THZ(11, "通话中"),
    WRJT(12, "无人接听"),
    JJ(13, "拒接"),
    JFWCL(14, "纠纷未处理"),
    WXWJJ(15, "维修未解决"),
    FBRJZ(16, "非本人居住"),
    ZRXTYY(17, "自如系统原因"),
    XE(19, "限额"),
    BJWT(20, "保洁问题"),
    ASJK(21, "按时交款"),
    YWBDCN(22, "业务不当承诺"),
    WQZF(23, "无钱支付"),
    TZZJBT(24, "投资资金被套"),
    GSZDKLC(25, "公司走打款流程"),
    KHCC(26, "客户出差");


    private int code;
    private String name;

    DebtReasonEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public static LinkedHashMap enumMap;

    static {
        enumMap = new LinkedHashMap();
        Arrays.stream(DebtReasonEnum.values()).forEach(p ->
                enumMap.put(p.getCode(), p.getName())
        );
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
