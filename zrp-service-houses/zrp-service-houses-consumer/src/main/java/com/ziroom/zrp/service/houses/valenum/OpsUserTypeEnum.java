package com.ziroom.zrp.service.houses.valenum;

/**
 * <p>密码锁操作人类型</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author zhangyl2
 * @Date Created in 2017年12月19日
 * @version 1.0
 * @since 1.0
 */
public enum OpsUserTypeEnum {

    ZRK(1,"自如客"),
    EMP(2,"内部员工"),
    DSF(3,"第三方");

    OpsUserTypeEnum(int code, String name) {
        this.code = code;
        this.name = name;
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

    public  static OpsUserTypeEnum getSmartUserTypeByCode(int code){

        for (OpsUserTypeEnum opsUserTypeEnum: OpsUserTypeEnum.values()){
            if (code == opsUserTypeEnum.getCode()){
                return opsUserTypeEnum;
            }
        }
        return  null;
    }
}
