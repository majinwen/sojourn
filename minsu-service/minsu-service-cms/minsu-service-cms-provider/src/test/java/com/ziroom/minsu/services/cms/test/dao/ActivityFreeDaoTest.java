package com.ziroom.minsu.services.cms.test.dao;

import com.ziroom.minsu.entity.cms.ActivityFreeEntity;
import com.ziroom.minsu.services.cms.dao.ActivityFreeDao;
import com.ziroom.minsu.services.cms.test.base.BaseTest;
import org.junit.Test;

import javax.annotation.Resource;

import static org.junit.Assert.*;

/**
 * <p>TODO</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author lishaochuan on 2016/10/11 14:48
 * @version 1.0
 * @since 1.0
 */
public class ActivityFreeDaoTest extends BaseTest {

    @Resource(name = "cms.activityFreeDao")
    ActivityFreeDao activityFreeDao;

    @Test
    public void getByUid() throws Exception {

    }

    @Test
    public void save() throws Exception {
        ActivityFreeEntity a = new ActivityFreeEntity();
        a.setUid("12312");
        a.setActCode("321321");
        activityFreeDao.save(a);
    }

    @Test
    public void updateByUid() throws Exception {

    }
    
    
    @Test
    public void testgetByUid() throws Exception {
    	ActivityFreeEntity byUid = activityFreeDao.getByUid("f51362b1-5c42-4157-8ab9-6ffc0dcd7a43");
    	System.out.println(byUid);
    }
}