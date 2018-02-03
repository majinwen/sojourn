package com.ziroom.minsu.services.order.proxy.finance;

import com.alibaba.fastjson.JSONObject;
import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.customer.CustomerBlackEntity;
import com.ziroom.minsu.entity.order.FinancePayVouchersEntity;
import com.ziroom.minsu.entity.order.FinancePaymentVouchersEntity;
import com.ziroom.minsu.entity.order.OrderPayEntity;
import com.ziroom.minsu.services.common.utils.CloseableHttpUtil;
import com.ziroom.minsu.services.customer.api.inner.CustomerBlackService;
import com.ziroom.minsu.services.finance.entity.CustomerInfoVo;
import com.ziroom.minsu.services.order.service.FinancePaymentServiceImpl;
import com.ziroom.minsu.services.order.service.FinancePenaltyServiceImpl;
import com.ziroom.minsu.services.order.utils.FinanceUtil;
import com.ziroom.minsu.valenum.finance.SyncStatusEnum;
import com.ziroom.minsu.valenum.order.OrderPaymentTypeEnum;
import com.ziroom.minsu.valenum.order.PaySourceTypeEnum;
import com.ziroom.minsu.valenum.order.PaymentStatusEnum;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * <p></p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author lishaochuan on 2016/11/18 10:39
 * @version 1.0
 * @since 1.0
 */
public abstract class FinancePvExecute extends FinanceExecute {

    private static final Logger LOGGER = LoggerFactory.getLogger(FinancePvExecute.class);

    @Value("#{'${CUSTOMER_BLACK_URL}'.trim()}")
    private String CUSTOMER_BLACK_URL;

    @Resource(name = "customer.customerBlackService")
    private CustomerBlackService customerBlackService;

    @Resource(name = "order.financePaymentServiceImpl")
    private FinancePaymentServiceImpl financePaymentServiceImpl;

    @Resource(name = "order.financePenaltyServiceImpl")
    private FinancePenaltyServiceImpl financePenaltyServiceImpl;


    /**
     * 执行付款单
     * @author lishaochuan
     * @create 2016年9月21日上午11:06:19
     * @param payVouchers
     * @throws Exception
     */
    public void run(FinancePayVouchersEntity payVouchers)throws Exception{
        if(this.checkMinsuBlack(payVouchers.getReceiveUid())){
            // 民宿黑名单
            LogUtil.info(LOGGER, "民宿黑名单付款单,修改为3小时后执行,pvSn:{}", payVouchers.getPvSn());
            payVouchersRunService.updateDelayRunTime(payVouchers);

        }else if (this.checkZiroomBlack(payVouchers.getReceiveUid()) && PaymentStatusEnum.FAILED.getCode() != payVouchers.getPaymentStatus()){
            LogUtil.error(LOGGER, "正在处理黑名单的付款单，付款单信息：{}", JsonEntityTransform.Object2Json(payVouchers));
            //如果是黑名单 并且不是 失败的单子 直接变成打到收入 黑名单的逻辑不兼容已经失败的单子，已经失败的单子继续进行否则风险比较大
            //1. 将当前的付款单的状态为 黑名单不打款
            //2. 将当前的数据保存到收入
            this.updateSend(payVouchers.getPvSn());
            payVouchersRunService.blackPayVoucherAndTrans2Income(payVouchers);

        }else if (this.checkCanPenalty(payVouchers)){
            LogUtil.info(LOGGER, "进入处理罚款单,pvSn={},uid={}", payVouchers.getPvSn(),payVouchers.getReceiveUid());
            //消费冻结金之前处理   罚款单冲抵付款单 转收入
            financePenaltyServiceImpl.penaltyOffsetPayAndTran2Income(payVouchers);
        }else {
            this.updateSend(payVouchers.getPvSn());
            this.ifFailedResetStatus(payVouchers);
            this.runDoSth(payVouchers);
        }
    }


    /**
     * 更新付款单的发送状态+1
     * @author lishaochuan
     * @create 2016年9月22日上午11:42:01
     * @param pvSn
     */
    private void updateSend(String pvSn){
        payVouchersRunService.updateIsSend(pvSn);
    }

    /**
     * 如果是失败状态，置为失败前的状态，重新执行
     * @author lishaochuan
     * @create 2016年9月21日上午10:09:51
     * @param payVouchers
     */
    private void ifFailedResetStatus(FinancePayVouchersEntity payVouchers){
        if(PaymentStatusEnum.FAILED.getCode() == payVouchers.getPaymentStatus()){
            LogUtil.info(LOGGER, "当前付款单是失败状态，置为失败前的状态执行,paymentStatus:{},previousPaymentStatus:{}",payVouchers.getPaymentStatus(),payVouchers.getPreviousPaymentStatus());
            payVouchers.setPaymentStatus(payVouchers.getPreviousPaymentStatus());
        }
    }

