package com.ziroom.minsu.services.order.proxy;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.DateUtil;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.customer.CustomerBankCardMsgEntity;
import com.ziroom.minsu.entity.order.AccountFillLogEntity;
import com.ziroom.minsu.services.account.dto.FillMoneyRequest;
import com.ziroom.minsu.services.common.constant.SysConst;
import com.ziroom.minsu.services.customer.api.inner.CustomerInfoService;
import com.ziroom.minsu.services.finance.entity.OrderActivityInfoVo;
import com.ziroom.minsu.services.order.dto.PayCallBackRequest;
import com.ziroom.minsu.services.order.dto.SendOrderEmailRequest;
import com.ziroom.minsu.services.order.entity.OrderInfoVo;
import com.ziroom.minsu.services.order.service.AccountFillLogServiceImpl;
import com.ziroom.minsu.services.order.service.OrderActivityServiceImpl;
import com.ziroom.minsu.services.order.service.OrderMoneyServiceImpl;
import com.ziroom.minsu.services.order.service.OrderPayServiceImpl;
import com.ziroom.minsu.services.order.utils.OrderSnUtil;
import com.ziroom.minsu.services.order.utils.PayUtil;
import com.ziroom.minsu.valenum.account.ConsumeTypeEnum;
import com.ziroom.minsu.valenum.account.CustomerTypeEnum;
import com.ziroom.minsu.valenum.account.FillBussinessTypeEnum;
import com.ziroom.minsu.valenum.account.FillTypeEnum;
import com.ziroom.minsu.valenum.common.ErrorCodeEnum;
import com.ziroom.minsu.valenum.common.RstTypeEnum;
import com.ziroom.minsu.valenum.finance.PaymentSourceTypeEnum;
import com.ziroom.minsu.valenum.house.RentWayEnum;
import com.ziroom.minsu.valenum.order.OrderAcTypeEnum;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * <p>处理支付的逻辑</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/6/19.
 * @version 1.0
 * @since 1.0
 */
@Service("order.dealPayServiceProxy")
public class DealPayServiceProxy {


    /**
     * 日志对象
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(DealPayServiceProxy.class);

    @Resource(name = "order.messageSource")
    private MessageSource messageSource;

    @Resource(name = "order.orderPayServiceImpl")
    private OrderPayServiceImpl orderPayServiceImpl;

    @Resource(name ="order.orderActivityServiceImpl")
    private OrderActivityServiceImpl orderActivityServiceImpl;

    @Resource(name ="order.orderMsgProxy")
    private OrderMsgProxy orderMsgProxy;

    @Resource(name = "customer.customerInfoService")
    private CustomerInfoService customerInfoService;

    @Resource(name = "order.callAccountServiceProxy")
    private CallAccountServiceProxy callAccountServiceProxy;

    @Resource(name ="order.accountFillLogServiceImpl")
    private AccountFillLogServiceImpl accountFillLogServiceImpl;
    
    @Resource(name ="order.orderMoneyServiceImpl")
    private OrderMoneyServiceImpl orderMoneyServiceImpl;


    /**
     * 账单支付回调具体处理业务
     * @author liyingjie
     * @created 2016年4月8日
     * @param  uid
     * @return
     */
    private boolean checkCard(String uid){
        boolean res = true;
        String resultJson = customerInfoService.getCustomerBankcard(uid);
        if(Check.NuNStr(resultJson)){
            res = false;
            return res;
        }
        DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(resultJson);
        CustomerBankCardMsgEntity cbcm = resultDto.parseData("bankcard", new TypeReference<CustomerBankCardMsgEntity>() {
        });
        if (Check.NuNObj(cbcm) || Check.NuNStr(cbcm.getBankcardHolder())
                || Check.NuNStr(cbcm.getBankcardNo())
                || Check.NuNStr(cbcm.getBankName())) {
            LogUtil.info(LOGGER, "没有银行卡信息. uid:{}", uid);
            res = false;
        }
        return res;
    }


