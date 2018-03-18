package com.zra.common.constant;

/**
 * <p></p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author renxw
 * @version 1.0
 * @date 2016/8/8 15:54
 * @since 1.0
 */
public enum FloorEnum {

    ONE(1, "1层"),
    TWO(2, "2层"),
    THREE(3, "3层"),
    FOUR(4, "4层"),
    FIVE(5, "5层"),
    SIX(6, "6层");

    private Integer code;
    private String name;

    FloorEnum(Integer code, String name) {
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

    public static FloorEnum getByCode(int state){
        for(FloorEnum floorEnum: FloorEnum.values()){
            if(floorEnum.getCode() == state){
                return floorEnum;
            }
        }
        return null;
    }

}
