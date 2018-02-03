package com.ziroom.minsu.services.house.entity;

/**
 * <p>今夜特价实体</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author wangwentao 2017/5/10
 * @version 1.0
 * @since 1.0
 */
public class ToNightDiscount {

    /**
     * 开抢时间
     */
    private String openTime;

    /**
     * 开抢时间提示文案
     */
    private String openTimeTips;

    /**
     * 活动进行中提示文案
     */
    private String goingTips;

    /**
     * 开抢倒计时 :单位毫秒，当前时间超过开抢时间返回为0
     */
    private Long remainTime;

    /**
     * 今夜房源价格
     */
    private Integer tonightPrice;

    /**
     * 今夜特价折扣值
     */
    private Double tonightDiscount;

    /**
     * 名称
     */
    private String tipsNname;

    /**
     * 截至时间
     */
    private String deadlineTime;

    /**
     * 截至时间文案
     */
    private String deadlineTimeTips;

    /**
     * 结束倒计时 :单位毫秒，当前时间超过截止时间返回为0
     */
    private Long deadlineRemainTime;


    public String getGoingTips() {
        return goingTips;
    }

    public void setGoingTips(String goingTips) {
        this.goingTips = goingTips;
    }

    public String getOpenTimeTips() {
        return openTimeTips;
    }

    public void setOpenTimeTips(String openTimeTips) {
        this.openTimeTips = openTimeTips;
    }

    public String getTipsNname() {
        return tipsNname;
    }

    public void setTipsNname(String tipsNname) {
        this.tipsNname = tipsNname;
    }

    public String getOpenTime() {
        return openTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }

    public Long getRemainTime() {
        return remainTime;
    }

    public void setRemainTime(Long remainTime) {
        this.remainTime = remainTime;
    }

    public Integer getTonightPrice() {
        return tonightPrice;
    }

    public void setTonightPrice(Integer tonightPrice) {
        this.tonightPrice = tonightPrice;
    }

    public Double getTonightDiscount() {
        return tonightDiscount;
    }

    public void setTonightDiscount(Double tonightDiscount) {
        this.tonightDiscount = tonightDiscount;
    }

    public String getDeadlineTime() {
        return deadlineTime;
    }

    public void setDeadlineTime(String deadlineTime) {
        this.deadlineTime = deadlineTime;
    }

    public String getDeadlineTimeTips() {
        return deadlineTimeTips;
    }

    public void setDeadlineTimeTips(String deadlineTimeTips) {
        this.deadlineTimeTips = deadlineTimeTips;
    }

    public Long getDeadlineRemainTime() {
        return deadlineRemainTime;
    }

    public void setDeadlineRemainTime(Long deadlineRemainTime) {
        this.deadlineRemainTime = deadlineRemainTime;
    }
}
