package com.zra.house.logic;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zra.house.entity.HouseTypeEntity;
import com.zra.house.service.HouseTypeService;

/**
 * 户型逻辑层
 * @author tianxf9
 *
 */
@Component
public class HouseTypeLogic {
	
	@Autowired
	private HouseTypeService houseTypeService; 
	
	/**
	 * 获取所有户型
	 * @author tianxf9
	 * @return
	 */
	public List<HouseTypeEntity> getAllHouseType() {
		return this.houseTypeService.findAllHouseType();
	}

	public String getPhoneByHtId(String houseTypeId) {
		return houseTypeService.getPhoneByHtId(houseTypeId);
	}

}
