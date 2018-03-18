/**
 * @FileName: LandlordStaticService.java
 * @Package com.ziroom.minsu.report.customer.service
 * 
 * @author zl
 * @created 2017年5月18日 上午10:26:20
 * 
 * Copyright 2016-2025 ziroom
 */
package com.ziroom.minsu.report.customer.service;

import java.util.ArrayList;
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
import com.ziroom.minsu.report.basedata.dto.NationRegionCityRequest;
import com.ziroom.minsu.report.basedata.vo.NationRegionCityVo;
import com.ziroom.minsu.report.common.service.ReportService;
import com.ziroom.minsu.report.common.util.NationRegionCityUtil;
import com.ziroom.minsu.report.customer.dao.LandlordStaticDao;
import com.ziroom.minsu.report.customer.dto.LandlordRequest;
import com.ziroom.minsu.report.customer.vo.LandlordStaticVo;
import com.ziroom.minsu.report.house.vo.HouseAddressVo;

/**
 * <p>TODO</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author zl
 * @since 1.0
 * @version 1.0
 */
@Service("report.landlordStaticService")
public class LandlordStaticService  implements ReportService<LandlordStaticVo, LandlordRequest> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LandlordStaticService.class);
 
	@Resource(name="report.landlordStaticDao")
	private LandlordStaticDao landlordStaticDao;
	
    @Resource(name = "report.confCityDao")
    private ConfCityDao confCityDao;
	
	
	@Override
	public PagingResult<LandlordStaticVo> getPageInfo(LandlordRequest par) {
		LogUtil.info(LOGGER, "房东粘性报表参数，parameter={}", JsonEntityTransform.Object2Json(par));
		try {
			
			NationRegionCityRequest cityRequest = new NationRegionCityRequest();
			cityRequest.setCityCode(par.getCityCode());
			cityRequest.setNationCode(par.getNationCode());
			cityRequest.setRegionFid(par.getRegionCode());
			
			//获取当前的城市列表
	    	List<NationRegionCityVo> nationRegionCityList = confCityDao.getNationRegionCity(cityRequest);
	    	if(Check.NuNCollection(nationRegionCityList)){
	    		return new PagingResult<>();
	    	}
	    	Map<String, NationRegionCityVo> map = new HashMap<>();
	    	List<String> cityList = new ArrayList<>();
	    	NationRegionCityUtil.fillNationRegionCity(nationRegionCityList, map, cityList);
			
	    	
	    	PagingResult<LandlordStaticVo> pagingResult = landlordStaticDao.getList(par);
			
			if (!Check.NuNCollection(pagingResult.getRows())){
	            for (LandlordStaticVo vo : pagingResult.getRows()) {
	                String cityCode = vo.getCityCode();
	                if (!map.containsKey(cityCode)){
	                    continue;
	                }
	                NationRegionCityVo regionCityVo = map.get(cityCode);
	                
	                vo.setCountroy(regionCityVo.getNationName());
	                vo.setRegion(regionCityVo.getRegionName());
	                vo.setCity(regionCityVo.getCityName());
	            }
	        }
			return pagingResult;
		} catch (Exception e) {
			LogUtil.error(LOGGER, "查询房东粘性报表信息异常，e={}", e);
		}
		return new PagingResult<LandlordStaticVo>();
	}
 
	@Override
	public Long countDataInfo(LandlordRequest par) {
		// TODO Auto-generated method stub
		return null;
	}

}
