package com.ziroom.minsu.services.order.api.inner;

/**
 * 订单罚款相关操作
 * @author jixd
 * @created 2017年05月15日 13:53:09
 * @param
 * @return
 */

public interface OrderPenaltyService {
    /**
     * 分页查询罚款单相关信息
     * @param paramJson
     * @return
     */
    String listPenaltyPageByCondition(String paramJson);

    /**
     * 作废罚款单
     * @param paramJson
     * @return
     */
    String cancelPenalty(String paramJson);

    /**
     * 罚款单 与付款单 和收入关系
     * @param penaltySn
     * @return
     */
    String listPenaltyPayAndIncomeRel(String penaltySn);
}
