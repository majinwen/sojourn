/**
 * @FileName: CustomerAuthController.java
 * @Package com.ziroom.minsu.portal.fd.center.customer.controller
 * 
 * @author bushujie
 * @created 2016年7月21日 下午4:31:54
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.portal.fd.center.customer.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.exception.SOAParseException;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.DateUtil;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.SOAResParseUtil;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.cache.redisOne.RedisOperations;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.customer.CustomerBaseMsgEntity;
import com.ziroom.minsu.entity.customer.CustomerBaseMsgExtEntity;
import com.ziroom.minsu.entity.customer.CustomerPicMsgEntity;
import com.ziroom.minsu.portal.fd.center.common.constant.HeaderParamName;
import com.ziroom.minsu.portal.fd.center.common.constant.PortalFdConstant;
import com.ziroom.minsu.portal.fd.center.common.dto.LoginDataDto;
import com.ziroom.minsu.portal.fd.center.common.utils.ImageHelper;
import com.ziroom.minsu.portal.fd.center.common.utils.UserUtils;
import com.ziroom.minsu.portal.fd.center.customer.dto.MobileAuthDto;
import com.ziroom.minsu.services.basedata.api.inner.SmsTemplateService;
import com.ziroom.minsu.services.basedata.dto.SmsRequest;
import com.ziroom.minsu.services.common.commenum.AccountSignEnum;
import com.ziroom.minsu.services.common.dto.ssoapi.LoginHeaderDto;
import com.ziroom.minsu.services.common.utils.ClassReflectUtils;
import com.ziroom.minsu.services.common.utils.CloseableHttpsUtil;
import com.ziroom.minsu.services.common.utils.PicUtil;
import com.ziroom.minsu.services.common.utils.RedisKeyConst;
import com.ziroom.minsu.services.common.utils.SignUtils;
import com.ziroom.minsu.services.common.utils.ValueUtil;
import com.ziroom.minsu.services.common.utils.randomUtil;
import com.ziroom.minsu.services.customer.api.inner.CustomerAuthService;
import com.ziroom.minsu.services.customer.api.inner.CustomerInfoPcService;
import com.ziroom.minsu.services.customer.api.inner.CustomerInfoService;
import com.ziroom.minsu.services.customer.api.inner.CustomerMsgManagerService;
import com.ziroom.minsu.services.customer.dto.CustomerBaseExtDto;
import com.ziroom.minsu.services.customer.dto.CustomerFieldAuditVo;
import com.ziroom.minsu.services.customer.entity.CustomerAuthVo;
import com.ziroom.minsu.services.customer.util.UserHeadImgUtils;
import com.ziroom.minsu.valenum.account.CustomerTypeEnum;
import com.ziroom.minsu.valenum.common.YesOrNoEnum;
import com.ziroom.minsu.valenum.customer.CustomerAuditStatusEnum;
import com.ziroom.minsu.valenum.customer.CustomerEduEnum;
import com.ziroom.minsu.valenum.customer.CustomerIdTypeEnum;
import com.ziroom.minsu.valenum.customer.CustomerPicTypeEnum;
import com.ziroom.minsu.valenum.customer.CustomerUpdateLogEnum;
import com.ziroom.minsu.valenum.customer.IsLandlordEnum;
import com.ziroom.minsu.valenum.login.LoginCodeEnum;
import com.ziroom.minsu.valenum.msg.MessageTemplateCodeEnum;
import com.ziroom.tech.storage.client.domain.FileInfoResponse;
import com.ziroom.tech.storage.client.service.StorageService;

/**
 * <p>房东资质认证</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author bushujie
 * @since 1.0
 * @version 1.0
 */
@RequestMapping("customer")
@Controller
public class LandlordAuthController {
	
	 /**
     * 日志对象
     */
    private static Logger LOGGER = LoggerFactory.getLogger(LandlordAuthController.class);
	
	@Value("#{'${IMG_VERIFY_CODE_POST}'.trim()}")
	private String IMG_VERIFY_CODE_POST;
	
	@Value("#{'${MINSU_WEB_SYS}'.trim()}")
	private String minsuWebSys;

	@Value("#{'${MINSU_WEB_ACCEPT}'.trim()}")
	private String minsuWebAccept;
	
	@Resource(name = "basedata.smsTemplateService")
	private SmsTemplateService smsTemplateService;
	
	@Autowired
	private RedisOperations redisOperations;
	
	@Resource(name = "customer.customerInfoService")
	private CustomerInfoService customerInfoService;
	
	@Resource(name="customer.customerMsgManagerService")
	private CustomerMsgManagerService customerMsgManagerService;
	
	@Resource(name="customer.customerInfoPcService")
	private CustomerInfoPcService customerInfoPcService;
	
	@Value("#{'${pic_base_addr_mona}'.trim()}")
	private String picBaseAddrMona;
	
	@Value("#{'${pic_size}'.trim()}")
	private String picSize;
	
	@Value("#{'${pic_size_120_120}'.trim()}")
	private String pic_size_120_120;
	
	@Value("#{'${pic_base_addr}'.trim()}")
	private String picBaseAddr;
	
	@Value("#{'${pic_default_head_url}'.trim()}")
	private String defaultHeadPic;
	
	@Resource(name="storageService")
	private StorageService storageService;
	
	@Value("#{'${storage_key}'.trim()}")
	private String storageKey;
	
	@Value("#{'${storage_limit}'.trim()}")
	private String storageLimit;
	
	@Value("#{'${IMG_VERIFY_CODE_GET}'.trim()}")
	private String IMG_VERIFY_CODE_GET;
	
	@Resource(name="customer.customerAuthService")
	private CustomerAuthService customerAuthService;
	
    @Value("#{'${passport_app_key_value}'.trim()}")
    private String appKeyValue;
    
    @Value("#{'${passport_save_appid}'.trim()}")
    private String saveAppid;
    
    @Value("#{'${passport_save_cert_uid}'.trim()}")
    private String saveCertUid;

	
    @Value("#{'${AUTH_CODE}'.trim()}")
    private String AUTH_CODE;
    
    @Value("#{'${AUTH_SECRET_KEY}'.trim()}")
    private String AUTH_SECRET_KEY;
    
