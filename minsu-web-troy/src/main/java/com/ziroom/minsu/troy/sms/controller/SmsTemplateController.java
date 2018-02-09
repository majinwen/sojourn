/**
 * @FileName: SmsTemplateController.java
 * @Package com.ziroom.minsu.troy.system.controller
 * 
 * @author yd
 * @created 2016年4月1日 下午3:17:23
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.troy.sms.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.conf.SmsTemplateEntity;
import com.ziroom.minsu.services.basedata.api.inner.SmsTemplateService;
import com.ziroom.minsu.services.basedata.dto.SmsTemplateRequest;
import com.ziroom.minsu.services.common.page.PageResult;

/**
 * <p>TODO</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author yd
 * @since 1.0
 * @version 1.0
 */
@Controller
@RequestMapping("sms")
public class SmsTemplateController {
	
	
	/**
	 * 日志对象
	 */
	private static Logger logger = LoggerFactory.getLogger(SmsTemplateController.class);

	@Resource(name="basedata.smsTemplateService")
	private SmsTemplateService smsTemplateService;
	
	
	/**
	 * 
	 * 条件分页查询短信模板
	 *
	 * @author yd
	 * @created 2016年4月1日 下午3:23:58
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping("querySmsByPage")
	public @ResponseBody PageResult querySmsByPage(HttpServletRequest request,@ModelAttribute("smsTemplateRequest") SmsTemplateRequest smsTemplateRequest){
		
		
		DataTransferObject dto =JsonEntityTransform.json2DataTransferObject( smsTemplateService.findEntityByCondition(JsonEntityTransform.Object2Json(smsTemplateRequest)));
		 //返回结果
		PageResult pageResult = new PageResult();
		pageResult.setRows(dto.getData().get("listSmsTemplate"));
		pageResult.setTotal(Long.parseLong(dto.getData().get("total").toString()));
		return pageResult;
	}
	
	/**
	 * 
	 * 添加短信模板
	 *
	 * @author yd
	 * @created 2016年4月2日 上午10:29:18
	 *
	 * @param request
	 */
	@RequestMapping("saveSmsTeplate")
	public String saveSmsTeplate(HttpServletRequest request,@ModelAttribute SmsTemplateEntity smsTemplateEntity) {
		
		if(smsTemplateEntity !=null){
			if(StringUtils.isNotBlank(smsTemplateEntity.getSmsCode())&&StringUtils.isNotBlank(smsTemplateEntity.getSmsName())&&StringUtils.isNotBlank(smsTemplateEntity.getSmsComment())){
				DataTransferObject dto =  null;
				
				if(!Check.NuNStr(smsTemplateEntity.getFid())){
				   dto = JsonEntityTransform.json2DataTransferObject(this.smsTemplateService.updateEntityByFid(JsonEntityTransform.Object2Json(smsTemplateEntity)));
				}else{
					dto = JsonEntityTransform.json2DataTransferObject(smsTemplateService.findEntityBySmsCode(smsTemplateEntity.getSmsCode()));
					SmsTemplateEntity smsEntity = dto.parseData("smsTemplateEntity", new TypeReference<SmsTemplateEntity>() {});
					//smscode不能重复
					if(smsEntity != null){
						return "redirect:/sms/addSmsTeplate";
					}
					dto = JsonEntityTransform.json2DataTransferObject(this.smsTemplateService.saveEntity(JsonEntityTransform.Object2Json(smsTemplateEntity)));
				}
				String result = (String) dto.getData().get("result").toString();
				if(Integer.parseInt(result)>0){
					LogUtil.info(logger,"保存成功");
				}else{
					LogUtil.info(logger,"保存失败");
				}
			}else{
				LogUtil.info(logger,"保存失败,请检查短信名称，编码，和内容");
			}
			
		}
		return "redirect:/sms/findSmsByPage";
	}
	
	/**
	 * 
	 * 删除短信模板
	 *
	 * @author yd
	 * @created 2016年4月2日 上午10:29:18
	 *
	 * @param request
	 */
	@RequestMapping("deleteSmsTeplate")
	public String deleteSmsTeplate(HttpServletRequest request) {
		
		String smsFid = request.getParameter("smsFid");
		
		if(StringUtils.isNotBlank(smsFid)){
			DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(this.smsTemplateService.deleteEntityById(JsonEntityTransform.Object2Json(smsFid)));
			String result = (String) dto.getData().get("result").toString();
			if(Integer.parseInt(result)>0){
				LogUtil.info(logger,"删除成功");
			}else{
				LogUtil.info(logger,"删除失败");
			}
		}
		
		return "redirect:/sms/findSmsByPage";
	}
	/**
	 * 到短信模板管理页面
	 *
	 * @author yd
	 * @created 2016年4月1日 下午9:00:57
	 *
	 */
	@RequestMapping("findSmsByPage")
	public void findSmsByPage(){}
	/**
	 * 到短信模板添加页面或者编辑页面
	 *
	 * @author yd
	 * @created 2016年4月1日 下午9:00:57
	 *
	 */
	@RequestMapping("addSmsTeplate")
	public void addSmsTeplate(HttpServletRequest request){
		
		String editFlag = request.getParameter("editFlag")==null?null:request.getParameter("editFlag");//修改标志 1=修改  其他是添加
		
		if(editFlag!=null&&"1".equals(editFlag)){
			String smsFid =  request.getParameter("smsFid");
			if(StringUtils.isNotBlank(smsFid)){
				DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(this.smsTemplateService.findEntityByFid(JsonEntityTransform.Object2Json(smsFid)));
				request.setAttribute("smsTemplateEntity", dto.getData().get("smsTemplateEntity"));
			}
		}
		
	}
	/**
	 * 
	 * 启用短信模板
	 *
	 * @author yd
	 * @created 2016年4月2日 下午2:53:55
	 *
	 * @param request
	 */
	@RequestMapping("enabledSms")
	public String enabledSms(HttpServletRequest request){
		
		String smsFid =  request.getParameter("smsFid");
		if(StringUtils.isNotBlank(smsFid)){
			DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(this.smsTemplateService.findEntityByFid(JsonEntityTransform.Object2Json(smsFid)));
			SmsTemplateEntity smsTemplateEntity =dto.parseData("smsTemplateEntity", new TypeReference<SmsTemplateEntity>() {
			});
			if(!Check.NuNObj(smsTemplateEntity)){
				if(smsTemplateEntity.getSmsEnabled().intValue() == 0){
					smsTemplateEntity.setSmsEnabled(1);
					DataTransferObject dt = JsonEntityTransform.json2DataTransferObject(smsTemplateService.updateEntityByFid(JsonEntityTransform.Object2Json(smsTemplateEntity)));
					String result = (String) dt.getData().get("result").toString();
					if(Integer.parseInt(result)>0){
						LogUtil.info(logger,"启用成功");
					}else{
						LogUtil.info(logger,"启用失败");
					}
				}
			}
		
		}
		
		return "redirect:/sms/findSmsByPage";
	}
}
