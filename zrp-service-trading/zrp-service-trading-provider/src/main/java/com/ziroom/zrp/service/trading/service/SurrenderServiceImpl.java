package com.ziroom.zrp.service.trading.service;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.utils.LogUtil;
import com.ziroom.zrp.service.trading.dao.*;
import com.ziroom.zrp.service.trading.dto.surrender.CancelSurrenderDto;
import com.ziroom.zrp.service.trading.dto.surrender.SurRoomListDto;
import com.ziroom.zrp.service.trading.dto.surrender.SurRoomListReturnDto;
import com.ziroom.zrp.service.trading.dto.surrender.SurrenderCostNextDto;
import com.ziroom.zrp.service.trading.entity.SurrenderCostSumBodyVo;
import com.ziroom.zrp.service.trading.entity.SyncContractVo;
import com.ziroom.zrp.service.trading.pojo.CalSurrenderPojo;
import com.ziroom.zrp.service.trading.valenum.*;
import com.ziroom.zrp.service.trading.valenum.surrender.SubmitStatusEnum;
import com.ziroom.zrp.trading.entity.*;
import com.zra.common.utils.ZraConst;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.math.BigDecimal;
import java.util.*;

/**
 * <p>解约</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @Author wangxm113
 * @Date 2017年10月11日 19时49分
 * @Version 1.0
 * @Since 1.0
 */
@Service("trading.surrenderServiceImpl")
public class SurrenderServiceImpl {
    private static final Logger LOGGER = LoggerFactory.getLogger(SurrenderServiceImpl.class);

    @Resource(name = "trading.surrenderDao")
    private SurrenderDao surrenderDao;

    @Resource(name = "trading.surrenderCostDao")
    private SurrenderCostDao surrenderCostDao;

    @Resource(name = "trading.surrenderCostItemDao")
    private SurrenderCostItemDao surrenderCostItemDao;

    @Resource(name = "trading.finReceiBillDao")
    private FinReceiBillDao finReceiBillDao;

    @Resource(name = "trading.finReceiBillDetailDao")
    private FinReceiBillDetailDao finReceiBillDetailDao;

    @Resource(name = "trading.paymentBillDao")
    private PaymentBillDao paymentBillDao;

    @Resource(name = "trading.paymentBillDetailDao")
    private PaymentBillDetailDao paymentBillDetailDao;

    @Resource(name = "trading.rentContractDao")
    private RentContractDao rentContractDao;
    
    @Resource(name="trading.surrendBackRecordDao")
	private SurrendBackRecordDao surrendBackRecordDao;

    /**
     * 查询父合同下所有信息
     *
     * @Author: wangxm113
     * @Date: 2017年11月04日 17时22分22秒
     */
    public List<CalSurrenderPojo> getSurAndSurCostInfo(String surParentId) {
        return surrenderDao.getSurAndSurCostInfo(surParentId);
    }

    /**
     * 查询指定合同下所有信息
     *
     * @Author: wangxm113
     * @Date: 2017年11月04日 17时22分46秒
     */
    public CalSurrenderPojo getSurAndSurCostInfoByConId(String contractId) {
        return surrenderDao.getSurAndSurCostInfoByConId(contractId);
    }

    /**
     * 根据父解约协议获取解约实体
     *
     * @Author: wangxm113
     * @Date: 2017年11月04日 17时23分23秒
     */
    public List<SurrenderEntity> getSurListByParentId(String surParentId) {
        return surrenderDao.getSurListByParentId(surParentId);
    }

