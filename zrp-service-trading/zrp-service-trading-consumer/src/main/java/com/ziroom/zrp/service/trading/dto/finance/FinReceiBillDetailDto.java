package com.ziroom.zrp.service.trading.dto.finance;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>应收账单明细表Dto</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @Author phil
 * @Date Created in 2018年01月31日 17:44
 * @Version 1.0
 * @Since 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FinReceiBillDetailDto implements Serializable{

    private static final long serialVersionUID = 2457091946358621709L;

    /**
     * 主键
     */
    private Integer id;

    /**
     * 业务id
     */
    private String fid;

    /**
     * 应收账单fid
     */
    private String billFid;

    /**
     * 付款计划id
     */
    private Integer paymentId;

    /**
     * 费用项目ID
     */
    private Integer expenseItemId;

    /**
     * 应收金额
     */
    private Double oughtAmount;

    /**
     * 已收金额
     */
    private Double actualAmount;

    /**
     * 城市id
     */
    private String cityId;

    /**
     * 房间id
     */
    private String roomId;

    /**
     * 创建人id
     */
    private String createId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新人id
     */
    private String updateId;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 是否删除(0:未删除；1:已删除)
     */
    private Integer isDel;

    /**
     * 是否有效(0:无效；1:有效)
     */
    private Integer isValid;

    /**
     * 备注
     */
    private String remark;

    /**
     * 财务账单号
     */
    private String billNum;

    /**
     * 财务账单类型
     */
    private String billType;

    /**
     * 财务状态 1-成功 2-失败
     */
    private Integer status;

    /**
     * 财务失败信息
     */
    private String failMsg;
}
