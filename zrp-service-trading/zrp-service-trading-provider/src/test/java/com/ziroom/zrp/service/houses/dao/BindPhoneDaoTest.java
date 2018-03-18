package com.ziroom.zrp.service.houses.dao;

import com.ziroom.zrp.service.houses.base.BaseTest;
import com.ziroom.zrp.service.trading.dao.BindPhoneDao;
import com.ziroom.zrp.trading.entity.BindPhoneEntity;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * <p>管家 400 分机号</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author cuiyh9
 * @version 1.0
 * @Date Created in 2017年11月01日 19:23
 * @since 1.0
 */
public class BindPhoneDaoTest extends BaseTest{

    @Resource(name="trading.bindPhoneDao")
    private BindPhoneDao bindPhoneDao;

    @Test
    public void testSelectByEmployeeId() {
        String employeeId = "9000088085420255004";
        BindPhoneEntity bindPhoneEntity = this.bindPhoneDao.selectByEmployeeId(employeeId);
        System.out.println("testSelectByEmployeeId:" + bindPhoneEntity.getFourooTel());
    }
}
