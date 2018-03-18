package com.zra.pay.logic;

import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.zra.common.utils.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.apollo.logproxy.slf4j.LoggerFactoryProxy;
import com.zra.cardCoupon.entity.CardCouponUsageEntity;
import com.zra.cardCoupon.entity.ExtCouponEntity;
import com.zra.cardCoupon.logic.CardCouponLogic;
import com.zra.common.dto.pay.BillDetailDto;
import com.zra.common.dto.pay.CardCouponDto;
import com.zra.common.dto.pay.CostDetailDto;
import com.zra.common.dto.pay.MyContractDetailDto;
import com.zra.common.dto.pay.MyContractDto;
import com.zra.common.dto.pay.PayParamDto;
import com.zra.common.dto.pay.PayWayDto;
import com.zra.common.dto.pay.PaymentNumParamDto;
import com.zra.common.dto.pay.ToPayDto;
import com.zra.common.enums.BillTypeEnum;
import com.zra.common.enums.CardCouponComeSourceEnum;
import com.zra.common.enums.CompanyCodeEnum;
import com.zra.common.enums.ErrorEnum;
import com.zra.common.enums.PayWayEnum;
import com.zra.common.enums.payChannelEnum;
import com.zra.common.error.ResultException;
import com.zra.house.service.ProjectService;
import com.zra.item.service.ItemService;
import com.zra.pay.entity.CallbackOfPaymentPlatformDto;
import com.zra.pay.entity.ContractDetailForPaymentPlatform;
import com.zra.pay.entity.FinReceiBillDetailEntity;
import com.zra.pay.entity.FinReceiBillEntity;
import com.zra.pay.entity.FinsettleEntity;
import com.zra.pay.entity.PaymentParam;
import com.zra.pay.entity.ResultOfPaymentPlatform;
import com.zra.pay.entity.SubmitOrderToPaymentPlatform;
import com.zra.pay.entity.ThirdParam;
import com.zra.pay.service.PayService;
import com.zra.pay.utils.CalculateYQWYJ;
import com.zra.pay.utils.PaymentPlatform;

/**
 * Created by cuigh6 on 2016/12/19.
 */
@Component
public class PayLogic {
    private static final Logger LOGGER = LoggerFactoryProxy.getLogger(PayLogic.class);
    
    @Autowired
    private PayService payService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ItemService itemService;
    
    @Autowired
    private CardCouponLogic cardCouponLogic;