	@Value("#{'${SSO_USER_DETAIL_URL}'.trim()}")
	private  String SSO_USER_DETAIL_URL;
	/**
	 * 
	 * 房东首次认证
	 *
	 * @author bushujie
	 * @created 2016年8月1日 下午5:47:38
	 *
	 * @param request
	 * @throws SOAParseException 
	 */
	@RequestMapping("dataAuthPage")
	public void  dataAuthPage(HttpServletRequest request) throws SOAParseException{
		String imgId = UUIDGenerator.hexUUID().substring(0, 32);
		String imgUrl = this.IMG_VERIFY_CODE_GET+"?imgId=";
		request.setAttribute("imgUrl", imgUrl);
		request.setAttribute("imgId", imgId);
		//获取登录用户uid
		String uid=UserUtils.getCurrentUid();
		//初始化认证信息
		String resultJson=customerInfoPcService.initCustomerAuthData(uid);
		CustomerAuthVo customerAuthVo=SOAResParseUtil.getValueFromDataByKey(resultJson, "customerAuthVo", CustomerAuthVo.class);
		List<CustomerIdTypeEnum> idTypeList= CustomerIdTypeEnum.getALLCustomerIdTypeEnums();
		request.setAttribute("idTypeName", CustomerIdTypeEnum.getCustomerIdTypeByCode(customerAuthVo.getIdType()).getName());
		
		request.setAttribute("idTypeList", idTypeList);
		request.setAttribute("picBaseAddrMona", picBaseAddrMona);
		request.setAttribute("picSize", picSize);
		
		CustomerPicMsgEntity voucherBackPic = customerAuthVo.getVoucherBackPic();
		CustomerPicMsgEntity voucherFrontPic = customerAuthVo.getVoucherFrontPic();
		CustomerPicMsgEntity voucherHandPic = customerAuthVo.getVoucherHandPic();
		CustomerPicMsgEntity voucherHeadPic = customerAuthVo.getVoucherHeadPic();
		
		//如果此房东的审核通过后，修改了头像或者个人介绍，则填充最新修改的后的值
		CustomerPicMsgEntity latestUnAuditHeadPic = new CustomerPicMsgEntity();
		CustomerBaseMsgExtEntity unCheckCustomerBaseMsg = new CustomerBaseMsgExtEntity();
		//从t_customer_update_history_log表中查询,看是否存在需要替换的字段
		if(customerAuthVo.getIsLandlord()==CustomerTypeEnum.landlord.getStatusCode()){
				fillLandNewHeadPicAndIntroduce(latestUnAuditHeadPic,unCheckCustomerBaseMsg, uid, customerAuthVo);
		}
		//如果是默认昵称，置为空
		if(customerAuthVo.getIsLandlord()==YesOrNoEnum.YES.getCode() && !Check.NuNStr(customerAuthVo.getNickName())){
				if(isDefaultNickName(customerAuthVo.getNickName(), customerAuthVo.getUid())){
						customerAuthVo.setNickName(null);
				}
		}
		request.setAttribute("customerAuthVo", customerAuthVo);
		String backpic = "";
		String frontpic = "";
		String handpic = "";
		String headpic = defaultHeadPic;
		
		if(!Check.NuNObj(voucherBackPic)){
			backpic = PicUtil.getFullPic(picBaseAddrMona,voucherBackPic.getPicBaseUrl() ,voucherBackPic.getPicSuffix(), pic_size_120_120);
		}
		if(!Check.NuNObj(voucherFrontPic)){
			frontpic = PicUtil.getFullPic(picBaseAddrMona,voucherFrontPic.getPicBaseUrl() ,voucherFrontPic.getPicSuffix(), pic_size_120_120);
		}
		if(!Check.NuNObj(voucherHandPic)){
			handpic = PicUtil.getFullPic(picBaseAddrMona,voucherHandPic.getPicBaseUrl() ,voucherHandPic.getPicSuffix(), pic_size_120_120);
		}
		if(!Check.NuNObj(voucherHeadPic)){
			if(!Check.NuNObj(latestUnAuditHeadPic) && !Check.NuNStr(latestUnAuditHeadPic.getPicBaseUrl())){
				headpic = PicUtil.getFullPic(picBaseAddrMona,latestUnAuditHeadPic.getPicBaseUrl() ,latestUnAuditHeadPic.getPicSuffix(), pic_size_120_120);
			}else{
				headpic = PicUtil.getFullPic(picBaseAddrMona,voucherHeadPic.getPicBaseUrl() ,voucherHeadPic.getPicSuffix(), pic_size_120_120);
			}
			
			request.setAttribute("imgUploaded", true);//已经上传图片
			if(Check.NuNStr(headpic)){//防止空字符串脏数据
				headpic = defaultHeadPic;
			}
			
		}
		 
		int authStep=0;
		if(customerAuthVo.getIsContactAuth()==1){
			authStep=1;
		}
		if(customerAuthVo.getIsContactAuth()==1 && customerAuthVo.getIsIdentityAuth()==1 ){
			authStep=2;
		}
		 
		
		request.setAttribute("authStep", authStep);		
		request.setAttribute("backpic", backpic);
		request.setAttribute("frontpic", frontpic);
		request.setAttribute("handpic", handpic);
		request.setAttribute("headpic", headpic);
		
		// 查询房东个人介绍
		String extJson=customerMsgManagerService.selectCustomerExtByUid(uid);  
		CustomerBaseMsgExtEntity customerBaseMsgExtEntity=SOAResParseUtil.getValueFromDataByKey(extJson, "customerBaseMsgExt", CustomerBaseMsgExtEntity.class);
		if(!Check.NuNObj(unCheckCustomerBaseMsg) && !Check.NuNStr(unCheckCustomerBaseMsg.getCustomerIntroduce())){
			request.setAttribute("customerExt", unCheckCustomerBaseMsg);
		}else{
			request.setAttribute("customerExt", customerBaseMsgExtEntity);
		}
		request.setAttribute("picBaseAddr", picBaseAddr);
	}
	
