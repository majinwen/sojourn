/**
 * @FileName: ZryHuanxinImService.java
 * @Package com.ziroom.minsu.services.message.api.inner
 * 
 * @author yd
 * @created 2017年7月31日 下午4:07:15
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.message.api.inner;

import com.asura.framework.base.entity.DataTransferObject;



/**
 * <p>环信 im接口代理层</p>
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
public interface HuanxinImManagerService {

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
	  public String queryGroupMemberByPage(String jsonParam);
	  
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
	  public String  queryGroupMemberByPageFromMinsu(String jsonParam);
	  
	  /**
	   * 
	   * 添加群组
	   *
	   * @author yd
	   * @created 2017年8月1日 上午10:25:10
	   *
	   * @param jsonParam
	   * @return
	   */
	  public String addGroup(String jsonParam);
	  
	  /**
	   * 
	   *  添加群成员(多个或单个)
	   *
	   * @author yd
	   * @created 2017年8月1日 上午10:27:13
	   *
	   * @param jsonParam
	   * @return
	   */
	  
	  public String  addManyGroupMember(String jsonParam);
	  
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
	  public String queryAppGroupsByPage(String jsonParam);
	  
	  /**
	   * 
	   * 添加禁言
	   *
	   * @author yd
	   * @created 2017年8月1日 上午10:31:57
	   *
	   * @param jsonParam
	   * @return
	   */
	  public String addGagMember(String jsonParam);
	  
	  /**
	   * 
	   * 保存消息记录（app调用）
	   *
	   * @author yd
	   * @created 2017年8月3日 上午11:30:11
	   *
	   * @param jsonParam
	   * @return
	   */
	  public String saveMsgHuanxinImLog(String jsonParam);
	  
	  
	  /**
	   * 
	   * 解除禁言
	   *
	   * @author yd
	   * @created 2017年8月3日 下午7:18:51
	   *
	   * @return
	   */
	  public String removeGagMember(String jsonParam);
	  

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
	  public String addAdminMember(String jsonParam);
	  
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
	  public String deleteAdminMember(String jsonParam);
	  
	  
	  /**
	   * 
	   * 移除群成员
	   *
	   * @author yd
	   * @created 2017年8月4日 下午3:43:32
	   *
	   * @param jsonParam
	   * @return
	   */
	  public String removeGroupMembers(String jsonParam);
	  
	  /**
	   * 
	   * 根据群组id 删除群组
	   *
	   * @author yd
	   * @created 2017年8月4日 下午3:44:38
	   *
	   * @param jsonParam
	   * @return
	   */
	  public String removeGroupByGroupId(String jsonParam);
	  
	  /**
	   * 
	   * 跟新群组信息
	   *
	   * @author yd
	   * @created 2017年8月4日 下午3:45:25
	   *
	   * @param jsonParam
	   * @return
	   */
	  public String updateGroupByGroupId(String jsonParam);
	  
	  /**
	   * 
	   * 针对 环信成功 民宿失败的业务处理
	   *
	   * @author yd
	   * @created 2017年8月9日 下午5:46:25
	   *
	   * @param jsonParam
	   * @return
	   */
	  public void dealGroupOpfailed();

	/**
	 * 保存聊天用户屏蔽关系
	 *
	 * @author loushuai
	 * @created 2017年9月4日 下午3:26:43
	 *
	 * @param object2Json
	 * @return 
	 */
	public String saveOrUpdateMsgUserRel(String jsonParam);

	/**
	 * 查询聊天用户关系
	 *
	 * @author loushuai
	 * @created 2017年9月4日 下午5:22:19
	 *
	 * @param object2Json
	 */
	public String queryMsgUserRel(String jsonParam);
	
	/**
	 * 
	 * 转让群组
	 *
	 * @author yd
	 * @created 2017年9月6日 下午4:43:39
	 *
	 * @param jsonParam
	 * @return
	 */
	public String transferGroup(String jsonParam);

	/**
	 * 处理黄色图片
	 *
	 * @author loushuai
	 * @created 2017年9月7日 下午8:43:53
	 *
	 */
	public void dealImYellowPic();
	  
}
