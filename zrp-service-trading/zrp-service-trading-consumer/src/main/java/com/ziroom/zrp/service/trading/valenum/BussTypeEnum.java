package com.ziroom.zrp.service.trading.valenum;

/**
 * <p>业务类型</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author cuiyh9
 * @version 1.0
 * @Date Created in 2018年01月19日 11:10
 * @since 1.0
 */
public enum BussTypeEnum {

    SIGN(1, "新签"),
    RENEWALSIGN(2, "续约"),
    SURRENDER(3, "解约");

    BussTypeEnum(int code,String desc) {
        this.code = code;
        this.desc = desc;
    }

    private int code;

    private String desc;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    /**
     * 是否为续约
     * @author cuiyuhui
     * @created
     * @param
     * @return
     */
    public static boolean isRenewalSign(int param) {
        return RENEWALSIGN.getCode() == param;
    }

    /**
     * 是否为解约
     * @author cuiyuhui
     * @created
     * @param
     * @return
     */
    public static boolean isSurrender(int param) {
        return SURRENDER.getCode() == param;
    }
}
