package com.ziroom.minsu.services.house.test.proxy;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import com.asura.framework.base.exception.SOAParseException;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.SOAResParseUtil;
import com.ziroom.minsu.entity.house.AbHouseRelateEntity;
import com.ziroom.minsu.services.house.airbnb.dto.AbHouseDto;
import com.ziroom.minsu.services.house.airbnb.vo.AbHouseRelateVo;
import com.ziroom.minsu.services.house.proxy.AbHouseServiceProxy;
import com.ziroom.minsu.services.house.test.base.BaseTest;

/**
 * Created by homelink on 2017/4/18.
 */
public class AbHouseServiceProxyTest extends BaseTest{

    @Resource(name="house.abHouseServiceProxy")
    private AbHouseServiceProxy abHouseServiceProxy;

    @Test
    public void testfindHouseRelateByFid() throws SOAParseException{
        AbHouseDto abHouseDto = new AbHouseDto();
        abHouseDto.setHouseFid("8a9e989e5d15d0fc015d15d0fcf10001");
        abHouseDto.setRentWay(0);
        String houseRelateByFid = abHouseServiceProxy.findHouseRelateByFid(JsonEntityTransform.Object2Json(abHouseDto));
        AbHouseRelateEntity entity = SOAResParseUtil.getValueFromDataByKey(houseRelateByFid, "obj", AbHouseRelateEntity.class);
        
        
        System.err.println(houseRelateByFid);
    }
    
    @Test
    public void listHouseRelateVoByPage(){
    	
    	AbHouseDto abHouseDto = new AbHouseDto();
    	abHouseDto.setLandlordName("志宏");
    	
    	List<String> landlordUidList = new ArrayList<>();
    	landlordUidList.add("a06f82a2-423a-e4e3-4ea8-e98317540190");
    	abHouseDto.setLandlordUidList(landlordUidList);
    	
        String houseRelateByFid = abHouseServiceProxy.listHouseRelateVoByPage(JsonEntityTransform.Object2Json(abHouseDto));
        System.err.println(houseRelateByFid);
        
    	
    }
    
    @Test
    public void testupdateHouseRelateByFid(){
    	AbHouseRelateVo abHouseRelateVo = new AbHouseRelateVo();
    	abHouseRelateVo.setFid("8a9084df5ba933e3015ba938a55d0016");
    	abHouseRelateVo.setIsDel(1);
    	String result = abHouseServiceProxy.updateHouseRelateByFid(JsonEntityTransform.Object2Json(abHouseRelateVo));
    	System.err.println(result);
    }
    
}
