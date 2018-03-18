package com.zra.common.enums;

public enum GoalTypeEnum {

    WEEK("1", "周报"),
    MONTH("2", "月报"),
    QUARTER("3", "季报"),
    YEAR("4", "年报");
    
    private String type;
    
    private String desc;
    
    GoalTypeEnum(String type, String desc) {
        this.type =  type;
        this.desc = desc;
    }
    
    public static boolean isWeek(String type) {
        return GoalTypeEnum.WEEK.getType().equals(type);
    }
    
    public static boolean isMonth(String type) {
        return GoalTypeEnum.MONTH.getType().equals(type);
    }

    public static boolean isQuarter(String type) {
        return GoalTypeEnum.QUARTER.getType().equals(type);
    }
    
    public static boolean isYear(String type) {
        return GoalTypeEnum.YEAR.getType().equals(type);
    }
    
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
    
}
