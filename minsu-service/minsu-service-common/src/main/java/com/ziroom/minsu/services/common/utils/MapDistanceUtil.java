package com.ziroom.minsu.services.common.utils;


/**
 * <p>距离的计算</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/4/7.
 * @version 1.0
 * @since 1.0
 */
public class MapDistanceUtil {



    private final static double PI = 3.14159265358979323; // 圆周率
    private final static double R = 6371.229; // 地球的半径 6378.137


    private static double EARTH_RADIUS = 6378.137;

    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }

    /**
     * 根据两个位置的经纬度，来计算两地的距离（单位为KM）
     * 参数为String类型
     * @param lat1Str 用户经度
     * @param lng1Str 用户纬度
     * @param lat2Str 商家经度
     * @param lng2Str 商家纬度
     * @return
     */
    public static String getDistance(String lat1Str, String lng1Str, String lat2Str, String lng2Str) {
        Double lat1 = Double.parseDouble(lat1Str);
        Double lng1 = Double.parseDouble(lng1Str);
        Double lat2 = Double.parseDouble(lat2Str);
        Double lng2 = Double.parseDouble(lng2Str);

        double x, y, distance;
        x = (lat2 - lat1) * PI * R
                * Math.cos(((lat1 + lat2) / 2) * PI / 180) / 180;
        y = (lng2 - lng1) * PI * R / 180;
        distance = Math.hypot(x, y);
        return ValueUtil.getStrValue(distance);


//        double radLat1 = rad(lat1);
//        double radLat2 = rad(lat2);
//        double difference = radLat1 - radLat2;
//        double mdifference = rad(lng1) - rad(lng2);
//        double distance = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(difference / 2), 2)
//                + Math.cos(radLat1) * Math.cos(radLat2)
//                * Math.pow(Math.sin(mdifference / 2), 2)));
//        distance = distance * EARTH_RADIUS;
//        distance = Math.round(distance * 10000) / 10000;
//        String distanceStr = distance+"";
//
//        return distanceStr;
    }

    public static void main(String[] args) {
        //济南国际会展中心经纬度：117.11811  36.68484
        //趵突泉：117.00999000000002  36.66123
        Long a = System.currentTimeMillis();
        //wangjing 40.0036480000,116.4734680000
        //望京西：40.0004420000,116.4577290000

        //116.422307,39.959325 地坛

        //116.415169,40.000706 安慧里

        //116.331686,39.937089 白石桥南

        //
        //  鸟巢 116.402131,39.999452
        // 天安门  116.403676,39.917775

        //北京-景点-南锣鼓巷
        // 房源经纬度：39.947083,116.467735  39.967377,116.499550
        // 南锣古巷经纬度：116.470665,39.989664

        System.out.println(getDistance("116.416534","39.95078","116.459228","39.942651"));
//        2.8624786087153207
//        System.out.println(getDistance("117.11811","36.68484","117.00999000000002","36.66123"));
        Long b = System.currentTimeMillis();
        System.out.println(b-a);
    }

}