package com.ziroom.minsu.services.cms.test.proxy;

import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.minsu.entity.cms.InviteEntity;
import com.ziroom.minsu.services.cms.dto.InviteAcceptRequest;
import com.ziroom.minsu.services.cms.proxy.InviteProxy;
import com.ziroom.minsu.services.cms.test.base.BaseTest;
import com.ziroom.minsu.services.common.constant.ActivityConst;
import com.ziroom.minsu.valenum.cms.IsGiveInviterPointsEnum;
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
 * @author afi on on 2016/11/4.
 * @version 1.0
 * @since 1.0
 */
public class InviteProxyTest extends BaseTest {

    @Resource(name = "cms.inviteProxy")
    private InviteProxy inviteProxy;

    @Test
    public void getInviteCouponInfo(){
        String str = inviteProxy.getInviteCouponInfo(ActivityConst.InviteConst.INVITEE_GROUP_SN);
        System.err.println(str);
    }

    @Test
    public void accept(){
        InviteAcceptRequest acceptRequest = new InviteAcceptRequest();
        acceptRequest.setUid("e0a0f779-9117-6283-84e1-43e0be20ecf4");
        acceptRequest.setMobile("18633033235");
        acceptRequest.setInviteCode("KBNW3545");

        String str = inviteProxy.accept(JsonEntityTransform.Object2Json(acceptRequest));
        System.err.println(str);

    }

    @Test
    public void saveTest() {
        InviteEntity invite = new InviteEntity();
        invite.setUid("yanbbbbbb");
        invite.setInviteUid("lousssssss");
        invite.setInviteSource(1);
        invite.setInviteStatus(0);
        invite.setIsGiveInviterPoints(IsGiveInviterPointsEnum.NOT_YET_ADD.getCode());
        invite.setInviteCode("aaabbbbcccddd");
        String result=inviteProxy.saveInvite(JsonEntityTransform.Object2Json(invite));
        System.err.println(result);

    }
}
