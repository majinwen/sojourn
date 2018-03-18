package com.ziroom.minsu.report.board.dao;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import com.asura.framework.base.paging.PagingResult;
import com.ziroom.minsu.report.board.dto.CityTargetRequest;
import com.ziroom.minsu.report.board.entity.CityTargetEntity;
import com.ziroom.minsu.report.board.entity.CityTargetLogEntity;
import com.ziroom.minsu.report.board.vo.CityTargetItem;
import com.ziroom.minsu.report.board.vo.RegionItem;

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
public class CityTargetDaoTest extends BaseTest {

    @Resource(name="report.cityTargetDao")
    private CityTargetDao cityTargetDao;

    @Resource(name="report.cityTargetLogDao")
    private CityTargetLogDao cityTargetLogDao;

    @Test
    public void testCityTargetInsert(){
        CityTargetEntity cityTargetEntity = new CityTargetEntity();
        cityTargetEntity.setCityCode("11100");
        cityTargetEntity.setCityName("北京");
        cityTargetEntity.setCreateEmpCode("20233512");
        cityTargetEntity.setCreateEmpName("llll");
        cityTargetEntity.setTargetHouseNum(12);
//        cityTargetEntity.setTargetMonth(new Date());
        cityTargetEntity.setTargetOrderNum(2);
        cityTargetEntity.setTargetPushHouseNum(34);
        cityTargetEntity.setTargetRentNum(30);
        cityTargetEntity.setTargetSelfHouseNum(23);
        cityTargetDao.insertCityTarget(cityTargetEntity);
    }

    @Test
    public void testCityTargetUpdate(){
        CityTargetEntity cityTargetEntity = new CityTargetEntity();
        cityTargetEntity.setFid("8a9e988b59821c160159821c163b0000");
        cityTargetEntity.setTargetHouseNum(100);
        cityTargetDao.updateByFid(cityTargetEntity);
    }

    @Test
    public void testInsertCityTargetLog(){
        CityTargetLogEntity cityTargetLogEntity = new CityTargetLogEntity();
        cityTargetLogEntity.setTargetHouseNum(30);
        cityTargetLogEntity.setTargetSelfHouseNum(20);
        cityTargetLogEntity.setTargetPushHouseNum(22);
        cityTargetLogEntity.setTargetOrderNum(90);
        cityTargetLogEntity.setTargetRentNum(200);
        cityTargetLogEntity.setCreateEmpCode("20233512");
        cityTargetLogEntity.setCreateEmpName("啦啦啦啦");
        cityTargetLogEntity.setTargetFid("dddddddfsdfsdfsdfsf");
        cityTargetLogDao.insertCityTargetLog(cityTargetLogEntity);

    }

    @Test
    public void testfindAll(){
        CityTargetRequest cityTargetRequest = new CityTargetRequest();
        cityTargetRequest.setRegionFid("8a9e988b59810f230159810f240b0000");
        List<CityTargetItem> targetCityList = cityTargetDao.findTargetCityList(cityTargetRequest);

    }

    @Test
    public void testFindFid(){
        CityTargetRequest cityTargetRequest = new CityTargetRequest();
        cityTargetRequest.setPage(1);
        cityTargetRequest.setLimit(2);
        PagingResult<RegionItem> regionItemPagingResult = cityTargetDao.groupByRegionFidList(cityTargetRequest);
    }


}