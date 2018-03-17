/**
 * @FileName: SmartLockController.java
 * @Package com.ziroom.minsu.mapp.house.controller
 * 
 * @author jixd
 * @created 2016年6月23日 上午11:25:06
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.mapp.house.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.DateUtil;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.RandomUtil;
import com.asura.framework.base.util.RegExpUtil;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.house.HouseBaseMsgEntity;
import com.ziroom.minsu.entity.house.HouseSmartlockEntity;
import com.ziroom.minsu.mapp.common.util.CustomerVoUtils;
import com.ziroom.minsu.services.basedata.api.inner.SmsTemplateService;
import com.ziroom.minsu.services.basedata.dto.SmsRequest;
import com.ziroom.minsu.services.common.utils.CloseableHttpUtil;
import com.ziroom.minsu.services.common.utils.StringUtils;
import com.ziroom.minsu.services.customer.entity.CustomerVo;
import com.ziroom.minsu.services.house.api.inner.HouseIssueService;
import com.ziroom.minsu.services.house.api.inner.HouseManageService;
import com.ziroom.minsu.services.house.smartlock.dto.MSmartLockDto;
import com.ziroom.minsu.services.house.smartlock.enumvalue.OnoffLineStatus;
import com.ziroom.minsu.services.house.smartlock.enumvalue.PasswordTypeEnum;
import com.ziroom.minsu.services.house.smartlock.enumvalue.SmartErrNoEnum;
import com.ziroom.minsu.services.house.smartlock.param.AUSLParam;
import com.ziroom.minsu.services.house.smartlock.param.PermissionTimeParam;
import com.ziroom.minsu.services.house.smartlock.vo.AddPassSLVo;
import com.ziroom.minsu.services.house.smartlock.vo.DynamicPassSlVo;
import com.ziroom.minsu.services.house.smartlock.vo.LockCallBackVo;
import com.ziroom.minsu.valenum.msg.MessageTemplateCodeEnum;

/**
 * <p>智能门锁</p>
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
@RequestMapping("/smartLock")
@Controller
public class SmartLockController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SmartLockController.class);
	
	@Resource(name="house.houseManageService")
	private HouseManageService houseManageService;
	
	@Resource(name="basedata.smsTemplateService")
	private SmsTemplateService smsTemplateService;
	
	@Resource(name = "house.houseIssueService")
	private HouseIssueService houseIssueService;
	
	@Value("${SMARTLOCK_ADD_PASSWORD}")
	private String SMARTLOCK_ADD_PASSWORD;
	
	@Value("${SMARTLOCK_UPDATE_PASSWORD}")
	private String SMARTLOCK_UPDATE_PASSWORD;
	
	@Value("${SMARTLOCK_DYNAMIC_PASSWORD}")
	private String SMARTLOCK_DYNAMIC_PASSWORD;
	
	@Value("${SMARTLOCK_LOCK_INFOS}")
	private String SMARTLOCK_LOCK_INFOS;
	
	@Value("${SMARTLOCK_CALLBACK}")
	private String SMARTLOCK_CALLBACK;
	
	private static final String DATE_PATTERN_C = "yyyy年MM日dd日 HH:mm";
	private static final String DATE_PATTERN_E = "yyyy-MM-dd HH:mm";
	
	/**
	 * 显示智能锁主面板
	 *
	 * @author jixd
	 * @created 2016年6月23日 上午11:27:25
	 *
	 * @return
	 */
	@RequestMapping("/${LOGIN_UNAUTH}/showPanel")
	public String showPanel(HttpServletRequest request){
		try {
			String houseBaseFid = request.getParameter("houseBaseFid");
			CustomerVo customerVo  = CustomerVoUtils.getCusotmerVoFromSesstion(request);
			String uid = customerVo.getUid();
			
			MSmartLockDto slDto = new MSmartLockDto();
			slDto.setLandlordUid(uid);
			slDto.setHouseBaseFid(houseBaseFid);
			
			String pass = "";
			DataTransferObject respDto = JsonEntityTransform.json2DataTransferObject(houseManageService.findHouseSmartlock(JsonEntityTransform.Object2Json(slDto)));
			if (respDto.getCode() == DataTransferObject.SUCCESS) {
				List<HouseSmartlockEntity> hsList = respDto.parseData("list", new TypeReference<List<HouseSmartlockEntity>>() {});
				if(!Check.NuNCollection(hsList)){
					HouseSmartlockEntity houseSmartlockEntity = hsList.get(0);
					pass = StringUtils.decode(houseSmartlockEntity.getPassword());
				}
			}
			request.setAttribute("pass", pass);
			request.setAttribute("houseBaseFid", houseBaseFid);
			request.setAttribute("houseRoomFid", request.getParameter("houseRoomFid"));
			request.setAttribute("rentWay", request.getParameter("rentWay"));
			//请求接口 查看门锁状态
			StringBuilder sb = new StringBuilder();
			sb.append("?op_userid="+uid+"&");
			sb.append("op_name="+URLEncoder.encode(customerVo.getRealName(),"UTF-8")+"&");
			sb.append("op_phone="+customerVo.getShowMobile()+"&");
			sb.append("house_id="+StringUtils.getSmartLockCode(houseBaseFid)+"&");
			sb.append("rooms=["+URLEncoder.encode("\""+StringUtils.getSmartLockCode(houseBaseFid)+"\"","UTF-8")+"]");
			LogUtil.info(LOGGER,"【智能锁状态请求参数】={}",sb.toString());
			String jsonStr = CloseableHttpUtil.sendGet(SMARTLOCK_LOCK_INFOS + sb.toString(), null);
			LogUtil.info(LOGGER,"【智能锁状态返回值】={}",jsonStr);
			JSONObject jsonObj = JSONObject.parseObject(jsonStr);
			if(Check.NuNObj(jsonObj)){
				//跳转错误页
				LogUtil.info(LOGGER, "【获取智能锁信息失败】error{}", jsonObj.getString("ErrMsg"));
				return "house/smartlock/smartLockOffLine";
			}
			Integer errno = jsonObj.getInteger("ErrNo");
			if(errno == SmartErrNoEnum.SUCCESS.getCode()){
				JSONArray lockArr = jsonObj.getJSONArray("locks");
				if(!Check.NuNObj(lockArr) && lockArr.size() > 0){
					JSONObject lockObj = lockArr.getJSONObject(0);
					JSONObject lock = lockObj.getJSONObject("lock");
					Integer onOffLine = lock.getInteger("onoff_line");
					
					Integer power = lock.getInteger("power");
					request.setAttribute("power", power);
					if(onOffLine == OnoffLineStatus.OFF_LINE.getCode()){
						return "house/smartlock/smartLockOffLine";
					}
				}
				
			}else{
				//跳转错误页
				LogUtil.info(LOGGER, "【获取智能锁信息失败】error{}", jsonObj.getString("ErrMsg"));
				return "house/smartlock/smartLockOffLine";
			}
		} catch (Exception e) {
			LogUtil.error(LOGGER, "【获取智能锁信息异常】error={}", e);
			return "house/smartlock/smartLockOffLine";
		}
		return "house/smartlock/smartLockOnLine";
	}
	
	/**
	 * 
	 * 管理密码（永久密码）
	 *
	 * @author jixd
	 * @created 2016年6月23日 上午11:34:34
	 *
	 * @return
	 */
	@RequestMapping("/${LOGIN_UNAUTH}/upPermanentPass")
	public String upPermanentPass(HttpServletRequest request){
		request.setAttribute("houseBaseFid", request.getParameter("houseBaseFid"));
		request.setAttribute("houseRoomFid", request.getParameter("houseRoomFid"));
		request.setAttribute("rentWay", request.getParameter("rentWay"));
		return "house/smartlock/upPermanentPass";
	}
	
	/**
	 * 
	 * 保存永久密码
	 *
	 * @author jixd
	 * @created 2016年6月24日 下午2:54:42
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/${LOGIN_UNAUTH}/savePermanentPass")
	@ResponseBody
	public DataTransferObject savePermanentPass(HttpServletRequest request){
		CustomerVo customerVo  = CustomerVoUtils.getCusotmerVoFromSesstion(request);
		String uid = customerVo.getUid();
		String houseBaseFid = request.getParameter("houseBaseFid");
		String slPass = request.getParameter("slpass");
		String reSlPass = request.getParameter("reSlPass");
		DataTransferObject dto = new DataTransferObject();
		try{
			if(Check.NuNStr(slPass) || Check.NuNStr(reSlPass)){ 
				dto.setErrCode(1);
				dto.setMsg("密码为空");
				return dto;
			}
			if(!slPass.equals(reSlPass)){
				dto.setErrCode(1);
				dto.setMsg("密码不一致");
				return dto;
			}
			if(slPass.length() != 6){
				dto.setErrCode(1);
				dto.setMsg("密码必须为6位");
				return dto;
			}
			if(!StringUtils.isNumeric(slPass)){
				dto.setErrCode(1);
				dto.setMsg("密码必须为数字");
				return dto;
			}
			
			MSmartLockDto slDto = new MSmartLockDto();
			slDto.setLandlordUid(uid);
			slDto.setHouseBaseFid(houseBaseFid);
			slDto.setRoomFid(houseBaseFid);
			String resultJson = houseManageService.findHouseSmartlock(JsonEntityTransform.Object2Json(slDto));
			DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(resultJson);
			if(resultDto.getCode() == DataTransferObject.ERROR){
				return resultDto;
			}
			List<HouseSmartlockEntity> list = resultDto.parseData("list", new TypeReference<List<HouseSmartlockEntity>>() {});
			
			String lanPhone = customerVo.getShowMobile();
			AUSLParam param = new AUSLParam(); 
			param.setOp_name(customerVo.getRealName());
			param.setOp_phone(customerVo.getShowMobile());
			param.setOp_userid(customerVo.getUid());
			
			param.setUser_name(customerVo.getRealName());
			param.setUser_phone(customerVo.getShowMobile());
			param.setCallback_url(SMARTLOCK_CALLBACK+"?passType=1&landlordUid="+uid+"&lanPhone="+lanPhone+"&houseBaseFid="+houseBaseFid);
			param.setHouse_id(StringUtils.getSmartLockCode(houseBaseFid));
			param.setRoom_id(StringUtils.getSmartLockCode(houseBaseFid));
			param.setIs_send_sms(1);
			param.setPassword(slPass);
			param.setPassword_type(PasswordTypeEnum.USER_PWD.getCode());
			param.setUser_identify(uid);
			
			PermissionTimeParam perTime = new PermissionTimeParam();
			Date beginTime = get10MinuteTime(new Date());
			Date endTime = get20YearDate(beginTime);
			
			//永久密码时间都设置为0
			perTime.setBegin(beginTime.getTime()/1000);
			perTime.setEnd(endTime.getTime()/1000);
			param.setPermission(perTime);
			
			String jsonPostParam = JsonEntityTransform.Object2Json(param);
			
			LogUtil.info(LOGGER, "【智能锁设置永久密码参数】param={}",jsonPostParam);
			
			if(!Check.NuNCollection(list) && list.get(0).getPasswordStatus() == 2){
				LogUtil.info(LOGGER, "智能锁更新密码流程");
				//发送更新请求
				String updateJson = CloseableHttpUtil.sendPost(SMARTLOCK_UPDATE_PASSWORD, jsonPostParam);
				AddPassSLVo addPassSL = JSONObject.parseObject(updateJson, AddPassSLVo.class);
				if(addPassSL.getErrNo() == SmartErrNoEnum.SUCCESS.getCode()){
					LogUtil.info(LOGGER, "【智能锁更新密码请求成功】");
					HouseSmartlockEntity houseSmartlockEntity = list.get(0);
					slDto.setFid(houseSmartlockEntity.getFid());
					//更新有 下划线
					slDto.setServiceId(addPassSL.getService_id());
					slDto.setUpdatePassword(StringUtils.encode(slPass));
					DataTransferObject upDto = JsonEntityTransform.json2DataTransferObject(houseManageService.updateHouseSmartlock(JsonEntityTransform.Object2Json(slDto)));
					if(upDto.getCode() != DataTransferObject.SUCCESS){
						LogUtil.error(LOGGER, "【更新智能锁密码入库失败】error={}", upDto.getMsg());
					}
				}else{
					LogUtil.error(LOGGER, "【智能锁密码请求失败】error={}", addPassSL.getErrMsg());
					dto.setErrCode(2);
					dto.setMsg("智能锁密码更新失败");
					return dto;
				}
			}else{
				LogUtil.info(LOGGER, "智能锁添加密码流程");
				String addpassJson = CloseableHttpUtil.sendPost(SMARTLOCK_ADD_PASSWORD, jsonPostParam);
				AddPassSLVo addPassSL = JSONObject.parseObject(addpassJson, AddPassSLVo.class);
				if(addPassSL.getErrNo() == SmartErrNoEnum.SUCCESS.getCode()){
					LogUtil.info(LOGGER, "智能锁添加密码成功");
					//新增返回没有下划线
					slDto.setServiceId(addPassSL.getServiceid());
					slDto.setPermissionBegin(beginTime);
					slDto.setPermissionEnd(endTime);
					slDto.setPassword(StringUtils.encode(slPass));
					if(Check.NuNCollection(list)){
						LogUtil.info(LOGGER, "【智能锁添加密码流程-->add】");
						DataTransferObject saveDto = JsonEntityTransform.json2DataTransferObject(houseManageService.saveHouseSmartlock(JsonEntityTransform.Object2Json(slDto)));
						if(saveDto.getCode() != DataTransferObject.SUCCESS){
							LogUtil.error(LOGGER, "【智能锁添加密码流程-->add】error={}", saveDto.getMsg());
						}
					}else{
						HouseSmartlockEntity smLock = list.get(0);
						slDto.setFid(smLock.getFid());
						LogUtil.info(LOGGER, "【智能锁添加密码流程-->update】");
						DataTransferObject upDto = JsonEntityTransform.json2DataTransferObject(houseManageService.updateHouseSmartlock(JsonEntityTransform.Object2Json(slDto)));
						if(upDto.getCode() != DataTransferObject.SUCCESS){
							LogUtil.error(LOGGER, "【智能锁添加密码流程-->update】error={}", upDto.getMsg());
						}
					}
				}else{
					LogUtil.error(LOGGER, "【智能锁密码请求失败】error={}", addPassSL.getErrMsg());
					dto.setErrCode(2);
					dto.setMsg("智能锁密码更新失败");
					return dto;
				}
			}
			
		}catch(Exception e){
			LogUtil.error(LOGGER, "【保存修改智能锁密码异常】error={}", e);
			dto.setErrCode(2);
			dto.setMsg("发送失败");
		}
		
		return dto;
	}
	
	/**
	 * 
	 * 门锁 不在线状态下    动态密码
	 *
	 * @author jixd
	 * @created 2016年6月23日 上午11:43:26
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/${LOGIN_UNAUTH}/getDynamicPass")
	public String getDynamicPass(HttpServletRequest request){
		request.setAttribute("houseBaseFid", request.getParameter("houseBaseFid"));
		request.setAttribute("houseRoomFid", request.getParameter("houseRoomFid"));
		request.setAttribute("rentWay", request.getParameter("rentWay"));
		return "house/smartlock/getDynamicPass";
	}
	/**
	 * 
	 * 门锁 不在线状态下  发送动态密码（2小时）
	 *
	 * @author jixd
	 * @created 2016年6月24日 下午2:58:05
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/${LOGIN_UNAUTH}/sendDynamicPass")
	@ResponseBody
	public DataTransferObject sendDynamicPass(HttpServletRequest request){
		String phoneNum = request.getParameter("phoneNum");
		String houseBaseFid = request.getParameter("houseBaseFid");
		CustomerVo customerVo  = CustomerVoUtils.getCusotmerVoFromSesstion(request);
		DataTransferObject dto = new DataTransferObject();
		if(!RegExpUtil.isMobilePhoneNum(phoneNum)){
			dto.setErrCode(1);
			dto.setMsg("电话号码格式不正确");
			return dto;
		}
		if(Check.NuNStr(houseBaseFid)){
			dto.setErrCode(1);
			dto.setMsg("房源Id为空");
			return dto;
		}
		try{
			StringBuilder sb = new StringBuilder();
			sb.append("?op_userid="+customerVo.getUid()+"&");
			sb.append("op_name="+URLEncoder.encode(customerVo.getRealName(),"UTF-8")+"&");
			sb.append("op_phone="+customerVo.getShowMobile()+"&");
			sb.append("house_id="+StringUtils.getSmartLockCode(houseBaseFid)+"&");
			sb.append("room_id="+StringUtils.getSmartLockCode(houseBaseFid));
			String responseStr = CloseableHttpUtil.sendGet(SMARTLOCK_DYNAMIC_PASSWORD + sb.toString(), null);
			DynamicPassSlVo passVo = JSONObject.parseObject(responseStr, DynamicPassSlVo.class);
			if(passVo.getErrNo() != SmartErrNoEnum.SUCCESS.getCode()){
				LogUtil.error(LOGGER, "【获取动态密码失败】error={}", passVo.getErrMsg());
				dto.setErrCode(1);
				dto.setMsg("获取密码失败");
				return dto;
			}else{
				String houseName = "自如民宿";
				DataTransferObject houseBaseMsgDto = JsonEntityTransform.json2DataTransferObject(houseIssueService.searchHouseBaseMsgByFid(houseBaseFid));
				if(houseBaseMsgDto.getCode() == DataTransferObject.SUCCESS){
					HouseBaseMsgEntity houseBaseMsgEntity = houseBaseMsgDto.parseData("obj", new TypeReference<HouseBaseMsgEntity>() {});
					houseName = houseBaseMsgEntity.getHouseName();
				}
				
				LogUtil.info(LOGGER, "获取动态密码成功 发送短信....");
				
				Date nowDate = new Date();
				String startDate = DateUtil.dateFormat(nowDate, DATE_PATTERN_C);
				String endDate = DateUtil.dateFormat(get2HourTime(nowDate), DATE_PATTERN_C);
				//给房东发 成功消息
				Map<String,String> paramsMap = new HashMap<String,String>();
				SmsRequest smsRequest = new SmsRequest();
				//给接收人发消息
				paramsMap.put("{1}", houseName);
				paramsMap.put("{2}", passVo.getPassword());
				paramsMap.put("{3}", startDate);
				paramsMap.put("{4}", endDate);
				smsRequest.setMobile(phoneNum);
				smsRequest.setParamsMap(paramsMap);
				smsRequest.setSmsCode(String.valueOf(MessageTemplateCodeEnum.SMARTLOCK_OTHER_DYNAPWD_SUCCESS.getCode()));
				smsTemplateService.sendSmsByCode(JsonEntityTransform.Object2Json(smsRequest));
			}
		}catch(Exception e){
			LogUtil.error(LOGGER, "【获取动态门锁密码异常】error={}", e);
			dto.setErrCode(1);
			dto.setMsg("服务错误");
		}
		//调用接口发送
		return dto;
	}
	
	
	/**
	 * 申请临时密码
	 *
	 * @author jixd
	 * @created 2016年6月23日 上午11:45:16
	 *
	 * @return
	 */
	@RequestMapping("/${LOGIN_UNAUTH}/appForTempPass")
	public String appForTempPass(HttpServletRequest request){
		request.setAttribute("houseBaseFid", request.getParameter("houseBaseFid"));
		request.setAttribute("houseRoomFid", request.getParameter("houseRoomFid"));
		request.setAttribute("rentWay", request.getParameter("rentWay"));
		return "house/smartlock/appForTempPass";
	}
	
	/**
	 * 
	 * 发送临时密码
	 *
	 * @author jixd
	 * @created 2016年6月24日 下午2:59:16
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/${LOGIN_UNAUTH}/sendTempPass")
	@ResponseBody
	public DataTransferObject sendTempPass(HttpServletRequest request){
		DataTransferObject dto = new DataTransferObject();
		try{
			String houseBaseFid = request.getParameter("houseBaseFid");
			String startTime = request.getParameter("start_time");
			String endTime = request.getParameter("end_time");
			String phoneNum = request.getParameter("phoneNum");
			CustomerVo customerVo  = CustomerVoUtils.getCusotmerVoFromSesstion(request);
			if(Check.NuNStr(houseBaseFid)){
				dto.setErrCode(1);
				dto.setMsg("房源ID为空");
				return dto;
			}
			if(Check.NuNStr(startTime)){
				dto.setErrCode(1);
				dto.setMsg("开始时间为空");
				return dto;
			}
			if(Check.NuNStr(endTime)){
				dto.setErrCode(1);
				dto.setMsg("结束时间为空");
				return dto;
			}
			if(!RegExpUtil.isMobilePhoneNum(phoneNum)){
				dto.setErrCode(1);
				dto.setMsg("电话号码格式不正确");
				return dto;
			}
			Date startDate = DateUtil.parseDate(startTime, DATE_PATTERN_E);
			Date endDate = DateUtil.parseDate(endTime, DATE_PATTERN_E);
			if(startDate.after(endDate)){
				dto.setErrCode(1);
				dto.setMsg("开始时间不能大于结束时间");
				return dto;
			}
			
			String lanPhone = customerVo.getShowMobile();
			String pass = RandomUtil.genRandomNum(6);
			String encodePass = StringUtils.encode(pass);
			
			
			StringBuilder sb = new StringBuilder();
			sb.append(SMARTLOCK_CALLBACK).append("?passType=2&pass=").append(encodePass)
			.append("&phone=").append(phoneNum).append("&lanPhone=").append(lanPhone).append("&houseBaseFid=").append(houseBaseFid)
			.append("&startDate=").append(DateUtil.dateFormat(startDate, DATE_PATTERN_C)).append("&endDate=").append(DateUtil.dateFormat(endDate, DATE_PATTERN_C));
			AUSLParam param = new AUSLParam();
			param.setOp_name(customerVo.getRealName());
			param.setOp_phone(lanPhone);
			param.setOp_userid(customerVo.getUid());
			//使用者电话
			param.setUser_name(phoneNum);
			param.setUser_phone(phoneNum);
			param.setCallback_url(sb.toString());
			param.setHouse_id(StringUtils.getSmartLockCode(houseBaseFid));
			param.setRoom_id(StringUtils.getSmartLockCode(houseBaseFid));
			param.setIs_send_sms(1);
			param.setPassword(pass);
			param.setPassword_type(PasswordTypeEnum.INTERNAL_STAFF.getCode());
			//临时密码的标识为手机号码
			param.setUser_identify(phoneNum);
			PermissionTimeParam perTime = new PermissionTimeParam();
			perTime.setBegin(startDate.getTime()/1000);
			perTime.setEnd(endDate.getTime()/1000);
			param.setPermission(perTime);
			String paramJson = JsonEntityTransform.Object2Json(param);
			LogUtil.info(LOGGER,"【获取临时密码请求参数】param={}", paramJson);
			String responseStr = CloseableHttpUtil.sendPost(SMARTLOCK_ADD_PASSWORD, paramJson);
			if (Check.NuNStr(responseStr)) {
				LogUtil.error(LOGGER, "【获取临时密码失败】没有响应");
				dto.setErrCode(1);
				dto.setMsg("获取密码失败");
				return dto;
			}
			AddPassSLVo slVo = JSONObject.parseObject(responseStr, AddPassSLVo.class);
			if(slVo.getErrNo() != SmartErrNoEnum.SUCCESS.getCode()){
				LogUtil.error(LOGGER, "【获取临时密码失败】error={}", slVo.getErrMsg());
				dto.setErrCode(1);
				dto.setMsg("获取密码失败");
				return dto;
			}
		}catch(Exception e){
			LogUtil.error(LOGGER, "【获取临时密码异常】e={}", e);
			dto.setErrCode(1);
			dto.setMsg("获取密码失败");
			return dto;
		}
		return dto;
	}
	
	/**
	 * 
	 * 智能锁回调参数返回
	 *
	 * @author jixd
	 * @created 2016年6月25日 下午12:46:51
	 *
	 * @param request
	 */
	@RequestMapping("/${NO_LOGIN_AUTH}/callback")
	public void smartLockCallBack(HttpServletRequest request){
		
		InputStream is = null;
		BufferedReader reader = null;
		try{
			is = request.getInputStream();
			reader = new BufferedReader(new InputStreamReader(is));
			StringBuilder sb = new StringBuilder();
			String line = "";
			while((line=reader.readLine())!=null){
				sb.append(line);
			}

			LogUtil.info(LOGGER, "【智能锁回调参数】param={}", sb.toString());

			String landlordUid = request.getParameter("landlordUid");
			String passType = request.getParameter("passType");
			String pass = request.getParameter("pass");
			String phone = request.getParameter("phone");
			String lanPhone = request.getParameter("lanPhone");
			String houseBaseFid = request.getParameter("houseBaseFid");
			String startDate = request.getParameter("startDate");
			String endDate = request.getParameter("endDate");
			if(Check.NuNStr(sb.toString())){
				LogUtil.info(LOGGER,"【智能锁回调数据为空】passType={},pass={},phone={},lanPhone={},houseBaseFid={}",passType,pass,phone,lanPhone,houseBaseFid);
				return;
			}
			LockCallBackVo lockCallBack = JSONObject.parseObject(sb.toString(), LockCallBackVo.class);
			LogUtil.info(LOGGER, "【智能锁回调返回参数-自定义】serviceId={},result={},passType={},pass={},phone={},lanPhone={},houseBaseFid={}", 
					lockCallBack.getService_id(),lockCallBack.getResult(),passType,pass,phone,lanPhone,houseBaseFid);
			
			//查询房源名称   发短信使用
			String houseName = "自如民宿";
			DataTransferObject houseBaseMsgDto = JsonEntityTransform.json2DataTransferObject(houseIssueService.searchHouseBaseMsgByFid(houseBaseFid));
			if(houseBaseMsgDto.getCode() == DataTransferObject.SUCCESS){
				HouseBaseMsgEntity houseBaseMsgEntity = houseBaseMsgDto.parseData("obj", new TypeReference<HouseBaseMsgEntity>() {});
				houseName = houseBaseMsgEntity.getHouseName();
			}
			
			if("1".equals(passType)){
				//永久密码
				MSmartLockDto slDto = new MSmartLockDto();
				slDto.setServiceId(lockCallBack.getService_id());
				slDto.setLandlordUid(landlordUid);
				DataTransferObject respDto = JsonEntityTransform.json2DataTransferObject(houseManageService.findHouseSmartlock(JsonEntityTransform.Object2Json(slDto)));
				if (respDto.getCode() == DataTransferObject.SUCCESS) {
					List<HouseSmartlockEntity> hsList = respDto.parseData("list", new TypeReference<List<HouseSmartlockEntity>>() {});
					if(!Check.NuNCollection(hsList)){
						HouseSmartlockEntity smartlockEntity = hsList.get(0);
						String updatePass = smartlockEntity.getUpdatePassword();
						
						//2种情况  1第一次添加密码 更新状态 ，没有updatePassword   
						if(lockCallBack.getResult() == 1){
							LogUtil.info(LOGGER, "【管理密码更新成功】result={}", lockCallBack.getResult());
							
							slDto.setFid(smartlockEntity.getFid());
							slDto.setHouseBaseFid(smartlockEntity.getHouseBaseFid());
							slDto.setPasswordStatus(2);
							if(!Check.NuNStr(updatePass)){
								//更新密码
								slDto.setPassword(updatePass);
								slDto.setUpdatePassword("");
								Date beginTime = new Date();
								Date endTime = get20YearDate(beginTime);
								slDto.setPermissionBegin(beginTime);
								slDto.setPermissionEnd(endTime);
							}else{
								//防止空指针
								updatePass = smartlockEntity.getPassword();
							}
							DataTransferObject upDto = JsonEntityTransform.json2DataTransferObject(houseManageService.updateHouseSmartlock(JsonEntityTransform.Object2Json(slDto)));
							if(upDto.getCode() == DataTransferObject.SUCCESS){
								LogUtil.info(LOGGER, "智能锁密码更新成功 housefid={}", smartlockEntity.getHouseBaseFid());
							}else{
								LogUtil.error(LOGGER, "更新智能锁密码入库失败={}", upDto.getMsg());
							}
							
							//管理密码更新成功发送短信
							Map<String,String> paramsMap = new HashMap<String,String>();
							paramsMap.put("{1}", houseName);
							paramsMap.put("{2}", StringUtils.decode(updatePass));
							SmsRequest smsRequest = new SmsRequest();
							smsRequest.setMobile(lanPhone);
							smsRequest.setParamsMap(paramsMap);
							smsRequest.setSmsCode(String.valueOf(MessageTemplateCodeEnum.SMARTLOCK_LANDLORD_MANAGERPWD_SUCCESS.getCode()));
							smsTemplateService.sendSmsByCode(JsonEntityTransform.Object2Json(smsRequest));
							
						}else{
							LogUtil.error(LOGGER, "【管理密码更新失败】result={}", lockCallBack.getResult());
							//管理密码更新失败  发送短信
							Map<String,String> paramsMap = new HashMap<String,String>();
							paramsMap.put("{1}", houseName);
							SmsRequest smsRequest = new SmsRequest();
							smsRequest.setMobile(lanPhone);
							smsRequest.setParamsMap(paramsMap);
							smsRequest.setSmsCode(String.valueOf(MessageTemplateCodeEnum.SMARTLOCK_LANDLORD_MANAGERPWD_FAIL.getCode()));
							smsTemplateService.sendSmsByCode(JsonEntityTransform.Object2Json(smsRequest));
							
						}
					}
				}
			}else{
				Map<String,String> paramsMap = new HashMap<String,String>();
				SmsRequest smsRequest = new SmsRequest();
				//临时密码 设置成功发送短信
				if(lockCallBack.getResult() == 1){
					LogUtil.info(LOGGER, "临时密码回调成功 发送短信...");
					//真实密码
					String truePass = StringUtils.decode(pass);
					paramsMap.put("{1}", houseName);
					paramsMap.put("{2}", StringUtils.getPhoneSecret(phone));
					paramsMap.put("{3}", startDate);
					paramsMap.put("{4}", endDate);
					smsRequest.setMobile(lanPhone);
					smsRequest.setParamsMap(paramsMap);
					smsRequest.setSmsCode(String.valueOf(MessageTemplateCodeEnum.SMARTLOCK_LANDLORD_TEMPPWD_SUCCESS.getCode()));
					smsTemplateService.sendSmsByCode(JsonEntityTransform.Object2Json(smsRequest));
					//告诉其他人密码
					paramsMap.clear();
					paramsMap.put("{1}", houseName);
					paramsMap.put("{2}", truePass);
					paramsMap.put("{3}", startDate);
					paramsMap.put("{4}", endDate);
					smsRequest.setMobile(phone);
					smsRequest.setParamsMap(paramsMap);
					smsRequest.setSmsCode(String.valueOf(MessageTemplateCodeEnum.SMARTLOCK_OTHER_TEMPPWD_SUCCESS.getCode()));
					smsTemplateService.sendSmsByCode(JsonEntityTransform.Object2Json(smsRequest));
				}else{
					//失败  发送短信
					smsRequest.setMobile(lanPhone);
					paramsMap.put("{1}", houseName);
					smsRequest.setParamsMap(paramsMap);
					smsRequest.setSmsCode(String.valueOf(MessageTemplateCodeEnum.SMARTLOCK_LANDLORD_TEMPPWD_FAIL.getCode()));
					smsTemplateService.sendSmsByCode(JsonEntityTransform.Object2Json(smsRequest));
				}
			}
		}catch(Exception e){
			LogUtil.error(LOGGER, "智能锁回调异常error={}", e);
		}finally{
			if(is != null){
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(reader != null){
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 
	 * 20年
	 *
	 * @author jixd
	 * @created 2016年6月25日 下午5:56:12
	 *
	 * @param date
	 * @return
	 */
	private Date get20YearDate(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.YEAR, 20);
		return calendar.getTime();
	}
	
	/**
	 * 
	 * 获取10分钟以前的时间，防止密码修改成功后不生效
	 *
	 * @author jixd
	 * @created 2016年6月27日 下午3:10:57
	 *
	 * @param date
	 * @return
	 */
	private Date get10MinuteTime(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MINUTE, -10);
		return calendar.getTime();
	}
	
	/**
	 * 获取两个小时时间
	 *
	 * @author jixd
	 * @created 2016年6月27日 下午8:57:20
	 *
	 * @param date
	 * @return
	 */
	private Date get2HourTime(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.HOUR, 2);
		return calendar.getTime();
	}

	/*public static void main(String[] args) {
		try {
			
			String startTime = "2016-08-12 00:00";
			Date startDate = DateUtil.parseDate(startTime, DATE_PATTERN_E);
			System.out.println(startDate);
			System.out.println(startDate.getTime()/1000);
			String m = DateUtil.dateFormat(startDate, DATE_PATTERN_C);
			System.out.println(m);
			
			String s = DateUtil.dateFormat(new Date(), DATE_PATTERN_E);
			System.out.println(s);
			
			
			SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_PATTERN_E);
			String format = dateFormat.format(startDate);
			System.out.println(format);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
	}*/
}
