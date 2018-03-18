package com.ziroom.zrp.service.houses.dao;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import com.alibaba.fastjson.JSONObject;
import com.ziroom.zrp.houses.entity.DictionaryEntity;
import com.ziroom.zrp.service.houses.base.BaseTest;

public class DictionaryDaoTest extends BaseTest{
	
	@Resource(name="houses.dictionaryDao")
	private DictionaryDao dictionaryDao;
	
	@Test
	public void testfindDictionaryByEnumName(){
		String enumName = "BusiTaskTypeEnum";
		List<DictionaryEntity> entityList = dictionaryDao.findDictionaryByEnumName(enumName);
		for(DictionaryEntity entity : entityList){
			System.err.println(JSONObject.toJSON(entity));
		}
	}
	
	
	
	

}
