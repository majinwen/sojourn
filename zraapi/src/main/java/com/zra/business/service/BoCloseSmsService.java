package com.zra.business.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zra.business.dao.BoCloseSmsMapper;
import com.zra.common.dto.business.BoCloseSMSContent;

/**
 * 
 * @author tianxf9
 *
 */
@Service
public class BoCloseSmsService {
	
	@Autowired
	private BoCloseSmsMapper boCloseSmsMapper;
	
	public BoCloseSMSContent getSmsContentById(Integer id) {
		return boCloseSmsMapper.selectByPrimaryKey(id);
	}
	
	public List<BoCloseSMSContent> getSmsList() {
		return boCloseSmsMapper.getSMSList();
	}

}
