package com.zra.system.logic;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zra.system.entity.UserAccountEntity;
import com.zra.system.service.UserAccountService;

/**
 * 用户logic
 * @author wangws21 2016-8-1
 */
@Component
public class UserAccountLogic {
	
	@Autowired
	private UserAccountService userAccountService;
	
	/**
	 * 判断用户是否为系统管理员
	 * @param userId 用户id
	 * @return true/false
	 */
	public boolean isAdmin(String userId){
		return userAccountService.isAdmin(userId);
	}
	
	/**
	 * 根据userid获取useraccount
	 * @param userId 用户id
	 * @return useraccount
	 */
	public UserAccountEntity getUserAccountByUserId(@Param("userId")String userId){
		return userAccountService.getUserAccountByUserId(userId);
	}

	/**
	 * 是否为管家
	 * wangws21 2016-8-8
	 * @param userId 用户id
	 * @return int
	 */
	public boolean isZO(String userId){
		return userAccountService.isZO(userId);
	}
	
	/**
	 * 判断用户是否是项目经理,是否是管家，是否是营销，是否是系统管理员
	 * @author tianxf9
	 * @param userId
	 * @return
	 */
	public Map<String,Boolean> getUserRoles(String userId) {
		
		Map<String,Boolean> resultMap = new HashMap<String,Boolean>();
		resultMap.put("isAdmin", this.userAccountService.isAdmin(userId));
		resultMap.put("isPM", this.userAccountService.isManager(userId));
		resultMap.put("isZO", this.userAccountService.isZO(userId));
		resultMap.put("isMK", this.userAccountService.isMK(userId));
		resultMap.put("isNewAdmin", this.userAccountService.isNewAdmin(userId));
		return resultMap;
	}

	public boolean isZOOther(String zoId) {
		return userAccountService.isZOOther(zoId);
	}
}
