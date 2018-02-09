package com.ziroom.minsu.troy.cms.controller;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.DateUtil;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.cms.*;
import com.ziroom.minsu.entity.customer.CustomerRoleBaseEntity;
import com.ziroom.minsu.services.basedata.api.inner.ConfCityService;
import com.ziroom.minsu.services.basedata.entity.entityenum.ServiceLineEnum;
import com.ziroom.minsu.services.cms.api.inner.ActivityFullService;
import com.ziroom.minsu.services.cms.api.inner.ActivityGiftService;
import com.ziroom.minsu.services.cms.api.inner.ActivityService;
import com.ziroom.minsu.services.cms.dto.ActivityGiftInfoRequest;
import com.ziroom.minsu.services.cms.dto.ActivityGiftRequest;
import com.ziroom.minsu.services.cms.entity.AcGiftItemVo;
import com.ziroom.minsu.services.customer.api.inner.CustomerRoleService;
import com.ziroom.minsu.troy.cms.service.CityService;
import com.ziroom.minsu.troy.cms.service.GroupService;
import com.ziroom.minsu.troy.common.util.UserUtil;
import com.ziroom.minsu.valenum.cms.ActivityTypeEnum;
import com.ziroom.minsu.valenum.cms.GroupTypeEnum;
import com.ziroom.minsu.valenum.msg.IsDelEnum;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>cms活动相关</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * @author lishaochuan on 2016年6月26日
 * @since 1.0
 * @version 1.0
 */
@Controller
@RequestMapping("normalActivity/")
public class NormalActivityController {


	/**
	 * 日志对象
	 */
	private static Logger LOGGER = LoggerFactory.getLogger(NormalActivityController.class);

	@Resource(name="basedata.confCityService")
	private ConfCityService confCityService;

	@Resource(name="cms.activityService")
	private ActivityService activityService;

	@Resource(name = "customer.customerRoleService")
	private CustomerRoleService customerRoleService;

	@Resource(name="cms.activityFullService")
	private ActivityFullService activityFullService;

	@Resource(name="api.cityService")
	private CityService cityService;

	@Resource(name="api.groupService")
	private GroupService groupService;

	@Resource(name = "cms.groupService")
	private com.ziroom.minsu.services.cms.api.inner.GroupService cmsgroupService;


	@Resource(name = "cms.activityGiftService")
	private ActivityGiftService activityGiftService;
	
	/**
	 * 跳转保存活动页
	 * @author lishaochuan
	 * @create 2016年6月24日下午5:21:59
	 * @param request
	 * @return
	 */
	@RequestMapping("toSaveActivity")
	public ModelAndView toSaveActivity(HttpServletRequest request){

		ModelAndView mv = new ModelAndView("/activity/normalActivitySave");

		//获取角色信息
		String resultRole = customerRoleService.getBaseRoles();
		DataTransferObject roleDto = JsonEntityTransform.json2DataTransferObject(resultRole);
		List<CustomerRoleBaseEntity>  roles = null;
		List<ActivityGiftEntity> listActivityGift = null;
		try{
			roles =roleDto.parseData("roles", new TypeReference<List<CustomerRoleBaseEntity>>() {});

			//查询当前系统 礼物 （最多查出500个）
			ActivityGiftRequest activityGiftRe  = new ActivityGiftRequest();

			activityGiftRe.setPage(1);
			activityGiftRe.setLimit(500);
			roleDto  = JsonEntityTransform.json2DataTransferObject(this.activityGiftService.queryActivityGifyByPage(JsonEntityTransform.Object2Json(activityGiftRe)));
			if(roleDto.getCode() == DataTransferObject.SUCCESS){
				listActivityGift = roleDto.parseData("listActivityGift", new TypeReference<List<ActivityGiftEntity>>() {
				});

			}
		}catch(Exception e){
			System.out.println(e.getMessage());
			LogUtil.error(LOGGER, "数据转化异常e={}", e);
		}
		GroupEntity groupEntity = new GroupEntity();
		groupEntity.setServiceLine(ServiceLineEnum.ZRP.getOrcode());
		DataTransferObject groupDto = JsonEntityTransform.json2DataTransferObject(cmsgroupService.listGroupByType(JsonEntityTransform.Object2Json(groupEntity)));
		if (groupDto.getCode() == DataTransferObject.SUCCESS) {
			List<GroupEntity> allList = groupDto.parseData("list", new TypeReference<List<GroupEntity>>() {
			});
			if (!Check.NuNCollection(allList)) {
				List<GroupEntity> userList = allList.stream().filter(p -> p.getGroupType() == GroupTypeEnum.USER.getCode()).collect(Collectors.toList());
				allList.removeAll(userList);
				mv.addObject("userGroups", userList);
				mv.addObject("houseGroups", allList);
			}
		}

		request.setAttribute("roles", roles);
		mv.addObject("cityMap", cityService.getOpenCityMap());
		List<Map> openList = cityService.getOpenCityList();

		mv.addObject("listActivityGift", listActivityGift);
		mv.addObject("cityList", openList);
		mv.addObject("groupList",groupService.getAllGroup());
		return mv;
	}


