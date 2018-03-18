package com.ziroom.zrp.service.trading.api;

/**
 * <p>收款单</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author lusp
 * @version 1.0
 * @Date Created in 2017年11月10日 09:31
 * @since 1.0
 */

public interface ReceiptService {

    /**
      * @description: 根据收款单号批量更新收款单为已收款
      * @author: lusp
      * @date: 2017/11/10 上午 9:33
      * @params: paramJson
      * @return: String
      */
    String updateBatchByBillNum(String paramJson);

    /**
     * 保存收款单
     * @param paramJson json
     * @return
     * @author cuigh6
     * @Date 2017年11月11日
     *
     */
    String saveBatchReceipt(String paramJson);

    /**
     * @description: 保存收款单并返回fid（保存收款单主记录时使用）
     * @author: lusp
     * @date: 2017/12/1 下午 15:52
     * @params: receiptEntity
     * @return: String
     */
    String saveReceiptAndReturnFid(String paramJson);

    /**
     * 批量保存收款单 和对应得应收关系
     * @author jixd
     * @created 2017年11月22日 17:32:45
     * @param
     * @return
     */
    String saveBatchReceiptAndRel(String receipts,String receiptRels);

    /**
      * @description: 根据收款单编号查询收款单
      * @author: lusp
      * @date: 2017/12/15 上午 11:31
      * @params: paramJson
      * @return: String
      */
    String getReceiptByBillNum(String receiptBillNum);

    /**
      * @description: 根据收款单编号查询对应的应收账单编号
      * @author: lusp
      * @date: 2017/12/15 上午 11:51
      * @params: receiptBillNum
      * @return: String
      */
    String getReceivableBillNumsByBillNum(String receiptBillNum);

    /**
      * @description: 根据收款单号逻辑删除收款单记录以及对应的应收账单记录
      * @author: lusp
      * @date: 2017/12/19 上午 10:42
      * @params: receiptBillNum
      * @return: String
      */
    String deleteReceiptAndRel(String receiptBillNum);

}
