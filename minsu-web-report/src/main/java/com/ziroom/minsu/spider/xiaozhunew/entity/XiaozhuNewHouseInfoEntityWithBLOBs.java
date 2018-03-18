package com.ziroom.minsu.spider.xiaozhunew.entity;

public class XiaozhuNewHouseInfoEntityWithBLOBs extends XiaozhuNewHouseInfoEntity {
    /**
     * 个性描述
     */
    private String personalDesc;

    /**
     * 内部情况
     */
    private String indoorDesc;

    /**
     * 交通情况
     */
    private String trafficDesc;

    /**
     * 周边情况
     */
    private String surroundDesc;

    /**
     * 房东对房客的要求
     */
    private String requireRule;

    /**
     * 预订条款
     */
    private String bookRule;

    public String getPersonalDesc() {
        return personalDesc;
    }

    public void setPersonalDesc(String personalDesc) {
        this.personalDesc = personalDesc == null ? null : personalDesc.trim();
    }

    public String getIndoorDesc() {
        return indoorDesc;
    }

    public void setIndoorDesc(String indoorDesc) {
        this.indoorDesc = indoorDesc == null ? null : indoorDesc.trim();
    }

    public String getTrafficDesc() {
        return trafficDesc;
    }

    public void setTrafficDesc(String trafficDesc) {
        this.trafficDesc = trafficDesc == null ? null : trafficDesc.trim();
    }

    public String getSurroundDesc() {
        return surroundDesc;
    }

    public void setSurroundDesc(String surroundDesc) {
        this.surroundDesc = surroundDesc == null ? null : surroundDesc.trim();
    }

    public String getRequireRule() {
        return requireRule;
    }

    public void setRequireRule(String requireRule) {
        this.requireRule = requireRule == null ? null : requireRule.trim();
    }

    public String getBookRule() {
        return bookRule;
    }

    public void setBookRule(String bookRule) {
        this.bookRule = bookRule == null ? null : bookRule.trim();
    }
}