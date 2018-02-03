package com.ziroom.minsu.services.order.test.proxy;

import java.util.Date;

import javax.annotation.Resource;

import com.asura.framework.base.util.DateUtil;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.DateUtil.IntervalUnit;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.services.order.proxy.OrderTaskServiceProxy;
import com.ziroom.minsu.services.order.proxy.StaticsServiceProxy;

import org.junit.Test;

import com.ziroom.minsu.services.common.utils.SystemGlobalsUtils;
import com.ziroom.minsu.services.order.test.base.BaseTest;
import com.ziroom.minsu.services.order.dao.HouseLockDao;
import com.ziroom.minsu.services.order.dao.OrderDao;
import com.ziroom.minsu.services.order.dto.OrderStaticsRequest;
import com.ziroom.minsu.services.order.dto.OrderTaskRequest;
import com.ziroom.minsu.services.order.service.OrderTaskServiceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>定时任务测试</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/4/3.
 * @version 1.0
 * @since 1.0
 */
public class StaticsServiceProxyTest extends BaseTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(StaticsServiceProxyTest.class);

    @Resource(name = "order.staticsServiceProxy")
    private StaticsServiceProxy staticsServiceProxy;

    @Test
    public void getStaticsCountLanReplyOrderNum(){
    	OrderStaticsRequest request = new OrderStaticsRequest();
    	request.setLimitTime(DateUtil.intervalDate(-60, IntervalUnit.DAY));
		request.setSumTime(30*60*1000);
		request.setLandlordUid("664524c5-6e75-ee98-4e0d-667d38b9eee1");
		String result = staticsServiceProxy.getStaticsCountLanReplyOrderNum(JsonEntityTransform.Object2Json(request));
    	System.err.println(result);
    	System.err.println(result);
    }

    @Test
    public void getStaticsCountLanRefuseOrderNum(){
    	OrderStaticsRequest request = new OrderStaticsRequest();
    	request.setLimitTime(DateUtil.intervalDate(-50, IntervalUnit.DAY));
		request.setSumTime(30*60*1000);
		request.setLandlordUid("8a9e9a9f544b372101544b3721de0000");
		String result = staticsServiceProxy.getStaticsCountLanRefuseOrderNum(JsonEntityTransform.Object2Json(request));
    	System.err.println(result);
    	System.err.println(result);
    }
    
    @Test
    public void getStaticsCountSysRefuseOrderNum(){
    	OrderStaticsRequest request = new OrderStaticsRequest();
    	request.setLimitTime(DateUtil.intervalDate(-50, IntervalUnit.DAY));
		request.setSumTime(30*60*1000);
		request.setLandlordUid("8a9e9a9f544b372101544b3721de0000");
		String result = staticsServiceProxy.getStaticsCountSysRefuseOrderNum(JsonEntityTransform.Object2Json(request));
    	System.err.println(result);
    	System.err.println(result);
    }

    @Test
    public void getStaticsCountLanReplyOrderTime(){
    	OrderStaticsRequest request = new OrderStaticsRequest();
    	request.setLimitTime(DateUtil.intervalDate(-50, IntervalUnit.DAY));
		request.setSumTime(30*60*1000);
		request.setLandlordUid("8a9e9a9f544b372101544b3721de0000");
		String result = staticsServiceProxy.getStaticsCountLanReplyOrderTime(JsonEntityTransform.Object2Json(request));
    	System.err.println(result);
    	System.err.println(result);
    }
    
    @Test
    public void getStaticsCountLanOrderNum(){
    	OrderStaticsRequest request = new OrderStaticsRequest();
    	request.setLimitTime(DateUtil.intervalDate(-50, IntervalUnit.DAY));
		request.setSumTime(30*60*1000);
		request.setLandlordUid("8a9e9a9f544b372101544b3721de0000");
		String result = staticsServiceProxy.getStaticsCountLanOrderNum(JsonEntityTransform.Object2Json(request));
    	System.err.println(result);
    	System.err.println(result);
    }
    
}
