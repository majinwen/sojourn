package com.ziroom.minsu.services.basedata.test.dao;

import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.minsu.entity.sys.CityEntity;
import com.ziroom.minsu.services.basedata.dao.CityDao;
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
public class CityDaoTest extends BaseTest {

    @Resource(name = "basedata.cityDao")
    private CityDao sysCityDao;





    @Test
    public void TestGetCityList() {
        List<CityEntity> list = sysCityDao.getCityList();

        System.out.println(JsonEntityTransform.Object2Json(list));
    }

}