    /**
     * 解约协议：费用结算页点击下一步（包括企业和个人）、企业单个合同的保存<br/>
     * 更新tsurrender、保存/更新tsurrendercost和tsurrendercostitem、生成应收账单或付款单
     *
     * @Author: wangxm113
     * @Date: 2017年10月20日 09时40分00秒
     */
    public void updateSurAndOperSurCostAndCostItem(List<SurrenderEntity> surrenderIdList,
                                                   SurrenderCostNextDto paramDto,
                                                   Map<String, List<SurrenderCostSumBodyVo>> map) {
        //==============================================================================================================
        //0、判断是新增还是修改
        boolean isNew;
        String surrendercostId = surrenderIdList.get(0).getSurrendercostId();//根据surrenderCostId来判断是新增还是修改
        if (surrendercostId == null || surrendercostId.isEmpty()) {
            //如果为新增（是否为修改 0：新增；1：修改）:更新tsurrender、插入tsurrendercost、插入tsurrendercostitem、插入应收/收款单主表子表
            LogUtil.info(LOGGER, "[解约费用结算下一步]父解约协议id={}，执行新增操作！", paramDto.getSurParentId());
            isNew = true;
        } else {//修改：更新tsurrender、更新tsurrendercost、更新tsurrendercostitem、插入应收/收款单主表子表
            LogUtil.info(LOGGER, "[解约费用结算下一步]父解约协议id={}，执行更新操作！", paramDto.getSurParentId());
            isNew = false;
        }
        //==============================================================================================================
        Date now = new Date();
        //1、封装tsurrendercost相同部分
        SurrenderCostEntity surrenderCostEntity = new SurrenderCostEntity();
        surrenderCostEntity.setFcostremark(paramDto.getFcostremark());
        surrenderCostEntity.setFpayer(paramDto.getFpayer());
        surrenderCostEntity.setFrentenddate(paramDto.getFrentenddate());
        surrenderCostEntity.setFresponsibility(paramDto.getFresponsibility());
        surrenderCostEntity.setFsurtype(paramDto.getFsurtype());
        surrenderCostEntity.setFhandlezo(paramDto.getZoName());
        if (isNew) {
            surrenderCostEntity.setCreaterid(paramDto.getZoId());
            surrenderCostEntity.setFcreatetime(now);
        } else {
            surrenderCostEntity.setUpdaterid(paramDto.getZoId());
            surrenderCostEntity.setFupdatetime(now);
        }
        //==============================================================================================================
        //循环每一条解约协议
        surrenderIdList.forEach(p -> {
            String contractId = p.getContractId();
            String surrenderId = p.getSurrenderId();
            RentContractEntity rentContractEntity = rentContractDao.findContractBaseByContractId(contractId);
            List<SurrenderCostSumBodyVo> itemList = map.get(contractId);
            double refundNum = itemList.stream()
                    .map(SurrenderCostSumBodyVo::getRefundNum)
                    .reduce(BigDecimal.ZERO, BigDecimal::add)
                    .doubleValue();
            //封装tsurrendercost
            surrenderCostEntity.setSurrenderId(surrenderId);
            surrenderCostEntity.setContractId(contractId);
            surrenderCostEntity.setFsettlementamount(refundNum);
            if (refundNum > 0) {//此时生成应付，则应该保存乙方账户信息（此处以后可能会有问题，批量解约，最后需收款，但某个可能需要付款，此时拿不到这些信息）
                surrenderCostEntity.setFaccountname(paramDto.getFaccountname());
                surrenderCostEntity.setFbankname(paramDto.getFbankname());
                surrenderCostEntity.setFbankaccount(paramDto.getFbankaccount());
                surrenderCostEntity.setFpaymentstatus(0);//付款状态 0:未付款 1:已付款 （财务审核状态）
            } else if (refundNum < 0) {//此时生成应收账单
                surrenderCostEntity.setFgatheringstatus(0);//收款状态 0:未收款 1:已收款 （财务审核状态）
            }
            String surCostId;
            if (isNew) {
                surCostId = UUID.randomUUID().toString();
                surrenderCostEntity.setSurrendercostId(surCostId);
            } else {
                surCostId = p.getSurrendercostId();
            }
            //==========================================================================================================
            //2、更新tsurrender
            paramDto.setSurrendercostId(surCostId);
            paramDto.setSurrenderId(surrenderId);
            Integer i = surrenderDao.updateById(paramDto);
            if (i != 1) {
                LogUtil.error(LOGGER, "[解约费用结算下一步]更新tsurrender出错！surrender_id={}，更新结果：{}条。", surrenderId, i);
                throw new RuntimeException("[解约费用结算下一步]更新tsurrender出错!");
            }
            //==========================================================================================================
            //3、插入/更新tsurrendercost
            if (isNew) {
                int j = surrenderCostDao.insertSelective(surrenderCostEntity);
                if (j != 1) {
                    LogUtil.error(LOGGER, "[解约费用结算下一步]保存tsurrendercost出错！surrender_id={}，更新结果：{}条。", surrenderId, i);
                    throw new RuntimeException("[解约费用结算下一步]保存tsurrendercost出错!");
                }
            } else {
                int j = surrenderCostDao.updateBySurId(surrenderCostEntity);
                if (j != 1) {
                    LogUtil.error(LOGGER, "[解约费用结算下一步]更新tsurrendercost出错！surrender_id={}，更新结果：{}条。", surrenderId, j);
                    throw new RuntimeException("[解约费用结算下一步]更新tsurrendercost出错!");
                }
            }
            //==========================================================================================================
            final FinReceiBillEntity receiBillEntity = new FinReceiBillEntity();
            final PaymentBillEntity paymentBillEntity = new PaymentBillEntity();
            String billFid = UUID.randomUUID().toString();
            if (paramDto.getIsTemp() != 1) {//是否临时保存（0：不是；1：是）;批量解约时，保存单个合同解约信息是不需要生成应收账单/付款单
                //4、生成应收账单或付款单
                //4-1、删除此解约协议对应的所有的应收账单和付款单以及分别对应的明细
                finReceiBillDao.deleteBySurrenderId(surrenderId);
                //4-2、删除付款单以及对应的明细
                paymentBillDao.deleteBySurrenderId(surrenderId);
                //4-3、判断生成应收账单还是付款单，并生成
                if (refundNum < 0) {//此时生成应收账单
                    receiBillEntity.setOughtTotalAmount(0D);
                    receiBillEntity.setActualTotalAmount(0D);//实收记为0
                    receiBillEntity.setFid(billFid);
                    receiBillEntity.setContractId(contractId);
                    receiBillEntity.setBillState(ReceiBillStateEnum.WSK.getCode());
                    receiBillEntity.setBillType(ReceiBillTypeEnum.QTSK.getCode());
                    receiBillEntity.setGenWay(ReceiBillGenWayEnum.ZDSC.getCode());
                    receiBillEntity.setCreateId(paramDto.getZoId());
                    receiBillEntity.setCreateTime(now);
                    receiBillEntity.setCommonts("解约系统自动产生的应收账单，不传财务！！！");
                    receiBillEntity.setSurrenderId(surrenderId);
                } else if (refundNum > 0) {//此时生成付款单
                    paymentBillEntity.setFid(billFid);
                    paymentBillEntity.setCustomerBankName(paramDto.getFbankname());
                    paymentBillEntity.setCustomerAccountName(paramDto.getFaccountname());
                    paymentBillEntity.setCustomerBankAccount(paramDto.getFbankaccount());
                    paymentBillEntity.setSurrenderId(surrenderId);
                    paymentBillEntity.setOutContract(rentContractEntity.getConRentCode());
                    paymentBillEntity.setPaySerialNum("");
                    paymentBillEntity.setCreateDate(now);
                    paymentBillEntity.setGenWay(0);
                    paymentBillEntity.setSynFinance(0);
                } else {//既不生成应收也不生成付款单
                    LogUtil.info(LOGGER, "[解约费用结算下一步]surrender_id={}，既不生成应收也不生成付款单。", surrenderId);
                }
            }
            //==========================================================================================================
            //5、插入tsurrendercostitem
            //循环每一个费用明细
            itemList.forEach(item -> {
                BigDecimal actualNum = item.getActualNum();
                BigDecimal originalNum = item.getOriginalNum();
                //生成tsurrendercostitem
                SurrenderCostItemEntity itemEntity = new SurrenderCostItemEntity();
                itemEntity.setFrefundnum(item.getRefundNum());
                itemEntity.setFactualnum(actualNum);
                itemEntity.setForiginalnum(originalNum);
                if (isNew) {
                    itemEntity.setFid(UUID.randomUUID().toString());
                    itemEntity.setContractid(item.getContractId());
                    itemEntity.setCreaterid(paramDto.getZoId());
                    itemEntity.setExpenseitemid(item.getExpenseItemId());
                    itemEntity.setExpenseItemName(item.getExpenseItemName());
                    itemEntity.setFcreatetime(now);
                    itemEntity.setRoomId(item.getRoomId());
                    itemEntity.setSurrendercostid(surCostId);
                    itemEntity.setFisdel(ZraConst.NOT_DEL_INT);
                    itemEntity.setFvalid(ZraConst.VALID_INT);
                    int m = surrenderCostItemDao.insertSelective(itemEntity);
                    if (m != 1) {
                        LogUtil.error(LOGGER, "[解约费用结算下一步]保存tsurrendercostitem出错！surrender_id={}，更新结果：{}条。", surrenderId, m);
                        throw new RuntimeException("[解约费用结算下一步]保存tsurrendercostitem出错!");
                    }
                } else {
                    itemEntity.setFid(item.getSurCostItemId());
                    itemEntity.setUpdaterid(paramDto.getZoId());
                    itemEntity.setFupdatetime(now);
                    int m = surrenderCostItemDao.updateItemById(itemEntity);
                    LogUtil.info(LOGGER, "[解约费用结算下一步]更新tsurrendercostitem表，itemFid={}，更新结果：{}条。", item.getSurCostItemId(), m);
                }
                //======================================================================================================
                if (paramDto.getIsTemp() != 1) {//是否临时保存（0：不是；1：是）;批量解约时，保存单个合同解约信息是不需要生成应收账单/付款单
                    //生成应收/付款单
                    if (originalNum.compareTo(actualNum) != 0) {
                        if (refundNum < 0) {//此时生成应收账单明细
                            //生成应收账单明细
                            FinReceiBillDetailEntity billDetailEntity = new FinReceiBillDetailEntity();
                            billDetailEntity.setActualAmount(actualNum.doubleValue());
                            billDetailEntity.setOughtAmount(originalNum.doubleValue());
                            billDetailEntity.setBillFid(billFid);
                            billDetailEntity.setCreateId(paramDto.getZoId());
                            billDetailEntity.setCreateTime(now);
                            billDetailEntity.setExpenseItemId(Integer.valueOf(item.getExpenseItemId()));
                            billDetailEntity.setFid(UUID.randomUUID().toString());
                            billDetailEntity.setRoomId(item.getRoomId());
                            billDetailEntity.setRemark("解约系统自动的应收账单，不传财务！！！");
                            int n = finReceiBillDetailDao.saveFinReceiBillDetail(billDetailEntity);
                            if (n != 1) {
                                LogUtil.error(LOGGER, "[解约费用结算下一步]保存fin_recei_bill_detail出错！surrender_id={}，更新结果：{}条。", surrenderId, n);
                                throw new RuntimeException("[解约费用结算下一步]保存fin_recei_bill_detail出错!");
                            }
                            //计算应收主表的应收金额
                            receiBillEntity.setOughtTotalAmount(BigDecimal.valueOf(receiBillEntity.getOughtTotalAmount())
                                    .add(originalNum).subtract(actualNum).doubleValue());
                        } else if (refundNum > 0) {//此时生成付款单
                            //生成付款单明细
                            PaymentBillDetailEntity paymentBillDetailEntity = new PaymentBillDetailEntity();
                            paymentBillDetailEntity.setFid(UUID.randomUUID().toString());
                            paymentBillDetailEntity.setCostCode(item.getExpenseItemId());//因为有的没有和财务对应的费用编码，所以存我们自己的
                            paymentBillDetailEntity.setRefundAmount(actualNum.subtract(originalNum).doubleValue());
                            paymentBillDetailEntity.setCreateDate(now);
                            paymentBillDetailEntity.setBillFid(billFid);
                            int count = paymentBillDetailDao.savePaymentBillDetailSingle(paymentBillDetailEntity);
                            if (count != 1) {
                                LogUtil.error(LOGGER, "[解约费用结算下一步]保存t_payment_bill_detail出错！surrender_id={}，更新结果：{}条。", surrenderId, count);
                                throw new RuntimeException("[解约费用结算下一步]保存t_payment_bill_detail出错!");
                            }
                        }
                    }
                }
            });
            //==========================================================================================================
            if (paramDto.getIsTemp() != 1) {//是否临时保存（0：不是；1：是）;批量解约时，保存单个合同解约信息是不需要生成应收账单/付款单
                if (refundNum < 0) {//此时生成应收账单
                    finReceiBillDao.saveFinReceiBill(receiBillEntity);
                } else if (refundNum > 0) {//此时生成付款单
                    paymentBillDao.savePaymentBillSingle(paymentBillEntity);
                }
            }
        });
    }

