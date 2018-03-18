package com.ziroom.zrp.service.houses.entity;

import java.io.Serializable;

/**
 * <p>户型vo</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author yd
 * @version 1.0
 * @Date Created in 2017年12月06日 10:42
 * @since 1.0
 */
public class HouseTypeVo implements Serializable{


    private static final long serialVersionUID = 1369425015377405396L;

    /**
     * 户型id
     */
    private  String houseTypeId;

    /**
     * 户型名称
     */
    private  String houseTypeName;


    public String getHouseTypeId() {
        return houseTypeId;
    }

    public void setHouseTypeId(String houseTypeId) {
        this.houseTypeId = houseTypeId;
    }

    public String getHouseTypeName() {
        return houseTypeName;
    }

    public void setHouseTypeName(String houseTypeName) {
        this.houseTypeName = houseTypeName;
    }
}
