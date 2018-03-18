package com.ziroom.zrp.service.trading.valenum;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.LinkedHashMap;

/**
 * <p></p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @Author wangxm113
 * @Date 2017年11月13日 11时41分
 * @Version 1.0
 * @Since 1.0
 */
public enum UrgeActionEnum {
    GZKH(1, "跟踪客户"),
    GZGJ(2, "跟踪管家"),
    GZWQ(3, "跟踪外勤"),
    FZJS(6, "跟踪技术"),
    GZQT(8, "跟踪其他部门");


    private int code;
    private String name;

    UrgeActionEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public static LinkedHashMap enumMap;

    static {
        enumMap = new LinkedHashMap();
        Arrays.stream(UrgeActionEnum.values()).forEach(p ->
                enumMap.put(p.getCode(), p.getName())
        );
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
