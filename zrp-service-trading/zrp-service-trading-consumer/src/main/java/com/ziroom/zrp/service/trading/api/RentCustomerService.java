package com.ziroom.zrp.service.trading.api;

/**
 * <p>企业签约客户信息</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author xiangb
 * @version 1.0
 * @Date Created in 2017年9月14日
 * @since 1.0
 */
public interface RentCustomerService {


	String saveRentEpsCustomerService(String epsCustomerJson);

	/**
	 * 查询临时客户信息。
	 * 如果合同表中存在customerId，则根据customerId去查RentEpsCustomer数据
	 * 如果不存在，则根据customerUid,去查询客户库信息
	 * @author cuiyuhui
	 * @created
	 * @param
	 * @return
	 */
	String findTempRentEpsCustomerInfo(String paramJson);
	
	/**
	 * <p>根据uid查询企业客户信息</p>
	 * @author xiangb
	 * @created 2017年10月30日
	 */
	String findRentEpsCustomerByUid(String customerUid);

	/**
	 * <p>根据id查询企业客户信息</p>
	 * @author cuiyuhui
	 * @created
	 * @param
	 * @return
	 */
	String findRentEpsCustomerById(String customerId);

	/**
	 * 保存或修改企业合同相关的客户信息
	 * @author cuiyuhui
	 * @created
	 * @param
	 * @return
	 */
	String saveOrUpdateEnterpriseContractCustomerInfo(String paramJson);


	/**
	 * 保存或者更新个人用户信息
	 *
	 * @param paramJson {"sharers":"","checkInPerson":""}
	 * @return
	 * @author cuigh6
	 * @Date 2017年10月
	 */
	String saveOrUpdatePersonCustomerInfo(String paramJson);
}