    /**
     * 获取待支付列表 created by cuigh6
     *
     * @param uid 用户uid
     * @return 待支付列表
     */
    public List<ToPayDto> getToPayList(String uid) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        List<ToPayDto> resultList = new LinkedList<>();
        // 待支付房费的
        List<ToPayDto> toPayDtos = this.payService.getToPayList(uid);
        Collections.sort(toPayDtos, new Comparator<ToPayDto>() {
            @Override
            public int compare(ToPayDto o1, ToPayDto o2) {
                if (o1.getPlanGatherDate() == null) {
                    return -1;
                } else if (o2.getPlanGatherDate() == null) {
                    return 1;
                } else if (o1.getPlanGatherDate().before(o2.getPlanGatherDate())) {
                    return -1;
                } else if (o2.getPlanGatherDate().before(o1.getPlanGatherDate())) {
                    return 1;
                } else {
                    return 0;
                }

            }
        });
        for (ToPayDto dto : toPayDtos) {
            //added by wangxm113 添加逾期违约金
            if (CalculateYQWYJ.ifCalYQWYJ(dto.getPlanGatherDate()) && dto.getBillType() == BillTypeEnum.CONTRACT_PLAN_COST.getIndex()) {
                FinReceiBillDetailEntity entity = payService.getYQWYJByBillFid(dto.getBillFid());//查询应收账单明细
                BigDecimal yqwyjOld = entity == null ? BigDecimal.ZERO : entity.getOughtAmount();//以前计算的逾期违约金
                BigDecimal yqwyjB = new BigDecimal(CalculateYQWYJ.calYQWYJByMS(dto.getBillFid()));//实时计算的逾期违约金
                dto.setAmount(dto.getAmount().subtract(yqwyjOld).add(yqwyjB));
            }
            //end
            ToPayDto payDto = new ToPayDto();
            payDto.setBillFid(dto.getBillFid());//待支付账单标识
            payDto.setAmountStr(String.valueOf(dto.getAmount().setScale(2, BigDecimal.ROUND_DOWN)));// 待支付房租
            payDto.setBillTypeStr(BillTypeEnum.getEnum2map().get(dto.getBillType())); //账单类型
            payDto.setPayNum(dto.getPayNum());// 期数
            payDto.setProjectImg(PropUtils.getString("PIC_PREFIX_URL") + dto.getProjectImg());  // 项目图片
            payDto.setRoomCode(dto.getRoomCode()); //房间号
            payDto.setProjectName(dto.getProjectName());  // 项目名称

            if (dto.getBillType() == BillTypeEnum.OTHER_COST.getIndex()) {
                //获取费用项
                Map<String, Object> map = this.payService.getCostItemValue(dto.getBillFid());
                if ((long) map.get("count") >= 3) {
                    payDto.setCostItems(map.get("name") + "等");
                } else {
                    payDto.setCostItems((String) map.get("name"));
                }
            }else{
                payDto.setEndDateStr(simpleDateFormat.format(dto.getEndDate())); //结束日期
                payDto.setStartDateStr(simpleDateFormat.format(dto.getStartDate())); // 开始日期
            }
            resultList.add(payDto);
        }
        return resultList;
    }

    /**
     * 获取账单详情
     *
     * @param billFid
     * @return
     */
    public BillDetailDto getBillDetail(BillDetailDto dto) {
        //查询账单信息
        BillDetailDto detailDto = this.payService.getBillDetail(dto.getBillFid());

        detailDto.setMinPayAmount(PropUtils.getString("min.pay.amount"));//最小支付金额
        //查询费用明细
        List<CostDetailDto> costDetails = this.payService.getCostItems(dto.getBillFid(), detailDto);
        if (costDetails == null) {
            costDetails = new ArrayList<>();
        }
        detailDto.setDetails(costDetails);
        //因为分批支付时，当支付金额小于最低支付金额时，客户端给后台传的支付金额是应缴金额，后台待改为，返回给客户端的应收金额实际为代缴金额 2017-02-23 start。。。
        if (!"ios".equalsIgnoreCase(dto.getSource())) {
            BigDecimal oldOughtTotalAmount = detailDto.getOughtTotalAmount();
            detailDto.setOughtTotalAmount(detailDto.getOughtTotalAmount().subtract(detailDto.getActualAmount() == null ? BigDecimal.ZERO : detailDto.getActualAmount()));
            detailDto.setOughtTotalAmountStr(String.valueOf(detailDto.getOughtTotalAmount().setScale(2, BigDecimal.ROUND_DOWN)));
            LOGGER.info("[获取订单详情]订单bid:" + dto.getBillFid() + "; 来源:" + dto.getSource() + ";原来应交金额:" + oldOughtTotalAmount.toPlainString() + ";更改后应交金额:" + detailDto.getOughtTotalAmount());
        }
        //end。。。
        return detailDto;
    }
    
    
    /**
     * 获取账单详情(对接卡券系统)
     * @author tianxf9
     * @param billFid
     * @return
     */
    public BillDetailDto getNewBillDetail(BillDetailDto dto) {
    	
        //查询账单信息
        BillDetailDto detailDto = this.payService.getBillDetail(dto.getBillFid());

        detailDto.setMinPayAmount(PropUtils.getString("min.pay.amount"));//最小支付金额
        //查询费用明细
        List<CostDetailDto> costDetails = this.payService.getNewCostItems(dto.getBillFid(), detailDto);
        if (costDetails == null) {
            costDetails = new ArrayList<>();
        }
        detailDto.setDetails(costDetails);
        detailDto.setUid(dto.getUid());
        setCardCouponMsg(detailDto);
        return detailDto;
    }
    
    /**
     * 根据条件获取卡券信息
     * @author tianxf9
     * @param dto
     * @return
     */
    public void setCardCouponMsg(BillDetailDto detailDto) {
    	Integer billType = detailDto.getBillType();
    	List<CardCouponDto> cardCouponDtos = new ArrayList<>();
    	if(billType==null) {
    		LOGGER.info("应收账单：fid="+detailDto.getBillFid()+"的billType为空！返回卡券为空");
    	}else if(BillTypeEnum.CONTRACT_PLAN_COST.getIndex()==billType.intValue()) {
            cardCouponDtos = this.cardCouponLogic.cardQuery(detailDto.getUid(), SysConstant.CARD_COUPON_STATUS);
        }else {
        	//modify by tianxf9 自如六周年专享自如寓服务费直减券——关闭app优惠券消费
    		//cardCouponDtos = this.cardCouponLogic.couponQuery(detailDto.getUid(), SysConstant.CARD_COUPON_STATUS);
        	LOGGER.info("自如六周年专享自如寓服务费直减券——关闭app优惠券消费！返回卡券为空");
    	}
    	detailDto.setCardCoupons(cardCouponDtos);
    }

    /**
     * 获取合同列表 created by cuigh6
     *
     * @param uid 用户标识
     * @return
     */
    public List<MyContractDto> getMyContractList(String uid) {
        //查询我的合同列表
        List<MyContractDto> contractDtos = this.payService.getMyContractList(uid);
        return contractDtos;
    }

    /**
     * 获取合同详情 created by cuigh6
     *
     * @param contractId 合同标识
     * @return 合同详情
     */
    public MyContractDetailDto getContractDetail(String contractId) {
        MyContractDetailDto contractDto = this.payService.getContractDetail(contractId);
        return contractDto;
    }

    /**
     * 查询合同账单列表 created by cuigh6
     *
     * @param contractId 合同标识
     * @return
     */
    public MyContractDetailDto getContractBillList(String contractId) {
        MyContractDetailDto contractDetailDto = this.payService.getContractInfo(contractId);
        return contractDetailDto;
    }


    /**
     * 获取支付方式 created by cuigh6 on 2016/12/27
     * @param dto
     * @return
     */
    public PayWayDto getPayWay(PaymentNumParamDto dto) {
        LOGGER.info("[获取支付方式]应收账单号：" + dto.getBillNum() + ",支付金额：" + dto.getPayAmount());
        //查询应缴金额
        BillDetailDto billDetailDto = this.payService.getBillDetail(dto.getBillFid());
        //added by wangxm113 添加逾期违约金
        if (CalculateYQWYJ.ifCalYQWYJ(billDetailDto.getGatherDate()) && billDetailDto.getBillType() == BillTypeEnum.CONTRACT_PLAN_COST.getIndex()) {
            FinReceiBillDetailEntity entity = payService.getYQWYJByBillFid(billDetailDto.getBillFid());//查询应收账单明细
            BigDecimal yqwyjOld = entity == null ? BigDecimal.ZERO : entity.getOughtAmount();//以前计算的逾期违约金
            BigDecimal yqwyjB = new BigDecimal(CalculateYQWYJ.calYQWYJByMS(billDetailDto.getBillFid()));//实时计算的逾期违约金
            billDetailDto.setOughtTotalAmount(billDetailDto.getOughtTotalAmount().subtract(yqwyjOld).add(yqwyjB));
            billDetailDto.setOughtTotalAmountStr(String.valueOf(billDetailDto.getOughtTotalAmount().setScale(2, BigDecimal.ROUND_DOWN)));
        }
        //end
        //查询已缴金额
        BigDecimal paidAmount = this.payService.getPaidAmount(dto.getBillFid());
        PayWayDto payWayDto = new PayWayDto();
        //判断支付的金额

        if (paidAmount == null) {
            paidAmount = new BigDecimal(0);
        }
        //待缴金额
        BigDecimal pendingAmount = billDetailDto.getOughtTotalAmount().subtract(paidAmount);

        BigDecimal actuAmount = new BigDecimal(dto.getPayAmount().multiply(new BigDecimal("100")).intValue());

        // 支付金额 大于等于 待缴金额
        if (dto.getPayAmount().compareTo(pendingAmount) > 0) {
            throw new ResultException(ErrorEnum.MSG_PAYMENT_AMOUNT_ERR);
        }

        String payOrderNumber = null;
        if (dto.getPayAmount().compareTo(BigDecimal.ZERO) == 0) {
            payWayDto.setPayOrderNum(PropUtils.getString(ZraApiConst.RETURN_APP_WITHOUT_PAYMONEY));//此时不需要调用支付凭条，则返回给app端订单号为"null"四个小写字母
        } else {
            String proejctCode = projectService.getProjectCodeByBillFid(dto.getBillFid());
            //返回支付订单号和支付方式
            List<String> list = new ArrayList<String>();
            for (PayWayEnum pw : PayWayEnum.values()) {
                list.add(pw.getPayType());
            }
            payWayDto.setPayWayList(list);
            //支付平台下单
            SubmitOrderToPaymentPlatform payment = new SubmitOrderToPaymentPlatform();
            List<ContractDetailForPaymentPlatform> platformList = new ArrayList<ContractDetailForPaymentPlatform>();
            ContractDetailForPaymentPlatform orderPlatform = new ContractDetailForPaymentPlatform();
            orderPlatform.setAmount(actuAmount);
            orderPlatform.setOrderType(ZraApiConst.ORDER_TYPE);
            platformList.add(orderPlatform);
            payment.setPayDetail(platformList);
            payment.setBizCode(dto.getBillNum()+"_"+System.currentTimeMillis());//账单号
            payment.setUid(dto.getUid());
            payment.setTotalFee(actuAmount);
            payment.setNotifyUrl(PropUtils.getString(ZraApiConst.PAY_CALLBACK_URL));
            payment.setExpiredDate(Integer.valueOf(PropUtils.getString(ZraApiConst.ORDER_EXPIRED)));
            if (!CompanyCodeEnum.QIBAO.getProjectCode().equals(proejctCode)) {//北京或者其灵
                payment.setCityCode(PropUtils.getString(ZraApiConst.CITY_CODE_FOR_PAYMENT_PLATFORM_BJ));
            } else {//上海七宝
                payment.setCityCode(PropUtils.getString(ZraApiConst.CITY_CODE_FOR_PAYMENT_PLATFORM_SH));
            }
            payment.setSourceType(payChannelEnum.IOS.getLiteral().equalsIgnoreCase(dto.getSource()) ? payChannelEnum.IOS.getLiteral() : payChannelEnum.ANDROID.getLiteral());
            payment.setUidType("KH");
            payment.setPassAccount(Integer.valueOf(PropUtils.getString(ZraApiConst.PASS_ACCOUNT_FOR_PAYMENT_PLATFORM)));
            try {
                payOrderNumber = PaymentPlatform.submitOrdersToPaymentPlatform(payment);
                payWayDto.setPayOrderNum(payOrderNumber);
            } catch (Exception e) {
                LOGGER.error("[向支付平台下单]失败:", e);
                throw new ResultException(ErrorEnum.MSG_SUBMIT_PAYMENT_PLATFORM_FAIL);
            }
        }

//        添加支付账单号到应收账单表中
//        this.payService.updatePaymentNum(dto.getBillFid(), payOrderNumber);
        //added by wangxm113
        PaymentParam paymentParam = new PaymentParam();
        paymentParam.setFid(UUID.randomUUID().toString());
        paymentParam.setBillFid(dto.getBillFid());
        paymentParam.setPaymentNum(payOrderNumber);
        Integer i = this.payService.insertPaymentParam(paymentParam);
        if (i == null || i != 1) {
            LOGGER.info("[向支付平台下单]保存支付参数错误！应收账单号:" + dto.getBillNum() + ";insert结果:" + i);
        }
        return payWayDto;
    }
    
    
    
    /**
     * 获取支付方式（对接卡券）
     * @author tianxf9
     * @param dto
     * @return
     */
    public PayWayDto getNewPayWay(PaymentNumParamDto dto) {
    	//查询应缴金额
        BillDetailDto billDetailDto = this.payService.getBillDetail(dto.getBillFid());
    	String cardCouponCodes = CollectionUtils.isEmpty(dto.getCardCouponCodes())?"":JSON.toJSONString(dto.getCardCouponCodes());
        LOGGER.info("[获取支付方式]应收账单号：" + dto.getBillNum() + ",支付金额：" + dto.getPayAmount()+",应收账单类型："+billDetailDto.getBillType().intValue()+",卡券code:"+cardCouponCodes);
        //判断卡券是否可用
        List<ExtCouponEntity> cardCouponEntitys = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(dto.getCardCouponCodes())) {
        	cardCouponEntitys = isCardCouponAvailable(billDetailDto.getBillType(), dto.getCardCouponCodes(),dto.getUid());
        }
        
        //added by wangxm113 添加逾期违约金
        if (CalculateYQWYJ.ifCalYQWYJ(billDetailDto.getGatherDate()) && billDetailDto.getBillType() == BillTypeEnum.CONTRACT_PLAN_COST.getIndex()) {
            FinReceiBillDetailEntity entity = payService.getYQWYJByBillFid(billDetailDto.getBillFid());//查询应收账单明细
            BigDecimal yqwyjOld = entity == null ? BigDecimal.ZERO : entity.getOughtAmount();//以前计算的逾期违约金
            BigDecimal yqwyjB = new BigDecimal(CalculateYQWYJ.calYQWYJByMS(billDetailDto.getBillFid()));//实时计算的逾期违约金
            billDetailDto.setOughtTotalAmount(billDetailDto.getOughtTotalAmount().subtract(yqwyjOld).add(yqwyjB));
            billDetailDto.setOughtTotalAmountStr(String.valueOf(billDetailDto.getOughtTotalAmount().setScale(2, BigDecimal.ROUND_DOWN)));
        }
        //end
        //查询已缴金额
        BigDecimal paidAmount = this.payService.getPaidAmount(dto.getBillFid());
        PayWayDto payWayDto = new PayWayDto();
        //判断支付的金额
        if (paidAmount == null) {
            paidAmount = new BigDecimal(0);
        }
        //获取卡券的金额
        BigDecimal cardCouponAmount = getCardCouponAmount(cardCouponEntitys);
        if(billDetailDto.getBillType() == BillTypeEnum.CONTRACT_PLAN_COST.getIndex()) {
        	FinReceiBillDetailEntity fangzuDetail = payService.getYSFZByBillFid(billDetailDto.getBillFid());
        	BigDecimal fangzuAmount = fangzuDetail.getOughtAmount()==null?new BigDecimal(0):fangzuDetail.getOughtAmount();
        	BigDecimal paidFZAmount = this.payService.getPaidFZAmount(dto.getBillFid());
        	if(paidFZAmount==null) {
        		paidFZAmount = new BigDecimal(0);
        	}
        	BigDecimal needPayFZAmount = fangzuAmount.subtract(paidFZAmount);
        	if(needPayFZAmount.compareTo(cardCouponAmount)<0) {
        		cardCouponAmount = needPayFZAmount;
        	}
        }
        //待缴金额
        BigDecimal pendingAmount = billDetailDto.getOughtTotalAmount().subtract(paidAmount).subtract(cardCouponAmount);

        BigDecimal actuAmount = new BigDecimal(dto.getPayAmount().multiply(new BigDecimal("100")).intValue());

        // 支付金额 大于等于 待缴金额
        if (dto.getPayAmount().compareTo(new BigDecimal(0))>0&&dto.getPayAmount().compareTo(pendingAmount) > 0) {
            throw new ResultException(ErrorEnum.MSG_PAYMENT_AMOUNT_ERR);
        }
        
        //消费卡券
        if(CollectionUtils.isNotEmpty(cardCouponEntitys)) {
        	consumeCardCoupon(dto.getBillFid(),dto.getBillType(),dto.getCardCouponCodes(),dto.getUid());
        }
       
        //维护卡券消费记录以及生成卡券对应的收款单；
        List<String> sucCodes = new ArrayList<>();
        try {
        	if(CollectionUtils.isNotEmpty(cardCouponEntitys))  {
			   consumeCardCouponRecord(dto.getBillFid(),dto.getBillType(),cardCouponEntitys,dto.getUid(),sucCodes);
        	}
		} catch (Exception e1) {
			LOGGER.error("【根据卡券生成收款单出错】", e1);
			if(sucCodes.size()<cardCouponEntitys.size()) {
				LOGGER.info("【部分卡券消费失败，恢复卡券】");
				recoveryCardCoupon(dto.getBillType(),sucCodes,cardCouponEntitys);
			}
			throw new ResultException(ErrorEnum.MSG_CARDCOUPON_CONSUME_ERR);
		}     
        String payOrderNumber = null;
        if (dto.getPayAmount().compareTo(BigDecimal.ZERO) == 0) {
            payWayDto.setPayOrderNum(PropUtils.getString(ZraApiConst.RETURN_APP_WITHOUT_PAYMONEY));//此时不需要调用支付凭条，则返回给app端订单号为"null"四个小写字母
        } else {
            String proejctCode = projectService.getProjectCodeByBillFid(dto.getBillFid());
            //返回支付订单号和支付方式
            List<String> list = new ArrayList<>();
            for (PayWayEnum pw : PayWayEnum.values()) {
                list.add(pw.getPayType());
            }
            payWayDto.setPayWayList(list);
            //支付平台下单
            SubmitOrderToPaymentPlatform payment = new SubmitOrderToPaymentPlatform();
            List<ContractDetailForPaymentPlatform> platformList = new ArrayList<>();
            ContractDetailForPaymentPlatform orderPlatform = new ContractDetailForPaymentPlatform();
            orderPlatform.setAmount(actuAmount);
            orderPlatform.setOrderType(ZraApiConst.ORDER_TYPE);
            platformList.add(orderPlatform);
            payment.setPayDetail(platformList);
            payment.setBizCode(dto.getBillNum()+"_"+System.currentTimeMillis());//账单号
            payment.setUid(dto.getUid());
            payment.setTotalFee(actuAmount);
            payment.setNotifyUrl(PropUtils.getString(ZraApiConst.PAY_CALLBACK_URL));
            payment.setExpiredDate(Integer.valueOf(PropUtils.getString(ZraApiConst.ORDER_EXPIRED)));
            if (!CompanyCodeEnum.QIBAO.getProjectCode().equals(proejctCode)) {//北京或者其灵
                payment.setCityCode(PropUtils.getString(ZraApiConst.CITY_CODE_FOR_PAYMENT_PLATFORM_BJ));
            } else {//上海七宝
                payment.setCityCode(PropUtils.getString(ZraApiConst.CITY_CODE_FOR_PAYMENT_PLATFORM_SH));
            }
            payment.setSourceType(payChannelEnum.IOS.getLiteral().equalsIgnoreCase(dto.getSource()) ? payChannelEnum.IOS.getLiteral() : payChannelEnum.ANDROID.getLiteral());
            payment.setUidType("KH");
            payment.setPassAccount(Integer.valueOf(PropUtils.getString(ZraApiConst.PASS_ACCOUNT_FOR_PAYMENT_PLATFORM)));
            try {
                payOrderNumber = PaymentPlatform.submitOrdersToPaymentPlatform(payment);
                payWayDto.setPayOrderNum(payOrderNumber);
            } catch (Exception e) {
                LOGGER.error("[向支付平台下单]失败:", e);
                throw new ResultException(ErrorEnum.MSG_SUBMIT_PAYMENT_PLATFORM_FAIL);
            }
        }
        
        //added by wangxm113
        PaymentParam paymentParam = new PaymentParam();
        paymentParam.setFid(UUID.randomUUID().toString());
        paymentParam.setBillFid(dto.getBillFid());
        paymentParam.setPaymentNum(payOrderNumber);
        Integer i = this.payService.insertPaymentParam(paymentParam);
        if (i == null || i != 1) {
            LOGGER.info("[向支付平台下单]保存支付参数错误！应收账单号:" + dto.getBillNum() + ";insert结果:" + i);
        }
        return payWayDto;
    }
    
    /**
     * 根据单据类型，卡券id获取卡券金额
     * @author tianxf9
     * @param billType
     * @param cardCouponCodes
     * @return
     */
    public BigDecimal getCardCouponAmount(List<ExtCouponEntity> entitys) {
    	
    	BigDecimal amount = new BigDecimal(0);
    	if(CollectionUtils.isEmpty(entitys)) {
    		return amount;
    	}
    	
    	int sumAmount = 0;//卡券总金额 单位分
    	for(ExtCouponEntity entity:entitys) {
    		sumAmount = sumAmount + entity.getMoney();
    	}
    	amount = new BigDecimal(sumAmount).divide(new BigDecimal(100));
		return amount;
    }
    
    /**
     * 根据单据类型，卡券id判断卡券是否可用
     * @author tianxf9
     * @param billType
     * @param cardCouponCodes
     * @param uid
     * @return
     */
    public List<ExtCouponEntity> isCardCouponAvailable(Integer billType,List<String> cardCouponCodes,String uid) {
    	List<ExtCouponEntity> entitys = new ArrayList<>();
    	if(billType.intValue()==BillTypeEnum.CONTRACT_PLAN_COST.getIndex()) {
    		entitys = this.cardCouponLogic.cardCheck(cardCouponCodes, uid);
    	}else {
    		ExtCouponEntity entity = this.cardCouponLogic.couponCheck(cardCouponCodes.get(0), uid);
    		if(entity!=null) {
    		  entitys.add(entity);
    		}
    	}
		return entitys;
    }
    
    /**
     * 消费卡券
     * @author tianxf9
     * @param billType
     * @param cardCouponCodes
     * @return
     */
    public void consumeCardCoupon(String billFid,Integer billType,List<String> codes,String uid) {
    	if(billType.intValue()==BillTypeEnum.CONTRACT_PLAN_COST.getIndex()) {
    		this.cardCouponLogic.cardUse(codes,uid,billFid,billType,CardCouponComeSourceEnum.APP.getIndex());
    	}else {
    		this.cardCouponLogic.couponUse(codes.get(0),uid,billFid,billType,CardCouponComeSourceEnum.APP.getIndex());
    	}
    }
    
    /**
     * 恢复卡券 cardCouponEntitys中不在successCodes中的卡券
     * @author tianxf9
     * @param billType
     * @param cardCouponId
     * @return
     */
	public void recoveryCardCoupon(Integer billType, List<String> successCodes,
			List<ExtCouponEntity> cardCouponEntitys) {
		
		for (ExtCouponEntity entity : cardCouponEntitys) {
			boolean isExist = false;
			for (String code : successCodes) {
				if (entity.getCode().equals(code)) {
					isExist = true;
					break;
				}
			}

			if (!isExist) {
				if (BillTypeEnum.CONTRACT_PLAN_COST.getIndex() == billType.intValue()) {
					this.cardCouponLogic.cardRecovery(entity.getCode());
				} else {
					this.cardCouponLogic.couponRecovery(entity.getCode());
				}
			}

		}
	}
    
    /**
     * 记录卡券消费（保存卡券消费记录表，回应收，生成收款）
     * @author tianxf9
     * @param billFid
     * @param billType
     * @param cardCouponIds
     * @param uid
     */
	public void consumeCardCouponRecord(String billFid, Integer billType, List<ExtCouponEntity> cardCouponEntitys,
			String uid, List<String> sucCodes) throws Exception {

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		FinReceiBillEntity finReceiBillEntity = this.payService.getReceBillByFid(billFid);
		if (finReceiBillEntity == null) {
			LOGGER.info("[消费卡券生成收款单]根据应收fid没有查询到应收账单！billFid:" + billFid);
			return;
		}

		// 基于目前这种根据公司编码，财务编码不能唯一确认一种结算方式的情况下根据id去获取结算方式
		String contractId = finReceiBillEntity.getContractId();
		MyContractDetailDto myContractDetailDto = this.payService.getContractDetail(contractId);
		String finCompanyCode = CompanyCodeEnum.getEnum2Map().get(myContractDetailDto.getProjectCode());
		if ("08".equals(myContractDetailDto.getProjectCode())) {
			finCompanyCode = finCompanyCode + "_SH";
		}
		String finSettleId = String
				.valueOf(PropUtils.getString(ZraApiConst.FINSETTLE_COUPONS_NEW_ID + "_" + finCompanyCode));
		for (ExtCouponEntity cardCouponDto : cardCouponEntitys) {
			Map<String, Object> rentPaymentVoucher = new HashedMap();
			List<Map<String, Object>> list = new ArrayList<>();
			BigDecimal callbackAmount = new BigDecimal(cardCouponDto.getMoney()).divide(new BigDecimal(100));
			LOGGER.info(Thread.currentThread().getId() + "-#根据卡券生成财务相关信息#-#参数#：billFid=" + billFid + ";billType="
					+ billType + ";卡券code=" + cardCouponDto.getCode() + "-#金额（元）#：" + callbackAmount + ";uid=" + uid);
			BigDecimal actualPayment = new BigDecimal(0);
			// 查询应收费用项明细 不考虑企业
			List<FinReceiBillDetailEntity> finReceiBillDetailEntities = this.payService
					.getCardCouponFinReceiBillDetail(billFid);
			for (FinReceiBillDetailEntity finReceiBillDetailEntity : finReceiBillDetailEntities) {// 循环费用项
																									// 判断应收是否大于实收
				Map<String, Object> billDetail = new HashedMap();
				boolean flag = false;// 金额不够支付费用项，跳出本次循环
				// 传后台
				billDetail.put("billId", finReceiBillDetailEntity.getBillFid());
				billDetail.put("id", finReceiBillDetailEntity.getCostItemId());
				billDetail.put("itemName", finReceiBillDetailEntity.getItemName());
				BigDecimal diff = finReceiBillDetailEntity.getOughtAmount()
						.subtract(finReceiBillDetailEntity.getActualAmount());// 应收-实收金额
				BigDecimal actualDetailAmount = new BigDecimal(0);
				if (callbackAmount.compareTo(diff) >= 0) {
					actualDetailAmount = diff;
					callbackAmount = callbackAmount.subtract(diff);
				} else {
					actualDetailAmount = callbackAmount;
					flag = true;
				}
				actualPayment = actualPayment.add(actualDetailAmount);// 收款单实际收款金额
				LOGGER.info(Thread.currentThread().getId() + "-#卡券消费#-#费用项:"+finReceiBillDetailEntity.getCostItemId()+"金额:" + actualDetailAmount);
				billDetail.put("shis", actualDetailAmount);// 收款金额减应收减实收=费用项实收金额
				billDetail.put("roomId", finReceiBillDetailEntity.getRoomId());
				billDetail.put("cityId", finReceiBillEntity.getCityId());
				list.add(billDetail);
				if (flag) {
					break;
				}
			}
			updateCardCouponUsageActualAmount(actualPayment, cardCouponDto.getCode(), billType);
			if (CollectionUtils.isNotEmpty(list)) {
				rentPaymentVoucher.put("collectionChannel", ZraConst.COLLECTION_CHANNEL);
				rentPaymentVoucher.put("rentContractCode", myContractDetailDto.getContractCode());
				rentPaymentVoucher.put("contractId", myContractDetailDto.getContractId());
				rentPaymentVoucher.put("projectId", myContractDetailDto.getProjectId());
				rentPaymentVoucher.put("roomName", myContractDetailDto.getRoomNum());
				rentPaymentVoucher.put("customerName", myContractDetailDto.getCustomerName());
				String currentDate = dateFormat.format(new Date());
				rentPaymentVoucher.put("paymentTime", currentDate);
				rentPaymentVoucher.put("createDate", currentDate);
				rentPaymentVoucher.put("collectionDate", currentDate);
				rentPaymentVoucher.put("collectionWay", ZraConst.COLLECTION_WAY);
				rentPaymentVoucher.put("finsettleId", finSettleId);
				rentPaymentVoucher.put("state", "2");// 已确认
				rentPaymentVoucher.put("genWay", 0);// 自动生成
				rentPaymentVoucher.put("factPayment", actualPayment.setScale(2, BigDecimal.ROUND_DOWN));
				rentPaymentVoucher.put("serialNo", cardCouponDto.getCode());
				rentPaymentVoucher.put("outTradeNo", null);
				rentPaymentVoucher.put("cityId", finReceiBillEntity.getCityId());

				// 调用ZRAMS 保存收款单接口
				Map<String, String> map = new HashedMap();
				map.put("voucher", JSON.toJSONString(rentPaymentVoucher));
				map.put("vouchers", JSON.toJSONString(list));
				map.put("billId", billFid);
				String path = PropUtils.getString("zrams.save.receipt.bill.url");

				LOGGER.info(Thread.currentThread().getId() + "-#保存收款单#-#路径#：" + path);
				LOGGER.info(Thread.currentThread().getId() + "-#保存收款单#-#参数#：" + JSON.toJSONString(map));
				InputStream inputStream = NetUtil.sendPostRequest(path, map);
				String resultJson = NetUtil.getTextContent(inputStream, "utf-8");
				Map resultMap = JSON.parseObject(resultJson, Map.class);
				String result = (String) resultMap.get("status");
				LOGGER.info(Thread.currentThread().getId() + "-#保存收款单#-#结果#：" + resultJson);
				if ("success".equals(result)) {
					sucCodes.add(cardCouponDto.getCode());
				} else {
					throw new ResultException(ErrorEnum.MSG_CARDCOUPON_CONSUME_ERR);
				}
			}
		}
	}
    
    /**
     * 更新卡券实际消费金额
     * @author tianxf9
     * @param actualPayment
     * @param code
     * @param billType
     */
    public void updateCardCouponUsageActualAmount(BigDecimal actualPayment,String code,Integer billType) {
        //维护卡券消费记录 更新支付信息实际支付的金额
        LOGGER.info(Thread.currentThread().getId()+"begin...#更新卡券:"+code+"消费信息实际支付金额:"+actualPayment);
        CardCouponUsageEntity updateEntity = new CardCouponUsageEntity();
        if(billType.intValue()==BillTypeEnum.CONTRACT_PLAN_COST.getIndex()) {
       	 updateEntity.setRentCardCode(code);
        }else {
       	 updateEntity.setCouponCode(code);
        }
        updateEntity.setPayAmount(actualPayment);
        int updaterows = this.cardCouponLogic.updateCardCouponUsage(updateEntity);
        LOGGER.info(Thread.currentThread().getId()+"end...#更新卡券"+code+"消费信息实际支付金额#影响行数："+updaterows);
    }


    /**
     * 向支付平台支付 created by cuigh6 on 2016/12/28 copy wangxm
     * @param pd
     * @return
     * @throws Exception
     */
    public String payToPaymentPlatform(PayParamDto pd) throws Exception {
        try {
            String payResult = PaymentPlatform.quryFromPaymentPlatform(pd.getPaymentNum());// 根据支付订单号，向支付平台查询是否已经支付过
            // 0 未支付,3支付中,6未查到,8支付失败,9支付成功
            if (!("0".equals(payResult) || "8".equals(payResult))) {
                LOGGER.error("[向支付平台查询]支付订单号:" + pd.getPaymentNum() + ",支付平台状态为:" + payResult);
                throw new ResultException(ErrorEnum.MSG_PAY_ORDER_STATUS_FAIL);
            }
        } catch (Exception e) {
            // 支付订单号出错
        }

        LOGGER.info("[向支付平台支付][支付信息:]支付订单号:" + pd.getPaymentNum() + ",即将支付" + pd.getPayAmount() + "元人民币。");
        /*支付下单时间作为实际支付时间  wangws21 2017-4-18 start*/
        PaymentParam paymentParam = this.payService.getPaymentParamByPaymentNum(pd.getPaymentNum());
        paymentParam.setUpdateTime(new Date());
        this.payService.updatePaymentParam(paymentParam);
        /*支付下单时间作为实际支付时间  wangws21 2017-4-18 end*/

//        FinReceiBillEntity num = payService.getBillByPaymentNum(pd.getPaymentNum());
        FinReceiBillEntity num = payService.getReceiBillByPaymentNum(pd.getPaymentNum());
        String projectCode = projectService.getProjectCodeByBillFid(num.getFid());
        String url = null;
        if (CompanyCodeEnum.QIBAO.getProjectCode().equals(projectCode)) {//七宝
            url = ThirdParam.PAY_PRE_PAY_URL.replace("{cityCode}", PropUtils.getString(ZraApiConst.CITY_CODE_FOR_PAYMENT_PLATFORM_SH)).replace("{payChannel}",
                    pd.getPayChannel());
        } else {
            url = ThirdParam.PAY_PRE_PAY_URL.replace("{cityCode}", PropUtils.getString(ZraApiConst.CITY_CODE_FOR_PAYMENT_PLATFORM_BJ)).replace("{payChannel}",
                    pd.getPayChannel());
        }

        Map<String, String> param = new HashMap<String, String>();
        param.put("orderCode", pd.getPaymentNum());
        param.put("payAmount", String.valueOf((pd.getPayAmount() * 100)));// 注意：此处需由元转成分
        LOGGER.info("[向支付平台支付]支付订单号:" + pd.getPaymentNum() + ",向支付平台支付入参:" + param);
        InputStream resultContentInputStream = NetUtil.sendPostRequest(url, param);
        String resultContent = NetUtil.getTextContent(resultContentInputStream, ThirdParam.ENCODING_UTF);
        LOGGER.info("[向支付平台支付]支付订单号:" + pd.getPaymentNum() + ",向支付平台支付返回结果:" + resultContent);

        if (resultContent != null) {
            ResultOfPaymentPlatform result = new ObjectMapper().readValue(resultContent, ResultOfPaymentPlatform.class);
            if ("success".equals(result.getStatus())) {
                return result.getData();// 支付平台返回的信息都在result中，目前，如果正确只返回给调用者data，错误抛异常
            } else {
                LOGGER.error("[向支付平台支付]支付订单号:" + pd.getPaymentNum() + "，调用支付平台支付返回错误！异常码:" + result.getCode() + ";异常信息:"
                        + result.getMessage() + "!");
                throw new ResultException(ErrorEnum.MSG_PAY_PAYMENT_PLATFORM_FAIL);
            }
        } else {
            throw new ResultException(ErrorEnum.MSG_PAY_PAYMENT_PLATFORM_FAIL);
        }
    }

    /**
     * 支付平台回调 created by cuigh6
     *
     * @param dto
     */
    public void callbackOfPayPlatform(CallbackOfPaymentPlatformDto dto) throws Exception {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Map<String, Object> rentPaymentVoucher = new HashedMap();
        List<Map<String, Object>> list = new ArrayList<>();
        LOGGER.info(Thread.currentThread().getId()+"-#支付平台回调#-#参数#：" + JSON.toJSONString(dto));
        //根据支付订单号查询应收账单
//        FinReceiBillEntity finReceiBillEntity = this.payService.getBillByPaymentNum(dto.getOrder_code());
        FinReceiBillEntity finReceiBillEntity = this.payService.getReceiBillByPaymentNum(dto.getOrder_code());
        BigDecimal callbackAmount = new BigDecimal(Integer.parseInt(dto.getCurrent_money())).divide(new BigDecimal(100));//单位转换成元
        LOGGER.info(Thread.currentThread().getId()+"-#支付平台回调#-#金额（元）#：" + callbackAmount);
        if (finReceiBillEntity == null) {
            LOGGER.info("[支付平台回调]根据支付订单号没有查询到应收账单！支付订单号:" + dto.getOrder_code());
            return;
        }
        /*支付调用时间为实际支付时间  wangws21 2017-4-18 start*/
        PaymentParam paymentParam = this.payService.getPaymentParamByPaymentNum(dto.getOrder_code());
        Date paymentTime = paymentParam.getUpdateTime();
        /*支付调用时间为实际支付时间  wangws21 2017-4-18 end*/
        
        paymentParam.setPaymentNum(dto.getOrder_code());
        paymentParam.setBizCode(dto.getBizCode());
        paymentParam.setCurrentMoney(dto.getCurrent_money());
        paymentParam.setOutTradeNo(dto.getOut_trade_no());
        paymentParam.setPayState(1);//支付状态（0：已下单；1：已回调）
        paymentParam.setPayType(dto.getPay_type());
        paymentParam.setUpdateTime(new Date());
        Integer j = payService.updateUnCallBackPayMsg(paymentParam);
        if (j == null || j.intValue() != 1) {
            LOGGER.info("[支付平台回调]更新支付信息表出错！支付订单号:" + dto.getOrder_code() + ";update信息:" + j);
            return;
        }

        //added by wangxm113 添加逾期违约金
        /*if (CalculateYQWYJ.ifCalYQWYJ(finReceiBillEntity.getGatherDate()) && finReceiBillEntity.getBillType() == BillTypeEnum.CONTRACT_PLAN_COST.getIndex()) {
            FinReceiBillDetailEntity entity = payService.getYQWYJByBillFid(finReceiBillEntity.getFid());//查询应收账单明细
            BigDecimal yqwyjOld = entity == null ? BigDecimal.ZERO : entity.getOughtAmount();//以前计算的逾期违约金
            BigDecimal yqwyjB = new BigDecimal(CalculateYQWYJ.calYQWYJByMS(finReceiBillEntity.getFid()));//实时计算的逾期违约金
            finReceiBillEntity.setOughtTotalAmount(finReceiBillEntity.getOughtTotalAmount().subtract(yqwyjOld).add(yqwyjB));
            *//**
             * 逻辑：如果之前应收账单明细表中没有逾期违约金，则插入实时计算出来的一条逾期违约金，并更新应收账单主表的应收金额；
             * 如果之前应收账单明细表中有逾期违约金，则更新该条的应收金额，并更新应收账单主表的应收金额；
             * 方便下面挨项填充应收账单明细表中的实收金额。
             *//*
            if (entity == null) {
                Integer id = itemService.getYQWYJId();
                FinReceiBillDetailEntity entity1 = payService.getOneReceiBillDetail(finReceiBillEntity.getFid());
                entity1.setFid(UUID.randomUUID().toString());
                entity1.setCostItemId(id.toString());
                entity1.setOughtAmount(yqwyjB);
                entity1.setCreateTime(new Date());
                entity1.setUpdateTime(new Date());
                Integer i = payService.insertReceiBillDetail(entity1);
                if (i != 1) {
                    LOGGER.info("支付平台回调，保存逾期违约金到应收账单明细表出错！支付订单号:" + dto.getOut_trade_no());
                }
                entity = entity1;
            } else {
                entity.setOughtAmount(yqwyjB);
                entity.setBillFid(finReceiBillEntity.getFid());
                Integer i = payService.updateReceiBillDetailForYQWYJ(entity);
                if (i != 1) {
                    LOGGER.info("支付平台回调，更新逾期违约金到应收账单明细表出错！支付订单号:" + dto.getOut_trade_no());
                }
            }
            //更新fin_recei_bill表的金额
            entity.setOughtAmount(finReceiBillEntity.getOughtTotalAmount());
            Integer i = payService.updateReceiBillOughtAmount(entity);
            if (i != 1) {
                LOGGER.info("支付平台回调，加上逾期违约金到应收账单表出错！支付订单号:" + dto.getOut_trade_no());
            }
        }*/
        //end

        //根据合同id 查询项目编码
        String billFid = finReceiBillEntity.getFid();
        String contractId = finReceiBillEntity.getContractId();
        MyContractDetailDto myContractDetailDto = this.payService.getContractDetail(contractId);
        //查询结算方式
        FinsettleEntity finsettleEntity = this.payService.getFinsettle(dto.getPay_type(), CompanyCodeEnum.getEnum2Map().get(myContractDetailDto.getProjectCode()));// TODO: 2016/12/29 项目编码

        //判断支付的金额
        /*Integer actualAmount = 0;
        if (finReceiBillEntity.getActualTotalAmount() != null) {
            actualAmount = finReceiBillEntity.getActualTotalAmount().multiply(new BigDecimal(100)).intValue();
        }
        int oughtAmount = finReceiBillEntity.getOughtTotalAmount().multiply(new BigDecimal(100)).intValue();
        FinReceiBillEntity bill = new FinReceiBillEntity();
        bill.setFid(billFid);
        bill.setActualTotalAmount(callbackAmount.add(finReceiBillEntity.getActualTotalAmount()==null?new BigDecimal(0):finReceiBillEntity.getActualTotalAmount()));//回调金额+已收金额=已收金额
        if (oughtAmount == actualAmount + Integer.parseInt(dto.getCurrent_money())) {//如果支付和应收相等 已收款
            //更改状态为已收款
            bill.setBillState(ZraConst.BILL_STATE_YSK);//已收款
            LOGGER.info(Thread.currentThread().getId()+"-#支付平台回调#-#状态#：" +ZraConst.BILL_STATE_YSK);
        } else {
            //更改为部分收款
            bill.setBillState(ZraConst.BILL_STATE_BFSK);//部分收款
            LOGGER.info(Thread.currentThread().getId()+"-#支付平台回调#-#状态#：" +ZraConst.BILL_STATE_BFSK);
        }
        int affect1 = this.payService.updateReceiBill(bill);
        LOGGER.info(Thread.currentThread().getId()+"-#支付平台回调#-#修改详情行数#：" + affect1);*/

        BigDecimal actualPayment = new BigDecimal(0);
        //查询应收费用项明细 不考虑企业
        List<FinReceiBillDetailEntity> finReceiBillDetailEntities = this.payService.getFinReceiBillDetai(billFid);
        for (FinReceiBillDetailEntity finReceiBillDetailEntity : finReceiBillDetailEntities) {//循环费用项 判断应收是否大于实收
            Map<String, Object> billDetail = new HashedMap();
            boolean flag = false;//金额不够支付费用项，跳出本次循环
            //传后台
            billDetail.put("billId", finReceiBillDetailEntity.getBillFid());
            billDetail.put("id", finReceiBillDetailEntity.getCostItemId());
            billDetail.put("itemName", finReceiBillDetailEntity.getItemName());
            //modify by tianxf9 支付平台回调，生成收款单失败问题
            BigDecimal diff = finReceiBillDetailEntity.getOughtAmount()==null?new BigDecimal(0):finReceiBillDetailEntity.getOughtAmount()
            		.subtract(finReceiBillDetailEntity.getActualAmount()==null?new BigDecimal(0):finReceiBillDetailEntity.getActualAmount());//应收-实收金额
            //end
            BigDecimal actualDetailAmount = new BigDecimal(0);
            if (callbackAmount.compareTo(diff) >= 0) {
                actualDetailAmount = diff;
                callbackAmount= callbackAmount.subtract(diff);
            }else{
                actualDetailAmount = callbackAmount;
                flag = true;
            }
            actualPayment = actualPayment.add(actualDetailAmount);//收款单实际收款金额
            LOGGER.info(Thread.currentThread().getId()+"-#支付平台回调#-#费用项金额#：" + actualDetailAmount);
            billDetail.put("shis", actualDetailAmount);//收款金额减应收减实收=费用项实收金额
            billDetail.put("roomId", finReceiBillDetailEntity.getRoomId());
            billDetail.put("cityId", finReceiBillEntity.getCityId());
            list.add(billDetail);
            /*//更新应收明细 的已收金额
            FinReceiBillDetailEntity detail = new FinReceiBillDetailEntity();
            detail.setFid(finReceiBillDetailEntity.getFid());
            detail.setActualAmount(actualDetailAmount.add(finReceiBillDetailEntity.getActualAmount()));
            int affect = this.payService.updateReceiBillDetail(detail);
            LOGGER.info(Thread.currentThread().getId()+"-#支付平台回调#-#修改详情行数#：" + affect);*/
            if (flag) {
                break;
            }
        }
        rentPaymentVoucher.put("collectionChannel", ZraConst.COLLECTION_CHANNEL);
        rentPaymentVoucher.put("rentContractCode", myContractDetailDto.getContractCode());
        rentPaymentVoucher.put("contractId", myContractDetailDto.getContractId());
        rentPaymentVoucher.put("projectId", myContractDetailDto.getProjectId());
        rentPaymentVoucher.put("roomName", myContractDetailDto.getRoomNum());
        rentPaymentVoucher.put("customerName", myContractDetailDto.getCustomerName());
        String currentDate = dateFormat.format(new Date());
        rentPaymentVoucher.put("paymentTime", dateFormat.format(paymentTime));
        rentPaymentVoucher.put("createDate", currentDate);
        rentPaymentVoucher.put("collectionDate", currentDate);
        rentPaymentVoucher.put("collectionWay", ZraConst.COLLECTION_WAY);
        rentPaymentVoucher.put("finsettleId", finsettleEntity.getFid());
        rentPaymentVoucher.put("state", "2");//已确认
        rentPaymentVoucher.put("genWay", 0);//自动生成
        //rentPaymentVoucher.put("collectionPersonCode", );// TODO: 2016/12/30
        //rentPaymentVoucher.put("financeConfirmCode", );// TODO: 2016/12/30
        //rentPaymentVoucher.put("collectionPersonName", );// TODO: 2016/12/30
        rentPaymentVoucher.put("factPayment", actualPayment.setScale(2,BigDecimal.ROUND_DOWN));
        rentPaymentVoucher.put("serialNo", dto.getOrder_code());
        rentPaymentVoucher.put("outTradeNo", dto.getOut_trade_no());
        rentPaymentVoucher.put("cityId", finReceiBillEntity.getCityId());

        //调用ZRAMS 保存收款单接口
        Map<String, String> map = new HashedMap();
        map.put("voucher", JSON.toJSONString(rentPaymentVoucher));
        map.put("vouchers", JSON.toJSONString(list));
        map.put("billId", billFid);
        String path = PropUtils.getString("zrams.save.receipt.bill.url");

        LOGGER.info(Thread.currentThread().getId()+"-#保存收款单#-#路径#：" + path);
        LOGGER.info(Thread.currentThread().getId()+"-#保存收款单#-#参数#：" + JSON.toJSONString(map));
        InputStream inputStream = NetUtil.sendPostRequest(path, map);

        String resultJson = NetUtil.getTextContent(inputStream, "utf-8");
        LOGGER.info(Thread.currentThread().getId()+"-#保存收款单#-#结果#：" + resultJson);
    }
    
    public boolean validParams(PaymentNumParamDto dto) {
    	
        
        if(StringUtils.isBlank(dto.getBillFid())) {
        	return false;
        }
        
        if(StringUtils.isBlank(dto.getBillNum())) {
        	return false;
        }
        
        if(dto.getPayAmount()==null) {
        	return false;
        }
        
        if(StringUtils.isBlank(dto.getUid())) {
        	return false;
        }
        
        if(dto.getBillType()==null) {
        	return false;
        }
        
        return true;
        
    }
}
