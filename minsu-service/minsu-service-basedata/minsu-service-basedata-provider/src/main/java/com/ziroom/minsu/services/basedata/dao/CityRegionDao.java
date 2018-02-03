package com.ziroom.minsu.services.basedata.dao;

import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.ziroom.minsu.entity.conf.CityRegionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 大区信息
 * </p>
 * <p/>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd on 2017/1/19.
 * @version 1.0
 * @since 1.0
 */
@Repository("basedata.cityRegionDao")
public class CityRegionDao {

	private String SQLID = "basedata.cityRegionDao.";

	@Autowired
	@Qualifier("basedata.MybatisDaoContext")
	private MybatisDaoContext mybatisDaoContext;


	public int insertCityRegion(CityRegionEntity cityRegionEntity){
		if (Check.NuNStr(cityRegionEntity.getFid())){
			cityRegionEntity.setFid(UUIDGenerator.hexUUID());
		}
		return mybatisDaoContext.save(SQLID + "insert",cityRegionEntity);
	}


	public int updateByFid(CityRegionEntity cityRegionEntity){
		return mybatisDaoContext.update(SQLID + "updateByFid",cityRegionEntity);
	}


	public CityRegionEntity findByName(String name){
		return mybatisDaoContext.findOne(SQLID + "findByName",CityRegionEntity.class,name);
	}


	public List<CityRegionEntity> findAllRegion(){
		return mybatisDaoContext.findAll(SQLID + "findAllRegion");
	}
}
