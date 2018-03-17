package com.asura.amp.authority.operator.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asura.amp.authority.entity.Operator;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.paging.SearchModel;
import com.asura.framework.dao.old.BaseIbatisDAO;
import com.ibatis.sqlmap.client.SqlMapClient;

/**
 * 
 * <p>
 * 操作员对象数据库操作类
 * </p>
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
@Service("operatorDao")
public class OperatorDao extends BaseIbatisDAO {

	@Autowired
	public void setSystemSqlMapClient(SqlMapClient ampSqlMapClient) {
		super.setCurrentSqlMapClient(ampSqlMapClient);
	}

	/**
	 * 
	 * 保存
	 * 
	 * @author zhangshaobin
	 * @created 2013-1-26 下午2:07:08
	 * 
	 * @param operator
	 */
	public void save(Operator operator) {
		this.save("com.asura.management.authority.operator.dao.save", operator);
	}

	/**
	 * 
	 * 更新
	 * 
	 * @author zhangshaobin
	 * @created 2013-1-26 下午3:10:12
	 * 
	 * @param operator
	 */
	public void update(Operator operator) {
		this.update("com.asura.management.authority.operator.dao.update",
				operator);
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
	public void updatePassword(Operator operator) {
		this.update("com.asura.management.authority.operator.dao.updatePassword",
				operator);
	}

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
	public PagingResult<Operator> findForPage(SearchModel searchModel) {
		return this.findForPage(
						"com.asura.management.authority.operator.dao.findCountBySearchCondition",// 查询记录数的sqlid
						"com.asura.management.authority.operator.dao.findBySearchCondition",// 查询数据的sqlid
						searchModel, Operator.class);
	}
	
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
	public int delete(Map<String,Object> map){
		return this.delete("com.asura.management.authority.operator.dao.updateStatu", map);
	}
	
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
	public Operator logon(Map<String, Object> map){
		return this.findOne("com.asura.management.authority.operator.dao.logon", Operator.class, map);
	}
	
	/**
	 * uas 集成   根据登陆用户名查询用户信息
	 * @param map
	 * @return
	 */
	public Operator findOperatorByLoginName(Map<String, Object> map){
		return this.findOne("com.asura.management.authority.operator.dao.findOperatorByLoginName", Operator.class, map);
	}

}
