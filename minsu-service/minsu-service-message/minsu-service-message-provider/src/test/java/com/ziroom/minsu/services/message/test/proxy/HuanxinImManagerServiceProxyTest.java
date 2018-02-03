/**
 * @FileName: HuanxinImManagerServiceProxyTest.java
 * @Package com.ziroom.minsu.services.message.test.proxy
 * 
 * @author yd
 * @created 2017年7月31日 下午4:14:36
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.message.test.proxy;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.minsu.entity.message.MsgHuanxinImLogEntity;
import com.ziroom.minsu.entity.message.MsgUserRelEntity;
import com.ziroom.minsu.services.message.dto.GagMemberDto;
import com.ziroom.minsu.services.message.dto.GroupBaseDto;
import com.ziroom.minsu.services.message.dto.GroupDto;
import com.ziroom.minsu.services.message.dto.GroupInfoDto;
import com.ziroom.minsu.services.message.dto.GroupMemberDto;
import com.ziroom.minsu.services.message.dto.GroupMemberPageInfoDto;
import com.ziroom.minsu.services.message.dto.HuanxinGroupDto;
import com.ziroom.minsu.services.message.dto.ManagerMerberDto;
import com.ziroom.minsu.services.message.proxy.HuanxinImManagerServiceProxy;
import com.ziroom.minsu.services.message.test.base.BaseTest;
import com.ziroom.minsu.valenum.msg.CreaterTypeEnum;
import com.ziroom.minsu.valenum.msg.SourceTypeEnum;

/**
 * <p>TODO</p>
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
public class HuanxinImManagerServiceProxyTest extends BaseTest{

	@Resource(name = "message.huanxinImManagerServiceProxy")
	private  HuanxinImManagerServiceProxy huanxinImManagerServiceProxy;

	/**
	 * Test method for {@link com.ziroom.minsu.services.message.proxy.HuanxinImManagerServiceProxy#queryGroupMemberByPage(java.lang.String)}.
	 */
	@Test
	public void testQueryGroupMemberByPage() {

		GroupMemberPageInfoDto groupMemberPageInfoDto = new GroupMemberPageInfoDto();
		groupMemberPageInfoDto.setGroupId("33577232302081");
		groupMemberPageInfoDto.setLimit(40);
		groupMemberPageInfoDto.setPage(1);
		String queryGroupMemberByPage = huanxinImManagerServiceProxy.queryGroupMemberByPage(JsonEntityTransform.Object2Json(groupMemberPageInfoDto));
		System.out.println(queryGroupMemberByPage);

	}

	@Test
	public void queryGroupMemberByPageFromMinsu(){
		
		GroupMemberPageInfoDto groupMemberPageInfoDto = new GroupMemberPageInfoDto();
		groupMemberPageInfoDto.setGroupId("23794144051201");
		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(huanxinImManagerServiceProxy.queryGroupMemberByPageFromMinsu(JsonEntityTransform.Object2Json(groupMemberPageInfoDto)));
		System.out.println(dto);
		
	}
	@Test
	public void testAddGroup(){
		HuanxinGroupDto huanxinGroupDto = new HuanxinGroupDto();

		huanxinGroupDto.setGroupname("CBD自如驿：杨东测试群主转化功能");
		huanxinGroupDto.setDesc("{\"headUrl\":\"http://10.16.34.42:8080/group3/M00/01/B0/ChAiKlmBjuaADgRrAAD9mF3jb8s382.jpg\"}");
		huanxinGroupDto.setOwner("82e52597-d8aa-4da2-92a9-78a07f3e29a1");
		huanxinGroupDto.setProjectBid("63615afa5a344153a047aca1ea32cc51");
		huanxinGroupDto.setOpFid("0f163457-ad6a-09ce-d5de-de452a251cf6");
		huanxinGroupDto.setOpType(CreaterTypeEnum.ZIROOM_USER.getCode());

		List<String> merbers = new ArrayList<String>();
		merbers.add("7a8c4184-8e2e-37b4-08e8-f4c20225e350");
		huanxinGroupDto.setMembers(merbers);

		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(huanxinImManagerServiceProxy.addGroup(JsonEntityTransform.Object2Json(huanxinGroupDto)));
		System.out.println(dto);
	}

	@Test
	public void testQueryAppGroupsByPage(){

		GroupDto groupDto = new GroupDto();
		groupDto.setName("谁的群");
		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(huanxinImManagerServiceProxy.queryAppGroupsByPage(JsonEntityTransform.Object2Json(groupDto)));
		System.out.println(dto);
	}

	@Test
	public void testAddManyGroupMember(){

		GroupMemberDto groupMemberDto = new GroupMemberDto();

		groupMemberDto.setGroupId("25781095956481");
		groupMemberDto.setOpFid("60002160");
		groupMemberDto.setOpType(CreaterTypeEnum.GUARD.getCode());
		groupMemberDto.setSourceType(SourceTypeEnum.ZRY_ZO_ADD_GROUP_MEMBER.getCode());
		List<String> merbers = new ArrayList<String>();
		merbers.add("b2c760ad-de1e-4113-a396-1a59dbbf2442");
		//merbers.add("2f62424a-fb11-ed5a-edfe-d7c0b9a00dc6");
		//merbers.add("2f7215c0-b31b-7785-5550-52bab986f064");
		//merbers.add("");
		groupMemberDto.setMembers(merbers);
		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(huanxinImManagerServiceProxy.addManyGroupMember(JsonEntityTransform.Object2Json(groupMemberDto)));
		System.out.println(dto);
	}
	@Test
	public void testRemoveGroupMembers() throws Exception{
		GroupMemberDto groupMemberDto = new GroupMemberDto();

		groupMemberDto.setGroupId("23953027432449");
		groupMemberDto.setOpFid("1111");
		groupMemberDto.setOpType(CreaterTypeEnum.ZIROOM_USER.getCode());
		groupMemberDto.setProjectBid("63615afa5a344153a047aca1ea32cc51");
		List<String> merbers = new ArrayList<String>();
		merbers.add("461eefee-a9a8-e9db-263b-196cd6b6df6f");
		groupMemberDto.setSourceType(SourceTypeEnum.ZRY_ZO_ADD_GROUP_MEMBER.getCode());
		//merbers.add("");
		groupMemberDto.setMembers(merbers);
		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(huanxinImManagerServiceProxy.removeGroupMembers(JsonEntityTransform.Object2Json(groupMemberDto)));
		System.out.println(dto.getMsg());
		
		Thread.sleep(100000);
		
	}

	@Test
	public void addGagMember(){
		GagMemberDto gagMemberDto = new GagMemberDto();
		gagMemberDto.setGroupId("23953027432449");
		List<String> members = new ArrayList<String>();
		members.add("461eefee-a9a8-e9db-263b-196cd6b6df6f");
		//gagMemberDto.setMuteDuration(10000L);
		gagMemberDto.setMembers(members);
		gagMemberDto.setOpFid("1111");
		gagMemberDto.setOpType(CreaterTypeEnum.ZIROOM_USER.getCode());

		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(huanxinImManagerServiceProxy.addGagMember(JsonEntityTransform.Object2Json(gagMemberDto)));
		System.out.println(dto);
		
		try {
			Thread.sleep(100000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void removeGagMember(){
		GagMemberDto gagMemberDto = new GagMemberDto();
		gagMemberDto.setGroupId("23976886730754");
		List<String> members = new ArrayList<String>();
		members.add("5f4f193b-07fd-a708-85f8-22907004fd6d");
		gagMemberDto.setMuteDuration(10000L);
		gagMemberDto.setMembers(members);
		gagMemberDto.setOpFid("1111");
		gagMemberDto.setOpType(CreaterTypeEnum.ZIROOM_USER.getCode());


		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(huanxinImManagerServiceProxy.removeGagMember(JsonEntityTransform.Object2Json(gagMemberDto)));
		System.out.println(dto);
		
		try {
			Thread.sleep(100000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void addAdminMember(){


		ManagerMerberDto managerMerberDto = new ManagerMerberDto();
		managerMerberDto.setAdminUid("5f4f193b-07fd-a708-85f8-22907004fd6d");
		managerMerberDto.setGroupId("23794144051201");

		managerMerberDto.setOpFid("1111");
		managerMerberDto.setOpType(CreaterTypeEnum.ZIROOM_USER.getCode());
		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(huanxinImManagerServiceProxy.addAdminMember(JsonEntityTransform.Object2Json(managerMerberDto)));
		System.out.println(dto);
	}

	@Test
	public void deleteAdminMember(){
		ManagerMerberDto managerMerberDto = new ManagerMerberDto();
		managerMerberDto.setAdminUid("5f4f193b-07fd-a708-85f8-22907004fd6d");
		managerMerberDto.setGroupId("23794144051201");
		managerMerberDto.setOpFid("1111");
		managerMerberDto.setOpType(CreaterTypeEnum.ZIROOM_USER.getCode());
		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(huanxinImManagerServiceProxy.deleteAdminMember(JsonEntityTransform.Object2Json(managerMerberDto)));
		System.out.println(dto);
	}
	
	@Test
	public void updateGroupByGroupId(){
		
		GroupInfoDto groupInfoDto  = new GroupInfoDto();
		groupInfoDto.setGroupId("23953027432449");
		groupInfoDto.setGroupname("CBD-A房间111111");
		groupInfoDto.setIsDefault(0);
		groupInfoDto.setOpBid("f5dsf5ds+f5+dsf45ds");
		groupInfoDto.setOpType(2);
		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(huanxinImManagerServiceProxy.updateGroupByGroupId(JsonEntityTransform.Object2Json(groupInfoDto)));
		System.out.println(dto);
	}
	
	@Test
	public void dealGroupOpfailedTest(){
		huanxinImManagerServiceProxy.dealGroupOpfailed();
	}
	
	@Test
	public void saveMsgUserRelTest(){
		MsgUserRelEntity record = new MsgUserRelEntity();
		record.setFid(UUIDGenerator.hexUUID());
		record.setFromUid("e1751628-5a0f-4756-a9fc-c000343a5976");
		record.setToUid("app_eaaf194b-067e-4289-bcd7-63a9433d3ef4");
		record.setSourceType(200);
		record.setRemark("测试屏蔽对方的方法");
		record.setZiroomFlag("ZIROOM_CHANGZU_IM");
		record.setCreateFid("e1751628-5a0f-4756-a9fc-c000343a5976");
		record.setCreaterType(0);
		String saveMsgUserRel = huanxinImManagerServiceProxy.saveOrUpdateMsgUserRel(JsonEntityTransform.Object2Json(record));
		System.out.println(saveMsgUserRel);
		
	}
	
	
	@Test
	public void transferGroup(){
		
		GroupBaseDto  groupBaseDto = new GroupBaseDto();
		groupBaseDto.setOpBid("60002160");
		groupBaseDto.setOpType(CreaterTypeEnum.GUARD.getCode());
		groupBaseDto.setGroupId("26529315749889");
		groupBaseDto.setOwner("7a8c4184-8e2e-37b4-08e8-f4c20225e350");
		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(this.huanxinImManagerServiceProxy.transferGroup(JsonEntityTransform.Object2Json(groupBaseDto)));
		System.out.println(dto);
	}
	
	@Test
	public void savesaveMsgHuanxinImLog(){

		MsgHuanxinImLogEntity msgHuanxinImLog = new MsgHuanxinImLogEntity();
		
		msgHuanxinImLog.setChatStatu(0);
		msgHuanxinImLog.setChatType("chat");
		msgHuanxinImLog.setContent("哈哈哈哈");
		msgHuanxinImLog.setExt("fdsafojsdfjdsfj");
		msgHuanxinImLog.setFromUid("1f56sd4f56s4f5dsf4ds56f4ds4fds564f");
		msgHuanxinImLog.setMsgId("GFGFDSDAS fdfaasfdafdsf");
		msgHuanxinImLog.setToUid("4564564564564564564564564564564564");
		msgHuanxinImLog.setType("txt");
		msgHuanxinImLog.setZiroomFlag("ZIROOM_CHANGZU_IM");
		msgHuanxinImLog.setExt("测试");
		msgHuanxinImLog.setType("txt");
/*		msgHuanxinImLog.setLength(1000);
		msgHuanxinImLog.setUrl("http:localhost:8080");
		msgHuanxinImLog.setFileLength(81818181);
		msgHuanxinImLog.setSize("房东发送放大");
		msgHuanxinImLog.setSecret("123456789");
		msgHuanxinImLog.setLat((float) 9.1);
		msgHuanxinImLog.setLng((float) 9.1);
		msgHuanxinImLog.setAddr("fdsafdsf");*/
		String saveMsgUserRel = huanxinImManagerServiceProxy.saveMsgHuanxinImLog(JsonEntityTransform.Object2Json(msgHuanxinImLog));
		System.out.println(saveMsgUserRel);
		
	}
	@Test
	public void testdealImYellowPic(){
		huanxinImManagerServiceProxy.dealImYellowPic();
	}
	
	@Test
	public void queryMsgUserReltest(){
		
		MsgUserRelEntity msgUserRel = new MsgUserRelEntity();
		msgUserRel.setFromUid("0b7ea7d4-0f47-bac2-7e14-8e12a531ece3");
		msgUserRel.setToUid("01c22938-c803-4f2f-851a-e67b537ab474");
		msgUserRel.setZiroomFlag("ZIROOM_CHANGZU_IM");
		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(this.huanxinImManagerServiceProxy.queryMsgUserRel(JsonEntityTransform.Object2Json(msgUserRel)));
		System.out.println(dto);
	}
}
