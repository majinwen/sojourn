package com.ziroom.minsu.ups.service;

import java.util.List;
import java.util.Map;

import com.asura.framework.base.paging.PagingResult;
import com.ziroom.minsu.entity.sys.CurrentuserCityEntity;
import com.ziroom.minsu.entity.sys.CurrentuserEntity;
import com.ziroom.minsu.services.basedata.dto.CurrentuserRequest;
import com.ziroom.minsu.services.basedata.entity.CurrentuserCityVo;
import com.ziroom.minsu.services.basedata.entity.CurrentuserVo;

/**
 * <p>用户相关</p>
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
public interface ICurrentUserService {

    PagingResult<CurrentuserVo> findCurrentuserPageList(CurrentuserRequest currentuserRequest);
    
    /**
     * 
     * 用户名查询用户信息
     *
     * @author bushujie
     * @created 2016年12月7日 下午4:12:10
     *
     * @param userAccount
     * @return
     */
    CurrentuserVo getCurrentuserByUserAccount(String userAccount);

    /**
     * 获取用户信息
     * @author jixd
     * @created 2016年12月08日 14:21:11
     * @param
     * @return
     */
    CurrentuserVo currentUserInfo(String fid);

    /**
     * 保存用户信息
     * @author jixd
     * @created 2016年12月08日 16:19:04
     * @param
     * @return
     */
    int saveCurrentUser(CurrentuserVo vo);

    /**
     * 更新用户信息
     * @author jixd
     * @created 2016年12月08日 17:54:49
     * @param
     * @return
     */
    int updateCurrentUser(CurrentuserVo vo);

    /**
     * 获取用户基本信息
     * @author jixd
     * @created 2016年12月09日 17:37:07
     * @param
     * @return
     */
    CurrentuserVo getCurrentuserVoByfid(String fid);

    /**
     * 更新
     * @author jixd
     * @created 2016年12月09日 17:44:51
     * @param
     * @return
     */
    int updateCurrentUserStatus(CurrentuserEntity currentuserEntity);

    /**
     * 插入用户
     * @author jixd
     * @created 2016年12月12日 17:19:56
     * @param
     * @return
     */
    int insertOrUpdateCurrentuser(CurrentuserEntity currentuserEntity);
    
    /**
     * 
     * 用户fid查询负责城市列表
     *
     * @author bushujie
     * @created 2016年12月20日 下午2:57:22
     *
     * @param fid
     * @return
     */
    List<CurrentuserCityVo> getCurrentuserCityListByUserFid(String fid);
    
    /**
     * 
     * 查询权限角色类型 
     *
     * @author bushujie
     * @created 2016年12月20日 下午8:08:46
     *
     * @param paramMap
     * @return
     */
    public  int findRoleTypeByMenu(Map<String, Object> paramMap);
    
    /**
     * 
     * 查询用户负责区域列表
     *
     * @author bushujie
     * @created 2016年12月20日 下午8:20:27
     *
     * @param currentuserFid
     * @return
     */
    public List<CurrentuserCityEntity> findCuserCityByUserFid(String currentuserFid);
    
    /**
     * 
     * 查询登录用户信息
     *
     * @author bushujie
     * @created 2017年10月28日 下午4:01:22
     *
     * @param userAccount
     * @return
     */
    CurrentuserEntity getCurrentByUserAccount(String userAccount);

    /**
     *
     * 根据员工fid查询用户信息
     * 
     * @author zhangyl2
     * @created 2018年02月05日 18:19
     * @param 
     * @return 
     */
    CurrentuserEntity getCurrentByEmpFid(String employeeFid);
}
