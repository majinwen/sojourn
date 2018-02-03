package com.ziroom.minsu.services.common.conf;

import com.asura.framework.base.util.Check;
import com.asura.framework.conf.subscribe.AsuraSub;

/**
 * <p>民宿的zk常量设置</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/7/30.
 * @version 1.0
 * @since 1.0
 */
@AsuraSub
public enum EnumMinsuConfig {


    minsu_test("minsu", "test", "1", "测试值，请忽略"),
    minsu_sumTime("statics", "sumTime", "30", "房东回复多长时间内"),
    minsu_limitTimeDay("statics", "limitTimeDay", "30", "统计多长时间内的信息"),
    minsu_maliceOrdeNum("order", "maliceOrderNum", "5", "恶意订单数量"),
    minsu_isOpenMsg("order","isOpenMsg","0","下单提示版本升级开关"),
    minsu_isAddUrl("evaluate","isAddUrl","0","评价版本上线后增加短链开关"),
    minsu_mobileList("job", "mobileList", "15010386533,18701482472", "张少斌,杨东"),
    minsu_mapType("map", "mapType", "1", "地图类型，1：百度，2：谷歌，3：高德"),
    minsu_todayDiscountStartTime("house", "todayDiscountStartTime", "12:00", "今夜特价开始时间 "),
    minsu_Municipalities ("house","Municipalities","110000,120000",""),
    minsu_isOpenNewVersion("house", "isOpenNewVersion", "0", "发布房源原生化打开新版本0=未打开 1=打开"),
    changzu_chat_cardActivity_switch("message","isOpenCardActivity","0","长租找找聊天卡片活动0=未打开 1=打开"),
    minsu_couponWarnPhone("minsu", "warnPhone", "", "发送短信提醒优惠券数量过少"),
    minsu_order_checkin_coupon_mobile("order", "orderCheckInCouponMobile", "", "发送短信号码给运营人员"),
    minsu_hall_switch("house","isOpenHall","0","发布房源共享客厅入口控制开关 0=未打开 1=打开"),
    minsu_devAndOptPhoneList("minsu", "devAndOptPhoneList", "15010386533,18701482472,13506152879,18801496650", "研发+运营，默认：张少斌,杨东,季晓静,铁欢欢"),
    minsu_operationPhoneList("minsu", "operationPhoneList", "13506152879,18801496650", "运营，默认：季晓静,铁欢欢"),
    zyu_isOpenSmart ("zyu", "isOpenSmart", "0", "是否开启智能设备 0：否，1：是 "),
    zyu_isValidAge ("zyu", "isValidAge", "1", "是否验证年龄 0:不验证 1:验证 ");


    private String type;
    private String code;
    private String defaultValue;
    private String notes;

    private EnumMinsuConfig(String type, String code, String defaultValue, String notes) {
        this.code = code;
        this.type = type;
        this.notes = notes;
        this.defaultValue = defaultValue;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNotes() {
        return this.notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getDefaultValue() {
        return this.defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }


    /**
     * 获取当前的默认值
     * @param type
     * @param code
     * @return
     */
    public static EnumMinsuConfig getConfig(String type, String code){
        if (Check.NuNStr(type) || Check.NuNStr(code)){
            return null;
        }
        for (EnumMinsuConfig config : EnumMinsuConfig.values()) {
            if(type.equals(config.getType()) && code.equals(config.getCode())){
                return config;
            }
        }
        return null;
    }

}
