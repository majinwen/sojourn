package com.ziroom.minsu.report.board.dao;

import java.util.HashMap;
import java.util.Map;

import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.ziroom.minsu.report.board.entity.EmpTargetEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 大区信息
 * </p>
 * <p/>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd on 2017/1/19.
 * @version 1.0
 * @since 1.0
 */
@Repository("report.empTargetDao")
public class EmpTargetDao {

	private String SQLID = "report.empTargetDao.";

	@Autowired
	@Qualifier("minsuReport.report.MybatisDaoContext")
	private MybatisDaoContext mybatisDaoContext;


	/**
	 * 插入
	 * @author jixd
	 * @created 2017年01月09日 14:34:55
	 * @param
	 * @return
	 */
	public int insertEmpTarget(EmpTargetEntity empTargetEntity){
		if (Check.NuNStr(empTargetEntity.getFid())){
			empTargetEntity.setFid(UUIDGenerator.hexUUID());
		}
		return mybatisDaoContext.save(SQLID + "insert",empTargetEntity);
	}

	/**
	 * 更新
	 * @author jixd
	 * @created 2017年01月09日 14:35:39
	 * @param
	 * @return
	 */
	public int updateByFid(EmpTargetEntity empTargetEntity){
		return mybatisDaoContext.update(SQLID + "updateByFid",empTargetEntity);
	}

	/**
	 *
	 * 员工号查询唯一月份目标记录
	 *
	 * @author bushujie
	 * @created 2017年1月13日 下午6:16:45
	 *
	 * @param empCode
	 * @param targetMonth
	 * @return
	 */
	public EmpTargetEntity findEmpTargetByMcode(String empCode,String targetMonth){
		Map<String, Object> paramMap=new HashMap<String, Object>();
		paramMap.put("empCode", empCode);
		paramMap.put("targetMonth", targetMonth);
		return mybatisDaoContext.findOne(SQLID+"findEmpTargetByMcode", EmpTargetEntity.class, paramMap);
	}


}
