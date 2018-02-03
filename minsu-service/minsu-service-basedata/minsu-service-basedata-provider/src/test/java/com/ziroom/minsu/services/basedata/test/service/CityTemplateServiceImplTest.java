package com.ziroom.minsu.services.basedata.test.service;

import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.services.basedata.service.CityTemplateServiceImpl;
import com.ziroom.minsu.services.basedata.test.base.BaseTest;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

/**
 * <p>城市模板测试</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/3/24.
 * @version 1.0
 * @since 1.0
 */
public class CityTemplateServiceImplTest extends BaseTest {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CityTemplateServiceImplTest.class);

    @Resource(name = "basedata.cityTemplateServiceImpl")
    private CityTemplateServiceImpl cityTemplateServiceImpl;


    @Test
    public void TestinsertTemplateAndCopyIntoDicItem(){
        try {
            cityTemplateServiceImpl.insertTemplateAndCopyIntoDicItem("aaaaaaaaaa","template_fid","afi");
        }catch (Exception e){
            LogUtil.error(LOGGER, "error:{}", e);
        }
    }


    @Test
    public void TestinsertIntoDicItem(){
        try {
            cityTemplateServiceImpl.insertIntoDicItem("template_fid","afi");
        }catch (Exception e){
            LogUtil.error(LOGGER, "error:{}", e);
        }
    }


}
