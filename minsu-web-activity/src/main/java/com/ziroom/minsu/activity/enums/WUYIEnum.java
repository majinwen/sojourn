package com.ziroom.minsu.activity.enums;

/**
 * 五一活动枚举
 * @author jixd
 * @created 2017年04月01日 16:54:34
 * @param
 * @return
 */
public enum WUYIEnum {

    WUYI500("WUYI500", "五一500活动包",90),
    WUYI800("WUYI800", "五一800活动包",8),
    WUYI1000("WUYI1000", "五一1000活动包",2);

    WUYIEnum(String code,String name,int rate){
        this.code = code;
        this.name = name;
        this.rate = rate;
    }

    private String code;

    private String name;

    private int rate;

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public int getRate() {
        return rate;
    }
}
