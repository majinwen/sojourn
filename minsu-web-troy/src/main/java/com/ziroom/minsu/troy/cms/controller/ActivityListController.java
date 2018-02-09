/**
 * @FileName: CmsController.java
 * @Package com.ziroom.minsu.troy.cms.controller
 * 
 * @author liyingjie
 * @created 2016年6月15日 下午10:03:17
 * 
 * Copyright 2011-2015 
 */
package com.ziroom.minsu.troy.cms.controller;

import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.util.DateUtil;
import com.ziroom.minsu.entity.cms.ActivityEntity;
import com.ziroom.minsu.services.cms.api.inner.ActCouponService;
import com.ziroom.minsu.services.cms.dto.MobileCouponRequest;
import com.ziroom.minsu.services.common.sms.MessageUtils;
import com.ziroom.minsu.services.common.sms.base.SmsMessage;
import com.ziroom.minsu.troy.cms.service.GroupService;
import org.apache.commons.collections.CollectionUtils;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.cms.ActivityCityEntity;
import com.ziroom.minsu.entity.cms.ActivityVo;
import com.ziroom.minsu.entity.customer.CustomerBaseMsgEntity;
import com.ziroom.minsu.entity.customer.CustomerRoleBaseEntity;
import com.ziroom.minsu.services.basedata.api.inner.ConfCityService;
import com.ziroom.minsu.services.cms.api.inner.ActivityFullService;
import com.ziroom.minsu.services.cms.api.inner.ActivityRecordService;
import com.ziroom.minsu.services.cms.api.inner.ActivityService;
import com.ziroom.minsu.services.cms.dto.ActivityInfoRequest;
import com.ziroom.minsu.services.cms.dto.ActivityRecordRequest;
import com.ziroom.minsu.services.cms.entity.ActRecordVo;
import com.ziroom.minsu.services.common.page.PageResult;
import com.ziroom.minsu.services.customer.api.inner.CustomerMsgManagerService;
import com.ziroom.minsu.services.customer.api.inner.CustomerRoleService;
import com.ziroom.minsu.valenum.cms.ActKindEnum;


/**
 * <p>
 *     活动相关
 * </p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author afi
 * @since 1.0
 * @version 1.0
 * @param
 */
@Controller
@RequestMapping("activity")
public class ActivityListController {


	/**
	 * 日志对象
	 */
	private static Logger LOGGER = LoggerFactory.getLogger(ActivityListController.class);


	@Resource(name="basedata.confCityService")
	private ConfCityService confCityService;


	@Resource(name="cms.activityService")
	private ActivityService activityService;

	@Resource(name="api.groupService")
	private GroupService groupService;

	@Resource(name="cms.activityFullService")
	private ActivityFullService activityFullService;

	@Resource(name = "cms.actCouponService")
	private ActCouponService actCouponService;

	@Resource(name = "customer.customerRoleService")
	private CustomerRoleService customerRoleService;

	@Resource(name = "cms.activityRecordService")
	private ActivityRecordService activityRecordService;

	@Resource(name = "customer.customerMsgManagerService")	
	private CustomerMsgManagerService customerMsgManagerService;
	/**
	 * 活动列表
	 *
	 * @author liyingjie
	 * @created 2016年6月15日 
	 * 
	 */
	@RequestMapping("/activityList")
	public ModelAndView toActivityList(HttpServletRequest request){
		ModelAndView mv = new ModelAndView("/activity/activityList");
		List<Map> openList = getOpenCityList();
		mv.addObject("cityList", openList);
		//获取角色信息
		String resultRole = customerRoleService.getBaseRoles();
		DataTransferObject roleDto = JsonEntityTransform.json2DataTransferObject(resultRole);
		List<CustomerRoleBaseEntity>  roles = null;
		try{
			roles =roleDto.parseData("roles", new TypeReference<List<CustomerRoleBaseEntity>>() {});
			//返回结果
		}catch(Exception e){
			LogUtil.error(LOGGER, "数据转化异常e={}", e);
		}
		mv.addObject("roles", roles);

		mv.addObject("groupList",groupService.getAllGroup());

		return mv;
	}