    /**
     * 在子类中处理执行付款单的逻辑
     * @author lishaochuan
     * @create 2016年9月21日上午11:06:29
     * @param payVouchers
     * @throws Exception
     */
    protected abstract void runDoSth(FinancePayVouchersEntity payVouchers)throws Exception;





    /**
     * 校验是否可以原路返回
     * @author lishaochuan
     * @create 2016年8月24日下午2:49:37
     * @param payVouchers
     * @param orderPay
     * @return
     */
    protected boolean canYlfh(FinancePayVouchersEntity payVouchers, OrderPayEntity orderPay){
        // 第一次校验通过，直接update成ylfh，第二次直接放过
        if(OrderPaymentTypeEnum.YLFH.getCode().equals(payVouchers.getPaymentType())){
            LogUtil.info(LOGGER, "付款类型是ylfh，直接通过校验,paymentType：{}", payVouchers.getPaymentType());
            return true;
        }
        if(!Check.NuNStr(payVouchers.getPaymentType())){
            LogUtil.info(LOGGER, "付款类型不是空，且不是ylfh，走其他校验,paymentType：{}", payVouchers.getPaymentType());
            return false;
        }

        boolean checkFlag = FinanceUtil.checkCanYlfh(payVouchers, orderPay);
        if (checkFlag) {
            payVouchers.setPaymentType(OrderPaymentTypeEnum.YLFH.getCode());
            payVouchersRunService.updateYlfh(payVouchers);
            return true;
        } else {
            return false;
        }
    }

    /**
     * 校验是否默认银行付款
     * @author lishaochuan
     * @create 2016年9月23日下午4:19:04
     * @param payVouchers
     * @param customerInfoVo
     * @return
     */
    protected boolean canYhfkDefult(FinancePayVouchersEntity payVouchers, CustomerInfoVo customerInfoVo){
        // 第一次校验通过，直接update成ylfh，第二次直接放过
        if(OrderPaymentTypeEnum.YHFK.getCode().equals(payVouchers.getPaymentType())){
            LogUtil.info(LOGGER, "付款类型是yhfk，直接通过校验,paymentType：{}", payVouchers.getPaymentType());
            return true;
        }
        if(!Check.NuNStr(payVouchers.getPaymentType())){
            LogUtil.info(LOGGER, "付款类型不是空，且不是yhfk，走其他校验,paymentType：{}", payVouchers.getPaymentType());
            return false;
        }
        // 落地bankcardFid
        if (customerInfoVo.isBankDefault()) {
            //校验是否是 民宿黑名单，如果是民宿黑名单，直接打到余额
            /*if (this.checkMinsuBlack(payVouchers.getReceiveUid())){
                //如果是民宿黑名单，return false;
                return false;
            }*/
            LogUtil.info(LOGGER, "付款类型是yhfk，通过校验,paymentType：{}", payVouchers.getPaymentType());
            payVouchers.setPaymentType(OrderPaymentTypeEnum.YHFK.getCode());
            payVouchers.setBankcardFid(customerInfoVo.getBankcard().getFid());
            payVouchersRunService.updateYhfk(payVouchers);
            return true;
        }
        return false;
    }




    /**
     * 检验是否存在银行卡
     * @author lishaochuan
     * @create 2016年9月23日下午5:39:10
     * @param payVouchers
     * @param customerInfoVo
     * @return
     */
    protected boolean canYhfkExists(FinancePayVouchersEntity payVouchers, CustomerInfoVo customerInfoVo){
        // 第一次校验通过，直接update成ylfh，第二次直接放过
        if(OrderPaymentTypeEnum.YHFK.getCode().equals(payVouchers.getPaymentType())){
            LogUtil.info(LOGGER, "付款类型是yhfk，直接通过校验,paymentType：{}", payVouchers.getPaymentType());
            return true;
        }
        if(!Check.NuNStr(payVouchers.getPaymentType())){
            LogUtil.info(LOGGER, "付款类型不是空，且不是yhfk，走其他校验,paymentType：{}", payVouchers.getPaymentType());
            return false;
        }

        // 落地bankcardFid
        if (customerInfoVo.checkBankCard()) {
            LogUtil.info(LOGGER, "付款类型是yhfk，通过校验,paymentType：{}", payVouchers.getPaymentType());
            payVouchers.setPaymentType(OrderPaymentTypeEnum.YHFK.getCode());
            payVouchers.setBankcardFid(customerInfoVo.getBankcard().getFid());
            payVouchersRunService.updateYhfk(payVouchers);
            return true;
        }
        return false;
    }


