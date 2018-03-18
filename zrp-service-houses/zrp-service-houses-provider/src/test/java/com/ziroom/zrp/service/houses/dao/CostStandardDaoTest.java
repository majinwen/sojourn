package com.ziroom.zrp.service.houses.dao;

import com.ziroom.zrp.houses.entity.CostStandardEntity;
import com.ziroom.zrp.service.houses.base.BaseTest;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * <p>水电修改</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author zhangyl2
 * @Date Created in 2018年02月09日
 * @version 1.0
 * @since 1.0
 */
public class CostStandardDaoTest extends BaseTest {

    @Resource(name = "houses.costStandardDao")
    private CostStandardDao costStandardDao;

    @Test
    public void updateByProjectIdTest(){
        CostStandardEntity costStandardEntity = new CostStandardEntity();
        costStandardEntity.setProjectid("19");
        costStandardEntity.setFmetertype("1");
        costStandardEntity.setReadCycle(8);
        costStandardEntity.setFremark("test11");
        costStandardEntity.setFprice(9.9);
        costStandardDao.updateByProjectId(costStandardEntity);

    }

}
