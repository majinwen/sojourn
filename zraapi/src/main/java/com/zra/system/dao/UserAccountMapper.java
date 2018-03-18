package com.zra.system.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.zra.system.entity.UserAccountEntity;

/**
 * 用户dao
 * @author wangws21 2016-8-1
 */
@Repository
public interface UserAccountMapper {

	/**
	 * 是否为管理员
	 * @param roleId 管理员角色id
	 * @param userId 用户id
	 * @return int
	 */
	int isAdmin(@Param("roleId") String roleId, @Param("userId") String userId);
	
	/**
	 * 根据userid获取useraccount
	 * @param userId 用户id
	 * @return useraccount
	 */
	UserAccountEntity getUserAccountByUserId(@Param("userId")String userId);
	
	/**
	 * 是否为管家
	 * wangws21 2016-8-8
	 * @param roleId 角色id
	 * @param userId 用户id
	 * @return int
	 */
	int isZo(@Param("roleName") String roleName, @Param("userId") String userId);
	
	/**
	 * 判断用户是否为项目经理
	 * @param roleId
	 * @param userId
	 * @return
	 */
	int isManager(@Param("roleId") String roleId, @Param("userId") String userId);
	
	/**
	 * 角色id判断用户是否有该角色
	 * @author tianxf9
	 * @param roleId
	 * @param userId
	 * @return
	 */
	int isRole(@Param("roleId")String roleId,@Param("userId")String userId);

    Integer isZOOther(@Param("roleName") String roleName, @Param("userId") String userId);
}