	/**
	 * 保存活动信息
	 * @author lishaochuan
	 * @create 2016年6月24日下午7:09:24
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/saveActivity", method = RequestMethod.POST)
	@ResponseBody
	public DataTransferObject saveActivity(HttpServletRequest request,@ModelAttribute ActivityEntity ac) {
		DataTransferObject dto = new DataTransferObject();
        if(ActivityTypeEnum.FIRST_ORDER_REDUC.getCode()==ac.getActType().intValue()){
        	if(!Check.NuNObj(ac.getActCut())){
        		ac.setActCut(ac.getActCut()*100);
        	}
        }
		String userFid = UserUtil.getCurrentUserFid();
		ac.setCreateId(userFid);
		String allCityCode = request.getParameter("allCityCode");
		String []cityCodeArray = request.getParameterValues("cityCodeArray");
		String giftInfo = request.getParameter("giftInfo");
		List<ActivityGiftItemEntity> listAcGiftItem = null;
		
		ActivityGiftInfoRequest  activityGiftInfoRequest = new ActivityGiftInfoRequest();

		transRequest2Ac(request,ac,dto);
		if (dto.getCode() != DataTransferObject.SUCCESS){
			return dto;
		}
		if(!Check.NuNStr(giftInfo)){

			String[] giftInfoArr = giftInfo.split(",");

			if(giftInfoArr.length>0){
				listAcGiftItem = new ArrayList<ActivityGiftItemEntity>();

				for (String gift : giftInfoArr) {
					String[] giftArr  = gift.split("_");
					if(giftArr.length == 2){
						ActivityGiftItemEntity activityGiftItem = new ActivityGiftItemEntity();
						activityGiftItem.setActSn(ac.getActSn());
						activityGiftItem.setFid(UUIDGenerator.hexUUID());
						activityGiftItem.setGiftCount(Integer.valueOf(giftArr[1]));
						activityGiftItem.setGiftFid(giftArr[0]);
						activityGiftItem.setIsDel(IsDelEnum.NOT_DEL.getCode());
						listAcGiftItem.add(activityGiftItem);
					}
				}
			}
		}

		activityGiftInfoRequest.setAc(ac);
		activityGiftInfoRequest.setListAcGiftItems(listAcGiftItem);
		String resultJson = activityService.saveGiftActivity(JsonEntityTransform.Object2Json(activityGiftInfoRequest), cityService.getCityCode(allCityCode,cityCodeArray));
		return JsonEntityTransform.json2DataTransferObject(resultJson);
	}


	/**
	 * 将当前的参数填充到活动对象
	 * @param request
	 * @param ac
	 */
	private void transRequest2Ac(HttpServletRequest request,ActivityEntity ac,DataTransferObject dto){
		if (Check.NuNObjs(request,ac)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("参数异常");
			return;
		}
		try {
			if(Check.NuNObj(ac.getActSn())){
				ac.setActSn(UUIDGenerator.hexUUID());
			}
			//各种日期设置
			String sActStartTime = request.getParameter("sActStartTime");
			if(!Check.NuNStr(sActStartTime)){
				ac.setActStartTime(DateUtil.parseDate(sActStartTime, "yyyy-MM-dd HH:mm:ss"));
			}
			String  sActEndTime = request.getParameter("sActEndTime");
			if(!Check.NuNStr(sActEndTime)){
				ac.setActEndTime(DateUtil.parseDate(sActEndTime, "yyyy-MM-dd HH:mm:ss"));
			}
		} catch (ParseException e) {
			LogUtil.error(LOGGER, "error:{}", e);
		}
	}

