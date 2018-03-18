package com.zra.common.enums;
public enum CycleTypeEnum {
	
	WEEK(1, "周报"), // 周报
	MONTH(2, "月报"),// 月报
	SEASON(3, "季报"), // 季报
	YEAR(4, "年报"); // 年报

    private int code;
    private String name;

    CycleTypeEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static CycleTypeEnum getByCode(int state){
        for(CycleTypeEnum roomStateEnum: CycleTypeEnum.values()){
            if(roomStateEnum.getCode() == state){
                return roomStateEnum;
            }
        }
        return null;
    }
}