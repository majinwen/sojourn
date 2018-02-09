/**
 * @FileName: FeatureTagController.java
 * @Package com.ziroom.minsu.troy.conf
 * 
 * @author zl
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.troy.config.controller;

import java.util.ArrayList;
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

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.conf.CityFeatureHousetypeEntity;
import com.ziroom.minsu.services.basedata.api.inner.ConfCityService;
import com.ziroom.minsu.services.basedata.entity.FeatureTagsVo;
import com.ziroom.minsu.troy.common.util.UserUtil;
import com.ziroom.minsu.valenum.common.YesOrNoEnum;

/**
 * <p></p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author zl
 * @since 1.0
 * @version 1.0
 */
@Controller
@RequestMapping("config/featureTag")
public class FeatureTagController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(FeatureTagController.class);
	
	@Resource(name = "basedata.confCityService")
	private ConfCityService confCityService;
	
	@Value("#{'${minsu.static.resource.url}'.trim()}")
	private String staticResourceUrl;
	

	/**
	 * 特色标识列表
	 * TODO
	 *
	 * @author zl
	 * @created 2017年1月5日 下午2:40:38
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/tagList")
	public void tagList(HttpServletRequest request){
		Map<String, List<FeatureTagsVo>> resultMap = new HashMap<>();
		
		try {
			String featureTagListStr =  confCityService.getAllFeatureTags(null,null);
			
			DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(featureTagListStr);
			List<FeatureTagsVo> featureTagList = resultDto.parseData("result", new TypeReference<List<FeatureTagsVo>>() {});
			
			if(!Check.NuNCollection(featureTagList)){
				for (FeatureTagsVo featureTagsVo : featureTagList) {
					List<FeatureTagsVo> groupList = resultMap.get(featureTagsVo.getTagTypeName());
					if (groupList == null) {
						groupList = new ArrayList<>();
					}
					groupList.add(featureTagsVo);
					
					resultMap.put(featureTagsVo.getTagTypeName(), groupList);
				}
			}
			
			
		} catch (Exception e) {
			LogUtil.error(LOGGER, "查询特色标识列表异常，e={}", e);
		}
		request.setAttribute("featureMap", resultMap); 
	}
	

	@RequestMapping("/changeStatus")
	@ResponseBody
	public  DataTransferObject changeStatus(HttpServletRequest request,String featureTagFid, Integer isValid) {
		
		DataTransferObject dto = new DataTransferObject();
		try { 
			
			if (Check.NuNStr(featureTagFid) || Check.NuNObj(isValid)) {
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("参数错误");
				return dto;
			}
			
			CityFeatureHousetypeEntity entity = new CityFeatureHousetypeEntity();
			entity.setFid(featureTagFid);
			entity.setIsValid(isValid);
			
			String resultJSON = confCityService.updateFeatureTagByFid(JsonEntityTransform.Object2Json(entity));
			dto = JsonEntityTransform.json2DataTransferObject(resultJSON);
			
		} catch (Exception e) {
			LogUtil.error(LOGGER, "featureTag changeStatus error:{}", e);
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("服务错误");
		}
       return dto;
    }
	
	@RequestMapping("/addFeatureTag")
	@ResponseBody
	public  DataTransferObject addFeatureTag(HttpServletRequest request,String foreignFid) {
		DataTransferObject dto = new DataTransferObject();
		try { 
			
			if (Check.NuNStr(foreignFid)) {
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("参数错误");
				return dto;
			}
			
			String fid = UUIDGenerator.hexUUID();
			
			CityFeatureHousetypeEntity entity = new CityFeatureHousetypeEntity();
			entity.setFid(fid);
			entity.setIsValid(YesOrNoEnum.YES.getCode());
			entity.setCreateDate(new Date());
			entity.setCreateFid(UserUtil.getCurrentUserFid());
			entity.setForeignFid(foreignFid);
			entity.setIsDel(YesOrNoEnum.NO.getCode());
			entity.setLastModifyDate(new Date());	
			
			String resultJSON = confCityService.addFeatureTag(JsonEntityTransform.Object2Json(entity));
			dto = JsonEntityTransform.json2DataTransferObject(resultJSON);
			dto.putValue("fid", fid);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "featureTag addFeatureTag error:{}", e);
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("服务错误");
		}
       return dto;
    }
	
	
 
}
