package com.ziroom.minsu.api.order.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.exception.SOAParseException;
import com.asura.framework.base.util.*;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.api.common.abs.AbstractController;
import com.ziroom.minsu.api.common.constant.ConstDef;
import com.ziroom.minsu.api.common.constant.OrderConst;
import com.ziroom.minsu.api.common.dto.ResponseSecurityDto;
import com.ziroom.minsu.api.evaluate.service.EvalOrderService;
import com.ziroom.minsu.api.house.valueenum.RulesEnum;
import com.ziroom.minsu.api.order.entity.LandlordOrderDetailApiVo;
import com.ziroom.minsu.api.order.entity.base.*;
import com.ziroom.minsu.entity.base.MinsuEleEntity;
import com.ziroom.minsu.entity.customer.CustomerPicMsgEntity;
import com.ziroom.minsu.entity.evaluate.EvaluateOrderEntity;
import com.ziroom.minsu.entity.evaluate.StatsTenantEvaEntity;
import com.ziroom.minsu.entity.order.FinancePenaltyEntity;
import com.ziroom.minsu.entity.order.OrderActivityEntity;
import com.ziroom.minsu.entity.order.OrderConfigEntity;
import com.ziroom.minsu.entity.order.OrderRemarkEntity;
import com.ziroom.minsu.entity.order.UsualContactEntity;
import com.ziroom.minsu.services.basedata.api.inner.CityTemplateService;
import com.ziroom.minsu.services.basedata.entity.EnumVo;
import com.ziroom.minsu.services.cms.api.inner.ActCouponService;
import com.ziroom.minsu.services.common.entity.NameValuePair;
import com.ziroom.minsu.services.common.utils.*;
import com.ziroom.minsu.services.customer.api.inner.CustomerMsgManagerService;
import com.ziroom.minsu.services.customer.entity.CustomerVo;
import com.ziroom.minsu.services.customer.util.UserHeadImgUtils;
import com.ziroom.minsu.services.evaluate.api.inner.EvaluateOrderService;
import com.ziroom.minsu.services.evaluate.dto.EvaluateRequest;
import com.ziroom.minsu.services.evaluate.dto.StatsTenantEvaRequest;
import com.ziroom.minsu.services.order.api.inner.OrderCommonService;
import com.ziroom.minsu.services.order.api.inner.OrderLoadlordService;
import com.ziroom.minsu.services.order.api.inner.OrderRemarkService;
import com.ziroom.minsu.services.order.constant.OrderFeeConst;
import com.ziroom.minsu.services.order.dto.*;
import com.ziroom.minsu.services.order.entity.FinancePenaltyPayRelVo;
import com.ziroom.minsu.services.order.entity.LandlordOrderVo;
import com.ziroom.minsu.services.order.entity.OrderDetailVo;
import com.ziroom.minsu.services.order.entity.OrderInfoVo;
import com.ziroom.minsu.valenum.common.RequestTypeEnum;
import com.ziroom.minsu.valenum.common.UserTypeEnum;
import com.ziroom.minsu.valenum.common.YesOrNoEnum;
import com.ziroom.minsu.valenum.evaluate.EvaluateClientBtnStatuEnum;
import com.ziroom.minsu.valenum.evaluate.EvaluateRulesEnum;
import com.ziroom.minsu.valenum.evaluate.EvaluateStatuEnum;
import com.ziroom.minsu.valenum.evaluate.EvaluateUserEnum;
import com.ziroom.minsu.valenum.house.RentWayEnum;
import com.ziroom.minsu.valenum.order.*;
import com.ziroom.minsu.valenum.traderules.TradeRulesEnum;
import com.ziroom.minsu.valenum.traderules.TradeRulesEnum0020;
import com.ziroom.minsu.valenum.traderules.TradeRulesEnum0020002;
import com.ziroom.minsu.valenum.traderules.TradeRulesEnum007Enum;
import com.ziroom.minsu.valenum.traderules.TradeRulesEnum008Enum;

import org.I0Itec.zkclient.DataUpdater;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>房东 订单列表</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/9/6.
 * @version 1.0
 * @since 1.0
 */
@Controller
@RequestMapping("/orderLan")
public class LandOrderController extends AbstractController{

    /**
     * 日志对象
     */
    private static Logger LOGGER = LoggerFactory.getLogger(LandOrderController.class);


    private String detailUrl = "orderland/43e881/showDetail";

    private String evaluateUrl = "landlordEva/43e881/queryEvaluateInfo";

    private String remarkUrl = "orderland/43e881/toOrderRemarkApp";

    @Value("#{'${pic_base_addr_mona}'.trim()}")
    private String picBaseAddrMona;

    @Value("#{'${default_head_size}'.trim()}")
    private String default_head_size;

    @Value("#{'${USER_DEFAULT_PIC_URL}'.trim()}")
    private String USER_DEFAULT_PIC_URL;

    @Value("#{'${MSIT_BASE_URL}'.trim()}")
    private String msitBaseUrl;

    @Value("#{'${MINSU_M_BOOKING_DETAIL_URL}'.trim()}")
    private String MINSU_M_BOOKING_DETAIL_URL;

    @Value("#{'${MINSU_M_HOUSE_CALENDAR_URL}'.trim()}")
    private String MINSU_M_HOUSE_CALENDAR_URL;

    @Resource(name = "basedata.cityTemplateService")
    private CityTemplateService cityTemplateService;

    @Resource(name = "order.orderCommonService")
    private OrderCommonService orderCommonService;

    @Resource(name = "order.orderRemarkService")
    private OrderRemarkService orderRemarkService;
    
	@Resource(name = "api.evalOrderService")
	private EvalOrderService evalOrderService;
	
	@Resource(name="evaluate.evaluateOrderService")
	private EvaluateOrderService evaluateOrderService;

    @Resource(name = "customer.customerMsgManagerService")
    private CustomerMsgManagerService customerMsgManagerService;

    @Resource(name = "order.orderLoadlordService")
    private OrderLoadlordService orderLoadlordService;

    @Resource(name = "cms.actCouponService")
    private ActCouponService actCouponService;

    @Value("#{'${CUSTOMER_DETAIL_URL}'.trim()}")
    private String CUSTOMER_DETAIL_URL;
    
    /**
     * 房东端订单详情
     *
     * @param
     * @return
     * @author jixd
     * @created 2017年07月28日 14:52:18
     */
    @RequestMapping("${LOGIN_AUTH}/orderDetail")
    //@RequestMapping("${NO_LGIN_AUTH}/orderDetail")
    @ResponseBody
    public ResponseEntity<ResponseSecurityDto> orderDetail(HttpServletRequest request) {
        try {
            String uid = getUserId(request);
            //uid="65279f21-9f94-4c2c-97d3-8ed0b94c330f";
            if (Check.NuNObj(uid)) {
                return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("请登录"), HttpStatus.OK);
            }
            String paramJson = (String) request.getAttribute(ConstDef.DECRYPT_PARAM_ATTRIBUTE);
            LogUtil.info(LOGGER, "【orderDetail】 参数={}", paramJson);
            if (Check.NuNStr(paramJson)) {
                return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("参数异常"), HttpStatus.OK);
            }
            JSONObject paramObj = JSONObject.parseObject(paramJson);
            String orderSn = paramObj.getString("orderSn");
            
