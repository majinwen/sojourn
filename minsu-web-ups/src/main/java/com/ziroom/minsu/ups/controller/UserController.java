package com.ziroom.minsu.ups.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.exception.SOAParseException;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.SOAResParseUtil;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.conf.ConfCityEntity;
import com.ziroom.minsu.entity.sys.EmployeeEntity;
import com.ziroom.minsu.entity.sys.SystemsEntity;
import com.ziroom.minsu.services.basedata.api.inner.ConfCityService;
import com.ziroom.minsu.services.basedata.api.inner.PermissionOperateService;
import com.ziroom.minsu.services.basedata.dto.CurrentuserRequest;
import com.ziroom.minsu.services.basedata.dto.EmployeeRequest;
import com.ziroom.minsu.services.basedata.entity.CurrentuserCityVo;
import com.ziroom.minsu.services.basedata.entity.CurrentuserVo;
import com.ziroom.minsu.services.basedata.entity.TreeNodeVo;
import com.ziroom.minsu.services.common.page.PageResult;
import com.ziroom.minsu.ups.dto.RoleListRequest;
import com.ziroom.minsu.ups.service.EmployeeService;
import com.ziroom.minsu.ups.service.ICurrentUserService;
import com.ziroom.minsu.ups.service.IRoleService;
import com.ziroom.minsu.ups.service.ISystemService;
import com.ziroom.minsu.ups.vo.RoleListVo;

/**
 *
 * <p>用户controller</p>
 *
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd
 * @since 1.0
 * @version 1.0
 */
@RequestMapping("/user")
@Controller
public class UserController {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private ICurrentUserService userService;

	@Resource(name = "basedata.confCityService")
	private ConfCityService confCityService;

	@Resource(name = "basedata.permissionOperateService")
	private PermissionOperateService permissionOperateService;

	@Resource(name="ups.employeeService")
	private EmployeeService employeeService;

	@Resource(name="ups.systemService")
	private ISystemService systemService;


	@Autowired
	private IRoleService roleService;

	/**
	 * 跳转用户列表
	 *
	 * @param
	 * @return
	 * @author jixd
	 * @created 2016年12月08日 13:47:27
	 */
	@RequestMapping("/currentuserList")
	public void userList() {
	}

	/**
	 * 获取用户
	 *
	 * @param
	 * @return
	 * @author jixd
	 * @created 2016年12月08日 13:47:43
	 */
	@RequestMapping("/showAllUser")
	@ResponseBody
	public PagingResult<CurrentuserVo> showAllUser(HttpServletRequest request, @ModelAttribute("paramRequest") CurrentuserRequest paramRequest) {
		PagingResult<CurrentuserVo> paginResult = userService.findCurrentuserPageList(paramRequest);
		return paginResult;
	}

	/**
	 * 跳转用户新增页
	 *
	 * @param
	 * @return
	 * @author jixd
	 * @created 2016年12月08日 13:49:49
	 */
	@RequestMapping("/currentuserAdd")
	public void userAdd(HttpServletRequest request) {
		String resultJson = confCityService.getConfCitySelect();
		List<TreeNodeVo> cityTreeList = null;
		try {
			cityTreeList = SOAResParseUtil.getListValueFromDataByKey(resultJson, "list", TreeNodeVo.class);
		} catch (SOAParseException e) {
			LogUtil.error(LOGGER, "error:{}", e);
		}
		List<SystemsEntity> sysList=systemService.findAllSystem();
		request.setAttribute("sysList", sysList);
		request.setAttribute("cityTreeList", JsonEntityTransform.Object2Json(cityTreeList));
	}

