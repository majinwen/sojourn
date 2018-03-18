package com.ziroom.zrp.service.trading.api;

/**
 * <p>水费清算服务</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author zhangyl2
 * @Date Created in 2018年01月31日
 * @version 1.0
 * @since 1.0
 */
public interface WaterClearingService {

    /**
     *
     * 后台手动清算一个房间下的所有合同水费并生成账单
     *
     * @author zhangyl2
     * @created 2018年02月26日 18:42
     * @param
     * @return
     */
    String manualClearingAllContractInRoom(String paramJson);

    /**
     * @description: 单个房间月结账单生成后付费水表应收账单
     *              1.抄表
     *              2.遍历房间下所有的合同，生成清算单
     *              3.结算清算记录，生成应收账单，修改清算单状态
     * @author: lusp
     * @date: 2018/2/27 下午 14:05
     * @params: paramJson
     * @return: String
     */
    String readWaterMeterAndsettleMonthly(String paramJson);

    /**
      * @description: 查询所有 有有效合同并且存在智能水表设备的房间列表
      * @author: lusp
      * @date: 2018/2/28 下午 15:29
      * @params:
      * @return: String
      */
    String getAllRoomOfValidContractAndExistIntellectWaterMeter();

    /**
     * @description: 调用水表抄表，获取最新抄表的值
     * @author: xiangb
     * @date: 2018年3月9日10:44:12
     * @params: param
     * @return: String
     */
    String surrenderMeterReading(String param);

    /**
     * @description: 解约最后一步，智能水表生成清算单并置为已结算
     * @author: xiangb
     * @date: 2018年3月15日20:39:37
     * @params: param
     * @return: String
     */
    String surrenderMeterCleaning(String contractId);

}
