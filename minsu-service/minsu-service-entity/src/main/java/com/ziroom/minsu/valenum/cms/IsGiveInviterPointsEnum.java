package com.ziroom.minsu.valenum.cms;

/**
 * <p></p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author yanb
 * @version 1.0
 * @Date Created in 2017年12月16日 19:00
 * @since 1.0
 */
public enum IsGiveInviterPointsEnum {
    NOT_YET_ADD(0, "尚未给邀请人增加积分"),
    ALREADY_ADD(1, "1，已给邀请人增加积分");

    private int code;
    private String name;

    IsGiveInviterPointsEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public static IsGiveInviterPointsEnum getEnumByCode(Integer code) {
        for (IsGiveInviterPointsEnum isGiveInviterPointsEnum : IsGiveInviterPointsEnum.values()) {
            if (isGiveInviterPointsEnum.getCode()==code) {
                return isGiveInviterPointsEnum;
            }
        }
        return null;
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
