package com.zra.cms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zra.cms.dao.CmsHousetypeLabelMapper;
import com.zra.house.entity.dto.HouseTypeLabDto;

/**
 * 
 * @author tianxf9
 *
 */
@Service
public class CmsHousetypeLabelService {
	
	@Autowired
	private CmsHousetypeLabelMapper cmsHousetypeLabelMapper;
	
	/**
	 * 根据标签类别和户型id获取户型标签
	 * @author tianxf9
	 * @param housetypeId
	 * @param labType
	 * @return
	 */
	public List<HouseTypeLabDto> getLabs(String housetypeId,Integer labType) {
		return this.cmsHousetypeLabelMapper.getLabsByHouseTypeId(housetypeId, labType);
	}

}
