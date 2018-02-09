package com.ziroom.minsu.troy.common.valenum;

/**
 * <p>fullcalendar 属性</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author JIXD on 2017/03/15.
 * @version 1.0
 * @since 1.0
 */
public enum  FullCalendarEnum {
    IDLE_COLOR("color","#f3f3f4"),
    TEXT_COLOR("color","black"),
    LAN_lOCK_COLOR("color","#ffa000"),
    ORDER_lOCK_COLOR("color","#ddd"),
    SYS_LOCK_COLOR("color","#ff0000");
    



    FullCalendarEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }


    /** code */
    private String key;

    /** 名称 */
    private String value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
