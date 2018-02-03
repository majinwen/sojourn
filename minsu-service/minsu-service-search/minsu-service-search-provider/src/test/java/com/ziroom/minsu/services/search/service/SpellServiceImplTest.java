package com.ziroom.minsu.services.search.service;

import base.BaseTest;
import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.minsu.services.solr.utils.SpellUtils;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>拼音拆词</p>
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
public class SpellServiceImplTest extends BaseTest {

    @Resource(name = "search.spellServiceImpl")
    private SpellServiceImpl spellService;





    @Test
    public void TestinitSpellInfo(){
        spellService.initSpellInfo();
    }


    @Test
    public void Testspell2Words(){
        Long aaa = System.currentTimeMillis();
        for(int i=0;i<100;i++){
            List<String> aa = SpellUtils.spell2Words("chaoyang",10);
            System.out.println(JsonEntityTransform.Object2Json(aa));
        }
        System.out.println(SpellUtils.getMapSize());
        Long bbb = System.currentTimeMillis();
        System.out.println("===========================================");
        System.out.println((bbb-aaa)/100);
    }


    @Test
    public void Testtrans2Words(){
        Long aaa = System.currentTimeMillis();
        for(int i=0;i<100;i++){
            String aa = SpellUtils.trans2Words("测试ceshi",5,"");
            System.out.println(JsonEntityTransform.Object2Json(aa));
        }
        System.out.println(SpellUtils.getMapSize());
        Long bbb = System.currentTimeMillis();
        System.out.println("===========================================");
        System.out.println((bbb-aaa)/100);
    }




}
