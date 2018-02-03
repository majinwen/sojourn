package com.ziroom.minsu.services.basedata.dao;


import java.util.List;
import java.util.Map;

import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.util.Check;

import com.ziroom.minsu.services.basedata.entity.CurrentuserCityVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.sys.CurrentuserEntity;
import com.ziroom.minsu.services.basedata.dto.CurrentuserRequest;
import com.ziroom.minsu.services.basedata.entity.CurrentuserVo;

import java.util.HashMap;

/**
 * 
 * <p>后台用户数据库操作类</p>
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
@Repository("basedata.currentuserDao")
public class CurrentuserDao {
	
	private String SQLID="basedata.currentuserDao.";
	
	@Autowired
	@Qualifier("basedata.MybatisDaoContext")
	private MybatisDaoContext mybatisDaoContext;
	
	/**
     * 日志对象
     */
    private static Logger logger = LoggerFactory.getLogger(CurrentuserDao.class);




	/**
	 *
	 * 插入后台用户表记录
	 *
	 * @author bushujie
	 * @created 2016年3月8日 下午4:10:16
	 *
	 * @param currentuserEntity
	 */
	public void saveCurrentuserById(CurrentuserEntity currentuserEntity){
		if(Check.NuNObj(currentuserEntity)){
			return;
		}
		if(Check.NuNObj(currentuserEntity.getFid())){
			
			LogUtil.info(logger,"the id is null on update currentuserEntity");
			throw  new BusinessException("the id is null on update currentuserEntity");
		}
		mybatisDaoContext.save(SQLID+"updateByPrimaryKeySelective", currentuserEntity);
	}


	/**
	 * 
	 * 插入后台用户表记录
	 *
	 * @author bushujie
	 * @created 2016年3月8日 下午4:10:16
	 *
	 * @param currentuserEntity
	 */
	public void insertCurrentuser(CurrentuserEntity currentuserEntity){
		mybatisDaoContext.save(SQLID+"insertCurrentuser", currentuserEntity);
	}

	/**
	 * 通过userFid 获取当前的用户信息
	 * @param userFid
	 * @return
	 */
	public CurrentuserEntity getCurrentuserByFid(String userFid){
		Map<String,Object> par = new HashMap<>();
		par.put("userFid",userFid);
		return  mybatisDaoContext.findOne(SQLID + "getCurrentuserByFid", CurrentuserEntity.class,par);
	}


	/**
	 * 
	 * 分页查询后台用户列表
	 *
	 * @author bushujie
	 * @created 2016年3月9日 上午10:54:08
	 *
	 * @param currentuserRequest
	 * @return
	 */
	public PagingResult<CurrentuserVo> findCurrentuserPageList(CurrentuserRequest currentuserRequest){
		PageBounds pageBounds=new PageBounds();
		pageBounds.setLimit(currentuserRequest.getLimit());
		pageBounds.setPage(currentuserRequest.getPage());
		return mybatisDaoContext.findForPage(SQLID + "findCurrentuserByCondition", CurrentuserVo.class, currentuserRequest, pageBounds);
	}
	/**
	 * 
	 * 插入用户角色关系表
	 *
	 * @author bushujie
	 * @created 2016年3月12日 下午4:45:28
	 *
	 * @param paramMap
	 */
	public void insertCurrentuserRole(Map<String, Object> paramMap){
		mybatisDaoContext.getWriteSqlSessionTemplate().insert(SQLID +"insertUserRole", paramMap);
	}
	
	/**
	 * 
	 * 查询用户信息
	 *
	 * @author bushujie
	 * @created 2016年3月16日 上午10:47:02
	 *
	 * @param fid
	 * @return
	 */
	public CurrentuserVo getCurrentuserVoByfid(String fid){
		return mybatisDaoContext.findOne(SQLID+"getCurrentuserVoByfid", CurrentuserVo.class, fid);
	}
	
	/**
	 * 
	 * 用户名称获取用户信息
	 *
	 * @author bushujie
	 * @created 2016年3月16日 下午8:00:33
	 *
	 * @param userAccount
	 * @return
	 */
	public CurrentuserVo getCurrentuserEntityByAccount(String userAccount){
		return mybatisDaoContext.findOne(SQLID+"getCurrentuserByUserAccount", CurrentuserVo.class, userAccount);
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
		return mybatisDaoContext.update(SQLID+"updateCurrentuserByFid", user);
	}
	
	/**
	 * 
	 *  插入用户负责区域
	 *
	 * @author bushujie
	 * @created 2016年10月21日 下午2:32:15
	 *
	 * @param paramMap
	 */
	public void insertCurrentuserCity(Map<String, Object> paramMap){
		mybatisDaoContext.getWriteSqlSessionTemplate().insert(SQLID+"insertCurrentuserCity", paramMap );
	}
	
	/**
	 * 
	 * 查询权限角色类型
	 *
	 * @author bushujie
	 * @created 2016年10月26日 上午11:17:20
	 *
	 * @param paramMap
	 * @return
	 */
	public  List<Integer> findRoleTypeByMenu(Map<String, Object> paramMap){
		return mybatisDaoContext.findAll(SQLID+"findRoleTypeByMenu", Integer.class, paramMap);
	}

}
