package com.ziroom.zrp.trading.entity;

import java.util.Date;

import com.asura.framework.base.entity.BaseEntity;
import lombok.Data;

/**
 * 
 * <p>智能水表清算记录</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author bushujie
 * @since 1.0
 * @version 1.0
 */
@Data
public class IntellectWatermeterClearEntity extends BaseEntity{
    /**
	 * 
	 */
	private static final long serialVersionUID = -1153272644830519429L;

	/**
     * 唯一标识
     */
    private Integer id;

    /**
     * 业务标识
     */
    private String fid;

    /**
     * 合同标识
     */
    private String contractId;

    /**
     * 起始示数
     */
    private Double startReading;

    /**
     * 清算示数
     */
    private Double clearingReading;

    /**
     * 单价
     */
    private Double unitPrice;

    /**
     * 金额 分
     */
    private Integer sumMoney;

    /**
     * 分摊因子
     */
    private Integer shareFactor;

    /**
     * 清算类型 0-定时清算 1-新签 2-新签清算 3-解约 4-解约清算 5-到期 6-到期清算 7-续约 8-续约清算 9-人工清算
     * @see com.ziroom.zrp.service.trading.valenum.waterwatt.WaterwattClearingTypeEnum
     */
    private Integer clearingType;

    /**
     * 结算状态 0-未结算 1-已结算 2-已转移
     * @see com.ziroom.zrp.service.trading.valenum.waterwatt.WaterwattSettlementStatusEnum
     */
    private Integer settlementStatus;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 是否删除 0-未删除 1-已删除
     */
    private Integer isDel;

}