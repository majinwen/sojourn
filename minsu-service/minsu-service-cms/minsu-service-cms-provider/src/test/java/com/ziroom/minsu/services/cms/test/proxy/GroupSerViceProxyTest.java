package com.ziroom.minsu.services.cms.test.proxy;

import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.minsu.services.cms.dto.ZrpAttendActRequest;
import com.ziroom.minsu.services.cms.proxy.CityFileProxy;
import com.ziroom.minsu.services.cms.proxy.GroupServiceProxy;
import com.ziroom.minsu.services.cms.test.base.BaseTest;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * <p>TODO</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd
 * @version 1.0
 * @Date Created in 2017年10月17日 18:59
 * @since 1.0
 */

public class GroupSerViceProxyTest extends BaseTest {

    @Resource(name = "cms.groupServiceProxy")
    private GroupServiceProxy groupServiceProxy;

    @Test
    public void testDelBatch() {
        String s = groupServiceProxy.deleteUserRelBatch("8a9e989a5f29ed3e015f29ed3e7b0000,");
        System.err.println(s);
    }

    @Test
    public void testuserAddGroupAct() {
        ZrpAttendActRequest zrpAttendActRequest = new ZrpAttendActRequest();
        zrpAttendActRequest.setGroupUserFid("8a90939760d998770160de15741706ec");
        zrpAttendActRequest.setUid("b4900ad3-d6c7-94cf-227c-1e49baeb11f0");
        zrpAttendActRequest.setCreateName("测试活动");
        zrpAttendActRequest.setCreateFid("001");
        String s = groupServiceProxy.userAddGroupAct(JsonEntityTransform.Object2Json(zrpAttendActRequest));
        System.out.println(s);

    }
}
