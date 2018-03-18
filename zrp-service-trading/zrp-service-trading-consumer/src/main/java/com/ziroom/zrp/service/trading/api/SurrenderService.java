package com.ziroom.zrp.service.trading.api;

/**
 * <p>解约接口服务</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @Author wangxm113
 * @Date 2017年10月11日 14时00分
 * @Version 1.0
 * @Since 1.0
 */
public interface SurrenderService {

    /**
     * 解约协议：费用结算页费用计算<br/>
     * 个人费用结算页+企业费用结算页+企业费用结算页的修改每个房间费用信息
     *
     * @Author: wangxm113
     * @Date: 2017年10月11日 15时06分10秒
     */
    String getCostItemAccount(String paramStr);

    /**
     * 解约协议-费用结算页点击下一步时（企业）<br/>
     * 更新tsurrendercost表
     *
     * @Author: wangxm113
     * @Date: 2017年10月16日 14时37分01秒
     */
    String saveSurrenderCost(String paramStr);

    /**
     * 作废解约协议
     *
     * @Author: wangxm113
     * @Date: 2017年10月24日 11时11分57秒
     */
    String cancelSurrender(String paramStr);

    /**
     * 解约-最后一步：确认解约
     *
     * @Author: wangxm113
     * @Date: 2017年10月24日 13时47分05秒
     */
    String doSurrender(String paramStr);
    
    /**
     * 支持线下解约
     * @author tianxf9
     * @param contractId
     * @return
     */
    String doLineSurrender(String contractId);

    /**
     * 获取批量解约的合同、房间信息
     *
     * @Author: wangxm113
     * @Date: 2017年11月02日 11时39分41秒
     */
    String getSurRoomList(String paramStr);

    /**
     * 修改解约协议时点提交审核
     *
     * @Author: wangxm113
     * @Date: 2017年11月07日 15时32分28秒
     */
    String editCommitAudit(String paramStr);

    /**
     * <p>保存解约协议</p>
     * @author xiangb
     * @created 2017年10月30日
     * @param
     * @return
     */
    String saveSurrender(String paramJson);
    /**
     * <p>1.查询合同当前状态</p>
     * <p>2.判断当前合同是否有欠款</p>
     * <p>3.申请是否逾期</p>
     * @author xiangb
     * @created 2017年11月8日
     * @param
     * @return
     */
    String queryConStatus(String contractId);
    /**
     * <p>根据合同ID查询申请解约房间</p>
     * @author xiangb
     * @created 2017年11月13日
     * @param
     * @return
     */
    String querySurrenderHouse(String contractId);
    /**
     * <p>更新解约协议</p>
     * @author xiangb
     * @created 2017年11月14日
     * @param
     * @return
     */
    String updateSurrender(String surrender);
    /**
     * <p>根据ID查询解约协议</p>
     * @author xiangb
     * @created 2017年11月16日
     * @param
     * @return
     */
    String findSurrenderById(String surrenderId);
    /**
     * 退租水电交割表的水电交割费用
     *
     * @Author: wangxm113
     * @Date: 2017年11月04日 17时18分12秒
     */
    public String getSDPriceBySurrenderId(String surrenderId);
    /**
     * <p>保存或者更新退租水电交割</p>
     * @author xiangb
     * @created 2017年11月20日
     * @param
     * @return
     */
    public String saveOrUpdateSurMeterDetail(String surMeterDetail);
    /**
     * <p>根据合同ID和解约ID查询</p>
     * @author xiangb
     * @created 2017年11月21日
     * @param
     * @return
     */
    public String selectSurrenderCostByParam(String param);
    /**
     * <p>查询已缴租期的最大值</p>
     * @author xiangb
     * @created 2017年11月21日
     * @param
     * @return
     */
    public String findMaxDate(String conRentCode);
    /**
     * <p>根据合同ID和解约ID查询</p>
     * @author xiangb
     * @created 2017年11月21日
     * @param
     * @return
     */
    public String findSurrenderCostByFid(String surrendercostId);
    /**
     * <p>更新解约费用结算单</p>
     * @author xiangb
     * @created 2017年11月21日
     * @param
     * @return
     */
    public String updateBySurId(String surrendercost);
    /**
     * <p>保存或更新解约审核不通过原因</p>
     * @author xiangb
     * @created 2017年11月21日
     * @param
     * @return
     */
    public String saveOrUpdateSurrrendBackRecord(String surrendBackRecord);
    /**
     * <p>审核解约协议</p>
     * @author xiangb
     * @created 2017年11月21日
     * @param
     * @return
     */
    public String confirmSurrAudit(String param);
    /**
     * <p>根据参数查询审核驳回信息</p>
     * @author xiangb
     * @created 2017年11月21日
     * @param
     * @return
     */
    public String findSurrendBackRecordEntityByParam(String param);
    /**
     * 根据父解约协议获取解约实体
     *
     * @Author: xiangbin
     * @Date: 2017年11月29日
     */
    public String getSurListByParentId(String surParentId);
    /**
     * 向解约协议中中保存解约类型 <br/>
     * @Author: xiangbin
     * @Date: 2017年11月29日
     */
    public String saveSurrenderType(String surrenderEntity);
    /**
     * <p>根据合同ID查询解约协议</p>
     * @author xiangb
     * @created 2017年12月10日
     * @param
     * @return
     */
    public String findSurrenderByContractId(String contractId);
    /**
     * <p>根据合同ID查询是否有续约合同（包括企业）</p>
     * @author xiangb
     * @created 2017年12月10日
     * @param
     * @return
     */
    public String findReNewContractByContractId(String contractId);
}
