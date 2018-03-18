package com.ziroom.minsu.report.test.board.dao;

import base.BaseTest;

import com.asura.framework.base.util.DateUtil;

import com.ziroom.minsu.report.board.dto.EmpStatsRequest;
import com.ziroom.minsu.report.board.entity.EmpTargetEntity;
import com.ziroom.minsu.report.board.entity.EmpTargetLogEntity;
import com.ziroom.minsu.report.board.dao.EmpTargetDao;
import com.ziroom.minsu.report.board.dao.EmpTargetLogDao;
import com.ziroom.minsu.report.house.dao.HouseDao;
import org.junit.Test;

import javax.annotation.Resource;

import java.util.Date;

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

    @Resource(name="report.houseDao")
    private HouseDao houseDao;

    @Test
    public void testEmpTargetInsert(){
        EmpTargetEntity empTargetEntity = new EmpTargetEntity();
        empTargetEntity.setTargetHouseNum(50);
        empTargetEntity.setTargetMonth(DateUtil.dateFormat(new Date(), "yyyy-MM"));
        empTargetEntity.setEmpCode("20080808");
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
        empTargetEntity.setTargetMonth(DateUtil.dateFormat(new Date(), "yyyy-MM"));
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



    @Test
    public void testcountHousePushNum(){
        EmpStatsRequest request = new EmpStatsRequest();
        request.setEmpCode("20127364");
        request.setStartTime("2017-01-01");
        request.setEndTime("2017-01-18");
        request.setHouseStatus(11);
        long l = houseDao.countHousePushNum(request);
        System.err.println(l);
    }

}