    /**
     * 处理已经取消的订单的回调
     * @param dto
     * @param payBackRequest
     * @param orderInfo
     */
    public void dealCancelPayCallBack(DataTransferObject dto,PayCallBackRequest payBackRequest,OrderInfoVo orderInfo){
        if (dto.getCode() != DataTransferObject.SUCCESS){
            return;
        }
        //校验当前用户是否绑定银行卡
        boolean checkCard = checkCard(orderInfo.getUserUid());
        //支付超时回调接口
        boolean result = orderPayServiceImpl.payUnNormalCallBack(payBackRequest, checkCard);
        if (!result){
            //处理支付回调失败
            PayUtil.transFailDto(dto);
            return;
        }
        String msg;
        if (checkCard){
            //充值冻结金额到客户账户
            this.fillCashFreeze(orderInfo,payBackRequest,CustomerTypeEnum.roomer.getCode(),ConsumeTypeEnum.filling_freeze.getCode());
            msg = SysConst.pay_fail_card;
        }else {
            //充值余额 到客户账户
            this.fillCashFreeze(orderInfo,payBackRequest,CustomerTypeEnum.roomer.getCode(),ConsumeTypeEnum.filling_balance.getCode());
            msg = SysConst.pay_fail_account;
        }

        PayUtil.transSuccessDto(dto);
        if(orderInfo.getCouponMoney() > 0){
            //恢复活动表状态为已领取
            orderActivityServiceImpl.updateGetStatusByCoupon(orderInfo.getOrderSn());
        }
        //处理成功发送短信
        orderMsgProxy.sendMsg4OrderCanceled(orderInfo,msg);
        //支付回调超时订单已经取消给开发人发短信
        orderMsgProxy.sendMsg4PayBackButCanceled4Dev(orderInfo.getOrderSn(),payBackRequest.getCurrent_money(),orderInfo.getCouponMoney());

    }


    /**
     * 处理正常支付回调公共逻辑
     * @author afi
     * @param dto
     * @param payBackRequest
     * @param orderActList
     * @param orderInfo
     */
    public void dealNormalPayCallBack(DataTransferObject dto, PayCallBackRequest payBackRequest, List<OrderActivityInfoVo> orderActList, OrderInfoVo orderInfo){
        if (dto.getCode() != DataTransferObject.SUCCESS){
            return;
        }
        LogUtil.info(LOGGER, "【支付回调】处理正常的回调 orderSn:{}", payBackRequest.getBizCode());

        orderMoneyServiceImpl.setPaymentRunTime(orderInfo,orderActList);
        
        RstTypeEnum res = orderPayServiceImpl.payNormalCallBack(payBackRequest, orderActList);
        if(res.getCode() == RstTypeEnum.SUCCESS.getCode()){
            //短信发送
            orderMsgProxy.sendMsg4PaySuccess(orderInfo);
            //发邮件参数拼装开始
            SendOrderEmailRequest orderEmailRequest=new SendOrderEmailRequest();
            orderEmailRequest.setOrderStartDate(orderInfo.getStartTime());
            orderEmailRequest.setOrderEndDate(orderInfo.getEndTime());
            orderEmailRequest.setOrderStatus("订单已支付成功");
            orderEmailRequest.setLandlordUid(orderInfo.getLandlordUid());
            orderEmailRequest.setBookName(orderInfo.getUserName());
            orderEmailRequest.setCheckInNum(orderInfo.getPeopleNum());
            if(orderInfo.getRentWay()==RentWayEnum.HOUSE.getCode()){
            	orderEmailRequest.setHouseName(orderInfo.getHouseName());
            } else if(orderInfo.getRentWay()==RentWayEnum.ROOM.getCode()) {
            	orderEmailRequest.setHouseName(orderInfo.getRoomName());
			}
            orderEmailRequest.setEmailTitle(orderEmailRequest.getHouseName()+"的"+DateUtil.dateFormat(orderInfo.getStartTime(),"yyyy-MM-dd")+"至"+DateUtil.dateFormat(orderInfo.getEndTime(),"yyyy-MM-dd")+"的订单已支付成功");
            dto.putValue("orderEmailRequest", orderEmailRequest);
            //发邮件参数拼装结束
            //给房东充值冻结金
            this.fillCashFreeze(orderInfo, payBackRequest, CustomerTypeEnum.landlord.getCode(), ConsumeTypeEnum.filling_freeze.getCode());
            if(!Check.NuNObj(orderActList)){
                dealFillCoupon(orderActList,orderInfo,true);
            }
            PayUtil.transSuccessDto(dto);
        }else if (res.getCode() == RstTypeEnum.REPEAT.getCode()){
            //已经回调完成 幂等返回
            LogUtil.info(LOGGER, "【支付回调】正已经回调完成 幂等返回 orderSn:{}", payBackRequest.getBizCode());
            PayUtil.transSuccessDto(dto);
        }else {
            //处理失败
            PayUtil.transFailDto(dto);
        }
    }

