package com.ziroom.minsu.services.order.test.lock;

import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.DateUtil;
import com.ziroom.minsu.entity.order.HouseLockEntity;
import com.ziroom.minsu.services.common.utils.DateSplitUtil;
import com.ziroom.minsu.services.order.proxy.BillManageServiceProxy;
import com.ziroom.minsu.services.order.test.base.BaseTest;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.Date;

/**
 * <p>TODO</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on on 2017/3/1.
 * @version 1.0
 * @since 1.0
 */
public class LockTest extends BaseTest {


    private static   String  sqlPre = "INSERT INTO `t_base_house_lock` (`fid`, `house_fid`, `room_fid`, `bed_fid`, `order_sn`, `rent_way`, `lock_time`, `lock_type`, `pay_status`, `create_time`, `last_modify_date`, `is_del`) VALUES ";

    private static  String  tmpZhengzu =  "('%s', '%s', NULL, NULL, NULL, 0, '%s', 2, 0, now(), now(), 0),";

    private static  String  tmpFenzu =  "('%s', '%s', '%s', NULL, NULL, 1, '%s', 2, 0, now(), now(), 0),";




    public static void main(String[] args) throws Exception {
        doFenzu();
    }


    private static  void doFenzu() throws Exception{
        System.out.println(sqlPre);
        Date start = DateUtil.parseDate("2017-03-01 00:00:00", "yyyy-MM-dd HH:mm:ss");
        Date end = DateUtil.parseDate("2017-09-26 00:00:00", "yyyy-MM-dd HH:mm:ss");

        String  houseFid = "8a90997758f76ca901591b2fab503b2c";
        String  roomFid = "8a90997758f76ca901591b42eac93b65";

        while (start.before(end)){
            String  msg = sqlRoom(houseFid,roomFid,start);
            System.out.println(msg);
            start = DateSplitUtil.getTomorrow(start);
        }
    }



    private static  void doZhengzu() throws Exception{
        System.out.println(sqlPre);
        Date start = DateUtil.parseDate("2017-03-01 00:00:00", "yyyy-MM-dd HH:mm:ss");
        Date end = DateUtil.parseDate("2017-09-16 00:00:00", "yyyy-MM-dd HH:mm:ss");
        String  houseFid = "8a90997757a9ad080157bca0024c26ce";
        while (start.before(end)){
            String  msg = sqlHouse(houseFid,start);
            System.out.println(msg);
            start = DateSplitUtil.getTomorrow(start);
        }
    }

    private static String sqlRoom(String houseFid,String roomFid,Date lockTime){
        String fid = generateMd54lock(houseFid,roomFid,1,lockTime);
        String msg = String.format(tmpFenzu,fid,houseFid,roomFid,DateUtil.timestampFormat(lockTime));
        return msg;
    }


    private static String sqlHouse(String houseFid,Date lockTime){
        String fid = generateMd54lock(houseFid,"",0,lockTime);
        String msg = String.format(tmpZhengzu,fid,houseFid,DateUtil.timestampFormat(lockTime));
        return msg;
    }


    /**
     * 生成MD5
     * @param fid
     * @param roomFid
     * @param rentWay
     * @param lockTime
     * @return
     */
    public static String generateMd54lock(String fid,String roomFid,Integer rentWay,Date lockTime){
        String key  = fid;
        //房间fid
        if (!Check.NuNStr(roomFid)){
            key += roomFid;
        }

        //房源类型
        if (!Check.NuNObj(rentWay)){
            key += rentWay;
        }
        key += lockTime.getTime();
        String md5Str = DigestUtils.md5Hex(key);
        //返回有效值的md5
        return md5Str;
    }

    @Test
    public void TestLockSql() {

        System.out.println("11111");


    }
}
