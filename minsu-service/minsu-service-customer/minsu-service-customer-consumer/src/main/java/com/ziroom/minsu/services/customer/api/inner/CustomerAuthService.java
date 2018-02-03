/**
 * @FileName: CustomerAuthService1.java
 * @Package com.ziroom.minsu.services.customer.api.inner
 * 
 * @author bushujie
 * @created 2016年4月22日 上午11:10:34
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.customer.api.inner;

/**
 * <p>客户认证流程接口</p>
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
public interface CustomerAuthService {
	
	/**
	 * 
	 * 保存验证短信记录
	 *
	 * @author bushujie
	 * @created 2016年4月22日 上午11:12:33
	 *
	 */
	public String saveSmsAuthLog(String paramJson);
	
	/**
	 * 
	 * 客户联系方式认证
	 *
	 * @author bushujie
	 * @created 2016年4月22日 下午2:21:06
	 *
	 * @param paramJson
	 * @return
	 */
	public String customerContactAuth(String paramJson);
	
	/**
	 * 
	 * 身份信息认证
	 *
	 * @author bushujie
	 * @created 2016年4月22日 下午9:14:48
	 *
	 * @param paramJson
	 * @return
	 */
	public String customerIdentityAuth(String paramJson);
	
	/**
	 * 
	 * 客户真实头像认证
	 *
	 * @author bushujie
	 * @created 2016年4月23日 下午1:41:17
	 *
	 * @param paramJson
	 * @return
	 */
	public String customerIconAuth(String paramJson);
	/**
	 * 
	 * 判断当前手机验证码是否可用
	 *
	 * @author yd
	 * @created 2016年5月10日 下午10:46:22
	 *
	 * @param contactAuthDtoStr
	 * @return
	 */
	public String findMobileVerifyResult(String smsAuthLogDtoStr);
	
}
