package com.zra.cms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zra.cms.dao.CmsHousetypeMapper;
import com.zra.cms.entity.CmsHousetype;

/***
 * 户型服务类
 * @author tianxf9
 *
 */
@Service
public class CmsHousetypeService {
	
	@Autowired
	private CmsHousetypeMapper cmsHousetypeMapper;
	
	/**
	 * @author tianxf9
	 * @param houseTypeId
	 * @return
	 */
	public CmsHousetype getCmsHousetypeEntity(String houseTypeId) {
		return this.cmsHousetypeMapper.selectHouseTypeId(houseTypeId);
	}

}