    /**
     * 根据合同id获取解约协议实体
     *
     * @Author: wangxm113
     * @Date: 2017年11月04日 17时24分21秒
     */
    public SurrenderEntity getSurrenderByConId(String contractId) {
        return surrenderDao.getSurrenderByConId(contractId);
    }

    /**
     * 作废解约协议
     *
     * @Author: wangxm113
     * @Date: 2017年10月24日 11时42分57秒
     */
    public List<SyncContractVo> updateForCancelSurrender(CancelSurrenderDto paramDto) {
        List<SyncContractVo> list = new ArrayList<>();
        String surParentId = paramDto.getSurParentId();
        List<SurrenderEntity> surList = new ArrayList<>();
        //1、查询需要作废的解约协议
        if (surParentId != null && !surParentId.isEmpty()) {//查询父解约协议下所有解约协议
            surList = surrenderDao.getSurListByParentId(surParentId);
        } else {
            SurrenderEntity surrenderByConId = surrenderDao.getSurrenderByConId(paramDto.getContractId());
            surList.add(surrenderByConId);
        }
        //2、还原合同信息
        //3、删除应收账单/付款单
        surList.forEach(p -> {
            String surrenderId = p.getSurrenderId();
            RentContractEntity rentContractEntity = rentContractDao.findContractBaseByContractId(p.getContractId());
            String foriginalstate = rentContractEntity.getForiginalstate();
            int i = rentContractDao.cancelSurrender(foriginalstate, paramDto.getZoId(), paramDto.getZoName(), p.getContractId());
            if (i != 1) {
                LogUtil.error(LOGGER, "[作废解约协议]更新trentcontract出错！contractId={}，更新结果：{}条。", p.getContractId(), i);
                throw new RuntimeException("[解约费用结算下一步]保存tsurrendercostitem出错!");
            }
            //3-1、删除此解约协议对应的所有的应收账单和付款单以及分别对应的明细
            finReceiBillDao.deleteBySurrenderId(surrenderId);
            //3-2、删除付款单以及对应的明细
            paymentBillDao.deleteBySurrenderId(surrenderId);
            //4、作废解约协议
            int j = surrenderDao.deleteBySurrenderId(surrenderId);
            LogUtil.info(LOGGER, "[作废解约协议]更新tsurrender！surrenderId={}，更新结果：{}条。", surrenderId, j);

            SyncContractVo syncContractVo = new SyncContractVo();
            syncContractVo.setCrmContractId(p.getContractId());
            syncContractVo.setRentContractCode(p.getConRentCode());
            syncContractVo.setStatusCode(foriginalstate);
            list.add(syncContractVo);
        });

        return list;
    }

