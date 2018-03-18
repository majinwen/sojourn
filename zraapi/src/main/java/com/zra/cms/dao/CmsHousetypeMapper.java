package com.zra.cms.dao;

import org.springframework.stereotype.Repository;

import com.zra.cms.entity.CmsHousetype;
/**
 * cms户型配置
 * @author tianxf9
 *
 */
@Repository
public interface CmsHousetypeMapper {
	
    CmsHousetype selectHouseTypeId(String housetypeId);
}