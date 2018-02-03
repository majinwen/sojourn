package com.ziroom.minsu.services.search.service;

import javax.annotation.Resource;

import org.junit.Test;

import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.minsu.services.search.dto.HouseInfoRequest;
import com.ziroom.minsu.services.search.proxy.ZrySearchProxy;

import base.BaseTest;

/**
 * 
 * <p>测试</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author zhangyl
 * @since 1.0
 * @version 1.0
 */
public class ZrySearchServiceTest extends BaseTest {

	@Resource(name = "search.zrySearchProxy")
    private ZrySearchProxy zrySearchProxy;


    @Test
    public void TestTest() {
    	HouseInfoRequest houseInfo = new HouseInfoRequest();
    	houseInfo.setQ("201708071032");
    	houseInfo.setStartTime("2017-07-07");
    	houseInfo.setEndTime("2018-09-07");
    	
    	String s = zrySearchProxy.getHouseListByConditionAndRecommend(JsonEntityTransform.Object2Json(houseInfo));
    	System.out.println(s);
    }

}