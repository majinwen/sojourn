/**
 * @FileName: houseAddressService.java
 * @Package com.ziroom.minsu.report.house.service
 * 
 * @author baiwei
 * @created 2017年5月2日 下午8:27:50
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.report.house.service;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.ziroom.minsu.report.basedata.dao.ConfCityDao;
import com.ziroom.minsu.report.basedata.vo.NationRegionCityVo;
import com.ziroom.minsu.report.common.service.ReportService;
import com.ziroom.minsu.report.common.util.NationRegionCityUtil;
import com.ziroom.minsu.report.house.dao.HouseAddressDao;
import com.ziroom.minsu.report.house.dto.HouseAddressRequest;
import com.ziroom.minsu.report.house.vo.HouseAddressVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * <p>TODO</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author baiwei
 * @since 1.0
 * @version 1.0
 */
@Service("report.houseAddressService")
public class HouseAddressService implements ReportService <HouseAddressVo,HouseAddressRequest>{
	
    private static final Logger LOGGER = LoggerFactory.getLogger(HouseAddressService.class);
    
    @Resource(name = "report.confCityDao")
    private ConfCityDao confCityDao;
    
    @Resource(name = "report.houseAddressDao")
    private HouseAddressDao houseAddressDao;
    
    /**
     * 
     * 分页查询
     *
     * @author baiwei
     * @created 2017年5月2日 下午8:47:34
     *
     * @param houseAddressRequest
     * @return
     */
    public PagingResult<HouseAddressVo> getHouseAddressByPage(HouseAddressRequest houseAddressRequest){
    	//获取当前的城市列表
    	List<NationRegionCityVo> nationRegionCityList = confCityDao.getNationRegionCity(houseAddressRequest);
    	if(Check.NuNCollection(nationRegionCityList)){
			PagingResult<HouseAddressVo> pagingResult = new PagingResult<>();
			pagingResult.setRows(Collections.emptyList());
    		return pagingResult;
    	}
    	Map<String, NationRegionCityVo> map = new HashMap<>();
    	List<String> cityList = new ArrayList<>();
    	NationRegionCityUtil.fillNationRegionCity(nationRegionCityList, map, cityList);
    	//填充城市code
    	if(!houseAddressRequest.checkEmpty()){
    		//当前参数城市相关配置不为空
    		houseAddressRequest.setCityList(cityList);
    	}
    	PagingResult<HouseAddressVo> pagingResult = houseAddressDao.getHouseAddressByPage(houseAddressRequest);
		if (!Check.NuNCollection(pagingResult.getRows())){
			for (HouseAddressVo vo : pagingResult.getRows()) {
				String cityCode = vo.getCityCode();
				if (!map.containsKey(cityCode)){
					continue;
				}
				fillContact(map, vo, cityCode);
			}
		}
		return pagingResult;
    }
    
    /**
     * 
     * 不分页查询
     *
     * @author baiwei
     * @created 2017年5月4日 上午10:36:55
     *
     * @param houseAddressRequest
     * @return
     */
    public List<HouseAddressVo> getHouseAddressList(HouseAddressRequest houseAddressRequest){
    	//获取当前的城市列表
    	List<NationRegionCityVo> nationRegionCityList = confCityDao.getNationRegionCity(houseAddressRequest);
    	if(Check.NuNCollection(nationRegionCityList)){
    		return new ArrayList<>();
    	}
    	Map<String, NationRegionCityVo> map = new HashMap<>();
    	List<String> cityList = new ArrayList<>();
    	NationRegionCityUtil.fillNationRegionCity(nationRegionCityList, map, cityList);
    	//填充城市code
    	if(!houseAddressRequest.checkEmpty()){
    		//当前参数城市相关配置不为空
    		houseAddressRequest.setCityList(cityList);
    	}
    	List<HouseAddressVo> houseAddressList = houseAddressDao.getHouseAddressList(houseAddressRequest);
    	if (!Check.NuNCollection(houseAddressList)){
            for (HouseAddressVo vo : houseAddressList) {
                String cityCode = vo.getCityCode();
                if (!map.containsKey(cityCode)){
                    continue;
                }
                fillContact(map, vo, cityCode);
            }
        }
    	return houseAddressList;
    }
    
	/**
	 * 填充当前的数据信息
	 *
	 * @author baiwei
	 * @created 2017年5月3日 上午10:12:08
	 *
	 * @param map
	 * @param vo
	 * @param cityCode
	 */
	private void fillContact(Map<String, NationRegionCityVo> map,HouseAddressVo vo, String cityCode) {
		NationRegionCityVo regionCityVo = map.get(cityCode);
		vo.setRegionName(regionCityVo.getRegionName());
		vo.setNationName(regionCityVo.getNationName());
		vo.setCityName(regionCityVo.getCityName());
	}

	/* (non-Javadoc)
	 * @see com.ziroom.minsu.report.common.service.ReportService#getPageInfo(com.ziroom.minsu.services.common.dto.PageRequest)
	 */
	@Override
	public PagingResult<HouseAddressVo> getPageInfo(HouseAddressRequest par) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.ziroom.minsu.report.common.service.ReportService#countDataInfo(com.ziroom.minsu.services.common.dto.PageRequest)
	 */
	@Override
	public Long countDataInfo(HouseAddressRequest par) {
		// TODO Auto-generated method stub
		return null;
	}
    
}
