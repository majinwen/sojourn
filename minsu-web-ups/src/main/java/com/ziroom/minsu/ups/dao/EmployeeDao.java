/**
 * @FileName: EmployeeDao.java
 * @Package com.ziroom.minsu.services.basedata.dao
 * 
 * @author liujun
 * @created 2016年3月12日 下午3:20:07
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.ups.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.ziroom.minsu.entity.sys.EmployeeEntity;
import com.ziroom.minsu.services.basedata.dto.EmployeeRequest;

/**
 * 
 * <p>用户测试</p>
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
@Repository("ups.employeeDao")
public class EmployeeDao {
	
	private String SQLID="ups.employeeDao.";
	
	@Autowired
	@Qualifier("ups.MybatisDaoContext")
	private MybatisDaoContext mybatisDaoContext;
	
	/**
	 * 
	 * 员工分页查询
	 *
	 * @author bushujie
	 * @created 2016年3月12日 下午3:35:20
	 *
	 * @param employeeRequest
	 * @return
	 */
	public PagingResult<EmployeeEntity> findEmployeeForPage(EmployeeRequest employeeRequest){
		PageBounds pageBounds=new PageBounds();
		pageBounds.setLimit(employeeRequest.getLimit());
		pageBounds.setPage(employeeRequest.getPage());
		return mybatisDaoContext.findForPage(SQLID+"findEmployeeList", EmployeeEntity.class, employeeRequest, pageBounds);
	}
	
	/**
	 * 
	 * 插入ehr同步过来的数据
	 *
	 * @author jixd
	 * @created 2016年4月23日 下午2:14:06
	 *
	 * @param syncEntity
	 */
	public int insertEmployeeSysc(EmployeeEntity syncEntity){
		if(Check.NuNObj(syncEntity)){
			return 0;
		}
		if(Check.NuNStr(syncEntity.getFid())){
			throw new BusinessException("the id is null");
		}
		return mybatisDaoContext.save(SQLID+"insertSelective", syncEntity);
	}
	/**
	 * 
	 * 根据员工号获取同步员工号，如果已存在则更新操作
	 *
	 * @author jixd
	 * @created 2016年4月23日 下午3:45:27
	 *
	 * @param empCode
	 * @return
	 */
	public EmployeeEntity getEmployeeEntity(String empCode){
		return mybatisDaoContext.findOne(SQLID+"selectByEmployCodeAndStatus", EmployeeEntity.class, empCode);
	}
	/**
	 * 
	 * 更新同步员工数据
	 *
	 * @author jixd
	 * @created 2016年4月23日 下午3:47:41
	 *
	 * @param syncEntity
	 * @return
	 */
	public int updateEmployeeEntity(EmployeeEntity syncEntity){
		return mybatisDaoContext.update(SQLID+"updateByfid", syncEntity);
	}

	/**
	 * 根据查询条件查询员工信息
	 *
	 * @author liujun
	 * @created 2016年7月7日
	 *
	 * @param emp
	 * @return
	 */
	public List<EmployeeEntity> findEmployeeByCondition(EmployeeRequest empRequest) {
		return mybatisDaoContext.findAll(SQLID+"findEmployeeList", EmployeeEntity.class, empRequest);
	}

	/**
	 * 根据员工表fid查询员工信息
	 *
	 * @author lunan
	 * @created 2016年8月19日 上午9:57:30
	 *
	 * @param empFid
	 * @return
	 */
	public EmployeeEntity getEmployeeEntityFid(String empFid) {
		return mybatisDaoContext.findOne(SQLID+"selectByEmployFid", EmployeeEntity.class, empFid);
	}
}
