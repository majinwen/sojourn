/**
 * @FileName: OperatorService.java
 * @Package com.asura.amp.authority.operator.service
 * 
 * @author zhangshaobin
 * @created 2013-1-26 下午1:38:31
 * 
 * Copyright 2011-2015 asura
 */
package com.asura.amp.authority.operator.service;

import java.util.Map;

import com.asura.amp.authority.entity.Operator;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.paging.SearchModel;

/**
 * <p>操作员对象服务接口</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author zhangshaobin
 * @since 1.0
 * @version 1.0
 */
public interface OperatorService {
	
	/**
	 * 
	 * 保存
	 *
	 * @author zhangshaobin
	 * @created 2013-1-26 下午2:07:08
	 *
	 * @param operator
	 */
	public void save(Operator operator);
	
	/**
	 * 
	 * 更新
	 * 
	 * @author zhangshaobin
	 * @created 2013-1-26 下午3:10:12
	 * 
	 * @param operator
	 */
	public void update(Operator operator);
	
	/**
	 * 
	 * 按照条件查询
	 * 
	 * @author zhangshaobin
	 * @created 2013-1-26 下午3:12:09
	 * 
	 * @param searchModel
	 * @return
	 */
	public PagingResult<Operator> findForPage(SearchModel searchModel);
	
	/**
	 * 
	 * 删除
	 *
	 * @author zhangshaobin
	 * @created 2013-1-26 下午3:31:31
	 *
	 * @param ids
	 * @return
	 */
	public int delete(Map<String,Object> map);
	
	/**
	 * 
	 * 保存角色信息
	 *
	 * @author zhangshaobin
	 * @created 2013-1-28 下午2:02:02
	 *
	 * @param operatorId
	 * @param roles
	 */
	public void saveOperatorRole(String operatorId, String roles);
	
	/**
	 * 
	 * 根据操作员id删除对应的角色关系信息
	 *
	 * @author zhangshaobin
	 * @created 2013-1-28 下午1:52:29
	 *
	 * @param map 操作员id
	 * @return
	 */
	public int deleteOperatorRoleByOperatorId(String operatorId);
	
	
	/**
	 * 
	 * 根据操作员id获取对应的角色id, 使用|符号分割开
	 *
	 * @author zhangshaobin
	 * @created 2013-1-28 下午2:22:10
	 *
	 * @param operatorId  操作员id
	 * @return
	 */
	public String findAllRoleIdsByOperatorId(String operatorId);
	
	/**
	 * 
	 * 登录查询
	 *
	 * @author zhangshaobin
	 * @created 2013-1-28 下午6:16:17
	 *
	 * @param map
	 * @return
	 */
	public Operator logon(Operator operator);
	
	/**
	 * 
	 * 修改密码
	 *
	 * @author zhangshaobin
	 * @created 2013-3-14 下午8:29:53
	 *
	 * @param operator
	 */
	public void updatePassword(Operator operator) ;
	
	
	public Operator findOperatorByLoginName(String loginName) ;

}
