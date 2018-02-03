/**
 * @FileName: CustomerMsgManager.java
 * @Package com.ziroom.minsu.services.customer.api.inner
 * 
 * @author jixd
 * @created 2016年4月26日 下午4:55:13
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.customer.api.inner;

import com.ziroom.minsu.entity.customer.CustomerBaseMsgExtEntity;

/**
 * <p>用户信息</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author jixd
 * @since 1.0
 * @version 1.0
 */
public interface CustomerMsgManagerService {
	/**
	 * 
	 * 条件查询客户列表
	 *
	 * @author jixd
	 * @created 2016年4月25日 上午9:58:57
	 *
	 * @param customerBaseMsgDto
	 * @return
	 */
	String queryCustomerBaseMsg(String reuest);


    /**
     * 获取角色信息列表
     * @author afi
     * @param reuest
     * @return
     */
    String queryCustomerRoleMsg(String reuest);
	/**
	 * 
	 * 查询用户详情
	 *
	 * @author jixd
	 * @created 2016年4月25日 上午9:59:45
	 *
	 * @param uid
	 * @return
	 */
	String getCustomerDetail(String uid);
	
	/**
	 * 
	 * 获取带图片的用户详情
	 *
	 * @author jixd
	 * @created 2016年5月25日 下午9:55:14
	 *
	 * @param uid
	 * @return
	 */
	String getCustomerDetailImage(String uid);


    /**
     * 获取用户的信息
     * @author afi
     * @param uid
     * @return
     */
    String getCustomerBaseMsgEntitybyUid(String uid);
	/**
	 * 
	 * 更新用户信息
	 *
	 * @author jixd
	 * @created 2016年4月26日 上午11:43:29
	 *
	 * @param uid
	 * @return
	 */
	String updateCustomerBaseMsg(String paramJson);
	
	/**
	 * 
	 * 保存操作记录
	 *
	 * @author jixd
	 * @created 2016年4月26日 上午11:29:27
	 *
	 * @param paramJson
	 * @return
	 */
	String saveCustomerOperHistory(String paramJson);
	
	/**
	 * 
	 * 增加客户图片信息
	 *
	 * @author jixd
	 * @created 2016年4月25日 下午10:46:39
	 *
	 * @param paramJson
	 * @return
	 */
	String insertCustomerPicMsg(String paramJson);


	/**
	 * 上传头像 上传成功删除同类型的其他照片
	 * @author afi
	 * @created 2017年2月23日 下午15:46:39
	 *
	 * @param paramJson
	 * @return
	 */
	String insertCustomerPicMsgAndDelOthers(String paramJson);

	
	/**
	 * 批量增加客户图片信息
	 * @author lishaochuan
	 * @create 2016年5月7日下午5:57:06
	 * @param paramJson
	 * @return
	 */
	String insertCustomerPicMsgList(String paramJson);
	
	/**
	 * 
	 * 更新客户图片信息
	 *
	 * @author jixd
	 * @created 2016年4月25日 下午10:47:23
	 *
	 * @param paramJson
	 * @return
	 */
	String updateCustomerPicMsg(String paramJson);
	
	/**
	 * 
	 * 根据uid和类型查询pic实体
	 *
	 * @author jixd
	 * @created 2016年4月25日 下午10:56:05
	 *
	 * @param paramJson
	 * @return
	 */
	String getCustomerPicByType(String paramJson);
	
	/**
	 * 
	 * 根据uid和类型查询piclist
	 *
	 * @author jixd
	 * @created 2016年6月27日 下午1:01:23
	 *
	 * @param paramJson
	 * @return
	 */
	String getCustomerPicListByType(String paramJson);
	/**
	 * 
	 * 保存用户相关信息
	 * 1.用户基本信息  2.用户照片信息  3.用户银行卡信息
	 *
	 * @author yd
	 * @created 2016年5月6日 下午4:59:41
	 *
	 * @param customerInfo
	 * @return
	 */
	String  saveCustomerInfo(String customerInfo);
	
	/**
	 * 
	 * 通过用户uid获取用户基本(redis 没有 从数据库拿)
	 *
	 * @author yd
	 * @created 2016年5月7日 下午3:10:09
	 *
	 * @param uid
	 * @return
	 */
	String getCutomerVo(String uid);
	/**
	 * 
	 * 通过用户uid去redis获取用户数据
	 *
	 * @author yd
	 * @created 2016年5月7日 下午3:10:09
	 *
	 * @param uid
	 * @return
	 */
	public String getCutomerVoFromRedis(String uid);
	
	/**
	 * 
	 * 通过用户uid去获取用户信息，直接从库中获取  这里主要给mapp端提供的接口  那边用户信息保存在session中
	 *
	 * @author yd
	 * @created 2016年5月7日 下午3:10:09
	 *
	 * @param uid
	 * @return
	 */
	public String getCutomerVoFromDb(String uid);
	
	/**
	 * 
	 * get entity by uid
	 *
	 * @author yd
	 * @created 2016年5月26日 下午9:48:38
	 *
	 * @param uid
	 * @return
	 */
	public String selectCustomerExtByUid(String uid);
	
	/**
	 * 
	 * 保存用户扩展信息
	 *
	 * @author yd
	 * @created 2016年5月26日 下午9:51:41
	 *
	 * @param customerBaseMsgExt
	 * @return
	 */
	public String insertCustomerExt(String customerBaseMsgExt);
	
	/**
	 * 
	 * update entity by uid
	 *
	 * @author yd
	 * @created 2016年5月26日 下午9:54:55
	 *
	 * @param customerBaseMsgExt
	 * @return
	 */
	public String updateCustomerExtByUid(String customerBaseMsgExt);


	/**
	 * 根据uid获取图片
	 * @author wangwt
	 * @created 2017年06月20日 15:21:39
	 * @param uid
	 * @return
	 */
	String getCustomerPicByUid(String uid);

	/**
	 * 保存用户昵称 和 个人介绍
	 * @author wangwt
	 * @created 2017年06月22日 15:10:58
	 * @param paramJson
	 * @return
	 */
	String saveNickNameAndIntroduce(String paramJson);


	/**
	 * 审核拒绝
	 *
	 * @author loushuai
	 * @created 2017年8月9日 上午11:20:42
	 *
	 * @param object2Json
	 * @return
	 */
	String updateCustomerUpdateFieldAuditNewlogByFid(String paramJson);


	/**
	 * 审核后，修改base，ext，pic三个表中的数据
	 *
	 * @author loushuai
	 * @created 2017年8月9日 下午2:37:51
	 *
	 * @param object2Json
	 * @return
	 */
	String updateBaseAndExtOrPic(String paramJson);


	/**
	 * 根据t_customer_update_field_audit_newlog表的fid获取对象
	 *
	 * @author loushuai
	 * @created 2017年8月11日 下午7:44:41
	 *
	 * @param fieldHeadPicKey
	 * @return
	 */
	String getUpdateFieldAuditNewlogByFid(String updateFieldAuditNewlogFid);
	
	/**
	 * 
	 * 用户信息 审核通过
	 *
	 * @author yd
	 * @created 2017年9月12日 上午10:05:11
	 *
	 * @param paramJson
	 * @return
	 */
	public String auditedCustomerInfo(String paramJson);


	/**
	 * 修改用户个人介绍（不需要审核，如：troy上修改）
	 *
	 * @author loushuai
	 * @created 2017年11月16日 下午3:55:12
	 *
	 * @param ext
	 * @return
	 */
	String updateCustomerExtNotAudit(String paramJson);
}

