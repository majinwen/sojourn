package com.ziroom.minsu.services.basedata.test.dao;

import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.minsu.entity.conf.CityRegionEntity;
import com.ziroom.minsu.entity.conf.CityRegionRelEntity;
import com.ziroom.minsu.entity.sys.CityEntity;
import com.ziroom.minsu.services.basedata.dao.CityDao;
import com.ziroom.minsu.services.basedata.dao.CityRegionDao;
import com.ziroom.minsu.services.basedata.dao.CityRegionRelDao;
import com.ziroom.minsu.services.basedata.test.base.BaseTest;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>城市测试</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/3/24.
 * @version 1.0
 * @since 1.0
 */
public class CityRegionTest extends BaseTest {

    @Resource(name = "basedata.cityRegionDao")
    private CityRegionDao cityRegionDao;

    @Resource(name = "basedata.cityRegionRelDao")
    private CityRegionRelDao cityRegionRelDao;





    @Test
    public void testInsert(){
        CityRegionEntity cityRegionEntity = new CityRegionEntity();
        cityRegionEntity.setRegionName("华北区");
        cityRegionDao.insertCityRegion(cityRegionEntity);
    }

    @Test
    public void testUpdate(){
        CityRegionEntity cityRegionEntity = new CityRegionEntity();
        cityRegionEntity.setFid("8a9e988b59810f230159810f240b0000");
        cityRegionEntity.setIsDel(1);
        cityRegionDao.updateByFid(cityRegionEntity);
    }

    @Test
    public void testInsertRel(){
        CityRegionRelEntity cityRegionRelEntity = new CityRegionRelEntity();
        cityRegionRelEntity.setRegionFid("8a9e988b59810f230159810f240b0000");
        cityRegionRelEntity.setProvinceCode("222000");
        cityRegionRelDao.insertCityRegionRel(cityRegionRelEntity);
    }

    @Test
    public void testFindAll(){
        List<CityRegionEntity> allRegion = cityRegionDao.findAllRegion();
    }

}
