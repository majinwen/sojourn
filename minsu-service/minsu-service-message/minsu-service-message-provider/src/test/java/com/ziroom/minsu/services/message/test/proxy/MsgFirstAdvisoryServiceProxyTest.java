package com.ziroom.minsu.services.message.test.proxy;

import java.util.ArrayList;
import java.util.List;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.SOAResParseUtil;
import com.ziroom.minsu.entity.message.MsgFirstAdvisoryEntity;
import com.ziroom.minsu.services.message.dto.MsgAdvisoryFollowRequest;
import com.ziroom.minsu.services.message.proxy.MsgFirstAdvisoryServiceProxy;
import com.ziroom.minsu.services.message.test.base.BaseTest;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * <p></p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author wangwentao 2017/5/25
 * @version 1.0
 * @since 1.0
 */
public class MsgFirstAdvisoryServiceProxyTest extends BaseTest{

    @Resource(name = "message.msgFirstAdvisoryServiceProxy")
    private MsgFirstAdvisoryServiceProxy msgFirstAdvisoryServiceProxy;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void queryByMsgBaseFid() throws Exception {
        String resultJson = msgFirstAdvisoryServiceProxy.queryByMsgBaseFid("8a90a3675c87ffc3015c8800db780003");
        DataTransferObject dtoResult = JsonEntityTransform.json2DataTransferObject(resultJson);
        Assert.assertTrue(dtoResult.getCode() == DataTransferObject.SUCCESS);
        Assert.assertNotNull(dtoResult.getData());
        MsgFirstAdvisoryEntity data = SOAResParseUtil.getValueFromDataByKey(resultJson, "data", MsgFirstAdvisoryEntity.class);
        Assert.assertNotNull(data);
    }
    
    @Test
    public void testqueryAllNeedFollowList() throws Exception {
    	MsgAdvisoryFollowRequest paramRequest = new MsgAdvisoryFollowRequest();
    	/*List<String> houseFidList = new ArrayList<String>();
    	
    	houseFidList.add("8a90a2d35c15300c015c15300c4e0000");
    	paramRequest.setHouseFidList(houseFidList);
    	List<String> tList = new ArrayList<String>();
    	tList.add("8d95b3a3-7860-491b-8e32-19ef5f02b756");
    	paramRequest.setTenantUids(null);
    	List<String> lList = new ArrayList<String>();
    	lList.add("03f896d5-7404-9569-bcbe-f526173a5705");
    	paramRequest.setLandlordUids(lList);

    	paramRequest.setLandlordUid(null);
    	paramRequest.setTenantUid(null);*/

    	//paramRequest.setTenantName("qwe6105");
    	//paramRequest.setFollowStatus(10);
    	//paramRequest.setTenantTel("111");
    	//paramRequest.setCityCode("111");
    	paramRequest.setIsReplay(1);
    	paramRequest.setPage(1);
    	paramRequest.setLimit(5);
    	String queryAllNeedFollowList = msgFirstAdvisoryServiceProxy.queryAllNeedFollowList(JsonEntityTransform.Object2Json(paramRequest));
    	System.out.println(queryAllNeedFollowList);
    }
}
