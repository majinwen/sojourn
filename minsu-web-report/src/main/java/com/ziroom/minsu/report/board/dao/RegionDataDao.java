package com.ziroom.minsu.report.board.dao;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.ziroom.minsu.report.board.dto.RegionRequest;
import com.ziroom.minsu.report.board.vo.RegionDataItem;
import com.ziroom.minsu.report.board.vo.RegionItem;

/**
 * <p>大区数据dao</p>
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
@Repository("report.regionDataDao")
public class RegionDataDao {

	private String SQLID = "report.regionDataDao.";
	
	/**
	 *  跨库使用
	 */
	@Autowired
	@Qualifier("minsuReport.all.MybatisDaoContext")
	private MybatisDaoContext unionDaoContext;

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
		return unionDaoContext.findAll(SQLID + "findRegionListByNationCode", RegionItem.class, nationCode);
	}

	/**
	 * 根据大区逻辑id及目标月份查询城市目标看板数据
	 *
	 * @author liujun
	 * @created 2017年1月16日
	 *
	 * @param regionRequest
	 * @throws ParseException 
	 * @return
	 */
	public List<RegionDataItem> findRegionDataItemList(RegionRequest regionRequest) {
		return unionDaoContext.findAll(SQLID + "findRegionDataItemList", RegionDataItem.class, regionRequest);
	}
	
	/**
	 * 根据大区逻辑id及目标月份查询城市目标看板数据-数据来源于定时任务
	 *
	 * @author liujun
	 * @created 2017年1月16日
	 *
	 * @param regionRequest
	 * @throws ParseException 
	 * @return
	 */
	public List<RegionDataItem> findRegionDataItemListFromTask(RegionRequest regionRequest) {
		return unionDaoContext.findAll(SQLID + "findRegionDataItemListFromTask", RegionDataItem.class, regionRequest);
	}

}
