package com.ziroom.minsu.services.finance.api.inner;

/**
 * <p>账单关联接口
 *  说明：收款单  付款单   收入 的后台管理接口
 * </p>
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
public interface BillManageService {

	
	/**
	 * 
	 * 按条件分页查询收款单
	 *
	 * @author yd
	 * @created 2016年4月28日 下午8:26:55
	 *
	 * @param financePunishReuest
	 * @return
	 */
	public String queryPaymentVoByPage(String paymentVouchersRequest);
	/**
	 * 
	 * 显示付款单详情
	 *
	 * @author jixd
	 * @created 2016年5月12日 下午5:48:10
	 *
	 * @param fid
	 * @return
	 */
	public String getPaymentVoById(String fid);
	
	/**
	 * 
	 * 按条件分页查询 公司收入
	 *
	 * @author yd
	 * @created 2016年4月28日 下午8:28:33
	 *
	 * @param financeIncomeReuest
	 * @return
	 */
	public String queryFinanceIncomeByPage(String financeIncomeReuest);


    /**
     * 获取收入详情
     * @author afi
     * @param fid
     * @return
     */
    String getFinanceIncomeDetail(String fid);


	/**
	 * 
	 * 按条件分页查询付款单表
	 *
	 * @author yd
	 * @created 2016年4月28日 下午8:30:00
	 *
	 * @param FinancePayVosReuest
	 * @return
	 */
	public String queryFinancePayVosByPage(String financePayVosReuest);
	
	
	/**
	 * 获取付款单详情
	 * @author lishaochuan
	 * @create 2016年5月17日上午11:16:52
	 * @param pvSn
	 * @return
	 */
	public String getPayVouchersDetail(String pvSn);
	
	
	/**
	 * 查询重新生成的付款单数量
	 * @author lishaochuan
	 * @create 2016年8月16日下午6:04:16
	 * @param parentPvSn
	 * @return
	 */
	public String countReCreatePvs(String parentPvSn); 
	
	
	/**
	 * 重新生成付款单
	 * @author lishaochuan
	 * @create 2016年8月17日上午11:46:52
	 * @param pvSn
	 * @return
	 */
	public String reCreatePvs(String pvSn, String userAccount);


	/**
	 * 校验是否可原路返回
	 * @author lishaochuan
	 * @param pvSn
	 * @return
     */
	public String checkCanYlfh(String pvSn);
	
	
}
