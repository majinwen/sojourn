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
import com.ziroom.minsu.entity.order.FinanceSyncLogEntity;

/**
 * <p>财务同步记录日志表</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * @author liyingjie on 2016年5月17日
 * @since 1.0
 * @version 1.0
 */
@Repository("order.financeSyncLogDao")
public class FinanceSyncLogDao {
	
	/**
	 * 日志对象
	 */
	private static Logger logger = LoggerFactory.getLogger(FinanceSyncLogDao.class);
	private String SQLID = "order.financeSyncLogDao.";

	@Autowired
	@Qualifier("order.MybatisDaoContext")
	private MybatisDaoContext mybatisDaoContext;
	
	
	/**
	 * 保存日志表记录
	 * @author liyingjie
	 * @create 2016年5月17日
	 * @param financeSyncLogEntity
	 * @return
	 */
	public int insertFinanceSyncLog(FinanceSyncLogEntity financeSyncLogEntity) {
		if (Check.NuNObj(financeSyncLogEntity)) {
			LogUtil.error(logger, "financeSyncLogEntity is null on insertFinanceSyncLog");
			throw new BusinessException("financeSyncLogEntity is null on insertFinanceSyncLog");
		}
		return mybatisDaoContext.save(SQLID + "insertSelective", financeSyncLogEntity);
	}

}