    /**
     * 0元订单的手动触发回调
     * @author afi
     * @param orderActList
     * @param orderInfo
     */
    public void dealZeroPayCallBack(List<OrderActivityInfoVo> orderActList,OrderInfoVo orderInfo){
        if (Check.NuNCollection(orderActList)){
            LogUtil.error(LOGGER, "处理0元订单 优惠券信息为空,orderInfo:{}", JsonEntityTransform.Object2Json(orderInfo));
            return;
        }
        PayCallBackRequest payBackRequest = this.getPayCallBackRequestByOrder(orderInfo);

        orderMoneyServiceImpl.setPaymentRunTime(orderInfo,orderActList);

        RstTypeEnum res = orderPayServiceImpl.payCallBack4Coupon(payBackRequest, orderActList);
        if(res.getCode() == RstTypeEnum.SUCCESS.getCode()){
            dealFillCoupon(orderActList, orderInfo, false);
            LogUtil.info(LOGGER, "处理0元订单 处理成功,orderInfo:{} couponAc:{}", JsonEntityTransform.Object2Json(orderInfo), JsonEntityTransform.Object2Json(orderActList));
        }else {
            LogUtil.error(LOGGER, "处理0元订单 处理失败,orderInfo:{} couponAc:{}", JsonEntityTransform.Object2Json(orderInfo),JsonEntityTransform.Object2Json(orderActList));
        }
    }

    /**
     * 通用订单获取支付回调信息
     * @param orderInfo
     * @return
     */
    private PayCallBackRequest getPayCallBackRequestByOrder(OrderInfoVo orderInfo){
        PayCallBackRequest Request = new PayCallBackRequest();
        Request.setOrderSn(orderInfo.getOrderSn());
        Request.setCityCode(orderInfo.getCityCode());
        Request.setBizCode(orderInfo.getOrderSn());
        Request.setPayUid(orderInfo.getUserUid());
        Request.setPaymentSourceType(PaymentSourceTypeEnum.customer.getCode());
        return Request;
    }




    /***
     * 充值优惠券信息
     * @param orderActList
     * @param orderInfo
     * @param dealConponStatus
     */
    private void dealFillCoupon(List<OrderActivityInfoVo> orderActList, OrderInfoVo orderInfo, boolean dealConponStatus){
        //充值优惠券
        this.fillCuponFreeze(orderInfo, orderActList,CustomerTypeEnum.landlord.getCode(),ConsumeTypeEnum.filling_freeze.getCode());
        if (dealConponStatus){
            //将活动券变为 由已冻结为已使用
            orderActivityServiceImpl.updateUsedStatusByCoupon(orderInfo.getOrderSn());
        }
    }

    /**
     * 充值优惠券
     * @param orderInfo
     * @param orderActList
     * @param cusType
     * @param fillMoneyType
     * @return
     */
    private void fillCuponFreeze(OrderInfoVo orderInfo,List<OrderActivityInfoVo> orderActList,String cusType,int fillMoneyType){
        if (Check.NuNCollection(orderActList)){
            LogUtil.info(LOGGER,"【fillCuponFreeze】活动集合为空");
            return;
        }
        for (OrderActivityInfoVo orderActivityInfoVo : orderActList){
            FillMoneyRequest ffr = new FillMoneyRequest();
            ffr.setTotalFee(orderActivityInfoVo.getAcMoney());
            ffr.setDzCityCode(orderInfo.getCityCode());
            ffr.setUidType(cusType);
            ffr.setFillType(FillTypeEnum.coupon.getCode());
            ffr.setPayTime(new Date());
            ffr.setTradeNo(orderInfo.getOrderSn()+"_"+orderActivityInfoVo.getAcFid()); //防止订单取消，优惠券恢复，重新充值会幂等
            ffr.setOrderSn(orderInfo.getOrderSn());
            //优惠券充值虚拟金额
            ffr.setPayType(SysConst.cash_fill);
            // 判断 给房东充值
            if(cusType.equals(CustomerTypeEnum.landlord.getCode())){
                ffr.setUid(orderInfo.getLandlordUid());
                ffr.setBiz_common_type(SysConst.account_fill_money+"."+ FillBussinessTypeEnum.receive_fill.getCode());
            }
            LogUtil.info(LOGGER, "fillCuponFreeze 参数为 params :{}", JsonEntityTransform.Object2Json(ffr));
            Map<String,String> resMap = new HashMap<String,String>();
            String msg = "";
            try {
                ffr.setFillMoneyType(fillMoneyType);
                resMap =  callAccountServiceProxy.fillFreezeAmount(ffr);
                LogUtil.info(LOGGER, "fillCuponFreeze  resultJson:{}", JsonEntityTransform.Object2Json(resMap));
                ffr.setFillStatus(ErrorCodeEnum.getCodeEn(resMap.get("status")).getSysCode());
                if(!Check.NuNMap(resMap)){
                    msg = resMap.get("status")+SysConst.sys_line+resMap.get("api")+SysConst.sys_line+resMap.get("result")+SysConst.sys_line+resMap.get("error_string");
                }
                //将充值记录 写入日志
                ffr.setBusiness_type(OrderAcTypeEnum.getByCode(orderActivityInfoVo.getAcType()).getFillType());
                this.insertAccountFillLog(ffr,msg);
            } catch (Exception e) {
                LogUtil.error(LOGGER, "fillCuponFreeze error:{}", e);
                ffr.setFillStatus(ErrorCodeEnum.fail.getSysCode());
                //将充值记录 写入日志
                ffr.setBusiness_type(OrderAcTypeEnum.getByCode(orderActivityInfoVo.getAcType()).getFillType());
                this.insertAccountFillLog(ffr,msg);
            }
        }

    }


