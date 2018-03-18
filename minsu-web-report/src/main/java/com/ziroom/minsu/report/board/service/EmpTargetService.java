/**
 * @FileName: EmpTargetService.java
 * @Package com.ziroom.minsu.report.board.service
 * 
 * @author bushujie
 * @created 2017年1月13日 下午5:18:07
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.report.board.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.minsu.report.board.dao.EmpTargetDao;
import com.ziroom.minsu.report.board.dao.EmpTargetLogDao;
import com.ziroom.minsu.report.board.entity.EmpTargetEntity;
import com.ziroom.minsu.report.board.entity.EmpTargetLogEntity;
import com.ziroom.minsu.services.basedata.entity.UpsUserVo;

/**
 * <p>专员目标业务层</p>
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
@Service("report.empTargetService")
public class EmpTargetService {
	
	@Resource(name="report.empTargetDao")
	private EmpTargetDao empTargetDao;
	
	@Resource(name="report.empTargetLogDao")
	private EmpTargetLogDao empTargetLogDao;
	
	/**
	 * 
	 * 插入专员月份目标
	 *
	 * @author bushujie
	 * @created 2017年1月13日 下午5:29:32
	 *
	 * @param empTargetEntity
	 */
	public String insertUpEmpTarget(EmpTargetEntity empTargetEntity,UpsUserVo userVo){
		String empTargetFid="";
		//判断月份目标是否存在
		EmpTargetEntity et=empTargetDao.findEmpTargetByMcode(empTargetEntity.getEmpCode(), empTargetEntity.getTargetMonth());
		if(!Check.NuNObj(et)){
			EmpTargetEntity upE=new EmpTargetEntity();
			upE.setFid(et.getFid());
			upE.setTargetHouseNum(empTargetEntity.getTargetHouseNum());
			empTargetDao.updateByFid(upE);
			empTargetFid=et.getFid();
		} else {
			empTargetEntity.setFid(UUIDGenerator.hexUUID());
			empTargetEntity.setCreateEmpCode(userVo.getEmployeeEntity().getEmpCode());
			empTargetEntity.setCreateEmpName(userVo.getEmployeeEntity().getEmpName());
			empTargetDao.insertEmpTarget(empTargetEntity);
			empTargetFid=empTargetEntity.getFid();
		}
		
		//保存日志
		EmpTargetLogEntity empTargetLogEntity=new EmpTargetLogEntity();
		empTargetLogEntity.setTargetFid(empTargetFid);
		empTargetLogEntity.setTargetHouseNum(empTargetEntity.getTargetHouseNum());
		empTargetLogEntity.setCreateEmpName(userVo.getEmployeeEntity().getEmpName());
		empTargetLogEntity.setCreateEmpCode(userVo.getEmployeeEntity().getEmpCode());
		empTargetLogDao.insertEmpTargetLog(empTargetLogEntity);
		return empTargetFid;
	}
	
	/**
	 * 
	 * 专员code和月份查询目标
	 *
	 * @author bushujie
	 * @created 2017年1月16日 上午10:37:56
	 *
	 * @param targetMonth
	 * @param empCode
	 * @return
	 */
	public EmpTargetEntity findEmpTargetByMcode(String targetMonth,String empCode){
		return empTargetDao.findEmpTargetByMcode(empCode, targetMonth);
	}
	
	/**
	 * 
	 * 查询目标操作日志
	 *
	 * @author bushujie
	 * @created 2017年1月16日 下午12:05:14
	 *
	 * @param targetFid
	 * @return
	 */
	public List<EmpTargetLogEntity> findCityTargetLog(String targetFid){
		return empTargetLogDao.findEmpTargetLog(targetFid);
	}
}
