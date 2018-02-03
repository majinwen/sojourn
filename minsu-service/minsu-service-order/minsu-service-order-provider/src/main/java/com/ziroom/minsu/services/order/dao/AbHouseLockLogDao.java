package com.ziroom.minsu.services.order.dao;

import com.asura.framework.base.util.Check;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.ziroom.minsu.entity.order.AbHouseLockLogEntity;
import com.ziroom.minsu.entity.order.HouseLockLogEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

/**
 * <p> 房源锁定日志 </p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd
 * @version 1.0
 * @since 1.0
 */
@Repository("order.abHouseLockLogDao")
public class AbHouseLockLogDao {

	/**
	 * 日志对象
	 */
	private static Logger logger = LoggerFactory.getLogger(AbHouseLockLogDao.class);

	private String SQLID = "order.abHouseLockLogDao.";

	@Autowired
	@Qualifier("order.MybatisDaoContext")
	private MybatisDaoContext mybatisDaoContext;

	/**
	 * 保存锁定房源操作日志
	 * @author jixd
	 * @created 2017年04月11日 14:50:32
	 * @param
	 * @return
	 */
	public int saveLog(AbHouseLockLogEntity abHouseLockLogEntity){
		if (Check.NuNObj(abHouseLockLogEntity)){
			return 0;
		}
		return mybatisDaoContext.save(SQLID + "insertSelective",abHouseLockLogEntity);
	}



}
