package com.ziroom.minsu.report.basedata.service;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.ziroom.minsu.entity.base.NationCodeEntity;
import com.ziroom.minsu.entity.conf.CityRegionEntity;
import com.ziroom.minsu.report.basedata.dao.ConfCityDao;
import com.ziroom.minsu.report.common.service.ReportService;
import com.ziroom.minsu.report.basedata.dto.CityRequest;
import com.ziroom.minsu.report.basedata.entity.ConfCityEntity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
@Service("report.confCityService")
public class ConfCityService implements ReportService <ConfCityEntity,CityRequest>{

    private static final Logger LOGGER = LoggerFactory.getLogger(ConfCityService.class);

    @Resource(name="report.confCityDao")
    private ConfCityDao confCityDao;

	
    /**
     * 分页查询
     * @author liyingjie
     * @param afiRequest
     * @return
     */
	@Override
	public PagingResult<ConfCityEntity> getPageInfo(CityRequest par) {
		return confCityDao.getPageInfo(par);
	}

	/**
	 * 获取开通城市
	 * @param par
	 * @return
	 */
	public List<ConfCityEntity> getOpenCity(CityRequest par) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		return confCityDao.getOpenCity(paramMap);
	}
	
	 /**
     * 根据code获取城市
     * @author liyingjie
     * @param afiRequest
     * @return
     */
    public ConfCityEntity getCityByCode(String cityCode){
    	if(Check.NuNStr(cityCode)){
    		return new ConfCityEntity();
    	}
    	return confCityDao.getCityByCode(cityCode);
    }

	/**
	 * 根据父级获取子集
	 * @author jixd
	 * @created 2017年01月10日 16:49:57
	 * @param
	 * @return
	 */
	public List<ConfCityEntity> getListByPcode(String pCode){
		return confCityDao.getListByPcode(pCode);
	}


	/**
	 * 查询关联省份
	 * @author jixd
	 * @created 2017年01月10日 18:01:47
	 * @param
	 * @return
	 */
	public List<ConfCityEntity> getRegionRelList(String regionFid){
		return confCityDao.getRegionRelList(regionFid);
	}

	@Override
	public Long countDataInfo(CityRequest par) {
		
		return null;
	}

	/**
	 * 查询所有国家
	 *
	 * @author liujun
	 * @created 2017年1月12日
	 *
	 * @return
	 */
	public List<ConfCityEntity> getNations() {
		return confCityDao.getNations();
	}


	/**
	 * 获取当前的开通城市的map
	 * @return
	 */
	public List<Map<String,String>> getCityListInfo(){
		CityRequest cityRequest = new CityRequest();
		List<ConfCityEntity> cityList = this.getOpenCity(cityRequest);
		List<Map<String,String>> cityMapList = new ArrayList<Map<String,String>>(cityList.size());
		Map<String,String> paramMap;
		for(ConfCityEntity cEntity : cityList){
			paramMap = new HashMap<>();
			paramMap.put("name", cEntity.getShowName());
			paramMap.put("code", cEntity.getCode());
			cityMapList.add(paramMap);
		}
		return cityMapList;
	}
	
	/**
	 * 获取大区列表
	 * @author loushuai
	 * @return
	 */
	public List<CityRegionEntity> getAllRegionList(){
		return confCityDao.getAllRegionList();
	}
	
	/**
	 * 获取国家列表
	 * @author loushuai
	 * @return
	 */
	public List<NationCodeEntity> getAllNationList(){
		return confCityDao.getAllNationList();
	}
	
}
