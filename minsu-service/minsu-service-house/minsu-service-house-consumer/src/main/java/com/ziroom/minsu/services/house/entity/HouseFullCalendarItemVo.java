package com.ziroom.minsu.services.house.entity;

/**
 * <p>房源日历 tory使用</p>
 *
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd
 * @since 1.0
 * @version 1.0
 */
public class HouseFullCalendarItemVo {
    /**
     * 开始时间 yyyy-MM-dd
     */
    private String start;
    /**
     * 设置单元格标题  设置价格
     */
    private String title;
    /**
     * 设置颜色
     */
    private String color;
    /**
     * 渲染属性
     */
    private String rendering;
    /**
     * 日期价格
     */
    private Integer price;

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRendering() {
        return rendering;
    }

    public void setRendering(String rendering) {
        this.rendering = rendering;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}
