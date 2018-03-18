package com.ziroom.zrp.trading.entity;

import java.util.Date;

import com.asura.framework.base.entity.BaseEntity;
import lombok.Data;

/**
 * 
 * <p>智能水表抄表记录</p>
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
public class IntellectWatermeterReadEntity extends BaseEntity{
    /**
	 * 
	 */
	private static final long serialVersionUID = 7713016192277359878L;

	/**
     * 唯一标识
     */
    private Integer id;

    /**
     * 业务标识
     */
    private String fid;

    /**
     * 项目id
     */
    private String projectId;

    /**
     * 房间标识
     */
    private String roomId;

    /**
     * 抄表类型 0-定时 1-新签 2-解约 3-到期 4-续约 5-人工
     * @see com.ziroom.zrp.service.trading.valenum.waterwatt.WaterwattReadTypeEnum
     */
    private Integer readType;

    /**
     * 触发抄表的合同id
     */
    private String contractId;

    /**
     * 抄表状态 0-成功 1-失败
     * @see com.ziroom.zrp.service.trading.valenum.waterwatt.WaterwattReadStatusEnum
     */
    private Integer readStatus;

    /**
     * 抄表示数
     */
    private Double meterReading;

    /**
     * 抄表时间
     */
    private Date readTime;

    /**
     * 上期抄表示数
     */
    private Double preMeterReading;

    /**
     * 上期抄表时间
     */
    private Date preReadTime;

    /**
     * 人工处理员工号
     */
    private String handleId;

    /**
     * 人工处理员工姓名
     */
    private String handleName;

    /**
     * 处理时间
     */
    private Date handleTime;

    /**
     * 创建时间
     */
    private Date createTime;

}