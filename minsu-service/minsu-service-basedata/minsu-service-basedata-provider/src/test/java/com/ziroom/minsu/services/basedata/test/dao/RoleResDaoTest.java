package com.ziroom.minsu.services.basedata.test.dao;

import com.ziroom.minsu.services.basedata.dao.RoleResDao;
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
public class RoleResDaoTest extends BaseTest {


    @Resource(name="basedata.roleResDao")
    private RoleResDao roleResDao;


    @Test
    public void TestTest(){

        System.out.println("test");
    }

    @Test
    public void TestDelRoleResourcesByFidnull(){

        roleResDao.delRoleResourcesByFid(null);
    }

    @Test
    public void TestDelRoleResourcesByFid(){
        roleResDao.delRoleResourcesByFid("aa");
    }


    @Test
    public void TestSaveRoleResourcesNull(){
        roleResDao.saveRoleResources("aa", null);
    }

    @Test
    public void TestSaveRoleResources(){
        String[] resFidArray = new String[]{"1111111","222222","b33333b","444444"};
        roleResDao.saveRoleResources("aa", resFidArray);
    }

}
