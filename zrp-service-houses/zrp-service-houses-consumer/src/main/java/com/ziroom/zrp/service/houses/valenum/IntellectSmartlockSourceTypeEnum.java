package com.ziroom.zrp.service.houses.valenum;

/**
 * <p>智能锁状态记录表-下发密码来源类型</p>
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
public enum IntellectSmartlockSourceTypeEnum {

    ZO(0, "ZO管家"),
    ADDRENTCONTRACT(1, "新增智能平台出房合同"),
    BACKRENT(2, "退租"),
    CONTINUEABOUT(3, "续约"),
    CHANGEOCCUPANTS(4, "更换入住人");

    IntellectSmartlockSourceTypeEnum(int code, String name) {
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

    public static IntellectSmartlockSourceTypeEnum getByCode(int code) {
        for (final IntellectSmartlockSourceTypeEnum sourceTypeEnum : IntellectSmartlockSourceTypeEnum.values()) {
            if (sourceTypeEnum.getCode() == code) {
                return sourceTypeEnum;
            }
        }
        return null;
    }
}
