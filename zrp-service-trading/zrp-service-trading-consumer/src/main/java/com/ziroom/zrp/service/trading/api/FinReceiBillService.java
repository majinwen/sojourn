package com.ziroom.zrp.service.trading.api;


import com.ziroom.zrp.service.trading.dto.finance.FinReceiBillDetailDto;
import com.ziroom.zrp.service.trading.dto.finance.FinReceiBillDto;

import java.util.List;

/**
 * <p></p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author cuigh6
 * @version 1.0
 * @Date Created in 2017年11月15日 15:08
 * @since 1.0
 */
public interface FinReceiBillService {

	/**
	 * 查询指定日期 需要收款的合同号
	 * @param projectIds 项目标识
	 * @return
	 * @author cuigh6
	 * @Date 2017年11月
	 */
	String getReceiptContractByDate(String projectIds,String startDate,String endDate);

	/**
	 * 保存应收账单和明细
	 * @param paramJson 账单和明细
	 * @return
	 * @author cuigh6
	 * @Date 2017年11月
	 */
	String saveFinReceiBillAndDetail(String paramJson);

	/**
	 * 根据应收明细查询
	 * @author cuiyuhui
	 * @created
	 * @param
	 * @return
	 */
	String syncReceiptBillToFinByReceiDetailId(String receiDetailId);


	/**
	 * 定时任务同步应收账单数据 - 异步调用
	 * @author cuiyuhui
	 * @created
	 * @param
	 * @return
	 */
	String asyncRetrySyncReceiptBillToFin();

	/**
	 * 修改应收账单
	 * @param paramJson 参数
	 * @return
	 * @author cuigh6
	 * @Date 2017年11月
	 */
	String updateFinReceivableBill(String paramJson);

	/**
	 *
	 * 合同id查询应收账单列表（未付款）
	 *
	 * @author bushujie
	 * @created 2018年1月31日 下午3:04:37
	 *
	 * @param contractId
	 * @return
	 */
	String getFinReceiBillByContractId(String paramJson);

	/**
	 * 保存应收账单（水表，电表）
	 *
	 * @param finReceiBillDto 应收账单
	 * @return
	 */
	String saveFinanceReceivableOfWaterWatt(FinReceiBillDto finReceiBillDto);
	
	/**
	 * 
	 *  fid查询应收账单
	 *
	 * @author bushujie
	 * @created 2018年2月1日 下午4:22:37
	 *
	 * @param billFid
	 * @return
	 */
	String getFinReceiBillByFid(String billFid);
}
