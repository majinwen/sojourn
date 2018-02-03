package com.ziroom.minsu.services.basedata.dao;

import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.ziroom.minsu.entity.conf.CityRegionEntity;
import com.ziroom.minsu.entity.conf.CityRegionRelEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 大区关联信息
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
@Repository("basedata.cityRegionRelDao")
public class CityRegionRelDao {

	private String SQLID = "basedata.cityRegionRelDao.";

	@Autowired
	@Qualifier("basedata.MybatisDaoContext")
	private MybatisDaoContext mybatisDaoContext;

	/**
	 * 插入操作
	 * @author jixd
	 * @created 2017年01月09日 10:25:36
	 * @param
	 * @return
	 */
	public int insertCityRegionRel(CityRegionRelEntity cityRegionRelEntity){
		if (Check.NuNStr(cityRegionRelEntity.getFid())){
			cityRegionRelEntity.setFid(UUIDGenerator.hexUUID());
		}
		return mybatisDaoContext.save(SQLID + "insert",cityRegionRelEntity);
	}

	/**
	 * 更新操作
	 * @author jixd
	 * @created 2017年01月09日 10:25:51
	 * @param
	 * @return
	 */
	public int updateByFid(CityRegionRelEntity cityRegionRelEntity){
		return mybatisDaoContext.update(SQLID + "updateByFid",cityRegionRelEntity);
	}

	/**
	 * 查找
	 * @author jixd
	 * @created 2017年01月10日 20:55:51
	 * @param
	 * @return
	 */
	public List<CityRegionRelEntity> findCityRegionRelList(CityRegionRelEntity cityRegionRelEntity){
		return mybatisDaoContext.findAll(SQLID + "findCityRegionRelList",CityRegionRelEntity.class,cityRegionRelEntity);
	}

}
