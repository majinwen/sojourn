package com.ziroom.minsu.services.order.proxy;

import com.alibaba.fastjson.JSONObject;
import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.*;
import com.asura.framework.rabbitmq.entity.QueueName;
import com.asura.framework.rabbitmq.send.RabbitMqSendClient;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.house.HouseRoomMsgEntity;
import com.ziroom.minsu.entity.order.FinanceCashbackEntity;
import com.ziroom.minsu.entity.order.OrderEntity;
import com.ziroom.minsu.services.basedata.api.inner.CityTemplateService;
import com.ziroom.minsu.services.cms.api.inner.ShortChainMapService;
import com.ziroom.minsu.services.common.conf.EnumMinsuConfig;
import com.ziroom.minsu.services.common.constant.JpushConst;
import com.ziroom.minsu.services.common.constant.SysConst;
import com.ziroom.minsu.services.common.mq.HouseMq;
import com.ziroom.minsu.services.common.sms.SendService;
import com.ziroom.minsu.services.common.thread.SendThread;
import com.ziroom.minsu.services.common.thread.pool.SendThreadPool;
import com.ziroom.minsu.services.common.utils.*;
import com.ziroom.minsu.services.house.api.inner.HouseManageService;
import com.ziroom.minsu.services.house.entity.OrderNeedHouseVo;
import com.ziroom.minsu.services.order.dto.CreateOrderRequest;
import com.ziroom.minsu.services.order.entity.OrderInfoVo;
import com.ziroom.minsu.services.order.entity.OrderSaveVo;
import com.ziroom.minsu.services.order.service.FinanceCashbackServiceImpl;
import com.ziroom.minsu.services.order.service.OrderCommonServiceImpl;
import com.ziroom.minsu.valenum.common.JumpOpenAppEnum;
import com.ziroom.minsu.valenum.common.UserTypeEnum;
import com.ziroom.minsu.valenum.common.YesOrNoEnum;
import com.ziroom.minsu.valenum.house.RentWayEnum;
import com.ziroom.minsu.valenum.house.RoomTypeEnum;
import com.ziroom.minsu.valenum.msg.MessageTemplateCodeEnum;
import com.ziroom.minsu.valenum.order.*;
import com.ziroom.minsu.valenum.traderules.TradeRulesEnum;
import com.ziroom.minsu.valenum.version.VersionCodeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

/**
 * <p>订单消息</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/5/12.
 * @version 1.0
 * @since 1.0
 */
@Component("order.orderMsgProxy")
public class OrderMsgProxy {

	/**
	 * log
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(OrderMsgProxy.class);

	@Value(value = "${JPUSH_APP_M_HOUSE}")
	private String m_house;


	@Value(value = "${lan.eva.url}")
	private String lanEvaUrl;

	@Resource(name = "order.smsSendService")
	private SendService<String,String,Map<String,String>,Void> smsSendService;

	@Resource(name = "order.jpushSendService")
	private SendService<String,String,Map<String,String>,Map<String,String>> jpushSendService;

	@Resource(name = "order.smsAndJpushSendService")
	private SendService<String,Map<String,String>,Map<String,String>,Map<String,String>> smsAndJpushSendService;


	@Resource(name = "basedata.cityTemplateService")
	private CityTemplateService cityTemplateService;

	@Resource(name = "cms.shortChainMapService")
	private ShortChainMapService shortChainMapService;

	private String smsAppJumpUrl = "common/ee5f86/goToApp?param=";
	
	@Resource(name = "house.rabbitSendClient")
	private RabbitMqSendClient rabbitMqSendClient;
	
	@Resource(name="house.queueName")
	private QueueName queueName ;

	@Resource(name="order.orderCommonServiceImpl")
	private OrderCommonServiceImpl orderCommonServiceImpl;
	
	@Resource(name="order.financeCashbackServiceImpl")
	private FinanceCashbackServiceImpl financeCashbackServiceImpl;
	
	// 房源信息
	@Resource(name = "house.houseManageService")
	private HouseManageService houseManageService;
	
	/**
	 * 获取支付时限
	 * @return
	 */
	private String getPayLimit(){
		String json = cityTemplateService.getTextValue(null, TradeRulesEnum.TradeRulesEnum002.getValue());
		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(json);
		String textValue = (String)dto.getData().get("textValue");
		return textValue;
	}


	/**
	 * 获取房东的评价详情
	 * @author afi
	 * @param orderSn
	 * @return
	 */
	private String getLanEvaDetail(String orderSn){
		return lanEvaUrl + orderSn;
	}

	/**
	 * 获取当前房源的房源详情
	 * @author afi
	 * @return
	 */
	private String getHouseDetailUrl(){
		return m_house + JpushConst.HOUSE_DETAIL_URL;
	}

	/**
	 * 获取当前的订单情况
	 * @author afi
	 * @param orderSn
	 * @param landlordUid
	 * @return
	 */
	private String getOrderDetailUrl(String orderSn,String landlordUid){
		return m_house + String.format(JpushConst.ORDER_DETAIL_URL,orderSn,landlordUid);
	}

