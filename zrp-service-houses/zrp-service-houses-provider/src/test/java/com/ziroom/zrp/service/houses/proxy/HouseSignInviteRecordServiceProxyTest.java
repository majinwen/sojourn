package com.ziroom.zrp.service.houses.proxy;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.exception.SOAParseException;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.SOAResParseUtil;
import com.ziroom.zrp.houses.entity.HouseSignInviteRecordEntity;
import com.ziroom.zrp.service.houses.base.BaseTest;
import com.ziroom.zrp.service.houses.dto.QueryInvitereCordParamDto;
import com.ziroom.zrp.service.houses.dto.SignInviteDto;


import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;


/**
 * <p>签约邀请测试类</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author cuiyh9
 * @version 1.0
 * @Date Created in 2017年09月20日 19:14
 * @since 1.0
 */
public class HouseSignInviteRecordServiceProxyTest extends BaseTest {

    @Resource(name="houses.houseSignInviteRecordServiceProxy")
    private HouseSignInviteRecordServiceProxy houseSignInviteRecordServiceProxy;


    @Test
    public void testCountByRoomIds() {
        String roomIds = "1,2,3,4,5";
        String result = houseSignInviteRecordServiceProxy.countByRoomIds(roomIds);
        DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(result);
        Map<String, Integer> map = (Map<String, Integer>)resultDto.getData().get("signInvite");
        assertTrue(map.size() == 5);
    }
    
    @Test
    public void testFindPageListByRoomId() throws SOAParseException {
    	QueryInvitereCordParamDto paramDto = new QueryInvitereCordParamDto();
    	paramDto.setLimit(10);
    	paramDto.setPage(1);
    	paramDto.setRoomId("1001247");
    	String result = this.houseSignInviteRecordServiceProxy.findPageListByRoomId(paramDto.toJsonStr());
        System.out.println("result:" + result);
        PagingResult<HouseSignInviteRecordEntity> pagingResult = SOAResParseUtil.getValueFromDataByKey(result, "data", PagingResult.class);
    	System.out.println("total:" + pagingResult.getTotal());
    	List<HouseSignInviteRecordEntity> list = pagingResult.getRows();
        System.out.println("size:" + list.size());
    	
    }

    @Test
    public void testSaveSignInviteRecord() {
        SignInviteDto signInviteDto  = new SignInviteDto();
        signInviteDto.setProjectId("16");
        signInviteDto.setRoomIds("1001247");
        signInviteDto.setProjectName("北京酒仙桥将府自如寓");
        signInviteDto.setConStartDate(new Date());
        signInviteDto.setRentPeriod(1);
        signInviteDto.setHandZo("刘倩倩");
        signInviteDto.setHandZoCode("20201384");
        signInviteDto.setEmployeeId("9000082288820201384");
        signInviteDto.setCustomerUid("8fdc471a-536c-6ab6-a334-1f8f16fc690f");
        signInviteDto.setPhone("13521263178");
        signInviteDto.setConType("1");
        Map<String, String> roomContractMap = new Hashtable<>();
        roomContractMap.put("1001247", "111");
        signInviteDto.setRoomContractMap(roomContractMap);

        String result = houseSignInviteRecordServiceProxy.saveSignInviteRecord(signInviteDto.toJsonStr());
        System.out.println("result:" +  result);
    }

}
