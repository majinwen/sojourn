package com.ziroom.minsu.services.basedata.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.ziroom.minsu.entity.conf.SubwayStationEntity;

@Repository("basedata.subwayStationDao")
public class SubwayStationDao {

	private String SQLID = "basedata.subwayStationDao.";

	@Autowired
	@Qualifier("basedata.MybatisDaoContext")
	private MybatisDaoContext mybatisDaoContext;

	/**
	 * 按Fid查询线路站点
	 * 
	 * @param subwayLine
	 * @return
	 */
	public SubwayStationEntity findSubwayStationByFid(SubwayStationEntity subwayStationEntity) {
		return mybatisDaoContext.findOne(SQLID + "findSubwayStationByFid", SubwayStationEntity.class, subwayStationEntity);
	}
	
	/**
	 * 按条件查询线路站点
	 * 
	 * @param subwayLine
	 * @return
	 */
	public List<SubwayStationEntity> findSubwayStation(SubwayStationEntity subwayStationEntity) {
		return mybatisDaoContext.findAll(SQLID + "findSubwayStation", subwayStationEntity);
	}

	/**
	 * 保存地铁站点
	 * 
	 * @param subwayStation
	 * @return
	 */
	public int saveSubwayStation(SubwayStationEntity subwayStation) {
		return mybatisDaoContext.save(SQLID + "insertSubwayStation", subwayStation);
	}

	/**
	 * 修改地铁站点
	 * 
	 * @param subwayStation
	 * @return
	 */
	public int updateSubwayStation(SubwayStationEntity subwayStation) {
		return mybatisDaoContext.save(SQLID + "updateSubwayStationByFid", subwayStation);
	}

}
