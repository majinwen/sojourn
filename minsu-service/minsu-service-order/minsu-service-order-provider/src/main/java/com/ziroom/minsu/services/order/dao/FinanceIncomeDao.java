package com.ziroom.minsu.services.order.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.order.FinanceIncomeEntity;
import com.ziroom.minsu.services.finance.dto.FinanceIncomeRequest;
import com.ziroom.minsu.services.finance.entity.FinanceIncomeVo;
import com.ziroom.minsu.services.order.utils.OrderSnUtil;
import com.ziroom.minsu.valenum.order.IncomeStatusEnum;
import com.ziroom.minsu.valenum.order.IncomeTypeEnum;


/**
 * <p>收入记录表操作</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * @author lishaochuan on 2016年4月20日
 * @since 1.0
 * @version 1.0
 */
@Repository("order.financeIncomeDao")
public class FinanceIncomeDao {
	/**
	 * 日志对象
	 */
	private static Logger logger = LoggerFactory.getLogger(FinanceIncomeDao.class);
	private String SQLID = "order.financeIncomeDao.";

	@Autowired
	@Qualifier("order.MybatisDaoContext")
	private MybatisDaoContext mybatisDaoContext;
	
	
	/**
	 * 保存收入表记录
	 * @author lishaochuan
	 * @create 2016年4月20日
	 * @param financeIncomeEntity
	 * @return
	 */
	public int insertFinanceIncome(FinanceIncomeEntity financeIncomeEntity) {
		if (Check.NuNObj(financeIncomeEntity)) {
			LogUtil.error(logger, "insertFinanceIncome param:{}",financeIncomeEntity);
			throw new BusinessException("financeIncomeEntity is null on insertFinanceIncome");
		}
		
        if(Check.NuNStr(financeIncomeEntity.getIncomeSn())){
            financeIncomeEntity.setIncomeSn(OrderSnUtil.getIncomeSn());
        }
        if(Check.NuNObj(financeIncomeEntity.getGenerateFeeTime())){
            financeIncomeEntity.setGenerateFeeTime(new Date());
        }

        if(Check.NuNObj(financeIncomeEntity.getRunTime())){
            financeIncomeEntity.setRunTime(new Date());
        }
        //设置默认状态
        if(Check.NuNObj(financeIncomeEntity.getIncomeStatus())){
            financeIncomeEntity.setIncomeStatus(IncomeStatusEnum.NO.getCode()); //未收款
        }
		
		return mybatisDaoContext.save(SQLID + "insertSelective", financeIncomeEntity);
	}
	
	/**
	 * 修改收入表状态
	 * @author lishaochuan
	 * @create 2016年9月18日下午4:48:04
	 * @param financeIncomeEntity
	 * @return
	 */
	public int consumeIncome(FinanceIncomeEntity financeIncomeEntity){
		if (Check.NuNObj(financeIncomeEntity)) {
			LogUtil.error(logger, "financeIncomeEntity is null on updateIncomeStatus");
			throw new BusinessException("financeIncomeEntity is null on updateIncomeStatus");
		}
		return mybatisDaoContext.update(SQLID + "consumeIncome", financeIncomeEntity);
	}
	
	/**
	 * 更新收入表记录
	 * @author lishaochuan
	 * @create 2016年4月22日
	 * @param financeIncomeEntity
	 * @return
	 */
	public int updateIncomeByIncomeSn(FinanceIncomeEntity financeIncomeEntity) {
		if (Check.NuNObj(financeIncomeEntity)) {
			LogUtil.error(logger, "financeIncomeEntity is null on updateIncomeByIncomeSn");
			throw new BusinessException("financeIncomeEntity is null on updateIncomeByIncomeSn");
		}
		return mybatisDaoContext.update(SQLID + "updateIncomeByIncomeSn", financeIncomeEntity);
	}
	
	/**
	 * 通过orderSn、incomeType修改收入表
	 * @author lishaochuan
	 * @create 2016年4月29日
	 * @param map
	 * @return
	 */
	public int updateIncomeLandlordPunish(Map<String, Object> map){
		if (Check.NuNObj(map)) {
			LogUtil.error(logger, "updateIncomeByOrder param:{}",map);
			throw new BusinessException("map is null on updateIncomeLandlordPunish");
		}
		List<Integer> incometTypeList = new ArrayList<Integer>(); 
		incometTypeList.add(IncomeTypeEnum.LANDLORD_PUNISH_COMMISSION.getCode());
		incometTypeList.add(IncomeTypeEnum.LANDLORD_PUNISH.getCode());
		map.put("incometTypeList", incometTypeList);
		
		return mybatisDaoContext.update(SQLID + "updateIncomeLandlordPunish", map);
	}

    /**
     * 通过订单获取收入信息
     * @author afi
     * @param orderSn
     * @return
     */
    public List<FinanceIncomeEntity> getIncomeListByOrderSn(String orderSn){
        if(Check.NuNStr(orderSn)){
            LogUtil.error(logger, "getIncomeListByOrderSn param:{}",orderSn);
            throw new BusinessException("orderSn is null");
        }
        return mybatisDaoContext.findAllByMaster(SQLID + "getIncomeListByOrderSn", FinanceIncomeEntity.class, orderSn);
    }