	/**
	 * 跳转修改活动页面
	 * @author lishaochuan
	 * @create 2016年6月24日下午8:08:57
	 * @param activitySn
	 * @param request
	 * @return
	 */
	@RequestMapping("toUpdateActivity")
	public ModelAndView toUpdateActivity(String activitySn, HttpServletRequest request){

		ModelAndView mv = new ModelAndView("/activity/normalActivityUpdate");
		return toActivityInfo(activitySn, request, mv);
	}


	/**
	 * 跳转修改活动页面
	 * @author lishaochuan
	 * @create 2016年6月24日下午8:08:57
	 * @param activitySn
	 * @param request
	 * @return
	 */
	@RequestMapping("toQueryActivity")
	public ModelAndView toQueryActivity(String activitySn, HttpServletRequest request){

		ModelAndView mv = new ModelAndView("/activity/normalActivityInfo");
		return toActivityInfo(activitySn, request, mv);
	}

	
	/**
	 * 
	 * 到活动详情页面
	 *
	 * @author yd
	 * @created 2016年10月12日 下午3:14:59
	 *
	 * @param activitySn
	 * @param request
	 * @param mv
	 * @return
	 */
	private ModelAndView toActivityInfo(String activitySn, HttpServletRequest request,ModelAndView mv){

		String resultJson = activityFullService.getActivityFullBySn(activitySn);
		DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(resultJson);
		ActivityFullEntity activity  = resultDto.parseData("full", new TypeReference<ActivityFullEntity>() {});
		if(!Check.NuNObj(activity.getActCut())){
			if(ActivityTypeEnum.FIRST_ORDER_REDUC.getCode()==activity.getActType().intValue()){
				activity.setActCut(activity.getActCut()/100);
			}
		}
		mv.addObject("activity", activity);
		List<ActivityCityEntity> cityEntityList = resultDto.parseData("cityList", new TypeReference<List<ActivityCityEntity>>() {});
		if(!Check.NuNCollection(cityEntityList)){
			String cityCodeStr = "";
			for(int i = 0;i<cityEntityList.size() ; i++){
				if(i == 0){
					cityCodeStr += cityEntityList.get(i).getCityCode();
				}else{
					cityCodeStr += ","+cityEntityList.get(i).getCityCode();
				}
			}
			if ("0".equals(cityCodeStr)){
				mv.addObject("all","1");
			}else {
				mv.addObject("all","0");
			}
			mv.addObject("cityCodeStr",cityCodeStr);
		}

		if(!Check.NuNObj(activity)){
			List<AcGiftItemVo> lsitAcGiftItemVo  = resultDto.parseData("lsitAcGiftItemVo", new TypeReference<List<AcGiftItemVo>>() {});
        	mv.addObject("lsitAcGiftItemVo",lsitAcGiftItemVo);
		}

		if (activity.getServiceLine() == ServiceLineEnum.ZRP.getOrcode()) {
			//获取组信息
			GroupEntity groupEntity = new GroupEntity();
			groupEntity.setServiceLine(ServiceLineEnum.ZRP.getOrcode());
			DataTransferObject groupDto = JsonEntityTransform.json2DataTransferObject(cmsgroupService.listGroupByType(JsonEntityTransform.Object2Json(groupEntity)));
			if (groupDto.getCode() == DataTransferObject.SUCCESS) {
				List<GroupEntity> allList = groupDto.parseData("list", new TypeReference<List<GroupEntity>>() {
				});
				if (!Check.NuNCollection(allList)) {
					List<GroupEntity> userList = allList.stream().filter(p -> p.getGroupType() == GroupTypeEnum.USER.getCode()).collect(Collectors.toList());
					allList.removeAll(userList);
					mv.addObject("userGroups", userList);
					mv.addObject("houseGroups", allList);

					mv.addObject("userGroupFid", activity.getGroupUserFid());
					mv.addObject("houseGroupFid", activity.getGroupHouseFid());

				}
			}
		}

		String cityJson = confCityService.getOpenCityMap();
		DataTransferObject cityDto = JsonEntityTransform.json2DataTransferObject(cityJson);
		Map<String, String> cityMap = cityDto.parseData("map", new TypeReference<Map<String, String>>() {});
		mv.addObject("cityMap", cityMap);


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
		List<Map> openList = cityService.getOpenCityList();
        mv.addObject("groupList",groupService.getAllGroup());
		mv.addObject("cityList", openList);
		return mv;
	}




