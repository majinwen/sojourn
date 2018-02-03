package com.ziroom.minsu.services.cms.test.proxy;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.minsu.entity.cms.ActivityGroupEntity;
import com.ziroom.minsu.services.cms.dto.GroupRequest;
import com.ziroom.minsu.services.cms.proxy.ActivityGiftProxy;
import com.ziroom.minsu.services.cms.proxy.ActivityGroupProxy;
import com.ziroom.minsu.services.cms.test.base.BaseTest;

import org.junit.Test;

import javax.annotation.Resource;

/**
 * <p>TODO</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on on 16/10/10.
 * @version 1.0
 * @since 1.0
 */
public class ActivityGroupProxyTest extends BaseTest {


    @Resource(name = "cms.activityGroupProxy")
    private ActivityGroupProxy activityGroupProxy;


    @Test
    public void getGroupByPage(){
        GroupRequest request = new GroupRequest();

        String rst = this.activityGroupProxy.getGroupByPage(JsonEntityTransform.Object2Json(request));
        System.out.println(rst);
    }

    @Test
    public void getAllGroup(){
        DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(this.activityGroupProxy.getAllGroup());
        System.out.println(dto.toJsonString());
    }
    
    @Test
	public void insertActivityGroupEntityTest(){
		ActivityGroupEntity activityGroup = new ActivityGroupEntity();
		activityGroup.setCreateId(UUIDGenerator.hexUUID());
		activityGroup.setGroupName("1018");
		activityGroup.setGroupSn("456465456456465");
		activityGroup.setRemark("1018组的活动");
		String resultJson = activityGroupProxy.insertActivityGroupEntity(JsonEntityTransform.Object2Json(activityGroup));
		System.err.println(resultJson);
	}
    
    @Test
    public void updateActivityGroupEntityTest(){
    	ActivityGroupEntity activityGroup = new ActivityGroupEntity();
    	activityGroup.setFid("8a9e988d57a805850157a8058e420001");
    	activityGroup.setGroupName("5-1018");
    	String resultJson = activityGroupProxy.updateActivityGroupEntity(JsonEntityTransform.Object2Json(activityGroup));
    	System.err.println(resultJson);
    }

}
