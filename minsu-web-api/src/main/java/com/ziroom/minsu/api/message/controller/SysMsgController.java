package com.ziroom.minsu.api.message.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.api.common.constant.ConstDef;
import com.ziroom.minsu.api.common.dto.BaseParamDto;
import com.ziroom.minsu.api.common.dto.ResponseSecurityDto;
import com.ziroom.minsu.api.message.dto.SysMsgParamDto;
import com.ziroom.minsu.services.message.api.inner.SysMsgService;
import com.ziroom.minsu.services.message.dto.SysMsgRequest;
import com.ziroom.minsu.valenum.msg.IsDelEnum;

/**
 * 
 * <p>系统消息api</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author jixd
 * @since 1.0
 * @version 1.0
 */
@Controller
@RequestMapping("auth/sysmsg")
public class SysMsgController {
	
	@Resource(name="message.sysMsgService")
	private SysMsgService sysMsgService;
	
	/**
	 * 日志对象
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(SysMsgController.class);
	
	/**
	 * 
	 * 获取消息列表
	 * 
	 * <p>
	 * 	  查询系统消息列表，需要参数
	 * 
	 * param	 {'uid':'2232355555','page':1,'limit':50}
	 * result    {"status": "0","message": "", "data": {"total": 3,"rows": [ ]}}  
	 * 
	 * </p>
	 *
	 * @author jixd
	 * @created 2016年4月21日 下午3:12:23
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value="list",method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<ResponseSecurityDto> userSysMsgList(HttpServletRequest request){
		try{
			String paramJson = (String) request.getAttribute(ConstDef.DECRYPT_PARAM_ATTRIBUTE);
			BaseParamDto baseParamDto=JsonEntityTransform.json2Object(paramJson,BaseParamDto.class);
			SysMsgRequest sysMsgRequest = new SysMsgRequest();
			sysMsgRequest.setIsDel(IsDelEnum.NOT_DEL.getCode());
			sysMsgRequest.setLimit(baseParamDto.getLimit());
			sysMsgRequest.setMsgTargetUid(baseParamDto.getUid());
			sysMsgRequest.setPage(baseParamDto.getPage());
			String querySysMsg = sysMsgService.querySysMsg(JsonEntityTransform.Object2Json(sysMsgRequest));
			DataTransferObject dto=JsonEntityTransform.json2DataTransferObject(querySysMsg);
			/*	dto.putValue("sysMsgList", pageResult.getRows());
		dto.putValue("total", pageResult.getTotal());*/
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptOK(dto.getData()), HttpStatus.OK);
		}catch(Exception e){
			LogUtil.error(LOGGER, "error:{}", e);
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("服务错误"),HttpStatus.OK);
		}
	}
	/**
	 * 
	 * 删除系统消息
	 *<p>
	 *	param	 {'sysMsgFid':'8a9e9ab45436c7fb015436c7fb610001'}
	 * 	result    {"status": "0","message": ""} 
	 *</p>
	 * @author jixd
	 * @created 2016年4月21日 下午3:12:03
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value="del",method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<ResponseSecurityDto> deleteSysMsg(HttpServletRequest request){
		try{
			String paramJson = (String) request.getAttribute(ConstDef.DECRYPT_PARAM_ATTRIBUTE);
			SysMsgParamDto sysMsgParamDto=JsonEntityTransform.json2Object(paramJson,SysMsgParamDto.class);
			String sysMsgFid = sysMsgParamDto.getSysMsgFid();
			String querySysMsg = sysMsgService.deleteSysMsg(sysMsgFid);
			DataTransferObject dto=JsonEntityTransform.json2DataTransferObject(querySysMsg);
			int result = dto.parseData("result", new TypeReference<Integer>() {});
			if(result > 0){
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptOK(dto.getData()), HttpStatus.OK);
			}else{
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("删除失败"), HttpStatus.OK);
			}
		}catch(Exception e){
			LogUtil.error(LOGGER, "error:{}", e);
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("服务错误"), HttpStatus.OK);
		}
		
	}
	/**
	 * 
	 * 更新系统消息为已读状态
	 *
	 *<p>
	 *	param	 {'sysMsgFid':'8a9e9ab45436c7fb015436c7fb610001'}
	 * 	result    {"status": "0","message": ""} 
	 *</p>
	 * @author jixd
	 * @created 2016年4月21日 下午4:14:35
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value="read",method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<ResponseSecurityDto> updateSysMsgRead(HttpServletRequest request){
		try{
			String paramJson = (String) request.getAttribute(ConstDef.DECRYPT_PARAM_ATTRIBUTE);
			SysMsgParamDto sysMsgParamDto=JsonEntityTransform.json2Object(paramJson,SysMsgParamDto.class);
			String sysMsgFid = sysMsgParamDto.getSysMsgFid();
			String querySysMsg = sysMsgService.updateSysMsgRead(sysMsgFid);
			DataTransferObject dto=JsonEntityTransform.json2DataTransferObject(querySysMsg);
			int result = dto.parseData("result", new TypeReference<Integer>() {});
			if(result > 0){
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptOK(dto.getData()), HttpStatus.OK);
			}else{
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("删除失败"), HttpStatus.OK);
			}
		}catch(Exception e){
			LogUtil.error(LOGGER, "error:{}", e);
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("服务错误"), HttpStatus.OK);
		}
		
	}
}
