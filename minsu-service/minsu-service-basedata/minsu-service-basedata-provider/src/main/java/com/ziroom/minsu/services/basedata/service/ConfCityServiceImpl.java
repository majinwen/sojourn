/**
 * @FileName: ConfCityServiceImpl.java
 * @Package com.ziroom.minsu.services.basedata.service
 * 
 * @author liyingjie
 * @created 2016年3月9日 上午11:14:06
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.basedata.service;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.ziroom.minsu.entity.base.NationCodeEntity;
import com.ziroom.minsu.entity.conf.CityFeatureHousetypeEntity;
import com.ziroom.minsu.entity.conf.ConfCityEntity;
import com.ziroom.minsu.services.basedata.dao.CityFeatureHouseTypeDao;
import com.ziroom.minsu.services.basedata.dao.ConfCityDao;
import com.ziroom.minsu.services.basedata.dao.NationCodeDao;
import com.ziroom.minsu.services.basedata.dto.ConfCityRequest;
import com.ziroom.minsu.services.basedata.entity.CityFeatureHouseVo;
import com.ziroom.minsu.services.basedata.entity.FeatureTagsVo;
import com.ziroom.minsu.services.basedata.entity.TreeNodeVo;
import com.ziroom.minsu.valenum.base.CityLevelEnum;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 后台国家、省份、城市、区域配置业务层
 * </p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author liyingjie
 * @since 1.0
 * @version 1.0
 */
@Service("basedata.confCityServiceImpl")
public class ConfCityServiceImpl {

	@Resource(name = "basedata.confCityDao")
	private ConfCityDao confCityDao;
	
	@Resource(name = "basedata.cityFeatureHouseTypeDao")
	private CityFeatureHouseTypeDao cityFeatureHouseTypeDao;
	
	@Resource(name="basedata.nationCodeDao")
	private NationCodeDao nationCodeDao;



    /**
     * 获取所有城市列表
     * @author afi
     * @return
     */
    public List<ConfCityEntity> getAllCityList() {
        return confCityDao.getAllCityList();
    }


    /**
     * 获取开通的城市列表
     * @author afi
     * @return
     */
    public List<ConfCityEntity> getOpenCity() {
        return confCityDao.getOpenCity();
    }


	/**
	 * 获取开通的国家列表(房东)
	 * @return
     */
	public List<ConfCityEntity> getOpenNationLandlord(){
		return confCityDao.getOpenNationLandlord();
	}


	/**
	 * 获取指定国家下开通的城市列表(房东)
	 * @author lunan
	 * @return
	 */
	public List<ConfCityEntity> getOpenCityLandlord4Nation(String code){
		return confCityDao.getOpenCityLandlord4Nation(code);
	}

    
    /**
     * 获取开通的城市列表(房东)
     * @author lunan
     * @return
     */
    public List<ConfCityEntity> getOpenCityLandlord(){
    	return confCityDao.getOpenCityLandlord();
    }
    
    /**
     * 获取开通的城市列表(房客)
     * @author lunan
     * @return
     */
    public List<ConfCityEntity> getOpenCityTenant(){
    	return confCityDao.getOpenCityTenant();
    }


	/**
	 * 获取热门城市列表
	 * @author afi
	 * @return
	 */
	public List<ConfCityEntity> getHotCity() {
		return confCityDao.getHotCity();
	}
	
	/**
	 * 获取热门城市列表(房东)
	 * @author lunan
	 * @return
	 */
	public List<ConfCityEntity> getHotCityLandlord() {
		return confCityDao.getHotCityLandlord();
	}
	
	/**
	 * 获取热门城市列表(房客)
	 * @author lunan
	 * @return
	 */
	public List<ConfCityEntity> getHotCityTenant() {
		return confCityDao.getHotCityTenant();
	}

	/**
	 * 
	 * 资源左侧树 列表
	 *
	 * @author liyingjie
	 * @created 2016年3月21日 
	 *
	 * @return
	 */
	public List<TreeNodeVo> findConfCityVos() {
		return confCityDao.findTreeNodeVoList();
	}

	/**
	 * 资源左侧树 列表
	 * @author liyingjie
	 * @created 2016年3月21日 
	 * @return
	 */
	public List<TreeNodeVo> findConfCityDataVos() {
		return confCityDao.findTreeNodeVoDataList();
	}

	
	public List<TreeNodeVo> findConfCityOnlyVos() {
		return confCityDao.findConfCityOnlyVos();
	}