	/**
	 * 填充房东最新的修改信息
	 * @param latestUnAuditHeadPic
	 * @param uid
	 */
	public void fillLandNewHeadPicAndIntroduce(CustomerPicMsgEntity latestUnAuditHeadPic, CustomerBaseMsgExtEntity unCheckCustomerBaseMsg, String uid, CustomerBaseMsgEntity customerBaseMsg){
		Map<String, Object> map = new HashMap<>();
    	map.put("fieldAuditStatu", 0);
    	map.put("uid", uid);
    	String customerResultJson = customerInfoService.getFieldAuditNewLogByParam(JsonEntityTransform.Object2Json(map));
    	DataTransferObject customerResultDto = JsonEntityTransform.json2DataTransferObject(customerResultJson);
    	if(customerResultDto.getCode()==DataTransferObject.SUCCESS){
    		 List<CustomerFieldAuditVo> customerFieldAuditVoList = customerResultDto.parseData("customerFieldAuditVoList", new TypeReference<List<CustomerFieldAuditVo>>() {});
    	     if(!Check.NuNCollection(customerFieldAuditVoList) && customerFieldAuditVoList.size()>0){
    	    	 for (CustomerFieldAuditVo customerFieldAuditVo : customerFieldAuditVoList) {
    	    		 String fieldPath = customerFieldAuditVo.getFieldPath();
         	    	 String fieldHeadPicPath = ClassReflectUtils.getFieldNamePath(CustomerPicMsgEntity.class,CustomerUpdateLogEnum.Customer_Pic_Msg_Pic_Base_Url.getFieldName());
 					 if(!Check.NuNStr(fieldPath) && !Check.NuNStr(fieldHeadPicPath) && fieldPath.equals(fieldHeadPicPath)){
 						Map<String, Object> headPicMap = new HashMap<String, Object>();
 						headPicMap.put("auditStatus", CustomerAuditStatusEnum.UN_AUDIT.getCode());
 						headPicMap.put("picType", 3);
 						headPicMap.put("uid", uid);
 						String latestUnAuditHeadPicJson = customerInfoService.getLatestUnAuditHeadPic(JsonEntityTransform.Object2Json(headPicMap));
 						DataTransferObject latestUnAuditHeadPicDto = JsonEntityTransform.json2DataTransferObject(latestUnAuditHeadPicJson);
 						if(latestUnAuditHeadPicDto.getCode()==DataTransferObject.SUCCESS){
 							CustomerPicMsgEntity resultUnAuditHeadPic = latestUnAuditHeadPicDto.parseData("customerPicMsg", new TypeReference<CustomerPicMsgEntity>() {});
 						    BeanUtils.copyProperties(resultUnAuditHeadPic, latestUnAuditHeadPic);
 						}
 					}
 					 
 					//修改了个人介绍
 					String fieldIntroducePath = ClassReflectUtils.getFieldNamePath(CustomerBaseMsgExtEntity.class,CustomerUpdateLogEnum.Customer_Base_Msg_Ext_Introduce.getFieldName());
 					if(!Check.NuNStr(fieldPath) && !Check.NuNStr(fieldIntroducePath) && fieldIntroducePath.equals(fieldPath)){
 						//将待审核的字段的newlog的赋到该对象中，以便于审核通过，以该id修改newlog表中该该用户的该审核字段的审核状态
 						unCheckCustomerBaseMsg.setCustomerIntroduce(customerFieldAuditVo.getNewValue());
 						unCheckCustomerBaseMsg.setId(customerFieldAuditVo.getId());
 					} 
 					
 					//修改了昵称
 					String fieldNickNamePath = ClassReflectUtils.getFieldNamePath(CustomerBaseMsgEntity.class,CustomerUpdateLogEnum.Customer_Base_Msg_NickName.getFieldName());
 					if(!Check.NuNStr(fieldPath) && !Check.NuNStr(fieldNickNamePath) && fieldNickNamePath.equals(fieldPath)){
 						//将待审核的字段的newlog的赋到该对象中，以便于审核通过，以该id修改newlog表中该该用户的该审核字段的审核状态
 						customerBaseMsg.setNickName(customerFieldAuditVo.getNewValue());
 					} 
    	    	 }
    	     }
    	}
	}
	
