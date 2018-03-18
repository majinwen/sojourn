package com.ziroom.zrp.service.houses.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.ziroom.zrp.houses.entity.DictionaryEntity;

@Repository("houses.dictionaryDao")
public class DictionaryDao {
	
	private String SQLID = "houses.dictionaryDao.";

	@Autowired
	@Qualifier("houses.MybatisDaoContext")
	private MybatisDaoContext mybatisDaoContext;
	
	public List<DictionaryEntity> findDictionaryByEnumName(String enumName){
		return mybatisDaoContext.findAll(SQLID+"findDictionaryByEnumName",DictionaryEntity.class,enumName);
	}
	
	
	    
	    
	    
}
