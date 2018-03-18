package com.ziroom.zrp.service.houses.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ziroom.zrp.houses.entity.DictionaryEntity;
import com.ziroom.zrp.service.houses.dao.DictionaryDao;

@Service("houses.dictionaryServiceImpl")
public class DictionaryServiceImpl {
	
	@Resource(name="houses.dictionaryDao")
	private DictionaryDao dictionaryDao;
	
	public List<DictionaryEntity> findDictionaryByEnumName(String enumName){
		return dictionaryDao.findDictionaryByEnumName(enumName);
	}

}