    /**
     * 充值冻结金额
     * @param oe
     * @param payBackRequest
     * @param cusType
     * @param fillMoneyType
     * @return
     */
    private Map<String,String> fillCashFreeze(OrderInfoVo oe,PayCallBackRequest payBackRequest,String cusType,int fillMoneyType){
        FillMoneyRequest ffr = new FillMoneyRequest();
        ffr.setTotalFee(payBackRequest.getTotal_price());
        ffr.setDzCityCode(oe.getCityCode());
        ffr.setUidType(cusType);
        ffr.setFillType(FillTypeEnum.cash.getCode());
        ffr.setPayTime(new Date());
        ffr.setTradeNo(payBackRequest.getOut_trade_no());
        ffr.setOrderSn(oe.getOrderSn());
        ffr.setPayType(payBackRequest.getPay_type());

        // 判断 给房东充值还是给房客充值
        if(cusType.equals(CustomerTypeEnum.landlord.getCode())){
            ffr.setUid(oe.getLandlordUid());
            ffr.setBusiness_type(FillBussinessTypeEnum.receive_fill.getCode());
        }else if(cusType.equals(CustomerTypeEnum.roomer.getCode())){
            ffr.setUid(oe.getUserUid());
            ffr.setBusiness_type(FillBussinessTypeEnum.checkout_fill.getCode());
        }
        LogUtil.info(LOGGER, "fillCashFreeze 参数为 params :{}", JsonEntityTransform.Object2Json(ffr));
        Map<String,String> resMap = new HashMap<String,String>();
        String mesg = "";
        try {
            //判断是充值余额 还是 充值冻结
            if(fillMoneyType == ConsumeTypeEnum.filling_balance.getCode()){
                ffr.setFillMoneyType(fillMoneyType);
                resMap =  callAccountServiceProxy.fillBalanceAmount(ffr);
            }else if(fillMoneyType == ConsumeTypeEnum.filling_freeze.getCode()){
                ffr.setFillMoneyType(fillMoneyType);
                //20160614 充值冻结金时，区分是虚拟充值还是现金充值
                ffr.setBiz_common_type(SysConst.account_fill_money);
                resMap =  callAccountServiceProxy.fillFreezeAmount(ffr);
            }
            LogUtil.info(LOGGER, "fillCashFreeze  resultJson:{}", JsonEntityTransform.Object2Json(resMap));

            ffr.setFillStatus(ErrorCodeEnum.getCodeEn(resMap.get("status")).getSysCode());

            if(!Check.NuNMap(resMap)){
                mesg = resMap.get("status")+SysConst.sys_line+resMap.get("api")+SysConst.sys_line+resMap.get("result")+SysConst.sys_line+resMap.get("error_string");
            }

            //将充值记录 写入日志
            this.insertAccountFillLog(ffr,mesg);

        } catch (Exception e) {
            LogUtil.error(LOGGER, "fillCashFreeze error:{}", e);
            ffr.setFillStatus(ErrorCodeEnum.fail.getSysCode());
            //将充值记录 写入日志
            this.insertAccountFillLog(ffr,mesg);
        }

        return resMap;
    }


    /**
     * 充值记录
     *
     * @author liyingjie
     * @created 2016年5月3日
     * @param ffr
     * @return
     */
    private void insertAccountFillLog(FillMoneyRequest ffr,String mesg){
        AccountFillLogEntity accountEntity = new AccountFillLogEntity();
        accountEntity.setFillSn(OrderSnUtil.getFillSn());
        accountEntity.setCityCode(ffr.getDzCityCode());
        accountEntity.setOrderSn(ffr.getOrderSn());
        accountEntity.setBussinessType(ffr.getBusiness_type());
        accountEntity.setPayTime(ffr.getPayTime());
        accountEntity.setFillType(ffr.getFillType());
        accountEntity.setFillMoneyType(ffr.getFillMoneyType());
        accountEntity.setTradeNo(ffr.getTradeNo());
        accountEntity.setTargetUid(ffr.getUid());
        accountEntity.setTargetType(CustomerTypeEnum.getByCode(ffr.getUidType()).getStatusCode());
        accountEntity.setTotalFee(ffr.getTotalFee());
        accountEntity.setFillStatus(ffr.getFillStatus());
        accountEntity.setResultMsg(mesg);
        accountEntity.setPayType(ffr.getPayType());
        accountFillLogServiceImpl.insertAccountFillLogRes(accountEntity);
    }

}
