package com.ziroom.minsu.services.cms.test.dao;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.minsu.entity.cms.InviteEntity;
import com.ziroom.minsu.services.cms.dao.InviteDao;
import com.ziroom.minsu.services.cms.dto.InviteListRequest;
import com.ziroom.minsu.services.cms.dto.InviteStateUidRequest;
import com.ziroom.minsu.services.cms.test.base.BaseTest;
import com.ziroom.minsu.valenum.cms.InviteStatusEnum;
import com.ziroom.minsu.valenum.cms.IsGiveInviterPointsEnum;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

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
public class InviteDaoTest extends BaseTest {

    @Resource(name = "cms.inviteDao")
    private InviteDao inviteDao;


    @Test
    public void getInvitePageByCondition(){
        InviteListRequest request = new InviteListRequest();
        List<String>  uids = new ArrayList<>();
        uids.add("111");
        request.setUids(uids);
        PagingResult<InviteEntity> pagingResult = inviteDao.getInvitePageByCondition(request);
        System.err.println(JsonEntityTransform.Object2Json(pagingResult));
    }

    @Test
    public void isInvitedByUidTest() {
        InviteStateUidRequest inviteStateUidRequest =new InviteStateUidRequest();
        inviteStateUidRequest.setUid("e0a0f779-9117-6283-84e1-43e0be20ecf4");
        inviteStateUidRequest.setInviteSource(1);
        inviteStateUidRequest.setIsGiveInviterPoints(IsGiveInviterPointsEnum.NOT_YET_ADD.getCode());
        inviteStateUidRequest.setInviteStatus(InviteStatusEnum.SEND_OTHER.getCode());


        Integer result = inviteDao.isInvitedByUid(inviteStateUidRequest);
        System.err.println(result);
    }

    @Test
    public void getInviteUidByUid() {
        InviteStateUidRequest inviteStateUidRequest=new InviteStateUidRequest();
        inviteStateUidRequest.setUid("loushuai1");
        inviteStateUidRequest.setInviteSource(1);
        String s=inviteDao.getInviteUidByUid(inviteStateUidRequest);
    }

}
