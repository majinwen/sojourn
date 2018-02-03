package com.ziroom.minsu.services.order.entity;

import com.ziroom.minsu.valenum.house.RentWayEnum;

/**
 * <p>订单快照返回实体</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author yd
 * @since 1.0
 * @version 1.0
 */
public class SnapshotVo {
	
	   /** 订单编号*/
    private String orderSn;

    /** 房源 fid */
    private String houseFid;

    /** 房间fid */
    private String roomFid;

    /** 床 fid */
    private String bedFid;

    /** 房源名称 */
    private String houseName;

    /** 房间名称  */
    private String roomName;

    /** 床编号  */
    private String bedSn;

    /** 房源地址  */
    private String houseAddr;

    /** 图片路径 */
    private String picUrl;

    /**
     * 出租类型
     * @see RentWayEnum
     */
    private Integer rentWay;

    /** 出租价格 */
    private Integer price;


    /**
     * 订单类型
     * @see com.ziroom.minsu.valenum.order.OrderTypeEnum
     */
    private Integer orderType;

    /** 优惠规则 */
    private String discountRulesCode;

    /** 押金规则 */
    private String depositRulesCode;

    /** 退订政策 */
    private String checkOutRulesCode;

    /** 入住时间 值必须是标准的时间格式 12:00:00 */
    private String checkInTime;

    /** 退订时间 值必须是标准的时间格式 12:00:00 */
    private String checkOutTime;
}
