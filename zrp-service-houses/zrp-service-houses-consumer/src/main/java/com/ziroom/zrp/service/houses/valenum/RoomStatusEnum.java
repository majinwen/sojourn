package com.ziroom.zrp.service.houses.valenum;

import com.ziroom.zrp.service.houses.entity.RoomStatuVo;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>房间状态枚举</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author lusp
 * @version 1.0
 * @Date Created in 2017年10月11日 10:18
 * @since 1.0
 */

public enum RoomStatusEnum {

    DZZ("0","待租中"),
    YCZ("1","已出租"),
    PZZ("2","配置中"),
    YXD("3","已下定"),
    SD("4","锁定"),
    YXJ("5","已下架"),
    YDJXZ("6","预定进行中"),
    QYJXZ("7","签约进行中"),
    KYD("8","可预订");

    RoomStatusEnum(String code,String name){
        this.code = code;
        this.name = name;
    }

    private String code;

    private String name;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    /**
     * 获取状态集合
     * @author yd
     * @created
     * @param
     * @return
     */
    public  static List<RoomStatuVo> getRoomStatuVo(){

        List<RoomStatuVo> list = new ArrayList<RoomStatuVo>();

        for (RoomStatusEnum roomStatusEnum:RoomStatusEnum.values()){
            RoomStatuVo roomStatuVo = new RoomStatuVo();
            roomStatuVo.setCurrentState(roomStatusEnum.getCode());
            roomStatuVo.setCurrentStateName(roomStatusEnum.getName());
            list.add(roomStatuVo);
        }

        return  list;
    }

    /**
     * 根据code 获取枚举
     * @author yd
     * @created
     * @param 
     * @return 
     */
    public  static  RoomStatusEnum getRoomStatusEnumByCode(String code){

        for (RoomStatusEnum roomStatusEnum:RoomStatusEnum.values()) {
            if (roomStatusEnum.getCode().equals(code)){
                return  roomStatusEnum;
            }
        }
        return  null;
    }
}
