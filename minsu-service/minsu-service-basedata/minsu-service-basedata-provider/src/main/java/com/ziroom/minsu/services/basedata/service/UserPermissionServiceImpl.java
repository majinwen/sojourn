/**
 * @FileName: UserPermissionServiceImpl.java
 * @Package com.ziroom.minsu.services.basedata.service
 * 
 * @author bushujie
 * @created 2016年3月9日 上午11:14:06
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.basedata.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.ziroom.minsu.services.basedata.entity.CurrentuserCityVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.minsu.entity.sys.CityEntity;
import com.ziroom.minsu.entity.sys.CurrentuserCityEntity;
import com.ziroom.minsu.entity.sys.CurrentuserEntity;
import com.ziroom.minsu.entity.sys.CurrentuserRoleEntity;
import com.ziroom.minsu.entity.sys.EmployeeEntity;
import com.ziroom.minsu.entity.sys.RoleEntity;
import com.ziroom.minsu.services.basedata.dao.CityDao;
import com.ziroom.minsu.services.basedata.dao.CurrentuserCityDao;
import com.ziroom.minsu.services.basedata.dao.CurrentuserDao;
import com.ziroom.minsu.services.basedata.dao.CurrentuserRoleDao;
import com.ziroom.minsu.services.basedata.dao.EmployeeDao;
import com.ziroom.minsu.services.basedata.dao.ResourceDao;
import com.ziroom.minsu.services.basedata.dao.RoleDao;
import com.ziroom.minsu.services.basedata.dao.RoleResDao;
import com.ziroom.minsu.services.basedata.dto.CurrentuserRequest;
import com.ziroom.minsu.services.basedata.dto.EmployeeRequest;
import com.ziroom.minsu.services.basedata.dto.RoleRequest;
import com.ziroom.minsu.services.basedata.entity.CurrentuserVo;
import com.ziroom.minsu.services.basedata.entity.RoleVo;

/**
 * <p>后台用户权限操作业务层</p>
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
@Service("basedata.userPermissionServiceImpl")
public class UserPermissionServiceImpl {
	
	@Resource(name="basedata.currentuserDao")
	private CurrentuserDao currentuserDao;

	@Resource(name = "basedata.currentuserRoleDao")
	private CurrentuserRoleDao currentuserRoleDao;

	@Resource(name="basedata.roleDao")
	private RoleDao roleDao;
	
	@Resource(name="basedata.roleResDao")
	private RoleResDao roleResDao;
	
	@Resource(name="basedata.resourceDao")
	private ResourceDao resourceDao;

	@Resource(name = "basedata.cityDao")
	private CityDao sysCityDao;

	@Resource(name="basedata.employeeDao")
	private EmployeeDao employeeDao;
	
	@Resource(name="basedata.currentuserCityDao")
	private CurrentuserCityDao currentuserCityDao;




	/**
	 * 修改用户信息
	 * @param userInfo
	 * @return
	 */
	public void saveUserInfo(CurrentuserVo userInfo){

		CurrentuserEntity user = new CurrentuserEntity();
		BeanUtils.copyProperties(userInfo, user);
		//更新用户的信息
		currentuserDao.saveCurrentuserById(user);
		//删除用户的角色信息
		currentuserRoleDao.delCurrentuserRoleByUserFid(userInfo.getFid());
		//重新赋值用户的角色信息
		for(String roleId:userInfo.getRoleFidList()){
			if(!Check.NuNObj(roleId)){
				CurrentuserRoleEntity currentuserRoleEntity = new CurrentuserRoleEntity();
				currentuserRoleEntity.setRoleFid(roleId);
				currentuserRoleEntity.setCurrenuserFid(user.getFid());
				currentuserRoleDao.insertCurrentuserRole(currentuserRoleEntity);
			}
		}
		//删除用户负责区域
		currentuserCityDao.delCurrentuserCity(userInfo.getFid());
		//添加新的负责区域
		for(String code:userInfo.getAreaCodeList()){
			String[] codeS=code.split(":");
			CurrentuserCityEntity currentuserCityEntity=new CurrentuserCityEntity();
			currentuserCityEntity.setCurrentuserFid(userInfo.getFid());
			for(int i=0;i<codeS.length;i++){
				if(i==0){
					currentuserCityEntity.setNationCode(codeS[0]);
				}
				if(i==1){
					currentuserCityEntity.setProvinceCode(codeS[1]);				
				}
				if(i==2){
					currentuserCityEntity.setCityCode(codeS[2]);				
				}
				if(i==3){
					currentuserCityEntity.setAreaCode(codeS[3]);				
				}
			}
			currentuserCityDao.insertCurrentuserCity(currentuserCityEntity);
		}
	}

	/**
	 * 获取当前用户的权限列表
	 * @author afi
	 * @created 2016年3月12日
	 *
	 */
	public List<RoleEntity> getRoleListByUserFid(String userFid){
		return roleDao.findRoleListByUserFid(userFid);
	}




	/**
	 * 获取城市列表
	 * @author afi
	 * @created 2016年3月12日
	 *
	 */
	public List<CityEntity> getCityList(){
		return sysCityDao.getCityList();
	}


	/**
	 * 通过userFid 获取当前的用户信息
	 * @param userFid
	 * @return
	 */
	public CurrentuserEntity getCurrentuserByFid(String userFid){
		return currentuserDao.getCurrentuserByFid(userFid);
	}
	/**
	 * 后台用户列表
	 *
	 * @author bushujie
	 * @created 2016年3月9日 上午11:20:09
	 *
	 * @param currentuserRequest
	 * @return
	 */
	public PagingResult<CurrentuserVo>findCurrentuserPageList(CurrentuserRequest currentuserRequest){
		return currentuserDao.findCurrentuserPageList(currentuserRequest);
	}

	/**
	 * 后台角色列表
	 *
	 * @author liujun
	 * @created 2016-3-10 下午7:38:44
	 *
	 * @param roleRequest
	 * @return
	 */
	public PagingResult<RoleVo> findRolePageList(RoleRequest roleRequest) {
		return roleDao.findRolePageList(roleRequest);
	}

	/**
	 * 根据角色逻辑id查询角色
	 *
	 * @author liujun
	 * @created 2016-3-12 下午4:38:13
	 *
	 * @param roleFid
	 * @return
	 */
	public RoleEntity findRoleByFid(String roleFid) {
		return roleDao.findRoleByFid(roleFid);
	}
	
	/**
	 * 
	 * 分页查询员工列表
	 *
	 * @author bushujie
	 * @created 2016年3月12日 下午4:05:02
	 *
	 * @param employeeRequest
	 * @return
	 */
	public PagingResult<EmployeeEntity> findEmployeeForPage (EmployeeRequest employeeRequest){
		return employeeDao.findEmployeeForPage(employeeRequest);
	}
	
	/**
	 * 
	 * 插入后台用户信息
	 *
	 * @author bushujie
	 * @created 2016年3月12日 下午4:33:44
	 *
	 * @param currentuserVo
	 */
	public void insertCurrentuser(CurrentuserVo currentuserVo){
		currentuserVo.setFid(UUIDGenerator.hexUUID());
		//插入用户
		currentuserDao.insertCurrentuser(currentuserVo);
		//插入用户角色关系
		for(String roleFid:currentuserVo.getRoleFidList()){
			Map<String, Object> paramMap=new HashMap<String, Object>();
			paramMap.put("currenuserFid", currentuserVo.getFid());
			paramMap.put("roleFid", roleFid);
			currentuserDao.insertCurrentuserRole(paramMap);
		}
		//插入用户负责区域
		for(String code:currentuserVo.getAreaCodeList()){
			String[] codeS=code.split(":");
			CurrentuserCityEntity currentuserCityEntity=new CurrentuserCityEntity();
			currentuserCityEntity.setCurrentuserFid(currentuserVo.getFid());
			for(int i=0;i<codeS.length;i++){
				if(i==0){
					currentuserCityEntity.setNationCode(codeS[0]);
				}
				if(i==1){
					currentuserCityEntity.setProvinceCode(codeS[1]);				
				}
				if(i==2){
					currentuserCityEntity.setCityCode(codeS[2]);				
				}
				if(i==3){
					currentuserCityEntity.setAreaCode(codeS[3]);				
				}
			}
			currentuserCityDao.insertCurrentuserCity(currentuserCityEntity);
		}
	}

	/**
	 * 根据角色逻辑id更新角色
	 *
	 * @author liujun
	 * @created 2016-3-12 下午5:08:22
	 *
	 * @param role
	 */
	public void updateRole(RoleEntity role) {
		roleDao.updateRole(role);
	}
	/**
	 * 
	 * 获取城市列表
	 *
	 * @author bushujie
	 * @created 2016年3月15日 下午5:37:17
	 *
	 * @return
	 */
	public List<CityEntity> findAllCity(){
		return sysCityDao.getCityList();
	}
	
	/**
	 * 
	 * 查询后台用户信息
	 *
	 * @author bushujie
	 * @created 2016年3月16日 上午11:39:22
	 *
	 * @return
	 */
	public CurrentuserVo getCurrentuserVoByfid(String fid){
		return currentuserDao.getCurrentuserVoByfid(fid);
	}
	/**
	 * 
	 * 用户名查询用户信息
	 *
	 * @author bushujie
	 * @created 2016年3月16日 下午8:08:57
	 *
	 * @param userAccount
	 * @return
	 */
	public CurrentuserVo getCurrentuserByAccount(String userAccount){
		return currentuserDao.getCurrentuserEntityByAccount(userAccount);
	}

	/**
	 * 更新用户信息
	 *
	 * @author liujun
	 * @created 2016年5月16日
	 *
	 * @param user
	 * @return
	 */
	public int updateCurrentuserByFid(CurrentuserEntity user) {
		return currentuserDao.updateCurrentuserByFid(user);
	}
	
	/**
	 * 
	 * 查询用户权限类型
	 *
	 * @author bushujie
	 * @created 2016年10月26日 上午11:26:58
	 *
	 * @param resUrl
	 * @param currenuserFid
	 * @return
	 */
	public List<Integer> findRoleTypeByMenu(String resUrl,String currentuserFid){
		Map<String, Object> paramMap=new HashMap<String,Object>();
		paramMap.put("resUrl", resUrl);
		paramMap.put("currentuserFid", currentuserFid);
		return currentuserDao.findRoleTypeByMenu(paramMap);
	}
	
	/**
	 * 
	 * 查询用户负责区域
	 *
	 * @author bushujie
	 * @created 2016年10月26日 下午3:28:32
	 *
	 * @param currenuserFid
	 * @return
	 */
	public List<CurrentuserCityEntity> findCuserCityByUserFid(String currentuserFid){
		return currentuserCityDao.findCuserCityByUserFid(currentuserFid);
	}

	/**
	 * 保存用户区域关系
	 * @author jixd
	 * @created 2016年12月08日 16:38:17
	 * @param
	 * @return
	 */
	public int insertCurrentuserCity(CurrentuserCityEntity currentuserCityEntity){
		return currentuserCityDao.insertCurrentuserCity(currentuserCityEntity);
	}

	/**
	 * 删除用户关联城市
	 * @author jixd
	 * @created 2016年12月08日 17:59:35
	 * @param
	 * @return
	 */
	public int delCurrentuserCity(String fid){
		return currentuserCityDao.delCurrentuserCity(fid);
	}

	/**
	 * 获取用户的城市列表
	 * @author jixd
	 * @created 2016年12月08日 20:30:56
	 * @param
	 * @return
	 */
	public List<CurrentuserCityVo> getCurrentuserCity(String fid){
		return currentuserCityDao.getCurrentuserCity(fid);
	}
}
