package com.ziroom.minsu.services.order.test.service;

import com.asura.framework.base.util.DateUtil;
import com.ziroom.minsu.services.order.dto.ToPayRequest;
import com.ziroom.minsu.services.order.entity.PayVo;
import com.ziroom.minsu.services.order.service.HouseLockServiceImpl;
import com.ziroom.minsu.services.order.service.OrderCommonServiceImpl;
import com.ziroom.minsu.services.order.service.OrderMoneyServiceImpl;
import com.ziroom.minsu.services.order.test.base.BaseTest;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>房源锁的测试</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/4/2.
 * @version 1.0
 * @since 1.0
 */
public class HouseLockServiceImplTest extends BaseTest{




    @Resource(name = "order.houseLockServiceImpl")
    private HouseLockServiceImpl houseLockService;




    @Test
    public void getCreviceCount() throws Exception {
        Date startTime = DateUtil.parseDate("2016-12-25", "yyyy-MM-dd");

        Date endTime = DateUtil.parseDate("2016-12-26", "yyyy-MM-dd");

        Date tillTime = DateUtil.parseDate("2016-12-27", "yyyy-MM-dd");

//        Integer con = houseLockService.getCreviceCount("8a90a2d459203bb90159204d4b1400a4",1,  startTime, endTime,null);

        Integer con = houseLockService.getCreviceCount("8a90a2d455daafe90155dcb508130049",1,  startTime, endTime,tillTime);


        System.out.println("=====");System.out.println(con);System.out.println(con);
        System.out.println(con);
    }




}
