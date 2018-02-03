package com.ziroom.minsu.services.basedata.proxy;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.ziroom.minsu.services.basedata.entity.RegionExtVo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.MessageSourceUtil;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.cache.redisOne.RedisOperations;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.conf.HotRegionEntity;
import com.ziroom.minsu.services.basedata.api.inner.HotRegionService;
import com.ziroom.minsu.services.basedata.dto.HotRegionRequest;
import com.ziroom.minsu.services.basedata.logic.ParamCheckLogic;
import com.ziroom.minsu.services.basedata.service.ConfCityServiceImpl;
import com.ziroom.minsu.services.basedata.service.HotRegionServiceImpl;
import com.ziroom.minsu.services.common.constant.MessageConst;
import com.ziroom.minsu.services.common.utils.RedisKeyConst;

/**
 * 
 * <p>热门区域管理操作</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author liujun
 * @since 1.0
 * @version 1.0
 */
@Component("basedata.hotRegionServiceProxy")
public class HotRegionServiceProxy implements HotRegionService{

	private static final Logger LOGGER = LoggerFactory.getLogger(HotRegionServiceProxy.class);

	@Resource(name="basedata.hotRegionServiceImpl")
	private HotRegionServiceImpl hotRegionServiceImpl;
	
	@Resource(name="basedata.confCityServiceImpl")
	private ConfCityServiceImpl confCityServiceImpl;

	@Resource(name="basedata.messageSource")
	private MessageSource messageSource;

	@Resource(name="basedata.paramCheckLogic")
	private ParamCheckLogic paramCheckLogic;
	
	@Autowired
	private RedisOperations redisOperations;

	/* (non-Javadoc)
	 * @see com.ziroom.minsu.services.basedata.api.inner.HotRegionService#searchHotRegions(java.lang.String)
	 */
	@Override
	public String searchHotRegions(String paramJson) {
		DataTransferObject dto = new DataTransferObject();
		try {
			HotRegionRequest hotRegionRequest = JsonEntityTransform.json2Object(paramJson, HotRegionRequest.class);
			// 条件查询后台用户
			PagingResult<HotRegionEntity> pr = hotRegionServiceImpl.findHotRegionPageList(hotRegionRequest);
			dto.putValue("list", pr.getRows());
			dto.putValue("total", pr.getTotal());
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		return dto.toJsonString();
	}

	/* (non-Javadoc)
	 * @see com.ziroom.minsu.services.basedata.api.inner.HotRegionService#addHotRegion(java.lang.String)
	 */
	@Override
	public String saveHotRegion(String paramJson) {
		DataTransferObject dto = new DataTransferObject();
		try {
			HotRegionEntity hotRegion = JsonEntityTransform.json2Object(paramJson, HotRegionEntity.class);
			hotRegion.setFid(UUIDGenerator.hexUUID());
			hotRegion.setCreateDate(new Date());
			// 保存热门区域
			hotRegionServiceImpl.saveHotRegion(hotRegion);
			
			// 变更景点商圈刷新缓存
			String key = RedisKeyConst.getConfigKey("validRadiiMap");
			redisOperations.del(key);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		return dto.toJsonString();
	}

	/* (non-Javadoc)
	 * @see com.ziroom.minsu.services.basedata.api.inner.HotRegionService#updateHotRegion(java.lang.String)
	 */
	@Override
	public String updateHotRegion(String paramJson) {
		DataTransferObject dto = new DataTransferObject();
		try {
			HotRegionEntity hotRegion = JsonEntityTransform.json2Object(paramJson, HotRegionEntity.class);
			// 保存热门区域
			hotRegionServiceImpl.updateHotRegion(hotRegion);
			
			// 变更景点商圈刷新缓存
			String key = RedisKeyConst.getConfigKey("validRadiiMap");
			redisOperations.del(key);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		return dto.toJsonString();
	}

	/* (non-Javadoc)
	 * @see com.ziroom.minsu.services.basedata.api.inner.HotRegionService#searchHotRegionByFid(java.lang.String)
	 */
	@Override
	public String searchHotRegionByFid(String hotRegionFid) {
		DataTransferObject dto = new DataTransferObject();
		if (Check.NuNStr(hotRegionFid)) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
			return dto.toJsonString();
		}
		try {
			HotRegionEntity hotRegion = hotRegionServiceImpl.findHotRegionByFid(hotRegionFid);
			if(hotRegion == null){
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.NOT_FOUND));
				return dto.toJsonString();
			}
			dto.putValue("hotRegion", hotRegion);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		return dto.toJsonString();
	}

	/* (non-Javadoc)
	 * @see com.ziroom.minsu.services.basedata.api.inner.HotRegionService#editHotRegionStatus(java.lang.String)
	 */
	@Override
	public String editHotRegionStatus(String hotRegionJson) {
		DataTransferObject dto = new DataTransferObject();
		HotRegionEntity hotRegion = JsonEntityTransform.json2Object(hotRegionJson, HotRegionEntity.class);
		if(hotRegion.getRegionStatus() == null) {
			hotRegion.setRegionStatus(0);
		}
		hotRegion.setRegionStatus(1^hotRegion.getRegionStatus());
		hotRegion.setLastModifyDate(new Date());
		
		try {
			hotRegionServiceImpl.updateHotRegion(hotRegion);
			
			// 变更景点商圈刷新缓存
			String key = RedisKeyConst.getConfigKey("validRadiiMap");
			redisOperations.del(key);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto = new DataTransferObject();
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		return dto.toJsonString();
	}

	/**
	 * 根据城市code查询已建立档案的景点商圈列表
	 */
	@Override
	public String getListWithFileByCityCode(String cityCode) {
		DataTransferObject dto = new DataTransferObject();
		if (Check.NuNStr(cityCode)) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
			return dto.toJsonString();
		}
		try {
			List<HotRegionEntity> list = hotRegionServiceImpl.getListWithFileByCityCode(cityCode);
			if(Check.NuNCollection(list)){
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.NOT_FOUND));
				return dto.toJsonString();
			}
			dto.putValue("list", list);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		return dto.toJsonString();
	}

