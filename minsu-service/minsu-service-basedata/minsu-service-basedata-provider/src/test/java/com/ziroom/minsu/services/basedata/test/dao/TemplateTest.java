package com.ziroom.minsu.services.basedata.test.dao;

import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.conf.TemplateEntity;
import com.ziroom.minsu.services.basedata.dao.TemplateDao;
import com.ziroom.minsu.services.basedata.entity.TemplateEntityVo;
import com.ziroom.minsu.services.basedata.test.base.BaseTest;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>模板管理</p>
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
public class TemplateTest extends BaseTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(TemplateTest.class);

    @Resource(name="basedata.templateDao")
    private TemplateDao templateDao;





    @Test
    public void TestgetTemplateList(){
        try {
            List<TemplateEntityVo> aa = templateDao.getTemplateList();
            System.out.println(JsonEntityTransform.Object2Json(aa));
        }catch (Exception e){
            LogUtil.error(LOGGER, "error:{}", e);
        }
    }



    @Test
    public void TestinsertTemplate(){
        try {
            TemplateEntity mouldEntity = new TemplateEntity();
            mouldEntity.setTemplateName("sdadasd");
            mouldEntity.setTemplateStatus(2);
            templateDao.insertTemplate(mouldEntity);
        }catch (Exception e){
            LogUtil.error(LOGGER, "error:{}", e);
        }
    }

    @Test
    public void TestupdateTemplateByFid(){
        try {
            TemplateEntity mouldEntity = new TemplateEntity();
            mouldEntity.setFid("8a9e9a92538ed86201538ed862430000");
            mouldEntity.setTemplateName("hhhhhhhhhhh");
            mouldEntity.setTemplateStatus(12);
            templateDao.updateTemplateByFid(mouldEntity);
        }catch (Exception e){
            LogUtil.error(LOGGER, "error:{}", e);
        }
    }

}
