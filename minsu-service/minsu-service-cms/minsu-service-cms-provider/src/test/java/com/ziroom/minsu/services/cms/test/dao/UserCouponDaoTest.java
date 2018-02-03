package com.ziroom.minsu.services.cms.test.dao;

import com.ziroom.minsu.entity.cms.UserActEmptyEntity;
import com.ziroom.minsu.services.cms.dao.UserActEmptyDao;
import com.ziroom.minsu.services.cms.dao.UserCouponDao;
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
 * @author afi on on 2017/1/10.
 * @version 1.0
 * @since 1.0
 */
public class UserCouponDaoTest extends BaseTest {

    @Resource(name = "cms.userCouponDao")
    private UserCouponDao userCouponDao;

    @Test
    public void testcountCouponNumByUidAndAct(){
        userCouponDao.countCouponNumByUidAndAct("sdfsdfsdf","sdf",null);
    }

}