	/**
	 * 
	 * 发送手机验证码
	 *
	 * @author bushujie
	 * @created 2016年7月21日 下午5:47:29
	 *
	 * @param mobileAuthVo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("sendAuthCode")
	@ResponseBody
	public DataTransferObject sendAuthCode(MobileAuthDto mobileAuthVo,HttpServletRequest request){
		DataTransferObject dto=new DataTransferObject();
		if(Check.NuNStr(mobileAuthVo.getMobile())){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("请填写手机号!");
			return dto;
		}
		
		String uid=UserUtils.getCurrentUid();
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("imgId", mobileAuthVo.getImgId());
		paramsMap.put("imgVValue", mobileAuthVo.getImgVal());
		Map<String, String> headerMap = getHeaderMap(request);
		String  registerResult = CloseableHttpsUtil.sendFormPost(this.IMG_VERIFY_CODE_POST, paramsMap, headerMap);
		Map<String, String> mapResult = (Map<String, String>) JsonEntityTransform.json2Map(registerResult);
		if(!Check.NuNMap(mapResult)){
			LoginDataDto loginDataDto = new LoginDataDto();
			loginDataDto.setCode(mapResult.get("code"));
			loginDataDto.setMessage(mapResult.get("message"));
			loginDataDto.setSys(mapResult.get("sys"));
			loginDataDto.setResp((Map<String, String>) JsonEntityTransform.json2Map(JsonEntityTransform.Object2Json(mapResult.get("resp"))));
			//验证成功发送短信验证码
			if(LoginCodeEnum.SUCCESS.getCode().equals(loginDataDto.getCode())){
				
				//验证成功后将图片验证码缓存，以便再次校验
				String imgKey= RedisKeyConst.getMobileCodeKey(uid,mobileAuthVo.getImgId(),PortalFdConstant.PC_MOBILE_CODE_SUFFIX);
				try {
					LogUtil.info(LOGGER, "当前图片验证码为:vcode={}", mobileAuthVo.getImgVal());
					this.redisOperations.setex(imgKey, RedisKeyConst.MOBILE_CODE_CACHE_TIME, mobileAuthVo.getImgVal());
				} catch (Exception e) {
					LogUtil.error(LOGGER, "redis错误e={}",e);
				}
				
				//获取验证码
		        String vcode = randomUtil.getNumrOrChar(6, "num");
		        //uid
		        String key = RedisKeyConst.getMobileCodeKey(uid,mobileAuthVo.getMobile(),PortalFdConstant.PC_MOBILE_CODE_SUFFIX);
		        SmsRequest smsRequest = new SmsRequest();
				smsRequest.setMobile(mobileAuthVo.getMobile());
				smsRequest.setSmsCode(String.valueOf(MessageTemplateCodeEnum.MOBILE_CODE.getCode()));
				Map<String, String> paMap = new HashMap<String, String>();
				paMap.put("{1}", vcode);
				paMap.put("{2}", String.valueOf(RedisKeyConst.MOBILE_CODE_CACHE_TIME/60));
				smsRequest.setParamsMap(paMap);
				dto = JsonEntityTransform.json2DataTransferObject( this.smsTemplateService.sendSmsByCode(JsonEntityTransform.Object2Json(smsRequest)));
				if(dto.getCode()== DataTransferObject.SUCCESS){
					try {
						LogUtil.info(LOGGER, "当前手机验证码为:vcode={}", vcode);
						this.redisOperations.setex(key, RedisKeyConst.MOBILE_CODE_CACHE_TIME, vcode);
					} catch (Exception e) {
						LogUtil.error(LOGGER, "redis错误e={}",e);
					}
					//dto.putValue("vcode", vcode);
				}
			}else{
				dto.setErrCode(DataTransferObject.ERROR);
//				dto.setMsg(loginDataDto.getMessage());
				dto.setMsg("图片验证码错误！");
			}
		}
		return dto;
	}
	
	
	/**
	 * 
	 * 房东联系方式修改
	 *
	 * @author bushujie
	 * @created 2016年8月2日 下午3:03:48
	 *
	 * @param mobile
	 * @param smsCode
	 * @return
	 */
	@RequestMapping("saveMobile")
	@ResponseBody
	public DataTransferObject saveMobile(String mobile,String smsCode,String imgId,String imgVal,HttpServletRequest request ){
		//获取用户uid
		String uid=UserUtils.getCurrentUid();
		DataTransferObject dto=new DataTransferObject();
		
		if(Check.NuNStr(imgId)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("请重新刷新图片验证码!");
			return dto;
		}
		if(Check.NuNStr(imgVal)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("请填写图片验证码!");
			return dto;
		}
		
		//校验图片验证码
		try {
			String imgKey= RedisKeyConst.getMobileCodeKey(uid,imgId,PortalFdConstant.PC_MOBILE_CODE_SUFFIX);
			String imgCode=redisOperations.get(imgKey);
			if(Check.NuNStr(imgCode)||!imgCode.equals(imgVal)){
	        	dto.setErrCode(1);
	        	dto.setMsg("图片验证码错误");
	        	return dto;
	        }else {//图片验证码校验通过
	        	String key = RedisKeyConst.getMobileCodeKey(uid,mobile,PortalFdConstant.PC_MOBILE_CODE_SUFFIX);
	        	String mobileCode=redisOperations.get(key);
	            if(!Check.NuNStr(mobileCode)&&mobileCode.equals(smsCode)){
	            	String resultJson=customerInfoService.updateLandPhone(mobile, uid, null);
	            	dto=JsonEntityTransform.json2DataTransferObject(resultJson);
	            } else {
	    			dto.setErrCode(1);
	    			dto.setMsg("手机验证码错误");
	    		}
	        	
			} 			
		} catch (Exception e) {
			LogUtil.error(LOGGER, "redis错误e={}",e);
		}
		
		return dto;
	}
	/**
	 * 
	 * 获取公用头信息
	 *
	 * @author yd
	 * @created 2016年5月11日 下午11:51:11
	 *
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Map<String, String> getHeaderMap(HttpServletRequest request){

		Map<String, String> map = new HashMap<String, String>();
		Enumeration<String> headerNames = request.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			String key = headerNames.nextElement();
			String value = request.getHeader(key);
			map.put(key, value);

		}
		LoginHeaderDto loginHeaderDto = new LoginHeaderDto();
		if(!Check.NuNStr(map.get("client-version")))
			loginHeaderDto.setClientVersion(map.get("client-version"));
		if(!Check.NuNStr(map.get("client-type")))
			loginHeaderDto.setClientType(Integer.valueOf(map.get("client-type")));
		if(!Check.NuNStr(map.get("user-agent")))
			loginHeaderDto.setUserAgent(map.get("user-agent"));

		if(Check.NuNStr(loginHeaderDto.getClientVersion())){
			loginHeaderDto.setClientVersion("1.0");
		}
		if(Check.NuNObj(loginHeaderDto.getClientType())){
			loginHeaderDto.setClientType(3);
		}
		loginHeaderDto.setAccept(minsuWebAccept);
		loginHeaderDto.setSys(minsuWebSys);
		Map<String, String> headerMap = new HashMap<String, String>();
		if(!checkLoginHeader(loginHeaderDto)){
			LogUtil.debug(LOGGER, "登录头信息验证失败，当前头信息loginHeaderDto={}", loginHeaderDto.toString());
			return null;
		}

		headerMap.put(HeaderParamName.ACCEPT,loginHeaderDto.getAccept());
		headerMap.put(HeaderParamName.CLIENT_TYPE,loginHeaderDto.getClientType()+"");
		headerMap.put(HeaderParamName.CLIENT_VERSION, loginHeaderDto.getClientVersion());
		headerMap.put(HeaderParamName.REQUEST_ID, loginHeaderDto.getRequestId());
		headerMap.put(HeaderParamName.SYS, loginHeaderDto.getSys());
		headerMap.put(HeaderParamName.USER_AGENT, loginHeaderDto.getUserAgent());
		return headerMap;
	}
	
	/**
	 *
	 * @author yd
	 * @created 2016年5月4日 下午11:25:00
	 *
	 * @param loginHeaderDto
	 * @return
	 */
	private boolean checkLoginHeader(LoginHeaderDto loginHeaderDto){

		if(Check.NuNObj(loginHeaderDto)
				||Check.NuNStr(loginHeaderDto.getAccept())
				||Check.NuNStr(loginHeaderDto.getClientVersion())
				||Check.NuNStr(loginHeaderDto.getRequestId())
				||Check.NuNStr(loginHeaderDto.getSys())
				||Check.NuNStr(loginHeaderDto.getUserAgent())
				||Check.NuNObj(loginHeaderDto.getClientType())){
			return false;
		}

		return true;
	}
	
