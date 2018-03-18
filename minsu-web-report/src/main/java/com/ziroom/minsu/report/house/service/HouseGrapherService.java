package com.ziroom.minsu.report.house.service;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.report.basedata.dao.ConfCityDao;
import com.ziroom.minsu.report.basedata.dto.NationRegionCityRequest;
import com.ziroom.minsu.report.basedata.vo.NationRegionCityVo;
import com.ziroom.minsu.report.common.service.ReportService;
import com.ziroom.minsu.report.common.util.NationRegionCityUtil;
import com.ziroom.minsu.report.customer.vo.LandlordStaticVo;
import com.ziroom.minsu.report.house.dao.HousePhotoGrapherDao;
import com.ziroom.minsu.report.house.dto.HouseGrapherRequest;
import com.ziroom.minsu.report.house.vo.HouseGrapherVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>房源摄影报表service</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author wangwt on 2017/5/5.
 * @version 1.0
 * @since 1.0
 */
@Service("report.houseGrapherService")
public class HouseGrapherService implements ReportService <HouseGrapherVo,HouseGrapherRequest>{

    private static final Logger LOGGER = LoggerFactory.getLogger(HouseGrapherService.class);
 
    @Resource(name="report.housePhotoGrapherDao")
    private HousePhotoGrapherDao housePhotoGrapherDao;

	@Resource(name = "report.confCityDao")
	private ConfCityDao confCityDao;

	@Override
	public Long countDataInfo(HouseGrapherRequest par) {
		return null;
	}


	/**
	 * 
	 * @author zl
	 * @created 2017/8/15 10:16
	 * @param 
	 * @return 
	 */
	@Override
	public PagingResult<HouseGrapherVo> getPageInfo(HouseGrapherRequest houseRequest) {
		if(Check.NuNObj(houseRequest)){
			LogUtil.info(LOGGER, "HouseOrderLifeCycleService param :{}", JsonEntityTransform.Object2Json(houseRequest));
			return null;
		}
		if(Check.NuNObj(houseRequest.getRentWay())){
			LogUtil.info(LOGGER, "HouseOrderLifeCycleService param rentWay:{}", houseRequest.getRentWay());
			return null;
		}

		NationRegionCityRequest cityRequest = new NationRegionCityRequest();
		cityRequest.setCityCode(houseRequest.getCityCode());
		cityRequest.setNationCode(houseRequest.getNationCode());
		cityRequest.setRegionFid(houseRequest.getRegionFid());

		//获取当前的城市列表
		List<NationRegionCityVo> nationRegionCityList = confCityDao.getNationRegionCity(cityRequest);
		if(Check.NuNCollection(nationRegionCityList)){
			return new PagingResult<>();
		}
		Map<String, NationRegionCityVo> map = new HashMap<>();
		List<String> cityList = new ArrayList<>();
		NationRegionCityUtil.fillNationRegionCity(nationRegionCityList, map, cityList);
		PagingResult<HouseGrapherVo> pagingResult = housePhotoGrapherDao.getHouseGrapherInfo(houseRequest);

		if (!Check.NuNCollection(pagingResult.getRows())){
			for (HouseGrapherVo vo : pagingResult.getRows()) {
				String cityCode = vo.getCityCode();
				if (!map.containsKey(cityCode)){
					continue;
				}
				NationRegionCityVo regionCityVo = map.get(cityCode);

				vo.setNationName(regionCityVo.getNationName());
				vo.setRegionName(regionCityVo.getRegionName());
				vo.setCityName(regionCityVo.getCityName());
			}
		}
		return pagingResult;
		
	}

	
}
