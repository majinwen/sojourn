package com.ziroom.minsu.services.basedata.test.api.inner;

import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.minsu.entity.sys.ResourceEntity;
import com.ziroom.minsu.services.basedata.proxy.ResourceServiceProxy;
import com.ziroom.minsu.services.basedata.test.base.BaseTest;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * <p>测试</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/3/25.
 * @version 1.0
 * @since 1.0
 */
public class ResourceServiceTest extends BaseTest {
    @Resource(name = "basedata.resourceServiceProxy")
    private ResourceServiceProxy resourceServiceProxy;




    @Test
    public void insertMenuOperProxyTest() {
        ResourceEntity resourceEntity = new ResourceEntity();
        resourceEntity.setFid(UUIDGenerator.hexUUID());
        resourceEntity.setIsLeaf(1);
        resourceEntity.setOrderCode(3);
        resourceEntity.setParentFid("8a9e9c9a537e0b5101537e0b51100000");
        resourceEntity.setResLevel(1);
        resourceEntity.setResName("角色管理");
        resourceEntity.setResType(2);
        resourceEntity.setResUrl("");
        resourceEntity.setIsDel(0);
        resourceEntity.setResState(1);
        resourceEntity.setClassName("left join");
        resourceServiceProxy.insertMenuResource(JsonEntityTransform.Object2Json(resourceEntity));
    }

    @Test
    public void findAllMenuClaster() {

        String resultJson = resourceServiceProxy.searchAllMenuChildResList();
        System.out.println(resultJson);
    }



    @Test
    public void findMenuTreeNodeVoProxy() {
        String resultJson = resourceServiceProxy.menuTreeVo();
        System.out.println(resultJson);
    }
}
