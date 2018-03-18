package com.ziroom.zrp.service.houses.valenum;

import com.zra.common.vo.base.BaseItemVo;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>物品类型枚举</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd
 * @version 1.0
 * @Date Created in 2017年09月21日 14:38
 * @since 1.0
 */
public enum ItemTypeEnum {

    FURNITURE(0,"家具"),
    HOUSE(1,"家居"),
    BIG_JD(2,"家电"),
    LITTLEJD(3,"工程"),
    ITEM_NET(4,"网络"),
    ITEM_LOCK(5,"锁具"),
    PUBLIC_JD(6,"公共家电"),
    PUBLIC_JJ(7,"公共家具"),
    PUBLIC_HOUSE(8,"公区家居"),
    PUBLIC_PROJECT(9,"公区工程"),
    PUBLIC_NET(10,"公区网络"),
    PUBLIC_LOCK(11,"公区锁具");

    ItemTypeEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    private int code;

    private String name;

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    /**
     * 根据code获取枚举
     * @author jixd
     * @created 2017年09月21日 15:02:19
     * @param
     * @return
     */
    public static ItemTypeEnum getByCode(int code){
        for(final ItemTypeEnum itemTypeEnum : ItemTypeEnum.values()){
            if(itemTypeEnum.getCode()== code){
                return itemTypeEnum;
            }
        }
        return null;
    }

    public static List<BaseItemVo> getList(){
        List<BaseItemVo> list = new ArrayList<>();
        for (final ItemTypeEnum itemTypeEnum : ItemTypeEnum.values()){
               BaseItemVo baseItemVo = new BaseItemVo();
               baseItemVo.setCode(itemTypeEnum.getCode());
               baseItemVo.setName(itemTypeEnum.getName());
            list.add(baseItemVo);
        }
        return list;
    }

}
