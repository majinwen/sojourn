package com.ziroom.minsu.troy.system.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.exception.SOAParseException;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.SOAResParseUtil;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.sys.EmployeeEntity;
import com.ziroom.minsu.entity.sys.ResourceEntity;
import com.ziroom.minsu.entity.sys.RoleEntity;
import com.ziroom.minsu.services.basedata.api.inner.ConfCityService;
import com.ziroom.minsu.services.basedata.api.inner.EhrAccountSyncService;
import com.ziroom.minsu.services.basedata.api.inner.PermissionOperateService;
import com.ziroom.minsu.services.basedata.api.inner.ResourceService;
import com.ziroom.minsu.services.basedata.dto.CurrentuserRequest;
import com.ziroom.minsu.services.basedata.dto.EmployeeRequest;
import com.ziroom.minsu.services.basedata.dto.ResourceRequest;
import com.ziroom.minsu.services.basedata.dto.RoleRequest;
import com.ziroom.minsu.services.basedata.entity.CurrentuserVo;
import com.ziroom.minsu.services.basedata.entity.RoleVo;
import com.ziroom.minsu.services.basedata.entity.TreeNodeVo;
import com.ziroom.minsu.services.common.page.PageResult;
import com.ziroom.minsu.troy.common.util.UserUtil;

