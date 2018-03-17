package com.ziroom.minsu.ups.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.asura.framework.base.paging.PagingResult;
import com.ziroom.minsu.entity.sys.RoleEntity;
import com.ziroom.minsu.services.basedata.dto.RoleRequest;
import com.ziroom.minsu.ups.dao.RoleDao;
import com.ziroom.minsu.ups.dao.RoleResDao;
import com.ziroom.minsu.ups.dto.RoleListRequest;
import com.ziroom.minsu.ups.service.IRoleService;
import com.ziroom.minsu.ups.vo.RoleListVo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>角色服务实现</p>
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
@Service("ups.roleService")
public class RoleServiceImpl implements IRoleService{

    @Autowired
    private RoleDao roleDao;
    
    @Resource(name="ups.roleResDao")
    private RoleResDao roleResDao;

    /**
     * 查找角色列表
     * @param roleRequest
     * @return
     */
    @Override
    public PagingResult<RoleListVo> findRolePageList(RoleListRequest roleRequest) {
        return roleDao.findRolePageList(roleRequest);
    }

	/* (non-Javadoc)
	 * @see com.ziroom.minsu.ups.service.IRoleService#insertRole(com.ziroom.minsu.entity.sys.RoleEntity, java.lang.String[])
	 */
	@Override
	public void insertRole(RoleEntity roleEntity, String[] resourceFids) {
		roleDao.insertRole(roleEntity);
		roleResDao.saveRoleResources(roleEntity.getFid(), resourceFids);
	}

	/* (non-Javadoc)
	 * @see com.ziroom.minsu.ups.service.IRoleService#getRoleEntityByFid(java.lang.String)
	 */
	@Override
	public RoleEntity getRoleEntityByFid(String roleFid) {
		return roleDao.findRoleByFid(roleFid);
	}

	/* (non-Javadoc)
	 * @see com.ziroom.minsu.ups.service.IRoleService#updateRole(com.ziroom.minsu.entity.sys.RoleEntity, java.lang.String[])
	 */
	@Override
	public void updateRole(RoleEntity roleEntity, String[] resourceFids) {
		//更新角色类型
		roleDao.updateRole(roleEntity);
		//删除旧权限
		roleResDao.delRoleResourcesByFid(roleEntity.getFid());
		//保存新权限
		roleResDao.saveRoleResources(roleEntity.getFid(), resourceFids);
	}

	@Override
	public int updateRole(RoleEntity roleEntity) {
		return roleDao.updateRole(roleEntity);

	}

	/* (non-Javadoc)
	 * @see com.ziroom.minsu.ups.service.IRoleService#getSysUserByRole(java.lang.String)
	 */
	@Override
	public List<Map> getSysUserByRole(String roleFid) {
		return roleDao.getSysUserByRole(roleFid);
	}
}
