/**
 * @FileName: CityFeatureHouseTypeDaoTest.java
 * @Package com.ziroom.minsu.services.basedata.test.dao
 * 
 * @author zl
 * @created 2016年12月1日 下午7:33:59
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.basedata.test.dao;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.minsu.entity.conf.CityFeatureHousetypeEntity;
import com.ziroom.minsu.services.basedata.dao.CityFeatureHouseTypeDao;
import com.ziroom.minsu.services.basedata.entity.CityFeatureHouseVo;
import com.ziroom.minsu.services.basedata.entity.FeatureTagsVo;
import com.ziroom.minsu.services.basedata.test.base.BaseTest;

/**
 * <p>TODO</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author zl
 * @since 1.0
 * @version 1.0
 */
public class CityFeatureHouseTypeDaoTest extends BaseTest {

	 @Resource(name = "basedata.cityFeatureHouseTypeDao")
	 private CityFeatureHouseTypeDao cityFeatureHouseTypeDao;
	 
	 
	 @Test
	 public void getCityFeatureHouseTypes() {
		 
		 List<CityFeatureHousetypeEntity> list = cityFeatureHouseTypeDao.getCityFeatureHouseTypes(null, null, "110100");
		 System.out.println(JsonEntityTransform.Object2Json(list));
		
	 }
	 
	 @Test
	 public void getAllCityFeatureHouseTypes() {
		 List<CityFeatureHouseVo> list = cityFeatureHouseTypeDao.getAllCityFeatureHouseTypes();
		 System.out.println(JsonEntityTransform.Object2Json(list));
	 }
	 
	 @Test
	 public void getAllFeatureTags(){
		 List<FeatureTagsVo> list = cityFeatureHouseTypeDao.getAllFeatureTags(null,null);
		 System.out.println(JsonEntityTransform.Object2Json(list));
	 }
	 
	 @Test
	 public void updateFeatureTagByFid(){
		 CityFeatureHousetypeEntity entity  = new CityFeatureHousetypeEntity();
		 entity.setFid("10000011000011010001");
		 entity.setIsValid(0);
		 System.out.println(cityFeatureHouseTypeDao.updateFeatureTagByFid(entity));
	 }
	 

}
