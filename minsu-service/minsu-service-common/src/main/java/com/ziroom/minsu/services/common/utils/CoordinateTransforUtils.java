/**
 * @FileName: CoordinateTransforUtils.java
 * @Package com.ziroom.minsu.services.common.utils
 * 
 * @author yd
 * @created 2017年2月14日 下午3:59:51
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.util.Check;
import com.asura.framework.utils.LogUtil;


/**
 * WGS84  GCJ02  BD09 坐标系转换
 * <p>
 * WGS84：地球坐标系，国际上通用的坐标系。设备一般包含GPS芯片或者北斗芯片获取的经纬度为WGS84地理坐标系,最基础的坐标，谷歌地图在非中国地区使用的坐标系
 * GCJ02：火星坐标系，是由中国国家测绘局制订的地理信息系统的坐标系统。并要求在中国使用的地图产品使用的都必须是加密后的坐标，而这套WGS84加密后的坐标就是gcj02。
 *  BD09：百度坐标系，百度在GCJ02的基础上进行了二次加密，官方解释是为了进一步保护用户隐私
 * </p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author yd
 * @since 1.0
 * @version 1.0
 */
public class CoordinateTransforUtils {
	
	private static Logger logger = LoggerFactory.getLogger(CoordinateTransforUtils.class);

	public static double x_pi = 3.14159265358979324*3000.0/180.0;
	// π
	public static double pi = 3.1415926535897932384626;
	// 长半轴
	public static double a = 6378245.0;
	// 扁率
	public static double ee = 0.00669342162296594323;
	


