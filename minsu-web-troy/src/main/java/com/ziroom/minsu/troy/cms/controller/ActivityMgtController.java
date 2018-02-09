/**
 * @FileName: ActivityMgtController.java
 * @Package com.ziroom.minsu.troy.cms.controller
 * 
 * @author liujun
 * @created 2016年10月14日
 * 
 * Copyright 2016 ziroom
 */
package com.ziroom.minsu.troy.cms.controller;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.exception.SOAParseException;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.SOAResParseUtil;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.cms.ActivityGiftEntity;
import com.ziroom.minsu.entity.cms.ActivityGroupEntity;
import com.ziroom.minsu.entity.sys.CurrentuserEntity;
import com.ziroom.minsu.services.cms.api.inner.ActivityGiftService;
import com.ziroom.minsu.services.cms.api.inner.ActivityGroupService;
import com.ziroom.minsu.services.cms.dto.ActivityGiftRequest;
import com.ziroom.minsu.services.cms.dto.GroupRequest;
import com.ziroom.minsu.services.common.page.PageResult;
import com.ziroom.minsu.troy.common.util.UserUtil;
import com.ziroom.minsu.valenum.cms.GiftTypeEnum;
import com.ziroom.minsu.valenum.common.YesOrNoEnum;

/**
 * <p>活动后台管理</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author liujun
 * @since 1.0
 * @version 1.0
 */
@Controller
@RequestMapping("activity")
public class ActivityMgtController {

	@Resource(name ="cms.activityGiftService")
	private ActivityGiftService activityGiftService;
	
	@Resource(name ="cms.activityGroupService")
	private ActivityGroupService activityGroupService;

	/**
	 * 日志对象
	 */
	private static Logger LOGGER = LoggerFactory.getLogger(ActivityMgtController.class);
	
	/**
	 * 
	 * 活动管理-跳转活动礼品列表
	 *
	 * @author liujun
	 * @created 2016年10月14日
	 *
	 * @param request
	 */
	@RequestMapping("actGiftList")
	public void listActGift(HttpServletRequest request) {
		// 活动礼物类型
		request.setAttribute("giftTypeMap", GiftTypeEnum.getEnumMap());
		request.setAttribute("giftTypeJson", JsonEntityTransform.Object2Json(GiftTypeEnum.getEnumMap()));
	}
	
	/**
	 * 
	 * 活动管理-查询活动礼物列表
	 *
	 * @author liujun
	 * @created 2016年10月17日
	 *
	 * @param actGiftRequest
	 * @return
	 */
	@RequestMapping("showActGift")
	@ResponseBody
	public PageResult showActGift(ActivityGiftRequest actGiftRequest) {
		try {
			String resultJson = activityGiftService.queryActivityGifyByPage(JsonEntityTransform.Object2Json(actGiftRequest));
			DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(resultJson);
			// 判断调用状态
			if(dto.getCode() == DataTransferObject.ERROR){
				LogUtil.error(LOGGER, "接口调用失败,参数:{}", JsonEntityTransform.Object2Json(dto));
				return new PageResult();
			}
			
			List<ActivityGiftEntity> list = SOAResParseUtil
					.getListValueFromDataByKey(resultJson, "listActivityGift", ActivityGiftEntity.class);
			PageResult pageResult = new PageResult();
			pageResult.setRows(list);
			pageResult.setTotal(Long.valueOf(dto.getData().get("count").toString()));
			return pageResult;
		} catch (Exception e) {
			LogUtil.error(LOGGER, "showActGift error:{}", e);
			return new PageResult();
		}
	}
	