	/**
	 * 修改活动
	 * 说明 ：当有礼品活动的时候 删除之前 的礼品活动相  在新增新添加的 礼品活动相
	 * @author afi
	 * @param request
	 * @param ac
	 * @return
	 */
	@RequestMapping(value = "/updateActivity", method = RequestMethod.POST)
	@ResponseBody
	public DataTransferObject updateActivity(HttpServletRequest request,@ModelAttribute ActivityEntity ac) {
		DataTransferObject dto = new DataTransferObject();
		if(ActivityTypeEnum.FIRST_ORDER_REDUC.getCode()==ac.getActType().intValue()){
        	if(!Check.NuNObj(ac.getActCut())){
        		ac.setActCut(ac.getActCut()*100);
        	}
        }
		String allCityCode = request.getParameter("allCityCode");
		String []cityCodeArray = request.getParameterValues("cityCodeArray");
		
		String giftInfo = request.getParameter("giftInfo");
		List<ActivityGiftItemEntity> listAcGiftItem = null;
		transRequest2Ac(request,ac,dto);
		if (dto.getCode() != DataTransferObject.SUCCESS){
			return dto;
		}
		
		ActivityGiftInfoRequest activityGiftInfoRe  = new ActivityGiftInfoRequest();
		if(!Check.NuNStr(giftInfo)){

			String[] giftInfoArr = giftInfo.split(",");

			if(giftInfoArr.length>0){
				listAcGiftItem = new ArrayList<ActivityGiftItemEntity>();

				for (String gift : giftInfoArr) {
					String[] giftArr  = gift.split("_");
					if(giftArr.length == 2){
						ActivityGiftItemEntity activityGiftItem = new ActivityGiftItemEntity();
						activityGiftItem.setActSn(ac.getActSn());
						activityGiftItem.setFid(UUIDGenerator.hexUUID());
						activityGiftItem.setGiftCount(Integer.valueOf(giftArr[1]));
						activityGiftItem.setGiftFid(giftArr[0]);
						activityGiftItem.setIsDel(IsDelEnum.NOT_DEL.getCode());
						listAcGiftItem.add(activityGiftItem);
					}
				}
			}
		}
		activityGiftInfoRe.setAc(ac);
		activityGiftInfoRe.setListAcGiftItems(listAcGiftItem);
		String resultJson = activityService.updateGiftAcByActivity(JsonEntityTransform.Object2Json(activityGiftInfoRe), cityService.getCityCode(allCityCode,cityCodeArray));
		return JsonEntityTransform.json2DataTransferObject(resultJson);
	}

}
