package com.ziroom.minsu.services.order.utils;

import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.order.FinancePayVouchersEntity;
import com.ziroom.minsu.entity.order.OrderPayEntity;
import com.ziroom.minsu.services.common.utils.DateSplitUtil;
import com.ziroom.minsu.services.order.service.FinancePayServiceImpl;
import com.ziroom.minsu.valenum.order.OrderPayTypeEnum;
import com.ziroom.minsu.valenum.order.ReceiveTypeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * <p></p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author lishaochuan on 2016/10/31 14:17
 * @version 1.0
 * @since 1.0
 */
public class FinanceUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(FinanceUtil.class);


    /**
     * 校验是否可原路返回
     * @author lishaochuan
     * @param payVouchers
     * @param orderPay
     * @return
     */
    public static boolean checkCanYlfh(FinancePayVouchersEntity payVouchers, OrderPayEntity orderPay){
        // 收款人必须是房客
        if(payVouchers.getReceiveType() != ReceiveTypeEnum.TENANT.getCode()){
            LogUtil.info(LOGGER, "原路返回校验不通过：只有房客才可以原路返回,receiveType：{}", payVouchers.getReceiveType());
            return false;
        }
        // 必须是微信支付
        if(OrderPayTypeEnum.wx_ios_pay.getPayType() != orderPay.getPayType() && OrderPayTypeEnum.wx_ad_pay.getPayType() != orderPay.getPayType()){
            LogUtil.info(LOGGER, "原路返回校验不通过：支付方式非微信，payType：{}", orderPay.getPayType());
            return false;
        }
        // 退款金额小于1块钱
        if(payVouchers.getTotalFee() < 100){
            LogUtil.info(LOGGER, "原路返回校验不通过：退款金额小于1块钱，totalFee：{}", payVouchers.getTotalFee());
            return false;
        }
        // 退款金额必须小于付款金额
        if(payVouchers.getTotalFee() > orderPay.getPayMoney()){
            LogUtil.info(LOGGER, "原路返回校验不通过：退款金额大于付款金额，totalFee：{}，payMoney：{}", payVouchers.getTotalFee(), orderPay.getPayMoney());
            return false;
        }
        // 退款天数不可超过90天
        if (DateSplitUtil.countDateSplit(orderPay.getCreateTime(), new Date()) > 89){
            LogUtil.info(LOGGER, "原路返回校验不通过：退款天数不可超过90天，createTime：{}，", orderPay.getCreateTime());
            return false;
        }

        return true;
    }


}