	/**
	 * 
	 * 1. 初始化个人资料
	 * 2. 同步房客头像
	 *
	 * @author bushujie
	 * @throws SOAParseException 
	 * @created 2016年7月22日 下午6:29:17
	 *
	 */
	@RequestMapping("initPersonData")
	public void initPersonData(HttpServletRequest request) throws SOAParseException{
		//获取用户uid
		String uid=UserUtils.getCurrentUid();
		//查询房东基本信息
		String resultJson=customerInfoService.getCustomerInfoByUid(uid);
		CustomerBaseMsgEntity customerBaseMsgEntity=SOAResParseUtil.getValueFromDataByKey(resultJson, "customerBase", CustomerBaseMsgEntity.class);
		String customerEduName = customerBaseMsgEntity.getCustomerEdu() == null ? "" :(CustomerEduEnum.getCustomerEduByCode(customerBaseMsgEntity.getCustomerEdu()).getName());
		
		request.setAttribute("customerEduName", customerEduName);
		// 查询房东个人介绍
		String extJson=customerMsgManagerService.selectCustomerExtByUid(uid);  
		CustomerBaseMsgExtEntity customerBaseMsgExtEntity=SOAResParseUtil.getValueFromDataByKey(extJson, "customerBaseMsgExt", CustomerBaseMsgExtEntity.class);
		
		//如果此房东的审核通过后，修改了头像或者个人介绍，则填充最新修改的后的值
		CustomerPicMsgEntity latestUnAuditHeadPic = new CustomerPicMsgEntity();
		CustomerBaseMsgExtEntity unCheckCustomerBaseMsg = new CustomerBaseMsgExtEntity();
		//从t_customer_update_history_log表中查询,看是否存在需要替换的字段
		if(customerBaseMsgEntity.getIsLandlord()==CustomerTypeEnum.landlord.getStatusCode()){
				fillLandNewHeadPicAndIntroduce(latestUnAuditHeadPic,unCheckCustomerBaseMsg, uid, customerBaseMsgEntity);
		}
		//如果是默认昵称，置为空
		if(customerBaseMsgEntity.getIsLandlord()==YesOrNoEnum.YES.getCode() && !Check.NuNStr(customerBaseMsgEntity.getNickName())){
			if(isDefaultNickName(customerBaseMsgEntity.getNickName(), customerBaseMsgEntity.getUid())){
				customerBaseMsgEntity.setNickName(null);
			}
		}
		request.setAttribute("customerBase", customerBaseMsgEntity);
		if(!Check.NuNObj(unCheckCustomerBaseMsg) && !Check.NuNStr(unCheckCustomerBaseMsg.getCustomerIntroduce())){
			request.setAttribute("customerExt", unCheckCustomerBaseMsg);
		}else{
			request.setAttribute("customerExt", customerBaseMsgExtEntity);
		}		
		request.setAttribute("nowDate", DateUtil.dateFormat(new Date()));
		
		
		//异步更新用户头像
		if(customerBaseMsgEntity.getIsLandlord() == IsLandlordEnum.NOT_LANDLORD.getCode()){
			Thread  thread = new Thread(new Runnable() {
				@Override
				public void run() {
					CustomerPicMsgEntity customerPicMsg = UserHeadImgUtils.getHeadImgFromZiroom(uid, SSO_USER_DETAIL_URL);
					if(!Check.NuNObj(customerPicMsg)){
						List<CustomerPicMsgEntity> picList = new ArrayList<CustomerPicMsgEntity>();
						picList.add(customerPicMsg);
						customerAuthService.customerIconAuth(JsonEntityTransform.Object2Json(picList));
					}
				}
			});
			thread.start();
		}
	}
	
