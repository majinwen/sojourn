/**
 * @FileName: CallHuanXinImServiceProxyTest.java
 * @Package com.ziroom.minsu.services.message.test.proxy
 * 
 * @author yd
 * @created 2017年7月28日 下午3:44:43
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.message.test.proxy;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import com.asura.framework.base.entity.DataTransferObject;
import com.ziroom.minsu.services.message.dto.AppGroupsPageInfo;
import com.ziroom.minsu.services.message.dto.GagMemberDto;
import com.ziroom.minsu.services.message.dto.GroupInfoDto;
import com.ziroom.minsu.services.message.dto.GroupMemberDto;
import com.ziroom.minsu.services.message.dto.GroupMemberPageInfoDto;
import com.ziroom.minsu.services.message.dto.HuanxinGroupDto;
import com.ziroom.minsu.services.message.dto.ManagerMerberDto;
import com.ziroom.minsu.services.message.proxy.CallHuanXinImServiceProxy;
import com.ziroom.minsu.services.message.test.base.BaseTest;
import com.ziroom.minsu.valenum.msg.SourceTypeEnum;

/**
 * <p>环信 接口测试</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author yd
 * @since 1.0
 * @version 1.0
 */
public class CallHuanXinImServiceProxyTest extends BaseTest{
	
	@Resource(name = "message.callHuanXinImServiceProxy")
	private  CallHuanXinImServiceProxy callHuanXinImServiceProxy;

	/**
	 * Test method for {@link com.ziroom.minsu.services.message.proxy.CallHuanXinImServiceProxy#addGroup(com.ziroom.minsu.services.message.dto.HuanxinGroupDto)}.
	 */
	@Test
	public void testAddGroup() {
		
		HuanxinGroupDto huanxinGroupDto = new HuanxinGroupDto();
		huanxinGroupDto.setGroupname("CBD自如驿111");
		huanxinGroupDto.setDesc("CBD自如驿是年轻人的家园，欢迎来到自如驿");
		huanxinGroupDto.setOwner("app_0f163457-ad6a-09ce-d5de-de452a251cf6");
		callHuanXinImServiceProxy.addGroup(huanxinGroupDto);
	}

	@Test
	public void testAddOneGroupMember(){
		callHuanXinImServiceProxy.addOneGroupMember("25773990805506", "0f163457-ad6a-09ce-d5de-de452a251cf612355",3);
	}
	
	@Test
	public void testAddManyGroupMember(){
		
		GroupMemberDto  groupMemberDto = new GroupMemberDto();
		groupMemberDto.setGroupId("25773990805506");
		groupMemberDto.setSourceType(SourceTypeEnum.ZRY_ADD_GROUP_FAILED.getCode());
		//List<String> members = new String[]{"0f163457-ad6a-09ce-d5de-de452a251cf6","c946422b-5687-4f63-288a-320fa65b86d8"};
		
		List<String> members = new ArrayList<String>();
		members.add("7a8c4184-8e2e-37b4-08e8-f4c20225e35011122");
		members.add("0f163457-ad6a-09ce-d5de-de452a251cf61235525553222");
		members.add("0f163457-ad6a-09ce-d5de-de452a251cf65656");
	
		groupMemberDto.setMembers(members);
		DataTransferObject dto = callHuanXinImServiceProxy.addManyGroupMember(groupMemberDto,3);
		
		System.out.println(dto.getMsg());
	}
	
	@Test
	public void testQueryGroupMemberByPage(){
		
		GroupMemberPageInfoDto groupMemberPageInfoDto = new GroupMemberPageInfoDto();
		groupMemberPageInfoDto.setGroupId("33577232302081");
		DataTransferObject dto = callHuanXinImServiceProxy.queryGroupMemberByPage(groupMemberPageInfoDto);
		System.out.println(dto.toJsonString());
	}
	
	@Test
	public void testQueryAppGroupsByPage(){
		
		AppGroupsPageInfo appGroupsPageInfo = new AppGroupsPageInfo();
		appGroupsPageInfo.setLimit(10);
		DataTransferObject dto = callHuanXinImServiceProxy.queryAppGroupsByPage(appGroupsPageInfo);
		System.out.println(dto.toJsonString());
	}
	
