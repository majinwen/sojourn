package com.ziroom.minsu.services.basedata.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.asura.framework.base.util.Check;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.ziroom.minsu.entity.conf.CityFeatureHousetypeEntity;
import com.ziroom.minsu.services.basedata.constant.BaseDataConstant;
import com.ziroom.minsu.services.basedata.entity.CityFeatureHouseVo;
import com.ziroom.minsu.services.basedata.entity.FeatureTagsVo;

/**
 * 
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
@Repository("basedata.cityFeatureHouseTypeDao")
public class CityFeatureHouseTypeDao {

	private String SQLID = "basedata.cityFeatureHouseTypeDao.";

	@Autowired
	@Qualifier("basedata.MybatisDaoContext")
	private MybatisDaoContext mybatisDaoContext;

	/**
	 * 查询城市下的特色房源
	 * TODO
	 *
	 * @author zl
	 * @created 2016年12月1日 下午5:46:11
	 *
	 * @param countryCode
	 * @param provinceCode
	 * @param cityCode
	 * @return
	 */
	public List<CityFeatureHousetypeEntity> getCityFeatureHouseTypes(String countryCode,String provinceCode,String cityCode) {
		 
		Map<String,String> params = new HashMap<String,String>();
		
		if (!Check.NuNStr(countryCode)) {
			params.put("countryCode", countryCode);
		}
		if (!Check.NuNStr(provinceCode)) {
			params.put("provinceCode", provinceCode);
		}
		if (!Check.NuNStr(cityCode)) {
			params.put("cityCode", cityCode);
		}
		return mybatisDaoContext.findAll(SQLID + "getCityFeatureHouseTypes", CityFeatureHousetypeEntity.class,params);
	}
	
	/**
	 * 查询所有的有特色房源的城市的房源
	 * TODO
	 *
	 * @author zl
	 * @created 2016年12月1日 下午5:58:03
	 *
	 * @return
	 */
	public List<CityFeatureHouseVo> getAllCityFeatureHouseTypes() {
		return mybatisDaoContext.findAll(SQLID + "getAllCityFeatureHouseTypes",CityFeatureHouseVo.class);
	}
	
	/**
	 * 根据有效状态查询所有的特色标识列表
	 * @author zl
	 * @created 2017年1月9日 下午5:58:03
	 * @param isValid
	 * @return
	 */
	public List<FeatureTagsVo> getAllFeatureTags(Boolean isValid,String templateFid) {
		Map<String,Object> params = new HashMap<String,Object>();
		if(!Check.NuNObj(isValid)){
			params.put("isValid", isValid);
		}
		if (Check.NuNStr(templateFid)) {
			templateFid = BaseDataConstant.HOUSE_RENTWAY_BY_HOUSE;
		}
		params.put("templateFid", templateFid);
		return mybatisDaoContext.findAll(SQLID + "getAllFeatureTags",FeatureTagsVo.class,params);
	
	}
	
	/**
	 * 更新特色标识
	 * @param entity
	 * @return
	 */
	public Integer updateFeatureTagByFid(CityFeatureHousetypeEntity entity){
		if(Check.NuNObj(entity) || Check.NuNStr(entity.getFid())){
			return 0;
		}		
		return mybatisDaoContext.update(SQLID + "updateSelectiveByFid", entity);
	}
	
	/**
	 * 新增特色标识
	 * @param entity
	 * @return
	 */
	public Integer addFeatureTag(CityFeatureHousetypeEntity entity){
		if(Check.NuNObj(entity) || Check.NuNStr(entity.getFid()) || Check.NuNStr(entity.getForeignFid())){
			return 0;
		}		
		return mybatisDaoContext.save(SQLID + "insert", entity);
	}
	
	
	

}
