package com.ziroom.minsu.services.search.service;

import base.BaseTest;
import com.asura.framework.base.util.JsonEntityTransform;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * <p>测试</p>
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
public class AutoServiceImplTest  extends BaseTest {



    @Resource(name = "search.autoServiceImpl")
    private AutoServiceImpl autoService;


    @Test
    public void TestTest() {
        Object o = autoService.getCommunityName("xincheng","beijing");
        System.out.println(JsonEntityTransform.Object2Json(o));
    }

}