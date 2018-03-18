package com.zra.common.constant;

/**
 * 查询日期枚举
 * @author huangy168@ziroom.com
 * @Date 2016年11月2日
 * @Time 下午7:57:55
 */
public enum QueryDateTypeEnum {

    TODAY(0, "今天"),
    YESTERDAY(1, "昨天"),
    LASTEST7DAYS(2, "最近7天"),
    LASTWEEK(3, "上周"),
    MONTH(4, "本月"),
    LASTMONTH(5, "上月");

    private Integer code;
    private String name;

    QueryDateTypeEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static QueryDateTypeEnum getByCode(int state){
        for(QueryDateTypeEnum dateTypeEnum: QueryDateTypeEnum.values()){
            if(dateTypeEnum.getCode() == state){
                return dateTypeEnum;
            }
        }
        return null;
    }

}
