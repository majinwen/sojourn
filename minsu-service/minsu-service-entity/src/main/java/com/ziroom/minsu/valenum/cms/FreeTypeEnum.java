package com.ziroom.minsu.valenum.cms;

/**
 * <p>免佣金类型</p>
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
public enum  FreeTypeEnum {

    ACTIVITY(1,"1018活动免佣金"),
    SEED_FREE(2,"种子房东免佣金");

    FreeTypeEnum( int code, String name) {
        this.code = code;
        this.name = name;

    }

    /**
     * 获取
     * @param code
     * @return
     */
    public static FreeTypeEnum getByCode(int code){
        for(final FreeTypeEnum freeTypeEnum : FreeTypeEnum.values()){
            if(freeTypeEnum.getCode() == code){
                return freeTypeEnum;
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
