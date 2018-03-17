package com.ziroom.minsu.mapp.customer.controller;

import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.exception.SOAParseException;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.MessageSourceUtil;
import com.asura.framework.base.util.SOAResParseUtil;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.cache.redisOne.RedisOperations;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.base.NationCodeEntity;
import com.ziroom.minsu.entity.customer.CustomerBaseMsgEntity;
import com.ziroom.minsu.entity.customer.CustomerPicMsgEntity;
import com.ziroom.minsu.mapp.common.constant.HeaderParamName;
import com.ziroom.minsu.mapp.common.constant.MappMessageConst;
import com.ziroom.minsu.mapp.common.dto.LoginDataDto;
import com.ziroom.minsu.mapp.common.dto.LoginHeaderDto;
import com.ziroom.minsu.mapp.common.enumvalue.LoginCodeEnum;
import com.ziroom.minsu.mapp.common.util.CustomerVoUtils;
import com.ziroom.minsu.services.basedata.api.inner.ConfCityService;
import com.ziroom.minsu.services.basedata.api.inner.SmsTemplateService;
import com.ziroom.minsu.services.basedata.dto.SmsRequest;
import com.ziroom.minsu.services.common.constant.MessageConst;
import com.ziroom.minsu.services.common.utils.CloseableHttpsUtil;
import com.ziroom.minsu.services.common.utils.RedisKeyConst;
import com.ziroom.minsu.services.common.utils.randomUtil;
import com.ziroom.minsu.services.customer.api.inner.CustomerAuthService;
import com.ziroom.minsu.services.customer.api.inner.CustomerInfoService;
import com.ziroom.minsu.services.customer.api.inner.CustomerMsgManagerService;
import com.ziroom.minsu.services.customer.entity.CustomerVo;
import com.ziroom.minsu.valenum.customer.CusotmerAuthEnum;
import com.ziroom.minsu.valenum.customer.CustomerSourceEnum;
import com.ziroom.minsu.valenum.customer.PicTypeEnum;
import com.ziroom.minsu.valenum.msg.IsDelEnum;
import com.ziroom.minsu.valenum.msg.MessageTemplateCodeEnum;

