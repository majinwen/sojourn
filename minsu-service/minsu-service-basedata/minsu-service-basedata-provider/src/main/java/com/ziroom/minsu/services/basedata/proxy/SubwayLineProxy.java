package com.ziroom.minsu.services.basedata.proxy;

import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.MessageSourceUtil;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.conf.SubwayLineEntity;
import com.ziroom.minsu.entity.conf.SubwayOutEntity;
import com.ziroom.minsu.entity.conf.SubwayStationEntity;
import com.ziroom.minsu.services.basedata.api.inner.SubwayLineService;
import com.ziroom.minsu.services.basedata.dto.SubwayLineRequest;
import com.ziroom.minsu.services.basedata.dto.SubwayLineSaveRequest;
import com.ziroom.minsu.services.basedata.entity.SubwayLineVo;
import com.ziroom.minsu.services.basedata.service.SubwayLineServiceImpl;
import com.ziroom.minsu.services.common.conf.EnumMinsuConfig;
import com.ziroom.minsu.services.common.constant.MessageConst;
import com.ziroom.minsu.services.common.utils.ZkUtil;

@Component("basedata.subwayLineProxy")
public class SubwayLineProxy implements SubwayLineService {

	private static final Logger LOGGER = LoggerFactory.getLogger(SubwayLineService.class);

	@Resource(name = "basedata.messageSource")
	private MessageSource messageSource;

	@Resource(name = "basedata.subwayLineServiceImpl")
	private SubwayLineServiceImpl subwayLineServiceImpl;

	/**
	 * 按条件分页查询地铁线路
	 * 
	 * @param paramJson
	 * @return
	 */
	@Override
	public String findSubwayLinePage(String paramJson) {
		DataTransferObject dto = new DataTransferObject();
		try {
			SubwayLineRequest subwayLineRequest = JsonEntityTransform.json2Object(paramJson, SubwayLineRequest.class);
			// 条件查询后台用户
			PagingResult<SubwayLineVo> pr = subwayLineServiceImpl.findSubwayLinePageList(subwayLineRequest);
			dto.putValue("list", pr.getRows());
			dto.putValue("total", pr.getTotal());
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		return dto.toJsonString();
	}

	/**
	 * 查询地铁线路、站点、出口
	 * 
	 * @param paramJson
	 * @return
	 */
	@Override
	public String findSubwayInfo(String paramJson) {
		DataTransferObject dto = new DataTransferObject();
		try {
			Map map = JsonEntityTransform.json2Map(paramJson);

			SubwayLineEntity subwayLineEntity = new SubwayLineEntity();
			subwayLineEntity.setFid((String) map.get("lineFid"));
			SubwayLineEntity subwayLine = subwayLineServiceImpl.findSubwayLineByFid(subwayLineEntity);

			SubwayStationEntity subwayStationEntity = new SubwayStationEntity();
			subwayStationEntity.setFid((String) map.get("stationFid"));
			SubwayStationEntity subwayStation = subwayLineServiceImpl.findSubwayStationByFid(subwayStationEntity);

			SubwayOutEntity subwayOutEntity = new SubwayOutEntity();
			subwayOutEntity.setFid((String) map.get("outFid"));
			SubwayOutEntity subwayOut = subwayLineServiceImpl.findSubwayOutByFid(subwayOutEntity);

			dto.putValue("subwayLine", subwayLine);
			dto.putValue("subwayStation", subwayStation);
			dto.putValue("subwayOut", subwayOut);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		return dto.toJsonString();
	}

	/**
	 * 保存地铁线路
	 * 
	 * @param paramJson
	 * @return
	 */
	@Override
	public String saveSubwayLine(String paramJson) {
		DataTransferObject dto = new DataTransferObject();
		try {
			// 非空校验
			if (Check.NuNObj(paramJson)) {
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
				return dto.toJsonString();
			}
			SubwayLineSaveRequest subwayLineSaveRequest = JsonEntityTransform.json2Object(paramJson, SubwayLineSaveRequest.class);
			if (Check.NuNObj(subwayLineSaveRequest)) {
				LogUtil.info(LOGGER, "subwayLineSaveRequest:{}", subwayLineSaveRequest);
				throw new BusinessException("subwayLineSaveRequest is error on insert");
			}

			String mapType = ZkUtil.getZkSysValue(EnumMinsuConfig.minsu_mapType.getType(), EnumMinsuConfig.minsu_mapType.getCode());
			subwayLineSaveRequest.setMapType(mapType);
			subwayLineServiceImpl.queryAndSaveSubwayLine(subwayLineSaveRequest);

		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
			return dto.toJsonString();
		}
		return dto.toJsonString();
	}

	/**
	 * 修改地铁线路
	 * 
	 * @param paramJson
	 * @return
	 */
	@Override
	public String updateSubwayLine(String paramJson) {
		DataTransferObject dto = new DataTransferObject();
		try {
			// 非空校验
			if (Check.NuNObj(paramJson)) {
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
				return dto.toJsonString();
			}
			SubwayLineSaveRequest subwayLineSaveRequest = JsonEntityTransform.json2Object(paramJson, SubwayLineSaveRequest.class);
			if (Check.NuNObj(subwayLineSaveRequest)) {
				LogUtil.info(LOGGER, "subwayLineSaveRequest:{}", subwayLineSaveRequest);
				throw new BusinessException("subwayLineSaveRequest is error on insert");
			}

			subwayLineServiceImpl.updateSubwayLine(subwayLineSaveRequest);

		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
			return dto.toJsonString();
		}
		return dto.toJsonString();
	}

}
