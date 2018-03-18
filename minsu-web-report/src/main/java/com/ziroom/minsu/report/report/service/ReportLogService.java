package com.ziroom.minsu.report.report.service;

import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.report.report.entity.ReportLogEntity;
import com.ziroom.minsu.report.report.dao.ReportLogDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>城市 ConfCityService</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/8/6.
 * @version 1.0
 * @since 1.0
 */
@Service("report.reportLogService")
public class ReportLogService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReportLogService.class);

    @Resource(name="report.reportLogDao")
    private ReportLogDao reportLogDao;

	
    /**
     * 插入日志记录
     * @author liyingjie
     * @param hpay
     */
	public int insertHouseDayPayOrderNum(ReportLogEntity hpay) {
		if(Check.NuNObj(hpay)){
			LogUtil.info(LOGGER, "param:{}", JsonEntityTransform.Object2Json(hpay));
			return -1;
		}
		return reportLogDao.insertReportLog(hpay);
	}

	
	
	

}
