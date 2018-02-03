/**
 * @FileName: CustomerBankCardServiceImpl.java
 * @Package com.ziroom.minsu.services.customer.service
 * 
 * @author bushujie
 * @created 2016年4月25日 下午4:42:19
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.customer.proxy;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.MessageSourceUtil;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.customer.CustomerCollectionEntity;
import com.ziroom.minsu.services.common.constant.MessageConst;
import com.ziroom.minsu.services.customer.api.inner.CustomerCollectionService;
import com.ziroom.minsu.services.customer.constant.CustomerMessageConst;
import com.ziroom.minsu.services.customer.dto.CollectionConditionDto;
import com.ziroom.minsu.services.customer.dto.CustomerCollectionDto;
import com.ziroom.minsu.services.customer.entity.CustomerCollectionVo;
import com.ziroom.minsu.services.customer.logic.ParamCheckLogic;
import com.ziroom.minsu.services.customer.logic.ValidateResult;
import com.ziroom.minsu.services.customer.service.CustomerCollectionServiceImpl;

/**
 * 
 * <p>客户房源收藏实现类</p>
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
@Service("customer.customerCollectionServiceProxy")
public class CustomerCollectionServiceProxy implements CustomerCollectionService{

    /**
     * 日志对象
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerCollectionServiceProxy.class);
	
	@Resource(name="customer.customerCollectionServiceImpl")
	private CustomerCollectionServiceImpl customerCollectionServiceImpl;

	@Resource(name="customer.messageSource")
	private MessageSource messageSource;
	
	@Resource(name="customer.paramCheckLogic")
	private ParamCheckLogic paramCheckLogic;
	
	@Override
	public String saveCustomerCollectionEntity(String paramJson) {
		LogUtil.info(LOGGER, "参数:{}", paramJson);
		//参数通用验证
		ValidateResult<CustomerCollectionEntity> validateResult =
				paramCheckLogic.checkParamValidate(paramJson, CustomerCollectionEntity.class);
		if (!validateResult.isSuccess()) {
			return validateResult.getDto().toJsonString();
		}
		CustomerCollectionEntity customerCollectionEntity= validateResult.getResultObj();
        DataTransferObject dto = new DataTransferObject();
        try {
            String customerCollFid = customerCollectionServiceImpl.insertCustomerCollection(customerCollectionEntity);
            dto.putValue("customerCollFid", customerCollFid);
        } catch (Exception e) {
            LogUtil.error(LOGGER, "error:{}", e);
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(e.getMessage());
        }
        LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
        return dto.toJsonString();
	}

	@Override
	public String findCustomerCollectionEntityByFid(String fid) {
		LogUtil.info(LOGGER, "参数:fid={}", fid);
        DataTransferObject dto = new DataTransferObject();
        // 校验客户房源收藏id不能为空
        if(Check.NuNStr(fid)){
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(MessageSourceUtil.getChinese(messageSource, CustomerMessageConst.CUSTOMER_COLLECTION_FID_NULL));
            LogUtil.error(LOGGER, dto.toJsonString());
            return dto.toJsonString();
        }

        try {
            CustomerCollectionEntity customerCollectionEntity = customerCollectionServiceImpl.findCustomerCollectionEntityByFid(fid);
            dto.putValue("obj", customerCollectionEntity);
        } catch (Exception e) {
            LogUtil.error(LOGGER, "error:{}", e);
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(e.getMessage());
        }
        LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
        return dto.toJsonString();
	}
	
	@Override
	public String findCustomerCollectionEntityByCondition(String paramJson) {
		LogUtil.info(LOGGER, "参数:{}", paramJson);
		CollectionConditionDto conditionDto = JsonEntityTransform.json2Object(paramJson, CollectionConditionDto.class);
		DataTransferObject dto = new DataTransferObject();
		
		// 校验参数不能为空
		if(Check.NuNObj(conditionDto)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, CustomerMessageConst.PARAM_NULL));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}
		
		// 校验用户不能为空
		if(Check.NuNObj(conditionDto.getUid())){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, CustomerMessageConst.UID_NULL_ERROR));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}
		
		// 校验房源(房间)fid不能为空
		if(Check.NuNStr(conditionDto.getHouseFid())){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, CustomerMessageConst.HOUSE_HOUSEFID_NULL));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}
		
		// 校验出租方式不能为空
		if(Check.NuNObj(conditionDto.getRentWay())){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, CustomerMessageConst.HOUSE_RENTWAY_NULL));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}
		
		try {
			CustomerCollectionEntity customerCollectionEntity = customerCollectionServiceImpl
					.findCustomerCollectionEntityByCondition(conditionDto);
			dto.putValue("obj", customerCollectionEntity);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(e.getMessage());
		}
		LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}

	@Override
	public String findCustomerCollectionVoListByUid(String paramJson) {
		LogUtil.info(LOGGER, "参数:{}", paramJson);
		CustomerCollectionDto customerCollDto = JsonEntityTransform.json2Object(paramJson, CustomerCollectionDto.class);
        DataTransferObject dto = new DataTransferObject();
        
        // 校验参数不能为空
        if(Check.NuNObj(customerCollDto)){
        	dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
        	dto.setMsg(MessageSourceUtil.getChinese(messageSource, CustomerMessageConst.PARAM_NULL));
        	LogUtil.error(LOGGER, dto.toJsonString());
        	return dto.toJsonString();
        }
        
        // 校验客户uid不能为空
        if(Check.NuNStr(customerCollDto.getUid())){
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(MessageSourceUtil.getChinese(messageSource, CustomerMessageConst.UID_NULL_ERROR));
            LogUtil.error(LOGGER, dto.toJsonString());
            return dto.toJsonString();
        }

        try {
            PagingResult<CustomerCollectionVo> pagingResult = customerCollectionServiceImpl
            		.findCustomerCollectionVoListByUid(customerCollDto);
            dto.putValue("rows", pagingResult.getRows());
            dto.putValue("total", pagingResult.getTotal());
        } catch (Exception e) {
            LogUtil.error(LOGGER, "error:{}", e);
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(e.getMessage());
        }
        LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
        return dto.toJsonString();
	}

	@Override
	public String updateCustomerCollectionByFid(String paramJson) {
		LogUtil.info(LOGGER, "参数:{}", paramJson);
		//参数通用验证
		ValidateResult<CustomerCollectionEntity> validateResult =
				paramCheckLogic.checkParamValidate(paramJson, CustomerCollectionEntity.class);
		if (!validateResult.isSuccess()) {
			return validateResult.getDto().toJsonString();
		}
		CustomerCollectionEntity customerCollectionEntity= validateResult.getResultObj();
        DataTransferObject dto = new DataTransferObject();
        
        // 校验客户收藏fid不能为空
        if(Check.NuNStr(customerCollectionEntity.getFid())){
        	dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
        	dto.setMsg(MessageSourceUtil.getChinese(messageSource, CustomerMessageConst.CUSTOMER_COLLECTION_FID_NULL));
        	LogUtil.error(LOGGER, dto.toJsonString());
        	return dto.toJsonString();
        }

        try {
            int upNum = customerCollectionServiceImpl.updateCustomerCollectionByFid(customerCollectionEntity);
            dto.putValue("upNum", upNum);
        } catch (Exception e) {
            LogUtil.error(LOGGER, "error:{}", e);
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(e.getMessage());
        }
        LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
        return dto.toJsonString();
	}

	@Override
	public String countByUid(String uid) {
		LogUtil.info(LOGGER, "参数:uid={}", uid);
        DataTransferObject dto = new DataTransferObject();
        // 校验客户房源收藏id不能为空
        if(Check.NuNStr(uid)){
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(MessageSourceUtil.getChinese(messageSource, CustomerMessageConst.UID_NULL_ERROR));
            LogUtil.error(LOGGER, dto.toJsonString());
            return dto.toJsonString();
        }

        try {
            long num = customerCollectionServiceImpl.countByUid(uid);
            dto.putValue("num", num);
        } catch (Exception e) {
            LogUtil.error(LOGGER, "error:{}", e);
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(e.getMessage());
        }
        LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
        return dto.toJsonString();
	}
	
}
