package com.ziroom.zrp.service.trading.valenum.delivery;


import java.util.LinkedHashMap;
import java.util.Map;

/**
 * <p>物业交割状态</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd
 * @version 1.0
 * @Date Created in 2017年09月14日 15:00
 * @since 1.0
 */
public enum  DeliveryStateEnum {

    WJG(0,"未交割"),
    JJG(1,"已交割");

    private int code;

    private String name;

    DeliveryStateEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static Map<Integer,String> getSelectMap(){
        Map<Integer,String> map = new LinkedHashMap<>();
        for (DeliveryStateEnum deliveryStateEnum : DeliveryStateEnum.values()){
            map.put(deliveryStateEnum.getCode(),deliveryStateEnum.getName());
        }
        return map;
    }
}
