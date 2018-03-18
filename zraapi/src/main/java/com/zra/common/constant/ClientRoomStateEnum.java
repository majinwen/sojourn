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
public enum ClientRoomStateEnum {

    WAITRENT(0, "马上入住"),
    BOOKABLE(8, "1个月左右可入住");

    private Integer code;
    private String name;

    ClientRoomStateEnum(Integer code, String name) {
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

    public static ClientRoomStateEnum getByCode(int state){
        for(ClientRoomStateEnum clientRoomStateEnum:ClientRoomStateEnum.values()){
            if(clientRoomStateEnum.getCode() == state){
                return clientRoomStateEnum;
            }
        }
        return null;
    }

}
