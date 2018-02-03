package com.ziroom.minsu.services.cms.test.service;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.minsu.services.cms.service.InviteAndCouponServiceImpl;
import com.ziroom.minsu.services.cms.test.base.BaseTest;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * <p>TODO</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on on 2016/11/3.
 * @version 1.0
 * @since 1.0
 */
public class InviteAndCouponServiceImplTest extends BaseTest {

    @Resource(name = "cms.inviteAndCouponServiceImpl")
    private InviteAndCouponServiceImpl inviteAndCouponService;

    @Test
    public void dealCouponByGroupSn() throws Exception {
        DataTransferObject dto = new DataTransferObject();
         inviteAndCouponService.dealCouponByGroupSn("111","001",2,dto);
        System.err.println(JsonEntityTransform.Object2Json(dto));
    }

}
