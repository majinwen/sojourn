package com.ziroom.minsu.services.house.proxy;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.MessageSourceUtil;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.house.HouseSurveyMsgEntity;
import com.ziroom.minsu.entity.house.HouseSurveyPicMsgEntity;
import com.ziroom.minsu.services.common.constant.MessageConst;
import com.ziroom.minsu.services.house.api.inner.HouseSurveyService;
import com.ziroom.minsu.services.house.constant.HouseMessageConst;
import com.ziroom.minsu.services.house.logic.ParamCheckLogic;
import com.ziroom.minsu.services.house.logic.ValidateResult;
import com.ziroom.minsu.services.house.service.HouseSurveyServiceImpl;
import com.ziroom.minsu.services.house.service.TroyHouseMgtServiceImpl;
import com.ziroom.minsu.services.house.survey.dto.SurveyPicDto;
import com.ziroom.minsu.services.house.survey.dto.SurveyRequestDto;
import com.ziroom.minsu.services.house.survey.entity.HouseSurveyVo;
import com.ziroom.minsu.valenum.common.YesOrNoEnum;

/**
 * <p>
 * 房源实勘操作接口proxy
 * </p>
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
@Component("house.houseSurveyServiceProxy")
public class HouseSurveyServiceProxy implements HouseSurveyService {

	private static final Logger LOGGER = LoggerFactory.getLogger(HouseSurveyServiceProxy.class);
	@Resource(name = "house.troyHouseMgtServiceImpl")
	private TroyHouseMgtServiceImpl troyHouseMgtServiceImpl;

	@Resource(name = "house.houseSurveyServiceImpl")
	private HouseSurveyServiceImpl houseSurveyServiceImpl;

	@Resource(name = "house.messageSource")
	private MessageSource messageSource;

	@Resource(name = "house.paramCheckLogic")
	private ParamCheckLogic paramCheckLogic;

	/**
	 * 根据房源fid查询需要实勘房源信息
	 */
	@Override
	public String findSurveyHouseListByPage(String paramJson) {
		LogUtil.info(LOGGER, "参数:{}", paramJson);
		// 1 参数校验
		ValidateResult<SurveyRequestDto> validateResult = paramCheckLogic.checkParamValidate(paramJson, SurveyRequestDto.class);
		if (!validateResult.isSuccess()) {
			LogUtil.error(LOGGER, validateResult.getDto().getMsg());
			return validateResult.getDto().toJsonString();
		}
		SurveyRequestDto requestDto = validateResult.getResultObj();
		DataTransferObject dto = new DataTransferObject();

		try {
			PagingResult<HouseSurveyVo> pagingResult = houseSurveyServiceImpl.findSurveyHouseListByPage(requestDto);
			dto.putValue("list", pagingResult.getRows());
			dto.putValue("total", pagingResult.getTotal());
		} catch (Exception e) {
			LogUtil.error(LOGGER, "findSurveyHouseListByPage error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}

	/**
	 * 查询需要实勘房源列表
	 */
	@Override
	public String findHouseSurveyMsgByHouseFid(String houseBaseFid) {
		LogUtil.info(LOGGER, "参数:houseBaseFid={}", houseBaseFid);
		DataTransferObject dto = new DataTransferObject();
		// 校验房源逻辑id不能为空
		if (Check.NuNStr(houseBaseFid)) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_BASE_FID_NULL));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}

		try {
			HouseSurveyMsgEntity houseSurveyMsgEntity = houseSurveyServiceImpl.findHouseSurveyMsgByHouseFid(houseBaseFid);
			dto.putValue("obj", houseSurveyMsgEntity);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "findHouseSurveyMsgByHouseFid error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}

	/**
	 * 新增房源实勘信息
	 */
	@Override
	public String insertHouseSurveyMsg(String paramJson) {
		LogUtil.info(LOGGER, "参数:{}", paramJson);
		// 1 参数校验
		ValidateResult<HouseSurveyMsgEntity> validateResult = paramCheckLogic.checkParamValidate(paramJson, HouseSurveyMsgEntity.class);
		if (!validateResult.isSuccess()) {
			LogUtil.error(LOGGER, validateResult.getDto().getMsg());
			return validateResult.getDto().toJsonString();
		}
		HouseSurveyMsgEntity surveyMsgEntity = validateResult.getResultObj();
		DataTransferObject dto = new DataTransferObject();

		if (Check.NuNStr(surveyMsgEntity.getHouseBaseFid())) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_BASE_FID_NULL));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}

		try {
			int upNum = houseSurveyServiceImpl.insertHouseSurveyMsg(surveyMsgEntity);
			dto.putValue("upNum", upNum);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "insertHouseSurveyMsg error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}

	/**
	 * 更新房源实勘信息
	 */
	@Override
	public String updateHouseSurveyMsg(String paramJson) {
		LogUtil.info(LOGGER, "参数:{}", paramJson);
		// 1 参数校验
		ValidateResult<HouseSurveyMsgEntity> validateResult = paramCheckLogic.checkParamValidate(paramJson, HouseSurveyMsgEntity.class);
		if (!validateResult.isSuccess()) {
			LogUtil.error(LOGGER, validateResult.getDto().getMsg());
			return validateResult.getDto().toJsonString();
		}
		HouseSurveyMsgEntity newEntity = validateResult.getResultObj();
		DataTransferObject dto = new DataTransferObject();

		if (Check.NuNStr(newEntity.getFid())) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.PARAM_NULL));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}
		
		if (!Check.NuNObj(newEntity.getOperateType()) && Check.NuNStr(newEntity.getOperateFid())) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.PARAM_NULL));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}

		try {
			HouseSurveyMsgEntity oldEntity = houseSurveyServiceImpl.findHouseSurveyMsgByFid(newEntity.getFid());
			if (Check.NuNObj(oldEntity)) {
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg("房源实勘信息不存在");
				LogUtil.error(LOGGER, dto.toJsonString());
				return dto.toJsonString();
			}

			if (!Check.NuNObj(oldEntity.getIsAudit()) && oldEntity.getIsAudit().intValue() == YesOrNoEnum.YES.getCode()) {
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg("房源实勘信息已审阅");
				LogUtil.error(LOGGER, dto.toJsonString());
				return dto.toJsonString();
			}
			
			if (Check.NuNStr(newEntity.getHouseBaseFid())) {
				newEntity.setHouseBaseFid(oldEntity.getHouseBaseFid());
			}

			int upNum = houseSurveyServiceImpl.updateHouseSurveyMsg(newEntity, oldEntity);
			dto.putValue("upNum", upNum);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "updateHouseSurveyMsg error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}

	/**
	 * 根据房源实勘图片fid查询房源实勘图片信息
	 */
	@Override
	public String findHouseSurveyPicMsgByFid(String surveyPicFid) {
		LogUtil.info(LOGGER, "参数:surveyPicFid={}", surveyPicFid);
		DataTransferObject dto = new DataTransferObject();
		if (Check.NuNStr(surveyPicFid)) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.PARAM_NULL));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}

		try {
			HouseSurveyPicMsgEntity houseSurveyPicMsgEntity = houseSurveyServiceImpl.findHouseSurveyPicMsgByFid(surveyPicFid);
			dto.putValue("obj", houseSurveyPicMsgEntity);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "findHouseSurveyPicMsgByFid error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}
	
	/**
	 * 根据房源实勘fid与图片类型查询房源实勘图片列表
	 */
	@Override
	public String findSurveyPicListByType(String paramJson) {
		LogUtil.info(LOGGER, "参数:{}", paramJson);
		// 1 参数校验
		ValidateResult<SurveyPicDto> validateResult = paramCheckLogic.checkParamValidate(paramJson, SurveyPicDto.class);
		if (!validateResult.isSuccess()) {
			LogUtil.error(LOGGER, validateResult.getDto().getMsg());
			return validateResult.getDto().toJsonString();
		}
		SurveyPicDto picDto = validateResult.getResultObj();
		DataTransferObject dto = new DataTransferObject();
		if (Check.NuNStr(picDto.getSurveyFid())) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.PARAM_NULL));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}

		try {
			List<HouseSurveyPicMsgEntity> surveyPicList = houseSurveyServiceImpl.findSurveyPicListByType(picDto);
			dto.putValue("list", surveyPicList);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "findHouseSurveyPicMsgByFid error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}

	/**
	 * 新增房源实勘图片信息
	 */
	@Override
	public String saveSurveyPicMsgList(String paramJson) {
		LogUtil.info(LOGGER, "参数:{}", paramJson);
		List<HouseSurveyPicMsgEntity> list = JsonEntityTransform.json2List(paramJson, HouseSurveyPicMsgEntity.class);
		DataTransferObject dto = new DataTransferObject();

		if (Check.NuNCollection(list)) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.PARAM_NULL));
			LogUtil.info(LOGGER, dto.toJsonString());
		}
		try {
			//注意:限制图片数量类型在controller中实现
			int upNum = 0;
			for (HouseSurveyPicMsgEntity surveyPicMsgEntity : list) {
				if (Check.NuNStr(surveyPicMsgEntity.getSurveyFid()) || Check.NuNStr(surveyPicMsgEntity.getPicServerUuid())
						|| Check.NuNObj(surveyPicMsgEntity.getPicType())) {
					dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
					dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.PARAM_NULL));
					LogUtil.info(LOGGER, dto.toJsonString());
				}
				upNum += houseSurveyServiceImpl.insertHouseSurveyPicMsg(surveyPicMsgEntity);
			}
			dto.putValue("upNum", upNum);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "saveSurveyPicMsgList error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}

	/**
	 * 更新房源实勘图片信息
	 */
	@Override
	public String updateHouseSurveyPicMsg(String paramJson) {
		LogUtil.info(LOGGER, "参数:{}", paramJson);
		// 1 参数校验
		ValidateResult<HouseSurveyPicMsgEntity> validateResult = paramCheckLogic.checkParamValidate(paramJson,
				HouseSurveyPicMsgEntity.class);
		if (!validateResult.isSuccess()) {
			LogUtil.error(LOGGER, validateResult.getDto().getMsg());
			return validateResult.getDto().toJsonString();
		}
		HouseSurveyPicMsgEntity surveyPicMsgEntity = validateResult.getResultObj();
		DataTransferObject dto = new DataTransferObject();

		if (Check.NuNStr(surveyPicMsgEntity.getFid())) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, HouseMessageConst.PARAM_NULL));
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto.toJsonString();
		}

		try {
			int upNum = houseSurveyServiceImpl.updateHouseSurveyPicMsg(surveyPicMsgEntity);
			dto.putValue("upNum", upNum);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "updateHouseSurveyPicMsg error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}

	/**
	 * 根据图片类型查询图片数量
	 */
	@Override
	public String findPicCountByType(String paramJson) {
		LogUtil.debug(LOGGER, "参数:{}", paramJson);
		// 1 参数校验
		ValidateResult<SurveyPicDto> validateResult = paramCheckLogic.checkParamValidate(paramJson, SurveyPicDto.class);
		if (!validateResult.isSuccess()) {
			LogUtil.error(LOGGER, validateResult.getDto().getMsg());
			return validateResult.getDto().toJsonString();
		}
		SurveyPicDto surveyPicDto = validateResult.getResultObj();
		DataTransferObject dto = new DataTransferObject();
		try {
			long count = houseSurveyServiceImpl.findPicCountByType(surveyPicDto);
			dto.putValue("count", count);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		LogUtil.debug(LOGGER, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}

}
