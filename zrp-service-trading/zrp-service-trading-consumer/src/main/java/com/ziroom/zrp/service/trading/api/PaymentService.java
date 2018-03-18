package com.ziroom.zrp.service.trading.api;

/**
 * <p>款项计算</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author cuigh6
 * @Date Created in 2017年09月12日 15:47
 * @version 1.0
 * @since 1.0
 */
public interface PaymentService {
	/**
	 * 查询账单详情
	 * @author cuigh6
	 * @param uid 用户标识
	 * @param contractId 合同标识
	 * @return
	 */
	String getPaymentDetail(String uid, String contractId);

	/**
	 * 查询账单详情 for PC
	 * @author cuigh6
	 * @param paramJson 参数 {"contractId":"合同标识","conType":"租赁方式 日租/月租","conCycleCode":"付款方式"}
	 * @return
	 */
	String getPaymentDetailForPC(String paramJson);

	/**
	  * @description: 获取合同账单集合对象
	  * @author: lusp
	  * @date: 2017/10/16 下午 15:11
	  * @params: contractId
	  * @return: String
	  */
	String getContractTerms(String paramJson);

	/**
	 * 查询支付详情
	 * @author cuigh6
	 * @Date 2017年10月10日
	 * @param contractId 合同标识
	 * @param period 期数
	 * @return
	 */
	String findBillPayDetail(String contractId, Integer period);

	/**
	 * 查询账单详情 (支付页数据)
	 * @author cuigh6
	 * @Date 2017年10月10日
	 * @param contractId 合同标识
	 * @param period 期数
	 * @param billType  账单类型
	 * @return json
	 */
	String findPayPageDetail(String contractId, Integer period, String billType);

	/**
	 * 下单校验(供财务使用)
	 *
	 * @param paramJson {"billNums":"123123131,1231313"} 逗号隔开
	 * @return json
	 * @author cuigh6
	 * @Date 2017年10月
	 */
	String validPayForFinance(String paramJson);

	/**
	 * 查询历史支付的生活费用账单
	 * @param contractId 合同号
	 * @return
	 * @author cuigh6
	 * @Date 2017年10月
	 */
	String findHistoryLifeFeeList(String contractId);

	/**
	 * 查询待支付账单列表
	 * @param uid 用户标识
	 * @return json
	 * @author cuigh6
	 * @Date 2017年11月11日
	 */
	String getMustPayBillList(String uid);

	/**
	 * 根据合同号查询 账单列表(房租账单&生活费用账单)
	 *
	 * @param contractId 合同Id
	 * @param firstPaid 首次支付标识
	 * @return 列表对象
	 * @author cuigh6
	 * @Date 2017年9月26日
	 */
	String findBillListByContractId(String contractId, Integer firstPaid);
}