	/**
	 * 
	 * 保存资源 信息
	 *
	 * @author liyingjie
	 * @created 2016年3月21日 
	 *
	 * @param confCityEntity
	 * @return
	 */
	public void saveConfCityRes(ConfCityEntity confCityEntity) {
		
		confCityDao.insertConfCityRes(confCityEntity);
	}
	/**
	 * 
	 * 更新 资源 信息
	 *
	 * @author liyingjie
	 * @created 2016年3月21日 
	 *
	 * @param confCityEntity
	 * @return
	 */
	public void cascadeUpdateConfCityByFid(ConfCityEntity confCityEntity) {
		// 级联更新区域
		List<TreeNodeVo> cityTreeList = null;
        if(confCityEntity.getCityStatus() == 1 ||confCityEntity.getCityStatus() == 2 ||confCityEntity.getCityStatus() == 3){//开通
        	if(!Check.NuNStr(confCityEntity.getFid())){
        		//开通城市的话，更新改城市信息
        		confCityDao.updateConfCityByFid(confCityEntity);
        	}
        	
        	// 级联查询所有上级行政区域
        	cityTreeList = confCityDao.findConfCityParentByCode(confCityEntity.getCode());
        	
        	if(CityLevelEnum.CITY.getCode() == confCityEntity.getLevel()){
        		// 城市开通,级联开通区域
        		cityTreeList.addAll(confCityDao.findConfCityChildrenByPcode(confCityEntity.getCode()));
        	}
        	
        	this.recursionUpdate(cityTreeList,confCityEntity.getCityStatus());
        } else {//关闭
        	// 级联查询所有下级行政区域
        	cityTreeList = confCityDao.findConfCityChildrenByPcode(confCityEntity.getCode());
        	
        	// 递归关闭所有行政区域
        	this.recursionUpdate(cityTreeList,confCityEntity.getCityStatus());
        	
        	confCityDao.updateConfCityByFid(confCityEntity);
		}
	}

	/**
	 * 递归更新所有下级行政区域状态
	 *
	 * @author liujun
	 * @created 2016年5月14日
	 *
	 * @param cityTreeList
	 * @param cityStatus 
	 */
	private void recursionUpdate(List<TreeNodeVo> cityTreeList, Integer cityStatus) {
		for (TreeNodeVo treeNodeVo : cityTreeList) {
			List<TreeNodeVo> cityList = treeNodeVo.getNodes();
			if(!Check.NuNCollection(cityList)){
				this.recursionUpdate(cityList, cityStatus);
			}
			ConfCityEntity confCityEntity = new ConfCityEntity();
			confCityEntity.setFid(treeNodeVo.getId());
			confCityEntity.setCityStatus(cityStatus);
			confCityEntity.setLastModifyDate(new Date());
			confCityDao.updateConfCityByFid(confCityEntity);
		}
		
	}

	/**
	 * 
	 * 后台菜单列表
	 *
	 * @author liyingjie
	 * @created 2016年3月9日 上午11:20:09
	 *
	 * @param resourceRequest
	 * @return
	 */
	public PagingResult<ConfCityEntity> findConfCityEntityList(ConfCityRequest confCityRequest) {
		
		return confCityDao.findConfCityPageList(confCityRequest);
	}

	/**
	 * 级联查询下级行政区域列表
	 *
	 * @author liujun
	 * @created 2016-3-22 下午2:32:09
	 *
	 * @param pCode
	 * @return
	 */
	public List<ConfCityEntity> findConfCityListByPcode(String pCode) {
		return confCityDao.findConfCityListByPcode(pCode);
	}
	
	/**
	 * 
	 * 房东端 查询下级行政区域列表
	 *
	 * @author jixd
	 * @created 2016年9月27日 上午12:30:13
	 *
	 * @param pCode
	 * @return
	 */
	public List<ConfCityEntity> findConfCityListByPcodeForLan(String pCode){
		return confCityDao.findConfCityListByPcodeForLan(pCode);
	}
	
	/**
	 * 
	 * 城市级联菜单结构数据-开通城市
	 *
	 * @author bushujie
	 * @created 2016年3月28日 下午2:56:23
	 *
	 * @return
	 */
	public List<TreeNodeVo> findConfCitySelect(){
		return confCityDao.findConfCitySelect();
	}
	
