package com.ziroom.minsu.api.order.controller;

import com.alibaba.fastjson.JSONObject;
import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.exception.SOAParseException;
import com.asura.framework.base.util.*;
import com.asura.framework.cache.redisOne.RedisOperations;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.api.common.abs.AbstractController;
import com.ziroom.minsu.api.common.constant.ConstDef;
import com.ziroom.minsu.api.common.constant.OrderConst;
import com.ziroom.minsu.api.common.dto.BaseParamDto;
import com.ziroom.minsu.api.common.dto.ResponseSecurityDto;
import com.ziroom.minsu.api.common.header.Header;
import com.ziroom.minsu.api.common.thread.SendEmailThread;
import com.ziroom.minsu.api.common.thread.SendOrderEmailThread;
import com.ziroom.minsu.api.common.util.ApiDateUtil;
import com.ziroom.minsu.api.common.valeenum.OrderProjectTypeEnum;
import com.ziroom.minsu.api.evaluate.service.EvalOrderService;
import com.ziroom.minsu.api.house.service.HouseService;
import com.ziroom.minsu.api.order.dto.CheckOutOrderApiRequest;
import com.ziroom.minsu.api.order.dto.CreateOrderApiRequest;
import com.ziroom.minsu.api.order.dto.NeedPayFeeApiRequest;
import com.ziroom.minsu.api.order.dto.OrderApiRequest;
import com.ziroom.minsu.api.order.entity.*;
import com.ziroom.minsu.api.order.service.TenantOrderService;
import com.ziroom.minsu.entity.cms.ActCouponEntity;
import com.ziroom.minsu.entity.cms.ActCouponUserEntity;
import com.ziroom.minsu.entity.customer.CustomerBaseMsgEntity;
import com.ziroom.minsu.entity.evaluate.EvaluateOrderEntity;
import com.ziroom.minsu.entity.order.OrderActivityEntity;
import com.ziroom.minsu.entity.order.OrderSmartLockEntity;
import com.ziroom.minsu.entity.order.UsualContactEntity;
import com.ziroom.minsu.services.basedata.api.inner.CityTemplateService;
import com.ziroom.minsu.services.basedata.api.inner.ConfCityService;
import com.ziroom.minsu.services.basedata.api.inner.SmsTemplateService;
import com.ziroom.minsu.services.basedata.dto.EmailRequest;
import com.ziroom.minsu.services.basedata.dto.SmsRequest;
import com.ziroom.minsu.services.cms.api.inner.ActCouponService;
import com.ziroom.minsu.services.cms.api.inner.InviteService;
import com.ziroom.minsu.services.cms.api.inner.MobileCouponService;
import com.ziroom.minsu.services.cms.constant.InviterCreateOrderConst;
import com.ziroom.minsu.services.cms.dto.*;
import com.ziroom.minsu.services.common.constant.MessageConst;
import com.ziroom.minsu.services.common.thread.pool.SendEmailThreadPool;
import com.ziroom.minsu.services.common.utils.*;
import com.ziroom.minsu.services.customer.api.inner.CustomerMsgManagerService;
import com.ziroom.minsu.services.customer.entity.CustomerVo;
import com.ziroom.minsu.services.evaluate.api.inner.EvaluateOrderService;
import com.ziroom.minsu.services.evaluate.dto.EvaluateRequest;
import com.ziroom.minsu.services.house.api.inner.HouseIssueService;
import com.ziroom.minsu.services.house.api.inner.HouseManageService;
import com.ziroom.minsu.services.house.smartlock.enumvalue.OnoffLineStatus;
import com.ziroom.minsu.services.house.smartlock.enumvalue.SmartErrNoEnum;
import com.ziroom.minsu.services.house.smartlock.param.GetSLInfosParam;
import com.ziroom.minsu.services.order.api.inner.OrderCommonService;
import com.ziroom.minsu.services.order.api.inner.OrderSmartLockService;
import com.ziroom.minsu.services.order.api.inner.OrderUserService;
import com.ziroom.minsu.services.order.constant.OrderFeeConst;
import com.ziroom.minsu.services.order.dto.*;
import com.ziroom.minsu.services.order.entity.CancleStrategyVo;
import com.ziroom.minsu.services.order.entity.CheckOutStrategyVo;
import com.ziroom.minsu.services.order.entity.OrderDetailVo;
import com.ziroom.minsu.services.order.entity.OrderInfoVo;
import com.ziroom.minsu.valenum.cms.InviteSourceEnum;
import com.ziroom.minsu.valenum.common.RequestTypeEnum;
import com.ziroom.minsu.valenum.common.UserTypeEnum;
import com.ziroom.minsu.valenum.common.YesOrNoEnum;
import com.ziroom.minsu.valenum.common.YesOrNoOrFrozenEnum;
import com.ziroom.minsu.valenum.customer.LocationTypeEnum;
import com.ziroom.minsu.valenum.evaluate.EvaluateClientBtnStatuEnum;
import com.ziroom.minsu.valenum.evaluate.EvaluateRulesEnum;
import com.ziroom.minsu.valenum.evaluate.EvaluateStatuEnum;
import com.ziroom.minsu.valenum.evaluate.EvaluateUserEnum;
import com.ziroom.minsu.valenum.house.RentWayEnum;
import com.ziroom.minsu.valenum.house.RoomTypeEnum;
import com.ziroom.minsu.valenum.msg.MessageTemplateCodeEnum;
import com.ziroom.minsu.valenum.order.*;
import com.ziroom.minsu.valenum.ordersmart.TempPswdStatusEnum;
import com.ziroom.minsu.valenum.traderules.CheckOutStrategy;
import com.ziroom.minsu.valenum.traderules.TradeRulesEnum;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

import static com.ziroom.minsu.api.common.dto.LoginDataDto.LOGGER;

/**
 * <p>房客端相关API接口</p>
 *
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author yd
 * @since 1.0
 * @version 1.0
 */
@Controller
@RequestMapping("/orderTen")
public class TenantOrderController extends AbstractController{


	@Resource(name = "api.messageSource")
	private MessageSource messageSource;
	/**
	 * 日志对象
	 */
	private static Logger logger = LoggerFactory.getLogger(TenantOrderController.class);

	@Resource(name = "order.orderCommonService")
	private OrderCommonService orderCommonService;

	@Resource(name = "order.orderUserService")
	private OrderUserService orderUserService;

	@Value("#{'${pic_base_addr_mona}'.trim()}")
	private String picBaseAddrMona;

	@Value("#{'${list_small_pic}'.trim()}")
	private String list_small_pic;

	@Value("#{'${HOUSE_SHARE_URL}'.trim()}")
	private String HOUSE_SHARE_URL;

	@Resource(name = "customer.customerMsgManagerService")
	private CustomerMsgManagerService customerMsgManagerService;

	@Resource(name = "cms.actCouponService")
	private ActCouponService actCouponService;


	@Value("#{'${SMART_LOCKS_GETLOCKSINFOS}'.trim()}")
	private String SMART_LOCKS_GETLOCKSINFOS;


	@Resource(name = "order.orderSmartLockService")
	private OrderSmartLockService  orderSmartLockService;

	@Resource(name ="house.houseManageService")
	private HouseManageService houseManageService;

	@Resource(name="basedata.confCityService")
    private ConfCityService confCityService;

	@Resource(name = "api.tenantOrderService")
	private TenantOrderService tenantOrderService;


	@Resource(name = "basedata.cityTemplateService")
	private CityTemplateService cityTemplateService;

	@Resource(name = "cms.inviteService")
	private InviteService inviteService;

	@Resource(name="basedata.smsTemplateService")
	private SmsTemplateService smsTemplateService;

	@Resource(name="api.houseService")
	private HouseService houseService;

	@Resource(name = "api.evalOrderService")
	private EvalOrderService evalOrderService;

	@Resource(name = "evaluate.evaluateOrderService")
    private EvaluateOrderService evaluateOrderService;

	@Value("#{'${ZIRUYI_UNCHECKIN_ORDERLIST_URL}'.trim()}")
	private String ZIRUYI_UNCHECKIN_ORDERLIST_URL;

	@Value("#{'${JUMP_TO_INVITE_CMS_PAGE_SHORT_CHAIN}'.trim()}")
	private String JUMP_TO_INVITE_CMS_PAGE_SHORT_CHAIN;

	@Value("#{'${JUMP_TO_MINSU_HOME_PAGE_URL}'.trim()}")
	private String JUMP_TO_MINSU_HOME_PAGE_URL;
	
	@Value("#{'${INVITEE_CAN_WIN_COUPON}'.trim()}")
	private String INVITEE_CAN_WIN_COUPON;
	
	@Value("#{'${CUSTOMER_DETAIL_URL}'.trim()}")
	private  String CUSTOMER_DETAIL_URL;

	@Autowired
	private RedisOperations redisOperations;
	
	@Resource(name = "cms.mobileCouponService")
    private MobileCouponService mobileCouponService;
	
	@Resource(name = "house.houseIssueService")
	private HouseIssueService houseIssueService;


