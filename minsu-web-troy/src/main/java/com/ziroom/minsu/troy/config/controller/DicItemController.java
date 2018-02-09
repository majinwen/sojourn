/**
 * @FileName: ConfItemController.java
 * @Package com.ziroom.minsu.troy.conf
 * 
 * @author bushujie
 * @created 2016年3月22日 下午10:03:17
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.troy.config.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.codehaus.jackson.type.TypeReference;
import org.codehaus.plexus.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.conf.DicItemEntity;
import com.ziroom.minsu.services.basedata.api.inner.CityTemplateService;
import com.ziroom.minsu.services.basedata.entity.TreeNodeVo;
import com.ziroom.minsu.services.common.page.PageResult;

/**
 * <p>系统配置项controller</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author liyingjie
 * @since 1.0
 * @version 1.0
 */
@Controller
@RequestMapping("config/dicItem")
public class DicItemController {

	private static final Logger LOGGER = LoggerFactory.getLogger(DicItemController.class);
	
	@Resource(name="basedata.cityTemplateService")
	private CityTemplateService cityTemplateService;
	
	@Value("#{'${minsu.static.resource.url}'.trim()}")
	private String staticResourceUrl;
	
	/**
	 * 
	 * 配置项树列表
	 *
	 * @author bushujie
	 * @created 2016年3月22日 下午10:39:35
	 *
	 */
	@RequestMapping("/itemTree")
	public void confItemTree(HttpServletRequest request){
		String line = request.getParameter("templateLine");
		List<TreeNodeVo> treeList = getTreeVoList(line);
		request.setAttribute("treeview", JsonEntityTransform.Object2Json(treeList));
		String templateId = request.getParameter("templateId");
		request.setAttribute("staticResourceUrl", staticResourceUrl);
		request.setAttribute("templateId", templateId);
	}
	
	
	/**
	 *
	 * 后台--右侧--列表
	 *
	 * @author liyingjie
	 * @created 2016年3月9日 下午4:59:06
	 * @param request
	 */
	@RequestMapping("/confitemDatalist")
	public @ResponseBody PageResult dataList(HttpServletRequest request) {
		//返回结果
		PageResult pageResult = new PageResult();
        String code = request.getParameter("code");
        String templateId = request.getParameter("templateId");
		if(StringUtils.isBlank(code) || StringUtils.isBlank(templateId)){
			pageResult.setTotal(0l);
			return pageResult;
		}
		
		String resultJson = cityTemplateService.getDicItemListByCodeAndTemplate(code, templateId); //cityTemplateService.getConfDicByPfid(pfid);
        DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(resultJson);
		List<DicItemEntity> confDicList = resultDto.parseData("list", new TypeReference<List<DicItemEntity>>() {
		});
		request.setAttribute("staticResourceUrl", staticResourceUrl);
        pageResult.setRows(confDicList);
		return pageResult;
    }
	
	
	/**
	 * 添加--资源--操作
	 * 
	 * @author liyingjie
	 * @created 2016年3月22日 
	 * @param request
	 */
	@RequestMapping("/addConfitemRes")
	@ResponseBody
	public Map<String,Object> addData(@ModelAttribute DicItemEntity confDic, HttpServletRequest request) {
        //保存 实体
		DicItemEntity cde = new DicItemEntity();
		
		cde.setFid(UUIDGenerator.hexUUID());
		
		if (StringUtils.isNotBlank(confDic.getTemplateFid())) {
			cde.setTemplateFid(confDic.getTemplateFid());
		}
		
		if (StringUtils.isNotBlank(confDic.getDicCode())) {
			cde.setDicCode(confDic.getDicCode());
		}
		
		if (StringUtils.isNotBlank(confDic.getShowName())) {
			cde.setShowName(confDic.getShowName());
		}
		
		if (StringUtils.isNotBlank(confDic.getItemValue())) {
			cde.setItemValue(confDic.getItemValue());
		}
		//向数据库保存数据
		String paramJson = JsonEntityTransform.Object2Json(cde);
		cityTemplateService.insertDicItem(paramJson);

		Map<String,Object> resMap= new HashMap<String,Object>(); 
		return resMap;
	}
	
	
	
