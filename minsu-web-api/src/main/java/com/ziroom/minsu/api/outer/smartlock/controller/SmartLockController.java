/**
 * @FileName: SmartLockController.java
 * @Package com.ziroom.minsu.api.outer.smartlock.controller
 * 
 * @author liujun
 * @created 2016年6月23日
 * 
 * Copyright 2016 ziroom
 */
package com.ziroom.minsu.api.outer.smartlock.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.MessageSourceUtil;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.api.common.constant.ApiMessageConst;
import com.ziroom.minsu.api.common.dto.ResponseSecurityDto;
import com.ziroom.minsu.api.common.logic.ParamCheckLogic;
import com.ziroom.minsu.services.house.api.inner.HouseManageService;
import com.ziroom.minsu.services.house.dto.SmartLockDto;
import com.ziroom.minsu.services.order.api.inner.OrderLoadlordService;

/**
 * <p>智能锁管理</p>
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
@RequestMapping("/smartlock")
public class SmartLockController {
	
	/**
	 * 日志对象
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(SmartLockController.class);
	
	@Resource(name="house.houseManageService")
	private HouseManageService houseManageService;

	@Resource(name="order.orderLoadlordService")
	private OrderLoadlordService orderLoadlordService;
	
	@Resource(name="api.messageSource")
	private MessageSource messageSource;

	@Resource(name="api.paramCheckLogic")
	private ParamCheckLogic paramCheckLogic;
	
	@RequestMapping(value = "/${NO_LGIN_AUTH}/bindSmartLock", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<ResponseSecurityDto> bindSmartLock(HttpServletRequest request, @RequestBody SmartLockDto paramDto){
		String paramJson = JsonEntityTransform.Object2Json(paramDto);
		
		LogUtil.info(LOGGER, "参数:{}", paramJson);
		try{
			if (Check.NuNStr(paramDto.getHouseFid()) && Check.NuNCollection(paramDto.getRoomFidList())) {
				LogUtil.info(LOGGER, "error msg:{}", 
						MessageSourceUtil.getChinese(messageSource, ApiMessageConst.HOUSEFID_AND_ROOMFID_LIST_NULL));
				return new ResponseEntity<>(ResponseSecurityDto.responseUnEncryptFail(MessageSourceUtil
						.getChinese(messageSource, ApiMessageConst.HOUSEFID_AND_ROOMFID_LIST_NULL)), HttpStatus.OK);
			}

			String resultJson = houseManageService.bindSmartLock(paramJson);
			LogUtil.info(LOGGER, "结果:{}", resultJson);
			DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(resultJson);
			
			//订单快照同步智能锁信息
			if(dto.getCode() == DataTransferObject.SUCCESS){
				orderLoadlordService.installHouseLock(paramJson);
				return new ResponseEntity<>(ResponseSecurityDto.responseUnEncryptOK(dto.getData()), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(ResponseSecurityDto.responseUnEncryptFail(dto.getMsg()), HttpStatus.OK);
			}
			
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			return new ResponseEntity<>(ResponseSecurityDto.responseUnEncryptFail(e.getMessage()), HttpStatus.OK);
		}
	}
	
}
