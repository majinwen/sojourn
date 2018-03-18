package com.ziroom.minsu.report.test.basedata.service;

import base.BaseTest;
import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.minsu.report.board.dto.RegionRequest;
import com.ziroom.minsu.report.board.service.RegionDataService;
import com.ziroom.minsu.report.board.vo.RegionDataItem;
import com.ziroom.minsu.report.board.vo.RegionItem;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.List;

/**
 * 
 * <p></p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author liujun
 * @since 1.0
 * @version 1.0
 */
public class RegionDataServiceTest extends BaseTest {

    @Resource(name="report.regionDataService")
    private RegionDataService regionDataService;

    @Test
    public void findRegionListByNationCodeTest() throws Exception {
        String nationCode = "100000";
        List<RegionItem> list = regionDataService.findRegionListByNationCode(nationCode);
        System.err.println(JsonEntityTransform.Object2Json(list));
    }
    
    @Test
    public void findRegionDataItemListTest() throws Exception {
    	RegionRequest regionRequest = new RegionRequest();
    	regionRequest.setRegionFid("8a9e988b59810f230159810f240b0000");
    	regionRequest.setTargetMonth("2017-01");
		List<RegionDataItem> list = regionDataService.findRegionDataItemList(regionRequest);
    	System.err.println(JsonEntityTransform.Object2Json(list));
    }
}