	/**
	 * 
	 	longitude        latitude
		116.482597       39.915699
		114.075150       22.543268
		113.931709       22.491167
		121.461365       31.229605
	 * TODO
	 *
	 * @author yd
	 * @created 2017年2月21日 上午10:02:37
	 *
	 * @param args
	 */
	public static void main(String[] args) {

		/*	double[] gcj = bd_decrypt(39.876911 ,116.349411);
		Gps gps = gcj_To_Gps84(gcj[0], gcj[1]);*/

		//百度转google
		Gps gps2 = bd09_To_Gps84(39.875572,116.474327);
		System.out.println("baidu转google:"+JsonTransform.Object2Json(gps2));
		
		//google转baidu
		Gps gps = wgs84_To_bd09(35.7095081,139.59092220000002);
		//Gps gps1 = google_bd_encrypt(39.8706742,116.46724280000001);
		System.out.println("google转baidu:"+JsonTransform.Object2Json(gps));
		
	}

	
	/**
	 * gg_lat 纬度
	 * gg_lon 经度
	 * GCJ-02转换BD-09
	 * Google地图经纬度转百度地图经纬度
	 * */
	public static Gps google_bd_encrypt(double gg_lat, double gg_lon){
		Gps point=new Gps();
		double x = gg_lon, y = gg_lat;
		double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * x_pi);
		double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * x_pi); 
		double bd_lon = z * Math.cos(theta) + 0.0065;
		double bd_lat = z * Math.sin(theta) + 0.006;
		point.setWgLat(bd_lat);
		point.setWgLon(bd_lon);
		return point;
	}
	/**
	 * 
	 * bd09 转 WGS84
	 * 即：  google经纬度转 百度经纬度(百度经纬度 一般都是国内的坐标)
	 *
	 * @author yd
	 * @created 2017年2月14日 下午5:00:20
	 *
	 * @param gg_lat
	 * @param gg_lon
	 * @return
	 */
	public static Gps wgs84_To_bd09(double gg_lat, double gg_lon){
		
		Gps gps = null;
		
		try {
			gps = gps84_To_Gcj02(gg_lat, gg_lon);
			if(Check.NuNObj(gps)) {
				throw new BusinessException("google经纬度 转 百度经纬度 异常");
			}
			gps = gcj02_To_Bd09(gps.getWgLat(), gps.getWgLon());
		} catch (Exception e) {
			LogUtil.error(logger, "google经纬度 转 百度经纬度 异常e={},gg_lat={},gg_lon={}", e,gg_lat,gg_lon);
		}
		 
		return gps;
	}
	
	/**
	 * 
	 * bd09 转 Gps84
	 * 即： 百度经纬度 转 google经纬度(百度经纬度 一般都是国内的坐标)
	 *
	 * @author yd
	 * @created 2017年2月14日 下午5:00:20
	 *
	 * @param gg_lat
	 * @param gg_lon
	 * @return
	 */
	public static Gps bd09_To_Gps84(double gg_lat, double gg_lon){
		
		Gps gps = null;
		try {
			 gps = bd09_To_Gcj02(gg_lat,gg_lon);
				if(Check.NuNObj(gps)) {
					throw new BusinessException("百度经纬度 转 google经纬度异常");
				}
			gps = gcj_To_Gps84(gps.getWgLat(), gps.getWgLon());
		} catch (Exception e) {
			LogUtil.error(logger, "百度经纬度 转 google经纬度异常e={},gg_lat={},gg_lon", e,gg_lat,gg_lon);
		}
		
		return gps;
	}
	/**
	 * 火星坐标系 (GCJ-02) 与百度坐标系 (BD-09) 的转换算法 将 GCJ-02 坐标转换成 BD-09 坐标
	 *
	 * @author yd
	 * @created 2017年2月14日 下午4:03:39
	 *
	 * @param gg_lat
	 * @param gg_lon
	 * @return
	 */
	public static Gps gcj02_To_Bd09(double gg_lat, double gg_lon) {
		double x = gg_lon, y = gg_lat;
		double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * pi);
		double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * pi);
		double bd_lon = z * Math.cos(theta) + 0.0065;
		double bd_lat = z * Math.sin(theta) + 0.006;
		return new Gps(bd_lat, bd_lon);
	}
	
	/**
	 * 
	 *  火星坐标系 (GCJ-02) 与百度坐标系 (BD-09) 的转换算法 将 BD-09 坐标转换成GCJ-02 坐标
	 *
	 * @author yd
	 * @created 2017年2月14日 下午4:07:57
	 *
	 * @param bd_lat
	 * @param bd_lon
	 * @return
	 */
	public static Gps bd09_To_Gcj02(double bd_lat, double bd_lon) {
		double x = bd_lon - 0.0065, y = bd_lat - 0.006;
		double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * x_pi);
		double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * x_pi);
		double gg_lon = z * Math.cos(theta);
		double gg_lat = z * Math.sin(theta);
		return new Gps(gg_lat, gg_lon);
	}

	
	/**
	 * 
	 * 地球坐标系 (WGS84) 与火星坐标系 (GCJ-02) 的转换算法 将 WGS84 坐标转换成 GCJ-02 坐标
	 *
	 * @author yd
	 * @created 2017年2月14日 下午3:53:26
	 *
	 * @param lat
	 * @param lon
	 * @return
	 */
	public static Gps gps84_To_Gcj02(double lat, double lon) {
		if (!outOfChina(lat, lon)) {
			return  new Gps(lat, lon);
		}
		double dLat = transformLat(lon - 105.0, lat - 35.0);
		double dLon = transformLon(lon - 105.0, lat - 35.0);
		double radLat = lat / 180.0 * pi;
		double magic = Math.sin(radLat);
		magic = 1 - ee * magic * magic;
		double sqrtMagic = Math.sqrt(magic);
		dLat = (dLat * 180.0) / ((a * (1 - ee)) / (magic * sqrtMagic) * pi);
		dLon = (dLon * 180.0) / (a / sqrtMagic * Math.cos(radLat) * pi);
		double mgLat = lat + dLat;
		double mgLon = lon + dLon;
		return new Gps(mgLat, mgLon);
	}
	/**
	 * 
	 * 地球坐标系 (WGS84) 与火星坐标系 (GCJ-02) 的转换算法 将 GCJ-02 坐标转换成 WGS84 坐标
	 *
	 * @author yd
	 * @created 2017年2月14日 下午3:54:41
	 *
	 * @param lat
	 * @param lon
	 * @return
	 */
	public static Gps gcj_To_Gps84(double lat, double lon) {
		Gps gps = transform(lat, lon);
		double lontitude = lon * 2 - gps.getWgLon();
		double latitude = lat * 2 - gps.getWgLat();
		return new Gps(latitude, lontitude);
	}

	/**
	 * 
	 * 坐标转换
	 *
	 * @author yd
	 * @created 2017年2月14日 下午3:54:51
	 *
	 * @param lat
	 * @param lon
	 * @return
	 */
	private static Gps transform(double lat, double lon) {
		if (!outOfChina(lat, lon)) return new Gps(lat, lon);
		double dLat = transformLat(lon - 105.0, lat - 35.0);
		double dLon = transformLon(lon - 105.0, lat - 35.0);
		double radLat = lat / 180.0 * pi;
		double magic = Math.sin(radLat);
		magic = 1 - ee * magic * magic;
		double sqrtMagic = Math.sqrt(magic);
		dLat = (dLat * 180.0) / ((a * (1 - ee)) / (magic * sqrtMagic) * pi);
		dLon = (dLon * 180.0) / (a / sqrtMagic * Math.cos(radLat) * pi);
		double mgLat = lat + dLat;
		double mgLon = lon + dLon;
		return new Gps(mgLat, mgLon);
	}
	/**
	 * 
	 * 根据经纬度 判断是国内还是国外
	 *
	 * @author yd
	 * @created 2017年2月14日 下午3:55:08
	 *
	 * @param lat
	 * @param lon
	 * @return
	 */
	public static boolean outOfChina(double lat, double lon) {
		if (lon < 72.004 || lon > 137.8347) return true;
		if (lat < 0.8293 || lat > 55.8271) return true;
		return false;
	}
	/**
	 * 
	 * 纬度转换
	 *
	 * @author yd
	 * @created 2017年2月14日 下午3:55:29
	 *
	 * @param x
	 * @param y
	 * @return
	 */
	private static double transformLat(double x, double y) {
		double ret = -100.0 + 2.0 * x + 3.0 * y + 0.2 * y * y + 0.1 * x * y
				+ 0.2 * Math.sqrt(Math.abs(x));
		ret += (20.0 * Math.sin(6.0 * x * pi) + 20.0 * Math.sin(2.0 * x * pi)) * 2.0 / 3.0;
		ret += (20.0 * Math.sin(y * pi) + 40.0 * Math.sin(y / 3.0 * pi)) * 2.0 / 3.0;
		ret += (160.0 * Math.sin(y / 12.0 * pi) + 320 * Math.sin(y * pi / 30.0)) * 2.0 / 3.0;
		return ret;
	}
	/**
	 * 
	 * 经度转换
	 *
	 * @author yd
	 * @created 2017年2月14日 下午3:55:42
	 *
	 * @param x
	 * @param y
	 * @return
	 */
	private static double transformLon(double x, double y) {
		double ret = 300.0 + x + 2.0 * y + 0.1 * x * x + 0.1 * x * y + 0.1
				* Math.sqrt(Math.abs(x));
		ret += (20.0 * Math.sin(6.0 * x * pi) + 20.0 * Math.sin(2.0 * x * pi)) * 2.0 / 3.0;
		ret += (20.0 * Math.sin(x * pi) + 40.0 * Math.sin(x / 3.0 * pi)) * 2.0 / 3.0;
		ret += (150.0 * Math.sin(x / 12.0 * pi) + 300.0 * Math.sin(x / 30.0 * pi)) * 2.0 / 3.0;
		return ret;
	}
}
