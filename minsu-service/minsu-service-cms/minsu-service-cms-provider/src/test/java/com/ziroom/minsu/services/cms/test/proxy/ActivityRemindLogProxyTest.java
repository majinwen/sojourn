package com.ziroom.minsu.services.cms.test.proxy;

import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.minsu.entity.cms.ActivityRemindLogEntity;
import com.ziroom.minsu.services.cms.proxy.ActivityRemindLogProxy;
import com.ziroom.minsu.services.cms.test.base.BaseTest;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lusp
 * @create on 2017/6/7.
 */
public class ActivityRemindLogProxyTest extends BaseTest{

    @Resource(name="cms.activityRemindLogProxy")
    private ActivityRemindLogProxy activityRemindLogProxy;

    @Test
    public void insertActivityRemindLogIgnore() throws Exception {
        ActivityRemindLogEntity activityRemindLogEntity = new ActivityRemindLogEntity();
        activityRemindLogEntity.setActSn("FJKLAJJFLAK");
        activityRemindLogEntity.setFid("fjlajflafjalljf");
        activityRemindLogEntity.setMobileNo("15639663256");
        activityRemindLogEntity.setRunTime(new Date());
        activityRemindLogEntity.setSource(2);
        activityRemindLogEntity.setSendTimes(0);
        activityRemindLogEntity.setUid("uid3");

        activityRemindLogProxy.insertActivityRemindLogIgnore(JsonEntityTransform.Object2Json(activityRemindLogEntity));
    }

    @Test
    public void deleteActivityRemindLogByUid() throws Exception {

        ActivityRemindLogEntity activityRemindLogEntity = new ActivityRemindLogEntity();
        activityRemindLogEntity.setUid("uid3");
        activityRemindLogProxy.deleteActivityRemindLogByUid(JsonEntityTransform.Object2Json(activityRemindLogEntity));
    }

    @Test
    public void queryRemindUidInfoByPage() throws Exception {

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("page", 1);
        paramMap.put("limit", 10);

        String result = activityRemindLogProxy.queryRemindUidInfoByPage(JsonEntityTransform.Object2Json(paramMap));
        System.out.print(result);
    }

    @Test
    public void updateSendTimesRunTimeByUid() throws Exception {

        ActivityRemindLogEntity activityRemindLogEntity = new ActivityRemindLogEntity();
        activityRemindLogEntity.setUid("uid3");
        activityRemindLogProxy.updateSendTimesRunTimeByUid(JsonEntityTransform.Object2Json(activityRemindLogEntity));

    }

}