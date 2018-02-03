package com.ziroom.minsu.valenum.customer;

/**
 * <p>收集用户位置入口</p>
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
public enum LocationTypeEnum {
    INIT(1,"初始化"),
    HOUSE_DETAIL(2,"房源详细"),
    SEARCH(3,"搜索"),
    IM(4,"IM"),
    COLLECT(5,"收藏"),
    SHARE(6,"分享"),
    ORDER(7,"订单");

    LocationTypeEnum(int code,String value){
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
