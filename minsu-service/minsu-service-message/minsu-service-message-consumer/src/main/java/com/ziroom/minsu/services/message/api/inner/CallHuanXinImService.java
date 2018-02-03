/**
 * @FileName: CallHuanXinImService.java
 * @Package com.ziroom.minsu.services.message.api.inner
 * 
 * @author yd
 * @created 2017年7月28日 下午2:23:28
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.message.api.inner;

import java.util.List;

import com.asura.framework.base.entity.DataTransferObject;
import com.ziroom.minsu.services.message.dto.AppGroupsPageInfo;
import com.ziroom.minsu.services.message.dto.GagMemberDto;
import com.ziroom.minsu.services.message.dto.GroupInfoDto;
import com.ziroom.minsu.services.message.dto.GroupMemberDto;
import com.ziroom.minsu.services.message.dto.GroupMemberPageInfoDto;
import com.ziroom.minsu.services.message.dto.HuanxinGroupDto;
import com.ziroom.minsu.services.message.dto.ManagerMerberDto;
import com.ziroom.minsu.services.message.dto.SendImMsgRequest;
import com.ziroom.minsu.services.message.entity.CustomerInfoVo;

/**
 * <p>调用 环信 接口的代理</p>
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
public interface CallHuanXinImService {


	/**
	 * 
	 * 添加群组
	 *
	 * @author yd
	 * @created 2017年7月28日 下午2:25:21
	 *
	 * @return
	 */
	public DataTransferObject addGroup(HuanxinGroupDto huanxinGroupDto);

	/**
	 * 
	 * 添加 单个 群成员
	 *
	 * @author yd
	 * @created 2017年7月31日 上午9:42:16
	 *
	 * @param groupId  群组id
	 * @param uid    ziroom的用户uid
	 * @return
	 */
	public DataTransferObject addOneGroupMember(String groupId,String uid,int num);


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
	public DataTransferObject removeOneGroupMember(String groupId,String uid);


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
	public DataTransferObject addManyGroupMember(GroupMemberDto  groupMemberDto,int num);


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
	public DataTransferObject removeManyGroupMember(GroupMemberDto  groupMemberDto);

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
	public DataTransferObject queryGroupMemberByPage(GroupMemberPageInfoDto groupMemberPageInfoDto);

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
	public  DataTransferObject queryAppGroupsByPage(AppGroupsPageInfo appGroupsPageInfo);

	/**
	 * 
	 * 禁言
	 *
	 * @author yd
	 * @created 2017年8月3日 下午7:18:51
	 *
	 * @return
	 */
	public DataTransferObject addGagMember(GagMemberDto gagMemberDto);

	/**
	 * 
	 * 解除禁言
	 *
	 * @author yd
	 * @created 2017年8月3日 下午7:18:51
	 *
	 * @return
	 */
	public DataTransferObject removeGagMember(GagMemberDto gagMemberDto);


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
	public DataTransferObject addAdminMember(ManagerMerberDto managerMerberDto);

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
	public DataTransferObject deleteAdminMember(ManagerMerberDto managerMerberDto);

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
	public DataTransferObject removeGroupByGroupId(String groupId);


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
	public  DataTransferObject updateGroupByGroupId(GroupInfoDto groupInfoDto);


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
	public  DataTransferObject queryGroupInfo(List<String> groupIds);

	/**
	 * 
	 * 根据uids 获取用户基本信息
	 *
	 * @author yd
	 * @created 2017年8月9日 上午11:36:29
	 *
	 * @param uids
	 */
	public List<CustomerInfoVo> queryUserInfoByUids(List<String> uidList);
	

	/**
	 * 发送IM消息
	 * @author yd
	 * @created 2017年8月4日 下午3:45:25
	 *
	 * @param jsonParam
	 * @return
	 */
	public void sendImMsg(SendImMsgRequest sendImMsgRequest);


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
	public DataTransferObject  registerChatdemoui(String uid);
	
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
	public DataTransferObject transferGroup(String newOwnerUid,String groupId);
}
