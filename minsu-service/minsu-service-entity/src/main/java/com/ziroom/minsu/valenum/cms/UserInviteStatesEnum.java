package com.ziroom.minsu.valenum.cms;

/**
 * <p></p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 * 邀请好友下单活动,用户的当前活动状态
 * @author yanb
 * @version 1.0
 * @Date Created in 2017年12月06日 15:30
 * @since 1.0
 */
public enum UserInviteStatesEnum {

    VALID(0, "可以参加活动"),
    HAVE_RECEVIED(1,"已经参加过活动"),
    INVITER(2,"邀请人自己");



    public static UserInviteStatesEnum getEnumByCode(Integer code) {
        for (UserInviteStatesEnum userInviteStatesEnum : UserInviteStatesEnum.values()) {
            if (userInviteStatesEnum.getCode().equals(code)) {
                return userInviteStatesEnum;
            }
        }
        return null;
    }

    private Integer code;

    private String name;

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    UserInviteStatesEnum(Integer code, String name) {

        this.code = code;
        this.name = name;
    }
}
