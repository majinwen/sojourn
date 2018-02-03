/**
 * @FileName: CustomerBaseMsgDao.java
 * @Package com.ziroom.minsu.services.customer.dao
 * 
 * @author bushujie
 * @created 2016年4月22日 下午4:08:48
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.customer.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.util.Check;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.customer.LandlordStatisticsEntity;

/**
 * <p>房东行为信息Dao</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author liyingjie
 * @since 1.0
 * @version 1.0
 */
@Repository("customer.landlordStaticsDao")
public class LandlordStaticsDao {
	private static Logger logger = LoggerFactory.getLogger(LandlordStaticsDao.class);
	private String SQLID="customer.landlordStaticsDao.";
	
	@Autowired
	@Qualifier("customer.MybatisDaoContext")
	private MybatisDaoContext mybatisDaoContext;


	/**
	 * 更新房东行为统计信息
	 * @author liyingjie
	 * @created 2016年7月29日 下午4:15:46
	 * @param customerBaseMsgEntity
	 * @return
	 */
	public int staticsUpdateLanActAssociation(LandlordStatisticsEntity landlordStatistics){
		if(Check.NuNObj(landlordStatistics)){
    		LogUtil.info(logger, "staticsCountLanReplyOrderTime 参数对象为空");
			throw new BusinessException("staticsCountLanReplyOrderTime 参数对象为空");
    	}
    	if(Check.NuNStr(landlordStatistics.getLandlordUid())){
    		LogUtil.info(logger, "staticsCountLanReplyOrderTime landlordUid为空");
			throw new BusinessException("staticsCountLanReplyOrderTime landlordUid为空");
    	}
    	
    	return mybatisDaoContext.update(SQLID+"staticsUpdateLanActAssociation", landlordStatistics);
	}

	/**
	 * 新增房东行为统计信息
	 * @author liyingjie
	 * @created 2016年7月29日 下午4:15:46
	 * @param customerBaseMsgEntity
	 * @return
	 */
	public void staticsInsertLanActAssociation(LandlordStatisticsEntity landlordStatistics){
		if(Check.NuNObj(landlordStatistics)){
    		LogUtil.info(logger, "staticsCountLanReplyOrderTime 参数对象为空");
			throw new BusinessException("staticsCountLanReplyOrderTime 参数对象为空");
    	}
    	if(Check.NuNStr(landlordStatistics.getLandlordUid())){
    		LogUtil.info(logger, "staticsCountLanReplyOrderTime landlordUid为空");
			throw new BusinessException("staticsCountLanReplyOrderTime landlordUid为空");
    	}
    	
    	mybatisDaoContext.save(SQLID+"staticsInsertLanActAssociation", landlordStatistics);
	}
	
	/**
	 * 查询 房东统计信息 
	 * @author liyingjie
	 * @created 2016年7月29日 下午4:15:46
	 * @param landlordUid
	 * @return
	 */
	public LandlordStatisticsEntity findLandlordStatisticsByUid(String landlordUid){
		if(Check.NuNStr(landlordUid)){
    		LogUtil.info(logger, "findLandlordStatisticsByUid landlordUid为空");
			throw new BusinessException("findLandlordStatisticsByUid landlordUid为空");
    	}
    	
    	return mybatisDaoContext.findOne(SQLID+"getByLandlordUid", LandlordStatisticsEntity.class, landlordUid);
	}
	
	
	
}
