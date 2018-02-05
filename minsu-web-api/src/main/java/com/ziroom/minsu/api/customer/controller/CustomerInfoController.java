/**
 * @FileName: CustomerInfoController.java
 * @Package com.ziroom.minsu.api.customer.controller
 * 
 * @author jixd
 * @created 2016年5月28日 下午4:24:56
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.api.customer.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.RegExpUtil;
import com.asura.framework.base.util.SOAResParseUtil;
import com.asura.framework.cache.redisOne.RedisOperations;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.api.common.constant.ConstDef;
import com.ziroom.minsu.api.common.constant.LoginAuthConst;
import com.ziroom.minsu.api.common.dto.ResponseSecurityDto;
import com.ziroom.minsu.api.customer.dto.AuthMsgDto;
import com.ziroom.minsu.api.customer.dto.LandlordDto;
import com.ziroom.minsu.api.customer.dto.MobileDto;
import com.ziroom.minsu.entity.customer.CustomerBaseMsgEntity;
import com.ziroom.minsu.entity.customer.CustomerBaseMsgExtEntity;
import com.ziroom.minsu.entity.customer.CustomerPicMsgEntity;
import com.ziroom.minsu.entity.evaluate.StatsHouseEvaEntity;
import com.ziroom.minsu.entity.house.HouseRoomMsgEntity;
import com.ziroom.minsu.services.basedata.api.inner.SmsTemplateService;
import com.ziroom.minsu.services.basedata.dto.SmsRequest;
import com.ziroom.minsu.services.common.utils.CloseableHttpUtil;
import com.ziroom.minsu.services.common.utils.PicUtil;
import com.ziroom.minsu.services.common.utils.RedisKeyConst;
import com.ziroom.minsu.services.common.utils.ValueUtil;
import com.ziroom.minsu.services.common.utils.randomUtil;
import com.ziroom.minsu.services.customer.api.inner.CustomerInfoService;
import com.ziroom.minsu.services.customer.api.inner.CustomerMsgManagerService;
import com.ziroom.minsu.services.customer.entity.CustomerDetailImageVo;
import com.ziroom.minsu.services.customer.entity.LandlordIntroduceVo;
import com.ziroom.minsu.services.evaluate.api.inner.EvaluateOrderService;
import com.ziroom.minsu.services.evaluate.dto.StatsHouseEvaRequest;
import com.ziroom.minsu.services.house.api.inner.HouseIssueService;
import com.ziroom.minsu.valenum.customer.CusotmerAuthEnum;
import com.ziroom.minsu.valenum.customer.CustomerIdTypeEnum;
import com.ziroom.minsu.valenum.customer.CustomerPicTypeEnum;
import com.ziroom.minsu.valenum.house.RentWayEnum;
import com.ziroom.minsu.valenum.msg.MessageTemplateCodeEnum;
import com.ziroom.minsu.valenum.passport.PassPortCodeEnum;

/**
 * <p>客户相关信息接口</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author jixd
 * @since 1.0
 * @version 1.0
 */

@RequestMapping("/customer")
@Controller
public class CustomerInfoController {
	
	@Resource(name = "customer.customerInfoService")
	private CustomerInfoService customerInfoService;
	
	@Resource(name = "customer.customerMsgManagerService")
	private CustomerMsgManagerService customerMsgManagerService;
	
	@Resource(name = "evaluate.evaluateOrderService")
	private EvaluateOrderService evaluateOrderService;
	
	@Resource(name="house.houseIssueService")
	private HouseIssueService houseIssueService;
	
	@Resource(name = "basedata.smsTemplateService")
	private SmsTemplateService smsTemplateService;
	
	@Autowired
	private RedisOperations redisOperations;
	
	@Value("#{'${pic_base_addr_mona}'.trim()}")
	private String picBaseAddrMona;

	@Value("#{'${pic_base_addr}'.trim()}")
	private String picBaseAddr;
	
	@Value("#{'${default_head_size}'.trim()}")
	private String default_head_size;
	
	@Value("#{'${USER_DEFAULT_PIC_URL}'.trim()}")
	private String USER_DEFAULT_PIC_URL;

