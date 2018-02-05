/**
 * @FileName: LandlordHouseMController.java
 * @Package com.ziroom.minsu.api.house.controller
 *
 * @author bushujie
 * @created 2016年4月18日 下午1:48:43
 *
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.api.house.controller;


import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.*;
import com.asura.framework.utils.LogUtil;
import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifDirectoryBase;
import com.drew.metadata.exif.ExifIFD0Directory;
import com.drew.metadata.jfif.JfifDirectory;
import com.drew.metadata.jpeg.JpegDirectory;
import com.ziroom.minsu.api.common.constant.ApiConst;
import com.ziroom.minsu.api.common.constant.ApiMessageConst;
import com.ziroom.minsu.api.common.constant.ConstDef;
import com.ziroom.minsu.api.common.dto.BaseParamDto;
import com.ziroom.minsu.api.common.dto.ResponseSecurityDto;
import com.ziroom.minsu.api.common.logic.ParamCheckLogic;
import com.ziroom.minsu.api.common.logic.ValidateResult;
import com.ziroom.minsu.api.common.util.BaseMethodUtil;
import com.ziroom.minsu.api.house.dto.CalendarParamDto;
import com.ziroom.minsu.api.house.dto.PicParamDto;
import com.ziroom.minsu.api.house.dto.RoomParamDto;
import com.ziroom.minsu.api.house.dto.UpDownHouseDto;
import com.ziroom.minsu.api.house.service.HouseService;
import com.ziroom.minsu.entity.house.HouseBaseExtEntity;
import com.ziroom.minsu.entity.house.HouseBaseMsgEntity;
import com.ziroom.minsu.entity.house.HousePicMsgEntity;
import com.ziroom.minsu.entity.house.HouseRoomMsgEntity;
import com.ziroom.minsu.entity.house.TonightDiscountEntity;
import com.ziroom.minsu.entity.order.HouseLockEntity;
import com.ziroom.minsu.services.basedata.api.inner.CityTemplateService;
import com.ziroom.minsu.services.common.utils.DataFormat;
import com.ziroom.minsu.services.common.utils.DateSplitUtil;
import com.ziroom.minsu.services.common.utils.JsonTransform;
import com.ziroom.minsu.services.common.utils.PicUtil;
import com.ziroom.minsu.services.house.api.inner.HouseIssueService;
import com.ziroom.minsu.services.house.api.inner.HouseManageService;
import com.ziroom.minsu.services.house.api.inner.HouseUpdateHistoryLogService;
import com.ziroom.minsu.services.house.api.inner.TroyHouseMgtService;
import com.ziroom.minsu.services.house.dto.*;
import com.ziroom.minsu.services.house.entity.*;
import com.ziroom.minsu.services.order.api.inner.HouseCommonService;
import com.ziroom.minsu.services.order.api.inner.OrderUserService;
import com.ziroom.minsu.services.order.dto.HouseLockRequest;
import com.ziroom.minsu.services.order.dto.LockHouseRequest;
import com.ziroom.minsu.valenum.house.CreaterTypeEnum;
import com.ziroom.minsu.valenum.house.HousePicTypeEnum;
import com.ziroom.minsu.valenum.house.HouseStatusEnum;
import com.ziroom.minsu.valenum.house.IsValidEnum;
import com.ziroom.minsu.valenum.house.RentWayEnum;
import com.ziroom.minsu.valenum.order.LockTypeEnum;
import com.ziroom.tech.storage.client.domain.FileInfoResponse;
import com.ziroom.tech.storage.client.service.StorageService;

import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.*;

/**
 * <p>房东房源管理</p>
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
@Controller
@RequestMapping("/house")
public class LandlordHouseController {


	/**
	 * 日志对象
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(LandlordHouseController.class);

	private static final String DATE_FORMAT_PATTERN = "yyyy-MM-dd";

	private static final String DATE_FORMAT_MONTH = "yyyy-MM";

	@Resource(name="house.houseManageService")
	private HouseManageService houseManageService;

	@Resource(name="house.troyHouseMgtService")
	private TroyHouseMgtService troyHouseMgtService;

	@Resource(name="house.houseIssueService")
	private HouseIssueService houseIssueService;

	@Resource(name="order.orderUserService")
	private OrderUserService orderUserService;

	@Resource(name="storageService")
	private StorageService storageService;

	@Resource(name="api.messageSource")
	private MessageSource messageSource;

	@Resource(name="api.paramCheckLogic")
	private ParamCheckLogic paramCheckLogic;

	@Value("#{'${pic_base_addr}'.trim()}")
	private String picBaseAddr;

	@Value("#{'${detail_big_pic}'.trim()}")
	private String detailBigPic;

	@Value("#{'${list_small_pic}'.trim()}")
	private String list_small_pic;

	@Value("#{'${pic_base_addr_mona}'.trim()}")
	private String picBaseAddrMona;

	@Value("#{'${storage_key}'.trim()}")
	private String storageKey;

	@Value("#{'${storage_limit}'.trim()}")
	private String storageLimit;

	@Resource(name="basedata.cityTemplateService")
	private CityTemplateService cityTemplateService;


	@Value("#{'${upper_limit_rate}'.trim()}")
	private String upperLimitRate;

	@Value("#{'${lower_limit_rate}'.trim()}")
	private String lowerLimitRate;

	@Resource(name = "order.houseCommonService")
	private HouseCommonService houseCommonService;
	
	@Resource(name = "api.houseService")
	private HouseService houseService;
	
	@Resource(name = "house.houseUpdateHistoryLogService")
	private HouseUpdateHistoryLogService houseUpdateHistoryLogService;

	/**
	 * <p>描述:</p>
	 * <p>&nbsp; &nbsp; &nbsp; &nbsp;<b>查询房东房源所在小区列表</b></p>
	 * <p>请求示例：<b>/house/ea61d2/communityNameList?2y5QfvAy=60d6a7090c1da858de1b9ea973e6938f61a178050137a70981f8c8cb53971fc2f26d13b70ec62a860bfecbd0c80e056bac7bb04b58281ebe37436a547f2405f0bef7dd706d13f64cd0823cc89a95af16466899d00d7905decf035f8a7acc5f4e&hPtJ39Xs=2b26f0241a53c3137f5d13c677cda7bb</b></p>
	 * <p>请求参数：<b>{"loginToken":"loginToken","uid":"8a9e9a8b53d6da8b0153d6da8bae0000","limit":50,"page":1}</b></p>
	 * <p>返回结果示例:<b>
	 *     {"status":0,"message":"","data":{"list":[{"termValue":"8a9e9a9e540e428f01540e428f4b0000","termName":"王府井小区"}],"houseStatusList":[{"termValue":"10","termName":"待发布"},{"termValue":"11","termName":"已发布"},{"termValue":"20","termName":"信息审核通过"},{"termValue":"21","termName":"信息审核未通过"},{"termValue":"30","termName":"照片审核未通过"},{"termValue":"40","termName":"上架"},{"termValue":"41","termName":"下架"},{"termValue":"50","termName":"强制下架"}]}}
	 * </b></p>
	 * <div>
	 * <table width='100%' cellpadding='1' cellspacing='1' bgcolor='gray'>
	 *     <tr bgcolor="#DDDDDD" style="font-size: 12px; ">
	 *         <td colspan="4" textAlign="center" >入参明细</td>
	 *     </tr>
	 *     <tr bgcolor="#DDDDDD" style="font-size: 12px;">
	 *         <td>参数名</td>
	 *         <td>类型</td>
	 *         <td>是否必须(是或否)</td>
	 *         <td>含义</td>
	 *     </tr>
	 *     <tr bgcolor="#DDDDDD" style="font-size: 12px;">
	 *         <td>loginToken</td>
	 *         <td>String</td>
	 *         <td>是</td>
	 *         <td>登录令牌</td>
	 *     </tr>
	 *     <tr bgcolor="#DDDDDD" style="font-size: 12px;">
	 *         <td>uid</td>
	 *         <td>String</td>
	 *         <td>是</td>
	 *         <td>登录用户逻辑id</td>
	 *     </tr>
	 *     <tr bgcolor="#DDDDDD" style="font-size: 12px;">
	 *         <td>page</td>
	 *         <td>int</td>
	 *         <td>是</td>
	 *         <td>请求页码</td>
	 *     </tr>
	 *     <tr bgcolor="#DDDDDD" style="font-size: 12px;">
	 *         <td>limit</td>
	 *         <td>int</td>
	 *         <td>是</td>
	 *         <td>每页显示条数</td>
	 *     </tr>
	 * </table>
	 *
	 * <table width='100%' cellpadding='1' cellspacing='1' bgcolor='gray'>
	 *     <tr bgcolor="#DDDDDD" style="font-size: 12px; ">
	 *         <td colspan="4" textAlign="center">返回结果明细</td>
	 *     </tr>
	 *     <tr bgcolor="#DDDDDD" style="font-size: 12px;">
	 *         <td>参数名</td>
	 *         <td>类型</td>
	 *         <td>是否必须(是或否)</td>
	 *         <td>含义</td>
	 *     </tr>
	 *     <tr bgcolor="#DDDDDD" style="font-size: 12px;">
	 *         <td>status</td>
	 *         <td>int</td>
	 *         <td>是</td>
	 *         <td>调用状态(0:成功,1:失败)</td>
	 *     </tr>
	 *     <tr bgcolor="#DDDDDD" style="font-size: 12px;">
	 *         <td>message</td>
	 *         <td>String</td>
	 *         <td>否</td>
	 *         <td>调用信息</td>
	 *     </tr>
	 *     <tr bgcolor="#DDDDDD" style="font-size: 12px;">
	 *         <td>data.list</td>
	 *         <td>int</td>
	 *         <td>是</td>
	 *         <td>小区名称集合</td>
	 *     </tr>
	 *     <tr bgcolor="#DDDDDD" style="font-size: 12px;">
	 *         <td>data.houseStatusList</td>
	 *         <td>list</td>
	 *         <td>是</td>
	 *         <td>房源状态集合(10:待发布,11:已发布,20:信息审核通过,21:信息审核未通过,30:照片审核未通过,40:上架,41:下架,50:强制下架)</td>
	 *     </tr>
	 * </table>
	 * </div>
	 *
	 * @author bushujie
	 * @created 2016年4月18日 下午9:17:22
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/${LOGIN_AUTH}/communityNameList")
	@ResponseBody
	public ResponseEntity<ResponseSecurityDto> communityNameList(HttpServletRequest request){
		try{
			String paramJson = (String) request.getAttribute(ConstDef.DECRYPT_PARAM_ATTRIBUTE);
			LogUtil.info(LOGGER, "参数：" + paramJson);

			ValidateResult<BaseParamDto> validateResult = paramCheckLogic.checkParamValidate(paramJson,
					BaseParamDto.class);
			if (!validateResult.isSuccess()) {
				LogUtil.error(LOGGER, "错误信息:{},参数:{}", validateResult.getDto().getMsg(), paramJson);
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail(validateResult.getDto().getMsg()),
						HttpStatus.OK);
			}
			BaseParamDto baseParamDto = validateResult.getResultObj();

			String resultJson = houseManageService.communityNameList(baseParamDto.getUid());
			DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(resultJson);
			//判断调用状态
			if (dto.getCode() == ApiConst.OPERATION_FAILURE) {
				LogUtil.error(LOGGER, "调用接口失败,uid={}", baseParamDto.getUid());
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail(dto.getMsg()), HttpStatus.OK);
			}

			LogUtil.debug(LOGGER, "结果：" + resultJson);
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptOK(dto.getData()), HttpStatus.OK);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("服务错误"), HttpStatus.OK);
		}
	}

	/**
	 * <p>描述:</p>
	 * <p>&nbsp; &nbsp; &nbsp; &nbsp;<b>查询房东房源列表</b></p>
	 * <p>请求示例：<b>/house/ea61d2/houseRoomList?2y5QfvAy=60d6a7090c1da858de1b9ea973e6938f61a178050137a70981f8c8cb53971fc2f26d13b70ec62a860bfecbd0c80e056bac7bb04b58281ebe37436a547f2405f0bef7dd706d13f64cd0823cc89a95af16201286e6a5fa73140697b4d2f71d1dc3aefdc6ee05b0c0bcf0b2e8fd74ba068dd89b25e7d3180b54c21eb14fa1b144d96c968d9dfd46b3d151b00c22efceb0f3ead4325dbc8e6eb1f3788ad33e15ffaf&hPtJ39Xs=3273c49e1c6ccb064e223cbfcb634a1f</b></p>
	 * <p>请求参数：<b>{"loginToken":"loginToken","uid":"8a9e9a8b53d6da8b0153d6da8bae0000","limit":50,"page":1,"houseStatus":50,"housePhyFid":"8a9e9aae5419cc22015419cc24e60001"}</b></p>
	 * <p>返回结果示例:<b>
	 *     {"status":0,"message":"","data":{"total":1,"list":[{"houseBaseFid":"8a9e9aae5419cc22015419cc250a0002","houseRoomFid":"8a9e9aae5419d73b015419d73ddb0001","rentWay":0,"houseStatus":50,"roomStatus":null,"houseName":"普天实业创新园","roomName":"幸福小屋","defaultPic":"group1/M00/00/08/ChAiMFcQ_a6AEXylAAIXNrMc2qU231.jpg","houseBookRate":null,"intactRate":0.0,"starRating":null,"houseAddr":"北京市朝阳区将台路5号院16号楼","zoName":"卜书杰","zoMobile":"255888000"}]}}
	 * </b></p>
	 * <div>
	 * <table width='100%' cellpadding='1' cellspacing='1' bgcolor='gray'>
	 *     <tr bgcolor="#DDDDDD" style="font-size: 12px; ">
	 *         <td colspan="4" textAlign="center" >入参明细</td>
	 *     </tr>
	 *     <tr bgcolor="#DDDDDD" style="font-size: 12px;">
	 *         <td>参数名</td>
	 *         <td>类型</td>
	 *         <td>是否必须(是或否)</td>
	 *         <td>含义</td>
	 *     </tr>
	 *     <tr bgcolor="#DDDDDD" style="font-size: 12px;">
	 *         <td>loginToken</td>
	 *         <td>String</td>
	 *         <td>是</td>
	 *         <td>登录令牌</td>
	 *     </tr>
	 *     <tr bgcolor="#DDDDDD" style="font-size: 12px;">
	 *         <td>uid</td>
	 *         <td>String</td>
	 *         <td>是</td>
	 *         <td>登录用户逻辑id</td>
	 *     </tr>
	 *     <tr bgcolor="#DDDDDD" style="font-size: 12px;">
	 *         <td>page</td>
	 *         <td>int</td>
	 *         <td>是</td>
	 *         <td>请求页码</td>
	 *     </tr>
	 *     <tr bgcolor="#DDDDDD" style="font-size: 12px;">
	 *         <td>limit</td>
	 *         <td>int</td>
	 *         <td>是</td>
	 *         <td>每页显示条数</td>
	 *     </tr>
	 *     <tr bgcolor="#DDDDDD" style="font-size: 12px;">
	 *         <td>houseStatus</td>
	 *         <td>int</td>
	 *         <td>否</td>
	 *         <td>房源状态(10:待发布,11:已发布,20:信息审核通过,21:信息审核未通过,30:照片审核未通过,40:上架,41:下架,50:强制下架)</td>
	 *     </tr>
	 *     <tr bgcolor="#DDDDDD" style="font-size: 12px;">
	 *         <td>housePhyFid</td>
	 *         <td>String</td>
	 *         <td>否</td>
	 *         <td>房源物理信息逻辑id</td>
	 *     </tr>
	 * </table>
	 *
	 * <table width='100%' cellpadding='1' cellspacing='1' bgcolor='gray'>
	 *     <tr bgcolor="#DDDDDD" style="font-size: 12px; ">
	 *         <td colspan="4" textAlign="center">返回结果明细</td>
	 *     </tr>
	 *     <tr bgcolor="#DDDDDD" style="font-size: 12px;">
	 *         <td>参数名</td>
	 *         <td>类型</td>
	 *         <td>是否必须(是或否)</td>
	 *         <td>含义</td>
	 *     </tr>
	 *     <tr bgcolor="#DDDDDD" style="font-size: 12px;">
	 *         <td>status</td>
	 *         <td>int</td>
	 *         <td>是</td>
	 *         <td>调用状态(0:成功,1:失败)</td>
	 *     </tr>
	 *     <tr bgcolor="#DDDDDD" style="font-size: 12px;">
	 *         <td>message</td>
	 *         <td>String</td>
	 *         <td>否</td>
	 *         <td>调用信息</td>
	 *     </tr>
	 *     <tr bgcolor="#DDDDDD" style="font-size: 12px;">
	 *         <td>data.total</td>
	 *         <td>int</td>
	 *         <td>是</td>
	 *         <td>查询结果条数</td>
	 *     </tr>
	 *     <tr bgcolor="#DDDDDD" style="font-size: 12px;">
	 *         <td>data.list</td>
	 *         <td>list</td>
	 *         <td>是</td>
	 *         <td>房源信息集合</td>
	 *     </tr>
	 * </table>
	 * </div>
	 *
	 * @author liujun
	 * @created 2016年4月21日 上午11:36:26
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/${LOGIN_AUTH}/houseRoomList")
	@ResponseBody
	public ResponseEntity<ResponseSecurityDto> houseRoomList(HttpServletRequest request) {
		try {
			String paramJson = (String) request.getAttribute(ConstDef.DECRYPT_PARAM_ATTRIBUTE);
			LogUtil.info(LOGGER, "参数：" + paramJson);

			ValidateResult<RoomParamDto> validateResult =
					paramCheckLogic.checkParamValidate(paramJson, RoomParamDto.class);
			if (!validateResult.isSuccess()) {
				LogUtil.error(LOGGER, "错误信息:{},参数:{}", validateResult.getDto().getMsg(), paramJson);
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail(validateResult.getDto().getMsg()),
						HttpStatus.OK);
			}
			RoomParamDto requestDto = validateResult.getResultObj();

			HouseBaseListDto paramDto = new HouseBaseListDto();
			paramDto.setHousePhyFid(requestDto.getHousePhyFid());
			paramDto.setLandlordUid(requestDto.getUid());
			paramDto.setHouseStatus(requestDto.getHouseStatus());
			paramDto.setPage(requestDto.getPage());
			paramDto.setLimit(requestDto.getLimit());

			String resultJson = houseManageService.searchHouseRoomList(JsonEntityTransform.Object2Json(paramDto));
			DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(resultJson);
			//判断调用状态
			if (dto.getCode() == ApiConst.OPERATION_FAILURE) {
				LogUtil.error(LOGGER, "调用接口失败,参数:{}", JsonEntityTransform.Object2Json(paramDto));
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail(dto.getMsg()), HttpStatus.OK);
			}

			LogUtil.debug(LOGGER, "结果：" + resultJson);
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptOK(dto.getData()), HttpStatus.OK);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("服务错误"), HttpStatus.OK);
		}
	}

	/**
	 * <p>描述:</p>
	 * <p>&nbsp; &nbsp; &nbsp; &nbsp;<b>房东设置特殊价格</b></p>
	 * <p>请求示例：<b>/house/ea61d2/setSpecialPrice?2y5QfvAy=60d6a7090c1da858de1b9ea973e6938f61a178050137a70981f8c8cb53971fc2f26d13b70ec62a860bfecbd0c80e056bac7bb04b58281ebe37436a547f2405f0bef7dd706d13f64cd0823cc89a95af16201286e6a5fa73144e5d1d6ae3012e85a9de96dbc5bf4bf3ab08f78bc4342bf11cca18b3ebed48ace614dee02ae5f53a7d024d1882867f673c8988ab81d01816c87ecd9bb1a038605e6303b4fb53fecdc618c5933ef12097cec5282357a38e4fc8697126213392871c9833bc224bfd714ae8ff313fc559ea35d5e7eb75bee52ebbf08630cfda98ff&hPtJ39Xs=f13dcd122416dfc46fcb3ca21d345d86</b></p>
	 * <p>请求参数：<b>{"loginToken":"loginToken","uid":"8a9e9a8b53d6da8b0153d6da8bae0000","houseBaseFid":"8a9e9aae5419cc22015419cc250a0002","houseRoomFid":null,"rentWay":0,"setTime":"2016-4-30","specialPrice":100}</b></p>
	 * <p>返回结果示例:<b>
	 *     {"status":0,"message":"","data":{}}
	 * </b></p>
	 * <div>
	 * <table width='100%' cellpadding='1' cellspacing='1' bgcolor='gray'>
	 *     <tr bgcolor="#DDDDDD" style="font-size: 12px; ">
	 *         <td colspan="4" textAlign="center" >入参明细</td>
	 *     </tr>
	 *     <tr bgcolor="#DDDDDD" style="font-size: 12px;">
	 *         <td>参数名</td>
	 *         <td>类型</td>
	 *         <td>是否必须(是或否)</td>
	 *         <td>含义</td>
	 *     </tr>
	 *     <tr bgcolor="#DDDDDD" style="font-size: 12px;">
	 *         <td>loginToken</td>
	 *         <td>String</td>
	 *         <td>是</td>
	 *         <td>登录令牌</td>
	 *     </tr>
	 *     <tr bgcolor="#DDDDDD" style="font-size: 12px;">
	 *         <td>uid</td>
	 *         <td>String</td>
	 *         <td>是</td>
	 *         <td>登录用户逻辑id</td>
	 *     </tr>
	 *     <tr bgcolor="#DDDDDD" style="font-size: 12px;">
	 *         <td>rentWay</td>
	 *         <td>int</td>
	 *         <td>是</td>
	 *         <td>出租方式(0:整租,1:合租,2:床位)</td>
	 *     </tr>
	 *     <tr bgcolor="#DDDDDD" style="font-size: 12px;">
	 *         <td>houseStatus</td>
	 *         <td>int</td>
	 *         <td>是</td>
	 *         <td>房源状态:</td>
	 *     </tr>
	 *     <tr bgcolor="#DDDDDD" style="font-size: 12px;">
	 *         <td>setTime</td>
	 *         <td>String</td>
	 *         <td>是</td>
	 *         <td>设置特殊价格日期(格式:yyyy-MM-dd)</td>
	 *     </tr>
	 *     <tr bgcolor="#DDDDDD" style="font-size: 12px;">
	 *         <td>houseBaseFid</td>
	 *         <td>String</td>
	 *         <td>否</td>
	 *         <td>房源逻辑id(整租必传,合租不传)</td>
	 *     </tr>
	 *     <tr bgcolor="#DDDDDD" style="font-size: 12px;">
	 *         <td>houseRoomFid</td>
	 *         <td>String</td>
	 *         <td>否</td>
	 *         <td>房间逻辑id(整租不传,合租必传)</td>
	 *     </tr>
	 * </table>
	 *
	 * <table width='100%' cellpadding='1' cellspacing='1' bgcolor='gray'>
	 *     <tr bgcolor="#DDDDDD" style="font-size: 12px; ">
	 *         <td colspan="4" textAlign="center">返回结果明细</td>
	 *     </tr>
	 *     <tr bgcolor="#DDDDDD" style="font-size: 12px;">
	 *         <td>参数名</td>
	 *         <td>类型</td>
	 *         <td>是否必须(是或否)</td>
	 *         <td>含义</td>
	 *     </tr>
	 *     <tr bgcolor="#DDDDDD" style="font-size: 12px;">
	 *         <td>status</td>
	 *         <td>int</td>
	 *         <td>是</td>
	 *         <td>调用状态(0:成功,1:失败)</td>
	 *     </tr>
	 *     <tr bgcolor="#DDDDDD" style="font-size: 12px;">
	 *         <td>message</td>
	 *         <td>String</td>
	 *         <td>否</td>
	 *         <td>调用信息</td>
	 *     </tr>
	 * </table>
	 * </div>
	 *
	 * @author liujun
	 * @created 2016年4月21日 下午6:38:13
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/${LOGIN_AUTH}/setSpecialPrice")
	@ResponseBody
	public ResponseEntity<ResponseSecurityDto> setSpecialPrice(HttpServletRequest request) {
		try {
			String paramJson = (String) request.getAttribute(ConstDef.DECRYPT_PARAM_ATTRIBUTE);
			LogUtil.info(LOGGER, "参数：" + paramJson);

			ValidateResult<SpecialPriceDto> validateResult =
					paramCheckLogic.checkParamValidate(paramJson, SpecialPriceDto.class);
			if (!validateResult.isSuccess()) {
				LogUtil.error(LOGGER, "错误信息:{},参数:{}", validateResult.getDto().getMsg(), paramJson);
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail(validateResult.getDto().getMsg()),
						HttpStatus.OK);
			}
			SpecialPriceDto requestDto = validateResult.getResultObj();

			List<HousePriceConfDto> housePriceList=new ArrayList<HousePriceConfDto>();
			for(String setTime:requestDto.getSetTime()){
				HousePriceConfDto paramDto = new HousePriceConfDto();
				paramDto.setHouseBaseFid(requestDto.getHouseBaseFid());
				paramDto.setRoomFid(requestDto.getHouseRoomFid());
				paramDto.setRentWay(requestDto.getRentWay());
				BigDecimal price= new BigDecimal(requestDto.getSpecialPrice()+"");
				BigDecimal setPrice=price.multiply(new BigDecimal("100"));
				paramDto.setPriceVal(setPrice.intValue());
				paramDto.setSetTime(DateUtil.parseDate(setTime, DATE_FORMAT_PATTERN));
				paramDto.setCreateUid(request.getParameter("uid"));
				housePriceList.add(paramDto);
			}

			String resultJson = houseManageService.setSpecialPrice(JsonEntityTransform.Object2Json(housePriceList));
			DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(resultJson);
			//判断调用状态
			if (dto.getCode() == ApiConst.OPERATION_FAILURE) {
				LogUtil.error(LOGGER, "调用接口失败,参数:{}", JsonEntityTransform.Object2Json(housePriceList));
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail(dto.getMsg()), HttpStatus.OK);
			}

			LogUtil.debug(LOGGER, "结果：" + resultJson);
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptOK(dto.getData()), HttpStatus.OK);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("服务错误"), HttpStatus.OK);
		}
	}

	/**
	 * <p>描述:</p>
	 * <p>&nbsp; &nbsp; &nbsp; &nbsp;<b>房东锁定房源</b></p>
	 * <p>请求示例：<b>/house/ea61d2/lockHouse?2y5QfvAy=60d6a7090c1da858de1b9ea973e6938f61a178050137a70981f8c8cb53971fc2f26d13b70ec62a860bfecbd0c80e056bac7bb04b58281ebe37436a547f2405f0bef7dd706d13f64cd0823cc89a95af16201286e6a5fa73144e5d1d6ae3012e85a9de96dbc5bf4bf3ab08f78bc4342bf11cca18b3ebed48ace614dee02ae5f53a7d024d1882867f673c8988ab81d01816c87ecd9bb1a03860d360c3291b0cea4593a6e4e027f613cec463fcc071241b60db9a34292aa9ef6267e9a746c81f0a65a116181ec74be60674faa832be725429b40f70f9a65ef64bdc351558c03de7a8b234df5a1bfeee6187a8c5b5d9a5ca22&hPtJ39Xs=01a6a1e39e9be870649691b9debe3214</b></p>
	 * <p>请求参数：<b>{"loginToken":"loginToken","uid":"8a9e9a8b53d6da8b0153d6da8bae0000","houseBaseFid":"8a9e9aae5419cc22015419cc250a0002","houseRoomFid":null,"rentWay":0,"lockDateList":["2016-5-1","2016-4-30"]}</b></p>
	 * <p>返回结果示例:<b>
	 *     {"status":0,"message":"","data":{}}
	 * </b></p>
	 * <div>
	 * <table width='100%' cellpadding='1' cellspacing='1' bgcolor='gray'>
	 *     <tr bgcolor="#DDDDDD" style="font-size: 12px; ">
	 *         <td colspan="4" textAlign="center" >入参明细</td>
	 *     </tr>
	 *     <tr bgcolor="#DDDDDD" style="font-size: 12px;">
	 *         <td>参数名</td>
	 *         <td>类型</td>
	 *         <td>是否必须(是或否)</td>
	 *         <td>含义</td>
	 *     </tr>
	 *     <tr bgcolor="#DDDDDD" style="font-size: 12px;">
	 *         <td>loginToken</td>
	 *         <td>String</td>
	 *         <td>是</td>
	 *         <td>登录令牌</td>
	 *     </tr>
	 *     <tr bgcolor="#DDDDDD" style="font-size: 12px;">
	 *         <td>uid</td>
	 *         <td>String</td>
	 *         <td>是</td>
	 *         <td>登录用户逻辑id</td>
	 *     </tr>
	 *     <tr bgcolor="#DDDDDD" style="font-size: 12px;">
	 *         <td>rentWay</td>
	 *         <td>int</td>
	 *         <td>是</td>
	 *         <td>出租方式(0:整租,1:合租)</td>
	 *     </tr>
	 *     <tr bgcolor="#DDDDDD" style="font-size: 12px;">
	 *         <td>lockDateList</td>
	 *         <td>String</td>
	 *         <td>是</td>
	 *         <td>被锁日期集合</td>
	 *     </tr>
	 *     <tr bgcolor="#DDDDDD" style="font-size: 12px;">
	 *         <td>houseBaseFid</td>
	 *         <td>String</td>
	 *         <td>是</td>
	 *         <td>房源逻辑id</td>
	 *     </tr>
	 *     <tr bgcolor="#DDDDDD" style="font-size: 12px;">
	 *         <td>houseRoomFid</td>
	 *         <td>String</td>
	 *         <td>否</td>
	 *         <td>房间逻辑id(整租不传,合租必传)</td>
	 *     </tr>
	 * </table>
	 *
	 * <table width='100%' cellpadding='1' cellspacing='1' bgcolor='gray'>
	 *     <tr bgcolor="#DDDDDD" style="font-size: 12px; ">
	 *         <td colspan="4" textAlign="center">返回结果明细</td>
	 *     </tr>
	 *     <tr bgcolor="#DDDDDD" style="font-size: 12px;">
	 *         <td>参数名</td>
	 *         <td>类型</td>
	 *         <td>是否必须(是或否)</td>
	 *         <td>含义</td>
	 *     </tr>
	 *     <tr bgcolor="#DDDDDD" style="font-size: 12px;">
	 *         <td>status</td>
	 *         <td>int</td>
	 *         <td>是</td>
	 *         <td>调用状态(0:成功,1:失败)</td>
	 *     </tr>
	 *     <tr bgcolor="#DDDDDD" style="font-size: 12px;">
	 *         <td>message</td>
	 *         <td>String</td>
	 *         <td>否</td>
	 *         <td>调用信息</td>
	 *     </tr>
	 * </table>
	 * </div>
	 *
	 * @author liujun
	 * @created 2016年4月22日 下午8:41:34
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/${LOGIN_AUTH}/lockHouse")
	@ResponseBody
	public ResponseEntity<ResponseSecurityDto> lockHouse(HttpServletRequest request) {
		try {
			String paramJson = (String) request.getAttribute(ConstDef.DECRYPT_PARAM_ATTRIBUTE);
			LogUtil.info(LOGGER, "参数：" + paramJson);

			ValidateResult<HouseLockDto> validateResult =
					paramCheckLogic.checkParamValidate(paramJson, HouseLockDto.class);
			if (!validateResult.isSuccess()) {
				LogUtil.error(LOGGER, "错误信息:{},参数:{}", validateResult.getDto().getMsg(), paramJson);
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail(validateResult.getDto().getMsg()),
						HttpStatus.OK);
			}
			HouseLockDto requestDto = validateResult.getResultObj();

			LockHouseRequest paramDto = new LockHouseRequest();
			paramDto.setHouseFid(requestDto.getHouseBaseFid());
			paramDto.setRoomFid(requestDto.getHouseRoomFid());
			paramDto.setRentWay(requestDto.getRentWay());
			paramDto.setLockType(requestDto.getLockType());
			paramDto.setLockDayList(getTransformDateList(requestDto.getLockDateList()));

			String resultJson = orderUserService.lockHouse(JsonEntityTransform.Object2Json(paramDto));
			DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(resultJson);
			//判断调用状态
			if (dto.getCode() == ApiConst.OPERATION_FAILURE) {
				LogUtil.error(LOGGER, "调用接口失败,参数:{}", JsonEntityTransform.Object2Json(paramDto));
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail(dto.getMsg()), HttpStatus.OK);
			}

			LogUtil.debug(LOGGER, "结果：" + resultJson);
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptOK(dto.getData()), HttpStatus.OK);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("服务错误"), HttpStatus.OK);
		}
	}

	/**
	 *
	 * 将日期字符串集合转换为日期集合
	 *
	 * @author liujun
	 * @created 2016年4月25日 下午2:16:53
	 *
	 * @param dateStrList
	 * @return
	 * @throws ParseException
	 */
	private List<Date> getTransformDateList(List<String> dateStrList) throws ParseException {
		if (Check.NuNCollection(dateStrList)) {
			return null;
		}

		List<Date> dateList = new ArrayList<Date>();
		for (String string : dateStrList) {
			dateList.add(DateUtil.parseDate(string, DATE_FORMAT_PATTERN));
		}
		return dateList;
	}

	/**
	 * <p>描述:</p>
	 * <p>&nbsp; &nbsp; &nbsp; &nbsp;<b>查询房源出租日历</b></p>
	 * <p>请求示例：<b>/house/ea61d2/leaseCalendar?2y5QfvAy=60d6a7090c1da858de1b9ea973e6938f61a178050137a70981f8c8cb53971fc2f26d13b70ec62a860bfecbd0c80e056bac7bb04b58281ebe37436a547f2405f0bef7dd706d13f64cd0823cc89a95af16201286e6a5fa73144e5d1d6ae3012e85a9de96dbc5bf4bf3ab08f78bc4342bf11cca18b3ebed48ace614dee02ae5f53a7d024d1882867f673c8988ab81d01816c87ecd9bb1a03860d360c3291b0cea4593a6e4e027f613cec463fcc071241b60db9a34292aa9ef62604d3bea0a3a1b9ae2c54cf7716d0e6380d4417188e3eca169eea58f96deaba82752c0c3d3dbf07e351590824869a4c9&hPtJ39Xs=468bd49464d912849bf897ef533632f3</b></p>
	 * <p>请求参数：<b>{"loginToken":"loginToken","uid":"8a9e9a8b53d6da8b0153d6da8bae0000","houseBaseFid":"8a9e9aae5419cc22015419cc250a0002","houseRoomFid":null,"rentWay":0,"startDate":"2016-5-1","endDate":"2016-5-31"}</b></p>
	 * <p>返回结果示例:<b>
	 *     {"status":0,"message":"","data": [{"date":"2016-4-22","price":510,"status":1},{"date":"2016-4-23","price":510,"status":1}]}
	 * </b></p>
	 * <div>
	 * <table width='100%' cellpadding='1' cellspacing='1' bgcolor='gray'>
	 *     <tr bgcolor="#DDDDDD" style="font-size: 12px; ">
	 *         <td colspan="4" textAlign="center" >入参明细</td>
	 *     </tr>
	 *     <tr bgcolor="#DDDDDD" style="font-size: 12px;">
	 *         <td>参数名</td>
	 *         <td>类型</td>
	 *         <td>是否必须(是或否)</td>
	 *         <td>含义</td>
	 *     </tr>
	 *     <tr bgcolor="#DDDDDD" style="font-size: 12px;">
	 *         <td>loginToken</td>
	 *         <td>String</td>
	 *         <td>是</td>
	 *         <td>登录令牌</td>
	 *     </tr>
	 *     <tr bgcolor="#DDDDDD" style="font-size: 12px;">
	 *         <td>uid</td>
	 *         <td>String</td>
	 *         <td>是</td>
	 *         <td>登录用户逻辑id</td>
	 *     </tr>
	 *     <tr bgcolor="#DDDDDD" style="font-size: 12px;">
	 *         <td>rentWay</td>
	 *         <td>int</td>
	 *         <td>是</td>
	 *         <td>出租方式(0:整租,1:合租)</td>
	 *     </tr>
	 *     <tr bgcolor="#DDDDDD" style="font-size: 12px;">
	 *         <td>startDate</td>
	 *         <td>String</td>
	 *         <td>是</td>
	 *         <td>开始时间</td>
	 *     </tr>
	 *     <tr bgcolor="#DDDDDD" style="font-size: 12px;">
	 *         <td>endDate</td>
	 *         <td>String</td>
	 *         <td>是</td>
	 *         <td>结束时间</td>
	 *     </tr>
	 *     <tr bgcolor="#DDDDDD" style="font-size: 12px;">
	 *         <td>houseBaseFid</td>
	 *         <td>String</td>
	 *         <td>是</td>
	 *         <td>房源逻辑id</td>
	 *     </tr>
	 *     <tr bgcolor="#DDDDDD" style="font-size: 12px;">
	 *         <td>houseRoomFid</td>
	 *         <td>String</td>
	 *         <td>否</td>
	 *         <td>房间逻辑id(整租不传,合租必传)</td>
	 *     </tr>
	 * </table>
	 *
	 * <table width='100%' cellpadding='1' cellspacing='1' bgcolor='gray'>
	 *     <tr bgcolor="#DDDDDD" style="font-size: 12px; ">
	 *         <td colspan="4" textAlign="center">返回结果明细</td>
	 *     </tr>
	 *     <tr bgcolor="#DDDDDD" style="font-size: 12px;">
	 *         <td>参数名</td>
	 *         <td>类型</td>
	 *         <td>是否必须(是或否)</td>
	 *         <td>含义</td>
	 *     </tr>
	 *     <tr bgcolor="#DDDDDD" style="font-size: 12px;">
	 *         <td>status</td>
	 *         <td>int</td>
	 *         <td>是</td>
	 *         <td>调用状态(0:成功,1:失败)</td>
	 *     </tr>
	 *     <tr bgcolor="#DDDDDD" style="font-size: 12px;">
	 *         <td>message</td>
	 *         <td>String</td>
	 *         <td>否</td>
	 *         <td>调用信息</td>
	 *     </tr>
	 *     <tr bgcolor="#DDDDDD" style="font-size: 12px;">
	 *         <td>data.date</td>
	 *         <td>String</td>
	 *         <td>是</td>
	 *         <td>日历时间</td>
	 *     </tr>
	 *     <tr bgcolor="#DDDDDD" style="font-size: 12px;">
	 *         <td>data.price</td>
	 *         <td>int</td>
	 *         <td>是</td>
	 *         <td>房源(或房间)价格</td>
	 *     </tr>
	 *     <tr bgcolor="#DDDDDD" style="font-size: 12px;">
	 *         <td>data.status</td>
	 *         <td>int</td>
	 *         <td>是</td>
	 *         <td>房源(或房间)状态(0:待出租 1:已出租 2:不可租)</td>
	 *     </tr>
	 * </table>
	 * </div>
	 *
	 * @author liujun
	 * @created 2016年4月21日 下午6:38:13
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/${UNLOGIN_AUTH}/leaseCalendar")
	@ResponseBody
	public ResponseEntity<ResponseSecurityDto> leaseCalendar(HttpServletRequest request) {
		try {
			String paramJson = (String) request.getAttribute(ConstDef.DECRYPT_PARAM_ATTRIBUTE);
			LogUtil.info(LOGGER, "参数：" + paramJson);
			if(Check.NuNStr(paramJson)){
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("参数不能为空"), HttpStatus.OK);
			}
			/**判断参数类型是否正确**/
			if(!BaseMethodUtil.isNumberKey(paramJson, "rentWay")){
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("出租方式不合法"), HttpStatus.OK);
			}
			ValidateResult<CalendarParamDto> validateResult =
					paramCheckLogic.checkParamValidate(paramJson, CalendarParamDto.class);
			if (!validateResult.isSuccess()) {
				LogUtil.error(LOGGER, "错误信息:{},参数:{}", validateResult.getDto().getMsg(), paramJson);
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail(validateResult.getDto().getMsg()),
						HttpStatus.OK);
			}

			CalendarParamDto requestDto = validateResult.getResultObj();
			Date startDate = DateUtil.parseDate(requestDto.getStartDate(), DATE_FORMAT_PATTERN);
			Date endDate = DateUtil.parseDate(requestDto.getEndDate(), DATE_FORMAT_PATTERN);

			/** 查询出租日历特殊价格 **/
			LeaseCalendarDto paramDto = new LeaseCalendarDto();
			paramDto.setHouseBaseFid(requestDto.getHouseBaseFid());
			paramDto.setHouseRoomFid(requestDto.getHouseRoomFid());
			paramDto.setRentWay(requestDto.getRentWay());
			paramDto.setStartDate(startDate);
			paramDto.setEndDate(endDate);

			DataTransferObject dto = new DataTransferObject();
			//处理日历
			dealCalendar(requestDto, dto, false);
			if(dto.getCode() == DataTransferObject.SUCCESS){
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptOK(dto.getData().get("info")), HttpStatus.OK);
			}else {
				return new ResponseEntity<>(ResponseSecurityDto.responseUnEncryptFail(dto.getMsg()), HttpStatus.OK);
			}

		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("服务错误"), HttpStatus.OK);
		}
	}


	/**
	 * 获取房源的出租日历
	 * @author afi
	 * @param requestDto
	 * @param dto
	 */
	private void dealCalendar(CalendarParamDto requestDto,DataTransferObject dto,boolean extStatus)throws Exception{
		Long time = System.currentTimeMillis();

		Date startDate = DateUtil.parseDate(requestDto.getStartDate(), DATE_FORMAT_PATTERN);
		Date endDate = DateUtil.parseDate(requestDto.getEndDate(), DATE_FORMAT_PATTERN);

		/** 查询出租日历特殊价格 **/
		LeaseCalendarDto paramDto = new LeaseCalendarDto();
		paramDto.setHouseBaseFid(requestDto.getHouseBaseFid());
		paramDto.setHouseRoomFid(requestDto.getHouseRoomFid());
		paramDto.setRentWay(requestDto.getRentWay());
		paramDto.setStartDate(startDate);
		paramDto.setEndDate(endDate);
		paramDto.setIsValid(IsValidEnum.WEEK_OPEN.getCode());


		//--记录调用时间-----
		Long time1 = System.currentTimeMillis();
		LogUtil.debug(LOGGER, "【calendar】【dealCalendar】-解析参数时间：{}", time1 - time);

		String houseJson = houseManageService.leaseCalendar(JsonEntityTransform.Object2Json(paramDto));
		DataTransferObject houseDto = JsonEntityTransform.json2DataTransferObject(houseJson);
		//判断调用状态
		if(houseDto.getCode() == ApiConst.OPERATION_FAILURE){
			LogUtil.error(LOGGER, "调用接口失败,参数:{}", JsonEntityTransform.Object2Json(paramDto));
			dto.setErrCode(houseDto.getCode());
			dto.setMsg(houseDto.getMsg());
			return;
		}
		/** 查询出租日历特殊价格 **/



		//--记录调用时间-----
		Long time2 = System.currentTimeMillis();
		LogUtil.debug(LOGGER, "【calendar】【dealCalendar】-获取日历价格信息：{}", time2 - time1);

		/** 封装返回信息**/
		LeaseCalendarVo leaseCalendarVo = houseDto.parseData("calendarData",
				new TypeReference<LeaseCalendarVo>() {});

		/** 查询出租日历出租状态 **/
		HouseLockRequest lockRequest = new HouseLockRequest();
		if(Check.NuNStr(requestDto.getHouseBaseFid())){
			String houseBaseFid = houseDto.parseData("houseBaseFid", new TypeReference<String>() {});
			requestDto.setHouseBaseFid(houseBaseFid);
		}

		lockRequest.setFid(requestDto.getHouseBaseFid());
		lockRequest.setRoomFid(requestDto.getHouseRoomFid());
		lockRequest.setRentWay(requestDto.getRentWay());
		lockRequest.setStarTime(startDate);

		Date endDateDefault = endDate;

		//取较小时间
		if (!Check.NuNObj(leaseCalendarVo.getTillDate())
				&& leaseCalendarVo.getTillDate().getTime() <= endDate.getTime()) {
			endDateDefault = leaseCalendarVo.getTillDate();
		}
		//说明 锁房源的结束日期应该是 房源的截止日期
		lockRequest.setEndTime(DateUtil.parseDate(DateUtil.dateFormat(endDateDefault),DATE_FORMAT_PATTERN));

		String orderJson = orderUserService.getHouseLockInfo(JsonEntityTransform.Object2Json(lockRequest));

		//--记录调用时间-----
		Long time3 = System.currentTimeMillis();
		LogUtil.debug(LOGGER, "【calendar】【dealCalendar】-获取出租日历信息：{}", time3 - time2);



		DataTransferObject orderDto = JsonEntityTransform.json2DataTransferObject(orderJson);
		//判断调用状态
		if(orderDto.getCode() == ApiConst.OPERATION_FAILURE){
			dto.setErrCode(orderDto.getCode());
			dto.setMsg(orderDto.getMsg());
			return;
		}
		/** 查询出租日历出租状态 **/


		//--记录调用时间-----
		Long time4 = System.currentTimeMillis();
		LogUtil.debug(LOGGER, "【calendar】【dealCalendar】-解析出租日历信息：{}", time4 - time3);

		int start = 0;
		Map<String, LinkedHashMap<String,CalendarResponseVo>> monthMap=new LinkedHashMap<>();
		for (int i = 0, j = DateUtil.getDatebetweenOfDayNum(startDate, endDate) + 1; i < j; i++) {
			CalendarResponseVo vo = new CalendarResponseVo();
			String dateMonth = this.getDateMonth(startDate, i);
			String dateDay = this.getDate(startDate, i);
			vo.setDate(dateDay);
			vo.setPrice(leaseCalendarVo.getUsualPrice());
			LinkedHashMap<String, CalendarResponseVo> dayMap=monthMap.get(dateMonth);
			if(!Check.NuNMap(dayMap)){
				dayMap.put(dateDay, vo);
			} else {
				dayMap=new LinkedHashMap<>();
				dayMap.put(dateDay, vo);
				monthMap.put(dateMonth, dayMap);
			}
			//判断截止日期
			Date dayDay=DateUtil.parseDate(dateDay, DATE_FORMAT_PATTERN);
			if (!Check.NuNObj(leaseCalendarVo.getTillDate())
					&& leaseCalendarVo.getTillDate().getTime() <= dayDay.getTime()) {
				vo.setStatus(LockTypeEnum.LANDLADY.getCode());
				start ++;
				if(start == 1 && extStatus){
					//截止时间的第一天是可租的
					vo.setStatus(LockTypeEnum.EXT_CAN_OUT.getCode());
				}
			}
		}

		//--记录调用时间-----
		Long time5 = System.currentTimeMillis();
		LogUtil.debug(LOGGER, "【calendar】【dealCalendar】-遍历出租日历信息：{}", time5 - time4);

		//设置特殊价格
		List<SpecialPriceVo> specialPriceList = leaseCalendarVo.getSpecialPriceList();
		if(!Check.NuNCollection(specialPriceList)){
			for (SpecialPriceVo specialPriceVo : specialPriceList) {
				Map<String, CalendarResponseVo> map=monthMap.get(DateUtil.dateFormat(specialPriceVo.getSetDate(), DATE_FORMAT_MONTH));
				if(!Check.NuNMap(map)){
					CalendarResponseVo vo = map.get(DateUtil.dateFormat(specialPriceVo.getSetDate(), DATE_FORMAT_PATTERN));
					if(!Check.NuNObj(vo)){
						vo.setPrice(specialPriceVo.getSetPrice());
					}
				}
			}
		}
		
		//房源当天的基本价格，供后面计算今夜特价时使用
		Integer currentDayBasePrice = null;
		Date currentDate = new Date();
		Map<String, CalendarResponseVo> maptemp=monthMap.get(DateUtil.dateFormat(currentDate, DATE_FORMAT_MONTH));
		if(!Check.NuNMap(maptemp)){
			CalendarResponseVo currentDayVo = maptemp.get(DateUtil.dateFormat(currentDate, DATE_FORMAT_PATTERN));
			if(!Check.NuNObj(currentDayVo)){
				currentDayBasePrice = currentDayVo.getPrice();
			}
		}

		//设置夹心价格
		setHousePriorityDate(monthMap,lockRequest,leaseCalendarVo.getTillDate());
		
		//设置今夜特价
		setHouseTonightDiscount(monthMap,lockRequest,currentDayBasePrice);

		//房源锁定信息列表
		List<HouseLockEntity> houseLockList = orderDto.parseData("houseLock",
				new TypeReference<List<HouseLockEntity>>() {
		});

		//--记录调用时间-----
		Long time6 = System.currentTimeMillis();
		LogUtil.debug(LOGGER, "【calendar】【dealCalendar】-设置特殊价格：{}", time6 - time5);


		//设置房源状态
		dealLockStatus(houseLockList, monthMap, extStatus);

		//--记录调用时间-----
		Long time7 = System.currentTimeMillis();
		LogUtil.debug(LOGGER, "【calendar】【dealCalendar】-设置房源状态：{}", time7 - time6);


		//对象封装
		List<CalendarMonth> calendarList=new ArrayList<>();
		for(String key:monthMap.keySet()){
			CalendarMonth calendarMonth=new CalendarMonth();
			calendarMonth.setMonthStr(key);
			calendarMonth.getCalendarList().addAll(monthMap.get(key).values());
			calendarList.add(calendarMonth);
		}

		//--记录调用时间-----
		Long time8 = System.currentTimeMillis();
		LogUtil.debug(LOGGER, "【calendar】【dealCalendar】-封装对象：{}", time8 - time7);

		/** 封装返回信息**/
		Map<String, Object> resultMap=new HashMap<String, Object>();
		resultMap.put("monthList", calendarList);
		resultMap.put("tillDate", DateUtil.dateFormat(leaseCalendarVo.getTillDate(), DATE_FORMAT_PATTERN));
		dto.putValue("info",resultMap);

		//--记录调用时间-----
		Long time9 = System.currentTimeMillis();
		LogUtil.debug(LOGGER, "【calendar】【dealCalendar】-累计：{}", time9 - time);
	}

	/**
	 * 
	 * 设置房源夹心价格 查看日期  是当前时间 往后推 6个月  
	 *
	 * @author yd
	 * @created 2016年12月8日 上午10:29:14
	 *
	 * @param monthMap
	 */
	private void setHousePriorityDate(Map<String, LinkedHashMap<String,CalendarResponseVo>> monthMap,HouseLockRequest lockRequest,Date tillDate ){

		if(!Check.NuNMap(monthMap)&&!Check.NuNObj(lockRequest)){

			try {
				Date curDate = new Date();
				if(Check.NuNObj(tillDate))tillDate = lockRequest.getEndTime();
				if(Check.NuNObj(lockRequest.getStarTime()))lockRequest.setStarTime(curDate);
				
				if(!(lockRequest.getStarTime().getTime()-curDate.getTime()>=0)){
					lockRequest.setStarTime(curDate);
				}
				HousePriorityDto housePriorityDt = new HousePriorityDto();
				housePriorityDt.setHouseBaseFid(lockRequest.getFid());
				housePriorityDt.setRentWay(lockRequest.getRentWay());
				housePriorityDt.setHouseRoomFid(lockRequest.getRoomFid());
				housePriorityDt.setStartDate(lockRequest.getStarTime());
				housePriorityDt.setEndDate(lockRequest.getEndTime());
				housePriorityDt.setTillDate(tillDate);
				housePriorityDt.setNowDate(new Date());
				DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(this.houseCommonService.findPriorityDate(JsonEntityTransform.Object2Json(housePriorityDt)));

				if(dto.getCode() == DataTransferObject.ERROR){
					LogUtil.error(LOGGER, "查看日历,夹心价格获取失败,参数paramDto={},msg={}", JsonEntityTransform.Object2Json(lockRequest),dto.getMsg());
					return ;
				}

				Map<String,HousePriorityVo> housePriorityMap  = dto.parseData("housePriorityMap", new TypeReference<Map<String,HousePriorityVo> >() {
				});
				if(!Check.NuNMap(housePriorityMap)){
					for (Map.Entry<String, HousePriorityVo>  entry : housePriorityMap.entrySet()) {
						String key = entry.getKey();
						Map<String, CalendarResponseVo> map=monthMap.get(DateUtil.dateFormat(DateUtil.parseDate(key, DATE_FORMAT_MONTH), DATE_FORMAT_MONTH));
						if(!Check.NuNMap(map)){
							CalendarResponseVo vo = map.get(key);
							HousePriorityVo val = entry.getValue();
							if(!Check.NuNObj(vo)&&!Check.NuNObj(val)
									&&!Check.NuNStrStrict(val.getPriorityDiscount())){
								vo.setPrice(DataFormat.getPriorityPrice(val.getPriorityDiscount(), vo.getPrice()));
							}
						}
					}
				}
			} catch (Exception e) {
				LogUtil.error(LOGGER, "查看日历-夹心价格设置失败paramDto={}", JsonEntityTransform.Object2Json(lockRequest));
			}

		}
	}
	
	/**
	 * 设置今夜特价价格
	 * @author lusp
	 * @created 2017年5月17日 上午10:29:14
	 * @param monthMap
	 * @param requestDto
	 */
	private void setHouseTonightDiscount(
			Map<String, LinkedHashMap<String, CalendarResponseVo>> monthMap,
			HouseLockRequest requestDto,Integer basePrice) {
		if(!Check.NuNMap(monthMap)&&!Check.NuNObj(requestDto)){
			TonightDiscountEntity tonightDiscountEntity = new TonightDiscountEntity();
			try {
				tonightDiscountEntity.setHouseFid(requestDto.getFid());
				if(!Check.NuNObj(requestDto.getRentWay()) && requestDto.getRentWay() == RentWayEnum.ROOM.getCode()){
					tonightDiscountEntity.setRoomFid(requestDto.getRoomFid());
				}
				tonightDiscountEntity.setRentWay(requestDto.getRentWay());
				String paramJson = JsonTransform.Object2Json(tonightDiscountEntity);
				String resultJson = houseCommonService.getEffectiveOfJYTJInfo(paramJson);
				DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(resultJson);
				//判断调用状态
				if(dto.getCode() == DataTransferObject.ERROR){
					LogUtil.error(LOGGER, "查看日历,获取当前房源今夜特价信息失败,参数paramDto={},msg={}", paramJson,dto.getMsg());
					return;
				}
				HouseTonightPriceInfoVo houseTonightPriceInfoVo = SOAResParseUtil.getValueFromDataByKey(resultJson, "data", HouseTonightPriceInfoVo.class);
				
				if(Check.NuNObj(houseTonightPriceInfoVo)){
					return;
				}
				if(houseTonightPriceInfoVo.isEffective()){
					double discount = houseTonightPriceInfoVo.getDiscount();
					if (Check.NuNObj(discount)) {
						return;
					}
					Date date = new Date();
					//当前系统时间大于今夜特价生效时间，今夜特价生效
					if(Check.NuNObj(basePrice)){
						return;
					}
					Map<String, CalendarResponseVo> map=monthMap.get(DateUtil.dateFormat(date, DATE_FORMAT_MONTH));
					if(!Check.NuNMap(map)){
						CalendarResponseVo vo = map.get(DateUtil.dateFormat(date, DATE_FORMAT_PATTERN));
						if(!Check.NuNObj(vo)){
							if(discount>0&discount<=1){
								Double priceD = BigDecimalUtil.mul(discount, basePrice);
								int price = priceD.intValue()/100;
								vo.setPrice(price*100);
							}
						}
					}
				}
			} catch (Exception e) {
				LogUtil.error(LOGGER, "查看日历,获取当前房源今夜特价信息失败,参数paramDto={}", requestDto);
			}
		}
	}
	
	/**
	 * 处理当前房源锁定的类型
	 * @author afi
	 * @param houseLockList
	 * @param extStatus
	 */
	private void dealLockStatus(List<HouseLockEntity> houseLockList,Map<String, LinkedHashMap<String,CalendarResponseVo>> monthMap,boolean extStatus){
		if(Check.NuNCollection(houseLockList)){
			return;
		}
		if(!extStatus){
			//只显示012三个状态，兼容之前的状态
			for (HouseLockEntity houseLockEntity : houseLockList) {
				Map<String, CalendarResponseVo> map=monthMap.get(DateUtil.dateFormat(houseLockEntity.getLockTime(), DATE_FORMAT_MONTH));
				if(!Check.NuNMap(map)){
					CalendarResponseVo vo = map.get(DateUtil.dateFormat(houseLockEntity.getLockTime(), DATE_FORMAT_PATTERN));
					if(!Check.NuNObj(vo)){
						vo.setStatus(houseLockEntity.getLockType());
					}
				}
			}
			return;
		}
		Map<String,String> locks = new HashMap<>();
		for(HouseLockEntity houseLockEntity : houseLockList){
			String key = DateUtil.dateFormat(houseLockEntity.getLockTime());
			locks.put(key,key);
		}
		for (HouseLockEntity houseLockEntity : houseLockList) {
			Map<String, CalendarResponseVo> map=monthMap.get(DateUtil.dateFormat(houseLockEntity.getLockTime(), DATE_FORMAT_MONTH));
			if(!Check.NuNMap(map)){
				Date lockDate = houseLockEntity.getLockTime();
				String lockKey = DateUtil.dateFormat(lockDate, DATE_FORMAT_PATTERN);
				CalendarResponseVo vo = map.get(lockKey);
				if(!Check.NuNObj(vo)){
					Date Yesterday = DateSplitUtil.getYesterday(lockDate);
					String yesterdayKeyDay = DateUtil.dateFormat(Yesterday);
					Date tomorrow = DateSplitUtil.getTomorrow(lockDate);
					String tomorrowKeyDay = DateUtil.dateFormat(tomorrow);
					String tomorrowKeyMonth = DateUtil.dateFormat(tomorrow, DATE_FORMAT_MONTH);
					vo.setStatus(houseLockEntity.getLockType());
					if(!locks.containsKey(yesterdayKeyDay)){
						//如果昨天可租，今天就是可离开的
						vo.setStatus(LockTypeEnum.EXT_CAN_OUT.getCode());
					}
					if(!locks.containsKey(tomorrowKeyDay)){
						//如果明天可租，那么明天肯定是不可离开的
						Map<String, CalendarResponseVo> tomorrowmap=monthMap.get(tomorrowKeyMonth);
						if(!Check.NuNObj(tomorrowmap)){
							CalendarResponseVo tomorrowVo = tomorrowmap.get(tomorrowKeyDay);
							if(!Check.NuNObj(tomorrowVo)&&tomorrowVo.getStatus().intValue() == 0){
								tomorrowVo.setStatus(LockTypeEnum.EXT_CANOT_OUT.getCode());
							}
						}
					}
				}
			}
		}
	}

	@RequestMapping("/${UNLOGIN_AUTH}/calendar")
	@ResponseBody
	public ResponseEntity<ResponseSecurityDto> calendar(HttpServletRequest request) {
		try {


			Long time = System.currentTimeMillis();
			String paramJson = (String) request.getAttribute(ConstDef.DECRYPT_PARAM_ATTRIBUTE);
			LogUtil.info(LOGGER, "【calendar】-开始：dealCalendar,参数：paramJson={}", paramJson);
			/**判断参数类型是否正确**/
			if(!BaseMethodUtil.isNumberKey(paramJson, "rentWay")){
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("出租方式不合法"), HttpStatus.OK);
			}
			ValidateResult<CalendarParamDto> validateResult =
					paramCheckLogic.checkParamValidate(paramJson, CalendarParamDto.class);
			if (!validateResult.isSuccess()) {
				LogUtil.error(LOGGER, "错误信息:{},参数:{}", validateResult.getDto().getMsg(), paramJson);
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail(validateResult.getDto().getMsg()),
						HttpStatus.OK);
			}

			CalendarParamDto requestDto = validateResult.getResultObj();
			Date startDate = DateUtil.parseDate(requestDto.getStartDate(), DATE_FORMAT_PATTERN);
			Date endDate = DateUtil.parseDate(requestDto.getEndDate(), DATE_FORMAT_PATTERN);

			/** 查询出租日历特殊价格 **/
			LeaseCalendarDto paramDto = new LeaseCalendarDto();
			paramDto.setHouseBaseFid(requestDto.getHouseBaseFid());
			paramDto.setHouseRoomFid(requestDto.getHouseRoomFid());
			paramDto.setRentWay(requestDto.getRentWay());
			paramDto.setStartDate(startDate);
			paramDto.setEndDate(endDate);


			DataTransferObject dto = new DataTransferObject();
			//处理日历
			dealCalendar(requestDto, dto, true);

			//--记录调用时间-----
			Long time2 = System.currentTimeMillis();
			LogUtil.debug(LOGGER, "【calendar】-处理逻辑时间：{}ms", time2 - time);

			if(dto.getCode() == DataTransferObject.SUCCESS){
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptOK(dto.getData().get("info")), HttpStatus.OK);
			}else {
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail(dto.getMsg()), HttpStatus.OK);
			}

		} catch (Exception e) {
			Map<String, String> map = new HashMap<String, String>();     
			Enumeration<String> headerNames = request.getHeaderNames();     
			while (headerNames.hasMoreElements()) {       
				String key = headerNames.nextElement();       
				String value = request.getHeader(key);       
				map.put(key, value);     
			} 
			LogUtil.info(LOGGER, "【calendar】-开始：dealCalendar,头header信息{}", JsonEntityTransform.Object2Json(map));
			LogUtil.error(LOGGER, "error:{}", e);
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("服务错误"), HttpStatus.OK);
		}
	}


	/**
	 * 获取开始时间指定间隔天数后的日期字符串
	 *
	 * @author liujun
	 * @created 2016年4月22日 下午2:09:27
	 *
	 * @param startDate 开始时间
	 * @param interval 间隔天数
	 * @return
	 */
	private String getDate(Date startDate, int interval) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(startDate);
		calendar.add(Calendar.DATE, interval);
		return DateUtil.dateFormat(calendar.getTime(), DATE_FORMAT_PATTERN);
	}

	/**
	 * 获取开始时间指定间隔天数后的日期字符串 yyyy-MM
	 *
	 * @author liujun
	 * @created 2016年4月22日 下午2:09:27
	 *
	 * @param startDate 开始时间
	 * @param interval 间隔天数
	 * @return
	 */
	private String getDateMonth(Date startDate, int interval) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(startDate);
		calendar.add(Calendar.DATE, interval);
		return DateUtil.dateFormat(calendar.getTime(), DATE_FORMAT_MONTH);
	}

	/**
	 * <p>描述:</p>
	 * <p>&nbsp; &nbsp; &nbsp; &nbsp;<b>图片上传</b></p>
	 * <p>请求示例：<b>/house/ea61d2/saveHousePic?2y5QfvAy=60d6a7090c1da858de1b9ea973e6938f61a178050137a70981f8c8cb53971fc2f26d13b70ec62a860bfecbd0c80e056bac7bb04b58281ebe37436a547f2405f0bef7dd706d13f64cd0823cc89a95af16201286e6a5fa73144e5d1d6ae3012e85a9de96dbc5bf4bf3ab08f78bc4342bf11cca18b3ebed48ace614dee02ae5f53a7d024d1882867f673c8988ab81d01816c87ecd9bb1a03860817671627a06396635685920d2aa733400cebd131dfe0632&hPtJ39Xs=202794e912ee2fbc1be4ca60f5a8dda6</b></p>
	 * <p>请求参数：<b>{"loginToken":"loginToken","uid":"8a9e9a8b53d6da8b0153d6da8bae0000","houseBaseFid":"8a9e9aae5419cc22015419cc250a0002","houseRoomFid":null,"picType":0}</b></p>
	 * <p>返回结果示例:<b>
	 *     {"status":0,"message":"","data":["http://pic.t.ziroom.com/group1/M00/00/16/ChAiMFcbO-qARpxnAAJRR7tA7N4309.jpg"]}
	 * </b></p>
	 * <div>
	 * <table width='100%' cellpadding='1' cellspacing='1' bgcolor='gray'>
	 *     <tr bgcolor="#DDDDDD" style="font-size: 12px; ">
	 *         <td colspan="4" textAlign="center" >入参明细</td>
	 *     </tr>
	 *     <tr bgcolor="#DDDDDD" style="font-size: 12px;">
	 *         <td>参数名</td>
	 *         <td>类型</td>
	 *         <td>是否必须(是或否)</td>
	 *         <td>含义</td>
	 *     </tr>
	 *     <tr bgcolor="#DDDDDD" style="font-size: 12px;">
	 *         <td>loginToken</td>
	 *         <td>String</td>
	 *         <td>是</td>
	 *         <td>登录令牌</td>
	 *     </tr>
	 *     <tr bgcolor="#DDDDDD" style="font-size: 12px;">
	 *         <td>uid</td>
	 *         <td>String</td>
	 *         <td>是</td>
	 *         <td>登录用户逻辑id</td>
	 *     </tr>
	 *     <tr bgcolor="#DDDDDD" style="font-size: 12px;">
	 *         <td>picType</td>
	 *         <td>int</td>
	 *         <td>是</td>
	 *         <td>图片类型(0:卧室,1:客厅,2:厨房,3卫生间,4:室外)</td>
	 *     </tr>
	 *     <tr bgcolor="#DDDDDD" style="font-size: 12px;">
	 *         <td>houseBaseFid</td>
	 *         <td>String</td>
	 *         <td>是</td>
	 *         <td>房源逻辑id</td>
	 *     </tr>
	 *     <tr bgcolor="#DDDDDD" style="font-size: 12px;">
	 *         <td>houseRoomFid</td>
	 *         <td>String</td>
	 *         <td>否</td>
	 *         <td>房间逻辑id(整租不传,合租必传)</td>
	 *     </tr>
	 * </table>
	 *
	 * <table width='100%' cellpadding='1' cellspacing='1' bgcolor='gray'>
	 *     <tr bgcolor="#DDDDDD" style="font-size: 12px; ">
	 *         <td colspan="4" textAlign="center">返回结果明细</td>
	 *     </tr>
	 *     <tr bgcolor="#DDDDDD" style="font-size: 12px;">
	 *         <td>参数名</td>
	 *         <td>类型</td>
	 *         <td>是否必须(是或否)</td>
	 *         <td>含义</td>
	 *     </tr>
	 *     <tr bgcolor="#DDDDDD" style="font-size: 12px;">
	 *         <td>status</td>
	 *         <td>int</td>
	 *         <td>是</td>
	 *         <td>调用状态(0:成功,1:失败)</td>
	 *     </tr>
	 *     <tr bgcolor="#DDDDDD" style="font-size: 12px;">
	 *         <td>message</td>
	 *         <td>String</td>
	 *         <td>否</td>
	 *         <td>调用信息</td>
	 *     </tr>
	 *     <tr bgcolor="#DDDDDD" style="font-size: 12px;">
	 *         <td>data</td>
	 *         <td>list</td>
	 *         <td>是</td>
	 *         <td>上传图片url集合</td>
	 *     </tr>
	 * </table>
	 * </div>
	 *
	 * 说明：照片标准这一版 不上
	 * @author liujun
	 * @created 2016年4月23日 下午3:22:05
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/${LOGIN_AUTH}/saveHousePic")
	@ResponseBody
	public ResponseEntity<ResponseSecurityDto> saveHousePic(@RequestParam MultipartFile[] file, HttpServletRequest request) {
		try {
			String paramJson = (String) request.getAttribute(ConstDef.DECRYPT_PARAM_ATTRIBUTE);
			LogUtil.info(LOGGER, "参数：" + paramJson);

			ValidateResult<PicParamDto> validateResult =
					paramCheckLogic.checkParamValidate(paramJson, PicParamDto.class);
			if (!validateResult.isSuccess()) {
				LogUtil.error(LOGGER, "错误信息:{},参数:{}", validateResult.getDto().getMsg(), paramJson);
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail(validateResult.getDto().getMsg()),
						HttpStatus.OK);
			}
			PicParamDto requestDto = validateResult.getResultObj();
			String houseBaseFid = requestDto.getHouseBaseFid();
			String houseRoomFid = requestDto.getHouseRoomFid();
			Integer picType = requestDto.getPicType();
			
			/** 获取房源状态 **/
			Integer houseStatus=HouseStatusEnum.DFB.getCode();
			String houseBaseJson=houseIssueService.searchHouseBaseMsgByFid(houseBaseFid);
			HouseBaseMsgEntity houseBaseMsgEntity=SOAResParseUtil.getValueFromDataByKey(houseBaseJson, "obj", HouseBaseMsgEntity.class);
			if(!Check.NuNObj(houseBaseMsgEntity)){
				if(RentWayEnum.HOUSE.getCode()==houseBaseMsgEntity.getRentWay()){
					houseStatus=houseBaseMsgEntity.getHouseStatus();
				} else if(RentWayEnum.ROOM.getCode()==houseBaseMsgEntity.getRentWay()&&!Check.NuNStr(houseRoomFid)) {
					String roomJson=houseIssueService.searchHouseRoomMsgByFid(houseRoomFid);
					HouseRoomMsgEntity houseRoomMsgEntity=SOAResParseUtil.getValueFromDataByKey(roomJson, "obj", HouseRoomMsgEntity.class);
					houseStatus=houseRoomMsgEntity.getRoomStatus();
				}
			}
			/** 获取房源状态 **/
			
			/** 判断图片数量是否超过限制开始 **/
			HousePicDto housePicDto = new HousePicDto();
			housePicDto.setHouseBaseFid(houseBaseFid);
			housePicDto.setHouseRoomFid(houseRoomFid);
			housePicDto.setPicType(picType);

			HousePicTypeEnum enumeration = HousePicTypeEnum.getEnumByCode(picType.intValue());
			DataTransferObject dto = checkPicNumAndRules(housePicDto, file);
			if(dto.getCode() == DataTransferObject.ERROR
					){
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail(dto.getMsg()), HttpStatus.OK);
			}
			/** 判断图片数量是否超过限制结束 **/

			//获取 照片标准 
			/* dto = JsonEntityTransform.json2DataTransferObject(cityTemplateService.getPicValidParams());

	        if(dto.getCode() == DataTransferObject.ERROR
	        		){
	        	LogUtil.info(LOGGER, "获取照片标准失败msg={}", dto.getMsg());
	        	return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail(dto.getMsg()), HttpStatus.OK);
	        }

	        @SuppressWarnings("unchecked")
			Map<String, Integer> validMap = (Map<String, Integer>) dto.getData().get("validMap");*/

			/** 上传图片 **/
			List<HousePicMsgEntity> picList = new ArrayList<HousePicMsgEntity>();
			List<HousePicListVo> urlList = new ArrayList<HousePicListVo>();
			// 循环上传图片
			for (MultipartFile mulfile : file) {
				LogUtil.info(LOGGER, "图片全名称:{},图片类型:{}, 图片文件是否为空:{},图片名称:{}",mulfile.getOriginalFilename(),mulfile.getContentType(),mulfile.isEmpty(),mulfile.getName());
                String fileName = mulfile.getOriginalFilename();
                if (!Check.NuNStr(fileName) && fileName.indexOf(".") == -1){
					fileName += ".jpg";
				}
				HousePicMsgEntity housePicMsg = new HousePicMsgEntity();

				//填充图片标准属性值
				this.validatePicture(mulfile, housePicMsg);

				FileInfoResponse fileResponse = storageService.upload(storageKey, storageLimit, fileName,
						mulfile.getBytes(), enumeration.getName(), 0l, fileName);
				LogUtil.info(LOGGER, "上传图片返回结果：{}", JsonEntityTransform.Object2Json(fileResponse));
				if(!"0".equals(fileResponse.getResponseCode()) || JsonEntityTransform.Object2Json(fileResponse).toLowerCase().indexOf("undefined")>=0){
					LogUtil.info(LOGGER, "图片全名称:{},图片类型:{}, 图片文件是否为空:{},图片名称:{}",mulfile.getOriginalFilename(),mulfile.getContentType(),mulfile.isEmpty(),mulfile.getName());
					continue;
				} 

				housePicMsg.setFid(UUIDGenerator.hexUUID());
				housePicMsg.setHouseBaseFid(houseBaseFid);
				if(HousePicTypeEnum.WS.getCode()==picType){
					housePicMsg.setRoomFid(houseRoomFid);
				}
				housePicMsg.setPicType(picType);
				housePicMsg.setPicBaseUrl(fileResponse.getFile().getUrlBase());
				housePicMsg.setOperateType(0);
				housePicMsg.setPicName(fileResponse.getFile().getOriginalFilename());
				housePicMsg.setPicSuffix(fileResponse.getFile().getUrlExt());
				housePicMsg.setPicServerUuid(fileResponse.getFile().getUuid());
				//审核不通过要新增状态
				if(HouseStatusEnum.ZPSHWTG.getCode()==houseStatus){
					housePicMsg.setAuditStatus(3);
				} else if(HousePicTypeEnum.WS.getCode()!=picType&&RentWayEnum.ROOM.getCode()==houseBaseMsgEntity.getRentWay()&&Check.NuNStr(houseRoomFid)) {
					housePicMsg.setAuditStatus(3);
				} else {
					housePicMsg.setAuditStatus(0);
				}
				picList.add(housePicMsg);
				HousePicListVo vo=new HousePicListVo();
				vo.setFid(housePicMsg.getFid());
				vo.setPicType(housePicMsg.getPicType());
				vo.setPicUrl(PicUtil.getFullPic(picBaseAddrMona, fileResponse.getFile().getUrlBase(), fileResponse.getFile().getUrlExt(), detailBigPic));
				vo.setMinPicUrl(PicUtil.getFullPic(picBaseAddrMona, fileResponse.getFile().getUrlBase(), fileResponse.getFile().getUrlExt(), list_small_pic));
				urlList.add(vo);
			}

			if(Check.NuNCollection(picList)){
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("上传失败,请重新上传"), HttpStatus.OK);
			}
			// 保存图片信息
			housePicDto.setPicList(picList);
			String resultJson = houseIssueService.newSaveHousePicMsgList(JsonEntityTransform.Object2Json(housePicDto));
			DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(resultJson);

			// 判断调用状态
			if (resultDto.getCode() == ApiConst.OPERATION_FAILURE) {
				LogUtil.info(LOGGER, "调用接口失败,参数:{},错误信息resultDto={}", JsonEntityTransform.Object2Json(picList),resultDto.getMsg());
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail(resultDto.getMsg()), HttpStatus.OK);
			}
			/** 上传图片 **/

			LogUtil.info(LOGGER, "结果：" + JsonEntityTransform.Object2Json(urlList));
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptOK(urlList), HttpStatus.OK);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("服务错误"), HttpStatus.OK);
		}
	}

	/**
	 * 
	 * 验证图片 相关标准
	 *
	 * @author yd
	 * @created 2016年10月20日 上午10:08:30
	 *
	 * @param mulfile
	 * @param validMap
	 * @return
	 */
	private void validatePicture(MultipartFile mulfile, HousePicMsgEntity housePicMsg){

		DataTransferObject dto = new DataTransferObject();
		try {
			if(!Check.NuNObj(housePicMsg)){
				Metadata metadata = ImageMetadataReader.readMetadata(mulfile.getInputStream());

				int widthPixel = 0;
				int heightPixel = 0;
				int heightDpi = 0;
				int widthDpi = 0;

				//像素
				if (metadata.containsDirectoryOfType(JpegDirectory.class)){
					Directory jpegDirectory = metadata.getFirstDirectoryOfType(JpegDirectory.class);
					widthPixel = jpegDirectory.getString(JpegDirectory.TAG_IMAGE_WIDTH)==null?0:Integer.valueOf(jpegDirectory.getString(JpegDirectory.TAG_IMAGE_WIDTH));
					heightPixel = jpegDirectory.getString(JpegDirectory.TAG_IMAGE_HEIGHT)==null?0:Integer.valueOf(jpegDirectory.getString(JpegDirectory.TAG_IMAGE_HEIGHT));
				}

				//分辨率
				Directory jfifDirectory = null;
				if (metadata.containsDirectoryOfType(JfifDirectory.class)) {
					jfifDirectory = metadata.getFirstDirectoryOfType(JfifDirectory.class);
					widthDpi = jfifDirectory.getString(JfifDirectory.TAG_RESX)==null?0:Integer.valueOf(jfifDirectory.getString(JfifDirectory.TAG_RESX));
					heightDpi = jfifDirectory.getString(JfifDirectory.TAG_RESY)==null?0:Integer.valueOf(jfifDirectory.getString(JfifDirectory.TAG_RESY));
				} 
				if (metadata.containsDirectoryOfType(ExifIFD0Directory.class)) {
					jfifDirectory = metadata.getFirstDirectoryOfType(ExifIFD0Directory.class);
					widthDpi   = jfifDirectory.getString(ExifDirectoryBase.TAG_X_RESOLUTION)==null?0:Integer.valueOf(jfifDirectory.getString(ExifDirectoryBase.TAG_X_RESOLUTION));
					heightDpi  = jfifDirectory.getString(ExifDirectoryBase.TAG_Y_RESOLUTION)==null?0:Integer.valueOf(jfifDirectory.getString(ExifDirectoryBase.TAG_Y_RESOLUTION));
				}

				//大小
				double picSize = BigDecimalUtil.div(mulfile.getSize(), 1024);

				housePicMsg.setWidthPixel(widthPixel);
				housePicMsg.setHeightPixel(heightPixel);
				housePicMsg.setWidthDpi(widthDpi);
				housePicMsg.setHeightDpi(heightDpi);
				housePicMsg.setPicSize(picSize);
				LogUtil.info(LOGGER, "图片上传参数housePicMsg={}", JsonEntityTransform.Object2Json(housePicMsg));
			}

		}catch(IOException e){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("获取图片io异常");
			LogUtil.error(LOGGER, "获取图片io异常常e={}", e);
		}catch(ImageProcessingException e){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("图片上传验证失败");
			LogUtil.error(LOGGER, "图片标准验证ImageProcessingException异常e={}", e);
		}catch (Exception e) {
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("图片上传验证失败");
			LogUtil.error(LOGGER, "图片标准验证异常e={}", e);
		}
	}

	/**
	 * 
	 * 校验图片数量 以及图片 上传标准
	 *
	 * @author yd
	 * @created 2016年10月19日 下午2:43:32
	 *
	 * @param houseBaseFid
	 * @param houseRoomFid
	 * @param picType
	 * @param file
	 * @return
	 */
	private DataTransferObject checkPicNumAndRules(HousePicDto housePicDto ,MultipartFile[] file){


		DataTransferObject dto = new DataTransferObject();
		/** 判断图片数量是否超过限制开始 **/
		String countJson = troyHouseMgtService.findHousePicCountByType(JsonEntityTransform
				.Object2Json(housePicDto));
		DataTransferObject countDto = JsonEntityTransform.json2DataTransferObject(countJson);

		//判断调用状态
		if(countDto.getCode() == ApiConst.OPERATION_FAILURE){
			LogUtil.error(LOGGER, "调用接口失败,参数:{},错误信息msg={}", JsonEntityTransform.Object2Json(housePicDto),countDto.getMsg());
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg(countDto.getMsg());
		}
		int count = countDto.parseData("count", new TypeReference<Integer>() {});

		HousePicTypeEnum enumeration = HousePicTypeEnum.getEnumByCode(housePicDto.getPicType().intValue());
		if (Check.NuNObj(enumeration)) {
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("图片类型错误");
		}
		if ((file.length + count) > enumeration.getMax()) {
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("超过图片的最大限制数量");
		}

		return dto;
	}

	/**
	 * <p>描述:</p>
	 * <p>&nbsp; &nbsp; &nbsp; &nbsp;<b>上下架房源</b></p>
	 * <p>请求示例：<b>/house/ea61d2/upDownHouse?2y5QfvAy=60d6a7090c1da858de1b9ea973e6938f61a178050137a70981f8c8cb53971fc2f26d13b70ec62a860bfecbd0c80e056bac7bb04b58281ebe37436a547f2405f0bef7dd706d13f64cd0823cc89a95af16201286e6a5fa73144e5d1d6ae3012e85a9de96dbc5bf4bf3ab08f78bc4342bf11cca18b3ebed48ace614dee02ae5f53a7d024d1882867f673c8988ab81d01816c87ecd9bb1a038605e6303b4fb53fecdc618c5933ef120972b7a3adc61154006&hPtJ39Xs=aa9a7ac24c365fd44ff6cdd3ce36eb4f</b></p>
	 * <p>请求参数：<b>{"loginToken":"loginToken","uid":"8a9e9a8b53d6da8b0153d6da8bae0000","houseBaseFid":"8a9e9aae5419cc22015419cc250a0002","houseRoomFid":null,"rentWay":0}</b></p>
	 * <p>返回结果示例:<b>
	 *     {"status":0,"message":"","data":""}
	 * </b></p>
	 * <div>
	 * <table width='100%' cellpadding='1' cellspacing='1' bgcolor='gray'>
	 *     <tr bgcolor="#DDDDDD" style="font-size: 12px; ">
	 *         <td colspan="4" textAlign="center" >入参明细</td>
	 *     </tr>
	 *     <tr bgcolor="#DDDDDD" style="font-size: 12px;">
	 *         <td>参数名</td>
	 *         <td>类型</td>
	 *         <td>是否必须(是或否)</td>
	 *         <td>含义</td>
	 *     </tr>
	 *     <tr bgcolor="#DDDDDD" style="font-size: 12px;">
	 *         <td>loginToken</td>
	 *         <td>String</td>
	 *         <td>是</td>
	 *         <td>登录令牌</td>
	 *     </tr>
	 *     <tr bgcolor="#DDDDDD" style="font-size: 12px;">
	 *         <td>uid</td>
	 *         <td>String</td>
	 *         <td>是</td>
	 *         <td>登录用户逻辑id</td>
	 *     </tr>
	 *     <tr bgcolor="#DDDDDD" style="font-size: 12px;">
	 *         <td>rentWay</td>
	 *         <td>int</td>
	 *         <td>是</td>
	 *         <td>出租方式(0:整租,1:合租)</td>
	 *     </tr>
	 *     <tr bgcolor="#DDDDDD" style="font-size: 12px;">
	 *         <td>houseBaseFid</td>
	 *         <td>String</td>
	 *         <td>否</td>
	 *         <td>房源逻辑id(整租必传,合租不传)</td>
	 *     </tr>
	 *     <tr bgcolor="#DDDDDD" style="font-size: 12px;">
	 *         <td>houseRoomFid</td>
	 *         <td>String</td>
	 *         <td>否</td>
	 *         <td>房间逻辑id(整租不传,合租必传)</td>
	 *     </tr>
	 * </table>
	 *
	 * <table width='100%' cellpadding='1' cellspacing='1' bgcolor='gray'>
	 *     <tr bgcolor="#DDDDDD" style="font-size: 12px; ">
	 *         <td colspan="4" textAlign="center">返回结果明细</td>
	 *     </tr>
	 *     <tr bgcolor="#DDDDDD" style="font-size: 12px;">
	 *         <td>参数名</td>
	 *         <td>类型</td>
	 *         <td>是否必须(是或否)</td>
	 *         <td>含义</td>
	 *     </tr>
	 *     <tr bgcolor="#DDDDDD" style="font-size: 12px;">
	 *         <td>status</td>
	 *         <td>int</td>
	 *         <td>是</td>
	 *         <td>调用状态(0:成功,1:失败)</td>
	 *     </tr>
	 *     <tr bgcolor="#DDDDDD" style="font-size: 12px;">
	 *         <td>message</td>
	 *         <td>String</td>
	 *         <td>否</td>
	 *         <td>调用信息</td>
	 *     </tr>
	 * </table>
	 * </div>
	 *
	 * @author liujun
	 * @created 2016年4月23日 下午3:22:05
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/${LOGIN_AUTH}/upDownHouse")
	@ResponseBody
	public ResponseEntity<ResponseSecurityDto> upDownHouse(HttpServletRequest request) {
		try {
			String paramJson = (String) request.getAttribute(ConstDef.DECRYPT_PARAM_ATTRIBUTE);
			LogUtil.info(LOGGER, "参数：" + paramJson);

			ValidateResult<UpDownHouseDto> validateResult =
					paramCheckLogic.checkParamValidate(paramJson, UpDownHouseDto.class);
			if (!validateResult.isSuccess()) {
				LogUtil.error(LOGGER, "错误信息:{},参数:{}", validateResult.getDto().getMsg(), paramJson);
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail(validateResult.getDto().getMsg()),
						HttpStatus.OK);
			}
			UpDownHouseDto requestDto = validateResult.getResultObj();

			String resultJson = "";
			if(RentWayEnum.HOUSE.getCode() == requestDto.getRentWay()){
				if(Check.NuNStr(requestDto.getHouseBaseFid())){
					LogUtil.error(LOGGER, "houseBaseFid is null or blank");
					return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail(MessageSourceUtil.getChinese(
							messageSource, ApiMessageConst.HOUSE_BASE_FID_NULL)), HttpStatus.OK);

				}
				resultJson = houseManageService.upDownHouse(requestDto.getHouseBaseFid(), requestDto.getUid());
			}else if (RentWayEnum.ROOM.getCode() == requestDto.getRentWay()) {
				if(Check.NuNStr(requestDto.getHouseRoomFid())){
					LogUtil.error(LOGGER, "houseRoomFid is null or blank");
					return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail(MessageSourceUtil.getChinese(
							messageSource, ApiMessageConst.HOUSE_ROOM_FID_NULL)), HttpStatus.OK);

				}
				resultJson = houseManageService.upDownHouseRoom(requestDto.getHouseRoomFid(), requestDto.getUid());
			}else {
				LogUtil.error(LOGGER, MessageSourceUtil.getChinese(messageSource, ApiMessageConst.HOUSE_RENTWAY_ERROR));
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail(MessageSourceUtil.getChinese(
						messageSource, ApiMessageConst.HOUSE_RENTWAY_ERROR)), HttpStatus.OK);
			}

			DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(resultJson);
			//判断调用状态
			if(dto.getCode() == ApiConst.OPERATION_FAILURE){
				LogUtil.error(LOGGER, "参数:{}", resultJson);
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail(dto.getMsg()), HttpStatus.OK);
			}

			LogUtil.debug(LOGGER, "结果：" + JsonEntityTransform.Object2Json(dto));
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptOK(dto.getData()), HttpStatus.OK);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("服务错误"), HttpStatus.OK);
		}
	}

	/**
	 * 
	 * 图片类型查询图片列表
	 *
	 * @author bushujie
	 * @created 2016年5月27日 下午10:41:09
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/${LOGIN_AUTH}/housePicMsgList")
	@ResponseBody
	public ResponseEntity<ResponseSecurityDto> housePicMsgList(HttpServletRequest request) {
		try {
			String paramJson = (String) request.getAttribute(ConstDef.DECRYPT_PARAM_ATTRIBUTE);
			LogUtil.debug(LOGGER, "查询房源照片参数：" + paramJson);
			//照片类型查询照片列表
			String resultJson=houseIssueService.searchHousePicMsgList(paramJson);
			LogUtil.debug(LOGGER, "结果：" + resultJson);
			List<HousePicMsgEntity>list=SOAResParseUtil.getListValueFromDataByKey(resultJson, "list", HousePicMsgEntity.class);
			List<HousePicListVo> picListVos=new ArrayList<HousePicListVo>();
			for(HousePicMsgEntity pic:list){
				HousePicListVo vo=new HousePicListVo();
				vo.setFid(pic.getFid());
				vo.setPicUrl(PicUtil.getFullPic(picBaseAddrMona, pic.getPicBaseUrl(), pic.getPicSuffix(), detailBigPic));
				vo.setIsDefault(pic.getIsDefault());
				vo.setPicType(pic.getPicType());
				vo.setWidthPixel(pic.getWidthPixel());
				vo.setHeightPixel(pic.getHeightPixel());
				picListVos.add(vo);
			}
			LogUtil.debug(LOGGER, "接口返回结果：" + JsonEntityTransform.Object2Json(picListVos));
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptOK(picListVos), HttpStatus.OK);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("服务错误"), HttpStatus.OK);
		}
	}

	/**
	 * 
	 * 删除房源图片
	 *
	 * @author bushujie
	 * @created 2016年5月27日 下午10:41:09
	 *
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/${LOGIN_AUTH}/deleteHousePic")
	@ResponseBody
	public ResponseEntity<ResponseSecurityDto> deleteHousePic(HttpServletRequest request) {
		try {
			String paramJson = (String) request.getAttribute(ConstDef.DECRYPT_PARAM_ATTRIBUTE);
			LogUtil.info(LOGGER, "删除房源照片参数：" + paramJson);
			DataTransferObject dto=new DataTransferObject();
			Map<String,String> paramMap=(Map<String, String>) JsonEntityTransform.json2Map(paramJson);
			List<HousePicDto> list=JsonEntityTransform.json2ObjectList(paramMap.get("picList"), HousePicDto.class);
			for(HousePicDto housePicDto:list){
				housePicDto.setPicSource(2);
				String resultJson=houseIssueService.deleteHousePicMsgByFid(JsonEntityTransform.Object2Json(housePicDto));
				dto=JsonEntityTransform.json2DataTransferObject(resultJson);
				if(dto.getCode()==1){
					LogUtil.error(LOGGER, "参数:{}", resultJson);
					return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail(dto.getMsg()), HttpStatus.OK);
				}
			}
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptOK(dto.getData()), HttpStatus.OK);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("服务错误"), HttpStatus.OK);
		}
	}

	/**
	 * 
	 * 设置房源默认图片
	 *
	 * @author bushujie
	 * @created 2016年5月27日 下午10:41:09
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/${LOGIN_AUTH}/setDefaultPic")
	@ResponseBody
	public ResponseEntity<ResponseSecurityDto> setDefaultPic(HttpServletRequest request) {
		try {
			String paramJson = (String) request.getAttribute(ConstDef.DECRYPT_PARAM_ATTRIBUTE);
			LogUtil.info(LOGGER, "设置默认图片参数：" + paramJson);
			//查询默认图片旧值
			HousePicDto picDto=JsonEntityTransform.json2Object(paramJson, HousePicDto.class);
			HouseUpdateHistoryLogDto houseUpdateHistoryLogDto=new HouseUpdateHistoryLogDto();
			houseUpdateHistoryLogDto.setSourceType(houseService.getUserSourceType(request));
			houseUpdateHistoryLogDto.setCreateType(CreaterTypeEnum.LANLORD.getCode());
			houseUpdateHistoryLogDto.setCreaterFid(houseService.getLandlordUid(request));
			houseUpdateHistoryLogDto.setHouseFid(picDto.getHouseBaseFid());
			String houseBaseExtJson=houseIssueService.searchHouseBaseAndExtByFid(picDto.getHouseBaseFid());
			HouseBaseExtDto houseBaseExtDto=SOAResParseUtil.getValueFromDataByKey(houseBaseExtJson, "obj", HouseBaseExtDto.class);
			houseUpdateHistoryLogDto.setRentWay(houseBaseExtDto.getRentWay());
			if (houseBaseExtDto.getRentWay()==RentWayEnum.HOUSE.getCode()) {
				houseUpdateHistoryLogDto.setOldHouseBaseExt(houseBaseExtDto.getHouseBaseExt());
			} else if(houseBaseExtDto.getRentWay()==RentWayEnum.ROOM.getCode()) {
				houseUpdateHistoryLogDto.setRoomFid(picDto.getHouseRoomFid());
				String roomJson=houseIssueService.searchHouseRoomMsgByFid(picDto.getHouseRoomFid());
				HouseRoomMsgEntity roomMsgEntity=SOAResParseUtil.getValueFromDataByKey(roomJson, "obj", HouseRoomMsgEntity.class);
				houseUpdateHistoryLogDto.setOldHouseRoomMsg(roomMsgEntity);
			}
			//更新默认图片
			String resultJson = houseIssueService.updateHouseDefaultPic(paramJson);
			DataTransferObject dto=JsonEntityTransform.json2DataTransferObject(resultJson);
			if(dto.getCode()== DataTransferObject.ERROR){
				LogUtil.info(LOGGER, "参数:{}", resultJson);
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail(dto.getMsg()), HttpStatus.OK);
			}
			//添加默认照片修改记录
			if(houseBaseExtDto.getRentWay()==RentWayEnum.HOUSE.getCode()){
				HouseBaseExtEntity newHouseBaseExtEntity=new HouseBaseExtEntity();
				newHouseBaseExtEntity.setDefaultPicFid(picDto.getHousePicFid());
				houseUpdateHistoryLogDto.setHouseBaseExt(newHouseBaseExtEntity);
			} else if(houseBaseExtDto.getRentWay()==RentWayEnum.ROOM.getCode()) {
				HouseRoomMsgEntity newHouseRoomMsgEntity =new HouseRoomMsgEntity();
				newHouseRoomMsgEntity.setDefaultPicFid(picDto.getHousePicFid());
				houseUpdateHistoryLogDto.setHouseRoomMsg(newHouseRoomMsgEntity);
			}
			houseUpdateHistoryLogService.saveHistoryLog(JsonEntityTransform.Object2Json(houseUpdateHistoryLogDto));
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptOK(dto.getData()), HttpStatus.OK);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("服务错误"), HttpStatus.OK);
		}
	}
}
