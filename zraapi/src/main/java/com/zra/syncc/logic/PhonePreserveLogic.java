package com.zra.syncc.logic;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.zra.common.utils.MultipleDataSource;
import com.zra.syncc.entity.PhonePreserveEntity;
import com.zra.syncc.service.PhonePreserveService;

/**
 * HOMELINK_PHONEPRESERVE表Logic
 * 
 * @author tianxf9
 *
 */
@Component
public class PhonePreserveLogic {

	private static final Logger LOGGER = LoggerFactory.getLogger(PhonePreserveLogic.class);
	@Autowired
	private PhonePreserveService phonePreserveService;

	public boolean updatePhonePreserve(List<PhonePreserveEntity> phoneDtos) {

		boolean result = true;
		MultipleDataSource.setDataSourceKey("newccdataSource");
		for (PhonePreserveEntity entity : phoneDtos) {
			try {
				 this.phonePreserveService.updatePhonePreserve(entity);
			}catch(Exception e) {
				LOGGER.error("cc库操作失败！", e);
				result = false;
				break;
			}
		}
		MultipleDataSource.setDataSourceKey("dataSource");
		return result;
	}


	/**
	 * 根据分机号删除CC分机用户表记录
	 * 
	 * @author tianxf9
	 * @param extensionNumbers
	 * @return
	 */
	public boolean deletePhonePreserveByExtNum(List<String> extensionNumbers) {
		boolean result = true;
		if (extensionNumbers != null && extensionNumbers.size() > 0) {
			MultipleDataSource.setDataSourceKey("newccdataSource");
			try {
				 this.phonePreserveService.delPhonePreserve(extensionNumbers);
			}catch(Exception e){
				LOGGER.error("cc库操作失败！", e);
				result = false;
			}
			MultipleDataSource.setDataSourceKey("dataSource");
			LOGGER.info("删除cc库分机用户表:extNum=" + JSON.toJSONString(extensionNumbers));
		}
		
		return result;
	}

	/**
	 * 调用400接口保存CC分机用户表
	 * 
	 * @author tianxf9
	 * @param dtos
	 * @return
	 */
	public boolean savePhonePreserve(List<PhonePreserveEntity> phoneEntitys) {
		
		boolean result = true;
		MultipleDataSource.setDataSourceKey("newccdataSource");
		try {
			 this.phonePreserveService.insertPhonePreserveEntity(phoneEntitys);
		} catch (Exception e) {
			LOGGER.error("CC数据库操作失败！", e);
			result = false;
		}
		MultipleDataSource.setDataSourceKey("dataSource");
		return result;
	}

}
