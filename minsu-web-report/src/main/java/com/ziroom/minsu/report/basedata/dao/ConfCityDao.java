package com.ziroom.minsu.report.basedata.dao;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.ziroom.minsu.entity.base.NationCodeEntity;
import com.ziroom.minsu.entity.conf.CityRegionEntity;
import com.ziroom.minsu.report.basedata.dto.NationRegionCityRequest;
import com.ziroom.minsu.report.basedata.vo.CityRegionVo;
import com.ziroom.minsu.report.basedata.dto.CityRegionRequest;
import com.ziroom.minsu.report.basedata.dto.CityRequest;
import com.ziroom.minsu.report.basedata.entity.ConfCityEntity;
import com.ziroom.minsu.report.basedata.vo.NationRegionCityVo;

import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * <p>TODO</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/5/5.
 * @version 1.0
 * @since 1.0
 */
@Repository("report.confCityDao")
public class ConfCityDao {

    private String SQLID="report.confCityDao.";

    @Autowired
    @Qualifier("minsuReport.all.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;



    /**
     * 分页查询
     * @author afi
     * @param cityRegionRequest
     * @return
     */
    public PagingResult<CityRegionVo> getCityRegionBypage(CityRegionRequest cityRegionRequest){
        PageBounds pageBounds = new PageBounds();
        pageBounds.setLimit(cityRegionRequest.getLimit());
        pageBounds.setPage(cityRegionRequest.getPage());
        return mybatisDaoContext.findForPage(SQLID + "getCityRegionBypage", CityRegionVo.class, cityRegionRequest, pageBounds);
    }





    /**
     * 分页查询
     * @author liyingjie
     * @param afiRequest
     * @return
     */
    public PagingResult<ConfCityEntity> getPageInfo(CityRequest afiRequest){
        PageBounds pageBounds = new PageBounds();
        pageBounds.setLimit(afiRequest.getLimit());
        pageBounds.setPage(afiRequest.getPage());
        return mybatisDaoContext.findForPage(SQLID + "getOpenCity", ConfCityEntity.class, afiRequest, pageBounds);
    }




    /**
     * 通过区域查询城市
     * @author afi
     * @param fid
     * @return
     */
    public List<ConfCityEntity> getCityByRegionFid(String fid){
        return mybatisDaoContext.findAll(SQLID + "getCityByRegionFid", ConfCityEntity.class, fid);

    }


    /**
     * 开通城市查询
     * @author liyingjie
     * @param paramMap
     * @return
     */
    public List<ConfCityEntity> getOpenCity(Map<String, Object> paramMap){
       return mybatisDaoContext.findAll(SQLID + "getOpenCity", ConfCityEntity.class, paramMap);
    	
    }
    
    /**
     * 开通城市查询
     * @author liyingjie
     * @param cityCode
     * @return
     */
    public ConfCityEntity getCityByCode(String cityCode){
    	if(Check.NuNStr(cityCode)){
    		return new ConfCityEntity();
    	}
    	
    	return mybatisDaoContext.findOneSlave(SQLID + "getCityByCode", ConfCityEntity.class, cityCode);
    }

    /**
     * 获取父级code下的城市列表
     * @author jixd
     * @created 2017年01月10日 16:46:53
     * @param
     * @return
     */
    public List<ConfCityEntity> getListByPcode(String pCode){
        return mybatisDaoContext.findAll(SQLID+"getListByPcode",ConfCityEntity.class,pCode);
    }

    /**
     * 查询关联省份
     * @author jixd
     * @created 2017年01月10日 18:01:47
     * @param
     * @return
     */
    public List<ConfCityEntity> getRegionRelList(String regionFid){
        return mybatisDaoContext.findAll(SQLID+"getRegionRelList",ConfCityEntity.class,regionFid);
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
		return mybatisDaoContext.findAll(SQLID+"getNations", ConfCityEntity.class);
	}


    /**
     * 查询当前的国家
     * @author afi
     * @created 2017年1月12日
     *
     * @return
     */
    public ConfCityEntity getNationByCode(String nationCode) {
        Map<String,Object>  par = new HashedMap();
        par.put("nationCode",nationCode);
        return mybatisDaoContext.findOne(SQLID+"getNationByCode", ConfCityEntity.class,par);
    }



    /**
     * 查询 条件列表
     * @author afi
     * @created 2017年1月12日
     *
     * @return
     */
    public List<NationRegionCityVo> getNationRegionCity(NationRegionCityRequest nationRegionCityRequest) {
        return mybatisDaoContext.findAll(SQLID+"getNationRegionCity", NationRegionCityVo.class,nationRegionCityRequest);
    }





	/**
	 * 获取大区列表
	 *
	 * @author loushuai
	 * @created 2017年5月19日 下午6:36:18
	 *
	 * @return
	 */
	public List<CityRegionEntity> getAllRegionList() {
		return mybatisDaoContext.findAll(SQLID+"getAllRegionList");
	}
	

	/**
	 * 获取所有国家
	 *
	 * @author loushuai
	 * @created 2017年5月19日 下午6:36:18
	 *
	 * @return
	 */
	public List<NationCodeEntity> getAllNationList() {
		return mybatisDaoContext.findAll(SQLID+"getAllNationList");
	}
	
	/**
	 * 根据nationCode获取NationName
	 *
	 * @author loushuai
	 * @created 2017年5月19日 下午6:36:18
	 *
	 * @return
	 */
	public NationCodeEntity getNationName(String nationCode) {
		Map<String, Object> map  = new HashMap<String, Object>();
		map.put("nationCode", nationCode);
		return mybatisDaoContext.findOne(SQLID+"getNationName", NationCodeEntity.class, map);
	}
	
	/**
	 *  根据provinceCode获取大区名称
	 *
	 * @author loushuai
	 * @created 2017年5月19日 下午6:36:18
	 *
	 * @return
	 */
	public NationCodeEntity getRegionNameByPCode(String provinceCode) {
		Map<String, Object> map  = new HashMap<String, Object>();
		map.put("nationCode", provinceCode);
		return mybatisDaoContext.findOne(SQLID+"getAllNationList", NationCodeEntity.class, map);
	}
	
	
	/**
	 * 
	 *
	 * @author loushuai
	 * @created 2017年5月19日 下午5:22:01
	 *
	 * @param provinceCode
	 * @return
	 */
	public CityRegionEntity getRegionByProvinceCode(String provinceCode) {
		return mybatisDaoContext.findOne(SQLID+"getRegionByProvinceCode", CityRegionEntity.class, provinceCode);
	}
	


}
