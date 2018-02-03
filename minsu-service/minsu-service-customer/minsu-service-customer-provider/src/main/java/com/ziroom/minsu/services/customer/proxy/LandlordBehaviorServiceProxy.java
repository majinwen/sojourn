/**
 * @FileName: CustomerInfoServiceProxy.java
 * @Package com.ziroom.minsu.services.customer.proxy
 * 
 * @author bushujie
 * @created 2016年4月25日 下午12:06:11
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.customer.proxy;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.MessageSourceUtil;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.customer.LandlordBehaviorEntity;
import com.ziroom.minsu.entity.customer.LandlordStatisticsEntity;
import com.ziroom.minsu.services.common.constant.MessageConst;
import com.ziroom.minsu.services.customer.api.inner.LandlordBehaviorService;
import com.ziroom.minsu.services.customer.api.inner.LandlordStaticsService;
import com.ziroom.minsu.services.customer.constant.CustomerMessageConst;
import com.ziroom.minsu.services.customer.service.LandlordBehaviorImpl;
import com.ziroom.minsu.services.customer.service.LandlordStaticsServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * <p>用户行为相关接口实现</p>
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
@Component("customer.landlordBehaviorServiceProxy")
public class LandlordBehaviorServiceProxy implements LandlordBehaviorService{

	private static final Logger LOGGER = LoggerFactory.getLogger(LandlordBehaviorServiceProxy.class);

	@Resource(name="customer.messageSource")
	private MessageSource messageSource;

    @Resource(name = "customer.landlordBehaviorImpl")
    private LandlordBehaviorImpl landlordBehaviorImpl;

	@Override
	public String findLandlordBehavior(String uid) {
		DataTransferObject dto = new DataTransferObject();
		if (Check.NuNStr(uid)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("参数为空");
			return dto.toJsonString();
		}
		LandlordBehaviorEntity landlordBehavior = landlordBehaviorImpl.findLandlordBehavior(uid);
		dto.putValue("obj",landlordBehavior);
		return dto.toJsonString();
	}

	@Override
	public String saveLandlordBehavior(String paramJson) {
		DataTransferObject dto = new DataTransferObject();
		LandlordBehaviorEntity landlordBehaviorEntity = JsonEntityTransform.json2Object(paramJson, LandlordBehaviorEntity.class);
		if (Check.NuNStr(landlordBehaviorEntity.getLandlordUid())){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("房东uid为空");
			return dto.toJsonString();
		}
		int i = landlordBehaviorImpl.saveLandlordBehavior(landlordBehaviorEntity);
		dto.putValue("count",i);
		return dto.toJsonString();
	}

	@Override
	public String updateLandlordBehaviorByUid(String paramJson) {
		DataTransferObject dto = new DataTransferObject();
		LandlordBehaviorEntity landlordBehaviorEntity = JsonEntityTransform.json2Object(paramJson, LandlordBehaviorEntity.class);
		if (Check.NuNStr(landlordBehaviorEntity.getLandlordUid())){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("房东uid为空");
			return dto.toJsonString();
		}
		int i = landlordBehaviorImpl.updateLandlordBehaviorByUid(landlordBehaviorEntity);
		dto.putValue("count",i);
		return dto.toJsonString();
	}
}
