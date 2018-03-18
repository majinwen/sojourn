package com.ziroom.zrp.service.trading.api;

/**
 * <p>入住人管理接口</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @Author phil
 * @Date Created in 2017年12月04日 18:18
 * @Version 1.0
 * @Since 1.0
 */
public interface RentCheckinService {

    /**
     * 
     * 条件查询入住人列表
     *
     * @author bushujie
     * @created 2017年12月4日 下午8:29:46
     *
     * @return
     */
    String searchByCriteria(String paramJson);

    /**
     * 保存
     *  入住人
     *  合住人
     * PersonAndSharerDto
     *  - rentCheckinPersonDto
     *  - sharerEntities
     * @param paramJson
     * @return
     */
    String saveCheckinAndSharer(String paramJson);

    /**
     * 查询入住人根据合同Id
     * @param contractId
     * @return
     * @throws Exception
     */
    String findByContractId(String contractId) throws Exception;

    /**
     *
     * 删除合住人
     *
     * @author zhangyl2
     * @created 2017年12月07日 14:26
     * @param
     * @return
     */
    String delSharer(String paramJson);

    /**
     *
     * 添加合住人
     *
     * @author zhangyl2
     * @created 2017年12月07日 14:27
     * @param
     * @return
     */
    String saveSharer(String paramJson);

    /**
     *
     * 历史入住人
     *
     * @author zhangyl2
     * @created 2017年12月07日 16:22
     * @param
     * @return
     */
    String selectHistoryPersonAndSharer(String paramJson);

    /**
     *
     * 根据合同与uid查询入住人详情
     *
     * @author zhangyl2
     * @created 2017年12月08日 11:00
     * @param
     * @return
     */
    String selectHistoryCheckinPerson(String paramJson);

    /**
     *
     * 查询合住人详情
     *
     * @author zhangyl2
     * @created 2017年12月08日 11:00
     * @param
     * @return
     */
    String selectSharer(String paramJson);

    /**
     * 查询客户有效的合同
     * @param uid 客户标识
     * @return
     * @author cuigh6
     * @Date 2017年12月11日
     */
    String getValidContractList(String uid);


    /**
     * <p>根据合同ID判断签约人信息是否符合限制</p>
     * @author xiangb
     * @created 2017年10月23日
     * @param
     * @return
     */
    String validSignPerson(String contractId);


    /**
     * 查询合同入住人信息
     *
     * @param paramJson 合同标识
     * @return
     * @author cuigh6
     * @Date 2017年10月
     *
     */
    String findCheckInPersonInfo(String paramJson);

}
