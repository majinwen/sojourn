package com.ziroom.minsu.services.order.service;

import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.minsu.entity.order.FinanceIncomeEntity;
import com.ziroom.minsu.entity.order.FinanceSyncLogEntity;
import com.ziroom.minsu.services.finance.entity.FinanceIncomeVo;
import com.ziroom.minsu.services.order.dao.FinanceIncomeDao;
import com.ziroom.minsu.services.order.dao.FinanceIncomeLogDao;
import com.ziroom.minsu.services.order.dao.FinanceSyncLogDao;
import com.ziroom.minsu.valenum.common.YesOrNoEnum;
import com.ziroom.minsu.valenum.finance.SyncStatusEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>收入表</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * @author lishaochuan on 2016年5月1日
 * @since 1.0
 * @version 1.0
 */
@Service("order.financeIncomeServiceImpl")
public class FinanceIncomeServiceImpl {

	/**
	 * 日志对象
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(FinanceIncomeServiceImpl.class);

	@Resource(name = "order.financeIncomeDao")
	private FinanceIncomeDao financeIncomeDao;
	
	@Resource(name = "order.financeIncomeLogDao")
	private FinanceIncomeLogDao financeIncomeLogDao;
	
	@Resource(name ="order.financeSyncLogDao")
	private  FinanceSyncLogDao financeSyncLogDao;
	
	/**
	 * 查询未同步收入记录数
	 * @author lishaochuan
	 * @create 2016年4月30日
	 * @return
	 */
	public Long getNotSyncIncomeCount(Date runTimeStart, Date runTimeEnd) {
		Map<String, Object> map = new HashMap<String, Object>();
		//map.put("incomeStatus", IncomeStatusEnum.YES.getCode()); //已收款的
		//map.put("syncStatus", SyncStatusEnum.unsync.getCode());
		map.put("runTimeStart", runTimeStart);
		map.put("runTimeEnd", runTimeEnd);
		return financeIncomeDao.getNotSyncIncomeCount(map);
	}
	
	/**
	 * 查询未同步收入记录list
	 * @author lishaochuan
	 * @create 2016年4月30日
	 * @param limit
	 * @return
	 */
	public List<FinanceIncomeVo> getNotSyncIncomeList(Date runTimeStart, Date runTimeEnd, int limit){
		Map<String, Object> map = new HashMap<String, Object>();
		//map.put("incomeStatus", IncomeStatusEnum.YES.getCode()); //已收款的
		//map.put("syncStatus", SyncStatusEnum.unsync.getCode());
		map.put("runTimeStart", runTimeStart);
		map.put("runTimeEnd", runTimeEnd);
		map.put("limit", limit);
		return financeIncomeDao.getNotSyncIncomeList(map);
	}

	/**
	 * 更新收入表记录
	 * 
	 * @author lishaochuan
	 * @create 2016年4月30日
	 * @param financeIncomeEntity
	 * @return
	 */
	public int updateIncomeByIncomeSn(FinanceIncomeEntity financeIncomeEntity) {
		return financeIncomeDao.updateIncomeByIncomeSn(financeIncomeEntity);
	}
	
	
	/**
	 * 财务同步收入后，更新状态，记录日志
	 * @author lishaochuan
	 * @create 2016年5月14日下午6:42:53
	 * @param financeIncomeVo
	 * @param resultMap
	 */
	public void updateSyncIncomeStatus(FinanceIncomeVo financeIncomeVo, Map<String,String> resultMap){
		int syncStatus = SyncStatusEnum.fail.getCode();
		int callStatus = YesOrNoEnum.NO.getCode();
		String resultCode = resultMap.get("code");
		String message = resultMap.get("message");
		String resultMsg = "【同步收入Job】,result:" + resultCode + ",message:" + message;
		Date actualSyncTime = null;
		// 200：成功，508：重复
		if("200".equals(resultCode) || "508".equals(resultCode)){
			syncStatus = SyncStatusEnum.success.getCode();
			callStatus = YesOrNoEnum.YES.getCode();
			actualSyncTime = new Date();
		}
		
		FinanceIncomeEntity financeIncomeEntity = new FinanceIncomeEntity();
		financeIncomeEntity.setIncomeSn(financeIncomeVo.getIncomeSn());
		financeIncomeEntity.setSyncStatus(syncStatus);
		financeIncomeEntity.setActualSyncTime(actualSyncTime);
		financeIncomeDao.updateIncomeByIncomeSn(financeIncomeEntity);
		
		FinanceSyncLogEntity fsle = new FinanceSyncLogEntity();
		fsle.setFid(UUIDGenerator.hexUUID());
		fsle.setSyncSn(financeIncomeVo.getIncomeSn());
		fsle.setOrderSn(financeIncomeVo.getOrderSn());
		fsle.setCallStatus(callStatus);
		fsle.setResultCode(resultCode);
		if(!Check.NuNStr(resultMsg) && resultMsg.length()>= 500){
			resultMsg = resultMsg.substring(0, 500);
		}
		fsle.setResultMsg(resultMsg);
		financeSyncLogDao.insertFinanceSyncLog(fsle);
	}


	/**
	 * 查询用户结算生成的收入
	 * @author lishaochuan
	 * @create 2017/1/10 18:19
	 * @param 
	 * @return 
	 */
	public List<FinanceIncomeEntity> getSettlementIncomeListByOrderSn(String orderSn){
		return financeIncomeDao.getSettlementIncomeListByOrderSn(orderSn);
	}
	
}
