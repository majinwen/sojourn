/**
 * @FileName: IntellectWatermeterReadServiceImpl.java
 * @Package com.ziroom.zrp.service.trading.service
 * 
 * @author lusp
 * @created 2018年2月7日 下午1:47:05
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.zrp.service.trading.service;

import com.ziroom.zrp.service.trading.dao.FinReceiBillDao;
import com.ziroom.zrp.service.trading.dao.FinReceiBillDetailDao;
import com.ziroom.zrp.service.trading.dao.IntellectWaterMeterBillLogDao;
import com.ziroom.zrp.service.trading.dao.IntellectWatermeterClearDao;
import com.ziroom.zrp.trading.entity.FinReceiBillDetailEntity;
import com.ziroom.zrp.trading.entity.FinReceiBillEntity;
import com.ziroom.zrp.trading.entity.IntellectWaterMeterBillLogEntity;
import com.ziroom.zrp.trading.entity.IntellectWatermeterClearEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>智能水表清算</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author lusp
 * @since 1.0
 * @version 1.0
 */
@Service("trading.intellectWatermeterClearServiceImpl")
public class IntellectWaterMeterClearServiceImpl {
	
    @Resource(name = "trading.finReceiBillDao")
    private FinReceiBillDao finReceiBillDao;

    @Resource(name = "trading.finReceiBillDetailDao")
    private FinReceiBillDetailDao finReceiBillDetailDao;

    @Resource(name="trading.IntellectWaterMeterBillLogDao")
    private IntellectWaterMeterBillLogDao intellectWaterMeterBillLogDao;

    @Resource(name="trading.IntellectWatermeterClearDao")
    private IntellectWatermeterClearDao intellectWatermeterClearDao;
	
	/**
	 * @description: 根据合同id查询智能水表清算记录
	 * @author: lusp
	 * @date: 2018/2/7 下午 14:35
	 * @params: contractId
	 * @return: List<IntellectWatermeterClearEntity>
	 */
	public List<IntellectWatermeterClearEntity> findIntellectWatermeterClearByContractId(String contractId){
		return intellectWatermeterClearDao.findIntellectWatermeterClearByContractId(contractId);
	}

    /**
     * @description: 结算水表清算记录时保存本地数据
     *              1.保存应收账单主表记录
     *              2.保存应收账单明细表记录
     *              3.保存智能水表应收账单记录
     * @author: lusp
     * @date: 2018/2/8 下午 15:01
     * @params:
     * @return:
     */
    public void saveReceivableInfo(FinReceiBillEntity finReceiBillEntity, FinReceiBillDetailEntity finReceiBillDetailEntity, IntellectWaterMeterBillLogEntity intellectWaterMeterBillLogEntity) {
        finReceiBillDao.saveFinReceiBill(finReceiBillEntity);
        finReceiBillDetailDao.saveFinReceiBillDetail(finReceiBillDetailEntity);
        intellectWaterMeterBillLogDao.insertIntellectWaterMeterBillLog(intellectWaterMeterBillLogEntity);
    }

    /**
     * @description: 批量更新清算记录表中的清算记录的结算状态
     * @author: lusp
     * @date: 2018/2/8 下午 16:04
     * @params: intellectWatermeterClearEntities,settlementStatus
     * @return:
     */
    public void updateSettlementStatusBatch(List<IntellectWatermeterClearEntity> intellectWatermeterClearEntities,Integer settlementStatus){
        IntellectWatermeterClearEntity updateEntity = new IntellectWatermeterClearEntity();
        updateEntity.setSettlementStatus(settlementStatus);
        for (IntellectWatermeterClearEntity i:intellectWatermeterClearEntities){
            updateEntity.setFid(i.getFid());
            intellectWatermeterClearDao.updateIntellectWatermeterClear(updateEntity);
        }
    }

    /**
     * 更新智能水表清算记录状态为已转移
     * @author cuiyuhui
     * @created
     * @param
     * @return
     */
    public int updateIntellectWatermeterClearStatus(String contractId) {
        return intellectWatermeterClearDao.updateIntellectWatermeterClearStatus(contractId);
    }

    /**
     * 更新智能水表清算记录状态为已结算
     * @author xiangb
     * @created 2018年3月16日11:40:17
     * @param
     * @return
     */
    public int updateClearStatusToYJS(String contractId) {
        return intellectWatermeterClearDao.updateClearStatusToYJS(contractId);
    }

    /**
     *
     * 查询最新（不管未结算、已结算）的清算记录
     *
     * @author zhangyl2
     * @created 2018年02月27日 18:20
     * @param
     * @return
     */
    public IntellectWatermeterClearEntity getLastClearingReading(String contractId){
        return intellectWatermeterClearDao.getLastClearingReading(contractId);
    }

    /**
     *
     * 插入智能水表清算记录
     *
     * @author zhangyl2
     * @created 2018年02月28日 16:52
     * @param
     * @return
     */
    public int insertIntellectWatermeterClear(IntellectWatermeterClearEntity intellectWatermeterClearEntity){
        return intellectWatermeterClearDao.insertIntellectWatermeterClear(intellectWatermeterClearEntity);
    }

}
