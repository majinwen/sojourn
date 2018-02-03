package com.ziroom.minsu.services.search.service;

import base.BaseTest;
import com.ziroom.minsu.services.lianjia.service.SyncHousesInfoServiceImpl;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * <p>楼盘拼写测试</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/4/13.
 * @version 1.0
 * @since 1.0
 */
public class SpellIndexServiceImplTest extends BaseTest {

    @Resource(name="search.spellIndexServiceImpl")
    private SpellIndexServiceImpl spellIndexService;

    @Resource(name="search.spellServiceImpl")
    private SpellServiceImpl spellService;



    @Resource(name="search.syncHousesInfoServiceImpl")
    private SyncHousesInfoServiceImpl syncHousesInfoService;


    @Test
    public void TestTest(){
        System.out.println("test");
    }


    @Test
    public void TestinitHousePinyin(){
        spellService.initIndexPinyin();
    }


    @Test
    public void TestsyncHousesInfoByCode(){

        syncHousesInfoService.syncHousesInfoByCode("110000");
    }


    @Test
    public void TestinitCommunityIndex(){
        spellIndexService.initCommunityIndex();
        System.out.println("test");
    }

}