/**
 * 
 * <p>用户登录  注册相关初始信息逻辑控制层</p>
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
@RequestMapping("customer/")
@Controller
public class CustomerIndexController {

	/**
	 * 日志对象
	 */
	private static Logger logger = LoggerFactory.getLogger(CustomerIndexController.class);


	@Resource(name="mapp.messageSource")
	private MessageSource messageSource;

	@Resource(name = "customer.customerInfoService")
	private CustomerInfoService customerInfoService;

	@Resource(name = "customer.customerMsgManagerService")
	private CustomerMsgManagerService customerMsgManagerService;

	@Resource(name = "basedata.smsTemplateService")
	private SmsTemplateService smsTemplateService;

	@Resource(name = "customer.customerAuthService")
	private CustomerAuthService customerAuthService;

	@Value("#{'${LOGIN_REGISTER_URL}'.trim()}")
	private String LOGIN_REGISTER_URL;
	@Value("#{'${minsu.web.sys}'.trim()}")
	private String minsuWebSys;

	@Value("#{'${minsu.web.accept}'.trim()}")
	private String minsuWebAccept;

	@Value("#{'${USER_VERIFY_CODE}'.trim()}")
	private String USER_VERIFY_CODE;

	@Value("#{'${VERIFY_CODE_V1}'.trim()}")
	private String VERIFY_CODE_V1;

	@Value("#{'${USER_LOGIN_V2_POST}'.trim()}")
	private String USER_LOGIN_V2_POST;

	@Value("#{'${IMG_VERIFY_CODE_GET}'.trim()}")
	private String IMG_VERIFY_CODE_GET;

	@Value("#{'${IMG_VERIFY_CODE_POST}'.trim()}")
	private String IMG_VERIFY_CODE_POST;

	@Value("#{'${USER_LOGINOUT_POST}'.trim()}")
	private String USER_LOGINOUT_POST;

	@Value("#{'${minsu.static.resource.url}'.trim()}")
	private String  staticUrl;

	@Value("#{'${USER_RESET_PASSWORD_POST}'.trim()}")
	private String USER_RESET_PASSWORD_POST;

	@Value("#{'${USER_PASSWORD_UPDATE_PUT}'.trim()}")
	private String USER_PASSWORD_UPDATE_PUT;

	@Value("#{'${USER_DEFAULT_PIC_URL}'.trim()}")
	private String USER_DEFAULT_PIC_URL;
	
	@Autowired
	private RedisOperations redisOperations;
	
	@Resource(name="basedata.confCityService")
	private ConfCityService confCityService;
	
	/**
	 * 
	 * 到用户登录页面	 
	 * @author yd
	 * @created 2016年5月9日 下午3:01:06
	 *
	 * @param request
	 */
	@RequestMapping("/login")
	public String toLogin(HttpServletRequest request){

		setImgCode(request);
		return "customer/userLogin";
	}

	/**
	 * 
	 * 用户登录V2
	 * 错误次数大于5次的需要使用图形验证码，传递图形验证码相关参数，获取图形验证码参看获取图形验证码接口
	 * 说明：
	 * 1.校验手机号或者邮箱，加上密码，是否为null
	 * 2.校验失败，返回失败结果
	 * 3.校验通过，去用户中心登录（单点登录）
	 * 4.登录失败，分A.失败次数小于等于5次，B.失败次数大于5次
	 * 5. A.失败，返回失败结果，成功， 
	 * 6. B.失败，去获取图形验证码，校验客户图形验证码，失败，返回失败结果
	 * 7. 登录成功，客户信息保存session，到上一次用户待打开的页面
	 *
	 * @author yd
	 * @created 2016年5月13日 上午1:58:39
	 *
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/userLogin")
	@ResponseBody
	public  DataTransferObject userLogin(HttpServletRequest request){
		//手机号  邮箱 用户名 三者只取其一
		String phone = request.getParameter("phone");
		String email = request.getParameter("email");
		String username = request.getParameter("username");
		String password = request.getParameter("password");//客户端需要传递MD5加密后的密码（md5(‘c’ + 密码 + 'b')），统一为小写
		String imgId = request.getParameter("imgId");//密码错误5次以上，需要传
		String imgVValue = request.getParameter("imgVValue");

		DataTransferObject dto = new DataTransferObject();

		if(Check.NuNStr(phone)&&Check.NuNStr(email)&&Check.NuNStr(username)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource,MappMessageConst.USER_LOGIN_USERNAME_ERROR));
			return dto;
		}
		if(Check.NuNStr(password)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource,MappMessageConst.USER_PASSWORD_REGISTER_ERROR));
			return dto;
		}

		Map<String, String> headerMap = getHeaderMap(request);
		if(Check.NuNObj(headerMap)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MappMessageConst.USER_HEADER__ERROR));
			return dto;
		}

		Map<String, String> paramsMap = new HashMap<String, String>();
		if(!Check.NuNStr(phone)){
			paramsMap.put("phone", phone);
		}else if(!Check.NuNStr(email)){
			paramsMap.put("email", email);
		}else{
			paramsMap.put("username", username);
		}
		paramsMap.put("password", password);

		try {
			
			if(!Check.NuNStr(imgId)&&!Check.NuNStr(imgVValue)){
				
				dto = checkImgCode(request);
				
				if(dto.getCode() == DataTransferObject.ERROR){
					return dto;
				}
				paramsMap.put("imgId", imgId);
				if(!Check.NuNObj(dto.getData())&&!Check.NuNObj(dto.getData().get("imgVid"))){
					paramsMap.put("imgVid", String.valueOf(dto.getData().get("imgVid")));
				}
				
			}
			//去登录
			String  loginResult = CloseableHttpsUtil.sendFormPost(USER_LOGIN_V2_POST, paramsMap, headerMap);
			Map<String, String> mapResult = (Map<String, String>) JsonEntityTransform.json2Map(loginResult);
			if(!Check.NuNMap(mapResult)){
				LoginDataDto loginDataDto = new LoginDataDto();
				loginDataDto.setCode(mapResult.get("code"));
				loginDataDto.setMessage(mapResult.get("message"));
				loginDataDto.setSys(mapResult.get("sys"));
				loginDataDto.setResp((Map<String, String>) JsonEntityTransform.json2Map(JsonEntityTransform.Object2Json(mapResult.get("resp"))));
				//成功返回 
				if(LoginCodeEnum.SUCCESS.getCode().equals(loginDataDto.getCode())){
					String token = (String) loginDataDto.getResp().get("token");
					String uid = (String) loginDataDto.getResp().get("uid");
					LogUtil.info(logger, "用户成功登录返回token={},uid={}", token,uid);

					//客户信息保存到session
					dto = JsonEntityTransform.json2DataTransferObject(this.customerMsgManagerService.getCutomerVoFromDb(uid));

					if(dto.getCode() == 0){
						CustomerVo customerVo = dto.parseData("customerVo", new TypeReference<CustomerVo>() {
						});
						//只有自如的客户 没有同步z到民宿 库 才会为null  此时对自如用户做一个简单的信息保存
						if(Check.NuNObj(customerVo)){
							customerVo = new CustomerVo();
							customerVo.setUid(uid);
							
							CustomerBaseMsgEntity customerBaseMsg= new CustomerBaseMsgEntity();
							customerBaseMsg.setUid(uid);
							
						
							if(!Check.NuNStr(phone)){
								customerBaseMsg.setIsContactAuth(CusotmerAuthEnum.IsAuth.getCode());
								customerBaseMsg.setCustomerMobile(phone);
							}
							if(!Check.NuNStr(email)){
								customerBaseMsg.setCustomerEmail(email);
							}
							if(!Check.NuNStr(username)){
								customerBaseMsg.setRealName(username);
							}
							customerBaseMsg.setCustomerSource(CustomerSourceEnum.Mapp_Login_First.getCode());
							dto = JsonEntityTransform.json2DataTransferObject(customerInfoService.insertCustomerInfo(JsonEntityTransform.Object2Json(customerBaseMsg)));
						}
						if(!Check.NuNStr(phone)){
							customerVo.setCustomerMobile(phone.substring(0, 3)+"********");
							customerVo.setShowMobile(phone);
						}
						customerVo.setToken(token);
						if(Check.NuNStr(customerVo.getUserPicUrl())){
							customerVo.setUserPicUrl(USER_DEFAULT_PIC_URL);
						}
						HttpSession session = request.getSession();
						session.setAttribute(MappMessageConst.SESSION_USER_KEY, customerVo);
						dto.putValue("uid", customerVo.getUid());
					}
				}else if(LoginCodeEnum.VERIFICATION_CODE_ERROR.getCode().equals(loginDataDto.getCode())){
					//登录失败  大于5次登录
					dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
					dto.setMsg("错误次数等于5次");
					dto.putValue("etTimes", LoginCodeEnum.VERIFICATION_CODE_ERROR.getCode());
				}else {
					dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
					dto.setMsg(loginDataDto.getMessage());
				}
			}
		} catch (Exception e) {
			LogUtil.info(logger, "登录失败e={}",e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("登录异常");
		}

		return dto;
	}
	/**
	 * 
	 * 到用户注册页面
	 *
	 * @author yd
	 * @created 2016年5月9日 下午4:36:18
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/toRegister")
	public String toRegister(HttpServletRequest request){
		setImgCode(request);
		return "customer/userRegister";
	}

	/**
	 * 
	 * 未登陆状态下 获取验证码V2（调用 车顺接口）
	 *
	 * @author yd
	 * @created 2016年5月9日 下午6:27:10
	 *
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/getMobileCode")
	public @ResponseBody DataTransferObject getMobileCode(HttpServletRequest request){

		String phone = request.getParameter("phone");
		String imgId = request.getParameter("imgId");
		//String imgVValue = request.getParameter("imgVValue");
		String type = request.getParameter("type") == null?"1": request.getParameter("type");//默认 注册
		String imgVid = request.getParameter("imgVid");
		DataTransferObject dto = new DataTransferObject();

		if(Check.NuNStr(phone)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("手机号不存在");
			return dto;
		}
		if(Check.NuNStr(imgId)||Check.NuNStr(imgVid)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("图行码错误");
			return dto;
		}

		Map<String, String> headerMap = getHeaderMap(request);
		if(Check.NuNObj(headerMap)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MappMessageConst.USER_HEADER__ERROR));
			return dto;
		}
		try {
			//获取验证码
			String  registerResult = CloseableHttpsUtil.sendGet(this.USER_VERIFY_CODE+"?imgId="+imgId+"&phone="+phone+"&type="+type+"&imgVid="+imgVid, headerMap);
			LogUtil.info(logger,"获取验证码返回值:{}",registerResult);
			Map<String, String> mapResult = (Map<String, String>) JsonEntityTransform.json2Map(registerResult);

			if(!Check.NuNMap(mapResult)){
				LoginDataDto loginDataDto = new LoginDataDto();
				loginDataDto.setCode(mapResult.get("code"));
				loginDataDto.setMessage(mapResult.get("message"));
				loginDataDto.setSys(mapResult.get("sys"));
				loginDataDto.setResp((Map<String, String>) JsonEntityTransform.json2Map(JsonEntityTransform.Object2Json(mapResult.get("resp"))));
				
				//测试用
				if(LoginCodeEnum.SUCCESS.getCode().equals(loginDataDto.getCode())){
					if(!Check.NuNObj(loginDataDto.getResp())&&!Check.NuNStr(loginDataDto.getResp().get("vcode"))){
						LogUtil.info(logger, "测试验证码vcode={}", loginDataDto.getResp().get("vcode"));
						dto.putValue("vcode", loginDataDto.getResp().get("vcode"));
					}
				}else{
					dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
					dto.setMsg(loginDataDto.getMessage());
				}
				//上线用
				/*if(!LoginCodeEnum.SUCCESS.getCode().equals(loginDataDto.getCode())){
					dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
					dto.setMsg(loginDataDto.getMessage());
				}*/
			}
		} catch (Exception e) {
			LogUtil.error(logger,"获取验证码异常:{}",e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("获取验证码异常");
		}
		return dto;
	}

	
	/**
	 * 
	 * 登陆状态下 获取验证码
	 *
	 * @author yd
	 * @created 2016年5月30日 下午6:48:05
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/${LOGIN_UNAUTH}/getMobileCodeByToken")
	public @ResponseBody DataTransferObject getMobileCodeByToken(HttpServletRequest request){

		String phone = request.getParameter("phone");
		DataTransferObject dto = new DataTransferObject();
		
		CustomerVo customerVo = CustomerVoUtils.getCusotmerVoFromSesstion(request);

		if(Check.NuNStr(phone)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("手机号不存在");
			return dto;
		}
        //获取验证码
        String vcode = randomUtil.getNumrOrChar(6, "num");
		try {
			if(!Check.NuNStr(vcode)){
				String key = RedisKeyConst.getMobileCodeKey(customerVo.getUid(),phone);
				SmsRequest smsRequest = new SmsRequest();
				smsRequest.setMobile(phone);
				smsRequest.setSmsCode(String.valueOf(MessageTemplateCodeEnum.MOBILE_CODE.getCode()));
				Map<String, String> paMap = new HashMap<String, String>();
				paMap.put("{1}", vcode);
				paMap.put("{2}", String.valueOf(RedisKeyConst.MOBILE_CODE_CACHE_TIME/60));
				smsRequest.setParamsMap(paMap);
				dto = JsonEntityTransform.json2DataTransferObject( this.smsTemplateService.sendSmsByCode(JsonEntityTransform.Object2Json(smsRequest)));
				
				if(dto.getCode()== DataTransferObject.SUCCESS){
					
					try {
						LogUtil.info(logger, "当前手机验证码为:vcode={}", vcode);
						this.redisOperations.setex(key, RedisKeyConst.MOBILE_CODE_CACHE_TIME, vcode);
					} catch (Exception e) {
						LogUtil.error(logger, "redis错误e={}",e);
					}
					dto.putValue("vcode", vcode);
				}
			}
		} catch (Exception e) {
            LogUtil.error(logger," phone:{}，vcode:{},e:{}",phone,vcode,e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("获取验证码异常");
		}
		return dto;
	}

	/**
	 * 
	 * 登陆手机验证码验证
	 *
	 * @author yd
	 * @created 2016年5月30日 下午6:56:44
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/${LOGIN_UNAUTH}/verifiVcodeByToken")
	public @ResponseBody DataTransferObject verifiVcodeByToken(HttpServletRequest request){
		
		String phone = request.getParameter("phone");
		String vcode = request.getParameter("vcode");
		DataTransferObject dto = new DataTransferObject();
		
		CustomerVo customerVo = CustomerVoUtils.getCusotmerVoFromSesstion(request);

		if(Check.NuNStr(phone)||Check.NuNStr(vcode)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("手机号或验证码不存在");
			return dto;
		}
		
		String currVcode = null;
		try {
			 currVcode = this.redisOperations.get(RedisKeyConst.getMobileCodeKey(customerVo.getUid(),phone));
		} catch (Exception e) {
			LogUtil.error(logger, "redis错误e={}",e);
		}
		if(Check.NuNStr(currVcode)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("验证码已失效");
			return dto;
		}
		
		if(!currVcode.equals(vcode)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("验证码错误");
			return dto;
		}
		return dto;
	}
	
	/**
	 * 
	 * 校验图形验证码
	 *
	 * @author yd
	 * @created 2016年5月13日 下午5:17:03
	 *
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/checkImgCode")
	public @ResponseBody DataTransferObject getImgCode(HttpServletRequest request){

		String imgId = request.getParameter("imgId");
		String imgVValue = request.getParameter("imgVValue");
		DataTransferObject dto = new DataTransferObject();
		Map<String, String> headerMap = getHeaderMap(request);
		if(Check.NuNObj(headerMap)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MappMessageConst.USER_HEADER__ERROR));
			return dto;
		}

		if(Check.NuNStr(imgId)||Check.NuNStr(imgVValue)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("验证码错误");
			return dto;
		}
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("imgId", imgId);
		paramsMap.put("imgVValue", imgVValue);

		try {
			//校验验证码
			String  registerResult = CloseableHttpsUtil.sendFormPost(this.IMG_VERIFY_CODE_POST, paramsMap, headerMap);
			Map<String, String> mapResult = (Map<String, String>) JsonEntityTransform.json2Map(registerResult);

			if(!Check.NuNMap(mapResult)){
				LoginDataDto loginDataDto = new LoginDataDto();
				loginDataDto.setCode(mapResult.get("code"));
				loginDataDto.setMessage(mapResult.get("message"));
				loginDataDto.setSys(mapResult.get("sys"));
				loginDataDto.setResp((Map<String, String>) JsonEntityTransform.json2Map(JsonEntityTransform.Object2Json(mapResult.get("resp"))));
				//成功返回  
				if(LoginCodeEnum.SUCCESS.getCode().equals(loginDataDto.getCode())){

					if(!Check.NuNMap(loginDataDto.getResp())){
						String imgVid = loginDataDto.getResp().get("imgVid");
						dto.putValue("imgVid", imgVid);
					}

				}else{
					dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
					dto.setMsg(loginDataDto.getMessage());
				}
			}

		} catch (Exception e) {
            LogUtil.error(logger,"获取验证码异常:{}",e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("验证码校验异常");
		}

		return dto;
	}
	
	/**
	 * 
	 * 校验图形验证码
	 *
	 * @author yd
	 * @created 2016年6月30日 下午5:01:06
	 *
	 * @param request
	 * @return
	 */
	private DataTransferObject checkImgCode(HttpServletRequest request){
		
		
		String imgId = request.getParameter("imgId");
		String imgVValue = request.getParameter("imgVValue");
		DataTransferObject dto = new DataTransferObject();
		Map<String, String> headerMap = getHeaderMap(request);
		if(Check.NuNObj(headerMap)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MappMessageConst.USER_HEADER__ERROR));
			return dto;
		}

		if(Check.NuNStr(imgId)||Check.NuNStr(imgVValue)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("验证码错误");
			return dto;
		}
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("imgId", imgId);
		paramsMap.put("imgVValue", imgVValue);

		try {
			//校验验证码
			String  registerResult = CloseableHttpsUtil.sendFormPost(this.IMG_VERIFY_CODE_POST, paramsMap, headerMap);
			Map<String, String> mapResult = (Map<String, String>) JsonEntityTransform.json2Map(registerResult);

			if(!Check.NuNMap(mapResult)){
				LoginDataDto loginDataDto = new LoginDataDto();
				loginDataDto.setCode(mapResult.get("code"));
				loginDataDto.setMessage(mapResult.get("message"));
				loginDataDto.setSys(mapResult.get("sys"));
				loginDataDto.setResp((Map<String, String>) JsonEntityTransform.json2Map(JsonEntityTransform.Object2Json(mapResult.get("resp"))));
				//成功返回  
				if(LoginCodeEnum.SUCCESS.getCode().equals(loginDataDto.getCode())){

					if(!Check.NuNMap(loginDataDto.getResp())){
						String imgVid = loginDataDto.getResp().get("imgVid");
						dto.putValue("imgVid", imgVid);
					}

				}else{
					dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
					dto.setMsg(loginDataDto.getMessage());
				}
			}

		} catch (Exception e) {
            LogUtil.error(logger,"获取验证码异常:{}",e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("验证码校验异常");
		}

		return dto;
	}

	/**
	 * 
	 * 校验验证码
	 * 说明：
	 * 1.校验验证码  
	 * 2.校验成功，输入密码
	 *
	 * @author yd
	 * @created 2016年5月10日 下午3:33:10
	 *
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/verifiVcode")
	public @ResponseBody DataTransferObject verifiMobileCode(HttpServletRequest request){
		String vcode = request.getParameter("vcode");
		String phone = request.getParameter("phone");
		String type = request.getParameter("type") == null?"1":request.getParameter("type") ;

		DataTransferObject dto = new DataTransferObject();

		if(Check.NuNStr(vcode)||Check.NuNStr(phone)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("验证码或者手机号不存在");
			return dto;
		}
		Map<String, String> headerMap = getHeaderMap(request);
		if(Check.NuNObj(headerMap)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MappMessageConst.USER_HEADER__ERROR));
			return dto;
		}

		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("phone", phone);
		paramsMap.put("type", type);
		paramsMap.put("vcode", vcode);

		try {
			String  registerResult = CloseableHttpsUtil.sendFormPost(VERIFY_CODE_V1, paramsMap, headerMap);
			Map<String, String> mapResult = (Map<String, String>) JsonEntityTransform.json2Map(registerResult);
			if(!Check.NuNMap(mapResult)){
				LoginDataDto loginDataDto = new LoginDataDto();
				loginDataDto.setCode(mapResult.get("code"));
				loginDataDto.setMessage(mapResult.get("message"));
				loginDataDto.setSys(mapResult.get("sys"));
				loginDataDto.setResp((Map<String, String>) JsonEntityTransform.json2Map(JsonEntityTransform.Object2Json(mapResult.get("resp"))));
				//成功返回
				if(LoginCodeEnum.SUCCESS.getCode().equals(loginDataDto.getCode())){
					dto.putValue("message", "success");
					dto.putValue("vid",loginDataDto.getResp().get("vid"));
				}else{
					dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
					dto.setMsg(MessageSourceUtil.getChinese(messageSource, MappMessageConst.USER_VERIFY_CODE_ERROR));
				}
			}
		} catch (Exception e) {
			LogUtil.error(logger, "校验验证码异常", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("校验验证码异");
		}
		return dto;
	}


	/**
	 * 
	 * 用户注册（主要是房东，基础数据全部在mapp这边，无需再去同步用户的基本信息，但银行卡是在用户空间，得实施去查）
	 * 说明：
	 * 1.密码在前台已经做md5加密，对md5校验是否为null
	 * 2.校验失败，返回失败结果
	 * 3.校验成功，去自如用户中心注册
	 * 4.注册失败，返回失败结果
	 * 5.注册成功，拿回token和uid，数据保存session和数据库，并且保存默认图片，到mapp首页
	 * 
	 * 由于房东的信息在民宿库，故不用校验token，只要session中有用户数据即可（session失效后，去登录）
	 * 
	 *
	 * @author yd
	 * @created 2016年5月10日 下午3:20:46
	 *
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/userRegister")
	public  @ResponseBody DataTransferObject userRegister(HttpServletRequest request){

		String phone = request.getParameter("phone");
		String password = request.getParameter("password");// md5(‘c’ + 密码 + 'b') 前台已处理
		String vcode = request.getParameter("vcode");
		String vid = request.getParameter("vid");

		DataTransferObject dto = new DataTransferObject();
		if(Check.NuNStr(phone)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MappMessageConst.USER_MOBILE_REGISTER_ERROR));
			return dto;
		}
		if(Check.NuNStr(password)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MappMessageConst.USER_PASSWORD_REGISTER_ERROR));
			return dto;
		}
		if(Check.NuNStr(vcode)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("验证码不存在");
			return dto;
		}
		Map<String, String> headerMap = getHeaderMap(request);
		if(Check.NuNObj(headerMap)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MappMessageConst.USER_HEADER__ERROR));
			return dto;
		}
		try {
			//先校验验证码
			Map<String, String> paramsMap = new HashMap<String, String>();
			
			paramsMap.put("phone", phone);
			paramsMap.put("password", password);
			paramsMap.put("vid", vid);
			paramsMap.put("isLogin", "true");
			CustomerVo customerVo = new CustomerVo();
			customerVo.setCustomerMobile(phone);

			String registerResult = CloseableHttpsUtil.sendFormPost(LOGIN_REGISTER_URL, paramsMap, headerMap);
			LogUtil.info(logger, "注册参数paramsMap={},注册请求返回参数registerResult={}", paramsMap.toString(),registerResult);
			Map<String, String> mapResult  = (Map<String, String>) JsonEntityTransform.json2Map(registerResult);

			if(!Check.NuNMap(mapResult)&&LoginCodeEnum.SUCCESS.getCode().equals(mapResult.get("code"))){
				LoginDataDto loginDataDto = new LoginDataDto();
				loginDataDto.setCode(mapResult.get("code"));
				loginDataDto.setMessage(mapResult.get("message"));
				loginDataDto.setSys(mapResult.get("sys"));
				loginDataDto.setResp((Map<String, String>) JsonEntityTransform.json2Map(JsonEntityTransform.Object2Json(mapResult.get("resp"))));
				//获取token
				String token = (String) loginDataDto.getResp().get("token");
				String uid = (String) loginDataDto.getResp().get("uid");

				CustomerBaseMsgEntity customerBaseMsg= new CustomerBaseMsgEntity();
				customerBaseMsg.setUid(uid);
				customerBaseMsg.setCustomerMobile(customerVo.getCustomerMobile());
				customerBaseMsg.setIsContactAuth(CusotmerAuthEnum.IsAuth.getCode());//手机号注册 默认认证通过

				dto = JsonEntityTransform.json2DataTransferObject(customerInfoService.insertCustomerInfo(JsonEntityTransform.Object2Json(customerBaseMsg)));
				if(dto.getCode() == 0){
					if(!Check.NuNObj(customerVo)){
						customerVo.setUid(uid);
						customerVo.setToken(token);
					}

					//保存用户默认图片
					CustomerPicMsgEntity customerPicMsgEntity = new CustomerPicMsgEntity();
					customerPicMsgEntity.setCreateDate(new Date());
					customerPicMsgEntity.setFid(UUIDGenerator.hexUUID());
					customerPicMsgEntity.setIsDel(IsDelEnum.NOT_DEL.getCode());
					customerPicMsgEntity.setLastModifyDate(new Date());
					customerPicMsgEntity.setPicBaseUrl(this.USER_DEFAULT_PIC_URL);
					customerPicMsgEntity.setPicType(PicTypeEnum.USER_PHOTO.getCode());
					customerPicMsgEntity.setUid(uid);
					customerBaseMsg.setCustomerSource(CustomerSourceEnum.Mapp_Rigister.getCode());

					dto = JsonEntityTransform.json2DataTransferObject(customerMsgManagerService.insertCustomerPicMsg(JsonEntityTransform.Object2Json(customerPicMsgEntity)));

					if(dto.getCode() == 0){
						if(Check.NuNObj(customerVo.getUserPicUrl())){
							customerVo.setUserPicUrl(this.USER_DEFAULT_PIC_URL);
						}
						
					}
					HttpSession session = request.getSession();
					session.setAttribute(MappMessageConst.SESSION_USER_KEY, customerVo);


				}

			}else{
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(mapResult.get("message"));
			}
		} catch (Exception e) {
            LogUtil.error(logger,"注册用户异常:{}",e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("注册用户异常");
		}

		return dto;
	}

	/**
	 * 
	 * 用户推出
	 * 1.清楚session
	 * 2.清楚token状态  不用管 那边成不成功
	 *
	 * @author yd
	 * @created 2016年5月15日 下午4:55:17
	 *
	 * @param request
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/${LOGIN_UNAUTH}/loginOut")
	public  String  loginOut(HttpServletRequest request){

		CustomerVo customerVo = CustomerVoUtils.getCusotmerVoFromSesstion(request);
		String token = customerVo.getToken();
		HttpSession session = request.getSession();
		session.removeAttribute(MappMessageConst.SESSION_USER_KEY);
		Map<String, String> headerMap = getHeaderMap(request);
		if(!Check.NuNObj(headerMap)&&!Check.NuNStr(token)){
			headerMap.put("token", token);
			try {
				String loginOut = CloseableHttpsUtil.sendFormPost(this.USER_LOGINOUT_POST, null, headerMap);
				LogUtil.info(logger, "注册参数paramsMap={},注册请求返回参数loginOut={}",loginOut);
				Map<String, String> mapResult  = (Map<String, String>) JsonEntityTransform.json2Map(loginOut);

				if(!Check.NuNMap(mapResult)&&LoginCodeEnum.SUCCESS.getCode().equals(mapResult.get("code"))){
					LogUtil.info(logger, "清楚token={}成功", token);
				}else{
					LogUtil.info(logger, "清楚token={}，失败", token);
				}
			} catch (Exception e) {
				LogUtil.error(logger, "清楚token={}，异常", token);
			}

		}else{
			LogUtil.info(logger, "清楚token={}，失败", token);
		}

		return "redirect:/customer/login";
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
			LogUtil.debug(logger, "登录头信息验证失败，当前头信息loginHeaderDto={}", loginHeaderDto.toString());
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
	 * 忘记密码
	 *
	 * @author yd
	 * @created 2016年5月15日 下午7:02:14
	 *
	 * @param request
	 */
	@RequestMapping("/forgetPw")
	public String forgerPw(HttpServletRequest request){
		setImgCode(request);
		return "customer/forgetPw";
	}

	/**
	 * 
	 * 重置密码 
	 * 1.失败 给出结果
	 * 2.成功 去登录页面
	 *
	 * @author yd
	 * @created 2016年5月15日 下午10:40:47
	 *
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/resetPassword")
	@ResponseBody
	public DataTransferObject resetPassword(HttpServletRequest request){


		DataTransferObject dto = new DataTransferObject();
		String phone = request.getParameter("phone");
		String password = request.getParameter("password");// md5(‘c’ + 密码 + 'b') 前台已处理
		String vcode = request.getParameter("vcode");
		String email = request.getParameter("email");


		Map<String, String> headerMap = getHeaderMap(request);
		if(Check.NuNObj(headerMap)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MappMessageConst.USER_HEADER__ERROR));
			return dto;
		}

		if(Check.NuNStr(password)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MappMessageConst.USER_PASSWORD_REGISTER_ERROR));
			return dto;
		}
		if(Check.NuNStr(vcode)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("验证码不存在");
			return dto;
		}
		Map<String, String> paramsMap = new HashMap<String, String>();
		if(!Check.NuNStr(phone)){
			paramsMap.put("phone", phone);
		}else if(!Check.NuNStr(email)){
			paramsMap.put("email", email);
		}
		paramsMap.put("password", password);
		paramsMap.put("vcode", vcode);

		try {
			CustomerVo customerVo = new CustomerVo();
			customerVo.setCustomerMobile(phone);
			String registerResult = CloseableHttpsUtil.sendFormPost(USER_RESET_PASSWORD_POST, paramsMap, headerMap);
			LogUtil.info(logger, "注册参数paramsMap={},注册请求返回参数registerResult={}", paramsMap.toString(),registerResult);
			Map<String, String> mapResult  = (Map<String, String>) JsonEntityTransform.json2Map(registerResult);

			if(Check.NuNMap(mapResult)||!LoginCodeEnum.SUCCESS.getCode().equals(mapResult.get("code"))){
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(mapResult.get("message"));
			}

		} catch (Exception e) {
            LogUtil.error(logger,"修改密码异常:{}",e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("修改密码异常");
		}
		return dto;
	}

	/**
	 * 
	 * 设置图形码
	 *
	 * @author yd
	 * @created 2016年5月15日 下午10:25:17
	 *
	 * @param request
	 */
	private void  setImgCode(HttpServletRequest request){
		String imgId = UUIDGenerator.hexUUID().substring(0, 32);
		String imgUrl = this.IMG_VERIFY_CODE_POST+"?imgId=";
		request.setAttribute("imgUrl", imgUrl);
		request.setAttribute("imgId", imgId);
	}

	/**
	 * 
	 * （个人中心）修改用户密码
	 *
	 * @author yd
	 * @created 2016年5月16日 上午1:09:39
	 *
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/${LOGIN_UNAUTH}/updataUserPw")
	public @ResponseBody DataTransferObject updataUserPw(HttpServletRequest request){

		DataTransferObject dto = new DataTransferObject();

		String oldPassword = request.getParameter("oldPassword");
		String newPassword = request.getParameter("newPassword");

		if(Check.NuNStr(oldPassword)||Check.NuNStr(newPassword)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("密码错误");

			return dto;
		}

		CustomerVo customerVo = CustomerVoUtils.getCusotmerVoFromSesstion(request);

		if(Check.NuNObj(customerVo)){
			LogUtil.info(logger, "当前用户已失效");
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("当前用户已失效");
			return dto;
		}
		Map<String, String> headerMap = getHeaderMap(request);
		if(Check.NuNObj(headerMap)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MappMessageConst.USER_HEADER__ERROR));
			return dto;
		}
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("oldPassword", oldPassword);
		paramsMap.put("newPassword", newPassword);
		paramsMap.put("token",customerVo.getToken());

		String registerResult = CloseableHttpsUtil.sendFormPut(this.USER_PASSWORD_UPDATE_PUT, paramsMap, headerMap);
		LogUtil.info(logger, "注册参数paramsMap={},注册请求返回参数registerResult={}", paramsMap.toString(),registerResult);
		Map<String, String> mapResult  = (Map<String, String>) JsonEntityTransform.json2Map(registerResult);

		if(!Check.NuNMap(mapResult)&&LoginCodeEnum.SUCCESS.getCode().equals(mapResult.get("code"))){
			LoginDataDto loginDataDto = new LoginDataDto();
			loginDataDto.setCode(mapResult.get("code"));
			loginDataDto.setMessage(mapResult.get("message"));
			loginDataDto.setSys(mapResult.get("sys"));
			loginDataDto.setResp((Map<String, String>) JsonEntityTransform.json2Map(JsonEntityTransform.Object2Json(mapResult.get("resp"))));
			//获取token
			String token = (String) loginDataDto.getResp().get("token");
			String uid = (String) loginDataDto.getResp().get("uid");

			HttpSession session = request.getSession();
			customerVo.setToken(token);
			customerVo.setUid(uid);
			session.removeAttribute(MappMessageConst.SESSION_USER_KEY);
			session.setAttribute(MappMessageConst.SESSION_USER_KEY, customerVo);

		}else{
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(mapResult.get("message"));
		}

		return dto;
	}

	/**
	 * 
	 * 到手机给更新页面
	 *
	 * @author yd
	 * @created 2016年5月16日 上午2:47:28
	 *
	 * @param request
	 * @return
	 * @throws SOAParseException 
	 */
	@RequestMapping("/${LOGIN_UNAUTH}/updateMobile")
	public String toUpdateMobile(HttpServletRequest request) throws SOAParseException{
		request.setAttribute("flag", request.getParameter("flag"));
		CustomerVo customerVo = CustomerVoUtils.getCusotmerVoFromSesstion(request);
		String customerJson=customerInfoService.getCustomerAndRoleInfoByUid(customerVo.getUid());
		CustomerBaseMsgEntity customerBaseMsgEntity=SOAResParseUtil.getValueFromDataByKey(customerJson, "customerBase", CustomerBaseMsgEntity.class);
		String resultJson=confCityService.findNationCodeList();
		List<NationCodeEntity> nationCodeList=SOAResParseUtil.getListValueFromDataByKey(resultJson, "list", NationCodeEntity.class);
		request.setAttribute("nationCodeList", nationCodeList);
		request.setAttribute("customerMobile", customerBaseMsgEntity.getCustomerMobile());
		request.setAttribute("countryCode",customerBaseMsgEntity.getCountryCode());
		this.setImgCode(request);
		return "personal/updateMobile";
	}

	/**
	 * 
	 * 更新手机号
	 *
	 * @author yd
	 * @created 2016年5月16日 上午2:58:55
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/${LOGIN_UNAUTH}/updatePho")
	public @ResponseBody DataTransferObject updatePho(HttpServletRequest request){

		DataTransferObject dto = new DataTransferObject();
		CustomerVo customerVo = CustomerVoUtils.getCusotmerVoFromSesstion(request);
		String flag = request.getParameter("flag") == null?"1":request.getParameter("flag");//跳转标识
		
		if(Check.NuNObj(customerVo)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("账户异常");
			return dto;
		}

		String mobile = request.getParameter("phone");

		if(Check.NuNStr(mobile)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("手机号错误");
			return dto;
		}
		CustomerBaseMsgEntity customerBaseMsgEntity = new CustomerBaseMsgEntity();
		customerBaseMsgEntity.setUid(customerVo.getUid());
		customerBaseMsgEntity.setCountryCode(request.getParameter("countryCode"));
		customerBaseMsgEntity.setCustomerMobile(mobile);
		customerBaseMsgEntity.setIsContactAuth(CusotmerAuthEnum.IsAuth.getCode());
        //change land phone and change 400
		dto = JsonEntityTransform.json2DataTransferObject(customerInfoService.updateLandPhoneNew(JsonEntityTransform.Object2Json(customerBaseMsgEntity)));
		if(dto.getCode() == 0){
			dto = JsonEntityTransform.json2DataTransferObject(this.customerMsgManagerService.getCutomerVoFromDb(customerVo.getUid()));
			if(dto.getCode() ==  DataTransferObject.SUCCESS){
				customerVo = dto.parseData("customerVo", new TypeReference<CustomerVo>() {
				});
				//更新session
				HttpSession session = request.getSession();
				session.removeAttribute(MappMessageConst.SESSION_RETURN_URL_KEY);
				session.setAttribute(MappMessageConst.SESSION_RETURN_URL_KEY, customerVo);
				dto.putValue("flag", flag);
			}
		}
		return dto;
	}
}
