package com.ziroom.minsu.services.basedata.test.dao;

import java.util.List;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.minsu.entity.conf.HotRegionEntity;
import com.ziroom.minsu.services.basedata.dao.HotRegionDao;
import com.ziroom.minsu.services.basedata.dto.HotRegionRequest;
import com.ziroom.minsu.services.basedata.entity.RegionExtVo;
import com.ziroom.minsu.services.basedata.test.base.BaseTest;

import org.junit.Test;

import javax.annotation.Resource;

/**
 * <p>热门区域测试</p>
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
public class HotRegionDaoTest extends BaseTest {



    @Resource(name="basedata.hotRegionDao")
    private HotRegionDao hotRegionDao;

    @Test
    public void findHotRegionPageListTest(){
    	HotRegionRequest hotRegionRequest = new HotRegionRequest();
    	hotRegionRequest.setNationCode(null);
    	PagingResult<HotRegionEntity> list = hotRegionDao.findHotRegionPageList(hotRegionRequest);
    	System.err.println(JsonEntityTransform.Object2Json(list));
    }

    @Test
    public void findHotRegionByFidTest(){
    	String hotRegionFid = "8a9e9ab153a4111f0153a4111f400000";
    	HotRegionEntity entity = hotRegionDao.findHotRegionByFid(hotRegionFid);
    	System.err.println(JsonEntityTransform.Object2Json(entity));
    }
    
    @Test
    public void getListWithFileByCityCodeTest(){
    	String cityCode = "110100";
		List<HotRegionEntity> list = hotRegionDao.getListWithFileByCityCode(cityCode);
    	System.err.println(JsonEntityTransform.Object2Json(list));
    }
    
    @Test
    public void findHotRegionsWithValidRadiiTest(){
    	List<HotRegionEntity> list = hotRegionDao.findHotRegionsWithValidRadii();
    	System.err.println(JsonEntityTransform.Object2Json(list));
    }

    @Test
    public void getRegionExtByRegionFidTest(){
        RegionExtVo regionExtVo = hotRegionDao.getRegionExtVoByRegionFid("8a9e9aa8565862960156586297020000");
        System.err.println(regionExtVo.getRegionName());
    }

}
