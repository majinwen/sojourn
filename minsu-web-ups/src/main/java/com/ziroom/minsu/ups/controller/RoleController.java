package com.ziroom.minsu.ups.controller;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.sys.RoleEntity;
import com.ziroom.minsu.entity.sys.SystemsEntity;
import com.ziroom.minsu.services.basedata.dto.RoleRequest;
import com.ziroom.minsu.services.basedata.entity.ResourceVo;
import com.ziroom.minsu.services.basedata.entity.TreeNodeVo;
import com.ziroom.minsu.services.common.utils.RedisKeyConst;
import com.ziroom.minsu.services.common.utils.SentinelJedisUtil;
import com.ziroom.minsu.ups.common.constant.Constant;
import com.ziroom.minsu.ups.common.util.UserUtil;
import com.ziroom.minsu.ups.dto.RoleListRequest;
import com.ziroom.minsu.ups.service.IResourceService;
import com.ziroom.minsu.ups.service.IRoleService;
import com.ziroom.minsu.ups.service.ISystemService;
import com.ziroom.minsu.ups.vo.RoleListVo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * <p>角色controller</p>
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
@Controller
@RequestMapping("/role")
public class RoleController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RoleController.class);

    @Autowired
    private IRoleService roleService;
    
    @Resource(name="ups.systemService")
    private ISystemService systemService;
    
    @Resource(name="ups.resourceService")
    private IResourceService resourceService;
    
    @Resource(name="sentinelJedisClient")
    private SentinelJedisUtil sentinelJedisClient;
    /**
     * 跳转角色列表
     * @param request
     */
    @RequestMapping("/listRoles")
    public void listRoles(HttpServletRequest request) {
    	List<SystemsEntity> sysList=systemService.findAllSystem();
    	request.setAttribute("sysList", sysList);
    }

    /**
     *
     * 角色管理-根据条件查询角色列表
     *
     * @author jixd
     * @param roleRequest
     */
    @RequestMapping("/showRoles")
    @ResponseBody
    public PagingResult<RoleListVo> showRoles(RoleListRequest roleRequest) {
        try {
            PagingResult<RoleListVo> rolePageList = roleService.findRolePageList(roleRequest);
            return rolePageList;
        } catch (Exception e) {
            LogUtil.error(LOGGER, "error:{}", e);
        }
        return null;
    }

    /**
     * 新增角色添加菜单
     */
    @RequestMapping("/addRoleResource")
    public void addRoleResource(HttpServletRequest request){
    	List<SystemsEntity> sysList=systemService.findAllSystem();
    	request.setAttribute("sysList", sysList);
    }
    
    /**
     * 
     * 系统fid查询系统权限树
     *
     * @author bushujie
     * @created 2016年12月8日 下午3:18:24
     *
     * @param systemFid
     * @return
     */
    @RequestMapping("listAllResource")
    @ResponseBody
	public DataTransferObject listAllResource(String systemFid) {
		DataTransferObject dto=new DataTransferObject();
		List<TreeNodeVo> treeNodeList=resourceService.findMenuTreeNodeVos(systemFid);
		dto.putValue("list", treeNodeList);
		String resultJson=dto.toJsonString();
		resultJson = resultJson.replaceAll("nodes", "children");
		return JsonEntityTransform.json2DataTransferObject(resultJson);
	}
    
    /**
     * 
     * 保存角色信息
     *
     * @author bushujie
     * @created 2016年12月8日 下午4:52:27
     *
     * @param roleName
     * @param resFids
     * @param roleType
     * @param systemsFid
     * @return
     */
    @RequestMapping("saveRoleResource")
    @ResponseBody
    public DataTransferObject saveRoleResource(String roleName, String resFids,Integer roleType,String systemsFid){
    	DataTransferObject dto=new DataTransferObject();
    	RoleEntity roleEntity=new RoleEntity();
    	roleEntity.setFid(UUIDGenerator.hexUUID());
    	roleEntity.setCreateFid(UserUtil.getCurrentUserFid());
    	roleEntity.setRoleName(roleName);
    	roleEntity.setSystemsFid(systemsFid);
    	roleEntity.setRoleType(roleType);
    	String[] resArr=resFids.split(",");
    	roleService.insertRole(roleEntity, resArr);
    	return dto;
    }
    
    /**
     * 
     * 编辑角色
     *
     * @author bushujie
     * @created 2016年12月8日 下午7:47:57
     *
     * @param roleFid
     * @param request
     */
    @RequestMapping("editRoleResource")
    public void editRoleResource(String roleFid,HttpServletRequest request){
    	RoleEntity roleEntity=roleService.getRoleEntityByFid(roleFid);
    	request.setAttribute("roleEntity", roleEntity);
    	List<SystemsEntity> sysList=systemService.findAllSystem();
    	request.setAttribute("sysList", sysList);
    }
    
    /**
     * 
     * 角色权限树
     *
     * @author bushujie
     * @created 2016年12月8日 下午8:30:43
     *
     * @param roleFid
     * @param systemFid
     * @return
     */
    @RequestMapping("listRoleResource")
    @ResponseBody
    public DataTransferObject listRoleResource(String roleFid,String systemFid){
    	DataTransferObject dto=new DataTransferObject();
    	List<TreeNodeVo> treeNodeList=resourceService.findTreeNodeResByRoleFid(roleFid, systemFid);
		dto.putValue("list", treeNodeList);
		String resultJson=dto.toJsonString();
		resultJson = resultJson.replaceAll("nodes", "children");
		return JsonEntityTransform.json2DataTransferObject(resultJson);
    }
    
    /**
     * 
     * 更新角色权限信息
     *
     * @author bushujie
     * @created 2016年12月8日 下午8:50:10
     *
     * @param roleFid
     * @param resFids
     * @return
     */
    @SuppressWarnings("rawtypes")
	@RequestMapping("updateRoleResource")
    @ResponseBody
    public DataTransferObject updateRoleResource(String roleFid,String resFids,Integer roleType){
    	DataTransferObject dto=new DataTransferObject();
    	RoleEntity roleEntity=new RoleEntity();
    	roleEntity.setFid(roleFid);
    	roleEntity.setRoleType(roleType);
    	roleService.updateRole(roleEntity, resFids.split(","));
    	//更新拥有此角色用户权限缓存
    	try{
	    	List<Map> sysuserMaps=roleService.getSysUserByRole(roleFid);
	    	for(Map map:sysuserMaps){
	    		String sysCode=(String) map.get("sys_code");
	    		String currenuserFid=(String) map.get("currenuser_fid");
	    		String sysFid=(String) map.get("systems_fid");
	    		String listKey=RedisKeyConst.getResCacheKey(sysCode,currenuserFid,Constant.RES_LIST);
	    		String setKey=RedisKeyConst.getResCacheKey(sysCode,currenuserFid,Constant.RES_SET);
	    		String resListJson=sentinelJedisClient.getex(listKey);
	    		String resSetJson=sentinelJedisClient.getex(setKey);
	    		if(!Check.NuNStr(resListJson)){
	    			List<ResourceVo> resourceVoList=resourceService.findResourceByCurrentuserId(currenuserFid, sysFid);
	    			sentinelJedisClient.setex(listKey, Constant.RES_CACHE_TIME, JsonEntityTransform.Object2Json(resourceVoList));
	    		}
	    		if(!Check.NuNStr(resSetJson)){
	    			Set<String> resourceVoSet=resourceService.findFnResourceSetByUid(sysFid, currenuserFid);
	    			sentinelJedisClient.setex(setKey, Constant.RES_CACHE_TIME, JsonEntityTransform.Object2Json(resourceVoSet));
	    		}
	    	}
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return dto;
    }

    /**
     * 开启关闭角色
     * @author jixd
     * @created 2016年12月09日 18:04:43
     * @param
     * @return
     */
    @RequestMapping("/editRoleStatus")
    @ResponseBody
    public DataTransferObject editRoleStatus(String roleFid){
        DataTransferObject dto = new DataTransferObject();
        RoleEntity roleEntity = roleService.getRoleEntityByFid(roleFid);
        if (Check.NuNObj(roleEntity)){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("角色不存在");
            return dto;
        }
        Integer isDel = roleEntity.getIsDel();
        isDel = 1^isDel;
        roleEntity.setIsDel(isDel);
        roleService.updateRole(roleEntity);
        return dto;
    }
}
