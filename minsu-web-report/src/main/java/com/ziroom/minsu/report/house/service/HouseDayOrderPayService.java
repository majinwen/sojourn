package com.ziroom.minsu.report.house.service;

import com.asura.framework.base.util.Check;
import com.ziroom.minsu.report.house.entity.HouseDayPayOrderEntity;
import com.ziroom.minsu.report.house.dao.HouseDayPayOrderDao;
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
@Service("report.houseDayOrderPayService")
public class HouseDayOrderPayService{

    private static final Logger LOGGER = LoggerFactory.getLogger(HouseDayOrderPayService.class);

    @Resource(name="report.houseDayPayOrderDao")
    private HouseDayPayOrderDao houseDayPayOrderDao;

	
    /**
     * 新增数据
     * @author liyingjie
     * @param hpay
     * @return
     */
	public int insertHouseDayPayOrderNum(HouseDayPayOrderEntity hpay) {
		if(Check.NuNObj(hpay)){
			return -1;
		}
		return houseDayPayOrderDao.insertHouseDayPayOrderNum(hpay);
	}

	
	
	

}
