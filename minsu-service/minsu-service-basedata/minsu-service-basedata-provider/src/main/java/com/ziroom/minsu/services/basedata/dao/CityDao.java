package com.ziroom.minsu.services.basedata.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.ziroom.minsu.entity.sys.CityEntity;

/**
 * <p>
 * 城市信息
 * </p>
 * <p/>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/3/12.
 * @version 1.0
 * @since 1.0
 */
@Repository("basedata.cityDao")
public class CityDao {

	private String SQLID = "basedata.cityDao.";

	@Autowired
	@Qualifier("basedata.MybatisDaoContext")
	private MybatisDaoContext mybatisDaoContext;

	/**
	 * 获取城市列表
	 * 
	 * @author afi
	 * @created 2016年3月12日
	 *
	 */
	public List<CityEntity> getCityList() {
		return mybatisDaoContext.findAll(SQLID + "getCityList", CityEntity.class);
	}

}
