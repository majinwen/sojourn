package com.zra.cms.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.zra.house.entity.dto.HouseTypeLabDto;
/**
 * cms户型标签配置
 * @author tianxf9
 *
 */
@Repository
public interface CmsHousetypeLabelMapper {
    
	/**
	 * 根据户型id，标签类型查询户型对应的标签
	 * @author tianxf9
	 * @param housetypeId
	 * @param labelType
	 * @return
	 */
    List<HouseTypeLabDto> getLabsByHouseTypeId(@Param("housetypeId")String housetypeId,@Param("labelType")Integer labelType);
}