/**
 * @FileName: CityFileProxy.java
 * @Package com.ziroom.minsu.services.cms.proxy
 * 
 * @author bushujie
 * @created 2016年11月7日 下午7:38:39
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.cms.proxy;

import java.util.List;

import javax.annotation.Resource;

import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.SOAResParseUtil;
import com.ziroom.minsu.entity.conf.HotRegionEntity;
import com.ziroom.minsu.entity.file.FileRegionEntity;
import com.ziroom.minsu.services.basedata.api.inner.CityArchiveService;
import com.ziroom.minsu.services.basedata.api.inner.HotRegionService;
import com.ziroom.minsu.services.cms.entity.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.MessageSourceUtil;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.cms.ColumnCityEntity;
import com.ziroom.minsu.entity.cms.ColumnTemplateEntity;
import com.ziroom.minsu.services.cms.api.inner.CityFileService;
import com.ziroom.minsu.services.cms.dto.ColumnCityRequest;
import com.ziroom.minsu.services.cms.dto.ColumnRegionAddRequest;
import com.ziroom.minsu.services.cms.dto.ColumnRegionRequest;
import com.ziroom.minsu.services.cms.dto.ColumnTemplateRequest;
import com.ziroom.minsu.services.cms.dto.OrderSortUpRequest;
import com.ziroom.minsu.services.cms.service.ColumnCityServiceImpl;
import com.ziroom.minsu.services.cms.service.ColumnRegionServiceImpl;
import com.ziroom.minsu.services.cms.service.ColumnTemplateServiceImpl;
import com.ziroom.minsu.services.common.constant.MessageConst;

/**
 * <p>城市档案相关接口实现</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author bushujie
 * @since 1.0
 * @version 1.0
 */
