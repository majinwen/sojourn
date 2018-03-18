package com.ziroom.minsu.report.board.service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.DateUtil;
import com.ziroom.minsu.report.board.dao.LandlordDataDao;
import com.ziroom.minsu.report.board.dto.LandlordRequest;
import com.ziroom.minsu.report.board.vo.LandlordDataItem;

/**
 * 
 * <p>经营数据房东service</p>
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
@Service("report.landlordDataService")
public class LandlordDataService {

    @Resource(name="report.landlordDataDao")
    private LandlordDataDao landlordDataDao;
	
	/**
	 * 查询大区所属城市数据列表-数据来源于定时任务
	 *
	 * @author liujun
	 * @created 2017年2月15日
	 *
	 * @param landlordRequest
	 * @return
	 * @throws ParseException 
	 */
	public List<LandlordDataItem> findLandlordDataItemList(LandlordRequest landlordRequest) {
		String currentDate = DateUtil.dateFormat(new Date());
		if (Check.NuNStrStrict(landlordRequest.getQueryDate()) || currentDate.equals(landlordRequest.getQueryDate())) {
			return landlordDataDao.findLandlordDataItemList(landlordRequest);
		} else {
			return landlordDataDao.findLandlordDataItemListFromTask(landlordRequest);
		}
	}

}
