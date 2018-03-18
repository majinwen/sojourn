package com.zra.repair.entity;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

@Data
@ToString
public class ZryRepairOrderLog implements Serializable {

    private static final long serialVersionUID = -7810658359525260460L;

    /**
     * 主键id
     */
    private Integer id;

    /**
     * 逻辑fid
     */
    private String fid;

    /**
     * 维修单fid
     */
    private String repairOrder;

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

    /**
     * 取消原因（家装维修枚举）
     */
    private Integer cancelReason;

    /**
     * 操作时间
     */
    private Timestamp createDate;

}