package com.ziroom.minsu.report.house.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.report.house.dto.HouseEfficDto;
import com.ziroom.minsu.report.house.vo.HouseEfficVo;

/**
 * 
 * <p>房源效率信息——dao层</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author ls
 * @since 1.0
 * @version 1.0
 */
@Repository("report.houseEfficDao")
public class HouseEfficDao {

private static final Logger LOGGER = LoggerFactory.getLogger(HouseInfoReportDao.class);
	
		private String SQLID = "report.houseEfficDao.";
	
	    @Autowired
	    @Qualifier("minsuReport.all.MybatisDaoContext")
	    private MybatisDaoContext mybatisDaoContext;
	
	    /**
	     * 整租，查询出所有房源Hfid集合
	     */
	    public PagingResult<HouseEfficVo> getEntireRentList(HouseEfficDto paramDto) {
	    	LogUtil.info(LOGGER, "HouseEfficDao, getEntireRentList, param={}", JsonEntityTransform.Object2Json(paramDto));
			PageBounds pageBounds = new PageBounds();
	        pageBounds.setLimit(paramDto.getLimit());
	        pageBounds.setPage(paramDto.getPage());
			return mybatisDaoContext.findForPage(SQLID+"getEntireRentList", HouseEfficVo.class, paramDto.toMap(), pageBounds);
		}
	    
	    /**
	     * 分租，查询出所有房间Rfid集合
	     */
	    public PagingResult<HouseEfficVo> getJoinRentList(HouseEfficDto paramDto) {
			PageBounds pageBounds = new PageBounds();
	        pageBounds.setLimit(paramDto.getLimit());
	        pageBounds.setPage(paramDto.getPage());
			return mybatisDaoContext.findForPage(SQLID+"getJoinRentList", HouseEfficVo.class, paramDto.toMap(), pageBounds);
		}
	    
	    /**
	     *  根据regionCode获取所有ProvinceCode集合
	     */
	    public List<String> getPCodeListByRCode(String regionCode) {
	    	return mybatisDaoContext.findAll(SQLID+"getPCodeListByRCode", String.class, regionCode);
		}
	    
	    /**
		 *  大区code==》大区名称
		 */
		public HouseEfficVo getFromCityRegionRel(String privinceCode) {
			return mybatisDaoContext.findOne(SQLID+"getFromCityRegionRel", HouseEfficVo.class, privinceCode);
		}
		
		/**
		 *  城市code==》城市名称
		 */
		public HouseEfficVo getFromConfCity(String cityCode) {
			return mybatisDaoContext.findOne(SQLID+"getFromConfCity", HouseEfficVo.class, cityCode);
		}
		
		/**
	     *  message库，t_msg_base表 首次咨询（整租，分租）
	     */
		public HouseEfficVo getFromTMsgByHfid(String houseBaseFid) {
			return mybatisDaoContext.findOne(SQLID+"getFromTMsgByHfid", HouseEfficVo.class, houseBaseFid);
		}
		public HouseEfficVo getFromTMsgByRfid(String roomFid) {
			return mybatisDaoContext.findOne(SQLID+"getFromTMsgByRfid", HouseEfficVo.class, roomFid);
		}
		
		/**
	     * evaluate库， t_msg_first_advisory表此房子 	  查询首次评价时间（整租，分租）
	     */
		public HouseEfficVo getFromTMsgFAByHfid(String houseBaseFid) {
			return mybatisDaoContext.findOne(SQLID+"getFromTMsgFAByHfid", HouseEfficVo.class, houseBaseFid);
		}
		public HouseEfficVo getFromTMsgFAByRfid(String roomFid) {
			return mybatisDaoContext.findOne(SQLID+"getFromTMsgFAByRfid", HouseEfficVo.class, roomFid);
		}

		/**
	     *  order库，订单快照表关联订单表     查询 首次申请（整租，分租）
	     */
		public HouseEfficVo getApplyFromTOrderByHfid(String houseBaseFid) {
			return mybatisDaoContext.findOne(SQLID+"getApplyFromTOrderByHfid", HouseEfficVo.class, houseBaseFid);
		}
		public HouseEfficVo getApplyFromTOrderByRfid(String roomFid) {
			return mybatisDaoContext.findOne(SQLID+"getApplyFromTOrderByRfid", HouseEfficVo.class, roomFid);
		}

		/**
	     *  order库，订单快照表关联订单表      查询首次支付（整租，分租）
	     */
		public HouseEfficVo getFromTOrderByHfid(String houseBaseFid) {
			return mybatisDaoContext.findOne(SQLID+"getFromTOrderByHfid", HouseEfficVo.class, houseBaseFid);
		}
		public HouseEfficVo getFromTOrderRfid(String roomFid) {
			return mybatisDaoContext.findOne(SQLID+"getFromTOrderRfid", HouseEfficVo.class, roomFid);
		}
		
		/**
	     *  order库，订单快照表关联订单表      首次入住（整租，分租）
	     */
		public HouseEfficVo getFirstRealCheckInTime(String houseBaseFid) {
			return mybatisDaoContext.findOne(SQLID+"getFirstRealCheckInTime", HouseEfficVo.class, houseBaseFid);
		}
		public HouseEfficVo getFirstRealCheckInTimeRoom(String roomFid) {
			return mybatisDaoContext.findOne(SQLID+"getFirstRealCheckInTimeRoom", HouseEfficVo.class, roomFid);
		}

		/**
	     * evaluate库， t_evaluate_order表此房子 	  查询首次评价时间（整租，分租）
	     */
		public HouseEfficVo getFromTEvalOrderdByHfid(String houseBaseFid) {
			return mybatisDaoContext.findOne(SQLID+"getFromTEvalOrderdByHfid", HouseEfficVo.class, houseBaseFid);
		}
		public HouseEfficVo getFromTEvalOrderdByRfid(String roomFid) {
			return mybatisDaoContext.findOne(SQLID+"getFromTEvalOrderdByRfid", HouseEfficVo.class, roomFid);
		}

		
}