/**
 *
 * <p>
 * 后台用户权限controller
 * </p>
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
@RequestMapping("system/permission")
public class PermissionController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PermissionController.class);

	@Value("#{'${minsu.static.resource.url}'.trim()}")
	private String staticResourceUrl;

	@Resource(name = "basedata.resourceService")
	private ResourceService resourceService;
	
	@Resource(name = "basedata.ehrAccountSyncService")
	private EhrAccountSyncService ehrAccountSyncService;

	@Resource(name = "basedata.permissionOperateService")
	private PermissionOperateService permissionOperateService;
	
	@Resource(name="basedata.confCityService")
	private ConfCityService confCityService;

	/**
	 *
	 * 后台用户列表
	 *
	 * @author bushujie
	 * @created 2016年3月9日 下午4:59:06
	 *
	 * @param request
	 */
	@RequestMapping("currentuserList")
	public void userList(HttpServletRequest request) {
		request.setAttribute("staticResourceUrl", staticResourceUrl);
	}

	/**
	 *
	 * 添加用户
	 *
	 * @author bushujie
	 * @created 2016年3月12日 下午5:22:25
	 *
	 */
	@RequestMapping("currentuserAdd")
	public void toAddUser(HttpServletRequest request) {
		String resultJson=confCityService.getConfCitySelect();
		List<TreeNodeVo> cityTreeList=null;
		try {
			cityTreeList=SOAResParseUtil.getListValueFromDataByKey(resultJson, "list", TreeNodeVo.class);
		} catch (SOAParseException e) {
			LogUtil.error(LOGGER, "error:{}", e);
		}
		request.setAttribute("cityTreeList", JsonEntityTransform.Object2Json(cityTreeList));
		request.setAttribute("staticResourceUrl", staticResourceUrl);
	}
	
	/**
	 * 
	 * 同步ehr数据(单个)
	 *
	 * @author jixd
	 * @created 2016年6月30日 下午6:23:00
	 *
	 * @param empCode
	 * @return
	 */
	@RequestMapping("syncEhrEmp")
	@ResponseBody
	public DataTransferObject syncEhrEmp(String empCode){
		DataTransferObject dto = new DataTransferObject();
		try{
			String resultJson =ehrAccountSyncService.syncSingleEmployee(empCode);
			dto=JsonEntityTransform.json2DataTransferObject(resultJson);
		}catch(Exception e){
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(1);
			dto.setMsg("服务错误");
		}
		return dto;
	}

	/**
	 *
	 * 角色管理-跳转角色列表页面
	 *
	 * @author liujun
	 * @created 2016-3-14 下午3:36:12
	 *
	 * @param request
	 */
	@RequestMapping("listRoles")
	public void listRoles(HttpServletRequest request) {
		request.setAttribute("staticResourceUrl", staticResourceUrl);
	}

	/**
	 *
	 * 角色管理-根据条件查询角色列表
	 *
	 * @author liujun
	 * @created 2016-3-11 下午6:32:20
	 *
	 * @param roleRequest
	 */
	@RequestMapping("showRoles")
	@ResponseBody
	public PageResult showRoles(RoleRequest roleRequest) {
		try {
			String resultJson = permissionOperateService.searchRoles(JsonEntityTransform.Object2Json(roleRequest));
			DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(resultJson);
			List<RoleVo> roleList = resultDto.parseData("list", new TypeReference<List<RoleVo>>() {});
			PageResult pageResult = new PageResult();
			pageResult.setRows(roleList);
			pageResult.setTotal(Long.valueOf(resultDto.getData().get("total").toString()));
			return pageResult;
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			return new PageResult();
		} 

	}

	/**
	 *
	 * 角色管理-查询角色资源列表
	 *
	 * @author liujun
	 * @created 2016-3-11 下午6:10:51
	 *
	 * @param roleFid
	 */
	@RequestMapping("/listRoleResource")
	@ResponseBody
	public DataTransferObject listRoleResource(String roleFid) {
		try {
			String resultJson = permissionOperateService.searchRoleResources(roleFid);
			// jstree子节点集合属性名称
			resultJson = resultJson.replaceAll("nodes", "children");
			return JsonEntityTransform.json2DataTransferObject(resultJson);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			DataTransferObject dto = new DataTransferObject();
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg(e.getMessage());
			return dto;
		}
	}

	/**
	 *
	 * 角色管理-查询所有资源列表
	 *
	 * @author liujun
	 * @created 2016-3-11 下午6:10:51
	 *
	 */
	@RequestMapping("/listAllResource")
	@ResponseBody
	public DataTransferObject listAllResource() {
		try {
			String resultJson = resourceService.menuTreeVo();
			// jstree子节点集合属性名称
			resultJson = resultJson.replaceAll("nodes", "children");
			return JsonEntityTransform.json2DataTransferObject(resultJson);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			DataTransferObject dto = new DataTransferObject();
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg(e.getMessage());
			return dto;
		}
	}

	/**
	 *
	 * 角色管理-启用|禁用角色
	 *
	 * @author liujun
	 * @created 2016-3-12 下午3:14:45
	 *
	 * @param roleFid
	 */
	@RequestMapping("editRoleStatus")
	@ResponseBody
	public DataTransferObject editRoleStatus(String roleFid) {
		try {
			String resultJson = permissionOperateService.searchRoleByFid(roleFid);
			DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(resultJson);
			
			if (dto.getCode() == DataTransferObject.ERROR) {
				LogUtil.error(LOGGER, "调用接口失败:roleFid={}", roleFid);
				return dto;
			}
			
			String returnJson = permissionOperateService.editRoleStatus(JsonEntityTransform.Object2Json(dto.getData().get("role")));
			return JsonEntityTransform.json2DataTransferObject(returnJson);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			DataTransferObject dto = new DataTransferObject();
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg(e.getMessage());
			return dto;
		}
	}
	
	/**
	 *
	 * 角色管理-跳转新增角色页面
	 *
	 * @author liujun
	 * @created 2016-3-12 下午9:56:10
	 *
	 * @param request
	 */
	@RequestMapping("addRoleResource")
	public void toAddRoleResource(HttpServletRequest request) {
		request.setAttribute("staticResourceUrl", staticResourceUrl);
	}

	/**
	 *
	 * 角色管理-新增角色
	 *
	 * @author liujun
	 * @created 2016-3-12 下午9:56:10
	 *
	 * @param roleName
	 */
	@RequestMapping("/saveRoleResource")
	@ResponseBody
	public DataTransferObject saveRoleResource(String roleName, String resFids,Integer roleType) {
		try {
			String userFid = UserUtil.getCurrentUserFid();
			DataTransferObject dto = new DataTransferObject();
			dto.putValue("userFid", userFid);
			dto.putValue("roleName", roleName);
			dto.putValue("resFids", resFids);
			dto.putValue("roleType", roleType);

			String resultJson = permissionOperateService.addRoleResources(JsonEntityTransform.entity2Json(dto));
			return JsonEntityTransform.json2DataTransferObject(resultJson);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			DataTransferObject dto = new DataTransferObject();
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg(e.getMessage());
			return dto;
		}
		
	}
	
	/**
	 *
	 * 角色管理-跳转修改角色页面
	 *
	 * @author liujun
	 * @created 2016-3-12 下午9:56:10
	 *
	 * @param request
	 * @throws SOAParseException 
	 */
	@RequestMapping("editRoleResource")
	public void toEditRoleResource(HttpServletRequest request,String roleFid) throws SOAParseException {
		String resultJson=permissionOperateService.searchRoleByFid(roleFid);
		RoleEntity roleEntity=SOAResParseUtil.getValueFromDataByKey(resultJson, "role", RoleEntity.class);
		request.setAttribute("staticResourceUrl", staticResourceUrl);
		request.setAttribute("roleFid", roleFid);
		request.setAttribute("roleEntity", roleEntity);
	}
	
	/**
	 *
	 * 角色管理-修改角色
	 *
	 * @author liujun
	 * @created 2016-3-12 下午9:56:10
	 *
	 * @param roleFid
	 */
	@RequestMapping("/updateRoleResource")
	@ResponseBody
	public DataTransferObject updateRoleResource(String roleFid, String resFids,Integer roleType) {
		try {
			DataTransferObject dto = new DataTransferObject();
			dto.putValue("roleFid", roleFid);
			dto.putValue("resFids", resFids);
			dto.putValue("roleType", roleType);
			
			String resultJson = permissionOperateService.updateRoleResources(JsonEntityTransform.Object2Json(dto));
			return JsonEntityTransform.json2DataTransferObject(resultJson);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			DataTransferObject dto = new DataTransferObject();
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg(e.getMessage());
			return dto;
		}
	}

	/**
	 *
	 * 分页查询后台用户列表
	 *
	 * @author bushujie
	 * @created 2016年3月13日 下午3:02:06
	 *
	 * @param request
	 * @param paramRequest
	 * @return
	 */
	@RequestMapping("/showAllUser")
	@ResponseBody
	public PageResult ajaxRequest(HttpServletRequest request,
			@ModelAttribute("paramRequest") CurrentuserRequest paramRequest) {
		try {
			String resultJson = permissionOperateService
					.searchCurrentuserList(JsonEntityTransform.Object2Json(paramRequest));
			DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(resultJson);
			
			if (resultDto.getCode() == DataTransferObject.ERROR) {
				LogUtil.error(LOGGER, "调用接口失败,参数:{}", JsonEntityTransform.Object2Json(paramRequest));
				return new PageResult();
			}
			
			List<CurrentuserVo> userList = resultDto.parseData("list", new TypeReference<List<CurrentuserVo>>() {
			});
			PageResult pageResult = new PageResult();
			pageResult.setRows(userList);
			pageResult.setTotal(Long.valueOf(resultDto.getData().get("total").toString()));
			return pageResult;
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			return new PageResult();
		}
	}

	/**
	 *
	 * 资源树结构
	 *
	 * @author bushujie
	 * @created 2016年3月13日 下午7:24:50
	 *
	 */
	@RequestMapping("/menuTreeview")
	public void menuTreeView(HttpServletRequest request) {
		String resultJson = resourceService.menuTreeVo();
		DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(resultJson);
		
		if (resultDto.getCode() == DataTransferObject.ERROR) {
			LogUtil.error(LOGGER, "调用接口失败");
		}
		List<TreeNodeVo> treeList = resultDto.parseData("list", new TypeReference<List<TreeNodeVo>>() {
		});
		request.setAttribute("staticResourceUrl", staticResourceUrl);
		request.setAttribute("treeview", JsonEntityTransform.Object2Json(treeList));
	}

	/**
	 *
	 * 员工列表
	 *
	 * @author bushujie
	 * @created 2016年3月14日 下午8:44:10
	 *
	 * @return
	 */
	@RequestMapping("/employeeList")
	@ResponseBody
	public PageResult employeeList(@ModelAttribute("paramRequest") EmployeeRequest paramRequest) {
		try {
			String resultJson = permissionOperateService.employeePageList(JsonEntityTransform.Object2Json(paramRequest));
			DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(resultJson);
			if (resultDto.getCode() == DataTransferObject.ERROR) {
				LogUtil.error(LOGGER, "调用接口失败,参数:{}", JsonEntityTransform.Object2Json(paramRequest));
			}
			List<EmployeeEntity> treeList = resultDto.parseData("list", new TypeReference<List<EmployeeEntity>>() {
			});
			PageResult pageResult = new PageResult();
			pageResult.setRows(treeList);
			pageResult.setTotal(Long.valueOf(resultDto.getData().get("total").toString()));
			return pageResult;
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			return new PageResult();
		}
	}

	// =======================菜单操作相关===========================//

	/**
	 * 进入--菜单资源--列表页面
	 * 
	 * @created 2016年3月9日 下午4:59:06
	 * @author liyingjie
	 */
	@RequestMapping(value = "/resourceList", method = RequestMethod.GET)
	public void toResourceList(HttpServletRequest request) {

		List<TreeNodeVo> treeList = getTreeVoList();
		request.setAttribute("staticResourceUrl", staticResourceUrl);
		String treeview = JsonEntityTransform.Object2Json(treeList);
		request.setAttribute("treeview", treeview);

		if(CollectionUtils.isNotEmpty(treeList)){
			request.setAttribute("treeNode", treeList.get(0));
		}
		
		request.setAttribute("staticResourceUrl", staticResourceUrl);

	}

	/**
	 *
	 * 后台菜单--右侧--列表
	 *
	 * @author liyingjie
	 * @created 2016年3月9日 下午4:59:06
	 * @param request
	 */
	@RequestMapping("/menuDatalist")
	public @ResponseBody PageResult menuList(@ModelAttribute("paramRequest") ResourceRequest paramRequest,HttpServletRequest request) {
		//上层菜单id
		String parent_id = request.getParameter("fid");

		if (parent_id != null && parent_id != "") {
			paramRequest.setParent_fid(parent_id);
		}

		String resultJson = resourceService.searchMenuResList(JsonEntityTransform.Object2Json(paramRequest));

		DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(resultJson);
		if (resultDto.getCode() == DataTransferObject.ERROR) {
			LogUtil.error(LOGGER, "调用接口失败,参数:{}", JsonEntityTransform.Object2Json(paramRequest));
		}
		List<ResourceEntity> menuList = resultDto.parseData("list", new TypeReference<List<ResourceEntity>>() {
		});
		request.setAttribute("staticResourceUrl", staticResourceUrl);
       //返回结果
		PageResult pageResult = new PageResult();
		pageResult.setRows(menuList);
		pageResult.setTotal(Long.valueOf(resultDto.getData().get("total").toString()));

		return pageResult;

	}

	/**
	 * 菜单--最左侧--导航
	 * 
	 * @author liyingjie
	 * @created 2016年3月9日 下午4:59:06
	 * @param request
	 * @return
	 */
	@RequestMapping("/menuSysList")
	public Object menuSysList(HttpServletRequest request) {
		String resultJson = resourceService.searchAllMenuChildResList();
		DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(resultJson);
		if (resultDto.getCode() == DataTransferObject.ERROR) {
			LogUtil.error(LOGGER, "调用接口失败");
		}
		List<ResourceEntity> menuList = resultDto.parseData("list", new TypeReference<List<ResourceEntity>>() {
		});
		request.setAttribute("menuList", menuList);
		return "";
	}

	/**
	 * 添加--菜单--操作
	 * 
	 * @author liyingjie
	 * @created 2016年3月9日 下午4:59:06
	 * @param request
	 */
	@RequestMapping("/addMenuRes")
	@ResponseBody
	public Map<String,Object> addMenu(@ModelAttribute ResourceEntity menu, HttpServletRequest request) {

		//上层菜单 id
		String parent_id = menu.getParentFid();
		
		//保存 实体
		ResourceEntity mre = new ResourceEntity();
		
		mre.setFid(UUIDGenerator.hexUUID());
		
		if (StringUtils.isNotBlank(parent_id)) {
			mre.setParentFid(parent_id);
		}
		if (StringUtils.isNotBlank(menu.getResName())) {
			mre.setResName(menu.getResName());
		}
		if (StringUtils.isNotBlank(menu.getClassName())) {
			mre.setClassName(menu.getClassName());
		}

		if (StringUtils.isNotBlank(menu.getResUrl())) {
			mre.setResUrl(menu.getResUrl());
		}
		
		if(!Check.NuNObj(menu.getMenuAuth())){
			mre.setMenuAuth(menu.getMenuAuth());
		}
		mre.setIsLeaf(1);//系统菜单树为根节点  其它的都是叶子节点

		mre.setOrderCode(menu.getOrderCode());

		// TODO:菜单层级
		
		//mre.setResLevel(Integer.parseInt(resLevel));
		mre.setResType(menu.getResType());

		//向数据库保存数据
		String paramJson = JsonEntityTransform.Object2Json(mre);
		resourceService.insertMenuResource(paramJson);

		//保存后  更新列表左侧树
		List<TreeNodeVo> treeList = getTreeVoList();
		request.setAttribute("staticResourceUrl", staticResourceUrl);
		request.setAttribute("treeview", JsonEntityTransform.Object2Json(treeList));
		
		Map<String,Object> resMap= new HashMap<String,Object>(); 
		resMap.put("treeview", JsonEntityTransform.Object2Json(treeList));
		
		return resMap;
	}
	
	/**
	 * 
	 * 获取菜单树--左侧树--方法封装
	 *
	 * @author liyingjie
	 * @created 2016年3月15日 下午9:57:23
	 *
	 */
	private List<TreeNodeVo> getTreeVoList(){
		String resultJson = resourceService.menuTreeVo();
		DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(resultJson);
		if (resultDto.getCode() == DataTransferObject.ERROR) {
			LogUtil.error(LOGGER, "调用接口失败");
		}
		List<TreeNodeVo> treeList = resultDto.parseData("list", new TypeReference<List<TreeNodeVo>>() {
		});
		return treeList;
	}
	
	/**
	 * 菜单--停用操作--导航
	 * 
	 * @author liyingjie
	 * @created 2016年3月9日 下午4:59:06
	 * @param request
	 * @return
	 */
	@RequestMapping("/changeStatus")
	@ResponseBody
	public Map<String,Object> changeMenuStatus(HttpServletRequest request,String selectedId,String resStatus) {
		ResourceEntity resourceEntity = new ResourceEntity();
		resourceEntity.setFid(selectedId);
		
		if(Integer.parseInt(resStatus) == 0){
			resourceEntity.setResState(1);
		}else{
			resourceEntity.setResState(0);
		}
		resourceService.updateMenuByFid(JsonEntityTransform.Object2Json(resourceEntity));
		
		Map<String,Object> resObject = new HashMap<String, Object>();
		resObject.put("status", JsonEntityTransform.Object2Json("success"));
		return resObject;
		
	}
	
	/**
	 * 
	 * 插入后台用户
	 *
	 * @author bushujie
	 * @created 2016年3月15日 下午9:57:23
	 *
	 */
	@RequestMapping("insertCurrentuser")
	public String insertCurrentuser(@ModelAttribute CurrentuserVo paramRequest){
		String requestJson = JsonEntityTransform.Object2Json(paramRequest);
		
		String resultJson=permissionOperateService.insertCurrentuser(requestJson);
		DataTransferObject resultDto=JsonEntityTransform.json2DataTransferObject(resultJson);
		if(resultDto.getCode() == DataTransferObject.ERROR){
			LogUtil.error(LOGGER, "调用接口失败,参数:{}",requestJson);
			return resultDto.getMsg();
		}
		return "redirect:/system/permission/currentuserList";
	}
	/**
	 * 
	 * 编辑后台用户页
	 *
	 * @author bushujie
	 * @created 2016年3月16日 上午10:23:41
	 *
	 * @param request
	 */
	@RequestMapping("editCurrentuser")
	public void toEditCurrentuser(HttpServletRequest request,String fid){
		String userResult=permissionOperateService.initSaveUserInfo(fid);
		DataTransferObject resultDto=JsonEntityTransform.json2DataTransferObject(userResult);
		if (resultDto.getCode() == DataTransferObject.ERROR) {
			LogUtil.error(LOGGER, "调用接口失败,fid={}",fid);
		}
		CurrentuserVo currentuserInfo=resultDto.parseData("userInfo", new TypeReference<CurrentuserVo>() {});
		String resultJson=confCityService.getConfCitySelect();
		List<TreeNodeVo> cityTreeList=null;
		try {
			cityTreeList=SOAResParseUtil.getListValueFromDataByKey(resultJson, "list", TreeNodeVo.class);
		} catch (SOAParseException e) {
			LogUtil.error(LOGGER, "error:{}", e);
		}
		request.setAttribute("cityTreeList", JsonEntityTransform.Object2Json(cityTreeList));
		request.setAttribute("userInfo", currentuserInfo);
		request.setAttribute("staticResourceUrl", staticResourceUrl);
	}
	/**
	 * 
	 * 修改后台用户信息
	 *
	 * @author bushujie
	 * @created 2016年3月16日 下午2:12:34
	 *
	 * @param request
	 * @param currentuserVo
	 * @return
	 */
	@RequestMapping("editCurrentuserSucceed")
	public Object edidCurrentuser(HttpServletRequest request, CurrentuserVo currentuserVo){
		String resultJson=permissionOperateService.saveUserInfo(JsonEntityTransform.Object2Json(currentuserVo));
		DataTransferObject resultDto=JsonEntityTransform.json2DataTransferObject(resultJson);
		if(resultDto.getCode()!=0){
			return resultDto;
		}
		return "redirect:/system/permission/currentuserList";
	}
	
	public String getExtension(String fName) {
		int index = fName.lastIndexOf(".");
		return fName.substring(index + 1);
	}
	

	/**
	 * 
	 * 用户管理-启用|禁用账户
	 *
	 * @author liujun
	 * @created 2016年5月16日
	 *
	 * @param roleFid
	 * @return
	 */
	@RequestMapping("editUserStatus")
	@ResponseBody
	public DataTransferObject editUserStatus(String uid) {
		try {
			String resultJson = permissionOperateService.searchCurrentuserByUid(uid);
			DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(resultJson);
			
			if (dto.getCode() == DataTransferObject.ERROR) {
				LogUtil.error(LOGGER, "调用接口失败,uid={}",uid);
				return dto;
			}
			
			String returnJson = permissionOperateService.editUserStatus(JsonEntityTransform.Object2Json(dto.getData().get("user")));
			return JsonEntityTransform.json2DataTransferObject(returnJson);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			DataTransferObject dto = new DataTransferObject();
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg(e.getMessage());
			return dto;
		}
	}
	
}
