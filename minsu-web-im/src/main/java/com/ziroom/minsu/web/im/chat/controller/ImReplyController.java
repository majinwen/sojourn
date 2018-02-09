/**
 * @FileName: ImAppController.java
 * @Package com.ziroom.minsu.web.im.chat.controller
 * 
 * @author yd
 * @created 2016年9月18日 下午11:11:02
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.web.im.chat.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.exception.SOAParseException;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.DateUtil;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.MessageSourceUtil;
import com.asura.framework.base.util.SOAResParseUtil;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.message.MsgCustomizationEntity;
import com.ziroom.minsu.services.common.constant.MessageConst;
import com.ziroom.minsu.services.message.api.inner.MsgBaseService;
import com.ziroom.minsu.services.message.api.inner.MsgCustomizationService;
import com.ziroom.minsu.services.message.dto.AppChatRecordsExt;
import com.ziroom.minsu.services.message.dto.MsgHouseRequest;
import com.ziroom.minsu.services.message.entity.AppMsgBaseVo;
import com.ziroom.minsu.services.message.entity.MsgCustomizationVo;
import com.ziroom.minsu.valenum.common.UserTypeEnum;
import com.ziroom.minsu.valenum.common.YesOrNoEnum;
import com.ziroom.minsu.valenum.msg.IsDelEnum;
import com.ziroom.minsu.valenum.msg.MsgTypeEnum;
import com.ziroom.minsu.web.im.chat.controller.dto.ChatRecordsRequest;
import com.ziroom.minsu.web.im.chat.controller.dto.MsgCustomizationRequest;
import com.ziroom.minsu.web.im.chat.controller.entity.MsgBaseAppVo;
import com.ziroom.minsu.web.im.common.constant.LoginAuthConst;
import com.ziroom.minsu.web.im.common.controller.abs.AbstractController;
import com.ziroom.minsu.web.im.common.utils.DtoToResponseDto;


/**
 * <p>IM回复接口</p>
 *
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author lunan
 * @since 1.0
 * @version 1.0
 */
@Controller
@RequestMapping("imReply")
public class ImReplyController extends AbstractController{


	/**
	 * 日志对象
	 */
	private static Logger logger = LoggerFactory.getLogger(ImReplyController.class);


	@Resource(name = "im.messageSource")
	private MessageSource messageSource;

	@Resource(name = "message.msgCustomizationService")
	private MsgCustomizationService msgCustomizationService;
	

	@Resource(name = "message.msgBaseService")
	private MsgBaseService msgBaseService;

	/**
	 *
	 * 查询用户自定义回复
	 * @author lunan
	 * @created 2017年3月29日
	 *
	 * @param request response
	 * @return
	 */
	@RequestMapping("${LOGIN_UNAUTH}/queryCustomizationByUid")
	@ResponseBody
	public void queryFriendsUid(HttpServletRequest request,HttpServletResponse response,MsgCustomizationRequest customRequest){
		try{
			DataTransferObject dto = new  DataTransferObject();
			if(Check.NuNObj(customRequest)){
				LogUtil.error(logger, "查询用户自定义消息，参数错误customRequest={}", customRequest == null?"":customRequest);
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
				DtoToResponseDto.getResponseMsg(response, DtoToResponseDto.responseEncrypt(dto));
				return ;
			}
			if(Check.NuNStr(customRequest.getUid())){
				LogUtil.error(logger, "查询用户自定义消息，未传入uid={}", null);
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
				DtoToResponseDto.getResponseMsg(response, DtoToResponseDto.responseEncrypt(dto));
				return ;
			}
			String resultJson = msgCustomizationService.queryMsgCustomizationByUid(customRequest.getUid());
			DataTransferObject dtoResult = JsonEntityTransform.json2DataTransferObject(resultJson);
			if(dtoResult.getCode()==DataTransferObject.ERROR){
				LogUtil.error(logger, "未查询到此用户相关，uid={}", customRequest.getUid());
				dtoResult.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dtoResult.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
				DtoToResponseDto.getResponseMsg(response, DtoToResponseDto.responseEncrypt(dtoResult));
				return ;
			}
			List<MsgCustomizationVo> list = SOAResParseUtil.getListValueFromDataByKey(resultJson, "list", MsgCustomizationVo.class);
			dto.putValue("listMsg",list);
			DtoToResponseDto.getResponseMsg(response, DtoToResponseDto.responseEncrypt(dto));
		}catch (Exception e){
			LogUtil.error(logger, "查询用户自定义回复e={}", e);
		}
	}

