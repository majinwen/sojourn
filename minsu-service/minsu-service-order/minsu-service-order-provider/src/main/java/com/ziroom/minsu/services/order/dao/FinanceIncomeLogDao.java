package com.ziroom.minsu.services.order.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.util.Check;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.order.FinanceIncomeLogEntity;

/**
 * <p>收入记录日志表</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * @author lishaochuan on 2016年4月23日
 * @since 1.0
 * @version 1.0
 */
@Repository("order.financeIncomeLogDao")
public class FinanceIncomeLogDao {
	
	/**
	 * 日志对象
	 */
	private static Logger logger = LoggerFactory.getLogger(FinanceIncomeLogDao.class);
	private String SQLID = "order.inanceIncomeLogDao.";

	@Autowired
	@Qualifier("order.MybatisDaoContext")
	private MybatisDaoContext mybatisDaoContext;

	/**
	 * 保存收入记录表日志
	 * @author lishaochuan
	 * @create 2016年4月23日
	 * @param financeIncomeLogEntity
	 * @return
	 */
	public int insertFinanceIncomeLog(FinanceIncomeLogEntity financeIncomeLogEntity) {
		if (Check.NuNObj(financeIncomeLogEntity)) {
			LogUtil.error(logger, "insertFinanceIncome param:{}",financeIncomeLogEntity);
			throw new BusinessException("financeIncomeLogEntity is null on insertFinanceIncome");
		}
		return mybatisDaoContext.save(SQLID + "insertSelective", financeIncomeLogEntity);
	}
	
	
	
}