	/**
	 * 
	 * 活动管理-新增活动礼品信息
	 *
	 * @author liujun
	 * @created 2016年10月17日
	 *
	 * @param actGiftRequest
	 * @return
	 */
	@RequestMapping("addActGift")
	@ResponseBody
	public DataTransferObject addActGift(ActivityGiftEntity activityGiftEntity) {
		DataTransferObject dto = new DataTransferObject();
		try {
			CurrentuserEntity user = UserUtil.getCurrentUser();
			if (Check.NuNObj(user)) {
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("请重新登录");
			}
			
			activityGiftEntity.setCreateId(user.getEmployeeFid());
			activityGiftEntity.setCreateTime(new Date());
			String resultJson = activityGiftService.insertActivityGiftEntity(JsonEntityTransform.Object2Json(activityGiftEntity));
			dto = JsonEntityTransform.json2DataTransferObject(resultJson);
			// 判断调用状态
			if(dto.getCode() == DataTransferObject.ERROR){
				LogUtil.error(LOGGER, "接口调用失败,参数:{}", JsonEntityTransform.Object2Json(dto));
				return dto;
			}
			
			int upNum = SOAResParseUtil.getIntFromDataByKey(resultJson, "upNum");
			dto.putValue("upNum", upNum);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "addActGift error:{}", e);
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg(e.getMessage());
		}
		return dto;
	}
	
	/**
	 * 
	 * 活动管理-根据逻辑id查询活动礼品信息
	 *
	 * @author liujun
	 * @created 2016年10月17日
	 *
	 * @param actGiftRequest
	 * @return
	 */
	@RequestMapping("findActGift")
	@ResponseBody
	public DataTransferObject findActGift(String actGiftFid) {
		DataTransferObject dto = new DataTransferObject();
		try {
			String resultJson = activityGiftService.queryActivityGifyByFid(actGiftFid);
			dto = JsonEntityTransform.json2DataTransferObject(resultJson);
			// 判断调用状态
			if(dto.getCode() == DataTransferObject.ERROR){
				LogUtil.error(LOGGER, "接口调用失败,参数:{}", JsonEntityTransform.Object2Json(dto));
				return dto;
			}
			
			ActivityGiftEntity activityGiftEntity = SOAResParseUtil
					.getValueFromDataByKey(resultJson, "obj", ActivityGiftEntity.class);
			dto.putValue("obj", activityGiftEntity);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "findActGift error:{}", e);
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg(e.getMessage());
		}
		return dto;
	}
	
	/**
	 * 
	 * 活动管理-修改礼品信息
	 *
	 * @author liujun
	 * @created 2016年10月17日
	 *
	 * @param actGiftRequest
	 * @return
	 */
	@RequestMapping("editActGift")
	@ResponseBody
	public DataTransferObject editActGift(ActivityGiftEntity activityGiftEntity) {
		DataTransferObject dto = new DataTransferObject();
		try {
			String resultJson = activityGiftService.updateActivityGiftEntity(JsonEntityTransform.Object2Json(activityGiftEntity));
			dto = JsonEntityTransform.json2DataTransferObject(resultJson);
			// 判断调用状态
			if(dto.getCode() == DataTransferObject.ERROR){
				LogUtil.error(LOGGER, "接口调用失败,参数:{}", JsonEntityTransform.Object2Json(dto));
				return dto;
			}
			
			int upNum = SOAResParseUtil.getIntFromDataByKey(resultJson, "upNum");
			dto.putValue("upNum", upNum);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "editActGift error:{}", e);
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg(e.getMessage());
		}
		return dto;
	}
	
	/**
	 * 
	 * 活动管理-逻辑删除礼品信息
	 *
	 * @author liujun
	 * @created 2016年10月17日
	 *
	 * @param actGiftRequest
	 * @return
	 */
	@RequestMapping("delActGift")
	@ResponseBody
	public DataTransferObject delActGift(String actGiftFid) {
		DataTransferObject dto = new DataTransferObject();
		try {
			ActivityGiftEntity activityGiftEntity = new ActivityGiftEntity();
			activityGiftEntity.setFid(actGiftFid);
			activityGiftEntity.setIsDel(YesOrNoEnum.YES.getCode());
			activityGiftEntity.setLastModifyDate(new Date());
			String resultJson = activityGiftService.updateActivityGiftEntity(JsonEntityTransform.Object2Json(activityGiftEntity));
			dto = JsonEntityTransform.json2DataTransferObject(resultJson);
			// 判断调用状态
			if(dto.getCode() == DataTransferObject.ERROR){
				LogUtil.error(LOGGER, "接口调用失败,参数:{}", JsonEntityTransform.Object2Json(dto));
				return dto;
			}
			
			int upNum = SOAResParseUtil.getIntFromDataByKey(resultJson, "upNum");
			dto.putValue("upNum", upNum);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg(e.getMessage());
		}
		return dto;
	}
	
	/**
	 * 
	 * 活动管理-跳转活动组列表
	 *
	 * @author liujun
	 * @created 2016年10月19日
	 *
	 * @param request
	 * @return 
	 */
	@RequestMapping("actGroupList")
	public void listActGroup(HttpServletRequest request) {
	}

	/**
	 * 
	 * 活动管理-查询活动组列表
	 *
	 * @author liujun
	 * @created 2016年10月19日
	 *
	 * @param actGiftRequest
	 * @return
	 */
	@RequestMapping("showActGroup")
	@ResponseBody
	public PageResult showActGroup(GroupRequest groupRequest) {
		try {
			String resultJson = activityGroupService.getGroupByPage(JsonEntityTransform.Object2Json(groupRequest));
			DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(resultJson);
			// 判断调用状态
			if(dto.getCode() == DataTransferObject.ERROR){
				LogUtil.error(LOGGER, "接口调用失败,参数:{}", JsonEntityTransform.Object2Json(dto));
				return new PageResult();
			}
			
			List<ActivityGroupEntity> list = SOAResParseUtil
					.getListValueFromDataByKey(resultJson, "list", ActivityGroupEntity.class);
			PageResult pageResult = new PageResult();
			pageResult.setRows(list);
			pageResult.setTotal(Long.valueOf(dto.getData().get("total").toString()));
			return pageResult;
		} catch (Exception e) {
			LogUtil.error(LOGGER, "showActGroup error:{}", e);
			return new PageResult();
		}
	}
	
	/**
	 * 
	 * 跳转活动组添加页
	 *
	 * @author bushujie
	 * @created 2017年5月25日 下午5:07:22
	 *
	 * @param request
	 */
	@RequestMapping("actGroupAdd")
	public void actGroupAdd(HttpServletRequest request){
		
	}
	/**
	 * 
	 * 活动管理-校验活动组编号是否唯一
	 *
	 * @author liujun
	 * @created 2016年10月19日
	 *
	 * @param activityGroupEntity
	 * @return
	 */
	@RequestMapping("validateGroupSn")
	@ResponseBody
	public DataTransferObject validateGroupSn(String groupSn) {
		DataTransferObject dto = new DataTransferObject();
		try {
			if (Check.NuNStr(groupSn)) {
				dto.setMsg("活动组编号不能为空");
				return dto;
			}
			
			boolean isUnique = this.isGroupSnUnique(groupSn);
			dto.putValue("isUnique", isUnique);
			if (!isUnique) {
				dto.setMsg("活动组编号已存在");
			}
		} catch (Exception e) {
			LogUtil.error(LOGGER, "addActGift error:{}", e);
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg(e.getMessage());
		}
		return dto;
	}
	
	/**
	 * 校验活动组编号是否唯一
	 *
	 * @author liujun
	 * @created 2016年10月19日
	 *
	 * @param groupSn
	 * @return 
	 * @throws SOAParseException 
	 */
	private boolean isGroupSnUnique(String groupSn) throws SOAParseException {
		ActivityGroupEntity activityGroupEntity = this.getActivityGroupEntityBySn(groupSn);
		boolean isUnique = false;
		if(Check.NuNObj(activityGroupEntity)){
			isUnique = true;
		}
		return isUnique;
	}

	/**
	 * 根据活动组编号获取互动组信息
	 *
	 * @author liujun
	 * @created 2016年10月19日
	 *
	 * @param groupSn
	 * @return
	 * @throws SOAParseException 
	 */
	private ActivityGroupEntity getActivityGroupEntityBySn(String groupSn) throws SOAParseException {
		String resultJson = activityGroupService.getGroupBySN(groupSn);
		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(resultJson);
		// 判断调用状态
		if(dto.getCode() == DataTransferObject.ERROR){
			LogUtil.error(LOGGER, "接口调用失败,参数:{}", JsonEntityTransform.Object2Json(dto));
			throw new BusinessException();
		}
		ActivityGroupEntity activityGroupEntity = SOAResParseUtil
				.getValueFromDataByKey(resultJson, "obj", ActivityGroupEntity.class);
		return activityGroupEntity;
	}

	/**
	 * 
	 * 活动管理-新增活动组信息
	 *
	 * @author liujun
	 * @created 2016年10月19日
	 *
	 * @param activityGiftEntity
	 * @return
	 */
	@RequestMapping("addActGroup")
	@ResponseBody
	public DataTransferObject addActGroup(ActivityGroupEntity activityGroupEntity) {
		DataTransferObject dto = new DataTransferObject();
		try {
			CurrentuserEntity user = UserUtil.getCurrentUser();
			if (Check.NuNObj(user)) {
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("请重新登录");
			}
			
			if (Check.NuNStr(activityGroupEntity.getGroupSn())) {
				LogUtil.info(LOGGER, "接口调用失败,参数:{}", JsonEntityTransform.Object2Json(dto));
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("活动组编号不能为空");
				return dto;
			}
			
			boolean isUnique = this.isGroupSnUnique(activityGroupEntity.getGroupSn());
			if (!isUnique) {
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("活动组编号已存在");
				return dto;
			}
			
			activityGroupEntity.setCreateId(user.getEmployeeFid());
			activityGroupEntity.setCreateTime(new Date());
			String resultJson = activityGroupService.insertActivityGroupEntity(JsonEntityTransform.Object2Json(activityGroupEntity));
			dto = JsonEntityTransform.json2DataTransferObject(resultJson);
			// 判断调用状态
			if(dto.getCode() == DataTransferObject.ERROR){
				LogUtil.error(LOGGER, "接口调用失败,参数:{}", JsonEntityTransform.Object2Json(dto));
				return dto;
			}
			
			int upNum = SOAResParseUtil.getIntFromDataByKey(resultJson, "upNum");
			dto.putValue("upNum", upNum);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "addActGroup error:{}", e);
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg(e.getMessage());
		}
		return dto;
	}
	
	/**
	 * 
	 * 活动管理-根据活动组编号查询活动组信息
	 *
	 *
	 * @author liujun
	 * @created 2016年10月19日
	 *
	 * @param groupSn
	 * @return
	 */
	@RequestMapping("findActGroup")
	@ResponseBody
	public DataTransferObject findActGroup(String groupSn) {
		DataTransferObject dto = new DataTransferObject();
		try {
			ActivityGroupEntity activityGroupEntity = this.getActivityGroupEntityBySn(groupSn);
			dto.putValue("obj", activityGroupEntity);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "findActGroup error:{}", e);
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg(e.getMessage());
		}
		return dto;
	}
	
	/**
	 * 
	 * 活动管理-修改活动组信息
	 *
	 * @author liujun
	 * @created 2016年10月19日
	 *
	 * @param activityGiftEntity
	 * @return
	 */
	@ResponseBody
	@RequestMapping("editActGroup")
	public DataTransferObject editActGroup(ActivityGroupEntity activityGroupEntity) {
		DataTransferObject dto = new DataTransferObject();
		try {
			String resultJson = activityGroupService.updateActivityGroupEntity(JsonEntityTransform.Object2Json(activityGroupEntity));
			dto = JsonEntityTransform.json2DataTransferObject(resultJson);
			// 判断调用状态
			if(dto.getCode() == DataTransferObject.ERROR){
				LogUtil.error(LOGGER, "接口调用失败,参数:{}", JsonEntityTransform.Object2Json(dto));
				return dto;
			}
			
			int upNum = SOAResParseUtil.getIntFromDataByKey(resultJson, "upNum");
			dto.putValue("upNum", upNum);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "editActGroup error:{}", e);
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg(e.getMessage());
		}
		return dto;
	}

	/**
	 * 
	 * 活动管理-逻辑删除活动组信息
	 *
	 * @author liujun
	 * @created 2016年10月19日
	 *
	 * @param actGiftRequest
	 * @return
	 */
	@RequestMapping("delActGroup")
	@ResponseBody
	public DataTransferObject delActGroup(String actGroupFid) {
		DataTransferObject dto = new DataTransferObject();
		try {
			ActivityGroupEntity activityGroupEntity = new ActivityGroupEntity();
			activityGroupEntity.setFid(actGroupFid);
			activityGroupEntity.setIsDel(YesOrNoEnum.YES.getCode());
			activityGroupEntity.setLastModifyDate(new Date());
			String resultJson = activityGroupService.updateActivityGroupEntity(JsonEntityTransform.Object2Json(activityGroupEntity));
			dto = JsonEntityTransform.json2DataTransferObject(resultJson);
			// 判断调用状态
			if(dto.getCode() == DataTransferObject.ERROR){
				LogUtil.error(LOGGER, "接口调用失败,参数:{}", JsonEntityTransform.Object2Json(dto));
				return dto;
			}
			
			int upNum = SOAResParseUtil.getIntFromDataByKey(resultJson, "upNum");
			dto.putValue("upNum", upNum);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "delActGroup error:{}", e);
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg(e.getMessage());
		}
		return dto;
	}

}
