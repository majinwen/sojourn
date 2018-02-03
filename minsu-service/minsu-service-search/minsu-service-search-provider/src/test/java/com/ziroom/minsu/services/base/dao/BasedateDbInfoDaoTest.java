package com.ziroom.minsu.services.base.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;

import base.BaseTest;

import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.minsu.entity.conf.ConfCityEntity;
import com.ziroom.minsu.entity.conf.DicItemEntity;
import com.ziroom.minsu.services.basedata.dao.BasedateDbInfoDao;
import com.ziroom.minsu.services.basedata.entity.CityFeatureHouseVo;
import com.ziroom.minsu.services.search.vo.StaticResourceVo;
import com.ziroom.minsu.valenum.city.CityRulesEnum;
import com.ziroom.minsu.valenum.productrules.ProductRulesEnum;

/**
 * <p>热门区域的测试</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/4/7.
 * @version 1.0
 * @since 1.0
 */
public class BasedateDbInfoDaoTest extends BaseTest {


    @Resource(name="search.basedateDbInfoDao")
    private BasedateDbInfoDao basedateDbInfoDao;


    @Test
    public void getDicItemListByCodeAndTemplate(){
        List<DicItemEntity> pagingResult =basedateDbInfoDao.getDicItemListByCodeAndTemplate(ProductRulesEnum.ProductRulesEnum0018.getValue(),null);
        System.out.println(JsonEntityTransform.Object2Json(pagingResult));
    }
    
    
    @Test
    public void getAllCityList(){
    	List<ConfCityEntity> list= basedateDbInfoDao.getAllCityList();
        System.err.println(JsonEntityTransform.Object2Json(list));
    }
    
    @Test
    public void getAllCityMap(){
    	Map<String, String> map = basedateDbInfoDao.getAllCityMap();
        System.err.println(JsonEntityTransform.Object2Json(map));
    }
    
    @Test
    public void selectEffectiveDefaultEnumList(){
    	List<DicItemEntity> list =basedateDbInfoDao.selectEffectiveDefaultEnumList(CityRulesEnum.CityRulesEnum002.getValue(),null);
        System.err.println(JsonEntityTransform.Object2Json(list));
    }
    
	 @Test
	 public void getAllCityFeatureHouseTypes() {
		 Map<String, List<Integer>> list = basedateDbInfoDao.getAllCityFeatureHouseTypes();
		 System.out.println(JsonEntityTransform.Object2Json(list));
	 }
	 
	 @Test
	 public void getStaticResourceByResCode(){
		 StaticResourceVo vo =  basedateDbInfoDao.getStaticResourceByResCode("TOP50_LIST_TITLE_BACKGROUND");
		 System.out.println(JsonEntityTransform.Object2Json(vo));
		 
	 }


}
