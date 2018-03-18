package com.ziroom.minsu.report.board.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.ziroom.minsu.report.board.entity.CityDailyMsgEntity;

/**
 * 
 * <p>城市日可出租天数记录dao</p>
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
@Repository("report.cityDailyMsgDao")
public class CityDailyMsgDao {

	private String SQLID = "report.cityDailyMsgDao.";

	@Autowired
	@Qualifier("minsuReport.report.MybatisDaoContext")
	private MybatisDaoContext mybatisDaoContext;
	
	/**
	 *  跨库使用
	 */
	@Autowired
	@Qualifier("minsuReport.all.MybatisDaoContext")
	private MybatisDaoContext unionDaoContext;

	/**
	 * 
	 * 插入城市日可出租天数记录
	 *
	 * @author liujun
	 * @created 2017年2月8日
	 *
	 * @param cityRentalMsgEntity
	 * @return
	 */
	public int insertCityDailyMsg(CityDailyMsgEntity cityDailyMsgEntity) {
		return mybatisDaoContext.save(SQLID + "insertCityDailyMsg", cityDailyMsgEntity);
	}

	/**
	 * 截至查询日当天平台上已有的上架房源数量
	 *
	 * @author liujun
	 * @created 2017年2月8日
	 *
	 * @param code
	 * @return
	 */
	public long getTotalUpNumByCityCode(String cityCode) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("cityCode", cityCode);
		return unionDaoContext.countBySlave(SQLID + "getTotalUpNum", paramMap);
	}

	/**
	 * 查询日该城市不可出租房源的数量
	 *
	 * @author liujun
	 * @created 2017年2月8日
	 *
	 * @param code
	 * @param lockDate
	 * @return
	 */
	public long getLockNumByCityCodeAndLockDate(String cityCode, String lockDate) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("cityCode", cityCode);
		paramMap.put("lockDate", lockDate);
		return unionDaoContext.countBySlave(SQLID + "getLockNum", paramMap);
	}
	
	/**
	 * 查询日当天首次发布的房源数量
	 *
	 * @author liujun
	 * @created 2017年2月8日
	 *
	 * @param code
	 * @param statDate
	 * @return
	 */
	public long getIssueNumByCityCodeAndStatDate(String cityCode, String statDate) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("cityCode", cityCode);
		paramMap.put("statDate", statDate);
		return unionDaoContext.countBySlave(SQLID + "getIssueNum", paramMap);
	}
	
	/**
	 * 
	 * 查询日当天首次上架的地推房源数量，整租数量按照套计算，合租数量按照间计算
	 *
	 * @author liujun
	 * @created 2017年2月13日
	 *
	 * @param cityCode
	 * @param statDate
	 * @return
	 */
	public long getInitPushUpNumByCityCodeAndStatDate(String cityCode, String statDate) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("cityCode", cityCode);
		paramMap.put("statDate", statDate);
		return unionDaoContext.countBySlave(SQLID + "getInitPushUpNum", paramMap);
	}
	
	/**
	 * 
	 * 查询日当天上架过的房源(多次上架算一次)数量
	 *
	 * @author liujun
	 * @created 2017年2月13日
	 *
	 * @param cityCode
	 * @param statDate
	 * @return
	 */
	public long getUpNumByCityCodeAndStatDate(String cityCode, String statDate) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("cityCode", cityCode);
		paramMap.put("statDate", statDate);
		return unionDaoContext.countBySlave(SQLID + "getUpNum", paramMap);
	}
	
	/**
	 * 
	 * 查询日当天下架过的房源(多次下架算一次)数量
	 *
	 * @author liujun
	 * @created 2017年2月13日
	 *
	 * @param cityCode
	 * @param statDate
	 * @return
	 */
	public long getDownNumByCityCodeAndStatDate(String cityCode, String statDate) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("cityCode", cityCode);
		paramMap.put("statDate", statDate);
		return unionDaoContext.countBySlave(SQLID + "getDownNum", paramMap);
	}
	
	/**
	 * 
	 * 查询日当天最后操作为上架的房源数量
	 *
	 * @author liujun
	 * @created 2017年2月13日
	 *
	 * @param cityCode
	 * @param statDate
	 * @return
	 */
	public long getFinalUpNumByCityCodeAndStatDate(String cityCode, String statDate) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("cityCode", cityCode);
		paramMap.put("statDate", statDate);
		return unionDaoContext.countBySlave(SQLID + "getFinalUpNum", paramMap);
	}
	
	/**
	 * 
	 * 查询日当天最后操作为下架的房源数量
	 *
	 * @author liujun
	 * @created 2017年2月13日
	 *
	 * @param cityCode
	 * @param statDate
	 * @return
	 */
	public long getFinalDownNumByCityCodeAndStatDate(String cityCode, String statDate) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("cityCode", cityCode);
		paramMap.put("statDate", statDate);
		return unionDaoContext.countBySlave(SQLID + "getFinalDownNum", paramMap);
	}
	
	/**
	 * 
	 * 查询日当天所有生成订单的数量
	 *
	 * @author liujun
	 * @created 2017年2月13日
	 *
	 * @param cityCode
	 * @param statDate
	 * @return
	 */
	public long getBookOrderNumByCityCodeAndStatDate(String cityCode, String statDate) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("cityCode", cityCode);
		paramMap.put("statDate", statDate);
		return unionDaoContext.countBySlave(SQLID + "getBookOrderNum", paramMap);
	}
	
	/**
	 * 
	 * 查询日当天已支付订单的数量
	 *
	 * @author liujun
	 * @created 2017年2月13日
	 *
	 * @param cityCode
	 * @param statDate
	 * @return
	 */
	public long getPayOrderNumByCityCodeAndStatDate(String cityCode, String statDate) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("cityCode", cityCode);
		paramMap.put("statDate", statDate);
		return unionDaoContext.countBySlave(SQLID + "getPayOrderNum", paramMap);
	}
	
	/**
	 * 
	 * 查询日当天已支付订单的数量
	 *
	 * @author liujun
	 * @created 2017年2月13日
	 *
	 * @param cityCode
	 * @param statDate
	 * @return
	 */
	public long getRoomNightNumByCityCodeAndStatDate(String cityCode, String statDate) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("cityCode", cityCode);
		paramMap.put("statDate", statDate);
		return unionDaoContext.countBySlave(SQLID + "getRoomNightNum", paramMap);
	}
	
	/**
	 * 
	 * 同一天,同一房东,同一房客,同一房源(间)多次咨询算一条
	 *
	 * @author liujun
	 * @created 2017年2月13日
	 *
	 * @param cityCode
	 * @param statDate
	 * @return
	 */
	public long getConsultNumByCityCodeAndStatDate(String cityCode, String statDate) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("cityCode", cityCode);
		paramMap.put("statDate", statDate);
		return unionDaoContext.countBySlave(SQLID + "getConsultNum", paramMap);
	}
	
	/**
	 * 
	 * 查询城市日体验型房东数量
	 *
	 * @author liujun
	 * @created 2017年2月15日
	 *
	 * @param cityCode
	 * @param statDate
	 * @return
	 */
	public long getExpLandNumByCityCode(String cityCode) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("cityCode", cityCode);
		return unionDaoContext.countBySlave(SQLID + "getExpLandNum", paramMap);
	}
	
	/**
	 * 
	 * 查询城市日非专业型房东数量
	 *
	 * @author liujun
	 * @created 2017年2月15日
	 *
	 * @param cityCode
	 * @param statDate
	 * @return
	 */
	public long getNonProLandNumByCityCode(String cityCode) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("cityCode", cityCode);
		return unionDaoContext.countBySlave(SQLID + "getNonProLandNum", paramMap);
	}
	
	/**
	 * 
	 * 查询城市日专业型房东数量
	 *
	 * @author liujun
	 * @created 2017年2月15日
	 *
	 * @param cityCode
	 * @param statDate
	 * @return
	 */
	public long getProLandNumByCityCode(String cityCode) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("cityCode", cityCode);
		return unionDaoContext.countBySlave(SQLID + "getProLandNum", paramMap);
	}
	
	/**
	 * 
	 * 条件查询房源数量
	 *
	 * @author bushujie
	 * @created 2017年2月17日 上午9:55:46
	 *
	 * @param paramMap
	 * @return
	 */
	public int getHouseDayNumByCondition(Map<String, Object> paramMap){
		return unionDaoContext.findOne(SQLID+"getHouseDayNumByCondition", Integer.class, paramMap);
	}

	/**
	 * 查询立即预定的房源数
	 *
	 * @author ls
	 * @created 2017年9月13日 下午2:58:27
	 *
	 * @param cityCode
	 * @return
	 */
	public long getImmediateBookTypeNumByCityCode(String cityCode) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("cityCode", cityCode);
		return unionDaoContext.countBySlave(SQLID + "getImmediateBookTypeNumByCityCode", paramMap);
	}
}
