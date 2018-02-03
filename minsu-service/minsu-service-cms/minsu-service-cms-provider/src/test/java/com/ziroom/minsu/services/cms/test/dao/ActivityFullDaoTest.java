package com.ziroom.minsu.services.cms.test.dao;

import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.minsu.entity.cms.ActivityFullEntity;
import com.ziroom.minsu.services.cms.dao.ActivityFullDao;
import com.ziroom.minsu.services.cms.test.base.BaseTest;
import org.junit.Test;

import javax.annotation.Resource;

public class ActivityFullDaoTest extends BaseTest {

	@Resource(name = "cms.activityFullDao")
	private ActivityFullDao activityFullDao;


    @Test
    public void getActivityFullBySn(){
        ActivityFullEntity page = activityFullDao.getActivityFullBySn("ziroom");
        System.err.println(JsonEntityTransform.Object2Json(page));
    }

}
