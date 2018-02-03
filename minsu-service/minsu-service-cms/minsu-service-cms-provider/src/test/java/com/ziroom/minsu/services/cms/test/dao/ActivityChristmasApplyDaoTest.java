package com.ziroom.minsu.services.cms.test.dao;

import javax.annotation.Resource;

import org.junit.Test;

import com.asura.framework.base.util.DateUtil;
import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.minsu.entity.cms.ActivityChristmasApplyEntity;
import com.ziroom.minsu.services.cms.dao.ActivityChristmasApplyDao;
import com.ziroom.minsu.services.cms.test.base.BaseTest;

/**
 * <p></p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author lishaochuan on 2016/12/9 10:58
 * @version 1.0
 * @since 1.0
 */
public class ActivityChristmasApplyDaoTest extends BaseTest {

    @Resource(name = "cms.activityChristmasApplyDao")
    ActivityChristmasApplyDao activityChristmasApplyDao;

    @Test
    public void selectByCondition()throws Exception {
        ActivityChristmasApplyEntity christmasApply = new ActivityChristmasApplyEntity();
        christmasApply.setMoblie("18688888888");
        christmasApply.setHouseSn("houseSn001");
        christmasApply.setActivityDate(DateUtil.parseDate("2016-04-03", "yyyy-MM-dd"));

        ActivityChristmasApplyEntity activityChristmasApplyEntity = activityChristmasApplyDao.selectByCondition(christmasApply);
        System.err.println(JsonEntityTransform.Object2Json(activityChristmasApplyEntity));
    }

    @Test
    public void save() throws Exception {
        ActivityChristmasApplyEntity christmasApply = new ActivityChristmasApplyEntity();
        christmasApply.setMoblie("18688888888");
        christmasApply.setHouseSn("houseSn001");
        christmasApply.setHouseName("房源名称001");
        christmasApply.setActivityDate(DateUtil.parseDate("2016-04-03", "yyyy-MM-dd"));
        christmasApply.setApplyReason("这是我的申请原因");
        int i = activityChristmasApplyDao.save(christmasApply);
        System.err.println(i);
    }
}