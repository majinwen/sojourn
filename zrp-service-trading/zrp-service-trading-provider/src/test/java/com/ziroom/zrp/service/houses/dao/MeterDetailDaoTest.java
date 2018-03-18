package com.ziroom.zrp.service.houses.dao;

import com.ziroom.zrp.service.houses.base.BaseTest;
import com.ziroom.zrp.service.trading.dao.MeterDetailDao;
import com.ziroom.zrp.trading.entity.MeterDetailEntity;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>TODO</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author cuiyh9
 * @version 1.0
 * @Date Created in 2017年11月10日 10:51
 * @since 1.0
 */
public class MeterDetailDaoTest extends BaseTest {

    @Resource(name="trading.meterDetailDao")
    private MeterDetailDao meterDetailDao;

    @Test
    public void testFindByContractIds() {
        List<String> contractIdList = new ArrayList<>();
        contractIdList.add("2c908d174c9ca766014c9cad56990002");
        contractIdList.add("2c908d174cd62d3e014cd6e2c1f20008");
        contractIdList.add("2c908d174cd62d3e014cd6ecfa62000c");
        List<MeterDetailEntity> meterDetailEntityList = meterDetailDao.findByContractIds(contractIdList);
        System.out.println("testFindByContractIds:" + meterDetailEntityList.size());
    }
}
