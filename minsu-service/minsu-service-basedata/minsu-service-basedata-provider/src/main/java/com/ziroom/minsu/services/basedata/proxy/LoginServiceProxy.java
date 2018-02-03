/**
 * @FileName: LoginServiceProxy.java
 * @Package com.ziroom.minsu.services.basedata.proxy
 * 
 * @author bushujie
 * @created 2016年3月16日 下午8:14:27
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.basedata.proxy;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.MessageSourceUtil;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.sys.CurrentuserEntity;
import com.ziroom.minsu.services.basedata.api.inner.LoginService;
import com.ziroom.minsu.services.common.constant.MessageConst;
import com.ziroom.minsu.services.basedata.entity.ResourceVo;
import com.ziroom.minsu.services.basedata.logic.ParamCheckLogic;
import com.ziroom.minsu.services.basedata.service.ResourceServiceImpl;
import com.ziroom.minsu.services.basedata.service.UserPermissionServiceImpl;

/**
 * <p>登录相关接口代理</p>
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
@Component("basedata.loginServiceProxy")
public class LoginServiceProxy implements LoginService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LoginServiceProxy.class);
	
	@Resource(name="basedata.userPermissionServiceImpl")
	private UserPermissionServiceImpl userPermissionServiceImpl;
	
	@Resource(name="basedata.resourceServiceImpl")
	private ResourceServiceImpl resourceServiceImpl;
	
	@Resource(name="basedata.messageSource")
	private MessageSource messageSource;

	@Resource(name="basedata.paramCheckLogic")
	private ParamCheckLogic paramCheckLogic;

	/* (non-Javadoc)
	 * @see com.ziroom.minsu.services.basedata.api.inner.LoginService#getCurrentuserInfo(java.lang.String)
	 */
	@Override
	public String getCurrentuserInfo(String userAccount) {
		DataTransferObject dto = new DataTransferObject();
		try{
			//json参数转换
			if(StringUtils.isBlank(userAccount)){
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
				return dto.toJsonString();
			}
			CurrentuserEntity currentuserEntity = userPermissionServiceImpl.getCurrentuserByAccount(userAccount);
			
			dto.putValue("userInfo", currentuserEntity);
		}catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
			return dto.toJsonString();
		}
		return dto.toJsonString();
	}

	/* (non-Javadoc)
	 * @see com.ziroom.minsu.services.basedata.api.inner.LoginService#currentuserReslist(java.lang.String)
	 */
	@Override
	public String currentuserReslist(String currentuserId) {
		DataTransferObject dto = new DataTransferObject();
		try{
			//json参数转换
			if(StringUtils.isBlank(currentuserId)){
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
				return dto.toJsonString();
			}
			List<ResourceVo> currentuserResList=resourceServiceImpl.findCurrentuserResList(currentuserId);
			dto.putValue("list", currentuserResList);
		}catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
			return dto.toJsonString();
		}
		return dto.toJsonString();
	}
	
	/**
	 * 
	 * 根据url 校验当前菜单是否是特权菜单
	 * 异常情况：一个url对应多个菜单，只要有一个是特权菜单，就默认所有都是特权菜单（此情况，在前台添加菜单时候没做校验，故后期得加上）
	 *
	 *  0=不是特权菜单  1=是特权菜单
	 * @author yd
	 * @created 2016年11月1日 上午9:21:41
	 *
	 * @param resurl
	 * @return
	 */
	@Override
	public String  checkAuthMenuByResurl(String resUrl){
		
		DataTransferObject dto = new DataTransferObject();
		
		if(Check.NuNStrStrict(resUrl)){
			LogUtil.error(LOGGER, "当前菜单权限查询，地址为空值resUrl={}", resUrl);
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("参数错误");
			return dto.toJsonString();
		}
		
		LogUtil.info(LOGGER, "当前菜单：resUrl={}", resUrl);
		
		int i = this.resourceServiceImpl.checkAuthMenuByResurl(resUrl);
		
		dto.putValue("menuAuth", i);
		return dto.toJsonString();
	}
	
}
