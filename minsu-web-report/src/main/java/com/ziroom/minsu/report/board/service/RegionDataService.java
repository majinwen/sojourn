package com.ziroom.minsu.report.board.service;

import java.text.ParseException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ziroom.minsu.report.board.dao.CityDailyMsgDao;
import com.ziroom.minsu.report.board.dao.RegionDataDao;
import com.ziroom.minsu.report.board.dto.RegionRequest;
import com.ziroom.minsu.report.board.vo.RegionDataItem;
import com.ziroom.minsu.report.board.vo.RegionItem;

/**
 * 
 * <p>大区数据service</p>
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
@Service("report.regionDataService")
public class RegionDataService {

    @Resource(name="report.regionDataDao")
    private RegionDataDao regionDataDao;
    
    @Resource(name="report.cityDailyMsgDao")
    private CityDailyMsgDao cityDailyMsgDao;

	/**
	 * 根据国家编码查询大区列表
	 *
	 * @author liujun
	 * @created 2017年1月16日
	 *
	 * @param nationCode
	 * @return 
	 */
	public List<RegionItem> findRegionListByNationCode(String nationCode) {
		return regionDataDao.findRegionListByNationCode(nationCode);
	}

	/**
	 * 查询大区所属城市数据列表
	 *
	 * @author liujun
	 * @created 2017年1月16日
	 *
	 * @param regionRequest
	 * @return
	 * @throws ParseException 
	 */
	public List<RegionDataItem> findRegionDataItemList(RegionRequest regionRequest) {
		return regionDataDao.findRegionDataItemList(regionRequest);
	}
	
	/**
	 * 查询大区所属城市数据列表-数据来源于定时任务
	 *
	 * @author liujun
	 * @created 2017年1月16日
	 *
	 * @param regionRequest
	 * @return
	 * @throws ParseException 
	 */
	public List<RegionDataItem> findRegionDataItemListFromTask(RegionRequest regionRequest) {
		return regionDataDao.findRegionDataItemListFromTask(regionRequest);
	}

}
