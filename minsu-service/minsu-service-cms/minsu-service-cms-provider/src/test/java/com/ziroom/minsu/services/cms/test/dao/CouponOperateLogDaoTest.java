package com.ziroom.minsu.services.cms.test.dao;

import com.ziroom.minsu.entity.cms.CouponOperateLogEntity;
import com.ziroom.minsu.services.cms.dao.CouponOperateLogDao;
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
 * @Date Created in 2017年10月23日 11:47
 * @since 1.0
 */
public class CouponOperateLogDaoTest extends BaseTest{
    @Resource(name = "cms.couponOperateLogDao")
    private CouponOperateLogDao couponOperateLogDao;

    @Test
    public void insertCouponOperateLog() {
        CouponOperateLogEntity couponOperateLogEntity = new CouponOperateLogEntity();
        couponOperateLogEntity.setFid("test2");
        couponOperateLogEntity.setCouponSn("test2");
        couponOperateLogEntity.setActSn("test2");
        couponOperateLogEntity.setFromStatus(1);
        couponOperateLogEntity.setToStatus(2);
        couponOperateLogEntity.setRemark("test2");
        couponOperateLogDao.insertCouponOperateLog(couponOperateLogEntity);
    }
}
