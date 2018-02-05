package com.ziroom.minsu.api.order.entity;

/**
 * <p>最近的旅程的实体</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/9/20.
 * @version 1.0
 * @since 1.0
 */
public class OrderItemLastApiVo extends OrderItemApiVo{


    /** 房源地址 */
    private String houseAddr;


    /** 联系房东 */
    private String landlordMobile;

    public String getHouseAddr() {
        return houseAddr;
    }

    public void setHouseAddr(String houseAddr) {
        this.houseAddr = houseAddr;
    }

    public String getLandlordMobile() {
        return landlordMobile;
    }

    public void setLandlordMobile(String landlordMobile) {
        this.landlordMobile = landlordMobile;
    }
}
