
package com.ziroom.minsu.api.order.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.exception.SOAParseException;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.DateUtil;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.MessageSourceUtil;
import com.asura.framework.base.util.RandomUtil;
import com.asura.framework.base.util.SOAResParseUtil;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.api.common.constant.ApiConst;
import com.ziroom.minsu.api.common.constant.ApiMessageConst;
import com.ziroom.minsu.api.common.constant.ConstDef;
import com.ziroom.minsu.api.common.dto.ResponseSecurityDto;
import com.ziroom.minsu.api.common.logic.ParamCheckLogic;
import com.ziroom.minsu.api.common.logic.ValidateResult;
import com.ziroom.minsu.api.order.dto.SmartLockApi;
import com.ziroom.minsu.entity.order.OrderSmartLockEntity;
import com.ziroom.minsu.services.basedata.api.inner.SmsTemplateService;
import com.ziroom.minsu.services.basedata.dto.SmsRequest;
import com.ziroom.minsu.services.common.constant.MessageConst;
import com.ziroom.minsu.services.common.constant.SysConst.SmartLock;
import com.ziroom.minsu.services.common.utils.CloseableHttpUtil;
import com.ziroom.minsu.services.common.utils.StringUtils;
import com.ziroom.minsu.services.customer.api.inner.CustomerMsgManagerService;
import com.ziroom.minsu.services.customer.entity.CustomerVo;
import com.ziroom.minsu.services.house.smartlock.enumvalue.OnoffLineStatus;
import com.ziroom.minsu.services.house.smartlock.enumvalue.PasswordTypeEnum;
import com.ziroom.minsu.services.house.smartlock.enumvalue.SmartErrNoEnum;
import com.ziroom.minsu.services.house.smartlock.param.AUSLParam;
import com.ziroom.minsu.services.house.smartlock.param.GetSLInfosParam;
import com.ziroom.minsu.services.house.smartlock.param.PermissionTimeParam;
import com.ziroom.minsu.services.house.smartlock.vo.DynamicPassSlVo;
import com.ziroom.minsu.services.order.api.inner.OrderCommonService;
import com.ziroom.minsu.services.order.api.inner.OrderSmartLockService;
import com.ziroom.minsu.services.order.entity.OrderInfoVo;
import com.ziroom.minsu.valenum.house.RentWayEnum;
import com.ziroom.minsu.valenum.msg.IsDelEnum;
import com.ziroom.minsu.valenum.msg.MessageTemplateCodeEnum;
import com.ziroom.minsu.valenum.order.OrderPayStatusEnum;
import com.ziroom.minsu.valenum.order.OrderStatusEnum;
import com.ziroom.minsu.valenum.ordersmart.TempPswdStatusEnum;

