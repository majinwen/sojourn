package com.ziroom.minsu.services.order.test.proxy;

import javax.annotation.Resource;

import org.junit.Test;

import com.ziroom.minsu.entity.order.FinancePayVouchersEntity;
import com.ziroom.minsu.services.order.dao.FinanceIncomeDao;
import com.ziroom.minsu.services.order.dao.FinancePayVouchersDao;
import com.ziroom.minsu.services.order.proxy.OrderTaskFinanceServiceProxy;
import com.ziroom.minsu.services.order.test.base.BaseTest;

/**
 * 
 * <p>
 * </p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author lishaochuan on 2016年4月25日
 * @since 1.0
 * @version 1.0
 */
public class OrderTaskFinanceServiceProxyTest extends BaseTest {

	@Resource(name = "order.orderTaskFinanceServiceProxy")
	private OrderTaskFinanceServiceProxy orderTaskFinanceServiceProxy;
	
	@Resource(name = "order.financePayVouchersDao")
    private FinancePayVouchersDao financePayVouchersDao;
    
    @Resource(name = "order.financeIncomeDao")
    private FinanceIncomeDao financeIncomeDao;
    

    /**
     * to已入住生成单据
     * @author lishaochuan
     * @create 2016年5月18日下午7:00:52
     */
	@Test
	public void taskCreateFinanceTest() {
		orderTaskFinanceServiceProxy.taskCreateFinance();
		
		try {
			Thread.sleep(999*1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void taskRunFinance(){
		orderTaskFinanceServiceProxy.taskRunFinance();
		
		try {
			Thread.sleep(1000000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void runAllFinancePayVoucher(){
		FinancePayVouchersEntity payVouchers = financePayVouchersDao.findPayVouchersByPvSn("FK160825UF1D8C9E153642");
		//orderTaskFinanceServiceProxy.runAllFinancePayVoucher(payVouchers);
	}
	

	/**
	 * 扫描执行付款计划
	 * @author lishaochuan
	 * @create 2016年4月27日
	 */
	@Test
	public void taskRunFinancePayVoucherTest() {
		//orderTaskFinanceServiceProxy.taskRunFinancePayVoucher();
	}
	
	/**
	 * 定时任务扫描收入表记录
	 * @author lishaochuan
	 * @create 2016年4月27日
	 */
	@Test
	public void taskRunFinanceIncomeTest() {
		//orderTaskFinanceServiceProxy.taskRunFinanceIncome();
	}
	
	/*@Test
	public void pushAndSendMsgTest(){
		FinancePayVouchersEntity payVouchers = new FinancePayVouchersEntity();
		payVouchers.setTotalFee(100);
		payVouchers.setReceiveUid("937d573a-4f25-638b-db9b-f97339e3e5ming-2");
		
		orderTaskFinanceServiceProxy.pushAndSendMsg(payVouchers);
	}*/
	
}
