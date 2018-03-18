package com.zra.common.enums;
public enum RoomStateEnum {

    WAITRENT(0, "待租中"),
    RENTAL(1, "已出租"), // 已出租
    CONFIG(2, "配置中"),// 配置中
    ORDER(3, "已下定"), // 已下定
    LOCKED(4, "锁定") , // 锁定
    SOLDOUT(5, "已下架") , // 已下架
    ORDERING(6, "预定进行中"), // 预定进行中
    SIGNING(7, "签约进行中") ,// 签约进行中
    BOOKABLE(8, "可预订"); // 可预订

    private Integer code;
    private String name;

    RoomStateEnum(Integer code, String name) {
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

    public static RoomStateEnum getByCode(int state){
        for(RoomStateEnum roomStateEnum: RoomStateEnum.values()){
            if(roomStateEnum.getCode() == state){
                return roomStateEnum;
            }
        }
        return null;
    }
}