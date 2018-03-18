package com.zra.repair.entity.dto;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * <p>废弃报修单传输实体</p>
 *
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author fengbo.yue
 * @Date Created in 2017年09月29日 17:23
 * @since 1.0
 * @version 1.0
 */
@Data
@ToString
public class ZryRepairOrderCancelDto implements Serializable {

    private static final long serialVersionUID = 8761252321979918527L;

    /**
     * 维修单号(家装维修订单号)
     */
    private String repairOrder;

    /***
     * 维修单fId
     */
    private String repairOrderFid;

    /**
     * 前状态
     */
    private Integer fromStatus;

    /**
     * 后状态
     */
    private Integer toStatus;

    /**
     * 操作人fid'
     */
    private String createFid;

    /**
     * 备注
     */
    private String remark;

    /***
     * 取消原因
     */
    private Integer cancelReason;

    public boolean cancelled() {
        // 已取消状态 : 10
        return this.getFromStatus() != null && 10 == this.getFromStatus();
    }
}