	//获取短链接
	private String getContentJump(String orderSn, Integer type) {
		JSONObject object = new JSONObject();
		object.put("orderSn",orderSn);
		object.put("type",type);
		object.put("jumpType", JumpOpenAppEnum.EVALUATE_INFO.getCode());
		String param = "";
		try {
			param = URLEncoder.encode(object.toString(),"utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String url = m_house + smsAppJumpUrl + param;
		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(shortChainMapService.generateShortLink(url, "001"));
		if (dto.getCode() == DataTransferObject.SUCCESS) {
			String shortLink = (String) dto.getData().get("shortLink");
			return shortLink;
		}
		return null;
	}

	//创建订单，获取短链接
	private String createOrderGetContentJump(String orderSn, Integer type, Integer jumpType) {
		JSONObject object = new JSONObject();
		object.put("orderSn",orderSn);
		object.put("type",type);
		object.put("jumpType", jumpType);
		String param = "";
		try {
			param = URLEncoder.encode(object.toString(),"utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String url = m_house + smsAppJumpUrl + param;
		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(shortChainMapService.generateShortLink(url, "001"));
		if (dto.getCode() == DataTransferObject.SUCCESS) {
			String shortLink = (String) dto.getData().get("shortLink");
			return shortLink;
		}
		return null;
	}
		
	/**
	 * 普通订单的提示消息模板
	 */
	public static final String msg_common = "订单提交成功，需房东确认是否可以接待。如果房东未及时确认，可联系房东进行沟通。如有疑问欢迎致电客服热线，我们将竭诚为您服务！\r\n客服电话：";
	public static final String service_phone = "400-7711-666";

	/**
	 * 用户可以评价
	 * @author afi
	 * @param info
	 */
	public void sendMsg4CanMarkUser(OrderInfoVo info){
		LogUtil.info(LOGGER,"开始发送信息  用户可以评价 orderSn:{}",info.getOrderSn());
		//给用户
		//您已可以对{1}房源及{2}房东进行评价了，评价后还可以查看房东对您的评价。
		//1. 给用户发短信
		Map<String,String> smsPar = new HashMap<>();
		smsPar.put("phone",info.getUserTel());
		smsPar.put("{1}",info.transName());
		smsPar.put("{2}", info.getLandlordName());
		if(!Check.NuNStr(info.getUserTelCode())){
			smsPar.put(SysConst.MOBILE_NATION_CODE_KEY,info.getUserTelCode());
		}
		//2. 给用用户发推送
		Map<String,String> pushPar = new HashMap<>();
		pushPar.put("uid",info.getUserUid());
		pushPar.put("{1}",info.transName());
		pushPar.put("{2}",info.getLandlordName());

		Map<String,String> bizPar = new HashMap<>();
		bizPar.put(JpushConst.MSG_SUB_TYPE_KEY, JpushConst.MSG_SUB_TYPE_VALULE_3);
		bizPar.put(JpushConst.MSG_TAG_TYPE,JpushConst.MSG_TARGET_TENANT);
		bizPar.put(JpushConst.MSG_HAS_RESPONSE,"0");
		bizPar.put("orderSn", info.getOrderSn());
		bizPar.put("rentWay", ValueUtil.getStrValue(info.getRentWay()));
		bizPar.put("fid", info.returnFid());

		//短信和推送同时发送
		SendThreadPool.execute(new SendThread<>(smsAndJpushSendService, MessageTemplateCodeEnum.ORDER_USER_CAN_MARK.getCode(), pushPar, smsPar,bizPar));
	}

	/**
	 * 定时任务，下单后订单，房东一段时间内未确认，发短信提醒 
	 * @author lishaochuan
	 * @create 2016年5月13日下午2:21:06
	 */
	public void sendMsg4WatiConfimOrder(OrderEntity order) {
		// 房客{1}的订单您还未处理，ta却一直在等待您的回复，千万不要错过耐心等待的ta，快去处理一下吧。
		LogUtil.info(LOGGER, "开始发送信息  房东一段时间内未确认orderSn:{}", order.getOrderSn());
		// 1. 给用户发短信
		Map<String, String> smsPar = new HashMap<>();
		smsPar.put("{1}", order.getUserName());

		if(!Check.NuNStr(order.getLandlordTelCode())){
			smsPar.put(SysConst.MOBILE_NATION_CODE_KEY, order.getLandlordTelCode());
		}

		// 短信和推送同时发送
		SendThreadPool.execute(new SendThread<>(smsSendService, order.getLandlordTel(), MessageTemplateCodeEnum.WAIT_COMFIRM.getCode(), smsPar,null));
	}


	/**
	 * 接受订单 直接发送短信
	 * @param dto
	 * @param order
	 */
	public void sendMsg4AcceptOrder(DataTransferObject dto,OrderInfoVo order) {
		if(dto.getCode() == DataTransferObject.SUCCESS){
			String payTime = this.getPayLimit();
			//当前订单：实时订单 直接支付
			//1. 给用户发短信 您的x房源xx订单还未支付，请在30分钟内完成支付。
			Map<String,String> smsPar = new HashMap<>();
			smsPar.put("phone", order.getUserTel());
			smsPar.put("{1}", ValueUtil.getStrValue(order.transName()));
			smsPar.put("{2}",ValueUtil.getStrValue(order.getOrderSn()));
			smsPar.put("{3}",payTime);

			if(!Check.NuNStr(order.getUserTelCode())){
				smsPar.put(SysConst.MOBILE_NATION_CODE_KEY,order.getUserTelCode());
			}
			//2. 给用用户发推送
			Map<String,String> pushPar = new HashMap<>();
			pushPar.put("uid",order.getUserUid());
			pushPar.put("{1}",ValueUtil.getStrValue(order.transName()));
			pushPar.put("{2}",ValueUtil.getStrValue(order.getOrderSn()));
			pushPar.put("{3}",payTime);

			Map<String,String> bizPar = new HashMap<>();
			bizPar.put(JpushConst.MSG_SUB_TYPE_KEY, JpushConst.MSG_SUB_TYPE_VALULE_2);
			bizPar.put(JpushConst.MSG_HAS_RESPONSE,"1");
			bizPar.put(JpushConst.MSG_TAG_TYPE,JpushConst.MSG_TARGET_TENANT);
			bizPar.put("orderSn", order.getOrderSn());
			//短信和推送同时发送
			//您的{1}房源{2}订单还未支付，请在{3}分钟内完成支付。
			SendThreadPool.execute(new SendThread<>(smsAndJpushSendService, MessageTemplateCodeEnum.ORDER_USER_TO_PAY.getCode(), pushPar, smsPar,bizPar));
		}
	}



	/**
	 * 下单并发送消息
	 * @author afi
	 * @param dto
	 * @param orderSaveInfo
	 * @param houseInfo
	 */
	public void sendMsg4CreateOrder(DataTransferObject dto,OrderSaveVo orderSaveInfo,OrderNeedHouseVo houseInfo,CreateOrderRequest request){
		if(dto.getCode() == DataTransferObject.SUCCESS){

			LogUtil.info(LOGGER,"开始发送信息  下单并发送消息 orderSn:{}",orderSaveInfo.getOrder().getOrderSn());

			if(orderSaveInfo.getOrder().getOrderStatus() == OrderStatusEnum.WAITING_CHECK_IN.getOrderStatus()
					&& orderSaveInfo.getOrderMoney().getNeedPay() > 0){
				//当前订单：实时订单 直接支付
				//1. 给用户发短信 您的x房源xx订单还未支付，请在30分钟内完成支付。
				Map<String,String> smsPar = new HashMap<>();
				smsPar.put("phone",orderSaveInfo.getOrder().getUserTel());
				smsPar.put("{1}", ValueUtil.getStrValue(houseInfo.transName()));
				smsPar.put("{2}",ValueUtil.getStrValue(orderSaveInfo.getOrder().getOrderSn()));
				smsPar.put("{3}",ValueUtil.getStrValue(orderSaveInfo.getPayTimeStr()));

				if(!Check.NuNStr(orderSaveInfo.getOrder().getUserTelCode())){
					smsPar.put(SysConst.MOBILE_NATION_CODE_KEY,orderSaveInfo.getOrder().getUserTelCode());
				}
				//2. 给用用户发推送
				Map<String,String> pushPar = new HashMap<>();
				pushPar.put("uid",orderSaveInfo.getOrder().getUserUid());
				pushPar.put("{1}",houseInfo.transName());
				pushPar.put("{2}",orderSaveInfo.getOrder().getOrderSn());
				pushPar.put("{3}",orderSaveInfo.getPayTimeStr());

				Map<String,String> bizPar = new HashMap<>();
				bizPar.put(JpushConst.MSG_SUB_TYPE_KEY, JpushConst.MSG_SUB_TYPE_VALULE_2);
				bizPar.put(JpushConst.MSG_HAS_RESPONSE,"1");
				bizPar.put(JpushConst.MSG_TAG_TYPE,JpushConst.MSG_TARGET_TENANT);
				bizPar.put("orderSn", orderSaveInfo.getOrder().getOrderSn());
				//短信和推送同时发送
				//您的{1}房源{2}订单还未支付，请在{3}分钟内完成支付。
				SendThreadPool.execute(new SendThread<>(smsAndJpushSendService, MessageTemplateCodeEnum.ORDER_USER_CREATE.getCode(), pushPar, smsPar,bizPar));
				
				//实时订单，给房东发送短信
				/*您的美屋：{1}，有新订单待房客{2}支付，入住时间为{3}至{4} 。由于您开启立即预订，请再次确认是否可以
				接待客户，如果不可以接待，及时与客户沟通并更新房源的房态。如有疑问欢迎致电客服热线：400-7711-
				666，我们将竭诚为您服务！*/
				String contentJump = createOrderGetContentJump(orderSaveInfo.getOrder().getOrderSn(), UserTypeEnum.LANDLORD.getUserCode(),JumpOpenAppEnum.LAND_ORDER_INFO.getCode());
				Map<String,String> smsToLandPar = new HashMap<>();
				smsToLandPar.put("phone",orderSaveInfo.getOrder().getLandlordTel());
				smsToLandPar.put("{1}", ValueUtil.getStrValue(houseInfo.transName()));
				smsToLandPar.put("{2}",ValueUtil.getStrValue(orderSaveInfo.getOrder().getUserName()));
				smsToLandPar.put("{3}",ValueUtil.getStrValue(DateUtil.dateFormat(orderSaveInfo.getOrder().getStartTime())));
				smsToLandPar.put("{4}",ValueUtil.getStrValue(DateUtil.dateFormat(orderSaveInfo.getOrder().getEndTime())));
				smsToLandPar.put("{5}",contentJump);

				if(!Check.NuNStr(orderSaveInfo.getOrder().getLandlordTelCode())){
					smsPar.put(SysConst.MOBILE_NATION_CODE_KEY,orderSaveInfo.getOrder().getUserTelCode());
				}
				//2. 给房东发推送
				Map<String,String> pushToLandPar = new HashMap<>();
				pushToLandPar.put("uid",orderSaveInfo.getOrder().getLandlordUid());
				pushToLandPar.put("{1}", ValueUtil.getStrValue(houseInfo.transName()));
				pushToLandPar.put("{2}",ValueUtil.getStrValue(orderSaveInfo.getOrder().getUserName()));
				pushToLandPar.put("{3}",ValueUtil.getStrValue(DateUtil.dateFormat(orderSaveInfo.getOrder().getStartTime())));
				pushToLandPar.put("{4}",ValueUtil.getStrValue(DateUtil.dateFormat(orderSaveInfo.getOrder().getEndTime())));
				pushToLandPar.put("{5}","");
				
				Map<String,String> bizToLandPar = new HashMap<>();
				bizToLandPar.put(JpushConst.MSG_SUB_TYPE_KEY, JpushConst.MSG_SUB_TYPE_VALULE_1);
				bizToLandPar.put(JpushConst.MSG_HAS_RESPONSE,"1");
				bizToLandPar.put(JpushConst.MSG_TAG_TYPE,JpushConst.MSG_TARGET_LAN);
				bizToLandPar.put(JpushConst.MSG_TITLE,JpushConst.MSG_TITLE_VALUE_1);
				bizToLandPar.put("orderSn", orderSaveInfo.getOrder().getOrderSn());
				
				bizToLandPar.put("url",getOrderDetailUrl(orderSaveInfo.getOrder().getOrderSn(), orderSaveInfo.getOrder().getLandlordUid()));

				
				//短信和推送同时发送
				SendThreadPool.execute(new SendThread<>(smsAndJpushSendService, MessageTemplateCodeEnum.CURRENT_ORDER_SMS_TO_LAND.getCode(), pushToLandPar, smsToLandPar,bizToLandPar));
				
			}

			//4 如果当前的订单的金额为0 直接给开发人员发送短信
			if (orderSaveInfo.getOrderMoney().getNeedPay() == 0){
				Map<String,String> devPar = new HashMap<>();
				devPar.put("orderSn", orderSaveInfo.getOrder().getOrderSn());
				String mobiles = ZkUtil.getZkSysValue(EnumMinsuConfig.minsu_devAndOptPhoneList.getType(), EnumMinsuConfig.minsu_devAndOptPhoneList.getCode());

				//当前的订单的金额为0 给开发人发送信息
				LogUtil.info(LOGGER,"当前的订单的金额为0 给开发人发送信息, mobiles:{}", mobiles);
				SendThreadPool.execute(new SendThread<>(smsSendService, mobiles, MessageTemplateCodeEnum.ORDER_ZERO.getCode(), devPar,null));

			}

			//低于长租退订政策版本才发送提示升级短信
			if (!VersionCodeEnum.checkLongTd(ValueUtil.getintValue(request.getVersionCode()))){
				String isOpenMsg = ZkUtil.getZkSysValue(EnumMinsuConfig.minsu_isOpenMsg.getType(), EnumMinsuConfig.minsu_isOpenMsg.getCode());
				if (ValueUtil.getintValue(isOpenMsg) == YesOrNoEnum.YES.getCode()){
					Map<String,String> smsPar = new HashMap<>();
					if(!Check.NuNStr(orderSaveInfo.getOrder().getUserTelCode())){
						smsPar.put(SysConst.MOBILE_NATION_CODE_KEY, orderSaveInfo.getOrder().getUserTelCode());
					}
					SendThreadPool.execute(new SendThread<>(smsSendService,orderSaveInfo.getOrder().getUserTel(),MessageTemplateCodeEnum.ORDER_CREATE_MSG.getCode(),smsPar,null));
				}
			}

			if(orderSaveInfo.getOrder().getOrderStatus() == OrderStatusEnum.WAITING_CHECK_IN.getOrderStatus()){
				//当前订单未实时订单，不需要确定
				return;
			}

			//3.给房东发送消息 您有房客xx的新订单待审核，请在30分钟内处理订单。
			Map<String,String> lanPushPar = new HashMap<>();
			lanPushPar.put("uid",orderSaveInfo.getOrder().getLandlordUid());
			lanPushPar.put("{1}",orderSaveInfo.getOrder().getUserName());
			lanPushPar.put("{2}", orderSaveInfo.getCheckTime());
			//极光推动的参数
			Map<String,String> bizParLan = new HashMap<>();
			bizParLan.put(JpushConst.MSG_SUB_TYPE_KEY, JpushConst.MSG_SUB_TYPE_VALULE_1);
			bizParLan.put(JpushConst.MSG_HAS_RESPONSE,"1");
			bizParLan.put(JpushConst.MSG_TAG_TYPE,JpushConst.MSG_TARGET_LAN);
            bizParLan.put("orderSn", orderSaveInfo.getOrder().getOrderSn());
            //获取房东的订单详情的H5的url
			bizParLan.put("url",getOrderDetailUrl(orderSaveInfo.getOrder().getOrderSn(), orderSaveInfo.getOrder().getLandlordUid()));

			Map<String,String> lanSmsPar = new HashMap<>();
			lanSmsPar.put("phone", orderSaveInfo.getOrder().getLandlordTel());
			lanSmsPar.put("{1}",orderSaveInfo.getOrder().getUserName());
			lanSmsPar.put("{2}", orderSaveInfo.getCheckTime());

			if(!Check.NuNStr(orderSaveInfo.getOrder().getLandlordTelCode())){
				lanSmsPar.put(SysConst.MOBILE_NATION_CODE_KEY,orderSaveInfo.getOrder().getLandlordTelCode());
			}
			SendThreadPool.execute(new SendThread<>(smsAndJpushSendService, MessageTemplateCodeEnum.ORDER_LANLORD_CREATE.getCode(),lanPushPar,lanSmsPar,bizParLan));
			
	//申请预定，给房客发送短信
			/*您的申请预订请求：{1}，入住时间为{2}至{3}，需房东确认是否可以接待。如果房东未及时确认，可联系房
			东进行沟通。如有疑问欢迎致电客服热线：400-7711-666，我们将竭诚为您服务！*/
			Map<String,String> tenPushPar = new HashMap<>();
			tenPushPar.put("uid", orderSaveInfo.getOrder().getUserUid());
			tenPushPar.put("{1}", ValueUtil.getStrValue(houseInfo.transName()));
			tenPushPar.put("{2}", ValueUtil.getStrValue(DateUtil.dateFormat(orderSaveInfo.getOrder().getStartTime())));
			tenPushPar.put("{3}", ValueUtil.getStrValue(DateUtil.dateFormat(orderSaveInfo.getOrder().getEndTime())));
			tenPushPar.put("{4}", "");
			//极光推动的参数
			Map<String,String> bizParTenMap = new HashMap<>();
			bizParTenMap.put(JpushConst.MSG_SUB_TYPE_KEY, JpushConst.MSG_SUB_TYPE_VALULE_2);
			bizParTenMap.put(JpushConst.MSG_HAS_RESPONSE,"1");
			bizParTenMap.put(JpushConst.MSG_TAG_TYPE,JpushConst.MSG_TARGET_TENANT);
			bizParTenMap.put(JpushConst.MSG_TITLE,JpushConst.MSG_TITLE_VALUE_2);
			bizParTenMap.put("orderSn", orderSaveInfo.getOrder().getOrderSn());
            //获取房东的订单详情的H5的url
			String url = m_house + String.format(JpushConst.USER_ORDER_DETAIL_URL,orderSaveInfo.getOrder().getOrderSn(),orderSaveInfo.getOrder().getUserUid());
			bizParLan.put("url",url);

			String applyContentJump = createOrderGetContentJump(orderSaveInfo.getOrder().getOrderSn(), UserTypeEnum.TENANT.getUserCode(),JumpOpenAppEnum.TEN_ORDER_INFO.getCode());
			Map<String,String> tenSmsPar = new HashMap<>();
			tenSmsPar.put("phone", orderSaveInfo.getOrder().getUserTel());
			tenSmsPar.put("{1}", ValueUtil.getStrValue(houseInfo.transName()));
			tenSmsPar.put("{2}", ValueUtil.getStrValue(DateUtil.dateFormat(orderSaveInfo.getOrder().getStartTime())));
			tenSmsPar.put("{3}", ValueUtil.getStrValue(DateUtil.dateFormat(orderSaveInfo.getOrder().getEndTime())));
			tenSmsPar.put("{4}", applyContentJump);
			
			if(!Check.NuNStr(orderSaveInfo.getOrder().getLandlordTelCode())){
				lanSmsPar.put(SysConst.MOBILE_NATION_CODE_KEY,orderSaveInfo.getOrder().getLandlordTelCode());
			}
			SendThreadPool.execute(new SendThread<>(smsAndJpushSendService, MessageTemplateCodeEnum.APPLY_ORDER_SMS_TO_TEN.getCode(),tenPushPar,tenSmsPar,bizParTenMap));

			//老版本并且有清洁费的情况 直接发送
			OrderSourceEnum orderSourceEnum = null;
			if (!Check.NuNObj(request.getSourceType())){
				orderSourceEnum = OrderSourceEnum.getByCode(request.getSourceType());
			}

			//当前清洁费大于0 and 不支持清洁费 and 是移动端
			if (ValueUtil.getintValue(orderSaveInfo.getOrderMoney().getCleanMoney()) > 0 &&
					!VersionCodeEnum.checkClean(ValueUtil.getintValue(request.getVersionCode()))
					&& orderSourceEnum != null && orderSourceEnum.checkMobile() ){
				//1. 给用户发短信 您的x房源xx订单还未支付，请在30分钟内完成支付。
				Map<String,String> cleanSmsPar = new HashMap<>();
				cleanSmsPar.put("phone",orderSaveInfo.getOrder().getUserTel());
				if(!Check.NuNStr(orderSaveInfo.getOrder().getUserTelCode())){
					cleanSmsPar.put(SysConst.MOBILE_NATION_CODE_KEY,orderSaveInfo.getOrder().getUserTelCode());
				}

				//2. 给用用户发推送
				Map<String,String> cleanPushPar = new HashMap<>();
				cleanPushPar.put("uid",orderSaveInfo.getOrder().getUserUid());

				Map<String,String> bizPar = new HashMap<>();
				bizPar.put(JpushConst.MSG_SUB_TYPE_KEY, JpushConst.MSG_SUB_TYPE_VALULE_2);
				bizPar.put(JpushConst.MSG_HAS_RESPONSE,"1");
				bizPar.put(JpushConst.MSG_TAG_TYPE,JpushConst.MSG_TARGET_TENANT);
				bizPar.put("orderSn", orderSaveInfo.getOrder().getOrderSn());
				//短信和推送同时发送
				SendThreadPool.execute(new SendThread<>(smsAndJpushSendService, MessageTemplateCodeEnum.ORDER_USER_CLEAN.getCode(), cleanPushPar, cleanSmsPar,bizPar));


			}


		}
	}


	/**
	 * 取消订单给房东发送短信
	 * @author afi
	 * @param info
	 * @param penaltyMoney
	 */
	public void sendMsg4Cancel(final OrderInfoVo info,int penaltyMoney){

		LogUtil.info(LOGGER,"取消订单给用户发送短信 orderSn:{}",info.getOrderSn());
		if(info.getOrderStatus() == OrderStatusEnum.WAITING_CONFIRM.getOrderStatus()){
			// 您的XX房源xx订单已被房客xx取消。
			Map<String,String> lanPushPar = new HashMap<>();
			lanPushPar.put("uid",info.getLandlordUid());
			lanPushPar.put("{1}",info.getHouseSn());
			lanPushPar.put("{2}",info.getOrderSn());
			lanPushPar.put("{3}",info.getUserName());

			//极光推动的参数
			Map<String,String> bizParLan = new HashMap<>();
			bizParLan.put(JpushConst.MSG_SUB_TYPE_KEY, JpushConst.MSG_SUB_TYPE_VALULE_1);
			bizParLan.put(JpushConst.MSG_HAS_RESPONSE,"1");
			bizParLan.put(JpushConst.MSG_TAG_TYPE,JpushConst.MSG_TARGET_LAN);
            bizParLan.put("orderSn", info.getOrderSn());
            //获取房东的订单详情的H5的url
			bizParLan.put("url",getOrderDetailUrl(info.getOrderSn(), info.getLandlordUid()));

			Map<String,String> lanSmsPar = new HashMap<>();
			lanSmsPar.put("phone", info.getLandlordTel());
			lanSmsPar.put("{1}",info.getHouseSn());
			lanSmsPar.put("{2}", info.getOrderSn());
			lanSmsPar.put("{3}", info.getUserName());

			if(!Check.NuNStr(info.getLandlordTelCode())){
				lanSmsPar.put(SysConst.MOBILE_NATION_CODE_KEY,info.getLandlordTelCode());
			}
			SendThreadPool.execute(new SendThread<>(smsAndJpushSendService, MessageTemplateCodeEnum.ORDER_LANLORD_NO_CONFIRM_CANCEL_MSG.getCode(),lanPushPar,lanSmsPar,bizParLan));

		}else{
			String msg = null;
			if(penaltyMoney > 0 ){
				msg = "，产生违约金"+ BigDecimalUtil.div(penaltyMoney,100) +"元，请注意查看您的收款账户";
			}else {
				msg = "，未产生违约金";
			}

			// 您的XX房源xx订单已被房客xx取消{，未产生违约金}。
			// 您的XX房源xx订单已被房客xx取消{，产生违约金xx元，请注意查看您的收款账户}。
			Map<String,String> lanPushPar = new HashMap<>();
			lanPushPar.put("uid",info.getLandlordUid());
			lanPushPar.put("{1}",info.getHouseSn());
			lanPushPar.put("{2}",info.getOrderSn());
			lanPushPar.put("{3}",info.getUserName());
			lanPushPar.put("{4}",msg);

			//极光推动的参数
			Map<String,String> bizParLan = new HashMap<>();
			bizParLan.put(JpushConst.MSG_SUB_TYPE_KEY, JpushConst.MSG_SUB_TYPE_VALULE_1);
			bizParLan.put(JpushConst.MSG_HAS_RESPONSE,"1");
			bizParLan.put(JpushConst.MSG_TAG_TYPE,JpushConst.MSG_TARGET_LAN);
            bizParLan.put("orderSn", info.getOrderSn());
            //获取房东的订单详情的H5的url
			bizParLan.put("url",getOrderDetailUrl(info.getOrderSn(), info.getLandlordUid()));

			Map<String,String> lanSmsPar = new HashMap<>();
			lanSmsPar.put("phone", info.getLandlordTel());
			lanSmsPar.put("{1}",info.getHouseSn());
			lanSmsPar.put("{2}", info.getOrderSn());
			lanSmsPar.put("{3}", info.getUserName());
			lanSmsPar.put("{4}", msg);
			if(!Check.NuNStr(info.getLandlordTelCode())){
				lanSmsPar.put(SysConst.MOBILE_NATION_CODE_KEY,info.getLandlordTelCode());
			}

			SendThreadPool.execute(new SendThread<>(smsAndJpushSendService, MessageTemplateCodeEnum.ORDER_LANLORD_CANCEL_MSG_V2.getCode(),lanPushPar,lanSmsPar,bizParLan));

			//刷新MQ
			Thread mqThread = new Thread(new Runnable() {
				@Override
				public void run() {

					try {						
						if (!Check.NuNObj(info) 
								&& !Check.NuNStr(info.getHouseFid()) && !Check.NuNObj(info.getRentWay())) {
							LogUtil.info(LOGGER, "订单取消成功,推送队列消息开始...");
							String pushContent = JsonEntityTransform.Object2Json(new HouseMq(info.getHouseFid(), null,
									info.getRentWay(), null, null));
							// 推送队列消息
							rabbitMqSendClient.sendQueue(queueName, pushContent);
							LogUtil.info(LOGGER, "订单取消成功,推送队列消息结束,推送内容:{}", pushContent);
						}
					} catch (Exception e) {
						LogUtil.error(LOGGER, "订单取消推送mq异常，orderSn={},e={}",info.getOrderSn(),e);
					}

				}
			});
			mqThread.start();
		}

	}



	/**
	 * 协商取消订单给房东发送短信
	 * @author lishaochuan
	 * @create 2017/1/6 11:29
	 * @param 
	 * @return 
	 */
	public void sendMsg4NegotiateCancelLandlord(OrderInfoVo info,int penaltyMoney){
		LogUtil.info(LOGGER,"协商取消订单给房东发送短信 orderSn:{}",info.getOrderSn());

		// 你有订单已协商取消。 通过协商， 您的{1}房源{2}订单已取消， 该笔订单将付给您{3}元违约金。
		Map<String,String> lanSmsPar = new HashMap<>();
		lanSmsPar.put("{1}",info.getHouseSn());
		lanSmsPar.put("{2}", info.getOrderSn());
		lanSmsPar.put("{3}", penaltyMoney + "");
		if(!Check.NuNStr(info.getLandlordTelCode())){
			lanSmsPar.put(SysConst.MOBILE_NATION_CODE_KEY, info.getLandlordTelCode());
		}
		SendThreadPool.execute(new SendThread<>(smsSendService, info.getLandlordTel(), MessageTemplateCodeEnum.ORDER_LANLORD_NEGOTIATE.getCode(),lanSmsPar, null));
	}

	/**
	 * 协商取消订单给房客发送短信
	 * @author lishaochuan
	 * @create 2017/1/6 14:33
	 * @param
	 * @return
	 */
	public void sendMsg4NegotiateCancelUser(OrderInfoVo info,int penaltyMoney){
		LogUtil.info(LOGGER,"协商取消订单给房客发送短信 orderSn:{}",info.getOrderSn());

		// 你有订单已协商取消。 通过协商， 您预订的{1}房源{2}订单已取消， 该笔订单您将支付{3}元违约金。
		Map<String,String> userSmsPar = new HashMap<>();
		userSmsPar.put("{1}", info.getHouseSn());
		userSmsPar.put("{2}", info.getOrderSn());
		userSmsPar.put("{3}", penaltyMoney + "");
		if(!Check.NuNStr(info.getUserTelCode())){
			userSmsPar.put(SysConst.MOBILE_NATION_CODE_KEY, info.getUserTelCode());
		}
		SendThreadPool.execute(new SendThread<>(smsSendService, info.getUserTel(), MessageTemplateCodeEnum.ORDER_USER_NEGOTIATE.getCode(),userSmsPar, null));
	}

	/**
	 * 房东申请取消订单
	 * 亲爱的房东您好，您对将于{1}入住的房源{2}订单{3}的取消申请已于{4}取消成功，我们将依据房东违约规则对您此次的取消进行处罚，有任何疑问请咨询客服：4007711666。
	 * @Author jixd
	 * @created 2017年05月24日 11:51:00
	 * @param info
	 */
	public void sendMsg4LandApplyCancelToLan(OrderInfoVo info){
		LogUtil.info(LOGGER,"房东申请取消订单给房东发送短信 orderSn:{}",info.getOrderSn());

		String houseName = info.getHouseName();
		if (info.getRentWay() == RentWayEnum.ROOM.getCode()){
			houseName = info.getRoomName();
		}
		String time = DateUtil.dateFormat(info.getStartTime(),"yyyy-MM-dd") + " " + info.getCheckInTime();
		Map<String,String> lanSmsPar = new HashMap<>();
		lanSmsPar.put("{1}",time);
		lanSmsPar.put("{2}", houseName);
		lanSmsPar.put("{3}", info.getOrderSn());
		lanSmsPar.put("{4}", DateUtil.dateFormat(new Date(),"yyyy-MM-dd HH:mm:ss"));
		if(!Check.NuNStr(info.getLandlordTelCode())){
			lanSmsPar.put(SysConst.MOBILE_NATION_CODE_KEY, info.getLandlordTelCode());
		}
		SendThreadPool.execute(new SendThread<>(smsSendService, info.getLandlordTel(), MessageTemplateCodeEnum.ORDER_LANLORD_APPLY_CANCEL_TO_LAN.getCode(),lanSmsPar, null));
	}
	/**
	 * 您好，您约定的房源{1}，由于房东原因已被取消，我们会对房东的这次取消行为进行酌情惩罚，对于房东取消对您造成的困扰，我们深表抱歉，平台将赠送您50元无门槛优惠券，可在房客端优惠券中查询。
	 * @author jixd
	 * @created 2017年05月24日 11:51:00
	 * @param
	 * @return
	 */
	public void sendMsg4LandApplyCancelToTen(OrderInfoVo info){
		LogUtil.info(LOGGER,"房东申请取消订单给房客发送短信 orderSn:{}",info.getOrderSn());
		String houseName = info.getHouseName();
		if (info.getRentWay() == RentWayEnum.ROOM.getCode()){
			houseName = info.getRoomName();
		}
		Map<String,String> lanSmsPar = new HashMap<>();
		lanSmsPar.put("{1}",houseName);
		if(!Check.NuNStr(info.getUserTelCode())){
			lanSmsPar.put(SysConst.MOBILE_NATION_CODE_KEY, info.getUserTelCode());
		}
		SendThreadPool.execute(new SendThread<>(smsSendService, info.getUserTel(), MessageTemplateCodeEnum.ORDER_LANLORD_APPLY_CANCEL_TO_TEN.getCode(),lanSmsPar, null));
	}





	/**
	 * 房客退房后 给房东发送短信和推送
	 * @author afi
	 * @param info
	 */
	public void sendMsg4CanMarkLand(OrderInfoVo info){

		LogUtil.info(LOGGER,"用户确认额外消费发送短信 orderSn:{}",info.getOrderSn());
		String contentJump = getContentJump(info.getOrderSn(), UserTypeEnum.LANDLORD.getUserCode());
		//{1}已经退房，为您的房客写一条真诚的评价，还可以提升您的搜索排名哦！{2}
		//1.给房东发短信
		Map<String,String> smsPar = new HashMap<>();
		smsPar.put("phone", info.getLandlordTel());
		smsPar.put("{1}",info.getUserName());
		smsPar.put("{2}",contentJump);
		if(!Check.NuNStr(info.getLandlordTelCode())){
			smsPar.put(SysConst.MOBILE_NATION_CODE_KEY,info.getLandlordTelCode());
		}

		//2. 给房东发推送
		Map<String,String> pushPar = new HashMap<>();
		pushPar.put("uid",info.getLandlordUid());
		pushPar.put("{1}",info.getUserName());
		pushPar.put("{2}","");

		//短信和推送同时发送
		Map<String,String> bizPar = new HashMap<>();
		bizPar.put(JpushConst.MSG_SUB_TYPE_KEY, JpushConst.MSG_SUB_TYPE_VALUE_7);
		String isAdd = ZkUtil.getZkSysValue(EnumMinsuConfig.minsu_isAddUrl.getType(), EnumMinsuConfig.minsu_isAddUrl.getCode());
		if ("1".equals(isAdd)){
			//不可点击
			bizPar.put(JpushConst.MSG_HAS_RESPONSE, "0");
		}else{
			bizPar.put(JpushConst.MSG_HAS_RESPONSE,"1");
		}
		bizPar.put(JpushConst.MSG_TAG_TYPE,JpushConst.MSG_TARGET_LAN);
		bizPar.put("orderSn", info.getOrderSn());
		bizPar.put("userType", String.valueOf(UserTypeEnum.LANDLORD.getUserCode()));

		SendThreadPool.execute(new SendThread<>(smsAndJpushSendService, MessageTemplateCodeEnum.ORDER_LANLORD_OTHER_AGREE.getCode(), pushPar, smsPar,bizPar));
	}
	/**
	 * 保存额外消费发送短信
	 * @author afi
	 * @param info
	 * @param otherMoney
	 */
	public void sendMsg4SaveOtherMoney(OrderInfoVo info,Integer otherMoney){
		if(!Check.NuNObj(otherMoney) && otherMoney > 0){

			LogUtil.info(LOGGER,"保存额外消费发送短信 orderSn:{}",info.getOrderSn());
			//第一种情况： 当前的金额大于0
			//房东xx已确认您的订单，您是否同意其他消费金额，如有异议请及时与客服咨询。
			//1.给用户发短信
			Map<String,String> smsPar = new HashMap<>();
			smsPar.put("phone", info.getUserTel());
			smsPar.put("{1}",info.getLandlordName());
			if(!Check.NuNStr(info.getUserTelCode())){
				smsPar.put(SysConst.MOBILE_NATION_CODE_KEY,info.getUserTelCode());
			}


			//2. 给用用户发推送
			Map<String,String> pushPar = new HashMap<>();
			pushPar.put("uid",info.getUserUid());
			pushPar.put("{1}",info.getLandlordName());

			Map<String,String> bizPar = new HashMap<>();
			bizPar.put(JpushConst.MSG_SUB_TYPE_KEY, JpushConst.MSG_SUB_TYPE_VALULE_2);
			bizPar.put(JpushConst.MSG_HAS_RESPONSE,"1");
			bizPar.put(JpushConst.MSG_TAG_TYPE,JpushConst.MSG_TARGET_TENANT);
			bizPar.put("orderSn", info.getOrderSn());

			//短信和推送同时发送
			SendThreadPool.execute(new SendThread<>(smsAndJpushSendService, MessageTemplateCodeEnum.ORDER_LANLORD_OTHER_YES.getCode(), pushPar, smsPar,bizPar));
		}
	}


	/**
	 * 退房发送短信 （提示其他额外消费   和 提示房东去评价）
	 * @author afi
	 * @param info
	 */
	public void sendMsg4CheckOut(OrderInfoVo info,String msg){

		LogUtil.info(LOGGER,"退房发送短信 orderSn:{}",info.getOrderSn());
		String msgFull = info.getUserName() + msg;
		//房客xx已退房，请确认是否有其他消费。
		//1, 给房东发短信
		Map<String,String> smsPar = new HashMap<>();
		smsPar.put("phone",info.getLandlordTel());
		smsPar.put("{1}",msgFull);
		if(!Check.NuNStr(info.getLandlordTelCode())){
			smsPar.put(SysConst.MOBILE_NATION_CODE_KEY,info.getLandlordTelCode());
		}
		//2. 给房东发推送
		Map<String,String> pushPar = new HashMap<>();
		pushPar.put("uid",info.getLandlordUid());
		pushPar.put("{1}",msgFull);
		//短信和推送同时发送
		Map<String,String> bizPar = new HashMap<>();
		bizPar.put(JpushConst.MSG_SUB_TYPE_KEY, JpushConst.MSG_SUB_TYPE_VALULE_1);
		bizPar.put(JpushConst.MSG_HAS_RESPONSE,"1");
		bizPar.put(JpushConst.MSG_TAG_TYPE,JpushConst.MSG_TARGET_LAN);
        bizPar.put("orderSn", info.getOrderSn());
        //获取房东的订单详情的H5的url
		bizPar.put("url",getOrderDetailUrl(info.getOrderSn(), info.getLandlordUid()));
		SendThreadPool.execute(new SendThread<>(smsAndJpushSendService, MessageTemplateCodeEnum.ORDER_LANLORD_CHECK_OUT.getCode(), pushPar, smsPar,bizPar));

		//给房东发送短息和消息提示写评价
		this.sendMsg4CanMarkLand(info);
		//给房东发送短信和消息提示写评价
		//1, 给房客发短信
		Map<String,String> smsParTen = new HashMap<>();
		smsParTen.put("phone",info.getUserTel());
		smsParTen.put("{1}",info.getCityName());
		smsParTen.put("{2}",this.getContentJump(info.getOrderSn(), UserTypeEnum.TENANT.getUserCode()));
		if(!Check.NuNStr(info.getUserTelCode())){
			smsParTen.put(SysConst.MOBILE_NATION_CODE_KEY,info.getUserTelCode());
		}

		//2. 给房客发推送
		Map<String,String> pushParTen = new HashMap<>();
		pushPar.put("uid",info.getUserUid());
		pushParTen.put("{1}",info.getCityName());
		pushParTen.put("{2}","");

		Map<String,String> bizParTen = new HashMap<>();
		bizParTen.put(JpushConst.MSG_SUB_TYPE_KEY, JpushConst.MSG_SUB_TYPE_VALULE_1);
		bizParTen.put(JpushConst.MSG_HAS_RESPONSE,"1");
		bizParTen.put(JpushConst.MSG_TAG_TYPE,JpushConst.MSG_TARGET_TENANT);
		bizParTen.put("url",this.getContentJump(info.getOrderSn(), UserTypeEnum.TENANT.getUserCode()));
		SendThreadPool.execute(new SendThread<>(smsAndJpushSendService, MessageTemplateCodeEnum.ORDER_TENANT_CHECK_OUT.getCode(), pushParTen, smsParTen,bizParTen));
	}


	/**
	 * 定时任务给用户结算退款（成功）时发送消息和短信
	 * @author lishaochuan
	 * @create 2016年5月13日下午2:21:06
	 * @param refundFee
	 * @param uid
	 * @param mobile
	 */
	public void sendMsg4UserSettlement(String refundFee, String uid, String mobile,String mobileNationCode) {
		//您的退款{1}元已到您的账户空间，请注意查收。
		LogUtil.info(LOGGER,"定时任务给用户结算退款（成功）时发送消息和短信 uid:{}", uid);
		// 1. 给用户发短信
		Map<String, String> smsPar = new HashMap<>();
		smsPar.put("phone", mobile);
		smsPar.put("{1}", refundFee);
		if(!Check.NuNStr(mobileNationCode)){
			smsPar.put(SysConst.MOBILE_NATION_CODE_KEY,mobileNationCode);
		}


		// 2. 给用户发推送
		Map<String, String> pushPar = new HashMap<>();
		pushPar.put("uid", uid);
		pushPar.put("{1}", refundFee);

		Map<String,String> bizPar = new HashMap<>();
		bizPar.put(JpushConst.MSG_SUB_TYPE_KEY, JpushConst.MSG_SUB_TYPE_VALULE_4);
		bizPar.put(JpushConst.MSG_HAS_RESPONSE,"0");
		bizPar.put(JpushConst.MSG_TAG_TYPE,JpushConst.MSG_TARGET_TENANT);

		// 短信和推送同时发送
		SendThreadPool.execute(new SendThread<>(smsAndJpushSendService, MessageTemplateCodeEnum.REFUND_CODE.getCode(), pushPar, smsPar,bizPar));
	}

	/**
	 * 定时任务给用户结算退款（失败）时发送短信
	 * @author lishaochuan
	 * @create 2016年5月16日下午6:17:51
	 * @param mobile
	 */
	public void sendMsg4UserSettlementFail(String mobile,String mobileNationCode) {
		LogUtil.info(LOGGER,"定时任务给用户结算退款（失败）时发送短信, mobile:{}", mobile);
		//您的退款操作失败，请联系客服进行处理，给您带来的不便我们深表歉意！400-7711-666
		Map<String, String> smsPar = new HashMap<>();
		if(!Check.NuNStr(mobileNationCode)){
			smsPar.put(SysConst.MOBILE_NATION_CODE_KEY, mobileNationCode);
		}
		SendThreadPool.execute(new SendThread<>(smsSendService, mobile, MessageTemplateCodeEnum.ORDER_USER_REFUND_FAIL.getCode(), smsPar,null));
	}


	/**
	 * 订单支付成功
	 * @author liyingjie
	 * @param orderInfoVo
	 */
	public void sendMsg4PaySuccess(OrderInfoVo orderInfoVo){
		try {
			LogUtil.info(LOGGER,"订单支付成功 orderSn:{} ",orderInfoVo.getOrderSn());

			String houseName = orderInfoVo.transName();
			Integer roomType = RoomTypeEnum.ROOM_TYPE.getCode();
			if (orderInfoVo.getRentWay() == RentWayEnum.ROOM.getCode()){
				houseName = orderInfoVo.getRoomName();
				
				//区分是分租还是共享客厅
				List<String> roomSns = new ArrayList<String>();
				roomSns.add(orderInfoVo.getHouseSn());
				LogUtil.info(LOGGER, "sendMsg4PaySuccess方法 ==>调用getRoomListByRoomSns方法  参数={}", JsonEntityTransform.Object2Json(roomSns));
				String roomListJson = houseManageService.getRoomListByRoomSns(JsonEntityTransform.Object2Json(roomSns));
				List<HouseRoomMsgEntity> roomList = SOAResParseUtil.getListValueFromDataByKey(roomListJson, "roomList", HouseRoomMsgEntity.class);
				if(!Check.NuNCollection(roomList)){
					HouseRoomMsgEntity houseRoomMsg = roomList.get(0);
					if(!Check.NuNObj(houseRoomMsg)){
						 roomType = houseRoomMsg.getRoomType();
					}
				}
				LogUtil.info(LOGGER, "sendMsg4PaySuccess方法 ==>调用getRoomListByRoomSns方法  结果={},roomType={}", roomListJson,roomType);
			}

			
			//1. 给用户发短信 订单支付成功
			Map<String,String> smsPar = new HashMap<String,String>(6);
			smsPar.put("phone",orderInfoVo.getUserTel());
			smsPar.put("{1}",houseName);
			smsPar.put("{2}",orderInfoVo.getOrderSn());
			smsPar.put("{3}",DateUtil.dateFormat(orderInfoVo.getStartTime()));
			smsPar.put("{4}",DateUtil.dateFormat(orderInfoVo.getEndTime()));
			smsPar.put("{5}",orderInfoVo.getHouseAddr());
			if(!Check.NuNStr(orderInfoVo.getUserTelCode())){
				smsPar.put(SysConst.MOBILE_NATION_CODE_KEY,orderInfoVo.getUserTelCode());
			}
			//2. 给用用户发推送  订单支付成功
			Map<String,String> pushPar = new HashMap<String,String>(6);
			pushPar.put("uid",orderInfoVo.getUserUid());
			pushPar.put("{1}",houseName);
			pushPar.put("{2}",orderInfoVo.getOrderSn());
			pushPar.put("{3}",DateUtil.dateFormat(orderInfoVo.getStartTime()));
			pushPar.put("{4}",DateUtil.dateFormat(orderInfoVo.getEndTime()));
			pushPar.put("{5}",orderInfoVo.getHouseAddr());
			//短信和推送同时发送
			//SendThreadPool.execute(new SendThread<>(smsAndJpushSendService, MessageTemplateCodeEnum.ORDER_USER_HAS_PAY.getCode(), pushPar, smsPar));
			//只给用户发送短信
			if(roomType==RoomTypeEnum.HALL_TYPE.getCode()){
				SendThreadPool.execute(new SendThread<>(smsSendService, orderInfoVo.getUserTel(), MessageTemplateCodeEnum.SHARE_HALLL_ORDER_USER_HAS_PAY.getCode(), smsPar, null));
			}else{
				SendThreadPool.execute(new SendThread<>(smsSendService, orderInfoVo.getUserTel(), MessageTemplateCodeEnum.ORDER_USER_HAS_PAY.getCode(), smsPar, null));
			}

			//3.给房东发送消息 您有已支付成功的订单。
			Map<String,String> lanPushPar = new HashMap<String,String>();
			lanPushPar.put("uid",orderInfoVo.getLandlordUid());
			lanPushPar.put("{1}", orderInfoVo.getUserName());
			lanPushPar.put("{2}", orderInfoVo.getHouseSn());
			lanPushPar.put("{3}", orderInfoVo.getOrderSn());
			lanPushPar.put("{4}", DateUtil.dateFormat(orderInfoVo.getStartTime()));
			lanPushPar.put("{5}", DateUtil.dateFormat(orderInfoVo.getEndTime()));

			Map<String,String> lanSmsPar = new HashMap<String,String>();
			lanSmsPar.put("phone",orderInfoVo.getLandlordTel());
			lanSmsPar.put("{1}", orderInfoVo.getUserName());
			lanSmsPar.put("{2}", orderInfoVo.getHouseSn());
			lanSmsPar.put("{3}", orderInfoVo.getOrderSn());
			lanSmsPar.put("{4}", DateUtil.dateFormat(orderInfoVo.getStartTime()));
			lanSmsPar.put("{5}", DateUtil.dateFormat(orderInfoVo.getEndTime()));
			if(!Check.NuNStr(orderInfoVo.getLandlordTelCode())){
				lanSmsPar.put(SysConst.MOBILE_NATION_CODE_KEY,orderInfoVo.getLandlordTelCode());
			}

			int lanSmsType  = MessageTemplateCodeEnum.ORDER_LANLORD_HAS_PAY_V2.getCode();
			if(orderInfoVo.isLanTonightDiscount()){
				lanSmsType = MessageTemplateCodeEnum.ORDER_LANLORD_HAS_PAY_TONIGHT_V2.getCode();
			}
			//极光推动的参数
			Map<String,String> bizPar = new HashMap<String,String>();
			bizPar.put("url",getOrderDetailUrl(orderInfoVo.getOrderSn(), orderInfoVo.getLandlordUid()));
			bizPar.put(JpushConst.MSG_SUB_TYPE_KEY, JpushConst.MSG_SUB_TYPE_VALULE_1);
			bizPar.put(JpushConst.MSG_HAS_RESPONSE,"1");
			bizPar.put(JpushConst.MSG_TAG_TYPE,JpushConst.MSG_TARGET_LAN);
            bizPar.put(JpushConst.MSG_TAG_TYPE, JpushConst.MSG_TARGET_LAN);
            bizPar.put("orderSn", orderInfoVo.getOrderSn());
            //给房东同时发送短信和push
			SendThreadPool.execute(new SendThread<>(smsAndJpushSendService, lanSmsType, lanPushPar, lanSmsPar,bizPar));
		}catch (Exception e){
			LogUtil.info(LOGGER, "【支付回调】发送信息失败", orderInfoVo.getOrderSn());
		}
	}

	/**
	 * 打款 成功 消息
	 * @author liyingjie
	 * @param paramMap
	 */
	public void sendMsg4RefundSuccess(Map<String, String> paramMap){
		LogUtil.info(LOGGER,"开始发送信息 ");
		if(Check.NuNMap(paramMap)){
			return;
		}
		int type = Integer.valueOf(paramMap.get("type"));
		//房客 退款到账
		if(type == ReceiveTypeEnum.TENANT.getCode()){
			Map<String,String> smsPar = new HashMap<>();
			smsPar.put("phone",paramMap.get("phone"));
			smsPar.put("{1}",paramMap.get("totalFee"));
			if(!Check.NuNStr(paramMap.get("userTelCode"))){
				smsPar.put(SysConst.MOBILE_NATION_CODE_KEY,paramMap.get("userTelCode"));
			}


			Map<String,String> pushPar = new HashMap<>();
			pushPar.put("uid",paramMap.get("uid"));
			pushPar.put("{1}",paramMap.get("totalFee"));

			//短信和推送同时发送
			Map<String,String> bizPar = new HashMap<>();
			bizPar.put(JpushConst.MSG_SUB_TYPE_KEY, JpushConst.MSG_SUB_TYPE_VALULE_4);
			bizPar.put(JpushConst.MSG_HAS_RESPONSE,"0");
			bizPar.put(JpushConst.MSG_TAG_TYPE,JpushConst.MSG_TARGET_TENANT);
			SendThreadPool.execute(new SendThread<>(smsAndJpushSendService, MessageTemplateCodeEnum.ORDER_USER_REFUND_SUSSESS.getCode(), pushPar, smsPar,bizPar));
		}else if(type == ReceiveTypeEnum.LANDLORD.getCode()){
			//房东 收款到账
			Map<String,String> smsPar = new HashMap<>();
			smsPar.put("phone",paramMap.get("phone"));
			smsPar.put("{1}",paramMap.get("orderSn"));
			smsPar.put("{2}",paramMap.get("houseSn"));
			smsPar.put("{3}",paramMap.get("totalFee"));
			if(!Check.NuNStr(paramMap.get("landlordTelCode"))){
				smsPar.put(SysConst.MOBILE_NATION_CODE_KEY,paramMap.get("landlordTelCode"));
			}

			Map<String,String> pushPar = new HashMap<>();
			pushPar.put("uid",paramMap.get("uid"));
			pushPar.put("{1}",paramMap.get("orderSn"));
			pushPar.put("{2}",paramMap.get("houseSn"));
			pushPar.put("{3}",paramMap.get("totalFee"));

			//短信和推送同时发送
			Map<String,String> bizPar = new HashMap<>();
			bizPar.put(JpushConst.MSG_SUB_TYPE_KEY, JpushConst.MSG_SUB_TYPE_VALULE_4);
			bizPar.put(JpushConst.MSG_HAS_RESPONSE,"0");
			bizPar.put(JpushConst.MSG_TAG_TYPE,JpushConst.MSG_TARGET_LAN);
			SendThreadPool.execute(new SendThread<>(smsAndJpushSendService, MessageTemplateCodeEnum.ORDER_LANLORD_REFUND_SUSSESS_V2.getCode(),pushPar,smsPar,bizPar));

		}
	}


	/**
	 * 打款 失败 消息
	 * @author liyingjie
	 * @param paramMap
	 */
	public void sendMsg4RefundFail(Map<String, String> paramMap){
		LogUtil.info(LOGGER,"打款 失败 消息 par:{} ", JsonEntityTransform.Object2Json(paramMap));
		if(Check.NuNMap(paramMap)){
			return;
		}

		int type = Integer.valueOf(paramMap.get("type"));
		Map<String,String> smsPar = new HashMap<>();
		//房客 退款到账
		if(type == ReceiveTypeEnum.TENANT.getCode()){

			if(!Check.NuNStr(paramMap.get("userTelCode"))){
				smsPar.put(SysConst.MOBILE_NATION_CODE_KEY,paramMap.get("userTelCode"));
			}
			//1. 给用户发短信 退款失败
			SendThreadPool.execute(new SendThread<>(smsSendService, paramMap.get("phone"), MessageTemplateCodeEnum.ORDER_USER_REFUND_FAIL.getCode(), smsPar,null));
			//推送
			//SendThreadPool.execute(new SendThread<>(jpushSendService, paramMap.get("uid"),MessageTemplateCodeEnum.ORDER_USER_REFUND_FAIL.getCode(),null));
		}else if(type == ReceiveTypeEnum.LANDLORD.getCode()){
			if(!Check.NuNStr(paramMap.get("landlordTelCode"))){
				smsPar.put(SysConst.MOBILE_NATION_CODE_KEY,paramMap.get("landlordTelCode"));
			}
			//发送短信 
			SendThreadPool.execute(new SendThread<>(smsSendService,paramMap.get("phone"), MessageTemplateCodeEnum.ORDER_LANLORD_REFUND_FAIL.getCode(),smsPar,null));
			//推送
			// SendThreadPool.execute(new SendThread<>(jpushSendService, paramMap.get("uid"),MessageTemplateCodeEnum.ORDER_LANLORD_REFUND_FAIL.getCode(),null));
		}
	}


	/**
	 * 房东拒绝订单
	 * @author afi
	 * @param orderInfoVo
	 * @param dto
	 */
	public void sendMsg4OrderRefused2User(OrderInfoVo orderInfoVo,DataTransferObject dto){
		if(Check.NuNObj(orderInfoVo) || Check.NuNObj(dto)){
			return;
		}
		if (dto.getCode() != DataTransferObject.SUCCESS){
			return;
		}
		LogUtil.info(LOGGER,"房东拒绝订单给房客发送消息 orderSn:{} ", orderInfoVo.getOrderSn());

		// 您发出的申请预订请求，XXX,入住时间为X月X日-X月X日，房东因为入住时间冲突或其他原因，无法接受您的预订。您可以搜索更多房源。

		//1. 给用户发短信 房东拒绝了当前订单
		Map<String,String> smsPar = new HashMap<>(3);
		smsPar.put("phone",orderInfoVo.getUserTel());
		smsPar.put("{1}",orderInfoVo.transName());
		smsPar.put("{2}",DateUtil.dateFormat(orderInfoVo.getStartTime()));
		smsPar.put("{3}",DateUtil.dateFormat(orderInfoVo.getEndTime()));
		if(!Check.NuNStr(orderInfoVo.getUserTelCode())){
			smsPar.put(SysConst.MOBILE_NATION_CODE_KEY,orderInfoVo.getUserTelCode());
		}

		//2. 给用用户发推送  房东拒绝了当前订单
		Map<String,String> pushPar = new HashMap<>(3);
		pushPar.put("uid",orderInfoVo.getUserUid());
		pushPar.put("{1}",orderInfoVo.transName());
		pushPar.put("{2}",DateUtil.dateFormat(orderInfoVo.getStartTime()));
		pushPar.put("{3}",DateUtil.dateFormat(orderInfoVo.getEndTime()));

		Map<String,String> bizPar = new HashMap<>();
		bizPar.put(JpushConst.MSG_SUB_TYPE_KEY, JpushConst.MSG_SUB_TYPE_VALULE_2);
		bizPar.put(JpushConst.MSG_HAS_RESPONSE,"1");
		bizPar.put(JpushConst.MSG_TAG_TYPE,JpushConst.MSG_TARGET_TENANT);
		bizPar.put("orderSn", orderInfoVo.getOrderSn());

		SendThreadPool.execute(new SendThread<>(smsAndJpushSendService, MessageTemplateCodeEnum.ORDER_REFUSED.getCode(), pushPar, smsPar,bizPar));
	}


	/**
	 * 订单被取消
	 * @author liyingjie
	 * @param orderInfoVo
	 * @param message
	 */
	public void sendMsg4OrderCanceled(OrderInfoVo orderInfoVo,String message){
		LogUtil.info(LOGGER,"订单被取消 par:{} ", JsonEntityTransform.Object2Json(orderInfoVo));
		if(Check.NuNObj(orderInfoVo) || Check.NuNStr(message)){
			return;
		}

		//1. 给用户发短信 订单被取消
		Map<String,String> smsPar = new HashMap<>(3);
		smsPar.put("phone",orderInfoVo.getUserTel());
		smsPar.put("{1}",orderInfoVo.transName());
		smsPar.put("{2}",message);
		if(!Check.NuNStr(orderInfoVo.getUserTelCode())){
			smsPar.put(SysConst.MOBILE_NATION_CODE_KEY,orderInfoVo.getUserTelCode());
		}

		//2. 给用用户发推送  订单被取消
		Map<String,String> pushPar = new HashMap<>(3);
		pushPar.put("uid",orderInfoVo.getUserUid());
		pushPar.put("{1}",orderInfoVo.transName());
		pushPar.put("{2}",message);

		//短信和推送同时发送
		Map<String,String> bizPar = new HashMap<>();
		bizPar.put(JpushConst.MSG_SUB_TYPE_KEY, JpushConst.MSG_SUB_TYPE_VALULE_1);
		bizPar.put(JpushConst.MSG_HAS_RESPONSE,"1");
		bizPar.put(JpushConst.MSG_TAG_TYPE,JpushConst.MSG_TARGET_TENANT);
        //bizPar.put("url", getOrderDetailUrl(orderInfoVo.getOrderSn(),orderInfoVo.getLandlordUid()));
        bizPar.put("orderSn", orderInfoVo.getOrderSn());
        SendThreadPool.execute(new SendThread<>(smsAndJpushSendService, MessageTemplateCodeEnum.ORDER_USER_PAY_FAIL.getCode(), pushPar, smsPar,bizPar));
	}


	/**
	 *
	 * @param orderSn
	 * @param money
	 */
	public void sendMsg4PayBackButCanceled4Dev(String orderSn, Integer money,Integer couponMoney) {
		LogUtil.info(LOGGER, "当前订单支付回调，但是订单已经取消");
		Map<String, String> smsPar = new HashMap<>();
		smsPar.put("{1}", orderSn);
		smsPar.put("{2}", ValueUtil.getStrValue(BigDecimalUtil.div(money,100)));
		smsPar.put("{3}", ValueUtil.getStrValue(BigDecimalUtil.div(couponMoney,100)));
		// 发送短信
		String mobiles = ZkUtil.getZkSysValue(EnumMinsuConfig.minsu_mobileList.getType(), EnumMinsuConfig.minsu_mobileList.getCode());
		SendThreadPool.execute(new SendThread<>(smsSendService, mobiles, MessageTemplateCodeEnum.ORDER_PAY_BACK_CANCELED.getCode(), smsPar, null));
	}


	/**
	 * 返现单金额大于订单金额，给开发人员发短信
	 * @author lishaochuan
	 * @create 2016年9月13日上午9:34:10
	 * @param cashbackSn
	 * @param orderSn
	 * @param cashbackFee
	 * @param orderFee
	 */
	public void sendMsg4CashbackHighFee(String cashbackSn, String orderSn, Integer cashbackFee, Integer orderFee) {
		LogUtil.info(LOGGER, "返现单金额大于订单金额，给开发人员发短信");
		Map<String, String> smsPar = new HashMap<>();
		smsPar.put("{1}", cashbackSn);
		smsPar.put("{2}", orderSn);
		smsPar.put("{3}", DataFormat.formatHundredPrice(cashbackFee) + "元");
		smsPar.put("{4}", DataFormat.formatHundredPrice(orderFee) + "元");

		// 发送短信
		String mobiles = ZkUtil.getZkSysValue(EnumMinsuConfig.minsu_devAndOptPhoneList.getType(), EnumMinsuConfig.minsu_devAndOptPhoneList.getCode());
		SendThreadPool.execute(new SendThread<>(smsSendService, mobiles, MessageTemplateCodeEnum.ORDER_CASHBACK_HIGH_FEE.getCode(), smsPar, null));
	}




	/**
	 * 返现单生成多次，给开发人员发短信
	 * @author lishaochuan
	 * @create 2016年9月13日上午9:38:55
	 * @param times
	 * @param cashbackSn
	 * @param orderSn
	 */
	public void sendMsg4CashbackManyTimes(long times, String cashbackSn, String orderSn) {
		LogUtil.info(LOGGER, "返现单生成多次，给开发人员发短信");
		Map<String, String> smsPar = new HashMap<>();
		smsPar.put("{1}", times+"");
		smsPar.put("{2}", cashbackSn);
		smsPar.put("{3}", orderSn);

		// 发送短信
		String mobiles = ZkUtil.getZkSysValue(EnumMinsuConfig.minsu_mobileList.getType(), EnumMinsuConfig.minsu_mobileList.getCode());
		SendThreadPool.execute(new SendThread<>(smsSendService, mobiles, MessageTemplateCodeEnum.ORDER_CASHBACK_HIGH_TIMES.getCode(), smsPar, null));
	}


	/**
	 * 不同支付单号重复回调，给开发人员发短信
	 * @param orderSn
	 * @param nowTradeNo
	 */
	public void sendMsg4RepeatPayCallBack(String orderSn, String nowTradeNo) {
		LogUtil.info(LOGGER, "重复支付回调，给开发人员发短信");
		Map<String, String> smsPar = new HashMap<>();
		smsPar.put("{1}", orderSn);
		smsPar.put("{2}", nowTradeNo);

		// 发送短信
		String mobiles = ZkUtil.getZkSysValue(EnumMinsuConfig.minsu_devAndOptPhoneList.getType(), EnumMinsuConfig.minsu_devAndOptPhoneList.getCode());
		SendThreadPool.execute(new SendThread<>(smsSendService, mobiles, MessageTemplateCodeEnum.ORDER_PAY_BACK_REPEATE.getCode(), smsPar, null));
	}


	/**
	 * 房客退房日当天给房客发短信
	 * @author lisc
	 * @param mobile
	 * @param userName
	 */
	public void sendMsg4CheckOutRemind(String mobile, String userName,String mobileNationCode) {
		LogUtil.info(LOGGER,"房客退房日当天给房客发短信,mobile:{}",mobile);
		Map<String, String> smsPar = new HashMap<>();
		smsPar.put("phone", mobile);
		smsPar.put("{1}", userName);
		if(!Check.NuNStr(mobileNationCode)){
			smsPar.put(SysConst.MOBILE_NATION_CODE_KEY, mobileNationCode);
		}
		SendThreadPool.execute(new SendThread<>(smsSendService, mobile, MessageTemplateCodeEnum.ORDER_CHECK_OUT_MSG.getCode(), smsPar, null));
	}
	
	/**
	 * 
	 * 打款成功(房费，清洁费，返现) ，给房东发送短信
	 *
	 * @author loushuai
	 * @created 2017年8月1日 下午2:52:25
	 *
	 * @param orderSn
	 * @param paySourceType
	 * @param totalFee
	 * @param accountTypeName
	 */
	public void sendMsgToLanForPayVouchers(String orderSn, Integer paySourceType, Integer totalFee, String accountTypeName,String pvSn){
		LogUtil.info(LOGGER, "sendMsgToLanForPayVouchers方法，给房东发短信  order={},paySourceType={},totalFee={},accountTypeName={},pvSn={}", orderSn,paySourceType,totalFee,accountTypeName,pvSn);
		if(Check.NuNStr(orderSn) || Check.NuNObj(paySourceType) || Check.NuNObj(totalFee) || Check.NuNStr(accountTypeName)){
			return; 
		}
		//根据paySourceType获取打款类型名称
		String paySourceTypeName = "";
		if(PaySourceTypeEnum.CLEAN.getCode() == paySourceType || PaySourceTypeEnum.CASHBACK.getCode() == paySourceType){
			paySourceTypeName = PaySourceTypeEnum.getPaySourceTypeName(paySourceType);
		}else{
			paySourceTypeName = "房费";
		}
		if(Check.NuNStr(paySourceTypeName)){
			return;
		}
		
		//根据orderSn获取房源名称(整租是房源名称，分租是房间名称)
		OrderInfoVo orderInfo = orderCommonServiceImpl.getOrderInfoByOrderSn(orderSn);
		LogUtil.info(LOGGER, "sendMsgToLanForPayVouchers方法，orderInfo={}",JsonEntityTransform.Object2Json(orderInfo));
		
		//判断orderInfo及电话是否为空
		String mobile = orderInfo.getLandlordTel();
		//String mobile = "18201693996";
		if(Check.NuNObj(orderInfo)  || Check.NuNObj(mobile)){
			return; 
		}
		String showTotolFee = DecimalCalculate.div(totalFee.toString(), "100", 2).toString();
		//根据不同打款类型，发送不同短信
		if(PaySourceTypeEnum.CASHBACK.getCode() == paySourceType){
			if(Check.NuNStr(pvSn)){
				LogUtil.info(LOGGER, "sendMsgToLanForPayVouchers方法，此时是返现类型，但是pvsn为空了");
				return ;
			}
			FinanceCashbackEntity financeCashback = financeCashbackServiceImpl.getByOrderSnAndPvsn( orderSn,  pvSn);
			LogUtil.info(LOGGER, "sendMsgToLanForPayVouchers方法，此时是返现类型，financeCashback={}",JsonEntityTransform.Object2Json(financeCashback));
			if(Check.NuNObj(financeCashback) || Check.NuNStr(financeCashback.getActSn())){
				return;
			}
			Map<String, String> smsPar = new HashMap<>();
			CashBackNameEnum cashBackNameEnum = CashBackNameEnum.getCashBackNameEnumByCode(financeCashback.getActSn());
			if(!Check.NuNObj(cashBackNameEnum) && !Check.NuNStr(cashBackNameEnum.getName())){
				String actName = cashBackNameEnum.getName();
				actName='"'+actName+'"';
				//返现类型打款短信
				smsPar.put("{1}", actName);
				smsPar.put("{2}", paySourceTypeName);
				smsPar.put("{3}", showTotolFee);
				smsPar.put("{4}", accountTypeName);
			}else{
				smsPar.put("{1}","返现" );
				smsPar.put("{2}", paySourceTypeName);
				smsPar.put("{3}", showTotolFee);
				smsPar.put("{4}", accountTypeName);
			}
			if(!Check.NuNStr(orderInfo.getLandlordTelCode())){
				smsPar.put(SysConst.MOBILE_NATION_CODE_KEY , orderInfo.getLandlordTelCode());
			}
			LogUtil.info(LOGGER, "sendMsgToLanForPayVouchers方法,立即准备发送短信，smsPar={}",JsonEntityTransform.Object2Json(smsPar));
			SendThreadPool.execute(new SendThread<>(smsSendService, mobile, MessageTemplateCodeEnum.ORDER_PAYCASHBACK_TO_LAN_SEND_MESSAGE_TO_LAN.getCode(), smsPar, null));
		}else{
			//房费，清洁费等打款短信
			String houseName = orderInfo.getHouseName();
			if(RentWayEnum.ROOM.getCode() == orderInfo.getRentWay() && !Check.NuNStr(orderInfo.getRoomName())){
				houseName = orderInfo.getRoomName();
			}
			if(Check.NuNStr(houseName) || Check.NuNObj(orderInfo.getStartTime()) || Check.NuNObj(orderInfo.getEndTime())){
				return ;
			}
			houseName='"'+houseName+'"';
			Map<String, String> smsPar = new HashMap<>();
			smsPar.put("{1}", houseName);
			smsPar.put("{2}", DateUtil.dateFormat(orderInfo.getStartTime()));
			smsPar.put("{3}", DateUtil.dateFormat(orderInfo.getEndTime()));
			smsPar.put("{4}", paySourceTypeName);
			smsPar.put("{5}", showTotolFee);
			smsPar.put("{6}", accountTypeName);
			if(!Check.NuNStr(orderInfo.getLandlordTelCode())){
				smsPar.put(SysConst.MOBILE_NATION_CODE_KEY, orderInfo.getLandlordTelCode());
			}
			LogUtil.info(LOGGER, "sendMsgToLanForPayVouchers方法,立即准备发送短信，smsPar={}",JsonEntityTransform.Object2Json(smsPar));
			SendThreadPool.execute(new SendThread<>(smsSendService, mobile, MessageTemplateCodeEnum.ORDER_PAYFEE_TO_LAN_SEND_MESSAGE_TO_LAN.getCode(), smsPar, null));
		}
	}
	
	/**
	 * 
	 * 房东 进击活动 给房东
	 *
	 * @author yd
	 * @created 2017年8月31日 下午7:05:38
	 *
	 * @param order
	 * @param cash
	 */
	public void  sendCashOrderToLan(OrderEntity order,String rankStr){
		
		if(Check.NuNStr(rankStr)){
			return ;
		}
		int rank =  Integer.valueOf(rankStr);
		CashOrderRankEnum cash = CashOrderRankEnum.getCashOrderRankEnumByRank(rank);
		if(!Check.NuNObjs(order,cash)&&!Check.NuNStr(order.getLandlordTel())){
			Map<String, String> smsPar = new HashMap<>();
			smsPar.put("{1}", cash.getOrderNum());
			smsPar.put("{2}", StringUtils.getPriceFormat(cash.getCashMoney()));
		
			int code = MessageTemplateCodeEnum.CASH_ORDER_TO_LANLORD_FIRST_SECONT_THIRD.getCode();
			if(rank<4){
				smsPar.put("{3}", cash.getOrderNumMax()-cash.getOrderNumMin()+"");
				smsPar.put("{4}", StringUtils.getPriceFormat(cash.getNextCashMoney()));
				smsPar.put("{5}", cash.getRankDesc());
			}else{
				code = MessageTemplateCodeEnum.CASH_ORDER_TO_LANLORD_FOUR.getCode();
				smsPar.put("{3}", StringUtils.getPriceFormat(cash.getNextCashMoney()));
				smsPar.put("{4}", cash.getRankDesc());
			}
	
			if(!Check.NuNStr(order.getLandlordTelCode())){
				smsPar.put(SysConst.MOBILE_NATION_CODE_KEY, order.getLandlordTelCode());
			}
			LogUtil.info(LOGGER, "sendCashOrderToLan方法,房东进击活动发送短信，smsPar={}",JsonEntityTransform.Object2Json(smsPar));
			SendThreadPool.execute(new SendThread<>(smsSendService, order.getLandlordTel(),code, smsPar, null));
		}
	}
	
	/**
	 * 
	 * 入住前 短信通知房客
	 *
	 * @author yd
	 * @created 2017年9月8日 下午3:07:21
	 *
	 * @param orderInfoVor
	 */
	public void  sendCheckInPreNotice(OrderInfoVo orderInfoVor){
		
		if(!Check.NuNObj(orderInfoVor)&&!Check.NuNStr(orderInfoVor.getUserTel())
				&&!Check.NuNStr(orderInfoVor.getHouseAddr())){
			
			LogUtil.info(LOGGER, "【入住前通知房客】tel={},houseAdr={}", orderInfoVor.getUserTel(),orderInfoVor.getHouseAddr());
			Map<String, String> smsPar = new HashMap<>();
			smsPar.put("{1}",orderInfoVor.getHouseAddr());
			smsPar.put(SysConst.MOBILE_NATION_CODE_KEY,orderInfoVor.getUserTelCode());
			SendThreadPool.execute(new SendThread<>(smsSendService, orderInfoVor.getUserTel(),MessageTemplateCodeEnum.ORDER_CHECK_IN_PRE_NOTICE_TENANT.getCode(), smsPar, null));
		}
	}


	/**
	 * 未支付订单协商取消，发送短信和推送消息
	 *
	 * @author loushuai
	 * @created 2017年10月11日 上午9:25:32
	 *
	 * @param orderSn
	 */
	public void sendMsg4CancelUnPayOrder(OrderInfoVo orderInfo) {
			//1.您有未支付订单被取消。您的{房源名称}房源{订单编号}订单已被房客取消。
			Map<String,String> smsPar = new HashMap<>();
			smsPar.put("phone", orderInfo.getLandlordTel());
			smsPar.put("{1}", ValueUtil.getStrValue(orderInfo.transName()));
			smsPar.put("{2}",ValueUtil.getStrValue(orderInfo.getOrderSn()));

			if(!Check.NuNStr(orderInfo.getLandlordTelCode())){
				smsPar.put(SysConst.MOBILE_NATION_CODE_KEY,orderInfo.getLandlordTelCode());
			}
			//2. 给用用户发推送 
			Map<String,String> pushPar = new HashMap<>();
			pushPar.put("uid",orderInfo.getLandlordUid());
			pushPar.put("{1}",ValueUtil.getStrValue(orderInfo.transName()));
			pushPar.put("{2}",ValueUtil.getStrValue(orderInfo.getOrderSn()));

			Map<String,String> bizPar = new HashMap<>();
			bizPar.put(JpushConst.MSG_SUB_TYPE_KEY, JpushConst.MSG_SUB_TYPE_VALULE_2);
			bizPar.put(JpushConst.MSG_HAS_RESPONSE,"1");
			bizPar.put(JpushConst.MSG_TAG_TYPE,JpushConst.MSG_TARGET_LAN);
			bizPar.put("orderSn", orderInfo.getOrderSn());
			//短信和推送同时发送
			//您的{1}房源{2}订单还未支付，请在{3}分钟内完成支付。
			SendThreadPool.execute(new SendThread<>(smsAndJpushSendService, MessageTemplateCodeEnum.ORDER_UN_PAY_CANCEL_TO_LAN_MSG.getCode(), pushPar, smsPar,bizPar));
	}
}
