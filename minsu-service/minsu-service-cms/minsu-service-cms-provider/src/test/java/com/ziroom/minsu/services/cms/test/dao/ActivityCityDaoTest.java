package com.ziroom.minsu.services.cms.test.dao;

import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.minsu.entity.cms.ActivityCityEntity;
import com.ziroom.minsu.services.cms.dao.ActivityCityDao;
import com.ziroom.minsu.services.cms.test.base.BaseTest;
import org.junit.Test;
import javax.annotation.Resource;
import java.util.List;

public class ActivityCityDaoTest extends BaseTest {

	@Resource(name = "cms.activityCityDao")
	private ActivityCityDao activityCityDao;
	

	@Test
	public void saveActivityCity() {
		ActivityCityEntity activityInfoEntity = new ActivityCityEntity();
		activityInfoEntity.setActSn("lisc001");
        activityInfoEntity.setCityCode("12313");
		int num = activityCityDao.saveActivityCity(activityInfoEntity);
		System.err.println(num);
	}

    @Test
    public void deleteByActSn() {
        int num = activityCityDao.deleteByActSn("lisc001");
        System.err.println(num);
    }


    @Test
    public void getActivityCitiesByActSn() {
        List<ActivityCityEntity> num = activityCityDao.getActivityCitiesByActSn("ziroom");
        System.err.println(JsonEntityTransform.Object2Json(num));
    }

}
