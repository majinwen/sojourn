package com.ziroom.minsu.services.house.photog.vo;

import com.ziroom.minsu.entity.house.HouseRoomMsgEntity;
import com.ziroom.minsu.entity.photographer.PhotographerBookOrderEntity;

import java.util.List;

/**
 * <p>摄影师列表</p>
 *
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd
 * @since 1.0
 * @version 1.0
 */
public class PhotographerBookOrderVo extends PhotographerBookOrderEntity{
    /**
     * 房源整租合租
     */
    private Integer rentWay;


    public Integer getRentWay() {
        return rentWay;
    }

    public void setRentWay(Integer rentWay) {
        this.rentWay = rentWay;
    }

}
