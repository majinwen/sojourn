/**
 * @FileName: ConfTagController.java
 * @Package com.ziroom.minsu.troy.conf
 * 
 * @author zl
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.troy.config.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.SOAResParseUtil;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.cache.redisOne.RedisOperations;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.conf.ConfTagEntity;
import com.ziroom.minsu.services.basedata.api.inner.CityTemplateService;
import com.ziroom.minsu.services.basedata.api.inner.ConfTagService;
import com.ziroom.minsu.services.basedata.constant.BaseDataConstant;
import com.ziroom.minsu.services.basedata.dto.ConfTagRequest;
import com.ziroom.minsu.services.basedata.dto.ConfTagVo;
import com.ziroom.minsu.services.basedata.entity.EnumVo;
import com.ziroom.minsu.services.common.page.PageResult;
import com.ziroom.minsu.services.common.utils.RedisKeyConst;
import com.ziroom.minsu.troy.common.util.UserUtil;
import com.ziroom.minsu.valenum.common.YesOrNoEnum;
import com.ziroom.minsu.valenum.productrules.ProductRulesEnum;

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
@RequestMapping("config/confTag")
public class ConfTagController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ConfTagController.class);
	
	@Resource(name = "basedata.confTagService")
	private ConfTagService confTagService;
	
	@Resource(name ="basedata.cityTemplateService")
	private CityTemplateService cityTemplateService;
	
	@Autowired
	private RedisOperations redisOperations;
	

	/**
	 * 标签列表
	 * TODO
	 *
	 * @author zl
	 * @created 2017年1月5日 下午2:40:38
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/confTagList")
	public void confTagList(HttpServletRequest request){
		
		HashMap<String , String> tagTypeMap = new HashMap<>();
		
		try {
			
			String resultJson = cityTemplateService.getEffectiveSelectEnum(null, ProductRulesEnum.ProductRulesEnum0022.getValue(), false);
			List<EnumVo> tagTypeList = SOAResParseUtil.getListValueFromDataByKey(resultJson, "selectEnum", EnumVo.class);
			if(!Check.NuNCollection(tagTypeList)){
				for (EnumVo enumVo : tagTypeList) {
					tagTypeMap.put(enumVo.getKey(), enumVo.getText());
				}
			}
			
		} catch (Exception e) {
			LogUtil.error(LOGGER, "查询标签列表异常，e={}", e);
		}
		
		request.setAttribute("tagTypeMap", tagTypeMap);
	}
	
	/**
	 * 标签列表数据
	 * TODO
	 *
	 * @author zl
	 * @created 2017年1月10日 下午8:55:07
	 *
	 * @param request
	 * @param params
	 * @return
	 */
	@RequestMapping("/showConfTagList")
	@ResponseBody
	public PageResult showConfTagList(HttpServletRequest request,ConfTagRequest params){
		PageResult pageResult = new PageResult();
		
		try {
			String resultStr =  confTagService.findByConfTagRequest(JsonEntityTransform.Object2Json(params));
			
			DataTransferObject resultDto =  JsonEntityTransform.json2DataTransferObject(resultStr);
			
			// 判断调用状态
			if(resultDto.getCode() == DataTransferObject.ERROR){
				LogUtil.error(LOGGER, "查询标签列表失败,参数:{}", JsonEntityTransform.Object2Json(params));
				return pageResult;
			}
			
			List<ConfTagVo> list = SOAResParseUtil.getListValueFromDataByKey(resultStr, "list", ConfTagVo.class);
			
			pageResult.setRows(list);
			pageResult.setTotal(Long.valueOf(resultDto.getData().get("total").toString()));
			
		} catch (Exception e) {
			LogUtil.error(LOGGER, "查询标签列表异常，params={}，e={}",JsonEntityTransform.Object2Json(params), e);
		}
		
		return pageResult;
	}
	
	/**
	 * 添加标签
	 * TODO
	 *
	 * @author zl
	 * @created 2017年1月10日 下午4:06:48
	 *
	 * @param request
	 * @param entity
	 * @return
	 */
	@RequestMapping("/addConfTag")
	@ResponseBody
	public  DataTransferObject addConfTag(HttpServletRequest request,ConfTagEntity entity) {
		DataTransferObject dto = new DataTransferObject();
		try { 
			
			if (Check.NuNObj(entity) || Check.NuNStr(entity.getTagName()) || Check.NuNObj(entity.getTagType())) {
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("参数错误");
				return dto;
			}
			
			String fid = UUIDGenerator.hexUUID(); 
			entity.setFid(fid);
			entity.setIsValid(YesOrNoEnum.NO.getCode());
			entity.setCreateDate(new Date());
			entity.setCreateFid(UserUtil.getCurrentUserFid());
			entity.setIsDel(YesOrNoEnum.NO.getCode());
			entity.setLastModifyDate(new Date());
			
			String resultJSON = confTagService.addConfTag(JsonEntityTransform.Object2Json(entity));
			dto = JsonEntityTransform.json2DataTransferObject(resultJSON); 
		} catch (Exception e) {
			LogUtil.error(LOGGER, "添加标签异常，参数={}， error:{}",JsonEntityTransform.Object2Json(entity),e);
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("服务错误");
		}
       return dto;
    }
	
	
	/**
	 * 修改标签状态
	 * TODO
	 *
	 * @author zl
	 * @created 2017年1月10日 下午4:07:04
	 *
	 * @param request
	 * @param fid
	 * @param isValid
	 * @return
	 */
	@RequestMapping("/changeStatus")
	@ResponseBody
	public  DataTransferObject changeStatus(HttpServletRequest request,String fid, Integer isValid) {
		
		DataTransferObject dto = new DataTransferObject();
		try { 
			
			if (Check.NuNStr(fid) || Check.NuNObj(isValid)) {
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("参数错误");
				return dto;
			}
			
			ConfTagEntity entity = new ConfTagEntity();
			entity.setFid(fid);
			entity.setIsValid(isValid);
			entity.setLastModifyDate(new Date());
			
			String resultJSON = confTagService.modifyTagStatus(JsonEntityTransform.Object2Json(entity));
			dto = JsonEntityTransform.json2DataTransferObject(resultJSON);
			
		} catch (Exception e) {
			LogUtil.error(LOGGER, "修改标签有效状态异常，fid={}，isValid={}，error:{}",fid,isValid, e);
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("服务错误");
		}
       return dto;
    }
	
	/**
	 * 修改标签名称
	 * TODO
	 *
	 * @author zl
	 * @created 2017年1月10日 下午4:08:25
	 *
	 * @param request
	 * @param fid
	 * @param tagName
	 * @return
	 */
	@RequestMapping("/modifyTagName")
	@ResponseBody
	public  DataTransferObject modifyTagName(HttpServletRequest request,String fid, String tagName) {
		
		DataTransferObject dto = new DataTransferObject();
		try { 
			
			if (Check.NuNStr(fid) || Check.NuNStr(tagName)) {
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("参数错误");
				return dto;
			}
			
			ConfTagEntity entity = new ConfTagEntity();
			entity.setFid(fid);
			entity.setTagName(tagName);
			entity.setLastModifyDate(new Date());
			
			String resultJSON = confTagService.modifyTagName(JsonEntityTransform.Object2Json(entity));
			dto = JsonEntityTransform.json2DataTransferObject(resultJSON);
			
		} catch (Exception e) {
			LogUtil.error(LOGGER, "修改标签有效状态异常，fid={}，tagName={}，error:{}",fid,tagName, e);
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("服务错误");
		}
       return dto;
    }
	
 
}
