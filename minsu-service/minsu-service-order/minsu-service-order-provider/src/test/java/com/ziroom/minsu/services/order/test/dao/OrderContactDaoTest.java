package com.ziroom.minsu.services.order.test.dao;

import com.ziroom.minsu.entity.order.OrderContactEntity;
import com.ziroom.minsu.services.order.dao.OrderContactDao;
import com.ziroom.minsu.services.order.test.base.BaseTest;
import org.junit.Test;
import javax.annotation.Resource;

/**
 * <p>常用入住人</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/3/31.
 * @version 1.0
 * @since 1.0
 */
public class OrderContactDaoTest extends BaseTest {

        @Resource(name = "order.orderContactDao")
        private OrderContactDao orderContactDao;

        @Test
        public void TestinsertUsualContact() {
                OrderContactEntity contact = new OrderContactEntity();
                contact.setOrderSn("sn");
                contact.setContactFid("fid");
                orderContactDao.insertOrderContact(contact);
        }

}
