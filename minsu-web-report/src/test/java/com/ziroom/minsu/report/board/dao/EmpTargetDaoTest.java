package com.ziroom.minsu.report.board.dao;

import java.util.Date;

import javax.annotation.Resource;

import org.junit.Test;

import com.asura.framework.base.util.DateUtil;
import com.ziroom.minsu.report.board.entity.EmpTargetEntity;
import com.ziroom.minsu.report.board.entity.EmpTargetLogEntity;

import base.BaseTest;

/**
 * <p></p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author lishaochuan on 2016/12/15 11:41
 * @version 1.0
 * @since 1.0
 */
public class EmpTargetDaoTest extends BaseTest {

    @Resource(name="report.empTargetDao")
    private EmpTargetDao empTargetDao;

    @Resource(name="report.empTargetLogDao")
    private EmpTargetLogDao empTargetLogDao;

    @Test
    public void testEmpTargetInsert(){
        EmpTargetEntity empTargetEntity = new EmpTargetEntity();
        empTargetEntity.setTargetHouseNum(50);
        empTargetEntity.setTargetMonth(DateUtil.dateFormat(new Date()));
        empTargetEntity.setEmpCode("20233512");
        empTargetEntity.setEmpName("sdfsfsdf");
        empTargetEntity.setCreateEmpCode("20233512");
        empTargetEntity.setCreateEmpName("sldjfsl");
        empTargetDao.insertEmpTarget(empTargetEntity);
    }

    @Test
    public void testEmpTargetUpdate(){
        EmpTargetEntity empTargetEntity = new EmpTargetEntity();
        empTargetEntity.setFid("8a9e988b59822af70159822af7020000");
        empTargetEntity.setTargetHouseNum(500);
        empTargetEntity.setTargetMonth(DateUtil.dateFormat(new Date()));
        empTargetDao.updateByFid(empTargetEntity);

    }

    @Test
    public void testInsertEmpTargetLog(){
        EmpTargetLogEntity empTargetLogEntity = new EmpTargetLogEntity();
        empTargetLogEntity.setCreateEmpCode("20233512");
        empTargetLogEntity.setCreateEmpName("sdfsfs");
        empTargetLogEntity.setTargetFid("8a9e988b59822af70159822af7020000");
        empTargetLogEntity.setTargetHouseNum(200);
        empTargetLogDao.insertEmpTargetLog(empTargetLogEntity);

    }


}