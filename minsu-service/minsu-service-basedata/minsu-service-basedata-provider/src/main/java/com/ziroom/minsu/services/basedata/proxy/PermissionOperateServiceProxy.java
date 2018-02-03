/**
 * @FileName: PermissionOperateServiceProxy.java
 * @Package com.ziroom.minsu.services.basedata.proxy
 * 
 * @author bushujie
 * @created 2016年3月15日 下午3:56:59
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.basedata.proxy;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.ziroom.minsu.services.basedata.entity.CurrentuserCityVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.MessageSourceUtil;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.sys.CityEntity;
import com.ziroom.minsu.entity.sys.CurrentuserCityEntity;
import com.ziroom.minsu.entity.sys.CurrentuserEntity;
import com.ziroom.minsu.entity.sys.EmployeeEntity;
import com.ziroom.minsu.entity.sys.RoleEntity;
import com.ziroom.minsu.entity.sys.RoleResourceEntity;
import com.ziroom.minsu.services.basedata.api.inner.PermissionOperateService;
import com.ziroom.minsu.services.basedata.dto.CurrentuserRequest;
import com.ziroom.minsu.services.basedata.dto.EmployeeRequest;
import com.ziroom.minsu.services.basedata.dto.RoleRequest;
import com.ziroom.minsu.services.basedata.entity.CurrentuserVo;
import com.ziroom.minsu.services.basedata.entity.RoleVo;
import com.ziroom.minsu.services.basedata.entity.TreeNodeVo;
import com.ziroom.minsu.services.basedata.logic.ParamCheckLogic;
import com.ziroom.minsu.services.basedata.service.ResourceServiceImpl;
import com.ziroom.minsu.services.basedata.service.UserPermissionServiceImpl;
import com.ziroom.minsu.services.common.constant.MessageConst;

/**
 * <p>权限管理操作</p>
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
@Component("basedata.permissionOperateServiceProxy")
public class PermissionOperateServiceProxy implements PermissionOperateService{

	private static final Logger LOGGER = LoggerFactory.getLogger(PermissionOperateServiceProxy.class);

	@Resource(name="basedata.userPermissionServiceImpl")
	private UserPermissionServiceImpl userPermissionServiceImpl;
	
	@Resource(name="basedata.resourceServiceImpl")
	private ResourceServiceImpl resourceServiceImpl;

	@Resource(name="basedata.messageSource")
	private MessageSource messageSource;

	@Resource(name="basedata.paramCheckLogic")
	private ParamCheckLogic paramCheckLogic;


	/**
	 * 修改用户信息
	 * @param paramJson
	 * @return
	 */
	public String saveUserInfo(String paramJson){
		DataTransferObject dto = new DataTransferObject();
		//json参数转换
		CurrentuserVo currentuserVo=JsonEntityTransform.json2Object(paramJson, CurrentuserVo.class);
		if(Check.NuNObj(currentuserVo)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
			return dto.toJsonString();
		}
		if(Check.NuNObj(currentuserVo.getFid())){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
			return dto.toJsonString();
		}
		try{
			userPermissionServiceImpl.saveUserInfo(currentuserVo);
		}catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
			return dto.toJsonString();
		}
		return dto.toJsonString();

	}


	/**
	 * 初始化当前的用户的 修改信息
	 * @param userFid
	 * @return
	 */
	public String initSaveUserInfo(String userFid){
		DataTransferObject dto = new DataTransferObject();
		if (Check.NuNStr(userFid)) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
			return dto.toJsonString();
		}
		try{
			CurrentuserVo currentuserVo=userPermissionServiceImpl.getCurrentuserVoByfid(userFid);
			if(Check.NuNObj(currentuserVo)) {
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.NOT_FOUND));
				return dto.toJsonString();
			}
			currentuserVo.setRoles(userPermissionServiceImpl.getRoleListByUserFid(userFid));
			dto.putValue("userInfo", currentuserVo);
		}catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
			return dto.toJsonString();
		}
		return dto.toJsonString();

	}

	@Override
	public String searchCurrentuserList(String paramJson) {
		DataTransferObject dto = null;
		try{
			CurrentuserRequest currentuserRequest=JsonEntityTransform.json2Object(paramJson, CurrentuserRequest.class);
            //条件查询后台用户
			PagingResult<CurrentuserVo> pr=userPermissionServiceImpl.findCurrentuserPageList(currentuserRequest);
            dto = new DataTransferObject();
            dto.putValue("list", pr.getRows());
            dto.putValue("total", pr.getTotal());
		}catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
            dto = new DataTransferObject();
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
            return dto.toJsonString();
		}
		return dto.toJsonString();
	}

	/* 条件查询后台角色列表
	 * @see com.ziroom.minsu.services.basedata.api.inner.UserPermissionService#searchRoleList(java.lang.String)
	 */
	@Override
	public String searchRoles(String paramJson) {
		DataTransferObject dto = new DataTransferObject();
		try {
			RoleRequest roleRequest = JsonEntityTransform.json2Object(paramJson, RoleRequest.class);
			// 条件查询后台用户
			PagingResult<RoleVo> pr = userPermissionServiceImpl.findRolePageList(roleRequest);
			dto.putValue("list", pr.getRows());
			dto.putValue("total", pr.getTotal());
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		return dto.toJsonString();
	}

	/* 根据角色逻辑fid查询角色
	 * @see com.ziroom.minsu.services.basedata.api.inner.UserPermissionService#searchRoleByFid(java.lang.String)
	 */
	@Override
	public String searchRoleByFid(String roleFid) {
		DataTransferObject dto = new DataTransferObject();
		if (Check.NuNStr(roleFid)) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
			return dto.toJsonString();
		}
		try {
			RoleEntity role = userPermissionServiceImpl.findRoleByFid(roleFid);
			if(role == null){
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.NOT_FOUND));
				return dto.toJsonString();
			}
			dto.putValue("role", role);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		return dto.toJsonString();
	}

	/* 启用|禁用角色
	 * @see com.ziroom.minsu.services.basedata.api.inner.UserPermissionService#editRole(com.ziroom.minsu.entity.RoleEntity)
	 */
	@Override
	public String editRoleStatus(String paramJson) {
		DataTransferObject dto = new DataTransferObject();
		RoleEntity role = JsonEntityTransform.json2Object(paramJson, RoleEntity.class);
		if(role.getIsDel() == null) {
			role.setIsDel(1);
		}
		role.setIsDel(1^role.getIsDel());
		role.setModifyDate(new Date());
		
		try {
			userPermissionServiceImpl.updateRole(role);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto = new DataTransferObject();
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		return dto.toJsonString();
	}

	/* (non-Javadoc)
	 * @see com.ziroom.minsu.services.basedata.api.inner.UserPermissionService#employeePageList(java.lang.String)
	 */
	@Override
	public String employeePageList(String paramJson) {
		DataTransferObject dto = null;
		try{
			EmployeeRequest employeeRequest=JsonEntityTransform.json2Object(paramJson, EmployeeRequest.class);
            //条件查询员工列表
			PagingResult<EmployeeEntity> pr=userPermissionServiceImpl.findEmployeeForPage(employeeRequest);
            dto = new DataTransferObject();
            dto.putValue("list", pr.getRows());
            dto.putValue("total", pr.getTotal());
		}catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
            dto = new DataTransferObject();
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
            return dto.toJsonString();
		}
		return dto.toJsonString();
	}


	/* (non-Javadoc)
	 * @see com.ziroom.minsu.services.basedata.api.inner.PermissionOperateService#cityList()
	 */
	@Override
	public String cityList() {
		DataTransferObject dto = null;
		try{
            //城市列表
			List<CityEntity> cityList=userPermissionServiceImpl.findAllCity();
            dto = new DataTransferObject();
            dto.putValue("list",cityList);
		}catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
            dto = new DataTransferObject();
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
            return dto.toJsonString();
		}
		return dto.toJsonString();
	}


	/* (non-Javadoc)
	 * @see com.ziroom.minsu.services.basedata.api.inner.PermissionOperateService#insertCurrentuser(java.lang.String)
	 */
	@Override
	public String insertCurrentuser(String paramJson) {
		DataTransferObject dto = null;
		try{
			CurrentuserVo currentuserVo=JsonEntityTransform.json2Object(paramJson, CurrentuserVo.class);
			userPermissionServiceImpl.insertCurrentuser(currentuserVo);
			dto = new DataTransferObject();
			dto.putValue("fid", currentuserVo.getFid());
		}catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto = new DataTransferObject();
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
            return dto.toJsonString();
		}
		return dto.toJsonString();
	}
	
	/* (non-Javadoc)
	 * @see com.ziroom.minsu.services.basedata.api.inner.ResourceService#findRoleResource(java.lang.String)
	 */
	@Override
	public String searchRoleResources(String roleFid) {
		DataTransferObject dto = new DataTransferObject();
		if(Check.NuNStr(roleFid)){
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
            return dto.toJsonString();
        }
		try {
			List<TreeNodeVo> treeNodeList = resourceServiceImpl.findMenuTreeNodeVos();
			List<RoleResourceEntity> roleResList = resourceServiceImpl.findRoleResourceList(roleFid);
			if (!Check.NuNCollection(treeNodeList) && !Check.NuNCollection(roleResList)) {
				recursionSelected(treeNodeList, roleResList);
			}
			dto.putValue("list", treeNodeList);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		return dto.toJsonString();
	}

	/**
	 * 递归判断资源列表是否选中
	 *
	 * @author liujun
	 * @created 2016-3-16 下午2:57:50
	 *
	 * @param treeNodeList
	 * @param roleResList
	 */
	private void recursionSelected(List<TreeNodeVo> treeNodeList, List<RoleResourceEntity> roleResList) {
		for (TreeNodeVo treeNodeVo : treeNodeList) {
			List<TreeNodeVo> treeNodeVoList = treeNodeVo.getNodes();
			if(!Check.NuNCollection(treeNodeVoList)){
				recursionSelected(treeNodeVoList,roleResList);
			}
			String id = treeNodeVo.getId();
			if (Check.NuNStr(id)) {
				continue;
			}
			for (RoleResourceEntity roleResource : roleResList) {
				String resFid = roleResource.getResourceFid();
				if (Check.NuNStr(resFid)) {
					continue;
				}
				if(resFid.equals(id)){
					Map<String, Object> map = treeNodeVo.getState();
					map.put("selected", true);
				}
			}
		}
	}
	
	@Override
    public String updateRoleResources(String paramJson) {
        DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(paramJson);
        Map<String, Object> map = dto.getData();
        String roleFid = (String) map.get("roleFid");
        String resFids = (String) map.get("resFids");
        Integer roleType=0;
        if(map.get("roleType")!=null){
        	roleType=Integer.valueOf(map.get("roleType").toString());
        }
        //非空校验
        dto = new DataTransferObject();
        if(Check.NuNStr(roleFid) || Check.NuNStr(resFids)){
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
            return dto.toJsonString();
        }
        try{
        	String[] resFidArray = resFids.split(",");
            //直接更新数据
        	resourceServiceImpl.saveRoleResources(roleFid,resFidArray,roleType);
        }catch (Exception e){
        	LogUtil.error(LOGGER, "error:{}", e);
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
        }
        return dto.toJsonString();
    }

	@Override
	public String addRoleResources(String paramJson) {
		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(paramJson);
		Map<String, Object> map = dto.getData();
		String userFid = (String) map.get("userFid");
		String roleName = (String) map.get("roleName");
		String resFids = (String) map.get("resFids");
		Integer roleType=0;
		if(map.get("roleType")!=null){
			roleType=Integer.valueOf(map.get("roleType").toString());
		}
		//非空校验
		dto = new DataTransferObject();
		if(Check.NuNStr(roleName) || Check.NuNStr(resFids)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
			return dto.toJsonString();
		}
		RoleEntity role = new RoleEntity();
		role.setRoleName(roleName);
		role.setFid(UUIDGenerator.hexUUID());
		role.setCreateFid(userFid);
		role.setRoleType(roleType);
		role.setCreateDate(new Date());
		try {
			resourceServiceImpl.saveRoleAndRoleResources(role, resFids);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		return dto.toJsonString();
	}

	@Override
	public String searchCurrentuserByUid(String uid) {
		DataTransferObject dto = new DataTransferObject();
		if(Check.NuNStr(uid)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
			return dto.toJsonString();
		}
		
		try {
			CurrentuserEntity user = userPermissionServiceImpl.getCurrentuserByFid(uid);
			dto.putValue("user", user);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		return dto.toJsonString();
	}

	@Override
	public String editUserStatus(String paramJson) {
		DataTransferObject dto = new DataTransferObject();
		CurrentuserEntity user = JsonEntityTransform.json2Object(paramJson, CurrentuserEntity.class);
		if(user.getAccountStatus() == null) {
			user.setAccountStatus(1);
		}
		user.setAccountStatus(1^user.getAccountStatus());
		user.setLastModifyDate(new Date());
		
		try {
			int upNum = userPermissionServiceImpl.updateCurrentuserByFid(user);
			dto.putValue("upNum", upNum);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto = new DataTransferObject();
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		return dto.toJsonString();
	}


	/* (non-Javadoc)
	 * @see com.ziroom.minsu.services.basedata.api.inner.PermissionOperateService#findRoleTypeByMenu(java.lang.String)
	 */
	@SuppressWarnings({ "unchecked" })
	@Override
	public String findRoleTypeByMenu(String paramJson) {
		DataTransferObject dto = new DataTransferObject();
		Map<String, String> paramMap=(Map<String, String>) JsonEntityTransform.json2Map(paramJson);
		Integer roleType=0;
		List<Integer> roleTypeList=userPermissionServiceImpl.findRoleTypeByMenu(paramMap.get("resUrl"), paramMap.get("currenuserFid"));
		//判断角色类型
		if(CollectionUtils.isNotEmpty(roleTypeList)){
			if(roleTypeList.contains(0)){
				roleType=0;
			}else if(roleTypeList.contains(1)){
				roleType=1;
			}else if(roleTypeList.contains(2)&&roleTypeList.contains(3)){
				roleType=1;
			}else{
				roleType=roleTypeList.get(0);
			}
		}
		dto.putValue("roleType", roleType);
		List<CurrentuserCityEntity> userCityList=userPermissionServiceImpl.findCuserCityByUserFid(paramMap.get("currenuserFid"));
		dto.putValue("userCityList", userCityList);
		return dto.toJsonString();
	}


	@Override
	public String insertCurrentuserCity(String paramJson) {
		DataTransferObject dto = new DataTransferObject();
		CurrentuserCityEntity currentuserCityEntity = JsonEntityTransform.json2Entity(paramJson, CurrentuserCityEntity.class);
		if (Check.NuNObj(currentuserCityEntity)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("参数为空");
			return dto.toJsonString();
		}
		int count = userPermissionServiceImpl.insertCurrentuserCity(currentuserCityEntity);
		dto.putValue("count",count);
		return dto.toJsonString();
	}

	@Override
	public String delCurrentuserCity(String fid) {
		DataTransferObject dto = new DataTransferObject();
		if (Check.NuNStr(fid)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("参数为空");
			return dto.toJsonString();
		}
		int i = userPermissionServiceImpl.delCurrentuserCity(fid);
		dto.putValue("count",i);
		return dto.toJsonString();
	}

	@Override
	public String getCurrentuserCity(String fid) {
		DataTransferObject dto = new DataTransferObject();
		if (Check.NuNStr(fid)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("参数为空");
			return dto.toJsonString();
		}
		List<CurrentuserCityVo> voList = userPermissionServiceImpl.getCurrentuserCity(fid);
		dto.putValue("list",voList);
		return dto.toJsonString();
	}
}
