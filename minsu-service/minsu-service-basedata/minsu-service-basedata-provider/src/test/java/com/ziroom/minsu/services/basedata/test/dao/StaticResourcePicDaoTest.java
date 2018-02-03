package com.ziroom.minsu.services.basedata.test.dao;

import javax.annotation.Resource;

import org.junit.Test;

import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.minsu.entity.base.StaticResourcePicEntity;
import com.ziroom.minsu.services.basedata.dao.StaticResourcePicDao;
import com.ziroom.minsu.services.basedata.test.base.BaseTest;

/**
 * 
 * <p>静态资源dao测试</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author liujun
 * @since 1.0
 * @version 1.0
 */
public class StaticResourcePicDaoTest extends BaseTest {

    @Resource(name = "basedata.staticResourcePicDao")
    private StaticResourcePicDao staticResourcePicDao;

    @Test
    public void insertStaticResourcePicTest() {
		StaticResourcePicEntity staticResourcePicEntity = new StaticResourcePicEntity();
		staticResourcePicEntity.setResFid("8a9e99c75adb5dae015adb5daea40000");
		staticResourcePicEntity.setPicBaseUrl("group1/M00/00/25/ChAiMFcu7m2AGdXYAAK0qAYSSnk649");
		staticResourcePicEntity.setPicServerUuid("b93a5775-696c-40e9-9174-738c461e0a53");
		staticResourcePicEntity.setPicSuffix(".jpg");
		int upNum = staticResourcePicDao.insertStaticResourcePic(staticResourcePicEntity);
		System.err.println(upNum);
    }
    
    @Test
    public void findStaticResourcePicByResFidTest() {
    	String resFid = "8a9e99c75adb5dae015adb5daea40000";
		StaticResourcePicEntity entity = staticResourcePicDao.findStaticResourcePicByResFid(resFid );
    	System.err.println(JsonEntityTransform.Object2Json(entity));
    }
    
    @Test
    public void updateStaticResourcePicByResFidTest() {
    	String resFid = "8a9e99c75adb5dae015adb5daea40000";
    	StaticResourcePicEntity entity = staticResourcePicDao.findStaticResourcePicByResFid(resFid );
    	int upNum = staticResourcePicDao.updateStaticResourcePicByResFid(entity);
    	System.err.println(upNum);
    }

}
