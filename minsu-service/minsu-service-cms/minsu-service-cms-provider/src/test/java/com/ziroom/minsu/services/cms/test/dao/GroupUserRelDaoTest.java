package com.ziroom.minsu.services.cms.test.dao;

import com.asura.framework.base.paging.PagingResult;
import com.ziroom.minsu.entity.cms.GroupHouseRelEntity;
import com.ziroom.minsu.entity.cms.GroupUserRelEntity;
import com.ziroom.minsu.services.cms.dao.GroupHouseRelDao;
import com.ziroom.minsu.services.cms.dao.GroupUserRelDao;
import com.ziroom.minsu.services.cms.dto.GroupRequest;
import com.ziroom.minsu.services.cms.dto.ZrpActRequest;
import com.ziroom.minsu.services.cms.test.base.BaseTest;
import org.junit.Assert;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>用户组</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd
 * @version 1.0
 * @Date Created in 2017年10月17日 09:29
 * @since 1.0
 */
public class GroupUserRelDaoTest extends BaseTest {

    @Resource(name = "cms.groupUserRelDao")
    private GroupUserRelDao groupUserRelDao;

    @Resource(name = "cms.groupHouseRelDao")
    private GroupHouseRelDao groupHouseRelDao;

    @Test
    public void testSave() {
        GroupUserRelEntity groupUserRelEntity = new GroupUserRelEntity();
        groupUserRelEntity.setGroupFid("8a9e989a5f0ff4f8015f0ff4f8b70000");
        groupUserRelEntity.setUid("sdfsdfsdfsdfsdfsdf");
        groupUserRelEntity.setCustomerName("sdfsdf");
        groupUserRelEntity.setCustomerPhone("18811366591");
        groupUserRelDao.save(groupUserRelEntity);
    }

    @Test
    public void testlistUserRelFilter() {
        List<String> fids = new ArrayList<>();
        fids.add("8a9e989a5f0ff4f8015f0ff4f8b70000");

        groupUserRelDao.listUserRelFilter(fids, "0f163457-ad6a-09ce-d5de-de452a251cf6");
    }


    @Test
    public void testSaveHouse() {
        GroupHouseRelEntity groupHouseRelEntity = new GroupHouseRelEntity();
        groupHouseRelEntity.setGroupFid("8a9e989a5f0ff620015f0ff620150000");
//        groupHouseRelEntity.setHouseType(1);
        groupHouseRelEntity.setProjectId("222");
        groupHouseRelDao.save(groupHouseRelEntity);

    }

    @Test
    public void testlistHouseRelFilter() {
        List<String> fids = new ArrayList<>();
        fids.add("8a9e989a5f0ff620015f0ff620150000");
        ZrpActRequest zrpActRequest = new ZrpActRequest();
        zrpActRequest.setProjectId("222");
        groupHouseRelDao.listHouseRelFilter(fids, zrpActRequest);
    }

    @Test
    public void testlistHouseRelByPage(){
        GroupRequest groupRequest = new GroupRequest();
        PagingResult<GroupHouseRelEntity> pagingResult = groupHouseRelDao.listHouseRelByFidForPage(groupRequest);
        System.err.print(pagingResult);
    }

    @Test
    public void testdeleteUserRelBatch() {
        List<String> list = new ArrayList<>();
        list.add("8a9e989a5f27fb6b015f27fb6bce0000");
        list.add("8a9e989a5f29ed3e015f29ed3e7b0000");
        groupUserRelDao.deleteUserRelBatch(list);
    }

    @Test
    public void testcountUserByGroupId() {
        System.out.println(groupUserRelDao.countUserByGroupId("sdfsdfsd"));
    }

}
