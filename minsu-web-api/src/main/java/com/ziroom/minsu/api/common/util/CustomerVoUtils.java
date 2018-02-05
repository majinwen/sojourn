
package com.ziroom.minsu.api.common.util;


import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.services.customer.api.inner.CustomerMsgManagerService;
import com.ziroom.minsu.services.customer.entity.CustomerVo;

/**
 * <p>获取用户信息的工具类</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author yd
 * @since 1.0
 * @version 1.0
 */
public class CustomerVoUtils {
	/**
	 * 日志对象
	 */
	private static Logger logger = LoggerFactory.getLogger(CustomerVoUtils.class);
	/**
	 * 获取用户的service
	 */
	private CustomerMsgManagerService customerMsgManagerService;
	
	public CustomerVoUtils(CustomerMsgManagerService customerMsgManagerService){
		this.customerMsgManagerService = customerMsgManagerService;
	}
	/**
	 * 
	 * 根据用户uid 获取用户信息
	 *
	 * @author yd
	 * @created 2016年5月7日 下午4:26:58
	 *
	 * @return
	 */
	public   CustomerVo getCustomerVo(String uid,CustomerMsgManagerService customerMsgManagerService){
		
		CustomerVo customerVo = null;
		if(Check.NuNStr(uid)){
			LogUtil.info(logger,"uid 不存在");
			return customerVo;
		}
		if(!Check.NuNObj(customerMsgManagerService)){
			this.setCustomerMsgManagerService(customerMsgManagerService);
		}
		
		if(Check.NuNObj(this.getCustomerMsgManagerService())){
			LogUtil.info(logger,"customerMsgManagerService is null");
			return customerVo;
		}
		try {
			DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(this.getCustomerMsgManagerService().getCutomerVoFromDb(uid));
			customerVo = dto.parseData("customerVo", new TypeReference<CustomerVo>() {
			});
			LogUtil.info(logger, "根据用户uid={},查询用户信息customerVo={}", uid,customerVo);
		} catch (Exception e) {
			LogUtil.error(logger, "根据用户uid={}查询异常,e={}", uid,e);
			customerVo = null;
		}
		
		return customerVo;
	}
	public CustomerMsgManagerService getCustomerMsgManagerService() {
		return customerMsgManagerService;
	}
	public void setCustomerMsgManagerService(
			CustomerMsgManagerService customerMsgManagerService) {
		this.customerMsgManagerService = customerMsgManagerService;
	}
	
	
}