    @Value("#{'${CUSTOMER_SAVE_CARD_URL}'.trim()}")
    private String CUSTOMER_SAVE_CARD_URL;
	
	
	/**
	 * 日志对象
	 */
	private static final Logger logger = LoggerFactory.getLogger(CustomerInfoController.class);
	
	
	/**
	 * 
	 * 房东个人信息接口
	 *
	 * @author jixd
	 * @created 2016年5月28日 下午4:28:06
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/${UNLOGIN_AUTH}/landlordintro")
	@ResponseBody
	public ResponseEntity<ResponseSecurityDto> landlordIntroduce(HttpServletRequest request){
		try{
			String paramJson=(String) request.getAttribute(ConstDef.DECRYPT_PARAM_ATTRIBUTE);
			if(Check.NuNStr(paramJson)){
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("参数为空"),HttpStatus.OK);
			}
			LandlordDto landlordDto = JsonEntityTransform.json2Object(paramJson, LandlordDto.class);
			String landlordUid = landlordDto.getLandlordUid();
			String houseFid = landlordDto.getHouseFid();
			Integer rentWay = landlordDto.getRentWay();
			
			if(Check.NuNStr(landlordUid)){
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("房东uid为空"),HttpStatus.OK);
			}
			if(Check.NuNStr(houseFid)){
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("房源fid为空"),HttpStatus.OK);
			}
			if(Check.NuNObj(rentWay)){
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("出租方式为空"),HttpStatus.OK);
			}
			
			
			
			String customerBase = customerMsgManagerService.getCustomerDetailImage(landlordUid);
			DataTransferObject customerDto = JsonEntityTransform.json2DataTransferObject(customerBase);
			if(customerDto.getCode() != DataTransferObject.SUCCESS){
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("服务错误"),HttpStatus.OK);
			}
			LandlordIntroduceVo introduceVo = new LandlordIntroduceVo();
			CustomerDetailImageVo customerMsg = SOAResParseUtil.getValueFromDataByKey(customerBase, "customerImageVo", CustomerDetailImageVo.class);
			List<CustomerPicMsgEntity> customerPicList = customerMsg.getCustomerPicList();
			introduceVo.setNickName(customerMsg.getNickName());
			introduceVo.setHeadPicUrl(USER_DEFAULT_PIC_URL);
			if(!Check.NuNCollection(customerPicList)){
				for(CustomerPicMsgEntity picMsgEntity : customerPicList){
					if(picMsgEntity.getPicType() == CustomerPicTypeEnum.YHTX.getCode()){
						if(!Check.NuNStr(picMsgEntity.getPicServerUuid())){
							//如果是用户头像
							String headPicUrl = PicUtil.getFullPic(picBaseAddrMona, picMsgEntity.getPicBaseUrl(), picMsgEntity.getPicSuffix(), default_head_size);
							introduceVo.setHeadPicUrl(headPicUrl);
						}
					}
				}
			}
			
			//查询房东的个人介绍
			DataTransferObject introduceDto = JsonEntityTransform.json2DataTransferObject(this.customerMsgManagerService.selectCustomerExtByUid(landlordUid));
			if(introduceDto.getCode() == DataTransferObject.SUCCESS){
				CustomerBaseMsgExtEntity customerBaseMsgExt = introduceDto.parseData("customerBaseMsgExt", new TypeReference<CustomerBaseMsgExtEntity>() {});
				if(!Check.NuNObj(customerBaseMsgExt)){
					introduceVo.setIntroduce(customerBaseMsgExt.getCustomerIntroduce());
				}
				
			}
			
			
			//如果是合租的话 先查询到houseFid
			if(rentWay == RentWayEnum.ROOM.getCode()){
				String roomMsgJson = houseIssueService.searchHouseRoomMsgByFid(houseFid);
				DataTransferObject roomMsgDto = JsonEntityTransform.json2DataTransferObject(roomMsgJson);
				if(roomMsgDto.getCode() == DataTransferObject.SUCCESS){
					HouseRoomMsgEntity roomMsgEntity = SOAResParseUtil.getValueFromDataByKey(roomMsgJson, "obj", HouseRoomMsgEntity.class);
					houseFid = roomMsgEntity.getHouseBaseFid();
				}
			}
			
			
			//房源的评分
			StatsHouseEvaRequest statsHouseEvaRequest = new StatsHouseEvaRequest();
			statsHouseEvaRequest.setHouseFid(houseFid);
			
			String statsHouseJson = evaluateOrderService.queryStatsHouseEvaByCondition(JsonEntityTransform.Object2Json(statsHouseEvaRequest));
			DataTransferObject statsHouseDto = JsonEntityTransform.json2DataTransferObject(statsHouseJson);
			if(statsHouseDto.getCode() == DataTransferObject.SUCCESS){
				List<StatsHouseEvaEntity> statsHouseEvaList = SOAResParseUtil.getListValueFromDataByKey(statsHouseJson, "listStatsHouseEvaEntities", StatsHouseEvaEntity.class);
				if(!Check.NuNCollection(statsHouseEvaList)){
					StatsHouseEvaEntity statsHouseEvaEntity = statsHouseEvaList.get(0);
					Float houseCleanAva = statsHouseEvaEntity.getHouseCleanAva();
					Float desMatchAva = statsHouseEvaEntity.getDesMatchAva();
					Float safeDegreeAva = statsHouseEvaEntity.getSafeDegreeAva();
					Float trafPosAva = statsHouseEvaEntity.getTrafPosAva();
					Float costPerforAva = statsHouseEvaEntity.getCostPerforAva();
					Float totalAva = (houseCleanAva + desMatchAva + safeDegreeAva + trafPosAva + costPerforAva)/5;
					introduceVo.setEva(ValueUtil.getEvaluteSoreDefault(totalAva.toString()).floatValue()); 
				}
			}
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptOK(introduceVo),HttpStatus.OK);
		}catch(Exception e){
			LogUtil.error(logger, "landlordIntroduce is error, e={}",e);
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("服务错误"),HttpStatus.OK);
		}
		
	}
	
	
	
	/**
	 * 
	 * 房东个人信息接口（修改房东评分）
	 *
	 * @author zl
	 * @created 2016年9月18日 
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/${UNLOGIN_AUTH}/landlordIntroduceNew")
	@ResponseBody
	public ResponseEntity<ResponseSecurityDto> landlordIntroduceNew(HttpServletRequest request){
		try {
			
			String paramJson=(String) request.getAttribute(ConstDef.DECRYPT_PARAM_ATTRIBUTE);
			if(Check.NuNStr(paramJson)){
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("参数为空"),HttpStatus.OK);
			}
			LandlordDto landlordDto = JsonEntityTransform.json2Object(paramJson, LandlordDto.class);
			String landlordUid = landlordDto.getLandlordUid(); 
			
			if(Check.NuNStr(landlordUid)){
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("房东uid为空"),HttpStatus.OK);
			} 
			
			String customerBase = customerMsgManagerService.getCustomerDetailImage(landlordUid);
			DataTransferObject customerDto = JsonEntityTransform.json2DataTransferObject(customerBase);
			if(customerDto.getCode() != DataTransferObject.SUCCESS){
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("服务错误"),HttpStatus.OK);
			}
			LandlordIntroduceVo introduceVo = new LandlordIntroduceVo();
			CustomerDetailImageVo customerMsg = SOAResParseUtil.getValueFromDataByKey(customerBase, "customerImageVo", CustomerDetailImageVo.class);
			if(!Check.NuNObj(customerMsg)){
				List<CustomerPicMsgEntity> customerPicList = customerMsg.getCustomerPicList();
				introduceVo.setNickName(customerMsg.getNickName());
				if(!Check.NuNCollection(customerPicList)){
					for(CustomerPicMsgEntity picMsgEntity : customerPicList){
						if(picMsgEntity.getPicType() == CustomerPicTypeEnum.YHTX.getCode()){
							if(!Check.NuNStr(picMsgEntity.getPicServerUuid())){
								//如果是用户头像
								String headPicUrl = PicUtil.getFullPic(picBaseAddrMona, picMsgEntity.getPicBaseUrl(), picMsgEntity.getPicSuffix(), default_head_size);
								introduceVo.setHeadPicUrl(headPicUrl);
								//大图
								String headPicUrlOrg = PicUtil.getOrgPic(picBaseAddr, picMsgEntity.getPicBaseUrl(), picMsgEntity.getPicSuffix());
								introduceVo.setHeadPicUrlOrg(headPicUrlOrg);
							}
						}
					}
				}
			}
			
			
			if (Check.NuNStr(introduceVo.getHeadPicUrl())) {
				introduceVo.setHeadPicUrl(USER_DEFAULT_PIC_URL);
			}
			
			
			//查询房东的个人介绍
			DataTransferObject introduceDto = JsonEntityTransform.json2DataTransferObject(this.customerMsgManagerService.selectCustomerExtByUid(landlordUid));
			if(introduceDto.getCode() == DataTransferObject.SUCCESS){
				CustomerBaseMsgExtEntity customerBaseMsgExt = introduceDto.parseData("customerBaseMsgExt", new TypeReference<CustomerBaseMsgExtEntity>() {});
				if(!Check.NuNObj(customerBaseMsgExt)){
					introduceVo.setIntroduce(customerBaseMsgExt.getCustomerIntroduce());
				}
				
			}
			
			String aveScoreJson = evaluateOrderService.selectByAVEScoreByUid(landlordUid);
			DataTransferObject statsHouseDto = JsonEntityTransform.json2DataTransferObject(aveScoreJson);
			if(statsHouseDto.getCode() == DataTransferObject.SUCCESS){
				introduceVo.setEva(ValueUtil.getEvaluteSoreDefault(SOAResParseUtil.getStrFromDataByKey(aveScoreJson, "aveScore")).floatValue());
			}
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptOK(introduceVo),HttpStatus.OK);
			
		} catch (Exception e) {
			LogUtil.error(logger, "landlordIntroduce is error, e={}",e);
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("服务错误"),HttpStatus.OK);
		}
	}
	
	
	/**
	 * 补充认证信息时获取手机验证码
	 * @author lishaochuan
	 * @create 2016年6月16日上午11:29:03
	 * @param request
	 * @return
	 */
	@RequestMapping("/${LOGIN_AUTH}/getMobileCodeAuthMsg")
	@ResponseBody
	public ResponseEntity<ResponseSecurityDto> getMobileCodeAuthMsg(HttpServletRequest request){
		try {
			String paramJson = (String) request.getAttribute(ConstDef.DECRYPT_PARAM_ATTRIBUTE);
			MobileDto paramDto = JsonEntityTransform.json2Object(paramJson, MobileDto.class);
			paramDto.setUid((String)request.getAttribute(LoginAuthConst.UID));
			
			if(Check.NuNStr(paramDto.getUid())){
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("未登录"),HttpStatus.OK);
			}
			if(Check.NuNStr(paramDto.getMobile())){
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("手机号不存在"),HttpStatus.OK);
			}
			
