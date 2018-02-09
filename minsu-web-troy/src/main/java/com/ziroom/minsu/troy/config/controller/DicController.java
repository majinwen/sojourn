/**
 * @FileName: SysConfController.java
 * @Package com.ziroom.minsu.troy.conf
 * 
 * @author bushujie
 * @created 2016年3月22日 下午10:03:17
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.troy.config.controller;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.minsu.entity.conf.ConfDicEntity;
import com.ziroom.minsu.services.basedata.api.inner.CityTemplateService;
import com.ziroom.minsu.services.basedata.entity.TreeNodeVo;
import com.ziroom.minsu.services.common.page.PageResult;
import org.apache.commons.collections.CollectionUtils;
import org.codehaus.jackson.type.TypeReference;
import org.codehaus.plexus.util.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>系统配置项controller</p>
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
@RequestMapping("config/dic")
public class DicController {
	
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
	@RequestMapping("confItemTree")
	public void confItemTree(HttpServletRequest request){
		List<TreeNodeVo> treeList = getTreeVoList();
		request.setAttribute("treeview", JsonEntityTransform.Object2Json(treeList));
		request.setAttribute("staticResourceUrl", staticResourceUrl);
		
		//确认最顶层 默认设置
		TreeNodeVo treeNode = new TreeNodeVo();
		if(CollectionUtils.isNotEmpty(treeList)){
			treeNode.setId(treeList.get(0).getId());
			treeNode.setLevel(treeList.get(0).getLevel());
			treeNode.setText(treeList.get(0).getText());
			
			request.setAttribute("treeNode", treeNode);
		}
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
        String pfid = request.getParameter("pfid");
		if(StringUtils.isBlank(pfid)){
			pageResult.setTotal(0l);
			return pageResult;
		}
		String resultJson = cityTemplateService.getConfDicByPfid(pfid);

		DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(resultJson);
		List<ConfDicEntity> confDicList = resultDto.parseData("list", new TypeReference<List<ConfDicEntity>>() {
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
	public Map<String,Object> addData(@ModelAttribute ConfDicEntity confDic, HttpServletRequest request) {
        //保存 实体
		ConfDicEntity cde = new ConfDicEntity();
		cde.setFid(UUIDGenerator.hexUUID());
		cde.setDicLevel(confDic.getDicLevel()+1);
		
		if (StringUtils.isNotBlank(confDic.getPfid())) {
			cde.setPfid(confDic.getPfid());;
		}
		if (StringUtils.isNotBlank(confDic.getShowName())) {
			cde.setShowName(confDic.getShowName());
		}
		if (StringUtils.isNotBlank(confDic.getDicCode())) {
			cde.setDicCode(confDic.getDicCode());
		}
		
		cde.setDicType(confDic.getDicType());
		cde.setDicIndex(confDic.getDicIndex());;
		//向数据库保存数据
		String paramJson = JsonEntityTransform.Object2Json(cde);
		String resJson = cityTemplateService.insertConfDic(paramJson);
		System.out.println("-----------------------"+resJson);

		//保存后  更新列表左侧树
		List<TreeNodeVo> treeList = getTreeVoList();
		request.setAttribute("staticResourceUrl", staticResourceUrl);
		request.setAttribute("treeview", JsonEntityTransform.Object2Json(treeList));
		
		Map<String,Object> resMap= new HashMap<String,Object>(); 
		resMap.put("treeview", JsonEntityTransform.Object2Json(treeList));
		
		return resMap;
	}
	
	
	/**
	 * 
	 * 获取菜单树--左侧树--方法封装
	 *
	 * @author liyingjie
	 * @created 2016年3月23日 
	 *
	 */
	private List<TreeNodeVo> getTreeVoList(){
		String resultJson = cityTemplateService.getDicTree(null);
		DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(resultJson);
		List<TreeNodeVo> treeList=resultDto.parseData("list", new TypeReference<List<TreeNodeVo>>() {});
		return treeList;
	}
}
