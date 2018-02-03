package com.ziroom.minsu.services.basedata.test.dao;

import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.conf.ConfDicEntity;
import com.ziroom.minsu.services.basedata.dao.ConfDicDao;
import com.ziroom.minsu.services.basedata.entity.TreeNodeVo;
import com.ziroom.minsu.services.basedata.test.base.BaseTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>字典管理</p>
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
@ContextConfiguration(locations = { "classpath:META-INF/spring/applicationContext-basedata.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class ConfDicDaoTest extends BaseTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(ConfDicDaoTest.class);

    @Resource(name="basedata.confDicDao")
    private ConfDicDao confDicDao;




    @Test
    public void TestcountChildNumBuCode(){
        try {
            Long tree =  confDicDao.countChildNumBuCode("code");
            System.out.println(JsonEntityTransform.Object2Json(tree));
        }catch (Exception e){
            LogUtil.error(LOGGER, "error:{}", e);
        }
    }



    @Test
    public void TestgetDicTree(){
        try {
            List<TreeNodeVo> tree = confDicDao.getDicTree("2");
            System.out.println(JsonEntityTransform.Object2Json(tree));
        }catch (Exception e){
            LogUtil.error(LOGGER, "error:{}", e);
        }
    }


    @Test
    public void TestinsertConfDicPfidIsNULL(){
        try {
            ConfDicEntity confDicEntity = new ConfDicEntity();
            confDicEntity.setDicCode("code");
            confDicEntity.setShowName("name");
            confDicDao.insertConfDic(confDicEntity);
        }catch (Exception e){
            LogUtil.error(LOGGER, "error:{}", e);
        }
    }

    @Test
    public void TestinsertConfDic(){
        try {
            ConfDicEntity confDicEntity = new ConfDicEntity();
           // confDicEntity.setDicCode("code");
            confDicEntity.setShowName("测试3");
            confDicEntity.setDicLevel(1);
            confDicEntity.setDicIndex(12);
            confDicEntity.setDicCode("CS3");
            confDicEntity.setFid(UUIDGenerator.hexUUID());
            confDicEntity.setPfid("8a9e9cce53a24f4a0153a24f4a500000");
            confDicEntity.setDicType(2);
            confDicDao.insertConfDic(confDicEntity);
        }catch (Exception e){
            LogUtil.error(LOGGER, "error:{}", e);
        }
    }
    @Test
    public void TestinsertConfDicFidIsNotExit(){
        try {
            ConfDicEntity confDicEntity = new ConfDicEntity();
            confDicEntity.setDicCode("code");
            confDicEntity.setShowName("name");
            confDicEntity.setPfid("sss");
            confDicDao.insertConfDic(confDicEntity);
        }catch (Exception e){
            LogUtil.error(LOGGER, "error:{}", e);
        }
    }

    @Test
    public void TestupdateConfDicByFid(){
        try {
            ConfDicEntity confDicEntity = new ConfDicEntity();
            confDicEntity.setFid("8a9e9a9c53970ac90153970ac9030000");
            confDicEntity.setDicCode("code1");
            confDicEntity.setShowName("name1");
            confDicDao.updateConfDicByFid(confDicEntity);
        }catch (Exception e){
            LogUtil.error(LOGGER, "error:{}", e);
        }
    }

    @Test
    public void TestupdateConfDicByFidFidIsNULL(){
        try {
            ConfDicEntity confDicEntity = new ConfDicEntity();
            confDicEntity.setFid("");
            confDicEntity.setDicCode("code1");
            confDicEntity.setShowName("name1");
            confDicDao.updateConfDicByFid(confDicEntity);
        }catch (Exception e){
            LogUtil.error(LOGGER, "error:{}", e);
        }
    }


    @Test
    public void TestgetConfDicByFid(){
        try {
            confDicDao.getConfDicByFid("");
        }catch (Exception e){
            LogUtil.error(LOGGER, "error:{}", e);
        }
    }


    @Test
    public void TestgetConfDicByCode(){
        try {
            ConfDicEntity confDicEntity = confDicDao.getConfDicByCode("aaaa");
            System.out.println(JsonEntityTransform.Object2Json(confDicEntity));
        }catch (Exception e){
            LogUtil.error(LOGGER, "error:{}", e);
        }
    }
    
    @Test
    public void TestgetConfDicByPfid(){
        try {
        	List<ConfDicEntity> res =  confDicDao.getConfDicByPfid("8a9e9cce53a24f4a0153a24f4a500000");
        	System.out.println(JsonEntityTransform.Object2Json(res));
        }catch (Exception e){
            LogUtil.error(LOGGER, "error:{}", e);
        }
    }
}
