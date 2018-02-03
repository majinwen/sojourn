package com.ziroom.minsu.services.cms.test.dao;

import com.asura.framework.base.paging.PagingResult;
import com.ziroom.minsu.entity.cms.ActivityRemindLogEntity;
import com.ziroom.minsu.services.cms.dao.ActivityRemindLogDao;
import com.ziroom.minsu.services.cms.test.base.BaseTest;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lusp
 * @create on 2017/6/6.
 */
public class ActivityRemindLogDaoTest extends BaseTest {



    @Resource(name="cms.activityRemindLogDao")
    private ActivityRemindLogDao activityRemindLogDao;


    @Test
    public void insertActivityRemindLogIgnore() throws Exception {
        ActivityRemindLogEntity activityRemindLogEntity = new ActivityRemindLogEntity();
        activityRemindLogEntity.setActSn("JFKDJFK");
        activityRemindLogEntity.setFid("fjdklsflkajffljdsljflajkjk");
//        activityRemindLogEntity.setMobileNo("18652365263");
        activityRemindLogEntity.setRunTime(new Date());
        activityRemindLogEntity.setSource(2);
        activityRemindLogEntity.setSendTimes(6);
        activityRemindLogEntity.setUid("uid2");
        activityRemindLogDao.insertActivityRemindLogIgnore(activityRemindLogEntity);

    }

    @Test
    public void deleteHouseConfMsgByHouseFid() throws Exception {
        ActivityRemindLogEntity activityRemindLogEntity = new ActivityRemindLogEntity();
        activityRemindLogEntity.setUid("uid2");
        activityRemindLogDao.deleteActivityRemindLogByUid(activityRemindLogEntity);

    }

    @Test
    public void queryRemindUidInfoByPage() throws Exception{

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("page", 1);
        paramMap.put("limit", 10);

        PagingResult<ActivityRemindLogEntity> result = activityRemindLogDao.queryRemindUidInfoByPage(paramMap);
    }

    @Test
    public void updateSendTimesByUid() throws Exception{

        ActivityRemindLogEntity activityRemindLogEntity = new ActivityRemindLogEntity();
        activityRemindLogEntity.setUid("uid1");
        int result = activityRemindLogDao.updateSendTimesRunTimeByUid(activityRemindLogEntity);
        System.out.print(result);
    }

}