package com.ziroom.zrp.service.houses.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.ziroom.zrp.houses.entity.EmployeeEntity;
/**
 * <p>ZO信息查询DAO</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author xiangb
 * @version 1.0
 * @Date Created in 2017年9月18日
 * @since 1.0
 */
@Repository("houses.employeeDao")
public class EmployeeDao {

	private String SQLID = "houses.employeeDao.";

	@Autowired
	@Qualifier("houses.MybatisDaoContext")
	private MybatisDaoContext mybatisDaoContext;
	
	public EmployeeEntity findEmployeeById(String fid){
		return mybatisDaoContext.findOne(SQLID+"findEmployeeById", EmployeeEntity.class,fid);
	}
	
	public EmployeeEntity findEmployeeByCode(String fcode){
		return mybatisDaoContext.findOne(SQLID+"findEmployeeByCode", EmployeeEntity.class,fcode);
	}
}
