package com.ziroom.minsu.services.search.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.cache.redisOne.RedisOperations;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.services.common.utils.RedisKeyConst;
import com.ziroom.minsu.valenum.city.CityRulesEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ziroom.minsu.entity.conf.DicItemEntity;
import com.ziroom.minsu.services.basedata.dao.BasedateDbInfoDao;
import com.ziroom.minsu.services.basedata.dao.HotRegionDbInfoDao;
import com.ziroom.minsu.services.basedata.dao.SubwayDbInfoDao;
import com.ziroom.minsu.services.house.dao.HouseDbInfoDao;
import com.ziroom.minsu.services.search.vo.HotRegionSimpleVo;
import com.ziroom.minsu.services.search.vo.HotRegionVo;
import com.ziroom.minsu.services.search.vo.StaticResourceVo;
import com.ziroom.minsu.services.search.vo.SubwayStationVo;

/**
 * <p>搜索查询条件接口</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * @author lishaochuan on 2016年8月23日
 * @since 1.0
 * @version 1.0
 */
@Service(value = "search.searchConditionServiceImpl")
public class SearchConditionServiceImpl {

	
	private static final Logger LOGGER = LoggerFactory.getLogger(SearchConditionServiceImpl.class);

	@Resource(name = "search.hotRegionDbInfoDao")
    private HotRegionDbInfoDao hotRegionDbInfoDao;

	@Resource(name = "search.subwayDbInfoDao")
	private SubwayDbInfoDao subwayDbInfoDao;
	
    @Resource(name="search.basedateDbInfoDao")
    private BasedateDbInfoDao basedateDbInfoDao;
    
    @Resource(name = "search.houseDbInfoDao")
    private HouseDbInfoDao houseDbInfoDao;

	@Autowired
	private RedisOperations redisOperations;
	
	
	/**
	 * 根据城市获取对应的区域 
	 * @author lishaochuan
	 * @create 2016年8月23日下午3:26:18
	 * @param cityCode
	 * @return
	 */
	public List<HotRegionSimpleVo> getAreaByCity(String cityCode){
		return hotRegionDbInfoDao.getAreaByCity(cityCode);
	}

	/**
	 * 获取热搜区域
	 *
	 * @author zhangyl
	 * @created 2017年8月18日 下午1:42:30
	 *
	 * @param cityCode
	 * @return
	 */
	public List<HotRegionVo> getHotSearchRegionList(String cityCode){
		List<HotRegionVo> hotRegionListResult = new ArrayList<>();

		List<DicItemEntity> dicList = selectEffectiveDefaultEnumList(CityRulesEnum.CityRulesEnum002.getValue(), null);

		if (!Check.NuNCollection(dicList)) {
			for (DicItemEntity dicItemEntity : dicList) {
				List<HotRegionVo> hotRegionList = getHotRegionListByEffectiveEnumStatus(CityRulesEnum.CityRulesEnum002.getValue(), null, cityCode, dicItemEntity.getItemValue());

				if (hotRegionList != null && hotRegionList.size() > 4) {
					hotRegionList = hotRegionList.subList(0, 4);
				}
				if (hotRegionList != null && hotRegionList.size() > 0) {
					hotRegionListResult.addAll(hotRegionList);
				}
			}

		}
		return hotRegionListResult;

	}
	
    /**
     * 获取当前有效的景点商圈类型下的有效的景点商圈
     * @author zl
     * @param cityCode
     * @return
     */
	public List<HotRegionVo> getHotRegionListByCityEnumStatus(String cityCode){
		return hotRegionDbInfoDao.getHotRegionListByCityEnumStatus(cityCode);
	}
	
	/**
	 * 获取地铁站点信息
	 * @author lishaochuan
	 * @create 2016年8月23日下午3:15:38
	 * @param cityCode
	 * @return
	 */
	public List<SubwayStationVo> getSubwayStation(String cityCode){
		return subwayDbInfoDao.getSubwayStation(cityCode);
	}
	
	
    /**
     * 获取当前模板的cod的值信息
     * @param code
     * @param templateFid
     * @return
     */
    public List<DicItemEntity> getDicItemListByCodeAndTemplate(String code, String templateFid){
        return basedateDbInfoDao.getDicItemListByCodeAndTemplate( code,  templateFid);
    }
    
    /**
     * 默认配置枚举列表（有效的）
     * @param templateFid
     * @param dicCode
     * @return
     */
	public List<DicItemEntity> selectEffectiveDefaultEnumList( String dicCode,String templateFid){
		return basedateDbInfoDao.selectEffectiveDefaultEnumList(dicCode, templateFid);
	}
	
    /**
     * 按照有效的枚举查询商圈、景点。。。
     * @param dicCode
     * @param templateFid
     * @param cityCode
     * @param regionType
     * @return
     */
	public List<HotRegionVo> getHotRegionListByEffectiveEnum( String dicCode,String templateFid,String cityCode,String regionType ){
		return hotRegionDbInfoDao.getHotRegionListByEffectiveEnum(dicCode, templateFid, cityCode, regionType);
	}
	
    /**
     * 按照有效的枚举查询有效的商圈、景点等类型下有效的景点商圈
     * @author zl
     * @param dicCode
     * @param templateFid
     * @param cityCode
     * @param regionType
     * @return
     */
	public List<HotRegionVo> getHotRegionListByEffectiveEnumStatus( String dicCode,String templateFid,String cityCode,String regionType ){
		return hotRegionDbInfoDao.getHotRegionListByEffectiveEnumStatus(dicCode, templateFid, cityCode, regionType);
	}
	
	 /**
     * 查询城市下已经上线的房源类型
     * @param cityCode
     * @return
     */
    public List<Integer> getHouseTypeByCityCode(String cityCode){
    	return houseDbInfoDao.getHouseTypeByCityCode(cityCode);
    }
    
    
	/**
	 * 
	 * 查询静态资源
	 *
	 * @author zl
	 * @created 2017年3月17日 下午5:55:50
	 *
	 * @param resCode
	 * @return
	 */
	public StaticResourceVo getStaticResourceByResCode(String resCode){


		String key = RedisKeyConst.getCollectKey("search_"+resCode);
		String json= null;
		try {
			json=redisOperations.get(key);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "redis错误,e:{}",e);
		}
		StaticResourceVo resource=null;

		if (Check.NuNStr(json)) {
			resource = basedateDbInfoDao.getStaticResourceByResCode(resCode);
			if (!Check.NuNObj(resource)) {
				try {
					redisOperations.setex(key.toString(), RedisKeyConst.CONF_CACHE_TIME, JsonEntityTransform.Object2Json(resource));
				} catch (Exception e) {
					LogUtil.error(LOGGER, "redis错误,e:{}",e);
				}
			}
		}else{
			resource = JsonEntityTransform.json2Entity(json, StaticResourceVo.class);
		}

		return resource;
	}
    
    
    
}
