/**
 * @FileName: InviteCreateOrderCmsService.java
 * @Package com.ziroom.minsu.services.cms.api.inner
 * 
 * @author loushuai
 * @created 2017年12月2日 下午2:13:06
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.cms.api.inner;

import com.ziroom.minsu.services.cms.dto.InviteCmsRequest;

/**
 * <p>邀请好友下单接口</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author loushuai
 * @since 1.0
 * @version 1.0
 */
public interface InviteCreateOrderCmsService {

	/**
	 * 获取邀请人详情页
	 *
	 * @author loushuai
	 * @created 2017年12月2日 下午2:39:03
	 *
	 * @param inviterDetailRequest
	 */
	public String getInviterDetail(String param);

	/**
	 * 根据邀请人uid获取邀请码
	 *
	 * @author loushuai
	 * @created 2017年12月4日 下午4:48:35
	 *
	 * @param object2Json
	 * @return
	 */
	public String getOrInitInviteCode(String param);

	/**
	 * 获取邀请人积分可兑换列表
	 *
	 * @author loushuai
	 * @created 2017年12月5日 下午2:15:22
	 *
	 * @param inviterDetailRequest
	 * @return
	 */
	public String getCouponList(String param);

	/**
	 * 邀请人发起积分兑换
	 *
	 * @author loushuai
	 * @created 2017年12月5日 下午4:07:56
	 *
	 * @param object2Json
	 * @return
	 */
	public String pointsExchange(String param);

	/**
	 * 根据邀请人的uid和积分类型查询出对应的档位
	 *
	 * @param * @param uid
	 * @param * @param pointsSource
	 * @return java.lang.String
	 * @author yanb
	 * @created 2017年12月08日 17:44:56
	 */
	public String getPointTiersByUidSource(String paramJson);

	/**
	 * 为uid增加积分等事务操作
	 * 定时任务调用
	 *
	 * @param * @param null
	 * @return
	 * @author yanb
	 * @created 2017年12月13日 16:09:20
	 */
	public String addPointsByList(String paramJson);

}
