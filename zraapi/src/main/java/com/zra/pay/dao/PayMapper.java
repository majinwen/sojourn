package com.zra.pay.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.zra.common.dto.pay.BillDetailDto;
import com.zra.common.dto.pay.CostDetailDto;
import com.zra.common.dto.pay.MyContractDetailDto;
import com.zra.common.dto.pay.MyContractDto;
import com.zra.common.dto.pay.ToPayDto;
import com.zra.pay.entity.FinReceiBillDetailEntity;
import com.zra.pay.entity.FinReceiBillEntity;
import com.zra.pay.entity.FinsettleEntity;
import com.zra.pay.entity.PaymentParam;

/**
 * Created by cuigh6 on 2016/12/19.
 */
@Repository
public interface PayMapper {
    /**
     * 获取合同计划费用待支付列表 created by cuigh6
     * @param uid 用户标识
     * @return 待支付列表
     */
    List<ToPayDto> getConToPayList(String uid);

    /**
     * 获取其他账单待支付列表 created by cuigh6
     * @param uid 用户表示
     * @return 待支付列表
     */
    List<ToPayDto> getOtherToPayList(String uid);

    /**
     * 获取费用项 created by cuigh6
     * @param billFid 账单标识
     * @return 费用项
     */
    Map<String,Object> getCostItemValue(String billFid);

    /**
     * 账单详情 created by cuigh6
     * @param billFid 账单标识
     * @return 账单详情
     */
    BillDetailDto getBillDetail(String billFid);

    /**
     * 获取费用项列表 created by cuigh6
     * @param billFid 账单标识
     * @return
     */
    List<CostDetailDto> getCostItems(String billFid);

    /**
     * 查询合同列表 created by cuigh6
     * @param uid 用户标识
     * @return
     */
    List<MyContractDto> getContractList(String uid);

    /**
     * 获取合同详情 created by cuigh6
     * @param contractId 合同号
     * @return 返回合同详情列表
     */
    MyContractDetailDto getContractDetail(String contractId);

    /**
     * 获取合同账单列表 created by cuigh6
     * @param contractId 合同标识
     * @return
     */
    List<BillDetailDto> getContractBillList(String contractId);

    BigDecimal getPaidAmount(String billFid);
    
    /**
     * 获取房租已缴金额
     * @author tianxf9
     * @param billFid
     * @return
     */
    BigDecimal getPaidFZAmount(String billFid);

    Date getPaymentTime(String billFid);

    /**
     * 更新支付订单号到应收账单表
     * @param paramMap
     * @return
     */
    Integer updatePaymentNum(Map<String, Object> paramMap);

    /**
     * 通过支付账单号 查询应收账单
     * @param order_code
     * @return
     */
    FinReceiBillEntity getBillByPaymentNum(String order_code);

    /**
     * 获取结算方式
     * @param paramMap
     * @return
     */
    FinsettleEntity getFinsettle(Map<String, Object> paramMap);

    /**
     * 获取收款单详情
     * @param fid
     * @return
     */
    List<FinReceiBillDetailEntity> getFinReceiBillDetail(String fid);
    
    /**
     * 查询app可以使用卡券的应收详情
     * @author tianxf9
     * @param fid
     * @return
     */
    List<FinReceiBillDetailEntity> getCardCouponFinReceiBillDetail(String fid);

    Integer updateReceiBillDetail(FinReceiBillDetailEntity detail);

    Integer updateReceiBill(FinReceiBillEntity bill);

    FinReceiBillDetailEntity getYQWYJByBillFid(String billFid);
    /**
     * 查询应收账单应交房租金额
     * @author tianxf9
     * @param billFid
     * @return
     */
    FinReceiBillDetailEntity getYSFZByBillFid(String billFid);

    FinReceiBillDetailEntity getOneReceiBillDetail(String billFid);

    Integer insertReceiBillDetail(FinReceiBillDetailEntity entity1);

    Integer updateReceiBillDetailForYQWYJ(FinReceiBillDetailEntity entity);

    Integer updateReceiBillOughtAmount(FinReceiBillDetailEntity entity);

    Integer insertPaymentParam(PaymentParam paymentParam);

    FinReceiBillEntity getReceiBillByPaymentNum(String order_code);

    Integer updatePaymentParam(PaymentParam paymentParam);
    
    /**
     * @author tianxf9
     * @param paymentParam
     * @return
     */
    Integer updateUnCallBackPayMsg(PaymentParam paymentParam);

    List<ToPayDto> getConToPayListExpire(String uid);
    
    /**
     * 根据支付账单号获取支付信息.
     * wangws21 2017-4-18
     * @param paymentNum 支付订单号
     * @return 支付信息
     */
    PaymentParam getPaymentParamByPaymentNum(String paymentNum);
    
    /**
     * 通过应收账单id查询应收账单
     * @author tianxf9
     * @param order_code
     * @return
     */
    FinReceiBillEntity getReceBillByFid(String billFid);
}