	/**
	 *
	 * 添加资源--校验--0：不能添加资源 1：只能添加一个有效资源 2：枚举类型，可以添加多个资源3：列表，可以添加多个资源
	 *
	 * @author liyingjie
	 * @created 2016年3月24日 
	 * @param request
	 */
	@RequestMapping("/checkitemType")
	public @ResponseBody Object checkitemType(HttpServletRequest request) {
		//返回结果
		Map<String,Object> resMap = new HashMap<String,Object>(2);
		boolean result = true;
		StringBuilder msg = new StringBuilder();
		
		//获取节点类型
		String dicType = request.getParameter("nodeType");
		
		//非叶子结点，不能添加值的信息
		if(Integer.valueOf(dicType) == 0){
			result = false;
			msg.append("只有叶子节点可以添加值！");
			resMap.put("msg", msg);
			resMap.put("result", result);
			return resMap;
		}
		
		String code = request.getParameter("dicCode");
        String templateId = request.getParameter("templateId");
        
		//查询有效值的数量
		String resultJson = cityTemplateService.countItemNumList(templateId,code);
	    DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(resultJson);
	    long countResult = Long.valueOf(resultDto.getData().get("countRes").toString());
		
	    //唯一值 类型的叶子节点  只能添加一个有效值
		if(Integer.valueOf(dicType) == 1 && countResult >= 1){
			result = false;
			msg.append("该叶子节点值唯一，只能存在一个有效值！");
			resMap.put("msg", msg);
			resMap.put("result", result);
			return resMap;
		}
        
		resMap.put("msg", msg);
		resMap.put("result", result);
		request.setAttribute("staticResourceUrl", staticResourceUrl);
        return resMap;
   }
	
	
	/**
	 * 值--状态改变--操作
	 * 
	 * @author liyingjie
	 * @created 2016年3月9日 下午4:59:06
	 * @param request
	 * @return
	 */
	@RequestMapping("/changeStatus")
	@ResponseBody
	public Map<String,Object> changeStatus(HttpServletRequest request,String selectedId,String resStatus) {
		DicItemEntity resourceEntity = new DicItemEntity();
		resourceEntity.setFid(selectedId);
		
		if(Integer.parseInt(resStatus) == 0){
			resourceEntity.setItemStatus(1);
		}else{
			resourceEntity.setItemStatus(0);
		}
		cityTemplateService.updateDicItem(JsonEntityTransform.Object2Json(resourceEntity));
		
		Map<String,Object> resObject = new HashMap<String, Object>();
		resObject.put("status", JsonEntityTransform.Object2Json("success"));
		return resObject;
		
	}
	
	/**
	 * 
	 * 获取菜单树--左侧树--方法封装
	 *
	 * @author liyingjie
	 * @created 2016年3月23日 
	 *
	 */
	private List<TreeNodeVo> getTreeVoList(String line) {
		String resultJson = cityTemplateService.getDicTree(line);
		DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(resultJson);
		List<TreeNodeVo> treeList=resultDto.parseData("list", new TypeReference<List<TreeNodeVo>>() {});
		return treeList;
	}

	/**
	 * 
	 * 配置项排序
	 *
	 * @author liujun
	 * @created 2017年1月9日
	 *
	 * @param request
	 */
	@RequestMapping("/itemIndex")
	public void resizeItemIndex(HttpServletRequest request){
		String code = request.getParameter("code");
        String templateId = request.getParameter("templateId");
		request.setAttribute("code", code);
		request.setAttribute("templateId", templateId);
		request.setAttribute("staticResourceUrl", staticResourceUrl);
	}
	
	/**
	 * 
	 * 更新配置项列表
	 *
	 * @author liujun
	 * @created 2017年1月10日
	 *
	 * @param jsonStr
	 * @return
	 */
	@RequestMapping("/updateItemList")
	@ResponseBody
	public DataTransferObject updateItemList(String jsonStr) {
		DataTransferObject dto  = new DataTransferObject();
		try {
			List<DicItemEntity> itemList = JsonEntityTransform.json2List(jsonStr, DicItemEntity.class);
			String resultJson = cityTemplateService.updateDicItemList(JsonEntityTransform.Object2Json(itemList));
			dto = JsonEntityTransform.json2DataTransferObject(resultJson);
		} catch (BusinessException e) {
			LogUtil.error(LOGGER, "updateItemList error:{}, jsonStr:{}", e, jsonStr);
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg(e.getMessage());
		}
		return dto;
	}
}
