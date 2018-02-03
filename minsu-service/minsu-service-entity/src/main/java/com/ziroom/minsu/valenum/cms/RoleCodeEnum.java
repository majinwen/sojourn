package com.ziroom.minsu.valenum.cms;

/**
 * <p>资质类型</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on on 2016/10/20.
 * @version 1.0
 * @since 1.0
 */
public enum  RoleCodeEnum {

    SEED(1,"种子房东");

    RoleCodeEnum( int code, String name) {
        this.code = code;
        this.name = name;

    }

    /**
     * 获取
     * @param code
     * @return
     */
    public static RoleCodeEnum getByCode(int code){
        for(final RoleCodeEnum roleCodeEnum : RoleCodeEnum.values()){
            if(roleCodeEnum.getCode() == code){
                return roleCodeEnum;
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
