package com.ziroom.minsu.services.basedata.test.proxy;

import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.minsu.entity.sys.CurrentuserEntity;
import com.ziroom.minsu.services.basedata.dto.CurrentuserRequest;
import com.ziroom.minsu.services.basedata.proxy.PermissionOperateServiceProxy;
import com.ziroom.minsu.services.basedata.test.base.BaseTest;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * @author liyingjie on 2016年5月8日
 * @version 1.0
 * @since 1.0
 */
public class PermissionOperateServiceProxyTest extends BaseTest {
	@Resource(name="basedata.permissionOperateServiceProxy")
	private PermissionOperateServiceProxy permissionOperateServiceProxy;
	
	@Test
	public void initSaveUserInfoTest(){
		String userFid = "8a9e9aaf537e3f7501537e3f75af0000";
		String resultJson = permissionOperateServiceProxy.initSaveUserInfo(userFid);
		System.err.println(resultJson);
	}
	
	@Test
	public void editUserStatusTest(){
		CurrentuserEntity user = new CurrentuserEntity();
		user.setAccountStatus(0);
		user.setFid("8a9e9aaf5383821f01538391fcbd0001");
		String resultJson = permissionOperateServiceProxy.editUserStatus(JsonEntityTransform.Object2Json(user));
		System.err.println(resultJson);
	}
	
	@Test
	public void searchCurrentuserListTest(){
		CurrentuserRequest user = new CurrentuserRequest();
		user.setRoleName("民宿管家");
		String resultJson = permissionOperateServiceProxy.searchCurrentuserList(JsonEntityTransform.Object2Json(user));
		System.err.println(resultJson);
	}
}
