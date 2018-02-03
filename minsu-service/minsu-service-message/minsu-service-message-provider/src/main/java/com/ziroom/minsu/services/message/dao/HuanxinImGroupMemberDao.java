/**
 * @FileName: HuanxinImGroupMemberDao.java
 * @Package com.ziroom.minsu.services.message.dao
 * 
 * @author yd
 * @created 2017年7月28日 上午11:41:44
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.message.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.message.HuanxinImGroupMemberEntity;
import com.ziroom.minsu.services.message.dto.GagMemberDto;
import com.ziroom.minsu.services.message.dto.GroupMemberPageInfoDto;
import com.ziroom.minsu.services.message.dto.GroupMembersVo;
import com.ziroom.minsu.services.message.dto.ImMembersVo;

/**
 * <p>群组成员表</p>
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
@Repository("message.huanxinImGroupMemberDao")
public class HuanxinImGroupMemberDao {
	
	
	/**
	 * 日志对象
	 */
	private static Logger logger = LoggerFactory.getLogger(HuanxinImGroupMemberDao.class);

	private String SQLID = "message.huanxinImGroupMemberDao.";
	
	@Autowired
	@Qualifier("message.MybatisDaoContext")
	private MybatisDaoContext mybatisDaoContext;
	
	/**
	 * 
	 * 保存
	 *
	 * @author yd
	 * @created 2016年9月9日 下午3:20:18
	 *
	 * @param imRecord
	 * @return
	 * 
	 */
	public int saveHuanxinImGroupMember(HuanxinImGroupMemberEntity huanxinImGroupMember){
		return mybatisDaoContext.save(SQLID+"insertSelective", huanxinImGroupMember);
	}
	
	/**
	 * 
	 * 修改 
	 *
	 * @author yd
	 * @created 2017年7月28日 下午1:50:56
	 *
	 * @param huanxinImGroupMember
	 * @return
	 */
	public int updateHuanxinImGroupMember(HuanxinImGroupMemberEntity huanxinImGroupMember){
		return mybatisDaoContext.update(SQLID+"updateHuanxinImGroupMember", huanxinImGroupMember);
	}
	
	/**
	 * 
	 * 修改 updateBathHuanxinImGroupMember
	 *
	 * @author yd
	 * @created 2017年7月28日 下午1:50:56
	 *
	 * @param huanxinImGroupMember
	 * @return
	 */
	public int updateHuanxinImGroupMemberByCon(GagMemberDto gagMemberDto){
		return mybatisDaoContext.update(SQLID+"updateHuanxinImGroupMemberByCon", gagMemberDto);
	}
	
	/**
	 * 
	 * 分页获取 群组成员
	 *
	 * @author yd
	 * @created 2017年8月8日 下午3:08:26
	 *
	 * @param groupMemberPageInfoDto
	 * @return
	 */
	public PagingResult<GroupMembersVo> queryGroupMemberByPage(GroupMemberPageInfoDto groupMemberPageInfoDto){
		
		if(Check.NuNObj(groupMemberPageInfoDto)){
			LogUtil.error(logger, "groupMemberPageInfoDto  is null");
		}
		PageBounds pageBounds = new PageBounds();
		pageBounds.setLimit(groupMemberPageInfoDto.getLimit());
		pageBounds.setPage(groupMemberPageInfoDto.getPage());

		return mybatisDaoContext.findForPage(SQLID+"queryGroupMemberByPage", GroupMembersVo.class, groupMemberPageInfoDto.toMap(), pageBounds);
	}

	
	/**
	 * 
	 * 通过 uid 和项目id 查询 当前用户该项目下所有群ids
	 *
	 * @author yd
	 * @created 2017年8月10日 下午4:49:39
	 *
	 * @param member
	 * @return
	 */
	public List<String> queryGroupIdsByMember(String member,String projectBid){
		Map<String, String> params = new HashMap<String, String>();
		params.put("member", member);
		params.put("projectBid", projectBid);
	
		return this.mybatisDaoContext.findAllByMaster(SQLID+"queryGroupIdsByMember", String.class, params);
	}

	/**
	 * 
	 * 查询禁言人员
	 *
	 * @author yd
	 * @created 2017年8月22日 下午7:51:18
	 *
	 * @param uid
	 * @return
	 */
	public HuanxinImGroupMemberEntity queryGagMemberByUid(String uid){
		return mybatisDaoContext.findOne(SQLID+"queryGagMemberByUid",HuanxinImGroupMemberEntity.class , uid);
	}
	
	
	/**
	 * 
	 * 查询 当前用户所有群的入群时间
	 *
	 * @author yd
	 * @created 2017年8月23日 上午9:48:40
	 *
	 * @param member
	 * @return
	 */
	public List<HuanxinImGroupMemberEntity> queryGroupInfoByMember(String member){
		return mybatisDaoContext.findAll(SQLID+"queryGroupInfoByMember", HuanxinImGroupMemberEntity.class, member);
	}
	
	/**
	 * 
	 * 根据member 和 groupId 查询群成员
	 *
	 * @author yd
	 * @created 2017年9月6日 下午4:59:47
	 *
	 * @param groupId
	 * @param member
	 * @return
	 */
	public HuanxinImGroupMemberEntity queryMeberByGroupAndMember(String groupId,String member,Integer memberRole){
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("groupId", groupId);
		if(!Check.NuNStr(member)){
			params.put("member", member);
		}
		if(!Check.NuNObj(memberRole)){
			params.put("memberRole", memberRole);
		}
		return mybatisDaoContext.findOne(SQLID+"queryMeberByGroupAndMember",HuanxinImGroupMemberEntity.class , params);
	}
}
