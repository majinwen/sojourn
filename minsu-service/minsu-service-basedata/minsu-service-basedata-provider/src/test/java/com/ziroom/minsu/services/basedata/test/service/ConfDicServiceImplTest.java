package com.ziroom.minsu.services.basedata.test.service;

import com.alibaba.fastjson.JSON;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.base.MinsuEleEntity;
import com.ziroom.minsu.entity.conf.ConfDicEntity;
import com.ziroom.minsu.entity.conf.DicItemEntity;
import com.ziroom.minsu.services.basedata.service.ConfDicServiceImpl;
import com.ziroom.minsu.services.basedata.test.base.BaseTest;
import com.ziroom.minsu.valenum.productrules.HouseRankEnum;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

import java.util.List;

/**
 * <p>配置测试</p>
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
public class ConfDicServiceImplTest extends BaseTest {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ConfDicServiceImplTest.class);

    @Resource(name = "basedata.confDicServiceImpl")
    private ConfDicServiceImpl confDicService;

    @Test
    public void TestgetDicItemListByCodeAndTemplate(){
        try {
            List<DicItemEntity> aa = confDicService.getDicItemListByCodeAndTemplate("code","template_fid");
            System.out.println(JsonEntityTransform.Object2Json(aa));
        }catch (Exception e){
        	LogUtil.error(LOGGER, "error:{}", e);
        }
    }
    
    @Test
    public void insertConfDicTest(){
    	ConfDicEntity confDicEntity=new ConfDicEntity();
    	confDicEntity.setFid(UUIDGenerator.hexUUID());
    	confDicEntity.setPfid("8a9e9cce53a24f4a0153a24f4a500000");
    	confDicEntity.setDicLevel(2);
    	confDicEntity.setShowName("dddd");
    	confDicEntity.setDicIndex(1);
    	confDicService.insertConfDic(confDicEntity);
    }
    
    @Test
    public void getTextListByLikeCodes(){
    	List<MinsuEleEntity> list = confDicService.getListByLike(null, "HouseRankEnum");    	
    	System.err.println(JSON.toJSON(list));
    }
}
