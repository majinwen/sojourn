package com.ziroom.zrp.service.trading.api;


/**
 * <p>企业签约服务</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author cuiyh9
 * @version 1.0
 * @Date Created in 2017年10月25日 10:12
 * @since 1.0
 */
public interface EnterpriseSigningService {
    
    /**
     *
     * 企业合同信息跳转到物业交割页面时修改合同部分信息
     * @author cuiyuhui
     * @created  
     * @param 
     * @return 
     */
    String updateContractInfoBySecond(String paramJson);


    /**
     * 查询应收账单信息
     * @author cuiyuhui
     * @created
     * @param
     * @return
     */
    String queryFinReceiBillInfo(String surParentRentId);

    /**
     * 查询签约成功页面房间信息列表
     * @author cuiyuhui
     * @created
     * @param
     * @return
     */
    String querySignRoomList(String surParentRentId);

    /**
     * 客户签字
     * @author cuiyuhui
     * @created
     * @param
     * @return
     */
    String customerSignature(String surParentRentId);

    /**
     * 分页查询合同详情
     * @author jixd
     * @created 2017年11月09日 14:26:38
     * @param
     * @return
     */
    String listRentDetailBySurParentRentId(String surParentPage);
    /**
     * 查询合同id列表
     * @author jixd
     * @created 2017年11月12日 11:21:32
     * @param
     * @return
     */
    String listContractBySurParentRentId(String surParentRentId);

    /**
     * 关闭企业合同接口
     * @param contractId 合同id
     * @param closeType 合同不关闭类型
     * @return
     * @author cuigh6
     * @Date 2017年10月13日
     */
    String closeEpsContract(String contractId,Integer closeType);

    /**
     * 只查询房租的应收信息
     * @author cuiyuhui
     * @created  
     * @param
     * @return 
     */
    String selectRentFinReceiBillByContractIds(String contractIds);
    /**
     * 是否完成交割
     * @author jixd
     * @created 2017年11月13日 11:24:22
     * @param
     * @return
     */
    String isFinishAllDelivery(String surParentRentId);
    /**
     * 保存应收信息（本地 不存财务当前）
     * @author jixd
     * @created 2017年11月13日 15:36:48
     * @param
     * @return
     */
    String saveEnterpriseReceiBill(String surParentRentId,String createId);

    /**
     * 企业续约时使用，获取前一个合同号的合同信息
     * 获取前一个
     * @author cuiyuhui
     * @created
     * @param
     * @return
     */
    String findOnePreContractInfoByPreSurParentRentId(String preSurParentRentId);
    /**
     * 更新子合同不可修改
     * @author jixd
     * @created 2017年11月15日 20:17:41
     * @param
     * @return
     */
    String contractNotModifyBySurParentRentId(String preSurParentRentId);

    /**
     * 同步企业合同和应收账单到财务 - 方法异步调用
     * @author cuiyuhui
     * @created
     * @param
     * @return
     */
    String asyncEntContractAndBillsToFin(String surParentRentId);

    /**
     * 定时任务同步合同数据 -- 方法为异步
     * @author cuiyuhui
     * @created
     * @param
     * @return
     */
    String asyncRetrySyncEntSubContractToFin();


    /**
     * 根据父合同id生成子合同pdf
     * @author cuiyuhui
     * @created
     * @param
     * @return
     */
    String asyncSubContractTransferToPdfByParentId(String surParentRentId);

    /**
     * 补偿生成合同文本
     * @author cuiyuhui
     * @created
     * @param
     * @return
     */
    String asyncRetrySubContractTransferToPdf();

    /**
     * 获取续约合同的父合同id
     * @author cuiyuhui
     * @created
     * @param
     * @return
     */
    String findRenewRootParentContractIds(String contractIds);

    /**
     * 延后续约
     * @author cuiyuhui
     * @created  
     * @param contractId 合同ID
     * @return 
     */
    String clearDelayRenewContract(String contractId);

}
