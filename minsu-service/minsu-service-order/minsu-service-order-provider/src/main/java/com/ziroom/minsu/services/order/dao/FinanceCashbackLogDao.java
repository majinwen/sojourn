package com.ziroom.minsu.services.order.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.util.Check;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.order.FinanceCashbackLogEntity;

/**
 * <p>订单返现操作日志Dao</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * @author lishaochuan on 2016年9月9日
 * @since 1.0
 * @version 1.0
 */
@Repository("order.financeCashbackLogDao")
public class FinanceCashbackLogDao {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(FinanceCashbackLogDao.class);
	private String SQLID = "order.financeCashbackLogDao.";
	
	@Autowired
	@Qualifier("order.MybatisDaoContext")
	private MybatisDaoContext mybatisDaoContext;
	
	
	/**
	 * 根据返现单号，查询返现日志
	 * @author lishaochuan
	 * @create 2016年9月11日下午2:50:33
	 * @param cashbackSn
	 * @return
	 */
	public List<FinanceCashbackLogEntity> queryByCashbackSn(String cashbackSn){
		if(Check.NuNStr(cashbackSn)){
			LogUtil.error(LOGGER, "queryByCashbackSn()参数为空,cashbackSn:{}", cashbackSn);
			throw new BusinessException("queryByCashbackSn()参数为空");
		}
		return mybatisDaoContext.findAll(SQLID + "queryByCashbackSn", cashbackSn);
	}
	
	
	/**
	 * 保存返现log
	 * @author lishaochuan
	 * @create 2016年9月9日上午9:46:09
	 * @param cashback
	 * @return
	 */
	public int saveCashbackLog(FinanceCashbackLogEntity cashbackLog){
		if(Check.NuNObj(cashbackLog)){
			LogUtil.error(LOGGER, "saveCashbackLog()参数为空,cashbackLog:{}", cashbackLog);
			throw new BusinessException("saveCashbackLog()参数为空");
		}
		return mybatisDaoContext.save(SQLID + "insert", cashbackLog);
	}
	
	/**
	 * 批量保存返现log
	 * @author lishaochuan
	 * @create 2016年9月11日上午11:52:24
	 * @param cashbackLogs
	 * @return
	 */
	public int saveCashbackLogs(List<FinanceCashbackLogEntity> cashbackLogs){
		if(Check.NuNCollection(cashbackLogs)){
			LogUtil.error(LOGGER, "saveCashbackLogs()参数为空,cashbackLogs:{}", cashbackLogs);
			throw new BusinessException("saveCashbackLogs()参数为空");
		}
		int num = 0;
		for (FinanceCashbackLogEntity cashbackLog : cashbackLogs) {
			num += mybatisDaoContext.save(SQLID + "insert", cashbackLog);
		}
		return num;
	}

}
