package com.ziroom.minsu.services.order.dto;

import com.ziroom.minsu.services.common.dto.PageRequest;

/**
 * <p>订单快照请求参数</p>
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
public class SnapshotRequest extends PageRequest{
	  /** 订单编号 */
    private String orderSn;
    /** 订单状态
     * 10:待确认
     * 20:待入住
     * 30:强制取消
     * 31:房东已拒绝
     * 32:房客取消
     * 33:未支付超时取消
     * 40:已入住
     * 50:退房中
     * 60:待用户确认额外消费
     * 71:提前退房完成
     * 72:正常退房完成 */
    private Integer orderStatus;

    /** 支付状态 */
    private Integer payStatus;

    /** 房东Uid */
    private String landlordUid;
    /** 用户Uid */
    private String userUid;

    /** 是否删除 0：否，1：是 */
    private Integer isDel;
}
