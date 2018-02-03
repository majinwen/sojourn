package com.ziroom.minsu.services.search.service;

import javax.annotation.Resource;

import org.junit.Test;

import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.minsu.services.search.dto.HouseInfoRequest;
import com.ziroom.minsu.services.search.dto.LandHouseRequest;
import com.ziroom.minsu.services.search.entity.QueryResult;

import base.BaseTest;

/**
 * <p>搜索测试</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/4/13.
 * @version 1.0
 * @since 1.0
 */
public class SearchServiceImplTest extends BaseTest {


    @Resource(name = "search.searchServiceImpl")
    private SearchServiceImpl searchService;


   @Test
    public void testgetHouseListInfo(){
    	HouseInfoRequest request = new HouseInfoRequest();
//    	request.setCityCode("110100");
    	request.setPage(1);
    	request.setLimit(100);
//    	request.setIsTargetCityLocal(1);
    	
    	request.setStartTime("2017-03-16");
    	request.setEndTime("2017-03-17");
    	request.setQ("8a9084df5a63f092015a63f4a0950017");
//    	request.setLongTermDiscount("ProductRulesEnum0019001");
//    	request.setInCityName("北京");
    	
    	QueryResult houseListInfo = searchService.getHouseListInfo("_Z_720_480", request,null);
    	System.out.println(JsonEntityTransform.Object2Json(houseListInfo));
    }
    
    
    @Test
    public void testgetLandHouseList(){
    	LandHouseRequest landRequest = new LandHouseRequest();
    	landRequest.setLandlordUid("1914e6a6-bcaa-4cc7-bbb0-0f96e42c2bb9");
    	landRequest.setPage(1);
    	landRequest.setLimit(10);
    	landRequest.setIsTop50Online(1);
    	landRequest.setFid("8a90a2d35af9bdad015af9dd32670002");
    	landRequest.setRentWay(1);
    	QueryResult houseListInfo = searchService.getLandHouseList("_Z_720_480", null,landRequest);
    	System.out.println(JsonEntityTransform.Object2Json(houseListInfo));
    }
    
    @Test
    public void getNewHouseLst(){
    	
    	QueryResult houseListInfo = searchService.getNewHouseLst("_Z_720_480",null);
    	System.out.println(JsonEntityTransform.Object2Json(houseListInfo));
    	
    }
    
}