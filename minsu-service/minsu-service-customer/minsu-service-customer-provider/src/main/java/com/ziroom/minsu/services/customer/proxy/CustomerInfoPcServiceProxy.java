/**
 * @FileName: CustomerInfoPcServiceProxy.java
 * @Package com.ziroom.minsu.services.customer.proxy
 * 
 * @author bushujie
 * @created 2016年7月26日 下午2:59:46
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.customer.proxy;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.cache.redisOne.RedisOperations;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.customer.CustomerPicMsgEntity;
import com.ziroom.minsu.services.common.utils.RedisKeyConst;
import com.ziroom.minsu.services.customer.api.inner.CustomerInfoPcService;
import com.ziroom.minsu.services.customer.dto.CustomerBaseExtDto;
import com.ziroom.minsu.services.customer.entity.CustomerAuthVo;
import com.ziroom.minsu.services.customer.service.CustomerBaseMsgServiceImpl;
import com.ziroom.minsu.services.customer.service.CustomerPicMsgServiceImpl;

/**
 * <p>pc端相关接口代理层</p>
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
@Component("customer.customerInfoPcServiceProxy")
public class CustomerInfoPcServiceProxy implements CustomerInfoPcService{
		
	private static final Logger LOGGER = LoggerFactory.getLogger(CustomerInfoPcServiceProxy.class);
	
	@Autowired
	private RedisOperations redisOperations;
	
	@Resource(name="customer.customerBaseMsgServiceImpl")
	private CustomerBaseMsgServiceImpl customerBaseMsgServiceImpl;
	
	@Resource(name="customer.customerPicMsgServiceImpl")
	private CustomerPicMsgServiceImpl customerPicMsgServiceImpl;

	/* (non-Javadoc)
	 * @see com.ziroom.minsu.services.customer.api.inner.CustomerInfoPcService#updatePersonData(java.lang.String)
	 */
	@Override
	public String updatePersonData(String paramJson) {
		DataTransferObject dto=new DataTransferObject();
		CustomerBaseExtDto customerBaseExtDto=JsonEntityTransform.json2Object(paramJson, CustomerBaseExtDto.class);
		int upNum=customerBaseMsgServiceImpl.updatePersonDataForPc(customerBaseExtDto);
		dto.putValue("upNum", upNum);
		 //删除redis
        try {
            redisOperations.del(RedisKeyConst.getCutomerKey(customerBaseExtDto.getUid()));
        } catch (Exception e) {
            LogUtil.error(LOGGER, "redis错误,e:{}",e);
        }
		return dto.toJsonString();
	}

	/* (non-Javadoc)
	 * @see com.ziroom.minsu.services.customer.api.inner.CustomerInfoPcService#initCustomerAuthData(java.lang.String)
	 */
	@Override
	public String initCustomerAuthData(String uid) {
		DataTransferObject dto=new DataTransferObject();
		CustomerAuthVo customerAuthVo=customerBaseMsgServiceImpl.getCustomerAuthDataForPc(uid);
		dto.putValue("customerAuthVo", customerAuthVo);
		return dto.toJsonString();
	}

	/* (non-Javadoc)
	 * @see com.ziroom.minsu.services.customer.api.inner.CustomerInfoPcService#updateCustomerAuthData(java.lang.String)
	 */
	@Override
	public String updateCustomerAuthData(String paramJson) {
		DataTransferObject dto=new DataTransferObject();
		CustomerAuthVo customerAuthVo=JsonEntityTransform.json2Object(paramJson, CustomerAuthVo.class);
		List<CustomerPicMsgEntity> picList=customerBaseMsgServiceImpl.updateCustomerAuthData(customerAuthVo);
		
		 //删除redis
        try {
            redisOperations.del(RedisKeyConst.getCutomerKey(customerAuthVo.getUid()));
        } catch (Exception e) {
            LogUtil.error(LOGGER, "redis错误,e:{}",e);
        }
		dto.putValue("picList", picList);
		return dto.toJsonString();
	}
}
