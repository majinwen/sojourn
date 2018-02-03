package com.ziroom.minsu.services.basedata.test.dao;

import com.ziroom.minsu.entity.sys.CurrentuserRoleEntity;
import com.ziroom.minsu.services.basedata.dao.CurrentuserRoleDao;
import com.ziroom.minsu.services.basedata.test.base.BaseTest;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * <p>角色测试</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/3/24.
 * @version 1.0
 * @since 1.0
 */
public class CurrentuserRoleDaoTest extends BaseTest {


    @Resource(name = "basedata.currentuserRoleDao")
    private CurrentuserRoleDao currentuserRoleDao;


    @Test
    public void TestInsertCurrentuserRole() {
        CurrentuserRoleEntity currentuserRoleEntity = new CurrentuserRoleEntity();
        currentuserRoleEntity.setRoleFid("aaa");
        currentuserRoleEntity.setCurrenuserFid("bbbb");
        currentuserRoleDao.insertCurrentuserRole(currentuserRoleEntity);
    }

}