	@Test
	public void addGagMember(){
		
		GagMemberDto gagMemberDto = new GagMemberDto();
		gagMemberDto.setGroupId("22892344573954");
		List<String> members = new ArrayList<String>();
		members.add("0f163457-ad6a-09ce-d5de-de452a251cf6");
		gagMemberDto.setMuteDuration(10000L);
		gagMemberDto.setMembers(members);
		callHuanXinImServiceProxy.addGagMember(gagMemberDto);
	}
	
	@Test
	public void removeGagMember(){
		GagMemberDto gagMemberDto = new GagMemberDto();
		gagMemberDto.setGroupId("25773990805506");
		List<String> members = new ArrayList<String>();
		members.add("7a8c4184-8e2e-37b4-08e8-f4c20225e350");
		members.add("0f163457-ad6a-09ce-d5de-de452a251cf612355232");
		members.add("0f163457-ad6a-09ce-d5de-de452a251cf65656");
		gagMemberDto.setMembers(members);
		callHuanXinImServiceProxy.removeGagMember(gagMemberDto);
	}
	
	@Test
	public void removeOneGroupMember(){
		callHuanXinImServiceProxy.removeOneGroupMember("22892344573954", "0f163457-ad6a-09ce-d5de-de452a251cf6");
	}
	
	@Test
	public void removeManyGroupMember(){
		GroupMemberDto  groupMemberDto = new GroupMemberDto();
		groupMemberDto.setGroupId("25773990805506");
		//List<String> members = new String[]{"0f163457-ad6a-09ce-d5de-de452a251cf6","c946422b-5687-4f63-288a-320fa65b86d8"};
		List<String> members = new ArrayList<String>();
		members.add("7a8c4184-8e2e-37b4-08e8-f4c20225e350");
		members.add("0f163457-ad6a-09ce-d5de-de452a251cf612355232");
		members.add("0f163457-ad6a-09ce-d5de-de452a251cf65656");
		members.add("7a8c4184-8e2e-37b4-08e8-f4c20225e350111");
		members.add("0f163457-ad6a-09ce-d5de-de452a251cf612355255532");
		members.add("7a8c4184-8e2e-37b4-08e8-f4c20225e35011122");
		members.add("0f163457-ad6a-09ce-d5de-de452a251cf61235525553222");
		groupMemberDto.setMembers(members);
		callHuanXinImServiceProxy.removeManyGroupMember(groupMemberDto);
	}
	
	
	@Test
	public void addAdminMember(){
		
		ManagerMerberDto managerMerberDto = new ManagerMerberDto();
		managerMerberDto.setAdminUid("0f163457-ad6a-09ce-d5de-de452a251cf6");
		managerMerberDto.setGroupId("22892344573954");
		callHuanXinImServiceProxy.addAdminMember(managerMerberDto);
	}
	
	@Test
	public void deleteAdminMember(){
		
		ManagerMerberDto managerMerberDto = new ManagerMerberDto();
		managerMerberDto.setAdminUid("0f163457-ad6a-09ce-d5de-de452a251cf6");
		managerMerberDto.setGroupId("22892344573954");
		callHuanXinImServiceProxy.deleteAdminMember(managerMerberDto);
	}
	
	@Test
	public void removeGroupByGroupId(){
		callHuanXinImServiceProxy.removeGroupByGroupId("22892344573954");
	}
	
	@Test
	public void updateGroupByGroupId(){
		
		GroupInfoDto groupInfoDto = new GroupInfoDto();
		
		groupInfoDto.setGroupId("22892344573954");
		groupInfoDto.setDescription("55555555555555555555555555555555555555555");
		groupInfoDto.setGroupname("ZRY好归宿");
		groupInfoDto.setMaxusers(500);
		callHuanXinImServiceProxy.updateGroupByGroupId(groupInfoDto);
	}
	
	@Test
	public void queryGroupInfo(){
		
		List<String> groupIds = new ArrayList<String>();
		groupIds.add("23973188403203");
		callHuanXinImServiceProxy.queryGroupInfo(groupIds);
	}
}
