package com.ziroom.minsu.services.cms.test.service;

import com.ziroom.minsu.services.cms.service.InviteCreateOrderCmsImpl;
import com.ziroom.minsu.services.cms.test.base.BaseTest;
import com.ziroom.minsu.valenum.cms.InviteSourceEnum;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * <p></p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author yanb
 * @version 1.0
 * @Date Created in 2017年12月13日 19:51
 * @since 1.0
 */
public class InviteCreateOrderCmsImplTest extends BaseTest {
    @Resource(name="cms.inviteCreateOrderCmsImpl")
    private InviteCreateOrderCmsImpl inviteCreateOrderCmsImpl;

    @Test
    public void getPointsDetail() throws Exception {
    }

    @Test
    public void getPointTiersByParam() throws Exception {
    }

    @Test
    public void getPointSumByUidSource() throws Exception {
    }

    @Test
    public void getInviteUidByUid() throws Exception {
    }

    @Test
    public void addPointsByParam() throws Exception {
        Map<String, Object> paramMap = new HashMap<>(6);
        paramMap.put("yaoQingRenUid", "yanb");
        paramMap.put("beiYaoQingUid", "loushuai");
        paramMap.put("orderSn", "AAAABBBB");
        paramMap.put("inviteSource", InviteSourceEnum.NEW_INVITE.getCode());
        paramMap.put("sumPerson", 0);
        paramMap.put("sumPoints", 10);
        paramMap.put("tiersPoints", 0);
        Integer result = inviteCreateOrderCmsImpl.addPointsByParam(paramMap);
        System.err.println(result);
    }

}