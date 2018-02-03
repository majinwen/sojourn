package com.ziroom.minsu.valenum.order;

import com.asura.framework.base.util.Check;

/**
 * <p>跟进状态</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on on 2016/12/14.
 * @version 1.0
 * @since 1.0
 */
public enum FollowStatusEnum {
    ING(1,"跟进中"),
    OVER(2,"已完成");

    FollowStatusEnum(int code, String value){
        this.code  = code;
        this.value = value;
    }

    /**
     * code值
     */
    private  int code;

    /**
     * z中文含义
     */
    private String value;


    public static String getNameByCode(Integer code) {
        if (Check.NuNObj(code)){
            return "";
        }
        for (final FollowStatusEnum status : FollowStatusEnum.values()) {
            if (status.getCode() == code) {
                return status.getValue();
            }
        }
        return "";
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }


}
