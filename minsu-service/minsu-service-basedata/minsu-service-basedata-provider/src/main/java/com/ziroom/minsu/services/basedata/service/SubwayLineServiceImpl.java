package com.ziroom.minsu.services.basedata.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.minsu.entity.conf.SubwayLineEntity;
import com.ziroom.minsu.entity.conf.SubwayOutEntity;
import com.ziroom.minsu.entity.conf.SubwayStationEntity;
import com.ziroom.minsu.services.basedata.dao.SubwayLineDao;
import com.ziroom.minsu.services.basedata.dao.SubwayOutDao;
import com.ziroom.minsu.services.basedata.dao.SubwayStationDao;
import com.ziroom.minsu.services.basedata.dto.SubwayLineRequest;
import com.ziroom.minsu.services.basedata.dto.SubwayLineSaveRequest;
import com.ziroom.minsu.services.basedata.dto.SubwayOutSaveRequest;
import com.ziroom.minsu.services.basedata.entity.SubwayLineVo;
import com.ziroom.minsu.services.basedata.entity.entityenum.GoogleBaiduCoordinateEnum;
import com.ziroom.minsu.services.common.utils.CoordinateTransforUtils;
import com.ziroom.minsu.services.common.utils.Gps;

/**
 * 
 * @author homelink
 *
 */
@Service("basedata.subwayLineServiceImpl")
public class SubwayLineServiceImpl {

	@Resource(name = "basedata.subwayLineDao")
	private SubwayLineDao subwayLineDao;

	@Resource(name = "basedata.subwayStationDao")
	private SubwayStationDao subwayStationDao;

	@Resource(name = "basedata.subwayOutDao")
	private SubwayOutDao subwayOutDao;

	/**
	 * 分页查询地铁线路
	 * 
	 * @param subwayLineRequest
	 * @return
	 */
	public PagingResult<SubwayLineVo> findSubwayLinePageList(SubwayLineRequest subwayLineRequest) {
		return subwayLineDao.findSubwayLinePageList(subwayLineRequest);
	}

	/**
	 * 单条查询地铁线路
	 * 
	 * @param subwayLineEntity
	 * @return
	 */
	public SubwayLineEntity findSubwayLineByFid(SubwayLineEntity subwayLineEntity) {
		return subwayLineDao.findSubwayLineByFid(subwayLineEntity);
	}

	/**
	 * 单条查询线路站点
	 * 
	 * @param subwayLineEntity
	 * @return
	 */
	public SubwayStationEntity findSubwayStationByFid(SubwayStationEntity subwayStationEntity) {
		return subwayStationDao.findSubwayStationByFid(subwayStationEntity);
	}

	/**
	 * 单条查询站点出口
	 * 
	 * @param subwayLineEntity
	 * @return
	 */
	public SubwayOutEntity findSubwayOutByFid(SubwayOutEntity subwayOutEntity) {
		return subwayOutDao.findSubwayOutByFid(subwayOutEntity);
	}

	/**
	 * 多条查询站点出口
	 * 
	 * @param subwayLineEntity
	 * @return
	 */
	public List<SubwayOutEntity> findSubwayOutByStationFid(SubwayOutEntity subwayOutEntity) {
		return subwayOutDao.findSubwayOutByStationFid(subwayOutEntity);
	}

