package com.ziroom.zrp.service.houses.proxy;

import com.ziroom.zrp.service.houses.base.BaseTest;
import com.ziroom.zrp.service.trading.proxy.TradingTaskServiceProxy;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * <p></p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author lusp
 * @version 1.0
 * @Date Created in 2017年10月13日 11:47
 * @since 1.0
 */

public class TradingTaskServiceProxyTest extends BaseTest{

    @Resource(name = "trading.tradingTaskServiceProxy")
    TradingTaskServiceProxy tradingTaskServiceProxy;


    @Test
    public void testFirstBillPayOvertimeCloseContract(){
        tradingTaskServiceProxy.firstBillPayOvertimeCloseContract();
    }

    @Test
    public void testfirstBillPayOvertimeCloseContract(){
        tradingTaskServiceProxy.firstBillPayOvertimeCloseContract();
    }

    @Test
    public void testSyncExpireContractToFin() {
        this.tradingTaskServiceProxy.syncExpireContractToFin();
    }

    @Test
    public void testInvalidMeterFinReceiBill() {
        this.tradingTaskServiceProxy.invalidMeterFinReceiBill();
    }
}
