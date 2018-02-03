package com.ziroom.minsu.services.cms.test.dao;

import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.minsu.entity.cms.ActCouponUserEntity;
import com.ziroom.minsu.entity.cms.CouponMobileLogEntity;
import com.ziroom.minsu.services.cms.dao.ActCouponDao;
import com.ziroom.minsu.services.cms.dao.CouponMobileLogDao;
import com.ziroom.minsu.services.cms.dto.CheckCouponRequest;
import com.ziroom.minsu.services.cms.dto.MobileCouponRequest;
import com.ziroom.minsu.services.cms.test.base.BaseTest;
import org.junit.Test;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.List;

/**
 * <p>TODO</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/8/25.
 * @version 1.0
 * @since 1.0
 */
public class CouponMobileLogDaoTest extends BaseTest {


    @Resource(name = "cms.couponMobileLogDao")
    private CouponMobileLogDao couponMobileLogDao;


    @Test
    public void saveMobileCoupon() throws ParseException {
        CouponMobileLogEntity couponMobileLogEntity = new CouponMobileLogEntity();
        couponMobileLogEntity.setActSn("11");
        couponMobileLogEntity.setCustomerMobile("123");
        couponMobileLogEntity.setSourceType(1);
        couponMobileLogEntity.setCouponSn("1231");
        couponMobileLogDao.saveMobileCoupon(couponMobileLogEntity);
    }


    @Test
    public void countMobileAc() throws ParseException {
        MobileCouponRequest request = new MobileCouponRequest();
        request.setActSn("11");
        request.setMobile("123");
        Long aa = couponMobileLogDao.countMobileAc(request);
        System.out.println(aa);
    }

    @Test
    public void testlistActSnByGroup(){
        MobileCouponRequest request = new MobileCouponRequest();
        request.setGroupSn("456465456456465");
        request.setMobile("18911123545");
        List<String> strings = couponMobileLogDao.listActSnByGroup(request);
    }

}
