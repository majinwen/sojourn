package com.zra.syncc.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.zra.syncc.dao.PhonePreserveMapper;
import com.zra.syncc.entity.PhonePreserveEntity;

@Service
public class PhonePreserveService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PhonePreserveService.class);
	@Autowired
	private PhonePreserveMapper phonePreserveMapper;
	
	/**
	 * 保存HOMELINK_PHONEPRESERVE表数据
	 * @author tianxf9
	 * @param phoneEntity
	 * @return
	 */
	public int insertPhonePreserveEntity(List<PhonePreserveEntity> phoneEntitys) {
		
		int rows = 0;
		LOGGER.info("=====新增HOMELINK_PHONEPRESERVE表数据："+JSON.toJSONString(phoneEntitys));
		for(int i = 0;i<phoneEntitys.size();i++) {
			  rows = rows + this.phonePreserveMapper.insertPhonePreserve(phoneEntitys.get(i));
		}
		return rows;
	}
	
	/**
	 * 删除HOMELINK_PHONEPRESERVE表数据
	 * @author tianxf9
	 * @param extNums
	 * @return
	 */
	public int delPhonePreserve(List<String> extNums) {
		int rows = 0;
		if(extNums!=null&&extNums.size()>0) {
			LOGGER.info("=====删除分机号为："+JSON.toJSONString(extNums)+"HOMELINK_PHONEPRESERVE表数据");
			rows = this.phonePreserveMapper.delPhonePreserve(extNums);
		}
		return rows;
	}
	
	/**
	 * 更新HOMELINK_PHONEPRESERVE表数据
	 * @author tianxf9
	 * @param phoneEntity
	 * @return
	 */
	public int updatePhonePreserve(PhonePreserveEntity phoneEntity) {
		LOGGER.info("=====更新HOMELINK_PHONEPRESERVE表数据："+JSON.toJSONString(phoneEntity)+"");
		int rows = this.phonePreserveMapper.updatePhonePreserve(phoneEntity);
		return rows;
	}

}
