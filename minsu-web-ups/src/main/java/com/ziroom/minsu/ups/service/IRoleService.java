package com.ziroom.minsu.ups.service;


import java.util.List;
import java.util.Map;

import com.asura.framework.base.paging.PagingResult;
import com.ziroom.minsu.entity.sys.RoleEntity;
import com.ziroom.minsu.services.basedata.dto.RoleRequest;
import com.ziroom.minsu.ups.dto.RoleListRequest;
import com.ziroom.minsu.ups.vo.RoleListVo;

/**
 * <p>角色相关接口</p>
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
public interface IRoleService {

    /**
     * 角色列表
     * @param roleRequest
     * @return
     */
    PagingResult<RoleListVo> findRolePageList(RoleListRequest roleRequest);
    
    /**
     * 
     *保存角色
     *
     * @author bushujie
     * @created 2016年12月8日 下午5:24:49
     *
     * @param roleEntity
     * @param resourceFids
     */
    void insertRole(RoleEntity roleEntity,String[] resourceFids);
    
    /**
     * 
     * fid查询角色
     *
     * @author bushujie
     * @created 2016年12月8日 下午7:39:28
     *
     * @param roleFid
     * @return
     */
    RoleEntity getRoleEntityByFid(String roleFid);
    
    /**
     * 
     * 更新角色权限
     *
     * @author bushujie
     * @created 2016年12月8日 下午8:54:37
     *
     * @param roleEntity
     * @param resourceFids
     */
    void updateRole(RoleEntity roleEntity,String[] resourceFids);

    /**
     * 更新角色
     * @param roleEntity
     * @return
     */
    int updateRole(RoleEntity roleEntity);
    
    /**
     * 
     * 查询拥有此角色用户和系统信息
     *
     * @author bushujie
     * @created 2016年12月14日 下午3:48:08
     *
     * @param roleFid
     * @return
     */
    List<Map> getSysUserByRole(String roleFid);
}
