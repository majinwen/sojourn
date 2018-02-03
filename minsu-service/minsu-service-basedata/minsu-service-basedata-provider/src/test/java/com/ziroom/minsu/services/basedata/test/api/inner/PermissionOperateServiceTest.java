package com.ziroom.minsu.services.basedata.test.api.inner;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.minsu.entity.sys.RoleEntity;
import com.ziroom.minsu.services.basedata.dto.CurrentuserRequest;
import com.ziroom.minsu.services.basedata.dto.RoleRequest;
import com.ziroom.minsu.services.basedata.entity.CurrentuserVo;
import com.ziroom.minsu.services.basedata.proxy.PermissionOperateServiceProxy;
import com.ziroom.minsu.services.basedata.test.base.BaseTest;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * <p>权限操作测试</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/3/24.
 * @version 1.0
 * @since 1.0
 */
public class PermissionOperateServiceTest extends BaseTest {

    @Resource(name="basedata.permissionOperateServiceProxy")
    private PermissionOperateServiceProxy permissionOperateServiceProxy;



    @Test
    public void findRolePageProxyTest() {
        RoleRequest roleRequest = new RoleRequest();
        roleRequest.setLimit(5);
        roleRequest.setPage(1);
        String resultJson = permissionOperateServiceProxy.searchRoles(JsonEntityTransform.Object2Json(roleRequest));
        System.err.println(resultJson);
    }

    @Test
    public void searchRoleByFidProxyTest() {
        String roleFid = "33b34bc9-8594-4107-815f-2e4546ae8ff7";
        String resultJson = permissionOperateServiceProxy.searchRoleByFid(roleFid);
        System.err.println(resultJson);
    }

    @Test
    public void editRoleProxyTest() {
        RoleEntity role = new RoleEntity();
        role.setFid("33b34bc9-8594-4107-815f-2e4546ae8ff7");
        String roleJson = JsonEntityTransform.Object2Json(role);
        String resultJson = permissionOperateServiceProxy.editRoleStatus(roleJson);
        System.err.println(resultJson);
    }



    @Test
    public void insertCurrentuserTestProxy(){
        CurrentuserVo currentuserVo=new CurrentuserVo();
        currentuserVo.setFid(UUIDGenerator.hexUUID());
        currentuserVo.setUserAccount("busj12355");
        currentuserVo.setUserPassword("123456");
        currentuserVo.setEmployeeFid("ddddd");
        permissionOperateServiceProxy.insertCurrentuser(JsonEntityTransform.Object2Json(currentuserVo));
    }


    @Test
    public void listUser(){
        CurrentuserRequest currentuserRequest=new CurrentuserRequest();
        currentuserRequest.setUserName("顶顶顶顶");
        String resultJson=permissionOperateServiceProxy.searchCurrentuserList(JsonEntityTransform.Object2Json(currentuserRequest));
        System.out.println(resultJson);
    }




    @Test
    public void addRoleResourcesTest() {
        String rolename = "业务";
        String resFids = "8a9e9c9a537e0b5101537e0b51100000,8a9e9c9a537e0d8001537e0d80ef0000";
        DataTransferObject dto = new DataTransferObject();
        dto.putValue("roleName", rolename);
        dto.putValue("resFids", resFids);
        permissionOperateServiceProxy.addRoleResources(JsonEntityTransform.entity2Json(dto));
    }


    @Test
    public void searchRoleResourcesTest() {
        String roleFid = "87b9ae06-c8b9-4cd8-bcae-51a23b3dbf6c";
        String resultJson = permissionOperateServiceProxy.searchRoleResources(roleFid);
        resultJson = resultJson.replaceAll("nodes", "children");
        System.err.println(resultJson);
    }

    @Test
    public void updateRoleResourcesTest(){
        DataTransferObject dto = new DataTransferObject();
        dto.putValue("roleFid", "aa");
        dto.putValue("resFids", "1111111,222222,b33333b,444444");
        String resultJson = permissionOperateServiceProxy.updateRoleResources(JsonEntityTransform.entity2Json(dto));
        System.err.println(resultJson);

    }


}
