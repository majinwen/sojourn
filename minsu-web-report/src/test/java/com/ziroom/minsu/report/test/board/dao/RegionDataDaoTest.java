package com.ziroom.minsu.report.test.board.dao;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.ziroom.minsu.report.basedata.dao.ConfCityDao;
import com.ziroom.minsu.report.board.dto.RegionRequest;
import com.ziroom.minsu.report.board.entity.CityDailyMsgEntity;
import com.ziroom.minsu.report.board.vo.RegionDataItem;
import com.ziroom.minsu.report.board.vo.RegionItem;
import com.ziroom.minsu.report.board.dao.CityDailyMsgDao;
import com.ziroom.minsu.report.board.dao.RegionDataDao;
import org.junit.Test;

import base.BaseTest;

import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.DateUtil;
import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.minsu.report.basedata.entity.ConfCityEntity;
import com.ziroom.minsu.valenum.common.YesOrNoEnum;

/**
 * 
 * <p>目标看板-大区数据</p>
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
public class RegionDataDaoTest extends BaseTest {

	@Resource(name = "report.regionDataDao")
	private RegionDataDao regionDataDao;
	
	@Resource(name = "report.confCityDao")
	private ConfCityDao confCityDao;
	
	@Resource(name = "report.cityDailyMsgDao")
	private CityDailyMsgDao cityDailyMsgDao;

	@Test
	public void findRegionListByNationCodeTest() {
		String nationCode = "100000";
		List<RegionItem> list = regionDataDao.findRegionListByNationCode(nationCode);
		System.err.println(JsonEntityTransform.Object2Json(list));
	}
	
	@Test
	public void findRegionDataItemListTest() {
		RegionRequest regionRequest = new RegionRequest();
		regionRequest.setRegionFid("8a9e988b59810f230159810f240b0000");
		List<RegionDataItem> list = regionDataDao.findRegionDataItemList(regionRequest);
		System.err.println(JsonEntityTransform.Object2Json(list));
	}
	
	@Test
	public void findRegionDataItemListFromTaskTest() {
		RegionRequest regionRequest = new RegionRequest();
		regionRequest.setRegionFid("8a9e988b59810f230159810f240b0000");
		List<RegionDataItem> list = regionDataDao.findRegionDataItemListFromTask(regionRequest);
		System.err.println(JsonEntityTransform.Object2Json(list));
	}
	

	@Test
	public void startTaskTest() throws ParseException {
		StringBuilder sql = new StringBuilder();
		List<String> dateList = getDateBetween("2017-01-01", "2017-02-13");
		List<ConfCityEntity> cityList = confCityDao.getOpenCity(null);
		for (String lockDate : dateList) {
			for (ConfCityEntity city : cityList) {
				String cityCode = city.getCode();
				CityDailyMsgEntity cityDailyMsgEntity = new CityDailyMsgEntity();
				cityDailyMsgEntity.setCityCode(cityCode);
				ConfCityEntity province = confCityDao.getCityByCode(city.getPcode());
				if (!Check.NuNObj(province)) {
					cityDailyMsgEntity.setProvinceCode(province.getCode());
					ConfCityEntity nation = confCityDao.getCityByCode(province.getPcode());
					if (!Check.NuNObj(nation)) {
						cityDailyMsgEntity.setNationCode(nation.getCode());
					}
				}
				cityDailyMsgEntity.setTotalUpNum((int) cityDailyMsgDao.getTotalUpNumByCityCode(cityCode));
				cityDailyMsgEntity.setLockNum((int) cityDailyMsgDao.getLockNumByCityCodeAndLockDate(cityCode, lockDate));
				cityDailyMsgEntity.setIssueNum((int) cityDailyMsgDao.getIssueNumByCityCodeAndStatDate(cityCode, lockDate));
				cityDailyMsgEntity.setInitialPushUpNum((int) cityDailyMsgDao.getInitPushUpNumByCityCodeAndStatDate(cityCode, lockDate));
				cityDailyMsgEntity.setUpNum((int) cityDailyMsgDao.getUpNumByCityCodeAndStatDate(cityCode, lockDate));
				cityDailyMsgEntity.setDownNum((int) cityDailyMsgDao.getDownNumByCityCodeAndStatDate(cityCode, lockDate));
				cityDailyMsgEntity.setFinalUpNum((int) cityDailyMsgDao.getFinalUpNumByCityCodeAndStatDate(cityCode, lockDate));
				cityDailyMsgEntity.setFinalDownNum((int) cityDailyMsgDao.getFinalDownNumByCityCodeAndStatDate(cityCode, lockDate));
				cityDailyMsgEntity.setBookOrderNum((int) cityDailyMsgDao.getBookOrderNumByCityCodeAndStatDate(cityCode, lockDate));
				cityDailyMsgEntity.setPayOrderNum((int) cityDailyMsgDao.getPayOrderNumByCityCodeAndStatDate(cityCode, lockDate));
				cityDailyMsgEntity.setRoomNightNum((int) cityDailyMsgDao.getRoomNightNumByCityCodeAndStatDate(cityCode, lockDate));
				cityDailyMsgEntity.setConsultNum((int) cityDailyMsgDao.getConsultNumByCityCodeAndStatDate(cityCode, lockDate));
				cityDailyMsgEntity.setStatDate(DateUtil.parseDate(lockDate, "yyyy-MM-dd"));
				cityDailyMsgEntity.setIsDel(YesOrNoEnum.NO.getCode());
				cityDailyMsgEntity.setCreateDate(new Date());
				assembleSql(sql, cityDailyMsgEntity);
			}
		}
		String fileName = "d:\\t_city_daily_msg.sql";
		writeTxtByAppend(fileName, sql.toString());
	}
	
	/**
	 * 获取两个日期之间的所有日期(包括给定的两个日期)
	 * @param minDate eg:2017-01-01
	 * @param maxDate eg:2017-05-01
	 * @return
	 * @throws ParseException
	 */
	private static List<String> getDateBetween(String minDate, String maxDate) throws ParseException {
		List<String> result = new ArrayList<String>();
		Calendar min = Calendar.getInstance();
		Calendar max = Calendar.getInstance();
		min.setTime(DateUtil.parseDate(minDate, "yyyy-MM-dd"));
		max.setTime(DateUtil.parseDate(maxDate, "yyyy-MM-dd"));
		if (min.after(max)) {
			Calendar temp = min;
			min = max;
			max = temp;
		}
		max.add(Calendar.DATE, 1);
		Calendar curr = min;
		while (curr.before(max)) {
			result.add(DateUtil.dateFormat(curr.getTime(), "yyyy-MM-dd"));
			curr.add(Calendar.DATE, 1);
		}
		return result;
	}
	
	private static void assembleSql(StringBuilder sql, CityDailyMsgEntity cityDailyMsgEntity) {
		sql.append("insert ignore into minsu_report_db.t_city_daily_msg");
		sql.append(" (");
		sql.append("nation_code, ");
		sql.append("province_code, ");
		sql.append("city_code, ");
		sql.append("total_up_num, ");
		sql.append("lock_num, ");
		sql.append("issue_num, ");
		sql.append("initial_push_up_num, ");
		sql.append("up_num, ");
		sql.append("down_num, ");
		sql.append("final_up_num, ");
		sql.append("final_down_num, ");
		sql.append("book_order_num, ");
		sql.append("pay_order_num, ");
		sql.append("room_night_num, ");
		sql.append("consult_num, ");
		sql.append("stat_date");
		sql.append(")");
		sql.append(" values (");
		sql.append(ifNullStr(cityDailyMsgEntity.getNationCode()));
		sql.append(", ");
		sql.append(ifNullStr(cityDailyMsgEntity.getProvinceCode()));
		sql.append(", ");
		sql.append(ifNullStr(cityDailyMsgEntity.getCityCode()));
		sql.append(", ");
		sql.append(ifNullObj(cityDailyMsgEntity.getTotalUpNum()));
		sql.append(", ");
		sql.append(ifNullObj(cityDailyMsgEntity.getLockNum()));
		sql.append(", ");
		sql.append(ifNullObj(cityDailyMsgEntity.getIssueNum()));
		sql.append(", ");
		sql.append(ifNullObj(cityDailyMsgEntity.getInitialPushUpNum()));
		sql.append(", ");
		sql.append(ifNullObj(cityDailyMsgEntity.getUpNum()));
		sql.append(", ");
		sql.append(ifNullObj(cityDailyMsgEntity.getDownNum()));
		sql.append(", ");
		sql.append(ifNullObj(cityDailyMsgEntity.getFinalUpNum()));
		sql.append(", ");
		sql.append(ifNullObj(cityDailyMsgEntity.getFinalDownNum()));
		sql.append(", ");
		sql.append(ifNullObj(cityDailyMsgEntity.getBookOrderNum()));
		sql.append(", ");
		sql.append(ifNullObj(cityDailyMsgEntity.getPayOrderNum()));
		sql.append(", ");
		sql.append(ifNullObj(cityDailyMsgEntity.getRoomNightNum()));
		sql.append(", ");
		sql.append(ifNullObj(cityDailyMsgEntity.getConsultNum()));
		sql.append(", ");
		sql.append("'");
		sql.append(DateUtil.dateFormat(cityDailyMsgEntity.getStatDate()));
		sql.append("'");
		sql.append(")");
		sql.append(";\n");
	}

	private static String ifNullStr(String str) {
		return Check.NuNStr(str) ? "" : str;
	}

	private static Integer ifNullObj(Integer obj) {
		return Check.NuNObj(obj) ? 0 : obj;
	}
	
	/**
	 * 追加文件
	 *
	 * @author liujun
	 * @created 2016年9月24日
	 *
	 * @param fileName 文件名称
	 * @param content 文件内容
	 */
	private static void writeTxtByAppend(String fileName, String content) {
		BufferedWriter out = null;     
		try {
			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName, true)));     
			out.write(content);
			out.flush();
		} catch (Exception e) {     
			e.printStackTrace();     
		} finally {     
			try {     
				if(out != null){  
					out.close();     
				}  
			} catch (IOException e) {     
				e.printStackTrace();     
			}     
		}     
	}
	
	private static void updateSql(StringBuilder sql, CityDailyMsgEntity cityDailyMsgEntity){
		sql.append("UPDATE `minsu_report_db`.`t_city_daily_msg` SET");
		sql.append(" exp_land_num=").append(cityDailyMsgEntity.getExpLandNum()).append(" , ");
		sql.append("non_pro_land_num=").append(cityDailyMsgEntity.getNonProLandNum()).append(" , ");
		sql.append("pro_land_num=").append(cityDailyMsgEntity.getProLandNum()).append(" , ");
		sql.append("push_issue_num=").append(cityDailyMsgEntity.getPushIssueNum()).append(" , ");
		sql.append("self_issue_num=").append(cityDailyMsgEntity.getSelfIssueNum()).append(" , ");
		sql.append("initial_self_up_num=").append(cityDailyMsgEntity.getInitialSelfUpNum());
		sql.append(" where city_code='").append(cityDailyMsgEntity.getCityCode()).append("' ");
		sql.append("and stat_date='").append(DateUtil.dateFormat(cityDailyMsgEntity.getStatDate())).append("';\n");
	}
	
	@Test
	public void startUpTaskTest() throws ParseException {
		StringBuilder sql = new StringBuilder();
		List<String> dateList = getDateBetween("2017-02-01", "2017-02-09");
		List<ConfCityEntity> cityList = confCityDao.getOpenCity(null);
		for (String lockDate : dateList) {
			for (ConfCityEntity city : cityList) {
				String cityCode = city.getCode();
				CityDailyMsgEntity cityDailyMsgEntity = new CityDailyMsgEntity();
				cityDailyMsgEntity.setCityCode(cityCode);
				ConfCityEntity province = confCityDao.getCityByCode(city.getPcode());
				if (!Check.NuNObj(province)) {
					cityDailyMsgEntity.setProvinceCode(province.getCode());
					ConfCityEntity nation = confCityDao.getCityByCode(province.getPcode());
					if (!Check.NuNObj(nation)) {
						cityDailyMsgEntity.setNationCode(nation.getCode());
					}
				}
				cityDailyMsgEntity.setExpLandNum((int) cityDailyMsgDao.getExpLandNumByCityCode(cityCode));
				cityDailyMsgEntity.setNonProLandNum((int) cityDailyMsgDao.getNonProLandNumByCityCode(cityCode));
				cityDailyMsgEntity.setProLandNum((int) cityDailyMsgDao.getProLandNumByCityCode(cityCode));
				cityDailyMsgEntity.setStatDate(DateUtil.parseDate(lockDate, "yyyy-MM-dd"));
				//增加统计busj 
				Map<String, Object> pMap=new HashMap<>();
				pMap.put("statDate", lockDate);
				pMap.put("cityCode", cityCode);
				pMap.put("houseChannel", 3);
				pMap.put("toStatus", 11);
				//日地推发布房源量
				cityDailyMsgEntity.setPushIssueNum(cityDailyMsgDao.getHouseDayNumByCondition(pMap));
				//日自主发布量
				pMap.put("channel", 2);
				cityDailyMsgEntity.setSelfIssueNum(cityDailyMsgDao.getHouseDayNumByCondition(pMap));
				//自主上架量
				pMap.put("toStatus", 40);
				cityDailyMsgEntity.setInitialSelfUpNum(cityDailyMsgDao.getHouseDayNumByCondition(pMap));
				updateSql(sql, cityDailyMsgEntity);
			}
		}
		String fileName = "f:\\t_city_daily_update_msg.sql";
		writeTxtByAppend(fileName, sql.toString());
	}
}