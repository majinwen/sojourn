package com.ziroom.minsu.services.search.service;

import base.BaseTest;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * <p>提示语测试</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/4/14.
 * @version 1.0
 * @since 1.0
 */
public class SuggestIndexServiceImplTest extends BaseTest {


    @Resource(name = "search.suggestIndexServiceImpl")
    private SuggestIndexServiceImpl suggestIndexService;


    @Test
    public void TestTest() {
        suggestIndexService.initSuggestIndex();

    }
}