package com.ziroom.minsu.services.order.test.proxy;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import com.ziroom.minsu.services.common.constant.JpushConst;
import com.ziroom.minsu.services.common.sms.SendService;
import com.ziroom.minsu.services.common.thread.SendThread;
import com.ziroom.minsu.services.common.thread.pool.SendThreadPool;
import com.ziroom.minsu.valenum.msg.MessageTemplateCodeEnum;
import org.junit.Test;

import com.asura.framework.base.util.MD5Util;
import com.ziroom.minsu.entity.order.OrderEntity;
import com.ziroom.minsu.services.order.dao.OrderBaseDao;
import com.ziroom.minsu.services.order.entity.OrderInfoVo;
import com.ziroom.minsu.services.order.proxy.OrderMsgProxy;
import com.ziroom.minsu.services.order.test.base.BaseTest;

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
public class OrderMsgProxyTest extends BaseTest {

    @Resource(name ="order.orderMsgProxy")
	private OrderMsgProxy orderMsgProxy;
    
    @Resource(name ="order.orderBaseDao")
    private OrderBaseDao orderBaseDao;


	@Resource(name = "order.smsAndJpushSendService")
	private SendService<String,Map<String,String>,Map<String,String>,Map<String,String>> smsAndJpushSendService;




	/**
	 * 定时任务，下单后订单，房东一段时间内未确认，发短信提醒
	 * @author lishaochuan
	 * @create 2016年5月19日上午4:44:30
	 */
	@Test
	public void TestSendMsg4CreatOrder() throws  Exception{

		//3.给房东发送消息 您有房客xx的新订单待审核，请在30分钟内处理订单。
		Map<String,String> lanPushPar = new HashMap<>();
		lanPushPar.put("uid","a06f82a2-423a-e4e3-4ea8-e98317540190");
		lanPushPar.put("{1}","ceshi");
		lanPushPar.put("{2}", "时间");
		//极光推动的参数
		Map<String,String> bizParLan = new HashMap<>();
		bizParLan.put(JpushConst.MSG_SUB_TYPE_KEY, JpushConst.MSG_SUB_TYPE_VALULE_1);
		//获取房东的订单详情的H5的url
		bizParLan.put("url","url");

		Map<String,String> lanSmsPar = new HashMap<>();
		lanSmsPar.put("phone", "18911123545");
		lanSmsPar.put("{1}","ceshi");
		lanSmsPar.put("{2}", "时间");

		SendThreadPool.execute(new SendThread<>(smsAndJpushSendService, MessageTemplateCodeEnum.ORDER_LANLORD_CREATE.getCode(), lanPushPar, lanSmsPar, bizParLan));

		Thread.sleep(50000L);

	}


	
    
    /**
     * 定时任务，下单后订单，房东一段时间内未确认，发短信提醒 
     * @author lishaochuan
     * @create 2016年5月19日上午4:44:30
     */
    @Test
    public void TestSendMsg4WatiConfimOrder(){
    	
    	OrderEntity order = orderBaseDao.getOrderBaseByOrderSn("160519PO6H1KT5014924");
    	orderMsgProxy.sendMsg4WatiConfimOrder(order);
    }
    
   
    /**
     * 打款失败推送消息
     * @author liyingjie
     * @create 2016年5月15日下午4:23:52
     */
    @Test
    public void TestSendMsg4RefundFail(){
    	Map<String, String> paramMapMap = new HashMap<String, String>(); //
    	paramMapMap.put("uid", "HAHAHHAHH");
    	paramMapMap.put("type", String.valueOf(2));
    	orderMsgProxy.sendMsg4RefundFail(paramMapMap);
    }
    
    /**
     * 打款成功推送消息
     * @author liyingjie
     * @create 2016年5月15日下午4:23:52
     */
    @Test
    public void TestSendMsg4RefundSuccess(){
    	Map<String, String> paramMapMap = new HashMap<String, String>(); //
    	paramMapMap.put("uid", "HAHAHHAHH");
    	paramMapMap.put("type", String.valueOf(2));
    	paramMapMap.put("totalFee", String.valueOf(200));
    	paramMapMap.put("phone", "18301315875");
    	orderMsgProxy.sendMsg4RefundSuccess(paramMapMap);
    }
    
    
    /**
     * 打款成功推送消息
     * @author liyingjie
     * @create 2016年5月15日下午4:23:52
     */
    @Test
    public void TestSendMsg4PaySuccess(){
    	OrderInfoVo orderInfoVo = new OrderInfoVo();
    	orderInfoVo.setUserUid("jiangzw_v11010");
    	orderInfoVo.setLandlordUid("jiangzw_v11010");
    	orderInfoVo.setUserTel("18633033235");
    	orderInfoVo.setHouseName("将府小院");
    	orderInfoVo.setOrderSn("tttttt");
    	orderInfoVo.setStartTimeStr("2016-5-18");
    	orderInfoVo.setEndTimeStr("2016-5-20");
    	orderInfoVo.setHouseAddr("将府北里");
    	orderInfoVo.setHouseSn("231040686458F");
    	orderInfoVo.setRentWay(1);
    	orderMsgProxy.sendMsg4PaySuccess(orderInfoVo);
    }
    
    
    @Test
    public void TestsendMsg4Cancel(){
    	
    	OrderInfoVo info = new OrderInfoVo();
    	info.setUserName("川");
    	info.setOrderSn("160519H7GU516H072821");
    	info.setLandlordUid("7dde1652-0908-4ef0-a8d1-4538fa2aff5c");
    	
    	orderMsgProxy.sendMsg4Cancel(info, 11);
    	
    }
    
    @Test
    public void TestsendMsgToLanForPayVouchers(){
    	//  1605223PD8SC6N150400
        //  160914BSC3SN2L091138  11  返现
    	orderMsgProxy.sendMsgToLanForPayVouchers("160706C12B7HHP181740", 11, 29145, "账户空间","FK160914U5L8LU8L155108");
    	
    }

}
