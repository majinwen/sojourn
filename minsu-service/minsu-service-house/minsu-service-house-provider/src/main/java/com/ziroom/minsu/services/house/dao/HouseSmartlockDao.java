package com.ziroom.minsu.services.house.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.ziroom.minsu.entity.house.HouseSmartlockEntity;

/**
 * 
 * <p>房源智能锁密码管理</p>
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
@Repository("house.houseSmartlockDao")
public class HouseSmartlockDao {
private String SQLID="house.houseSmartlockDao.";
	
	private static Logger logger = LoggerFactory.getLogger(HouseSmartlockDao.class);

	@Autowired
	@Qualifier("house.MybatisDaoContext")
	private MybatisDaoContext mybatisDaoContext;
	
	/**
	 * 
	 * 插入房源智能锁记录
	 *
	 * @author jixd
	 * @created 2016年6月24日 上午9:36:20
	 *
	 * @param houseSmartlockEntity
	 * @return
	 */
	public int saveHouseSmartlock(HouseSmartlockEntity houseSmartlockEntity){
		return mybatisDaoContext.save(SQLID+"insertHouseSmartlock", houseSmartlockEntity);
	}
	
	/**
	 * 
	 * 更新记录
	 *
	 * @author jixd
	 * @created 2016年6月24日 上午9:38:42
	 *
	 * @param houseSmartlockEntity
	 * @return
	 */
	public int updateHouseSmartlock(HouseSmartlockEntity houseSmartlockEntity){
		return mybatisDaoContext.save(SQLID+"updateByfid", houseSmartlockEntity);
	}
	
	/**
	 * 
	 * 查询列表（组合条件查，只有一个或者为空）
	 *
	 * @author jixd
	 * @created 2016年6月24日 上午10:21:35
	 *
	 * @param houseSmartlockEntity
	 * @return
	 */
	public List<HouseSmartlockEntity> findHouseSmartlock(HouseSmartlockEntity houseSmartlockEntity){
		return mybatisDaoContext.findAll(SQLID+"findHouseSmartlock",HouseSmartlockEntity.class,houseSmartlockEntity);
	}
}