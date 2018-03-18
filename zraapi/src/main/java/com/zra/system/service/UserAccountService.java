package com.zra.system.service;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zra.common.utils.SysConstant;
import com.zra.system.dao.UserAccountMapper;
import com.zra.system.entity.UserAccountEntity;

/**
 * @author wangws21 2016-8-1
 * 用户服务
 */
@Service
public class UserAccountService {
	
	@Autowired
	private UserAccountMapper userAccountMapper;
	
	/**
	 * 判断用户是否为系统管理员
	 * @param userId 用户id
	 * @return true/false
	 */
	public boolean isAdmin(String userId){
		int count = userAccountMapper.isAdmin(SysConstant.ADMIN_ROLE_ID, userId);
		return count > 0;
	}
	
	/**
	 * 根据userid获取useraccount
	 * @param userId 用户id
	 * @return useraccount
	 */
	public UserAccountEntity getUserAccountByUserId(@Param("userId")String userId){
		return userAccountMapper.getUserAccountByUserId(userId);
	}
	
	/**
	 * 是否为管家
	 * wangws21 2016-8-8
	 * @param roleId 角色id
	 * @param userId 用户id
	 * @return int
	 */
	public boolean isZO(String userId){
		int count = userAccountMapper.isZo(SysConstant.ZO_ROLE_NAME, userId);
		return count > 0;
	}
	
	/**
	 * 判断用户是否项目经理
	 * @author tianxf9
	 * @param userId
	 * @return
	 */
	public boolean isManager(String userId) {
		int count = userAccountMapper.isManager(SysConstant.PMID, userId);
		return count>0;
	}
	
	/**
	 * 判断用户是否是营销人员
	 * @author tianxf9
	 * @param userId
	 * @return
	 */
	public boolean isMK(String userId) {
		int count = userAccountMapper.isRole(SysConstant.MK_ROLE_ID, userId);
		return count>0;
		
	}
	
	/**
	 * 这里建立了一个新的判断是否是 管理员的原因是原来的方法是更具tuseraccount的fid=userid判断的，这种方法以后不再使用，使用的是temployee的fid=useid
	 * 想修改来着，但是发现现有稳定模块用的挺多的。。。。以后再说吧。。。。。。
	 * @author tianxf9
	 * @param userId
	 * @return
	 */
    public boolean isNewAdmin(String userId) {
    	int count = userAccountMapper.isRole(SysConstant.ADMIN_ROLE_ID, userId);
    	return count>0;
    }

	public boolean isZOOther(String zoId) {
		Integer count = userAccountMapper.isZOOther(SysConstant.ZO_ROLE_NAME, zoId);
		return count != null && count > 0;
	}
}
