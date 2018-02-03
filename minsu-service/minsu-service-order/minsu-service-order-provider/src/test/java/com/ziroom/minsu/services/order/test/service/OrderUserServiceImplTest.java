package com.ziroom.minsu.services.order.test.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.ziroom.minsu.services.common.utils.DateSplitUtil;
import com.ziroom.minsu.services.order.entity.OrderConfigVo;
import com.ziroom.minsu.services.order.entity.OrderInfoVo;
import com.ziroom.minsu.services.order.service.OrderCommonServiceImpl;
import com.ziroom.minsu.services.order.service.OrderConfigServiceImpl;
import com.ziroom.minsu.services.order.service.OrderUserServiceImpl;

import org.junit.Test;

import com.asura.framework.base.util.DateUtil;
import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.minsu.entity.order.OrderEntity;
import com.ziroom.minsu.services.order.test.base.BaseTest;
import com.ziroom.minsu.services.order.utils.FinanceMoneyUtil;
import com.ziroom.minsu.services.order.dto.CanclOrderRequest;

/**
 * <p>用户的测试</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/4/2.
 * @version 1.0
 * @since 1.0
 */
public class OrderUserServiceImplTest extends BaseTest{

    @Resource(name = "order.orderUserServiceImpl")
    private OrderUserServiceImpl orderUserService;


    @Resource(name = "order.orderCommonServiceImpl")
    private OrderCommonServiceImpl orderCommonService;

    @Resource(name = "order.orderConfigServiceImpl")
    private OrderConfigServiceImpl orderConfigService;



    @Test
    public void TestcountHouseLock() throws Exception{
        Date start = DateUtil.parseDate("2016-05-10", "yyyy-MM-dd");
        Date end = DateUtil.parseDate("2016-05-10", "yyyy-MM-dd");
        Long countHouseLock = orderUserService.countHouseLock("8a9e9aae5419cc22015419cc250a0002", start, end);
        System.out.println(JsonEntityTransform.Object2Json(countHouseLock));
    }

    @Test
    public void TestCancleOrderOnly() throws Exception{
        OrderInfoVo order = new OrderInfoVo();
    	order.setOrderStatus(10);
    	CanclOrderRequest request = new CanclOrderRequest();
    	request.setOrderSn("8a9e9cd253d0b29d0153d0b29d460004");
    	
    	orderUserService.cancleOrderOnly(order, request);
    }

    @Test
    public void TestCancelOrder() throws Exception{
    	OrderInfoVo order = new OrderInfoVo();
    	order.setOrderStatus(10);
//    	order.setPayMoney(2650);
    	CanclOrderRequest request = new CanclOrderRequest();
    	request.setOrderSn("8a9e9cd253d0b29d0153d0b29d460004");
    	
    	orderUserService.cancleOrderOnly(order, request);
    }
    
    @Test
    public void TestCheckByOrderPayRecording() throws Exception{
    	String orderSn = "8a9e9cd253d0b29d0153d0b29d460004";
    	orderUserService.checkByOrderPayRecording(orderSn);
    }
    
    @Test
    public void TestgetDayCutPricesMap() throws Exception{
    	//String orderSn = "170616U9X1L3P0175651";//170530ZNND8092002655
    	List<String> list = new ArrayList<String>();
    	list.add("170530ZNND8092002655");list.add("170602M9HQJ3LF172415");list.add("170613ZRXZA988155023");
    	//list.add("1706139PR0U03W023121");
    	//list.add("170613ZRXZA988155023");list.add("170628J78L7EL4164203");list.add("170703T3ZOG8MB172836");list.add("170602M9HQJ3LF172415");
   
    	for (String str : list) {
    		OrderInfoVo orderInfo = orderCommonService.getOrderInfoByOrderSn(str);
    		Map<String, Integer> priceMap = orderUserService.getDayCutPricesMap(orderInfo,  orderInfo.getDiscountMoney());
    		Date testDate = DateSplitUtil.transDateTime2Date(orderInfo.getStartTime());
    		Date teststartTime = orderInfo.getStartTime();
    		//当前的折扣金额
    		OrderConfigVo config = orderConfigService.getOrderConfigVo(str);
    		int rentalMoney = FinanceMoneyUtil.getRealRentalMoney(config, orderInfo, DateSplitUtil.transDateTime2Date(new Date()), priceMap);
    		int discountMoney = FinanceMoneyUtil.getDiscountMoneyBetweenDays(config, orderInfo, orderInfo.getStartTime(), DateSplitUtil.transDateTime2Date(new Date()), priceMap);
        	Date startTime = orderInfo.getStartTime();
        	Integer firstNight = priceMap.get(DateUtil.dateFormat(startTime));
        	System.out.println(firstNight);
        	System.out.println(testDate);
		}
    }
}
