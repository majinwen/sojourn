/**
 * @FileName: ConfCityServiceProxy.java
 * @Package com.ziroom.minsu.services.basedata.proxy
 * 
 * @author liyingjie
 * @created 2016年3月9日 上午11:28:42
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.basedata.proxy;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.MessageSourceUtil;
import com.asura.framework.cache.redisOne.RedisOperations;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.base.NationCodeEntity;
import com.ziroom.minsu.entity.conf.CityFeatureHousetypeEntity;
import com.ziroom.minsu.entity.conf.ConfCityEntity;
import com.ziroom.minsu.services.basedata.api.inner.ConfCityService;
import com.ziroom.minsu.services.basedata.dto.ConfCityRequest;
import com.ziroom.minsu.services.basedata.entity.CityFeatureHouseVo;
import com.ziroom.minsu.services.basedata.entity.FeatureTagsVo;
import com.ziroom.minsu.services.basedata.entity.TreeNodeVo;
import com.ziroom.minsu.services.basedata.service.ConfCityServiceImpl;
import com.ziroom.minsu.services.common.constant.MessageConst;
import com.ziroom.minsu.services.common.utils.PinYinUtil;
import com.ziroom.minsu.services.common.utils.RedisKeyConst;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import java.util.*;

/**
 * <p>
 * 后台操作国家、省份、城市、区域 代理层
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
@Component("basedata.confCityServiceProxy")
public class ConfCityServiceProxy implements ConfCityService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ConfCityServiceProxy.class);

	@Resource(name = "basedata.confCityServiceImpl")
	private ConfCityServiceImpl confCityServiceImpl;

	@Resource(name = "basedata.messageSource")
	private MessageSource messageSource;


	@Autowired
	private RedisOperations redisOperations;


	/**
	 * 获取所有的城市map
	 * @author afi
	 * @return
     */
	@Override
	public String getAllCityMap() {
		DataTransferObject dto = new DataTransferObject();
		try {
			String key = RedisKeyConst.getConfigKey("allCityMap");
			String listJson = null;
			try {
				listJson = redisOperations.get(key);
			} catch (Exception e) {
				LogUtil.error(LOGGER, "redis错误,e:{}", e);
			}
			// 判断缓存是否存在
			if (!StringUtils.isBlank(listJson)) {
				return listJson;
			}
			List<ConfCityEntity> openList = confCityServiceImpl.getAllCityList();
			Map<String, String> map = new HashMap<String, String>(openList.size());
			if (!Check.NuNCollection(openList)) {
				for (ConfCityEntity city : openList) {
					map.put(city.getCode(), city.getShowName());
				}
				dto.putValue("map", map);
			}
			// 当城市列表不为空更新到缓存
			if (!Check.NuNMap(map)) {
				try {
					redisOperations.setex(key.toString(), RedisKeyConst.CONF_CACHE_TIME, dto.toJsonString());
				} catch (Exception e) {
					LogUtil.error(LOGGER, "redis错误,e:{}", e);
				}
			}
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		return dto.toJsonString();
	}


    /**
     * 获取所有城市的列表
     * @author afi
     * @return
     */
    public String getAllCityList(){
        DataTransferObject dto = new DataTransferObject();
        String key = RedisKeyConst.getConfigKey("cityList");
        String listJson= null;
        try {
            listJson=redisOperations.get(key);
        } catch (Exception e) {
            LogUtil.error(LOGGER, "redis错误,e:{}",e);
        }

        //判断缓存是否存在
        if(!StringUtils.isBlank(listJson)){
            return listJson;
        }
        List<Map<String, String>> cityList = null;
        try {
            List<ConfCityEntity> allList = confCityServiceImpl.getAllCityList();
			cityList = tranCityList(allList);
			dto.putValue("list", cityList);
        } catch (Exception e) {
            LogUtil.error(LOGGER, "error:{}", e);
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
        }
        if(!Check.NuNCollection(cityList)){
            //当城市列表不为空更新到缓存
            try {
                redisOperations.setex(key.toString(), RedisKeyConst.CONF_CACHE_TIME, dto.toJsonString());
            } catch (Exception e) {
                LogUtil.error(LOGGER, "redis错误,e:{}",e);
            }

        }
        return dto.toJsonString();
    }


	/**
	 * 获取开通的城市列表
	 * 并获取热门城市
	 * @author afi
	 * @return
	 */
	public String getOpenCityAndHot(){
		DataTransferObject dto = new DataTransferObject();
		String key = RedisKeyConst.getConfigKey("openCityHot");
		String listJson= null;
		try {
			listJson=redisOperations.get(key);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "redis错误,e:{}",e);
		}

		//判断缓存是否存在
		if(!StringUtils.isBlank(listJson)){
			return listJson;
		}
		List<Map<String, String>> cityList = new ArrayList<>();
		try {
			List<ConfCityEntity> openList = confCityServiceImpl.getOpenCity();
			if (!Check.NuNCollection(openList)){
				List<ConfCityEntity> hotList = confCityServiceImpl.getHotCity();
				cityList = tranCityList(hotList);
				dto.putValue("hot",cityList);
			}
			dto.putValue("list", tranSortCityMap(openList));
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		if(!Check.NuNCollection(cityList)){
			//当城市列表不为空更新到缓存
			try {
				redisOperations.setex(key.toString(), RedisKeyConst.CONF_CACHE_TIME, dto.toJsonString());
			} catch (Exception e) {
				LogUtil.error(LOGGER, "redis错误,e:{}",e);
			}

		}
		return dto.toJsonString();
	}
	
	/**
	 * (房东)
	 * 获取开通的城市列表
	 * 并获取热门城市
	 * @author lunan
	 * @return
	 */
	public String getOpenCityAndHotLandlord(){
		DataTransferObject dto = new DataTransferObject();
		String key = RedisKeyConst.getConfigKey("openCityHot");
		String listJson= null;
		try {
			listJson=redisOperations.get(key);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "redis错误,e:{}",e);
		}
		
		//判断缓存是否存在
		if(!StringUtils.isBlank(listJson)){
			return listJson;
		}
		List<Map<String, String>> cityList = new ArrayList<>();
		try {
			List<ConfCityEntity> openList = confCityServiceImpl.getOpenCityLandlord();
			if (!Check.NuNCollection(openList)){
				List<ConfCityEntity> hotList = confCityServiceImpl.getHotCityLandlord();
				cityList = tranCityList(hotList);
				dto.putValue("hot", cityList);
			}
			dto.putValue("list", tranSortCityMap(openList));
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		if(!Check.NuNCollection(cityList)){
			//当城市列表不为空更新到缓存
			try {
				redisOperations.setex(key.toString(), RedisKeyConst.CONF_CACHE_TIME, dto.toJsonString());
			} catch (Exception e) {
				LogUtil.error(LOGGER, "redis错误,e:{}",e);
			}
			
		}
		return dto.toJsonString();
	}
	
	/**
	 * (房客)
	 * 获取开通的城市列表
	 * 并获取热门城市
	 * @author lunan
	 * @return
	 */
	public String getOpenCityAndHotTenant(){
		DataTransferObject dto = new DataTransferObject();
		String key = RedisKeyConst.getConfigKey("openCityHot");
		String listJson= null;
		try {
			listJson=redisOperations.get(key);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "redis错误,e:{}",e);
		}
		
		//判断缓存是否存在
		if(!StringUtils.isBlank(listJson)){
			return listJson;
		}
		List<Map<String,String>> cityList = new ArrayList<>();
		try {
			List<ConfCityEntity> openList = confCityServiceImpl.getOpenCityTenant();
			if (!Check.NuNCollection(openList)){
				List<ConfCityEntity> hotList = confCityServiceImpl.getHotCityTenant();
				cityList = tranCityList(hotList);
				dto.putValue("hot", cityList);
			}
			dto.putValue("list", tranSortCityMap(openList));
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		if(!Check.NuNCollection(cityList)){
			//当城市列表不为空更新到缓存
			try {
				redisOperations.setex(key.toString(), RedisKeyConst.CONF_CACHE_TIME, dto.toJsonString());
			} catch (Exception e) {
				LogUtil.error(LOGGER, "redis错误,e:{}",e);
			}
			
		}
		return dto.toJsonString();
	}


	/**
	 * 将城市信息转化对外的列表
	 * @param cityEntityList
	 * @return
	 */
	private List<Map<String,Object>>  tranSortCityMap(List<ConfCityEntity> cityEntityList ){
		List<Map<String,Object>> mapList = new ArrayList<>();
		Map<String,Object> sortMap = new TreeMap();
		if(!Check.NuNCollection(cityEntityList)){
			for(ConfCityEntity city: cityEntityList){

				String name = city.getShowName();
				String pinyin = PinYinUtil.getCityPinyin(name);
				String frist = pinyin.substring(0,1).toUpperCase();
				if (sortMap.containsKey(frist)){
					Map<String,Object> eleMap = (Map<String,Object>)sortMap.get(frist);
					eleMap.put(pinyin,city);
				}else {
					Map<String,Object> eleMap = new TreeMap<>();
					eleMap.put(pinyin,city);
					sortMap.put(frist,eleMap);
				}
			}
		}
		if (!Check.NuNMap(sortMap)){
			for (Map.Entry<String,Object> entry : sortMap.entrySet()){
				Map<String,Object> citys = new HashMap<>();
				String key = entry.getKey();
				citys.put("key",key);
				List<Map<String,String>>  cityList = new ArrayList<>();
				Map<String,ConfCityEntity> citysMap  = (Map<String,ConfCityEntity>) entry.getValue();

				for (Map.Entry<String,ConfCityEntity> ele : citysMap.entrySet()){
					ConfCityEntity cityEntity = ele.getValue();
					Map entity = new HashMap();
					entity.put("code",cityEntity.getCode());
					entity.put("name", cityEntity.getShowName());
					cityList.add(entity);
				}
				citys.put("cityList",cityList);
				mapList.add(citys);
			}
		}
		return mapList;

	}


	/**
	 * 将城市信息转化对外的列表
	 * @param cityEntityList
	 * @return
     */
	private List<Map<String, String>>  tranCityList(List<ConfCityEntity> cityEntityList ){
		List<Map<String, String>> cityList = new ArrayList<>();
		if(!Check.NuNCollection(cityEntityList)){
			for(ConfCityEntity city: cityEntityList){
				Map<String, String> entity = new HashMap<String, String>();
				entity.put("code",city.getCode());
				entity.put("name", city.getShowName());
				cityList.add(entity);
			}
		}
		return cityList;
	}


	/**
	 * 获取开通的城市列表
	 * @author afi
	 * @return
	 */
	public String getOpenCity(){
		DataTransferObject dto = new DataTransferObject();
		String key = RedisKeyConst.getConfigKey("openCity");
		String listJson= null;
		try {
			listJson=redisOperations.get(key);
		} catch (Exception e) {
            LogUtil.error(LOGGER, "redis错误,e:{}",e);
		}
		
		//判断缓存是否存在
		if(!StringUtils.isBlank(listJson)){
			return listJson;
		}
		List<Map<String,String>> cityList = new ArrayList<>();
		try {
			List<ConfCityEntity> openList = confCityServiceImpl.getOpenCity();
			cityList  = tranCityList(openList);
			dto.putValue("list",cityList );
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		if(!Check.NuNCollection(cityList)){
			//当城市列表不为空更新到缓存
			try {
				redisOperations.setex(key.toString(), RedisKeyConst.CONF_CACHE_TIME, dto.toJsonString());
			} catch (Exception e) {
                LogUtil.error(LOGGER, "redis错误,e:{}",e);
			}

		}
		return dto.toJsonString();
	}


	/**
	 * 获取开通的国家列表(房东)
	 *
	 * @return
	 * @created 2016年9月26日 下午9:55:51
	 */
	@Override
	public String getOpenNationLandlord() {
		DataTransferObject dto = new DataTransferObject();
		String key = RedisKeyConst.getConfigKey("openNationLand");
		String listJson= null;
		try {
			listJson=redisOperations.get(key);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "redis错误,e:{}",e);
		}

		//判断缓存是否存在
		if(!StringUtils.isBlank(listJson)){
			return listJson;
		}
		List<Map<String, String>> cityList = new ArrayList<Map<String, String>>();
		try {
			List<ConfCityEntity> openList = confCityServiceImpl.getOpenNationLandlord();
			cityList = tranCityList(openList);
			dto.putValue("list", cityList);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		if(!Check.NuNCollection(cityList)){
			//当城市列表不为空更新到缓存
			try {
				redisOperations.setex(key.toString(), RedisKeyConst.CONF_CACHE_TIME, dto.toJsonString());
			} catch (Exception e) {
				LogUtil.error(LOGGER, "redis错误,e:{}",e);
			}

		}
		return dto.toJsonString();
	}


	/**
	 * 获取指定国家下开通的城市(房东)
	 *
	 * @param code
	 * @return
	 * @author lishaochuan
	 * @create 2017/2/27 19:42
	 */
	@Override
	public String getOpenCityLandlord4Nation(String code) {
		DataTransferObject dto = new DataTransferObject();
		String key = RedisKeyConst.getConfigKey("openCityLand4Nation", code);
		String listJson= null;
		try {
			listJson=redisOperations.get(key);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "redis错误,e:{}",e);
		}

		//判断缓存是否存在
		if(!StringUtils.isBlank(listJson)){
			return listJson;
		}
		List<Map<String, String>> cityList = new ArrayList<>();
		try {
			List<ConfCityEntity> openList = confCityServiceImpl.getOpenCityLandlord4Nation(code);
			cityList = tranCityList(openList);
			dto.putValue("list", cityList);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		if(!Check.NuNCollection(cityList)){
			//当城市列表不为空更新到缓存
			try {
				redisOperations.setex(key.toString(), RedisKeyConst.CONF_CACHE_TIME, dto.toJsonString());
			} catch (Exception e) {
				LogUtil.error(LOGGER, "redis错误,e:{}",e);
			}

		}
		return dto.toJsonString();
	}

	/**
	 * 获取所有开通的城市列表(房东)
	 * @author lunan
     * @created 2016年9月26日 下午9:55:51
     * 
	 */
	public String getOpenCityLandlord(){
		DataTransferObject dto = new DataTransferObject();
		String key = RedisKeyConst.getConfigKey("openCity");
		String listJson= null;
		try {
			listJson=redisOperations.get(key);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "redis错误,e:{}",e);
		}
		
		//判断缓存是否存在
		if(!StringUtils.isBlank(listJson)){
			return listJson;
		}
		List<Map<String, String>> cityList = new ArrayList<>();
		try {
			List<ConfCityEntity> openList = confCityServiceImpl.getOpenCityLandlord();
			cityList = tranCityList(openList);
			dto.putValue("list", cityList);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		if(!Check.NuNCollection(cityList)){
			//当城市列表不为空更新到缓存
			try {
				redisOperations.setex(key.toString(), RedisKeyConst.CONF_CACHE_TIME, dto.toJsonString());
			} catch (Exception e) {
				LogUtil.error(LOGGER, "redis错误,e:{}",e);
			}
			
		}
		return dto.toJsonString();
	}
	/**
	 * 获取开通的城市列表(房客)
	 * @author lunan
	 * @created 2016年9月26日 下午9:55:51
	 * 
	 */
	public String getOpenCityTenant(){
		DataTransferObject dto = new DataTransferObject();
		String key = RedisKeyConst.getConfigKey("openCity");
		String listJson= null;
		try {
			listJson=redisOperations.get(key);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "redis错误,e:{}",e);
		}
		
		//判断缓存是否存在
		if(!StringUtils.isBlank(listJson)){
			return listJson;
		}
		List<Map<String, String>> cityList = new ArrayList<>();
		try {
			List<ConfCityEntity> openList = confCityServiceImpl.getOpenCityTenant();
			cityList = tranCityList(openList);
			if(!Check.NuNCollection(cityList)){
				LogUtil.info(LOGGER, "size={}", cityList.size());
			}
			dto.putValue("list",cityList);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		if(!Check.NuNCollection(cityList)){
			//当城市列表不为空更新到缓存
			try {
				redisOperations.setex(key.toString(), RedisKeyConst.CONF_CACHE_TIME, dto.toJsonString());
			} catch (Exception e) {
				LogUtil.error(LOGGER, "redis错误,e:{}",e);
			}
			
		}
		return dto.toJsonString();
	}
	
	
	/**
     * 获取开通的城市（返回map）
     * @author lishaochuan
     * @create 2016年6月26日下午1:03:18
     * @return
     */
	@Override
	public String getOpenCityMap() {
		DataTransferObject dto = new DataTransferObject();
		try {
			String key = RedisKeyConst.getConfigKey("openCityMap");
			String listJson = null;
			try {
				listJson = redisOperations.get(key);
			} catch (Exception e) {
				LogUtil.error(LOGGER, "redis错误,e:{}", e);
			}
			// 判断缓存是否存在
			if (!StringUtils.isBlank(listJson)) {
				return listJson;
			}
			List<ConfCityEntity> openList = confCityServiceImpl.getOpenCity();
			Map<String, String> map = new HashMap<String, String>(openList.size());
			if (!Check.NuNCollection(openList)) {
				for (ConfCityEntity city : openList) {
					map.put(city.getCode(), city.getShowName());
				}
				dto.putValue("map", map);
			}
			// 当城市列表不为空更新到缓存
			if (!Check.NuNMap(map)) {
				try {
					redisOperations.setex(key.toString(), RedisKeyConst.CONF_CACHE_TIME, dto.toJsonString());
				} catch (Exception e) {
					LogUtil.error(LOGGER, "redis错误,e:{}", e);
				}
			}
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		return dto.toJsonString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ziroom.minsu.services.basedata.api.inner.ConfCityService#
	 * insertConfCityRes(java.lang.String)
	 */
	@Override
	public void insertConfCityRes(String paramJson) {
		try {
			ConfCityEntity confCityEntity = JsonEntityTransform.json2Object(paramJson, ConfCityEntity.class);
			confCityServiceImpl.saveConfCityRes(confCityEntity);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
		}

	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ziroom.minsu.services.basedata.api.inner.ConfCityService#
	 * updateConfCityByFid(java.lang.String)
	 */
	@Override
	public String updateConfCityByFid(String paramJson) {
		DataTransferObject dto = new DataTransferObject();
		try {
			ConfCityEntity confCity = JsonEntityTransform.json2Object(paramJson, ConfCityEntity.class);
			LogUtil.info(LOGGER, "【改变状态的参数】={}", paramJson);
			if(Check.NuNStr(confCity.getFid())){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("城市fid为空");
				return dto.toJsonString();
			}

			if(Check.NuNStr(confCity.getCode())){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("城市code为空");
				return dto.toJsonString();
			}

			if(confCity.getCityStatus()== null) {
				// 默认城市状态为未开通
				confCity.setCityStatus(0);
			}

			//confCity.setCityStatus(1^confCity.getCityStatus());
			// 直接更新菜单信息
			confCityServiceImpl.cascadeUpdateConfCityByFid(confCity);

			try {
				//清除 开通城市缓存
				redisOperations.del( RedisKeyConst.getConfigKey("openCity"));
                //清除 行政区域级联缓存
                redisOperations.del( RedisKeyConst.getConfigKey("openCityMap"));
				//清除 行政区域级联缓存
				redisOperations.del( RedisKeyConst.getCityConfKey("cityJson"));
			} catch (Exception e) {
                LogUtil.error(LOGGER, "redis错误,e:{}",e);
			}
			

		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}

		return dto.toJsonString();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ziroom.minsu.services.basedata.api.inner.ConfCityService#
	 * ConfCityTreeVo(java.lang.String)
	 */
	@Override
	public String confCityTreeVo() {

		DataTransferObject dto = new DataTransferObject();
		try {
			String cityKey=RedisKeyConst.getCityConfKey("cityTree");
			String cityTreeJson= null;
			try {
				 cityTreeJson=redisOperations.get(cityKey);
			} catch (Exception e) {
                LogUtil.error(LOGGER, "redis错误,e:{}",e);
			}
			if(!Check.NuNStr(cityTreeJson)){
				dto.putValue("list", JsonEntityTransform.json2List(cityTreeJson, TreeNodeVo.class));
			} else {
				List<TreeNodeVo> confCityVos = confCityServiceImpl.findConfCityVos();
				dto.putValue("list", confCityVos);
				if(!Check.NuNCollection(confCityVos)){
					try {
						redisOperations.setex(cityKey, RedisKeyConst.CITY_CONF_CACHE_TIME, JsonEntityTransform.Object2Json(confCityVos));
					} catch (Exception e) {
                        LogUtil.error(LOGGER, "redis错误,e:{}",e);
					}
					
				}
			}
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		return dto.toJsonString();
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ziroom.minsu.services.basedata.api.inner.ConfCityService#
	 * ConfCityTreeVo(java.lang.String)
	 */
	@Override
	public String confCityTreeDataVo() {

		DataTransferObject dto = new DataTransferObject();
		try {
			List<TreeNodeVo> confCityVos = confCityServiceImpl.findConfCityDataVos();
			dto.putValue("list", confCityVos);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		return dto.toJsonString();
	}
	
	
	@Override
	public String confCityOnlyTreeVo() {

		DataTransferObject dto = new DataTransferObject();
		try {
			String key=RedisKeyConst.getCityConfKey("confCityOnly");
			String cityOnlyJson= null;
			try {
				 cityOnlyJson=redisOperations.get(key);
			} catch (Exception e) {
                LogUtil.error(LOGGER, "redis错误,e:{}",e);
			}
			if(!Check.NuNStr(cityOnlyJson)){
				dto.putValue("list", JsonEntityTransform.json2List(cityOnlyJson, TreeNodeVo.class));
			} else {
				List<TreeNodeVo> confCityVos = confCityServiceImpl.findConfCityOnlyVos();
				dto.putValue("list", confCityVos);
				if(!Check.NuNCollection(confCityVos)){
					try {
						redisOperations.setex(key, RedisKeyConst.CITY_CONF_CACHE_TIME, JsonEntityTransform.Object2Json(confCityVos));
					} catch (Exception e) {
                        LogUtil.error(LOGGER, "redis错误,e:{}",e);
					}
					
				}
			}
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		return dto.toJsonString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ziroom.minsu.services.basedata.api.inner.ConfCityService#
	 * findConfCityByFid(java.lang.String)
	 */
	@Override
	public String findConfCityByFid(String paramJson) {

		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ziroom.minsu.services.basedata.api.inner.ConfCityService#
	 * searchNodeListByFid(java.lang.String)
	 */
	@Override
	public String searchNodeListByFid(String paramJson) {

		DataTransferObject dto = new DataTransferObject();
		try {
			ConfCityRequest confCityRequest = JsonEntityTransform.json2Object(paramJson, ConfCityRequest.class);
			PagingResult<ConfCityEntity> confCityList = confCityServiceImpl.findConfCityEntityList(confCityRequest);
			dto.putValue("list", confCityList.getRows());
			dto.putValue("total", confCityList.getTotal());
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		return dto.toJsonString();
	}

	/* (non-Javadoc)
	 * @see com.ziroom.minsu.services.basedata.api.inner.ConfCityService#searchDistricts(java.lang.String)
	 */
	@Override
	public String searchDistricts(String pCode) {
		DataTransferObject dto = new DataTransferObject();
		try {
			List<ConfCityEntity> cityList = confCityServiceImpl.findConfCityListByPcode(pCode);
			dto.putValue("list", cityList);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto = new DataTransferObject();
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		return dto.toJsonString();
	}
	
	@Override
	public String searchAreaListForLan(String pCode){
		DataTransferObject dto = new DataTransferObject();
		try {
			List<ConfCityEntity> cityList = confCityServiceImpl.findConfCityListByPcodeForLan(pCode);
			dto.putValue("list", cityList);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto = new DataTransferObject();
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		return dto.toJsonString();
		
	}
	
	/* (non-Javadoc)
	 * @see com.ziroom.minsu.services.basedata.api.inner.ConfCityService#getConfCitySelect()
	 */
	@Override
	public String getConfCitySelect() {
		DataTransferObject dto = new DataTransferObject();
		try {
			String cityKey=RedisKeyConst.getCityConfKey("cityJson");
			String cityJson= null;
			try {
				 cityJson=redisOperations.get(cityKey);
			} catch (Exception e) {
                LogUtil.error(LOGGER, "redis错误,e:{}",e);
			}
			if(!Check.NuNStr(cityJson)){
				dto.putValue("list", JsonEntityTransform.json2List(cityJson, TreeNodeVo.class));
			} else {
				List<TreeNodeVo> confCityVos = confCityServiceImpl.findConfCitySelect();
				if(!Check.NuNCollection(confCityVos)){
					try {
						 redisOperations.setex(cityKey, RedisKeyConst.CITY_CONF_CACHE_TIME, JsonEntityTransform.Object2Json(confCityVos));
					} catch (Exception e) {
                        LogUtil.error(LOGGER, "redis错误,e:{}",e);
					}
					
				}
				dto.putValue("list", confCityVos);
			}
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		return dto.toJsonString();
	}
	
	/* (non-Javadoc)
	 * @see com.ziroom.minsu.services.basedata.api.inner.ConfCityService#getConfCitySelectForLandlord()
	 */
	@Override
	public String getConfCitySelectForLandlord() {
		DataTransferObject dto = new DataTransferObject();
		try {
			String cityKey=RedisKeyConst.getCityConfKey("cityLandlordJson");
			String cityJson= null;
			try {
				 cityJson=redisOperations.get(cityKey);
			} catch (Exception e) {
                LogUtil.error(LOGGER, "redis错误,e:{}",e);
			}
			if(!Check.NuNStr(cityJson)){
				dto.putValue("list", JsonEntityTransform.json2List(cityJson, TreeNodeVo.class));
			} else {
				List<TreeNodeVo> confCityVos = confCityServiceImpl.findConfCitySelectForLandlord();
				if(!Check.NuNCollection(confCityVos)){
					try {
						 redisOperations.setex(cityKey, RedisKeyConst.CITY_CONF_CACHE_TIME, JsonEntityTransform.Object2Json(confCityVos));
					} catch (Exception e) {
                        LogUtil.error(LOGGER, "redis错误,e:{}",e);
					}
					
				}
				dto.putValue("list", confCityVos);
			}
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		return dto.toJsonString();
	}

	/* (non-Javadoc)
	 * @see com.ziroom.minsu.services.basedata.api.inner.ConfCityService#getConfCitySelectForTenant()
	 */
	@Override
	public String getConfCitySelectForTenant() {
		DataTransferObject dto = new DataTransferObject();
		try {
			String cityKey=RedisKeyConst.getCityConfKey("cityTenantJson");
			String cityJson= null;
			try {
				 cityJson=redisOperations.get(cityKey);
			} catch (Exception e) {
                LogUtil.error(LOGGER, "redis错误,e:{}",e);
			}
			if(!Check.NuNStr(cityJson)){
				dto.putValue("list", JsonEntityTransform.json2List(cityJson, TreeNodeVo.class));
			} else {
				List<TreeNodeVo> confCityVos = confCityServiceImpl.findConfCitySelectForTenant();
				if(!Check.NuNCollection(confCityVos)){
					try {
						 redisOperations.setex(cityKey, RedisKeyConst.CITY_CONF_CACHE_TIME, JsonEntityTransform.Object2Json(confCityVos));
					} catch (Exception e) {
                        LogUtil.error(LOGGER, "redis错误,e:{}",e);
					}
					
				}
				dto.putValue("list", confCityVos);
			}
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		return dto.toJsonString();
	}

	/**
	 * 城市多级数据结构-所有城市
	 */
	@Override
	public String getConfCitySelectForAll() {
		DataTransferObject dto = new DataTransferObject();
		try {
			String cityKey = RedisKeyConst.getCityConfKey("cityAllJson");
			String cityJson = null;
			try {
				cityJson = redisOperations.get(cityKey);
			} catch (Exception e) {
				LogUtil.error(LOGGER, "redis错误,e:{}", e);
			}
			if (!Check.NuNStr(cityJson)) {
				dto.putValue("list", JsonEntityTransform.json2List(cityJson, TreeNodeVo.class));
			} else {
				List<TreeNodeVo> confCityVos = confCityServiceImpl.findConfCitySelectForAll();
				if (!Check.NuNCollection(confCityVos)) {
					try {
						redisOperations.setex(cityKey, RedisKeyConst.CITY_CONF_CACHE_TIME, JsonEntityTransform.Object2Json(confCityVos));
					} catch (Exception e) {
						LogUtil.error(LOGGER, "redis错误,e:{}", e);
					}
				}
				dto.putValue("list", confCityVos);
			}
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		return dto.toJsonString();
	}

	@Override
	public String getCityNameByCode(String code) {
		DataTransferObject dto = new DataTransferObject();
		try {
			String cityName = confCityServiceImpl.getCityNameByCode(code);
			dto.putValue("cityName", cityName);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		return dto.toJsonString();
	}


	/**
	 * 根据城市codeList获取城市名称
	 * @author lishaochuan
	 * @create 2016年5月26日上午12:08:40
	 * @param paramJson
	 * @return
	 */
	@Override
	public String getCityNameByCodeList(String paramJson) {
		List<String> codeList = JsonEntityTransform.json2ObjectList(paramJson, String.class);

		DataTransferObject dto = new DataTransferObject();
		try {
			List<ConfCityEntity> cityList = confCityServiceImpl.getCityNameByCodeList(codeList);
			dto.putValue("cityList", cityList);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		return dto.toJsonString();
	}



	/* (non-Javadoc)
	 * @see com.ziroom.minsu.services.basedata.api.inner.ConfCityService#getConfCityByCode(java.lang.String)
	 */
	@Override
	public String getConfCityByCode(String code) {
		DataTransferObject dto = new DataTransferObject();
		ConfCityEntity cityEntity=confCityServiceImpl.getConfCityByCode(code);
		dto.putValue("cityEntity", cityEntity);
		return dto.toJsonString();
	}
	
	@Override
	public String getOpenCityWithFile() {
		DataTransferObject dto = new DataTransferObject();
		/*String key = RedisKeyConst.getConfigKey("openCityWithFile");
		String listJson= null;
		try {
			listJson=redisOperations.get(key);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "redis错误,e:{}",e);
		}

		//判断缓存是否存在
		if(!StringUtils.isBlank(listJson)){
			return listJson;
		}*/
		List<ConfCityEntity> cityList = new ArrayList<>();
		try {
			cityList = confCityServiceImpl.getOpenCityWithFile();
			dto.putValue("list", tranCityList(cityList));
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		
		/*if(!Check.NuNCollection(cityList)){
			//当城市列表不为空更新到缓存
			try {
				redisOperations.setex(key.toString(), RedisKeyConst.CONF_CACHE_TIME, dto.toJsonString());
			} catch (Exception e) {
				LogUtil.error(LOGGER, "redis错误,e:{}",e);
			}

		}*/
		return dto.toJsonString();
	}


	@Override
	public String getCityFeatureHouseTypes(String countryCode, String provinceCode, String cityCode) {
		DataTransferObject dto = new DataTransferObject();
		try {
			List<CityFeatureHousetypeEntity> list = confCityServiceImpl.getCityFeatureHouseTypes(countryCode, provinceCode, cityCode);
			dto.putValue("result", list);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.ERROR_CODE));
		}
		return dto.toJsonString();
	}


	@Override
	public String getAllCityFeatureHouseTypes() {
		DataTransferObject dto = new DataTransferObject();
		try {
			List<CityFeatureHouseVo> list = confCityServiceImpl.getAllCityFeatureHouseTypes();
			dto.putValue("result", list);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.ERROR_CODE));
		}
		return dto.toJsonString();
	}


	@Override
	public String getAllFeatureTags(Boolean isValid,String templateFid) {
		 
		DataTransferObject dto = new DataTransferObject();
		try {
			List<FeatureTagsVo> list = confCityServiceImpl.getAllFeatureTags(isValid,templateFid);
			dto.putValue("result", list);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "getAllFeatureTags error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.ERROR_CODE));
		}
		return dto.toJsonString();		
	}


	@Override
	public String updateFeatureTagByFid(String paramJson) {
		DataTransferObject dto = new DataTransferObject();
		try {			
			CityFeatureHousetypeEntity entity = JsonEntityTransform.json2Entity(paramJson, CityFeatureHousetypeEntity.class);
			Integer num = confCityServiceImpl.updateFeatureTagByFid(entity);
			dto.putValue("result", num);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "getAllFeatureTags error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.ERROR_CODE));
		}
		return dto.toJsonString();	
	}


	@Override
	public String addFeatureTag(String paramJson) {
		DataTransferObject dto = new DataTransferObject();
		try {			
			CityFeatureHousetypeEntity entity = JsonEntityTransform.json2Entity(paramJson, CityFeatureHousetypeEntity.class);
			Integer num = confCityServiceImpl.addFeatureTag(entity);
			dto.putValue("result", num);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "getAllFeatureTags error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.ERROR_CODE));
		}
		return dto.toJsonString();	
	}


	/* (non-Javadoc)
	 * @see com.ziroom.minsu.services.basedata.api.inner.ConfCityService#findNationCodeList()
	 */
	@Override
	public String findNationCodeList() {
		DataTransferObject dto = new DataTransferObject();
		try {			
			List<NationCodeEntity> list=confCityServiceImpl.findNationCodeList();
			dto.putValue("list", list);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "getAllFeatureTags error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.ERROR_CODE));
		}
		return dto.toJsonString();	
	}
}
