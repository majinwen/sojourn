package com.ziroom.zrp.service.houses.valenum;

/**
 * <p>密码锁的用户类型</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author cuigh6
 * @version 1.0
 * @Date Created in 2017年12月11日 14:07
 * @since 1.0
 */
public enum SmartUserTypeEnum {

    OTHER(0,"其他"),
    ZO(1,"zo管家（内部员工）"),
    BJ(2,"第三方（保洁 没有系统号的使用人员）"),
    SYS(4,"系统（用户）");

    SmartUserTypeEnum(int code, String name) {

        this.name = name;this.code = code;
    }
    /**
     * 类型
     */
    private int code;
    /**
     * 名字
     */
    private String name;


    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static SmartUserTypeEnum getSmartUserTypeByCode(int code){

        for (SmartUserTypeEnum smartUserTypeEnum:SmartUserTypeEnum.values()){
            if (code == smartUserTypeEnum.getCode()){
                return smartUserTypeEnum;
            }
        }
        return  null;
    }
}
