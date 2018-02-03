package com.ziroom.minsu.services.basedata.test.dao;

import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.base.MinsuEleEntity;
import com.ziroom.minsu.entity.conf.DicItemEntity;
import com.ziroom.minsu.services.basedata.dao.DicItemDao;
import com.ziroom.minsu.services.basedata.entity.EnumVo;
import com.ziroom.minsu.services.basedata.test.base.BaseTest;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>字典值管理</p>
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
public class DicItemDaoTest extends BaseTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(DicItemDaoTest.class);

    @Resource(name="basedata.dicItemDao")
    private DicItemDao dicItemDao;





    @Test
    public void TestgetDicItemListByCodeAndTemplate(){
        try {
            List<DicItemEntity> aa = dicItemDao.getDicItemListByCodeAndTemplate("code","template_fid");
            System.out.println(JsonEntityTransform.Object2Json(aa));
        }catch (Exception e){
            LogUtil.error(LOGGER, "error:{}", e);
        }
    }

    @Test
    public void TestcountCodeNum(){
        try {
           Long aa = dicItemDao.countCodeNum("222");
            System.out.println(aa);
        }catch (Exception e){
            LogUtil.error(LOGGER, "error:{}", e);
        }
    }

    @Test
    public void TestinsertDicItem(){
        try {
            DicItemEntity dicItemEntity = new DicItemEntity();
            dicItemEntity.setTemplateFid("template_fid");
            dicItemEntity.setShowName("test");
            dicItemEntity.setItemValue("value");
            dicItemEntity.setDicCode("code");
            dicItemDao.insertDicItem(dicItemEntity);
        }catch (Exception e){
            LogUtil.error(LOGGER, "error:{}", e);
        }
    }


    @Test
    public void TestupdateDicItemByFid(){
        try {
            DicItemEntity dicItemEntity = new DicItemEntity();
            dicItemEntity.setShowName("test1");
            dicItemEntity.setItemValue("value1");
            dicItemEntity.setDicCode("code1");
            dicItemEntity.setFid("8a9e9a9c53974a680153974a68ec0000");
            dicItemDao.updateDicItemByFid(dicItemEntity);
        }catch (Exception e){
            LogUtil.error(LOGGER, "error:{}", e);
        }
    }

    @Test
    public void TestupdateDicItemByFidFidIsNULL(){
        try {
            DicItemEntity dicItemEntity = new DicItemEntity();
            dicItemEntity.setShowName("test1");
            dicItemEntity.setItemValue("value1");
            dicItemEntity.setDicCode("code1");
            dicItemDao.updateDicItemByFid(dicItemEntity);
        }catch (Exception e){
            LogUtil.error(LOGGER, "error:{}", e);
        }
    }


    @Test
    public void TestgetConfDicByFid(){
        try {
            dicItemDao.getDicItemByFid("11111");
        }catch (Exception e){
            LogUtil.error(LOGGER, "error:{}", e);
        }
    }

    @Test
    public void selectEnumListTest(){
        List<EnumVo> list=dicItemDao.selectEnumList("8a9e9a92538ed86201538ed862430000","code1");
        System.out.println(JsonEntityTransform.Object2Json(list));
    }
    
    @Test
    public void selectDefaultEnumListTest(){
    	 List<EnumVo> list=dicItemDao.selectDefaultEnumList("8a9e9aaf53c6a9df0153c6a9df880000", "ProductRulesEnum001");
    	 System.err.println(JsonEntityTransform.Object2Json(list));
    }
    
    @Test
    public void getDefaultTextValueTest(){
    	List<String> list=dicItemDao.getDefaultTextValue("8a9e9aaf53c6a9df0153c6a9df880000", "HouseAuditEnum003");
    	System.err.println(JsonEntityTransform.Object2Json(list));
    }
    
    @Test
    public void getListByLikeTest(){
    	List<MinsuEleEntity>list=dicItemDao.getListByLike("BJS", "HouseAuditEnum");
    	System.err.println(list);
    }
}
