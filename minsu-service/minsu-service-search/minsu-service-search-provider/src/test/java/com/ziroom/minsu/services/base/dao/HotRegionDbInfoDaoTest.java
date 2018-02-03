package com.ziroom.minsu.services.base.dao;

import base.BaseTest;

import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.minsu.services.basedata.dao.HotRegionDbInfoDao;
import com.ziroom.minsu.services.search.vo.HotRegionSimpleVo;
import com.ziroom.minsu.services.search.vo.HotRegionVo;
import com.ziroom.minsu.valenum.city.CityRulesEnum;

import org.junit.Test;

import javax.annotation.Resource;

import java.util.List;

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
public class HotRegionDbInfoDaoTest extends BaseTest {


    @Resource(name="search.hotRegionDbInfoDao")
    private HotRegionDbInfoDao hotRegionDbInfoDao;

    @Test
    public void TestgetHotRegionList(){
        List<HotRegionVo> pagingResult =hotRegionDbInfoDao.getHotRegionList("110100");
        System.out.println(JsonEntityTransform.Object2Json(pagingResult));
    }


    @Test
    public void TestgetSubwaySimpleList(){
        List<HotRegionSimpleVo> pagingResult =hotRegionDbInfoDao.getSubwaySimpleList();
        System.out.println(JsonEntityTransform.Object2Json(pagingResult));
    }



    @Test
    public void TestgetKm(){
        Integer aa =hotRegionDbInfoDao.getKm();
        System.out.println(JsonEntityTransform.Object2Json(aa));
    }

    
    @Test
    public void getAreaByCityTest(){
    	
    	List<HotRegionSimpleVo> areaByCity = hotRegionDbInfoDao.getAreaByCity("110100");
    	System.err.println(JsonEntityTransform.Object2Json(areaByCity));
    }
    
    
    @Test
    public void getHotRegionListByEffectiveEnum(){
    	List<HotRegionVo> list= hotRegionDbInfoDao.getHotRegionListByEffectiveEnum(CityRulesEnum.CityRulesEnum002.getValue(),null,null,null);
    	System.err.println(JsonEntityTransform.Object2Json(list));
    }
    
    @Test
    public void getHotRegionListByEnumStatus(){
    	List<HotRegionVo> list= hotRegionDbInfoDao.getHotRegionListByCityEnumStatus("110100");
    			System.err.println(JsonEntityTransform.Object2Json(list));
    	
    }
    
    @Test
    public void getHotRegionListByEffectiveEnumStatus(){
    	List<HotRegionVo> list= hotRegionDbInfoDao.getHotRegionListByEffectiveEnumStatus(CityRulesEnum.CityRulesEnum002.getValue(),null,"110100",null);
    	System.err.println(JsonEntityTransform.Object2Json(list));
    }
    
    
    
}
