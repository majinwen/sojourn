package com.ziroom.minsu.report.board.service;

import com.asura.framework.base.util.BigDecimalUtil;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.DateUtil;
import com.ziroom.minsu.report.board.dao.EmpTargetDao;
import com.ziroom.minsu.report.board.dto.EmpStatsRequest;
import com.ziroom.minsu.report.board.entity.EmpTargetEntity;
import com.ziroom.minsu.report.board.vo.EmpTargetStatItem;
import com.ziroom.minsu.report.common.util.DateUtils;
import com.ziroom.minsu.report.house.dao.HouseDao;
import com.ziroom.minsu.valenum.house.HouseStatusEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.Date;

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
@Service("report.empDataService")
public class EmpDataService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmpDataService.class);

    @Resource(name="report.houseDao")
    private HouseDao houseDao;

    @Resource(name="report.empTargetDao")
    private EmpTargetDao empTargetDao;

	/**
	 *
	 * @author jixd
	 * @created 2017年01月17日 09:23:33
	 * @param
	 * @return
	 */
	public EmpTargetStatItem getEmpStatsHouseNum(EmpStatsRequest request, EmpTargetStatItem statItem) throws ParseException {
		request.setHouseStatus(HouseStatusEnum.SJ.getCode());

		int onlineNum = (int) houseDao.countHousePushNum(request);
		request.setHouseStatus(HouseStatusEnum.YFB.getCode());
		int pubNum = (int) houseDao.countHousePushNum(request);

        /*if (onlineNum == 0){
            onlineNum = 20;
        }
        if (pubNum == 0){
            pubNum = 40;
        }*/
		statItem.setHouseOnlineNum(onlineNum);
		statItem.setHousePubNum(pubNum);

		double currRate = 0.0;
		if (statItem.getTargetHouseNum() != 0 && statItem.getTargetHouseNum() != null){
			currRate = BigDecimalUtil.div((double) onlineNum, (double) statItem.getTargetHouseNum(), 4);
			statItem.setAchievingRate(BigDecimalUtil.round(currRate*100,2));
		}

		//上个月的比率
        Date lastMonthDate = DateUtils.getLastMonth(DateUtil.parseDate(request.getStartTime(),"yyyy-MM-dd"));
        String lastFirstDay = DateUtil.dateFormat(DateUtils.getMonthFirstDay(lastMonthDate),"yyyy-MM-dd");
        String lastLastDay = DateUtil.dateFormat(DateUtils.getMonthLastDay(lastMonthDate),"yyyy-MM-dd");
        request.setStartTime(lastFirstDay);
        request.setEndTime(lastLastDay);
        request.setHouseStatus(HouseStatusEnum.SJ.getCode());
        int lastOnlineNum = (int)houseDao.countHousePushNum(request);

        /*if (lastOnlineNum == 0){
            lastOnlineNum = 80;
        }*/
        Date lastFirstDate = DateUtil.parseDate(lastFirstDay, "yyyy-MM-dd");
        String strDate = DateUtil.dateFormat(lastFirstDate, "yyyy-MM");
        EmpTargetEntity empTargetEntity = empTargetDao.findEmpTargetByMcode(request.getEmpCode(),strDate);
		if (Check.NuNObj(empTargetEntity)){
			statItem.setRelativeRate(0.0);
		}else{
			Integer targetHouseNum = empTargetEntity.getTargetHouseNum();
			double achievingRate = BigDecimalUtil.div((double) lastOnlineNum, (double) targetHouseNum, 4);
			double relaRate =0d;
		    if (achievingRate==0) {
		    	relaRate=0d;
		    } else {
				relaRate=BigDecimalUtil.div((achievingRate - currRate), achievingRate, 4);
			}
			statItem.setRelativeRate(BigDecimalUtil.round(relaRate*100,2));
		}
		return statItem;
	}

}
