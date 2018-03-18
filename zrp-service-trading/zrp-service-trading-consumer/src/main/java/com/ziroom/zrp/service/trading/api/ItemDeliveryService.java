package com.ziroom.zrp.service.trading.api;

import java.util.List;


/**
 * <p>物业交割相关接口</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd
 * @version 1.0
 * @Date Created in 2017年09月18日 15:19
 * @since 1.0
 */
public interface ItemDeliveryService {

    /**
     * 保存或者更新合住人
     * @author jixd
     * @created 2017年09月25日 16:45:59
     * @param
     * @return
     */
    String saveOrUpdateSharer(String paramJson);
    /**
     * 查询合住人列表
     * @author jixd
     * @created 2017年09月25日 16:46:14
     * @param
     * @return
     */
    String listSharerByContractId(String paramJson);
    /**
     * 删除合住人
     * @author jixd
     * @created 2017年09月21日 16:09:03
     * @param
     * @return
     */
    String deleteSharerByFid(String paramJson);

    /**
     * 获取有效的物品
     * @author jixd
     * @created 2017年09月21日 16:08:47
     * @param
     * @return
     */
    String listValidItemByContractIdAndRoomId(String paramJson);

    /**
     * 批量获取有效物品
     * @author cuiyuhui
     * @created
     * @param
     * @return
     */
    String listValidItemByContractIds(String contractIds);

    /**
     * 查询水电交割详情信息
     * @author jixd
     * @created 2017年09月25日 18:18:16
     * @param
     * @return
     */
    String findMeterDetailById(String paramJson);

    /**
     * 查询水电交割详情信息
     * @author cuiyh9
     * @created 2017年09月25日 18:18:16
     * @param
     * @return
     */
    String findMeterDetailsByContractIds(String contractIds);

    /**
     * 确认物业交割
     * @author jixd
     * @created 2017年09月26日 13:48:36
     * @param
     * @return
     */
    String confirmDelivery(String contractId);
    /**
     * <p>根据合同ID查询其他生活费用收费明细</p>
     * @author xiangb
     * @created 2017年9月28日
     * @param
     * @return
     */
    String findLifeItemByContractId(String contractId);

    /**
     * <p>根据合同列表查询其他生活费用收费明细</p>
     * @author cuiyh9
     * @created 2017年9月28日
     * @param
     * @return
     */
    String findLifeItemByContractIdList(String contractIds);

    /**
     * 获取生活费用信息和水电费信息
     * @author jixd
     * @created 2017年10月30日 18:49:51
     * @param
     * @return
     */
    String findDeliveryInfo(String paramJson);

    /**
     * 保存物业交割信息  个人后台签约
     * @author jixd
     * @created 2017年11月03日 15:27:39
     * @param
     * @return
     */
    String saveDeliveryInfo(String paramJson);
    /**
     * 查询房间物业交割信息 （打印需要的数据）
     * @author jixd
     * @created 2017年11月12日 10:52:35
     * @param
     * @return
     */
    String findDeliveryRoomInfo(String contractId);

    /**
     * 查询生活费用项
     * @return
     * @author cuigh6
     * @Date 2017年11月
     */
    String listExpenseByItemCodes();
    /**
     * 获取房屋交割清单 -- 订单打印 只为展示 数据库无数据 关联的是房屋信息数据
     *@author xiangbin
     * @param roomId
     * @param type
     * @return
     */
    String getItemDeliveryListForView(String param);
    /**
     * 根据itemIds获取物品
     * @author xiangbin
     * @created 2017年11月20日
     * @param
     * @return
     */
    String listItemDeliverysByItemIds(String itemIds);
    /**
     * 保存或者更新物品
     * @author xiangbin
     * @created 2017年11月20日
     * @param
     * @return
     */
    String saveOrUpdateItemDelivery(String param);
    /**
     * 根据多个条件查询物品
     * @author xiangbin
     * @created 2017年11月20日
     * @param
     * @return
     */
    String selectRentItemDeliveryEntityByparam(String param);

    /**
     * 更新水电信息
     * @param param
     * @return
     */
    String updateMeterByContractId(String param);

    /**
     * 复制水电交割数据
     * @param contractId
     * @return
     */
    String copyMeterInfo(String contractId);

    /**
     *  电费充值： 生成电费应收账单
     *
     *   1. 参数校验,成功
     *
     *   2. 去财务生成应收账单号，成功
     *
     *   3. 落地应收账单、应收账单明细到自如寓库，成功
     *
     *   4. 以上失败，处理失败流程，并返回失败信息
     * @author yd
     * @created
     * @param
     * @return
     */
    public String generatingElectricityBill(String paramJson);

    /**
      * @description: 智能设备抄表并返回当前智能设备当前信息
      * @author: lusp
      * @date: 2018/3/5 下午 15:17
      * @params: paramJson
      * @return: String
      */
    String readSmartDeviceByRoomInfo(String paramJson);

}