	/**
	 * 查询清洁费收入
	 * @author lishaochuan
	 * @create 2017/1/6 9:49
	 * @param 
	 * @return 
	 */
	public List<FinanceIncomeEntity> getCleanIncomeListByOrderSn(String orderSn){
		return mybatisDaoContext.findAllByMaster(SQLID + "getCleanIncomeListByOrderSn", FinanceIncomeEntity.class, orderSn);
	}

	/**
	 * 查询用户结算生成的收入
	 * @author lishaochuan
	 * @create 2017/1/10 18:18
	 * @param 
	 * @return 
	 */
	public List<FinanceIncomeEntity> getSettlementIncomeListByOrderSn(String orderSn){
		return mybatisDaoContext.findAll(SQLID + "getSettlementIncomeListByOrderSn", FinanceIncomeEntity.class, orderSn);
	}

    public int deleteIncomeByIncomeSnList(List<String> incomeSnList){
       if(Check.NuNCollection(incomeSnList)){
           throw new BusinessException("fidList is null");
       }
        Map<String,Object> par = new HashMap<>();
        par.put("incomeSnList",incomeSnList);
        return mybatisDaoContext.update(SQLID + "deleteIncomeByIncomeSnList", par);
    }
    
    
    /**
     * 查询收入表中未收款记录数量
     * @author lishaochuan
     * @create 2016年4月22日
     * @param map
     * @return
     */
    public Long getNotIncomeCount(Map<String, Object> map){
    	if (Check.NuNObj(map)) {
			LogUtil.error(logger, "getNotIncomeCount param:{}",map);
			throw new BusinessException("map is null on getNotIncomeCount");
		}
		return mybatisDaoContext.findOne(SQLID + "getNotIncomeCount", Long.class, map);
    }
    
    /**
     * 查询收入表中未收款记录list
     * @author lishaochuan
     * @create 2016年4月22日
     * @param map
     * @return
     */
    public List<FinanceIncomeEntity> getNotIncomeList(Map<String, Object> map){
    	if(Check.NuNObj(map)){
            LogUtil.error(logger, "getNotIncomeList param:{}",map);
            throw new BusinessException("map is null on getNotIncomeList");
        }
        return mybatisDaoContext.findAll(SQLID + "getNotIncomeList", FinanceIncomeEntity.class, map);
    }
    
    /**
     * 
     * 按条件分页查询收入表数据
     *
     * @author yd
     * @created 2016年4月28日 下午9:33:12
     *
     * @param financeIncomeRequest
     * @return
     */
    public PagingResult<FinanceIncomeVo> queryFinanceIncomeByPage(FinanceIncomeRequest financeIncomeRequest){
    	
    	if(Check.NuNObj(financeIncomeRequest)) financeIncomeRequest = new FinanceIncomeRequest();
    	
    	PageBounds pageBounds = new PageBounds();
    	pageBounds.setLimit(financeIncomeRequest.getLimit());
    	pageBounds.setPage(financeIncomeRequest.getPage());
    	if(financeIncomeRequest.getRoleType()>0){
    		return this.mybatisDaoContext.findForPage(SQLID + "specialQueryFinanceIncomeByPage", FinanceIncomeVo.class, financeIncomeRequest.toMap(), pageBounds);
    	}
    	return this.mybatisDaoContext.findForPage(SQLID + "queryFinanceIncomeByPage", FinanceIncomeVo.class, financeIncomeRequest, pageBounds);
    }


    /**
     * 获取收入明细
     * @author afi
     * @param incomeSn
     * @return
     */
    public FinanceIncomeVo getFinanceIncomeDetail(String incomeSn){
        return this.mybatisDaoContext.findOneSlave(SQLID + "getFinanceIncomeDetail", FinanceIncomeVo.class, incomeSn);
    }
    
    
    
    /**
     * 查询未同步收入记录数
     * @author lishaochuan
     * @create 2016年4月22日
     * @param map
     * @return
     */
    public Long getNotSyncIncomeCount(Map<String, Object> map){
    	if (Check.NuNObj(map)) {
			LogUtil.error(logger, "getNotSyncIncomeCount param:{}",map);
			throw new BusinessException("map is null on getNotSyncIncomeCount");
		}
		return mybatisDaoContext.findOne(SQLID + "getNotSyncIncomeCount", Long.class, map);
    }
    
    /**
     * 查询未同步收入记录list
     * @author lishaochuan
     * @create 2016年4月22日
     * @param map
     * @return
     */
    public List<FinanceIncomeVo> getNotSyncIncomeList(Map<String, Object> map){
    	if(Check.NuNObj(map)){
            LogUtil.error(logger, "getNotSyncIncomeList param{}",map);
            throw new BusinessException("map is null on getNotSyncIncomeList");
        }
        return mybatisDaoContext.findAllByMaster(SQLID + "getNotSyncIncomeList", FinanceIncomeVo.class, map);
    }

}

