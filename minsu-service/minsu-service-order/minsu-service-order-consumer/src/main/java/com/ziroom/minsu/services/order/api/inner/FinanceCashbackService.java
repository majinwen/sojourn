package com.ziroom.minsu.services.order.api.inner;

/**
 * <p>订单返现相关接口</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * @author lishaochuan on 2016年9月8日
 * @since 1.0
 * @version 1.0
 */
public interface FinanceCashbackService {
	
	/**
	 * troy查询返现申请列表
	 * @author lishaochuan
	 * @create 2016年9月8日下午2:23:11
	 * @param param
	 * @return
	 */
	public String getOrderCashbackList(String param);
	
	
	/**
	 * 保存返现
	 * @author lishaochuan
	 * @create 2016年9月9日上午10:14:05
	 * @param param
	 * @return
	 */
	public String saveCashback(String param);
	
	
	/**
	 * troy查询返现审核列表
	 * @author lishaochuan
	 * @create 2016年9月9日下午4:03:35
	 * @param param
	 * @return
	 */
	public String getAuditCashbackList(String param);
	
	
	/**
	 * troy查询返现总金额
	 * @author lishaochuan
	 * @create 2016年9月13日下午4:57:42
	 * @param param
	 * @return
	 */
	public String getAuditCashbackSumFee(String param);
	
	
	/**
	 * troy查询返现日志
	 * @author lishaochuan
	 * @create 2016年9月11日下午2:56:58
	 * @param params
	 * @return
	 */
	public String getLogByCashbackSn(String cashbackSn);
	
	
	/**
	 * 审核返现
	 * @author lishaochuan
	 * @create 2016年9月9日下午8:20:32
	 * @param param
	 * @return
	 */
	public String auditCashback(String param);
	
	
	/**
	 * 驳回返现
	 * @author lishaochuan
	 * @create 2016年9月11日下午3:59:38
	 * @param param
	 * @return
	 */
	public String rejectCashback(String param);


	/**
	 * 根据uid和actSn，获取该uid（无论房东房客）在该活动下，已经有了多少返现单（无论什么状态）
	 *
	 * @author loushuai
	 * @created 2018年1月22日 下午12:45:24
	 *
	 * @param object2Json
	 * @return
	 */
	public String getHasCashBackNum(String object2Json);


	/**
	 * 批量申请返现单，单个处理没一个返现单
	 *
	 * @author loushuai
	 * @created 2018年1月24日 下午2:25:48
	 *
	 * @param object2Json
	 * @return
	 */
	public String saveCashbackBatch(String object2Json);
}
