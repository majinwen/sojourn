package com.ziroom.minsu.services.cms.test.proxy;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.minsu.entity.cms.ActCouponUserEntity;
import com.ziroom.minsu.entity.cms.ActivityInfoEntity;
import com.ziroom.minsu.services.cms.dto.ActCouponRequest;
import com.ziroom.minsu.services.cms.dto.ActivityInfoRequest;
import com.ziroom.minsu.services.cms.proxy.ActivityFullProxy;
import com.ziroom.minsu.services.cms.proxy.ActivityProxy;
import com.ziroom.minsu.services.cms.test.base.BaseTest;

import org.junit.Test;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivityFullProxyTest extends BaseTest{

	@Resource(name = "cms.activityFullProxy")
	private ActivityFullProxy activityFullProxy;
	
	@Test
	public void generateCouponByActSnExt() throws Exception{
		String str = activityFullProxy.generateCouponByActSnExt("147258",1);
		System.err.println(str);
        Thread.sleep(500000L);
	}


    @Test
    public void getActivityBySnTest() throws Exception{
        String str = activityFullProxy.generateCouponByActSn("147258");
        System.err.println(str);
        Thread.sleep(50000L);
    }


    @Test
    public void getCouponFullList(){

        ActCouponRequest request = new ActCouponRequest();

//        request.setActSn("ziroom");
//        request.setUid("e0a0f779-9117-6283-84e1-43e0be20ecf4");
//        request.setCouponSn("ziroomBRAHXE");
//        request.setCouponStatus(2);


        String v = activityFullProxy.getCouponFullList(JsonEntityTransform.Object2Json(request));

        System.err.println(JsonEntityTransform.Object2Json(v));
    }
    
    @Test
    public void getCouponActTest(){

       
        String resultJson = activityFullProxy.getActivityFullBySn("limit1");

        System.err.println(resultJson);
        //System.err.println(resultJson);
    }
    
    @Test
    public void testDisRule(){
    	/*String disRule = "500_100_80|700_300_15|1000_500_5|";
    	disRule = disRule.trim().substring(0, disRule.length()-1);
//    	System.err.println(disRule);
    	//去掉末尾|
    	String[] rules = disRule.split("\\|");
    	StringBuilder sb = new StringBuilder();
    	String[] rule = null;
    	for(int i = 0 ; i<rules.length ; i++){
    		rule = rules[i].split("_");
    		sb.append(rule[rule.length-1]).append(",");
    	}
    	System.err.println("==============>"+sb.toString());*/
    	List<Map<Integer,Integer>> dis = new ArrayList<>();
		Map<Integer,Integer> map = new HashMap<Integer,Integer>();
		map.put(500, 100);
//		map.put(300, 50);
		dis.add(map);
		dis.add(map);
		System.out.println("asdf");
    }
    
    @Test
    public void getActivityFullBySnTest(){
    	
    	DataTransferObject dto  = JsonEntityTransform.json2DataTransferObject(this.activityFullProxy.getActivityFullBySn("8a9e988d57ac71cc0157ac71cc090000"));
    	
    	System.out.println(dto);
    	
    }
	
}

