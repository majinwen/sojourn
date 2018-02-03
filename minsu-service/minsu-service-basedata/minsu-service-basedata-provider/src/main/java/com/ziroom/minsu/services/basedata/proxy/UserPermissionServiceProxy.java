/**
 * @FileName: UserPermissionServiceImplProxy.java
 * @Package com.ziroom.minsu.services.basedata.proxy
 *
 * @author bushujie
 * @created 2016年3月9日 上午11:28:42
 *
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.basedata.proxy;

import javax.annotation.Resource;

import com.asura.framework.base.util.Check;
import com.ziroom.minsu.entity.sys.CurrentuserEntity;
import com.ziroom.minsu.services.basedata.dto.SaveUserInfoRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.MessageSourceUtil;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.sys.EmployeeEntity;
import com.ziroom.minsu.services.basedata.api.inner.UserPermissionService;
import com.ziroom.minsu.services.common.constant.MessageConst;
import com.ziroom.minsu.services.basedata.dto.CurrentuserRequest;
import com.ziroom.minsu.services.basedata.dto.EmployeeRequest;
import com.ziroom.minsu.services.basedata.entity.CurrentuserVo;
import com.ziroom.minsu.services.basedata.logic.ParamCheckLogic;
import com.ziroom.minsu.services.basedata.service.UserPermissionServiceImpl;

/**
 * <p>后台用户权限业务代理层</p>
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
@Component("basedata.userPermissionServiceProxy")
public class UserPermissionServiceProxy implements UserPermissionService{

	private static final Logger LOGGER = LoggerFactory.getLogger(UserPermissionServiceProxy.class);

	@Resource(name="basedata.userPermissionServiceImpl")
	private UserPermissionServiceImpl userPermissionServiceImpl;

	@Resource(name="basedata.messageSource")
	private MessageSource messageSource;

	@Resource(name="basedata.paramCheckLogic")
	private ParamCheckLogic paramCheckLogic;
	

	/**
	 * 修改用户信息
	 * @param userInfo
	 * @return
	 */
	public String saveUserInfo(SaveUserInfoRequest userInfo){
		DataTransferObject dto = new DataTransferObject();
		if(Check.NuNObj(userInfo)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
			return dto.toJsonString();
		}
		if(Check.NuNObj(userInfo.getFid())){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
			return dto.toJsonString();
		}
		CurrentuserEntity currentuserEntity = userPermissionServiceImpl.getCurrentuserByFid(userInfo.getFid());
		if(Check.NuNObj(currentuserEntity)){
			//校验当前用户是否存在
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.NOT_FOUND));
			return dto.toJsonString();
		}
		try{
			//userPermissionServiceImpl.saveUserInfo(userInfo);
		}catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
			return dto.toJsonString();
		}
		return dto.toJsonString();

	}


	/**
	 * 初始化当前的用户的 修改信息
	 * @param userFid
	 * @return
	 */
	public String initSaveUserInfo(String userFid){
		DataTransferObject dto = new DataTransferObject();
		if (Check.NuNStr(userFid)) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
			return dto.toJsonString();
		}
		try{
			CurrentuserEntity user = userPermissionServiceImpl.getCurrentuserByFid(userFid);
			if(Check.NuNObj(user)) {
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.NOT_FOUND));
				return dto.toJsonString();
			}
			SaveUserInfoRequest saveUserInfo = new SaveUserInfoRequest();
			BeanUtils.copyProperties(user,saveUserInfo);
			saveUserInfo.setCitys(userPermissionServiceImpl.getCityList());
			saveUserInfo.setRoles(userPermissionServiceImpl.getRoleListByUserFid(userFid));
			dto.putValue("userInfo", saveUserInfo);
		}catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
			return dto.toJsonString();
		}
		return dto.toJsonString();

	}







	/* (non-Javadoc)
	 * @see com.ziroom.minsu.services.basedata.api.inner.UserPermissionService#searchCurrentuserList(java.lang.String)
	 */
	@Override
	public String searchCurrentuserList(String paramJson) {
		DataTransferObject dto = null;
		try{
			CurrentuserRequest currentuserRequest=JsonEntityTransform.json2Object(paramJson, CurrentuserRequest.class);
            //条件查询后台用户
			PagingResult<CurrentuserVo> pr=userPermissionServiceImpl.findCurrentuserPageList(currentuserRequest);
            dto = new DataTransferObject();
            dto.putValue("list", pr.getRows());
            dto.putValue("total", pr.getTotal());
		}catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
            dto = new DataTransferObject();
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
            return dto.toJsonString();
		}
		return dto.toJsonString();
	}

	/* (non-Javadoc)
	 * @see com.ziroom.minsu.services.basedata.api.inner.UserPermissionService#employeePageList(java.lang.String)
	 */
	@Override
	public String employeePageList(String paramJson) {
		DataTransferObject dto = null;
		try{
			EmployeeRequest employeeRequest=JsonEntityTransform.json2Object(paramJson, EmployeeRequest.class);
            //条件查询员工列表
			PagingResult<EmployeeEntity> pr=userPermissionServiceImpl.findEmployeeForPage(employeeRequest);
            dto = new DataTransferObject();
            dto.putValue("list", pr.getRows());
            dto.putValue("total", pr.getTotal());
		}catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
            dto = new DataTransferObject();
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
            return dto.toJsonString();
		}
		return dto.toJsonString();
	}

}
