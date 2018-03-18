package com.ziroom.zrp.service.trading.dto.contract;

import com.ziroom.zrp.trading.entity.RentContractEntity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

/**
 * <p>合同管理 返回结果</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd
 * @version 1.0
 * @Date Created in 2017年11月27日 16:10
 * @since 1.0
 */
@Data
public class ContractManageDto extends RentContractEntity {
    /**
     * 物业交割状态   0：未交割；1:已交割     @See com.ziroom.zrp.service.trading.valenum.delivery.DeliveryStateEnum
     */
    private Integer deliveryState;
    /**
     * 首次审核时间
     */
    private Date firstAuditDate;
    /**
     * 审核通过时间
     */
    private Date approveDate;
    /**
     * 企业或者个人  0未核销全部账单  1已核销全部账单
     */
    private int isFinishBill;
    /**
     * 是否完成 应收账单得同步  0未完成  1 已完成
     */
    private int isFinishRecei;

    /**
     * 合同状态展示
     */
    private String conStatusShow;
    /**
     * 审核状态展示
     */
    private String conAuditStateShow;
    /**
     * 客户类型展示
     */
    private String customerTypeShow;
    /**
     * 合同类型
     */
    private String conTypeShow;
    /**
     * 参加活动列表
     */
    private String actNames;


}
