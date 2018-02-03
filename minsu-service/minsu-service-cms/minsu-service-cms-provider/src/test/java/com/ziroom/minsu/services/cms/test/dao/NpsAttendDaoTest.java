package com.ziroom.minsu.services.cms.test.dao;

import com.ziroom.minsu.entity.cms.NpsAttendEntiy;
import com.ziroom.minsu.services.cms.dao.NpsAttendDao;
import com.ziroom.minsu.services.cms.test.base.BaseTest;
import com.ziroom.minsu.valenum.common.UserTypeEnum;
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
 * @author afi on on 2016/11/12.
 * @version 1.0
 * @since 1.0
 */
public class NpsAttendDaoTest extends BaseTest {

    @Resource(name = "cms.npsAttendDao")
    private NpsAttendDao npsAttendDao;


    @Test
    public void saveNpsAttend(){
        NpsAttendEntiy npsAttendEntiy = new NpsAttendEntiy();
        npsAttendEntiy.setNpsCode("code");
//        npsAttendEntiy.setRemark("mark");
        npsAttendEntiy.setScore(10);
        npsAttendEntiy.setUid("uid");
        npsAttendEntiy.setUserType(UserTypeEnum.TENANT.getUserType());
        npsAttendDao.saveNpsAttend(npsAttendEntiy);
    }
}
