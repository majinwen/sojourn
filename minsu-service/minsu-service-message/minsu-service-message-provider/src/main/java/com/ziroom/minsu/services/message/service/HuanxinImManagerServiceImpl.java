/**
 * @FileName: HuanxinImManagerServiceImpl.java
 * @Package com.ziroom.minsu.services.message.service
 * 
 * @author yd
 * @created 2017年8月1日 上午11:18:52
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.message.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.RandomUtil;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.message.HuanxinImGroupEntity;
import com.ziroom.minsu.entity.message.HuanxinImGroupMemberEntity;
import com.ziroom.minsu.entity.message.HuanxinImGroupOpfailedLogEntity;
import com.ziroom.minsu.entity.message.HuanxinImRecordEntity;
import com.ziroom.minsu.entity.message.HuanxinImRecordImgEntity;
import com.ziroom.minsu.entity.message.MsgHuanxinImLogEntity;
import com.ziroom.minsu.entity.message.MsgUserRelEntity;
import com.ziroom.minsu.entity.message.MsgUserRelOperaEntity;
import com.ziroom.minsu.entity.message.ZryProjectGroupEntity;
import com.ziroom.minsu.services.common.utils.CloseableHttpUtil;
import com.ziroom.minsu.services.message.dao.HuanxinImGroupDao;
import com.ziroom.minsu.services.message.dao.HuanxinImGroupMemberDao;
import com.ziroom.minsu.services.message.dao.HuanxinImGroupOpfailedLogDao;
import com.ziroom.minsu.services.message.dao.HuanxinImRecordDao;
import com.ziroom.minsu.services.message.dao.HuanxinImRecordImgDao;
import com.ziroom.minsu.services.message.dao.MsgHuanxinImLogDao;
import com.ziroom.minsu.services.message.dao.MsgUserRelDao;
import com.ziroom.minsu.services.message.dao.MsgUserRelOperaDao;
import com.ziroom.minsu.services.message.dao.ZryProjectGroupDao;
import com.ziroom.minsu.services.message.dto.DealImYellowPicRequest;
import com.ziroom.minsu.services.message.dto.GagMemberDto;
import com.ziroom.minsu.services.message.dto.GroupBaseDto;
import com.ziroom.minsu.services.message.dto.GroupDto;
import com.ziroom.minsu.services.message.dto.GroupMemberDto;
import com.ziroom.minsu.services.message.dto.GroupMemberPageInfoDto;
import com.ziroom.minsu.services.message.dto.GroupMembersVo;
import com.ziroom.minsu.services.message.dto.GroupOpfailedLogDto;
import com.ziroom.minsu.services.message.dto.HuanxinGroupDto;
import com.ziroom.minsu.services.message.dto.ManagerMerberDto;
import com.ziroom.minsu.services.message.entity.GroupVo;
import com.ziroom.minsu.services.message.entity.ImExtForChangzuVo;
import com.ziroom.minsu.services.message.utils.DealHuanxinPicUtils;
import com.ziroom.minsu.valenum.common.YesOrNoEnum;
import com.ziroom.minsu.valenum.msg.FailedReasonEnum;
import com.ziroom.minsu.valenum.msg.IsDelEnum;
import com.ziroom.minsu.valenum.msg.MemberRoleEnum;
import com.ziroom.minsu.valenum.msg.RunStatusEnum;
import com.ziroom.minsu.valenum.msg.SourceTypeEnum;


/**
 * <p>环信 相关接口 事务层</p>
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
@Service("message.huanxinImManagerServiceImpl")
public class HuanxinImManagerServiceImpl {


	private static Logger LOGGER  = LoggerFactory.getLogger(HuanxinImManagerServiceImpl.class);

	@Resource(name = "message.huanxinImGroupDao")
	private HuanxinImGroupDao huanxinImGroupDao;

	@Resource(name = "message.huanxinImGroupMemberDao")
	private HuanxinImGroupMemberDao  huanxinImGroupMemberDao;

	@Resource(name = "message.huanxinImGroupOpfailedLogDao")
	private HuanxinImGroupOpfailedLogDao  huanxinImGroupOpfailedLogDao;

	@Resource(name = "message.zryProjectGroupDao")
	private ZryProjectGroupDao zryProjectGroupDao;

	@Resource(name = "message.msgHuanxinImLogDao")
	private  MsgHuanxinImLogDao msgHuanxinImLogDao;
	
	@Resource(name="message.msgUserRelDao")
    private MsgUserRelDao msgUserRelDao;
	
	@Resource(name="message.msgUserRelOperaDao")
    private MsgUserRelOperaDao msgUserRelOperaDao;
	
	@Resource(name="message.huanxinImRecordImgDao")
    private HuanxinImRecordImgDao huanxinImRecordImgDao;
	
	@Resource(name="message.huanxinImRecordDao")
    private HuanxinImRecordDao huanxinImRecordDao;

	/**
	 * 
	 * 添加群组
	 * 1. 添加群组 
	 * 2. 添加群成员
	 * 3. 添加项目编号与群组的关心
	 * 
	 * 说明：这里如果 组员添加失败了 就不做保存了 需要通过 sql 添加进去 或 去环信 拉取
	 *
	 * @author yd
	 * @created 2017年8月1日 上午11:41:07
	 *
	 * @param huanxinGroupDto
	 * @return
	 */
	public int saveHuanxinImGroup(HuanxinGroupDto huanxinGroupDto,HuanxinImGroupOpfailedLogEntity huanxinImGroupOpfailedLog){

		if(Check.NuNObj(huanxinGroupDto)||Check.NuNStr(huanxinGroupDto.getGroupId())
				||Check.NuNStr(huanxinGroupDto.getProjectBid())){
			LogUtil.error(LOGGER, "【添加群组参数异常】huanxinGroupDto={}", huanxinGroupDto==null?"":JsonEntityTransform.Object2Json(huanxinGroupDto));
			return 0;
		}

		HuanxinImGroupEntity  huanxinImGroup = new HuanxinImGroupEntity();
		huanxinImGroup.setGroupId(huanxinGroupDto.getGroupId());
		huanxinImGroup.setName(huanxinGroupDto.getGroupname());
		huanxinImGroup.setDescription(huanxinGroupDto.getDesc());
		huanxinImGroup.setIsPublic(huanxinGroupDto.isPublic()?0:1);
		huanxinImGroup.setMembersonly(huanxinGroupDto.isMembersOnly()?0:1);
		huanxinImGroup.setAllowinvites(huanxinGroupDto.isAllowinvites()?0:1);
		huanxinImGroup.setMaxusers(huanxinGroupDto.getMaxusers());
		huanxinImGroup.setInviteNeedConfirm(huanxinGroupDto.isInviteNeedConfirm()?0:1);
		huanxinImGroup.setOwner(huanxinGroupDto.getOwner());
		huanxinImGroup.setIsDel(IsDelEnum.NOT_DEL.getCode());
		huanxinImGroup.setAffiliationsCount(1);


		List<String> members = huanxinGroupDto.getMembers();
		if(!Check.NuNCollection(members)){
			huanxinImGroup.setAffiliationsCount(huanxinImGroup.getAffiliationsCount()+members.size());
		}
		int i  = this.huanxinImGroupDao.saveHuanxinImGroup(huanxinImGroup);
		int j = saveGroupMemberFromAddGroup(huanxinGroupDto);

		if(i<=0||j<=0){
			throw new BusinessException("群组保存异常i="+i+",j="+j+",huanxinGroupDto={"+JsonEntityTransform.Object2Json(huanxinGroupDto)+"}");
		}
		//插入关心
		ZryProjectGroupEntity zryProjectGroup = new ZryProjectGroupEntity();
		zryProjectGroup.setFid(UUIDGenerator.hexUUID());
		zryProjectGroup.setGroupId(huanxinGroupDto.getGroupId());
		zryProjectGroup.setIsDefault(huanxinGroupDto.getIsDefault());
		zryProjectGroup.setIsDel(IsDelEnum.NOT_DEL.getCode());
		zryProjectGroup.setProjectBid(huanxinGroupDto.getProjectBid());
		zryProjectGroup.setOpBid(huanxinGroupDto.getOpFid());
		zryProjectGroup.setOpType(huanxinGroupDto.getOpType());
		int k = zryProjectGroupDao.saveZryProjectGroup(zryProjectGroup);

		if(!Check.NuNObj(huanxinImGroupOpfailedLog)){
			this.huanxinImGroupOpfailedLogDao.updateHuanxinImGroupOpfailedLog(huanxinImGroupOpfailedLog);
		}
		return k;
	}

	/**
	 * 
	 * 添加组 时 添加群成员
	 *
	 * @author yd
	 * @created 2017年8月7日 下午2:22:50
	 *
	 * @param huanxinGroupDto
	 */
	private int saveGroupMemberFromAddGroup(HuanxinGroupDto huanxinGroupDto){

		int i = 0;
		List<HuanxinImGroupMemberEntity> listMembers = new ArrayList<HuanxinImGroupMemberEntity>();
		HuanxinImGroupMemberEntity owerMember = new HuanxinImGroupMemberEntity();
		owerMember.setFid(UUIDGenerator.hexUUID());
		owerMember.setGroupId(huanxinGroupDto.getGroupId());
		owerMember.setMember(huanxinGroupDto.getOwner());
		owerMember.setMemberRole(MemberRoleEnum.OWNER_MEMBER.getCode());
		owerMember.setOpFid(huanxinGroupDto.getOpFid());
		owerMember.setOpType(huanxinGroupDto.getOpType());
		listMembers.add(owerMember);

		List<String> members = huanxinGroupDto.getMembers();
		if(!Check.NuNCollection(members)){
			for (String uid : members) {
				HuanxinImGroupMemberEntity member = new HuanxinImGroupMemberEntity();
				member.setFid(UUIDGenerator.hexUUID());
				member.setGroupId(huanxinGroupDto.getGroupId());
				member.setMember(uid);
				member.setMemberRole(MemberRoleEnum.ORDINARY_MEMBER.getCode());
				member.setOpFid(huanxinGroupDto.getOpFid());
				member.setOpType(huanxinGroupDto.getOpType());
				listMembers.add(member);
			}
		}
		for (HuanxinImGroupMemberEntity huanxinImGroupMemberEntity : listMembers) {
			i +=this.huanxinImGroupMemberDao.saveHuanxinImGroupMember(huanxinImGroupMemberEntity);
		}

		return i;
	}

	/**
	 * 
	 * 添加管理员
	 *
	 * @author yd
	 * @created 2017年8月7日 下午5:28:07
	 *
	 * @param managerMerberDto
	 * @return
	 */
	public int updateGroupMemberFromAdmin(ManagerMerberDto managerMerberDto){
		HuanxinImGroupMemberEntity adminMember = new HuanxinImGroupMemberEntity();
		adminMember.setGroupId(managerMerberDto.getGroupId());
		adminMember.setMember(managerMerberDto.getAdminUid());
		adminMember.setMemberRole(managerMerberDto.getMemberRole());
		adminMember.setOpFid(managerMerberDto.getOpFid());
		adminMember.setOpType(managerMerberDto.getOpType());
		return this.huanxinImGroupMemberDao.updateHuanxinImGroupMember(adminMember);
	}

	/**
	 * 
	 * 删除成员 入库
	 *
	 * @author yd
	 * @created 2017年8月7日 下午6:05:02
	 *
	 * @param groupMemberDto
	 * @return
	 */
	public int updateGroupMemberFromRemMember(GroupMemberDto groupMemberDto,HuanxinImGroupOpfailedLogEntity huanxinImGroupOpfailedLog){

		int i = 0;
		GagMemberDto gagMemberDto = new GagMemberDto();
		gagMemberDto.setMembers(groupMemberDto.getMembers());
		gagMemberDto.setGroupId(groupMemberDto.getGroupId());
		gagMemberDto.setIsDel(IsDelEnum.DEL.getCode());
		gagMemberDto.setRemark(groupMemberDto.getRemark());
		gagMemberDto.setOpFid(groupMemberDto.getOpFid());
		gagMemberDto.setOpType(groupMemberDto.getOpType());
		i = this.updateHuanxinImGroupMemberByCon(gagMemberDto);
		if(i <= 0){
			LogUtil.error(LOGGER, "【删除群成员失败】未更新到记录groupMemberDto={}", JsonEntityTransform.Object2Json(groupMemberDto));
		}
		if(!Check.NuNObj(huanxinImGroupOpfailedLog)){
			this.huanxinImGroupOpfailedLogDao.updateHuanxinImGroupOpfailedLog(huanxinImGroupOpfailedLog);
		}
		return i;
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
	public int updateHuanxinImGroupOpfailedLog(HuanxinImGroupOpfailedLogEntity huanxinImGroupOpfailedLog){
		return this.huanxinImGroupOpfailedLogDao.updateHuanxinImGroupOpfailedLog(huanxinImGroupOpfailedLog);
	}

	/**
	 * 
	 * 解散群
	 *
	 * @author yd
	 * @created 2017年8月8日 上午9:20:38
	 *
	 * @param groupBaseDto
	 * @return
	 */
	public void removeGroup(DataTransferObject dto ,GroupBaseDto groupBaseDto,HuanxinImGroupOpfailedLogEntity huanxinImGroupOpfailedLog ){


		if(dto.getCode() == DataTransferObject.ERROR){
			return ;
		}

		ZryProjectGroupEntity zryProjectGroup = new ZryProjectGroupEntity();

		zryProjectGroup.setGroupId(groupBaseDto.getGroupId());
		zryProjectGroup.setIsDel(IsDelEnum.DEL.getCode());
		zryProjectGroup.setOpBid(groupBaseDto.getOpBid());
		zryProjectGroup.setOpType(groupBaseDto.getOpType());
		int j = this.zryProjectGroupDao.updateZryProjectGroup(zryProjectGroup);

		if(j<=0){
			throw new BusinessException("【删除群与项目的关系失败】groupMemberDto={"+JsonEntityTransform.Object2Json(groupBaseDto)+"}");
		}

		HuanxinImGroupEntity huanxinImGroup = new HuanxinImGroupEntity();
		huanxinImGroup.setIsDel(IsDelEnum.DEL.getCode());
		huanxinImGroup.setGroupId(groupBaseDto.getGroupId());
		j = this.huanxinImGroupDao.updateHuanxinImGroup(huanxinImGroup);

		if(j<=0){
			throw new BusinessException("【删除组失败】groupMemberDto={"+JsonEntityTransform.Object2Json(groupBaseDto)+"}");
		}

		if(!Check.NuNObj(huanxinImGroupOpfailedLog)){
			this.huanxinImGroupOpfailedLogDao.updateHuanxinImGroupOpfailedLog(huanxinImGroupOpfailedLog);
		}

	}


	/**
	 * 
	 * 修改
	 * 如果更新成默认群 需要修改关系
	 * @author yd
	 * @created 2017年7月28日 上午11:29:01
	 *
	 * @param huanxinImGroup
	 * @return
	 */
	public int updateHuanxinImGroup(HuanxinImGroupEntity huanxinImGroup,Integer isDefault){

		LogUtil.info(LOGGER, "参数：huanxinImGroup={},isDefault={}", huanxinImGroup.toJsonStr(),isDefault);
		if(!Check.NuNObj(isDefault) &&isDefault == 0){
			//如果更新成默认群 当前默认群 直接修改成普通群
			ZryProjectGroupEntity zryProjectGroupEntity = 	this.zryProjectGroupDao.queryZryProjectGroup(huanxinImGroup.getGroupId());

			if(Check.NuNObj(zryProjectGroupEntity)){
				throw new BusinessException("【群组已不存在】huanxinImGroup={"+huanxinImGroup.toJsonStr()+"}");
			}
			//默认群组
			HuanxinImGroupEntity defaultGroup = this.huanxinImGroupDao.queryDefaultGroupByProBid(zryProjectGroupEntity.getProjectBid());
			if(!Check.NuNObj(defaultGroup)&&!defaultGroup.getGroupId().equals(huanxinImGroup.getGroupId())){
				ZryProjectGroupEntity zryProjectGroup = new ZryProjectGroupEntity();
				zryProjectGroup.setIsDefault(1);
				zryProjectGroup.setProjectBid(zryProjectGroupEntity.getProjectBid());
				zryProjectGroup.setGroupId(defaultGroup.getGroupId());
				this.zryProjectGroupDao.updateZryProjectGroup(zryProjectGroup);
			}
			ZryProjectGroupEntity zryProjectGroup = new ZryProjectGroupEntity();
			zryProjectGroup.setProjectBid(zryProjectGroupEntity.getProjectBid());
			zryProjectGroup.setGroupId(huanxinImGroup.getGroupId());
			zryProjectGroup.setIsDefault(0);
			this.zryProjectGroupDao.updateZryProjectGroup(zryProjectGroup);
		}

		return  this.huanxinImGroupDao.updateHuanxinImGroup(huanxinImGroup);
	}
	/**
	 * 
	 * 保存失败激励 来自如 移除群成员
	 *
	 * @author yd
	 * @created 2017年8月7日 下午6:26:51
	 *
	 * @param groupMemberDto
	 * @return
	 */
	public int saveGroupOpfailedLogFromRmMem(GroupMemberDto groupMemberDto){

		int i = 0;
		List<String> uids = groupMemberDto.getMembers();

		if(!Check.NuNCollection(uids)){
			List<HuanxinImGroupOpfailedLogEntity> list = new ArrayList<HuanxinImGroupOpfailedLogEntity>();
			for (String uid : uids) {
				HuanxinImGroupOpfailedLogEntity huanxinImGroupOpfailedLog = new HuanxinImGroupOpfailedLogEntity();
				huanxinImGroupOpfailedLog.setFid(UUIDGenerator.hexUUID());
				huanxinImGroupOpfailedLog.setUid(uid);
				huanxinImGroupOpfailedLog.setFailedReason(FailedReasonEnum.DELETE_GROUP_MEMBER_FAILED.getCode());
				huanxinImGroupOpfailedLog.setGroupId(groupMemberDto.getGroupId());
				huanxinImGroupOpfailedLog.setOpFid(groupMemberDto.getOpFid());
				huanxinImGroupOpfailedLog.setOpType(groupMemberDto.getOpType());
				huanxinImGroupOpfailedLog.setSourceType(groupMemberDto.getSourceType());
				huanxinImGroupOpfailedLog.setSysStatu(RunStatusEnum.NOT_RUN.getValue());
				list.add(huanxinImGroupOpfailedLog);
			}
			if(!Check.NuNCollection(list)){
				this.saveBathHuanxinImGroupOpfailedLog(list);
			}
		}

		return i;
	}

	/**
	 * 
	 * 删除群组失败 保存失败记录
	 *
	 * @author yd
	 * @created 2017年8月8日 上午10:11:18
	 *
	 * @param groupBaseDto
	 * @return
	 */
	public int saveGroupOpfailedLogFromRmGroup(GroupBaseDto groupBaseDto){

		int i = 0;

		if(!Check.NuNObj(groupBaseDto)){

			HuanxinImGroupOpfailedLogEntity huanxinImGroupOpfailedLog = new HuanxinImGroupOpfailedLogEntity();
			huanxinImGroupOpfailedLog.setFid(UUIDGenerator.hexUUID());
			huanxinImGroupOpfailedLog.setUid(groupBaseDto.getOwner());
			huanxinImGroupOpfailedLog.setFailedReason(FailedReasonEnum.DELETE_GROUP_FAILED.getCode());
			huanxinImGroupOpfailedLog.setGroupId(groupBaseDto.getGroupId());
			huanxinImGroupOpfailedLog.setOpFid(groupBaseDto.getOpBid());
			huanxinImGroupOpfailedLog.setOpType(groupBaseDto.getOpType());
			huanxinImGroupOpfailedLog.setSourceType(SourceTypeEnum.ZRY_DELETE_GROUP_FAILED.getCode());
			huanxinImGroupOpfailedLog.setSysStatu(RunStatusEnum.NOT_RUN.getValue());
			i= this.saveHuanxinImGroupOpfailedLog(huanxinImGroupOpfailedLog);
		}

		return i;
	}
	/**
	 * 
	 * 保存环信 操作失败记录
	 *
	 * @author yd
	 * @created 2017年8月1日 下午2:12:07
	 *
	 * @param huanxinImGroupOpfailedLog
	 * @return
	 */
	public int saveHuanxinImGroupOpfailedLog(HuanxinImGroupOpfailedLogEntity huanxinImGroupOpfailedLog){
		return huanxinImGroupOpfailedLogDao.saveHuanxinImGroupOpfailedLog(huanxinImGroupOpfailedLog);

	}


	/**
	 * 
	 * 批量保存
	 *
	 * @author yd
	 * @created 2017年8月3日 下午6:42:54
	 *
	 * @param list
	 */
	public void saveBathHuanxinImGroupOpfailedLog(List<HuanxinImGroupOpfailedLogEntity> list){

		for (HuanxinImGroupOpfailedLogEntity huanxinImGroupOpfailedLogEntity : list) {
			huanxinImGroupOpfailedLogDao.saveHuanxinImGroupOpfailedLog(huanxinImGroupOpfailedLogEntity);
		}
	}

	/**
	 * 
	 * 分页查询群组信息
	 *
	 * @author yd
	 * @created 2017年8月1日 下午4:16:13
	 *
	 * @param groupDto
	 * @return
	 */
	public PagingResult<GroupVo>  queryGroupByPage(GroupDto groupDto){
		return huanxinImGroupDao.queryGroupByPage(groupDto);
	}

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
	public int saveMsgHuanxinImLog(MsgHuanxinImLogEntity msgHuanxinImLog){
		return msgHuanxinImLogDao.saveMsgHuanxinImLog(msgHuanxinImLog);
	}

	/**
	 * 
	 * 查询默认群组
	 *
	 * @author yd
	 * @created 2017年8月3日 下午5:59:18
	 *
	 * @param projectBid
	 * @return
	 */
	public HuanxinImGroupEntity queryDefaultGroupByProBid(String projectBid){
		return huanxinImGroupDao.queryDefaultGroupByProBid(projectBid);
	}


	/**
	 * 
	 * 查询默认群组
	 *
	 * @author yd
	 * @created 2017年8月3日 下午5:59:18
	 *
	 * @param projectBid
	 * @return
	 */
	public HuanxinImGroupEntity queryGroupByGroupId(String groupId){
		return huanxinImGroupDao.queryGroupByGroupId(groupId);
	}
	/**
	 * 
	 * 批量保存群成员
	 *
	 * @author yd
	 * @created 2017年8月3日 下午6:20:17
	 *
	 * @param list
	 */
	public void saveBathHuanxinImGroupMember( GroupMemberDto groupMemberDto,HuanxinImGroupOpfailedLogEntity huanxinImGroupOpfailedLog){

		if(!Check.NuNObj(groupMemberDto)){
			List<String> members  = groupMemberDto.getMembers();
			List<HuanxinImGroupMemberEntity> list = new ArrayList<HuanxinImGroupMemberEntity>();
			for (String member : members) {
				HuanxinImGroupMemberEntity  huanxinImGroupMember = new HuanxinImGroupMemberEntity();
				huanxinImGroupMember.setFid(UUIDGenerator.hexUUID());
				huanxinImGroupMember.setGroupId(groupMemberDto.getGroupId());
				huanxinImGroupMember.setMember(member);
				huanxinImGroupMember.setMemberRole(groupMemberDto.getMemberRole());
				huanxinImGroupMember.setOpFid(groupMemberDto.getOpFid());
				huanxinImGroupMember.setOpType(groupMemberDto.getOpType());
				list.add(huanxinImGroupMember);
			}

			if(!Check.NuNCollection(list)){
				for (HuanxinImGroupMemberEntity huanxinImGroupMemberEntity : list) {
					this.huanxinImGroupMemberDao.saveHuanxinImGroupMember(huanxinImGroupMemberEntity);
				}
			}

			if(!Check.NuNObj(huanxinImGroupOpfailedLog)){
				this.huanxinImGroupOpfailedLogDao.updateHuanxinImGroupOpfailedLog(huanxinImGroupOpfailedLog);
			}
		}
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
		return huanxinImGroupMemberDao.updateHuanxinImGroupMember(huanxinImGroupMember);
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
		return huanxinImGroupMemberDao.updateHuanxinImGroupMemberByCon(gagMemberDto);
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
		return huanxinImGroupMemberDao.queryGroupMemberByPage(groupMemberPageInfoDto);
	}

	/**
	 * 
	 * 分页查询群组信息
	 *
	 * @author yd
	 * @created 2017年8月1日 下午4:16:13
	 *
	 * @param groupDto
	 * @return
	 */
	public PagingResult<HuanxinImGroupOpfailedLogEntity>  queryGroupOpfailedByPage(GroupOpfailedLogDto groupOpfailedLogDto){
		return this.huanxinImGroupOpfailedLogDao.queryGroupOpfailedByPage(groupOpfailedLogDto);
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
		return huanxinImGroupMemberDao.queryGroupIdsByMember(member, projectBid);
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
		return huanxinImGroupMemberDao.queryGagMemberByUid(uid);
	}

	/**
	 * 
	 * 查询当前用户 所有群的入群时间
	 *
	 * @author yd
	 * @created 2017年8月23日 上午9:52:32
	 *
	 * @param member
	 * @return
	 */
	public Map<String, Date> findIntoGroupTimeByMember(String member){

		List<HuanxinImGroupMemberEntity> list = huanxinImGroupMemberDao.queryGroupInfoByMember(member);
		Map<String, Date> map = new HashMap<String, Date>();
		if(!Check.NuNCollection(list)){
			for (HuanxinImGroupMemberEntity huanxinImGroupMemberEntity : list) {
				if(!Check.NuNStr(huanxinImGroupMemberEntity.getGroupId())
						&&!Check.NuNObj(huanxinImGroupMemberEntity.getCreateDate())){
					map.put(huanxinImGroupMemberEntity.getGroupId(), huanxinImGroupMemberEntity.getCreateDate());
				}
			}
		}
		return map;
	}

	/**
	 * 保存或者修改聊天用户关系
	 *
	 * @author loushuai
	 * @created 2017年9月4日 下午3:29:38
	 *
	 * @param msgUserRel
	 */
	public int saveMsgUserRel(MsgUserRelEntity msgUserRel) {
			int code = msgUserRelDao.insertSelective(msgUserRel);
			if(code>0){
				code = code + saveMsgUserRelLog(msgUserRel, YesOrNoEnum.YES.getCode(), YesOrNoEnum.NO.getCode());
			}
			return code;
	}

	/**
	 *  查询聊天用户关系
	 *
	 * @author loushuai
	 * @created 2017年9月4日 下午5:24:02
	 *
	 * @param msgUserRel
	 */
	public MsgUserRelEntity queryMsgUserRel(MsgUserRelEntity msgUserRel) {
		return msgUserRelDao.selectByParam(msgUserRel);
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
		return huanxinImGroupMemberDao.queryMeberByGroupAndMember(groupId, member, memberRole);
	}
	/**
	 * 
	 * 更换群组
	 * 1. 把老群组 更新成为普通成员
	 * 2. 把信成员 更新成群组
	 *
	 * @author yd
	 * @created 2017年9月6日 下午4:52:37
	 *
	 * @param groupBaseDto
	 * @param oldOwer  老群组uid
	 * @return
	 */
	public int updateGroupOwer(GroupBaseDto groupBaseDto,String oldOwer){
		
		if(Check.NuNObj(groupBaseDto)||Check.NuNStr(groupBaseDto.getGroupId())
				||Check.NuNStr(groupBaseDto.getOwner())||Check.NuNStr(oldOwer)){
			LogUtil.error(LOGGER, "【群主修改】参数错误groupBaseDto：{},老群主uid:{}}", groupBaseDto==null?"":JsonEntityTransform.Object2Json(groupBaseDto),oldOwer);
			throw new BusinessException("【群主修改】参数错误");
		}
		LogUtil.info(LOGGER, "【群主修改】群号：{},老群主uid:{},新群主uid:{}", groupBaseDto.getGroupId(),oldOwer,groupBaseDto.getOwner());
	
		
		int i = 0;
		
		HuanxinImGroupMemberEntity old = new HuanxinImGroupMemberEntity();
		old.setOpFid(groupBaseDto.getOpBid());
		old.setOpType(groupBaseDto.getOpType());
		old.setGroupId(groupBaseDto.getGroupId());
		old.setMemberRole(MemberRoleEnum.ORDINARY_MEMBER.getCode());
		old.setMember(oldOwer);
		
		i = this.updateHuanxinImGroupMember(old);
		if(i!=1){
			LogUtil.error(LOGGER, "【老群主修改成普通成员失败】groupBaseDto：{},老群主uid:{},i={}", groupBaseDto==null?"":JsonEntityTransform.Object2Json(groupBaseDto),oldOwer,i);
			throw new BusinessException("【老群主修改成普通成员失败】");
		}
		
		old.setMember(groupBaseDto.getOwner());
		old.setMemberRole(MemberRoleEnum.OWNER_MEMBER.getCode());
		
		int j = this.updateHuanxinImGroupMember(old);
		
		if(j!=1){
			LogUtil.error(LOGGER, "【新群主修改失败】groupBaseDto：{},老群主uid:{},j={}", groupBaseDto==null?"":JsonEntityTransform.Object2Json(groupBaseDto),oldOwer,j);
			throw new BusinessException("【新群主修改失败】");
		}
		return i;
	}

	/**
	 * 获取所有尚未鉴别的照片
	 *
	 * @author loushuai
	 * @created 2017年9月8日 下午3:54:57
	 *
	 * @param request
	 * @return
	 */
	public List<HuanxinImRecordImgEntity> queryAllNeedDealImPic(Map<String, Object> paramMap) {
		return huanxinImRecordImgDao.queryAllNeedDealImPic(paramMap);
	}

	/**
	 * 处理黄色图片
	 *
	 * @author loushuai
	 * @created 2017年9月8日 下午4:24:53
	 *
	 * @param list
	 */
	public void dealImYellowPic(HuanxinImRecordImgEntity huanxinImRecordImg,String porn) {
		            LogUtil.info(LOGGER, "dealImYellowPic参数  huanxinImRecordImg={} porn={}", JsonEntityTransform.Object2Json(huanxinImRecordImg),porn);
					HuanxinImRecordImgEntity newHuanxinImRecordImg = new HuanxinImRecordImgEntity();
					if(porn.equals("no")){//不是黄色图片
						newHuanxinImRecordImg.setHuanxinFid(huanxinImRecordImg.getHuanxinFid());
						newHuanxinImRecordImg.setIsCertified(YesOrNoEnum.YES.getCode());
						huanxinImRecordImgDao.updateByParam(newHuanxinImRecordImg);
						return;
					}
					newHuanxinImRecordImg.setHuanxinFid(huanxinImRecordImg.getHuanxinFid());
					newHuanxinImRecordImg.setIsCertified(YesOrNoEnum.YES.getCode());
					newHuanxinImRecordImg.setIsCompliance(YesOrNoEnum.YES.getCode());
					int updateByParam = huanxinImRecordImgDao.updateByParam(newHuanxinImRecordImg);
					if(updateByParam>0){
						 HuanxinImRecordEntity result = huanxinImRecordDao.getByHuanxinFid(huanxinImRecordImg.getHuanxinFid());
						 if(!Check.NuNObj(result)){
							 ImExtForChangzuVo imExtForChangzuVo = JSONObject.parseObject(result.getExt(), ImExtForChangzuVo.class);
							 imExtForChangzuVo.setIsPicNeedVagueDeal(YesOrNoEnum.YES.getCode());
							 HuanxinImRecordEntity huanxinImRecord = new HuanxinImRecordEntity();
							 huanxinImRecord.setFid(huanxinImRecordImg.getHuanxinFid());
							 huanxinImRecord.setExt(imExtForChangzuVo.toJsonStr());
							 huanxinImRecordDao.updateByParam(huanxinImRecord);
						 }
					}
	}

	/**
	 * 获取对象
	 *
	 * @author loushuai
	 * @created 2017年9月12日 下午7:01:50
	 *
	 * @param msgUserRel
	 * @return
	 */
	public MsgUserRelEntity getMsgUserRelByParam(MsgUserRelEntity msgUserRel) {
		return msgUserRelDao.selectByParam(msgUserRel);
	}

	/**
	 * 更新聊天关系
	 *
	 * @author loushuai
	 * @created 2017年9月12日 下午7:13:30
	 *
	 * @param newMsgUserRel
	 */
	public int upMsgUserRel(MsgUserRelEntity msgUserRel) {
		int code = msgUserRelDao.updateByFid(msgUserRel);
		if(code>0){
			saveMsgUserRelLog(msgUserRel, YesOrNoEnum.NO.getCode(), YesOrNoEnum.NO.getCode());
		}
		return code;
	}

	/**
	 * 从t_huanxin_im_record_img获取所有is_certified=1和is_compliance=1的huanxin_fid集合
	 *
	 * @author loushuai
	 * @created 2017年9月13日 上午10:12:28
	 *
	 * @return
	 */
	public List<String> getAllAbnormalPic(Map<String,Object> map) {
		return huanxinImRecordImgDao.getAllAbnormalPic(map);
	}

	/**
	 * 插入聊天关系日志表
	 *
	 * @author loushuai
	 * @created 2017年9月13日 下午8:28:19
	 *
	 * @param msgUserRel
	 */
	public int saveMsgUserRelLog(MsgUserRelEntity msgUserRel, Integer fromStatus, Integer toStatus) {
		MsgUserRelOperaEntity  msgUserRelOpera = new MsgUserRelOperaEntity();
		if(!Check.NuNStr(msgUserRel.getFid())){
			msgUserRelOpera.setMsgUserRelFid(msgUserRel.getFid());
		}else{
			String fid = UUIDGenerator.hexUUID();
			msgUserRel.setFid(fid);
			msgUserRelOpera.setMsgUserRelFid(fid);
		}
		msgUserRelOpera.setFromStatus(fromStatus);
		msgUserRelOpera.setToStatus(toStatus);
		msgUserRelOpera.setCreaterType(msgUserRel.getCreaterType());
		msgUserRelOpera.setCreateFid(msgUserRel.getCreateFid());
		msgUserRelOpera.setRemark(msgUserRel.getRemark());
		return msgUserRelOperaDao.insertSelective(msgUserRelOpera);
	}

	/**
	 * 取消屏蔽关系
	 *
	 * @author loushuai
	 * @created 2017年9月14日 上午11:29:15
	 *
	 * @param dataMsgUserRel
	 */
	public void cancelShield(MsgUserRelEntity dataMsgUserRel) {
		MsgUserRelEntity newMsgUserRel = new MsgUserRelEntity();
		newMsgUserRel.setFid(dataMsgUserRel.getFid());
		newMsgUserRel.setRelStatus(YesOrNoEnum.YES.getCode());
		newMsgUserRel.setIsDel(YesOrNoEnum.YES.getCode());
		int updateByFid = msgUserRelDao.updateByFid(newMsgUserRel);
		if(updateByFid>0){
			saveMsgUserRelLog(dataMsgUserRel, YesOrNoEnum.NO.getCode(), YesOrNoEnum.YES.getCode());
		}
	}
}
