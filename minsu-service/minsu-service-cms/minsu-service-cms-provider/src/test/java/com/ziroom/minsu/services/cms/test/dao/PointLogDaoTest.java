package com.ziroom.minsu.services.cms.test.dao;

import com.ziroom.minsu.entity.cms.PointLogEntity;
import com.ziroom.minsu.services.cms.dao.PointLogDao;
import com.ziroom.minsu.services.cms.test.base.BaseTest;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * <p></p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author yanb
 * @version 1.0
 * @Date Created in 2017年12月05日 18:28
 * @since 1.0
 */
public class PointLogDaoTest extends BaseTest {
    @Resource(name = "cms.pointLogDao")
    private PointLogDao pointLogDao;

    @Test
    public void insertPointLog() throws Exception {
        PointLogEntity pointLogEntity=new PointLogEntity();
        pointLogEntity.setFid("AAA");
        pointLogEntity.setUid("AAA");
        pointLogEntity.setFromPoints(10);
        pointLogEntity.setToPoints(15);
        pointLogEntity.setChangePoints(5);
        pointLogEntity.setChangeType(1);
        pointLogEntity.setRemark("单元测试");
        pointLogDao.insertPointLog(pointLogEntity);
    }

    @Test
    public void updatePointLogByFidTest() {
        PointLogEntity pointLogEntity=new PointLogEntity();
        pointLogEntity.setFid("AAA");
        pointLogEntity.setUid("BBB");
        pointLogEntity.setFromPoints(15);
        pointLogEntity.setToPoints(18);
        pointLogEntity.setChangePoints(3);
        pointLogEntity.setChangeType(2);
        pointLogEntity.setRemark("单元测试");
        pointLogDao.updatePointLogByFid(pointLogEntity);
    }

}