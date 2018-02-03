/**
 * @FileName: HuanxinImGroupMemberDaoTest.java
 * @Package com.ziroom.minsu.services.message.test.dao
 * 
 * @author yd
 * @created 2017年8月3日 下午6:59:26
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.message.test.dao;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.minsu.entity.message.HuanxinImGroupMemberEntity;
import com.ziroom.minsu.services.message.dao.HuanxinImGroupMemberDao;
import com.ziroom.minsu.services.message.dto.GroupMemberPageInfoDto;
import com.ziroom.minsu.services.message.dto.GroupMembersVo;
import com.ziroom.minsu.services.message.test.base.BaseTest;

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
public class HuanxinImGroupMemberDaoTest extends BaseTest{


	@Resource(name = "message.huanxinImGroupMemberDao")
	private HuanxinImGroupMemberDao huanxinImGroupMemberDao;
	/**
	 * Test method for {@link com.ziroom.minsu.services.message.dao.HuanxinImGroupMemberDao#saveHuanxinImGroupMember(com.ziroom.minsu.entity.message.HuanxinImGroupMemberEntity)}.
	 */
	@Test
	public void testSaveHuanxinImGroupMember() {

		HuanxinImGroupMemberEntity  huanxinImGroupMember = new HuanxinImGroupMemberEntity();
		huanxinImGroupMember.setFid(UUIDGenerator.hexUUID());
		huanxinImGroupMember.setGroupId("1564564564564564564");
		huanxinImGroupMember.setMember("456456456f45d6s4f65ds4f");
		huanxinImGroupMember.setMemberRole(2);
		huanxinImGroupMember.setOpFid("4564fds564f5d6s4f56ds");
		huanxinImGroupMember.setOpType(1);
		huanxinImGroupMemberDao.saveHuanxinImGroupMember(huanxinImGroupMember);
	}

	/**
	 * Test method for {@link com.ziroom.minsu.services.message.dao.HuanxinImGroupMemberDao#updateHuanxinImGroupMember(com.ziroom.minsu.entity.message.HuanxinImGroupMemberEntity)}.
	 */
	@Test
	public void testQueryGroupMemberByPage() {
		
		GroupMemberPageInfoDto groupMemberPageInfoDto = new GroupMemberPageInfoDto();
		//groupMemberPageInfoDto.setMemberRole(MemberRoleEnum.ADMIN_MEMBER.getCode());
		groupMemberPageInfoDto.setMemberStatu(1);
		PagingResult<GroupMembersVo>list = this.huanxinImGroupMemberDao.queryGroupMemberByPage(groupMemberPageInfoDto);
		System.out.println(list.getRows());
	}

	@Test
	public void queryGroupIdsByMemberTest(){
		
		List<String> list = this.huanxinImGroupMemberDao.queryGroupIdsByMember("08d1dfaa-c3b2-e4f4-8f66-03ced4bd15b9", "63615afa5a344153a047aca1ea32cc51");
		
		System.out.println(list);
	}
	@Test
	public void queryGagMemberByUid(){
		HuanxinImGroupMemberEntity mem = this.huanxinImGroupMemberDao.queryGagMemberByUid("2f9c68b6-9e44-4a74-a0e4-c181bee8bda6");
		
		System.out.println(mem);
	}
	
	
	@Test
	public void queryMeberByGroupAndMember(){
		
		HuanxinImGroupMemberEntity mem = this.huanxinImGroupMemberDao.queryMeberByGroupAndMember("25780968030209","0481bb8a-f6ac-1633-63fe-91fc2c9f1ae0",0);
		
		System.out.println(mem);
	}
}
