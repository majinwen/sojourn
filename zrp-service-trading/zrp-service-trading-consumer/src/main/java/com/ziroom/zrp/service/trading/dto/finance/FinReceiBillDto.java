package com.ziroom.zrp.service.trading.dto.finance;

import com.google.common.collect.Lists;
import lombok.*;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * <p>应收账单Dto</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @Author phil
 * @Date Created in 2018年01月31日 17:02
 * @Version 1.0
 * @Since 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FinReceiBillDto implements Serializable {

    private static final long serialVersionUID = 6173086259708698040L;

    // 不需要付款计划约定默认值
    private final static Integer DEFAULT_PAYMENT_ID = -1;

    // 应收账单明细
    List<FinReceiBillDetailDto > finReceiBillDetailDtos;

    /**
     * 业务id
     */
    private String fid;

    /**
     * 付款计划id
     *
     * 注：存在不需要付款计划Id的应收，
     *     约定<code>paymentId = DEFAULT_PAYMENT_ID</code>
     */
    private Integer paymentId;

    /**
     * 合同id
     *
     * 表：trentcontract
     */
    private String contractId;

    /**
     * 应收账单编号
     */
    private String billNumber;

    /**
     * 单据状态(0.未收款；1.部分收款；2.已收款；3.已作废)
     */
    private Integer billState;

    /**
     * 单据类型(0.合同计划收款；1.其它收款)
     */
    private Integer billType;

    /**
     * 生成方式(0.自动生成；1.手工录入)
     */
    private Integer genWay;

    /**
     * 第几次收款
     */
    private Integer payNum;

    /**
     * 计划收款日期
     */
    private Date planGatherDate;

    /**
     * 应收金额总计
     */
    private Double oughtTotalAmount;

    /**
     * 已收金额总计
     */
    private Double actualTotalAmount;

    /**
     * 开始收款周期
     */
    private Date startCycle;

    /**
     * 截止收款周期
     */
    private Date endCycle;

    /**
     * 制单人
     */
    private String createId;

    /**
     * 制单日期
     */
    private Date createTime;

    /**
     * 备注
     */
    private String commonts;

    /**
     * 城市id
     */
    private String cityId;

    /**
     * 更新人
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
     * 是否有效(0:失效；1:有效)
     */
    private Integer isValid;

    /**
     * 如果数据来源于解约，解约协议的id
     */
    private String surrenderId;

    /**
     * 支付订单号
     */
    private String paymentnum;

    /**
     * 计算违约金时间
     */
    private Date calcWyjTime;

    /**
     * 是否计算逾期违约金（0计算  1不计算），与应收账单状态、类型、日期等属性同时验证是否需要计算逾期违约金
     */
    private Integer isCalcWyj;

    /**
     * 项目Id
     */
    private String projectId;

    //========智能水电设备信息===========================
    // 设备充值示数
    private Double reading;
    // 设备类型
    private String deviceTypeName;

    public FinReceiBillDto addFinReceiBillDetailDto(@NonNull FinReceiBillDetailDto detailDto) {
        if (CollectionUtils.isEmpty(finReceiBillDetailDtos)) {
            finReceiBillDetailDtos = Lists.newArrayList();
        }
        finReceiBillDetailDtos.add(detailDto);
        return this;
    }

    /**
     * 是否系统生成
     * @param yesOrNo
     * @return
     */
    public FinReceiBillDto createBySystem(@NonNull Boolean yesOrNo) {
        //生成方式(0.自动生成；1.手工录入)
        genWay = yesOrNo ? 0 : 1;
        return this;
    }
}
