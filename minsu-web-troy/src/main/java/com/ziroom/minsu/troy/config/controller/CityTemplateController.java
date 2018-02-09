package com.ziroom.minsu.troy.config.controller;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.minsu.entity.conf.CityTemplateEntity;
import com.ziroom.minsu.entity.conf.ConfCityEntity;
import com.ziroom.minsu.entity.conf.TemplateEntity;
import com.ziroom.minsu.services.basedata.api.inner.CityTemplateService;
import com.ziroom.minsu.services.basedata.api.inner.ConfCityService;
import com.ziroom.minsu.services.basedata.dto.ConfCityRequest;
import com.ziroom.minsu.services.basedata.entity.TemplateEntityVo;
import com.ziroom.minsu.services.basedata.entity.TreeNodeVo;
import com.ziroom.minsu.services.common.dto.PageRequest;
import com.ziroom.minsu.services.common.page.PageResult;
import org.apache.commons.collections.CollectionUtils;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>配置</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/3/12.
 * @version 1.0
 * @since 1.0
 */
@Controller
@RequestMapping("/config/cityTemplate")
public class CityTemplateController {
	@Value("#{'${minsu.static.resource.url}'.trim()}")
	private String staticResourceUrl;


	@Resource(name = "basedata.confCityService")
	private ConfCityService confCityService;

	@Resource(name = "basedata.cityTemplateService")
	private CityTemplateService cityTemplateService;

	/**
	 * 获取模板列表
	 * @param request
	 */
	@RequestMapping(value = "/cityTemplate", method = RequestMethod.GET)
	public void toResourceList(HttpServletRequest request) {
		List<TreeNodeVo> treeList = getTreeVoList();
		request.setAttribute("staticResourceUrl", staticResourceUrl);
		request.setAttribute("treeview", JsonEntityTransform.Object2Json(treeList));
		//查询最顶层菜单
		ConfCityRequest paramRequest = new ConfCityRequest();
		paramRequest.setLevel(0);
		String resultJson = confCityService.searchNodeListByFid(JsonEntityTransform.Object2Json(paramRequest));
		DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(resultJson);
		List<ConfCityEntity> confCityList = resultDto.parseData("list", new TypeReference<List<ConfCityEntity>>() {
		});
		if(CollectionUtils.isNotEmpty(confCityList)){
			request.setAttribute("confCity", confCityList.get(0));
		}

	}


	/**
	 * 获取模板列表
	 * @param pageRequest
	 * @param request
	 * @param cityCode
	 * @return
	 */
	@RequestMapping("/templateList")
	public @ResponseBody PageResult templateList(@ModelAttribute("paramRequest") PageRequest pageRequest,HttpServletRequest request,String cityCode) {

		String resultJson = cityTemplateService.getTemplateListByPage(JsonEntityTransform.Object2Json(pageRequest));

		DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(resultJson);
		List<TemplateEntityVo> templateEntityVoList = resultDto.parseData("list", new TypeReference<List<TemplateEntityVo>>() {
		});
		request.setAttribute("staticResourceUrl", staticResourceUrl);
		//返回结果
		PageResult pageResult = new PageResult();
		pageResult.setRows(templateEntityVoList);
		pageResult.setTotal(Long.valueOf(resultDto.getData().get("total").toString()));
		return pageResult;
	}

	/**
	 * 绑定信息
	 * @param request
	 * @param templateFid
	 * @param cityCode
	 * @return
	 */
	@RequestMapping("/bindInfo")
	@ResponseBody
	public String bindInfo(HttpServletRequest request,String templateFid,String cityCode) {

		CityTemplateEntity cityTemplateEntity = new CityTemplateEntity();
		cityTemplateEntity.setCityCode(cityCode);
		cityTemplateEntity.setTemplateFid(templateFid);
		return cityTemplateService.insertCityTemplate(JsonEntityTransform.Object2Json(cityTemplateEntity));
	}


	/**
	 * 绑定信息
	 * @param request
	 * @param templateEntity
	 * @return
	 */
	@RequestMapping("/addTemplate")
	@ResponseBody
	public String addTemplate(HttpServletRequest request,TemplateEntity templateEntity) {
		return cityTemplateService.insertTemplate(JsonEntityTransform.Object2Json(templateEntity));
	}

	/**
	 * 绑定信息
	 * @param request
	 * @param cityCode
	 * @return
	 */
	@RequestMapping("/getCityInfo")
	@ResponseBody
	public String getCityInfo(HttpServletRequest request,String cityCode) {

		String resultJson = cityTemplateService.getCityTemplateByCityCode(cityCode);
		return resultJson;
	}


	/**
	 * 初始化树信息
	 * @return
	 */
	private List<TreeNodeVo> getTreeVoList(){
		String resultJson = confCityService.confCityOnlyTreeVo();
		DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(resultJson);
		List<TreeNodeVo> treeList = resultDto.parseData("list", new TypeReference<List<TreeNodeVo>>() {
		});
		return treeList;
	}


}
