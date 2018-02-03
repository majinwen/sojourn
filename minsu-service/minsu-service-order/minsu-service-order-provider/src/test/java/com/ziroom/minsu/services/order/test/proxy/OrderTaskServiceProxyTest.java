package com.ziroom.minsu.services.order.test.proxy;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.asura.framework.base.context.ApplicationContext;
import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.DateUtil;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.MessageSourceUtil;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.conf.SmsTemplateEntity;
import com.ziroom.minsu.services.order.proxy.OrderTaskServiceProxy;

import org.codehaus.jackson.type.TypeReference;
import org.junit.Test;

import com.ziroom.minsu.services.basedata.api.inner.SmsTemplateService;
import com.ziroom.minsu.services.basedata.dto.SmsRequest;
import com.ziroom.minsu.services.common.constant.MessageConst;
import com.ziroom.minsu.services.common.constant.SysConst;
import com.ziroom.minsu.services.common.dto.PageRequest;
import com.ziroom.minsu.services.common.sms.MessageUtils;
import com.ziroom.minsu.services.common.sms.base.SmsMessage;
import com.ziroom.minsu.services.common.utils.SystemGlobalsUtils;
import com.ziroom.minsu.services.common.utils.ValueUtil;
import com.ziroom.minsu.services.order.test.base.BaseTest;
import com.ziroom.minsu.services.order.api.inner.OrderTaskOrderService;
import com.ziroom.minsu.services.order.dao.HouseLockDao;
import com.ziroom.minsu.services.order.dao.OrderDao;
import com.ziroom.minsu.services.order.dto.OrderTaskRequest;
import com.ziroom.minsu.services.order.entity.OrderHouseVo;
import com.ziroom.minsu.services.order.service.OrderTaskServiceImpl;
import com.ziroom.minsu.valenum.msg.MessageTemplateCodeEnum;

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
public class OrderTaskServiceProxyTest extends BaseTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderTaskServiceProxyTest.class);

    @Resource(name = "order.orderTaskOrderProxy")
    private OrderTaskServiceProxy orderTaskServiceProxy;

    
    @Resource(name = "order.orderDao")
    private OrderDao orderDao;
    
    
    
    @Resource(name = "order.houseLockDao")
    private HouseLockDao houseLockDao;
    
    
    @Resource(name = "order.orderTaskServiceImpl")
    private OrderTaskServiceImpl orderTaskServiceImpl;
    
    @Resource(name = "basedata.smsTemplateService")
    private SmsTemplateService smsTemplateService;
    
    /**
     * 下单后订单，房东在【申请预定提醒时限】内未接受订单，发短信提醒 
     * @author lishaochuan
     * @create 2016年5月16日下午12:02:46
     */
    @Test
    public void TestTaskWatiConfimOrder(){
    	orderTaskServiceProxy.taskWatiConfimOrder();
    }
    
    /**
     * 到入住时间，更新订单状态为已入住
     * @author lishaochuan
     * @create 2016年5月14日下午4:23:52
     */
    @Test
    public void TestUpdateOrderStatus(){
    	long startTime = System.currentTimeMillis();
        orderTaskServiceProxy.updateOrderStatus();
        long endTime = System.currentTimeMillis();
        System.out.println("@@@@@@@@@@@@@@@@@@@@@"+(endTime-startTime));
    }
    
    @Test
    public void TestTaskRefusedOrder(){
    	long startTime = System.currentTimeMillis();
        orderTaskServiceProxy.taskRefusedOrder();
        long endTime = System.currentTimeMillis();
        System.out.println("@@@@@@@@@@@@@@@@@@@@@"+(endTime-startTime));
    }


    /**
     * 自动退房
     * @author lishaochuan
     * @create 2016年5月18日下午6:59:50
     */
    @Test
    public void TesttaskCheckOut() {
        long startTime = System.currentTimeMillis();
        orderTaskServiceProxy.taskCheckOut();
        long endTime = System.currentTimeMillis();
        System.out.println("@@@@@@@@@@@@@@@@@@@@@"+(endTime-startTime));
    }
    
    @Test
    public void TestupdateOrderStatusByProxy() {
    	long startTime = System.currentTimeMillis();
    			
        orderTaskServiceProxy.updateOrderStatus();
        long endTime = System.currentTimeMillis();
        System.out.println("@@@@@@@@@@@@@@@@@@@@@"+(endTime-startTime));
    }

    
    @Test
    public void TestGetOverCheckInTimeAllOrderList(){
    	OrderTaskRequest taskRequest = new OrderTaskRequest();
    	taskRequest.setLimit(20);
    	taskRequest.setPage(1);
    	taskRequest.setOrderStatus(20);
    	taskRequest.setPayStatus(1);
    	taskRequest.setLimitDate(new Date());
    }

    
    @Test
    public void TestUpdateToCancelOrderUnLockByProxy() {
    	long startTime = System.currentTimeMillis();
        orderTaskServiceProxy.cancelOrderAndUnlockHouse();
        long endTime = System.currentTimeMillis();
        System.out.println("@@@@@@@@@@@@@@@@@@@@@"+(endTime-startTime));
    }
    
   /* @Test
    public void TestSendWeatherMsgForTomorrowOrder() {
    	
    	SystemGlobalsUtils.getValue("sms.send.url");
    	orderTaskServiceProxy.sendWeatherMsgForTomorrowOrder();
    	System.out.println("---------------");
    	
    	try {
			Thread.sleep(999999999);
		} catch (InterruptedException e) {
            LogUtil.error(LOGGER, "e:{}", e);
		}
    }*/
    
    
    
    @Test
    public void TestTaskConfirmOtherFeeLandlord(){
    	long startTime = System.currentTimeMillis();
    	orderTaskServiceProxy.taskConfirmOtherFeeLandlord();
    	long endTime = System.currentTimeMillis();
        System.err.println("@@@@@@@@@@@@@@@@@@@@@"+(endTime-startTime));
    }
    
    @Test
    public void TestTaskConfirmOtherUser(){
    	long startTime = System.currentTimeMillis();
    	orderTaskServiceProxy.taskConfirmOtherUser();
    	long endTime = System.currentTimeMillis();
        System.err.println("@@@@@@@@@@@@@@@@@@@@@"+(endTime-startTime));
    }
    
    

    @Test
    public void taskCheckOutRemind(){
        orderTaskServiceProxy.taskCheckOutRemind();
        try {
            Thread.sleep(3000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testgetWaitCheckinList(){
		//短信code  
		String msgCode = ValueUtil.getStrValue(MessageTemplateCodeEnum.ORDER_WILL_CHECKIN_SEND_MESSAGE_TO_LAN.getCode());
		
		//获取明天将要入住的订单，给房东发短信
		PageRequest pageRequest = new PageRequest();
		Map<String, String> map = new HashMap<String, String>();
		int page = 1;
		for(; ; ){
			pageRequest.setPage(page);
			String waitCheckinListJson = orderTaskServiceProxy.getWaitCheckinList(JsonEntityTransform.Object2Json(pageRequest));
			LogUtil.info(LOGGER, "查询结果result={}", waitCheckinListJson);
			DataTransferObject waitCheckinListDto = JsonEntityTransform.json2DataTransferObject(waitCheckinListJson);
			if(waitCheckinListDto.getCode() == DataTransferObject.ERROR){
				break;
			}
			List<OrderHouseVo> waitCheckinList = waitCheckinListDto.parseData("list", new TypeReference<List<OrderHouseVo>>() {});
			if(Check.NuNCollection(waitCheckinList)){
				break;
			}
			page++;
			for (OrderHouseVo orderHouseVo : waitCheckinList) {
				String houseName = orderHouseVo.getHouseName();
				if( Check.NuNStr(orderHouseVo.getLandlordTel()) || Check.NuNStr(houseName) || Check.NuNStr(orderHouseVo.getUserName())){
					continue;
				}
				if(Check.NuNObj(orderHouseVo.getStartTime()) || Check.NuNObj(orderHouseVo.getEndTime())){
					continue;
				}
				houseName='"'+houseName+'"';
				int datebetweenOfDayNum = DateUtil.getDatebetweenOfDayNum(orderHouseVo.getStartTime(), orderHouseVo.getEndTime());
				
				SmsRequest smsRequest = new SmsRequest();
				smsRequest.setMobile("18201693996");
				smsRequest.setSmsCode(msgCode);
				map.put("{1}", orderHouseVo.getUserName());
				map.put("{2}", houseName);
				map.put("{3}", String.valueOf(datebetweenOfDayNum));
				smsRequest.setParamsMap(map);
				
				String findEntityBySmsCode = smsTemplateService.findEntityBySmsCode(smsRequest.getSmsCode());
				DataTransferObject SMSDto = JsonEntityTransform.json2DataTransferObject(findEntityBySmsCode);
				SmsTemplateEntity parseData = SMSDto.parseData("smsTemplateEntity", new TypeReference<SmsTemplateEntity>() {});
				String smsComment = parseData.getSmsComment();
				this.sendSmsByCode(JsonEntityTransform.Object2Json(smsRequest),parseData);
			}
		}
      
    }
    
    
    public String sendSmsByCode(String smsRequestStr,SmsTemplateEntity smsTemplateEntity){

		DataTransferObject dto = new DataTransferObject();

		SmsRequest smsRequest = JsonEntityTransform.json2Object(smsRequestStr, SmsRequest.class);

		if(Check.NuNObj(smsRequest)||Check.NuNStr(smsRequest.getSmsCode())
				||Check.NuNStr(smsRequest.getMobile())){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("请求参数不存在或模板code不存在或者手机号错误");
			return dto.toJsonString();
		}
		LogUtil.info(LOGGER, "短信发送参数smsRequest={}", smsRequest.toString());
		//SmsTemplateEntity smsTemplateEntity = smsTemplateServiceImpl.findEntityBySmsCode(smsRequest.getSmsCode());
		if(Check.NuNObj(smsTemplateEntity)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("按照模板code={"+smsRequest.getSmsCode()+"}查询，结果不存在");
			return dto.toJsonString();
		}
		Map<String, String> paramsMap = smsRequest.getParamsMap();
		LogUtil.info(LOGGER, "短信发送手机号mobile={},发送内容smsContent={},发送参数paramsMap={}", smsRequest.getMobile(),smsTemplateEntity.getSmsComment(),paramsMap);
		String  mobileNationCode =Check.NuNStr(paramsMap.get(SysConst.MOBILE_NATION_CODE_KEY))?SysConst.MOBILE_NATION_CODE:paramsMap.get(SysConst.MOBILE_NATION_CODE_KEY);
		SmsMessage smsMessage = new SmsMessage(smsRequest.getMobile(),smsTemplateEntity.getSmsComment(),mobileNationCode);
		MessageUtils.sendSms(smsMessage, paramsMap); 
		return dto.toJsonString();
	}
    
    @Test
    public void checkInPreNoticeTenant(){
    	this.orderTaskServiceProxy.checkInPreNoticeTenant("");
    	
    	try {
			Thread.sleep(100000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    }
}
