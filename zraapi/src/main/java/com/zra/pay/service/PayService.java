package com.zra.pay.service;

import com.zra.common.dto.pay.*;
import com.zra.common.enums.BillTypeEnum;
import com.zra.common.utils.PropUtils;
import com.zra.item.service.ItemService;
import com.zra.pay.dao.PayMapper;
import com.zra.pay.entity.FinReceiBillDetailEntity;
import com.zra.pay.entity.FinReceiBillEntity;
import com.zra.pay.entity.FinsettleEntity;
import com.zra.pay.entity.PaymentParam;
import com.zra.pay.utils.CalculateYQWYJ;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import com.apollo.logproxy.slf4j.LoggerFactoryProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by cuigh6 on 2016/12/19.
 */
@Service
public class PayService {
    private static final Logger LOGGER = LoggerFactoryProxy.getLogger(PayService.class);

    @Autowired
    private PayMapper payMapper;

    @Autowired
    private ItemService itemService;

    /**
     * 获取待支付列表 created by cuigh6
     * @param uid 用户标识
     * @return 待支付列表
     */
    public List<ToPayDto> getToPayList(String uid) {
        List<ToPayDto> dtos = new LinkedList<>();
        dtos.addAll(this.payMapper.getConToPayList(uid));//最近一期待支付
        dtos.addAll(this.payMapper.getConToPayListExpire(uid));//所有逾期的
        dtos.addAll(this.payMapper.getOtherToPayList(uid));//其他费用
        return dtos;
    }

    /**
     * 获取费用项列表
     * @param billFid 标识
     * @return
     */
    public Map<String, Object> getCostItemValue(String billFid) {
        return this.payMapper.getCostItemValue(billFid);
    }

