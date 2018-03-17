/**
 * @FileName: UpsApiController.java
 * @Package com.ziroom.minsu.ups.controller
 * 
 * @author bushujie
 * @created 2016年12月7日 上午10:39:59
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.ups.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.sys.CurrentuserEntity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.exception.SOAParseException;
import com.ziroom.minsu.entity.sys.EmployeeEntity;
import com.ziroom.minsu.entity.sys.SystemsEntity;
import com.ziroom.minsu.services.basedata.entity.CurrentuserVo;
import com.ziroom.minsu.services.basedata.entity.ResourceVo;
import com.ziroom.minsu.services.basedata.entity.UpsUserVo;
import com.ziroom.minsu.ups.service.EmployeeService;
import com.ziroom.minsu.ups.service.ICurrentUserService;
import com.ziroom.minsu.ups.service.IResourceService;
import com.ziroom.minsu.ups.service.ISystemService;

/**
 * <p>权限系统对外API接口</p>
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
@Controller
@RequestMapping("upsApi")
public class UpsApiController {

	private static final Logger LOGGER = LoggerFactory.getLogger(UpsApiController.class);
	@Resource(name="ups.currentUserService")
	private ICurrentUserService currentUserService;
	
	@Resource(name="ups.employeeService")
	private EmployeeService employeeService;
	
	@Resource(name="ups.resourceService")
	private IResourceService resourceService;
	
	@Resource(name="ups.systemService")
	private ISystemService systemService;

	
	/**
	 * 
	 * 获取用户系统权限集合list树结构
	 *
	 * @author bushujie
	 * @created 2016年12月7日 上午10:47:49
	 *
	 * @param sysCode
	 * @param userAccountFid
	 * @return
	 * @throws SOAParseException 
	 */
	@RequestMapping(value="userAuthList",headers = "Accept=*/*",produces="application/json")
	@ResponseBody
	public DataTransferObject userAuthList(String sysCode,String userAccountFid) throws SOAParseException{
		DataTransferObject dto=new DataTransferObject();
		SystemsEntity systemsEntity=systemService.getSystemsEntityByCode(sysCode);
		//查询用户权限树
		List<ResourceVo> resourceVoList=resourceService.findResourceByCurrentuserId(userAccountFid, systemsEntity.getFid());
		dto.putValue("resourceVoList", resourceVoList);
		return dto;
	}

	/**
	 * 插入用户信息
	 * @author jixd
	 * @created 2016年12月12日 21:02:28
	 * @param
	 * @return
	 */
	@RequestMapping(value="/insertCurrentuser",headers = "Accept=*/*",produces = "application/json")
	@ResponseBody
	public DataTransferObject insertCurrentuser(String currentUser,String empJson){
		DataTransferObject dto = new DataTransferObject();
		if (Check.NuNStr(currentUser)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("用户参数为空");
			return dto;
		}
		if (Check.NuNStr(empJson)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("员工参数为空");
			return dto;
		}
		LogUtil.info(LOGGER,"参数={}",currentUser);
		CurrentuserEntity currentuserEntity = com.alibaba.fastjson.JSONObject.parseObject(currentUser, CurrentuserEntity.class);
		LogUtil.info(LOGGER,"入参result={}", JsonEntityTransform.Object2Json(currentuserEntity));
		int count = currentUserService.insertOrUpdateCurrentuser(currentuserEntity);
		
		LogUtil.info(LOGGER,"参数={}",empJson);
		EmployeeEntity employeeEntity=JsonEntityTransform.json2Entity(empJson, EmployeeEntity.class);
		int empCount=employeeService.insertEmployee(employeeEntity);
		dto.putValue("empCount", empCount);
		dto.putValue("count",count);
		return dto;
	}
	
	/**
	 * 
	 * 用户信息查询
	 *
	 * @author bushujie
	 * @created 2016年12月14日 上午9:49:37
	 *
	 * @param userAccount
	 * @return
	 * @throws SOAParseException
	 */
	@RequestMapping(value="userMsg",headers = "Accept=*/*",produces="application/json")
	@ResponseBody
	public DataTransferObject userMsg(String userAccount) throws SOAParseException{
		DataTransferObject dto=new DataTransferObject();
		CurrentuserVo currentuserVo=currentUserService.getCurrentuserByUserAccount(userAccount);
		if(Check.NuNObj(currentuserVo)){
			currentuserVo=currentUserService.getCurrentuserVoByfid(userAccount);
		}
		EmployeeEntity employeeEntity=employeeService.findEmployeeByFid(currentuserVo.getEmployeeFid());
		if(!Check.NuNObj(employeeEntity)){
			currentuserVo.setEmpCode(employeeEntity.getEmpCode());
			currentuserVo.setFullName(employeeEntity.getEmpName());
		}
		dto.putValue("currentuserVo", currentuserVo);
		return dto;
	}
	
	/**
	 * 
	 *  获取用户系统权限集合Set
	 *
	 * @author bushujie
	 * @created 2016年12月14日 上午10:36:42
	 *
	 * @param sysCode
	 * @param userAccountFid
	 * @return
	 */
	@RequestMapping(value="userAuthSet",headers = "Accept=*/*",produces="application/json")
	@ResponseBody
	public DataTransferObject userAuthSet(String sysCode,String userAccountFid ){
		DataTransferObject dto=new DataTransferObject();
		SystemsEntity systemsEntity=systemService.getSystemsEntityByCode(sysCode);
		//查询用户权限树
		Set<String> resourceVoSet=resourceService.findFnResourceSetByUid(systemsEntity.getFid(), userAccountFid);
		dto.putValue("resourceVoSet", resourceVoSet);
		return dto;
	}
	
	
	/**
	 * 
	 * 判断是否特权菜单
	 *
	 * @author bushujie
	 * @created 2016年12月20日 下午5:07:02
	 *
	 * @param sysCode
	 * @param resUrl
	 * @return
	 */
	@RequestMapping(value="isPrivilegeRes",headers = "Accept=*/*",produces="application/json")
	@ResponseBody
	public DataTransferObject isPrivilegeRes(String sysCode,String resUrl){
		DataTransferObject dto=new DataTransferObject();
		SystemsEntity systemsEntity=systemService.getSystemsEntityByCode(sysCode);
		dto.putValue("isPrivilegeRes", resourceService.isPrivilegeRes(systemsEntity.getFid(), resUrl));
		return dto;
	}
	
	/**
	 * 
	 * 权限url查询角色类型和用户负责区域集合
	 *
	 * @author bushujie
	 * @created 2016年12月20日 下午6:15:59
	 *
	 * @param sysCode
	 * @param resUrl
	 * @return
	 */
	@RequestMapping(value="findRoleTypeByMenu",headers = "Accept=*/*",produces="application/json")
	@ResponseBody
	public DataTransferObject findRoleTypeByMenu(String sysCode,String resUrl,String currentuserFid){
		DataTransferObject dto=new DataTransferObject();
		SystemsEntity systemsEntity=systemService.getSystemsEntityByCode(sysCode);
		Map<String, Object> paramMap=new HashMap<String, Object>();
		paramMap.put("resUrl", resUrl);
		paramMap.put("currentuserFid", currentuserFid);
		paramMap.put("systemsFid", systemsEntity.getFid());
		dto.putValue("roleType", currentUserService.findRoleTypeByMenu(paramMap));
		dto.putValue("userCityList", currentUserService.findCuserCityByUserFid(currentuserFid));
		return dto;
	}

    /**
     * 获取登录信息
     *
     * @param userAccount
     * @return
     * @throws SOAParseException
     * @author bushujie
     * @created 2017年10月28日 下午3:52:35
     */
    @RequestMapping(value="upsUserMsg",headers = "Accept=*/*",produces="application/json")
    @ResponseBody
    public DataTransferObject upsUserMsg(String userAccount,String sysCode) throws SOAParseException{
        DataTransferObject dto=new DataTransferObject();
        //参数验证
        if(Check.NuNStr(userAccount)){
            dto.setErrCode(1);
            dto.setMsg("用户名不能为空！");
            return dto;
        }
        if(Check.NuNStr(sysCode)){
            dto.setErrCode(1);
            dto.setMsg("系统标识不能为空！");
            return dto;
        }
        UpsUserVo upsUserVo=new UpsUserVo();
        CurrentuserEntity currentuserEntity=currentUserService.getCurrentByUserAccount(userAccount);
        if(!Check.NuNObj(currentuserEntity)){
            upsUserVo.setCurrentuserEntity(currentuserEntity);
            EmployeeEntity employeeEntity=employeeService.findEmployeeByFid(currentuserEntity.getEmployeeFid());
            if(!Check.NuNObj(employeeEntity)){
                upsUserVo.setEmployeeEntity(employeeEntity);
            }
            SystemsEntity systemsEntity=systemService.getSystemsEntityByCode(sysCode);
            upsUserVo.setResourceVoSet(resourceService.findFnResourceSetByUid(systemsEntity.getFid(), currentuserEntity.getFid()));
            upsUserVo.setResourceVoList(resourceService.findResourceByCurrentuserId(currentuserEntity.getFid(), systemsEntity.getFid()));
			upsUserVo.setRoleFids(resourceService.queryRoleFidsByCurFid(currentuserEntity.getFid()));
            dto.putValue("upsUser", upsUserVo);
        } else {
            dto.setErrCode(1);
            dto.setMsg("用户不存在！");
        }
        return dto;
    }


    /**
     *
     * 获取登录信息-V2
     *
     * 前后分离的项目开始使用，根据用户系统号查询
     *
     * @author zhangyl2
     * @created 2018年03月08日 16:24
     * @param
     * @return
     */
    @RequestMapping(value = "upsUserMsg/v2", headers = "Accept=*/*", produces = "application/json")
    @ResponseBody
    public DataTransferObject upsUserMsgV2(String empCode, String sysCode) throws SOAParseException {
        DataTransferObject dto = new DataTransferObject();

        //参数验证
        if(Check.NuNStr(empCode)){
            dto.setErrCode(1);
            dto.setMsg("系统号不能为空！");
            return dto;
        }
        if (Check.NuNStr(sysCode)) {
            dto.setErrCode(1);
            dto.setMsg("系统标识不能为空！");
            return dto;
        }

        UpsUserVo upsUserVo = new UpsUserVo();
        EmployeeEntity employeeEntity = employeeService.getEmployeeEntity(empCode);
        if(!Check.NuNObj(employeeEntity)){
            upsUserVo.setEmployeeEntity(employeeEntity);
            CurrentuserEntity currentuserEntity = currentUserService.getCurrentByEmpFid(employeeEntity.getFid());
            if (!Check.NuNObj(currentuserEntity)) {
                upsUserVo.setCurrentuserEntity(currentuserEntity);
                SystemsEntity systemsEntity = systemService.getSystemsEntityByCode(sysCode);

                // 注意，这里的权限结构与老的接口不一致

                // 用户菜单权限fid集合（前后端分离的权限格式：0：非功能点fid + 1：功能点菜单fid）
                upsUserVo.setResourceVoSet(resourceService.findMenuFidList(systemsEntity.getFid(), currentuserEntity.getFid()));

                // 用户功能点菜单权限树（前后端分离的权限格式：res_type=1:功能点菜单与其子权限）
                upsUserVo.setResourceVoList(resourceService.findMenuChildTree(systemsEntity.getFid(), currentuserEntity.getFid()));

                dto.putValue("upsUser", upsUserVo);
            } else {
                dto.setErrCode(1);
                dto.setMsg("用户不存在！");
            }
        }

        return dto;
    }
}
