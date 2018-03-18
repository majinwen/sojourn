package com.ziroom.minsu.report.customer.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.report.basedata.dao.ConfCityDao;
import com.ziroom.minsu.report.basedata.dto.CityRequest;
import com.ziroom.minsu.report.basedata.entity.ConfCityEntity;
import com.ziroom.minsu.report.common.service.ReportService;
import com.ziroom.minsu.report.customer.dao.LandlordInfoDao;
import com.ziroom.minsu.report.customer.dto.LandlordRequest;
import com.ziroom.minsu.report.customer.vo.UserLandlordInfoVo;



/**
 * <p>用户 LandService</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author lusp on 2017/4/27.
 * @version 1.0
 * @since 1.0
 */
@Service("report.landlordService")
public class LandlordService implements ReportService<UserLandlordInfoVo,LandlordRequest>{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LandlordService.class);
	
	@Resource(name="report.landlordInfoDao")
	private LandlordInfoDao landlordInfoDao;
	
	@Resource(name="report.confCityDao")
	private ConfCityDao confCityDao;
	/**
     * 房东信息详细
     * @author lusp
     * @param landlordRequest
     * @return UserLandlordInfoVo
     */
    public PagingResult<UserLandlordInfoVo> findLandlordItemList(LandlordRequest landlordRequest) {
    	if(Check.NuNObj(landlordRequest)){
			LogUtil.info(LOGGER, " LandlordService findLandlordItemList param:{}", JsonEntityTransform.Object2Json(landlordRequest));
    		return null;
    	}
    	return landlordInfoDao.findLandlordItemList(landlordRequest);
    }
	/* (non-Javadoc)
	 * @see com.ziroom.minsu.report.common.service.ReportService#getPageInfo(com.ziroom.minsu.services.common.dto.PageRequest)
	 */
	@Override
	public PagingResult<UserLandlordInfoVo> getPageInfo(LandlordRequest landlordRequest) {
		if(Check.NuNObj(landlordRequest)){
			LogUtil.info(LOGGER, " LandlordService getPageInfo param:{}", JsonEntityTransform.Object2Json(landlordRequest));
    		return null;
    	}
		PagingResult<UserLandlordInfoVo> result = landlordInfoDao.findLandlordItemList(landlordRequest);
		List<UserLandlordInfoVo> rows = result.getRows();
 	    if(!Check.NuNCollection(rows)){
 	    	//获取所有国家
             List<ConfCityEntity> nations = confCityDao.getNations();
             //获取所有开通城市
             Map<String, Object> paramMap = new HashMap<String, Object>();
             List<ConfCityEntity> cityListInfo = confCityDao.getOpenCity(paramMap);
             for (UserLandlordInfoVo row : rows) {
             	//填充国家名称
                 for (ConfCityEntity temp : nations) {
                 	if(!Check.NuNStr(row.getNationCode())){
                 		if(row.getNationCode().equals(temp.getCode())){
                 			row.setNationName(temp.getShowName());
                 			break;
 						}
                 	}
					}
                 //填充城市名称
                 for (ConfCityEntity temp : cityListInfo) {
                 	if(!Check.NuNStr(row.getCityCode())){
                 		if(row.getCityCode().equals(temp.getCode())){
                 			row.setCityName(temp.getShowName());
                 			break;
 						}
                 	}
					}
                 //填充所在地
                 if(!Check.NuNStr(row.getIdCardNo())){
                 	String cityNo = row.getIdCardNo().substring(0, 4)+"00";
                 	for (ConfCityEntity temp : cityListInfo) {
                     	if(!Check.NuNStr(cityNo)){
                     		if(cityNo.equals(temp.getCode())){
                     			row.setLocationCity(temp.getShowName());
                     			break;
     						}
                     	}
 					}
                 }
 		   }
             
 	    }
 	    result.setRows(rows);
    	return result;
	}
	/* (non-Javadoc)
	 * @see com.ziroom.minsu.report.common.service.ReportService#countDataInfo(com.ziroom.minsu.services.common.dto.PageRequest)
	 */
	@Override
	public Long countDataInfo(LandlordRequest par) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
