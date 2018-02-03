package com.ziroom.minsu.services.search.service;

import javax.annotation.Resource;

import org.junit.Test;

import base.BaseTest;

/**
 * 
 * <p>测试</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author zhangyl
 * @since 1.0
 * @version 1.0
 */
public class FreshZryIndexServiceImplTest extends BaseTest {

	@Resource(name = "search.freshZryIndexServiceImpl")
    private FreshZryIndexServiceImpl freshZryIndexService;


    @Test
    public void TestTest() {
    	freshZryIndexService.dealSearchIndex("");
    }

}