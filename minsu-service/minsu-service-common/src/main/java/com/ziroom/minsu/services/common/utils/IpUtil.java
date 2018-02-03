package com.ziroom.minsu.services.common.utils;

import com.asura.framework.base.exception.BusinessException;

import java.net.InetAddress;
import java.util.regex.Pattern;

/**
 * <p>获取ip地址</p>
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
public class IpUtil {

    private static final Pattern IP_PATTERN = Pattern.compile("\\b((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\b");
            //Pattern.compile("");


    /**
     * 获取当前机器的id。知道是谁更新没放图片
     * @return
     */
    public static String getIp(){
        try {
            String ip = InetAddress.getLocalHost().getHostAddress();
            return ip;
        }catch (Exception e){
            throw new BusinessException(e);
        }
    }


    /**
     * 校验当前的经纬度
     * @author afi
     * @param name
     * @return
     */
    public static  boolean checkIp(String name){
        return name != null && !"0.0.0.0".equals(name) && !"127.0.0.1".equals(name) && IP_PATTERN.matcher(name).matches();
    }


    /**
     * 将字符串型ip转成int型ip
     * @param strIP
     * @return
     */
    public static Long Ip2Long(String strIP){
    	if (strIP==null || strIP.trim().length()==0) {
			return null;
		}
		if (!checkIp(strIP)){
            return null;
        }
        long[] ip=new long[4];
        //先找到IP地址字符串中.的位置
        int position1=strIP.indexOf(".");
        int position2=strIP.indexOf(".",position1+1);
        int position3=strIP.indexOf(".",position2+1);
        //将每个.之间的字符串转换成整型
        ip[0]=Long.parseLong(strIP.substring(0,position1));
        ip[1]=Long.parseLong(strIP.substring(position1+1,position2));
        ip[2]=Long.parseLong(strIP.substring(position2+1,position3));
        ip[3]=Long.parseLong(strIP.substring(position3+1));
        return  (ip[0]<<24)+(ip[1]<<16)+(ip[2]<<8)+ip[3];

    }


    //将10进制整数形式转换成127.0.0.1形式的IP地址
    public static String intToIP(Long longIP){
    	if (longIP==null) {
			return null;
		}
        StringBuffer sb=new StringBuffer("");
        //直接右移24位
        sb.append(String.valueOf(longIP>>>24));
        sb.append(".");
        //将高8位置0，然后右移16位
        sb.append(String.valueOf((longIP&0x00FFFFFF)>>>16));
        sb.append(".");
        sb.append(String.valueOf((longIP&0x0000FFFF)>>>8));
        sb.append(".");
        sb.append(String.valueOf(longIP&0x000000FF));
        return sb.toString();
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        String ipStr = "198.16.1";
        Boolean ipLong = checkIp(ipStr);
        System.out.println(ipLong);

        System.out.println(Ip2Long(ipStr));


        System.out.println(intToIP(Ip2Long(ipStr)));

    }


}
