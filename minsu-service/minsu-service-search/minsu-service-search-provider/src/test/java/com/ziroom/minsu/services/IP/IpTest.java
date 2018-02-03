package com.ziroom.minsu.services.IP;

import java.net.InetAddress;

/**
 * <p>ip地址获取</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/5/10.
 * @version 1.0
 * @since 1.0
 */
public class IpTest {

    public static void main(String[] args) throws Exception{
        String ip = InetAddress.getLocalHost().getHostAddress();
        System.out.println(ip);
    }
}
