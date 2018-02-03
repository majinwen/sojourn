/**
 * @FileName: HuanxinImManagerServiceProxy.java
 * @Package com.ziroom.minsu.services.message.proxy
 * 
 * @author yd
 * @created 2017年7月31日 下午4:10:33
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.message.proxy;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.cxf.common.jaxb.JAXBUtils.IdentifierType;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.message.HuanxinImGroupEntity;
import com.ziroom.minsu.entity.message.HuanxinImGroupMemberEntity;
import com.ziroom.minsu.entity.message.HuanxinImGroupOpfailedLogEntity;
import com.ziroom.minsu.entity.message.HuanxinImRecordEntity;
import com.ziroom.minsu.entity.message.HuanxinImRecordImgEntity;
import com.ziroom.minsu.entity.message.MsgHuanxinImLogEntity;
import com.ziroom.minsu.entity.message.MsgUserRelEntity;
import com.ziroom.minsu.services.common.dto.PageRequest;
import com.ziroom.minsu.services.common.utils.CloseableHttpUtil;
import com.ziroom.minsu.services.message.api.inner.HuanxinImManagerService;
import com.ziroom.minsu.services.message.dto.DealImYellowPicRequest;
import com.ziroom.minsu.services.message.dto.GagMemberDto;
import com.ziroom.minsu.services.message.dto.GroupBaseDto;
import com.ziroom.minsu.services.message.dto.GroupDto;
import com.ziroom.minsu.services.message.dto.GroupInfoDto;
import com.ziroom.minsu.services.message.dto.GroupMemberDto;
import com.ziroom.minsu.services.message.dto.GroupMemberPageInfoDto;
import com.ziroom.minsu.services.message.dto.GroupMembersVo;
import com.ziroom.minsu.services.message.dto.GroupOpfailedLogDto;
import com.ziroom.minsu.services.message.dto.HuanxinGroupDto;
import com.ziroom.minsu.services.message.dto.ManagerMerberDto;
import com.ziroom.minsu.services.message.dto.SendImMsgRequest;
import com.ziroom.minsu.services.message.entity.CustomerInfoVo;
import com.ziroom.minsu.services.message.entity.GroupVo;
import com.ziroom.minsu.services.message.entity.ImExtForChangzuVo;
import com.ziroom.minsu.services.message.send.JpushSendService;
import com.ziroom.minsu.services.message.send.SmsAndJpushSendService;
import com.ziroom.minsu.services.message.send.SmsSendService;
import com.ziroom.minsu.services.message.service.HuanxinImManagerServiceImpl;
import com.ziroom.minsu.services.message.utils.DealHuanxinPicUtils;
import com.ziroom.minsu.valenum.base.AuthIdentifyEnum;
import com.ziroom.minsu.valenum.common.YesOrNoEnum;
import com.ziroom.minsu.valenum.msg.FailedReasonEnum;
import com.ziroom.minsu.valenum.msg.ImTypeEnum;
import com.ziroom.minsu.valenum.msg.MemberRoleEnum;
import com.ziroom.minsu.valenum.msg.MemberStatuEnum;
import com.ziroom.minsu.valenum.msg.MessageTemplateCodeEnum;
import com.ziroom.minsu.valenum.msg.MsgExtTypeEnum;
import com.ziroom.minsu.valenum.msg.MsgUserRelSCreateTypeEnum;
import com.ziroom.minsu.valenum.msg.MsgUserRelSourceTypeEnum;
import com.ziroom.minsu.valenum.msg.RunStatusEnum;
import com.ziroom.minsu.valenum.msg.SourceTypeEnum;
import com.ziroom.minsu.valenum.msg.TargetTypeEnum;

/**
 * <p>环信 相关操作</p>
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
@Component("message.huanxinImManagerServiceProxy")
public class HuanxinImManagerServiceProxy implements HuanxinImManagerService{



	private  static Logger logger =LoggerFactory.getLogger(HuanxinImManagerServiceProxy.class);


	@Resource(name = "message.callHuanXinImServiceProxy")
	private CallHuanXinImServiceProxy callHuanXinImServiceProxy;


	@Resource(name = "message.huanxinImManagerServiceImpl")
	private HuanxinImManagerServiceImpl huanxinImManagerServiceImpl;


	@Value("#{'${HUANXIN_DOMAIN}'.trim()}")
	private String HUANXIN_DOMAIN;

	@Value("#{'${IM_DOMAIN_FLAG}'.trim()}")
	private String IM_DOMAIN_FLAG;


	@Value("#{'${IM_ZRY_FLAG}'.trim()}")
	private String IM_ZRY_FLAG;

	@Value("#{'${ZIROOM_CHANGZU_IM}'.trim()}")
	private String ZIROOM_CHANGZU_IM;
	
	@Value("#{'${INENTIFY_PIC_URL}'.trim()}")
	private String INENTIFY_PIC_URL;
	
	@Resource(name = "message.jpushSendService")
	private JpushSendService  jpushSendService;

	@Resource(name = "message.smsAndJpushSendService")
	private SmsAndJpushSendService smsAndJpushSendService;


	@Resource(name = "message.smsSendService")
	private SmsSendService  smsSendService;
	/**
	 * 
	 * 分页从 环信查询 当前群成员
	 *
	 * @author yd
	 * @created 2017年7月31日 上午11:30:19
	 *
	 * @param groupMemberPageInfoDto
	 * @return
	 */
	@Override
	public String queryGroupMemberByPage(String jsonParam) {
		GroupMemberPageInfoDto groupMemberPageInfoDto = JsonEntityTransform.json2Object(jsonParam, GroupMemberPageInfoDto.class);
		DataTransferObject dto = callHuanXinImServiceProxy.queryGroupMemberByPage(groupMemberPageInfoDto);
		return dto.toJsonString();
	}


	/**
	 * 
	 * 从 民宿库 分页查询 群成员列表
	 *
	 * @author yd
	 * @created 2017年8月8日 下午3:57:36
	 *
	 * @param jsonParam
	 * @return
	 */
	@Override
	public String queryGroupMemberByPageFromMinsu(String jsonParam) {

		DataTransferObject dto = new DataTransferObject();


		GroupMemberPageInfoDto groupMemberPageInfoDto = JsonEntityTransform.json2Object(jsonParam, GroupMemberPageInfoDto.class);

		if(Check.NuNObj(groupMemberPageInfoDto)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("参数对象不存在");
			return dto.toJsonString();
		}
		PagingResult<GroupMembersVo> page = this.huanxinImManagerServiceImpl.queryGroupMemberByPage(groupMemberPageInfoDto);

		dto.putValue("count", page.getTotal());
		List<GroupMembersVo> list = page.getRows();
		//禁言的用户状态修改  如果已经超过禁言时间 直接转化成正常用户
		if(!Check.NuNCollection(list)){
			Date now = new Date();
			for (GroupMembersVo groupMembersVo : list) {
				if(!Check.NuNObjs(groupMembersVo.getMemberStatu(),groupMembersVo.getRecoveryGagTime())
						&&groupMembersVo.getMemberStatu() == MemberStatuEnum.GAG.getCode()&&groupMembersVo.getRecoveryGagTime().before(now)){
					groupMembersVo.setMemberStatu(MemberStatuEnum.NORMAL.getCode());
				}
			}
		}
		dto.putValue("listGroupMembersVo",list);
		return dto.toJsonString();
	}
	/**
	 * 
	 * 添加群组
	 * 
	 * 1. 调用环信 添加群组 
	 * 2. 环信返回失败,直接返回失败信息
	 * 3. 环信成功返回,添加民宿群组表
	 * 4. 民宿群组添加成功，成功返回
	 * 5. 添加失败或异常，记录到操作失败日志 （定时任务补充）
	 *
	 *
	 * @author yd
	 * @created 2017年8月1日 上午10:25:10
	 *
	 * @param jsonParam
	 * @return
	 */
	@Override
	public String addGroup(String jsonParam) {

		HuanxinGroupDto huanxinGroupDto = JsonEntityTransform.json2Object(jsonParam, HuanxinGroupDto.class);

		DataTransferObject dto = new DataTransferObject();

		if(Check.NuNObj(huanxinGroupDto)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("参数对象不存在");
			return dto.toJsonString();
		}

		if(Check.NuNStr(huanxinGroupDto.getGroupname())){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("请填写群名称");
			return dto.toJsonString();
		}

		if(Check.NuNStr(huanxinGroupDto.getDesc())){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("请填写群描述");
			return dto.toJsonString();
		}
		if(Check.NuNStr(huanxinGroupDto.getOwner())){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("群主未填写");
			return dto.toJsonString();
		}

		if(Check.NuNStr(huanxinGroupDto.getProjectBid())){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("项目不存在");
			return dto.toJsonString();
		}

		if(Check.NuNStr(huanxinGroupDto.getOpFid())
				||Check.NuNObj(huanxinGroupDto.getOpType())){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("操作人不存在");
			return dto.toJsonString();
		}
		huanxinGroupDto.setIsDefault(1);
		HuanxinImGroupEntity defaultGroup = this.huanxinImManagerServiceImpl.queryDefaultGroupByProBid(huanxinGroupDto.getProjectBid());
		if(Check.NuNObj(defaultGroup)){
			huanxinGroupDto.setIsDefault(0);
		}
		dto = callHuanXinImServiceProxy.addGroup(huanxinGroupDto);
		if(dto.getCode() == DataTransferObject.SUCCESS){
			int i  = 0;
			try {
				i = huanxinImManagerServiceImpl.saveHuanxinImGroup(huanxinGroupDto,null);
			} catch (Exception e) {
				i = 0;
				LogUtil.info(logger, "【民宿库添加群组失败】环信已添加成功,这里保存失败记录到民宿库,由定时任务在次添加,e={},groupId={},projectBid={}",huanxinGroupDto.getGroupId(),huanxinGroupDto.getProjectBid(), e);
			}
			if(i == 0){
				try {
					huanxinImManagerServiceImpl.saveHuanxinImGroupOpfailedLog(fillHuanxinImGroupOpfailedLog(huanxinGroupDto,FailedReasonEnum.ADD_GROUP_FAILED.getCode(),huanxinGroupDto.getOwner(),SourceTypeEnum.ZRY_ADD_GROUP_FAILED.getCode()));
				} catch (Exception e) {
					LogUtil.info(logger, "【添加群组失败记录异常】环信已添加成功，此记录需要手动从新添加，参数huanxinGroupDto：{},e={}", JsonEntityTransform.Object2Json(huanxinGroupDto),e);
					dto.setErrCode(DataTransferObject.ERROR);
					dto.setMsg("添加群组失败");
				}
			}

		}
		return dto.toJsonString();
	}




	/**
	 * 
	 * 填充 群组添加失败记录
	 *
	 * @author yd
	 * @created 2017年8月1日 下午2:20:01
	 *
	 * @param huanxinGroupDto
	 * @return
	 */
	private HuanxinImGroupOpfailedLogEntity fillHuanxinImGroupOpfailedLog(HuanxinGroupDto huanxinGroupDto,int failedReason,String uid,int sourceType){

		HuanxinImGroupOpfailedLogEntity huanxinImGroupOpfailedLog = new HuanxinImGroupOpfailedLogEntity();
		huanxinImGroupOpfailedLog.setFid(UUIDGenerator.hexUUID());
		huanxinImGroupOpfailedLog.setUid(uid);
		huanxinImGroupOpfailedLog.setFailedReason(failedReason);
		huanxinImGroupOpfailedLog.setGroupId(huanxinGroupDto.getGroupId());
		huanxinImGroupOpfailedLog.setOpFid(huanxinGroupDto.getOpFid());
		huanxinImGroupOpfailedLog.setOpType(huanxinGroupDto.getOpType());
		huanxinImGroupOpfailedLog.setSourceType(sourceType);
		huanxinImGroupOpfailedLog.setSysStatu(RunStatusEnum.NOT_RUN.getValue());
		huanxinImGroupOpfailedLog.setProjectBid(huanxinGroupDto.getProjectBid());
		return huanxinImGroupOpfailedLog;

	}

	/**
	 * 
	 *  添加群成员(多个或单个)
	 *  1. 如果groupId 存在    保存当前组内
	 *  2. 如果groupId 不存在    项目编号 projectBid 存在 保存到当前项目的默认群中
	 *  3. 如果都不存在 直接返回错误信息
	 *  4. 添加用户到环信群： 环信添加成功  直接添加到群成员表（t_huanxin_im_group_member） ， 添加群成员表成功 直接返回   添加群成员表失败 判断来源 符合入库到操作失败记录表规则 入库t_huanxin_im_group_opfailed_log 否则直接返回
	 *  5.  环信添加失败 直接返回
	 *
	 * @author yd
	 * @created 2017年8月1日 上午10:27:13
	 *
	 * @param jsonParam
	 * @return
	 */
	@Override
	public String addManyGroupMember(String jsonParam) {


		GroupMemberDto groupMemberDto = JsonEntityTransform.json2Object(jsonParam, GroupMemberDto.class);
		DataTransferObject dto = new DataTransferObject();

		if(Check.NuNObj(groupMemberDto)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("参数对象不存在");
			return dto.toJsonString();
		}

		SourceTypeEnum  sourceTypeEnum = SourceTypeEnum.getSourceTypeEnumByCode(groupMemberDto.getSourceType());
		if(Check.NuNObj(sourceTypeEnum)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("请填写来源");
			return dto.toJsonString();
		}

		List<String> members  = groupMemberDto.getMembers();
		if(Check.NuNCollection(members)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("请添加群成员");
			return dto.toJsonString();
		}

		if(Check.NuNStr(groupMemberDto.getOpFid())
				||Check.NuNObj(groupMemberDto.getOpType())){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("操作人信息错误");
			return dto.toJsonString();
		}

		if(Check.NuNStr(groupMemberDto.getGroupId())&&Check.NuNStr(groupMemberDto.getProjectBid())){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("群参数错误");
			return dto.toJsonString();
		}

		String groupName = "";
		String projectName = "";
		if(!Check.NuNStr(groupMemberDto.getGroupId())){
			HuanxinImGroupEntity group = this.huanxinImManagerServiceImpl.queryGroupByGroupId(groupMemberDto.getGroupId());
			if(Check.NuNObj(group)){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("群组不存在");
				return dto.toJsonString();
			}
			groupName = group.getName();
			projectName = fillProjectName(group.getDescription());
		}

		//查询当前项目默认群组
		if(Check.NuNStr(groupMemberDto.getGroupId())&&!Check.NuNStr(groupMemberDto.getProjectBid())){
			HuanxinImGroupEntity defaultGroup = this.huanxinImManagerServiceImpl.queryDefaultGroupByProBid(groupMemberDto.getProjectBid());
			if(Check.NuNObj(defaultGroup)){
				LogUtil.error(logger, "【添加群组失败】无默认群组members={},projectBid={}",JsonEntityTransform.Object2Json(members),groupMemberDto.getProjectBid());
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("当前无默认群组");
				return dto.toJsonString();
			}
			groupMemberDto.setGroupId(defaultGroup.getGroupId());
			groupName = defaultGroup.getName();
			projectName = fillProjectName(defaultGroup.getDescription());
		}

		if(members.size() == 1){
			dto = callHuanXinImServiceProxy.addOneGroupMember(groupMemberDto.getGroupId(), members.get(0),3);
		}
		if(members.size() > 1){
			dto = callHuanXinImServiceProxy.addManyGroupMember(groupMemberDto,3);
		}
		groupMemberDto.setMemberRole(MemberRoleEnum.ORDINARY_MEMBER.getCode());
		if(dto.getCode() == DataTransferObject.SUCCESS){

			//入群 发送群消息
			sendImMsg(groupMemberDto.getGroupId(), members, MemberStatuEnum.NORMAL.getInImMsg());

			//发送推送和短信
			sendJpushOrSmsMsgByCode(sourceTypeEnum.getCode(), MemberStatuEnum.NORMAL.getCode(),members,groupMemberDto.getMobiles(),projectName,groupName);
			try {
				this.huanxinImManagerServiceImpl.saveBathHuanxinImGroupMember(groupMemberDto,null);
			} catch (Exception e) {
				LogUtil.info(logger, "【保存群成员异常】e={},groupMemberDto={}", JsonEntityTransform.Object2Json(groupMemberDto));
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("保存群成员异常");
			}
			if(dto.getCode() == DataTransferObject.ERROR){
				LogUtil.info(logger, "【保存群成员失败】e={},groupMemberDto={},msg={}", JsonEntityTransform.Object2Json(groupMemberDto),dto.getMsg());
				//保存操作失败记录
				if(sourceTypeEnum.getCode() != SourceTypeEnum.ZRY_DEFAULT.getCode()){
					saveBathHuanxinImGroupOpfailedLog(groupMemberDto);
				}
			}
		}

		
		return dto.toJsonString();
	}


	/**
	 * 获取项目名字
	 *
	 * @author yd
	 * @created 2017年8月17日 下午2:57:13
	 *
	 * @param desc
	 * @return
	 */
	private String fillProjectName(String desc){
		String projectName = "自如驿";
		if(!Check.NuNStr(desc)){
			JSONObject obj = JSONObject.parseObject(desc);
			if(obj.containsKey("projectName")){
				projectName = obj.getString("projectName");
			}
		}
		return projectName;
	}

	/**
	 * 
	 * 发送 极光消息 和 短信
	 *
	 * @author yd
	 * @created 2017年8月17日 下午2:26:20
	 *
	 * @param sourceType
	 * @param memberStatu
	 */
	private void  sendJpushOrSmsMsgByCode(int sourceType,int memberStatu,List<String> uids,List<String> phones,String projectName,String groupName){


		LogUtil.info(logger, "【发送极光或短信参数】sourceType={},memberStatu={},uids={},phones={},projectName={},groupName={}", sourceType,memberStatu,uids==null?"":JsonEntityTransform.Object2Json(uids),
				phones==null?"":JsonEntityTransform.Object2Json(phones),projectName,groupName);

		
		if(Check.NuNStr(groupName)){
			LogUtil.error(logger, "【退群发送消息失败】群组名称不存在groupName={}", groupName);
			return ;
		}
		try {
			//入群 发送短信 和 系统消息
			if(memberStatu == MemberStatuEnum.NORMAL.getCode()){
				
				if(sourceType == SourceTypeEnum.ZRY_ZO_ADD_GROUP_MEMBER.getCode()
						||sourceType == SourceTypeEnum.ZRY_ORDER_ADD_GROUP_MEMBER_FAILED.getCode()){
					
					if(Check.NuNStr(projectName)){
						LogUtil.error(logger, "【退群发送消息失败】项目名称不存在projectName={}", projectName);
						return ;
					}

					Map<String,String> pushPar = new HashMap<String, String>();
					Map<String, String> smsPar = new HashMap<String, String>();
					Map<String,String> bizPar = new HashMap<String, String>();

					smsPar.put("{1}",projectName);
					smsPar.put("{2}",groupName);
					if(!Check.NuNCollection(phones)){
						StringBuffer phone = new StringBuffer();
						int i=0;
						for (String ph : phones) {
							phone.append(ph);
							i++;
							if(i<phones.size()){
								phone.append(",");
							}
						}
						smsPar.put("phone", phone.toString());
					}
					pushPar.put("{1}",projectName);
					pushPar.put("{2}",groupName);
					pushPar.put("title","自如驿消息");
					if(!Check.NuNCollection(uids)){
						int i=0;
						StringBuffer uidsStr = new StringBuffer();
						for (String uid : uids) {
							uidsStr.append(uid);
							i++;
							if(i<uids.size()){
								uidsStr.append(",");
							}
						}
						pushPar.put("uids", uidsStr.toString());

					}
					smsAndJpushSendService.send( MessageTemplateCodeEnum.IM_ZRY_CHECK_IN_ADD_GROUP.getCode(), pushPar, smsPar, bizPar);
				}
				//退群 退租退群
				if(sourceType == SourceTypeEnum.ZRY_CHECKOUT_DELETE_GROUP_MEMBER_FAILED.getCode()){

					Map<String,String> pushPar = new HashMap<String, String>();
					Map<String, String> smsPar = new HashMap<String, String>();
					Map<String,String> bizPar = new HashMap<String, String>();
					smsPar.put("{1}",groupName);
					pushPar.put("{1}",groupName);
					pushPar.put("title","自如驿消息");
					if(!Check.NuNCollection(phones)){
						StringBuffer phone = new StringBuffer();
						int i=0;
						for (String ph : phones) {
							phone.append(ph);
							i++;
							if(i<phones.size()){
								phone.append(",");
							}
						}
						smsPar.put("phone", phone.toString());
						
					}
					if(!Check.NuNCollection(uids)){
						int i=0;
						StringBuffer uidsStr = new StringBuffer();
						for (String uid : uids) {
							uidsStr.append(uid);
							i++;
							if(i<phones.size()){
								uidsStr.append(",");
							}
						}
						pushPar.put("uids", uidsStr.toString());
						
					}
					smsAndJpushSendService.send( MessageTemplateCodeEnum.IM_ZRY_CHECK_OUT_EXIT_GROUP.getCode(), pushPar, smsPar, bizPar);
				}
				//退群 1. 用户主动退群   2. 被管理员 移除群
				if(sourceType == SourceTypeEnum.ZRY_TENANT_EXIT_GROUP.getCode()
						||sourceType == SourceTypeEnum.ZRY_DELETE_GROUP_MEMBER_FAILED.getCode()){
					Map<String,String> bizPar = new HashMap<String, String>(); 
					Map<String, String> par = new HashMap<String, String>();
					par.put("{1}",groupName);
					par.put("title","自如驿消息");
					int code = sourceType == SourceTypeEnum.ZRY_TENANT_EXIT_GROUP.getCode()? MessageTemplateCodeEnum.IM_ZRY_USER_EXIT_GROUP.getCode():MessageTemplateCodeEnum.IM_ZRY_ZO_DELETE_GROUP.getCode();
					jpushSendService.send(uids,code, par, bizPar);
				}
			}else{
				Map<String,String> bizPar = new HashMap<String, String>(); 
				Map<String, String> par = new HashMap<String, String>();
				par.put("{1}",groupName);
				par.put("title","自如驿消息");
				Integer template = null;

				//禁言
				if(memberStatu == MemberStatuEnum.GAG.getCode()){
					if(sourceType == 1){
						template = MessageTemplateCodeEnum.IM_ZRY_ZO_ADD_GAG.getCode();
					}
					if(sourceType == 2){
						template = MessageTemplateCodeEnum.IM_ZRY_ZO_DELETE_GAG.getCode();
					}
					
				}

				if(memberStatu == MemberStatuEnum.MANAGER.getCode()){
					//添加管理员
					if(sourceType == 1){
						template = MessageTemplateCodeEnum.IM_ZRY_ZO_ADD_GROUP_MANAGER.getCode();
					}
					//取消管理员
					if(sourceType == 2){
						template = MessageTemplateCodeEnum.IM_ZRY_ZO_DELETE_GROUP_MANAGER.getCode();
					}
				}
				jpushSendService.send(uids, template, par, bizPar);
			}
		} catch (Exception e) {
			LogUtil.error(logger, "【发送极光或短信异常】sourceType={},memberStatu={},uids={},phones={},projectName={},groupName={}，e={}", sourceType,memberStatu,uids==null?"":JsonEntityTransform.Object2Json(uids),
					phones==null?"":JsonEntityTransform.Object2Json(phones),projectName,groupName,e);
		}





	}

	/**
	 * 
	 * 以群组的身份给群发送：群消息 和 系统消息
	 *
	 * @author yd
	 * @created 2017年8月9日 上午11:16:15
	 *
	 * @param groupId
	 * @param toUids   去客户库查询 用户昵称
	 * @param sendImMsgRequest  消息对象
	 * @param smsMsg 短信消息
	 */
	public  void sendImMsg(String groupId,List<String> toUids,String imMsg){

		try {
			if(Check.NuNStr(groupId)
					||Check.NuNCollection(toUids)){
				LogUtil.error(logger, "【IM发送消息】参数错误groupId={},toUids={}", groupId,toUids==null?"":JsonEntityTransform.Object2Json(toUids));
				return ;
			}
			HuanxinImGroupEntity group = this.huanxinImManagerServiceImpl.queryGroupByGroupId(groupId);
			if(Check.NuNObj(group)||Check.NuNStr(imMsg)){
				LogUtil.error(logger, "【IM发送消息】群组不存在groupId={},imMsg={}",groupId,imMsg);
				return ;
			}

			//去客户库查询昵称
			List<CustomerInfoVo> listInfoVo = this.callHuanXinImServiceProxy.queryUserInfoByUids(toUids);
			//封装昵称
			StringBuffer  nickName = new StringBuffer();
			if(!Check.NuNCollection(listInfoVo)){
				int i=0;
				for (CustomerInfoVo customerInfoVo : listInfoVo) {
					nickName.append(customerInfoVo.getNickName());
					i++;
					if(i<listInfoVo.size()){
						nickName.append(" ");
					}
				}
			}

			Map<String, Object> extMap = new HashMap<String, Object>();

			// extMap.put("houseCard", String.valueOf(HouseCardEnum.HOUSE_CARD_GENERAL.getVal()));
			extMap.put("msgType", MsgExtTypeEnum.AUTO_REPLY.getCode() + "");
			// extMap.put("msgSenderType", UserTypeEnum.LANDLORD_HUAXIN.getUserCode() + "");
			// extMap.put("roleType", msgFirstAdvisoryEntity.getRoleType() + "");
			extMap.put("em_ignore_notification", true);
			extMap.put("ziroomFlag", IM_ZRY_FLAG);
			extMap.put("domainFlag", IM_DOMAIN_FLAG);
			//消息内容
			String msg = imMsg.replace("#{nickName}", nickName.toString());
			String[] target = new String[]{groupId};
			SendImMsgRequest sendImMsgRequest = new SendImMsgRequest();
			sendImMsgRequest.setFrom(group.getOwner());
			sendImMsgRequest.setTarget(target);
			sendImMsgRequest.setTarget_type(TargetTypeEnum.ALL_LANDLORD.getHuanxinTargetType());
			sendImMsgRequest.setExtMap(extMap);
			sendImMsgRequest.setMsg(msg);

			//发送群消息
			this.callHuanXinImServiceProxy.sendImMsg(sendImMsgRequest);

		} catch (Exception e) {
			LogUtil.error(logger, "【IM发送消息】异常groupId={},toUids={},e={}", groupId,toUids==null?"":JsonEntityTransform.Object2Json(toUids),e);
		}



	}

	/**
	 * 
	 * 添加群成员失败 保存
	 *
	 * @author yd
	 * @created 2017年8月3日 下午6:43:28
	 *
	 * @param groupMemberDto
	 */
	private void saveBathHuanxinImGroupOpfailedLog(GroupMemberDto groupMemberDto){

		if(!Check.NuNObj(groupMemberDto)){
			List<String> members  = groupMemberDto.getMembers();
			List<HuanxinImGroupOpfailedLogEntity> list = new ArrayList<HuanxinImGroupOpfailedLogEntity>();
			for (String member : members) {
				HuanxinImGroupOpfailedLogEntity huanxinImGroupOpfailedLog = new HuanxinImGroupOpfailedLogEntity();
				huanxinImGroupOpfailedLog.setFid(UUIDGenerator.hexUUID());
				huanxinImGroupOpfailedLog.setUid(member);
				huanxinImGroupOpfailedLog.setFailedReason(FailedReasonEnum.ADD_GROUP_MEMBER_FAILED.getCode());
				huanxinImGroupOpfailedLog.setGroupId(groupMemberDto.getGroupId());
				huanxinImGroupOpfailedLog.setOpFid(groupMemberDto.getOpFid());
				huanxinImGroupOpfailedLog.setOpType(groupMemberDto.getOpType());
				huanxinImGroupOpfailedLog.setSourceType(groupMemberDto.getSourceType());
				huanxinImGroupOpfailedLog.setSysStatu(RunStatusEnum.NOT_RUN.getValue());
				list.add(huanxinImGroupOpfailedLog);
			}
			this.huanxinImManagerServiceImpl.saveBathHuanxinImGroupOpfailedLog(list);
		}
	}
	/**
	 * 
	 * 分页查询 app下所有的群组
	 * 说明： 群组从我们自己库 通过项目id 来拉取
	 *
	 * @author yd
	 * @created 2017年8月1日 上午10:28:03
	 *
	 * @param jsonParam
	 * @return
	 */
	@Override
	public String queryAppGroupsByPage(String jsonParam) {
		GroupDto groupDto = JsonEntityTransform.json2Object(jsonParam, GroupDto.class);

		DataTransferObject dto = new DataTransferObject();
		if(Check.NuNObj(groupDto)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("参数对象不存在");
			return dto.toJsonString();
		}
		PagingResult<GroupVo>  page  = huanxinImManagerServiceImpl.queryGroupByPage(groupDto);

		dto.putValue("count", page.getTotal());
		dto.putValue("listGroupVo", page.getRows());
		return dto.toJsonString();
	}


	/**
	 * 
	 * 添加禁言
	 * 
	 * 1. 环信 添加禁言失败  返回失败原因
	 * 2. 环信 添加禁言成功  修改民宿库 对应禁言人员数据
	 *
	 * @author yd
	 * @created 2017年8月1日 上午10:31:57
	 *
	 * @param jsonParam
	 * @return
	 */
	@Override
	public String addGagMember(String jsonParam) {

		DataTransferObject dto =new DataTransferObject();
		GagMemberDto gagMemberDto = JsonEntityTransform.json2Object(jsonParam, GagMemberDto.class);

		if(Check.NuNObj(gagMemberDto)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("参数不存在");
			return dto.toJsonString();
		}

		if(Check.NuNStr(gagMemberDto.getOpFid())
				||Check.NuNObj(gagMemberDto.getOpType())){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("操作人信息错误");
			return dto.toJsonString();
		}

		dto = this.callHuanXinImServiceProxy.addGagMember(gagMemberDto);
		if(dto.getCode() == DataTransferObject.SUCCESS){

			HuanxinImGroupEntity group = this.huanxinImManagerServiceImpl.queryGroupByGroupId(gagMemberDto.getGroupId());
			String groupName = group == null?"自如驿":group.getName();
			try {
				//禁言 发送消息
				sendImMsg(gagMemberDto.getGroupId(), gagMemberDto.getMembers(), MemberStatuEnum.GAG.getInImMsg());
				//发送推送和短信
				sendJpushOrSmsMsgByCode(1, MemberStatuEnum.GAG.getCode(),gagMemberDto.getMembers(),null,null,groupName);
				this.huanxinImManagerServiceImpl.updateHuanxinImGroupMemberByCon(gagMemberDto);
			} catch (Exception e) {
				LogUtil.info(logger, "【禁言失败】环信成功：gagMemberDto={}，e={}", JsonEntityTransform.Object2Json(gagMemberDto),e);
			}

		}
		return dto.toJsonString();
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
	@Override
	public String saveMsgHuanxinImLog(String jsonParam){

		DataTransferObject dto = new DataTransferObject();
		MsgHuanxinImLogEntity msgHuanxinImLog = JsonEntityTransform.json2Object(jsonParam, MsgHuanxinImLogEntity.class);

		if(Check.NuNObj(msgHuanxinImLog)||Check.NuNStr(msgHuanxinImLog.getFromUid())
				||Check.NuNStr(msgHuanxinImLog.getToUid())||Check.NuNStr(msgHuanxinImLog.getChatType())
				||Check.NuNStr(msgHuanxinImLog.getType())||Check.NuNStr(msgHuanxinImLog.getZiroomFlag())){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("参数错误");
			LogUtil.info(logger, "【保存环信消息激励】msgHuanxinImLog={}", jsonParam);
			return dto.toJsonString();
		}
		huanxinImManagerServiceImpl.saveMsgHuanxinImLog(msgHuanxinImLog);
		return dto.toJsonString();
	}

	/**
	 * 
	 * 解除禁言
	 *
	 * @author yd
	 * @created 2017年8月3日 下午7:18:51
	 *
	 * @return
	 */
	@Override
	public String removeGagMember(String jsonParam) {

		DataTransferObject dto =new DataTransferObject();
		GagMemberDto gagMemberDto = JsonEntityTransform.json2Object(jsonParam, GagMemberDto.class);

		if(Check.NuNObj(gagMemberDto)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("参数不存在");
			return dto.toJsonString();
		}
		if(Check.NuNStr(gagMemberDto.getOpFid())
				||Check.NuNObj(gagMemberDto.getOpType())){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("操作人信息错误");
			return dto.toJsonString();
		}

		dto = this.callHuanXinImServiceProxy.removeGagMember(gagMemberDto);

		if(dto.getCode() == DataTransferObject.SUCCESS){
			try {

				HuanxinImGroupEntity group = this.huanxinImManagerServiceImpl.queryGroupByGroupId(gagMemberDto.getGroupId());
				String groupName = group == null?"自如驿":group.getName();
				
				//禁言 发送消息
				sendImMsg(gagMemberDto.getGroupId(), gagMemberDto.getMembers(), MemberStatuEnum.GAG.getOutImMsg());
				gagMemberDto.setRecoveryGagTime(new Date());
				gagMemberDto.setMemberStatu(MemberStatuEnum.NORMAL.getCode());
				
				//发送推送和短信
				sendJpushOrSmsMsgByCode(2, MemberStatuEnum.GAG.getCode(),gagMemberDto.getMembers(),null,null,groupName);
				
				
				this.huanxinImManagerServiceImpl.updateHuanxinImGroupMemberByCon(gagMemberDto);
			} catch (Exception e) {
				LogUtil.info(logger, "【解除禁言失败】环信成功：gagMemberDto={}，e={}", JsonEntityTransform.Object2Json(gagMemberDto),e);
			}

		}
		return dto.toJsonString();
	}

	/**
	 * 
	 * 添加群管理员
	 * 
	 * 1. 添加环信管理员失败  直接返回
	 * 2. 添加成功  添加到 民宿库 (如果失败 记录日志 需要手动去同步  暂不支持)
	 *
	 * @author yd
	 * @created 2017年8月4日 上午10:43:28
	 *
	 * @param managerMerberDto
	 * @return
	 */
	@Override
	public String addAdminMember(String jsonParam) {
		DataTransferObject dto = new DataTransferObject();
		ManagerMerberDto managerMerberDto = JsonEntityTransform.json2Object(jsonParam, ManagerMerberDto.class);

		if(Check.NuNObj(managerMerberDto)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("参数不存在");
			return dto.toJsonString();
		}
		if(Check.NuNStr(managerMerberDto.getOpFid())
				||Check.NuNObj(managerMerberDto.getOpType())){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("操作人信息错误");
			return dto.toJsonString();
		}
		dto = this.callHuanXinImServiceProxy.addAdminMember(managerMerberDto);
		if(dto.getCode() == DataTransferObject.SUCCESS){
			try {

				HuanxinImGroupEntity group = this.huanxinImManagerServiceImpl.queryGroupByGroupId(managerMerberDto.getGroupId());
				String groupName = group == null?"自如驿":group.getName();

				// 发送消息
				List<String> members = new ArrayList<String>();
				members.add(managerMerberDto.getAdminUid());
				sendImMsg(managerMerberDto.getGroupId(),members, MemberStatuEnum.MANAGER.getInImMsg());
				managerMerberDto.setMemberRole(MemberRoleEnum.ADMIN_MEMBER.getCode());

				//发送推送和短信
				sendJpushOrSmsMsgByCode(1, MemberStatuEnum.MANAGER.getCode(),members,null,null,groupName);

				this.huanxinImManagerServiceImpl.updateGroupMemberFromAdmin(managerMerberDto);
			} catch (Exception e) {
				LogUtil.error(logger, "【添加管理员失败】环信成功：managerMerberDto={}，e={}", JsonEntityTransform.Object2Json(managerMerberDto),e);
			}

		}
		return dto.toJsonString();
	}
	/**
	 * 
	 * 删除群管理员
	 *
	 * @author yd
	 * @created 2017年8月4日 上午10:43:28
	 *
	 * @param managerMerberDto
	 * @return
	 */
	@Override
	public String deleteAdminMember(String jsonParam) {
		DataTransferObject dto = new DataTransferObject();
		ManagerMerberDto managerMerberDto = JsonEntityTransform.json2Object(jsonParam, ManagerMerberDto.class);

		if(Check.NuNObj(managerMerberDto)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("参数不存在");
			return dto.toJsonString();
		}
		if(Check.NuNStr(managerMerberDto.getOpFid())
				||Check.NuNObj(managerMerberDto.getOpType())){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("操作人信息错误");
			return dto.toJsonString();
		}
		dto = this.callHuanXinImServiceProxy.deleteAdminMember(managerMerberDto);

		if(dto.getCode() == DataTransferObject.SUCCESS){
			try {

				HuanxinImGroupEntity group = this.huanxinImManagerServiceImpl.queryGroupByGroupId(managerMerberDto.getGroupId());
				String groupName = group == null?"自如驿":group.getName();


				// 发送消息
				List<String> members = new ArrayList<String>();
				members.add(managerMerberDto.getAdminUid());
				sendImMsg(managerMerberDto.getGroupId(),members, MemberStatuEnum.MANAGER.getOutImMsg());
				managerMerberDto.setMemberRole(MemberRoleEnum.ORDINARY_MEMBER.getCode());

				//发送推送和短信
				sendJpushOrSmsMsgByCode(2, MemberStatuEnum.MANAGER.getCode(),members,null,null,groupName);
				this.huanxinImManagerServiceImpl.updateGroupMemberFromAdmin(managerMerberDto);
			} catch (Exception e) {
				LogUtil.info(logger, "【删除管理员失败】环信成功：managerMerberDto={}，e={}", JsonEntityTransform.Object2Json(managerMerberDto),e);
			}

		}
		return dto.toJsonString();
	}

	/**
	 * 
	 * 移除群成员   
	 * (如果 无群组id 如果来源 自如驿退房等 退出用户该项目下所有群)
	 * 
	 * 1. 环信移除失败  直接返回
	 * 2. 环信移除成功  移除民宿库  
	 * 3. 民宿库移除成功 直接返回
	 * 4. 民宿库移除失败  ： 判断来源 看是否需要记录操作 处理
	 *
	 * @author yd
	 * @created 2017年8月4日 下午3:43:32
	 *
	 * @param jsonParam
	 * @return
	 */
	@Override
	public String removeGroupMembers(String jsonParam) {

		DataTransferObject dto = new DataTransferObject();
		GroupMemberDto groupMemberDto = JsonEntityTransform.json2Object(jsonParam, GroupMemberDto.class);

		if(Check.NuNObj(groupMemberDto)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("参数不存在");
			return dto.toJsonString();
		}

		List<String> members  = groupMemberDto.getMembers();
		if(Check.NuNCollection(members)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("请选择待删除群成员");
			return dto.toJsonString();
		}

		if(Check.NuNStr(groupMemberDto.getOpFid())
				||Check.NuNObj(groupMemberDto.getOpType())){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("操作人信息不存在");
			return dto.toJsonString();
		}

		if(Check.NuNObj(groupMemberDto.getSourceType())){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("操作来源不存在");
			return dto.toJsonString();
		}
		if(Check.NuNStr(groupMemberDto.getGroupId())&&Check.NuNStr(groupMemberDto.getProjectBid())){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("群组不存在");
			return dto.toJsonString();
		}

		int sourceType = groupMemberDto.getSourceType();
		if(!Check.NuNStr(groupMemberDto.getGroupId())){

			dto =  exitGroup(groupMemberDto, dto);
			//发送 推送 和短信
			if(dto.getCode() == DataTransferObject.SUCCESS){
				//发送推送和短信
				sendJpushOrSmsMsgByCode(groupMemberDto.getSourceType(), MemberStatuEnum.NORMAL.getCode(),groupMemberDto.getMembers(),groupMemberDto.getMobiles(),null,groupMemberDto.getGroupName());
			}
			return dto.toJsonString();
		}
		//如果 来源为 自如驿退房 则退出全部群  支持多人退群
		if(Check.NuNStr(groupMemberDto.getGroupId())&&!Check.NuNStr(groupMemberDto.getProjectBid())&&(sourceType== SourceTypeEnum.ZRY_ORDER_ADD_GROUP_MEMBER_FAILED.getCode()
				||sourceType== SourceTypeEnum.ZRY_CHECKOUT_DELETE_GROUP_MEMBER_FAILED.getCode()
				||sourceType== SourceTypeEnum.ZRY_REFUND_DELETE_GROUP_MEMBER_FAILED.getCode())){
			List<String> curMembers = new ArrayList<String>();
			for (String member : members) {
				curMembers.clear();
				curMembers.add(member);
				groupMemberDto.setMembers(curMembers);
				List<String> groupIds =   this.huanxinImManagerServiceImpl.queryGroupIdsByMember(member, groupMemberDto.getProjectBid());
				if(Check.NuNCollection(groupIds)){
					LogUtil.info(logger, "【当前用户还未添加群】：member={},projectBid",member,groupMemberDto.getProjectBid());
					continue;
				}
				for (String groupId : groupIds) {
					groupMemberDto.setGroupId(groupId);
					dto = exitGroup(groupMemberDto, dto);
					//发送 推送 和短信
					if(dto.getCode() == DataTransferObject.SUCCESS){
						
						Map<String, String> uidMobileMap = groupMemberDto.getUidMobileMap();
						List<String> phones = new ArrayList<String>();
						phones.add(uidMobileMap.get(member));
						//发送推送和短信
						sendJpushOrSmsMsgByCode(groupMemberDto.getSourceType(), MemberStatuEnum.NORMAL.getCode(),groupMemberDto.getMembers(),phones,null,groupMemberDto.getGroupName());
					}
				}
			}

		}else{
			LogUtil.error(logger, "【移除群成员失败】环信成功：groupMemberDto={}", JsonEntityTransform.Object2Json(groupMemberDto));
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("参数错误");
		}

		return dto.toJsonString();
	}

	/**
	 * 
	 * 退群
	 *  说明： 如果存在禁言 则先解禁
	 * @author yd
	 * @created 2017年8月10日 下午5:14:16
	 *
	 * @param groupMemberDto
	 * @param dto
	 */
	private DataTransferObject exitGroup(GroupMemberDto groupMemberDto,DataTransferObject dto){


		List<String> uids = groupMemberDto.getMembers();
		
		
		//解禁禁言人员
		for (String uid : uids) {
			HuanxinImGroupMemberEntity mem = 	huanxinImManagerServiceImpl.queryGagMemberByUid(uid);
			if(!Check.NuNObj(mem)){
				GagMemberDto gagMemberDto = new GagMemberDto();
				gagMemberDto.setGroupId(mem.getGroupId());
				List<String> memList = new ArrayList<String>();
				memList.add(uid);
				gagMemberDto.setMembers(memList);
				try {
					this.callHuanXinImServiceProxy.removeGagMember(gagMemberDto);
				} catch (Exception e) {
					LogUtil.error(logger, "【退群解禁异常】gagMemberDto={},e={}", JsonEntityTransform.Object2Json(gagMemberDto),e);
				}
			
			}
		}
		
		if(groupMemberDto.getMembers().size() == 1){
			dto = this.callHuanXinImServiceProxy.removeOneGroupMember(groupMemberDto.getGroupId(), groupMemberDto.getMembers().get(0));
		}
		if(groupMemberDto.getMembers().size() > 1){
			dto = this.callHuanXinImServiceProxy.removeManyGroupMember(groupMemberDto);
		}

		if(dto.getCode() == DataTransferObject.SUCCESS){

			HuanxinImGroupEntity group = this.huanxinImManagerServiceImpl.queryGroupByGroupId(groupMemberDto.getGroupId());
			String name = "自如驿";
			if(!Check.NuNObj(group)){
				name = group.getName();
				groupMemberDto.setGroupName(name);
			}

			String msg = MemberStatuEnum.NORMAL.getOutImMsg();
			if(groupMemberDto.getSourceType()!= SourceTypeEnum.ZRY_DELETE_GROUP_MEMBER_FAILED.getCode()){
				msg = MemberStatuEnum.NORMAL.getOutImMsgExt();
			}
			//sendImMsg(groupMemberDto.getGroupId(),groupMemberDto.getMembers(),msg);

			try {
				this.huanxinImManagerServiceImpl.updateGroupMemberFromRemMember(groupMemberDto,null);

			} catch (Exception e) {
				LogUtil.error(logger, "【移除群成员失败】环信成功：groupMemberDto={}，e={}", JsonEntityTransform.Object2Json(groupMemberDto),e);
				if(groupMemberDto.getSourceType()!= SourceTypeEnum.ZRY_DEFAULT.getCode()){
					huanxinImManagerServiceImpl.saveGroupOpfailedLogFromRmMem(groupMemberDto);
				}

			}
		}

		return dto;
	}
	/**
	 * 
	 * 根据群组id 删除群组
	 * 1. 删除环信失败 返回
	 * 2. 删除环信成功 民宿库群组删除  项目与群组 关系删除
	 * 3. 民宿库删除失败 递归删除三次  再次失败 做删除失败记录
	 *
	 * @author yd
	 * @created 2017年8月4日 下午3:44:38
	 *
	 * @param jsonParam
	 * @return
	 */
	@Override
	public String removeGroupByGroupId(String jsonParam) {

		DataTransferObject dto = new DataTransferObject();


		GroupBaseDto groupBaseDto = JsonEntityTransform.json2Object(jsonParam, GroupBaseDto.class);

		if(Check.NuNObj(groupBaseDto)){

			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("参数不存在");
			return dto.toJsonString();
		}

		if(Check.NuNStr(groupBaseDto.getGroupId())){

			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("请选择群组");
			return dto.toJsonString();
		}


		if(Check.NuNStr(groupBaseDto.getOpBid())
				||Check.NuNObj(groupBaseDto.getOpType())){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("操作人不存在");
			return dto.toJsonString();
		}

		dto = this.callHuanXinImServiceProxy.removeGroupByGroupId(groupBaseDto.getGroupId());

		if(dto.getCode() == DataTransferObject.SUCCESS){
			try {
				this.huanxinImManagerServiceImpl.removeGroup(dto, groupBaseDto,null);
			} catch (Exception e) {
				LogUtil.info(logger, "【移除群失败】环信成功：groupMemberDto={}，e={}", JsonEntityTransform.Object2Json(groupBaseDto),e);
				huanxinImManagerServiceImpl.saveGroupOpfailedLogFromRmGroup(groupBaseDto);
			}
		}
		return dto.toJsonString();
	}

	/**
	 * 
	 * 更新群组信息
	 * 说明： 失败后 不做记录 给出日志
	 * @author yd
	 * @created 2017年8月4日 下午3:45:25
	 *
	 * @param jsonParam
	 * @return
	 */
	@Override
	public String updateGroupByGroupId(String jsonParam) {


		DataTransferObject dto = new DataTransferObject();

		GroupInfoDto groupInfoDto = JsonEntityTransform.json2Object(jsonParam, GroupInfoDto.class);

		if(Check.NuNObj(groupInfoDto)){

			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("参数不存在");
			return dto.toJsonString();
		}

		if(Check.NuNStr(groupInfoDto.getGroupId())){

			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("请选择群组");
			return dto.toJsonString();
		}


		if(Check.NuNStr(groupInfoDto.getOpBid())
				||Check.NuNObj(groupInfoDto.getOpType())){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("操作人不存在");
			return dto.toJsonString();
		}

		dto = this.callHuanXinImServiceProxy.updateGroupByGroupId(groupInfoDto);
		if(dto.getCode() == DataTransferObject.SUCCESS){
			HuanxinImGroupEntity huanxinImGroup = new HuanxinImGroupEntity();
			huanxinImGroup.setGroupId(groupInfoDto.getGroupId());
			huanxinImGroup.setDescription(groupInfoDto.getDescription());
			huanxinImGroup.setName(groupInfoDto.getGroupname());
			try {
				this.huanxinImManagerServiceImpl.updateHuanxinImGroup(huanxinImGroup,groupInfoDto.getIsDefault());
			} catch (Exception e) {
				LogUtil.error(logger, "【更新群组失败】环信成功：groupInfoDto={}，e={}", JsonEntityTransform.Object2Json(groupInfoDto),e);
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("更新群组失败");
			}
		}
		return dto.toJsonString();
	}


	/**
	 * 
	 * 针对 环信成功 民宿失败的业务处理
	 *  1=添加群组失败 （uid为群主的uid）2=添加群成员 3=删除群成员 4=删除群组失败  针对这4中情况进行 处理  其中 1 需要去环信 获取群组详情
	 * @author yd
	 * @created 2017年8月9日 下午5:46:25
	 *
	 * @param jsonParam
	 * @return
	 */
	@Override
	public void dealGroupOpfailed() {
		Thread th = new Thread(new Runnable() {
			@Override
			public void run() {

				GroupOpfailedLogDto groupOpfailedLogDto = new GroupOpfailedLogDto();
				groupOpfailedLogDto.setLimit(100);
				DataTransferObject dto = new DataTransferObject();

				int pageNum = 1;
				do {
					groupOpfailedLogDto.setPage(pageNum);
					PagingResult<HuanxinImGroupOpfailedLogEntity> page = huanxinImManagerServiceImpl.queryGroupOpfailedByPage(groupOpfailedLogDto);
					List<HuanxinImGroupOpfailedLogEntity> list = page.getRows();
					if(!Check.NuNCollection(list)){
						List<String> groupIds = new ArrayList<String>();
						Map<String, HuanxinImGroupOpfailedLogEntity> projectMap = new HashMap<String, HuanxinImGroupOpfailedLogEntity>();
						for (HuanxinImGroupOpfailedLogEntity huanxinImGroupOpfailedLog : list) {

							int failedReason = huanxinImGroupOpfailedLog.getFailedReason();
							//添加群成员 失败
							if(failedReason == FailedReasonEnum.ADD_GROUP_MEMBER_FAILED.getCode()){
								GroupMemberDto groupMemberDto = new GroupMemberDto();
								try {
									groupMemberDto.setGroupId(huanxinImGroupOpfailedLog.getGroupId());
									groupMemberDto.setMemberRole(MemberRoleEnum.ORDINARY_MEMBER.getCode());
									groupMemberDto.setMemberStatu(MemberStatuEnum.NORMAL.getCode());
									groupMemberDto.setOpFid(huanxinImGroupOpfailedLog.getOpFid());
									groupMemberDto.setOpType(huanxinImGroupOpfailedLog.getOpType());
									List<String> uids = new ArrayList<String>();
									uids.add(huanxinImGroupOpfailedLog.getUid());
									groupMemberDto.setMembers(uids);
									huanxinImGroupOpfailedLog.setSysStatu(1);
									huanxinImManagerServiceImpl.saveBathHuanxinImGroupMember(groupMemberDto,huanxinImGroupOpfailedLog);
								} catch (Exception e) {
									LogUtil.error(logger, "【定时任务-保存群成员异常】e={},groupMemberDto={}", JsonEntityTransform.Object2Json(groupMemberDto));
								}
							}

							//删除群成员失败
							if(failedReason == FailedReasonEnum.DELETE_GROUP_MEMBER_FAILED.getCode()){
								GroupMemberDto groupMemberDto = new GroupMemberDto();
								try {
									groupMemberDto.setGroupId(huanxinImGroupOpfailedLog.getGroupId());
									groupMemberDto.setOpFid(huanxinImGroupOpfailedLog.getOpFid());
									groupMemberDto.setOpType(huanxinImGroupOpfailedLog.getOpType());
									List<String> uids = new ArrayList<String>();
									uids.add(huanxinImGroupOpfailedLog.getUid());
									groupMemberDto.setMembers(uids);
									huanxinImGroupOpfailedLog.setSysStatu(1);
									huanxinImManagerServiceImpl.updateGroupMemberFromRemMember(groupMemberDto,huanxinImGroupOpfailedLog);
								} catch (Exception e) {
									LogUtil.error(logger, "【定时任务-移除群成员失败】环信成功：groupMemberDto={}，e={}", JsonEntityTransform.Object2Json(groupMemberDto),e);
								}

							}

							//删除群组失败
							if(failedReason == FailedReasonEnum.DELETE_GROUP_FAILED.getCode()){
								GroupBaseDto groupBaseDto = new GroupBaseDto();
								try {
									groupBaseDto.setGroupId(huanxinImGroupOpfailedLog.getGroupId());
									groupBaseDto.setOpBid(huanxinImGroupOpfailedLog.getOpFid());
									groupBaseDto.setOpType(huanxinImGroupOpfailedLog.getOpType());
									groupBaseDto.setOwner(huanxinImGroupOpfailedLog.getUid());
									huanxinImGroupOpfailedLog.setSysStatu(1);
									huanxinImManagerServiceImpl.removeGroup(dto, groupBaseDto,huanxinImGroupOpfailedLog);
								} catch (Exception e) {
									LogUtil.error(logger, "【定时任务-移除群失败】环信成功：groupMemberDto={}，e={}", JsonEntityTransform.Object2Json(groupBaseDto),e);
								}
							}

							//添加群组失败  环信从新获取
							if(failedReason == FailedReasonEnum.ADD_GROUP_FAILED.getCode()){
								if(!Check.NuNStr(huanxinImGroupOpfailedLog.getProjectBid())){
									groupIds.add(huanxinImGroupOpfailedLog.getGroupId());
									projectMap.put(huanxinImGroupOpfailedLog.getGroupId(),huanxinImGroupOpfailedLog);
								}
							}
						}
						//重新创建群组
						if(!Check.NuNCollection(groupIds)){
							dto = callHuanXinImServiceProxy.queryGroupInfo(groupIds);
							if(dto.getCode() == DataTransferObject.SUCCESS){
								List<HuanxinGroupDto> listHuanxinGroupDto = dto.parseData("listHuanxinGroupDto", new TypeReference<List<HuanxinGroupDto>>() {
								});
								if(!Check.NuNCollection(listHuanxinGroupDto)){
									for (HuanxinGroupDto huanxinGroupDto : listHuanxinGroupDto) {
										huanxinGroupDto.setProjectBid(projectMap.get(huanxinGroupDto.getGroupId()).getProjectBid());
										try {
											HuanxinImGroupOpfailedLogEntity huanxinImGroupOpfailedLog  = projectMap.get(huanxinGroupDto.getGroupId());
											if(!Check.NuNObj(huanxinImGroupOpfailedLog)){
												huanxinImGroupOpfailedLog.setSysStatu(1);
											}
											huanxinImManagerServiceImpl.saveHuanxinImGroup(huanxinGroupDto,huanxinImGroupOpfailedLog);
										} catch (Exception e) {
											LogUtil.error(logger, "【定时任务-民宿库添加群组失败】环信已添加成功,e={},groupId={},projectBid={}",huanxinGroupDto.getGroupId(),huanxinGroupDto.getProjectBid(), e);
										}
									}
								}

							}
						}
						pageNum ++;
					}else{
						pageNum = 0;
					}

				} while (pageNum>1);

			}
		});
		th.start();
	}

	/**
	 * 保存或者修改聊天用户关系
	 *
	 * @author loushuai
	 * @created 2017年9月4日 下午3:26:43
	 *
	 * @param object2Json
	 * @return 
	 */
	@Override
	public String saveOrUpdateMsgUserRel(String jsonParam){
        LogUtil.info(logger, "saveOrUpdateMsgUserRel jsonParam={}", jsonParam);
		DataTransferObject dto = new DataTransferObject();
		MsgUserRelEntity msgUserRel = JsonEntityTransform.json2Object(jsonParam, MsgUserRelEntity.class);

		if(Check.NuNObj(msgUserRel)||Check.NuNStr(msgUserRel.getToUid())
				||Check.NuNStr(msgUserRel.getFromUid())
				||Check.NuNObj(msgUserRel.getSourceType())
				||Check.NuNStr(msgUserRel.getZiroomFlag())
				|| (YesOrNoEnum.YES.getCode()==msgUserRel.getSourceType() && Check.NuNStr(msgUserRel.getRemark()))){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("不能为空的参数出现了空值");
			LogUtil.info(logger, "【保存屏蔽关系】msgUserRel={}", jsonParam);
			return dto.toJsonString();
		}
		MsgUserRelEntity queryMsgUserRel = new MsgUserRelEntity();
		queryMsgUserRel.setFromUid(msgUserRel.getFromUid());
		queryMsgUserRel.setToUid(msgUserRel.getToUid());
		queryMsgUserRel.setZiroomFlag(msgUserRel.getZiroomFlag());
		MsgUserRelEntity dataMsgUserRel = huanxinImManagerServiceImpl.getMsgUserRelByParam(queryMsgUserRel);
		//取消屏蔽
		if(msgUserRel.getSourceType()==MsgUserRelSourceTypeEnum.CANCELSHIELD.getCode()){
			if(Check.NuNObj(dataMsgUserRel)){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("没有屏蔽过对方，无需取消");
				return dto.toJsonString();
			}
			huanxinImManagerServiceImpl.cancelShield(dataMsgUserRel);
			return dto.toJsonString();
		}
		//屏蔽或者取消屏蔽
		if(!Check.NuNObj(dataMsgUserRel)){
			msgUserRel.setFid(dataMsgUserRel.getFid());
			huanxinImManagerServiceImpl.upMsgUserRel(msgUserRel);
			return dto.toJsonString();
		}
		msgUserRel.setRelStatus(YesOrNoEnum.NO.getCode());
		huanxinImManagerServiceImpl.saveMsgUserRel(msgUserRel);
		return dto.toJsonString();
	}
	
	/**
	 * 查询聊天用户关系
	 *
	 * @author loushuai
	 * @created 2017年9月4日 下午3:26:43
	 *
	 * @param object2Json
	 * @return 
	 */
	@Override
	public String queryMsgUserRel(String jsonParam){
        LogUtil.info(logger, "queryMsgUserRel方法参数   jsonParam={} ", jsonParam);
		DataTransferObject dto = new DataTransferObject();
		MsgUserRelEntity msgUserRel = JsonEntityTransform.json2Object(jsonParam, MsgUserRelEntity.class);

		if(Check.NuNObj(msgUserRel)||Check.NuNStr(msgUserRel.getToUid())
				||Check.NuNStr(msgUserRel.getFromUid())
				||Check.NuNStr(msgUserRel.getZiroomFlag())){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("参数错误");
			LogUtil.info(logger, "【保存屏蔽关系】msgUserRel={}", jsonParam);
			return dto.toJsonString();
		}
	    MsgUserRelEntity fromMsgUserRel = huanxinImManagerServiceImpl.queryMsgUserRel(msgUserRel);
	    //查询
	    MsgUserRelEntity param = new  MsgUserRelEntity();
	    param.setFromUid(msgUserRel.getToUid());
	    param.setToUid(msgUserRel.getFromUid());
	    param.setZiroomFlag(msgUserRel.getZiroomFlag());
	    MsgUserRelEntity toMsgUserRel = huanxinImManagerServiceImpl.queryMsgUserRel(param);
	    if(!Check.NuNObj(fromMsgUserRel) && Check.NuNObj(toMsgUserRel)){
	    	//屏蔽了对方
	    	dto.putValue("shieldFlag", 1);
	    	return dto.toJsonString();
	    }
	    if(Check.NuNObj(fromMsgUserRel) && !Check.NuNObj(toMsgUserRel)){
	    	//对方屏蔽了你
	    	dto.putValue("shieldFlag", 2);
	    	return dto.toJsonString();
	    }
	    if(!Check.NuNObj(fromMsgUserRel) && !Check.NuNObj(toMsgUserRel)){
	    	//互相屏蔽
	    	dto.putValue("shieldFlag", 3);
	    	return dto.toJsonString();
	    }
	    //默认是双方都没有屏蔽
	    dto.putValue("shieldFlag", 0);
		return dto.toJsonString();
	}



	/**
	 * 
	 * 转让群组
	 * 1. 校验当前人员 是否在群里
	 * 2. 环信转让失败直接返回
	 * 3. 环信转让成功  更新民宿库a. 更新数据库老群主未普通成员   b.更新当前uid为新群主
	 * 4. 
	 * @author yd
	 * @created 2017年9月6日 下午4:43:39
	 *
	 * @param jsonParam
	 * @return
	 */
	@Override
	public String transferGroup(String jsonParam) {
		
		DataTransferObject dto = new DataTransferObject();
		GroupBaseDto  groupBaseDto = JsonEntityTransform.json2Object(jsonParam, GroupBaseDto.class);
		
		if(Check.NuNObj(groupBaseDto)){

			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("参数不存在");
			return dto.toJsonString();
		}

		if(Check.NuNStr(groupBaseDto.getGroupId())||Check.NuNStr(groupBaseDto.getOwner())){

			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("群号或新群组号错误");
			return dto.toJsonString();
		}

		if(Check.NuNStr(groupBaseDto.getOpBid())
				||Check.NuNObj(groupBaseDto.getOpType())){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("操作人不存在");
			return dto.toJsonString();
		}
		
		HuanxinImGroupMemberEntity newMemer= this.huanxinImManagerServiceImpl.queryMeberByGroupAndMember(groupBaseDto.getGroupId(), groupBaseDto.getOwner(), null);
		if(Check.NuNObj(newMemer)){
			
			LogUtil.info(logger, "【新群主未入群】groupId={}，owner={}", groupBaseDto.getGroupId(),groupBaseDto.getOwner());
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("新群主还未入群,请先添加");
			return dto.toJsonString();
		}
		
		HuanxinImGroupMemberEntity owner= this.huanxinImManagerServiceImpl.queryMeberByGroupAndMember(groupBaseDto.getGroupId(), null, MemberRoleEnum.OWNER_MEMBER.getCode());
		
		if(Check.NuNObj(owner)){
			LogUtil.info(logger, "【群主不存在】groupId={}", groupBaseDto.getGroupId());
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("群主不存在");
			return dto.toJsonString();
		}
		dto = callHuanXinImServiceProxy.transferGroup(groupBaseDto.getOwner(), groupBaseDto.getGroupId());
		if(dto.getCode() == DataTransferObject.SUCCESS){
			try {
				this.huanxinImManagerServiceImpl.updateGroupOwer(groupBaseDto, owner.getMember());
			} catch (Exception e) {
				LogUtil.error(logger, "【群组修改异常】jsonParam={},oldOwner={},e={}", jsonParam,owner.getMember(),e);
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("群组修改异常");
			}
		
		}
		return dto.toJsonString();
	}


	/**
	 * 
	 * 处理黄色图片
	 * @author loushuai
	 * @created 2017年9月7日 下午9:43:39
	 *
	 * @param jsonParam
	 * @return
	 */
	@Override
	public void dealImYellowPic() {
		    long startTime = System.currentTimeMillis();
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("ziroomFlag", AuthIdentifyEnum.ZIROOM_CHANGZU_IM.getType());
			paramMap.put("isCertified", YesOrNoEnum.NO.getCode());
			List<HuanxinImRecordImgEntity> list = huanxinImManagerServiceImpl.queryAllNeedDealImPic(paramMap);
			if(Check.NuNCollection(list)){
					return;
			}
			//开始处理鉴黄
			LogUtil.info(logger, "dealImYellowPic参数  list={} identifyPicUrl={}", JsonEntityTransform.Object2Json(list),INENTIFY_PIC_URL);
			for (HuanxinImRecordImgEntity huanxinImRecordImg : list) {
				try {
					String picUrl = huanxinImRecordImg.getUrl();
					//调用鉴黄接口
					if(Check.NuNStr(picUrl)){
					      continue;
					}
					String picBase64 = DealHuanxinPicUtils.encodeImgageToBase64(picUrl);
					if(Check.NuNStr(picBase64)){
						continue;
					}
					JSONObject msgObj = new  JSONObject();
					msgObj.put("source", "bnb.minsu");
					msgObj.put("filename", huanxinImRecordImg.getFilename());
					msgObj.put("base64", picBase64);
					msgObj.put("type", "ei");
					msgObj.put("contentSecurity", true);
					msgObj.put("porn", true);
					String request = msgObj.toJSONString();
					String identifyResult = CloseableHttpUtil.sendPost(INENTIFY_PIC_URL, request);
					JSONObject identifyObject = JSONObject.parseObject(identifyResult);
					LogUtil.info(logger, "鉴黄结果    identifyObject={}", identifyObject.toJSONString());
					String porn = identifyObject.getString("porn");
					if(!Check.NuNObj(porn)){
						huanxinImManagerServiceImpl.dealImYellowPic(huanxinImRecordImg, porn);
					}
				} catch (Exception e) {
					LogUtil.info(logger, "错误信息：{}", e);
					continue;
				}
			}
			long endTime = System.currentTimeMillis();
			long doneTime = endTime-startTime;
			LogUtil.info(logger, "dealImYellowPic方法，startTime={},endTime={},最终耗时={}", startTime,endTime,doneTime);
			//huanxinImManagerServiceImpl.dealImYellowPic(list,INENTIFY_PIC_URL);
	}
}
