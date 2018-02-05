
package com.ziroom.minsu.api.common.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.DateUtil;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.cache.redisOne.RedisOperations;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.api.common.constant.HeaderParamName;
import com.ziroom.minsu.api.common.constant.LoginAuthConst;
import com.ziroom.minsu.api.common.dto.GatewayResponseDto;
import com.ziroom.minsu.api.common.dto.LoginHeaderDto;
import com.ziroom.minsu.api.common.dto.ResponseDto;
import com.ziroom.minsu.api.common.encrypt.EncryptFactory;
import com.ziroom.minsu.api.common.encrypt.IEncrypt;
import com.ziroom.minsu.entity.customer.CustomerBaseMsgEntity;
import com.ziroom.minsu.entity.customer.CustomerPicMsgEntity;
import com.ziroom.minsu.services.common.utils.CloseableHttpUtil;
import com.ziroom.minsu.services.common.utils.RedisKeyConst;
import com.ziroom.minsu.services.customer.api.inner.CustomerBlackService;
import com.ziroom.minsu.services.customer.api.inner.CustomerInfoService;
import com.ziroom.minsu.services.customer.api.inner.CustomerMsgManagerService;
import com.ziroom.minsu.services.customer.dto.CustomerInfoDto;
import com.ziroom.minsu.services.customer.entity.CustomerVo;
import com.ziroom.minsu.valenum.customer.AuditStatusEnum;
import com.ziroom.minsu.valenum.customer.PicTypeEnum;
import com.ziroom.minsu.valenum.msg.IsDelEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * <p>登录拦截器（包含lonAuth 或 lonUnauth）
 *   说明：拦截  1.登录并且解密 2 登录并且加密 
 *   
 *   v1.0
 *   业务说明：
 *   1.获取头部参数信息，并校验，不通过，直接返回false
 *   2.校验通过，获取token和uid
 *   3.token校验，不通过，返回false
 *   4.通过，用uid，取redis拿用户数据
 *   5.拿到数据，放过请求
 *   6.未拿到用户数据，用uid取用户系统获取用户信息，保存数据库（更新或添加），并放入redis（保存条件：电话号或者真实姓名为null，放redis2分钟  ，其他保存12小时），返回true
 *   
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
public class LoginInterceptor extends HandlerInterceptorAdapter{