	/**
	 * 获取开放城市列表
	 *
	 * @author liyingjie
	 * @created 2016年6月15日
	 * @param
	 */
	private List<Map> getOpenCityList(){
		List<Map> openList = null;
		try{
			String resultJson =  confCityService.getOpenCity();
			DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(resultJson);
			openList = resultDto.parseData("list", new TypeReference<List<Map>>() {});
		}catch(Exception ex){
			LogUtil.error(LOGGER, "error:{}", ex);
			openList = new ArrayList<Map>();
		}
		return openList;
	}


	/**
	 * 获取活动列表
	 * @author lishaochuan
	 * @create 2016年6月24日上午11:12:28
	 * @param request
	 * @param paramRequest
	 * @return
	 */
	@RequestMapping("activityDataList")
	@ResponseBody
	public PageResult activityDataList(ActivityInfoRequest paramRequest, HttpServletRequest request) {
		//获取城市信息
		String cityJson = confCityService.getAllCityMap();
		DataTransferObject cityDto = JsonEntityTransform.json2DataTransferObject(cityJson);
		Map<String, String> cityMap = cityDto.parseData("map", new TypeReference<Map<String, String>>() {
		});

		//获取角色信息
		String resultRole = customerRoleService.getBaseRolesMap();
		DataTransferObject roleDto = JsonEntityTransform.json2DataTransferObject(resultRole);
		Map<String,CustomerRoleBaseEntity> roleMap = null;
		try{
			roleMap =roleDto.parseData("rolesMap", new TypeReference<Map<String,CustomerRoleBaseEntity>>() {});
			//返回结果
		}catch(Exception e){
			LogUtil.error(LOGGER, "数据转化异常e={}", e);
		}
		if (Check.NuNObj(roleMap)){
			roleMap = new HashMap<>();
		}
		String resultJson = activityService.getActivityVoListByCondiction(JsonEntityTransform.Object2Json(paramRequest));
		DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(resultJson);
		if (resultDto.getCode() != DataTransferObject.SUCCESS){
			return new PageResult();
		}
		List<ActivityVo> actCouponList = resultDto.parseData("list", new TypeReference<List<ActivityVo>>() {});
		if (Check.NuNObj(actCouponList)){
			actCouponList = new ArrayList<>();
		}
		for (ActivityVo activityInfoVo : actCouponList) {
			//设置当前的活动对象code
			String roleCode = activityInfoVo.getRoleCode();
			if (!Check.NuNStr(roleCode)){
				CustomerRoleBaseEntity roleBaseEntity = roleMap.get(roleCode);
				if (!Check.NuNObj(roleBaseEntity)){
					activityInfoVo.setRoleCode(roleBaseEntity.getRoleName());
				}
				if ("0".equals(roleCode)){
					activityInfoVo.setRoleCode("全部");
				}
			}

			List<ActivityCityEntity> activityCityEntityList = activityInfoVo.getCityList();
			if (Check.NuNCollection(activityCityEntityList)){
				continue;
			}
			String cityStr = "";
			int i=0;
			for (ActivityCityEntity cityEntity : activityCityEntityList) {
				if ("0".equals(cityEntity.getCityCode())){
					cityStr = "全部";
					break;
				}else {
					if(!Check.NuNMap(cityMap) && !Check.NuNStr(cityMap.get(cityEntity.getCityCode()))){
						if(i == 0){
							cityStr +=  cityMap.get(cityEntity.getCityCode());
						}else{
							cityStr +=  ","+ cityMap.get(cityEntity.getCityCode()) ;
						}
						i++;
					}
				}
			}
			activityInfoVo.setCityStr(cityStr);
		}

		//返回结果
		PageResult pageResult = new PageResult();
		pageResult.setRows(actCouponList);
		pageResult.setTotal(Long.valueOf(resultDto.getData().get("total").toString()));
		return pageResult;
	}


	/**
	 * 追加优惠券
	 * @author afi
	 * @create 2016年9月12日下午7:48:51
	 * @param extActivitySn
	 * @param extNum
	 * @param request
	 * @return
	 */
	@RequestMapping("extActivity")
	@ResponseBody
	public DataTransferObject extActivity(String extActivitySn, Integer extNum,HttpServletRequest request) {
		DataTransferObject dto = new DataTransferObject();

		if (Check.NuNStr(extActivitySn)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("活动编号为空");
			return dto;
		}

		if (Check.NuNObj(extNum) || extNum < 1){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("异常的数量");
			return dto;
		}
		//优惠券追加优惠券数量
		String resultJson = activityFullService.generateCouponByActSnExt(extActivitySn,extNum);
		DataTransferObject couponDto = JsonEntityTransform.json2DataTransferObject(resultJson);
		return couponDto;
	}


