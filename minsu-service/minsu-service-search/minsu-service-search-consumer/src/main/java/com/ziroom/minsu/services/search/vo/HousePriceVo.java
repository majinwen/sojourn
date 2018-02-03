package com.ziroom.minsu.services.search.vo;

import com.asura.framework.base.entity.BaseEntity;

/**
 * <p>房源的特殊价格</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/5/25.
 * @version 1.0
 * @since 1.0
 */
public class HousePriceVo extends BaseEntity{

    private static final long serialVersionUID = 546532465346546L;

    /**
     * 房源的fid
     */
    private String houseFid;

    /**
     * 房间的id
     */
    private String roomFid;

    /**
     * 价格时间
     */
    private String setTime;

    /**
     * 价格
     */
    private Integer price;


    public String getHouseFid() {
        return houseFid;
    }

    public void setHouseFid(String houseFid) {
        this.houseFid = houseFid;
    }

    public String getRoomFid() {
        return roomFid;
    }

    public void setRoomFid(String roomFid) {
        this.roomFid = roomFid;
    }

    public String getSetTime() {
        return setTime;
    }

    public void setSetTime(String setTime) {
        this.setTime = setTime;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}
