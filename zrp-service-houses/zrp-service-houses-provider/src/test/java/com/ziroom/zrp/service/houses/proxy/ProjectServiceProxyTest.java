package com.ziroom.zrp.service.houses.proxy;

import com.alibaba.fastjson.JSONObject;
import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.zrp.service.houses.base.BaseTest;

import com.ziroom.zrp.service.houses.dto.AddHouseGroupDto;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * <p>TODO</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd
 * @version 1.0
 * @Date Created in 2017年09月07日 11:39
 * @since 1.0
 */
public class ProjectServiceProxyTest extends BaseTest{

    @Resource(name = "houses.projectServiceProxy")
    private ProjectServiceProxy projectServiceProxy;

    @Test
    public void testFindProjectByCode(){
        String projectByCode = projectServiceProxy.findProjectByCode("0100");
        System.err.println(projectByCode);
    }
    
    @Test
    public void testFindProjectDetail(){
        String projectDetail = projectServiceProxy.findProjectById("2c908d174f6e5d0b014f8c0185c106a8");
        System.err.println(projectDetail);
    }

    @Test
    public void testfindCostStandardByProjectId(){
        String costStandardByProjectId = projectServiceProxy.findCostStandardByProjectId("20");
        System.err.println(costStandardByProjectId);
    }


    @Test
    public void testQueryAllPro(){

        AddHouseGroupDto addHouseGroupDto = new AddHouseGroupDto();
        String re = projectServiceProxy.queryAllPro(JsonEntityTransform.Object2Json(addHouseGroupDto));
        System.out.println(re);
    }
    
    @Test
    public void testUserProjectList(){
    	String resultJson=projectServiceProxy.userProjectList("20266142");
    	System.err.println(resultJson);
    }
}
