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

import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.ziroom.minsu.entity.customer.LandlordBehaviorEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

/**
 * <p>房东行为信息Dao</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author jixd
 * @since 1.0
 * @version 1.0
 */
@Repository("customer.landlordBehaviorDao")
public class LandlordBehaviorDao {
	private static Logger logger = LoggerFactory.getLogger(LandlordBehaviorDao.class);
	private String SQLID="customer.landlordBehaviorDao.";
	
	@Autowired
	@Qualifier("customer.MybatisDaoContext")
	private MybatisDaoContext mybatisDaoContext;


	/**
	 * 更新房东统计数据
	 * @author jixd
	 * @created 2016年11月04日 09:36:40
	 * @param
	 * @return
	 */
	public int updateLandlordBehavior(LandlordBehaviorEntity landLordBehaviorEntity){
    	return mybatisDaoContext.update(SQLID+"updateByUid", landLordBehaviorEntity);
	}

	/**
	 * 插入房东统计数据
	 * @author jixd
	 * @created 2016年11月04日 09:37:35
	 * @param
	 * @return
	 */
	public int insertLandlordBehavior(LandlordBehaviorEntity landLordBehaviorEntity){
		if (Check.NuNStr(landLordBehaviorEntity.getFid())){
			landLordBehaviorEntity.setFid(UUIDGenerator.hexUUID());
		}
    	return mybatisDaoContext.save(SQLID+"insertSelective", landLordBehaviorEntity);
	}
	
	/**
	 * 查询单个房东行为数据
	 * @author jixd
	 * @created 2016年11月04日 09:55:07
	 * @param
	 * @return
	 */
	public LandlordBehaviorEntity findLandlordBehavior(String landlordUid){
    	return mybatisDaoContext.findOne(SQLID+"findBehaviorByUid", LandlordBehaviorEntity.class, landlordUid);
	}
	
	
	
}
