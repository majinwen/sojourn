package com.zra.repair.logic;

import com.zra.repair.entity.ZryRepairOrder;
import com.zra.repair.entity.ZryRepairOrderLog;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.Assert.*;

/**
 * <p></p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author fengbo.yue
 * @version 1.0
 * @Date Created in 2017年09月28日 18:31
 * @since 1.0
 */
// TODO: 2017/9/28 启动不了,框架依赖关系有问题
//@RunWith(MockitoJUnitRunner.class)
//@ContextConfiguration({"/spring/app*.xml"})
//@ActiveProfiles("dev")
public class ZryRepairOrderLogicTest {

    @Mock
    private ZryRepairOrderLogic zryRepairOrderLogic;

    @Before
    public void setUp() throws Exception {
        assertNotNull("autowired ZryRepairOrderLogic fail !!",zryRepairOrderLogic);
    }

    @After
    public void tearDown() throws Exception {
        // noop
    }

//    @Test
    public void saveAndLog() throws Exception {

        ZryRepairOrder zryRepairOrder = new ZryRepairOrder();
        zryRepairOrder.setAreaCode("123");
        zryRepairOrder.setAreaName("望京");
        zryRepairOrder.setCategoryCode("0001");
        zryRepairOrder.setBusinessType(1);
        zryRepairOrder.setContractCode("BJ007990");
        zryRepairOrder.setCreateDate(Timestamp.valueOf(LocalDateTime.now()));
        zryRepairOrder.setGoodsCode("213");
        zryRepairOrder.setCreateFid("517");
        zryRepairOrder.setRoomNum("A001");

        ZryRepairOrderLog zryRepairOrderLog = new ZryRepairOrderLog();
        zryRepairOrderLog.setCreateFid("517");
        zryRepairOrderLog.setRepairOrder("ZR000000001");
        zryRepairOrderLog.setFromStatus(1);

        boolean flag = zryRepairOrderLogic.saveAndLog(zryRepairOrder,zryRepairOrderLog);

        assertTrue("zryRepairOrderLogic saveAndLog fail!", flag);

    }
}