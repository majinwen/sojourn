/**
 * @Description:
 * @Author:chengxiang.huang
 * @Date:2016年3月24日 下午10:36:16
 * @Version: V1.0
 */
package com.ziroom.minsu.services.search.thread;

import com.ziroom.minsu.services.solr.common.impl.IndexServiceImpl;
import com.ziroom.minsu.services.solr.entity.ZiRoom;
import com.ziroom.minsu.services.solr.index.SolrCore;

import java.util.Random;
import java.util.UUID;

/**
 * 
 * @Description:
 * @Author:chengxiang.huang 2016年3月24日
 * @CreateTime: 2016年3月24日 下午10:36:16
 * @Version 1.0
 */
public class AddIndexThread implements Runnable{

	private ZiRoom ziRoomHouseSolrBean;
	
	@Override
	public void run() {
		for(int i=0;i<5000;i++){
			ziRoomHouseSolrBean=new ZiRoom();
			ziRoomHouseSolrBean.setId(UUID.randomUUID().toString());
	        ziRoomHouseSolrBean.setName("益城君安家园");
	        ziRoomHouseSolrBean.setDesc("益城此小区环境十分优美，小区门口24小时保安执勤。出小区北门向西步行5分钟可到乐天马特超市，附近有中国招商银行和工商银行，民生银行，华联超市也在附近，北京同仁堂，金像大药房都在附近，生活设施非常方便！");
	        ziRoomHouseSolrBean.setAddress("益城从小区北门出来向西步行5分钟可到地铁4号线公益西桥地铁站，步行6分钟可到城南嘉园北公交站，可乘坐48路、66路、72路、556路、646路、998路、特14路、特8外、夜12路、夜18路等公交车，出行十分方便，期待您的入住！");
	        ziRoomHouseSolrBean.setPrice(new Random().nextFloat()*1000);
	        ziRoomHouseSolrBean.setLat(new Random().nextFloat()*200);
	        ziRoomHouseSolrBean.setLgt(new Random().nextFloat()*200);
	        ziRoomHouseSolrBean.setPicUrl("http://ziroom/imgages/2.jpg");
	        new IndexServiceImpl().create(SolrCore.m_suggest, ziRoomHouseSolrBean);
		}
	}

}
