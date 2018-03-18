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
public enum DirectionEnum {

    EAST(1, "东"),
    SOUTH(2, "南"),
    WEST(3, "西"),
    NORTH(4, "北");

    private Integer code;
    private String name;

    DirectionEnum(Integer code, String name) {
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

    public static DirectionEnum getByCode(int state){
        for(DirectionEnum directionEnum: DirectionEnum.values()){
            if(directionEnum.getCode() == state){
                return directionEnum;
            }
        }
        return null;
    }

}