			// 校验是否已经绑定手机号
			String resultJson = customerInfoService.getCustomerInfoByUid(paramDto.getUid());
			CustomerBaseMsgEntity customerBase = SOAResParseUtil.getValueFromDataByKey(resultJson, "customerBase", CustomerBaseMsgEntity.class);
			if (!Check.NuNStr(customerBase.getCustomerMobile())) {
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("您已绑定手机号"),HttpStatus.OK);
			}
			
			
			// 获取验证码
	        String vcode = randomUtil.getNumrOrChar(6, "num");
			if (Check.NuNStr(vcode)) {
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("获取验证码错误"),HttpStatus.OK);
			}
			
			// 发送验证码短信
			String key = RedisKeyConst.getMobileCodeKey(paramDto.getUid(), paramDto.getMobile());
			SmsRequest smsRequest = new SmsRequest();
			smsRequest.setMobile(paramDto.getMobile());
			smsRequest.setSmsCode(String.valueOf(MessageTemplateCodeEnum.MOBILE_CODE.getCode()));
			Map<String, String> paMap = new HashMap<String, String>();
			paMap.put("{1}", vcode);
			paMap.put("{2}", String.valueOf(RedisKeyConst.MOBILE_CODE_CACHE_TIME / 60));
			smsRequest.setParamsMap(paMap);
			DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(this.smsTemplateService.sendSmsByCode(JsonEntityTransform.Object2Json(smsRequest)));
			if(dto.getCode() != DataTransferObject.SUCCESS){
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("获取验证码错误"),HttpStatus.OK);
			}
			try {
				// Redis保存验证码
				redisOperations.setex(key, RedisKeyConst.MOBILE_CODE_CACHE_TIME, vcode);
			} catch (Exception e) {
				LogUtil.error(logger, "redis错误");
			}
			
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptOK(null),HttpStatus.OK);
		} catch (Exception e) {
			LogUtil.error(logger, "getMobileCodeAuthMsg is error, e={}",e);
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("服务错误"),HttpStatus.OK);
		}
	}
	
	
	/**
	 * 保存补充的证件信息
	 * @author lishaochuan
	 * @create 2016年6月16日上午10:20:41
	 * @param request
	 * @return
	 */
	@RequestMapping("/${LOGIN_AUTH}/saveAuthMsg")
	@ResponseBody
	public ResponseEntity<ResponseSecurityDto> saveAuthMsg(HttpServletRequest request){
		try {
			String paramJson = (String) request.getAttribute(ConstDef.DECRYPT_PARAM_ATTRIBUTE);
			AuthMsgDto authMsgDto = JsonEntityTransform.json2Object(paramJson, AuthMsgDto.class);
			authMsgDto.setUid((String)request.getAttribute(LoginAuthConst.UID));
			
			if(Check.NuNStr(authMsgDto.getUid())){
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("未登录"),HttpStatus.OK);
			}
			if(Check.NuNStr(authMsgDto.getRealName())){
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("姓名为空"),HttpStatus.OK);
			}
			if(Check.NuNObj(authMsgDto.getIdType())){
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("证件类型为空"),HttpStatus.OK);
			}
			if(Check.NuNObj(authMsgDto.getIdNo())){
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("证件号码为空"),HttpStatus.OK);
			}
			if(authMsgDto.getIdType() == CustomerIdTypeEnum.ID.getCode() && !RegExpUtil.idIdentifyCardNum(authMsgDto.getIdNo())){
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("身份证校验不通过"),HttpStatus.OK);
			}
			if(authMsgDto.getIdType() == CustomerIdTypeEnum.PASSPORT.getCode() && !RegExpUtil.isPassportNum(authMsgDto.getIdNo())){
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("护照校验不通过"),HttpStatus.OK);
			}
			if (authMsgDto.getIdType() == CustomerIdTypeEnum.GA_PASSPORT.getCode() && !RegExpUtil.isHMT(authMsgDto.getIdNo())) {
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("港澳居民来往通行证校验不通过"), HttpStatus.OK);
			}
			if (authMsgDto.getIdType() == CustomerIdTypeEnum.tw_PASSPORT.getCode() && !RegExpUtil.isHMT(authMsgDto.getIdNo())) {
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("台湾居民来往通行证校验不通过"), HttpStatus.OK);
			}
			
			// 校验是否已经绑定手机号
			if(!Check.NuNStr(authMsgDto.getMobile())){
				String resultJson = customerInfoService.getCustomerInfoByUid(authMsgDto.getUid());
				CustomerBaseMsgEntity customerBase = SOAResParseUtil.getValueFromDataByKey(resultJson, "customerBase", CustomerBaseMsgEntity.class);
				if (!Check.NuNStr(customerBase.getCustomerMobile())) {
					return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("您已绑定手机号"),HttpStatus.OK);
				}
			}

	
			
			// 校验手机号、验证码
			if(!Check.NuNStr(authMsgDto.getMobile()) && !Check.NuNStr(authMsgDto.getAuthCode())){
				String realAuthCode = null;
				try {
					 realAuthCode = this.redisOperations.get(RedisKeyConst.getMobileCodeKey(authMsgDto.getUid(), authMsgDto.getMobile()));
				} catch (Exception e) {
					LogUtil.error(logger, "redis错误");
				}
				if(Check.NuNStr(realAuthCode)){
					return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("验证码已失效"),HttpStatus.OK);
				}
				if(!realAuthCode.equals(authMsgDto.getAuthCode())){
					return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("验证码错误"),HttpStatus.OK);
				}
			}
			// 修改用户系统信息
			CustomerBaseMsgEntity customerBaseMsgEntity = new CustomerBaseMsgEntity();
			if(!Check.NuNStr(authMsgDto.getMobile())){
				customerBaseMsgEntity.setIsContactAuth(CusotmerAuthEnum.IsAuth.getCode());
			}
			customerBaseMsgEntity.setUid(authMsgDto.getUid());
			customerBaseMsgEntity.setRealName(authMsgDto.getRealName());
			customerBaseMsgEntity.setCustomerMobile(authMsgDto.getMobile());
			customerBaseMsgEntity.setIdType(authMsgDto.getIdType());
			customerBaseMsgEntity.setIdNo(authMsgDto.getIdNo());

			DataTransferObject passDto = new DataTransferObject();
			String token = (String)request.getAttribute(LoginAuthConst.TOKEN);
			//掉用账户保存成功之后再入库
            callCenter4saveinfo(passDto,customerBaseMsgEntity,token);
            if (passDto.getCode() != DataTransferObject.SUCCESS){
                return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail(passDto.getMsg()),HttpStatus.OK);
            }
			// 修改本地系统信息
			DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(customerInfoService.updateCustomerInfo(JsonEntityTransform.Object2Json(customerBaseMsgEntity)));
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptOK(dto),HttpStatus.OK);
		} catch (Exception e) {
			LogUtil.error(logger, "saveAuthMsg is error, e={}",e);
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("服务错误"),HttpStatus.OK);
		}
	}


    /**
     * 调用账户信息
     * @param customerBaseMsgEntity
     * @param token
     * @return
     */
    private void callCenter4saveinfo(DataTransferObject dto,CustomerBaseMsgEntity customerBaseMsgEntity,String token){
        if (dto.getCode() == DataTransferObject.SUCCESS){
            Map<String,String> par = new HashMap<>();
            par.put("user_name",customerBaseMsgEntity.getRealName());
            par.put("ziroom_token", token);
            par.put("cert_type", ValueUtil.getStrValue(customerBaseMsgEntity.getIdType()));
            par.put("cert_num",customerBaseMsgEntity.getIdNo());
            String json = CloseableHttpUtil.sendFormPost(CUSTOMER_SAVE_CARD_URL, par);
			try {
				Map rst = JsonEntityTransform.json2Map(json);
				String code = ValueUtil.getStrValue(rst.get("errorcode"));
				if (code.equals(ConstDef.USER_RST_SUCCESS)){
					//调用成功
				}else if (code.equals(ConstDef.USER_RST_DOUBLE)){
					//调用失败
					dto.setErrCode(DataTransferObject.ERROR);
					dto.setMsg("证件号码已被其他账号绑定，请核实证件号码，或更换证件类型");
					LogUtil.info(logger,"修改用户信息返回结果：{}",json);
				}else {
					//调用失败
					dto.setErrCode(DataTransferObject.ERROR);
					dto.setMsg(ValueUtil.getStrValue(rst.get("errormsg")));
					LogUtil.info(logger,"修改用户信息返回结果：{}",json);
				}
			}catch (Exception e){
				//兼容之前的错误信息
				PassPortCodeEnum passPortCodeEnum = PassPortCodeEnum.getByStr(json);
				if (!passPortCodeEnum.checkOk()){
					//证件号码已被其他账号绑定，请核实证件号码，或更换证件类型
					if (json.contains("has already been taken")){
						dto.setMsg("证件号码已被其他账号绑定，请核实证件号码，或更换证件类型");
					}else {
						dto.setMsg(passPortCodeEnum.getName());
					}
					//调用失败
					dto.setErrCode(DataTransferObject.ERROR);

				}
			}

        }
    }
    
   /**
    * 
    * 判断客户手机号码是否重复
    *
    * @author liujun
    * @created 2016年8月4日
    *
    * @param request
    * @return 0:否 1:是
    */
	@RequestMapping("/${LOGIN_AUTH}/judgeMobileExist")
	@ResponseBody
	public ResponseEntity<ResponseSecurityDto> judgeMobileExist(HttpServletRequest request){
		String paramJson = (String) request.getAttribute(ConstDef.DECRYPT_PARAM_ATTRIBUTE);
		LogUtil.info(logger, "参数:{}", paramJson);
		JSONObject object = SOAResParseUtil.getJsonObj(paramJson);
		if(Check.NuNObj(object) || !object.containsKey("mobile") || Check.NuNStr(object.getString("mobile"))){
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("手机号不存在"),HttpStatus.OK);
		}
		
		try {
			String mobile = object.getString("mobile");
			String resultJson = customerInfoService.getCustomerByMobile(mobile);
			DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(resultJson);
			if(resultDto.getCode() == DataTransferObject.ERROR){
				LogUtil.info(logger, "customerInfoService.getCustomerByMobile错误,参数:{},结果:{}", mobile, resultJson);
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail(resultDto.getMsg()),HttpStatus.OK);
			}
			
			CustomerBaseMsgEntity entity = SOAResParseUtil
					.getValueFromDataByKey(resultJson, "customerBase", CustomerBaseMsgEntity.class);
			
			DataTransferObject dto = new DataTransferObject();
			if(Check.NuNObj(entity)){
				dto.putValue("isExist", "0");
			} else {
				dto.putValue("isExist", "1");
			}
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptOK(dto.getData()),HttpStatus.OK);
		} catch (Exception e) {
			LogUtil.error(logger, "judgeMobileExist is error, e={}",e);
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("服务错误"),HttpStatus.OK);
		}
		
	}
    
}
