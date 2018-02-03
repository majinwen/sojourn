/**
 * @FileName: GoogleBaiduConvertText.java
 * @Package com.ziroom.minsu.services.common.utils.test
 * 
 * @author yd
 * @created 2017年2月13日 下午4:50:09
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.common.utils.test;

import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.asura.framework.base.util.Check;
import com.ziroom.minsu.services.common.utils.CloseableHttpsUtil;
import com.ziroom.minsu.services.common.utils.GoogleApiUtils;
import com.ziroom.minsu.services.common.utils.Gps;
import com.ziroom.minsu.services.common.utils.JsonTransform;


/**
 * <p>google地图经纬度或百度经纬度 互转
 *  百度接口地址：http://api.map.baidu.com/ag/coord/convert?from=0&to=4&x=116.308992&y=40.059225
 *  params:
 *  其中，from和to对应的值分别是：0真实坐标；2google坐标；4baidu坐标。
 *  from：被转换的坐标体系
 *  to：转换到这个坐标体系
 *  x：经度 39.914889
 *  y：纬度116.403874
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
public class GoogleBaiduConvertText {


	// 测试变量
	private  static volatile int number = 0;


	/**
	 * 线程池框架
	 */
	private final static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(50, 100, 3000L, TimeUnit.MILLISECONDS,
			new LinkedBlockingQueue<Runnable>());

	public static void main(String[] args) {

		//百度转谷歌
		/*	String url = "http://api.map.baidu.com/ag/coord/convert?from=0&to=2&x=121.449951&y=31.224106";

		String res = CloseableHttpUtil.sendGet(url, null);
		System.out.println(res);*/

		/*double[] googleL = bd_decrypt(116.543488, 39.969398);

		System.out.println(googleL.toString());*/

		addAbc();

	}

	public static void addAbc(){
		int i = 0;
		Runnable ru = new Runnable() {
			@Override
			public void run() {
				number++;
				Gps gps = GoogleApiUtils.geocode("北京", 1);
				if(number==10000||number==20000||number==30000){
					System.out.println(gps);	
				}	

			}
		};

		for (i = 0 ;i<30000 ;i++) {
			threadPoolExecutor.execute(ru);
		}

	}



}
