package com.ziroom.minsu.valenum.house;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * <p></p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author wangwt
 * @version 1.0
 * @Date Created in 2017年06月27日 18:04
 * @since 1.0
 */
public enum HouseModifyTypeEnum {

    TYPELOCATION("类型及位置", "点击修改房源类型、出租类型、位置信息", "房源类型、出租类型、位置信息"),
    DESCRIBEBASE("描述及基础信息", "点击修改房源描述、配套设施等信息", "房源描述、配套设施等信息"),
    ORDERRULE("预订及政策信息", "点击修改预订类型、退订政策等信息", "预订类型、退订政策等信息"),
    CHECKINFOMATION("入住信息", "点击修改入住时间、退房时间等信息", "入住时间、退房时间等信息"),
    PRICE("价格", "点击修改基础价格、周末价格、清洁费等信息", "基础价格、周末价格、清洁费等信息"),
    SMART_LOCK("智能门锁", "点击修改临时密码、管理密码等", "点击修改临时密码、管理密码等"),
    HOUSE_SN("房源编码", "", "");

    HouseModifyTypeEnum(String title, String modifyTip, String tip) {
        this.title = title;
        this.modifyTip = modifyTip;
        this.tip = tip;
    }

    private static final Map<String,String> modifyMap = new LinkedHashMap<>();

    static {
        for (HouseModifyTypeEnum houseModifyTypeEnum : HouseModifyTypeEnum.values()) {
            modifyMap.put(houseModifyTypeEnum.title, houseModifyTypeEnum.getModifyTip());
        }
    }

    private static final Map<String,String> enumMap = new LinkedHashMap<>();

    static {
        for (HouseModifyTypeEnum houseModifyTypeEnum : HouseModifyTypeEnum.values()) {
            enumMap.put(houseModifyTypeEnum.title, houseModifyTypeEnum.getTip());
        }
    }

    /*
     *修改页列表标题
     */
    private String title;

    /*
     *可以修改时的文案
     */
    private String modifyTip;

    /*
     *不能修改时的文案
     */
    private String tip;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getModifyTip() {
        return modifyTip;
    }

    public void setModifyTip(String modifyTip) {
        this.modifyTip = modifyTip;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public static Map<String, String> getModifyMap() {
        return modifyMap;
    }

    public static Map<String, String> getEnumMap() {
        return enumMap;
    }
}