/**
 * <p>订单对于智能锁的相关操作
 *  1.点击获取智能锁密码
 *  2.点击获取智能锁临时密码
 * 
 * </p>
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
@RequestMapping("/smart")
public class SmartLocksController {

	private static Logger logger = LoggerFactory.getLogger(SmartLocksController.class);
	
	@Resource(name = "order.orderSmartLockService")
	private OrderSmartLockService orderSmartLockService;

	@Resource(name = "order.orderCommonService")
	private OrderCommonService orderCommonService;
	
	@Resource(name = "customer.customerMsgManagerService")
	private CustomerMsgManagerService customerMsgManagerService;
	
	@Resource(name="basedata.smsTemplateService")
	private SmsTemplateService smsTemplateService;

	@Resource(name = "api.messageSource")
	private MessageSource messageSource;
	
	@Resource(name="api.paramCheckLogic")
	private ParamCheckLogic paramCheckLogic;

	@Value("#{'${SMART_LOCKS_ADDPASSWORD}'.trim()}")
	private String SMART_LOCKS_ADDPASSWORD;

	@Value("#{'${SMART_LOCKS_GETLOCKSINFOS}'.trim()}")
	private String SMART_LOCKS_GETLOCKSINFOS;
	
	@Value("#{'${SMART_LOCKS_GET_DYNA_PSWD}'.trim()}")
	private String SMART_LOCKS_GET_DYNA_PSWD;
	
	@Value("#{'${SMART_LOCKS_NOTIFY_URL}'.trim()}")
	private String SMART_LOCKS_NOTIFY_URL;


	/**
	 * 
	 * 用户点击获取智能锁密码
	 * 算法：
	 * 1.校验（A.校验订单是否存在 B.校验订单是否可以获取密码锁  1.订单支付成功 2.当前时间在入住前一天到订单结束时间）
	 * 2.校验房源是否已绑定智能锁（此处不用 因为在订单那边已经校验  且去获取智能锁肯定也是失败的）
	 * 3.去redis获取当前用户的智能锁密码（稍后加上），redis没有，去数据库db 按订单号查询密码  不存在去获取，存在返回状态和密码
	 * 4.调用获取用户智能锁密码接口，返回用户智能锁密码
	 * 5.去更新（或者插入） 订单智能锁密码关联表（此处插入失败直接抛异常，不能返回密码，若返回密码了，下次展示的的智能锁密码就有可能是错误：这样就有存在这种场景，调用智能锁密码成功了，我们这边返回失败）
	 *
	 *  入参：
	 *  订单号：orderSn  
	 * @author yd
	 * @created 2016年6月23日 上午9:59:18
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("${LOGIN_AUTH}/getUserSmartLocks")
	@ResponseBody
	public ResponseEntity<ResponseSecurityDto>  getUserSmartLocks(HttpServletRequest request){


		String paramJson=(String) request.getAttribute(ConstDef.DECRYPT_PARAM_ATTRIBUTE);
		LogUtil.info(logger, "getUserSmartLocks params:{}",paramJson);

		SmartLockApi martLockApi = JsonEntityTransform.json2Object(paramJson, SmartLockApi.class);
		String uid = (String) request.getAttribute("uid");

	
		DataTransferObject dto = null;
		if(Check.NuNObj(martLockApi)||Check.NuNStr(martLockApi.getOrderSn())||Check.NuNStr(uid)){
			dto = new DataTransferObject();
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("参数错误");
			return new ResponseEntity<>(ResponseSecurityDto.responseEncrypt(dto), HttpStatus.OK);
		}
		String  orderSn = martLockApi.getOrderSn();
		LogUtil.info(logger, "获取智能锁密码接口参数uid={},orderSn={}", uid,orderSn);
	
		//查询当前订单
		dto  = JsonEntityTransform.json2DataTransferObject(this.orderCommonService.getOrderInfoByOrderSn(orderSn));

		if(dto.getCode() == 0){
			OrderInfoVo order;
			try {
				order = SOAResParseUtil.getValueFromDataByKey(dto.toJsonString(), "orderInfoVo", OrderInfoVo.class);

				if(Check.NuNObj(order)){
					dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
					dto.setMsg(MessageSourceUtil.getChinese(messageSource, ApiMessageConst.SMART_ORDER_NULL));
					return new ResponseEntity<ResponseSecurityDto>(ResponseSecurityDto.responseEncrypt(dto), HttpStatus.OK);
				}
				//用户非法
				if(!uid.equals(order.getUserUid())){
					dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
					dto.setMsg(MessageSourceUtil.getChinese(messageSource, ApiMessageConst.SMART_USER_NOAUTH));
					return new ResponseEntity<ResponseSecurityDto>(ResponseSecurityDto.responseEncrypt(dto), HttpStatus.OK);
				}
				//校验订单是否可以获取智能锁密码  
				if(Check.NuNObj(order.getPayStatus())||order.getPayStatus().intValue() != OrderPayStatusEnum.HAS_PAY.getPayStatus()){
					dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
					dto.setMsg(MessageSourceUtil.getChinese(messageSource, ApiMessageConst.SMART_ORDER_PAYSTATUS));
					return new ResponseEntity<ResponseSecurityDto>(ResponseSecurityDto.responseEncrypt(dto), HttpStatus.OK);
				}
				if(!checkOrderStatus(order.getOrderStatus())){
					dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
					dto.setMsg(MessageSourceUtil.getChinese(messageSource, ApiMessageConst.SMART_ORDER_ORDERSTATUS));
					return new ResponseEntity<ResponseSecurityDto>(ResponseSecurityDto.responseEncrypt(dto), HttpStatus.OK);
				}
				//校验当前时间是否在入住时间的前一天
				if(!checkOrderTime(order.getStartTime(), order.getEndTime())){
					dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
					dto.setMsg(MessageSourceUtil.getChinese(messageSource, ApiMessageConst.SMART_ORDER_ENDTIME));
					return new ResponseEntity<ResponseSecurityDto>(ResponseSecurityDto.responseEncrypt(dto), HttpStatus.OK);
				}

				//数据库查询
				dto = JsonEntityTransform.json2DataTransferObject(this.orderSmartLockService.findOrderSmartLockByOrderSn(orderSn));
				if(dto.getCode() == DataTransferObject.ERROR){
					logger.error("根据订单号orderSn={}智能锁查询异常",orderSn);
					return new ResponseEntity<ResponseSecurityDto>(ResponseSecurityDto.responseEncrypt(dto), HttpStatus.OK);
				}
				OrderSmartLockEntity orderSmartLock  = dto.parseData("orderSmartLock", new TypeReference<OrderSmartLockEntity>() {
				});
				if(!Check.NuNObj(orderSmartLock)&&!Check.NuNStr(orderSmartLock.getTempPswd())&&!Check.NuNObj(orderSmartLock.getTempExpiredTime())){
					Long persistExpiredTime = orderSmartLock.getTempExpiredTime().getTime();
					Long currTimeLong = (new Date()).getTime();
					if(currTimeLong>persistExpiredTime){
						dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
						dto.setMsg(MessageSourceUtil.getChinese(messageSource,ApiMessageConst.SMART_PWD_EXPIRED));
						return new ResponseEntity<ResponseSecurityDto>(ResponseSecurityDto.responseEncrypt(dto), HttpStatus.OK);
					}
					dto = new DataTransferObject();
					dto.putValue("status", orderSmartLock.getTempPswdStatus());
					if(orderSmartLock.getTempPswdStatus().intValue() == TempPswdStatusEnum.SUCCESS.getCode()){
						dto.putValue("password", StringUtils.decode(orderSmartLock.getTempPswd()));
						return new ResponseEntity<ResponseSecurityDto>(ResponseSecurityDto.responseEncrypt(dto), HttpStatus.OK);
					}
				
				}


				dto = JsonEntityTransform.json2DataTransferObject(this.customerMsgManagerService.getCutomerVoFromDb(uid));
				CustomerVo customerVo = dto.parseData("customerVo", new TypeReference<CustomerVo>() {
				});
				if(!Check.NuNObj(customerVo)){

					GetSLInfosParam paramInfos = new GetSLInfosParam();
					//校验门锁状态
					paramInfos.setHouse_id(StringUtils.getSmartLockCode(order.getHouseFid()));
					paramInfos.setOp_name(customerVo.getRealName());
					paramInfos.setOp_phone(customerVo.getShowMobile());
					paramInfos.setOp_userid(customerVo.getUid());
					String [] rooms = new String[]{StringUtils.getSmartLockCode(order.getHouseFid())};
					paramInfos.setRooms(rooms);
					dto = judgeGatWayStatus(paramInfos, dto);

					if(dto.getCode() == DataTransferObject.ERROR){
						return new ResponseEntity<ResponseSecurityDto>(ResponseSecurityDto.responseEncrypt(dto), HttpStatus.OK);
					}

					Map<String, String> baseMap  = (Map<String, String>) dto.getData().get("smartMap");
					if(!Check.NuNMap(baseMap)){
						Object lock = baseMap.get("lock");
						baseMap.clear();
						baseMap = (Map<String, String>) JsonEntityTransform.json2Map(JsonEntityTransform.Object2Json(lock));
						if(!Check.NuNMap(baseMap)){
							Object onOffLine = baseMap.get("onoff_line");
							if(onOffLine==null||(int)onOffLine == OnoffLineStatus.OFF_LINE.getCode()){
								dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
								dto.setMsg(MessageSourceUtil.getChinese(messageSource, ApiMessageConst.SMART_LOCK_OFF));
								return new ResponseEntity<ResponseSecurityDto>(ResponseSecurityDto.responseEncrypt(dto), HttpStatus.OK);
							}
						}

					}

					AUSLParam param = new AUSLParam(); 
					param.setOp_name(customerVo.getRealName());
					param.setOp_phone(customerVo.getShowMobile());
					param.setOp_userid(customerVo.getUid());
					param.setUser_name(customerVo.getRealName());
					param.setUser_phone(customerVo.getShowMobile());
					param.setHouse_id(StringUtils.getSmartLockCode(order.getHouseFid()));
					param.setRoom_id(StringUtils.getSmartLockCode(order.getHouseFid()));
					param.setIs_send_sms(1);
					param.setPassword(RandomUtil.genRandomNum(6));
					param.setPassword_type(PasswordTypeEnum.USER_PWD.getCode());
					param.setUser_identify(order.getOrderSn());
					PermissionTimeParam perTime = new PermissionTimeParam();
					param.setCallback_url(SMART_LOCKS_NOTIFY_URL+"?orderSn="+orderSn+"&pwd="+StringUtils.decode(param.getPassword()));
					Calendar ca = Calendar.getInstance();
					ca.setTime(order.getStartTime());
					ca.add(ca.MINUTE, -30);
					Long beforeOneTime = ca.getTime().getTime();
					perTime.setBegin(beforeOneTime/1000);
					perTime.setEnd(order.getEndTime().getTime()/1000);
					param.setPermission(perTime);
					LogUtil.info(logger, "获取智能锁密码参数param={}", JsonEntityTransform.Object2Json(param));
					//获取智能锁

					baseMap.clear();
					String sendPost = CloseableHttpUtil.sendPost(SMART_LOCKS_ADDPASSWORD, JsonEntityTransform.Object2Json(param));
					baseMap = (Map<String, String>) JsonEntityTransform.json2Map(sendPost);
					if(Check.NuNObj(baseMap)||Check.NuNObj(baseMap.get("ErrNo"))){
						dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
						dto.setMsg(MessageSourceUtil.getChinese(messageSource, ApiMessageConst.SMART_RETURN_ERROR));
						return new ResponseEntity<ResponseSecurityDto>(ResponseSecurityDto.responseEncrypt(dto), HttpStatus.OK);
					}
					Object errNo = baseMap.get("ErrNo");
					LogUtil.info(logger, "设置智能锁密码，返回数据baseMap={}", baseMap.toString());
					if((int)errNo !=SmartErrNoEnum.SUCCESS.getCode()){
						dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
						SmartErrNoEnum smartErrNoEnum = SmartErrNoEnum.getSmartErrNoEnumByCode((int)errNo);
						if(!Check.NuNObj(smartErrNoEnum)){
							dto.setMsg(smartErrNoEnum.getValue());
						}else{
							dto.setMsg(baseMap.get("ErrMsg"));
						}
						return new ResponseEntity<ResponseSecurityDto>(ResponseSecurityDto.responseEncrypt(dto), HttpStatus.OK);
					}

					dto = saveOrderSmartLock(baseMap, order, param,dto,ca.getTime());


					if(dto.getCode() == DataTransferObject.SUCCESS){
						Object result = dto.getData().get("result");
						if(!Check.NuNObj(result)&&(int)result>0){
							dto = new DataTransferObject();
							dto.putValue("status", TempPswdStatusEnum.SENDING.getCode());//获取就是下发中
						}
					}

				}

			} catch (SOAParseException e) {
				LogUtil.error(logger, "获取智能锁密码异常，e={}", e);
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg("获取智能锁密码异常");
			} catch (UnsupportedEncodingException e) {
				LogUtil.error(logger, "校验门锁状态异常，e={}", e);
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg("校验门锁状态异常");
			}

		}

		return new ResponseEntity<>(ResponseSecurityDto.responseEncrypt(dto), HttpStatus.OK);

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
	 * 
	 * 保存或者更新
	 *
	 * @author yd
	 * @created 2016年6月23日 下午7:47:44
	 *
	 * @param addPassSLVo
	 * @param orderSn
	 */
	private DataTransferObject saveOrderSmartLock(Map<String, String> baseMap,OrderInfoVo order,AUSLParam param,DataTransferObject dto,Date startTime){

		if(!Check.NuNObj(baseMap)&&!Check.NuNObj(order)&&!Check.NuNObj(param)){

			OrderSmartLockEntity orderSmartLockEntity = new OrderSmartLockEntity();
			orderSmartLockEntity.setCreateDate(new Date());
			orderSmartLockEntity.setFid(UUIDGenerator.hexUUID());
			orderSmartLockEntity.setOrderSn(order.getOrderSn());
			orderSmartLockEntity.setIsDel(IsDelEnum.NOT_DEL.getCode());
			orderSmartLockEntity.setLastModifyDate(new Date());
			orderSmartLockEntity.setTempPswd(StringUtils.decode(param.getPassword()));
			orderSmartLockEntity.setTempPswdStatus(TempPswdStatusEnum.SENDING.getCode());
			orderSmartLockEntity.setTempStartTime(startTime);
			orderSmartLockEntity.setTempExpiredTime(order.getEndTime());
			orderSmartLockEntity.setServiceId(String.valueOf(baseMap.get("serviceid")));
			orderSmartLockEntity.setPasswordId(String.valueOf(baseMap.get("password_id")));
			dto = JsonEntityTransform.json2DataTransferObject(this.orderSmartLockService.updateOrderSmartLockByOrderSn(JsonEntityTransform.Object2Json(orderSmartLockEntity)));
		}
		return dto;
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
	 * 校验当前时间是否在入住时间的前一天
	 *
	 * @author yd
	 * @created 2016年6月23日 上午11:13:23
	 *
	 * @param startTime 
	 * @param endTime
	 * @return
	 */
	private boolean checkOrderTime(Date startTime ,Date endTime){
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
		return orderStatusFlag;
	}

	/**
	 * 
	 *网关不在线   获取智能锁临时密码
	 * 算法：
	 * 1.校验订单编号与用户uid是否存在(不存在:直接返回,存在:下一步)
	 * 2.根据订单编号查询订单详细信息,校验订单详细信息是否存在(不存在:直接返回,存在:下一步)
	 * 3.根据订单uid校验用户是否合法(不合法:直接返回,合法:下一步)
	 * 4.校验订单支付状态能否获取动态密码(不可以:直接返回,可以:下一步)
	 * 5.校验订单状态能否获取动态密码(不可以:直接返回,可以:下一步)
	 * 6.根据订单编号查询订单智能锁信息,校验是否存在(不存在:直接返回,存在:下一步)
	 * 7.校验是否存在有效的动态密码(存在:直接返回,短信通知房东房客;存在:下一步)
	 * 8.校验获取动态密码次数是否超过最大上限(超过:直接返回,未超过:调用第三方获取动态密码返回)
	 * 
	 * 
	 *  入参：
	 *  订单号：orderSn 
	 * @author liujun
	 * @created 2016年6月24日
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("${LOGIN_AUTH}/getUserDynaPswd")
	@ResponseBody
	public ResponseEntity<ResponseSecurityDto> getUserDynaPswd(HttpServletRequest request){
		String paramJson=(String) request.getAttribute(ConstDef.DECRYPT_PARAM_ATTRIBUTE);
		LogUtil.info(logger, "getUserDynaPswd params:{}", paramJson);

		SmartLockApi martLockApi = JsonEntityTransform.json2Object(paramJson, SmartLockApi.class);
		String uid = (String) request.getAttribute("uid");

		DataTransferObject dto = null;
		// 校验参数对象是否为空
		if (Check.NuNObj(martLockApi)) {
			dto = new DataTransferObject();
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, ApiMessageConst.PARAM_NULL));
			LogUtil.info(logger, "error:{}", dto.getMsg());
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail(dto.getMsg()), HttpStatus.OK);
		}
		
		// 校验东单号不能为空
		if (Check.NuNStr(martLockApi.getOrderSn())) {
			dto = new DataTransferObject();
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, ApiMessageConst.SMARTLOCK_ORDERSN_NULL));
			LogUtil.info(logger, "error:{}", dto.getMsg());
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail(dto.getMsg()), HttpStatus.OK);
		}
		
		if (Check.NuNObj(martLockApi) || Check.NuNStr(martLockApi.getOrderSn()) || Check.NuNStr(uid)) {
			dto = new DataTransferObject();
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, ApiMessageConst.UID_NULL));
			LogUtil.info(logger, "error:{}", dto.getMsg());
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail(dto.getMsg()), HttpStatus.OK);
		}
		String orderSn = martLockApi.getOrderSn();
		LogUtil.info(logger, "调用智能锁动态密码接口,uid={},orderSn={}", uid, orderSn);

		//查询当前订单
		try {
			dto = JsonEntityTransform.json2DataTransferObject(this.orderCommonService.getOrderInfoByOrderSn(orderSn));
			if (dto.getCode() == DataTransferObject.SUCCESS) {
				OrderInfoVo order = SOAResParseUtil
						.getValueFromDataByKey(dto.toJsonString(), "orderInfoVo", OrderInfoVo.class);
				dto = this.validateDyna(order, uid, dto);
				if (dto.getCode() == DataTransferObject.ERROR) {
					return new ResponseEntity<ResponseSecurityDto>(
							ResponseSecurityDto.responseEncryptFail(dto.getMsg()), HttpStatus.OK);
				}

				// 数据库查询
				dto = JsonEntityTransform.json2DataTransferObject(
						this.orderSmartLockService.findOrderSmartLockByOrderSn(orderSn));
				if (dto.getCode() == DataTransferObject.ERROR) {
					LogUtil.error(logger,"orderSmartLockService#findOrderSmartLockByOrderSn接口调用失败,orderSn={},结果:{}", 
							orderSn, dto.toJsonString());
					return new ResponseEntity<ResponseSecurityDto>(
							ResponseSecurityDto.responseEncryptFail(dto.getMsg()), HttpStatus.OK);
				}
				OrderSmartLockEntity orderSmartLock = dto.parseData("orderSmartLock", new TypeReference<OrderSmartLockEntity>() {});
				if(Check.NuNObj(orderSmartLock)){
					//新增订单智能锁信息
					orderSmartLock = new OrderSmartLockEntity();
				}
				
				// 校验订单密码状态
				if (!Check.NuNObj(orderSmartLock.getTempPswdStatus()) 
						&& orderSmartLock.getTempPswdStatus().intValue() == TempPswdStatusEnum.CLOSED.getCode()) {
					dto.setErrCode(DataTransferObject.ERROR);
					dto.setMsg(MessageSourceUtil.getChinese(messageSource, ApiMessageConst.SMARTLOCK_ORDER_CLOSED));
					LogUtil.info(logger, "error:{}", dto.getMsg());
					return new ResponseEntity<ResponseSecurityDto>(
							ResponseSecurityDto.responseEncryptFail(dto.getMsg()), HttpStatus.OK);
				}
				
				if (!Check.NuNStr(orderSmartLock.getDynaPswd()) && !Check.NuNObj(orderSmartLock.getDynaExpiredTime())) {
					long dynaExpiredTime = orderSmartLock.getDynaExpiredTime().getTime();
					long currTimeLong = new Date().getTime();
					if (currTimeLong <= dynaExpiredTime) {
						String password = StringUtils.decode(orderSmartLock.getDynaPswd());
						dto.setErrCode(DataTransferObject.SUCCESS);
						dto.putValue("password", password);
						dto.putValue("expiredTime", DateUtil.timestampFormat(orderSmartLock.getDynaExpiredTime()));
						return new ResponseEntity<ResponseSecurityDto>(
								ResponseSecurityDto.responseEncryptOK(dto.getData()), HttpStatus.OK);
					}
				}
				
				if (!Check.NuNObj(orderSmartLock.getDynaNum())
						&& orderSmartLock.getDynaNum().intValue() >= SmartLock.MAX_DYNAMIC_PSWD_NUM) {
					dto.setErrCode(DataTransferObject.ERROR);
					dto.setMsg(MessageSourceUtil.getChinese(messageSource,
							ApiMessageConst.SMARTLOCK_DYNA_PSWD_NUM_OVER_LIMIT));
					LogUtil.info(logger, "动态密码获取次数达到上限,maxNum={},dynaNum={}",
							SmartLock.MAX_DYNAMIC_PSWD_NUM, orderSmartLock.getDynaNum());
					return new ResponseEntity<ResponseSecurityDto>(
							ResponseSecurityDto.responseEncryptFail(dto.getMsg()), HttpStatus.OK);
				}

				String getUrl = this.assembleUrl(order);
				String resultJson = CloseableHttpUtil.sendGet(getUrl, null);
				ValidateResult<DynamicPassSlVo> validateResult = paramCheckLogic
						.checkParamValidate(resultJson, DynamicPassSlVo.class);
				DynamicPassSlVo vo = validateResult.getResultObj();
				if (vo.getErrNo() == ApiConst.OPERATION_SUCCESS) {
					if(Check.NuNStr(orderSmartLock.getFid())){//新增
						orderSmartLock.setFid(UUIDGenerator.hexUUID());
						orderSmartLock.setOrderSn(orderSn);
						orderSmartLock.setDynaNum(1);
						orderSmartLock.setCreateDate(new Date());
					} else {//更新
						int dynaNum = orderSmartLock.getDynaNum().intValue();
						dynaNum ++ ;
						orderSmartLock.setDynaNum(dynaNum);
						orderSmartLock.setLastModifyDate(new Date());
					}
					orderSmartLock.setDynaPswd(StringUtils.encode(vo.getPassword()));
					orderSmartLock.setDynaExpiredTime(new Date(vo.getInvalid_time()));
					LogUtil.info(logger, "orderSmartLock:{}", JsonEntityTransform.Object2Json(orderSmartLock));
					orderSmartLockService.updateOrderSmartLockByOrderSn(JsonEntityTransform.Object2Json(orderSmartLock));
					
					// 发送短信
					this.sendSms(order, vo.getPassword());
					
					dto.setErrCode(DataTransferObject.SUCCESS);
					dto.putValue("password", vo.getPassword());
					dto.putValue("expiredTime", DateUtil.timestampFormat(vo.getInvalid_time()));
					return new ResponseEntity<ResponseSecurityDto>(
							ResponseSecurityDto.responseEncryptOK(dto.getData()), HttpStatus.OK);
				} else {
					LogUtil.info(logger, "动态密码获取失败,error:{}", resultJson);
					return new ResponseEntity<ResponseSecurityDto>(ResponseSecurityDto.responseEncryptFail(vo.getErrMsg()), HttpStatus.OK);
				}
			} else {
				LogUtil.error(logger, "orderCommonService#getOrderInfoByOrderSn接口调用失败, orderSn={}, 结果:{}",
						orderSn, dto.toJsonString());
				return new ResponseEntity<ResponseSecurityDto>(
						ResponseSecurityDto.responseEncryptFail(dto.getMsg()), HttpStatus.OK);
			}
		} catch (Exception e) {
			LogUtil.error(logger, "动态密码获取失败，e:{}", e);
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg(e.getMessage());
			return new ResponseEntity<ResponseSecurityDto>(
					ResponseSecurityDto.responseEncryptFail(dto.getMsg()), HttpStatus.OK);
		}
	}
	
	/**
	 * 动态密码参数校验
	 *
	 * @author liujun
	 * @created 2016年6月26日
	 *
	 * @param order
	 * @param uid 
	 * @param dto
	 * @return
	 */
	private DataTransferObject validateDyna(OrderInfoVo order, String uid, DataTransferObject dto) {
		if (Check.NuNObj(order)) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, ApiMessageConst.SMART_ORDER_NULL));
			LogUtil.info(logger, "error:{}", dto.getMsg());
			return dto;
		}
		// 用户非法
		if (!uid.equals(order.getUserUid())) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, ApiMessageConst.SMART_USER_NOAUTH));
			LogUtil.info(logger, "error:{}", dto.getMsg());
			return dto;
		}
		
		// 校验支付状态是否可以获取动态锁密码
		if (Check.NuNObj(order.getPayStatus())
				|| order.getPayStatus().intValue() != OrderPayStatusEnum.HAS_PAY.getPayStatus()) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, ApiMessageConst.SMART_ORDER_PAYSTATUS));
			LogUtil.info(logger, "error:{}", dto.getMsg());
			return dto;
		}
		
		// 校验订单状态是否可以获取动态密码
		if (!checkOrderStatus(order.getOrderStatus())) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, ApiMessageConst.SMART_ORDER_ORDERSTATUS));
			LogUtil.info(logger, "error:{}", dto.getMsg());
			return dto;
		}
		
		return dto;
	}
	
	/**
	 * 拼装获取动态密码url
	 *
	 * @author liujun
	 * @created 2016年6月26日
	 *
	 * @param order
	 * @return
	 */
	private String assembleUrl(OrderInfoVo order) {
		StringBuilder sb = new StringBuilder(SMART_LOCKS_GET_DYNA_PSWD);
		sb.append("?op_userid=").append(order.getUserUid());
		sb.append("&op_name=").append(order.getUserName());
		sb.append("&op_phone=").append(order.getUserTel());
		sb.append("&house_id=").append(StringUtils.getSmartLockCode(order.getHouseFid()));
		sb.append("&room_id=").append(StringUtils.getSmartLockCode(order.getHouseFid()));
		return sb.toString();
	}
	
	/**
	 * 获取动态密码成功后,给房东房客发送短信
	 *
	 * @author liujun
	 * @created 2016年6月26日
	 *
	 * @param order
	 * @param password 
	 */
	private void sendSms(OrderInfoVo order, String password) {
		if (Check.NuNObj(order)) {
			LogUtil.info(logger, MessageSourceUtil.getChinese(messageSource, ApiMessageConst.SMART_ORDER_NULL));
			return;
		}
		
		if (Check.NuNObj(order.getRentWay())) {
			LogUtil.info(logger, MessageSourceUtil.getChinese(messageSource, ApiMessageConst.HOUSE_RENTWAY_NULL));
			return;
		}
		
		String houseName = null;
		if (order.getRentWay().intValue() == RentWayEnum.HOUSE.getCode()) {
			houseName = order.getHouseName();
		} else if (order.getRentWay().intValue() == RentWayEnum.ROOM.getCode()) {
			houseName = order.getRoomName();
		} else {
			LogUtil.info(logger, MessageSourceUtil.getChinese(messageSource, ApiMessageConst.HOUSE_RENTWAY_ERROR));
			return;
		}
		
		// 发送短信-房东
		Map<String,String> paramsMap = new HashMap<String,String>();
		paramsMap.put("{1}", houseName);
		paramsMap.put("{2}", order.getUserName());
		paramsMap.put("{3}", String.valueOf(SmartLock.MAX_DYNAMIC_PSWD_NUM));
		SmsRequest smsRequest = new SmsRequest();
		smsRequest.setMobile(order.getLandlordTel());
		smsRequest.setParamsMap(paramsMap);
		smsRequest.setSmsCode(String.valueOf(MessageTemplateCodeEnum
				.SMARTLOCK_TENENT_DYNAPSWD_NOTIFY_LANDLORD.getCode()));
		smsTemplateService.sendSmsByCode(JsonEntityTransform.Object2Json(smsRequest));
		
		// 发送短信-房客
		paramsMap.clear();
		paramsMap.put("{1}", houseName);
		paramsMap.put("{2}", password);
		paramsMap.put("{3}", String.valueOf(SmartLock.MAX_DYNAMIC_PSWD_NUM));
		smsRequest.setMobile(order.getUserTel());
		smsRequest.setParamsMap(paramsMap);
		smsRequest.setSmsCode(String.valueOf(MessageTemplateCodeEnum
				.SMARTLOCK_TENENT_DYNAPSWD_NOTIFY_TENENT.getCode()));
		smsTemplateService.sendSmsByCode(JsonEntityTransform.Object2Json(smsRequest));
	}
	
}