	/**
	 * 保存地铁线路
	 * 
	 * @param subwayLineSaveRequest
	 */
	public void queryAndSaveSubwayLine(SubwayLineSaveRequest subwayLineSaveRequest) {
		if (Check.NuNObj(subwayLineSaveRequest)) {
			return;
		}

		// 查询地铁线路
		SubwayLineEntity subwayLineEntity = new SubwayLineEntity();
		subwayLineEntity.setLineName(subwayLineSaveRequest.getLineName());
		subwayLineEntity.setNationCode(subwayLineSaveRequest.getNationCode());
		subwayLineEntity.setProvinceCode(subwayLineSaveRequest.getProvinceCode());
		subwayLineEntity.setCityCode(subwayLineSaveRequest.getCityCode());
		List<SubwayLineEntity> lineList = subwayLineDao.findSubwayLine(subwayLineEntity);
		if(Check.NuNObj(lineList) || lineList.size() <= 0){
			// 保存地铁线路表
			subwayLineEntity.setFid(UUIDGenerator.hexUUID());
			subwayLineEntity.setSort(subwayLineSaveRequest.getLineSort());
			subwayLineEntity.setCreateFid(subwayLineSaveRequest.getCreateFid());
			subwayLineDao.saveSubwayLine(subwayLineEntity);
		} else{
			subwayLineEntity = lineList.get(0);
		}

		// 查询线路站点
		SubwayStationEntity subwayStationEntity = new SubwayStationEntity();
		subwayStationEntity.setLineFid(subwayLineEntity.getFid());
		subwayStationEntity.setStationName(subwayLineSaveRequest.getStationName());
		List<SubwayStationEntity> stationList = subwayStationDao.findSubwayStation(subwayStationEntity);
		if(Check.NuNObj(stationList) || stationList.size() <= 0){
			// 保存线路站点表
			subwayStationEntity.setFid(UUIDGenerator.hexUUID());
			GoogleBaiduCoordinateEnum.SubwayStationEntity.googleBaiduCoordinateTranfor(subwayStationEntity,subwayLineSaveRequest.getStationLatitude(),subwayLineSaveRequest.getStationLatitude(), subwayLineSaveRequest.getMapType());
			
			subwayStationEntity.setSort(subwayLineSaveRequest.getStationSort());
			subwayStationEntity.setCreateFid(subwayLineSaveRequest.getCreateFid());
			subwayStationDao.saveSubwayStation(subwayStationEntity);
		} else {
			subwayStationEntity = stationList.get(0);
		}

		List<SubwayOutSaveRequest> outRequestList = subwayLineSaveRequest.getOutList();
		if (Check.NuNObj(outRequestList) && outRequestList.size() >= 1) {
			return;
		}
		for (SubwayOutSaveRequest subwayOutSaveRequest : outRequestList) {
			// 保存站点出口表
			SubwayOutEntity subwayOutEntity = new SubwayOutEntity();
			subwayOutEntity.setFid(UUIDGenerator.hexUUID());
			subwayOutEntity.setStationFid(subwayStationEntity.getFid());
			subwayOutEntity.setOutName(subwayOutSaveRequest.getOutName());
			
			GoogleBaiduCoordinateEnum.SubwayOutEntity.googleBaiduCoordinateTranfor(subwayOutEntity, subwayOutSaveRequest.getOutLatitude(), subwayOutSaveRequest.getOutLongitude(), subwayLineSaveRequest.getMapType());
			subwayOutEntity.setSort(subwayOutSaveRequest.getOutSort());
			subwayOutEntity.setCreateFid(subwayLineSaveRequest.getCreateFid());
			subwayOutDao.saveSubwayOut(subwayOutEntity);
		}
	}

	/**
	 * 修改地铁线路
	 * 
	 * @param subwayLineSaveRequest
	 */
	public void updateSubwayLine(SubwayLineSaveRequest subwayLineSaveRequest) {
		if (Check.NuNObj(subwayLineSaveRequest)) {
			return;
		}

		// 修改地铁线路表
		SubwayLineEntity subwayLineEntity = new SubwayLineEntity();
		subwayLineEntity.setFid(subwayLineSaveRequest.getLineFid());
		subwayLineEntity.setLineName(subwayLineSaveRequest.getLineName());
		subwayLineEntity.setNationCode(subwayLineSaveRequest.getNationCode());
		subwayLineEntity.setProvinceCode(subwayLineSaveRequest.getProvinceCode());
		subwayLineEntity.setCityCode(subwayLineSaveRequest.getCityCode());
		subwayLineEntity.setSort(subwayLineSaveRequest.getLineSort());
		int lineNum = subwayLineDao.updateSubwayLine(subwayLineEntity);

		// 修改线路站点表
		SubwayStationEntity subwayStationEntity = new SubwayStationEntity();
		subwayStationEntity.setFid(subwayLineSaveRequest.getStationFid());
		subwayStationEntity.setStationName(subwayLineSaveRequest.getStationName());
		subwayStationEntity.setLongitude(subwayLineSaveRequest.getStationLongitude());
		subwayStationEntity.setLatitude(subwayLineSaveRequest.getStationLatitude());
		subwayStationEntity.setSort(subwayLineSaveRequest.getStationSort());
		int stationNum = subwayStationDao.updateSubwayStation(subwayStationEntity);

		List<SubwayOutSaveRequest> outRequestList = subwayLineSaveRequest.getOutList();
		if (Check.NuNObj(outRequestList)) {
			return;
		}
		for (SubwayOutSaveRequest subwayOutSaveRequest : outRequestList) {
			// 修改站点出口表
			SubwayOutEntity subwayOutEntity = new SubwayOutEntity();
			subwayOutEntity.setFid(subwayOutSaveRequest.getOutFid());
			subwayOutEntity.setOutName(subwayOutSaveRequest.getOutName());
			subwayOutEntity.setLongitude(subwayOutSaveRequest.getOutLongitude());
			subwayOutEntity.setLatitude(subwayOutSaveRequest.getOutLatitude());
			subwayOutEntity.setSort(subwayOutSaveRequest.getOutSort());
			subwayOutDao.updateSubwayOut(subwayOutEntity);
		}
	}

}
