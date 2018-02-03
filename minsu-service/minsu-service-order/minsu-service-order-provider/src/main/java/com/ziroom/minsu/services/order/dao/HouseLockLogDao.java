package com.ziroom.minsu.services.order.dao;

import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.util.Check;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.order.HouseLockEntity;
import com.ziroom.minsu.entity.order.HouseLockLogEntity;
import com.ziroom.minsu.services.common.utils.DateSplitUtil;
import com.ziroom.minsu.services.house.dto.HousePriorityDto;
import com.ziroom.minsu.services.utils.HouseLockUtil;
import com.ziroom.minsu.valenum.house.RentWayEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.text.SimpleDateFormat;
import java.util.*;

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
@Repository("order.houseLockLogDao")
public class HouseLockLogDao {

	/**
	 * 日志对象
	 */
	private static Logger logger = LoggerFactory.getLogger(HouseLockLogDao.class);

	private String SQLID = "order.houseLockLogDao.";

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
	public int saveLog(HouseLockLogEntity houseLockLogEntity){
		if (Check.NuNObj(houseLockLogEntity)){
			return 0;
		}
		return mybatisDaoContext.save(SQLID + "insertSelective",houseLockLogEntity);
	}



}