    /**
     * 获取账单详情
     * @param billFid
     * @return
     */
    public BillDetailDto getBillDetail(String billFid) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        BillDetailDto billDetailDto = this.payMapper.getBillDetail(billFid);
        BigDecimal actualAmount = this.payMapper.getPaidAmount(billFid);
        if (actualAmount == null) {
            actualAmount = new BigDecimal(0);
        }
        billDetailDto.setActualAmount(actualAmount);
        billDetailDto.setActualAmountStr(String.valueOf(actualAmount.setScale(2,BigDecimal.ROUND_DOWN)));
        billDetailDto.setOughtTotalAmountStr(String.valueOf(billDetailDto.getOughtTotalAmount().setScale(2,BigDecimal.ROUND_DOWN)));
        billDetailDto.setGatherDateStr(billDetailDto.getGatherDate() == null ? null : sdf.format(billDetailDto.getGatherDate()));
        billDetailDto.setPendingAmount(billDetailDto.getOughtTotalAmount().subtract(billDetailDto.getActualAmount() == null ? BigDecimal.ZERO : billDetailDto.getActualAmount()));
        billDetailDto.setPendingAmountStr(String.valueOf(billDetailDto.getPendingAmount().setScale(2, BigDecimal.ROUND_DOWN)));
        return billDetailDto;
    }

    /**
     * 获取费用项列表
     * @param billFid
     * @return
     */
    public List<CostDetailDto> getCostItems(String billFid, BillDetailDto detailDto) {
       List< CostDetailDto> costDetailDtos = this.payMapper.getCostItems(billFid);
        //added by wangxm113 添加逾期违约金
       if (CalculateYQWYJ.ifCalYQWYJ(detailDto.getGatherDate()) && detailDto.getBillType() == BillTypeEnum.CONTRACT_PLAN_COST.getIndex()) {
                FinReceiBillDetailEntity entity = payMapper.getYQWYJByBillFid(billFid);//查询应收账单明细
                BigDecimal yqwyjOld = entity == null ? BigDecimal.ZERO : entity.getOughtAmount();//以前计算的逾期违约金
                BigDecimal yqwyjB = new BigDecimal(CalculateYQWYJ.calYQWYJByMS(billFid));//实时计算的逾期违约金
                detailDto.setOughtTotalAmount(detailDto.getOughtTotalAmount().subtract(yqwyjOld).add(yqwyjB));
                detailDto.setOughtTotalAmountStr(String.valueOf(detailDto.getOughtTotalAmount().setScale(2, BigDecimal.ROUND_DOWN)));

                if (entity == null) {
                    CostDetailDto costDetailDto = new CostDetailDto();
                    costDetailDto.setKey(itemService.getYQWYJItemName());
                    costDetailDto.setAmount(yqwyjB);
                    costDetailDtos.add(costDetailDto);
                } else {
                    for (CostDetailDto costDetailDto : costDetailDtos) {
                        if ("逾期违约金".equals(costDetailDto.getKey())) {
                            costDetailDto.setAmount(yqwyjB);
                        }
                    }
                }
            }
        //end
        for (CostDetailDto dto: costDetailDtos){
           BigDecimal amount = dto.getAmount();
           String result;
           if (amount.compareTo(BigDecimal.ZERO) < 0) {
               result = "-¥" + amount.abs().setScale(2,BigDecimal.ROUND_DOWN).toPlainString();
           } else {
               result = "¥" + amount.setScale(2,BigDecimal.ROUND_DOWN).toPlainString();
           }
           dto.setValue(result);
       }
        return costDetailDtos;
    }
    
    
    
    /**
     * 对接卡券0608
     * 获取费用项列表
     * @author tianxf9
     * @param billFid
     * @return
     */
    public List<CostDetailDto> getNewCostItems(String billFid, BillDetailDto detailDto) {
       List< CostDetailDto> costDetailDtos = this.payMapper.getCostItems(billFid);
        //added by wangxm113 添加逾期违约金
        if (CalculateYQWYJ.ifCalYQWYJ(detailDto.getGatherDate()) && detailDto.getBillType() == BillTypeEnum.CONTRACT_PLAN_COST.getIndex()) {
            FinReceiBillDetailEntity entity = payMapper.getYQWYJByBillFid(billFid);//查询应收账单明细
            BigDecimal yqwyjOld = entity == null ? BigDecimal.ZERO : entity.getOughtAmount();//以前计算的逾期违约金
            BigDecimal yqwyjB = new BigDecimal(CalculateYQWYJ.calYQWYJByMS(billFid));//实时计算的逾期违约金
            detailDto.setOughtTotalAmount(detailDto.getOughtTotalAmount().subtract(yqwyjOld).add(yqwyjB));
            detailDto.setOughtTotalAmountStr(String.valueOf(detailDto.getOughtTotalAmount().setScale(2, BigDecimal.ROUND_DOWN)));

            if (entity == null) {
                CostDetailDto costDetailDto = new CostDetailDto();
                costDetailDto.setKey(itemService.getYQWYJItemName());
                costDetailDto.setAmount(yqwyjB);
                costDetailDtos.add(costDetailDto);
            } else {
                for (CostDetailDto costDetailDto : costDetailDtos) {
                    if ("逾期违约金".equals(costDetailDto.getKey())) {
                        costDetailDto.setAmount(yqwyjB);
                    }
                }
            }
        }
        //end
        //添加已缴总计金额显示项 added by wangxm113
        if (detailDto.getActualAmount().compareTo(BigDecimal.ZERO) > 0) {
            CostDetailDto costDetailDto = new CostDetailDto();
            costDetailDto.setKey("已缴金额总计");
            costDetailDto.setAmount(detailDto.getActualAmount().negate());
            costDetailDtos.add(costDetailDto);
        }
        //end
        for (CostDetailDto dto: costDetailDtos){
           BigDecimal amount = dto.getAmount();
           String result;
           if (amount.compareTo(BigDecimal.ZERO) < 0) {
               result = "-¥" + amount.abs().setScale(2,BigDecimal.ROUND_DOWN).toPlainString();
           } else {
               result = "¥" + amount.setScale(2,BigDecimal.ROUND_DOWN).toPlainString();
           }
           dto.setValue(result);
       }
        return costDetailDtos;
    }


    /**
     * 获取合同列表 created by cuigh6
     * @param uid 用户标识
     * @return
     */
    public List<MyContractDto> getMyContractList(String uid) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        List<MyContractDto> contractDtos = this.payMapper.getContractList(uid);
        for (MyContractDto contractDto : contractDtos) {
            contractDto.setEndDateStr(dateFormat.format(contractDto.getEndDate()));
            contractDto.setStartDateStr(dateFormat.format(contractDto.getStartDate()));
            contractDto.setRoomPriceStr(String.valueOf(contractDto.getRoomPrice().setScale(2,BigDecimal.ROUND_DOWN)));
            contractDto.setProjectImg(PropUtils.getString("PIC_PREFIX_URL")+contractDto.getProjectImg());
            contractDto.setContactTel("");
        }
        return contractDtos;
    }

    /**
     * 获取合同详情 created by cuigh6
     * @param contractId 合同标识
     * @return 合同详情
     */
    public MyContractDetailDto getContractDetail(String contractId) {
        MyContractDetailDto myContractDetailDto = this.payMapper.getContractDetail(contractId);
        myContractDetailDto.setProjectImg(PropUtils.getString("PIC_PREFIX_URL") + myContractDetailDto.getProjectImg());
        myContractDetailDto.setRoomPriceStr(String.valueOf(myContractDetailDto.getRoomPrice().setScale(2,BigDecimal.ROUND_DOWN)));
        return myContractDetailDto;
    }

    public MyContractDetailDto getContractInfo(String contractId) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日");
        MyContractDetailDto myContractDetailDto = this.payMapper.getContractDetail(contractId);
        myContractDetailDto.setRoomPriceStr(String.valueOf(myContractDetailDto.getRoomPrice()));
        myContractDetailDto.setProjectImg(PropUtils.getString("PIC_PREFIX_URL") + myContractDetailDto.getProjectImg());
        List<BillDetailDto> billDetailDtos = this.payMapper.getContractBillList(contractId);
        BillDetailDto showPayDto = null;
        for (BillDetailDto billDetailDto : billDetailDtos) {
            //added by wangxm113 添加逾期违约金
            if (billDetailDto.getState() != 2 && billDetailDto.getBillType() == BillTypeEnum.CONTRACT_PLAN_COST.getIndex()) {
                //added by wangxm113 start
                if (showPayDto == null) {
                    showPayDto = billDetailDto;
                } else {
                    if (billDetailDto.getPayNum() < showPayDto.getPayNum()) {
                        showPayDto = billDetailDto;
                    }
                }
                //added by wangxm113 end
                if (CalculateYQWYJ.ifCalYQWYJ(billDetailDto.getGatherDate())) {
                    FinReceiBillDetailEntity entity = payMapper.getYQWYJByBillFid(billDetailDto.getBillFid());//查询应收账单明细
                    BigDecimal yqwyjOld = entity == null ? BigDecimal.ZERO : entity.getOughtAmount();//以前计算的逾期违约金
                    BigDecimal yqwyjB = new BigDecimal(CalculateYQWYJ.calYQWYJByMS(billDetailDto.getBillFid()));//实时计算的逾期违约金
                    billDetailDto.setOughtTotalAmount(billDetailDto.getOughtTotalAmount().subtract(yqwyjOld).add(yqwyjB));
                }
            } else if (billDetailDto.getBillType() == BillTypeEnum.OTHER_COST.getIndex()) {//其它费用可以支付
                billDetailDto.setShowPay(0);//是否显示支付按钮(0:显示；1:不显示)
            }
            //end
            BigDecimal paidAmount=this.payMapper.getPaidAmount(billDetailDto.getBillFid());//获取已缴款金额
            if (paidAmount == null) {
                paidAmount = new BigDecimal(0);
            }
            billDetailDto.setGatherDateStr(billDetailDto.getGatherDate() == null ? null : dateFormat.format(billDetailDto.getGatherDate()));//格式化缴款日期
            billDetailDto.setActualAmountStr(String.valueOf(paidAmount.setScale(2,BigDecimal.ROUND_DOWN).toPlainString()));//实际收款金额
            billDetailDto.setOughtTotalAmountStr(String.valueOf(billDetailDto.getOughtTotalAmount().setScale(2,BigDecimal.ROUND_DOWN).toPlainString()));//应缴金额
            billDetailDto.setPendingAmountStr(String.valueOf(billDetailDto.getOughtTotalAmount().subtract(paidAmount).setScale(2,BigDecimal.ROUND_DOWN).toPlainString()));// 待缴金额
            //added by wangxm113
            Integer state = billDetailDto.getState();
            if (state == 2) {
                Date paymentTime = payMapper.getPaymentTime(billDetailDto.getBillFid());
                billDetailDto.setPaymentTime(paymentTime == null ? null : dateFormat.format(paymentTime));
            }
        }
        if (showPayDto != null) {
            showPayDto.setShowPay(0);
        }
        myContractDetailDto.setBillList(billDetailDtos);
        return myContractDetailDto;
    }

    public BigDecimal getPaidAmount(String billFid) {
        return this.payMapper.getPaidAmount(billFid);
    }
    
    public BigDecimal getPaidFZAmount(String billFid) {
    	return this.payMapper.getPaidFZAmount(billFid);
    }

    public Integer updatePaymentNum(String billFid, String payOrderNumber) {
        Map<String, Object> paramMap = new HashedMap();
        paramMap.put("billFid", billFid);
        paramMap.put("payOrderNumber", payOrderNumber);
        return this.payMapper.updatePaymentNum(paramMap);
    }

    public FinReceiBillEntity getBillByPaymentNum(String order_code) {
        return this.payMapper.getBillByPaymentNum(order_code);
    }

    /**
     * 获取结算方式
     * @param financeCode 财务编码 和支付平台的支付方式
     * @param companyCode 公司编码
     * @return
     */
    public FinsettleEntity getFinsettle(String financeCode, String companyCode) {
        Map<String, Object> paramMap = new HashedMap();
        paramMap.put("financeCode", financeCode);
        paramMap.put("companyCode", companyCode);
        return this.payMapper.getFinsettle(paramMap);
    }

    public List<FinReceiBillDetailEntity> getFinReceiBillDetai(String fid) {
        return this.payMapper.getFinReceiBillDetail(fid);
    }
    
    /**
     * 查询app支付可以使用卡券的应收详情（去除违约金）
     * @author tianxf9
     * @param fid
     * @return
     */
    public List<FinReceiBillDetailEntity> getCardCouponFinReceiBillDetail(String fid) {
    	return this.payMapper.getCardCouponFinReceiBillDetail(fid);
    }

    /**
     * 更新应收账单详情
     * @param detail
     */
    public Integer updateReceiBillDetail(FinReceiBillDetailEntity detail) {
        return this.payMapper.updateReceiBillDetail(detail);
    }

    /**
     * 更新应收账单
     * @param bill
     */
    public Integer updateReceiBill(FinReceiBillEntity bill) {
        return this.payMapper.updateReceiBill(bill);
    }

    public FinReceiBillDetailEntity getYQWYJByBillFid(String billFid){
        return payMapper.getYQWYJByBillFid(billFid);
    }
    
    /**
     * 查询应收账单的房租金额
     * @author tianxf9
     * @param billFid
     * @return
     */
    public FinReceiBillDetailEntity getYSFZByBillFid(String billFid) {
    	return payMapper.getYSFZByBillFid(billFid);
    }

    public FinReceiBillDetailEntity getOneReceiBillDetail(String fid) {
        return payMapper.getOneReceiBillDetail(fid);
    }

    public Integer insertReceiBillDetail(FinReceiBillDetailEntity entity1) {
        return payMapper.insertReceiBillDetail(entity1);
    }

    public Integer updateReceiBillDetailForYQWYJ(FinReceiBillDetailEntity entity) {
        return payMapper.updateReceiBillDetailForYQWYJ(entity);
    }

    public Integer updateReceiBillOughtAmount(FinReceiBillDetailEntity entity) {
        return payMapper.updateReceiBillOughtAmount(entity);
    }

    public Integer insertPaymentParam(PaymentParam paymentParam) {
        return payMapper.insertPaymentParam(paymentParam);
    }

    public FinReceiBillEntity getReceiBillByPaymentNum(String order_code) {
        return payMapper.getReceiBillByPaymentNum(order_code);
    }

    public Integer updatePaymentParam(PaymentParam paymentParam) {
        return payMapper.updatePaymentParam(paymentParam);
    }
    
    /**
     * 更新未回调的支付信息，防止支付平台重复回调
     * @author tianxf9
     * @param paymentParam
     * @return
     */
    public Integer updateUnCallBackPayMsg(PaymentParam paymentParam) {
    	return payMapper.updateUnCallBackPayMsg(paymentParam);
    }
    
    /**
     * 根据支付账单号获取支付信息.
     * wangws21 2017-4-18
     * @param paymentNum 支付订单号
     * @return 支付信息
     */
    public PaymentParam getPaymentParamByPaymentNum(String paymentNum){
        return this.payMapper.getPaymentParamByPaymentNum(paymentNum);
    }

    
    /**
     * 根据应收账单id获取应收账单单
     * @author tianxf9
     * @param billFid
     * @return
     */
    public FinReceiBillEntity getReceBillByFid(String billFid) {
    	return this.payMapper.getReceBillByFid(billFid);
    }
}