	/**
	 * 日志对象
	 */
	private static Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);

	@Autowired
	private RedisOperations redisOperations;

	@Resource(name = "customer.customerMsgManagerService")
	private CustomerMsgManagerService customerMsgManagerService;

	@Resource(name = "customer.customerInfoService")
	private CustomerInfoService customerInfoService;

	@Resource(name = "customer.customerBlackService")
	private CustomerBlackService customerBlackService;

	/**
	 * 解密类型工具:默认DES
	 */
	private String encryptType="DES";


	@Resource(name="api.messageSource")
	private MessageSource messageSource;


	@Value("#{'${CUSTOMER_DETAIL_URL}'.trim()}")
	private  String CUSTOMER_DETAIL_URL;

	@Value("#{'${minsu.web.sys}'.trim()}")
	private String minsuWebSys;

	@Value("#{'${minsu.web.accept}'.trim()}")
	private String minsuWebAccept;

	@Value("#{'${LOGIN_LOGIN_V2}'.trim()}")
	private String LOGIN_LOGIN_V2;

	@Value("#{'${LOGIN_AUTH_URL}'.trim()}")
	private String LOGIN_AUTH_URL;

	@Value("#{'${CUSTOMER_BLACK_URL}'.trim()}")
	private String CUSTOMER_BLACK_URL;

	@Value("#{'${X-Forwarded-Ziroom}'.trim()}")
	private String X_Forwarded_Ziroom;


	/**
	 * 请求到来前对结果进行解密
	 * 
	 * 没有token和uid的暂时用这个：token=ff90d0e51e7e40726c98663b4009d69e,  uid=f8a4375c-7437-b078-6f4b-5e2b7d6dfcb6
	 * 
	 * 密码123456 加密后a53c9868827ee7df457110549547fbb0 
	 * 
	 * 说明：拦截器暂不做token校验（因为我们这边让多台设备同时登录），以后只让一台设备登录在打开
	 *
	 * @param request
	 * @param response
	 * @param handler
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		ResponseDto	responseDto = new ResponseDto();
		
		try {
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

			loginHeaderDto.setAccept(minsuWebAccept);
			loginHeaderDto.setSys(minsuWebSys);
			Map<String, String> headerMap = new HashMap<String, String>();

			headerMap.put(HeaderParamName.ACCEPT,loginHeaderDto.getAccept());
			headerMap.put(HeaderParamName.CLIENT_TYPE,loginHeaderDto.getClientType()+"");
			headerMap.put(HeaderParamName.CLIENT_VERSION, loginHeaderDto.getClientVersion());
			headerMap.put(HeaderParamName.REQUEST_ID, loginHeaderDto.getRequestId());
			headerMap.put(HeaderParamName.SYS, loginHeaderDto.getSys());
			headerMap.put(HeaderParamName.USER_AGENT, loginHeaderDto.getUserAgent());
			String token = map.get(LoginAuthConst.TOKEN);
			String uid = map.get(LoginAuthConst.UID);
			String version = map.get(LoginAuthConst.VERSION);

			if(!checkLoginHeader(loginHeaderDto)){
				LogUtil.info(logger, "登录头信息验证失败，当前头信息loginHeaderDto={}", JsonEntityTransform.Object2Json(loginHeaderDto));
				getResponseMsg(response, request, "头信息错误,请重新登录", responseDto, -1);
				return false;
			}

			if(Check.NuNStr(uid)||Check.NuNStr(token)){
				LogUtil.info(logger, "token 或者 uid 不存在");
				getResponseMsg(response, request, "您的账号登录状态已失效，请重新登录", responseDto, -1);
				return false;
			}

			//通过uid 去redis拿用户数据   这里不再做token校验  
			String cutoemrkey  = RedisKeyConst.getCutomerKey(uid);
			String customerVoStr = null;
			try {
				 customerVoStr =this.redisOperations.get(cutoemrkey);
			} catch (Exception e) {
				LogUtil.error(logger, "redis错误");
			}
			
			LogUtil.debug(logger, "根据cutoemrkey={}，去redis获取数据customerVoStr={}",cutoemrkey, customerVoStr);
			CustomerVo customerVo= null;
			if(!Check.NuNStr(customerVoStr)){
				customerVo=JsonEntityTransform.json2Object(customerVoStr, CustomerVo.class);
			}
			//获取用户登录信息,并保存
			if(Check.NuNObj(customerVo)){
				saveCustomerInfo(uid,token);
			}
			request.setAttribute("uid", uid);
			if (!Check.NuNObj(customerVo)){
				request.setAttribute(HeadersInterceptor.USERID, customerVo.getUid());
			}else {
				request.setAttribute(HeadersInterceptor.USERID, uid);
			}

			request.setAttribute("token", token);
			request.setAttribute(LoginAuthConst.VERSION, version);
			return super.preHandle(request, response, handler);

		} catch (Exception e) {
			LogUtil.error(logger, "登录失败e={}",e);
			getResponseMsg(response, request, "token验证异常,请重新登录", responseDto, -1);
		}
		return false;
	}


	/**
	 * 用户是否在黑名单列表中
	 * @author jixd
	 * @param uid
	 * @return
	 */
	private boolean isBlack(String uid){
		boolean is = false;
		try{
			Map<String,String> paramMap = new HashMap<>();
			paramMap.put("uid",uid);
			String resultJson = CloseableHttpUtil.sendFormPost(CUSTOMER_BLACK_URL, paramMap);
			JSONObject jsonObject = JSONObject.parseObject(resultJson);
			Integer errorCode = jsonObject.getInteger("error_code");
			if (errorCode == 0){
				Integer flag = jsonObject.getInteger("data");
				if (flag == 1){
					is = true;
				}
			}
		}catch (Exception e){
			LogUtil.error(logger,"黑名单接口调用异常e={}",e);
		}
		return is;
	}


	/**
	 * 
	 * 重新登录公用返回
	 *
	 * @author yd
	 * @created 2016年5月20日 上午2:06:32
	 *
	 * @param response
	 * @param msg
	 * @param responseDto
	 * @throws BusinessException
	 * @throws IOException
	 */
	private void getResponseMsg(HttpServletResponse response, HttpServletRequest request, String msg, ResponseDto responseDto, int status) throws BusinessException, IOException {
		if(Check.NuNObj(responseDto)) responseDto = new ResponseDto();
		IEncrypt encrypt2 = EncryptFactory.createEncryption(encryptType);
		response.setContentType("application/json; charset=utf-8");
		responseDto.setStatus(String.valueOf(status));
		responseDto.setMessage(msg);
		String forwarded = request.getHeader(HeaderParamName.X_Forwarded_Ziroom);
		if (X_Forwarded_Ziroom.equals(forwarded)) {
			//来自网关的请求 不加密返回
			response.getOutputStream().write(JsonEntityTransform.Object2Json(GatewayResponseDto.response2GatewayDto(responseDto)).getBytes());
		} else {
			response.getOutputStream().write(encrypt2.encrypt(JsonEntityTransform.Object2Json(responseDto)).getBytes());
		}
		response.flushBuffer();
	}


	/**
	 * 
	 * 保存用户信息
	 * 1.用户基本信息  2.用户银行卡信息 3.用户身份证正反面照片（注意照片要和民宿的区分开，存全地址，例如：http://pic.ziroom.com.cn/static/images/default.png）
	 *
	 * @author yd
	 * @created 2016年5月6日 上午11:14:26
	 *
	 * @param uid
	 * @return 始终返回true 因为已经token验证通过就不阻碍用户登录了
	 */
	@SuppressWarnings("unchecked")
	private boolean saveCustomerInfo(String uid,String token){

		
		if(!Check.NuNStr(uid)){
			Map<String, String> paramMap = new HashMap<String, String>();
			StringBuffer url = new StringBuffer();
			url.append(CUSTOMER_DETAIL_URL).append(uid);
			try {
				LogUtil.info(logger, "根据用户uid={}获取用户信息，请求url={}",uid, url.toString());
				
				String getResult = CloseableHttpUtil.sendGet(url.toString(), null);
				
				if(Check.NuNStrStrict(getResult)){
					LogUtil.info(logger, "CUSTOMER_ERROR:根据用户uid={},获取用户信息失败，原因请求接口超时，具体看日志", uid);
					return true;
				}
				try {
					paramMap = (Map<String, String>) JsonEntityTransform.json2Map(getResult);
				} catch (Exception e) {
					LogUtil.info(logger, "用户信息转化错误，请求url={}，返回结果result={}，e={}",url.toString(),getResult,e);
					return true;
				}
				Object code = paramMap.get("error_code");
				if(Check.NuNObj(code)){
					LogUtil.info(logger, "【登录拦截】获取用户信息错误code={}，请求url={}，返回结果result={}",code,url.toString(),getResult);
					return true;
				}
				Integer errorCode =Integer.valueOf(code.toString());
				if ( errorCode !=DataTransferObject.SUCCESS){
					LogUtil.info(logger, "【登录拦截】获取用户信息错误code={}，请求url={}，返回结果result={}",code,url.toString(),getResult);
				}
				if(!Check.NuNMap(paramMap)&&DataTransferObject.SUCCESS == errorCode){
					//封装用户基本信息
					CustomerBaseMsgEntity customerBaseMsg= new CustomerBaseMsgEntity();
					if(!Check.NuNObj(paramMap.get("data"))){

						paramMap = (Map<String, String>) JsonEntityTransform.json2Map(JsonEntityTransform.Object2Json(paramMap.get("data")));
						if(!Check.NuNMap(paramMap)){
							CustomerInfoDto customerInfoDto = new CustomerInfoDto();
							LogUtil.debug(logger, "返回用户信息map={}", paramMap.toString());
							customerBaseMsg.setUid(paramMap.get("uid"));
							
							customerBaseMsg.setCustomerMobile(paramMap.get("mobile"));
							if(!Check.NuNObj(paramMap.get("gender"))){ 
								String gender = String.valueOf(paramMap.get("gender"));
								customerBaseMsg.setCustomerSex(Integer.valueOf(gender));
							}
							if(!Check.NuNStr(paramMap.get("birth"))){
								customerBaseMsg.setCustomerBirthday(DateUtil.parseDate(paramMap.get("birth"), "yyyy-MM-dd"));
							}
							customerBaseMsg.setCustomerEmail(paramMap.get("email"));
							Map<String, String> baseMap = new HashMap<String, String>();
							Object certStr = paramMap.get("cert");
							LogUtil.debug(logger, "身份证信息：{}", certStr);
							if (!Check.NuNObj(certStr) && !(certStr instanceof Collection)) {
								baseMap = (Map<String, String>) JsonEntityTransform.json2Map(JsonEntityTransform.Object2Json(paramMap.get("cert")));
								
							}
							if (!Check.NuNObj(certStr) && (certStr instanceof Collection)) {
								List<HashMap> list = JsonEntityTransform.json2ObjectList(JsonEntityTransform.Object2Json(paramMap.get("cert")), HashMap.class);
								if(!Check.NuNCollection(list)){
									baseMap = list.get(0);
								}
							}
							if(!Check.NuNMap(baseMap)){
								LogUtil.info(logger, baseMap.toString());
								if(!Check.NuNStr(baseMap.get("cert_type")))
									customerBaseMsg.setIdType(Integer.valueOf(baseMap.get("cert_type")));
								customerBaseMsg.setIdNo(baseMap.get("cert_num"));
								customerBaseMsg.setRealName(baseMap.get("real_name"));
							}
							//如果真是姓名存在 则保存 不存在 保存手机号
							if(Check.NuNStr(customerBaseMsg.getRealName())){
								if(!Check.NuNStr(paramMap.get("user_name"))){
									customerBaseMsg.setRealName(paramMap.get("user_name"));
								}
							}
							/*if(Check.NuNStr(customerBaseMsg.getRealName())){
								customerBaseMsg.setRealName(paramMap.get("mobile"));
							}*/
							String realName = customerBaseMsg.getRealName();
							if (!Check.NuNStr(realName) && realName.length() > 49) {
								realName = realName.substring(0, 49);
								customerBaseMsg.setRealName(realName);
							}
						
							customerBaseMsg.setIsLandlord(0);
							customerBaseMsg.setAuditStatus(AuditStatusEnum.SUBMITAUDIT.getCode());
							customerBaseMsg.setResideAddr(paramMap.get("address_street"));
							if(!Check.NuNObj(paramMap.get("degree")))
								customerBaseMsg.setCustomerEdu(Integer.valueOf(String.valueOf(paramMap.get("degree"))));
							customerBaseMsg.setCustomerJob(paramMap.get("job"));
							customerBaseMsg.setIsContactAuth(0);
							customerBaseMsg.setIsIdentityAuth(0);
							customerBaseMsg.setIsUploadIcon(0);
							customerBaseMsg.setCreateDate(new Date());
							customerBaseMsg.setLastModifyDate(new Date());
							customerBaseMsg.setIsDel(IsDelEnum.NOT_DEL.getCode());
							customerBaseMsg.setNickName(paramMap.get("nick_name"));
							customerInfoDto.setCustomerBaseMsg(customerBaseMsg);
							List<CustomerPicMsgEntity> listCustomerPicMsg = new ArrayList<CustomerPicMsgEntity>();
							//保存用户照片 可能还有其他照片信息  身份证
							/*if(!Check.NuNStr(paramMap.get("head_img"))){
								CustomerPicMsgEntity customerPicMsg = initCustomerPicMsg(customerBaseMsg.getUid());
								customerPicMsg.setPicBaseUrl(paramMap.get("head_img"));
								listCustomerPicMsg.add(customerPicMsg);
							}
*/
							//可能还有其他照片信息  身份证
							customerInfoDto.setListCustomerPicMsg(listCustomerPicMsg);
							JsonEntityTransform.json2DataTransferObject(this.customerMsgManagerService.saveCustomerInfo(JsonEntityTransform.Object2Json(customerInfoDto)));

						}
					}
				}
			} catch (Exception e) {
				LogUtil.error(logger, "获取用户信息失败，e={}", e);
			}
		}
		return true;
	}

	/**
	 * 
	 * 初始化头像信息
	 *
	 * @author yd
	 * @created 2016年11月2日 下午1:54:50
	 *
	 * @param uid
	 * @return
	 */
	private  CustomerPicMsgEntity initCustomerPicMsg(String uid){
		
		CustomerPicMsgEntity customerPicMsg = new CustomerPicMsgEntity();
		customerPicMsg.setFid(UUIDGenerator.hexUUID());
		customerPicMsg.setUid(uid); 
		customerPicMsg.setPicType(PicTypeEnum.USER_PHOTO.getCode());
		customerPicMsg.setCreateDate(new Date());
		customerPicMsg.setLastModifyDate(new Date());
		customerPicMsg.setIsDel(IsDelEnum.NOT_DEL.getCode());
		
		return customerPicMsg;
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


}
