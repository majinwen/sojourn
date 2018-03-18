package com.ziroom.minsu.report.board.service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.ziroom.minsu.report.basedata.dao.ConfCityDao;
import org.springframework.stereotype.Service;

import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.DateUtil;
import com.ziroom.minsu.report.board.dao.CityDailyMsgDao;
import com.ziroom.minsu.report.board.entity.CityDailyMsgEntity;
import com.ziroom.minsu.report.basedata.entity.ConfCityEntity;
import com.ziroom.minsu.valenum.common.YesOrNoEnum;

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
@Service("report.cityDailyMsgService")
public class CityDailyMsgService {
    
    @Resource(name="report.cityDailyMsgDao")
    private CityDailyMsgDao cityDailyMsgDao;
    
    @Resource(name="report.confCityDao")
    private ConfCityDao confCityDao;

	/**
	 * 统计城市日信息
	 * 注意:非幂等函数
	 *
	 * @author liujun
	 * @created 2017年2月8日
	 *
	 * @param cityList
	 * @throws ParseException 
	 */
	public void startTask(List<ConfCityEntity> cityList) throws ParseException {
		String lockDate = DateUtil.getDayBeforeCurrentDate();
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
			cityDailyMsgEntity.setExpLandNum((int) cityDailyMsgDao.getExpLandNumByCityCode(cityCode));
			cityDailyMsgEntity.setNonProLandNum((int) cityDailyMsgDao.getNonProLandNumByCityCode(cityCode));
			cityDailyMsgEntity.setProLandNum((int) cityDailyMsgDao.getProLandNumByCityCode(cityCode));
			cityDailyMsgEntity.setImmediateBookTypeNum((int) cityDailyMsgDao.getImmediateBookTypeNumByCityCode(cityCode));
			//增加统计busj 
			/*Map<String, Object> pMap=new HashMap<>();
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
			cityDailyMsgEntity.setInitialSelfUpNum(cityDailyMsgDao.getHouseDayNumByCondition(pMap));*/
			cityDailyMsgEntity.setStatDate(DateUtil.parseDate(lockDate, "yyyy-MM-dd"));
			cityDailyMsgEntity.setIsDel(YesOrNoEnum.NO.getCode());
			cityDailyMsgEntity.setCreateDate(new Date());
			cityDailyMsgDao.insertCityDailyMsg(cityDailyMsgEntity);
		}
	}

}
