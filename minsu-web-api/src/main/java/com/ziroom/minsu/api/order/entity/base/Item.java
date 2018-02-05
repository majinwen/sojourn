package com.ziroom.minsu.api.order.entity.base;

/**
 * <p>基本项</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd
 * @version 1.0
 * @Date Created in 2017年08月01日 11:31
 * @since 1.0
 */
public class Item {
    /**
     * 名字
     */
    private String name;
    /**
     * 金额
     */
    private String value;

    public Item(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public Item() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
