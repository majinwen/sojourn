/**
 * @FileName: CurrentuserCityDao.java
 * @Package com.ziroom.minsu.services.basedata.dao
 * 
 * @author bushujie
 * @created 2016年10月24日 下午3:17:03
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.ups.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ziroom.minsu.services.basedata.entity.CurrentuserCityVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.ziroom.minsu.entity.sys.CurrentuserCityEntity;

/**
 * <p>管理用户负责区域Dao</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author bushujie
 * @since 1.0
 * @version 1.0
 */
@Repository("ups.currentuserCityDao")
public class CurrentuserCityDao {
	
	private String SQLID="ups.currentuserCityDao.";
	
	@Autowired
	@Qualifier("ups.MybatisDaoContext")
	private MybatisDaoContext mybatisDaoContext;
	
	/**
	 * 
	 * 插入用户负责区域信息
	 *
	 * @author bushujie
	 * @created 2016年10月24日 下午3:19:07
	 *
	 * @param currentuserCityEntity
	 */
	public int insertCurrentuserCity(CurrentuserCityEntity currentuserCityEntity){
		return mybatisDaoContext.save(SQLID+"insertCurrentuserCity", currentuserCityEntity);
	}
	
	/**
	 * 
	 * 删除用户负责区域
	 *
	 * @author bushujie
	 * @created 2016年10月25日 上午10:55:27
	 *
	 * @param currentuserFid
	 */
	public int delCurrentuserCity(String currentuserFid){
		Map<String,Object> params=new HashMap<String, Object>();
		params.put("currentuserFid", currentuserFid);
		return mybatisDaoContext.delete(SQLID+"deleteCurrentuserCityByFid", params);
	}
	
	/**
	 * 
	 * 查询用户负责区域
	 *
	 * @author bushujie
	 * @created 2016年10月26日 下午3:15:35
	 *
	 * @param currentuserFid
	 * @return
	 */
	public List<CurrentuserCityEntity> findCuserCityByUserFid(String currentuserFid){
		return mybatisDaoContext.findAllByMaster(SQLID+"findCuserCityByUserFid", CurrentuserCityEntity.class, currentuserFid);
	}

	/**
	 * 查询用户城市列表
	 * @author jixd
	 * @created 2016年12月08日 20:29:14
	 * @param
	 * @return
	 */
	public List<CurrentuserCityVo> getCurrentuserCity(String fid){
		return mybatisDaoContext.findAll(SQLID + "getCurrentuserCity",CurrentuserCityVo.class,fid);
	}
}
