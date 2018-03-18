package com.zra.business.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.zra.common.dto.business.BoCloseSMSContent;
/**
 * 
 * @author tianxf9
 *
 */
@Repository
public interface BoCloseSmsMapper {
	
	public BoCloseSMSContent selectByPrimaryKey(Integer id);
	
	public List<BoCloseSMSContent> getSMSList();
}