/**
 * @FileName: OperatorServiceImpl.java
 * @Package com.asura.amp.authority.operator.service.impl
 * 
 * @author zhangshaobin
 * @created 2013-1-26 下午2:14:31
 * 
 * Copyright 2011-2015 asura
 */
package com.asura.amp.authority.operator.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.asura.amp.authority.entity.Operator;
import com.asura.amp.authority.entity.OperatorRole;
import com.asura.amp.authority.operator.dao.OperatorDao;
import com.asura.amp.authority.operator.dao.OperatorRoleDao;
import com.asura.amp.authority.operator.service.OperatorService;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.paging.SearchModel;

/**
 * <p>操作员对象服务接口实现类</p>
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
@Service("operatorService")
public class OperatorServiceImpl implements OperatorService {
	
	@Autowired
	private OperatorDao operatorDao;
	
	@Autowired
	private OperatorRoleDao operatorRoleDao;

	@Override
	public void save(Operator operator) {
		operator.setLoginPwd(DigestUtils.md5DigestAsHex(operator.getLoginPwd().getBytes()));
		this.operatorDao.save(operator);
	}

	@Override
	public void update(Operator operator) {
		this.operatorDao.update(operator);
	}

	@Override
	public PagingResult<Operator> findForPage(SearchModel searchModel) {
		return this.operatorDao.findForPage(searchModel);
	}

	@Override
	public int delete(Map<String, Object> map) {
		return this.operatorDao.delete(map);
	}
	
	@Override
	public void saveOperatorRole(String operatorId, String roles){
		String [] roleArray = roles.split(",");
		OperatorRole operatorRole = new OperatorRole();
		for(String roleId : roleArray){
			operatorRole.setOperatorId(Integer.parseInt(operatorId));
			operatorRole.setRoleId(Integer.parseInt(roleId));
			this.operatorRoleDao.save(operatorRole);
		}
	}
	
	@Override
	public int deleteOperatorRoleByOperatorId(String operatorId){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("operatorId", operatorId);
		return this.operatorRoleDao.deleteByOperatorId(map);
	}
	
	@Override
	public String findAllRoleIdsByOperatorId(String operatorId){
		StringBuffer roleIds = new StringBuffer();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("operatorId", operatorId);
		List<OperatorRole> ors = this.operatorRoleDao.findAllByOperatorId(map);
		for(OperatorRole operatorRole : ors){
			roleIds.append(operatorRole.getRoleId()+"|");
		}
		return roleIds.toString();
	}
	
	@Override
	public Operator logon(Operator operator){
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("loginName", operator.getLoginName());
		map.put("loginPwd", DigestUtils.md5DigestAsHex(operator.getLoginPwd().getBytes()));
		return this.operatorDao.logon(map);
	}
	
	/**
	 * 
	 * 修改密码
	 *
	 * @author zhangshaobin
	 * @created 2013-3-14 下午8:29:53
	 *
	 * @param operator
	 */
	@Override
	public void updatePassword(Operator operator) {
		operator.setLoginPwd(DigestUtils.md5DigestAsHex(operator.getLoginPwd().getBytes()));
		this.operatorDao.updatePassword(operator);
	}
	
	
	@Override
	public Operator findOperatorByLoginName(String loginName){
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("loginName", loginName);
		return this.operatorDao.findOperatorByLoginName(map);
	}

}
