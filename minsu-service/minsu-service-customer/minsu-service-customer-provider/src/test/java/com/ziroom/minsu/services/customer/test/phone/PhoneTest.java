package com.ziroom.minsu.services.customer.test.phone;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.asura.framework.base.util.MD5Util;
import com.ziroom.minsu.services.common.utils.CloseableHttpUtil;

/**
 * <p>TODO</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/5/5.
 * @version 1.0
 * @since 1.0
 */
public class PhoneTest {


    public static void main(String[] args) throws Exception{


        update();
    }

    private static void add(){
        String access_time = new Date().getTime()+"";
        String key = "duanzuxitong";
        System.out.println("access_time:"+access_time);
        String sys_code = MD5Util.MD5Encode(key + access_time, "UTF-8");
        System.out.println("sys_code:"+sys_code);
        //String url = "http://s.t.ziroom.com/crm-reserve/bindPhone/addNewKeeper";
        String url = "http://s.ziroom.com/crm/bindPhone/addNewKeeper";
        String keeperPhone = "13925251807";
        String keeperId = "13925251807001";
        String keeperName = "zhenggege";
        String extCode = "89999";
        Map<String,String> par = new HashMap<>();
        par.put("sys_code",sys_code);
        par.put("keeperPhone",keeperPhone);
        par.put("keeperId",keeperId);
        par.put("keeperName",keeperName);
        par.put("access_time",access_time);
        par.put("extCode",extCode);
        String urlStr =  url +"?" +"sys_code="+sys_code+
                              "&" +"keeperPhone="+keeperPhone+
                                "&" +"keeperId="+keeperId+
                                "&" +"keeperName="+keeperName+
                                "&" +"access_time="+access_time+
                                "&" +"extCode="+extCode;
        String rst = CloseableHttpUtil.sendFormPost(url, par);
        System.out.println(urlStr);
        System.out.println("rst:"+rst);
    }


    private static void update(){


//        String access_time = new Date().getTime()+"";
//        String key = "duanzuxitong";
//        System.out.println("access_time:"+access_time);
//        String sys_code = MD5Util.MD5Encode(key + access_time, "UTF-8");
//        System.out.println("sys_code:"+sys_code);
//        //String url = "http://s.t.ziroom.com/crm-reserve/bindPhone/addNewKeeper";
//        String url = "http://s.ziroom.com/crm/bindPhone/addNewKeeper";
//        String keeperPhone = "13925251807";
//        String keeperId = "13925251807001";
//        String keeperName = "zhenggege";
//        String extCode = "89999";
//        Map<String,String> par = new HashMap<>();
//        par.put("sys_code",sys_code);
//        par.put("keeperPhone",keeperPhone);
//        par.put("keeperId",keeperId);
//        par.put("keeperName",keeperName);
//        par.put("access_time",access_time);
//        par.put("extCode",extCode);
//        String urlStr =  url +"?" +"sys_code="+sys_code+
//                "&" +"keeperPhone="+keeperPhone+
//                "&" +"keeperId="+keeperId+
//                "&" +"keeperName="+keeperName+
//                "&" +"access_time="+access_time+
//                "&" +"extCode="+extCode;
//        String rst = CloseableHttpUtil.sendFormPost(url, par);
//        System.out.println(urlStr);
//        System.out.println("rst:"+rst);



        String access_time = new Date().getTime()+"";
        String key = "duanzuxitong";
        System.out.println("access_time:"+access_time);
        String sys_code = MD5Util.MD5Encode(key + access_time, "UTF-8");
        System.out.println("sys_code:"+sys_code);
        String url = "http://s.t.ziroom.com/crm-reserve/bindPhone/modifyKeeper";
        String keeperPhone = "18911123545";
        String keeperId = "13925251807001";
        String keeperName = "afi";
        String extCode = "89999";
        String urlStr =  url +"?" +"sys_code="+sys_code+
                "&" +"keeperPhone="+keeperPhone+
                "&" +"keeperId="+keeperId+
//                "&" +"keeperName="+keeperName+
                "&" +"access_time="+access_time+
                "&" +"crmflag=1"+
                "&" +"extCode="+extCode;
        String rst = CloseableHttpUtil.sendPost(urlStr);
        System.out.println(urlStr);
        System.out.println("rst:"+rst);
    }


    private static void del(){
        String access_time = new Date().getTime()+"";
        String key = "duanzuxitong";
        System.out.println("access_time:"+access_time);
        String sys_code = MD5Util.MD5Encode(key + access_time, "UTF-8");
        System.out.println("sys_code:"+sys_code);
        String url = "http://s.t.ziroom.com/crm-reserve/bindPhone/deleteKeeper";
        String keeperPhone = "18911123545";
        String keeperId = "8a9e9a8b53d6da8b0153d6da8bae0000";
        String keeperName = "afi";
        String extCode = "555556";
        String urlStr =  url +"?" +"sys_code="+sys_code+
//                "&" +"keeperPhone="+keeperPhone+
                "&" +"keeperId="+keeperId+
//                "&" +"keeperName="+keeperName+
                "&" +"access_time="+access_time+
                "&" +"sourceId="+""+
                "&" +"extCode="+extCode;
        String rst = CloseableHttpUtil.sendPost(urlStr);
        System.out.println(urlStr);
        System.out.println("rst:"+rst);
    }


    private static void get(){
        String access_time = new Date().getTime()+"";
        String key = "duanzuxitong";
        System.out.println("access_time:"+access_time);
        String sys_code = MD5Util.MD5Encode(key + access_time, "UTF-8");
        System.out.println("sys_code:"+sys_code);
        String url = "http://s.t.ziroom.com/crm-reserve/bindPhone/queryKeeperDetail";
        String keeperPhone = "18911123545";
        String keeperId = "8a9e9a8b53d6da8b0153d6da8bae0000";
        String keeperName = "afi";
        String extCode = "555556";
        String urlStr =  url +"?" +"sys_code="+sys_code+
//                "&" +"keeperPhone="+keeperPhone+
                "&" +"keeperId="+keeperId+
//                "&" +"keeperName="+keeperName+
                "&" +"access_time="+access_time+
                "&" +"extCode="+extCode;
        String rst = CloseableHttpUtil.sendPost(urlStr);
        System.out.println(urlStr);
        System.out.println("rst:"+rst);
    }
}
