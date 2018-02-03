/**
 * @FileName: GuardAreaDao.java
 * @Package com.ziroom.minsu.services.basedata.dao
 * 
 * @author yd
 * @created 2016年7月5日 下午2:59:34
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.basedata.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.ziroom.minsu.entity.conf.GuardAreaEntity;
import com.ziroom.minsu.entity.house.HouseBaseMsgEntity;
import com.ziroom.minsu.services.basedata.dto.GuardAreaRequest;

/**
 * <p>区域管家数据库持久层</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author yd
 * @since 1.0
 * @version 1.0
 */
@Repository("basedata.guardAreaDao")
public class GuardAreaDao {
	private String SQLID="basedata.guardAreaDao.";

	@Autowired
	@Qualifier("basedata.MybatisDaoContext")
	private MybatisDaoContext mybatisDaoContext;


	/**
	 * 
	 * 添加实体
	 *
	 * @author yd
	 * @created 2016年7月5日 下午3:05:17
	 *
	 * @param guardAreaEntity
	 * @return
	 */
	public int saveGuardArea(GuardAreaEntity guardAreaEntity){

		if(Check.NuNObj(guardAreaEntity)){
			return 0;
		}
		if(Check.NuNObj(guardAreaEntity.getFid())){
			guardAreaEntity.setFid(UUIDGenerator.hexUUID());
		}
		return this.mybatisDaoContext.save(SQLID+"insertSelective", guardAreaEntity);
	}

	/**
	 * 
	 * 根据fid修改
	 *
	 * @author yd
	 * @created 2016年7月5日 下午3:09:49
	 *
	 * @param guardAreaEntity
	 * @return
	 */
	public int updateGuardAreaByFid(GuardAreaEntity guardAreaEntity){

		if(Check.NuNObj(guardAreaEntity)||Check.NuNObj(guardAreaEntity.getFid())){
			return 0;
		}

		return this.mybatisDaoContext.update(SQLID+"updateGuardAreaByFid", guardAreaEntity);
	}

    /**	
     * 
     * 根据fid 获取GuardAreaEntity
     *
     * @author yd
     * @created 2016年7月5日 下午7:12:07
     *
     * @param fid
     * @return
     */
	public GuardAreaEntity findGuardAreaByFid(String fid){
		if(Check.NuNStr(fid)) return null;
		return this.mybatisDaoContext.findOneSlave(SQLID+"findGuardAreaByFid", GuardAreaEntity.class, fid);
	}
    /**
     * 
     * 条件分页查询 区域管家
     *
     * @author yd
     * @created 2016年7月5日 下午3:31:05
     *
     * @param guardAreaR
     * @return
     */
	public PagingResult<GuardAreaEntity> findGaurdAreaByPage(GuardAreaRequest guardAreaR) {
		
		if(Check.NuNObj(guardAreaR)) guardAreaR = new GuardAreaRequest();
		PageBounds pageBounds=new PageBounds();
		pageBounds.setLimit(guardAreaR.getLimit());
		pageBounds.setPage(guardAreaR.getPage());
	
		return mybatisDaoContext.findForPage(SQLID+"findGaurdAreaByPage", GuardAreaEntity.class, guardAreaR, pageBounds);
	}
	
	
	/**
	 * 
	 * 条件查询
	 * 当条件为null的时候 返回空集合
	 *
	 * @author yd
	 * @created 2016年7月5日 下午3:42:14
	 *
	 * @param guardAreaR
	 * @return
	 */
	public List<GuardAreaEntity> findGaurdAreaByCondition(GuardAreaRequest guardAreaR){
		
		if(Check.NuNObj(guardAreaR)) return null;
		return this.mybatisDaoContext.findAllByMaster(SQLID+"findGaurdAreaByCondition", GuardAreaEntity.class, guardAreaR);
	}
	
	/**
	 * 查询
	 * @param paramMap
	 * @return
	 */
	public List<GuardAreaEntity> findByPhy(Map<String, Object> paramMap){
		return mybatisDaoContext.findAll(SQLID+"selectByPhy", GuardAreaEntity.class, paramMap);
	}
	
}
