package com.ziroom.minsu.services.order.dao;

import java.util.Date;
import java.util.HashMap;
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
import com.ziroom.minsu.entity.order.FinancePunishEntity;
import com.ziroom.minsu.services.finance.dto.PunishListRequest;

/**
 * 
 * <p>
 * 扣款单记录表
 * </p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author lishaochuan on 2016年4月25日
 * @since 1.0
 * @version 1.0
 */
@Repository("order.financePunishDao")
public class FinancePunishDao {

	/**
	 * 日志对象
	 */
	private static Logger logger = LoggerFactory.getLogger(FinanceIncomeDao.class);
	private String SQLID = "order.financePunishDao.";

	@Autowired
	@Qualifier("order.MybatisDaoContext")
	private MybatisDaoContext mybatisDaoContext;
	
	
	/**
	 * 插入扣款单表
	 * @author lishaochuan
	 * @create 2016年4月25日
	 * @param financePunishEntity
	 * @return
	 */
	public int saveFinancePunish(FinancePunishEntity financePunishEntity) {
		if (Check.NuNObj(financePunishEntity)) {
			LogUtil.error(logger, "financePunishEntity is null on insertFinancePunish");
			throw new BusinessException("financePunishEntity is null on insertFinancePunish");
		}
		return mybatisDaoContext.save(SQLID + "insertSelective", financePunishEntity);
	}
	
	/**
	 * 根据punishSn获取扣款单
	 * @author lishaochuan
	 * @create 2016年4月27日
	 * @param punishSn
	 * @return
	 */
	public FinancePunishEntity getPunishByPunishSn(String punishSn){
		return mybatisDaoContext.findOne(SQLID + "selectByPunishSn", FinancePunishEntity.class, punishSn);
	}
	
	/**
	 * 分页获取扣款单列表
	 * @author lishaochuan
	 * @create 2016年4月25日
	 * @param punishRequest
	 * @return
	 */
	public PagingResult<FinancePunishEntity> getPunishListByCondition(PunishListRequest punishRequest){
    	PageBounds pageBounds = new PageBounds();
		pageBounds.setLimit(punishRequest.getLimit());
		pageBounds.setPage(punishRequest.getPage());
    	return mybatisDaoContext.findForPage(SQLID + "getPunishListByCondition", FinancePunishEntity.class, punishRequest, pageBounds);
    }
	
	   
    /**
     * 更新扣款单表数据
     * @author lishaochuan
     * @create 2016年4月27日
     * @param financePunishEntity
     * @return
     */
    public int updateByPunishSn(FinancePunishEntity financePunishEntity) {
		if (Check.NuNObj(financePunishEntity) || Check.NuNStr(financePunishEntity.getPunishSn())) {
			LogUtil.error(logger, "financePunishEntity is null on updateByPunishSn | {}", financePunishEntity);
			return 0;
		}
		return mybatisDaoContext.update(SQLID + "updateByPunishSn", financePunishEntity);
	}


    /**
     * 获取当前订单的罚款记录
     * @author afi
     * @create 2016年4月26日
     * @param orderSn
     * @return
     */
    public FinancePunishEntity getPunishListByOrderSn(String orderSn){
        return mybatisDaoContext.findOne(SQLID + "getPunishListByOrderSn", FinancePunishEntity.class, orderSn);

    }

    /**
     * 获取当前的房东的过期罚款数量
     * @param landlordUid
     * @return
     */
    public Long countPunishOverTime(String landlordUid){
        Map<String,Object> par = new HashMap<>();
        par.put("landlordUid",landlordUid);
        par.put("nowTime",new Date());
        return mybatisDaoContext.countBySlave(SQLID + "countPunishOverTime", par);
    }
 
}
