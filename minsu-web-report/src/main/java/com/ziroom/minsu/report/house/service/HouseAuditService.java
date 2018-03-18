/**
 * @FileName: HouseAuditService.java
 * @Package com.ziroom.minsu.report.house.service
 * 
 * @author baiwei
 * @created 2017年5月4日 下午5:52:05
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.report.house.service;

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
import com.ziroom.minsu.report.basedata.dao.ConfCityDao;
import com.ziroom.minsu.report.basedata.valenum.HouseAuditCauseEnum;
import com.ziroom.minsu.report.basedata.vo.NationRegionCityVo;
import com.ziroom.minsu.report.common.service.ReportService;
import com.ziroom.minsu.report.common.util.NationRegionCityUtil;
import com.ziroom.minsu.report.house.dao.HouseAuditDao;
import com.ziroom.minsu.report.house.dto.HouseAuditRequest;
import com.ziroom.minsu.report.house.vo.HouseAuditVoNew;


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
@Service("report.houseAuditService")
public class HouseAuditService implements ReportService <HouseAuditVoNew,HouseAuditRequest> {
	private static final Logger LOGGER = LoggerFactory.getLogger(HouseAddressService.class);

	@Resource(name = "report.confCityDao")
    private ConfCityDao confCityDao;
	
	@Resource(name = "report.houseAuditDao")
    private HouseAuditDao houseAuditDao;
	/**
	 * 分页查询
	 *
	 * @author baiwei
	 * @created 2017年5月4日 下午5:53:32
	 *
	 * @param houseAuditRequest
	 * @return
	 */
	public PagingResult<HouseAuditVoNew> getHouseAuditByPage(HouseAuditRequest houseAuditRequest) {
		//获取当前的城市列表
    	List<NationRegionCityVo> nationRegionCityList = confCityDao.getNationRegionCity(houseAuditRequest);
    	if(Check.NuNCollection(nationRegionCityList)){
    		return new PagingResult<>();
    	}
    	Map<String, NationRegionCityVo> map = new HashMap<>();
    	List<String> cityList = new ArrayList<>();
    	NationRegionCityUtil.fillNationRegionCity(nationRegionCityList, map, cityList);
    	//填充城市code
    	if(!houseAuditRequest.checkEmpty()){
    		//当前参数城市相关配置不为空
    		houseAuditRequest.setCityList(cityList);
    	}
    	PagingResult<HouseAuditVoNew> pagingResult = houseAuditDao.getHouseAuditByPage(houseAuditRequest);
    	if (!Check.NuNCollection(pagingResult.getRows())){
            for (HouseAuditVoNew vo : pagingResult.getRows()) {
                String cityCode = vo.getCityCode();
                if (!map.containsKey(cityCode)){
                    continue;
                }
                fillContact(map, vo, cityCode);
                //封装审核不通过原因
                String firstRefuseReason = vo.getFirstRefuseReason();
                if (!Check.NuNStr(firstRefuseReason)){
                	vo.setFirstRefuseReasonName(HouseAuditCauseEnum.getNameString(firstRefuseReason));
                }
                //封装审核不通过次数
                Map<String, Object> mapTimes = new HashMap<>();
                int rentWay = vo.getRentWay();
                String fid = vo.getFid();  
                mapTimes.put("rentWay", rentWay);
                mapTimes.put("fid", fid);
                if(!Check.NuNMap(mapTimes)){
                	int times = houseAuditDao.findHouseAuditNoLogTime(mapTimes);
                	vo.setTimes(times);
                }
            }
        }
    	return pagingResult;
	}


	/**
	 * 不分页查询
	 *
	 * @author baiwei
	 * @created 2017年5月4日 下午5:54:21
	 *
	 * @param houseAuditRequest
	 * @return
	 */
	public List<HouseAuditVoNew> getHouseAuditList(HouseAuditRequest houseAuditRequest) {
		//获取当前的城市列表
    	List<NationRegionCityVo> nationRegionCityList = confCityDao.getNationRegionCity(houseAuditRequest);
    	if(Check.NuNCollection(nationRegionCityList)){
    		return new ArrayList<>();
    	}
    	Map<String, NationRegionCityVo> map = new HashMap<>();
    	List<String> cityList = new ArrayList<>();
    	NationRegionCityUtil.fillNationRegionCity(nationRegionCityList, map, cityList);
    	//填充城市code
    	if(!houseAuditRequest.checkEmpty()){
    		//当前参数城市相关配置不为空
    		houseAuditRequest.setCityList(cityList);
    	}
    	List<HouseAuditVoNew> houseAuditList = houseAuditDao.getHouseAuditList(houseAuditRequest);
    	if (!Check.NuNCollection(houseAuditList)){
            for (HouseAuditVoNew vo : houseAuditList) {
                String cityCode = vo.getCityCode();
                if (!map.containsKey(cityCode)){
                    continue;
                }
                fillContact(map, vo, cityCode);
            }
        }
    	return houseAuditList; 
	}
	
	/**
	 * 
	 * 填充当前的数据信息
	 *
	 * @author baiwei
	 * @created 2017年5月4日 下午5:59:45
	 *
	 * @param map
	 * @param vo
	 * @param cityCode
	 */
	private void fillContact(Map<String, NationRegionCityVo> map,HouseAuditVoNew vo, String cityCode) {
		NationRegionCityVo regionCityVo = map.get(cityCode);
		vo.setRegionName(regionCityVo.getRegionName());
		vo.setNationName(regionCityVo.getNationName());
		vo.setCityName(regionCityVo.getCityName());
	}


	/* (non-Javadoc)
	 * @see com.ziroom.minsu.report.common.service.ReportService#getPageInfo(com.ziroom.minsu.services.common.dto.PageRequest)
	 */
	@Override
	public PagingResult<HouseAuditVoNew> getPageInfo(HouseAuditRequest par) {
		// TODO Auto-generated method stub
		return null;
	}


	/* (non-Javadoc)
	 * @see com.ziroom.minsu.report.common.service.ReportService#countDataInfo(com.ziroom.minsu.services.common.dto.PageRequest)
	 */
	@Override
	public Long countDataInfo(HouseAuditRequest par) {
		// TODO Auto-generated method stub
		return null;
	}
}
