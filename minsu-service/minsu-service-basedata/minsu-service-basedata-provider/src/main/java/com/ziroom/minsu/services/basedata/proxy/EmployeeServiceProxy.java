/**
 * @FileName: UserPermissionServiceImpl.java
 * @Package com.ziroom.minsu.services.basedata.service
 * 
 * @author bushujie
 * @created 2016年3月9日 上午11:14:06
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.basedata.proxy;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.MessageSourceUtil;
import com.asura.framework.cache.redisOne.RedisOperations;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.sys.EmployeeEntity;
import com.ziroom.minsu.services.basedata.api.inner.EmployeeService;
import com.ziroom.minsu.services.basedata.dto.EmployeeRequest;
import com.ziroom.minsu.services.basedata.service.EmployeeServiceImpl;
import com.ziroom.minsu.services.common.constant.MessageConst;
import com.ziroom.minsu.services.common.utils.RedisKeyConst;

/**
 * 
 * <p>员工操作service</p>
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
@Service("basedata.employeeServiceProxy")
public class EmployeeServiceProxy implements EmployeeService{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeServiceProxy.class);
	
	@Resource(name="basedata.employeeServiceImpl")
	private EmployeeServiceImpl employeeServiceImpl;
	
	@Resource(name = "basedata.messageSource")
	private MessageSource messageSource;

	@Autowired
	private RedisOperations redisOperations;
	
	/**
	 * 
	 * 根据员工系统号查询员工信息
	 *
	 * @author liujun
	 * @created 2016年7月7日
	 *
	 * @param empCode
	 * @return
	 */
	public String findEmployeeByEmpCode(String empCode){
		LogUtil.info(LOGGER, "empCode={}", empCode);
		DataTransferObject dto = new DataTransferObject();
		try{
			//非空校验
			if(Check.NuNStr(empCode)){
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
				return dto.toJsonString();
			}
			
			String key = RedisKeyConst.getEmpKey(empCode);
			String empJson= null;
			try {
				empJson = redisOperations.get(key);
			} catch (Exception e) {
				LogUtil.error(LOGGER, "redis错误,e:{}", e);
			}
			
			//判断缓存是否存在
			EmployeeEntity emp = null;
			if(Check.NuNStr(empJson)){
				emp = employeeServiceImpl.findEmployeeByEmpCode(empCode);
				if(!Check.NuNObj(emp)){
					try {
						redisOperations.setex(key, (int)TimeUnit.MINUTES.toSeconds(30), JsonEntityTransform.Object2Json(emp));
					} catch (Exception e) {
                        LogUtil.error(LOGGER, "redis错误,e:{}",e);
					}

				}
			}else{
				emp = JsonEntityTransform.json2Object(empJson, EmployeeEntity.class);
			}
			dto.putValue("employee", emp);
		}catch (Exception e){
			LogUtil.error(LOGGER, "error:{},empCode={}", e, empCode);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(e.getMessage());
			return dto.toJsonString();
		}
		LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}

	@Override
	public String findEmployeeByCondition(String paramJson) {
		LogUtil.info(LOGGER, "参数:{}", paramJson);
		DataTransferObject dto = new DataTransferObject();
		EmployeeRequest empRequest = JsonEntityTransform.json2Object(paramJson, EmployeeRequest.class);
		try{
			//非空校验
			if(Check.NuNObj(empRequest)){
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
				return dto.toJsonString();
			}
			List<EmployeeEntity> list = employeeServiceImpl.findEmployeeByCondition(empRequest);
			dto.putValue("list", list);
		}catch (Exception e){
			LogUtil.error(LOGGER, "error:{},params:{}", e, paramJson);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(e.getMessage());
			return dto.toJsonString();
		}
		LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}

	/* (non-Javadoc)
	 * @see com.ziroom.minsu.services.basedata.api.inner.EmployeeService#findEmployeByEmpFid(java.lang.String)
	 */
	@Override
	public String findEmployeByEmpFid(String empFid) {
		LogUtil.info(LOGGER, "empCode={}", empFid);
		DataTransferObject dto = new DataTransferObject();
		try{
			//非空校验
			if(Check.NuNStr(empFid)){
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
				return dto.toJsonString();
			}
			
			String key = RedisKeyConst.getEmpKey(empFid);
			String empJson= null;
			try {
				empJson = redisOperations.get(key);
			} catch (Exception e) {
				LogUtil.error(LOGGER, "redis错误,e:{}", e);
			}
			
			//判断缓存是否存在
			EmployeeEntity emp = null;
			if(Check.NuNStr(empJson)){
				emp = employeeServiceImpl.findEmployeeByEmpFid(empFid);
				if(!Check.NuNObj(emp)){
					try {
						redisOperations.setex(key, (int)TimeUnit.MINUTES.toSeconds(30), JsonEntityTransform.Object2Json(emp));
					} catch (Exception e) {
                        LogUtil.error(LOGGER, "redis错误,e:{}",e);
					}

				}
			}else{
				emp = JsonEntityTransform.json2Object(empJson, EmployeeEntity.class);
			}
			dto.putValue("employee", emp);
		}catch (Exception e){
			LogUtil.error(LOGGER, "error:{},empCode={}", e, empFid);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(e.getMessage());
			return dto.toJsonString();
		}
		LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}
}
