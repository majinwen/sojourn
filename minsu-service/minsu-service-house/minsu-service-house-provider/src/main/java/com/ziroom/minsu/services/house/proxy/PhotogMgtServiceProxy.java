package com.ziroom.minsu.services.house.proxy;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.MessageSourceUtil;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.photographer.PhotographerBaseMsgEntity;
import com.ziroom.minsu.entity.photographer.PhotographerBaseMsgPicEntity;
import com.ziroom.minsu.services.common.constant.MessageConst;
import com.ziroom.minsu.services.house.api.inner.PhotogMgtService;
import com.ziroom.minsu.services.house.constant.HouseMessageConst;
import com.ziroom.minsu.services.house.logic.ParamCheckLogic;
import com.ziroom.minsu.services.house.logic.ValidateResult;
import com.ziroom.minsu.services.house.photog.dto.PhotogDto;
import com.ziroom.minsu.services.house.photog.dto.PhotogDetailDto;
import com.ziroom.minsu.services.house.photog.dto.PhotogPicDto;
import com.ziroom.minsu.services.house.photog.dto.PhotogRequestDto;
import com.ziroom.minsu.services.house.service.PhotogMgtServiceImpl;

/**
 * <p>摄影师管理代理类</p>
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
@Component("photog.photogMgtServiceProxy")
public class PhotogMgtServiceProxy implements PhotogMgtService {

	private static final Logger LOGGER = LoggerFactory.getLogger(PhotogMgtServiceProxy.class);

	@Resource(name="photog.photogMgtServiceImpl")
	private PhotogMgtServiceImpl photogMgtServiceImpl;

	@Resource(name="house.messageSource")
	private MessageSource messageSource;
	
	@Resource(name="house.paramCheckLogic")
	private ParamCheckLogic paramCheckLogic;

	/**
	 * 分页查询摄影师信息
	 */
	@Override
	public String findPhotographerListByPage(String paramJson) {
		LogUtil.info(LOGGER, "参数:paramJson={}", paramJson);
		//1 参数校验
		ValidateResult<PhotogRequestDto> validateResult =
				paramCheckLogic.checkParamValidate(paramJson, PhotogRequestDto.class);
		if (!validateResult.isSuccess()) {
			LogUtil.error(LOGGER, validateResult.getDto().getMsg());
			return validateResult.getDto().toJsonString();
		}
		PhotogRequestDto photogDto = validateResult.getResultObj();
		
		DataTransferObject dto = new DataTransferObject();
		try {
			PagingResult<PhotographerBaseMsgEntity> pagingResult = photogMgtServiceImpl.findPhotographerListByPage(photogDto);
			dto.putValue("total", pagingResult.getTotal());
	        dto.putValue("list", pagingResult.getRows());
		} catch (Exception e) {
			LogUtil.error(LOGGER, "findPhotographerListByPage error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}

	/**
	 * 新增摄影师信息
	 */
	@Override
	public String insertPhotographerMsg(String paramJson) {
		LogUtil.info(LOGGER, "参数:paramJson={}", paramJson);
		//1 参数校验
		ValidateResult<PhotogDto> validateResult =
				paramCheckLogic.checkParamValidate(paramJson, PhotogDto.class);
		if (!validateResult.isSuccess()) {
			LogUtil.error(LOGGER, validateResult.getDto().getMsg());
			return validateResult.getDto().toJsonString();
		}
		PhotogDto photogDto = validateResult.getResultObj();
		
		DataTransferObject dto = new DataTransferObject();
		PhotographerBaseMsgEntity base = photogDto.getBase();
		if (Check.NuNObj(base)) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
        	dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.PARAM_NULL));
        	LogUtil.error(LOGGER, dto.toJsonString());
        	return dto.toJsonString();
		}
		
		if (Check.NuNStr(base.getMobile())) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.PARAM_NULL));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}
		
		if (Check.NuNObj(base.getPhotographerStatu())) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.PARAM_NULL));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}
		
		try {
			int upNum = photogMgtServiceImpl.insertPhotographerMsg(photogDto);
			dto.putValue("upNum", upNum);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "insertPhotographerMsg error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}

	/**
	 * 根据摄影师uid查询信息	
	 */
	@Override
	public String findPhotographerMsgByUid(String photographerUid) {
		LogUtil.info(LOGGER, "参数:photographerUid={}", photographerUid);
		DataTransferObject dto = new DataTransferObject();
		
		if (Check.NuNStr(photographerUid)) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.PARAM_NULL));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}
		try {
			PhotogDetailDto photogDetailDto = photogMgtServiceImpl.findPhotogDetailByUid(photographerUid);
			dto.putValue("obj", photogDetailDto);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "findPhotographerMsgByUid error:{}", e);
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg(e.getMessage());
		}
		LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}

	/**
	 * 修改摄影师信息
	 */
	@Override
	public String updatePhotographerMsg(String paramJson) {
		LogUtil.info(LOGGER, "参数:paramJson={}", paramJson);
		//1 参数校验
		ValidateResult<PhotogDto> validateResult =
				paramCheckLogic.checkParamValidate(paramJson, PhotogDto.class);
		if (!validateResult.isSuccess()) {
			LogUtil.error(LOGGER, validateResult.getDto().getMsg());
			return validateResult.getDto().toJsonString();
		}
		PhotogDto photogDto = validateResult.getResultObj();
		
		DataTransferObject dto = new DataTransferObject();
		PhotographerBaseMsgEntity base = photogDto.getBase();
		if (Check.NuNStr(base.getPhotographerUid())) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.PARAM_NULL));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}
		
		try {
			int upNum = photogMgtServiceImpl.updatePhotographerMsg(photogDto);
			dto.putValue("upNum", upNum);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "updatePhotographerMsg error:{}", e);
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg(e.getMessage());
		}
		LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}

	/**
	 * 根据uid和picType查询摄影师图片
	 */
	@Override
	public String findPhotogPicByUidAndType(String paramJson) {
		LogUtil.info(LOGGER, "参数:paramJson={}", paramJson);
		//1 参数校验
		ValidateResult<PhotogPicDto> validateResult =
				paramCheckLogic.checkParamValidate(paramJson, PhotogPicDto.class);
		if (!validateResult.isSuccess()) {
			LogUtil.error(LOGGER, validateResult.getDto().getMsg());
			return validateResult.getDto().toJsonString();
		}
		PhotogPicDto picDto = validateResult.getResultObj();
		DataTransferObject dto = new DataTransferObject();
		
		if (Check.NuNStr(picDto.getPhotographerUid()) || Check.NuNObj(picDto.getPicType())) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.PARAM_NULL));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}
		
		try {
			PhotographerBaseMsgPicEntity picEntity = photogMgtServiceImpl.findPhotogPicByUidAndType(picDto);
			dto.putValue("obj", picEntity);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "findPhotogPicByUidAndType error:{}", e);
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg(e.getMessage());
		}
		LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}

	/**
	 * 修改摄影师图片信息
	 */
	@Override
	public String updatePhotographerPicMsg(String paramJson) {
		LogUtil.info(LOGGER, "参数:paramJson={}", paramJson);
		//1 参数校验
		ValidateResult<PhotographerBaseMsgPicEntity> validateResult =
				paramCheckLogic.checkParamValidate(paramJson, PhotographerBaseMsgPicEntity.class);
		if (!validateResult.isSuccess()) {
			LogUtil.error(LOGGER, validateResult.getDto().getMsg());
			return validateResult.getDto().toJsonString();
		}
		PhotographerBaseMsgPicEntity picEntity = validateResult.getResultObj();
		
		DataTransferObject dto = new DataTransferObject();
		if (Check.NuNStr(picEntity.getFid())) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.PARAM_NULL));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}
		
		try {
			int upNum = photogMgtServiceImpl.updatePhotographerPicMsg(picEntity);
			dto.putValue("upNum", upNum);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "updatePhotographerMsg error:{}", e);
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg(e.getMessage());
		}
		LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}

	/**
	 * 新增摄影师图片信息
	 */
	@Override
	public String insertPhotographerPicMsg(String paramJson) {
		LogUtil.info(LOGGER, "参数:paramJson={}", paramJson);
		//1 参数校验
		ValidateResult<PhotographerBaseMsgPicEntity> validateResult =
				paramCheckLogic.checkParamValidate(paramJson, PhotographerBaseMsgPicEntity.class);
		if (!validateResult.isSuccess()) {
			LogUtil.error(LOGGER, validateResult.getDto().getMsg());
			return validateResult.getDto().toJsonString();
		}
		PhotographerBaseMsgPicEntity picEntity = validateResult.getResultObj();
		
		DataTransferObject dto = new DataTransferObject();
		if (Check.NuNStr(picEntity.getPhotographerUid()) || Check.NuNObj(picEntity.getPicType())
				|| Check.NuNStr(picEntity.getPicServerUuid())) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.PARAM_NULL));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}
		
		try {
			int upNum = photogMgtServiceImpl.insertPhotographerPicMsg(picEntity);
			dto.putValue("upNum", upNum);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "updatePhotographerMsg error:{}", e);
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg(e.getMessage());
		}
		LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
		
	}

}