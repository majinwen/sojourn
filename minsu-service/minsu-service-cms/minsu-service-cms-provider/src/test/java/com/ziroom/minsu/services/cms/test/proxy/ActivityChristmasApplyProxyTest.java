package com.ziroom.minsu.services.cms.test.proxy;

import com.asura.framework.base.util.DateUtil;
import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.minsu.entity.cms.ActivityChristmasApplyEntity;
import com.ziroom.minsu.services.cms.dto.ChristmasApplyRequest;
import com.ziroom.minsu.services.cms.proxy.ActivityChristmasApplyProxy;
import com.ziroom.minsu.services.cms.test.base.BaseTest;
import org.junit.Test;

import javax.annotation.Resource;

import static org.junit.Assert.*;

/**
 * <p></p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author lishaochuan on 2016/12/9 14:26
 * @version 1.0
 * @since 1.0
 */
public class ActivityChristmasApplyProxyTest extends BaseTest{

    @Resource(name = "cms.activityChristmasApplyProxy")
    ActivityChristmasApplyProxy activityChristmasApplyProxy;

    @Test
    public void saveApply() throws Exception {
        ChristmasApplyRequest request = new ChristmasApplyRequest();
        request.setHouseSn("houseSn001");
        request.setHouseName("房源名称001");
        request.setMobile("18688888888");
        request.setActivityDate("2016-04-03");

        String str = JsonEntityTransform.Object2Json(request);
        System.err.println(str);
        String apply = activityChristmasApplyProxy.apply(str);
        System.err.println(apply);
    }
}