	/**
	 * 
	 * 城市级联菜单结构数据(房东开通)
	 *
	 * @author lunan
	 * @created 2016年9月24日 下午6:45:54
	 *
	 * @return
	 */
	public List<TreeNodeVo> findConfCitySelectForLandlord(){
		return confCityDao.findConfCitySelectForLandlord();
	}

	/**
	 * 
	 * 城市级联菜单结构数据(房客开通)
	 *
	 * @author lunan
	 * @created 2016年9月26日 上午10:03:48
	 *
	 * @return
	 */
	public List<TreeNodeVo> findConfCitySelectForTenant(){
		return confCityDao.findConfCitySelectForTenant();
	}
	
	/**
	 * 
	 * 城市级联菜单结构数据-所有城市
	 *
	 * @author liujun
	 * @created 2016年12月15日
	 *
	 * @return
	 */
	public List<TreeNodeVo> findConfCitySelectForAll(){
		return confCityDao.findConfCitySelectForAll();
	}
	
	/**
	 * 根据城市code获取城市名称
	 * @author jixd on 2016年4月14日
	 */
	public String getCityNameByCode(String code){
		return confCityDao.getCityNameByCode(code);
	}

	
	/**
	 * 根据城市codeList获取城市名称
	 * @author lishaochuan
	 * @create 2016年5月26日上午12:09:55
	 * @param code
	 * @return
	 */
	public List<ConfCityEntity> getCityNameByCodeList(List<String> codeList){
		return confCityDao.getCityNameByCodeList(codeList);
	}
	
	/**
	 * 
	 * 查询城市根据code
	 *
	 * @author bushujie
	 * @created 2016年8月8日 下午5:46:06
	 *
	 * @param code
	 * @return
	 */
	public ConfCityEntity getConfCityByCode(String code){
		return confCityDao.getConfCityByCode(code);
	}

	/**
	 * 
	 * 获取开通的城市列表
	 *
	 * @author liujun
	 * @created 2016年11月10日
	 *
	 * @return
	 */
	public List<ConfCityEntity> getOpenCityWithFile() {
		return confCityDao.getOpenCityWithFile();
	}
	
	/**
	 * 查询城市下的特色房源
	 * TODO
	 *
	 * @author zl
	 * @created 2016年12月1日 下午5:46:11
	 *
	 * @param countryCode
	 * @param provinceCode
	 * @param cityCode
	 * @return
	 */
	public List<CityFeatureHousetypeEntity> getCityFeatureHouseTypes(String countryCode,String provinceCode,String cityCode) {
		return cityFeatureHouseTypeDao.getCityFeatureHouseTypes(countryCode, provinceCode, cityCode);
	}
	/**
	 * 查询所有的有特色房源的城市的房源
	 * TODO
	 *
	 * @author zl
	 * @created 2016年12月1日 下午5:58:03
	 *
	 * @return
	 */
	public List<CityFeatureHouseVo> getAllCityFeatureHouseTypes() {
		return cityFeatureHouseTypeDao.getAllCityFeatureHouseTypes();
	}
	
	/**
	 * 根据有效状态查询所有的特色标识列表
	 * @author zl
	 * @created 2017年1月9日 下午5:58:03
	 * @param isValid
	 * @return
	 */
	public List<FeatureTagsVo> getAllFeatureTags(Boolean isValid,String templateFid){
		return cityFeatureHouseTypeDao.getAllFeatureTags(isValid,templateFid);
	}
	
	/**
	 *  更新特色标识
	 * @param entity
	 * @return
	 */
	public Integer updateFeatureTagByFid(CityFeatureHousetypeEntity entity){
		return cityFeatureHouseTypeDao.updateFeatureTagByFid(entity);
	}
	
	/**
	 * 新增特色标识
	 * @param entity
	 * @return
	 */
	public Integer addFeatureTag(CityFeatureHousetypeEntity entity){		 
		return cityFeatureHouseTypeDao.addFeatureTag(entity);
	}
	
	/**
	 * 
	 * 查询国家码列表
	 *
	 * @author bushujie
	 * @created 2017年4月11日 下午3:14:21
	 *
	 * @return
	 */
	public List<NationCodeEntity> findNationCodeList(){
		return nationCodeDao.findNationCodeList();
	}
	
}
