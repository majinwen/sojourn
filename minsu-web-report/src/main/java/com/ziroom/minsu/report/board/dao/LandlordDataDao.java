package com.ziroom.minsu.report.board.dao;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.ziroom.minsu.report.board.dto.LandlordRequest;
import com.ziroom.minsu.report.board.vo.LandlordDataItem;

/**
 * <p>经营数据房东dao</p>
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
@Repository("report.landlordDataDao")
public class LandlordDataDao {

	private String SQLID = "report.landlordDataDao.";
	
	/**
	 *  跨库使用
	 */
	@Autowired
	@Qualifier("minsuReport.all.MybatisDaoContext")
	private MybatisDaoContext unionDaoContext;
	
	/**
	 * 根据大区逻辑id及日期查询数据-数据来源于定时任务
	 *
	 * @author liujun
	 * @created 2017年2月15日
	 *
	 * @param landlordRequest
	 * @throws ParseException 
	 * @return
	 */
	public List<LandlordDataItem> findLandlordDataItemList(LandlordRequest landlordRequest) {
		return unionDaoContext.findAll(SQLID + "findLandlordDataItemList", LandlordDataItem.class, landlordRequest);
	}
	
	/**
	 * 根据大区逻辑id及日期查询数据
	 *
	 * @author liujun
	 * @created 2017年2月15日
	 *
	 * @param landlordRequest
	 * @throws ParseException 
	 * @return
	 */
	public List<LandlordDataItem> findLandlordDataItemListFromTask(LandlordRequest landlordRequest) {
		return unionDaoContext.findAll(SQLID + "findLandlordDataItemListFromTask", LandlordDataItem.class, landlordRequest);
	}

}