    /**
     * 解约-最后一步：确认解约
     *
     * @Author: wangxm113
     * @Date: 2017年10月24日 14时05分58秒
     */
    public List<String> updateForDoSurrender(List<SurrenderEntity> list, CancelSurrenderDto paramDto) {
        List<String> releaseRoomList = new ArrayList<>();
        list.forEach(p -> {
            String conId = p.getContractId();
            //1、获取合同
            RentContractEntity rentContractEntity = rentContractDao.findContractBaseByContractId(conId);
            //2、将合同状态更改为[已退租]
            RentContractEntity param = new RentContractEntity();
            param.setContractId(conId);
            param.setConStatusCode(ContractStatusEnum.YTZ.getStatus());
            int i = rentContractDao.updateContractToTargetStatus(param);
            LogUtil.info(LOGGER, "[确认解约]更新trentcontract！contractId={}，更新结果：{}条。", conId, i);
            boolean releaseRoom = true;
            //3、判断是否需要释放房间
            if (rentContractEntity.getPreConRentCode() != null
                    && rentContractEntity.getFsigntype().equals(ContractSignTypeEnum.RENEW.getValue())) {
                //3-1、获取前合同信息
                RentContractEntity preContract = rentContractDao.findValidContractByRentCode(rentContractEntity.getPreConRentCode());
                //3-2、判断是否需要释放房间
                if (preContract.getConEndDate().after(new Date())) {
                    releaseRoom = false;
                }
            }
            //4、释放房间
            if (releaseRoom) {
                releaseRoomList.add(rentContractEntity.getRoomId());
            }
            //5、将解约协议更改状态为【已提交】
            int j = surrenderDao.updateForDoSurrender(p.getSurrenderId(), paramDto.getZoId(),
                    paramDto.getZoName(), SubmitStatusEnum.YTJ.getCode());
            LogUtil.info(LOGGER, "[确认解约]更新tsurrender！surrenderId={}，更新结果：{}条。", p.getSurrenderId(), j);
        });
        //6、同步合同到财务
        //7、释放房间
        return releaseRoomList;
    }

