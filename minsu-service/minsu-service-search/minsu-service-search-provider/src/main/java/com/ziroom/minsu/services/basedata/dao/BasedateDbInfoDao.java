package com.ziroom.minsu.services.basedata.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.asura.framework.base.util.Check;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.ziroom.minsu.entity.conf.ConfCityEntity;
import com.ziroom.minsu.entity.conf.DicItemEntity;
import com.ziroom.minsu.services.basedata.entity.CityFeatureHouseVo;
import com.ziroom.minsu.services.common.constant.BaseConstant;
import com.ziroom.minsu.services.search.vo.StaticResourceVo;
import com.ziroom.minsu.valenum.common.YesOrNoEnum;


/**
 * <p>模板的配置</p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi
 * @version 1.0
 * @since 1.0
 */
@Repository("search.basedateDbInfoDao")
public class BasedateDbInfoDao {

	
    private String SQLID="search.basedateDbInfoDao.";

    @Autowired
    @Qualifier("searchBase.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;


    /**
     * 获取当前模板的cod的值信息
     * @param code
     * @param templateFid
     * @return
     */
    public List<DicItemEntity> getDicItemListByCodeAndTemplate(String code, String templateFid){
        if(Check.NuNStr(code) ){
            return null;
        }
        if (Check.NuNStr(templateFid)){
            templateFid = BaseConstant.BASE_TEMPLATE;
        }
        Map<String,Object> par = new HashMap<>();
        par.put("code",code);
        par.put("templateFid",templateFid);
        return mybatisDaoContext.findAll(SQLID + "getDicItemListByCodeAndTemplate", DicItemEntity.class, par);
    }
    
    /**
     * 获取所有城市列表
     * @return
     */
    public List<ConfCityEntity> getAllCityList() {
        return mybatisDaoContext.findAll(SQLID + "getAllCityList", ConfCityEntity.class);
    }
    
    /**
     * 获取开通的城市列表(房客)
     * @return
     */
    public List<ConfCityEntity> getOpenCityTenant() {
        return mybatisDaoContext.findAll(SQLID + "getOpenCityTenant", ConfCityEntity.class);
    }
    
    /**
	 * 获取所有的城市map
	 * @return
     */
    public Map<String, String> getAllCityMap(){
    	Map<String, String> map =null;
    	List<ConfCityEntity> list =mybatisDaoContext.findAll(SQLID + "getAllCityList", ConfCityEntity.class);
		if (!Check.NuNCollection(list)) {
			map = new HashMap<String, String>(list.size());
			for (ConfCityEntity city : list) {
				map.put(city.getCode(), city.getShowName());
			}
		}
		if (map==null) {
			map=new HashMap<String, String>();
		}
		return map;
    }
    
    
    /**
     * 默认配置枚举列表（有效的）
     * @param templateFid
     * @param dicCode
     * @return
     */
	public List<DicItemEntity> selectEffectiveDefaultEnumList( String dicCode,String templateFid) {
		if(Check.NuNStr(dicCode) ){
            return null;
        }
        if (Check.NuNStr(templateFid)){
            templateFid = BaseConstant.BASE_TEMPLATE;
        }
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dicCode", dicCode);
		paramMap.put("templateFid", templateFid);
		paramMap.put("itemStatus", YesOrNoEnum.YES.getCode());
		return mybatisDaoContext.findAll(SQLID + "getDefaultSelectEnumList", DicItemEntity.class, paramMap);
	}
	
	/**
	 * 查询所有的有特色房源的城市的房源MAP
	 * TODO
	 *
	 * @author zl
	 * @created 2016年12月1日 下午5:58:03
	 *
	 * @return
	 */
	public Map<String, List<Integer>> getAllCityFeatureHouseTypes() {
		
		List<CityFeatureHouseVo> list = mybatisDaoContext.findAll(SQLID + "getAllCityFeatureHouseTypes",CityFeatureHouseVo.class);
		if (Check.NuNCollection(list)) {
			return null;
		}
		
		Map<String, List<Integer>> map = new HashMap<String, List<Integer>>();
		for (CityFeatureHouseVo cityFeatureHouseVo : list) {
			if(!Check.NuNStr(cityFeatureHouseVo.getCityCode())){
				map.put(cityFeatureHouseVo.getCityCode(), cityFeatureHouseVo.getHouseTypes());
			}
		}	
		
		return map;
	}
	
	
	/**
	 * 
	 * 查询静态资源
	 *
	 * @author zl
	 * @created 2017年3月17日 下午5:55:50
	 *
	 * @param resCode
	 * @return
	 */
	public StaticResourceVo getStaticResourceByResCode(String resCode){
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("resCode", resCode);
		return mybatisDaoContext.findOneSlave(SQLID + "getStaticResourceByResCode", StaticResourceVo.class, paramMap);
	} 
	
	/** 
	 * 查询标签
	 *
	 * @author zl
	 * @created 2017年3月21日 下午4:27:25
	 *
	 * @param params
	 * @return
	 */
	public List<String> findByConfTagFidsList(List<String> fidsList){
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("fids", fidsList);		
		return mybatisDaoContext.findAll(SQLID+"findByConfTagFidsList", String.class, paramMap);
	}
	
	
	
	
    

}