	/**
	 * 
	 * 编辑用户信息
	 *
	 * @author bushujie
	 * @created 2016年12月20日 下午3:40:21
	 *
	 * @param request
	 * @return
	 * @throws SOAParseException 
	 */
	@RequestMapping("/editCurrentuser")
	public String userEdit(HttpServletRequest request) throws SOAParseException {
		String fid = request.getParameter("fid");
		CurrentuserVo currentuserInfo = userService.currentUserInfo(fid);
		String resultJson = confCityService.getConfCitySelect();
		List<TreeNodeVo> cityTreeList = null;
		try {
			cityTreeList = SOAResParseUtil.getListValueFromDataByKey(resultJson, "list", TreeNodeVo.class);
		} catch (SOAParseException e) {
			LogUtil.error(LOGGER, "error:{}", e);
		}
		currentuserInfo.setCityList(userService.getCurrentuserCityListByUserFid(fid));
		for(CurrentuserCityVo vo:currentuserInfo.getCityList()){
			List<String> codeList=new ArrayList<String>();
			if(!Check.NuNStr(vo.getNationCode())){
				codeList.add(vo.getNationCode());
			}
			if(!Check.NuNStr(vo.getProvinceCode())){
				codeList.add(vo.getProvinceCode());
			}
			if(!Check.NuNStr(vo.getCityCode())){
				codeList.add(vo.getCityCode());
			}
			if(!Check.NuNStr(vo.getAreaCode())){
				codeList.add(vo.getAreaCode());
			}
			String cityNameJson=confCityService.getCityNameByCodeList(JsonEntityTransform.Object2Json(codeList));
			List<ConfCityEntity> cityList=SOAResParseUtil.getListValueFromDataByKey(cityNameJson, "cityList", ConfCityEntity.class);
			if(!Check.NuNStr(vo.getNationCode())){
				vo.setNationName(cityList.get(0).getShowName());
			}
			if(!Check.NuNStr(vo.getProvinceCode())){
				vo.setProvinceName(cityList.get(1).getShowName());
			}
			if(!Check.NuNStr(vo.getCityCode())){
				vo.setCityName(cityList.get(2).getShowName());
			}
			if(!Check.NuNStr(vo.getAreaCode())){
				vo.setAreaName(cityList.get(3).getShowName());
			}
		}
		List<SystemsEntity> sysList=systemService.findAllSystem();
		request.setAttribute("sysList", sysList);
		request.setAttribute("cityTreeList", JsonEntityTransform.Object2Json(cityTreeList));
		request.setAttribute("userInfo", currentuserInfo);
		return "user/editCurrentuser";
	}

	/**
	 * 保存用户
	 * @author jixd
	 * @created 2016年12月08日 16:32:09
	 * @param
	 * @return
	 */
	@RequestMapping("insertCurrentuser")
	@ResponseBody
	public DataTransferObject insertCurrentuser(@ModelAttribute CurrentuserVo vo){
		DataTransferObject dto=new DataTransferObject();
		vo.setFid(UUIDGenerator.hexUUID());
		userService.saveCurrentUser(vo);
		return dto;
	}


	/**
	 * 更新用户信息
	 * @author jixd
	 * @created 2016年12月08日 19:26:26
	 * @param
	 * @return
	 */
	@RequestMapping("/updateCurrentuser")
	@ResponseBody
	public DataTransferObject updateCurrentuser(@ModelAttribute CurrentuserVo vo){
		DataTransferObject dto=new DataTransferObject();
		userService.updateCurrentUser(vo);
		return dto;
	}


	/**
	 * 员工列表
	 *
	 * @param
	 * @return
	 * @author jixd
	 * @created 2016年12月08日 15:33:15
	 */
	@RequestMapping("/employeeList")
	@ResponseBody
	public PageResult employeeList(@ModelAttribute("paramRequest") EmployeeRequest paramRequest) {
		try {
			PagingResult<EmployeeEntity> empList=employeeService.findEmployeeForPage(paramRequest);
			PageResult pageResult = new PageResult();
			pageResult.setRows(empList.getRows());
			pageResult.setTotal(empList.getTotal());
			return pageResult;
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			return new PageResult();
		}
	}


	/**
	 * @param
	 * @return
	 * @author jixd
	 * @created 2016年12月08日 15:40:08
	 */
	@RequestMapping("showRoles")
	@ResponseBody
	public PagingResult<RoleListVo> showRoles(RoleListRequest roleRequest) {
		try {
			return roleService.findRolePageList(roleRequest);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
		}
		return new PagingResult<RoleListVo>();
	}


	/**
	 * 账号启用停用
	 * @author jixd
	 * @created 2016年12月09日 17:34:22
	 * @param
	 * @return
	 */
	@RequestMapping("editUserStatus")
	@ResponseBody
	public DataTransferObject editUserStatus(String uid) {
		DataTransferObject dto = new DataTransferObject();
		try {
			CurrentuserVo currentuserVo = userService.getCurrentuserVoByfid(uid);
			if (Check.NuNObj(currentuserVo)){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("用户不存在");
				return dto;
			}
			Integer accountStatus = currentuserVo.getAccountStatus();
			accountStatus = accountStatus == 1 ? 0:1;
			currentuserVo.setAccountStatus(accountStatus);
			userService.updateCurrentUserStatus(currentuserVo);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg(e.getMessage());
		}
		return dto;
	}
}