package com.ziroom.minsu.services.basedata.test.api.inner;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.minsu.entity.conf.HotRegionEntity;
import com.ziroom.minsu.services.basedata.dto.HotRegionRequest;
import com.ziroom.minsu.services.basedata.proxy.HotRegionServiceProxy;
import com.ziroom.minsu.services.basedata.test.base.BaseTest;

import org.junit.Test;

import javax.annotation.Resource;

import java.util.Date;

/**
 * <p>热门区域测试</p>
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
public class HotRegionServiceTest extends BaseTest {

    @Resource(name="basedata.hotRegionServiceProxy")
    private HotRegionServiceProxy hotRegionServiceProxy;

    @Test
    public void saveHotRegionTest(){
        HotRegionEntity hotRegion = new HotRegionEntity();
        hotRegion.setNationCode("CN");
        hotRegion.setProvinceCode("BJ");
        hotRegion.setCityCode("BJ");
        hotRegion.setAreaCode("DCQ");
        hotRegion.setCreateFid("8a9e9aaf5383821f01538391fcbd0001");
        hotRegion.setHot(85);
        hotRegion.setLastModifyDate(new Date());
        hotRegion.setLongitude(116.3);
        hotRegion.setLatitude(39.9);
        hotRegion.setRegionName("将台");
        hotRegion.setRegionStatus(0);
        hotRegion.setRegionType(1);
        String resultJson = hotRegionServiceProxy.saveHotRegion(JsonEntityTransform.Object2Json(hotRegion));
        System.err.println(resultJson);
    }

    @Test
    public void searchHotRegionByFidTest(){
        String hotRegionFid = "8a9e9aab539e838901539e83892a0000";
        String resultJson = hotRegionServiceProxy.searchHotRegionByFid(hotRegionFid);
        System.err.println(resultJson);
    }

    @Test
    public void updateHotRegionTest(){
        HotRegionEntity hotRegion = new HotRegionEntity();
        hotRegion.setFid("8a9e9aab539e838901539e83892a0000");
        hotRegion.setRegionName("将台");
        hotRegion.setRegionStatus(0);
        hotRegion.setRegionType(2);
        hotRegion.setIsFile(1);
        String resultJson = hotRegionServiceProxy.updateHotRegion(JsonEntityTransform.Object2Json(hotRegion));
        System.err.println(resultJson);
    }

    @Test
    public void searchHotRegionsTest(){
        HotRegionRequest hotRegion = new HotRegionRequest();
        hotRegion.setNationCode("CN");
        hotRegion.setProvinceCode("BJ");
        hotRegion.setCityCode("BJS");
        hotRegion.setAreaCode("DCQ");
        hotRegion.setRegionType(1);
        hotRegion.setRegionName("将台");
        String resultJson = hotRegionServiceProxy.searchHotRegions(JsonEntityTransform.Object2Json(hotRegion));
        System.err.println(resultJson);
    }

    @Test
    public void editHotRegionStatusTest(){
        String hotRegionFid = "8a9e9aab539e838901539e83892a0000";
        String resultJson = hotRegionServiceProxy.searchHotRegionByFid(hotRegionFid);
        System.err.println(resultJson);
        DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(resultJson);
        String returnJson = hotRegionServiceProxy.editHotRegionStatus(JsonEntityTransform.Object2Json(dto.getData().get("hotRegion")));
        System.err.println(returnJson);
    }
    
    @Test
    public void getListWithFileByCityCodeTest(){
    	String cityCode = "110100";
		String resultJson = hotRegionServiceProxy.getListWithFileByCityCode(cityCode);
    	System.err.println(resultJson);
    }
    
    @Test
    public void getValidRadiiMapTest(){
    	String resultJson = hotRegionServiceProxy.getValidRadiiMap();
    	System.err.println(resultJson);
    }

}
