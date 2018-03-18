package com.ziroom.zrp.service.houses.dao;

import com.ziroom.zrp.service.houses.base.BaseTest;
import com.ziroom.zrp.service.trading.dao.DeliveryNotifyLogDao;
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
 * @Date Created in 2017年11月21日 17:44
 * @since 1.0
 */
public class DeliveryNotifyLogDaoTest extends BaseTest{
    @Resource(name = "trading.deliveryNotifyLogDao")
    private DeliveryNotifyLogDao deliveryNotifyLogDao;

    @Test
    public void testfindByContractId(){
        deliveryNotifyLogDao.findByContractId("8a908e105fdc951d015fdda34de801ae");
    }

}
