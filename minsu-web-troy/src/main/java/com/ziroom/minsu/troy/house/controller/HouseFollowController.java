/**
 * @FileName: HouseFollowController.java
 * @Package com.ziroom.minsu.troy.house.controller
 * 
 * @author bushujie
 * @created 2017年2月24日 上午11:27:37
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.troy.house.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.exception.SOAParseException;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.DateUtil;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.SOAResParseUtil;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.conf.ConfCityEntity;
import com.ziroom.minsu.entity.customer.CustomerBaseMsgEntity;
import com.ziroom.minsu.services.basedata.api.inner.CityTemplateService;
import com.ziroom.minsu.services.basedata.api.inner.ConfCityService;
import com.ziroom.minsu.services.basedata.api.inner.SmsTemplateService;
import com.ziroom.minsu.services.basedata.dto.SmsRequest;
import com.ziroom.minsu.services.basedata.entity.TreeNodeVo;
import com.ziroom.minsu.services.common.utils.ValueUtil;
import com.ziroom.minsu.services.customer.api.inner.CustomerInfoService;
import com.ziroom.minsu.services.customer.dto.CustomerBaseMsgDto;
import com.ziroom.minsu.services.house.api.inner.HouseFollowService;
import com.ziroom.minsu.services.house.constant.HouseConstant;
import com.ziroom.minsu.services.house.dto.HouseFollowDto;
import com.ziroom.minsu.services.house.dto.HouseFollowLogDto;
import com.ziroom.minsu.services.house.dto.HouseFollowSaveDto;
import com.ziroom.minsu.services.house.dto.HouseFollowUpdateDto;
import com.ziroom.minsu.services.house.entity.HouseFollowVo;
import com.ziroom.minsu.troy.common.util.UserUtil;
import com.ziroom.minsu.valenum.house.FollowStatusEnum;
import com.ziroom.minsu.valenum.house.HouseLockEnum;
import com.ziroom.minsu.valenum.house.HouseStatusEnum;
import com.ziroom.minsu.valenum.houseaudit.HouseAuditCauseEnum;
import com.ziroom.minsu.valenum.houseaudit.HouseAuditEnum005;
import com.ziroom.minsu.valenum.msg.MessageTemplateCodeEnum;


/**
 * <p>房源跟进</p>
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
@Controller
@RequestMapping("house/houseFollow")
public class HouseFollowController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(HouseFollowController.class);
	
	@Resource(name="customer.customerInfoService")
	private CustomerInfoService customerInfoService;
	
	@Resource(name = "basedata.confCityService")
	private ConfCityService confCityService;
	
	@Resource(name="house.houseFollowService")
	private HouseFollowService houseFollowService;
	
	@Resource(name="basedata.cityTemplateService")
	private CityTemplateService cityTemplateService;
	
	@Resource(name="basedata.smsTemplateService")
	private SmsTemplateService smsTemplateService;
	
	/**
	 * 
	 * 跳转到客服跟进页
	 *
	 * @author bushujie
	 * @throws SOAParseException 
	 * @created 2017年2月24日 下午2:19:13
	 *
	 */
	@RequestMapping("serviceFollowList")
	public void serviceFollowList(HttpServletRequest request) throws SOAParseException{
		String resultJson=confCityService.getConfCitySelect();
		List<TreeNodeVo> cityTreeList=SOAResParseUtil.getListValueFromDataByKey(resultJson, "list", TreeNodeVo.class);
		request.setAttribute("cityTreeList", JsonEntityTransform.Object2Json(cityTreeList));
	}
	
	/**
	 * 
	 * 客服跟进列表数据
	 *
	 * @author bushujie
	 * @created 2017年2月24日 下午2:25:32
	 *
	 * @param houseFollowDto
	 * @return
	 * @throws SOAParseException 
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("serviceFollowData")
	@ResponseBody
	public String serviceFollowData(HouseFollowDto houseFollowDto) throws SOAParseException{
		//查询客服多少小时后开始跟进
		String resultJson=cityTemplateService.getTextValue(null,HouseAuditEnum005.HouseAuditEnum001.getValue());
		String value=SOAResParseUtil.getStrFromDataByKey(resultJson,"textValue");
		Integer interval=HouseConstant.SERVICE_FOLLOW_WAIT_INT;
		if(!Check.NuNStr(value)){
			interval=Integer.valueOf(value);
		}
		houseFollowDto.setBeforeDate(DateUtil.dateFormat(DateUtils.addHours(new Date(), -interval), "yyyy-MM-dd HH:mm:ss"));
		//查询专员多少小时后开始跟进
		String attacheJson=cityTemplateService.getTextValue(null,HouseAuditEnum005.HouseAuditEnum002.getValue());
		String attacheValue=SOAResParseUtil.getStrFromDataByKey(attacheJson,"textValue");
		Integer attacheInterval=HouseConstant.ATTACHE_FOLLOW_WAIT_INT;
		if(!Check.NuNStr(attacheValue)){
			attacheInterval=Integer.valueOf(attacheValue);
		}
		houseFollowDto.setAttacheStartDate(DateUtil.dateFormat(DateUtils.addHours(new Date(), -attacheInterval), "yyyy-MM-dd HH:mm:ss"));
		houseFollowDto.setStartDate(HouseConstant.HOUSE_FOLLOW_START_TIME);
		//有房东姓名和电话搜索条件时
		if(!Check.NuNStr(houseFollowDto.getLandlordMobile())||!Check.NuNStr(houseFollowDto.getLandlordName())){
			CustomerBaseMsgDto customerBaseDto=new CustomerBaseMsgDto();
			customerBaseDto.setRealName(houseFollowDto.getLandlordName());
			customerBaseDto.setCustomerMobile(houseFollowDto.getLandlordMobile());
			String customerJson=customerInfoService.selectByCondition(JsonEntityTransform.Object2Json(customerBaseDto));
			List<CustomerBaseMsgEntity> listCustomerBaseMsg=SOAResParseUtil.getListValueFromDataByKey(customerJson, "listCustomerBaseMsg", CustomerBaseMsgEntity.class);
			StringBuilder sBuilder=new StringBuilder();
			if(!Check.NuNCollection(listCustomerBaseMsg)){
				for(int i=0;i<listCustomerBaseMsg.size();i++){
					if(i==0){
						sBuilder.append("'").append(listCustomerBaseMsg.get(i).getUid()).append("'");
					} else {
						sBuilder.append(",'").append(listCustomerBaseMsg.get(i).getUid()).append("'");
					}
				}
				houseFollowDto.setUidStr(sBuilder.toString());
			}
		}
		if(!Check.NuNObj(houseFollowDto.getIsNotLock())&&houseFollowDto.getIsNotLock()==1){
			houseFollowDto.setOperateFid(UserUtil.getCurrentUserFid());
			houseFollowDto.setOperateDate(DateUtil.dateFormat(new Date(), "yyyyMMddHHmm"));
		}
		LogUtil.info(LOGGER, "serviceFollowData 参数：", JsonEntityTransform.Object2Json(houseFollowDto));
		String followJson=houseFollowService.findServicerFollowHouseList(JsonEntityTransform.Object2Json(houseFollowDto));
		List<HouseFollowVo> dataList=SOAResParseUtil.getListValueFromDataByKey(followJson, "dataList", HouseFollowVo.class);
		//获取开通城市
		String cityJson = confCityService.getOpenCityMap();
		Map<String, String> cityMap=SOAResParseUtil.getValueFromDataByKey(cityJson, "map", Map.class);
		for(HouseFollowVo vo:dataList){
			//城市赋值
			vo.setCityName(cityMap.get(vo.getCityCode()));
			//房东信息赋值
			String customerJson=customerInfoService.getCustomerInfoByUid(vo.getLandlordUid());
			CustomerBaseMsgEntity customerBaseMsgEntity=SOAResParseUtil.getValueFromDataByKey(customerJson, "customerBase", CustomerBaseMsgEntity.class);
			vo.setLandlordName(customerBaseMsgEntity.getRealName());
			vo.setLandordMobile(customerBaseMsgEntity.getCustomerMobile());
			//跟进状态赋值
			if(Check.NuNObj(vo.getFollowStatus())||vo.getFollowStatus()==FollowStatusEnum.KFDGJ.getCode()){
				vo.setFollowStatusStr(FollowStatusEnum.KFDGJ.getName());
			} else {
				vo.setFollowStatusStr(FollowStatusEnum.getEnumMap().get(vo.getFollowStatus()));
			}
			//房源状态赋值
			vo.setHouseStatusStr(HouseStatusEnum.getEnumMap().get(vo.getHouseStatus()));
			//未通过原因赋值
			StringBuilder sb=new StringBuilder();
			Map<Integer, String> auditMap=HouseAuditCauseEnum.getEnumMap();
			if(!Check.NuNStr(vo.getAuditCause())){
				for(int i=0;i<vo.getAuditCause().split(",").length;i++){
					if(i==0){
						sb.append(auditMap.get(Integer.valueOf(vo.getAuditCause().split(",")[i])));
					} else {
						sb.append(",").append(auditMap.get(Integer.valueOf(vo.getAuditCause().split(",")[i])));
					}
				}
				vo.setAuditCause(sb.toString());
			}
		}
		Long size=SOAResParseUtil.getLongFromDataByKey(followJson, "count");
		JSONObject result = new JSONObject();
		Long totalpages = 0L;
		if(!Check.NuNObj(size)){
			if(size % houseFollowDto.getLimit() == 0){
				totalpages = size/houseFollowDto.getLimit();
			}else {
				totalpages = size/houseFollowDto.getLimit() + 1;
			}
		}
		result.put("totalpages", totalpages);
		result.put("currPage", houseFollowDto.getPage());
		result.put("totalCount", 0);
		result.put("dataList", dataList);
		return result.toString();
	}
	
	/**
	 * 
	 * 异步客服锁并且未没有主跟进记录表添加记录
	 *
	 * @author bushujie
	 * @created 2017年2月25日 下午1:53:40
	 *
	 * @param followFid
	 * @param houseSn
	 * @param rentWay
	 * @return
	 */
	@RequestMapping("lockAndSaveHouseFollow")
	@ResponseBody
	public DataTransferObject lockAndSaveHouseFollow(HouseFollowSaveDto houseFollowSaveDto){
		DataTransferObject dto=new DataTransferObject();
		houseFollowSaveDto.setCreateFid(UserUtil.getCurrentUserFid());
	    String resultJson=houseFollowService.lockAndSaveHouseFollow(JsonEntityTransform.Object2Json(houseFollowSaveDto));
		dto=JsonEntityTransform.json2DataTransferObject(resultJson);
		if(!Check.NuNObj(dto.getData().get("lockerFid"))){
			dto.setErrCode(1);
			dto.setMsg("跟进记录被锁定！");
		}
		return dto;
	}
	
	/**
	 * 
	 * 房源跟进记录详情
	 *
	 * @author bushujie
	 * @created 2017年2月27日 上午11:31:58
	 *
	 * @param houseFollowSaveDto
	 * @throws SOAParseException 
	 */
	@RequestMapping("houseFollowDetail")
	public void houseFollowDetail(HouseFollowSaveDto houseFollowSaveDto,HttpServletRequest request) throws SOAParseException{
		String resultJson=houseFollowService.houseFollowDetail(JsonEntityTransform.Object2Json(houseFollowSaveDto));
		HouseFollowVo houseFollowVo=SOAResParseUtil.getValueFromDataByKey(resultJson, "followDetail", HouseFollowVo.class);
		//获取城市名称
		String cityJson=confCityService.getConfCityByCode(houseFollowVo.getCityCode());
		ConfCityEntity confCityEntity=SOAResParseUtil.getValueFromDataByKey(cityJson, "cityEntity", ConfCityEntity.class);
		houseFollowVo.setCityName(confCityEntity.getShowName());
		//房东信息赋值
		String customerJson=customerInfoService.getCustomerInfoByUid(houseFollowVo.getLandlordUid());
		CustomerBaseMsgEntity customerBaseMsgEntity=SOAResParseUtil.getValueFromDataByKey(customerJson, "customerBase", CustomerBaseMsgEntity.class);
		houseFollowVo.setLandlordName(customerBaseMsgEntity.getRealName());
		houseFollowVo.setLandordMobile(customerBaseMsgEntity.getCustomerMobile());
		//跟进状态赋值
		if(Check.NuNObj(houseFollowVo.getFollowStatus())||houseFollowVo.getFollowStatus()==FollowStatusEnum.KFDGJ.getCode()){
			houseFollowVo.setFollowStatusStr(FollowStatusEnum.KFDGJ.getName());
		} else {
			houseFollowVo.setFollowStatusStr(FollowStatusEnum.getEnumMap().get(houseFollowVo.getFollowStatus()));
		}
		//未通过原因赋值
		StringBuilder sb=new StringBuilder();
		Map<Integer, String> auditMap=HouseAuditCauseEnum.getEnumMap();
		if(!Check.NuNStr(houseFollowVo.getAuditCause())){
			for(int i=0;i<houseFollowVo.getAuditCause().split(",").length;i++){
				if(i==0){
					sb.append(auditMap.get(Integer.valueOf(houseFollowVo.getAuditCause().split(",")[i])));
				} else {
					sb.append(",").append(auditMap.get(Integer.valueOf(houseFollowVo.getAuditCause().split(",")[i])));
				}
			}
			houseFollowVo.setAuditCause(sb.toString());
		}
		request.setAttribute("houseFollowVo", houseFollowVo);
	}
	
	/**
	 * 
	 * 保存房源跟进记录详情
	 *
	 * @author bushujie
	 * @created 2017年2月27日 下午4:57:47
	 *
	 * @param houseFollowLogEntity
	 * @return
	 */
	@RequestMapping("saveHouseFollowLog")
	@ResponseBody
	public DataTransferObject saveHouseFollowLog(HouseFollowLogDto houseFollowLogDto,String notLandlord){
		if(notLandlord.equals("false")){
			houseFollowLogDto.setToStatus(FollowStatusEnum.KFGJZ.getCode());
		} else if(notLandlord.equals("true")) {
			houseFollowLogDto.setToStatus(FollowStatusEnum.KFWLXSFD.getCode());
			//发短信给房东
			try {
				String customerJson=customerInfoService.getCustomerInfoByUid(houseFollowLogDto.getLandlordUid());
				CustomerBaseMsgEntity customer=SOAResParseUtil.getValueFromDataByKey(customerJson, "customerBase", CustomerBaseMsgEntity.class);
				SmsRequest smsRequest = new SmsRequest();
				smsRequest.setMobile(customer.getCustomerMobile());
				Map<String, String> paramsMap=new HashMap<String, String>();
				smsRequest.setParamsMap(paramsMap);
				String msgCode = ValueUtil.getStrValue(MessageTemplateCodeEnum.HOUSE_QA_AUDIT_FAIL_NOT_CONTACT_LANDLORD_SMS.getCode());
				smsRequest.setSmsCode(String.valueOf(msgCode));
				smsTemplateService.sendSmsByCode(JsonEntityTransform.Object2Json(smsRequest));
			} catch (Exception e) {
				LogUtil.error(LOGGER,"saveHouseFollowLog 发短信error:{}",e);
			}
		}
		houseFollowLogDto.setFollowUserFid(UserUtil.getCurrentUserFid());
		houseFollowLogDto.setFollowEmpCode(UserUtil.getFullCurrentUser().getEmpCode());
		houseFollowLogDto.setFollowEmpName(UserUtil.getFullCurrentUser().getFullName());
		houseFollowLogDto.setFid(UUIDGenerator.hexUUID());
		houseFollowLogDto.setHouseLockCode(HouseLockEnum.KFGJWSHTGFY.getCode()+"");
		String resultJson=houseFollowService.insertFollowLog(JsonEntityTransform.Object2Json(houseFollowLogDto));
		return JsonEntityTransform.json2DataTransferObject(resultJson);
	}
	
	/**
	 * 
	 * 客服跟进结束
	 *
	 * @author yd
	 * @created 2017年4月17日 下午9:20:37
	 *
	 * @return
	 */
	@RequestMapping("updateHouseFollow")
	@ResponseBody
	public DataTransferObject updateHouseFollow(HouseFollowUpdateDto houseFollowUpdateDto){
		
		HouseFollowLogDto houseFollowLogDto = houseFollowUpdateDto.getHouseFollowLogDto();
		DataTransferObject dto =  null;
		if(Check.NuNStr(houseFollowLogDto.getFollowDesc())){
			
			dto = new DataTransferObject();
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("请填写备注");
			return dto;
		}
		houseFollowLogDto.setFollowUserFid(UserUtil.getCurrentUserFid());
		houseFollowLogDto.setFollowEmpCode(UserUtil.getFullCurrentUser().getEmpCode());
		houseFollowLogDto.setFollowEmpName(UserUtil.getFullCurrentUser().getFullName());
		houseFollowLogDto.setFid(UUIDGenerator.hexUUID());
		houseFollowLogDto.setHouseLockCode(HouseLockEnum.KFGJWSHTGFY.getCode()+"");
		
		houseFollowUpdateDto.setHouseFollowLogDto(houseFollowLogDto);
		houseFollowUpdateDto.setFollowStatus(FollowStatusEnum.KFGJJS.getCode());
		 dto = JsonEntityTransform.json2DataTransferObject(this.houseFollowService.updateHouseFollowByFid(JsonEntityTransform.Object2Json(houseFollowUpdateDto)));
		return dto;
	}
	/**
	 * 
	 * 跳转专员房源跟进页
	 *
	 * @author bushujie
	 * @throws SOAParseException 
	 * @created 2017年2月28日 下午2:29:20
	 *
	 */
	@RequestMapping("attacheFollowList")
	public void attacheFollowList(HttpServletRequest request) throws SOAParseException{
		String resultJson=confCityService.getConfCitySelect();
		List<TreeNodeVo> cityTreeList=SOAResParseUtil.getListValueFromDataByKey(resultJson, "list", TreeNodeVo.class);
		request.setAttribute("causeMap", HouseAuditCauseEnum.getValidEnumMap());
		request.setAttribute("cityTreeList", JsonEntityTransform.Object2Json(cityTreeList));
	}
	
	/**
	 * 
	 * 运营专员房源跟进列表数据
	 *
	 * @author bushujie
	 * @created 2017年2月28日 下午2:58:29
	 *
	 * @param houseFollowDto
	 * @return
	 * @throws SOAParseException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("attacheFollowData")
	@ResponseBody
	public String attacheFollowData(HouseFollowDto houseFollowDto) throws SOAParseException{
		//查询专员多少小时后开始跟进
		String resultJson=cityTemplateService.getTextValue(null,HouseAuditEnum005.HouseAuditEnum002.getValue());
		String value=SOAResParseUtil.getStrFromDataByKey(resultJson,"textValue");
		Integer interval=HouseConstant.ATTACHE_FOLLOW_WAIT_INT;
		if(!Check.NuNStr(value)){
			interval=Integer.valueOf(value);
		}
		houseFollowDto.setAttacheStartDate(DateUtil.dateFormat(DateUtils.addHours(new Date(), -interval), "yyyy-MM-dd HH:mm:ss"));
		//有房东姓名和电话搜索条件时
		if(!Check.NuNStr(houseFollowDto.getLandlordMobile())||!Check.NuNStr(houseFollowDto.getLandlordName())){
			CustomerBaseMsgDto customerBaseDto=new CustomerBaseMsgDto();
			customerBaseDto.setRealName(houseFollowDto.getLandlordName());
			customerBaseDto.setCustomerMobile(houseFollowDto.getLandlordMobile());
			String customerJson=customerInfoService.selectByCondition(JsonEntityTransform.Object2Json(customerBaseDto));
			List<CustomerBaseMsgEntity> listCustomerBaseMsg=SOAResParseUtil.getListValueFromDataByKey(customerJson, "listCustomerBaseMsg", CustomerBaseMsgEntity.class);
			StringBuilder sBuilder=new StringBuilder();
			if(!Check.NuNCollection(listCustomerBaseMsg)){
				for(int i=0;i<listCustomerBaseMsg.size();i++){
					if(i==0){
						sBuilder.append("'").append(listCustomerBaseMsg.get(i).getUid()).append("'");
					} else {
						sBuilder.append(",'").append(listCustomerBaseMsg.get(i).getUid()).append("'");
					}
				}
				houseFollowDto.setUidStr(sBuilder.toString());
			}
		}
		if(!Check.NuNObj(houseFollowDto.getIsNotLock())&&houseFollowDto.getIsNotLock()==1){
			houseFollowDto.setOperateFid(UserUtil.getCurrentUserFid());
			houseFollowDto.setOperateDate(DateUtil.dateFormat(new Date(), "yyyyMMddHHmm"));
		}
		LogUtil.info(LOGGER, "attacheFollowData 参数：", JsonEntityTransform.Object2Json(houseFollowDto));
		
		String followJson=houseFollowService.findAttacheFollowHouseList(JsonEntityTransform.Object2Json(houseFollowDto));
		List<HouseFollowVo> dataList=SOAResParseUtil.getListValueFromDataByKey(followJson, "dataList", HouseFollowVo.class);
		//获取开通城市
		String cityJson = confCityService.getOpenCityMap();
		Map<String, String> cityMap=SOAResParseUtil.getValueFromDataByKey(cityJson, "map", Map.class);
		for(HouseFollowVo vo:dataList){
			//城市赋值
			vo.setCityName(cityMap.get(vo.getCityCode()));
			//房东信息赋值
			String customerJson=customerInfoService.getCustomerInfoByUid(vo.getLandlordUid());
			CustomerBaseMsgEntity customerBaseMsgEntity=SOAResParseUtil.getValueFromDataByKey(customerJson, "customerBase", CustomerBaseMsgEntity.class);
			vo.setLandlordName(customerBaseMsgEntity.getRealName());
			vo.setLandordMobile(customerBaseMsgEntity.getCustomerMobile());
			//跟进状态赋值
			if(Check.NuNObj(vo.getFollowStatus())||vo.getFollowStatus()==FollowStatusEnum.KFDGJ.getCode()){
				vo.setFollowStatusStr(FollowStatusEnum.KFDGJ.getName());
			} else {
				vo.setFollowStatusStr(FollowStatusEnum.getEnumMap().get(vo.getFollowStatus()));
			}
			//房源状态赋值
			vo.setHouseStatusStr(HouseStatusEnum.getEnumMap().get(vo.getHouseStatus()));
			//未通过原因赋值
			StringBuilder sb=new StringBuilder();
			Map<Integer, String> auditMap=HouseAuditCauseEnum.getEnumMap();
			if(!Check.NuNStr(vo.getAuditCause())){
				for(int i=0;i<vo.getAuditCause().split(",").length;i++){
					if(i==0){
						sb.append(auditMap.get(Integer.valueOf(vo.getAuditCause().split(",")[i])));
					} else {
						sb.append(",").append(auditMap.get(Integer.valueOf(vo.getAuditCause().split(",")[i])));
					}
				}
				vo.setAuditCause(sb.toString());
			}
		}
		Long size=SOAResParseUtil.getLongFromDataByKey(followJson, "count");
		JSONObject result = new JSONObject();
		Long totalpages = 0L;
		if(!Check.NuNObj(size)){
			if(size % houseFollowDto.getLimit() == 0){
				totalpages = size/houseFollowDto.getLimit();
			}else {
				totalpages = size/houseFollowDto.getLimit() + 1;
			}
		}
		result.put("totalpages", totalpages);
		result.put("currPage", houseFollowDto.getPage());
		result.put("totalCount", 0);
		result.put("dataList", dataList);
		return result.toString();
	}
	
	/**
	 * 
	 * 房源专员跟进详情
	 *
	 * @author bushujie
	 * @created 2017年2月28日 下午4:34:16
	 *
	 * @param houseFollowSaveDto
	 * @param request
	 * @throws SOAParseException
	 */
	@RequestMapping("attacheFollowDetail")
	public void attacheFollowDetail(HouseFollowSaveDto houseFollowSaveDto,HttpServletRequest request) throws SOAParseException{
		String resultJson=houseFollowService.houseFollowDetail(JsonEntityTransform.Object2Json(houseFollowSaveDto));
		HouseFollowVo houseFollowVo=SOAResParseUtil.getValueFromDataByKey(resultJson, "followDetail", HouseFollowVo.class);
		//获取城市名称
		String cityJson=confCityService.getConfCityByCode(houseFollowVo.getCityCode());
		ConfCityEntity confCityEntity=SOAResParseUtil.getValueFromDataByKey(cityJson, "cityEntity", ConfCityEntity.class);
		houseFollowVo.setCityName(confCityEntity.getShowName());
		//房东信息赋值
		String customerJson=customerInfoService.getCustomerInfoByUid(houseFollowVo.getLandlordUid());
		CustomerBaseMsgEntity customerBaseMsgEntity=SOAResParseUtil.getValueFromDataByKey(customerJson, "customerBase", CustomerBaseMsgEntity.class);
		houseFollowVo.setLandlordName(customerBaseMsgEntity.getRealName());
		houseFollowVo.setLandordMobile(customerBaseMsgEntity.getCustomerMobile());
		//跟进状态赋值
	    houseFollowVo.setFollowStatusStr(FollowStatusEnum.getEnumMap().get(houseFollowVo.getFollowStatus()));
		//未通过原因赋值
		StringBuilder sb=new StringBuilder();
		Map<Integer, String> auditMap=HouseAuditCauseEnum.getEnumMap();
		if(!Check.NuNStr(houseFollowVo.getAuditCause())){
			for(int i=0;i<houseFollowVo.getAuditCause().split(",").length;i++){
				if(i==0){
					sb.append(auditMap.get(Integer.valueOf(houseFollowVo.getAuditCause().split(",")[i])));
				} else {
					sb.append(",").append(auditMap.get(Integer.valueOf(houseFollowVo.getAuditCause().split(",")[i])));
				}
			}
			houseFollowVo.setAuditCause(sb.toString());
		}
		request.setAttribute("houseFollowVo", houseFollowVo);
	}
	
	/**
	 * 
	 * 运营专员跟进保存房源跟进明细
	 *
	 * @author bushujie
	 * @created 2017年2月28日 下午5:14:21
	 *
	 * @param houseFollowLogEntity
	 * @return
	 */
	@RequestMapping("saveAttacheFollowLog")
	@ResponseBody
	public DataTransferObject saveAttacheFollowLog(HouseFollowLogDto houseFollowLogDto){
		if (Check.NuNObj(houseFollowLogDto.getFollowEndCause())) {
			houseFollowLogDto.setToStatus(FollowStatusEnum.ZYGJZ.getCode());
		} else {
			houseFollowLogDto.setToStatus(FollowStatusEnum.ZYGJJS.getCode());
		}
		houseFollowLogDto.setFollowUserFid(UserUtil.getCurrentUserFid());
		houseFollowLogDto.setFollowEmpCode(UserUtil.getFullCurrentUser().getEmpCode());
		houseFollowLogDto.setFollowEmpName(UserUtil.getFullCurrentUser().getFullName());
		houseFollowLogDto.setFid(UUIDGenerator.hexUUID());
		houseFollowLogDto.setHouseLockCode(HouseLockEnum.ZYGJWSHTGFY.getCode()+"");
		String resultJson=houseFollowService.insertFollowLog(JsonEntityTransform.Object2Json(houseFollowLogDto));
		return JsonEntityTransform.json2DataTransferObject(resultJson);
	}
}