    /**
     * 回滚
     *
     * @Author: wangxm113
     * @Date: 2017年11月04日 17时41分09秒
     */
    public void rollBackForDoSurrender(List<SurrenderEntity> list, CancelSurrenderDto paramDto) {
        list.forEach(p -> {
            String conId = p.getContractId();
            //1、获取合同
//            RentContractEntity rentContractEntity = rentContractDao.findContractBaseByContractId(conId);
            //2、将合同状态更改为[解约中]
            RentContractEntity rentContractEntity = new RentContractEntity();
            rentContractEntity.setContractId(conId);
            rentContractEntity.setConStatusCode(ContractStatusEnum.JYZ.getStatus());
            int i = rentContractDao.updateContractToTargetStatus(rentContractEntity);
            LogUtil.info(LOGGER, "[确认解约]回滚trentcontract！contractId={}，更新结果：{}条。", conId, i);
            //5、将解约协议更改状态为【未提交】
            int j = surrenderDao.updateForDoSurrender(p.getSurrenderId(), paramDto.getZoId(),
                    paramDto.getZoName(), SubmitStatusEnum.WTJ.getCode());
            LogUtil.info(LOGGER, "[确认解约]回滚tsurrender！surrenderId={}，更新结果：{}条。", p.getSurrenderId(), j);
        });
    }

