package com.ziroom.minsu.report.board.dao;

import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.ziroom.minsu.report.board.entity.CityTargetLogEntity;
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
@Repository("report.cityTargetLogDao")
public class CityTargetLogDao {

	private String SQLID = "report.cityTargetLogDao.";

	@Autowired
	@Qualifier("minsuReport.report.MybatisDaoContext")
	private MybatisDaoContext mybatisDaoContext;

	/**
	 * 插入
	 * @author jixd
	 * @created 2017年01月09日 14:34:55
	 * @param
	 * @return
	 */
	public int insertCityTargetLog(CityTargetLogEntity cityTargetLogEntity){
		return mybatisDaoContext.save(SQLID + "insert",cityTargetLogEntity);
	}

	/**
	 * 查找历史记录
	 * @author jixd
	 * @created 2017年01月09日 14:45:39
	 * @param
	 * @return
	 */
	public List<CityTargetLogEntity> findCityTargetLog(String targetFid){
		return mybatisDaoContext.findAll(SQLID + "selectLog",targetFid);
	}

}
