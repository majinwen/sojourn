package com.ziroom.minsu.services.house.test.dao;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.minsu.entity.house.AbHouseRelateEntity;
import com.ziroom.minsu.services.house.airbnb.dto.AbHouseDto;
import com.ziroom.minsu.services.house.airbnb.vo.AbHouseRelateVo;
import com.ziroom.minsu.services.house.dao.AbHouseRelateDao;
import com.ziroom.minsu.services.house.test.base.BaseTest;

/**
 * Created by homelink on 2017/4/15.
 */
public class AbHouseRelateDaoTest extends BaseTest{

    @Resource(name="house.abHouseRelateDao")
    private AbHouseRelateDao abHouseRelateDao;

    @Test
    public void testSaveEntity(){
        AbHouseRelateEntity abHouseRelateEntity = new AbHouseRelateEntity();
        abHouseRelateEntity.setHouseFid("sdfsdfsdf");
        abHouseRelateEntity.setCalendarUrl("http://sdfsfsdfslsldf");
        abHouseRelateEntity.setAbSn("sdfsf");
        abHouseRelateEntity.setRentWay(0);
        abHouseRelateDao.save(abHouseRelateEntity);
    }

    @Test
    public void testlistRelateByPage(){
        AbHouseDto abHouseDto = new AbHouseDto();
        abHouseDto.setPage(1);
        abHouseDto.setLimit(5);
        List<String> landlordUidList = new ArrayList<>();
    	landlordUidList.add("a06f82a2-423a-e4e3-4ea8-e98317540190");
    	abHouseDto.setLandlordUidList(landlordUidList);
        PagingResult<AbHouseRelateVo> list = abHouseRelateDao.listRelateByPage(abHouseDto);
        
        System.out.println(JsonEntityTransform.Object2Json(list));
        
    }

    @Test
    public void testfindRelateByHouseFid(){
        AbHouseDto abHouseDto = new AbHouseDto();
        abHouseDto.setHouseFid("8a9084df54c22d180154c275d846000f");
        abHouseDto.setRentWay(0);
        AbHouseRelateVo vo = abHouseRelateDao.findRelateByHouseFid(abHouseDto);
        System.out.println(JsonEntityTransform.Object2Json(vo));
    }
    
    @Test
    public void testupdateHouseRelateByFid(){
    	AbHouseRelateVo abHouseRelateVo = new AbHouseRelateVo();
    	abHouseRelateVo.setFid("8a9084df5ba933e3015ba938a55d0016");
    	abHouseRelateVo.setIsDel(1);
        int upNum = abHouseRelateDao.updateHouseRelateByFid(abHouseRelateVo);
        System.out.println(JsonEntityTransform.Object2Json(upNum));
    }
}