	/**
	 * 校验是否是默认昵称
	 * @author loushuai
	 * @param nickName
	 * @param uid
	 * @return
	 */
	public boolean isDefaultNickName(String nickName, String uid){
		if(!Check.NuNStr(nickName) && !Check.NuNStr(uid)){
			String uidTemp = uid.substring(uid.length()-5);
			String check = "自如客" + uidTemp;
			if(nickName.equals(check)){
				return true;
			} 
		}
		return false;
	}
	/**
	 * 
	 * 更新客户个人资料
	 *
	 * @author bushujie
	 * @created 2016年7月26日 下午3:40:01
	 *
	 * @param customerBaseExtDto
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping("updatePersonData")
	@ResponseBody
	public DataTransferObject  updatePersonData(CustomerBaseExtDto customerBaseExtDto) throws ParseException{
		//获取用户uid
		String uid=UserUtils.getCurrentUid();
		if(!Check.NuNStrStrict(customerBaseExtDto.getCustomerBirthdayStr())){
			customerBaseExtDto.setCustomerBirthday(DateUtil.parseDate(customerBaseExtDto.getCustomerBirthdayStr(), "yyyy-MM-dd"));
		}
		customerBaseExtDto.setUid(uid);
		String resultJson=customerInfoPcService.updatePersonData(JsonEntityTransform.Object2Json(customerBaseExtDto));
		return JsonEntityTransform.json2DataTransferObject(resultJson);
	}
	
	/**
	 * 
	 * 初始化认证信息
	 *
	 * @author bushujie
	 * @created 2016年7月26日 下午6:59:21
	 *
	 * @param request
	 * @throws SOAParseException 
	 */
	@RequestMapping("initAuthData")
	public void initAuthData(HttpServletRequest request) throws SOAParseException{
		//获取用户uid
		String uid=UserUtils.getCurrentUid();
		String resultJson=customerInfoPcService.initCustomerAuthData(uid);
		CustomerAuthVo customerAuthVo=SOAResParseUtil.getValueFromDataByKey(resultJson, "customerAuthVo", CustomerAuthVo.class);
		List<CustomerIdTypeEnum> idTypeList= CustomerIdTypeEnum.getALLCustomerIdTypeEnums();
		request.setAttribute("idTypeName", CustomerIdTypeEnum.getCustomerIdTypeByCode(customerAuthVo.getIdType()).getName());
		request.setAttribute("customerAuthVo", customerAuthVo);
		request.setAttribute("idTypeList", idTypeList);
		request.setAttribute("picBaseAddrMona", picBaseAddrMona);
		request.setAttribute("picSize", picSize);
	}
	
	
	/**
	 * 
	 * 保存客户认证信息
	 *
	 * @author bushujie
	 * @created 2016年7月27日 下午7:10:09
	 *
	 * @param customerAuthVo
	 * @param frontPicStr
	 * @param backPicStr
	 * @param handPicStr
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("updateAuthData")
	@ResponseBody
	public DataTransferObject updateAuthData(CustomerAuthVo customerAuthVo,String frontPicStr,String backPicStr, String handPicStr){
		DataTransferObject dto=new DataTransferObject();
		try {
			//获取用户uid
			String uid=UserUtils.getCurrentUid();

            //保存操作之前，查询库信息判断是否已审核，通过则不许修改
            CustomerAuthVo customerAuthVoDb = SOAResParseUtil.getValueFromDataByKey(customerInfoPcService.initCustomerAuthData(uid), "customerAuthVo", CustomerAuthVo.class);
            if (customerAuthVoDb.getAuditStatus() == 1) {
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("您的认证信息已经审核通过，无法修改，请刷新页面");
                return dto;
            }

			customerAuthVo.setUid(uid);
			//身份认证通过
			customerAuthVo.setIsIdentityAuth(YesOrNoEnum.YES.getCode());
			customerAuthVo.setIsLandlord(YesOrNoEnum.YES.getCode());
			FileInfoResponse fileResponse=null;
			String oldVoucherFrontPic=null;
			String oldVoucherBackPic=null;
			String oldVoucherHandPic=null;
			
			
			Pattern p = Pattern.compile("^[0-9]*$",Pattern.CASE_INSENSITIVE);
			Matcher matcher = p.matcher(customerAuthVo.getRealName());
			if (matcher.find()) {
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("姓名不能全为数字");
				return dto;
			}
			
					
			//证件正面照片是否替换
			if(!Check.NuNStr(frontPicStr)){
				fileResponse=upLoadFileBase64(frontPicStr, CustomerPicTypeEnum.ZJZM.getName(), "民宿客户证件正面照片");
				if(!Check.NuNObj(customerAuthVo)&&!Check.NuNStr(customerAuthVo.getVoucherFrontPic().getPicServerUuid())){
					oldVoucherFrontPic=customerAuthVo.getVoucherFrontPic().getPicServerUuid();
				}
				if(!Check.NuNObj(customerAuthVo)){
					customerAuthVo.getVoucherFrontPic().setUid(uid);
					customerAuthVo.getVoucherFrontPic().setPicType(CustomerPicTypeEnum.ZJZM.getCode());
					customerAuthVo.getVoucherFrontPic().setPicName(fileResponse.getFile().getOriginalFilename());
					customerAuthVo.getVoucherFrontPic().setPicBaseUrl(fileResponse.getFile().getUrlBase());
					customerAuthVo.getVoucherFrontPic().setPicSuffix(fileResponse.getFile().getUrlExt());
					customerAuthVo.getVoucherFrontPic().setPicServerUuid(fileResponse.getFile().getUuid());
				}
			}
			//证件反面照片是否替换
			if(!Check.NuNStr(backPicStr)){
				fileResponse=upLoadFileBase64(backPicStr, CustomerPicTypeEnum.ZJFM.getName(), "民宿客户证件反面照片");
				if(!Check.NuNObj(customerAuthVo)&&!Check.NuNObj(customerAuthVo.getVoucherBackPic())&&!Check.NuNStr(customerAuthVo.getVoucherBackPic().getPicServerUuid())){
					oldVoucherBackPic=customerAuthVo.getVoucherBackPic().getPicServerUuid();
				}
				if(!Check.NuNObj(customerAuthVo)){
					customerAuthVo.getVoucherBackPic().setUid(uid);
					customerAuthVo.getVoucherBackPic().setPicType(CustomerPicTypeEnum.ZJFM.getCode());
					customerAuthVo.getVoucherBackPic().setPicName(fileResponse.getFile().getOriginalFilename());
					customerAuthVo.getVoucherBackPic().setPicBaseUrl(fileResponse.getFile().getUrlBase());
					customerAuthVo.getVoucherBackPic().setPicSuffix(fileResponse.getFile().getUrlExt());
					customerAuthVo.getVoucherBackPic().setPicServerUuid(fileResponse.getFile().getUuid());
				}
			}
			//证件手持照片是否替换
			if(!Check.NuNStr(handPicStr)){
				if(customerAuthVo.getIdType()==CustomerIdTypeEnum.CHARTER.getCode()){
					fileResponse=upLoadFileBase64(handPicStr, CustomerPicTypeEnum.YYZZ.getName(), "民宿客户营业执照照片");
				}else{
					fileResponse=upLoadFileBase64(handPicStr, CustomerPicTypeEnum.ZJSC.getName(), "民宿客户证件手持照片");
				}
				
				if(!Check.NuNObj(customerAuthVo)&&!Check.NuNObj(customerAuthVo.getVoucherHandPic())&&!Check.NuNStr(customerAuthVo.getVoucherHandPic().getPicServerUuid())){
					oldVoucherHandPic=customerAuthVo.getVoucherHandPic().getPicServerUuid();
				}
				if(!Check.NuNObj(customerAuthVo)){
					customerAuthVo.getVoucherHandPic().setUid(uid);
					if(customerAuthVo.getIdType()==CustomerIdTypeEnum.CHARTER.getCode()){
						customerAuthVo.getVoucherHandPic().setPicType(CustomerPicTypeEnum.YYZZ.getCode());
					}else{
						customerAuthVo.getVoucherHandPic().setPicType(CustomerPicTypeEnum.ZJSC.getCode());
					}
					customerAuthVo.getVoucherHandPic().setPicName(fileResponse.getFile().getOriginalFilename());
					customerAuthVo.getVoucherHandPic().setPicBaseUrl(fileResponse.getFile().getUrlBase());
					customerAuthVo.getVoucherHandPic().setPicSuffix(fileResponse.getFile().getUrlExt());
					customerAuthVo.getVoucherHandPic().setPicServerUuid(fileResponse.getFile().getUuid());
				}
			}
			//同步信息到自如网，验证证件信息是否被占用 开始
			Map<String,String> savePar = new HashMap<>();
			savePar.put("uid",uid);
			savePar.put("real_name",customerAuthVo.getRealName());
			savePar.put("cert_type", ValueUtil.getStrValue(customerAuthVo.getIdType()));
			savePar.put("cert_num", ValueUtil.getStrValue(customerAuthVo.getIdNo()));
			String sign = SignUtils.md5AppkeySign(appKeyValue, savePar);
			savePar.put("sign",sign);
			savePar.put("appid",saveAppid);
			savePar.put("auth_code",AUTH_CODE);
			savePar.put("auth_secret_key",AUTH_SECRET_KEY);
			
            String rstJson = CloseableHttpsUtil.sendFormPost(saveCertUid, savePar);
			JSONObject json = JSONObject.parseObject(rstJson);
			LogUtil.info(LOGGER,"同步用户信息到自如网返回结果：rstJson:{}",rstJson);
			Integer code = json.getInteger("error_code");
			AccountSignEnum signEnum = AccountSignEnum.getAccountSignEnumByCode(code);
			if(signEnum == null){
				 dto.setErrCode(DataTransferObject.ERROR);
				 dto.setMsg("同步用户信息到自如网失败");
				 return dto;
			}
			if (code !=AccountSignEnum.ACCOUNT_CODE_0.getCode()){
				String msg = signEnum.getShowTip();
                LogUtil.info(LOGGER,"同步用户信息到自如网失败,失败原因"+json.getString("error_message"));
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg(msg);
                return dto;
        	}
			//同步信息到自如网，验证证件信息是否被占用 结束
			//调用服务修改数据
			String resultJson=customerInfoPcService.updateCustomerAuthData(JsonEntityTransform.Object2Json(customerAuthVo));
			dto=JsonEntityTransform.json2DataTransferObject(resultJson);
//			//删除图片服务器上旧图片
//			if(!Check.NuNStr(oldVoucherFrontPic)){
//				boolean isNO=storageService.delete(oldVoucherFrontPic);
//				LogUtil.info(LOGGER, "删除民宿客户证件正面照片", isNO);
//			}
//			if(!Check.NuNStr(oldVoucherBackPic)){
//				boolean isNO=storageService.delete(oldVoucherBackPic);
//				LogUtil.info(LOGGER, "删除民宿客户证件反面照片", isNO);
//			}
//			if(!Check.NuNStr(oldVoucherHandPic)){
//				boolean isNO=storageService.delete(oldVoucherHandPic);
//				LogUtil.info(LOGGER, "删除民宿客户证件手持照片", isNO);
//			}

		} catch (Exception e) {
			LogUtil.error(LOGGER, "资质认证异常e={}", e);
		}
		return dto;
	}
	
	/**
	 * 
	 * 上传base64图片文件
	 *
	 * @author bushujie
	 * @created 2016年7月27日 下午7:26:51
	 *
	 * @param imgBase64
	 * @return
	 * @throws IOException 
	 */
	private FileInfoResponse upLoadFileBase64(String imgBase64,String imgType,String imgDesc) throws IOException{
		String str =imgBase64;
		if(imgBase64.split(",").length==2){
			str = imgBase64.substring(imgBase64.indexOf("base64") +7);
		} 
		byte[] b = Base64Utils.decodeFromString(str); 
        for(int i=0;i<b.length;++i) {  
            if(b[i]<0){//调整异常数据  
                b[i]+=256;  
            }  
        }
        
        //图片上传成功后才删除原图片
        String fileName = UUIDGenerator.hexUUID() + ".jpg";
        FileInfoResponse fileResponse = storageService.upload(storageKey, storageLimit, fileName, b, imgType,0l, imgDesc);
        return fileResponse;
	}
	