	/**
	 * 获取景点商圈的有效区域半径集合
	 */
	@Override
	public String getValidRadiiMap() {
		DataTransferObject dto = new DataTransferObject();
		String key = RedisKeyConst.getConfigKey("validRadiiMap");
		String listJson= null;
		try {
			listJson = redisOperations.get(key);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "redis错误,e:{}",e);
		}

		//判断缓存是否存在
		if(!StringUtils.isBlank(listJson)){
			return listJson;
		}
		List<HotRegionEntity> list = new ArrayList<>();
		try {
			list = hotRegionServiceImpl.findHotRegionsWithValidRadii();
			dto.putValue("map", tranList2Map(list));
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		
		if(!Check.NuNCollection(list)){
			try {
				redisOperations.setex(key.toString(), RedisKeyConst.CONF_CACHE_TIME, dto.toJsonString());
			} catch (Exception e) {
				LogUtil.error(LOGGER, "redis错误,e:{}",e);
			}

		}
		return dto.toJsonString();
	}

	/**
	 * 将景点商圈列表转换为有效区域半径集合
	 *
	 * @author liujun
	 * @created 2016年11月12日
	 *
	 * @param list
	 * @return
	 */
	private Map<String, Integer> tranList2Map(List<HotRegionEntity> list) {
		Map<String, Integer> map = new HashMap<>();
		if (!Check.NuNCollection(list)) {
			for (HotRegionEntity hotRegionEntity : list) {
				if (!Check.NuNObj(hotRegionEntity.getRadii())) {
					map.put(hotRegionEntity.getFid(), hotRegionEntity.getRadii());
				}
			}
		}
		return map;
	}

	/**
	 * 查询景点商圈以及其内容和描述
	 * @Author lunan【lun14@ziroom.com】
	 * @Date 2016/11/16 17:57
	 */
	@Override
	public String getRegionExtVoByRegionFid(String regionFid) {
		LogUtil.info(LOGGER, "传入的景点商圈fid:{}", regionFid);
		DataTransferObject dto = new DataTransferObject();
		if(Check.NuNObj(regionFid)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
			return dto.toJsonString();
		}
		RegionExtVo regionExtVo = hotRegionServiceImpl.getRegionExtVoByRegionFid(regionFid);
		dto.putValue("regionExtVo", regionExtVo);
		return dto.toJsonString();
	}

}