    /**
     * 校验是否可以付款到余额
     * @author lishaochuan
     * @create 2016年9月23日下午5:24:09
     * @param payVouchers
     * @return
     */
    protected boolean canAccount(FinancePayVouchersEntity payVouchers) {
        if (OrderPaymentTypeEnum.ACCOUNT.getCode().equals(payVouchers.getPaymentType())) {
            LogUtil.info(LOGGER, "付款类型是account，直接通过校验,paymentType：{}", payVouchers.getPaymentType());
            return true;
        }
        if (!Check.NuNStr(payVouchers.getPaymentType())) {
            LogUtil.error(LOGGER, "付款类型不是空，且不是account，不应该走到这里啊,paymentType：{}", payVouchers.getPaymentType());
            return false;
        }
        payVouchers.setPaymentType(OrderPaymentTypeEnum.ACCOUNT.getCode());
        payVouchersRunService.updateAccount(payVouchers);
        return true;
    }


    /**
     * 校验收款单是否已经同步到财务
     * @author lishaochuan
     * @create 2016年8月24日下午9:55:06
     * @param orderSn
     * @return
     */
    protected boolean isSyncPayment(String orderSn, String pvSn){
        FinancePaymentVouchersEntity payment = financePaymentServiceImpl.getOrderPayment(orderSn);
        if(payment.getSyncStatus() != SyncStatusEnum.success.getCode()){
            LogUtil.info(LOGGER, "原路返回校验不通过：收款单尚未同步到财务，此次不执行，等待下次,payment:{}",payment);
            payVouchersRunService.updateNoSend(pvSn);
            return false;
        }
        LogUtil.info(LOGGER, "原路返回校验付款单通过：已经同步到财务,payment:{}",payment);
        return true;
    }


    /**
     * 校验是否是民宿的黑名单
     * @author afi
     * @param uid
     * @return
     */
    private boolean checkMinsuBlack(String uid){
        boolean isBlack = false;
        String result = customerBlackService.findCustomerBlackByUid(uid);
        DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(result);
        if (dto.getCode() == DataTransferObject.SUCCESS){
            CustomerBlackEntity customerBlackEntity = dto.parseData("obj", new TypeReference<CustomerBlackEntity>() {});
            if (!Check.NuNObj(customerBlackEntity)){
                isBlack = true;
            }
        }
        return isBlack;
    }


    /**
     * 用户是否在自如网的黑名单列表中
     * @author afi
     * @param uid
     * @return
     */
    private boolean checkZiroomBlack(String uid){
        boolean is = false;
        try{
            Map<String,String> paramMap = new HashMap<>();
            paramMap.put("uid",uid);
            String resultJson = CloseableHttpUtil.sendFormPost(CUSTOMER_BLACK_URL, paramMap);
            JSONObject jsonObject = JSONObject.parseObject(resultJson);
            Integer errorCode = jsonObject.getInteger("error_code");
            if (errorCode == 0){
                Integer flag = jsonObject.getInteger("data");
                if (flag == 1){
                    is = true;
                }
            }
        }catch (Exception e){
            LogUtil.error(LOGGER,"黑名单接口调用异常e={}",e);
            throw new BusinessException("黑名单接口调用异常",e);
        }
        return is;
    }

    /**
     * 校验是否能进入罚款流程
     * 1.付款单来源是 定时任务，清洁费，用户结算
     * 2.付款单状态 未付款
     * @author jixd
     * @created 2017年05月10日 16:12:58
     * @param
     * @return
     */
    private boolean checkCanPenalty(FinancePayVouchersEntity payVouchers){
        int sourceType = payVouchers.getPaySourceType();
        boolean s = PaymentStatusEnum.UN_PAY.getCode() == payVouchers.getPaymentStatus()
                    && (sourceType == PaySourceTypeEnum.TASK.getCode()
                        || sourceType == PaySourceTypeEnum.CLEAN.getCode()
                        || sourceType == PaySourceTypeEnum.USER_SETTLEMENT.getCode());
        if (!s)return s;
        long count = financePenaltyServiceImpl.countAvaliableFinancePenaltyByUid(payVouchers.getReceiveUid());
        return count > 0L;
    }


}
