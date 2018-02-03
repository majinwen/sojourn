package com.ziroom.minsu.services.cms.test.dao;

import com.ziroom.minsu.entity.cms.PointTiersEntity;
import com.ziroom.minsu.services.cms.dao.PointTiersDao;
import com.ziroom.minsu.services.cms.test.base.BaseTest;
import com.ziroom.minsu.services.common.utils.JsonTransform;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

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
 * @Date Created in 2017年12月05日 20:17
 * @since 1.0
 */
public class PointTiersDaoTest extends BaseTest {
    @Resource(name = "cms.pointTiersDao")
    private PointTiersDao pointTiersDao;

    @Test
    public void insertPointTiers() throws Exception {
        PointTiersEntity pointTiersEntity = new PointTiersEntity();
        pointTiersEntity.setFid("AAA");
        pointTiersEntity.setTiersType(1);
        pointTiersEntity.setSort(1);
        pointTiersEntity.setMaxNum(10);
        pointTiersEntity.setMinNum(0);
        pointTiersDao.insertPointTiers(pointTiersEntity);
    }

    @Test
    public void updatePointTiers() throws Exception {
        PointTiersEntity pointTiersEntity = new PointTiersEntity();
        pointTiersEntity.setFid("AAA");
        pointTiersEntity.setTiersType(2);
        pointTiersEntity.setSort(2);
        pointTiersEntity.setMaxNum(15);
        pointTiersEntity.setMinNum(5);
        pointTiersDao.updatePointTiersByFid(pointTiersEntity);
    }

    @Test
    public void getPointTiersByParamTest() {
        Map<String, Object> map = new HashMap<>();
        map.put("sumPerson",10);
        map.put("tiersType", 1);
        PointTiersEntity result=pointTiersDao.getPointTiersByParam(map);
        System.err.println(JsonTransform.Object2Json(result));
    }

}