	/**
	 *
	 * 添加用户自定义回复
	 * @author lunan
	 * @created 2017年3月30日
	 *
	 * @param request response
	 * @return
	 */
	@RequestMapping("${LOGIN_UNAUTH}/addCustomization")
	@ResponseBody
	public void addCustomization(HttpServletRequest request,HttpServletResponse response,MsgCustomizationVo msgCustomizationVo){
		DataTransferObject dto = null;
		try{

			if(!Check.NuNObj(msgCustomizationVo) && !Check.NuNStr(msgCustomizationVo.getUid())){
				if(Check.NuNStr(msgCustomizationVo.getContent())){
					dto = new DataTransferObject();
					dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
					dto.setMsg("新增的内容为空");
					DtoToResponseDto.getResponseMsg(response, DtoToResponseDto.responseEncrypt(dto));
					return ;
				}
				MsgCustomizationEntity msgCustom = new MsgCustomizationEntity();
				msgCustom.setContent(msgCustomizationVo.getContent());
				msgCustom.setUid(msgCustomizationVo.getUid());
				msgCustom.setMsgType(MsgTypeEnum.CUSTOM_USER.getCode());
				String resultJson = msgCustomizationService.addMsgCustomization(JsonEntityTransform.Object2Json(msgCustom));
				dto = JsonEntityTransform.json2DataTransferObject(resultJson);
				String result = (String) dto.getData().get("result").toString();
				if(Check.NuNStr(result)){
					dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
					dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
					DtoToResponseDto.getResponseMsg(response, DtoToResponseDto.responseEncrypt(dto));
					return ;
				}
				if(Integer.parseInt(result)>0){
					LogUtil.info(logger,"保存成功");
					dto.putValue("ok", YesOrNoEnum.YES.getCode());
				}else{
					LogUtil.info(logger,"保存失败");
					dto.putValue("ok",YesOrNoEnum.NO.getCode());
				}
			}else{
				dto = new DataTransferObject();
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
			}
			DtoToResponseDto.getResponseMsg(response, DtoToResponseDto.responseEncrypt(dto));
		}catch (Exception e){
			LogUtil.error(logger, "添加用户自定义回复e={}", e);
		}

	}

	/**
	 *
	 * 删除用户自定义回复
	 * @author lunan
	 * @created 2017年3月30日
	 *
	 * @param request response
	 * @return
	 */
	@RequestMapping("${LOGIN_UNAUTH}/deleteCustomization")
	@ResponseBody
	public void deleteCustomization(HttpServletRequest request,HttpServletResponse response,MsgCustomizationVo msgCustomizationVo){
		DataTransferObject dto = null;
		try{
			if(!Check.NuNObj(msgCustomizationVo) && !Check.NuNStr(msgCustomizationVo.getFid()) && !Check.NuNStr(msgCustomizationVo.getUid())){
				MsgCustomizationEntity msgCustom = new MsgCustomizationEntity();
				msgCustom.setUid(msgCustomizationVo.getUid());
				msgCustom.setFid(msgCustomizationVo.getFid());
				msgCustom.setIsDel(IsDelEnum.DEL.getCode());
				String resultJson = msgCustomizationService.updateMsgCustomization(JsonEntityTransform.Object2Json(msgCustom));
				dto = JsonEntityTransform.json2DataTransferObject(resultJson);
				String result = (String) dto.getData().get("result").toString();
				if(Check.NuNStr(result)){
					dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
					dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
					DtoToResponseDto.getResponseMsg(response, DtoToResponseDto.responseEncrypt(dto));
					return ;
				}
				if(Integer.parseInt(result)>0){
					LogUtil.info(logger,"删除成功");
					dto.putValue("ok", YesOrNoEnum.YES.getCode());
				}else{
					LogUtil.info(logger,"删除失败");
					dto.putValue("ok",YesOrNoEnum.NO.getCode());
				}
			}else{
				dto = new DataTransferObject();
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
			}
			DtoToResponseDto.getResponseMsg(response, DtoToResponseDto.responseEncrypt(dto));
		}catch (Exception e){
			LogUtil.error(logger, "删除用户自定义回复e={}", e);
		}
	}

	/**
	 *
	 * 编辑用户自定义回复
	 * @author lunan
	 * @created 2017年3月30日
	 *
	 * @param request response
	 * @return
	 */
	@RequestMapping("${LOGIN_UNAUTH}/updateCustomization")
	@ResponseBody
	public void updateCustomization(HttpServletRequest request,HttpServletResponse response,MsgCustomizationVo msgCustomizationVo){
		DataTransferObject dto = null;
		try{
			if(!Check.NuNObj(msgCustomizationVo) && !Check.NuNStr(msgCustomizationVo.getFid()) && !Check.NuNStr(msgCustomizationVo.getUid()) && !Check.NuNStr(msgCustomizationVo.getContent())){
				MsgCustomizationEntity msgCustom = new MsgCustomizationEntity();
				msgCustom.setUid(msgCustomizationVo.getUid());
				msgCustom.setFid(msgCustomizationVo.getFid());
				msgCustom.setContent(msgCustomizationVo.getContent());
				String resultJson = msgCustomizationService.updateMsgCustomization(JsonEntityTransform.Object2Json(msgCustom));
				dto = JsonEntityTransform.json2DataTransferObject(resultJson);
				String result = (String) dto.getData().get("result").toString();
				if(Check.NuNStr(result)){
					dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
					dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
					DtoToResponseDto.getResponseMsg(response, DtoToResponseDto.responseEncrypt(dto));
					return ;
				}
				if(Integer.parseInt(result)>0){
					LogUtil.info(logger,"编辑成功");
					dto.putValue("ok", YesOrNoEnum.YES.getCode());
				}else{
					LogUtil.info(logger,"编辑失败");
					dto.putValue("ok",YesOrNoEnum.NO.getCode());
				}

			}else{
				dto = new DataTransferObject();
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
			}
			DtoToResponseDto.getResponseMsg(response, DtoToResponseDto.responseEncrypt(dto));
		}catch (Exception e){
			LogUtil.error(logger, "编辑用户自定义回复e={}", e);
		}
	}

}
