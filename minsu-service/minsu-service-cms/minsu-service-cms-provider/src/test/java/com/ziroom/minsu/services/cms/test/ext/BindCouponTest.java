package com.ziroom.minsu.services.cms.test.ext;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.cms.ActCouponEntity;
import com.ziroom.minsu.services.cms.dao.ActCouponDao;
import com.ziroom.minsu.services.cms.dto.ActCouponRequest;
import com.ziroom.minsu.services.cms.test.base.BaseTest;

/**
 * <p>TODO</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on on 2017/2/13.
 * @version 1.0
 * @since 1.0
 */
public class BindCouponTest extends BaseTest {
	
	private static Logger logger = LoggerFactory.getLogger(BindCouponTest.class);


    @Resource(name = "cms.actCouponDao")
    private ActCouponDao actCouponDao;
    


    String  actSn ="HYDY01";

    String tmp = "('%s', '%s', '%s', '%s', now(),0),";


    String  update = "update  t_act_coupon set coupon_status = 6 where  act_sn = '%s'";

    @Test
    public void testTest(){
    	
    	
    	File file = new File("D:\\mobile.txt");
        Set<String> words = new HashSet<>();
        Set<String> wordsSam = new HashSet<>();
		BufferedReader reader = null;
		try {
			reader=new BufferedReader(new InputStreamReader(new FileInputStream(file),"utf-8"));
			String tempString = null;

			while ((tempString = reader.readLine()) != null) {
				if(!Check.NuNStr(tempString)){
					if(words.contains(tempString.trim())){
						wordsSam.add(tempString.trim());
					}
                    words.add(tempString.trim());
				}
			}
			reader.close();
		} catch (IOException e) {
            LogUtil.error(logger, "e={}", e);
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
                    LogUtil.error(logger, "e={}", e1);
				}
			}
		}


        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO `t_coupon_mobile_log` ( `fid`, `customer_mobile`, `coupon_sn`, `act_sn`, `create_time`, `source_type`)");
        sb.append(" VALUES ");

        ActCouponRequest request = new ActCouponRequest();
        request.setLimit(1454);//这个数一定要跟灌券的人数相等
        request.setActSn(actSn);
        request.setPage(1);
        PagingResult<ActCouponEntity> pagingResult = actCouponDao.getCouponListByActSn(request);
        //Long count = pagingResult.getTotal();
        Long count = (long) pagingResult.getRows().size();
        Map<Integer,ActCouponEntity>  actMap = new HashMap<>();
        List<ActCouponEntity> list = pagingResult.getRows();
        int step = 0;
        for (ActCouponEntity couponEntity : list) {
            step=step + 1;
            actMap.put(step,couponEntity);
        }
      //  String[] tels = telsStr.split("\\n");

        int size = words.size();
        if (count.intValue() != size){
            throw new BusinessException("数据不匹配");
        }

        System.out.println(sb.toString());




        String msg = String.format(update,actSn);
        System.out.println(msg);
        
        String fileName="D:\\coupon.txt";
        
        try
         {
             FileWriter writer = new FileWriter(fileName);
             writer.write(msg+";");
             writer.write("\n\r"+sb.toString());
             /*for (String word : words) {
                 writer.write("\n\t"+msg);
             }*/
             
             int stepNow = 0;
             
             for (String tel : words) {
                 stepNow=stepNow+1;
                 ActCouponEntity ac = actMap.get(stepNow);
                 String fid = UUIDGenerator.hexUUID();
//                 String group = "null";
//                 if (!Check.NuNStr(ac.getGroupSn())){
//                     group = "'"+ac.getGroupSn()+"'";
//                 }
                 String msg1 = String.format(tmp,fid,tel,ac.getCouponSn(),actSn);

                 String tmp = "('%s', '%s', '%s', %s, now(),'0'),";

                 System.out.println(msg1);
                 writer.write("\n\t"+msg1+"\n\r");
             }
             writer.write(";");
             writer.close();
         } catch (IOException e){
            e.printStackTrace();
        }

    }

}
