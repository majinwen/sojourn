package com.ziroom.minsu.services.basedata.test.dao;

import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.conf.CityTemplateEntity;
import com.ziroom.minsu.services.basedata.dao.CityTemplateDao;
import com.ziroom.minsu.services.basedata.test.base.BaseTest;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

/**
 * <p>模板测试</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/3/12.
 * @version 1.0
 * @since 1.0
 */
public class CityTemplateDaoTest extends BaseTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(CityTemplateDaoTest.class);

    @Resource(name="basedata.cityTemplateDao")
    private CityTemplateDao cityTemplateDao;



    @Test
    public void TestgetCityTemplateByCityFid(){
        try {
            cityTemplateDao.getCityTemplateByCityCode("");
        }catch (Exception e){
        	LogUtil.error(LOGGER, "error:{}", e);
        }
    }

    @Test
    public void TestinsertCityTemplate(){
        try {
            CityTemplateEntity cityMouldEntity = new CityTemplateEntity();
            cityMouldEntity.setCityCode("aaa");
            cityMouldEntity.setTemplateFid("bbb");
            cityTemplateDao.insertCityTemplate(cityMouldEntity);

        }catch (Exception e){
        	LogUtil.error(LOGGER, "error:{}", e);
        }
    }

    @Test
    public void TestdeleteByCityFid(){
        try {
            cityTemplateDao.deleteByCityCode("aaa");
        }catch (Exception e){
        	LogUtil.error(LOGGER, "error:{}", e);
        }
    }

}