	/**
	 * 校验优惠券
	 * @author lishaochuan
	 * @create 2016年6月15日下午6:50:27
	 * @param request
	 * @return
	 */
	@RequestMapping("/${LOGIN_AUTH}/checkCoupon")
	@ResponseBody
	public ResponseEntity<ResponseSecurityDto> checkCoupon(HttpServletRequest request){
		try {
			String paramJson=(String) request.getAttribute(ConstDef.DECRYPT_PARAM_ATTRIBUTE);
			LogUtil.info(logger, "checkCoupon params:{}", paramJson);
			NeedPayFeeApiRequest needPayApi = JsonEntityTransform.json2Object(paramJson, NeedPayFeeApiRequest.class);

			NeedPayFeeRequest needPayRequest = new NeedPayFeeRequest();
			BeanUtils.copyProperties(needPayApi, needPayRequest);
			needPayRequest.setUserUid(needPayApi.getUid());

			if(Check.NuNStr(needPayRequest.getCouponSn())){
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("优惠券号不能为空"),HttpStatus.OK);
			}


			// 绑定券
			DataTransferObject bindDto = null;
			String couponResultJson = "";
			if(SnUtil.isInviteSn(needPayRequest.getCouponSn()) || SnUtil.isInviteSnNew(needPayRequest.getCouponSn())){
				// 根据邀请码绑定优惠券
				String customerVoJson = this.customerMsgManagerService.getCutomerVoFromDb(needPayApi.getUid());
				CustomerVo customerVo = SOAResParseUtil.getValueFromDataByKey(customerVoJson, "customerVo", CustomerVo.class);
                if(SnUtil.isInviteSn(needPayRequest.getCouponSn())){
                	InviteAcceptRequest acceptRequest = new InviteAcceptRequest();
    				acceptRequest.setUid(customerVo.getUid());
    				acceptRequest.setMobile(customerVo.getShowMobile());
    				acceptRequest.setInviteCode(needPayRequest.getCouponSn());
    				couponResultJson = inviteService.accept(JsonEntityTransform.Object2Json(acceptRequest));
    				bindDto = JsonEntityTransform.json2DataTransferObject(couponResultJson);
    				if (bindDto.getCode() == DataTransferObject.SUCCESS) {
    					String inviteUid = ValueUtil.getStrValue(bindDto.getData().get("inviteUid"));
    					this.sendMsg4Invitee(needPayApi.getUid(), inviteUid);
    				}
                }else{
                	InviteCouponRequest param = new InviteCouponRequest();
                	param.setUid(needPayApi.getUid());
                	param.setInviteCode(needPayRequest.getCouponSn());
                	param.setInviteSource(InviteSourceEnum.NEW_INVITE.getCode());
                	param.setGroupSn(InviterCreateOrderConst.beInviterGroupSn);
                	LogUtil.info(logger, "checkCoupon方法 ，被邀请人用邀请码接收邀请发券，参数={}", JsonEntityTransform.Object2Json(param));
                	couponResultJson = mobileCouponService.acceptPullCouponByUid(JsonEntityTransform.Object2Json(param));
                	LogUtil.info(logger, "checkCoupon方法 ，被邀请人用邀请码接收邀请发券，结果={}", couponResultJson);
                	bindDto = JsonEntityTransform.json2DataTransferObject(couponResultJson);
                	if (bindDto.getCode() == DataTransferObject.SUCCESS) {
    					String inviteUid = ValueUtil.getStrValue(bindDto.getData().get("inviteUid"));
    					this.sendMsg4InviteeAndInviter(needPayApi.getUid(), inviteUid);
    				}
                	//return new ResponseEntity<>(ResponseSecurityDto.responseEncrypt(bindDto), HttpStatus.OK);
                }

			}else{
				// 绑定优惠券
				BindCouponRequest couponRequest = new BindCouponRequest();
				couponRequest.setCouponSn(needPayRequest.getCouponSn());
				couponRequest.setUid(needPayRequest.getUserUid());
				couponResultJson = actCouponService.exchangeCode(JsonEntityTransform.Object2Json(couponRequest));

				bindDto = JsonEntityTransform.json2DataTransferObject(couponResultJson);
			}

			// 校验绑定券结果
			if(bindDto.getCode() != DataTransferObject.SUCCESS){
				LogUtil.info(logger, "绑定优惠券未通过{}", bindDto.toJsonString());
				return new ResponseEntity<>(ResponseSecurityDto.responseEncrypt(bindDto), HttpStatus.OK);
			}
			ActCouponEntity ace = bindDto.parseData("coupon", new TypeReference<ActCouponEntity>(){});
			if(Check.NuNObj(ace)){
				LogUtil.info(logger, "返回优惠券为空:{}", couponResultJson);
				return new ResponseEntity<>(ResponseSecurityDto.responseEncrypt(bindDto), HttpStatus.OK);
			}
			if(Check.NuNStr(ace.getCouponSn())){
				LogUtil.info(logger, "返回优惠券编号为空:{}", couponResultJson);
				return new ResponseEntity<>(ResponseSecurityDto.responseEncrypt(bindDto), HttpStatus.OK);
			}
			needPayRequest.setCouponSn(ace.getCouponSn());


			//获取优惠券信息
			DataTransferObject couponDto = new DataTransferObject();
			ActCouponUserEntity actCouponUserEntity = tenantOrderService.getCouponFullInfo(couponDto, needPayRequest.getCouponSn());
			needPayRequest.setActCouponUserEntity(actCouponUserEntity);
			if (couponDto.getCode() != DataTransferObject.SUCCESS) {
				//获取优惠券信息失败
				LogUtil.info(logger, "获取优惠券信息失败 resultJson:{}", couponDto.toJsonString());
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail(couponDto.getMsg()), HttpStatus.OK);
			}
			// 获取优惠券信息成功，验证订单是否可用
			DataTransferObject dto = tenantOrderService.getNeedPayFee(needPayRequest);
			LogUtil.info(logger, "checkCoupon getNeedPayFee resultJson:{}", dto.toJsonString());
			if(dto.getCode() == DataTransferObject.SUCCESS){
				// 成功，返回绑定优惠券接口出参
				return new ResponseEntity<>(ResponseSecurityDto.responseEncrypt(bindDto), HttpStatus.OK);
			}else{
				String msg = "兑换成功！但是，"+dto.getMsg()+"，本单无法使用该优惠券，请核实使用条件。可在个人中心详细查看优惠券";
				dto.setMsg(msg);
			}
			return new ResponseEntity<>(ResponseSecurityDto.responseEncrypt(dto), HttpStatus.OK);
        } catch (Exception e) {
			LogUtil.error(logger, "checkCoupon is error, e={}",e);
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("服务错误"),HttpStatus.OK);
		}
	}


	/**
	 * 给邀请人和被邀请人发送短信
	 * @param inviteeUid
	 * @param inviterUid
	 */
	private void sendMsg4InviteeAndInviter(String inviteeUid, String inviterUid){
		LogUtil.info(LOGGER, "邀请人接受邀请，给邀请人和被邀请人发送短信入参，inviteeUid={},inviterUid={}", inviteeUid,inviterUid);
		try {
			// 被邀请人信息
			Map<String, String> inviteeMap = getNicknameAndHeadpicByUid(inviteeUid);
			if(Check.NuNObj(inviteeMap) || Check.NuNStr(inviteeMap.get("mobile"))){
				return;
			}
			String inviteeNickName = "自小如";
            if(!Check.NuNStr(inviteeMap.get("nick_name"))){
            	inviteeNickName = inviteeMap.get("nick_name");
            }
			// 邀请人信息
			Map<String, String> inviterMap = getNicknameAndHeadpicByUid(inviterUid);
			if(Check.NuNObj(inviterMap) || Check.NuNStr(inviterMap.get("mobile"))){
				return;
			}

			//给被邀请人送短信
			SmsRequest smsToInviteeRequest = new SmsRequest();
			Map<String, String> paramsToInviteeMap = new HashMap<>();
			paramsToInviteeMap.put("{1}", INVITEE_CAN_WIN_COUPON);
			paramsToInviteeMap.put("{2}", JUMP_TO_MINSU_HOME_PAGE_URL);
			smsToInviteeRequest.setParamsMap(paramsToInviteeMap);
			smsToInviteeRequest.setMobile(inviteeMap.get("mobile"));
			smsToInviteeRequest.setSmsCode(String.valueOf(MessageTemplateCodeEnum.INVITEE_ACCEPT_SMS_TO_HESELF.getCode()));
			LogUtil.info(LOGGER, "sendMsg4InviteeAndInviter方法  给被邀请人发送短信参数， smsToInviteeRequest={} ", JsonEntityTransform.Object2Json(smsToInviteeRequest));
			smsTemplateService.sendSmsByCode(JsonEntityTransform.Object2Json(smsToInviteeRequest));
			
			//给邀请人送短信
			SmsRequest smsToInviterRequest = new SmsRequest();
			Map<String, String> paramsToInviterMap = new HashMap<>();
			paramsToInviterMap.put("{1}", inviteeNickName);
			paramsToInviterMap.put("{2}", JUMP_TO_INVITE_CMS_PAGE_SHORT_CHAIN);
			smsToInviterRequest.setParamsMap(paramsToInviterMap);
			smsToInviterRequest.setMobile(inviterMap.get("mobile"));
			smsToInviterRequest.setSmsCode(String.valueOf(MessageTemplateCodeEnum.INVITEE_ACCEPT_SMS_TO_INVITER.getCode()));
			LogUtil.info(LOGGER, "sendMsg4InviteeAndInviter方法  给邀请人发送短信参数，  smsToInviterRequest={}  ", JsonEntityTransform.Object2Json(smsToInviterRequest));
			smsTemplateService.sendSmsByCode(JsonEntityTransform.Object2Json(smsToInviterRequest));
			
		}catch (Exception e){
			LogUtil.error(LOGGER,"邀请下单活动，给邀请人和被邀请人发送短信失败：e：{}",e);
		}
	}
	
	/**
	 * 
	 * uid获取用户头像和昵称
	 *
	 * @author bushujie
	 * @created 2017年8月29日 下午6:50:02
	 *
	 * @return
	 * @throws IOException 
	 * @throws SOAParseException 
	 */
	public Map<String, String> getNicknameAndHeadpicByUid(String uid) throws IOException, SOAParseException{
		//查询自如客头像和昵称
		StringBuffer url = new StringBuffer();
		url.append(CUSTOMER_DETAIL_URL).append(uid);
		String getResult = CloseableHttpUtil.sendGet(url.toString(), null);
		LogUtil.info(LOGGER, "调用接口：{}，返回用户信息：{}",url.toString(),getResult);
		if(Check.NuNStrStrict(getResult)){
			LogUtil.error(LOGGER, "CUSTOMER_ERROR:根据用户uid={},获取用户信息失败", uid);
		}
		Map<String, String> resultMap=new HashMap<String, String>();
		try {
			resultMap = (Map<String, String>) JsonEntityTransform.json2Map(getResult);
		} catch (Exception e) {
			LogUtil.info(LOGGER, "用户信息转化错误，请求url={}，返回结果result={}，e={}",url.toString(),getResult,e);
		}
		Object code = resultMap.get("error_code");
		if(Check.NuNObj(code)){
			LogUtil.error(LOGGER, "【获取用户头像】获取用户头像错误code={}，请求url={}，返回结果result={}",code,url.toString(),getResult);
		}
		Map<String, String>  dataMap = (Map<String, String>) JsonEntityTransform.json2Map(JsonEntityTransform.Object2Json(resultMap.get("data")));
	    return dataMap;
	    		
	}
	
	/**
	 * 给受邀人发短信
	 */
	private void sendMsg4Invitee(String inviteeUid, String inviterUid){
		try {
			// 受邀人信息
			String inviteeCustomerJson = customerMsgManagerService.getCustomerBaseMsgEntitybyUid(inviteeUid);
			CustomerBaseMsgEntity inviteeCustomer = SOAResParseUtil.getValueFromDataByKey(inviteeCustomerJson, "customer", CustomerBaseMsgEntity.class);
			if(Check.NuNObj(inviteeCustomer) || Check.NuNStr(inviteeCustomer.getCustomerMobile())){
				LogUtil.info(LOGGER, "受邀人信息错误，不发短信，inviteeUid:{}, mobile:{}, inviteeCustomerJson:{}", inviteeUid, inviteeCustomer.getCustomerMobile(), inviteeCustomerJson);
				return;
			}

			// 邀请人信息
			String inviterCustomerJson = customerMsgManagerService.getCustomerBaseMsgEntitybyUid(inviterUid);
			CustomerBaseMsgEntity inviterCustomer = SOAResParseUtil.getValueFromDataByKey(inviterCustomerJson, "customer", CustomerBaseMsgEntity.class);

			String name = "";
			if(!Check.NuNObj(inviterCustomer.getNickName())){
				name = ValueUtil.getStrValue(inviterCustomer.getNickName());
			}

			SmsRequest smsRequest = new SmsRequest();
			Map<String, String> paramsMap = new HashMap<>();
			paramsMap.put("{1}", name);
			smsRequest.setParamsMap(paramsMap);
			smsRequest.setMobile(inviteeCustomer.getCustomerMobile());
			smsRequest.setSmsCode(String.valueOf(MessageTemplateCodeEnum.ACTIVITY_SYRSQ.getCode()));
			smsTemplateService.sendSmsByCode(JsonEntityTransform.Object2Json(smsRequest));
		}catch (Exception e){
			LogUtil.error(LOGGER,"给受邀人发送短信失败：e：{}",e);
		}
	}
	
    /**
     * <p>描述:</p>
     * @author afi
     * <p><b>当前可用的优惠券列表</b></p>
     * @return
     */
    @RequestMapping("/${LOGIN_AUTH}/couponList")
    @ResponseBody
    public ResponseEntity<ResponseSecurityDto> couponList(HttpServletRequest request){
        try {
            String paramJson=(String) request.getAttribute(ConstDef.DECRYPT_PARAM_ATTRIBUTE);
            LogUtil.info(logger, "couponList params:{}", paramJson);
            NeedPayFeeApiRequest needPayApi = JsonEntityTransform.json2Object(paramJson, NeedPayFeeApiRequest.class);
            NeedPayFeeRequest needPayRequest = new NeedPayFeeRequest();
            BeanUtils.copyProperties(needPayApi, needPayRequest);
            needPayRequest.setUserUid(needPayApi.getUid());

			// 查询此用户绑定的手机号，是否有未绑定到用户身上的优惠券，如果有则绑定
			String json = customerMsgManagerService.getCustomerBaseMsgEntitybyUid(needPayRequest.getUserUid());
			DataTransferObject customerDto = JsonEntityTransform.json2DataTransferObject(json);
			if(customerDto.getCode() == DataTransferObject.SUCCESS){
				CustomerBaseMsgEntity customer = customerDto.parseData("customer", new TypeReference<CustomerBaseMsgEntity>() {});
				if(!Check.NuNObj(customer) && !Check.NuNObj(customer.getCustomerMobile())){
					BindCouponMobileRequest bindCouponMobileRequest = new BindCouponMobileRequest();
					bindCouponMobileRequest.setUid(needPayRequest.getUserUid());
					bindCouponMobileRequest.setMobile(customer.getCustomerMobile());
					actCouponService.bindCouponByMobile(JsonEntityTransform.Object2Json(bindCouponMobileRequest));
				}
			}

			//获取优惠券需要的needPayFee信息
            DataTransferObject dto = tenantOrderService.getNeedPayFee(needPayRequest);
            LogUtil.info(logger, "getNeedPayFee resultJson:{}", dto.toJsonString());
			if (dto.getCode() != DataTransferObject.SUCCESS){
                return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail(dto.getMsg()),HttpStatus.OK);
            }
            //拼装获取可用优惠券的参数
            CheckCouponRequest checkRequest = tenantOrderService.getCheckRequestByNeedPayFeeDto(dto,needPayRequest.getUserUid());
            DataTransferObject dtoList = JsonEntityTransform.json2DataTransferObject(actCouponService.getCouponListCheckByUid(JsonEntityTransform.Object2Json(checkRequest)));
            LogUtil.info(logger, "couponList resultJson:{}", dtoList.toJsonString());
            return new ResponseEntity<>(ResponseSecurityDto.responseEncrypt(dtoList), HttpStatus.OK);
        } catch (Exception e) {
            LogUtil.error(logger, "getNeedPayFee is error, e={}",e);
            return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("服务错误"),HttpStatus.OK);
        }
    }



	/**
	 * [获取订单金额,并获取默认的优惠券信息]V3
	 * @author afi
	 * @return
	 */
	@RequestMapping("/${LOGIN_AUTH}/needPayV3")
	@ResponseBody
	public ResponseEntity<ResponseSecurityDto> needPayV3(HttpServletRequest request){
		try {

			Header header = getHeader(request);
			String paramJson=(String) request.getAttribute(ConstDef.DECRYPT_PARAM_ATTRIBUTE);
            LogUtil.info(logger, "【needPayV3】params:{},header={}", paramJson, JsonEntityTransform.Object2Json(header));
            NeedPayFeeApiRequest needPayApi = JsonEntityTransform.json2Object(paramJson, NeedPayFeeApiRequest.class);
			NeedPayFeeRequest needPayRequest = new NeedPayFeeRequest();
			//获取当前的版本号信息
			needPayRequest.setVersionCode(header.getVersionCode());
			BeanUtils.copyProperties(needPayApi, needPayRequest);
			needPayRequest.setUserUid(needPayApi.getUid());
			//获取当前的订单金额信息
			DataTransferObject dto = tenantOrderService.needPayFeeAndDefaultCoupon(needPayRequest);
            LogUtil.info(logger, "【needPayV3】resultJson:{}", dto.toJsonString());
            return new ResponseEntity<>(ResponseSecurityDto.responseEncrypt(dto), HttpStatus.OK);
		} catch (Exception e) {
            LogUtil.error(logger, "【needPayV3】is error, e={}", e);
            return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("服务错误"),HttpStatus.OK);
		}
	}



	/**
	 * [下单页获取订单金额]V1
	 * 目前只有历史版本app和pc使用
	 * @author afi
	 * @return
	 */
	@RequestMapping("/${LOGIN_AUTH}/getNeedPayFee")
	@ResponseBody
	public ResponseEntity<ResponseSecurityDto> getNeedPayFee(HttpServletRequest request){
		try {
			Header header = getHeader(request);
			String paramJson=(String) request.getAttribute(ConstDef.DECRYPT_PARAM_ATTRIBUTE);
			LogUtil.info(logger, "getNeedPayFee params:{}", paramJson);
			NeedPayFeeApiRequest needPayApi = JsonEntityTransform.json2Object(paramJson, NeedPayFeeApiRequest.class);
			NeedPayFeeRequest needPayRequest = new NeedPayFeeRequest();
			//获取当前的版本号信息
			needPayRequest.setVersionCode(header.getVersionCode());
			BeanUtils.copyProperties(needPayApi, needPayRequest);
			needPayRequest.setUserUid(needPayApi.getUid());

			//获取当前的订单金额信息
			DataTransferObject dto = tenantOrderService.getNeedPayFee(needPayRequest);
			LogUtil.info(logger, "getNeedPayFee resultJson:{}", dto.toJsonString());
			return new ResponseEntity<>(ResponseSecurityDto.responseEncrypt(dto), HttpStatus.OK);

		} catch (Exception e) {
			LogUtil.error(logger, "getNeedPayFee is error, e={}",e);
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("服务错误"),HttpStatus.OK);
		}
	}


	/**
	 * [下单页获取订单金额]V2
	 * @author lishaochuan
	 * @create 2016年8月19日下午6:43:59
	 * @param request
	 * @return
	 */
	@RequestMapping("/${LOGIN_AUTH}/needPay")
	@ResponseBody
	public ResponseEntity<ResponseSecurityDto> needPay(HttpServletRequest request){
		try {
			Header header = getHeader(request);
			String paramJson=(String) request.getAttribute(ConstDef.DECRYPT_PARAM_ATTRIBUTE);
			LogUtil.info(logger, "needPay params:{}", paramJson);
			NeedPayFeeApiRequest needPayApi = JsonEntityTransform.json2Object(paramJson, NeedPayFeeApiRequest.class);
			NeedPayFeeRequest needPayRequest = new NeedPayFeeRequest();

			needPayRequest.setVersionCode(header.getVersionCode());

			BeanUtils.copyProperties(needPayApi, needPayRequest);
			needPayRequest.setUserUid(needPayApi.getUid());
            //获取需支付金额
			DataTransferObject dto = tenantOrderService.needPay(needPayRequest);
			LogUtil.info(logger, "needPay resultJson:{}", dto.toJsonString());
			return new ResponseEntity<>(ResponseSecurityDto.responseEncrypt(dto), HttpStatus.OK);

		} catch (Exception e) {
			LogUtil.error(logger, "needPay is error, e={}",e);
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("服务错误"),HttpStatus.OK);
		}
	}


	/**
	 * 下单
	 * @author afi
	 * @param request
	 * @return
	 */
	@RequestMapping("/${LOGIN_AUTH}/createOrder")
	@ResponseBody
	public ResponseEntity<ResponseSecurityDto> createOrder(HttpServletRequest request){
		try{
			Header header = getHeader(request);
			String paramJson=(String) request.getAttribute(ConstDef.DECRYPT_PARAM_ATTRIBUTE);
			LogUtil.info(logger, "createOrder params:" + paramJson);
			CreateOrderApiRequest creatOrderApi = JsonEntityTransform.json2Object(paramJson, CreateOrderApiRequest.class);
			//将当前的参数转化
			CreateOrderRequest creatOrderRequest = new CreateOrderRequest();
			creatOrderRequest.setVersionCode(header.getVersionCode());
			BeanUtils.copyProperties(creatOrderApi, creatOrderRequest);
			creatOrderRequest.setUserUid(creatOrderApi.getUid());
			//调用本地service的下单逻辑
			DataTransferObject dto = tenantOrderService.createOrder(creatOrderRequest);
			LogUtil.info(logger, "createOrder rst:" + dto.toJsonString());

			if (dto.getCode() == DataTransferObject.SUCCESS){
				try {
					houseService.saveLocation(creatOrderApi.getUid(),header,getIpAddress(request), LocationTypeEnum.ORDER,ValueUtil.getStrValue(dto.getData().get("orderSn")),null);

					//下单给房东发邮件
					sendEmailOrder(creatOrderApi.getStartTime(), creatOrderApi.getEndTime(),"订单待您处理",creatOrderApi.getUid(),creatOrderApi.getTenantFids().size(), creatOrderApi.getFid(), creatOrderApi.getRentWay());
				}catch (Exception e){
					LogUtil.error(LOGGER, "下单保存用户位置信息异常，error = {}", e);
				}
			}

			return new ResponseEntity<>(ResponseSecurityDto.responseEncrypt(dto), HttpStatus.OK);
		}catch(Exception e){
			LogUtil.error(logger, "createOrder is error, e={}",e);
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("服务错误"),HttpStatus.OK);
		}
	}








	/**
	 * 获取用户信息
	 * @author afi
	 * @param uid
	 * @return
	 */
	private CustomerBaseMsgEntity getCustomerInfo(String uid){
		if(Check.NuNStr(uid)){
			return null;
		}
		CustomerBaseMsgEntity customer = null;

		//获取cms的优惠券信息
		String json = customerMsgManagerService.getCustomerBaseMsgEntitybyUid(uid);
		DataTransferObject customerDto = JsonEntityTransform.json2DataTransferObject(json);
		if(customerDto.getCode() != DataTransferObject.SUCCESS){
			LogUtil.error(logger, "下单获取用户信息失败：{} uid：{}", customerDto.toJsonString(),uid);
			return customer;
		}else {
			customer = customerDto.parseData("customer", new TypeReference<CustomerBaseMsgEntity>() {});
			LogUtil.info(logger, "下单获取用户信息：{} ", JsonEntityTransform.Object2Json(customer));
		}
		return customer;
	}




	/**
	 * 参数
	 * <p>{"loginToken":"1212235","uid":"1122222","orderSn":"16042847E2OYWL212522"}</p>
	 * 显示订单详情
	 *
	 * 智能锁业务：
	 * 1.当前订单校验（A.校验订单是否存在 B.校验订单的状态 1.订单支付成功 2.当前时间在入住前一天到订单结束时间）
	 * 2.校验不通过，不做智能锁业务处理
	 * 3.校验通过，做智能锁业务处理
	 * 4.订单是否是入住前一天，到入住前半小时期间，A，网关在线，展示密码，无密码，展示获取密码按钮；B 网关不在线，提示入住前半小时获取临时密码
	 * 5.到入住前半小时到入住结束，A.网关在线，展示密码，无密码，展示获取密码按钮；B，网关不在线，展示临时密码，临时密码无，展示获取临时密码按钮
	 *
	 * @author jixd
	 * @created 2016年4月30日 下午5:00:25
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/${LOGIN_AUTH}/showDetail",method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<ResponseSecurityDto> showOrderDetail(HttpServletRequest request){
		try{
			String paramJson = (String) request.getAttribute(ConstDef.DECRYPT_PARAM_ATTRIBUTE);
			Header header = getHeader(request);
			Integer versionCode = header.getVersionCode();
			LogUtil.info(logger, "【TenantOrderController.showDetail】参数={},header={},", paramJson, JsonEntityTransform.Object2Json(header));
			OrderApiRequest requestDto = JsonEntityTransform.json2Object(paramJson,OrderApiRequest.class);
			if(Check.NuNStr(requestDto.getOrderSn())){
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("订单号为空"),HttpStatus.OK);
			}
            OrderDetailRequest orderDetailRequest = new OrderDetailRequest();
			orderDetailRequest.setUid(requestDto.getUid());
			orderDetailRequest.setOrderSn(requestDto.getOrderSn());
			//房客
			orderDetailRequest.setRequestType(1);
			String resultJson = orderCommonService.queryOrderInfoBySn(JsonEntityTransform.Object2Json(orderDetailRequest));
			DataTransferObject resultDto  = JsonEntityTransform.json2DataTransferObject(resultJson);
			if(resultDto.getCode() != DataTransferObject.SUCCESS){
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail(resultDto.getMsg()),HttpStatus.OK);
			}
			OrderDetailVo orderDetailVo = SOAResParseUtil.getValueFromDataByKey(resultJson, "orderDetailVo", OrderDetailVo.class);

			//查询退订政策
			String strategyJson = orderCommonService.getCheckOutStrategyByOrderSn(requestDto.getOrderSn());
			DataTransferObject strategyDto  = JsonEntityTransform.json2DataTransferObject(strategyJson);

			if(strategyDto.getCode() != DataTransferObject.SUCCESS){
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail(strategyDto.getMsg()),HttpStatus.OK);
			}
			CheckOutStrategy checkOutStrategy = strategyDto.parseData("checkOutStrategy", new TypeReference<CheckOutStrategy>() {});
			Integer freeDay = checkOutStrategy.getPreFreeDayCount();
			OrderDetailApiVo orderDetailApiVo = new OrderDetailApiVo();
			//重新封装返回参数
			wrapOrderDetail(orderDetailVo, orderDetailApiVo);
			// 处理订单详情金额
            this.dealOrderDetailMoney(orderDetailVo, versionCode, orderDetailApiVo);
            //处理房东的头像信息
			this.dealLandTel(orderDetailVo,orderDetailApiVo);
			String msg = null;
			//天数转换成小时
			if (ValueUtil.getdoubleValue(checkOutStrategy.getCancelFreePercent()) > 0
					|| ValueUtil.getintValue(checkOutStrategy.getFreeCost()) > 0){
				orderDetailApiVo.setPreFreeHour(0);
				msg = "入住前取消订单将产生违约金";
			}else {
				orderDetailApiVo.setPreFreeHour(freeDay * 24);
				msg = "入住前"+ValueUtil.getTimeInfoByDay(freeDay)+"取消订单将产生违约金";
			}
			//提示当前订单的退订信息
			orderDetailApiVo.setMsgInfo(msg);
			//智能锁处理
			orderDetailApiVo = showSmartLock(orderDetailApiVo, orderDetailVo);
            //设置分享链接
			this.showShareLinks(orderDetailApiVo, orderDetailVo);
			//查询是否国外用户
			String customerJson=customerMsgManagerService.getCustomerBaseMsgEntitybyUid(orderDetailVo.getLandlordUid());
			CustomerBaseMsgEntity customerBaseMsgEntity=SOAResParseUtil.getValueFromDataByKey(customerJson, "customer", CustomerBaseMsgEntity.class);
			if(!"86".equals(customerBaseMsgEntity.getCountryCode())){
				orderDetailApiVo.setLandlordMobile("4007711666");
			}
			//共享客厅返回特殊出租方式名称
			String isHallJson=houseIssueService.isHallByHouseBaseFid(orderDetailVo.getHouseFid());
			Integer isHall=SOAResParseUtil.getIntFromDataByKey(isHallJson, "isHall");
			if(isHall==RoomTypeEnum.HALL_TYPE.getCode()){
				orderDetailApiVo.setRentWayName(RentWayEnum.HALL.getName());
			} else {
				orderDetailApiVo.setRentWayName(RentWayEnum.getRentWayByCode(orderDetailApiVo.getRentWay()).getName());
			}
			LogUtil.info(LOGGER, "【TenantOrderController.showDetail】结果={}", JsonEntityTransform.Object2Json(orderDetailApiVo));
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptOK(orderDetailApiVo),HttpStatus.OK);
		}catch(Exception e){
			LogUtil.error(logger, "showOrderDetail is error, e={}",e);
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("服务错误"),HttpStatus.OK);
		}
	}


	/**
	 * 订单详情智能锁业务处理
	 * @author yd
	 * @created 2016年6月25日 下午2:59:11
	 * @param orderDetailVo
	 * @throws UnsupportedEncodingException
	 */
	private OrderDetailApiVo  showSmartLock(OrderDetailApiVo orderDetailApiVo,OrderDetailVo orderDetailVo){


		//校验订单是否可以获取智能锁密码
		if(Check.NuNObj(orderDetailVo)||Check.NuNObj(orderDetailVo.getPayStatus())||orderDetailVo.getPayStatus().intValue() != OrderPayStatusEnum.HAS_PAY.getPayStatus()){
			return orderDetailApiVo;
		}
		if(!checkOrderStatus(orderDetailVo.getOrderStatus())){
			return orderDetailApiVo;
		}
		//校验房源是否有智能锁
		DataTransferObject dto =JsonEntityTransform.json2DataTransferObject( this.houseManageService.isHasSmartLock(orderDetailApiVo.getHouseFid()));
		orderDetailApiVo.setIsLock(0);
		if(dto.getCode() == DataTransferObject.ERROR){
			LogUtil.error(logger, "根据房源fid={}校验查询是否有智能锁异常，msg={}", orderDetailApiVo.getHouseFid(),dto.getMsg());
			return orderDetailApiVo;
		}

		Object flag = dto.getData().get("result");

		if(Check.NuNObj(flag)||(int)flag == 2){
			return orderDetailApiVo;
		}
		orderDetailApiVo.setIsLock(1);
		//校验当前时间是否在入住时间的前一天
		if(!checkOrderTime(orderDetailVo.getStartTime(), orderDetailVo.getEndTime(),orderDetailApiVo)){
			orderDetailApiVo.setIsOneDay(0);
			return orderDetailApiVo;
		}
		orderDetailApiVo.setIsOneDay(1);

		try {
			//添加门锁状态
			dto = JsonEntityTransform.json2DataTransferObject(this.customerMsgManagerService.getCutomerVoFromDb(orderDetailVo.getUserUid()));
			CustomerVo customerVo = dto.parseData("customerVo", new TypeReference<CustomerVo>() {
			});
			if(!Check.NuNObj(customerVo)){
				GetSLInfosParam paramInfos = new GetSLInfosParam();
				//校验门锁状态
				paramInfos.setHouse_id(StringUtils.getSmartLockCode(orderDetailVo.getHouseFid()));
				paramInfos.setOp_name(customerVo.getRealName());
				paramInfos.setOp_phone(customerVo.getShowMobile());
				paramInfos.setOp_userid(customerVo.getUid());
				String [] rooms = new String[]{StringUtils.getSmartLockCode(orderDetailVo.getHouseFid())};
				paramInfos.setRooms(rooms);
				dto = judgeGatWayStatus(paramInfos, dto);
				if(dto.getCode() == DataTransferObject.ERROR){
					orderDetailApiVo.setSmartLockSta(OnoffLineStatus.ERROR.getCode());
				}
				Map<String, String> baseMap  = (Map<String, String>) dto.getData().get("smartMap");
				if(!Check.NuNMap(baseMap)){
					Object lock = baseMap.get("lock");
					baseMap.clear();
					baseMap = (Map<String, String>) JsonEntityTransform.json2Map(JsonEntityTransform.Object2Json(lock));
					if(!Check.NuNMap(baseMap)){
						Object onOffLine = baseMap.get("onoff_line");
						orderDetailApiVo.setSmartLockSta(OnoffLineStatus.ON_LINE.getCode());
						if(onOffLine!=null&&(int)onOffLine == OnoffLineStatus.OFF_LINE.getCode()){
							orderDetailApiVo.setSmartLockSta(OnoffLineStatus.OFF_LINE.getCode());
						}
					}

				}
			}

			dto = JsonEntityTransform.json2DataTransferObject(this.orderSmartLockService.findOrderSmartLockByOrderSn(orderDetailVo.getOrderSn()));
			if(dto.getCode() == DataTransferObject.ERROR){
				orderDetailApiVo.setPersistPswdStatus(4);
				logger.error("根据订单号orderSn={}智能锁查询异常",orderDetailVo.getOrderSn());
				return orderDetailApiVo;
			}
			OrderSmartLockEntity orderSmartLock  = dto.parseData("orderSmartLock", new TypeReference<OrderSmartLockEntity>() {
			});

			//数据库查询
			//有智能锁密码
			if(!Check.NuNObj(orderSmartLock)&&
					!Check.NuNStr(orderSmartLock.getTempPswd())&&!Check.NuNObj(orderSmartLock.getTempExpiredTime())){
				Long persistExpiredTime = orderSmartLock.getTempExpiredTime().getTime();
				Long currTimeLong = (new Date()).getTime();
				if(currTimeLong>persistExpiredTime){
					orderDetailApiVo.setPersistPswdStatus(5);
					logger.info("根据订单号orderSn={}智能锁查询,密码过期",orderDetailVo.getOrderSn());
				}else{
					orderDetailApiVo.setPersistPswdStatus(orderSmartLock.getTempPswdStatus());
					if(orderSmartLock.getTempPswdStatus().intValue() == TempPswdStatusEnum.SUCCESS.getCode()){
						orderDetailApiVo.setPersistPswd( StringUtils.decode(orderSmartLock.getTempPswd()));
					}
					return orderDetailApiVo;
				}


			}

			//有临时密码
			if(!Check.NuNObj(orderSmartLock)&&
					!Check.NuNStr(orderSmartLock.getDynaPswd())&&!Check.NuNObj(orderSmartLock.getDynaExpiredTime())
					&&orderDetailApiVo.getIsHalfHoure() == 1){
				Long dynaExpiredTime = orderSmartLock.getDynaExpiredTime().getTime();
				Long currTimeLong = (new Date()).getTime();
				orderDetailApiVo.setDynaNum(orderSmartLock.getDynaNum());
				orderDetailApiVo.setDynaExpiredTimeStr(DateUtil.dateFormat(orderSmartLock.getDynaExpiredTime(), "yyyy-MM-dd HH:mm:ss"));
				if(currTimeLong>dynaExpiredTime){
					orderDetailApiVo.setPersistPswdStatus(5);
					logger.info("根据订单号orderSn={}智能锁查询,密码临时密码过期",orderDetailVo.getOrderSn());
				}else{
					if(!Check.NuNObj(orderSmartLock.getDynaNum())&&orderSmartLock.getDynaNum().intValue()<3){
						orderDetailApiVo.setDynaPswd(StringUtils.decode(orderSmartLock.getDynaPswd()));
						return orderDetailApiVo;
					}
				}

			}

			if(Check.NuNStr(orderDetailApiVo.getDynaPswd())&&Check.NuNStr(orderDetailApiVo.getPersistPswd())){
				orderDetailApiVo.setPersistPswdStatus(6);
			}
		} catch (UnsupportedEncodingException e) {
			LogUtil.error(logger, "智能锁业务异常e={}", e);
		}

		return orderDetailApiVo;
	}


	/**
	 * 设置分享链接
	 * @author lishaochuan
	 * @create 2016年8月22日上午11:05:45
	 * @param orderDetailApiVo
	 * @param orderDetailVo
	 */
	private void showShareLinks(OrderDetailApiVo orderDetailApiVo,OrderDetailVo orderDetailVo){
		StringBuilder shareLinks = new StringBuilder(HOUSE_SHARE_URL);
		shareLinks.append("orderten/ee5f86/orderShare?fid=");
		shareLinks.append(orderDetailVo.returnFid());
		shareLinks.append("&rentWay=");
		shareLinks.append(orderDetailVo.getRentWay());
		shareLinks.append("&c=");
		shareLinks.append(orderDetailVo.getCityCode());
		shareLinks.append("&orderSn=");
		shareLinks.append(orderDetailVo.getOrderSn());
		orderDetailApiVo.setShareLinks(shareLinks.toString());
	}


	/**
	 *
	 * 校验当前时间是否在入住时间的前一天
	 * 在前一天，判断是否在前半小时之内
	 *
	 * @author yd
	 * @created 2016年6月23日 上午11:13:23
	 *
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	private boolean checkOrderTime(Date startTime ,Date endTime,OrderDetailApiVo orderDetailApiVo){
		boolean orderStatusFlag = true;
		if(Check.NuNObj(startTime)||Check.NuNObj(endTime)){
			orderStatusFlag = false;
			return orderStatusFlag;
		}

		Calendar ca = Calendar.getInstance();
		ca.setTime(startTime);
		ca.add(ca.DATE, -1);
		Long beforeOneTime = ca.getTime().getTime();
		Long endTimeLong = endTime.getTime();
		Long currtTime = (new Date()).getTime();
		if(!(currtTime>=beforeOneTime&&currtTime<=endTimeLong)){
			orderStatusFlag = false;
		}
		Calendar ca1 = Calendar.getInstance();
		ca1.setTime(startTime);
		ca1.add(ca.MINUTE, -30);
		beforeOneTime = ca1.getTime().getTime();


		orderDetailApiVo.setIsHalfHoure(1);
		if(!(currtTime>=beforeOneTime&&currtTime<=endTimeLong)){
			orderDetailApiVo.setIsHalfHoure(0);
		}
		return orderStatusFlag;
	}

	/**
	 *
	 * 校验订单是否能获取智能锁密码
	 *
	 * @author yd
	 * @created 2016年6月23日 上午11:02:07
	 *
	 * @param orderSta
	 * @return
	 */
	private boolean checkOrderStatus(Integer orderSta){

		boolean orderStatusFlag = true;
		if(Check.NuNObj(orderSta)){
			orderStatusFlag = false;
			return orderStatusFlag;
		}
		int orderStatus = orderSta.intValue();
		if(orderStatus == OrderStatusEnum.WAITING_CONFIRM.getOrderStatus()
				||orderStatus == OrderStatusEnum.CANCLE_FORCE.getOrderStatus()
				||orderStatus == OrderStatusEnum.REFUSED.getOrderStatus()
				||orderStatus == OrderStatusEnum.CANCLE_TENANT.getOrderStatus()
				||orderStatus == OrderStatusEnum.CANCLE_TIME.getOrderStatus()
				||orderStatus == OrderStatusEnum.FINISH_COMMON.getOrderStatus()
				||orderStatus == OrderStatusEnum.FINISH_PRE.getOrderStatus()){
			orderStatusFlag = false;
			return orderStatusFlag;
		}
		return orderStatusFlag;
	}

	/**
	 *
	 *判断网关是否在线
	 *
	 * @author yd
	 * @created 2016年6月24日 上午10:08:19
	 *
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@SuppressWarnings("unchecked")
	public  DataTransferObject judgeGatWayStatus(GetSLInfosParam param,DataTransferObject dto) throws UnsupportedEncodingException{

		if(dto == null) dto = new DataTransferObject();
		if(Check.NuNObj(param)
				||Check.NuNStr(param.getHouse_id())
				||Check.NuNStr(param.getOp_name())
				||Check.NuNStr(param.getOp_phone())
				||Check.NuNStr(param.getOp_userid())
				||Check.NuNObject(param.getRooms())){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("请求智能锁门锁参数错误");
			LogUtil.error(logger, "请求智能锁门锁参数错误param={}", param.toString());
			return dto;
		}
		StringBuffer getUrl = new StringBuffer(SMART_LOCKS_GETLOCKSINFOS+"?op_phone="+param.getOp_phone());
		getUrl.append("&op_userid="+param.getOp_userid()).append("&op_name="+param.getOp_name())
		.append("&house_id="+param.getHouse_id()).append("&rooms=["+URLEncoder.encode("\""+param.getHouse_id()+"\"","UTF-8")+"]");
		//获取智能锁

		LogUtil.info(logger, "校验智能锁状态getUrl={}", getUrl.toString());
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap =  (Map<String, String>) JsonEntityTransform.json2Map(CloseableHttpUtil.sendGet(getUrl.toString(), null));
		if(!Check.NuNMap(paramMap)){
			LogUtil.info(logger, "判断智能锁门锁状态返回paramMap={}", paramMap.toString());

			Object errNo =paramMap.get("ErrNo");
			if(Check.NuNObj(errNo)||(int)errNo != SmartErrNoEnum.SUCCESS.getCode()){
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(paramMap.get("ErrMsg"));
				return dto;
			}
			Map<String, String> baseMap = new HashMap<String, String>();
			Object locks = paramMap.get("locks");
			if(!Check.NuNObj(locks)){
				List<HashMap> list = JsonEntityTransform.json2ObjectList(JsonEntityTransform.Object2Json(locks), HashMap.class);
				if(!Check.NuNCollection(list)){
					dto.putValue("smartMap", list.get(0));
				}
			}

		}

		return dto;

	}

	/**
	 * 处理当前的房东电话信息
	 * @author afi
	 * @param detailVo
	 * @param apiVo
	 * @throws Exception
	 */
	private void dealLandTel(OrderDetailVo detailVo,OrderDetailApiVo apiVo) throws Exception{
		if(Check.NuNObjs(detailVo,apiVo)){
			return;
		}
		CustomerVo landlord = this.getCustomerVo(detailVo.getLandlordUid());
		LogUtil.info(logger,"用户信息：{}",JsonEntityTransform.Object2Json(landlord));
		if(!Check.NuNObj(landlord)){
			apiVo.setLandlordMobile(landlord.getHostNumber()+","+landlord.getZiroomPhone());
		}

	}

	/**
	 * 获取当前的房东信息
	 * @param uid
	 * @return
	 * @throws Exception
     */
	private CustomerVo getCustomerVo(String uid) throws Exception{
		if (Check.NuNStr(uid)){
			return null;
		}
		//房东信息获取
		String landlordJson=customerMsgManagerService.getCutomerVo(uid);
		CustomerVo landlord=SOAResParseUtil.getValueFromDataByKey(landlordJson, "customerVo", CustomerVo.class);
		return landlord;
	}

	/**
	 * {"loginToken":"33333","uid":"8a9e9a9e543d23f901543d23f9e90000","orderSn":"1605022FDZ6352190804"}
	 * 取消订单提示扣款信息
	 *
	 * @author jixd
	 * @created 2016年5月1日 下午8:30:17
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/${LOGIN_AUTH}/cancleOrderMsg",method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<ResponseSecurityDto> cancleOrderMsg(HttpServletRequest request){
		try{
			String paramJson = (String) request.getAttribute(ConstDef.DECRYPT_PARAM_ATTRIBUTE);
			OrderApiRequest requestDto = JsonEntityTransform.json2Object(paramJson,OrderApiRequest.class);
			if(Check.NuNStr(requestDto.getOrderSn())){
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("订单号为空"),HttpStatus.OK);
			}
			CanclOrderRequest canclOrderRequest = new CanclOrderRequest();
			canclOrderRequest.setUid(requestDto.getUid());
			canclOrderRequest.setOrderSn(requestDto.getOrderSn());

			String resultJson = orderUserService.initCancleOrder(JsonEntityTransform.Object2Json(canclOrderRequest));
			DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(resultJson);
			if(resultDto.getCode() != DataTransferObject.SUCCESS){
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail(resultDto.getMsg()),HttpStatus.OK);
			}
			CancleStrategyVo cancleStrategyVo = SOAResParseUtil.getValueFromDataByKey(resultJson, "cancleInfo", CancleStrategyVo.class);

			Map<String,Object> map = new HashMap<String,Object>();
			String msg = "";
			Integer msgstatus = 0;
			//如果已经支付
			if(cancleStrategyVo.getHasPay() == true){
				int penaltyMoney = cancleStrategyVo.getPenaltyMoney();
				//如果产生违约金
				if(penaltyMoney >0){
					double penaltyMoneyReal = penaltyMoney/100.00;
					int last = cancleStrategyVo.getHasPayMoney() + cancleStrategyVo.getCouponMoney()-cancleStrategyVo.getPenaltyMoney() - ValueUtil.getintValue(cancleStrategyVo.getUserComm());
					//获取剩余和已经支付金额的最小值
					double returnMoney = (ValueUtil.getMin(last,cancleStrategyVo.getHasPayMoney()))/100.00;
					if (ValueUtil.getintValue(cancleStrategyVo.getUserComm()) > 0){
						double userCommMoney = (ValueUtil.getintValue(cancleStrategyVo.getUserComm()))/100.00;
						msg = String.format(OrderConst.MSG_HASPAY_HASPENALTY_COMM,penaltyMoneyReal,userCommMoney,returnMoney);
					}else {
						msg = String.format(OrderConst.MSG_HASPAY_HASPENALTY,penaltyMoneyReal,returnMoney);
					}
					//已支付，发生扣款
					msgstatus = 1;
				}else{
					int last = cancleStrategyVo.getHasPayMoney() + cancleStrategyVo.getCouponMoney();
					//获取剩余和已经支付金额的最小值
					double returnMoney = (ValueUtil.getMin(last,cancleStrategyVo.getHasPayMoney()))/100.00;
					msg = String.format(OrderConst.MSG_HASPAY_NOPENALTY, returnMoney);
					//已支付，未发生扣款
					msgstatus = 2;
				}
			}else{
				//没有支付，不发生扣款
				msg = OrderConst.MSG_NOPAY;
				msgstatus = 0;
			}
			map.put("msgstatus", msgstatus);
			map.put("msg", msg);
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptOK(map),HttpStatus.OK);
		}catch(Exception e){
			LogUtil.error(logger, "cancleOrderMsg is error, e={}",e);
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("服务错误"),HttpStatus.OK);
		}
	}

	/**
	 *
	 * 参数
	 * <p>{"loginToken":"1212235","uid":"1122222","orderSn":"16042847E2OYWL212522"}</p>
	 * 房客端取消订单
	 *
	 * @author jixd
	 * @created 2016年4月30日 下午5:18:01
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/${LOGIN_AUTH}/cancleOrder",method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<ResponseSecurityDto> cancleOrder(HttpServletRequest request){
		try{
			String paramJson = (String) request.getAttribute(ConstDef.DECRYPT_PARAM_ATTRIBUTE);
			OrderApiRequest requestDto = JsonEntityTransform.json2Object(paramJson,OrderApiRequest.class);

			if(Check.NuNStr(requestDto.getOrderSn())){
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("订单号为空"),HttpStatus.OK);
			}

			CanclOrderRequest cancleRequest = new CanclOrderRequest();
			cancleRequest.setUid(requestDto.getUid());
			cancleRequest.setOrderSn(requestDto.getOrderSn());

			String resultJson = orderUserService.cancleOrder(JsonEntityTransform.Object2Json(cancleRequest));
			DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(resultJson);
			if(resultDto.getCode() != DataTransferObject.SUCCESS){
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail(resultDto.getMsg()),HttpStatus.OK);
			}
			//取消成功发邮件
			SendOrderEmailRequest orderEmailRequest=SOAResParseUtil.getValueFromDataByKey(resultJson, "orderEmailRequest", SendOrderEmailRequest.class);
			if(!Check.NuNObj(orderEmailRequest)){
				SendEmailThreadPool.execute(new SendOrderEmailThread(orderEmailRequest,customerMsgManagerService,smsTemplateService));
			}
			String couponSn = resultDto.parseData("couponSn", new TypeReference<String>() {});
			if (!Check.NuNStr(couponSn)){
				//取消成功，释放优惠券 不管成功还是失败，失败会有同步机制
				List<OrderActivityEntity> orderActList = new ArrayList<OrderActivityEntity>();
				OrderActivityEntity orderAct = new OrderActivityEntity();
				orderAct.setAcFid(couponSn);
				orderAct.setAcStatus(CouponStatusEnum.GET.getCode());
				orderActList.add(orderAct);

				//不管是否处理成功
				actCouponService.syncCouponStatus(JsonEntityTransform.Object2Json(orderActList));
			}

			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptOK(resultDto.getData()),HttpStatus.OK);
		}catch(Exception e){
			LogUtil.error(logger, "cancleOrder is error, e={}",e);
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("服务错误"),HttpStatus.OK);
		}
	}




	/**
	 *
	 * <p>初始化退房:</p>
	 * @author lishaochuan
	 * <p>请求示例：<b>auth/orderTen/checkOutOrderMsg</b></p>
	 * 		{"uid":"uid", "orderSn":"1605042NL8KYP8111215"}
	 * <p>返回结果示例:<b>
	 *     {"status":"0","message":"","data":{"msg":"当前属于提前退房，您将支付违约金5.1元，如确认提前退房，请等待房东确认是否存在其他消费","msgstatus":1}}
	 * </b></p>
	 */
	@RequestMapping(value="/${LOGIN_AUTH}/checkOutOrderMsg",method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<ResponseSecurityDto> checkOutOrderMsg(HttpServletRequest request){
		try{
			String paramJson = (String) request.getAttribute(ConstDef.DECRYPT_PARAM_ATTRIBUTE);
			CheckOutOrderApiRequest apiRequest = JsonEntityTransform.json2Object(paramJson, CheckOutOrderApiRequest.class);
			if(Check.NuNStr(apiRequest.getOrderSn())){
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("订单号为空"),HttpStatus.OK);
			}

			CheckOutOrderRequest checkOutRequest = new CheckOutOrderRequest();
			checkOutRequest.setUid(apiRequest.getUid());
			checkOutRequest.setOrderSn(apiRequest.getOrderSn());

			String resultJson = orderUserService.initCheckOutOrder(JsonEntityTransform.Object2Json(checkOutRequest));
			DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(resultJson);
			if(resultDto.getCode() != DataTransferObject.SUCCESS){
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail(resultDto.getMsg()),HttpStatus.OK);
			}
			CheckOutStrategyVo checkOutStrategyVo = SOAResParseUtil.getValueFromDataByKey(resultJson, "checkOutInfo", CheckOutStrategyVo.class);

			Map<String,Object> map = new HashMap<String,Object>();
			Integer msgstatus = 0;
			String msg = "";
			if (checkOutStrategyVo.getIsEarly()) {
				// 提前退房
				msgstatus = 1;
				int penaltyMoney = checkOutStrategyVo.getPenaltyMoney();
				double penaltyMoneyReal = penaltyMoney/100.00;
                if (checkOutStrategyVo.getIsLock() == YesOrNoEnum.YES.getCode()){
                    msg = String.format(OrderConst.MSG_EARLY_CHECKOUT_LOCK, penaltyMoneyReal);
                }else {
					if (ValueUtil.getintValue(checkOutStrategyVo.getUserComm()) > 0){
						msg = String.format(OrderConst.MSG_EARLY_CHECKOUT_COMM, penaltyMoneyReal,checkOutStrategyVo.getUserComm()/100.00);
					}else {
						msg = String.format(OrderConst.MSG_EARLY_CHECKOUT, penaltyMoneyReal);
					}
                }

			}else{
				// 正常退房
				msgstatus = 2;
				msg = OrderConst.MSG_NORMAL_CHECKOUT;
			}
			map.put("msgstatus", msgstatus);
			map.put("msg", msg);
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptOK(map), HttpStatus.OK);
		}catch(Exception e){
			LogUtil.error(logger, "checkOutOrderMsg is error, e={}",e);
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("服务错误"),HttpStatus.OK);
		}

	}


	/**
	 *
	 * <p>退房:</p>
	 * @author lishaochuan
	 * <p>请求示例：<b>auth/orderTen/checkOutOrder</b></p>
	 * 		{"uid":"uid","orderSn":"1605042NL8KYP8111215"}
	 * <p>返回结果示例:<b>
	 * 		{"status":"0","message":"","data":{}}
	 * </b></p>
	 */
	@RequestMapping(value="/${LOGIN_AUTH}/checkOutOrder",method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<ResponseSecurityDto> checkOutOrder(HttpServletRequest request){
		try{
			String paramJson = (String) request.getAttribute(ConstDef.DECRYPT_PARAM_ATTRIBUTE);
			CheckOutOrderApiRequest apiRequest = JsonEntityTransform.json2Object(paramJson, CheckOutOrderApiRequest.class);
			if(Check.NuNStr(apiRequest.getOrderSn())){
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("订单号为空"),HttpStatus.OK);
			}

			CheckOutOrderRequest checkOutRequest = new CheckOutOrderRequest();
			checkOutRequest.setUid(apiRequest.getUid());
			checkOutRequest.setOrderSn(apiRequest.getOrderSn());

			String resultJson = orderUserService.checkOutOrder(JsonEntityTransform.Object2Json(checkOutRequest));
			DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(resultJson);
			//退房成功 ，发邮件
			if(resultDto.getCode()==DataTransferObject.SUCCESS){
				SendOrderEmailRequest orderEmailRequest=SOAResParseUtil.getValueFromDataByKey(resultJson, "orderEmail", SendOrderEmailRequest.class);
				if(!Check.NuNObj(orderEmailRequest)){
					SendEmailThreadPool.execute(new SendOrderEmailThread(orderEmailRequest,customerMsgManagerService,smsTemplateService));
				}
			}
			return new ResponseEntity<>(ResponseSecurityDto.responseEncrypt(resultDto), HttpStatus.OK);
		}catch(Exception e){
			LogUtil.error(logger, "checkOutOrder is error, e={}",e);
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("服务错误"),HttpStatus.OK);
		}

	}


	/**
	 * <p>描述:</p>
	 * <p>&nbsp; &nbsp; &nbsp; &nbsp;<b>房客端确认额外消费</b></p>
	 * <p>请求示例：<b>/orderTen/ea61d2/showList</b></p>
	 * <p>请求参数：<b>{"loginToken":"33333","uid":"1122222","orderSn":"1605042NL8KYP8111215"}</b></p>
	 *
	 * @author jixd
	 * @created 2016年5月4日
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/${LOGIN_AUTH}/confirmOtherMoney",method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<ResponseSecurityDto> confirmOtherMoney(HttpServletRequest request){
		try {
			String paramJson = (String) request.getAttribute(ConstDef.DECRYPT_PARAM_ATTRIBUTE);
			CheckOutOrderApiRequest requestDto = JsonEntityTransform.json2Object(paramJson,CheckOutOrderApiRequest.class);


			if(Check.NuNStr(requestDto.getOrderSn())){
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("订单号为空"),HttpStatus.OK);
			}
			ConfirmOtherMoneyRequest confirmRequest = new ConfirmOtherMoneyRequest();
			confirmRequest.setUid(requestDto.getUid());
			confirmRequest.setOrderSn(requestDto.getOrderSn());

			String resultJson = orderUserService.confirmOtherMoney(JsonEntityTransform.Object2Json(confirmRequest));
			DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(resultJson);
			if(resultDto.getCode() != DataTransferObject.SUCCESS){
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail(resultDto.getMsg()),HttpStatus.OK);
			}
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptOK(resultDto.getData()),HttpStatus.OK);
		}catch(Exception e){
			LogUtil.error(logger, "confirmOtherMoney is error, e={}",e);
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("服务错误"),HttpStatus.OK);
		}
	}


	/**
	 * 获取当前的四种订单的数量信息
	 * @author afi
	 * @param request
	 * @return
     */
	@RequestMapping(value="/${LOGIN_AUTH}/showOrderNum",method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<ResponseSecurityDto> showOrderNum(HttpServletRequest request){
		try{
			String uid = getUserId(request);
			if(Check.NuNStr(uid)){
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("没有权限"),HttpStatus.OK);
			}
			String resultJson = orderCommonService.getOrderCount4User(uid);
			DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(resultJson);
			if(resultDto.getCode() != DataTransferObject.SUCCESS){
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail(resultDto.getMsg()),HttpStatus.OK);
			}
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptOK(resultDto.getData()),HttpStatus.OK);
		}catch(Exception e){
			LogUtil.error(logger, "showOrderNum is error, e={}",e);
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("服务错误"),HttpStatus.OK);
		}

	}


	/**
	 * <p>描述:</p>
	 * <p>&nbsp; &nbsp; &nbsp; &nbsp;<b>查询房客端订单列表</b></p>
	 * <p>请求示例：<b>/orderTen/ea61d2/showList</b></p>
	 * <p>请求参数：<b>{"loginToken":"33333","uid":"1122222","limit":50,"page":1,"tenantOrderStatus":1}</b></p>
	 * 显示房客端订单列表
	 *
	 * @author jixd
	 * @created 2016年4月30日 下午5:34:19
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/${LOGIN_AUTH}/showList",method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<ResponseSecurityDto> showOrderList(HttpServletRequest request){
		try{
			String uid = getUserId(request);
			if(Check.NuNStr(uid)){
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("没有权限"),HttpStatus.OK);
			}
			String paramJson = (String) request.getAttribute(ConstDef.DECRYPT_PARAM_ATTRIBUTE);
			if(Check.NuNStr(paramJson)){
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("参数为空"),HttpStatus.OK);
			}

			OrderApiRequest requestDto = JsonEntityTransform.json2Object(paramJson,OrderApiRequest.class);
			if (Check.NuNObj(requestDto)){
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("参数为空"),HttpStatus.OK);
			}

			if(Check.NuNStr(requestDto.getUid())){
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("没有权限"),HttpStatus.OK);
			}

			TenantOrderTypeEnum tenantOrderTypeEnum =TenantOrderTypeEnum.getTenantOrderTypeEnumByCode(requestDto.getTenantOrderStatus());
			if(Check.NuNObj(tenantOrderTypeEnum)){
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("没有状态"),HttpStatus.OK);
			}

			OrderRequest orderRequest = new OrderRequest();
			orderRequest.setUserUid(requestDto.getUid());
			//用户请求
			orderRequest.setRequestType(RequestTypeEnum.TENANT.getRequestType());
			orderRequest.setTenantOrderType(requestDto.getTenantOrderStatus());
			orderRequest.setPage(requestDto.getPage());
			orderRequest.setLimit(requestDto.getLimit());

			String resultJson = orderCommonService.getOrderList(JsonEntityTransform.Object2Json(orderRequest));
			DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(resultJson);
			if(resultDto.getCode() != DataTransferObject.SUCCESS){
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail(resultDto.getMsg()),HttpStatus.OK);
			}
			List<OrderInfoVo> orderDetailList = SOAResParseUtil.getListValueFromDataByKey(resultJson, "orderHouseList", OrderInfoVo.class);

			Integer total = SOAResParseUtil.getValueFromDataByKey(resultJson, "size", Integer.class);
			List<OrderItemApiVo> itemList = new ArrayList<OrderItemApiVo>();
			warpOrderList(orderDetailList,itemList,uid);
			Map<String,Object> resultMap = new HashMap<String,Object>();
			resultMap.put("total", total);
			resultMap.put("orderList", itemList);
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptOK(resultMap),HttpStatus.OK);
		}catch(Exception e){
			LogUtil.error(logger, "showOrderList is error, e={}",e);
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("服务错误"),HttpStatus.OK);
		}
	}


	/**
	 *
	 * 民宿和自如驿未入住订单列表
	 *
	 * @author zl
	 * @created 2017年5月5日 下午6:44:46
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/${LOGIN_AUTH}/unCheckinMsYzOrderList",method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<ResponseSecurityDto> unCheckinMsYzOrderList(HttpServletRequest request){
		try{

			String uid = getUserId(request);

			Header header = getHeader(request);

			if(Check.NuNStr(uid)){
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("没有权限"),HttpStatus.OK);
			}
			String paramJson = (String) request.getAttribute(ConstDef.DECRYPT_PARAM_ATTRIBUTE);
			if(Check.NuNStr(paramJson)){
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("参数为空"),HttpStatus.OK);
			}

			OrderApiRequest requestDto = JsonEntityTransform.json2Object(paramJson,OrderApiRequest.class);
			if (Check.NuNObj(requestDto)){
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("参数为空"),HttpStatus.OK);
			}
			
			Map<String,Object> resultMap =null;
			String misuMapStr = null;

			//通过uid 去redis拿用户数据   这里不再做token校验  
			String key  = RedisKeyConst.getUnCheckinMsYzOrderListKey(uid);
			try {
				misuMapStr =this.redisOperations.get(key);
				if(!Check.NuNStr(misuMapStr)){
					resultMap = (Map<String, Object>) JsonEntityTransform.json2Map(misuMapStr);
				}
			} catch (Exception e) {
				LogUtil.error(logger, "【民宿和自如驿未入住订单列表】redis错误:key={}",key);
			}
			
			if(!Check.NuNMap(resultMap)){
				
				LogUtil.info(LOGGER, "key={},返回数据：{}",key, JsonEntityTransform.Object2Json(resultMap));
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptOK(resultMap),HttpStatus.OK);
			}
	

			requestDto.setUid(uid);


			//房客端 待入住+已入住+已经退房且未评价 订单
			OrderRequest orderRequest = new OrderRequest();
			orderRequest.setUserUid(requestDto.getUid());
			//房客待入住
			orderRequest.setRequestType(RequestTypeEnum.TENANT.getRequestType());
			orderRequest.setTenantOrderType(TenantOrderTypeEnum.ACTIVE.getCode());
			orderRequest.setLimit(300);

			String resultJson = orderCommonService.getOrderList(JsonEntityTransform.Object2Json(orderRequest));
			DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(resultJson);
			if(resultDto.getCode() != DataTransferObject.SUCCESS){
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail(resultDto.getMsg()),HttpStatus.OK);
			}

			List<OrderInfoVo> orderDetailList = SOAResParseUtil.getListValueFromDataByKey(resultJson, "orderHouseList", OrderInfoVo.class);
			Integer total = SOAResParseUtil.getValueFromDataByKey(resultJson, "size", Integer.class);


			List<OrderItemApiVo> itemList = new ArrayList<OrderItemApiVo>();
			warpOrderList(orderDetailList,itemList,uid);

			//协商取消、已经退房超时未评价订单
			Iterator<OrderItemApiVo> iterator = itemList.iterator();
			int n = 0;
			while (iterator.hasNext()) {
				OrderItemApiVo orderItemApiVo = iterator.next();

				if (orderItemApiVo.getOrderStatus() == OrderStatusEnum.FINISH_NEGOTIATE.getOrderStatus()
						|| (orderItemApiVo.getOrderStatus() >= OrderStatusEnum.CHECKING_OUT.getOrderStatus()
								&& orderItemApiVo.getPjStatus() != EvaluateClientBtnStatuEnum.C_EVAL
										.getEvaStatuCode())) {
					iterator.remove();
					n++;
				}

			}

			total = total-n;

			LogUtil.info(LOGGER, "房客订单={}", JsonEntityTransform.Object2Json(itemList));

			List<ZiruyiOrderListVo> ziruyiList = getZiruyiUncheckinOrderList(requestDto.getUid(),header);

			List<OrderItemApiVo> resultMinsuList = new ArrayList<OrderItemApiVo>();
			List<ZiruyiOrderListVo> resultZiruyiList = new ArrayList<ZiruyiOrderListVo>();

			List<Object> resultList = combineOrderList(itemList, ziruyiList);

			if (!Check.NuNCollection(resultList)) {
				if (requestDto.getLimit() == 1 && resultList.size() > 1) {
					resultList = resultList.subList(0, 1);
				}

				for (int i = 0; i < resultList.size(); i++) {
					OrderItemApiVo minsuOrder;
					ZiruyiOrderListVo ziruyiOrder;
					if (resultList.get(i) instanceof OrderItemApiVo) {
						minsuOrder = (OrderItemApiVo) resultList.get(i);
						minsuOrder.setSortIndex(i);
						resultMinsuList.add(minsuOrder);
					} else if (resultList.get(i) instanceof ZiruyiOrderListVo) {
						ziruyiOrder = (ZiruyiOrderListVo) resultList.get(i);
						ziruyiOrder.setSortIndex(i);
						resultZiruyiList.add(ziruyiOrder);
					}
				}

			}

			Map<String,Object> misuMap = new HashMap<String,Object>();
			misuMap.put("total", total);
			misuMap.put("orderList", resultMinsuList);

			Map<String,Object> ziruyiMap = new HashMap<String,Object>();
			ziruyiMap.put("total", ziruyiList.size());
			ziruyiMap.put("orderList", resultZiruyiList);

			resultMap = new HashMap<String,Object>();
			resultMap.put("minsuOrder", misuMap);
			resultMap.put("ziruyiOrder", ziruyiMap);

			LogUtil.info(LOGGER, "返回数据：{}", JsonEntityTransform.Object2Json(resultMap));
			
			try {
				redisOperations.setex(key,RedisKeyConst.CUSTOMERVO_LOCK_CACHE_TIME_SHORT, JsonEntityTransform.Object2Json(resultMap));
			} catch (Exception e) {
                LogUtil.error(LOGGER, "【民宿和自如驿未入住订单列表-redis异常】uid={},key={},e:{}",uid,key,e);
			}
		
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptOK(resultMap),HttpStatus.OK);
		}catch(Exception e){
			LogUtil.error(logger, "unCheckinMsYzOrderList is error, e={}",e);
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("服务错误"),HttpStatus.OK);
		}
	}

	/**
	 *
	 * 合并订单并排序
	 *
	 * @author zl
	 * @created 2017年5月8日 下午4:25:36
	 *
	 * @param minsuOrderList
	 * @param ziruyiOrderList
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List<Object> combineOrderList(List<OrderItemApiVo> minsuOrderList,List<ZiruyiOrderListVo> ziruyiOrderList){
		List<Object> resultList = new ArrayList<>();
		if (!Check.NuNCollection(minsuOrderList)) {
			for (OrderItemApiVo order : minsuOrderList) {
				order.setOrderProjectType(OrderProjectTypeEnum.MINSU_ORDER.getCode());
				resultList.add(order);
			}
		}
		if (!Check.NuNCollection(ziruyiOrderList)) {
			for (ZiruyiOrderListVo order : ziruyiOrderList) {
				order.setOrderProjectType(OrderProjectTypeEnum.ZIRUYI_ORDER.getCode());
				resultList.add(order);
			}
		}

		if (resultList.size()>1) {

			Collections.sort(resultList, new Comparator(){

				@Override
				public int compare(Object o1, Object o2) {
					OrderItemApiVo minsuOrder ;
					ZiruyiOrderListVo ziruyiOrder ;
					OrderItemApiVo minsuOrder1 ;
					ZiruyiOrderListVo ziruyiOrder1 ;

					final String dateFormatPattern ="yyyy-MM-dd";

					try {
						if(o1 instanceof OrderItemApiVo){
							minsuOrder=(OrderItemApiVo)o1;
							if(o2 instanceof ZiruyiOrderListVo){
								ziruyiOrder=(ZiruyiOrderListVo)o2;
								return DateUtil.parseDate(minsuOrder.getStartTimeStr(), dateFormatPattern).compareTo(DateUtil.parseDate(ziruyiOrder.getStartDate(), dateFormatPattern));
							}else{
								minsuOrder1=(OrderItemApiVo)o2;
								return DateUtil.parseDate(minsuOrder.getStartTimeStr(), dateFormatPattern).compareTo(DateUtil.parseDate(minsuOrder1.getStartTimeStr(), dateFormatPattern));
							}
						}else{
							ziruyiOrder1=(ZiruyiOrderListVo)o1;
							if(o2 instanceof ZiruyiOrderListVo){
								ziruyiOrder=(ZiruyiOrderListVo)o2;
								return DateUtil.parseDate(ziruyiOrder1.getStartDate(), dateFormatPattern).compareTo(DateUtil.parseDate(ziruyiOrder.getStartDate(), dateFormatPattern));

							}else{
								minsuOrder=(OrderItemApiVo)o2;
								return DateUtil.parseDate(ziruyiOrder1.getStartDate(), dateFormatPattern).compareTo(DateUtil.parseDate(minsuOrder.getStartTimeStr(), dateFormatPattern));

							}
						}
					} catch (Exception e) {
						LogUtil.error(logger, "合并订单列表异常，arg1={}，arg2={},e={}", JsonEntityTransform.Object2Json(o1), JsonEntityTransform.Object2Json(o2),e);
					}

					return 0;
				}

			});

		}

		return resultList;
	}


	/**
	 *
	 * 获取自如驿待入住订单列表
	 *
	 * @author zl
	 * @created 2017年5月5日 下午2:18:56
	 *
	 * @param uid
	 * @return
	 */
	private List<ZiruyiOrderListVo> getZiruyiUncheckinOrderList(String uid,Header header){

		List<ZiruyiOrderListVo> list = new ArrayList<>();

		if (Check.NuNStr(uid)) {
			return list;
		}

		try {
			StringBuffer key = new StringBuffer();
			key.append(RedisKeyConst.ZIRUYI_KEY_ORDER)
					.append("ZIRUYI_UNCHECKIN_ORDERLIST")
					.append(uid);

			String resJson = null;
			try {
				resJson = redisOperations.get(key.toString());
			} catch (Exception e) {
				LogUtil.error(LOGGER, "redis错误,e:{}", e);
			}

			if (Check.NuNStr(resJson)) {
				String orderListJson = null;
				try {
					Map<String, String> param = new HashMap<String, String>();
					param.put("uuid", uid);

					Map<String, String> head = new HashMap<String, String>();
//				head.put("Accept","application/json");

					if (!Check.NuNObj(header)) {
//					head.put("imei", header.getImei());//设备唯一标识;
////					head.put("timestamp", String.valueOf(System.currentTimeMillis()));//时间戳;
//					head.put("phoneModel", header.getPhoneModel());//手机型号;
//					head.put("l", "zh");//语言版本,示例：en/zh;
//					head.put("appVersionStr", String.valueOf(header.getVersionCode()));//应用版本号;
//					head.put("sysVersionStr", header.getOsVersion());//系统版本号;
//					head.put("source", header.getChannelName());//来源;
//					head.put("cityCode", header.getLocationCityCode());//城市编码;
						head.put("uuid", uid);//用户uuid

					}

					long t1 = System.currentTimeMillis();

					orderListJson = CloseableHttpUtil.sendCustomPost(ZIRUYI_UNCHECKIN_ORDERLIST_URL, uid, head, 5000, 2000, 2000, 3);

					if (Check.NuNStr(orderListJson)) {
						LogUtil.error(LOGGER, "【getZiruyiUncheckinOrderList】自如驿未入住订单接口请求异常:URL={},uuid={}", ZIRUYI_UNCHECKIN_ORDERLIST_URL, uid);
						return list;
					}

					LogUtil.info(LOGGER, "【getZiruyiUncheckinOrderList】自如驿未入住订单接口耗时{}ms返回数据，result={}", (System.currentTimeMillis() - t1), orderListJson);

					JSONObject listJsonObject = JSONObject.parseObject(orderListJson);

					if (Check.NuNObj(listJsonObject) || !listJsonObject.containsKey("error_code") || !"200".equals(listJsonObject.getString("error_code"))) {
						LogUtil.error(LOGGER, "【getZiruyiUncheckinOrderList】自如驿未入住订单接口请求异常:URL={},uid={}", ZIRUYI_UNCHECKIN_ORDERLIST_URL, uid);
						return list;
					}

					if (listJsonObject.containsKey("data")) {
						list = JSONObject.parseArray(listJsonObject.getJSONArray("data").toJSONString(), ZiruyiOrderListVo.class);
						try {
							redisOperations.setex(key.toString(), RedisKeyConst.ZIRUYI_ORDER_CACHE_TIME, listJsonObject.getJSONArray("data").toJSONString());
						} catch (Exception e) {
							LogUtil.error(LOGGER, "redis错误,e:{}", e);
						}
					}


				} catch (Exception e) {
					LogUtil.error(LOGGER, "【getZiruyiUncheckinOrderList】自如驿未入住订单接口请求异常:URL={},uid={},result={}", ZIRUYI_UNCHECKIN_ORDERLIST_URL, uid, orderListJson);
					return list;
				}
			} else {
				list = JSONObject.parseArray(resJson, ZiruyiOrderListVo.class);
			}
		} catch (Exception e) {
			LogUtil.error(LOGGER, "【getZiruyiUncheckinOrderList】处理自如驿未入住订单异常:URL={},uid={},e={}", ZIRUYI_UNCHECKIN_ORDERLIST_URL, uid, e);
		}
		
		return list;		
	}
	


	/**
	 * 获取当前的最新的一条申请的订单
	 * @param request
	 * @return
     */
	@RequestMapping(value="/${LOGIN_AUTH}/showLast",method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<ResponseSecurityDto> showOrderLast(HttpServletRequest request){
		try{
			String uid = getUserId(request);
			if(Check.NuNStr(uid)){
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("没有权限"),HttpStatus.OK);
			}
			String resultJson = orderCommonService.getOrderLast(uid);
			DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(resultJson);
			if(resultDto.getCode() != DataTransferObject.SUCCESS){
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail(resultDto.getMsg()),HttpStatus.OK);
			}
			OrderInfoVo orderInfoVo = SOAResParseUtil.getValueFromDataByKey(resultJson, "orderInfoVo", OrderInfoVo.class);

			//评价关闭入口时间（天）
			String timeStrJson = cityTemplateService.getTextValue(null, String.valueOf(EvaluateRulesEnum.EvaluateRulesEnum003.getValue()));
			DataTransferObject limitDto = JsonEntityTransform.json2DataTransferObject(timeStrJson);
			String limitStr = null;
			if(limitDto.getCode() == DataTransferObject.SUCCESS){
				limitStr = limitDto.parseData("textValue", new TypeReference<String>() {});
			}else {
				LogUtil.error(LOGGER,"获取评价关闭入口异常：rst：{}",limitStr);
			}
			//将当前的时间住转化成天
			int  jumpDay = ValueUtil.getintValue(limitStr);
			OrderItemApiVo voOrg = this.trans2ItemVO(orderInfoVo,jumpDay,uid);
			OrderItemLastApiVo item = null;
			if (!Check.NuNObj(orderInfoVo)){
				item = new OrderItemLastApiVo();
				BeanUtils.copyProperties(voOrg,item);
			}
			if (!Check.NuNObj(orderInfoVo)){
				item.setHouseAddr(orderInfoVo.getHouseAddr());
				CustomerVo landlord = this.getCustomerVo(orderInfoVo.getLandlordUid());
				if (!Check.NuNObj(landlord)){
					item.setLandlordMobile(landlord.getHostNumber()+","+landlord.getZiroomPhone());
				}else {
					item.setLandlordMobile("");
				}
			}
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptOK(item),HttpStatus.OK);
		}catch(Exception e){
			LogUtil.error(logger, "showLast is error, e={}",e);
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("服务错误"),HttpStatus.OK);
		}
	}
	
	

	/**
	 * 获取当前的用户有效智能锁的数量
	 * @author afi
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/${LOGIN_AUTH}/lock",method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<ResponseSecurityDto> lock(HttpServletRequest request){
		try{
			String paramJson = (String) request.getAttribute(ConstDef.DECRYPT_PARAM_ATTRIBUTE);
			Map<String,String> par = JsonEntityTransform.json2Object(paramJson, Map.class);
			String uid = par.get("uid");
			if(Check.NuNObj(uid)){
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("没有权限"),HttpStatus.OK);
			}

			String resultJson = orderCommonService.countLock(uid);
			DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(resultJson);
			if(resultDto.getCode() != DataTransferObject.SUCCESS){
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail(resultDto.getMsg()),HttpStatus.OK);
			}
			//获取当前的订单的数量
			Long total = SOAResParseUtil.getLongFromDataByKey(resultJson, "total");
			Map<String,Object> resultMap = new HashMap<String,Object>();
			resultMap.put("total", total);
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptOK(resultMap),HttpStatus.OK);
		}catch(Exception e){
			LogUtil.error(logger, "lock is error, e={}",e);
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("服务错误"),HttpStatus.OK);
		}
	}



	/**
	 * 获取未登陆的用户中心订单列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/${NO_LGIN_AUTH}/getUOList",method=RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<ResponseSecurityDto> getUOList(HttpServletRequest request,BaseParamDto parDto){
		try{
			if(Check.NuNObj(parDto)){
				return new ResponseEntity<>(ResponseSecurityDto.responseUnEncryptFail("par is null"),HttpStatus.OK);
			}
			if(Check.NuNStr(parDto.getUid())){
				return new ResponseEntity<>(ResponseSecurityDto.responseUnEncryptFail("uid is null"),HttpStatus.OK);
			}
			OrderRequest orderRequest = new OrderRequest();
			orderRequest.setUserUid(parDto.getUid());
			//用户请求
			orderRequest.setRequestType(1);
			orderRequest.setPage(parDto.getPage());
			orderRequest.setLimit(parDto.getLimit());
			String resultJson = orderCommonService.getOrderListByCondiction(JsonEntityTransform.Object2Json(orderRequest));
			DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(resultJson);
			if(resultDto.getCode() != DataTransferObject.SUCCESS){
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail(resultDto.getMsg()),HttpStatus.OK);
			}
			List<OrderInfoVo> orderDetailList = SOAResParseUtil.getListValueFromDataByKey(resultJson, "orderHouseList", OrderInfoVo.class);
			List<OrderUCVo> voList = new ArrayList<OrderUCVo>();
			//将订单列表转化
			this.transOrderList(orderDetailList, voList);

			Integer total = SOAResParseUtil.getValueFromDataByKey(resultJson, "size", Integer.class);

			Map<String,Object> resultMap = new HashMap<String,Object>();
			resultMap.put("total", total);
			resultMap.put("orderList", voList);
			return new ResponseEntity<>(ResponseSecurityDto.responseUnEncryptOK(resultMap),HttpStatus.OK);
		}catch(Exception e){
			LogUtil.error(logger, "getUOList is error, e={}",e);
			return new ResponseEntity<>(ResponseSecurityDto.responseUnEncryptFail("服务错误"),HttpStatus.OK);
		}
	}

	/** 
	 * 城市名称
	 * @author liyingjie
	 * @created 2016年8月5日 下午6:43:45
	 *@param code
	 */
	private String getCityName(String code){
		String result = "";
		if(Check.NuNStr(code)){
			return result;
		}
		//获取城市
		String cityJson = confCityService.getOpenCityMap();
        DataTransferObject cityDto = JsonEntityTransform.json2DataTransferObject(cityJson);
        Map<String, String> cityMap = cityDto.parseData("map", new TypeReference<Map<String, String>>() {});
        if(!Check.NuNMap(cityMap)){
        	result = cityMap.get(code);
        }
        return result;
	}
	

	/**
	 * 
	 * 订单详情重新封装返回
	 *
	 * @author jixd
	 * @created 2016年5月1日 下午6:43:45
	 *
	 * @param vo
	 * @param apiVo
	 */
	private void wrapOrderDetail(OrderDetailVo vo,OrderDetailApiVo apiVo){
		apiVo.setLandlordUid(vo.getLandlordUid());
		apiVo.setUserUid(vo.getUserUid());
		apiVo.setUserTel(vo.getUserTel());
		apiVo.setUserName(vo.getUserName());
		apiVo.setOrderSn(vo.getOrderSn());
		apiVo.setHouseFid(vo.getHouseFid());
		//获取30分钟超时时间
		apiVo.setExpireTime(ApiDateUtil.getExpireTime(vo.getLastModifyDate(),this.getExpireTime()));
		//应付金额 需要用户支付的金额
		apiVo.setNeedPay(vo.getNeedPay());
		//折扣金额
		apiVo.setDiscountMoney(vo.getDiscountMoney());
		//实际消费金额
		apiVo.setRealMoney(vo.getRealMoney());
		//实际支付金额 支付回调（可能是多个）的和
		apiVo.setPayMoney(vo.getPayMoney());
		//额外消费
		apiVo.setOtherMoney(vo.getOtherMoney());
		//违约金
		apiVo.setPenaltyMoney(vo.getPenaltyMoney());
		//房租金额    
		apiVo.setRentalMoney(vo.getRentalMoney());
		//退款金额
		apiVo.setRefundMoney(vo.getRefundMoney());
		//押金
		apiVo.setDepositMoney(vo.getDepositMoney());

		//优惠券金额
		apiVo.setCouponMoney(vo.getCouponMoney());
		//普通活动金额
		apiVo.setActMoney(vo.getActMoney());
		//城市code
		apiVo.setCityCode(vo.getCityCode());
		//城市名称
        apiVo.setCityName(getCityName(vo.getCityCode()));
		
		apiVo.setRoomFid(vo.getRoomFid());
		apiVo.setRoomName(vo.getRoomName());
		apiVo.setBedFid(vo.getBedFid());
		apiVo.setHouseAddr(vo.getHouseAddr());

		int rentWay = vo.getRentWay();
		apiVo.setRentWay(rentWay);
		if(rentWay == RentWayEnum.HOUSE.getCode()){
			apiVo.setHouseName(vo.getHouseName());
		}else if(rentWay == RentWayEnum.ROOM.getCode()){
			apiVo.setHouseName(vo.getRoomName());
			apiVo.setHouseFid(vo.getRoomFid());
		}else if(rentWay == RentWayEnum.BED.getCode()){
			apiVo.setHouseName(vo.getRoomName());
		}else{
			apiVo.setHouseName(vo.getHouseName());
		}


		//获取当前的用户的佣金

		OrderStatusEnum statusEnum = OrderStatusEnum.getOrderStatusByCode(vo.getOrderStatus());

		int comm = statusEnum.getComm(vo.getUserCommMoney(),vo.getRealUserMoney(),vo);

		apiVo.setUserCommMoney(comm);
//		//用户佣金
//		if(Check.NuNObj(vo.getRealUserMoney()) || vo.getRealUserMoney() == 0){
//			//用户佣金
//			apiVo.setUserCommMoney(vo.getUserCommMoney());
//		}else{
//			apiVo.setUserCommMoney(vo.getRealUserMoney());
//		}

		apiVo.setAccountsStatus(vo.getAccountsStatus());
		//获取规格图片url
		apiVo.setPicUrl(PicUtil.getSpecialPic(picBaseAddrMona, vo.getPicUrl(), list_small_pic));
		apiVo.setOrderStatus(vo.getOrderStatus());
		apiVo.setPayStatus(vo.getPayStatus());

		Map<String, Object> paramMap = vo.getParamMap();
		apiVo.setOtherMoneyDes((String)paramMap.get(OrderParamEnum.OTHER_COST_DES.getCode()));
		apiVo.setStartTimeStr(ApiDateUtil.getDateStr(vo.getStartTime(),vo.getCheckInTime()));
		apiVo.setEndTimeStr(ApiDateUtil.getDateStr(vo.getEndTime(), vo.getCheckOutTime()));
		OrderStatusEnum orderStatusEnum = OrderStatusEnum.getOrderStatusByCode(vo.getOrderStatus());
		if(!Check.NuNObj(orderStatusEnum)){
			apiVo.setOrderStatusName(orderStatusEnum.getShowName(vo));
			apiVo.setOrderStatusShowCode(orderStatusEnum.getShowStatus(vo));
		}
		List<UsualContactEntity> usualContactList = vo.getListUsualContactEntities();
		List<UsualContactVo> resultList = new ArrayList<UsualContactVo>();
		for(UsualContactEntity contactEntity:usualContactList){
			UsualContactVo usualContact = new UsualContactVo();
			usualContact.setConTel(contactEntity.getConTel());
			usualContact.setUserUid(contactEntity.getUserUid());
			usualContact.setConName(contactEntity.getConName());
			usualContact.setCardType(contactEntity.getCardType());
			usualContact.setCardValue(contactEntity.getCardValue());
			resultList.add(usualContact);
		}
		apiVo.setUsualContactList(resultList);
	}

	/**
	 * 获取当前的支付时限
	 * @author  afi
	 * @return
	 */
	private int getExpireTime(){
		/** 封装 查询参数  订单状态  支付状态  30分钟前的时间*/
		String timeStrJson = cityTemplateService.getTextValue(null, String.valueOf(TradeRulesEnum.TradeRulesEnum002.getValue())); //支付时限(分钟)
		DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(timeStrJson);
		if(resultDto.getCode() != DataTransferObject.SUCCESS){
			LogUtil.error(logger, "【取消订单Job】获取支付时限失败,timeStrJson:{}", timeStrJson);
			throw new BusinessException("【取消订单Job】获取支付时限失败");
		}
		int timeLimit = ValueUtil.getintValue(resultDto.getData().get("textValue"));
		if (timeLimit <= 0) {
			timeLimit = 30;
		}
		return timeLimit;
	}
	
	/**
	 * 处理订单详情金额
	 * @author lishaochuan
	 * @create 2016年8月22日上午11:25:18
	 * @param vo
	 * @param apiVo
	 */
    private void dealOrderDetailMoney(OrderDetailVo vo, Integer versionCode, OrderDetailApiVo apiVo) {
        OrderDetailFeeResponse detailFee = new OrderDetailFeeResponse();
        detailFee.setTotalFee(apiVo.getNeedPay());
        //100020 这个版本以后去掉押金
        if (!Check.NuNObj(versionCode) && versionCode > 100019) {
			detailFee.setTotalFee(vo.getNeedPay() - vo.getDepositMoney());
		}
        detailFee.setFeeUnit(OrderFeeConst.FEE_UNIT.getShowName());
		if (apiVo.getOrderStatus() == OrderStatusEnum.CHECKING_OUT.getShowStatus() || apiVo.getOrderStatus() == OrderStatusEnum.CHECKING_OUT_PRE.getShowStatus()
				|| apiVo.getOrderStatus() == OrderStatusEnum.WAITING_EXT.getShowStatus() || apiVo.getOrderStatus() == OrderStatusEnum.WAITING_EXT_PRE.getShowStatus()) {
            this.addItemFee(detailFee, vo, versionCode, apiVo.getRefundMoney(), OrderFeeConst.ORDER_DETAIL_PRE_REFUND);
        } else {
            this.addItemFee(detailFee, vo, versionCode, apiVo.getRefundMoney(), OrderFeeConst.ORDER_DETAIL_REFUND);
        }
		if (!Check.NuNObj(versionCode) && versionCode <= 100019) {
			this.addItemFee(detailFee, vo, versionCode, apiVo.getDepositMoney(), OrderFeeConst.ORDER_DETAIL_DEPOSIT);
		}
		this.addItemFee(detailFee, vo, versionCode, apiVo.getRentalMoney(), OrderFeeConst.ORDER_DETAIL_RENTAL);
        this.addItemFee(detailFee, vo, versionCode, apiVo.getUserCommMoney(), OrderFeeConst.ORDER_DETAIL_USER_COMMISSION);
        this.addItemFee(detailFee, vo, versionCode, apiVo.getDiscountMoney(), OrderFeeConst.ORDER_DETAIL_DISCOUNT);
        this.addItemFee(detailFee, vo, versionCode, apiVo.getPenaltyMoney(), OrderFeeConst.ORDER_DETAIL_PENALTY);
        this.addItemFee(detailFee, vo, versionCode, vo.getCleanMoney(), OrderFeeConst.ORDER_DETAIL_CLEAN);
        this.addItemFee(detailFee, vo, versionCode, apiVo.getCouponMoney(), OrderFeeConst.ORDER_DETAIL_COUPON);
        this.addItemFee(detailFee, vo, versionCode, apiVo.getOtherMoney(), OrderFeeConst.ORDER_DETAIL_OTHER);
        this.addItemFee(detailFee, vo, versionCode, apiVo.getActMoney(), OrderFeeConst.FIRST_ORDER_REDUC);
        apiVo.setDetailFee(detailFee);
	}
	
	/**
	 * 订单详情金额
	 * @author lishaochuan
	 * @create 2016年8月22日下午2:18:07
	 * @param detailFee
	 * @param money
	 */
    private void addItemFee(OrderDetailFeeResponse detailFee, OrderDetailVo vo, Integer versionCode, Integer money, OrderFeeConst feeEnum) {
        if(!Check.NuNObj(money) && money > 0){
			OrderDetailFeeItemResponse item = new OrderDetailFeeItemResponse();
			detailFee.getFeeItemList().add(item);
			item.setName(feeEnum.getShowName());
            //100020 这个版本以后去掉押金
            if (feeEnum == OrderFeeConst.ORDER_DETAIL_RENTAL && (!Check.NuNObj(versionCode) && versionCode > 100019)) {
                String title1 = feeEnum.getTitle1();
				item.setName(title1.replace("{1}", String.valueOf(vo.getHousingDay())));
			}
			item.setColorType(feeEnum.getColorType());
			item.setFee(DataFormat.formatHundredPrice(money));
			item.setIndex(feeEnum.getIndex());
		}
	}


	/**
	 * 将订单信息转化
	 * @author afi
	 * @param orderList
	 * @param voList
	 */
	private void transOrderList(List<OrderInfoVo> orderList,List<OrderUCVo> voList){
		if(Check.NuNObjs(orderList,voList)){
			return;
		}
		//转化订单列表
		for(OrderInfoVo infoVo : orderList){
			OrderUCVo vo = new OrderUCVo();
			vo.setEndTimeStr(infoVo.getEndTimeStr());
			vo.setStartTimeStr(infoVo.getStartTimeStr());
			vo.setCreateTimeStr(DateUtil.dateFormat(infoVo.getCreateTime()));
			vo.setOrderSn(infoVo.getOrderSn());
			vo.setSumMoney(infoVo.getSumMoney());
			vo.setRealMoney(infoVo.getRealMoney());
			Integer orderStatus = infoVo.getOrderStatus();
			OrderStatusEnum statusEnum = OrderStatusEnum.getOrderStatusByCode(orderStatus);
			if(!Check.NuNObj(statusEnum)) {
				vo.setOrderStatus(statusEnum.getShowStatus());
				vo.setOrderStatusName(statusEnum.getShowName());
			}

			voList.add(vo);
		}

	}

	/**
	 * 将订单的信息转化成api对象
	 * @author afi
	 * @param infoVo
	 * @return
     */
	private OrderItemApiVo trans2ItemVO(OrderInfoVo infoVo,int jumpDay,String uid){
		if (Check.NuNObj(infoVo)){
			return null;
		}

		OrderItemApiVo vo = new OrderItemApiVo();
		vo.setLandlordUid(infoVo.getLandlordUid());
		vo.setIsLock(infoVo.getIsLock());
		vo.setEndTimeStr(infoVo.getEndTimeStr());
		vo.setStartTimeStr(infoVo.getStartTimeStr());
		vo.setStartTimeStamp(infoVo.getStartTime().getTime()/1000);
		vo.setOrderSn(infoVo.getOrderSn());
		vo.setNeedPay(infoVo.getNeedPay());
		vo.setHousingDay(infoVo.getHousingDay());
		vo.setCityCode(infoVo.getCityCode());
		vo.setUserName(infoVo.getUserName());
		vo.setOrderEvaStatus(infoVo.getEvaStatus());
		//设置电话为400
		try{
			CustomerVo landlord = this.getCustomerVo(infoVo.getLandlordUid());
			if (!Check.NuNObj(landlord)){
				vo.setLandlordMobile(landlord.getHostNumber()+","+landlord.getZiroomPhone());
			}else {
				vo.setLandlordMobile("");
			}
		}catch (Exception e){

		}
		vo.setHouseAddr(infoVo.getHouseAddr());
		//获取规格图片
		vo.setPicUrl(PicUtil.getSpecialPic(picBaseAddrMona, infoVo.getPicUrl(), list_small_pic));
		Integer evaStatus = infoVo.getEvaStatus();
		Integer orderStatus = infoVo.getOrderStatus();
		vo.setOrderStatus(orderStatus);
		int rentWay = infoVo.getRentWay();
		vo.setRentWay(rentWay);
		
		vo.setRoomName(infoVo.getRoomName());
		//根据出租类型设置返回的房源id
		if(rentWay == RentWayEnum.HOUSE.getCode()){
			vo.setFid(infoVo.getHouseFid());
		}else if(rentWay == RentWayEnum.ROOM.getCode()){
			vo.setFid(infoVo.getRoomFid());
		}else if(rentWay == RentWayEnum.BED.getCode()){
			vo.setFid(infoVo.getRoomFid());
		}else{
			vo.setFid(infoVo.getHouseFid());
			vo.setHouseName(infoVo.getHouseName());
		}
		vo.setHouseName(infoVo.transName());

		//获取当前的订单状态
		OrderStatusEnum orderStatusEnum = OrderStatusEnum.getOrderStatusByCode(vo.getOrderStatus());
		if(!Check.NuNObj(orderStatusEnum)){
			vo.setOrderStatusName(orderStatusEnum.getShowName(infoVo));
			vo.setOrderStatusShowCode(orderStatusEnum.getShowStatus(infoVo));
		}

		int evaStatusShow =  YesOrNoOrFrozenEnum.FROZEN.getCode();
		//如果是待评价 和房东已评价 则显示 去评价
		if(evaStatus == OrderEvaStatusEnum.WAITINT_EVA.getCode() || evaStatus == OrderEvaStatusEnum.LANLORD_EVA.getCode()){
			//在超时时间之内的
			if ((Check.NuNObj(infoVo.getRealEndTime()) && (infoVo.getOrderStatus()==OrderStatusEnum.CHECKED_IN.getOrderStatus()||infoVo.getOrderStatus()==OrderStatusEnum.CHECKED_IN_BILL.getOrderStatus()))
				||(!Check.NuNObj(infoVo.getRealEndTime()) && new Date().before(DateSplitUtil.jumpDate(infoVo.getRealEndTime(),jumpDay)))){
				evaStatusShow = YesOrNoEnum.NO.getCode();
			}
		}else if(evaStatus == OrderEvaStatusEnum.UESR_HVA_EVA.getCode() || evaStatus == OrderEvaStatusEnum.ALL_EVA.getCode()){
			evaStatusShow = YesOrNoEnum.YES.getCode();
		}
		//兼容iso的展示问题，以后这个字段只能有0 1 其他的废掉了
		if (evaStatusShow == YesOrNoOrFrozenEnum.FROZEN.getCode()){
			evaStatusShow = YesOrNoOrFrozenEnum.YES.getCode();
		}
		//设置当前的评价状态
		vo.setEvaStatus(evaStatusShow);
		
		
		EvaluateRequest evaluateRequest  = new EvaluateRequest();
		evaluateRequest.setOrderSn(infoVo.getOrderSn());
		evaluateRequest.setEvaUserType(UserTypeEnum.All.getUserType());
		evaluateRequest.setEvaUserUid(uid);
		
		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(evaluateOrderService.queryEvaluateByOrderSn(JsonEntityTransform.Object2Json(evaluateRequest)));

		if(dto.getCode()==DataTransferObject.SUCCESS){
			
			Map<String, Object> map = dto.parseData("evaluateMap", new TypeReference<Map<String, Object>>() {
			});
			
			LogUtil.debug(LOGGER, "评价查询结果：orderSn={},evalMap={}", infoVo.getOrderSn(),JsonEntityTransform.Object2Json(map));
			
			List<EvaluateOrderEntity>  listOrderEvaluateOrderEntities = null;
			if(map.get("listOrderEvaluateOrder") !=null){
				listOrderEvaluateOrderEntities = JsonEntityTransform.json2ObjectList(JsonEntityTransform.Object2Json(map.get("listOrderEvaluateOrder")), EvaluateOrderEntity.class);
			}
			if(!Check.NuNCollection(listOrderEvaluateOrderEntities)){
				for (EvaluateOrderEntity evaluateOrderEntity : listOrderEvaluateOrderEntities) {
					
					if(evaluateOrderEntity.getEvaUserType().intValue() == EvaluateUserEnum.LAN.getCode()){
						infoVo.setLandEvaStatu(evaluateOrderEntity.getEvaStatu());
						if(evaluateOrderEntity.getEvaStatu()==EvaluateStatuEnum.ONLINE.getEvaStatuCode()){
							infoVo.setLandEvalTime(evaluateOrderEntity.getLastModifyDate());
						}
					}else if(evaluateOrderEntity.getEvaUserType().intValue() == EvaluateUserEnum.TEN.getCode()){
						infoVo.setTenantEvaStatu(evaluateOrderEntity.getEvaStatu());
						if(evaluateOrderEntity.getEvaStatu()==EvaluateStatuEnum.ONLINE.getEvaStatuCode()){
							infoVo.setTenantEvalTime(evaluateOrderEntity.getLastModifyDate());
						}
					}else if(evaluateOrderEntity.getEvaUserType().intValue() == EvaluateUserEnum.LAN_REPLY.getCode()){
						infoVo.setLandReplyTime(evaluateOrderEntity.getCreateTime());
					}  
					
				}

			} 
			
		}
		
		 EvaluateClientBtnStatuEnum pjStatusEnum = evalOrderService.getPjStatusEnum(infoVo, RequestTypeEnum.TENANT.getRequestType());
         
         if(!Check.NuNObj(pjStatusEnum)){        	
        	vo.setPjStatus(pjStatusEnum.getEvaStatuCode());
        	vo.setPjButton(pjStatusEnum.getEvaStatuName());
         }

		//房东取消订单退房状态 不能评价
		if (infoVo.getOrderStatus() == OrderStatusEnum.CHECKED_IN.FINISH_LAN_APPLY.getOrderStatus() || infoVo.getOrderStatus()	== OrderStatusEnum.FINISH_NEGOTIATE.getOrderStatus()
        		|| infoVo.getOrderStatus() == OrderStatusEnum.CANCEL_NEGOTIATE.getOrderStatus()){
			vo.setPjStatus(EvaluateClientBtnStatuEnum.N_EVAL.getEvaStatuCode());
			vo.setPjButton(EvaluateClientBtnStatuEnum.N_EVAL.getEvaStatuName());
		}
         
		return vo;
	}

	/**
	 * 
	 * 封装订单详情列表返回给客户端
	 *
	 * @author jixd
	 * @created 2016年5月2日 下午2:24:01
	 *
	 * @param detailList
	 * @param itemList
	 * @throws SOAParseException 
	 */
	private void warpOrderList(List<OrderInfoVo> detailList,List<OrderItemApiVo> itemList,String uid) throws SOAParseException{
		if (Check.NuNCollection(detailList)){
			return;
		}
		if (Check.NuNObj(itemList)){
			return;
		}
		//评价关闭入口时间（天）
		String timeStrJson = cityTemplateService.getTextValue(null, String.valueOf(EvaluateRulesEnum.EvaluateRulesEnum003.getValue()));
		DataTransferObject limitDto = JsonEntityTransform.json2DataTransferObject(timeStrJson);
		String limitStr = null;
		if(limitDto.getCode() == DataTransferObject.SUCCESS){
			limitStr = limitDto.parseData("textValue", new TypeReference<String>() {});
		}else {
			LogUtil.error(LOGGER,"获取评价关闭入口异常：rst：{}",limitStr);
		}
		//将当前的时间住转化成天
		int  jumpDay = ValueUtil.getintValue(limitStr);
		for(OrderInfoVo infoVo : detailList){
			OrderItemApiVo vo = trans2ItemVO(infoVo,jumpDay,uid);
			//查询是否国外用户
			String customerJson=customerMsgManagerService.getCustomerBaseMsgEntitybyUid(vo.getLandlordUid());
			CustomerBaseMsgEntity customerBaseMsgEntity=SOAResParseUtil.getValueFromDataByKey(customerJson, "customer", CustomerBaseMsgEntity.class);
			if(!Check.NuNObj(customerBaseMsgEntity)){
				if(!Check.NuNStr(customerBaseMsgEntity.getCountryCode()) && !Check.NuNStr(customerBaseMsgEntity.getCustomerMobile())
						&& "86".equals(customerBaseMsgEntity.getCountryCode())){
				}else{
					vo.setLandlordMobile("4007711666");
				}
			}
			
			itemList.add(vo);
		}
	}

	
	/**
	 * 兑换码
	 * @author liyingjie
	 * @param request
	 * @return
	 */
	@Deprecated
	@RequestMapping(value="/${LOGIN_AUTH}/exchangeCode",method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<ResponseSecurityDto> exchangeCode(HttpServletRequest request){
		try{
			String paramJson = (String) request.getAttribute(ConstDef.DECRYPT_PARAM_ATTRIBUTE);
			
			if(Check.NuNStr(paramJson)){
				LogUtil.info(logger, "changeCode params:{}" , paramJson);
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("参数为空"),HttpStatus.OK);
			}
			LogUtil.info(logger, "changeCode params:{}" , paramJson);
			BindCouponRequest couponRequest = JsonEntityTransform.json2Object(paramJson, BindCouponRequest.class);
			if(Check.NuNObj(couponRequest)){
				LogUtil.info(logger, "changeCode params:{}" , paramJson);
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("参数为空"),HttpStatus.OK);
			}
			String resultJson = actCouponService.exchangeCode(JsonEntityTransform.Object2Json(couponRequest));
			LogUtil.info(logger, "changeCode resultJson:{}" , resultJson);
			DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(resultJson);
			if(resultDto.getCode() != DataTransferObject.SUCCESS){
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail(resultDto.getMsg()),HttpStatus.OK);
			}
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptOK(resultDto),HttpStatus.OK);
		}catch(Exception e){
			LogUtil.error(logger, "changeCode is error, e={},param:{}",e,(String) request.getAttribute(ConstDef.DECRYPT_PARAM_ATTRIBUTE));
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("服务错误"),HttpStatus.OK);
		}
	}

	
	/**
	 * 
	 * 查询房东端 申请预定页信息 (功能：房东端IM聊天页-申请预定页)
	 *
	 * @author yd
	 * @created 2017年3月29日 下午4:29:10
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("${NO_LGIN_AUTH}/queryLanBookOrderInfo")
	@ResponseBody
	public  ResponseEntity<ResponseSecurityDto> queryLanBookOrderInfo(HttpServletRequest request){
		
		try {
			
		    NeedPayFeeApiRequest needPayFeeApiRequest = new NeedPayFeeApiRequest();
			String startTime = request.getParameter("startTime");
			String endTime = request.getParameter("endTime");
			String fid =  request.getParameter("fid");
			String rentWay =  request.getParameter("rentWay");
			needPayFeeApiRequest.setFid(fid);
			if(!Check.NuNStr(rentWay)){
				needPayFeeApiRequest.setRentWay(Integer.valueOf(rentWay));
			}
			
			if(!Check.NuNStr(endTime)){
				needPayFeeApiRequest.setStartTime(DateUtil.parseDate(startTime,"yyyy-MM-dd"));
			}
			
			if(!Check.NuNStr(endTime)){
				needPayFeeApiRequest.setEndTime(DateUtil.parseDate(endTime,"yyyy-MM-dd"));
			}
			
			Header header = getHeader(request);
			NeedPayFeeRequest needPayRequest = new NeedPayFeeRequest();

			needPayRequest.setVersionCode(header.getVersionCode());
			BeanUtils.copyProperties(needPayFeeApiRequest, needPayRequest);
			needPayRequest.setUserUid(needPayFeeApiRequest.getUid());
            //获取需支付金额
			DataTransferObject dto = tenantOrderService.needPayForLan(needPayRequest);
			LogUtil.info(logger, "needPay resultJson:{}", dto.toJsonString());
			return new ResponseEntity<>(ResponseSecurityDto.responseUnEncrypt(dto), HttpStatus.OK);
		} catch (Exception e) {
			LogUtil.error(logger, "【查询房东端 申请预定页信息异常】 e={}",e);
			return new ResponseEntity<>(ResponseSecurityDto.responseUnEncryptFail("服务错误"),HttpStatus.OK);
		}
		
	}
	
	/**
	 * 
	 * 发送邮件
	 *
	 * @author bushujie
	 * @created 2017年4月22日 下午4:36:20
	 *
	 * @param startTime
	 * @param endTime
	 * @param orderStatus
	 * @param checkInNumber
	 * @throws SOAParseException
	 */
    private void sendEmailOrder(Date startTime,Date endTime,String orderStatus,String uid,Integer checkInNumber,String houseFid,Integer rentWay) throws SOAParseException{
    	Map<String, String> emailPar = new HashMap<>();
    	emailPar.put("{2}", DateUtil.dateFormat(startTime, "yyyy-MM-dd"));
    	emailPar.put("{3}", DateUtil.dateFormat(endTime, "yyyy-MM-dd"));
    	emailPar.put("{4}", orderStatus);
    	emailPar.put("{5}", DateUtil.dateFormat(startTime, "yyyy年MM月dd日"));
    	emailPar.put("{6}", DateUtil.dateFormat(endTime, "yyyy年MM月dd日"));
    	emailPar.put("{8}", checkInNumber+"");
    	EmailRequest emailRequest=new EmailRequest();
    	emailRequest.setEmailCode(MessageTemplateCodeEnum.EMAIL_CODE_ORDER.getCode()+"");
    	emailRequest.setParamsMap(emailPar);
    	LogUtil.info(logger, "发邮件参数：{}",JsonEntityTransform.Object2Json(emailRequest));
    	SendEmailThreadPool.execute(new SendEmailThread(houseManageService, customerMsgManagerService, emailRequest, smsTemplateService, houseFid, rentWay,uid));
    }
}
