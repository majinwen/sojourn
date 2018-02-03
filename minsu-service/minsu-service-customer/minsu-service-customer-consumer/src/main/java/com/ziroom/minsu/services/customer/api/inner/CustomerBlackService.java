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
 * <p>用户黑名单服务类</p>
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
public interface CustomerBlackService {
	
	/**
	 * 
	 * 根据用户uid查询黑名单
	 *
	 * @author jixd
	 * @created 2016年10月27日 上午11:12:33
	 * @param uid
	 */
	public String findCustomerBlackByUid(String uid);

	/**
	 * 根据imei查询黑名单
	 * @param
	 * @return
	 * @author wangwt
	 * @created 2017年08月02日 15:02:42
	 */
	public String findCustomerBlackByImei(String imei);
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
	public String saveCustomerBlack(String paramJson);


	/**
	 * 查询黑名单列表
	 * @author afi
	 * @param parJson
	 * @return
	 */
	public String queryCustomerBlackList(String parJson);

	/**
	 * 修改黑名单
	 * @Author lunan【lun14@ziroom.com】
	 * @Date 2017/1/12 20:20
	 */
	public String upBlack(String paramJson);

	/**
	 * 批量获取民宿黑名单接口
	 *
	 * @author loushuai
	 * @created 2018年1月20日 下午5:02:36
	 *
	 * @param object2Json
	 * @return
	 */
	public String getCustomerBlackBatch(String object2Json);
}
