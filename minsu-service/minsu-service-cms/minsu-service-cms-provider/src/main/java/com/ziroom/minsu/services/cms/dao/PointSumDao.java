package com.ziroom.minsu.services.cms.dao;

import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.util.Check;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.cms.PointSumEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository("cms.pointSumDao")
public class PointSumDao {
	
	private static Logger LOGGER = LoggerFactory.getLogger(PointSumDao.class);
	
	String SQLID = "cms.pointSumDao.";
	
	@Autowired
	@Qualifier("cms.MybatisDaoContext")
	private MybatisDaoContext mybatisDaoContext;

	/**
	 * 
	 * 插入积分总数实体
	 *
	 * @author loushuai
	 * @created 2017年12月1日 下午2:46:54
	 *
	 * @param record
	 * @return
	 */
    public int insertPointSum(PointSumEntity record){
		return mybatisDaoContext.save(SQLID+"insertPointSum", record);
    }
    	
    /**
	 * 
	 * 根据uid查询实体
	 *
	 * @author loushuai
	 * @created 2017年12月1日 下午2:46:54
	 *
	 * @param record
	 * @return
	 */
	public PointSumEntity selectByParam(PointSumEntity record){
		return mybatisDaoContext.findOne(SQLID+"selectByParam", PointSumEntity.class, record);
	};

    /**
	 * 
	 * 更新实体
	 * 根据uid和pointsSource
	 * @author loushuai
	 * @created 2017年12月1日 下午2:46:54
	 *
	 * @param record
	 * @return
	 */
    public int updateByParam(PointSumEntity record){
    	return mybatisDaoContext.update(SQLID+"updateByParam", record);
    };

	/**
	 * 根据用户uid和pointsSource获取PointSum信息
	 * @author yanb
	 * @created 2017年12月10日 16:51:17
	 * @param  * @param null
	 * @return
	 */
	public PointSumEntity getPointSumByUidSource(Map<String,Object> param) {
		if (Check.NuNObj(param)) {
			LogUtil.error(LOGGER, "参数为空");
			throw new BusinessException("参数为空");
		}
		return mybatisDaoContext.findOne(SQLID + "getPointSumByUidSource", PointSumEntity.class, param);
	}


}