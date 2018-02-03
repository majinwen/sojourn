package com.ziroom.minsu.services.order.test.base;/*
 * <P></P>
 * <P>
 * <PRE>
 * <BR> 修改记录
 * <BR>------------------------------------------
 * <BR> 修改日期       修改人        修改内容
 * </PRE>
 * 
 * @Author lusp
 * @Date Create in 2017年07月 19日 14:51
 * @Version 1.0
 * @Since 1.0
 */

import com.alibaba.dubbo.container.spring.SpringContainer;
import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.minsu.services.common.entity.CalendarDataVo;
import com.ziroom.minsu.services.mq.CalendarLockMq;
import com.ziroom.minsu.services.order.dto.LockHouseRequest;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ContainTest extends SpringContainer {



    @Test
    public void testCalendarLockMq(){
        CalendarLockMq calendarLockMq = new CalendarLockMq();
        LockHouseRequest lockHouseRequest = new LockHouseRequest();
        CalendarDataVo calendarDataVo = new CalendarDataVo();
        calendarDataVo.setStartDate(new Date());
        calendarDataVo.setEndDate(new Date());
        List<CalendarDataVo> list = new ArrayList<>();
        list.add(calendarDataVo);
        lockHouseRequest.setCalendarDataVos(list);
        calendarLockMq.setData(lockHouseRequest);
        calendarLockMq.setMessage("hahah");
        calendarLockMq.setStatus(0);
        String result = JsonEntityTransform.Object2Json(calendarLockMq);
        System.out.println(result);
        String str = "{\"data\":{\"calendarDataVos\":[{\"endDate\":1489939200000,\"startDate\":1489766400000},{\"endDate\":1490371200000,\"startDate\":1490284800000},{\"endDate\":1490803200000,\"startDate\":1490716800000},{\"endDate\":1491235200000,\"startDate\":1491062400000},{\"endDate\":1491667200000,\"startDate\":1491494400000},{\"endDate\":1492185600000,\"startDate\":1492099200000},{\"endDate\":1492358400000,\"startDate\":1492185600000},{\"endDate\":1492531200000,\"startDate\":1492444800000},{\"endDate\":1493913600000,\"startDate\":1493740800000},{\"endDate\":1495209600000,\"startDate\":1494777600000},{\"endDate\":1495296000000,\"startDate\":1495209600000},{\"endDate\":1496505600000,\"startDate\":1496246400000},{\"endDate\":1497801600000,\"startDate\":1497715200000},{\"endDate\":1498665600000,\"startDate\":1498492800000},{\"endDate\":1499184000000,\"startDate\":1499011200000},{\"endDate\":1501344000000,\"startDate\":1501084800000},{\"endDate\":1501430400000,\"startDate\":1501344000000},{\"endDate\":1502294400000,\"startDate\":1502208000000},{\"endDate\":1502380800000,\"startDate\":1502294400000},{\"endDate\":1502467200000,\"startDate\":1502380800000},{\"endDate\":1503072000000,\"startDate\":1502467200000},{\"endDate\":1507219200000,\"startDate\":1506960000000},{\"endDate\":1489334400000,\"startDate\":1489248000000},{\"endDate\":1489507200000,\"startDate\":1489420800000},{\"endDate\":1489766400000,\"startDate\":1489593600000},{\"endDate\":1490198400000,\"startDate\":1489939200000},{\"endDate\":1490976000000,\"startDate\":1490803200000},{\"endDate\":1492099200000,\"startDate\":1491667200000},{\"endDate\":1492444800000,\"startDate\":1492358400000},{\"endDate\":1493568000000,\"startDate\":1492617600000},{\"endDate\":1494086400000,\"startDate\":1494000000000},{\"endDate\":1494345600000,\"startDate\":1494259200000},{\"endDate\":1494691200000,\"startDate\":1494518400000},{\"endDate\":1495900800000,\"startDate\":1495296000000},{\"endDate\":1496246400000,\"startDate\":1496160000000},{\"endDate\":1497110400000,\"startDate\":1496505600000},{\"endDate\":1497715200000,\"startDate\":1497196800000},{\"endDate\":1498147200000,\"startDate\":1497801600000},{\"endDate\":1498492800000,\"startDate\":1498233600000},{\"endDate\":1499011200000,\"startDate\":1498665600000},{\"endDate\":1499529600000,\"startDate\":1499184000000},{\"endDate\":1500134400000,\"startDate\":1499875200000},{\"endDate\":1500480000000,\"startDate\":1500220800000},{\"endDate\":1500739200000,\"startDate\":1500566400000},{\"endDate\":1500912000000,\"startDate\":1500825600000},{\"endDate\":1502208000000,\"startDate\":1501603200000},{\"endDate\":1503676800000,\"startDate\":1503072000000},{\"endDate\":1504368000000,\"startDate\":1503849600000},{\"endDate\":1505059200000,\"startDate\":1504972800000},{\"endDate\":1535212800000,\"startDate\":1511366400000}],\"houseFid\":\"8a9084df5b9da32e015b9da3a31d0008\",\"rentWay\":0,\"roomFid\":\"\"},\"message\":\"尝试发送\",\"status\":200}";
//        "{\"data\":{\"calendarDataVos\":[{\"endDate\":1489939200000,\"startDate\":1489766400000},{\"endDate\":1490371200000,\"startDate\":1490284800000},{\"endDate\":1490803200000,\"startDate\":1490716800000},{\"endDate\":1491235200000,\"startDate\":1491062400000},{\"endDate\":1491667200000,\"startDate\":1491494400000},{\"endDate\":1492185600000,\"startDate\":1492099200000},{\"endDate\":1492358400000,\"startDate\":1492185600000},{\"endDate\":1492531200000,\"startDate\":1492444800000},{\"endDate\":1493913600000,\"startDate\":1493740800000},{\"endDate\":1495209600000,\"startDate\":1494777600000},{\"endDate\":1495296000000,\"startDate\":1495209600000},{\"endDate\":1496505600000,\"startDate\":1496246400000},{\"endDate\":1497801600000,\"startDate\":1497715200000},{\"endDate\":1498665600000,\"startDate\":1498492800000},{\"endDate\":1499184000000,\"startDate\":1499011200000},{\"endDate\":1501344000000,\"startDate\":1501084800000},{\"endDate\":1501430400000,\"startDate\":1501344000000},{\"endDate\":1502294400000,\"startDate\":1502208000000},{\"endDate\":1502380800000,\"startDate\":1502294400000},{\"endDate\":1502467200000,\"startDate\":1502380800000},{\"endDate\":1503072000000,\"startDate\":1502467200000},{\"endDate\":1507219200000,\"startDate\":1506960000000},{\"endDate\":1489334400000,\"startDate\":1489248000000},{\"endDate\":1489507200000,\"startDate\":1489420800000},{\"endDate\":1489766400000,\"startDate\":1489593600000},{\"endDate\":1490198400000,\"startDate\":1489939200000},{\"endDate\":1490976000000,\"startDate\":1490803200000},{\"endDate\":1492099200000,\"startDate\":1491667200000},{\"endDate\":1492444800000,\"startDate\":1492358400000},{\"endDate\":1493568000000,\"startDate\":1492617600000},{\"endDate\":1494086400000,\"startDate\":1494000000000},{\"endDate\":1494345600000,\"startDate\":1494259200000},{\"endDate\":1494691200000,\"startDate\":1494518400000},{\"endDate\":1495900800000,\"startDate\":1495296000000},{\"endDate\":1496246400000,\"startDate\":1496160000000},{\"endDate\":1497110400000,\"startDate\":1496505600000},{\"endDate\":1497715200000,\"startDate\":1497196800000},{\"endDate\":1498147200000,\"startDate\":1497801600000},{\"endDate\":1498492800000,\"startDate\":1498233600000},{\"endDate\":1499011200000,\"startDate\":1498665600000},{\"endDate\":1499529600000,\"startDate\":1499184000000},{\"endDate\":1500134400000,\"startDate\":1499875200000},{\"endDate\":1500480000000,\"startDate\":1500220800000},{\"endDate\":1500739200000,\"startDate\":1500566400000},{\"endDate\":1500912000000,\"startDate\":1500825600000},{\"endDate\":1502208000000,\"startDate\":1501603200000},{\"endDate\":1503676800000,\"startDate\":1503072000000},{\"endDate\":1504368000000,\"startDate\":1503849600000},{\"endDate\":1505059200000,\"startDate\":1504972800000},{\"endDate\":1535212800000,\"startDate\":1511366400000}],\"houseFid\":\"8a9084df5b9da32e015b9da3a31d0008\",\"rentWay\":0,\"roomFid\":\"\"},\"message\":\"尝试发送\",\"status\":200}"
        CalendarLockMq calendarLockMq1 = JsonEntityTransform.json2Entity(str,CalendarLockMq.class);
        System.out.println(calendarLockMq1);
    }


    @Test
    public void dubboStart(){
        start();

        synchronized (ContainTest.class) {
            while (true) {
                try {
                    ContainTest.class.wait();
                } catch (Throwable e) {

                }
            }

        }
    }
}
