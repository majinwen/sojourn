package com.ziroom.minsu.valenum.cms;

/**
 * <p>好友邀请状态</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on on 2016/11/2.
 * @version 1.0
 * @since 1.0
 */
public enum  InviteStatusEnum {


    INIT(0,"初始化"),
    SEND_OTHER(1,"已被邀请且给被邀请人送券"),
    SEND_BACK(2,"已给邀请人送券")
    ;

    InviteStatusEnum( int code, String name) {
        this.code = code;
        this.name = name;

    }

    /**
     * 获取
     * @param code
     * @return
     */
    public static InviteStatusEnum getByCode(int code){
        for(final InviteStatusEnum inviteStatusEnum : InviteStatusEnum.values()){
            if(inviteStatusEnum.getCode() == code){
                return inviteStatusEnum;
            }
        }
        return null;
    }

    /** code */
    private int code;

    /** 名称 */
    private String name;

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }


}
