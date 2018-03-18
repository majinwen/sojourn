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
 * @Date 2017年10月13日 16时01分
 * @Version 1.0
 * @Since 1.0
 */
public enum ExpenseItemEnum {
    FZ("1", "C01", "房租"),
    FWF("2", "C02", "服务费"),
    YJ("3", "C03", "押金"),
    DJ("4", "C04", "定金"),
    YQWYJ("5", "C05", "逾期违约金"),
    HZSXF("6", "C06", "换租手续费"),
    FZSJ("7", "C07", "房租税金"),
    SF("8", "S01", "水费"),
    DF("9", "S02", "电费"),
    BJF("10", "S03", "保洁费"),
    WXF("11", "S04", "维修费"),
    TCF("12", "S05", "停车费"),
    XYKCZ("13", "S06", "洗衣卡充值"),
    DSKX("15", "Z02", "多收款项"),
    QTFY("55", "Z03", "其他费用"),
    BBFK("57", "Z04", "补办房卡"),
    TZJGPCFY("64", "C08", "退租交割赔偿费用"),
    WYJ("65", "C09", "违约金"),
    FYZFZ("66", "C10", "付业主房租");

    private String id;
    private String code;
    private String name;

    ExpenseItemEnum(String id, String code, String name) {
        this.id = id;
        this.code = code;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
