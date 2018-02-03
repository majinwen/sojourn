package com.ziroom.minsu.services.search.index;

import base.BaseTest;
import com.ziroom.minsu.services.search.thread.AddIndexThread;
import com.ziroom.minsu.services.solr.common.IndexService;
import com.ziroom.minsu.services.solr.common.QueryService;
import com.ziroom.minsu.services.solr.entity.FieldEntity;
import com.ziroom.minsu.services.solr.entity.Phone;
import com.ziroom.minsu.services.solr.entity.ZiRoom;
import com.ziroom.minsu.services.solr.index.SolrCore;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.*;

/**
 * <p>创建索引的测试</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/3/16.
 * @version 1.0
 * @since 1.0
 */
public class IndexTest extends BaseTest {

    @Resource(name="search.queryService")
    private QueryService queryService;

    @Resource(name="search.indexService")
    private IndexService indexService;



    @Test
    public void TestTest(){

        System.out.println("test");
    }



    @Test
    public void TestIndex(){
        Long  aa = System.currentTimeMillis();
        FieldEntity entity = new FieldEntity();
        entity.setId(3);
        entity.setName("修改之后的名字");
        entity.setPrice(1312312);
        Set<String> set = new HashSet<>();
        set.add("aaaa");
        set.add("bbbb");
        set.add("cccc");
        entity.setText(set);
        entity.setLatitude(39.913773);
        entity.setLongitude(116.31645);
        indexService.creatIndex(SolrCore.m_suggest, entity);
        System.out.println("++++++++++++++++++++++++");
        System.out.println("++++++++++++++++++++++++");
        System.out.println("++++++++++++++++++++++++");
        System.out.println("++++++++++++++++++++++++");
        System.out.println("++++++++++++++++++++++++");
        Long  bb = System.currentTimeMillis();
        System.out.println("===================:  "+Thread.currentThread().getName()+" 线程创建一次索引的时间：" + (bb - aa) );
    }
    
    @Test
    public void testCreateIndex(){
    	ZiRoom ziRoomHouseSolrBean=new ZiRoom();
    	ziRoomHouseSolrBean.setId(UUID.randomUUID().toString());
        ziRoomHouseSolrBean.setName("君安一家");
        ziRoomHouseSolrBean.setDesc("此小区环境十分优美，小区门口24小时保安执勤。出小区北门向西步行5分钟可到乐天马特超市，附近有中国招商银行和工商银行，民生银行，华联超市也在附近，北京同仁堂，金像大药房都在附近，生活设施非常方便！");
        ziRoomHouseSolrBean.setAddress("从小区北门出来向西步行5分钟可到地铁4号线公益西桥地铁站，步行6分钟可到城南嘉园北公交站，可乘坐48路、66路、72路、556路、646路、998路、特14路、特8外、夜12路、夜18路等公交车，出行十分方便，期待您的入住！");
        ziRoomHouseSolrBean.setPrice(new Random().nextFloat()*1000);
        ziRoomHouseSolrBean.setLat(new Random().nextFloat()*200);
        ziRoomHouseSolrBean.setLgt(new Random().nextFloat()*200);
        ziRoomHouseSolrBean.setPicUrl("http://ziroom/imgages/2.jpg");
        Long  start = System.currentTimeMillis();
        indexService.create(SolrCore.m_suggest, ziRoomHouseSolrBean);
        Long  end = System.currentTimeMillis();
        System.out.println("创建单条索引的时间为:"+(end-start)+"ms");
    }
    
    @Test
    public void testCreateIndexWithoutToken(){
    	Long  start = System.currentTimeMillis();
        indexService.create(SolrCore.m_spell, new Phone(UUID.randomUUID().toString(),"魅族1009","华为2","白色"));
        Long  end = System.currentTimeMillis();
        System.out.println("创建单条索引的时间为:"+(end-start)+"ms");
    }
    
    @Test
    public void testBatchCreatIndex(){
    	List<Object> ziroomList=new ArrayList<Object>();
    	ZiRoom ziroom=new ZiRoom();
    	for(int i=0;i<100;i++){
    		ziroom.setId(UUID.randomUUID().toString());
            ziroom.setName("君安一家");
            ziroom.setDesc("此小区环境十分优美，小区门口24小时保安执勤。出小区北门向西步行5分钟可到乐天马特超市，附近有中国招商银行和工商银行，民生银行，华联超市也在附近，北京同仁堂，金像大药房都在附近，生活设施非常方便！");
            ziroom.setAddress("从小区北门出来向西步行5分钟可到地铁4号线公益西桥地铁站，步行6分钟可到城南嘉园北公交站，可乘坐48路、66路、72路、556路、646路、998路、特14路、特8外、夜12路、夜18路等公交车，出行十分方便，期待您的入住！");
            ziroom.setPrice(new Random().nextFloat()*1000);
            ziroom.setLat(new Random().nextFloat()*200);
            ziroom.setLgt(new Random().nextFloat()*200);
            ziroom.setPicUrl("http://ziroom/imgages/2.jpg");
            ziroomList.add(ziroom);
    	}
    	Long  start = System.currentTimeMillis();
    	indexService.batchCreateIndex(SolrCore.m_suggest, ziroomList);
    	Long  end = System.currentTimeMillis();
        System.out.println("创建单条索引的时间为:"+(end-start)+"ms");
    	
    }
    
    @Test
    public void testCreateIndex_list(){
        Long  aa = System.currentTimeMillis();
        for(int i=0;i<100000;i++){
        	List<Object> phoneList = new ArrayList<Object>();
            /*phoneList.add(new Phone(UUID.randomUUID().toString(),"中兴1008","华为2","白色"));
            phoneList.add(new Phone(UUID.randomUUID().toString(),"中兴1009","华为2","白色"));
            phoneList.add(new Phone(UUID.randomUUID().toString(),"中兴1010","华为2","白色"));
            phoneList.add(new Phone(UUID.randomUUID().toString(),"中兴1011","华为2","白色"));
            phoneList.add(new Phone(UUID.randomUUID().toString(),"中兴1012","华为2","蓝色"));
            phoneList.add(new Phone(UUID.randomUUID().toString(),"中兴1013","华为2","白色"));
            phoneList.add(new Phone(UUID.randomUUID().toString(),"中兴1014","华为2","红色"));
            phoneList.add(new Phone(UUID.randomUUID().toString(),"中兴1015","华为2","白色"));
            phoneList.add(new Phone(UUID.randomUUID().toString(),"中兴1016","华为2","黑色"));
            phoneList.add(new Phone(UUID.randomUUID().toString(),"中兴1008","中兴2","白色"));
            phoneList.add(new Phone(UUID.randomUUID().toString(),"中兴1009","中兴2","白色"));
            phoneList.add(new Phone(UUID.randomUUID().toString(),"中兴1008","小米2","白色"));*/
            phoneList.add(new Phone(UUID.randomUUID().toString(),"中兴1008","小米2","白色"));
            indexService.batchCreateIndex(SolrCore.m_spell, phoneList);
        }
        Long  bb = System.currentTimeMillis();
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>:  "+Thread.currentThread().getName()+" 线程创建一次索引的时间：" + (bb - aa) );
    }
    
    public static void main(String[] args) {
    	Long  aa = System.currentTimeMillis();
		for(int i=0;i<40;i++){
			Thread thread=new Thread(new AddIndexThread());
			thread.start();
        }
		Long  bb = System.currentTimeMillis();
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>:  "+Thread.currentThread().getName()+" 20个线程创建10W条索引的时间为：" + (bb - aa) );
	}

    @Test
    public void TestIndexCount(){
    	//dealIndex();
    }




}
