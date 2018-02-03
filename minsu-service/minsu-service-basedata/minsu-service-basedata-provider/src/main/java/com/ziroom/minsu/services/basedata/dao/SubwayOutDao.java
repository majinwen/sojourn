package com.ziroom.minsu.services.basedata.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.ziroom.minsu.entity.conf.SubwayOutEntity;

@Repository("basedata.subwayOutDao")
public class SubwayOutDao {

	private String SQLID = "basedata.subwayOutDao.";

	@Autowired
	@Qualifier("basedata.MybatisDaoContext")
	private MybatisDaoContext mybatisDaoContext;
	
	
	/**
	 * 按Fid查询站点出口
	 * @param subwayOut
	 * @return
	 */
	public SubwayOutEntity findSubwayOutByFid(SubwayOutEntity subwayOut) {
		return mybatisDaoContext.findOne(SQLID + "findSubwayOutByFid", SubwayOutEntity.class, subwayOut);
	}
	
	/**
	 * 按stationFid查询站点出口
	 * @param subwayOut
	 * @return
	 */
	public List<SubwayOutEntity> findSubwayOutByStationFid(SubwayOutEntity subwayOut) {
		return mybatisDaoContext.findAll(SQLID + "findSubwayOutByStationFid", SubwayOutEntity.class, subwayOut);
	}

	/**
	 * 保存地铁出口
	 * 
	 * @param subwayOut
	 * @return
	 */
	public int saveSubwayOut(SubwayOutEntity subwayOut) {
		return mybatisDaoContext.save(SQLID + "insertSubwayOut", subwayOut);
	}

	/**
	 * 修改地铁出口
	 * 
	 * @param subwayOut
	 * @return
	 */
	public int updateSubwayOut(SubwayOutEntity subwayOut) {
		return mybatisDaoContext.save(SQLID + "updateSubwayOutByFid", subwayOut);
	}
}
