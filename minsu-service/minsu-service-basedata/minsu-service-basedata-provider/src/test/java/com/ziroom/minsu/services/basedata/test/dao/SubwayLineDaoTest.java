package com.ziroom.minsu.services.basedata.test.dao;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.minsu.entity.conf.SubwayLineEntity;
import com.ziroom.minsu.services.basedata.dao.SubwayLineDao;
import com.ziroom.minsu.services.basedata.dto.SubwayLineRequest;
import com.ziroom.minsu.services.basedata.entity.SubwayLineVo;
import com.ziroom.minsu.services.basedata.test.base.BaseTest;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.List;

/**
 * 
 * @author homelink
 *
 */
public class SubwayLineDaoTest extends BaseTest {

	@Resource(name = "basedata.subwayLineDao")
	private SubwayLineDao subwayLineDao;
	
	@Test
	public void TestFindSubwayLinePageList() {
		SubwayLineRequest subwayLineRequest = new SubwayLineRequest();
		subwayLineRequest.setLimit(5);
		subwayLineRequest.setPage(1);
		subwayLineRequest.setLineName("10号");
		subwayLineRequest.setStationName("十里");
		PagingResult<SubwayLineVo> result = subwayLineDao.findSubwayLinePageList(subwayLineRequest);
		List<SubwayLineVo> list = result.getRows();
		System.out.println(list);
	}

	@Test
	public void TestSaveSubwayLine() {
		SubwayLineEntity subwayLine = new SubwayLineEntity();
		subwayLine.setFid(UUIDGenerator.hexUUID());
		subwayLine.setLineName("2号线");
		subwayLine.setNationCode("11");
		subwayLine.setProvinceCode("22");
		subwayLine.setCityCode("33");
		subwayLine.setSort(1);
		subwayLine.setCreateFid(UUIDGenerator.hexUUID());
		/*int resultNum = subwayLineDao.saveSubwayLine(subwayLine);
		System.out.println("resultNum:" + resultNum);*/
	}

}