            //String orderSn="1711023JO00238095256";
            if (Check.NuNStr(orderSn)) {
                return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("订单号为空"), HttpStatus.OK);
            }
            OrderDetailRequest orderDetailRequest = new OrderDetailRequest();
            orderDetailRequest.setLandlordUid(uid);
            orderDetailRequest.setRequestType(2);
            orderDetailRequest.setOrderSn(orderSn);
            String resultJson = orderCommonService.queryOrderInfoBySn(JsonEntityTransform.Object2Json(orderDetailRequest));
            LogUtil.info(LOGGER, "orderInfo 结果={}", resultJson);
            DataTransferObject orderInfoDto = JsonEntityTransform.json2DataTransferObject(resultJson);
            if (orderInfoDto.getCode() == DataTransferObject.ERROR) {
                return new ResponseEntity<>(ResponseSecurityDto.responseEncrypt(orderInfoDto), HttpStatus.OK);
            }
            OrderDetailVo orderInfo = SOAResParseUtil.getValueFromDataByKey(resultJson, "orderDetailVo", OrderDetailVo.class);
            LandlordOrderDetailApiVo apiVo = new LandlordOrderDetailApiVo();

            //1.填充返回订单基础信息
            this.fillBaseMsg(orderInfo, apiVo);

            //2.处理预订人信息
            DataTransferObject dto = this.fillUserMsg(orderInfo, apiVo);
            if (dto.getCode() == DataTransferObject.ERROR) {
                return new ResponseEntity<>(ResponseSecurityDto.responseEncrypt(dto), HttpStatus.OK);
            }

            //3.处理入住信息  入住时间格式化
            this.fillCheckInMsg(orderInfo, apiVo);

            //4.处理订单金额相关信息
            this.fillMoneyMsg(orderInfo, apiVo);

            //5.出行目地 备注信息
            this.fillTripRemarkMsg(uid, orderInfo, apiVo);

            LogUtil.info(LOGGER, "【orderDetail】 result={}", JsonEntityTransform.Object2Json(apiVo));
            return new ResponseEntity<>(ResponseSecurityDto.responseEncryptOK(apiVo), HttpStatus.OK);
        } catch (Exception e) {
            LogUtil.error(LOGGER, "【orderDetail】 error={}", e);
            return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("服务错误"), HttpStatus.OK);
        }

    }

    /**
     * 填充 备注信息
     *
     * @param uid
     * @param orderInfo
     * @throws SOAParseException
     */
    private void fillTripRemarkMsg(String uid, OrderDetailVo orderInfo, LandlordOrderDetailApiVo apiVo) throws SOAParseException {
        //旅行目地没有不展示
        if (!Check.NuNStr(orderInfo.getTripPurpose())) {
            Item tripPurpose = new Item();
            tripPurpose.setName(OrderConst.FIELD_TRIP_PURPOSE);
            tripPurpose.setValue(orderInfo.getTripPurpose());
            apiVo.setTripPurpose(tripPurpose);
        }

        Map<String, Object> remarkObj = new HashMap<>();
        //备注
        List<Map<String, String>> remark = new ArrayList<>();
        RemarkRequest remarkRequest = new RemarkRequest();
        remarkRequest.setOrderSn(orderInfo.getOrderSn());
        remarkRequest.setUid(uid);
        String remarkResult = orderRemarkService.getRemarkList(JsonEntityTransform.Object2Json(remarkRequest));
        DataTransferObject remarkDto = JsonEntityTransform.json2DataTransferObject(remarkResult);
        if (remarkDto.getCode() == DataTransferObject.SUCCESS) {
            List<OrderRemarkEntity> remarkList = SOAResParseUtil.getListValueFromDataByKey(remarkResult, "list", OrderRemarkEntity.class);
            for (OrderRemarkEntity remarkEntity : remarkList) {
                Map<String, String> remarkMap = new HashMap<>();
                remarkMap.put("fid", remarkEntity.getFid());
                remarkMap.put("content", remarkEntity.getRemarkContent());
                remark.add(remarkMap);
            }
        }
        remarkObj.put("name", OrderConst.FIELD_REMARK);
        remarkObj.put("remarkList", remark);
        remarkObj.put("desc", OrderConst.FIELD_REMARK_DESC);
        apiVo.setRemarks(remarkObj);

        //按钮文案
        List<String> buttonNames = new ArrayList<>();
        Integer orderStatus = orderInfo.getOrderStatus();
        if (orderStatus == OrderStatusEnum.WAITING_CONFIRM.getOrderStatus()) {
            buttonNames.add("拒绝订单");
            buttonNames.add("接受订单");
            //接受订单提示信息
            String startStr = DateUtil.dateFormat(orderInfo.getStartTime(), "MM月dd日");
            String endStr = DateUtil.dateFormat(orderInfo.getEndTime(), "MM月dd日");
            apiVo.setAcceptConfirmMsg((String.format(OrderConst.LANDLORD_ACCEPT_CONFIRM_MSG, orderInfo.getUserName(), startStr, endStr, orderInfo.getHousingDay())));
        }
        if (orderStatus == OrderStatusEnum.CHECKING_OUT.getOrderStatus() || orderStatus == OrderStatusEnum.CHECKING_OUT_PRE.getOrderStatus()) {
            buttonNames.add("确认其他消费");
            apiVo.setOtherHasMoneyConfirmMsg(OrderConst.LANDLORD_OTHER_HAS_MONEY_CONFIRM_MSG);
            apiVo.setOtherNoMoneyConfirmMsg(OrderConst.LANDLORD_OTHER_NO_MONEY_CONFIRM_MSG);

        }


        apiVo.setButtonNames(buttonNames);
    }


    /**
     * 填充订单金额相关信息
     *
     * @param orderInfo
     * @param apiVo
     */
    private void fillMoneyMsg(OrderDetailVo orderInfo, LandlordOrderDetailApiVo apiVo) {
    	 /**
         * 获取房东佣金比率
         */
    	List<String> codeList = new ArrayList<>();
    	List<OrderConfigEntity> listOrderConfigEntities = orderInfo.getListOrderConfigEntities();
    	if(!Check.NuNCollection(listOrderConfigEntities)){
    		for (OrderConfigEntity orderConfigEntity : listOrderConfigEntities) {
    			if(orderConfigEntity.getConfigCode().startsWith(TradeRulesEnum.TradeRulesEnum007.getValue())){
    				codeList.add(orderConfigEntity.getConfigCode());
    			}
			}
    	}
    	
        //房东佣金比率
        codeList.add(TradeRulesEnum008Enum.TradeRulesEnum008002.getValue());
        //长期入住比率
        codeList.add(TradeRulesEnum0020002.TradeRulesEnum0020002002.getValue());
        //长期入住最小天数
        codeList.add(TradeRulesEnum0020.TradeRulesEnum0020001.getValue());

        String confJson = cityTemplateService.getTextListByCodes(null, ValueUtil.transList2Str(codeList));
        DataTransferObject cinfDto = JsonEntityTransform.json2DataTransferObject(confJson);
        if (cinfDto.getCode() == DataTransferObject.ERROR) {
            LogUtil.error(LOGGER, "【orderDetail】获取配置信息异常,rateStrJson:{}", confJson);
            throw new BusinessException("【orderDetail】获取配置信息异常");
        }

        List<MinsuEleEntity> confList = cinfDto.parseData("confList", new TypeReference<List<MinsuEleEntity>>() {
        });

        int longLimitDay = 0;
        double commRate = 0.0,longCommRate = 0.0, cheepComm = 0.0, commCheepRate=0.0;
        for (MinsuEleEntity ele : confList) {
            if (ele.getEleKey().equals(TradeRulesEnum0020.TradeRulesEnum0020001.getValue())) {
                longLimitDay = ValueUtil.getintValue(ele.getEleValue());
            } else if (ele.getEleKey().equals(TradeRulesEnum008Enum.TradeRulesEnum008002.getValue())) {
                commRate = ValueUtil.getdoubleValue(ele.getEleValue());
            } else if (ele.getEleKey().equals(TradeRulesEnum0020002.TradeRulesEnum0020002002.getValue())) {
                longCommRate = ValueUtil.getdoubleValue(ele.getEleValue());
            } else if (ele.getEleKey().startsWith(TradeRulesEnum.TradeRulesEnum007.getValue())) {
                commCheepRate = ValueUtil.getdoubleValue(ele.getEleValue());
            }
        }

        Date endDate = orderInfo.getEndTime();
        if (!Check.NuNObj(orderInfo.getRealEndTime())) {
            endDate = orderInfo.getRealEndTime();
        }
        if (endDate.after(orderInfo.getStartTime())) {
        	if(DateSplitUtil.countDateSplit(orderInfo.getStartTime(), endDate) >= longLimitDay){
        		 //符合长期入住条件 计算实际优惠的佣金值
                //房租 (当前房租 -折扣金额)*房东佣金比*长租优惠率   保留2位小数
            	if(commRate==longCommRate){
            		cheepComm=0.0;
            	}else{
            		
            		cheepComm = BigDecimalUtil.mul((orderInfo.getRentalMoney() - orderInfo.getDiscountMoney()), (commRate-longCommRate));
            		if(commCheepRate>0){
                        cheepComm = BigDecimalUtil.mul(BigDecimalUtil.mul((orderInfo.getRentalMoney() - orderInfo.getDiscountMoney() + orderInfo.getCleanMoney() + orderInfo.getPenaltyMoney()), (commRate-longCommRate)), (1-commCheepRate));
            		}
            	}
                
                //保留2位小数
                cheepComm = BigDecimalUtil.div(cheepComm, 100.0, 2);
        	}
           
        }

        LogUtil.info(LOGGER, "长租天数={},长期住宿收取房东服务费比率={},服务费比率={}, 长租比短租优惠的佣金={}", longLimitDay, longCommRate, commRate, cheepComm);
        String checkOutRulesCode = orderInfo.getCheckOutRulesCode();
        if (DateSplitUtil.countDateSplit(orderInfo.getStartTime(), orderInfo.getEndTime()) >= longLimitDay) {
            //符合长租退订政策
            checkOutRulesCode = RulesEnum.RETURN_LONG.getType();
        }
        //退订政策
        DescItem checkOutRule = new DescItem();
        checkOutRule.setName(OrderConst.FIELD_CHECK_OUT_RULE);
        RulesEnum checkOutEnum = RulesEnum.getEnumByType(checkOutRulesCode);
        checkOutRule.setValue(String.valueOf(checkOutEnum.getCode()));
        checkOutRule.setDesc(checkOutEnum.getTitle());
        apiVo.setCheckOutRule(checkOutRule);



        OrderStatusEnum orderStatusEnum = OrderStatusEnum.getOrderStatusByCode(orderInfo.getOrderStatus());
        //费用明细
        List<Item> moneyItems = new ArrayList<>();
        //房费
        MoneyItem rentalMoney = new MoneyItem();
        rentalMoney.setName(OrderFeeConst.NEED_PAY_COST.getShowName());
        rentalMoney.setValue(DataFormat.formatHundredPriceNoZero(orderInfo.getRentalMoney() - orderInfo.getDiscountMoney()));
        moneyItems.add(rentalMoney);

        //清洁费
        if (orderInfo.getCleanMoney() != 0) {
            MoneyItem cleanMoney = new MoneyItem();
            cleanMoney.setName(OrderFeeConst.ORDER_DETAIL_CLEAN.getShowName());
            cleanMoney.setValue(DataFormat.formatHundredPriceNoZero(orderInfo.getCleanMoney()));
            moneyItems.add(cleanMoney);
        }

        //房客支付给房东的违约金
        if (orderInfo.getPenaltyMoney() > 0) {
            MoneyItem penaltyMoney = new MoneyItem();
            penaltyMoney.setName(OrderFeeConst.ORDER_DETAIL_PENALTY.getShowName());
            penaltyMoney.setValue(DataFormat.formatHundredPriceNoZero(orderInfo.getPenaltyMoney()));
            moneyItems.add(penaltyMoney);
        }

        //其他消费
        if (orderInfo.getOtherMoney() > 0) {
            MoneyItem otherMoney = new MoneyItem();
            otherMoney.setName(OrderFeeConst.ORDER_DETAIL_OTHER.getShowName());
            otherMoney.setValue(DataFormat.formatHundredPriceNoZero(orderInfo.getOtherMoney()));
            moneyItems.add(otherMoney);
        }

        //当前状态下房东的佣金
        int lanCommMoney = orderStatusEnum.getComm(orderInfo.getLanCommMoney(), orderInfo.getRealLanMoney(), orderInfo);

        //服务费
        String lanCommRate = String.valueOf(BigDecimalUtil.round(BigDecimalUtil.mul(commRate, 100.0), 2)) + "%";
        MoneyDescTipsMsgItem serviceChargeMoney = new MoneyDescTipsMsgItem();
        serviceChargeMoney.setName(OrderFeeConst.NEED_PAY_LAN_COMMISSION.getShowName());
        serviceChargeMoney.setValue(DataFormat.formatHundredPriceNoZero(lanCommMoney));
        serviceChargeMoney.setSymbol(OrderFeeConst.NEED_PAY_LAN_COMMISSION.getSymbol());
        serviceChargeMoney.setTipMsg(String.format(OrderConst.LANDLORD_ORDER_COMM_MONEY_TIPS, lanCommRate));
        if (cheepComm > 0.0) {
            serviceChargeMoney.setDesc(String.format(OrderConst.LANDLORD_ORDER_COMM_MONEY_CHEEP_TIPS, String.valueOf(cheepComm)));
        }
        moneyItems.add(serviceChargeMoney);



        //罚款
        List<FinancePenaltyPayRelVo> financePenaltyPayRelVos = orderInfo.getListFinancePenaltyPayRelVo();
        //总共的罚款，来自其他订单的罚款
        int totalPenaltyFee = 0;
        if (!Check.NuNCollection(financePenaltyPayRelVos)) {
            MoneySubListItem realPenaltyMoney = new MoneySubListItem();
            realPenaltyMoney.setName(OrderFeeConst.ORDER_DETAIL_FINE.getShowName());
            realPenaltyMoney.setSymbol(OrderFeeConst.ORDER_DETAIL_FINE.getSymbol());
            realPenaltyMoney.setSubTitle(OrderConst.FIELD_PENALTY_NAME);

            List<Map<String, Object>> penaltyOrders = new ArrayList<>();
            for (FinancePenaltyPayRelVo penaltyPayRelVo : financePenaltyPayRelVos) {
                Map<String, Object> itemMap = new HashMap<>();
                itemMap.put("orderSn", penaltyPayRelVo.getPenaltyOrderSn());
                itemMap.put("fee", String.valueOf(DataFormat.formatHundredPriceNoZero(penaltyPayRelVo.getTotalFee())));
                totalPenaltyFee += penaltyPayRelVo.getTotalFee();
                penaltyOrders.add(itemMap);
            }
            realPenaltyMoney.setSubList(penaltyOrders);
            realPenaltyMoney.setValue(DataFormat.formatHundredPriceNoZero(totalPenaltyFee));
            moneyItems.add(realPenaltyMoney);
        }

        apiVo.setMoneyItems(moneyItems);

        //收入
        MoneyDescTipsMsgItem orderIncome = new MoneyDescTipsMsgItem();
        //结算状态 显示预计收入，结算完成显示真实收入
        orderIncome.setName(orderStatusEnum.getIncomeName(orderInfo));
        orderIncome.setDesc(OrderConst.FIELD_INCOME_DESC);

        //收入 = 房租 +清洁费 + 额外消费 + 违约金 - 房东佣金 - 房租折扣 - 总罚款金额
        int income = orderInfo.getRentalMoney() + orderInfo.getCleanMoney() + orderInfo.getOtherMoney() + orderInfo.getPenaltyMoney()
                - lanCommMoney - orderInfo.getDiscountMoney() - totalPenaltyFee;
        orderIncome.setValue(DataFormat.formatHundredPriceNoZero(income));
        apiVo.setOrderIncome(orderIncome);

        //本单罚款
        if (!Check.NuNObj(orderInfo.getFinancePenalty())) {
            MoneyItem orderPenalty = new MoneyItem();
            orderPenalty.setName(OrderConst.FIELD_PENALTY_MONEY);
            FinancePenaltyEntity financePenalty = orderInfo.getFinancePenalty();
            orderPenalty.setValue(DataFormat.formatHundredPriceNoZero(financePenalty.getPenaltyFee()));
            orderPenalty.setSymbol(OrderFeeConst.ORDER_DETAIL_FINE.getSymbol());
            apiVo.setOrderPenalty(orderPenalty);
        }

        //其他消费 订单状态退房后的状态显示额外消费
        if (isShowOtherMoneyItem(orderInfo.getOrderStatus())) {
            Map<String, Object> otherMoneyMap = new HashMap<>();
            Integer otherMoney = orderInfo.getOtherMoney();
            int isShow = 1;
            //其他消费描述
            String desc = "";
            otherMoneyMap.put("name", OrderConst.FIELD_OTHER_MONEY);
            Map<String, Object> otherMoneyDetail = new HashMap<>();
            otherMoneyMap.put("detail", otherMoneyDetail);
            if (otherMoney.intValue() == 0 && (orderInfo.getOrderStatus() == OrderStatusEnum.CHECKING_OUT.getOrderStatus() || orderInfo.getOrderStatus() == OrderStatusEnum.CHECKING_OUT_PRE.getOrderStatus())) {
                String timeStrJson = cityTemplateService.getTextValue(null, String.valueOf(TradeRulesEnum.TradeRulesEnum0015.getValue()));
                DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(timeStrJson);
                if (resultDto.getCode() == DataTransferObject.ERROR) {
                    LogUtil.error(LOGGER, "【orderDetail】获取房东额外消费时限,timeStrJson:{}", timeStrJson);
                    throw new BusinessException("【orderDetail】获取房东额外消费时限异常");
                }
                String timeLimit = ValueUtil.getStrValue(resultDto.getData().get("textValue"));
                //房客退房24小时确认
                desc = String.format(OrderConst.FIELD_OTHER_MONEY_DESC, timeLimit);
            }
            if (otherMoney.intValue() == 0 && !(orderInfo.getOrderStatus() == OrderStatusEnum.CHECKING_OUT.getOrderStatus() || orderInfo.getOrderStatus() == OrderStatusEnum.CHECKING_OUT_PRE.getOrderStatus())) {
                desc = OrderConst.FIELD_OTHER_MONEY_DESC_1;
                //不能展示详情
                isShow = 0;
            }

            otherMoneyMap.put("desc", desc);
            otherMoneyMap.put("isShow", isShow);

            MoneyDescItem otherMoneyItem = new MoneyDescItem();
            otherMoneyItem.setName(OrderConst.FIELD_OTHER_MONEY_VALUE);
            otherMoneyItem.setDesc(OrderConst.FIELD_OTHER_MONEY_VALUE_DESC);
            if (orderInfo.getOtherMoney() != 0) {
                otherMoneyItem.setValue(DataFormat.formatHundredPriceNoZero(orderInfo.getOtherMoney()));
            }


            DescItem otherMoneyReasonItem = new DescItem();
            otherMoneyReasonItem.setName(OrderConst.FIELD_OTHER_MONEY_DETAIL);
            otherMoneyReasonItem.setDesc(OrderConst.FIELD_OTHER_MONEY_DETAIL_DESC);
            otherMoneyReasonItem.setValue(orderInfo.getOtherMoneyDes());

            otherMoneyDetail.put("money", otherMoneyItem);
            otherMoneyDetail.put("reason", otherMoneyReasonItem);
            
            apiVo.setOtherMoney(otherMoneyMap);
        }
      //填充押金
        MoneyItem depositMoney = new MoneyItem();
        depositMoney.setName(OrderFeeConst.ORDER_DETAIL_DEPOSIT.getShowName());
        depositMoney.setValue(DataFormat.formatHundredPriceNoZero(orderInfo.getDepositMoney()));
        apiVo.setDepositMoney(depositMoney);

    }

    /**
     * 处理入住信息
     *
     * @param orderInfo
     * @param apiVo
     * @return
     */
    private void fillCheckInMsg(OrderDetailVo orderInfo, LandlordOrderDetailApiVo apiVo) {
        List<UsualContactEntity> usualContactEntities = orderInfo.getListUsualContactEntities();
        ItemSubListItem contacts = new ItemSubListItem();
        contacts.setName(OrderConst.FIELD_CHECK_IN_PEOPLE);
        contacts.setSubTitle(OrderConst.FIELD_CHECK_IN_PEOPLE);
        contacts.setValue(orderInfo.getPeopleNum() + "人");
        List<Map<String, Object>> peopleList = new ArrayList<>();
        for (UsualContactEntity contactEntity : usualContactEntities) {
            Map<String, Object> map = new HashMap<>();
            Integer cardType = contactEntity.getCardType();
            Item nameItem = new Item();
            nameItem.setName("姓名");
            nameItem.setValue(contactEntity.getConName());
            map.put("name", nameItem);
            String sex = "", age = "";
            //如果是身份证 计算性别 年龄
            if (cardType == CardTypeEnum.SHENFENZHENG.getCode()) {
                String cardValue = contactEntity.getCardValue();
                sex = CheckIdCardUtils.getSexByIdCard(cardValue);
                age = String.valueOf(CheckIdCardUtils.getAgeByIdCard(cardValue));
            }
            Item sexItem = new Item();
            sexItem.setName("性别");
            sexItem.setValue(sex);
            Item ageItem = new Item();
            ageItem.setName("年龄");
            ageItem.setValue(age);
            map.put("sex", sexItem);
            map.put("age", ageItem);
            peopleList.add(map);
        }
        contacts.setSubList(peopleList);
        apiVo.setContacts(contacts);

        //入住时间
        Map<String, String> startTime = new HashMap<>();
        startTime.put("name", OrderConst.FIELD_CHECK_IN_TIME);
        startTime.put("date", DateUtil.dateFormat(orderInfo.getStartTime(), "yyyy年MM月dd日"));
        startTime.put("week", DateSplitUtil.getWeekOfDate(orderInfo.getStartTime()));
        //离开时间
        Map<String, String> endTime = new HashMap<>();
        endTime.put("name", OrderConst.FIELD_CHECK_OUT_TIME);
        endTime.put("date", DateUtil.dateFormat(orderInfo.getEndTime(), "yyyy年MM月dd日"));
        endTime.put("week", DateSplitUtil.getWeekOfDate(orderInfo.getEndTime()));

        apiVo.setStartTime(startTime);
        apiVo.setEndTime(endTime);

    }

    /**
     * 填充预订人信息
     * 1.支付后展示用户电话
     * 2.退房后7天不展示用户电话
     * @param orderInfo
     * @param apiVo
     * @return
     * @throws
     */
    private DataTransferObject fillUserMsg(OrderDetailVo orderInfo, LandlordOrderDetailApiVo apiVo) throws SOAParseException {
        apiVo.setUserName(orderInfo.getUserName());
        apiVo.setUserUid(orderInfo.getUserUid());
        String userTel = "";
        Integer payStatus = orderInfo.getPayStatus();
        if (payStatus == OrderPayStatusEnum.HAS_PAY.getPayStatus()) {
            Date realEndTime = orderInfo.getRealEndTime();
            //不展示联系方式的天数
            String timeStrJson = cityTemplateService.getTextValue(null, String.valueOf(TradeRulesEnum.TradeRulesEnum0021.getValue()));
            DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(timeStrJson);
            if (resultDto.getCode() == DataTransferObject.ERROR) {
                LogUtil.error(LOGGER, "【orderDetail】获取用户电话时限失败,timeStrJson:{}", timeStrJson);
                throw new BusinessException("【orderDetail】获取用户电话时限失败");
            }
            int timeLimit = ValueUtil.getintValue(resultDto.getData().get("textValue"));
            if (Check.NuNObj(realEndTime) || (!Check.NuNObj(realEndTime) && DateUtil.getDatebetweenOfDayNum(realEndTime, new Date()) <= timeLimit)) {
                userTel = orderInfo.getUserTel();
            }
        }
        apiVo.setUserTel(userTel);
        apiVo.setUserHeadPic(USER_DEFAULT_PIC_URL);
        DataTransferObject customerDto = JsonEntityTransform.json2DataTransferObject(customerMsgManagerService.getCutomerVo(orderInfo.getUserUid()));
        if (customerDto.getCode() == DataTransferObject.SUCCESS) {
            CustomerVo customerVo = customerDto.parseData("customerVo", new TypeReference<CustomerVo>() {
            });
            apiVo.setUserHeadPic(customerVo.getUserPicUrl());
        }

        StatsTenantEvaRequest evaRequest = new StatsTenantEvaRequest();
        evaRequest.setTenantUid(orderInfo.getUserUid());
        String evaJson = evaluateOrderService.queryStatsTenantEvaByCondion(JsonEntityTransform.Object2Json(evaRequest));
        DataTransferObject evaDto = JsonEntityTransform.json2DataTransferObject(evaJson);
        if (evaDto.getCode() == DataTransferObject.ERROR) {
            return evaDto;
        }
        List<StatsTenantEvaEntity> evaList = SOAResParseUtil.getListValueFromDataByKey(evaJson, "lisTenantEvaEntities", StatsTenantEvaEntity.class);
        Integer evaTotal = 0;
        Float evaAva = 0f;
        //如果没有评价 默认为0
        if (evaList.size() > 0) {
            StatsTenantEvaEntity statsTenantEvaEntity = evaList.get(0);
            evaTotal = statsTenantEvaEntity.getEvaTotal();
            evaAva = statsTenantEvaEntity.getLandSatisfAva();
        }
        apiVo.setUserEvaTotal(evaTotal);
        apiVo.setEvaAva(evaAva);

        String realEndTime = "";
        if (!Check.NuNObj(orderInfo.getRealEndTime())) {
            realEndTime = DateUtil.dateFormat(orderInfo.getRealEndTime(), "yyyy-MM-dd HH:mm:ss");
        }
        //跳转预订人信息页
        String url = MINSU_M_BOOKING_DETAIL_URL + "?orderStatus=" + orderInfo.getOrderStatus() + "&payStatus=" + orderInfo.getPayStatus()
                + "&userUid=" + orderInfo.getUserUid() + "&orderSn=" + orderInfo.getOrderSn();
        if (!Check.NuNStr(realEndTime)) {
            url += "&checkOutTime=" + realEndTime;
        }
        apiVo.setUserInfoUrl(url);
        return evaDto;
    }

    /**
     * 填充订单基础信息
     *
     * @param orderInfo
     * @param apiVo
     * @return
     */
    private void fillBaseMsg(OrderDetailVo orderInfo, LandlordOrderDetailApiVo apiVo) {
        //填充基础信息
        apiVo.setOrderTitle(String.format(OrderConst.LANDLORD_ORDER_DETAIL_TITLE, orderInfo.getUserName(), orderInfo.getHousingDay()));
        apiVo.setOrderSn(orderInfo.getOrderSn());
        apiVo.setHouseFid(orderInfo.getHouseFid());
        apiVo.setRoomFid(orderInfo.getRoomFid());
        apiVo.setRentWay(orderInfo.getRentWay());
        apiVo.setHouseName(orderInfo.transName());
        apiVo.setCreateTime(DateUtil.dateFormat(orderInfo.getCreateTime(), "yyyy.MM.dd"));
        apiVo.setRealEndTime(DateUtil.dateFormat(orderInfo.getRealEndTime(), "yyyy-MM-dd HH:mm:ss"));
        apiVo.setOrderStatus(orderInfo.getOrderStatus());
        apiVo.setPayStatus(orderInfo.getPayStatus());
        //订单状态
        OrderStatusEnum orderStatusEnum = OrderStatusEnum.getOrderStatusByCode(orderInfo.getOrderStatus());
        if (!Check.NuNObj(orderStatusEnum)) {
            apiVo.setOrderStatusName(orderStatusEnum.getShowName(orderInfo));
        }
        apiVo.setLandlordUid(orderInfo.getLandlordUid());
    }

    /**
     * 是否显示额外消费项
     *
     * @return
     */
    private boolean isShowOtherMoneyItem(int orderStatus) {
        return (orderStatus == OrderStatusEnum.CHECKING_OUT.getOrderStatus()
                || orderStatus == OrderStatusEnum.CHECKING_OUT_PRE.getOrderStatus() || orderStatus == OrderStatusEnum.WAITING_EXT.getOrderStatus()
                || orderStatus == OrderStatusEnum.WAITING_EXT_PRE.getOrderStatus() || orderStatus == OrderStatusEnum.FINISH_COMMON.getOrderStatus()
                || orderStatus == OrderStatusEnum.FINISH_PRE.getOrderStatus());

    }


    /**
     * 校验订单备注数量
     * @author afi
     * @param request
     * @param orderSn
     * @return
     */
    @RequestMapping("${LOGIN_AUTH}/checkRemarkCount")
    @ResponseBody
    public ResponseEntity<ResponseSecurityDto> checkRemarkCount(HttpServletRequest request,String orderSn){
        try{
            /*** 获取当前的头信息 */
            String uid = getUserId(request);
            if (Check.NuNObj(uid)){
                return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("请登录"), HttpStatus.OK);
            }
            if (Check.NuNObj(orderSn)){
                return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("参数为空"), HttpStatus.OK);
            }
            RemarkRequest remarkRequest = new RemarkRequest();
            remarkRequest.setOrderSn(orderSn);
            remarkRequest.setUid(uid);
            String resultJson = orderRemarkService.getRemarkCount(JsonEntityTransform.Object2Json(remarkRequest));
            DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(resultJson);
            if (dto.getCode() == DataTransferObject.SUCCESS){
                return new ResponseEntity<>(ResponseSecurityDto.responseEncryptOK(dto.getData()),HttpStatus.OK);
            }else {
                return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail(dto.getMsg()),HttpStatus.OK);
            }

        }catch(Exception e){
            LogUtil.error(LOGGER, "checkRemarkCount is error, e={}",e);
            return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("服务错误"),HttpStatus.OK);
        }
    }



    /**
     * 获取当前的订单列表
     * @param request
     * @return
     */
    @RequestMapping(value="/${LOGIN_AUTH}/showList",method= RequestMethod.POST)
    //@RequestMapping(value="/${NO_LGIN_AUTH}/showList",method= RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseSecurityDto> showOrderList(HttpServletRequest request){
        try{
            /*** 获取当前的头信息 */
            String uid = getUserId(request);
            //uid="d5811cc2-8fbf-45cf-8755-9d4dae4cc3c7";
            String paramJson = (String) request.getAttribute(ConstDef.DECRYPT_PARAM_ATTRIBUTE);
            if (Check.NuNStr(paramJson)){
                LogUtil.error(LOGGER, "参数异常");
                return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("参数异常"),HttpStatus.OK);
            }
            LandOrderListRequest landRequest = JsonEntityTransform.json2Object(paramJson,LandOrderListRequest.class);
            if(Check.NuNStr(uid)){
                return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("没有权限"),HttpStatus.OK);
            }

            LandlordOrderTypeEnum landlordOrderTypeEnum = LandlordOrderTypeEnum.getLandlordOrderTypeEnumByCode(landRequest.getLanOrderType());
            if(Check.NuNObj(landlordOrderTypeEnum)){
                return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("没有状态"),HttpStatus.OK);
            }
            OrderRequest orderRequest = new OrderRequest();
            BeanUtils.copyProperties(landRequest,orderRequest);
            //用户请求
            orderRequest.setRequestType(RequestTypeEnum.LANDLORD.getRequestType());
            orderRequest.setLandlordUid(uid);
            LogUtil.info(LOGGER, "getOrderList  orderRequest={}", JsonEntityTransform.Object2Json(orderRequest));
            String resultJson = orderCommonService.getOrderList(JsonEntityTransform.Object2Json(orderRequest));
            LogUtil.info(LOGGER, "getOrderList  resultJson={}", resultJson);
            DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(resultJson);
            if(resultDto.getCode() != DataTransferObject.SUCCESS){
                return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail(resultDto.getMsg()), HttpStatus.OK);
            }
            List<OrderInfoVo> orderLandList = SOAResParseUtil.getListValueFromDataByKey(resultJson, "orderHouseList", OrderInfoVo.class);
            Integer total = SOAResParseUtil.getValueFromDataByKey(resultJson, "size", Integer.class);
            //将当前的订单列表转化成app接口需要的对象
            List<LandlordOrderVo> orderList = getListLandlordOrderVo(orderLandList, orderRequest);
            Map<String,Object> resultMap = new HashMap<String,Object>();
            resultMap.put("total", total);
            resultMap.put("orderList", orderList);
            
            //将至的订单接口，需要挺尸返归一个展示字段isShowMoreOrder 0不展示  1展示，是否需要展示     “房东首页-6个月内即将到来的订单”
            if(orderRequest.getLanOrderType() ==LandlordOrderTypeEnum.COMING_TEN_ORDERS.getCode()){
                 Integer isShowMoreOrder =  YesOrNoEnum.NO.getCode();
           	     if(total > 10){
           	    	isShowMoreOrder = YesOrNoEnum.YES.getCode();	
           	     }
           	     resultMap.put("isShowMoreOrder", isShowMoreOrder);
           	     //如果是page>1, list得返回为空，否则app端会将第10条之后的数据展示出来
           	     if(landRequest.getPage()>1){
           	    	resultMap.put("orderList", null);
           	     }
            }
            return new ResponseEntity<>(ResponseSecurityDto.responseEncryptOK(resultMap),HttpStatus.OK);
        }catch(Exception e){
            LogUtil.error(LOGGER, "showOrderList is error, e={}",e);
            return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("服务错误"),HttpStatus.OK);
        }
    }


    /**
     *
     * 封装房客端订单列表返回数据
     *
     * @author jixd
     * @created 2016年5月9日 下午3:25:04
     *
     * @param landlordOrderList
     * @return
     */
    private List<LandlordOrderVo> getListLandlordOrderVo(List<OrderInfoVo> landlordOrderList, OrderRequest orderRequest){
        //评价关闭入口时间（天）
        String timeStrJson = cityTemplateService.getTextValue(null, String.valueOf(EvaluateRulesEnum.EvaluateRulesEnum003.getValue()));
        DataTransferObject limitDto = JsonEntityTransform.json2DataTransferObject(timeStrJson);
        String limitStr = null;
        if(limitDto.getCode() == DataTransferObject.SUCCESS){
            limitStr = limitDto.parseData("textValue", new TypeReference<String>() {});
        }else {
            LogUtil.error(LOGGER,"获取评价关闭入口异常：rst：{}",limitStr);
        }
        //将当前的时间住转化成天
        int  jumpDay = ValueUtil.getintValue(limitStr);

        //如果lanOrderType==11或者12
        SimpleDateFormat format = new SimpleDateFormat("MM月dd日");
        SimpleDateFormat format1 = new SimpleDateFormat("YYYY/MM/dd");
        List<LandlordOrderVo> list = new ArrayList<LandlordOrderVo>();
        List<LandlordOrderVo> todayUnCheckInList = new ArrayList<LandlordOrderVo>();
        List<LandlordOrderVo> todayHasCheckInList = new ArrayList<LandlordOrderVo>();
        List<LandlordOrderVo> todayWillLeaveList = new ArrayList<LandlordOrderVo>();
        List<LandlordOrderVo> ortherDayList = new ArrayList<LandlordOrderVo>();
        if(!Check.NuNCollection(landlordOrderList)){
            for (OrderInfoVo orderInfoVo : landlordOrderList) {
                LandlordOrderVo landlordOrderVo =  new LandlordOrderVo();
                landlordOrderVo.setOrderSn(orderInfoVo.getOrderSn());
                landlordOrderVo.setContactsNum(orderInfoVo.getPeopleNum());
                landlordOrderVo.setStartTimeStr(orderInfoVo.getStartTimeStr());
                landlordOrderVo.setEndTimeStr(orderInfoVo.getEndTimeStr());
                landlordOrderVo.setHouseAddr(orderInfoVo.getHouseAddr());
                //根据整租还是合租显示房源名称
                int rentWay = orderInfoVo.getRentWay();
                landlordOrderVo.setRentWay(rentWay);
                if(rentWay == RentWayEnum.HOUSE.getCode()){
                    landlordOrderVo.setHouseName(orderInfoVo.getHouseName());
                }else if(rentWay == RentWayEnum.ROOM.getCode()){
                    landlordOrderVo.setHouseName(orderInfoVo.getRoomName());
                }else if(rentWay == RentWayEnum.BED.getCode()){
                    landlordOrderVo.setHouseName(orderInfoVo.getRoomName());
                }else{
                    landlordOrderVo.setHouseName(orderInfoVo.getHouseName());
                }

                landlordOrderVo.setOrderStatus(orderInfoVo.getOrderStatus());
                landlordOrderVo.setHousingDay(orderInfoVo.getHousingDay());
                //显示订单状态名称
                OrderStatusEnum orderStatusEnum = OrderStatusEnum.getOrderStatusByCode(orderInfoVo.getOrderStatus());
                if(!Check.NuNObj(orderStatusEnum)){
                    landlordOrderVo.setOrderStatusShowName(orderStatusEnum.getShowName(orderInfoVo));
                }
                landlordOrderVo.setUserUid(orderInfoVo.getUserUid());
                landlordOrderVo.setLandlordUid(orderInfoVo.getLandlordUid());
                double money = BigDecimalUtil.div(orderInfoVo.getNeedPay() + orderInfoVo.getCouponMoney() + orderInfoVo.getActMoney(),100);
                //double money = BigDecimalUtil.div(orderInfoVo.getNeedPay() + orderInfoVo.getCouponMoney(),100);
                landlordOrderVo.setNeedMoney(money);
                landlordOrderVo.setUserName(orderInfoVo.getUserName());
                //房源照片
                landlordOrderVo.setHousePicUrl(PicUtil.getSpecialPic(picBaseAddrMona, orderInfoVo.getPicUrl(), default_head_size));
                int evaStatus = orderInfoVo.getEvaStatus();
                
                landlordOrderVo.setEvaStatus(OrderEvaStatusEnum.NO_EVA.getCode());

                
                if(evaStatus == OrderEvaStatusEnum.LANLORD_EVA.getCode() || evaStatus == OrderEvaStatusEnum.ALL_EVA.getCode()
                		|| ((evaStatus == OrderEvaStatusEnum.WAITINT_EVA.getCode() || evaStatus == OrderEvaStatusEnum.UESR_HVA_EVA.getCode()) &&
                				(orderInfoVo.getOrderStatus() >= OrderStatusEnum.CHECKING_OUT.getOrderStatus() )
        						&& !Check.NuNObj(orderInfoVo.getRealEndTime()) && new Date().before(DateSplitUtil.jumpDate(orderInfoVo.getRealEndTime(),jumpDay)))){
                	
                	landlordOrderVo.setEvaStatus(evaStatus);
                	
                } 
				
				
				if(evaStatus != OrderEvaStatusEnum.NO_EVA.getCode()){
					landlordOrderVo.setEvaluateUrl(this.getEvaluateUrl(orderInfoVo.getOrderSn()));
				}
                
                landlordOrderVo.setDetailUrl(this.getDetailUrl(orderInfoVo.getOrderSn()));
                landlordOrderVo.setRemarkUrl(this.getRemarkUrl(orderInfoVo.getOrderSn()));
                
                EvaluateRequest evaluateRequest  = new EvaluateRequest();
				evaluateRequest.setOrderSn(orderInfoVo.getOrderSn());
				evaluateRequest.setEvaUserType(UserTypeEnum.All.getUserType());
				evaluateRequest.setEvaUserUid(orderInfoVo.getLandlordUid());
				
				DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(evaluateOrderService.queryEvaluateByOrderSn(JsonEntityTransform.Object2Json(evaluateRequest)));

				if(dto.getCode()==DataTransferObject.SUCCESS){
					
					Map<String, Object> map = dto.parseData("evaluateMap", new TypeReference<Map<String, Object>>() {
					});
					
					LogUtil.debug(LOGGER, "评价查询结果：orderSn={},evalMap={}", orderInfoVo.getOrderSn(),JsonEntityTransform.Object2Json(map));
					
					List<EvaluateOrderEntity>  listOrderEvaluateOrderEntities = null;
					if(map.get("listOrderEvaluateOrder") !=null){
						listOrderEvaluateOrderEntities = JsonEntityTransform.json2ObjectList(JsonEntityTransform.Object2Json(map.get("listOrderEvaluateOrder")), EvaluateOrderEntity.class);
					}
					if(!Check.NuNCollection(listOrderEvaluateOrderEntities)){
						for (EvaluateOrderEntity evaluateOrderEntity : listOrderEvaluateOrderEntities) {
							
							if(evaluateOrderEntity.getEvaUserType().intValue() == EvaluateUserEnum.LAN.getCode()){
								orderInfoVo.setLandEvaStatu(evaluateOrderEntity.getEvaStatu());
								if(evaluateOrderEntity.getEvaStatu()==EvaluateStatuEnum.ONLINE.getEvaStatuCode()){
									orderInfoVo.setLandEvalTime(evaluateOrderEntity.getLastModifyDate());
								}
							}else if(evaluateOrderEntity.getEvaUserType().intValue() == EvaluateUserEnum.TEN.getCode()){
								orderInfoVo.setTenantEvaStatu(evaluateOrderEntity.getEvaStatu());
								if(evaluateOrderEntity.getEvaStatu()==EvaluateStatuEnum.ONLINE.getEvaStatuCode()){
									orderInfoVo.setTenantEvalTime(evaluateOrderEntity.getLastModifyDate());
								}
							}else if(evaluateOrderEntity.getEvaUserType().intValue() == EvaluateUserEnum.LAN_REPLY.getCode()){
								orderInfoVo.setLandReplyTime(evaluateOrderEntity.getCreateTime());
							}  
							
						}

					} 
					
				}
                
                EvaluateClientBtnStatuEnum pjStatusEnum = evalOrderService.getPjStatusEnum(orderInfoVo, RequestTypeEnum.LANDLORD.getRequestType());
                
                if(!Check.NuNObj(pjStatusEnum)){        	
                	landlordOrderVo.setPjStatus(pjStatusEnum.getEvaStatuCode());
                	landlordOrderVo.setPjButton(pjStatusEnum.getEvaStatuName());
                }

                //房东取消订单退房状态 不能评价
                if (orderInfoVo.getOrderStatus() == OrderStatusEnum.CHECKED_IN.FINISH_LAN_APPLY.getOrderStatus() || orderInfoVo.getOrderStatus()== OrderStatusEnum.FINISH_NEGOTIATE.getOrderStatus()
                		|| orderInfoVo.getOrderStatus() == OrderStatusEnum.CANCEL_NEGOTIATE.getOrderStatus()){
                    landlordOrderVo.setPjStatus(EvaluateClientBtnStatuEnum.N_EVAL.getEvaStatuCode());
                    landlordOrderVo.setPjButton(EvaluateClientBtnStatuEnum.N_EVAL.getEvaStatuName());
                }
                
                //2017-11.27版本， 房东首页改版，待处理订单点击按钮
                if (orderInfoVo.getOrderStatus() == OrderStatusEnum.WAITING_CONFIRM.getOrderStatus()){
                    landlordOrderVo.setWaitDealOrderButton("确认订单");
                }
                if (orderInfoVo.getOrderStatus() == OrderStatusEnum.CHECKING_OUT.getOrderStatus() || orderInfoVo.getOrderStatus() == OrderStatusEnum.CHECKING_OUT_PRE.getOrderStatus() ) {
                    landlordOrderVo.setWaitDealOrderButton("确认其他消费");

                }
                //将至订单和待处理订单，填充头像和时间格式
                if(orderRequest.getLanOrderType() ==LandlordOrderTypeEnum.COMING_TEN_ORDERS.getCode() 
                    	|| orderRequest.getLanOrderType() ==LandlordOrderTypeEnum.COMING_All_ORDERS.getCode()
                    	|| orderRequest.getLanOrderType() ==LandlordOrderTypeEnum.PENDING_NEW.getCode()){
                    	  //填充客户头像
                      	   CustomerPicMsgEntity customerPicMsg = UserHeadImgUtils.getHeadImgFromZiroom(orderInfoVo.getUserUid(), CUSTOMER_DETAIL_URL);
                           if (!Check.NuNObj(customerPicMsg)) {
                          	 //String headPicUrl = PicUtil.getFullPic(picBaseAddrMona, customerPicMsg.getPicBaseUrl(), customerPicMsg.getPicSuffix(), default_head_size);
                          	 landlordOrderVo.setHeadPicUrl(customerPicMsg.getPicBaseUrl());
                           }
                           
                          //待处理订单，格式化开始时间和结束时间，如2017/11/15-2017/11/16
                       	  landlordOrderVo.setStartTimeStr(format1.format(orderInfoVo.getStartTime()));
                       	  landlordOrderVo.setEndTimeStr(format1.format(orderInfoVo.getEndTime()));
                }
                
                //2017-11.27版本， 房东首页改版，“将至订单”, 填充订单状态展示文案展示文案 
                if(orderRequest.getLanOrderType() ==LandlordOrderTypeEnum.COMING_TEN_ORDERS.getCode() || orderRequest.getLanOrderType() ==LandlordOrderTypeEnum.COMING_All_ORDERS.getCode()){
                	//填充房源或房间fid，“给Ta发消息”功能要用
                	landlordOrderVo.setHouseFid(orderInfoVo.getHouseFid());
                	if(RentWayEnum.ROOM.getCode()==orderInfoVo.getRentWay()){
                		landlordOrderVo.setHouseFid(orderInfoVo.getRoomFid());
                	}
                	
                	//待入住，开始时间是今天的，放到第一顺位
                	if(OrderStatusEnum.WAITING_CHECK_IN.getOrderStatus()==orderInfoVo.getOrderStatus()
                			&& DateUtil.dateFormat(new Date()).equals(DateUtil.dateFormat(orderInfoVo.getStartTime()))){
                		landlordOrderVo.setStartTimeStr("今天");
                		todayUnCheckInList.add(landlordOrderVo);
                		continue;
                	}
                	
                	//已入住的状态的，放在第二,三顺位
                	if(OrderStatusEnum.CHECKED_IN.getOrderStatus() == orderInfoVo.getOrderStatus() || OrderStatusEnum.CHECKED_IN_BILL.getOrderStatus() == orderInfoVo.getOrderStatus()){
                    	   String endTime = DateUtil.dateFormat(orderInfoVo.getEndTime());
                    	   String now = DateUtil.dateFormat(new Date());
                    	   if(!Check.NuNStr(endTime) && !Check.NuNStr(now) && endTime.equals(now)){
                    		   //即将离开的放在第三顺位
                    		   landlordOrderVo.setStartTimeStr("今天");
                    		   landlordOrderVo.setOrderStatusShowName("即将离开");
                    		   todayWillLeaveList.add(landlordOrderVo);
                    		   continue;
                    	   }else{
                    		   //已入住的状态的，放在第二顺位
                    		   landlordOrderVo.setStartTimeStr("今天");
                    		   landlordOrderVo.setOrderStatusShowName("入住中");
                    		   todayHasCheckInList.add(landlordOrderVo);
                    		   continue;
                    	   }
                     }
                	//非今天的放在第四顺位
                	landlordOrderVo.setStartTimeStr(format.format(orderInfoVo.getStartTime()));
                	ortherDayList.add(landlordOrderVo);
                	continue;
                }
                list.add(landlordOrderVo);
            }
            //需要对“将至订单”，重新排序
            if(orderRequest.getLanOrderType() ==LandlordOrderTypeEnum.COMING_TEN_ORDERS.getCode() || orderRequest.getLanOrderType() ==LandlordOrderTypeEnum.COMING_All_ORDERS.getCode()){
            	//重新排序
            	list.addAll(todayUnCheckInList);
                list.addAll(todayHasCheckInList);
                list.addAll(todayWillLeaveList);
                list.addAll(ortherDayList);
            }
        }
        return list;
    }

    /**
     * 获取某个时间，5个月之后的时间
     * @param date
     * @return
     */
    public String getDateAfterOtherMonths(Date param, Integer month){
    	if(Check.NuNObjs(param, month)){
    		return null;
    	}
    	Calendar calender = Calendar.getInstance();
    	String finalDate = null;
    	try {
			calender.setTime(param);
			calender.add(Calendar.MONTH, month);
		    finalDate = DateUtil.dateFormat(calender.getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return finalDate;
    } 
    
    /**
     * 
     * 描述:获取下一个月的第一天.
     * 
     * @return
     */
    public String getPerFirstDayOfMonth() {
        SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        return dft.format(calendar.getTime());
    }
    
    
    
    /**
     * 获取当前的订单详情
     * @param orderSn
     * @return
     */
    private String getMsitUrl(String baseUrl,String orderSn){
        StringBuffer sb = new StringBuffer();
        sb.append(msitBaseUrl);
        sb.append(baseUrl);
        sb.append("?orderSn=");
        sb.append(orderSn);
        return sb.toString();
    }

    /**
     * 获取当前的订单详情
     * @param orderSn
     * @return
     */
    private String getDetailUrl(String orderSn){
        return  getMsitUrl(detailUrl,orderSn);
    }


    /**
     * 获取当前的订单详情
     * @param orderSn
     * @return
     */
    private String getEvaluateUrl(String orderSn){
        return  getMsitUrl(evaluateUrl,orderSn);
    }

    /**
     * 获取当前的订单详情
     * @param orderSn
     * @return
     */
    private String getRemarkUrl(String orderSn){
        return  getMsitUrl(remarkUrl,orderSn);
    }


    /**
     * 房东接受订单
     *
     * @param
     * @return
     * @author jixd
     * @created 2017年07月27日 11:56:32
     */
    @RequestMapping(value = "/${LOGIN_AUTH}/acceptOrder", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseSecurityDto> acceptOrder(HttpServletRequest request) {
        try {
            String uid = getUserId(request);
            if (Check.NuNObj(uid)) {
                return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("请登录"), HttpStatus.OK);
            }
            String paramJson = (String) request.getAttribute(ConstDef.DECRYPT_PARAM_ATTRIBUTE);
            LogUtil.info(LOGGER, "【acceptOrder】 param={}", paramJson);
            DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(customerMsgManagerService.getCutomerVo(uid));
            if (dto.getCode() == DataTransferObject.ERROR) {
                return new ResponseEntity<>(ResponseSecurityDto.responseEncrypt(dto), HttpStatus.OK);
            }
            CustomerVo customerVo = dto.parseData("customerVo", new TypeReference<CustomerVo>() {
            });
            LoadlordRequest loadlordRequest = JsonEntityTransform.json2Object(paramJson, LoadlordRequest.class);
            loadlordRequest.setLandlordUid(uid);
            if (Check.NuNStr(loadlordRequest.getOrderSn())) {
                return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("订单号为空"), HttpStatus.OK);
            }
            //接受订单，更新到待入住状态
            loadlordRequest.setOrderStatus(OrderStatusEnum.WAITING_CHECK_IN.getOrderStatus());
            loadlordRequest.setCountryCode(customerVo.getCountryCode());
            String resultJson = orderLoadlordService.acceptOrder(JsonEntityTransform.Object2Json(loadlordRequest));
            LogUtil.info(LOGGER, "【acceptOrder】 result={}", resultJson);
            return new ResponseEntity<>(ResponseSecurityDto.responseEncrypt(JsonEntityTransform.json2DataTransferObject(resultJson)), HttpStatus.OK);
        } catch (Exception e) {
            LogUtil.error(LOGGER, "【acceptOrder】 error={}", e);
            return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("服务错误"), HttpStatus.OK);
        }
    }

    /**
     * 拒绝订单
     *
     * @param
     * @return
     * @author jixd
     * @created 2017年07月27日 14:19:41
     */
    @RequestMapping("${LOGIN_AUTH}/refusedOrder")
    @ResponseBody
    public ResponseEntity<ResponseSecurityDto> refusedOrder(HttpServletRequest request) {
        try {
            String uid = getUserId(request);
            if (Check.NuNObj(uid)) {
                return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("请登录"), HttpStatus.OK);
            }
            String paramJson = (String) request.getAttribute(ConstDef.DECRYPT_PARAM_ATTRIBUTE);
            LogUtil.info(LOGGER, "【refusedOrder】 param={}", paramJson);

            LoadlordRequest loadlordRequest = JsonEntityTransform.json2Object(paramJson, LoadlordRequest.class);
            loadlordRequest.setLandlordUid(uid);

            if (Check.NuNStr(loadlordRequest.getOrderSn())) {
                return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("订单号为空"), HttpStatus.OK);
            }
            if (Check.NuNStr(String.valueOf(loadlordRequest.getRefuseCode()))) {
                return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("未选择拒绝原因"), HttpStatus.OK);
            }
            if ("50".equals(loadlordRequest.getRefuseCode()) && Check.NuNStr(loadlordRequest.getRefuseReason())) {
                return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("请填写具体的拒绝原因"), HttpStatus.OK);
            }

            //设置拒绝原因
            if (!"50".equals(loadlordRequest.getRefuseCode())) {
                String houseTypeJson = cityTemplateService.getSelectEnum(null, TradeRulesEnum.TradeRulesEnum0018.getValue());
                List<EnumVo> nodeList = SOAResParseUtil.getListValueFromDataByKey(houseTypeJson, "selectEnum", EnumVo.class);
                if (!Check.NuNCollection(nodeList)) {
                    for (EnumVo eVo : nodeList) {
                        if (loadlordRequest.getRefuseCode().equals(eVo.getKey())) {
                            loadlordRequest.setRefuseReason(eVo.getText());
                            break;
                        }
                    }
                }
            }

            //拒绝订单,更新订单状态
            loadlordRequest.setOrderStatus(OrderStatusEnum.REFUSED.getOrderStatus());
            String paramStr = JsonEntityTransform.Object2Json(loadlordRequest);
            String resultJson = orderLoadlordService.refusedOrder(paramStr);
            LogUtil.info(LOGGER, "【refusedOrder】result={}：", resultJson);
            DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(resultJson);

            String couponSn = resultDto.parseData("couponSn", new TypeReference<String>() {
            });
            if (!Check.NuNStr(couponSn)) {
                //拒绝成功，释放优惠券 不管成功还是失败，失败会有同步机制
                List<OrderActivityEntity> orderActList = new ArrayList<OrderActivityEntity>();
                OrderActivityEntity orderAct = new OrderActivityEntity();
                orderAct.setAcFid(couponSn);
                orderAct.setAcStatus(CouponStatusEnum.GET.getCode());
                orderActList.add(orderAct);
                //不管是否处理成功
                actCouponService.syncCouponStatus(JsonEntityTransform.Object2Json(orderActList));
            }
            return new ResponseEntity<>(ResponseSecurityDto.responseEncrypt(resultDto), HttpStatus.OK);
        } catch (Exception e) {
            LogUtil.error(LOGGER, "【acceptOrder】 error={}", e);
            return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("服务错误"), HttpStatus.OK);
        }

    }


    /**
     * 确认额外消费
     *
     * @param
     * @return
     * @author jixd
     * @created 2017年07月27日 14:56:38
     */
    @RequestMapping("${LOGIN_AUTH}/confirmOtherMoney")
    @ResponseBody
    public ResponseEntity<ResponseSecurityDto> confirmOtherMoney(HttpServletRequest request) {
        try {
            String uid = getUserId(request);
            if (Check.NuNObj(uid)) {
                return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("请登录"), HttpStatus.OK);
            }
            String paramJson = (String) request.getAttribute(ConstDef.DECRYPT_PARAM_ATTRIBUTE);
            LogUtil.info(LOGGER, "【confirmOtherMoney】 param={}", paramJson);

            LoadlordRequest loadlordRequest = JsonEntityTransform.json2Object(paramJson, LoadlordRequest.class);
            loadlordRequest.setLandlordUid(uid);
            if (Check.NuNStr(loadlordRequest.getOrderSn())) {
                return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("订单号为空"), HttpStatus.OK);
            }
            /*获取额外消费限制*/
            int otherMoney = loadlordRequest.getOtherMoney();
            if (Check.NuNObj(loadlordRequest.getOtherMoney())) {
                otherMoney = 0;
            }
            String otherParam = loadlordRequest.getParamValue();
            //原因限制
            if (otherMoney > 0 && otherParam.length() > 150) {
                return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("最大字数不超过150字"), HttpStatus.OK);
            }

            String loadlordRequestStr = JsonEntityTransform.Object2Json(loadlordRequest);
            String moneyLimitJson = orderLoadlordService.getOtherMoneyLimit(loadlordRequestStr);
            DataTransferObject limitJsonDto = JsonEntityTransform.json2DataTransferObject(moneyLimitJson);
            int limit = 0;
            if (limitJsonDto.getCode() == DataTransferObject.SUCCESS) {
                limit = (int) limitJsonDto.getData().get("otherMoneyLimit");
            }
            if (otherMoney > 0 && otherMoney > limit) {
                return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("最大限额" + getPriceFormat(limit) + "元"), HttpStatus.OK);
            }

            //更改为确认额外消费状态
            loadlordRequest.setOrderStatus(OrderStatusEnum.WAITING_EXT.getOrderStatus());
            //输入金额 元   存入分
            loadlordRequest.setOtherMoney(otherMoney);
            String resultJson = orderLoadlordService.saveOtherMoney(JsonEntityTransform.Object2Json(loadlordRequest));
            LogUtil.info(LOGGER, "【confirmOtherMoney】 结果={}", resultJson);
            return new ResponseEntity<>(ResponseSecurityDto.responseEncrypt(JsonEntityTransform.json2DataTransferObject(resultJson)), HttpStatus.OK);
        } catch (Exception e) {
            LogUtil.error(LOGGER, "【acceptOrder】 error={}", e);
            return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("服务错误"), HttpStatus.OK);
        }

    }

    /**
     * 格式化时间
     *
     * @param
     * @return
     * @author jixd
     * @created 2017年07月27日 15:01:25
     */
    private static String getPriceFormat(int price) {
        DecimalFormat myformat = new DecimalFormat();
        myformat.applyPattern("##,##0.00");

        double priceD = price / 100.00;
        return myformat.format(priceD);
    }

    /**
     * 订单拒绝原因列表
     *
     * @param
     * @return
     * @author jixd
     * @created 2017年07月27日 17:19:12
     */
    @RequestMapping("${LOGIN_AUTH}/refuseReason")
    @ResponseBody
    public ResponseEntity<ResponseSecurityDto> refuseReason(HttpServletRequest request) {
        try {
            String paramJson = (String) request.getAttribute(ConstDef.DECRYPT_PARAM_ATTRIBUTE);
            LogUtil.info(LOGGER, "【refuseReason】拒绝原因 param={}", paramJson);
            JSONObject jsonObject = JSONObject.parseObject(paramJson);
            String houseFid = jsonObject.getString("houseFid");
            String roomFid = jsonObject.getString("roomFid");
            String rentWay = jsonObject.getString("rentWay");
            String reasonJson = cityTemplateService.getSelectEnum(null, TradeRulesEnum.TradeRulesEnum0018.getValue());
            LogUtil.info(LOGGER, "【refuseReason】reaseList={}", reasonJson);
            List<EnumVo> enumVoList = SOAResParseUtil.getListValueFromDataByKey(reasonJson, "selectEnum", EnumVo.class);
            List<NameValuePair<String, String>> selectList = new ArrayList<>();
            for (EnumVo enumVo : enumVoList) {
                NameValuePair<String, String> nameValuePair = new NameValuePair<>();
                nameValuePair.setName(enumVo.getText());
                nameValuePair.setValue(enumVo.getKey());
                selectList.add(nameValuePair);
            }
            //拼接跳转日历参数
            String calendarUrl = MINSU_M_HOUSE_CALENDAR_URL;
            if (!Check.NuNStr(houseFid) && !Check.NuNStr(rentWay)) {
                calendarUrl = MINSU_M_HOUSE_CALENDAR_URL + "?houseBaseFid=" + houseFid + "&rentWay=" + rentWay;
                if (!Check.NuNStr(roomFid)) {
                    calendarUrl += "&houseRoomFid=" + roomFid;
                }
            }

            Map<String, Object> map = new HashMap<>();
            map.put("reasonList", selectList);
            map.put("title", "拒绝原因");
            map.put("calendarUrl", calendarUrl);
            map.put("reasonDesc", OrderConst.FIELD_REFUSE_REASON_DESC);
            map.put("confirmMsg", OrderConst.LANDLORD_REFUSE_CONFIRM_MSG);
            map.put("calendarConfirmMsg", OrderConst.LANDLORD_REFUSE_CALENDAR_CONFIRM_MSG);
            LogUtil.info(LOGGER, "【refuseReason】 result={}", JsonEntityTransform.Object2Json(map));
            return new ResponseEntity<>(ResponseSecurityDto.responseEncryptOK(map), HttpStatus.OK);
        } catch (Exception e) {
            LogUtil.error(LOGGER, "【refuseReason】 error={}", e);
            return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("服务错误"), HttpStatus.OK);
        }
    }

    /**
     * 保存订单备注
     *
     * @param
     * @return
     * @author jixd
     * @created 2017年07月27日 17:55:37
     */
    @RequestMapping("${LOGIN_AUTH}/saveOrderRemark")
    @ResponseBody
    public ResponseEntity<ResponseSecurityDto> saveOrderRemark(HttpServletRequest request, HttpServletResponse response) {
        try {
            String uid = getUserId(request);
            if (Check.NuNObj(uid)) {
                return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("请登录"), HttpStatus.OK);
            }
            String paramJson = (String) request.getAttribute(ConstDef.DECRYPT_PARAM_ATTRIBUTE);
            LogUtil.info(LOGGER, "【saveOrderRemark】 param={}", paramJson);
            if (Check.NuNStr(paramJson)) {
                return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("参数为空"), HttpStatus.OK);
            }
            JSONObject paramObj = JSONObject.parseObject(paramJson);
            String orderSn = paramObj.getString("orderSn");
            String remark = paramObj.getString("remark");
            if (Check.NuNStr(orderSn)) {
                return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("订单号为空"), HttpStatus.OK);
            }
            if (Check.NuNStr(remark)) {
                return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("备注内容为空"), HttpStatus.OK);
            }
            OrderRemarkEntity remarkEntity = new OrderRemarkEntity();
            remarkEntity.setOrderSn(orderSn);
            remarkEntity.setRemarkContent(remark);
            remarkEntity.setUid(uid);
            String resultJson = orderRemarkService.insertRemarkRes(JsonEntityTransform.Object2Json(remarkEntity));
            LogUtil.info(LOGGER, "【saveOrderRemark】 result={}", resultJson);
            return new ResponseEntity<>(ResponseSecurityDto.responseEncrypt(JsonEntityTransform.json2DataTransferObject(resultJson)), HttpStatus.OK);
        } catch (Exception e) {
            LogUtil.error(LOGGER, "【acceptOrder】 error={}", e);
            return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("服务错误"), HttpStatus.OK);
        }

        /*try {

            String uid = getUserId(request);
            GatewayResponse gatewayResponse = new GatewayResponse();
            String forwarded = request.getHeader("X-Forwarded-Ziroom");
            if (X_Forwarded_Ziroom.equals(forwarded)) {
                LogUtil.info(LOGGER, "来自网关请求");
            }
            if (Check.NuNObj(uid)) {
                return GatewayResponse.responseFail("请登录");
            }
            String orderSn = request.getParameter("orderSn");
            String remark = request.getParameter("remark");
            LogUtil.info(LOGGER, "【saveOrderRemark】 orderSn={},remark={}", orderSn, remark);
            orderSn = (String) request.getAttribute("orderSn");
            remark = (String) request.getAttribute("remark");
            LogUtil.info(LOGGER, "【saveOrderRemark】 orderSn={},remark={}", orderSn, remark);
            if (Check.NuNStr(orderSn)) {
                return GatewayResponse.responseFail("订单号为空");
            }
            if (Check.NuNStr(remark)) {
                return GatewayResponse.responseFail("备注为空");
            }
            OrderRemarkEntity remarkEntity = new OrderRemarkEntity();
            remarkEntity.setOrderSn(orderSn);
            remarkEntity.setRemarkContent(remark);
            remarkEntity.setUid(uid);
            String resultJson = orderRemarkService.insertRemarkRes(JsonEntityTransform.Object2Json(remarkEntity));
            LogUtil.info(LOGGER, "【saveOrderRemark】 result={}", resultJson);
            return GatewayResponse.responseOK(resultJson);
        } catch (Exception e) {
            LogUtil.error(LOGGER, "【saveOrderRemark】 error={}", e);
            return GatewayResponse.responseFail("服务错误");
        }*/
    }


    /**
     * 删除备注
     *
     * @param
     * @return
     * @author jixd
     * @created 2017年07月27日 18:03:38
     */
    @RequestMapping("${LOGIN_AUTH}/delRemark")
    @ResponseBody
    public ResponseEntity<ResponseSecurityDto> delRemark(HttpServletRequest request) {
        try {
            String uid = getUserId(request);
            if (Check.NuNObj(uid)) {
                return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("请登录"), HttpStatus.OK);
            }
            String paramJson = (String) request.getAttribute(ConstDef.DECRYPT_PARAM_ATTRIBUTE);

            LogUtil.info(LOGGER, "【delRemark】 param={}", paramJson);

            JSONObject paramObj = JSONObject.parseObject(paramJson);
            String orderSn = paramObj.getString("orderSn");
            String fid = paramObj.getString("fid");
            if (Check.NuNStr(orderSn)) {
                return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("订单号为空"), HttpStatus.OK);
            }
            if (Check.NuNStr(fid)) {
                return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("请选择删除的备注"), HttpStatus.OK);
            }
            RemarkRequest remarkRequest = new RemarkRequest();
            remarkRequest.setOrderSn(orderSn);
            remarkRequest.setFid(fid);
            remarkRequest.setUid(uid);
            String resultJson = orderRemarkService.delRemark(JsonEntityTransform.Object2Json(remarkRequest));
            LogUtil.info(LOGGER, "【delRemark】 result={}", resultJson);
            return new ResponseEntity<>(ResponseSecurityDto.responseEncrypt(JsonEntityTransform.json2DataTransferObject(resultJson)), HttpStatus.OK);
        } catch (Exception e) {
            LogUtil.error(LOGGER, "【delRemark】 error={}", e);
            return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("服务错误"), HttpStatus.OK);
        }

    }




}
