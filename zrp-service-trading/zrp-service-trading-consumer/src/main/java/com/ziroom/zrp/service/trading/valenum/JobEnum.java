package com.ziroom.zrp.service.trading.valenum;

/**
 * <p>职业枚举</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author zhangyl2
 * @version 1.0
 * @Date Created in 2017年12月06日
 * @since 1.0
 */
public enum JobEnum {

    OTHER(0, "其他"),
    COMPUTER(1, "计算机/网络/技术"),
    COMMUNICATION(2, "电子/电器/通信技术"),
    SALING(3, "销售"),
    MARKET(4, "市场/公关/媒介"),
    FINANCE(5, "财务/统计/审计"),
    HUMANRECOURSE(6, "人力资源"),
    MANAGER(7, "企业高管/投资人"),
    DESIGNER(8, "美术/设计/创意"),
    BANKING(9, "金融(银行/基金/证券/期货)"),
    TRADING(10, "贸易/物流/运输/采购"),
    BUILDING(11, "建筑/房地产/装修装饰/物业管理"),
    SERVICE(12, "酒店/餐饮/旅游/服务"),
    MEDIA(13, "影视/媒体/文体/写作"),
    MEDICAL(14, "生物/医疗/美容保健"),
    ADMINISTATION(15, "行政/客户服务/后勤"),
    TRANSLATION(16, "翻译"),
    EDUCATION(17, "教育/培训"),
    CONSULT(18, "咨询/顾问/项目管理"),
    LAW(19, "法律"),
    INDUSTRY(20, "生物/制药/化工/环保"),
    STUDENT(21, "在校学生"),
    TECHNICIAN(22, "技工"),
    GOVERNMENT(23, "政府部门职员");

    JobEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    private int code;

    private String name;

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static JobEnum getByCode(int code) {
        for (final JobEnum certTypeEnum : JobEnum.values()) {
            if (certTypeEnum.getCode() == code) {
                return certTypeEnum;
            }
        }
        return null;
    }
}