	/**
	 * 启动活动
	 * @author lishaochuan
	 * @create 2016年6月24日下午7:48:51
	 * @param activitySn
	 * @param actKind
	 * @param request
	 * @return
	 */
	@RequestMapping("startActivity")
	@ResponseBody
	public DataTransferObject startActivity(String activitySn, String actKind,HttpServletRequest request) {
		DataTransferObject dto = new DataTransferObject();

		if (Check.NuNStr(activitySn)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("参数为空");
			return dto;
		}

		if (Check.NuNStr(actKind)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("参数为空");
			return dto;
		}
		if (actKind.equals(ActKindEnum.COUPON.getCode()+"")){
			//优惠券
			String resultJson = activityFullService.generateCouponByActSn(activitySn);
			DataTransferObject couponDto = JsonEntityTransform.json2DataTransferObject(resultJson);
			if (couponDto.getCode() == DataTransferObject.SUCCESS){
				activityService.enableActivity(activitySn);
			}
			return JsonEntityTransform.json2DataTransferObject(resultJson);

		}else if (actKind.equals(ActKindEnum.NORMAL.getCode()+"")){
			//普通活动
			String resultJson = activityService.enableActivity(activitySn);
			return JsonEntityTransform.json2DataTransferObject(resultJson);
		}else {
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("异常的活动状态");
			return dto;
		}

	}



	/**
	 *  终止活动
	 * @author afi
	 * @param activitySn
	 * @param request
	 * @return
	 */
	@RequestMapping("endActivity")
	@ResponseBody
	public DataTransferObject endActivity(String activitySn, HttpServletRequest request) {
		String resultJson = activityService.endActivity(activitySn);
		return JsonEntityTransform.json2DataTransferObject(resultJson);
	}

	/**
	 * 
	 * 到活动记录 列表
	 *
	 * @author yd
	 * @created 2016年10月11日 下午4:37:02
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/goToAcRecordList")
	public String goToAcRecordList(HttpServletRequest request){
		return "/activity/acRecordList";
	}


	/**
	 * 
	 * 查询活动领取记录列表
	 *
	 * @author yd
	 * @created 2016年10月11日 下午4:47:32
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/queryAcRecordList")
	@ResponseBody
	public PageResult  queryAcRecordList(HttpServletRequest request,ActivityRecordRequest paramRequest){


		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(this.activityRecordService.queryAcRecordInfoByPage(JsonEntityTransform.Object2Json(paramRequest)));

		if (dto.getCode() != DataTransferObject.SUCCESS){
			return new PageResult();
		}

		List<ActRecordVo> listActRecordVos = dto.parseData("listActRecordVo", new TypeReference<List<ActRecordVo>>() {
		});

		if(!Check.NuNCollection(listActRecordVos)){
			DataTransferObject cusDto = null;
			for (ActRecordVo actRecordVo : listActRecordVos) {
				if(!Check.NuNStr(actRecordVo.getUserUid())){
					cusDto = JsonEntityTransform.json2DataTransferObject(this.customerMsgManagerService.getCustomerBaseMsgEntitybyUid(actRecordVo.getUserUid()));
					if(cusDto.getCode()== DataTransferObject.SUCCESS){
						CustomerBaseMsgEntity cus = cusDto.parseData("customer", new TypeReference<CustomerBaseMsgEntity>() {
						});
						if(!Check.NuNObj(cus)){
							if(Check.NuNStr(actRecordVo.getUserName())) actRecordVo.setUserName(cus.getRealName());
							if(Check.NuNStr(actRecordVo.getUserMobile())) actRecordVo.setUserMobile(cus.getCustomerMobile());
						}
					}
				}
				
			}
		}
		//返回结果
		PageResult pageResult = new PageResult();
		pageResult.setRows(listActRecordVos);
		pageResult.setTotal(Long.valueOf(dto.getData().get("count").toString()));
		return pageResult;
	}
	
	/**
	 * 跳转发送优惠券页面
	 * @author jixd
	 * @created 2017年02月16日 12:04:32
	 * @param
	 * @return
	 */
	@RequestMapping("/sendCoupon")
	public String sendCoupon(HttpServletRequest request){
		return "/activity/sendCoupon";
	}

