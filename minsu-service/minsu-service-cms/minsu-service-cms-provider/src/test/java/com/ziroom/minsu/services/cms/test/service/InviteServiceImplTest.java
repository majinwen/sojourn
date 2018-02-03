package com.ziroom.minsu.services.cms.test.service;

import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.minsu.entity.cms.InviteEntity;
import com.ziroom.minsu.services.cms.service.ActivityServiceImpl;
import com.ziroom.minsu.services.cms.service.InviteServiceImpl;
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
 * @author lishaochuan on 2016/11/1 18:07
 * @version 1.0
 * @since 1.0
 */
public class InviteServiceImplTest extends BaseTest {

    @Resource(name = "cms.inviteServiceImpl")
    private InviteServiceImpl inviteService;

    @Test
    public void getByUid() throws Exception {
        InviteEntity invite = inviteService.getByUid("001");
        System.err.println(JsonEntityTransform.Object2Json(invite));
    }

    @Test
    public void getByCode() throws Exception {
        InviteEntity invite = inviteService.getByCode("yqm001");
        System.err.println(JsonEntityTransform.Object2Json(invite));
    }

    @Test
    public void saveInvite() throws Exception {
        InviteEntity invite = new InviteEntity();
        invite.setUid("001");
        invite.setInviteCode("yqm001");
        int i = inviteService.saveInvite(invite);
        System.err.println("这是结果："+i);
    }

    @Test
    public void inviterInitIdempotency() {
        InviteEntity inviteEntity = inviteService.inviterInitIdempotency("111","18911123545");
        System.err.println("这是结果："+JsonEntityTransform.Object2Json(inviteEntity));
    }
}