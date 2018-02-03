package com.ziroom.minsu.services.cms.dao;

import com.asura.framework.base.util.Check;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.ziroom.minsu.entity.cms.PointDetailEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * 
 * <p>积分明细表</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author loushuai
 * @since 1.0
 * @version 1.0
 */
@Repository("cms.pointDetailDao")
public class PointDetailDao {
	
	 /**
     * 日志对象
     */
    private static Logger LOGGER = LoggerFactory.getLogger(PointDetailDao.class);
	
    private String SQLID = "cms.pointDetailDao.";
	@Autowired
	@Qualifier("cms.MybatisDaoContext")
	private MybatisDaoContext mybatisContextDao;
	
    /**
	 * 
	 * 插入积分明细实体
	 *
	 * @author loushuai
	 * @created 2017年12月1日 下午1:53:19
	 *
	 * @param record
	 * @return
	 */
    public int insertPointDetail(PointDetailEntity record){
    	return mybatisContextDao.save("insertPointDetail", record);
    };

    /**
	 * 
	 * 获取积分明细实体
	 *
	 * @author loushuai
	 * @created 2017年12月1日 下午1:53:19
	 *
	 * @param record
	 * @return
	 */
    public  PointDetailEntity selectByParam(PointDetailEntity record){
    	return mybatisContextDao.findOne(SQLID+"selectByParam", PointDetailEntity.class, record);
    };

    /**
	 * 
	 * 插入积分明细实体
	 *
	 * @author loushuai
	 * @created 2017年12月1日 下午1:53:19
	 *
	 * @param map
	 * @return
	 */
    public int updateByParam(Map<String, Object> map){
    	return mybatisContextDao.update(SQLID+"updateByParam", map);
    };

	/**
	 *
	 * @author yanb
	 * @created 2017年12月20日 14:50:47
	 * @param  * @param record
	 * @return java.lang.Integer
	 */
	public Integer countByParam(PointDetailEntity record) {
		Integer result = mybatisContextDao.findOne(SQLID + "countByParam", Integer.class, record);
		//做非空赋值
		if (Check.NuNObj(result)) {
			result = 0;
		}
		return result;
	}
}