	/**
	 * 
	 * img转换base64
	 *
	 * @author bushujie
	 * @created 2016年7月29日 下午7:39:35
	 *
	 * @param picFile
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value="imgFileToBase64",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String imgFileToBase64(MultipartFile picFile){
		DataTransferObject dto=new DataTransferObject();
		try {
			
			if(Check.NuNObj(picFile)){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("文件为空");
				return dto.toJsonString();
			}
			byte[] bytes = picFile.getBytes();
			if(picFile.getSize()>1024*500){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("文件太大");
				return dto.toJsonString();
			}
			String encodeString=Base64Utils.encodeToString(bytes);
			dto.putValue("picBase64", encodeString);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "图片转base64错误e={}", e);
		}
		return dto.toJsonString();
	}
	/**
	 * 
	 * 头像上传
	 *
	 * @author bushujie
	 * @created 2016年8月1日 上午11:47:48
	 *
	 * @param UpFile
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value="uploadHeadImg",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String uploadHeadImg(MultipartFile UpFile,HttpServletRequest request){
		DataTransferObject dto=new DataTransferObject();
		try {
			FileInfoResponse fileResponse=storageService.upload(storageKey, storageLimit, UpFile.getOriginalFilename(),UpFile.getBytes(),"房东头像", 0l,UpFile.getOriginalFilename());
			if(!Check.NuNObj(fileResponse.getFile())){
				dto.putValue("headUrl", fileResponse.getFile().getUrl());
				dto.putValue("headUUid", fileResponse.getFile().getUuid());
			} else {
				dto.setErrCode(1);
				dto.setMsg("头像上传失败!");
			}
		} catch (Exception e) {
			LogUtil.error(LOGGER, "上传头像错误e={}", e);
		}
		
		return dto.toJsonString();
	}
	
	/**
	 * 
	 * 裁剪保存头像图片(非ie)
	 *
	 * @author bushujie
	 * @created 2016年8月3日 下午3:07:00
	 *
	 * @param UpFile
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value="saveHeadImg",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String saveHeadImg(MultipartFile UpFile,HttpServletRequest request) {
		
		DataTransferObject dto =new DataTransferObject();
		try {
			//获取用户uid
			String uid=UserUtils.getCurrentUid();
			FileInfoResponse fileResponse=storageService.upload(storageKey, storageLimit, UUIDGenerator.hexUUID()+".jpg",UpFile.getBytes(),"房东头像", 0l,"剪切房东头像");
			if(!Check.NuNObj(fileResponse.getFile())){
				//保存更新房东头像
				CustomerPicMsgEntity customerPicMsgEntity=new CustomerPicMsgEntity();
				customerPicMsgEntity.setUid(uid);
				customerPicMsgEntity.setPicType(CustomerPicTypeEnum.YHTX.getCode());
				customerPicMsgEntity.setPicName(fileResponse.getFile().getOriginalFilename());
				customerPicMsgEntity.setPicBaseUrl(fileResponse.getFile().getUrlBase());
				customerPicMsgEntity.setPicSuffix(fileResponse.getFile().getUrlExt());
				customerPicMsgEntity.setPicServerUuid(fileResponse.getFile().getUuid());
				customerPicMsgEntity.setFid(UUIDGenerator.hexUUID());
				List<CustomerPicMsgEntity> picList=new ArrayList<CustomerPicMsgEntity>();
				picList.add(customerPicMsgEntity);
				String resultJson=customerAuthService.customerIconAuth(JsonEntityTransform.Object2Json(picList));
				LogUtil.info(LOGGER, "【上传头像返回结果】result={}", resultJson);
				//String serImgUuid=SOAResParseUtil.getStrFromDataByKey(resultJson, "picServerUuid");
//				//删除老头像
//				if(!Check.NuNStr(serImgUuid)){
//					storageService.delete(serImgUuid);
//				}
				
				//上传头像更新成功以后 上传头像状态为已上传
				DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(resultJson);
				if(resultDto.getCode() == DataTransferObject.SUCCESS){
					DataTransferObject customerDto = JsonEntityTransform.json2DataTransferObject(customerMsgManagerService.getCustomerBaseMsgEntitybyUid(uid));
					CustomerBaseMsgEntity customerBaseMsg = customerDto.parseData("customer", new TypeReference<CustomerBaseMsgEntity>() {});
					LogUtil.info(LOGGER, "【查询用户信息】info={}", JsonEntityTransform.Object2Json(customerBaseMsg));
					if(customerBaseMsg.getIsUploadIcon() == 0){
						customerBaseMsg.setIsUploadIcon(1);
						customerMsgManagerService.updateCustomerBaseMsg(JsonEntityTransform.Object2Json(customerBaseMsg));
					}
					
				}
				
				dto.putValue("ImgUrl", fileResponse.getFile().getUrl());
				dto.putValue("imgUuid", fileResponse.getFile().getUuid());
				dto.putValue("imgUploaded", true);//已经上传图片
			} else {
				dto.setErrCode(1);
				dto.setMsg("头像上传失败");
			}
			
		} catch (Exception e) {
			LogUtil.error(LOGGER, "上传头像错误e={}", e);
		}
		return dto.toJsonString();
	}
	
	/**
	 * 
	 * 裁剪保存头像图片(ie)
	 *
	 * @author bushujie
	 * @created 2016年8月3日 下午8:01:14
	 *
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings({ "unused", "unchecked" })
	@RequestMapping(value="saveHeadImgIE",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String saveHeadImgIE(HttpServletRequest request){
		
		DataTransferObject dto =new DataTransferObject();
		try {
			//获取用户uid
			String uid=UserUtils.getCurrentUid();
			String JSdate=request.getParameter("JSdate");
			String shearPhoto=request.getParameter("shearphoto");
			shearPhoto=shearPhoto.replaceAll("px", "");
			String[] paramS=shearPhoto.split(",");
			Map<String, Object> paramMap=(Map<String, Object>) JsonEntityTransform.json2Map(JSdate);
	        URL url = new URL(paramMap.get("url").toString().substring(3));    
	        HttpURLConnection conn = (HttpURLConnection)url.openConnection();    
	        conn.setRequestMethod("GET");    
	        conn.setConnectTimeout(5 * 1000);    
	        InputStream inStream = conn.getInputStream();//通过输入流获取图片数据 
	        ByteArrayOutputStream out=new ByteArrayOutputStream();
	        ImageHelper.createPreviewImageofCut(inStream, out, new BigDecimal(paramMap.get("X").toString()).setScale(0, BigDecimal.ROUND_HALF_UP).intValue(), new BigDecimal(paramMap.get("Y").toString()).setScale(0, BigDecimal.ROUND_HALF_UP).intValue(),
	        		new BigDecimal(paramMap.get("IW").toString()).setScale(0, BigDecimal.ROUND_HALF_UP).intValue(), new BigDecimal(paramMap.get("IH").toString()).setScale(0, BigDecimal.ROUND_HALF_UP).intValue());
	        FileInfoResponse fileResponse=storageService.upload(storageKey, storageLimit, UUIDGenerator.hexUUID()+".jpg",out.toByteArray(),"房东头像", 0l,"剪切房东头像");
	        if(!Check.NuNObj(fileResponse.getFile())){
				//保存更新房东头像
				CustomerPicMsgEntity customerPicMsgEntity=new CustomerPicMsgEntity();
				customerPicMsgEntity.setUid(uid);
				customerPicMsgEntity.setPicType(CustomerPicTypeEnum.YHTX.getCode());
				customerPicMsgEntity.setPicName(fileResponse.getFile().getOriginalFilename());
				customerPicMsgEntity.setPicBaseUrl(fileResponse.getFile().getUrlBase());
				customerPicMsgEntity.setPicSuffix(fileResponse.getFile().getUrlExt());
				customerPicMsgEntity.setPicServerUuid(fileResponse.getFile().getUuid());
				customerPicMsgEntity.setFid(UUIDGenerator.hexUUID());
				List<CustomerPicMsgEntity> picList=new ArrayList<CustomerPicMsgEntity>();
				picList.add(customerPicMsgEntity);
				String resultJson=customerAuthService.customerIconAuth(JsonEntityTransform.Object2Json(picList));
				String serImgUuid=SOAResParseUtil.getStrFromDataByKey(resultJson, "picServerUuid");
//				//删除老头像
//				if(!Check.NuNStr(serImgUuid)){
//					storageService.delete(serImgUuid);
//				}
				
				//上传头像更新成功以后 上传头像状态为已上传
				DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(resultJson);
				if(resultDto.getCode() == DataTransferObject.SUCCESS){
					DataTransferObject customerDto = JsonEntityTransform.json2DataTransferObject(customerMsgManagerService.getCustomerBaseMsgEntitybyUid(uid));
					CustomerBaseMsgEntity customerBaseMsg = customerDto.parseData("customer", new TypeReference<CustomerBaseMsgEntity>() {});
					if(customerBaseMsg.getIsUploadIcon() == 0){
						customerBaseMsg.setIsUploadIcon(1);
						customerInfoService.updateCustomerInfo(JsonEntityTransform.Object2Json(customerBaseMsg));
					}
					
				}
				
				
				dto.putValue("ImgUrl", fileResponse.getFile().getUrl());
				dto.putValue("imgUuid", fileResponse.getFile().getUuid());
			} else {
				dto.setErrCode(1);
				dto.setMsg("头像上传失败");
			}
		} catch (Exception e) {
			LogUtil.error(LOGGER, "IE上传头像错误e={}", e);
		}
		
		return dto.toJsonString();
	}
	
	/**
	 * 
	 * 
	 *
	 * @author bushujie
	 * @created 2016年8月4日 下午4:46:47
	 *
	 * @param customerBaseExtDto
	 * @return
	 */
	@RequestMapping("updateNickNameAndExt")
	@ResponseBody
	public DataTransferObject updateNickNameAndExt(CustomerBaseExtDto customerBaseExtDto){
		//获取用户uid		
		DataTransferObject dto =new DataTransferObject();
		
		try {
			
			if(Check.NuNObj(customerBaseExtDto)){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("信息为空");
				return dto;
			}
			
			if(Check.NuNStr(customerBaseExtDto.getNickName())){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("昵称不能为空");
				return dto;
			}
			if(customerBaseExtDto.getNickName().length()>30){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("昵称过长");
				return dto;
			}
			if(Check.NuNStr(customerBaseExtDto.getCustomerIntroduce())){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("个人介绍不能为空");
				return dto;
			}
			
			if(customerBaseExtDto.getCustomerIntroduce().length()<70){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("个人介绍不能少于70字");
				return dto;
			}
			
			if(customerBaseExtDto.getCustomerIntroduce().length()>500){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("个人介绍过长");
				return dto;
			}
			
			
			if(Check.NuNObj(customerBaseExtDto)){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("信息为空");
				return dto;
			}
			
			String uid=UserUtils.getCurrentUid();
			customerBaseExtDto.setUid(uid);
			String resultJson=customerInfoPcService.updatePersonData(JsonEntityTransform.Object2Json(customerBaseExtDto));
			dto= JsonEntityTransform.json2DataTransferObject(resultJson);
			
		} catch (Exception e) {
			LogUtil.error(LOGGER, "更新昵称错误e={}", e);
		}
		return dto;
	}
}
