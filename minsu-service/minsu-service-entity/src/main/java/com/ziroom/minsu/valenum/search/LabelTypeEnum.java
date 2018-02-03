package com.ziroom.minsu.valenum.search;

/**
 * <p>标签类型</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/8/24.
 * @version 1.0
 * @since 1.0
 */
public enum LabelTypeEnum {


    IS_NEW("isNew", "新上房源","/search/img/label/{iconPath}/xinshang.png"),
    IS_SPEED("isSpeed", "闪订房源","/search/img/label/{iconPath}/shandian.png"),
    IS_NEW_V2("isNew", "新上房源","/search/img/label/{iconPath}/xinshang_2.png"),
    IS_SPEED_V2("isSpeed", "闪订房源","/search/img/label/{iconPath}/shandian_2.png"),
    IS_SPEED_V3("isSpeed", "闪订房源","/search/img/label/{iconPath}/shandian_3.png");


    private final String code;

    private final String name;

    private final String path;

    LabelTypeEnum(String code,String name,String path) {
        this.code = code;
        this.name = name;
        this.path = path;

    }

    public String getPath() {
        return path;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

}
