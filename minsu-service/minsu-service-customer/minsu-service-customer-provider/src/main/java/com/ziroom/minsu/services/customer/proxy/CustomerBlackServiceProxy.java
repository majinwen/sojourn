/**
 * @FileName: SmsAuthLogServiceImplProxy.java
 * @Package com.ziroom.minsu.services.customer.proxy
 * 
 * @author bushujie
 * @created 2016年4月22日 上午11:15:19
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.customer.proxy;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.customer.CustomerBaseMsgEntity;
import com.ziroom.minsu.entity.customer.CustomerBlackEntity;
import com.ziroom.minsu.entity.customer.CustomerLocationEntity;
import com.ziroom.minsu.services.customer.api.inner.CustomerBlackService;
import com.ziroom.minsu.services.customer.dto.CustomerBlackDto;
import com.ziroom.minsu.services.customer.entity.CustomerBlackVo;
import com.ziroom.minsu.services.customer.service.CustomerBaseMsgServiceImpl;
import com.ziroom.minsu.services.customer.service.CustomerBlackServiceImpl;
import com.ziroom.minsu.services.customer.service.CustomerLocationServiceImpl;
import com.ziroom.minsu.valenum.common.YesOrNoEnum;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * <p>用户黑名单代理层</p>
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
@Component("customer.customerBlackServiceProxy")
public class CustomerBlackServiceProxy implements CustomerBlackService{

	private static final Logger LOGGER = LoggerFactory.getLogger(CustomerBlackServiceProxy.class);

	@Resource(name="customer.customerBlackServiceImpl")
	private CustomerBlackServiceImpl customerBlackServiceImpl;

	@Resource(name="customer.messageSource")
	private MessageSource messageSource;
	
    
    @Resource(name = "customer.customerBaseMsgServiceImpl")
    private CustomerBaseMsgServiceImpl customerBaseMsgServiceImpl;

	@Resource(name = "customer.customerLocationServiceImpl")
	private CustomerLocationServiceImpl customerLocationServiceImpl;

	/**
	 * 根据uid查询黑名单
	 * @param uid
	 * @return
	 */
	@Override
	public String findCustomerBlackByUid(String uid) {
		DataTransferObject dto = new DataTransferObject();
		if (Check.NuNStr(uid)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("UID为空");
			return dto.toJsonString();
		}
        CustomerBlackEntity customerBlack = customerBlackServiceImpl.findCustomerBlackByUid(uid);

        dto.putValue("obj",customerBlack);
        return dto.toJsonString();
	}

	/**
	 * 根据imei查询黑名单
	 * @param uid
	 * @return
	 */
	@Override
	public String findCustomerBlackByImei(String imei) {
		DataTransferObject dto = new DataTransferObject();
		if (Check.NuNStr(imei)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("imei为空");
			return dto.toJsonString();
		}
		CustomerBlackEntity customerBlack = customerBlackServiceImpl.findCustomerBlackByImei(imei);

		dto.putValue("obj",customerBlack);
		return dto.toJsonString();
	}

	/**
	 * 保存黑名单
	 * @param paramJson
	 * @return
	 */
	@Override
	public String saveCustomerBlack(String paramJson) {
        DataTransferObject dto = new DataTransferObject();
        if (Check.NuNStr(paramJson)){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("参数为空");
            return dto.toJsonString();
        }
        CustomerBlackEntity customerBlackEntity = JsonEntityTransform.json2Entity(paramJson, CustomerBlackEntity.class);
        if (Check.NuNStr(customerBlackEntity.getUid())){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("UID不能为空");
            return dto.toJsonString();
        }
        
        CustomerBaseMsgEntity cust =  customerBaseMsgServiceImpl.getCustomerBaseMsgEntitybyUid(customerBlackEntity.getUid());
        
        if (Check.NuNObj(cust)){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("当前用户不存在,请填写正确的uid");
            return dto.toJsonString();
        }


		boolean selectImei = customerBlackEntity.getSelectImei();
		if (selectImei) {
			//查customer_location 表 拿到imei 保存到黑名单表
			CustomerLocationEntity customerLocationEntity = customerLocationServiceImpl.getCustomerLocationOneHas(cust.getUid(), null);
			if (!Check.NuNObj(customerLocationEntity)) {
				String imei = customerLocationEntity.getImei();
				customerBlackEntity.setImei(imei);
			}
		}
		try{
			customerBlackServiceImpl.saveCustomerBlack(customerBlackEntity);
		}catch (Exception e){
			dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("保存失败");
		}
        return dto.toJsonString();
	}



	/**
	 * 查询黑名单列表
	 * @author afi
	 * @param parJson
	 * @return
	 */
	@Override
	public String queryCustomerBlackList(String parJson) {
		DataTransferObject dto = new DataTransferObject();
		if (Check.NuNStr(parJson)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("参数为空");
			return dto.toJsonString();
		}
		CustomerBlackDto customerBlackDto = JsonEntityTransform.json2Object(parJson, CustomerBlackDto.class);
		if (Check.NuNObj(customerBlackDto)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("参数为空");
			return dto.toJsonString();
		}
		PagingResult<CustomerBlackVo> pagingResult = customerBlackServiceImpl.queryCustomerBlackList(customerBlackDto);
		dto.putValue("rows", pagingResult.getRows());
		dto.putValue("total", pagingResult.getTotal());
		return dto.toJsonString();
	}

	/**
	 * 修改黑名单
	 * @Author lunan【lun14@ziroom.com】
	 * @Date 2017/1/12 20:22
	 */
	@Override
	public String upBlack(String paramJson) {
		DataTransferObject dto = new DataTransferObject();
		if (Check.NuNStr(paramJson)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("参数为空");
			return dto.toJsonString();
		}
		try{
			CustomerBlackEntity black = JsonEntityTransform.json2Object(paramJson, CustomerBlackEntity.class);
			customerBlackServiceImpl.upBlack(black);
		}catch (Exception e){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("更新服务错误");
		}
		return dto.toJsonString();
	}


	/**
	 * 获取黑名单的值
	 * @author afi
	 * @param fid
	 * @return
	 */
	public String getCustomerBlackVo(String fid) {
		DataTransferObject dto = new DataTransferObject();
		if (Check.NuNStr(fid)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("fid为空");
			return dto.toJsonString();
		}
		CustomerBlackVo customerBlackVo = customerBlackServiceImpl.getCustomerBlackVo(fid);
		dto.putValue("obj", customerBlackVo);
		return dto.toJsonString();
	}

	/**
	 * 批量获取民宿黑名单接口
	 *
	 * @author loushuai
	 * @created 2018年1月20日 下午5:02:36
	 *
	 * @param object2Json
	 * @return
	 */
	@Override
	public String getCustomerBlackBatch(String object2Json) {
		LogUtil.info(LOGGER, "getCustomerBlackBatch方法   参数={}", object2Json);
		DataTransferObject dto  = new DataTransferObject();
		Set<String> uidSet = JsonEntityTransform.json2Object(object2Json, Set.class);
		if(Check.NuNCollection(uidSet)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("参数为空");
			return dto.toJsonString();
		}
		Map<String, Object> resultMap =  new HashMap<String, Object>();
		for (String uid : uidSet) {
			CustomerBlackEntity customerBlack = customerBlackServiceImpl.findCustomerBlackByUid(uid);
			if(!Check.NuNObj(customerBlack)){
				resultMap.put(uid, YesOrNoEnum.YES.getCode());
			}else{
				resultMap.put(uid, YesOrNoEnum.NO.getCode());
			}
		}
		dto.putValue("resultMap", resultMap);
		return dto.toJsonString();
	}




}