	/**
	 * 手机号绑定优惠券
	 * @author jixd
	 * @created 2017年02月16日 14:43:08
	 * @param
	 * @return
	 */
	@RequestMapping("/saveCouponAndPhoneNums")
	@ResponseBody
	public DataTransferObject saveCouponAndPhoneNums(HttpServletRequest request){
		DataTransferObject dto = new DataTransferObject();
		String smsContent = request.getParameter("smsContent");
		String phoneNums = request.getParameter("phoneNums");
		String actSn = request.getParameter("actSn");
		if (Check.NuNStr(smsContent)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("短信内容为空");
			return dto;
		}
		if (Check.NuNStr(phoneNums)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("手机号码为空");
			return dto;
		}
		if (Check.NuNStr(actSn)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("活动号为空");
			return dto;
		}
		MobileCouponRequest mobileCouponRequest = new MobileCouponRequest();
		mobileCouponRequest.setMobile(phoneNums);
		mobileCouponRequest.setActSn(actSn);
		String result = actCouponService.bindCouponByPhoneNums(JsonEntityTransform.Object2Json(mobileCouponRequest));
		LogUtil.info(LOGGER,"批量绑定手机优惠券返回结果result={}",result);
		dto = JsonEntityTransform.json2DataTransferObject(result);
		if (dto.getCode() == DataTransferObject.SUCCESS){
			List<String> tarList = new ArrayList<>();
			List<String> sucList = dto.parseData("sucList", new TypeReference<List<String>>() {});
			//绑定成功 发送短信
			SmsMessage smsMessage = new SmsMessage("",smsContent);
			for (String sendMobile : sucList){
				smsMessage.setTo(sendMobile);
				MessageUtils.sendSms(smsMessage,null);
			}

			String[] sourceArr = phoneNums.split("\\s+");
			for (int i = 0;i<sourceArr.length;i++){
				tarList.add(sourceArr[i]);
			}

			tarList.removeAll(sucList);
			dto.putValue("list",tarList);
			dto.toJsonString();
		}
		return dto;
	}

	/**
	 * 活动延期
	 * @author yanb
	 * @created 2018年01月24日 17:41:10
	 * @param  * @param extensionActivitySn
	 * @param actEndTime
	 * @param request
	 * @return com.asura.framework.base.entity.DataTransferObject
	 */
	@RequestMapping("extensionActivity")
	@ResponseBody
	public DataTransferObject extensionActivity(String extensionActivitySn,String actEndTime,String actStartTime,HttpServletRequest request) {
		LogUtil.info(LOGGER,"【活动延期】入参活动号={},日期={}",extensionActivitySn,actEndTime);
		DataTransferObject dto = new DataTransferObject();
		if (Check.NuNStr(extensionActivitySn)) {
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("活动码不能为空");
			return dto;
		}
		if (Check.NuNStr(actEndTime)) {
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("截止时间不能为空");
			return dto;
		}
		try {
			if (!Check.NuNStr(actStartTime)) {
				//时间全部转成LONG类型的毫秒数
				long actStartTimeL = Long.parseLong(actStartTime);
				long actEndTimeL = DateUtil.formatTimestampToLong(actEndTime);
				if (actEndTimeL < actStartTimeL || actEndTimeL < System.currentTimeMillis()) {
					dto.setErrCode(DataTransferObject.ERROR);
					dto.setMsg("时间不可用");
					return dto;
				}
			}
			Date actEndTimeDate = DateUtil.parseDate(actEndTime, "yyyy-MM-dd HH:mm:ss");

			ActivityEntity activityEntity = new ActivityEntity();
			activityEntity.setActEndTime(actEndTimeDate);
			activityEntity.setActSn(extensionActivitySn);
			String resultJson = activityService.updateActivityByActSn(JsonEntityTransform.Object2Json(activityEntity));
			dto = JsonEntityTransform.json2DataTransferObject(resultJson);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "【活动延期】error:{}", e);
		}
		return dto;
	}

}