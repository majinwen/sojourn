/**
 * @FileName: UserPermissionService.java
 * @Package com.ziroom.minsu.services.basedata.api.inner
 * 
 * @author bushujie
 * @created 2016年3月9日 上午11:22:55
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.basedata.api.inner;

import com.ziroom.minsu.services.basedata.dto.SaveUserInfoRequest;

/**
 * <p>后台用户权限操作接口</p>
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
public interface UserPermissionService {
	
	/**
	 * 
	 * 条件查询后台用户列表
	 *
	 * @author bushujie
	 * @created 2016年3月9日 上午11:26:09
	 *
	 * @param paramJson
	 * @return
	 */
	public String searchCurrentuserList(String paramJson);

	/**
	 * 修改用户信息
	 * @param userInfo
	 * @return
	 */
	String saveUserInfo(SaveUserInfoRequest userInfo);


	/**
	 * 初始化当前的用户的 修改信息
	 * @param userFid
	 * @return
	 */
	String initSaveUserInfo(String userFid);

	/**
	 * 
	 * 员工分页列表
	 *
	 * @author bushujie
	 * @created 2016年3月12日 下午4:15:43
	 *
	 * @param paramJson
	 * @return
	 */
	public String employeePageList(String paramJson);
	
}
