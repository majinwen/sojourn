package com.ziroom.minsu.valenum.common;

/**
 * 跳转app打开页面
 */
public enum JumpOpenAppEnum {

    EVALUATE_INFO(0,"评价详情"),

    MINSU_HOME_PAGE(1,"民宿首页"),

    TEN_ORDER_INFO(2,"房客订单详情页"),
    
    LAND_ORDER_INFO(3,"房东订单详情页"),
    
    LAND_HOUSE_LIST(4,"房源列表页"),

    PERSONAL_CENTER(5,"app个人中心");
    
    private int code;

    private String name;

    JumpOpenAppEnum(int code,String name){
        this.code = code;
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