    /**
     * 获取批量解约的房间
     *
     * @Author: wangxm113
     * @Date: 2017年11月04日 17时25分46秒
     */
    public PagingResult<SurRoomListReturnDto> getSurRoomList(SurRoomListDto paramDto) {
        return surrenderDao.getSurRoomList(paramDto);
    }
    /**
     * <p>保存解约协议</p>
     * @author xiangb
     * @created 2017年10月30日
     */
    public int saveSurrender(SurrenderEntity surrender){
    	return surrenderDao.saveSurrrender(surrender);
    }

    /**
     * 修改解约协议时点提交审核
     *
     * @Author: wangxm113
     * @Date: 2017年11月07日 16时01分56秒
     */
    public Integer editCommitAudit(String contractId, String surParentId) {
        return surrenderDao.editCommitAudit(contractId, surParentId);
    }
    
    public Integer updateSurrenderApplyTime(Integer time,String contractId){
    	return surrenderDao.updateSurrenderApplyTime(time, contractId);
    }
    /**
     * <p>更新解约协议</p>
     * @author xiangb
     * @created 2017年11月14日
     * @param
     * @return
     */
    public Integer updateSurrender(SurrenderEntity surrender){
    	return surrenderDao.updateSurrender(surrender);
    }
    /**
     * <p>根据解约协议ID查询</p>
     * @author xiangb
     * @created 2017年11月16日
     * @param
     * @return
     */
    public SurrenderEntity findSurrenderById(String surrenderId){
    	return surrenderDao.findSurrenderById(surrenderId);
    }

    /**
     * 计算指定合同的退租交割赔偿费
     *
     * @Author: wangxm113
     * @Date: 2017年12月19日 20时30分22秒
     */
    public BigDecimal getTZJGPCF(String contractId) {
        return surrenderDao.getTZJGPCF(contractId);
    }
    /**
     * <p>保存审核解约协议信息 </p>
     * @author xiangb
     * @created 2017年12月29日
     * @param
     * @return
     */
    public void updateSurrenderAudit(List<SurrenderEntity> surrenderList,List<SurrendBackRecordEntity> surrenderBackRecordList,List<SurrenderCostEntity> surrenderCostList){
    	if(!Check.NuNCollection(surrenderList) && surrenderList.size() > 0){
    		for(SurrenderEntity surrender:surrenderList){
        		this.updateSurrender(surrender);
        	}
    	}
    	if(!Check.NuNCollection(surrenderBackRecordList) && surrenderBackRecordList.size() > 0){
	    	for(SurrendBackRecordEntity surrendBackRecordEntity:surrenderBackRecordList){
	    		if(Check.NuNStr(surrendBackRecordEntity.getFid())){
	    			surrendBackRecordDao.saveSurrendBackRecord(surrendBackRecordEntity);
	    		}
	    		if(!Check.NuNStr(surrendBackRecordEntity.getFid())){
	    			surrendBackRecordDao.updateSurrendBackRecord(surrendBackRecordEntity);
	    		}
	    	}
    	}
    	if(!Check.NuNCollection(surrenderCostList) && surrenderCostList.size() > 0){
    		for(SurrenderCostEntity surrenderCostEntity:surrenderCostList){
    			surrenderCostDao.updateBySurId(surrenderCostEntity);
    		}
    	}
    }
}
