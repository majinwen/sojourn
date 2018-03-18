package com.ziroom.zrp.service.trading.api;

/**
 * <p>交易相关定时任务服务</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd
 * @version 1.0
 * @Date Created in 2017年09月26日 15:47
 * @since 1.0
 */
public interface TradingTaskService {

    /**
     * 提示用户进行物业交割
     * @author jixd
     * @created 2017年09月26日 15:50:52
     * @param
     * @return
     */
    void  notifyUserDelivery();

    /**
      * @description: 首期账单支付超时关闭合同
      * @author: lusp
      * @date: 2017/10/11 下午 16:48
      * @params:
      * @return:
      */
    void firstBillPayOvertimeCloseContract();

    /** 首次支付超时前 n 小时提醒定时任务
      * @description: 
      * @author: lusp
      * @date: 2017/12/8 下午 15:10
      * @params: 
      * @return: 
      */
    void firstBillPayBeforeOvertimeRemind();

    /**
      * @description: 当天未签约关闭合同
      * @author: lusp
      * @date: 2017/10/20 下午 19:51
      * @params:
      * @return:
      */
    void sameDayUnsignedCloseContract();
    
    /**
      * @description: 签约有效期超时前n小时发送提醒
      * @author: lusp
      * @date: 2017/12/12 上午 10:22
      * @params: 
      * @return: 
      */
    void sameDayBeforeOvertimeUnsignedRemind();

    /**
     * @description: 关闭合同到期第二天未续约的合同
     * @author: lusp
     * @date: 2017/10/20 下午 20:15
     * @params:
     * @return:
     */
    void sameDayUnrenewedCloseContract();

    /**
     * 同步过期合同
     * @author jixd
     * @created 2017年12月07日 11:08:37
     * @param
     * @return
     */
    void syncExpireContractToFin();

    /**
     * 作废电费的应收账单
     * @author xiangb
     * @created 2018年2月6日16:39:31
     * @param
     * @return
     */
    void invalidMeterFinReceiBill();

}
