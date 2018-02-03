/**
 * @FileName: CallHuanXinImServiceProxy.java
 * @Package com.ziroom.minsu.services.message.proxy
 * 
 * @author yd
 * @created 2017年7月28日 下午2:18:28
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.message.proxy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.cache.redisOne.RedisOperations;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.services.common.constant.MessageConst;
import com.ziroom.minsu.services.common.utils.CloseableHttpUtil;
import com.ziroom.minsu.services.common.utils.CloseableHttpsUtil;
import com.ziroom.minsu.services.message.api.inner.CallHuanXinImService;
import com.ziroom.minsu.services.message.dto.AppGroupsPageInfo;
import com.ziroom.minsu.services.message.dto.GagMemberDto;
import com.ziroom.minsu.services.message.dto.GroupInfoDto;
import com.ziroom.minsu.services.message.dto.GroupMemberDto;
import com.ziroom.minsu.services.message.dto.GroupMemberPageInfoDto;
import com.ziroom.minsu.services.message.dto.HuanxinGroupDto;
import com.ziroom.minsu.services.message.dto.ImMembersVo;
import com.ziroom.minsu.services.message.dto.ManagerMerberDto;
import com.ziroom.minsu.services.message.dto.SendImMsgRequest;
import com.ziroom.minsu.services.message.entity.CustomerInfoVo;
import com.ziroom.minsu.services.message.entity.ImGroupsInfoVo;
import com.ziroom.minsu.services.message.utils.HuanxinUtils;
import com.ziroom.minsu.services.message.utils.base.HuanxinConfig;
import com.ziroom.minsu.valenum.msg.CreaterTypeEnum;
import com.ziroom.minsu.valenum.msg.MemberRoleEnum;

/**
 * <p>调用换新接口 代理</p>
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
@Component("message.callHuanXinImServiceProxy")
public class CallHuanXinImServiceProxy implements CallHuanXinImService{


	private  static Logger logger =LoggerFactory.getLogger(CallHuanXinImServiceProxy.class);

	@Autowired
	private RedisOperations redisOperations;

	@Value("#{'${HUANXIN_TOKEN_URL}'.trim()}")
	private String HUANXIN_TOKEN_URL;

	@Value("#{'${HUANXIN_CLIENT_ID}'.trim()}")
	private String HUANXIN_CLIENT_ID;

	@Value("#{'${HUANXIN_CLIENT_SECRET}'.trim()}")
	private String HUANXIN_CLIENT_SECRET;

	@Value("#{'${HUANXIN_CHATGROUPS_URL}'.trim()}")
	private String HUANXIN_CHATGROUPS_URL;

	@Value("#{'${IM_MINSU_FLAG}'.trim()}")
	private String IM_MINSU_FLAG;

	@Value("#{'${HUANXIN_APP_KEY}'.trim()}")
	private String HUANXIN_APP_KEY;

	@Value("#{'${HUANXIN_DOMAIN}'.trim()}")
	private String HUANXIN_DOMAIN;

	@Value("#{'${IM_DOMAIN_FLAG}'.trim()}")
	private String IM_DOMAIN_FLAG;

	@Value("#{'${HUANXIN_ADD_ONE_GROUP_MEMBER}'.trim()}")
	private String HUANXIN_ADD_ONE_GROUP_MEMBER;

	@Value("#{'${HUANXIN_ADD_MANY_GROUP_MEMBERS}'.trim()}")
	private String HUANXIN_ADD_MANY_GROUP_MEMBERS;

	@Value("#{'${HUANXIN_FIND_GROUP_MEMBERS_BY_PAGE_GET}'.trim()}")
	private String HUANXIN_FIND_GROUP_MEMBERS_BY_PAGE_GET;

	@Value("#{'${HUANXIN_FIND_APP_GROUPS_GET}'.trim()}")
	private String HUANXIN_FIND_APP_GROUPS_GET;

	@Value("#{'${HUANXIN_ADD_GAG_MEMBER_POST}'.trim()}")
	private String HUANXIN_ADD_GAG_MEMBER_POST;

	@Value("#{'${HUANXIN_ADD_ADMIN_MEMBER_POST}'.trim()}")
	private String HUANXIN_ADD_ADMIN_MEMBER_POST;

	@Value("#{'${HUANXIN_QUERY_GROUPS_INFO_GET}'.trim()}")
	private String HUANXIN_QUERY_GROUPS_INFO_GET;

	@Value("#{'${HUANXIN_SEND_IM_MSG_URL}'.trim()}")
	private String HUANXIN_SEND_IM_MSG_URL;

	@Value("#{'${ACCOUNT_QUERY_USER_INFO}'.trim()}")
	private String ACCOUNT_QUERY_USER_INFO;

	@Value("#{'${AUTH_CODE}'.trim()}")
	private String AUTH_CODE;

	@Value("#{'${AUTH_SECRET_KEY}'.trim()}")
	private String AUTH_SECRET_KEY;

	@Value("#{'${IM_ZRY_FLAG}'.trim()}")
	private String IM_ZRY_FLAG;

	@Value("#{'${HUANXIN_REGISTER_USER_POSET}'.trim()}")
	private String HUANXIN_REGISTER_USER_POSET;
	
	@Value("#{'${HUANXIN_TRANSFER_GROUP_PUT}'.trim()}")
	private String HUANXIN_TRANSFER_GROUP_PUT;
	/**
	 * 
	 * 添加群组
	 *
	 * @author yd
	 * @created 2017年7月28日 下午2:25:21
	 *
	 * @return
	 */
	@Override
	public DataTransferObject addGroup(HuanxinGroupDto huanxinGroupDto) {


		DataTransferObject dto = new DataTransferObject();
		String result = null;
		try {
			Map<String, String> headerMap = fillHeaderMap();

			if(headerMap == null){
				LogUtil.error(logger, "【环信添加群组-获取toekn失败】");
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("环信添加群组-获取toekn失败");
				return dto;
			}
			result = CloseableHttpsUtil.sendPost(HUANXIN_CHATGROUPS_URL,JsonEntityTransform.Object2Json(fillAddGroupParam(huanxinGroupDto)), headerMap);
		} catch (Exception e) {
			LogUtil.error(logger, "【添加环信群组失败】HUANXIN_CHATGROUPS_URL={},result={},e={}", HUANXIN_CHATGROUPS_URL,result,e);
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("添加环信群组失败");
			return dto;
		}

		if(!Check.NuNStr(result)){
			Map<String, Object> resMap = (Map<String, Object>) JsonEntityTransform.json2Map(result);
			if(!Check.NuNMap(resMap)){
				Object errorObj = resMap.get("error");
				if(!Check.NuNObj(errorObj)){
					LogUtil.info(logger, "【群组创建失败】result={}", result);
					dto.setErrCode(DataTransferObject.ERROR);
					Object desObj = resMap.get("error_description");
					dto.setMsg(desObj == null?"添加环信群组失败":desObj+"");
					return dto;
				}
				Object data = resMap.get("data");
				if(!Check.NuNObj(data)){
					Map<String, Object> dataMap = (Map<String, Object>) JsonEntityTransform.json2Map(JsonEntityTransform.Object2Json(data));
					Object groupidObj = dataMap.get("groupid");
					if(Check.NuNObj(groupidObj)){
						LogUtil.info(logger, "【群组创建失败】groupid不存在,data={}", JsonEntityTransform.Object2Json(data));
						dto.setErrCode(DataTransferObject.ERROR);
						dto.setMsg("添加环信群组失败,groupid不存在");
						return dto;
					}
					huanxinGroupDto.setGroupId(groupidObj.toString());
					dto.putValue("groupid", groupidObj.toString());
				}

			}
		}
		return dto;
	}



	/**
	 * 
	 * 获取头map
	 *
	 * @author yd
	 * @created 2017年7月28日 下午3:31:51
	 *
	 * @return
	 */
	private Map<String, String> fillHeaderMap(){
		Map<String, String> headerMap = new HashMap<String, String>();
		try {
			String  token = getHuanxinToken();
			headerMap.put("Content-Type", "application/json");
			headerMap.put("Authorization", "Bearer "+token);
		} catch (Exception e) {
			LogUtil.error(logger, "【环信请求-获取toekn失败】e={}", e);
			headerMap = null;
		}

		return  headerMap;
	}

	/**
	 * 
	 * 填充 增加群组参数
	 *
	 * @author yd
	 * @created 2017年7月28日 下午3:11:18
	 *
	 * @param huanxinGroupDto
	 * @return
	 */
	private Map<String, Object> fillAddGroupParam(HuanxinGroupDto huanxinGroupDto){

		Map<String, Object> params = new HashMap<String, Object>();

		if(!Check.NuNObj(huanxinGroupDto)){
			params.put("groupname", huanxinGroupDto.getGroupname());
			params.put("desc", huanxinGroupDto.getDesc());
			params.put("public", huanxinGroupDto.isPublic());
			params.put("maxusers", huanxinGroupDto.getMaxusers());
			params.put("members_only", huanxinGroupDto.isMembersOnly());
			params.put("allowinvites", huanxinGroupDto.isAllowinvites());
			params.put("owner", MessageConst.IM_UID_PRE+huanxinGroupDto.getOwner());
			params.put("invite_need_confirm", huanxinGroupDto.isInviteNeedConfirm());

			List<String> list = huanxinGroupDto.getMembers();
			if(!Check.NuNCollection(list)){
				List<String> newlist = new ArrayList<String>();
				for (String uid : list) {
					newlist.add(MessageConst.IM_UID_PRE+uid);
				}
				params.put("members",newlist);
			}
		}

		return params;
	}

	/**
	 * 
	 * 获取环信 配置参数
	 *
	 * @author yd
	 * @created 2017年7月28日 下午2:40:21
	 *
	 * @return
	 */
	private String getHuanxinToken(){
		HuanxinConfig huanxinConfig = new HuanxinConfig();
		huanxinConfig.setHuanxinAppKey(HUANXIN_APP_KEY);
		huanxinConfig.setHuanxinClientId(HUANXIN_CLIENT_ID);
		huanxinConfig.setHuanxinClientSecret(HUANXIN_CLIENT_SECRET);
		huanxinConfig.setHuanxinDomain(HUANXIN_DOMAIN);
		huanxinConfig.setHuanxinTokenUrl(HUANXIN_TOKEN_URL);
		huanxinConfig.setImMinsuFlag(IM_MINSU_FLAG);
		huanxinConfig.setDomainFlag(IM_DOMAIN_FLAG);
		return HuanxinUtils.getHuanxinToken(redisOperations, huanxinConfig);
	}



	/**
	 * 
	 * 添加 单个 群成员
	 * 
	 * 添加成功后： 群里发送消息 ：XX已入群
	 *
	 * @author yd
	 * @created 2017年7月31日 上午9:42:16
	 *
	 * @param groupId  群组id
	 * @param uid    ziroom的用户uid
	 * @return
	 */
	@Override
	public DataTransferObject addOneGroupMember(String groupId, String uid,int num) {

		DataTransferObject dto = new DataTransferObject();

		num--;
		if(Check.NuNStr(groupId)||Check.NuNStr(uid)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("参数错误");
			LogUtil.error(logger, "【添加单个群组成员参数错误】groupId={},uid={}", groupId,uid);
			return dto;
		}
		
		if(num == 0){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("注册环信多次失败");
			LogUtil.error(logger, "【添加单个群组成员参数错误——注册环信多次失败】groupId={},uid={},num={}", groupId,uid,num);
			return dto;
		}
		String result = null;
		String url  = null;
		try {
			Map<String, String> headerMap = fillHeaderMap();

			if(headerMap == null){
				LogUtil.error(logger, "【环信添加单个群成员-获取toekn失败】");
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("环信添加单个群成员获取toekn失败");
				return dto;
			}

			url = HUANXIN_ADD_ONE_GROUP_MEMBER.replace("#{group_id}", groupId).replace("#{username}", MessageConst.IM_UID_PRE+uid);
			result = CloseableHttpsUtil.sendPost(url,"", headerMap);

			if(!Check.NuNStr(result)){
				Map<String, Object> resMap = (Map<String, Object>) JsonEntityTransform.json2Map(result);
				if(!Check.NuNMap(resMap)){
					Object errorObj = resMap.get("error");
					if(!Check.NuNObj(errorObj)){
						//如果未注册 
						if(MessageConst.IM_REGISTER_ERROR.equals(errorObj.toString())){
							DataTransferObject dtoR = this.registerChatdemoui(uid);
							if(dtoR.getCode() == DataTransferObject.SUCCESS){
								dto = addOneGroupMember(groupId, uid,num);
							}
						}else{
							LogUtil.info(logger, "【添加单个群成员失败】result={}", result);
							dto.setErrCode(DataTransferObject.ERROR);
							Object desObj = resMap.get("error_description");
							dto.setMsg(desObj == null?"添加单个群成员失败":desObj+"");
							dto.putValue("result", result);
							return dto;
						}
						
					}
				

				}
			}

		} catch (Exception e) {
			LogUtil.info(logger, "【添加环信添加单个群成员失败】url={},result={},e={}", url,result,e);
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("添加环信单个群成员失败");
			return dto;
		}
		return dto;
	}


	/**
	 * 
	 * 添加群组 多成员
	 *
	 * @author yd
	 * @created 2017年7月31日 上午9:50:27
	 *
	 * @param groupMembersJson
	 * @return
	 */
	@Override
	public DataTransferObject addManyGroupMember(GroupMemberDto groupMemberDto,int num) {

		DataTransferObject dto = new DataTransferObject();

		num--;
		if(Check.NuNObj(groupMemberDto)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("参数不存在");
			return dto;
		}

		if(Check.NuNStr(groupMemberDto.getGroupId())){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("群组号参数错误");
			return dto;
		}
		if(num == 0){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("添加群组成员多次失败");
			return dto;
		}

		List<String> members = groupMemberDto.getMembers();
		if(Check.NuNCollection(members)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("添加群组成员不存在");
			return dto;
		}

		if(members.size()>60){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("添加群组成员不能超过60");
			return dto;
		}

		List<String> imList = new ArrayList<String>();

		String result = null;
		String url  = null;
		try {
			Map<String, String> headerMap = fillHeaderMap();

			if(headerMap == null){
				LogUtil.error(logger, "【环信添加多个群成员-获取toekn失败】");
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("环信添加多个群成员获取toekn失败");
				return dto;
			}

			for (String uid : members) {
				imList.add(MessageConst.IM_UID_PRE+uid);
			}

			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("usernames",imList);
			url = HUANXIN_ADD_MANY_GROUP_MEMBERS.replace("#{chatgroupid}", groupMemberDto.getGroupId());
			result = CloseableHttpsUtil.sendPost(url,JsonEntityTransform.Object2Json(paramMap), headerMap);

			if(!Check.NuNStr(result)){
				Map<String, Object> resMap = (Map<String, Object>) JsonEntityTransform.json2Map(result);
				if(!Check.NuNMap(resMap)){
					Object errorObj = resMap.get("error");
					if(!Check.NuNObj(errorObj)){
						
						//如果未注册 
						if(MessageConst.IM_REGISTER_ERROR.equals(errorObj.toString())){
							for (String uid : members) {
								DataTransferObject dtoR = null;
								try {
									 dtoR = this.registerChatdemoui(uid);
								} catch (Exception e) {
									LogUtil.error(logger, "【添加多个群成员-注册环信失败】e={},msg={}", e,dtoR.getMsg());
								}
							}
							dto = addManyGroupMember(groupMemberDto, num);
						}else{
							LogUtil.info(logger, "【添加多个群成员失败】result={}", result);
							dto.setErrCode(DataTransferObject.ERROR);
							Object desObj = resMap.get("error_description");
							dto.setMsg(desObj == null?"添加多个群成员失败":desObj+"");
							dto.putValue("result", result);
							return dto;
						}
					
					}
				
				}
			}

		} catch (Exception e) {
			LogUtil.info(logger, "【添加环信添加多个群成员失败】url={},result={},members={},e={}", url,result,JsonEntityTransform.Object2Json(imList),e);
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("添加环信多个群成员失败");
			return dto;
		}
		return dto;

	}


	/**
	 * 
	 * 分页从 环信查询 当前群成员
	 * 说明： 1. 这块接口无法区分 是否是管理员   
	 *   2. 页无法区分当前的成员的状态 是否禁言  是否黑名单 
	 *
	 * @author yd
	 * @created 2017年7月31日 上午11:30:19
	 *
	 * @param groupMemberPageInfoDto
	 * @return
	 */
	@Override
	public DataTransferObject queryGroupMemberByPage(
			GroupMemberPageInfoDto groupMemberPageInfoDto) {

		DataTransferObject dto = new DataTransferObject();

		if(Check.NuNObj(groupMemberPageInfoDto)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("参数不存在");
			return dto;
		}

		if(Check.NuNStr(groupMemberPageInfoDto.getGroupId())){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("群组号不存在");
			return dto;
		}

		String result = null;
		String url  = null;
		try {
			Map<String, String> headerMap = fillHeaderMap();

			if(headerMap == null){
				LogUtil.error(logger, "【环信添加多个群成员-获取toekn失败】");
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("环信添加多个群成员获取toekn失败");
				return dto;
			}
			url = HUANXIN_FIND_GROUP_MEMBERS_BY_PAGE_GET.replace("#{group_id}", groupMemberPageInfoDto.getGroupId()).replace("#{pagenum}", groupMemberPageInfoDto.getPage()+"")
					.replace("#{pagesize}", groupMemberPageInfoDto.getLimit()+"");
			LogUtil.info(logger, "【分页获取群成员失败】queryGroupMemberByPage  url={}, GroupId={}, pagenum={}", url,groupMemberPageInfoDto.getGroupId(),groupMemberPageInfoDto.getPage());
			result = CloseableHttpsUtil.sendGet(url, headerMap);
			if(!Check.NuNStr(result)){
				Map<String, Object> resMap = (Map<String, Object>) JsonEntityTransform.json2Map(result);
				if(!Check.NuNMap(resMap)){
					Object errorObj = resMap.get("error");
					if(!Check.NuNObj(errorObj)){
						LogUtil.info(logger, "【分页获取群成员失败】result={}", result);
						dto.setErrCode(DataTransferObject.ERROR);
						Object desObj = resMap.get("error_description");
						dto.setMsg(desObj == null?"分页获取群成员失败":desObj+"");
						return dto;
					}
					Object data = resMap.get("data");
					dto.putValue("count",resMap.get("count"));
					if(!Check.NuNObj(data)){
						List<ImMembersVo> uidList = new ArrayList<ImMembersVo>();
						List<ImMembersVo> dataMap = JsonEntityTransform.json2ObjectList(JsonEntityTransform.Object2Json(data), ImMembersVo.class);
						if(!Check.NuNCollection(dataMap)){
							for (ImMembersVo imMembersVo : dataMap) {

								ImMembersVo imMembers = new ImMembersVo();
								if(!Check.NuNStr(imMembersVo.getMember())){
									imMembers.setMember(imMembersVo.getMember().replace(MessageConst.IM_UID_PRE, "").trim());
									imMembers.setMemberRole(MemberRoleEnum.ORDINARY_MEMBER.getCode());
									uidList.add(imMembers);
								}
								if(!Check.NuNStr(imMembersVo.getOwner())){
									imMembers.setMember(imMembersVo.getOwner().replace(MessageConst.IM_UID_PRE, "").trim());
									imMembers.setMemberRole(MemberRoleEnum.OWNER_MEMBER.getCode());
									uidList.add(imMembers);
								}
							}
						}
						dto.putValue("uidList",uidList);
					}
				}
			}

		} catch (Exception e) {
			LogUtil.info(logger, "【分页获取群成员失败】url={},groupMemberPageInfoDto={},result={},e={}", url,JsonEntityTransform.Object2Json(groupMemberPageInfoDto),result,e);
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("分页获取群成员失败");
			return dto;
		}
		return dto;
	}



	/**
	 * 
	 * 分页获取ziroom的app下所有群组
	 *
	 * @author yd
	 * @created 2017年7月31日 下午3:00:31
	 *
	 * @param appGroupsPageInfo
	 * @return
	 */
	@Override
	public DataTransferObject queryAppGroupsByPage(
			AppGroupsPageInfo appGroupsPageInfo) {

		DataTransferObject dto = new DataTransferObject();

		if(Check.NuNObj(appGroupsPageInfo)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("参数不存在");
			return dto;
		}


		String result = null;
		String url  = null;

		try {
			Map<String, String> headerMap = fillHeaderMap();

			if(headerMap == null){
				LogUtil.error(logger, "【环信添加多个群成员-获取toekn失败】");
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("环信添加多个群成员获取toekn失败");
				return dto;
			}
			url = HUANXIN_FIND_APP_GROUPS_GET.replace("#{limit}",appGroupsPageInfo.getLimit()+"");

			result = CloseableHttpsUtil.sendGet(url, headerMap);
			if(!Check.NuNStr(result)){
				Map<String, Object> resMap = (Map<String, Object>) JsonEntityTransform.json2Map(result);
				if(!Check.NuNMap(resMap)){
					Object errorObj = resMap.get("error");
					if(!Check.NuNObj(errorObj)){
						LogUtil.info(logger, "【分页所有群失败】result={}", result);
						dto.setErrCode(DataTransferObject.ERROR);
						Object desObj = resMap.get("error_description");
						dto.setMsg(desObj == null?"分页所有群失败":desObj+"");
						return dto;
					}
					Object data = resMap.get("data");
					dto.putValue("count",resMap.get("count"));
					if(!Check.NuNObj(data)){
						List<ImGroupsInfoVo> listImGroupsInfo = JsonEntityTransform.json2ObjectList(JsonEntityTransform.Object2Json(data), ImGroupsInfoVo.class);
						dto.putValue("listImGroupsInfo",listImGroupsInfo);
					}
				}
			}

		} catch (Exception e) {
			LogUtil.info(logger, "【分页所有群失败】url={},appGroupsPageInfo={},result={},e={}", url,JsonEntityTransform.Object2Json(appGroupsPageInfo),result,e);
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("分页所有群失败");
			return dto;
		}
		return dto;
	}



	/**
	 * 
	 * 禁言
	 *
	 * @author yd
	 * @created 2017年8月3日 下午7:18:51
	 *
	 * @return
	 */
	@Override
	public DataTransferObject addGagMember(GagMemberDto gagMemberDto) {

		DataTransferObject dto = new DataTransferObject();

		if(Check.NuNObj(gagMemberDto)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("参数不存在");
			return dto;
		}

		if(Check.NuNObj(gagMemberDto.getMuteDuration())||Check.NuNCollection(gagMemberDto.getMembers())
				||Check.NuNStr(gagMemberDto.getGroupId())){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("参数错误");
			return dto;
		}

		List<String> members = gagMemberDto.getMembers();

		List<String> imList = new ArrayList<String>();

		String result = null;
		String url  = null;
		try {
			Map<String, String> headerMap = fillHeaderMap();

			if(headerMap == null){
				LogUtil.error(logger, "【禁言群成员-获取toekn失败】");
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("禁言群成员获取toekn失败");
				return dto;
			}

			for (String uid : members) {
				imList.add(MessageConst.IM_UID_PRE+uid);
			}

			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("usernames",imList);
			paramMap.put("mute_duration",gagMemberDto.getMuteDuration());
			url = HUANXIN_ADD_GAG_MEMBER_POST.replace("#{group_id}", gagMemberDto.getGroupId());
			result = CloseableHttpsUtil.sendPost(url,JsonEntityTransform.Object2Json(paramMap), headerMap);

			if(!Check.NuNStr(result)){
				Map<String, Object> resMap = (Map<String, Object>) JsonEntityTransform.json2Map(result);
				if(!Check.NuNMap(resMap)){
					Object errorObj = resMap.get("error");
					if(!Check.NuNObj(errorObj)){
						LogUtil.info(logger, "【禁言群成员失败】result={}", result);
						dto.setErrCode(DataTransferObject.ERROR);
						Object desObj = resMap.get("error_description");
						dto.setMsg(desObj == null?"禁言群成员失败":desObj+"");
						return dto;
					}
					dto.putValue("result", result);
				}
			}

		} catch (Exception e) {
			LogUtil.info(logger, "【禁言群成员员失败】url={},members={},result={},e={}", url,result,JsonEntityTransform.Object2Json(imList),e);
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("禁言群成员失败");

		}

		return dto;
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
	public DataTransferObject removeGagMember(GagMemberDto gagMemberDto) {
		DataTransferObject dto = new DataTransferObject();

		if(Check.NuNObj(gagMemberDto)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("参数不存在");
			return dto;
		}

		if(Check.NuNCollection(gagMemberDto.getMembers())
				||Check.NuNStr(gagMemberDto.getGroupId())){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("参数错误");
			return dto;
		}

		List<String> members = gagMemberDto.getMembers();

		StringBuffer imList =new StringBuffer();

		String result = null;
		String url  = null;
		try {
			Map<String, String> headerMap = fillHeaderMap();

			if(headerMap == null){
				LogUtil.error(logger, "【禁言群成员-获取toekn失败】");
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("禁言群成员获取toekn失败");
				return dto;
			}

			int i = 0;
			for (String uid : members) {
				i++;
				imList.append(MessageConst.IM_UID_PRE+uid);
				if(i <members.size() ){
					imList.append(",");
				}

			}
			url = HUANXIN_ADD_GAG_MEMBER_POST.replace("#{group_id}", gagMemberDto.getGroupId())+"/"+imList.toString();
			result = CloseableHttpsUtil.sendDelete(url,headerMap);

			if(!Check.NuNStr(result)){
				Map<String, Object> resMap = (Map<String, Object>) JsonEntityTransform.json2Map(result);
				if(!Check.NuNMap(resMap)){
					Object errorObj = resMap.get("error");
					if(!Check.NuNObj(errorObj)){
						LogUtil.info(logger, "【解除禁言群成员失败】result={}", result);
						dto.setErrCode(DataTransferObject.ERROR);
						Object desObj = resMap.get("error_description");
						dto.setMsg(desObj == null?"解除禁言群成员失败":desObj+"");
						return dto;
					}
					dto.putValue("result", result);
				}
			}

		} catch (Exception e) {
			LogUtil.info(logger, "【解除禁言群成员员失败】url={},members={},result={},e={}", url,result,JsonEntityTransform.Object2Json(imList),e);
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("解除禁言群成员失败");

		}

		return dto;
	}



	/**
	 * 
	 * 移除 单个 群成员
	 *
	 * @author yd
	 * @created 2017年7月31日 上午9:42:16
	 *
	 * @param groupId  群组id
	 * @param uid    ziroom的用户uid
	 * @return
	 */
	@Override
	public DataTransferObject removeOneGroupMember(String groupId, String uid) {

		DataTransferObject dto = new DataTransferObject();

		if(Check.NuNStr(groupId)||Check.NuNStr(uid)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("参数错误");
			LogUtil.error(logger, "【删除单个群组成员参数错误】groupId={},uid={}", groupId,uid);
			return dto;
		}
		String result = null;
		String url  = null;
		try {
			Map<String, String> headerMap = fillHeaderMap();

			if(headerMap == null){
				LogUtil.error(logger, "【环信删除单个群成员-获取toekn失败】");
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("环信删除单个群成员获取toekn失败");
				return dto;
			}

			url = HUANXIN_ADD_ONE_GROUP_MEMBER.replace("#{group_id}", groupId).replace("#{username}", MessageConst.IM_UID_PRE+uid);
			result = CloseableHttpsUtil.sendDelete(url,headerMap);

			if(!Check.NuNStr(result)){
				Map<String, Object> resMap = (Map<String, Object>) JsonEntityTransform.json2Map(result);
				if(!Check.NuNMap(resMap)){
					Object errorObj = resMap.get("error");
					if(!Check.NuNObj(errorObj)){
						LogUtil.info(logger, "【删除单个群成员失败】result={}", result);
						dto.setErrCode(DataTransferObject.ERROR);
						Object desObj = resMap.get("error_description");
						dto.setMsg(desObj == null?"删除单个群成员失败":desObj+"");
						return dto;
					}
					dto.putValue("result", result);
				}
			}

		} catch (Exception e) {
			LogUtil.info(logger, "【删除环信添加单个群成员失败】url={},result={},e={}", url,result,e);
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("删除环信单个群成员失败");
		}
		return dto;
	}



	/**
	 * 
	 * 移除 群组 多成员
	 *
	 * @author yd
	 * @created 2017年7月31日 上午9:50:27
	 *
	 * @param groupMembersJson
	 * @return
	 */
	@Override
	public DataTransferObject removeManyGroupMember(
			GroupMemberDto groupMemberDto) {
		DataTransferObject dto = new DataTransferObject();

		if(Check.NuNObj(groupMemberDto)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("参数不存在");
			return dto;
		}

		if(Check.NuNStr(groupMemberDto.getGroupId())){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("群组号参数错误");
			return dto;
		}

		List<String> members = groupMemberDto.getMembers();
		if(Check.NuNCollection(members)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("删除群组成员不存在");
			return dto;
		}

		if(members.size()>60){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("删除群组成员不能超过60");
			return dto;
		}

		StringBuffer imList = new StringBuffer();

		String result = null;
		String url  = null;
		try {
			Map<String, String> headerMap = fillHeaderMap();

			if(headerMap == null){
				LogUtil.error(logger, "【环信删除多个群成员-获取toekn失败】");
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("环信删除多个群成员获取toekn失败");
				return dto;
			}

			int i = 0;
			for (String uid : members) {
				i++;
				imList.append(MessageConst.IM_UID_PRE+uid);
				if(i <members.size() ){
					imList.append(",");
				}

			}
			url = HUANXIN_ADD_MANY_GROUP_MEMBERS.replace("#{chatgroupid}", groupMemberDto.getGroupId())+"/"+imList.toString();
			result = CloseableHttpsUtil.sendDelete(url,headerMap);

			if(!Check.NuNStr(result)){
				Map<String, Object> resMap = (Map<String, Object>) JsonEntityTransform.json2Map(result);
				if(!Check.NuNMap(resMap)){
					Object errorObj = resMap.get("error");
					if(!Check.NuNObj(errorObj)){
						LogUtil.info(logger, "【删除多个群成员失败】result={}", result);
						dto.setErrCode(DataTransferObject.ERROR);
						Object desObj = resMap.get("error_description");
						dto.setMsg(desObj == null?"删除多个群成员失败":desObj+"");
						return dto;
					}
					dto.putValue("result", result);
				}
			}

		} catch (Exception e) {
			LogUtil.info(logger, "【删除环信添加多个群成员失败】url={},result={},members={},e={}", url,result,JsonEntityTransform.Object2Json(imList),e);
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("删除环信多个群成员失败");
			return dto;
		}
		return dto;
	}



	/**
	 * 
	 * 添加群管理员 
	 *
	 * @author yd
	 * @created 2017年8月4日 上午10:43:28
	 *
	 * @param managerMerberDto
	 * @return
	 */
	@Override
	public DataTransferObject addAdminMember(ManagerMerberDto managerMerberDto) {

		DataTransferObject dto = new DataTransferObject();

		if(Check.NuNObj(managerMerberDto)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("参数不存在");
			return dto;
		}

		if(Check.NuNStr(managerMerberDto.getGroupId())
				||Check.NuNStr(managerMerberDto.getAdminUid())){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("参数错误");
			return dto;
		}
		String result = null;
		String url  = null;
		try {
			Map<String, String> headerMap = fillHeaderMap();

			if(headerMap == null){
				LogUtil.error(logger, "【环信添加管理员-获取toekn失败】");
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("环信添加管理员获取toekn失败");
				return dto;
			}
			Map<String, String>  param = new HashMap<String, String>();
			param.put("newadmin",MessageConst.IM_UID_PRE+managerMerberDto.getAdminUid());

			url = HUANXIN_ADD_ADMIN_MEMBER_POST.replace("#{group_id}", managerMerberDto.getGroupId());
			result = CloseableHttpsUtil.sendPost(url, param, headerMap);

			if(!Check.NuNStr(result)){
				Map<String, Object> resMap = (Map<String, Object>) JsonEntityTransform.json2Map(result);
				if(!Check.NuNMap(resMap)){
					Object errorObj = resMap.get("error");
					if(!Check.NuNObj(errorObj)){
						LogUtil.info(logger, "【添加管理员失败】result={}", result);
						dto.setErrCode(DataTransferObject.ERROR);
						Object desObj = resMap.get("error_description");
						dto.setMsg(desObj == null?"添加管理员失败":desObj+"");
						return dto;
					}
					dto.putValue("result", result);
				}
			}

		} catch (Exception e) {
			LogUtil.info(logger, "【添加管理员失败】url={},result={},managerMerberDto={},e={}", url,result,JsonEntityTransform.Object2Json(managerMerberDto),e);
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("添加管理员失败");
			return dto;
		}
		return dto;
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
	public DataTransferObject deleteAdminMember(
			ManagerMerberDto managerMerberDto) {
		DataTransferObject dto = new DataTransferObject();

		if(Check.NuNObj(managerMerberDto)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("参数不存在");
			return dto;
		}

		if(Check.NuNStr(managerMerberDto.getGroupId())
				||Check.NuNStr(managerMerberDto.getAdminUid())){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("参数错误");
			return dto;
		}
		String result = null;
		String url  = null;
		try {
			Map<String, String> headerMap = fillHeaderMap();

			if(headerMap == null){
				LogUtil.error(logger, "【环信删除管理员-获取toekn失败】");
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("环信删除管理员获取toekn失败");
				return dto;
			}
			url = HUANXIN_ADD_ADMIN_MEMBER_POST.replace("#{group_id}", managerMerberDto.getGroupId())+"/"+MessageConst.IM_UID_PRE+managerMerberDto.getAdminUid();
			result = CloseableHttpsUtil.sendDelete(url, headerMap);

			if(!Check.NuNStr(result)){
				Map<String, Object> resMap = (Map<String, Object>) JsonEntityTransform.json2Map(result);
				if(!Check.NuNMap(resMap)){
					Object errorObj = resMap.get("error");
					if(!Check.NuNObj(errorObj)){
						LogUtil.info(logger, "【删除管理员失败】result={}", result);
						dto.setErrCode(DataTransferObject.ERROR);
						Object desObj = resMap.get("error_description");
						dto.setMsg(desObj == null?"删除管理员失败":desObj+"");
						return dto;
					}
					dto.putValue("result", result);
				}
			}

		} catch (Exception e) {
			LogUtil.info(logger, "【删除管理员失败】url={},result={},managerMerberDto={},e={}", url,result,JsonEntityTransform.Object2Json(managerMerberDto),e);
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("删除管理员失败");
			return dto;
		}
		return dto;
	}



	/**
	 * 
	 * 根据组id删除群组
	 *
	 * @author yd
	 * @created 2017年8月4日 下午2:11:13
	 *
	 * @param groupId
	 * @return
	 */
	@Override
	public DataTransferObject removeGroupByGroupId(String groupId) {

		DataTransferObject dto = new DataTransferObject();

		if(Check.NuNStr(groupId)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("请选择群");
			return dto;
		}

		String result = null;
		String url  = null;
		try {
			Map<String, String> headerMap = fillHeaderMap();

			if(headerMap == null){
				LogUtil.error(logger, "【环信删除群-获取toekn失败】");
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("环信删除群获取toekn失败");
				return dto;
			}
			url = HUANXIN_QUERY_GROUPS_INFO_GET+groupId;
			result = CloseableHttpsUtil.sendDelete(url,headerMap);

			if(!Check.NuNStr(result)){
				Map<String, Object> resMap = (Map<String, Object>) JsonEntityTransform.json2Map(result);
				if(!Check.NuNMap(resMap)){
					Object errorObj = resMap.get("error");
					if(!Check.NuNObj(errorObj)){
						LogUtil.info(logger, "【删除群失败】result={}", result);
						dto.setErrCode(DataTransferObject.ERROR);
						Object desObj = resMap.get("error_description");
						dto.setMsg(desObj == null?"删除群失败":desObj+"");
						return dto;
					}
					dto.putValue("result", result);
				}
			}

		} catch (Exception e) {
			LogUtil.info(logger, "【删除群失败】url={},result={},groupId={},e={}", url,result,groupId,e);
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("删除群失败");

		}
		return dto;
	}




	/**
	 * 
	 * 修改群组信息
	 *
	 * @author yd
	 * @created 2017年8月4日 下午2:25:42
	 *
	 * @param groupInfoDto
	 * @return
	 */
	@Override
	public DataTransferObject updateGroupByGroupId(GroupInfoDto groupInfoDto) {

		DataTransferObject dto = new DataTransferObject();

		if(Check.NuNObj(groupInfoDto)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("参数不存在");
			return dto;
		}

		if(Check.NuNObj(groupInfoDto.getMaxusers())&&Check.NuNStr(groupInfoDto.getGroupname())
				&&Check.NuNStr(groupInfoDto.getDescription())){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("参数错误");
			return dto;
		}
		String result = null;
		String url  = null;
		try {

			Map<String, String> headerMap = fillHeaderMap();

			if(headerMap == null){
				LogUtil.error(logger, "【修改群组-获取toekn失败】");
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("修改群组获取toekn失败");
				return dto;
			}

			Map<String, Object> param = new HashMap<String, Object>();
			if(!Check.NuNObj(groupInfoDto.getMaxusers())){
				param.put("maxusers",groupInfoDto.getMaxusers() );
			}
			if(!Check.NuNStr(groupInfoDto.getGroupname())){

				param.put("groupname",groupInfoDto.getGroupname() );
			}
			if(!Check.NuNStr(groupInfoDto.getDescription())){
				param.put("description", groupInfoDto.getDescription());

			}
			url = HUANXIN_QUERY_GROUPS_INFO_GET+groupInfoDto.getGroupId();
			result = CloseableHttpsUtil.sendPut(url, JsonEntityTransform.Object2Json(param), headerMap);

			if(!Check.NuNStr(result)){
				Map<String, Object> resMap = (Map<String, Object>) JsonEntityTransform.json2Map(result);
				if(!Check.NuNMap(resMap)){
					Object errorObj = resMap.get("error");
					if(!Check.NuNObj(errorObj)){
						LogUtil.info(logger, "【修改群组失败】result={}", result);
						dto.setErrCode(DataTransferObject.ERROR);
						Object desObj = resMap.get("error_description");
						dto.setMsg(desObj == null?"修改群组失败":desObj+"");
						return dto;
					}
					dto.putValue("result", result);
				}
			}
		} catch (Exception e) {
			LogUtil.error(logger, "【修改群组失败】url={},result={},groupInfoDto={},e={}", url,result,JsonEntityTransform.Object2Json(groupInfoDto),e);
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("修改群组失败");

		}

		return dto;
	}




	/**
	 * 
	 * 可以获取一个或多个群组的详情  
	 *
	 * @author yd
	 * @created 2017年8月4日 下午2:28:43
	 *
	 * @param groupIds
	 * @return
	 */
	@Override
	public DataTransferObject queryGroupInfo(List<String> groupIds) {


		DataTransferObject dto = new DataTransferObject();

		if(Check.NuNCollection(groupIds)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("请选择群");
			return dto;
		}

		StringBuffer groupIdsStr = new StringBuffer();
		int i = 0;
		for (String groupId : groupIds) {
			i++;
			groupIdsStr.append(groupId);
			if(i <groupIds.size() ){
				groupIdsStr.append(",");
			}

		}
		String result = null;
		String url  = null;
		try {

			Map<String, String> headerMap = fillHeaderMap();

			if(headerMap == null){
				LogUtil.error(logger, "【环信获取群组详情-获取toekn失败】");
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("环信获取群组详情获取toekn失败");
				return dto;
			}
			url = HUANXIN_QUERY_GROUPS_INFO_GET+groupIdsStr.toString();

			result = CloseableHttpsUtil.sendGet(url, headerMap);

			if(!Check.NuNStr(result)){
				Map<String, Object> resMap = (Map<String, Object>) JsonEntityTransform.json2Map(result);
				if(!Check.NuNMap(resMap)){
					Object errorObj = resMap.get("error");
					if(!Check.NuNObj(errorObj)){
						LogUtil.info(logger, "【环信获取群组详情失败】result={}", result);
						dto.setErrCode(DataTransferObject.ERROR);
						Object desObj = resMap.get("error_description");
						dto.setMsg(desObj == null?"环信获取群组详情失败":desObj+"");
						return dto;
					}
					dto.putValue("result", result);

					JSONObject msgObj = JSONObject.parseObject(result);
					JSONArray dataArr = msgObj.getJSONArray("data");
					if(!dataArr.isEmpty()){

						List<HuanxinGroupDto> listHuanxinGroupDto  = new ArrayList<HuanxinGroupDto>();
						for (int j = 0; j < dataArr.size(); j++) {
							JSONObject groupObj = dataArr.getJSONObject(j);
							HuanxinGroupDto huanxinGroupDto = new HuanxinGroupDto();
							huanxinGroupDto.setGroupId(groupObj.getString("id"));
							huanxinGroupDto.setGroupname(groupObj.getString("name"));
							huanxinGroupDto.setDesc(groupObj.getString("description"));
							huanxinGroupDto.setPublic(groupObj.getBooleanValue("public"));
							huanxinGroupDto.setMembersOnly(groupObj.getBooleanValue("membersonly"));
							huanxinGroupDto.setAllowinvites(groupObj.getBooleanValue("allowinvites"));
							huanxinGroupDto.setMaxusers(groupObj.getInteger("maxusers"));
							List<ImMembersVo> dataMap = JsonEntityTransform.json2ObjectList(JsonEntityTransform.Object2Json(groupObj.get("affiliations")), ImMembersVo.class);
							if(!Check.NuNCollection(dataMap)){
								List<String> memberList = new ArrayList<String>();
								for (ImMembersVo imMembersVo : dataMap) {
									if(!Check.NuNStr(imMembersVo.getMember())){
										memberList.add(imMembersVo.getMember().split(MessageConst.IM_UID_PRE)[1]);
									}
									if(!Check.NuNStr(imMembersVo.getOwner())){
										huanxinGroupDto.setOwner(imMembersVo.getOwner().split(MessageConst.IM_UID_PRE)[1]);
										huanxinGroupDto.setOpFid(imMembersVo.getOwner());
										huanxinGroupDto.setOpType(CreaterTypeEnum.ZIROOM_USER.getCode());
									}
								}
								huanxinGroupDto.setMembers(memberList);
							}
							listHuanxinGroupDto.add(huanxinGroupDto);
						}

						dto.putValue("listHuanxinGroupDto", listHuanxinGroupDto);
					}
				}
			}
		} catch (Exception e) {
			LogUtil.error(logger, "【环信获取群组详情失败】url={},result={},groupIds={},e={}", url,result,JsonEntityTransform.Object2Json(groupIds),e);
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("环信获取群组详情失败");

		}
		return dto;
	}


	/**
	 * 发送IM消息
	 * @author yd
	 * @created 2017年8月4日 下午3:45:25
	 *
	 * @param jsonParam
	 * @return
	 */
	public void sendImMsg(SendImMsgRequest sendImMsgRequest){

		if(Check.NuNObj(sendImMsgRequest)){
			LogUtil.error(logger, "【发送IM消息参数不存在】");
			return ;
		}

		if(Check.NuNStr(sendImMsgRequest.getFrom())
				||Check.NuNStr(sendImMsgRequest.getMsg())
				||Check.NuNObj(sendImMsgRequest.getTarget())
				||Check.NuNStr(sendImMsgRequest.getTarget_type())){
			LogUtil.error(logger, "【发送IM消息参数错误】sendImMsgRequest={}",sendImMsgRequest.toJsonStr());
			return ;
		}
		Thread th = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					Map<String, String> headerMap = fillHeaderMap();

					if(headerMap == null){
						LogUtil.error(logger, "【发送IM消息-获取toekn失败】sendImMsgRequest={}",sendImMsgRequest.toJsonStr());
						return ;
					}
					Map<String, String > msgMap = new HashMap<String, String>();
					Map<String, Object> param = new HashMap<String, Object>();
					msgMap.put("type", "txt");
					msgMap.put("msg", sendImMsgRequest.getMsg());
					param.put("target_type", sendImMsgRequest.getTarget_type());
					param.put("target", sendImMsgRequest.getTarget());
					param.put("msg", msgMap);
					param.put("from", sendImMsgRequest.getFrom());
					if(!Check.NuNMap(sendImMsgRequest.getExtMap())){
						param.put("ext", sendImMsgRequest.getExtMap());
					}
					String result = CloseableHttpsUtil.sendPost(HUANXIN_SEND_IM_MSG_URL, JsonEntityTransform.Object2Json(param), headerMap);
					LogUtil.info(logger, "消息发送返回结果result={}，sendImMsgRequest={}", result,sendImMsgRequest.toJsonStr());
				} catch (Exception e) {
					LogUtil.error(logger, "【发送IM消息异常sendImMsgRequest={},e={}】",sendImMsgRequest.toJsonStr(),e);
				}

			}
		});
		th.start();
	} 


	/**
	 * 
	 * 根据uids 获取用户基本信息
	 *
	 * @author yd
	 * @created 2017年8月9日 上午11:36:29
	 *
	 * @param uids
	 */
	@Override
	public List<CustomerInfoVo> queryUserInfoByUids(List<String> uidList){
		List<CustomerInfoVo> listUserInfo = new ArrayList<CustomerInfoVo>();
		try {
			if(!Check.NuNCollection(uidList)){
				StringBuffer uids = new StringBuffer();
				int i = 0;
				for (String uid : uidList) {
					i++;
					uids.append(uid);
					if(i<uidList.size()){
						uids.append(",");
					}
				}
				Map<String, String> param = new HashMap<String, String>();
				param.put("uid", uids.toString());
				param.put("hide", "0");
				param.put("auth_code",AUTH_CODE);
				param.put("auth_secret_key", AUTH_SECRET_KEY);
				String result  = CloseableHttpUtil.sendFormPost(ACCOUNT_QUERY_USER_INFO, param);

				if(!Check.NuNStr(result)){
					JSONObject resultObj = JSONObject.parseObject(result);
					int errorCode  = resultObj.getIntValue("error_code");
					if(errorCode != DataTransferObject.SUCCESS){
						LogUtil.error(logger, "【批量查询用户信息失败】error_message", resultObj.getString("error_message"));
						return listUserInfo;
					}
					JSONArray  dataArray = resultObj.getJSONArray("data");
					if(!dataArray.isEmpty()){
						for(int j=0;j<dataArray.size();j++){
							JSONObject obj = dataArray.getJSONObject(j);
							CustomerInfoVo customerInfoVo = new CustomerInfoVo();
							customerInfoVo.setUid(obj.getString("uid"));
							customerInfoVo.setNickName(obj.getString("nick_name"));
							customerInfoVo.setHeadImg(obj.getString("head_img"));
							listUserInfo.add(customerInfoVo);

						}
					}
				}
			}
		} catch (Exception e) {
			LogUtil.error(logger, "【批量查询用户信息异常】e={}",e);
		}
		return listUserInfo;
	}



	/**
	 * 
	 * 注册用户
	 *
	 * @author yd
	 * @created 2017年8月29日 下午4:48:19
	 *
	 * @param appUid
	 * @return
	 */
	@Override
	public DataTransferObject registerChatdemoui(String uid) {

		DataTransferObject dto = new DataTransferObject();
		if(Check.NuNStr(uid)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("uid不存在");
			return dto;
		}
		String result = null;
		String url  = null;
		try {

			Map<String, String> headerMap = fillHeaderMap();

			if(headerMap == null){
				LogUtil.error(logger, "【注册环信-获取toekn失败】");
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("注册环信获取toekn失败");
				return dto;
			}

			Map<String, String> param = new HashMap<String, String>();
			param.put("username",MessageConst.IM_UID_PRE+uid);
			param.put("password",MessageConst.IM_USER_PASSARD);
			url = HUANXIN_REGISTER_USER_POSET;
			result = CloseableHttpsUtil.sendPost(url, param, headerMap);

			if(!Check.NuNStr(result)){
				Map<String, Object> resMap = (Map<String, Object>) JsonEntityTransform.json2Map(result);
				if(!Check.NuNMap(resMap)){
					Object errorObj = resMap.get("error");
					if(!Check.NuNObj(errorObj)){
						LogUtil.info(logger, "【注册环信失败】result={}", result);
						dto.setErrCode(DataTransferObject.ERROR);
						Object desObj = resMap.get("error_description");
						dto.setMsg(desObj == null?"注册环信失败":desObj+"");
						return dto;
					}
					dto.putValue("result", result);
				}
			}
		} catch (Exception e) {
			LogUtil.error(logger, "【注册环信失败】url={},result={},uid={},e={}", url,result,uid,e);
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("注册环信失败");

		}

		return dto;
	}



	/**
	 * 
	 * 转让群组
	 *
	 * @author yd
	 * @created 2017年9月6日 下午3:50:48
	 *
	 * @param newOwner  新群组uid
	 * @param groupId   群组号
	 * @return
	 */
	@Override
	public DataTransferObject transferGroup(String newOwnerUid, String groupId) {
		
		DataTransferObject dto = new DataTransferObject();
		
		if(Check.NuNStr(newOwnerUid)||Check.NuNStr(groupId)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("组号或新群组uid不存在");
			return dto;
		}
		
		String result = null;
		String url  = null;
		try {

			Map<String, String> headerMap = fillHeaderMap();

			if(headerMap == null){
				LogUtil.error(logger, "【转让群组-获取toekn失败】");
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("转让群组获取toekn失败");
				return dto;
			}

			Map<String, String> param = new HashMap<String, String>();
			param.put("newowner",MessageConst.IM_UID_PRE+newOwnerUid);
			url = HUANXIN_TRANSFER_GROUP_PUT.replace("#{group_id}", groupId);
			result = CloseableHttpsUtil.sendPut(url, JsonEntityTransform.Object2Json(param), headerMap);

			if(!Check.NuNStr(result)){
				Map<String, Object> resMap = (Map<String, Object>) JsonEntityTransform.json2Map(result);
				if(!Check.NuNMap(resMap)){
					Object errorObj = resMap.get("error");
					if(!Check.NuNObj(errorObj)){
						LogUtil.info(logger, "【转让群组失败】result={}", result);
						dto.setErrCode(DataTransferObject.ERROR);
						Object desObj = resMap.get("error_description");
						dto.setMsg(desObj == null?"转让群组失败":desObj+"");
						return dto;
					}
					dto.putValue("result", result);
				}
			}
		} catch (Exception e) {
			LogUtil.error(logger, "【转让群组失败】url={},result={},uid={},e={}", url,result,newOwnerUid,e);
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("转让群组失败");
		}
		return dto;
	}
}
