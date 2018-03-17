/**
 * @FileName: CurrentuserDaoTest.java
 * @Package com.ziroom.minsu.report.test.dao
 * 
 * @author bushujie
 * @created 2016年11月30日 下午5:15:14
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.report.test.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.ziroom.minsu.services.basedata.entity.ResourceVo;
import com.ziroom.minsu.ups.dao.ResourceDao;
import org.junit.Test;

import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.minsu.entity.sys.CurrentuserEntity;
import com.ziroom.minsu.entity.sys.EmployeeEntity;
import com.ziroom.minsu.ups.dao.CurrentuserDao;
import com.ziroom.minsu.ups.dao.RoleDao;
import com.ziroom.minsu.ups.service.EmployeeService;

import base.BaseTest;

/**
 * <p>TODO</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author bushujie
 * @since 1.0
 * @version 1.0
 */
public class CurrentuserDaoTest  extends BaseTest{
	
	@Resource(name="ups.currentuserDao")
	private CurrentuserDao currentuserDao;
	
	@Resource(name="ups.roleDao")
	private RoleDao roleDao;
	
	@Resource(name="ups.employeeService")
	private EmployeeService employeeService;

    @Resource(name="ups.resourceDao")
    private ResourceDao resourceDao;
	
	@Test
	public void getCurrentuserByFidTest(){
		CurrentuserEntity cc=currentuserDao.getCurrentuserByFid("dddd");
		System.err.println(JsonEntityTransform.Object2Json(cc));
	}
	
	@Test
	public void getSysUserByRole(){
		List<Map> list=roleDao.getSysUserByRole("8a90a50958f2fc740158f3010a8b0003");
		System.err.println(list.size());
	}
	
	@Test
	public void findMenuFidListTest(){
        List<String> list = resourceDao.findMenuFidList("8a9091a2600c095501600c09557d0000", "8a90a36a5ce8dea2015cf39dd2ca1a3b");
		System.err.println(list);
	}

    @Test
    public void findMenuChildTreeTest(){
        List<ResourceVo> list = resourceDao.findMenuChildTree("8a9091a2600c095501600c09557d0000", "8a90a36a5ce8dea2015cf39dd2ca1a3b");
        System.err.println(list);
    }
}
