package com.ziroom.minsu.services.cms.test.dao;

import com.asura.framework.base.paging.PagingResult;
import com.ziroom.minsu.entity.cms.GroupEntity;
import com.ziroom.minsu.entity.cms.GroupHouseRelEntity;
import com.ziroom.minsu.services.basedata.entity.entityenum.ServiceLineEnum;
import com.ziroom.minsu.services.cms.dao.GroupDao;
import com.ziroom.minsu.services.cms.dao.GroupHouseRelDao;
import com.ziroom.minsu.services.cms.dto.ActivityGroupRequest;
import com.ziroom.minsu.services.cms.test.base.BaseTest;
import com.ziroom.minsu.valenum.cms.GroupTypeEnum;
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
 * @Date Created in 2017年10月12日 17:34
 * @since 1.0
 */
public class GroupDaoTest extends BaseTest {

    @Resource(name = "cms.groupDao")
    private GroupDao groupDao;

    @Resource(name = "cms.groupHouseRelDao")
    private GroupHouseRelDao groupHouseRelDao;

    @Test
    public void testSave() {
        GroupEntity groupEntity = new GroupEntity();
        groupEntity.setServiceLine(ServiceLineEnum.ZRP.getOrcode());
        groupEntity.setGroupName("我的房源组2");
        groupEntity.setGroupType(GroupTypeEnum.HOUSE.getCode());
        groupDao.save(groupEntity);
    }

    @Test
    public void testlistGroup(){
        ActivityGroupRequest activityGroupRequest = new ActivityGroupRequest();
        activityGroupRequest.setProjectId("16");
        activityGroupRequest.setLayoutId("0000000048e846de0148e8492d4b0002");
        activityGroupRequest.setGroupName("1116第一波房源");
        PagingResult<GroupEntity> groupEntityPagingResult = groupDao.listGroup(activityGroupRequest);
    }

    @Test
    public void testselectCountByGroupName(){
        GroupEntity groupEntity = new GroupEntity();
        groupEntity.setGroupName("测试房源组插入");
        Long num = groupDao.selectCountByGroupName(groupEntity);
        System.err.print(num);
    }

    @Test
    public void testselectExistCount(){
        GroupHouseRelEntity groupHouseRelEntity = new GroupHouseRelEntity();
        groupHouseRelEntity.setProjectId("12445");
        groupHouseRelEntity.setLayoutId("89489");
        groupHouseRelEntity.setRoomId("898989");
        Long num = groupHouseRelDao.selectExistCount(groupHouseRelEntity);
        System.err.print(num);
    }

    @Test
    public void testfindGroupByFid() {
        GroupEntity groupByFid = groupDao.findGroupByFid("8a90939760df8f840160dfffd5c80984");

    }
}
