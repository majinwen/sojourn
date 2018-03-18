package com.ziroom.zrp.service.houses.valenum;

import com.asura.framework.base.util.Check;

/**
 * <p>智能平台业务类型</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author yd
 * @version 1.0
 * @Date Created in 2017年12月13日 15:21
 * @since 1.0
 */
public enum HouseTypeEnum {

    ZYU(4,"自如寓");

    private int code;

    private String desc;

    HouseTypeEnum(int code,String desc){

        this.code = code;
        this.desc = desc;
    }


    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    /**
     *
     * 是否合法
     *
     * @author zhangyl2
     * @created 2018年01月16日 15:01
     * @param
     * @return
     */
    public static boolean checkLegalCode(Integer code){
        if(!Check.NuNObj(code)){
            for (HouseTypeEnum houseTypeEnum : HouseTypeEnum.values()){
                if (houseTypeEnum.getCode() == code){
                    return true;
                }
            }
        }
        return false;
    }

}
