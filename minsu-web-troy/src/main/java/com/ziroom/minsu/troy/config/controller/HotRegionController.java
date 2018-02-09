/**
 * @FileName: HotRegionController.java
 * @Package com.ziroom.minsu.troy.hotregion.controller
 * 
 * @author liujun
 * @created 2016-3-21 下午6:49:05
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.troy.config.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.SOAResParseUtil;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.conf.HotRegionEntity;
import com.ziroom.minsu.services.basedata.api.inner.CityTemplateService;
import com.ziroom.minsu.services.basedata.api.inner.ConfCityService;
import com.ziroom.minsu.services.basedata.api.inner.HotRegionService;
import com.ziroom.minsu.services.basedata.api.inner.ZkSysService;
import com.ziroom.minsu.services.basedata.dto.HotRegionRequest;
import com.ziroom.minsu.services.basedata.entity.EnumVo;
import com.ziroom.minsu.services.basedata.entity.TreeNodeVo;
import com.ziroom.minsu.services.basedata.entity.entityenum.GoogleBaiduCoordinateEnum;
import com.ziroom.minsu.services.common.conf.EnumMinsuConfig;
import com.ziroom.minsu.services.common.page.PageResult;
import com.ziroom.minsu.services.common.utils.CoordinateTransforUtils;
import com.ziroom.minsu.services.common.utils.Gps;
import com.ziroom.minsu.troy.common.util.UserUtil;
import com.ziroom.minsu.valenum.base.MapTypeEnum;
import com.ziroom.minsu.valenum.city.CityRulesEnum;

/**
 * <p>景点商圈管理</p>
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
@RequestMapping("config/hotRegion")
public class HotRegionController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(HotRegionController.class);

	/**
	 * 系统通用配置值
	 */
	private static final Integer DEFAULT_RADII = 0;
	
	@Value("#{'${minsu.static.resource.url}'.trim()}")
	private String staticResourceUrl;
	
	@Resource(name = "basedata.hotRegionService")
	private HotRegionService hotRegionService;
	
	@Resource(name = "basedata.confCityService")
	private ConfCityService confCityService;
	
	@Resource(name="basedata.cityTemplateService")
	private CityTemplateService cityTemplateService;
	
	
	@Resource(name="basedata.zkSysService")
	private ZkSysService zkSysService;
	
	/**
	 * 
	 * 景点商圈管理-跳转景点商圈列表页
	 *
	 * @author liujun
	 * @created 2016-3-21 下午7:06:07
	 *
	 * @param request
	 */
	@RequestMapping("listHotRegions")
	public void listHotRegion(HttpServletRequest request) {
		cascadeDistricts(request);
		List<EnumVo> hotRegionEnum = new ArrayList<>();
		try {
			String hotRegionJson = cityTemplateService.getEffectiveSelectEnum(null, CityRulesEnum.CityRulesEnum002.getValue());
			DataTransferObject datadto = JsonEntityTransform.json2DataTransferObject(hotRegionJson);
			if (datadto.getCode() == DataTransferObject.SUCCESS) {
				hotRegionEnum = SOAResParseUtil.getListValueFromDataByKey(hotRegionJson, "selectEnum", EnumVo.class);
				request.setAttribute("hotRegionEnumList", hotRegionEnum);
			}
		} catch (Exception e) {
			LogUtil.error(LOGGER, "e={}", e);
		}
		
		request.setAttribute("regionTypeJson", JsonEntityTransform.Object2Json(hotRegionEnum));
		request.setAttribute("staticResourceUrl", staticResourceUrl);
	}
	
	/**
	 * 
	 * 获取行政区域列表
	 *
	 * @author liujun
	 * @created 2016年5月7日
	 *
	 * @param request
	 */
	private void cascadeDistricts(HttpServletRequest request) {
		String resultJson = confCityService.getConfCitySelect();
		DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(resultJson);
		List<TreeNodeVo> cityTreeList = resultDto.parseData("list", new TypeReference<List<TreeNodeVo>>() {});
		request.setAttribute("cityTreeList", JsonEntityTransform.Object2Json(cityTreeList));
	};
	
	/**
	 * 
	 * 景点商圈管理-根据条件查询景点商圈列表
	 *
	 * @author liujun
	 * @created 2016-3-21 下午7:06:21
	 *
	 * @param roleRequest
	 * @return
	 */
	@RequestMapping("showHotRegions")
	@ResponseBody
	public PageResult showHotRegions(HotRegionRequest hotRegionRequest) {
		try {
			Integer radii = this.getDefaultRadii();
			if (!Check.NuNObj(hotRegionRequest.getRadii()) && hotRegionRequest.getRadii().equals(radii)) {
				hotRegionRequest.setRadii(DEFAULT_RADII);//通用配置值落地到数据库中为0
			}
			String resultJson = hotRegionService.searchHotRegions(JsonEntityTransform.Object2Json(hotRegionRequest));
			DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(resultJson);
			if (dto.getCode() == DataTransferObject.ERROR) {
				LogUtil.error(LOGGER, "hotRegionService.searchHotRegions error:{}", resultJson);
				return new PageResult();
			}
			List<HotRegionEntity> roleList = SOAResParseUtil.getListValueFromDataByKey(resultJson, "list", HotRegionEntity.class);
			if (!Check.NuNCollection(roleList)) {
				if (!Check.NuNObj(radii)) {
					for (HotRegionEntity hotRegionEntity : roleList) {
						if (!Check.NuNObj(hotRegionEntity.getRadii()) 
								&& DEFAULT_RADII == hotRegionEntity.getRadii().intValue()) {
							hotRegionEntity.setRadii(radii);
						}
					}
				}
			}
			PageResult pageResult = new PageResult();
			pageResult.setRows(roleList);
			pageResult.setTotal(Long.valueOf(dto.getData().get("total").toString()));
			return pageResult;
		} catch (Exception e) {
			LogUtil.error(LOGGER, "showHotRegions error:{}", e);
			return new PageResult();
		} 
	}
	
	/**
	 * 获取通用配置半径
	 *
	 * @author liujun
	 * @created 2016年11月14日
	 *
	 * @return
	 */
	private Integer getDefaultRadii() {
		Integer radii = null;
		String resultJson = cityTemplateService.getTextValue(null, CityRulesEnum.CityRulesEnum001.getValue());
		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(resultJson);
		if (dto.getCode() == DataTransferObject.ERROR) {
			LogUtil.error(LOGGER, "cityTemplateService.getTextValue error:{}, dicCode:{}", 
					resultJson, CityRulesEnum.CityRulesEnum001.getValue());
		} else {
			radii = SOAResParseUtil.getIntFromDataByKey(resultJson, "textValue");
		}
		return radii;
	}

	/**
	 * 
	 * 景点商圈管理-级联下级行政区域列表
	 * 
	 * 建议使用 {@link #cascadeDistricts}
	 *
	 * @author liujun
	 * @created 2016-3-21 下午9:21:48
	 *
	 * @return
	 */
	@Deprecated
	@RequestMapping("/linkDistrict")
	@ResponseBody
	public DataTransferObject linkDistrict(String pCode) {
		try {
			String resultJson = confCityService.searchDistricts(pCode);
			return JsonEntityTransform.json2DataTransferObject(resultJson);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			DataTransferObject dto = new DataTransferObject();
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg(e.getMessage());
			return dto;
		}
	}
	
	/**
	 * 
	 * 景点商圈管理-跳转新增景点商圈页面
	 *
	 * @author liujun
	 * @created 2016-3-21 下午11:09:01
	 *
	 * @param request
	 */
	@RequestMapping("addHotRegion")
	public void toAddHotRegion(HttpServletRequest request) {
		cascadeDistricts(request);
		try {
			String regionTypeJson = cityTemplateService.getEffectiveSelectEnum(null, CityRulesEnum.CityRulesEnum002.getValue());
			DataTransferObject datadto = JsonEntityTransform.json2DataTransferObject(regionTypeJson);
			if (datadto.getCode() == DataTransferObject.SUCCESS) {
				List<EnumVo> hotRegionEnum = SOAResParseUtil.getListValueFromDataByKey(regionTypeJson, "selectEnum", EnumVo.class);
				request.setAttribute("hotRegionEnumList", hotRegionEnum);
			}
		} catch (Exception e) {
			LogUtil.error(LOGGER, "cityTemplateService.getEffectiveSelectEnum error:{}", e);
		}
		request.setAttribute("staticResourceUrl", staticResourceUrl);
	}
	
	/**
	 * 
	 * 景点商圈管理-新增景点商圈
	 *
	 * @author liujun
	 * @created 2016-3-21 下午11:31:05
	 *
	 * @param roleName
	 * @param resFids
	 * @return
	 */
	@RequestMapping("/saveHotRegion")
	@ResponseBody
	public DataTransferObject addHotRegion(String hotRegion) {
		try {
			HotRegionEntity hotRegionEntity = JsonEntityTransform.json2Object(hotRegion, HotRegionEntity.class);
			Integer radii = this.getDefaultRadii();
			if (!Check.NuNObj(radii) && !Check.NuNObj(hotRegionEntity.getRadii()) 
					&& radii.intValue() == hotRegionEntity.getRadii().intValue()) {
				hotRegionEntity.setRadii(DEFAULT_RADII);
			}
			String userFid = UserUtil.getCurrentUserFid();
			hotRegionEntity.setCreateFid(userFid);
			
			String mapType = zkSysService.getZkSysValue(EnumMinsuConfig.minsu_mapType.getType(), EnumMinsuConfig.minsu_mapType.getCode());
			String mapTypeReal = mapType;
			if(MapTypeEnum.PC_GOOLGE_M_BAIDU.getCode().equals(mapType)){
				mapTypeReal = MapTypeEnum.GOOGLE.getCode();
			}
			GoogleBaiduCoordinateEnum.HotRegionEntity.googleBaiduCoordinateTranfor(hotRegionEntity, hotRegionEntity.getGoogleLatitude(), hotRegionEntity.getGoogleLatitude(), mapTypeReal);
			
			String resultJson =  hotRegionService.saveHotRegion(JsonEntityTransform.Object2Json(hotRegionEntity));
			return JsonEntityTransform.json2DataTransferObject(resultJson);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			DataTransferObject dto = new DataTransferObject();
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg(e.getMessage());
			return dto;
		}
	}
	
	/**
	 * 
	 * 景点商圈管理-跳转修改景点商圈页面
	 *
	 * @author liujun
	 * @created 2016-3-22 上午10:47:39
	 *
	 * @param request
	 */
	@RequestMapping("editHotRegion")
	public void toEditHotRegion(HttpServletRequest request, String hotRegionFid) {
		//获取景点商圈实体
		String hotRegionJson = hotRegionService.searchHotRegionByFid(hotRegionFid);
		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(hotRegionJson);
		HotRegionEntity hotRegion = dto.parseData("hotRegion", new TypeReference<HotRegionEntity>() {});
		if (!Check.NuNObj(hotRegion)) {
			Integer radii = this.getDefaultRadii();
			if (!Check.NuNObj(radii) && !Check.NuNObj(hotRegion.getRadii()) 
					&& DEFAULT_RADII == hotRegion.getRadii()) {
				hotRegion.setRadii(radii);
			}
		}
		request.setAttribute("hotRegion", hotRegion);
		
		cascadeDistricts(request);
		try {
			String regionTypeJson = cityTemplateService.getEffectiveSelectEnum(null, CityRulesEnum.CityRulesEnum002.getValue());
			DataTransferObject datadto = JsonEntityTransform.json2DataTransferObject(regionTypeJson);
			if (datadto.getCode() == DataTransferObject.SUCCESS) {
				List<EnumVo> hotRegionEnum = SOAResParseUtil.getListValueFromDataByKey(regionTypeJson, "selectEnum", EnumVo.class);
				request.setAttribute("hotRegionEnumList", hotRegionEnum);
			}
		} catch (Exception e) {
			LogUtil.error(LOGGER, "cityTemplateService.getEffectiveSelectEnum error:{}", e);
		}
		request.setAttribute("staticResourceUrl", staticResourceUrl);
	}
	
	/**
	 * 
	 * 景点商圈管理-修改景点商圈
	 *
	 * @author liujun
	 * @created 2016-3-21 下午11:31:05
	 *
	 * @param roleName
	 * @param resFids
	 * @return
	 */
	@RequestMapping("/updateHotRegion")
	@ResponseBody
	public DataTransferObject updateHotRegion(String hotRegion) {
		try {
			HotRegionEntity hotRegionEntity = JsonEntityTransform.json2Object(hotRegion, HotRegionEntity.class);
			Integer radii = this.getDefaultRadii();
			if (!Check.NuNObj(radii) && !Check.NuNObj(hotRegionEntity.getRadii()) 
					&& radii.intValue() == hotRegionEntity.getRadii().intValue()) {
				hotRegionEntity.setRadii(DEFAULT_RADII);
			}
			
			String resultJson = hotRegionService.updateHotRegion(JsonEntityTransform.Object2Json(hotRegionEntity));
			return JsonEntityTransform.json2DataTransferObject(resultJson);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			DataTransferObject dto = new DataTransferObject();
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg(e.getMessage());
			return dto;
		}
	}
	
	/**
	 * 
	 * 景点商圈管理-启用|禁用景点商圈
	 *
	 * @author liujun
	 * @created 2016-3-22 上午11:02:04
	 *
	 * @param roleFid
	 * @return
	 */
	@RequestMapping("editHotRegionStatus")
	@ResponseBody
	public DataTransferObject editHotRegionStatus(String hotRegionFid) {
		try {
			String searchJson = hotRegionService.searchHotRegionByFid(hotRegionFid);
			DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(searchJson);
			
			//返回状态失败 0:成功 1:失败
			if (dto.getCode() == DataTransferObject.ERROR) {
				return dto;
			}
			
			String editJson = hotRegionService.editHotRegionStatus(JsonEntityTransform
					.Object2Json(dto.getData().get("hotRegion")));
			return JsonEntityTransform.json2DataTransferObject(editJson);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			DataTransferObject dto = new DataTransferObject();
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg(e.getMessage());
			return dto;
		}
	}
	
}
