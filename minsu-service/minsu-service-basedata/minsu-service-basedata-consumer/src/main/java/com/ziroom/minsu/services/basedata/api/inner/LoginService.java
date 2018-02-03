/**
 * @FileName: LoginService.java
 * @Package com.ziroom.minsu.services.basedata.api.inner
 * 
 * @author bushujie
 * @created 2016年3月16日 下午8:12:06
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.basedata.api.inner;

/**
 * <p>登录相关接口</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author bushujie
 * @since 1.0
 * @version 1.0
 */
public interface LoginService {
	
	/**
	 * 
	 *  登录获取账户信息
	 *
	 * @author bushujie
	 * @created 2016年3月16日 下午8:13:34
	 *
	 * @param userAccount
	 * @return
	 */
	public String getCurrentuserInfo(String userAccount);
	
	/**
	 * 
	 *  登录用户权限树
	 *
	 * @author bushujie
	 * @created 2016年3月16日 下午10:42:43
	 *
	 * @param currentuserId
	 * @return
	 */
	public String currentuserReslist(String currentuserId);
	
	/**
	 * 
	 * 根据url 校验当前菜单是否是特权菜单
	 * 异常情况：一个url对应多个菜单，只要有一个是特权菜单，就默认所有都是特权菜单（此情况，在前台添加菜单时候没做校验，故后期得加上）
	 *
	 *  0=不是特权菜单  1=是特权菜单
	 * @author yd
	 * @created 2016年11月1日 上午9:21:41
	 *
	 * @param resurl
	 * @return
	 */
	public String  checkAuthMenuByResurl(String resUrl);
	
}