@Service("cms.CityFileProxy")
public class CityFileProxy implements CityFileService{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CityFileProxy.class);
	
	@Resource(name = "cms.messageSource")
	private MessageSource messageSource;
	
	@Resource(name="cms.columnTemplateServiceImpl")
	private ColumnTemplateServiceImpl columnTemplateServiceImpl;
	
	@Resource(name="cms.columnCityServiceImpl")
	private ColumnCityServiceImpl columnCityServiceImpl;
	
	@Resource(name="cms.columnRegionServiceImpl")
	private ColumnRegionServiceImpl columnRegionServiceImpl;

	@Resource(name="basedata.cityArchiveService")
	private CityArchiveService cityArchiveService;

	@Resource(name="basedata.hotRegionService")
	private HotRegionService hotRegionService;

	@Value("#{'${PIC_JUMP_URL}'.trim()}")
	private String PIC_JUMP_PRE;

	/* (non-Javadoc)
	 * @see com.ziroom.minsu.services.cms.api.inner.CityFileService#columnTemplateList(java.lang.String)
	 */
	@Override
	public String columnTemplateList(String paramJson) {
		DataTransferObject dto=new DataTransferObject();
		try {
			ColumnTemplateRequest templateRequest=JsonEntityTransform.json2Object(paramJson, ColumnTemplateRequest.class);
			PagingResult<ColumnTemplateVo> pagingResult=columnTemplateServiceImpl.findColumnTemplateList(templateRequest);
			dto.putValue("tempList", pagingResult.getRows());
			dto.putValue("count", pagingResult.getTotal());
		}catch(Exception e){
        	LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		return dto.toJsonString();
	}

	/* (non-Javadoc)
	 * @see com.ziroom.minsu.services.cms.api.inner.CityFileService#insertColumnTemplate(java.lang.String)
	 */
	@Override
	public String insertColumnTemplate(String paramJson) {
		DataTransferObject dto=new DataTransferObject();
		try {
			ColumnTemplateEntity columnTemplateEntity=JsonEntityTransform.json2Object(paramJson, ColumnTemplateEntity.class);
			columnTemplateServiceImpl.insertColumnTemplate(columnTemplateEntity);
		}catch(Exception e){
        	LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		return dto.toJsonString();
	}

	/* (non-Javadoc)
	 * @see com.ziroom.minsu.services.cms.api.inner.CityFileService#getColumnTemplateByFid(java.lang.String)
	 */
	@Override
	public String getColumnTemplateByFid(String tempFid) {
		DataTransferObject dto=new DataTransferObject();
		try {
			ColumnTemplateEntity columnTemplateEntity=columnTemplateServiceImpl.getColumnTemplateEntityByFid(tempFid);
			dto.putValue("template", columnTemplateEntity);
		}catch(Exception e){
        	LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		return dto.toJsonString();
	}

	/* (non-Javadoc)
	 * @see com.ziroom.minsu.services.cms.api.inner.CityFileService#updateColumnTemplate(java.lang.String)
	 */
	@Override
	public String updateColumnTemplate(String paramJson) {
		DataTransferObject dto=new DataTransferObject();
		try {
			ColumnTemplateEntity columnTemplateEntity=JsonEntityTransform.json2Object(paramJson, ColumnTemplateEntity.class);
			int upNum=columnTemplateServiceImpl.updateColumnTemplate(columnTemplateEntity);
			dto.putValue("upNum", upNum);
		}catch(Exception e){
        	LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		return dto.toJsonString();
	}

	/* (non-Javadoc)
	 * @see com.ziroom.minsu.services.cms.api.inner.CityFileService#columnCityList(java.lang.String)
	 */
	@Override
	public String columnCityList(String paramJson) {
		DataTransferObject dto=new DataTransferObject();
		try {
			ColumnCityRequest columnCityRequest=JsonEntityTransform.json2Object(paramJson, ColumnCityRequest.class);
			PagingResult<ColumnCityVo> pagingResult=columnCityServiceImpl.findColumnCityList(columnCityRequest);
			dto.putValue("list", pagingResult.getRows());
			dto.putValue("count", pagingResult.getTotal());
		}catch(Exception e){
        	LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		return dto.toJsonString();
	}

	/* (non-Javadoc)
	 * @see com.ziroom.minsu.services.cms.api.inner.CityFileService#insertColumnCity(java.lang.String)
	 */
	@Override
	public String insertColumnCity(String paramJson) {
		DataTransferObject dto=new DataTransferObject();
		try {
			ColumnCityEntity columnCityEntity=JsonEntityTransform.json2Object(paramJson, ColumnCityEntity.class);
			columnCityServiceImpl.insertColumnCity(columnCityEntity);
		}catch(Exception e){
        	LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		return dto.toJsonString();
	}

	/* (non-Javadoc)
	 * @see com.ziroom.minsu.services.cms.api.inner.CityFileService#getColumnCityByFid(java.lang.String)
	 */
	@Override
	public String getColumnCityByFid(String fid) {
		DataTransferObject dto=new DataTransferObject();
		try {
			ColumnCityEntity columnCityEntity=columnCityServiceImpl.getColumnCityByFid(fid);
			dto.putValue("columnCity", columnCityEntity);
		}catch(Exception e){
        	LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		return dto.toJsonString();
	}

	/* (non-Javadoc)
	 * @see com.ziroom.minsu.services.cms.api.inner.CityFileService#updateColumnCity(java.lang.String)
	 */
	@Override
	public String updateColumnCity(String paramJson) {
		DataTransferObject dto=new DataTransferObject();
		try {
			ColumnCityEntity columnCityEntity=JsonEntityTransform.json2Object(paramJson, ColumnCityEntity.class);
			int upNum=columnCityServiceImpl.updateColumnCity(columnCityEntity);
			dto.putValue("upNum", upNum);
		}catch(Exception e){
        	LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		return dto.toJsonString();
	}

	/* (non-Javadoc)
	 * @see com.ziroom.minsu.services.cms.api.inner.CityFileService#findAllRegTemplate()
	 */
	@Override
	public String findAllRegTemplate() {
		DataTransferObject dto=new DataTransferObject();
		try {
			List<ColumnTemplateEntity> list=columnTemplateServiceImpl.findAllRegTemplate();
			dto.putValue("list", list);
		}catch(Exception e){
        	LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		return dto.toJsonString();
	}

	/* (non-Javadoc)
	 * @see com.ziroom.minsu.services.cms.api.inner.CityFileService#columnRegionList()
	 */
	@Override
	public String columnRegionList(String paramJson) {
		DataTransferObject dto=new DataTransferObject();
		try {
			PagingResult<ColumnRegionVo> pagingResult=columnRegionServiceImpl.findColumnRegionList(JsonEntityTransform.json2Object(paramJson, ColumnRegionRequest.class));
			dto.putValue("list", pagingResult.getRows());
			dto.putValue("count", pagingResult.getTotal());
		}catch(Exception e){
        	LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		return dto.toJsonString();
	}

	/* (non-Javadoc)
	 * @see com.ziroom.minsu.services.cms.api.inner.CityFileService#insertColumnRegion(java.lang.String)
	 */
	@Override
	public String insertColumnRegion(String paramJson) {
		DataTransferObject dto=new DataTransferObject();
		try {
			ColumnRegionAddRequest columnRegionAddRequest=JsonEntityTransform.json2Object(paramJson, ColumnRegionAddRequest.class);
			columnRegionServiceImpl.insertColumnRegion(columnRegionAddRequest);
		}catch(Exception e){
        	LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		return dto.toJsonString();
	}

	/* (non-Javadoc)
	 * @see com.ziroom.minsu.services.cms.api.inner.CityFileService#initUpColumnRegion(java.lang.String)
	 */
	@Override
	public String initUpColumnRegion(String fid) {
		DataTransferObject dto=new DataTransferObject();
		try {
			ColumnRegionUpVo columnRegionUpVo=columnRegionServiceImpl.findColumnRegionUpVo(fid);
			dto.putValue("regionUpVo", columnRegionUpVo);
		}catch(Exception e){
        	LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		return dto.toJsonString();
	}

	/* (non-Javadoc)
	 * @see com.ziroom.minsu.services.cms.api.inner.CityFileService#updateColumnRegion(java.lang.String)
	 */
	@Override
	public String updateColumnRegion(String paramJson) {
		DataTransferObject dto=new DataTransferObject();
		try {
			ColumnRegionAddRequest columnRegionAddRequest=JsonEntityTransform.json2Object(paramJson, ColumnRegionAddRequest.class);
			columnRegionServiceImpl.updateColumnRegion(columnRegionAddRequest);
		}catch(Exception e){
        	LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		return dto.toJsonString();
	}

	/* (non-Javadoc)
	 * @see com.ziroom.minsu.services.cms.api.inner.CityFileService#delColumnRegion(java.lang.String)
	 */
	@Override
	public String delColumnRegion(String fid) {
		DataTransferObject dto=new DataTransferObject();
		try {
			columnRegionServiceImpl.delColumnRegion(fid);
		}catch(Exception e){
        	LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		return dto.toJsonString();
	}

	/**
	 * 查询城市及下辖专栏
	 * @Author lunan【lun14@ziroom.com】
	 * @Date 2016/11/17 20:22
	 */
	@Override
	public String getCityRegionsByCityCode(String cityCode) {
		DataTransferObject dto=new DataTransferObject();
		try{
			if(Check.NuNObj(cityCode)){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("未传入cityCode");
				return dto.toJsonString();
			}
			FileRegionsVo fileRegionsVo = columnCityServiceImpl.getCityRegionsByCityCode(cityCode);
			if(!Check.NuNObj(fileRegionsVo)){
				List<ColumnRegionPicVo> regionList = fileRegionsVo.getRegionList();
				for(ColumnRegionPicVo regionVo : regionList){
					//根据regionfid查询region
					String hotRegion = hotRegionService.searchHotRegionByFid(regionVo.getRegionFid());
					HotRegionEntity hotRegionEntity = SOAResParseUtil.getValueFromDataByKey(hotRegion, "hotRegion", HotRegionEntity.class);
					regionVo.setRegionName(hotRegionEntity.getRegionName());
					//根据regionFid查询描述
					FileRegionEntity fileRegionEntity = new FileRegionEntity();
					fileRegionEntity.setHotRegionFid(regionVo.getRegionFid());
					String region = cityArchiveService.getRegion(JsonEntityTransform.Object2Json(fileRegionEntity));
					DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(region);
					if(resultDto.getCode()!=DataTransferObject.ERROR){
						FileRegionEntity fileRegion = SOAResParseUtil.getValueFromDataByKey(region,"region",FileRegionEntity.class);
						regionVo.setRegionBrief(fileRegion.getHotRegionBrief());
					}
					//设置图片跳转路径
					regionVo.setJumpUrl(PIC_JUMP_PRE+fileRegionsVo.getFid()+"/"+regionVo.getFid()+".html");
				}
			}
			dto.putValue("fileRegions",fileRegionsVo);
		}catch (Exception e){
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		return dto.toJsonString();
	}
	
	@Override
	public String findColumnRegionUpVoListByCityFid(String columnCityFid) {
		DataTransferObject dto=new DataTransferObject();
		try {
			List<ColumnRegionUpVo> list=columnRegionServiceImpl.findColumnRegionUpVoListByCityFid(columnCityFid);
			dto.putValue("list", list);
		}catch(Exception e){
        	LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		return dto.toJsonString();
	}

	/* (non-Javadoc)
	 * @see com.ziroom.minsu.services.cms.api.inner.CityFileService#upColumnRegionOrderSort(java.lang.String)
	 */
	@Override
	public String upColumnRegionOrderSort(String paramJson) {
		DataTransferObject dto=new DataTransferObject();
		try{
			OrderSortUpRequest orderSortUpRequest=JsonEntityTransform.json2Object(paramJson, OrderSortUpRequest.class);
			columnRegionServiceImpl.upColumnRegionOrderSort(orderSortUpRequest);
		}catch(Exception e){
        	LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		return dto.toJsonString();
	}
}
