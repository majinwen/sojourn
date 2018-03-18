package com.zrp.client;

import com.alibaba.fastjson.JSONObject;
import com.apollo.logproxy.slf4j.LoggerFactoryProxy;
import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.SOAResParseUtil;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.services.basedata.api.inner.CityTemplateService;
import com.ziroom.minsu.services.basedata.dto.ZrpPayRequest;
import com.ziroom.minsu.services.basedata.entity.ConfValueVo;
import com.ziroom.zrp.houses.entity.RoomRentInfoEntity;
import com.ziroom.zrp.service.houses.api.EmployeeService;
import com.ziroom.zrp.service.houses.api.HouseSignInviteRecordService;
import com.ziroom.zrp.service.houses.api.RoomService;
import com.ziroom.zrp.service.trading.api.*;
import com.ziroom.zrp.service.trading.dto.PaymentTermsDto;
import com.ziroom.zrp.service.trading.dto.RentContractDetailDto;
import com.ziroom.zrp.service.trading.entity.CheckSignCusInfoVo;
import com.ziroom.zrp.service.trading.valenum.ContractSignTypeEnum;
import com.ziroom.zrp.service.trading.valenum.ContractStatusEnum;
import com.ziroom.zrp.service.trading.valenum.PaymentCycleEnum;
import com.ziroom.zrp.service.trading.valenum.finance.DocumentTypeEnum;
import com.ziroom.zrp.trading.entity.CheckSignErrorLogEntity;
import com.ziroom.zrp.trading.entity.RentContractEntity;
import com.zra.common.constant.BillMsgConstant;
import com.zra.common.enums.ErrorEnum;
import com.zra.common.error.AppResult;
import com.zra.common.esp.signcheck.ItrusUtil;
import com.zra.common.esp.signcheck.ItrusUtilPortType;
import com.zra.common.result.ResponseDto;
import com.zra.common.security.DESUtils;
import com.zra.common.utils.EspUtil;
import com.zra.common.utils.StrUtils;
import com.zra.common.utils.ZraApiConst;
import com.zra.common.vo.contract.ProjectInfoVo;
import com.zra.common.vo.contract.RentContractListVo;
import com.zra.common.vo.pay.PayItemVo;
import com.zra.common.vo.perseon.SignSubjectVo;
import com.zra.common.vo.room.SignRoomInfoVo;
import com.zra.rentcontract.dto.VerifySignatureDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.activation.MimetypesFileTypeMap;
import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>签约流程</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @Author wangxm113
 * @Date 2017年09月12日 09时58分
 * @Version 1.0
 * @Since 1.0
 */
@Component
@Path("/contractsign")
@Api(value = "/合同签约", description = "签约流程相关")
public class ContractSignResource {
    private static final Logger LOGGER = LoggerFactoryProxy.getLogger(ContractSignResource.class);

	@Resource(name = "basedata.cityTemplateService")
	private CityTemplateService cityTemplateService;

    @Resource(name="trading.rentContractService")
    private RentContractService rentContractService;

    @Resource(name = "trading.paymentService")
	private PaymentService paymentService;

	@Resource(name = "trading.checkSignService")
	private CheckSignService checkSignService;

	@Resource(name = "trading.rentCheckinService")
	private RentCheckinService rentCheckinService;
	@Resource(name="houses.employeeService")
	private EmployeeService employeeService;

	@Resource(name = "houses.roomService")
	private RoomService roomService;

	@Resource(name = "trading.callFinanceService")
	private CallFinanceService callFinanceService;
	
	@Resource(name = "houses.houseSignInviteRecordService")
	private HouseSignInviteRecordService houseSignInviteRecordService;

	@Resource(name = "trading.receiptService")
	private ReceiptService receiptService;
	
	@Resource(name="trading.itemDeliveryService")
	private ItemDeliveryService itemDeliveryService;

	@Value("#{'${contract_pdf_url}'.trim()}")
    private String contractPdfUrl;
    /**
     * <P>合同详情接口</p>
	 *
	 * @author xiangb
	 * @created 2017年9月13日
	 * @param
	 * @return
     */
    @POST
    @Path("/detail/v1")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @ApiOperation(value = "获取合同详情", notes = ZraApiConst.CON_NEED_PARAM
            + "<br/> contractId(String)-合同标识", response = ResponseDto.class)
	public ResponseDto getContractDetail(@FormParam("contractId") String contractId,@FormParam("deCode") String deCode) {
    	LogUtil.info(LOGGER, "【getContractDetail】入参：{}", contractId);
    	try{
    		if (Check.NuNStr(contractId) || Check.NuNStr(deCode)) {
    			return ResponseDto.responseDtoErrorEnum(ErrorEnum.MSG_PARAM_NULL);
    		}
    		Map<String,String> param = new HashMap<>();
    		param.put("contractId", contractId);
    		param.put("deCode", deCode);
    		String contract = rentContractService.findContractByContractId(JSONObject.toJSONString(param));
    		LogUtil.info(LOGGER, "【getContractDetail】查询合同详情返回：{}", contract);
    		DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(contract);
    		RentContractDetailDto contractDetailDto = null;
    		if(resultDto.getCode() == DataTransferObject.ERROR){
    			return ResponseDto.responseDtoFail(resultDto.getMsg());
    		}
			contractDetailDto = resultDto.parseData("contractDto", new TypeReference<RentContractDetailDto>() {
			});
    		return ResponseDto.responseOK(contractDetailDto);
    	}catch(Exception e){
    		LogUtil.info(LOGGER, "【getContractDetail】出错!{}", e);
			return ResponseDto.responseDtoErrorEnum(ErrorEnum.MSG_FAIL);
    	}
	}
    /**
     * <P>个人中心合同卡片</p>
	 *
	 * @author xiangb
	 * @created 2017年9月13日
	 * @param
	 * @return
     */
    @POST
    @Path("/card/v1")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @ApiOperation(value = "个人中心合同卡片", notes = ZraApiConst.CON_NEED_PARAM,
    				response = ResponseDto.class)
	public ResponseDto getContractOfCard(@FormParam("uid") String uid) {
    	LogUtil.info(LOGGER, "【getContracatOfCard】入参：{}", uid);
    	try{
    		if (Check.NuNStr(uid)) {
    			return ResponseDto.responseDtoErrorEnum(ErrorEnum.MSG_PARAM_NULL);
    		}
    		String contractsStr = rentContractService.findLatelyContractByUid(uid);
    		LogUtil.info(LOGGER, "【getContracatOfCard】查询后台返回：{}", contractsStr);
    		DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(contractsStr);
    		if(resultDto.getCode() == DataTransferObject.ERROR){
    			return ResponseDto.responseDtoFail(resultDto.getMsg());
    		}
    		Map<String,Object> result = resultDto.parseData("resultMap", new TypeReference<Map<String,Object>>() {
			});
    		return ResponseDto.responseOK(result);
    	}catch(Exception e){
    		LogUtil.info(LOGGER, "【getContractDetail】出错!{}", e);
			return ResponseDto.responseDtoErrorEnum(ErrorEnum.MSG_FAIL);
    	}
	}

    /**
     * <P>合同列表页</p>
	 *
	 * @author xiangb
	 * @created 2017年10月6日
	 * @param
	 * @return
     */
    @POST
    @Path("/list/v1")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @ApiOperation(value = "合同列表页", notes = ZraApiConst.CON_NEED_PARAM,
    				response = ResponseDto.class)
	public ResponseDto getContracatOfList(@FormParam("uid") String uid) {
    	LogUtil.info(LOGGER, "【getContracatOfList】入参：{}", uid);
    	if(Check.NuNStr(uid)){
    		return  ResponseDto.responseDtoErrorEnum(ErrorEnum.MSG_PARAM_NULL);
    	}
    	try{
    		String contracts = rentContractService.findContractListByUid(uid);
    		LogUtil.info(LOGGER, "【getContracatOfList】查询合同列表返回：{}", contracts);
    		List<RentContractListVo> contractList = null;
    		DataTransferObject contractsObj = JsonEntityTransform.json2DataTransferObject(contracts);
    		if(contractsObj.getCode() == DataTransferObject.ERROR){
    			return ResponseDto.responseDtoFail(contractsObj.getMsg());
    		}
    		contractList = contractsObj.parseData("rentContractList", new TypeReference<List<RentContractListVo>>() {
			});
    		Map<String,Object> resultMap = new HashMap<>();
    		resultMap.put("contractList", contractList);
    		return ResponseDto.responseOK(resultMap);
    	}catch(Exception e){
    		LogUtil.info(LOGGER, "【getContracatOfList】出错：{}", e);
    		return ResponseDto.responseDtoErrorEnum(ErrorEnum.MSG_FAIL);
    	}
    }
    /**
	 * 判断合同和房源是否可签约
	 * @author xiangb
	 * @created 2017年9月13日
	 * @param
	 * @return
	 */
	@POST
	@Path("/valid/v1")
	@Produces("application/json")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@ApiOperation(value = "验证合同和房源是否可签约状态", notes = ZraApiConst.CON_NEED_PARAM
			+ "<p>contractId(String)-合同标识;", response = ResponseDto.class)
	public ResponseDto getContractValid(@FormParam("contractId") String contractId) {
		LogUtil.info(LOGGER, "【getContractValid】入参：{}", contractId);
		if (Check.NuNStr(contractId)) {
			return ResponseDto.responseDtoErrorEnum(ErrorEnum.MSG_PARAM_NULL);
		}
		try{
			String contractStatus = rentContractService.validContract(contractId);
			LogUtil.info(LOGGER, "【getContractValid】验证合同和房源是否可签约:{}", contractStatus);
			DataTransferObject resultDto = JsonEntityTransform
					.json2DataTransferObject(contractStatus);
			if(resultDto.getCode() == DataTransferObject.SUCCESS){
				return ResponseDto.responseOK(null);
			}else{
				return ResponseDto.responseDtoFail(resultDto.getMsg());
			}
		}catch(Exception e){
			LogUtil.info(LOGGER, "【getContractValid】出错!{}", e);
			return ResponseDto.responseDtoErrorEnum(ErrorEnum.MSG_FAIL);
		}
	}
	
	/**
	 * 返回签约主体信息
	 * @author xiangb
	 * @created 2017年9月13日
	 * @param
	 * @return
	 */
	@POST
	@Path("/signSubject/v1")
	@Produces("application/json")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@ApiOperation(value = "查询签约主体信息", notes = ZraApiConst.CON_NEED_PARAM
			+ "<p>contractId(String)-合同标识;", response = ResponseDto.class)
	public ResponseDto getContractSignSubject(@FormParam("contractId") String contractId) {
		LogUtil.info(LOGGER, "【getContractSignSubject】入参：{}", contractId);
		if (Check.NuNStr(contractId)) {
			return ResponseDto.responseDtoErrorEnum(ErrorEnum.MSG_PARAM_NULL);
		}
		try{
			String contractStatus = rentContractService.findCheckinPerson(contractId);
			LogUtil.info(LOGGER, "【getContractSignSubject】查询签约人返回：{}", contractStatus);
			DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(contractStatus);
			if(resultDto.getCode() == DataTransferObject.SUCCESS){
				SignSubjectVo signSubject = resultDto.parseData("signSubject", new TypeReference<SignSubjectVo>() {
				});
				return ResponseDto.responseOK(signSubject);
			}else{
				return ResponseDto.responseDtoFail(resultDto.getMsg());
			}
		}catch(Exception e){
			LogUtil.info(LOGGER, "【getContractSignSubject】出错!{}", e);
			return ResponseDto.responseDtoErrorEnum(ErrorEnum.MSG_FAIL);
		}
	}
	/**
	 * 返回签约房间信息
	 * @author xiangb
	 * @created 2017年9月28日
	 * @param
	 * @return
	 */
	@POST
	@Path("/signRoomInfo/v1")
	@Produces("application/json")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@ApiOperation(value = "查询签约房间信息", notes = ZraApiConst.CON_NEED_PARAM
			+ "<p>contractId(String)-合同标识;", response = ResponseDto.class)
	public ResponseDto getContractSignRoomInfo(@FormParam("contractId") String contractId) {
		LogUtil.info(LOGGER, "【getContractSignRoomInfo】入参：{}", contractId);
		if (Check.NuNStr(contractId)) {
			return ResponseDto.responseDtoErrorEnum(ErrorEnum.MSG_PARAM_NULL);
		}
		try{
			String contractStatus = rentContractService.findRentRoomInfo(contractId);
			LogUtil.info(LOGGER, "【getContractSignRoomInfo】查询签约房间信息返回：{}", contractId);
			DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(contractStatus);
			if(resultDto.getCode() == DataTransferObject.SUCCESS){
				SignRoomInfoVo signRoomInfo = resultDto.parseData("signRoomInfo", new TypeReference<SignRoomInfoVo>() {
				});
				return ResponseDto.responseOK(signRoomInfo);
			}else{
				LogUtil.info(LOGGER, "【getContractSignRoomInfo】返回错误信息：{}", resultDto.getMsg());
				return ResponseDto.responseDtoFail(resultDto.getMsg());
			}
		}catch(Exception e){
			LogUtil.info(LOGGER, "【getContractSignRoomInfo】出错!{}", e);
			return ResponseDto.responseDtoErrorEnum(ErrorEnum.MSG_FAIL);
		}
	}



	/**
	 * 选择支付方式
	 * @author jixd
	 * @created 2017年09月28日 10:54:42
	 * @param
	 * @return
	 */
	@POST
	@Path("/choosePayStyle/v1")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@ApiOperation(value = "example", notes = ZraApiConst.CON_NEED_PARAM
			+ "<br/> projectId(String)-项目bid", response = ResponseDto.class)
	public ResponseDto choosePayStyle(@FormParam("contractId") String contractId) {
		LogUtil.info(LOGGER,"【choosePayStyle】contractId={}",contractId);
		if (StrUtils.isNullOrBlank(contractId)) {
			return ResponseDto.responseDtoErrorEnum(ErrorEnum.MSG_PARAM_NULL);
		}
		try {
			DataTransferObject headerDto = JsonEntityTransform.json2DataTransferObject(rentContractService.findContractHeaderInfo(contractId));
			if (headerDto.getCode() == DataTransferObject.ERROR){
				return ResponseDto.responseDtoFail(headerDto.getMsg());
			}
			ProjectInfoVo header = headerDto.parseData("header", new TypeReference<ProjectInfoVo>() {});
			RentContractEntity rentContractEntity = headerDto.parseData("rentContractEntity", new TypeReference<RentContractEntity>() {});
			double orginalPrice = (double) headerDto.getData().get("originalPrice");

			Map<String,Object> resultMap = new HashMap<>();
			ZrpPayRequest zrpPayRequest = new ZrpPayRequest();
			zrpPayRequest.setRentType(Integer.parseInt(rentContractEntity.getConType()));
			zrpPayRequest.setRentTime(rentContractEntity.getConRentYear());
			LogUtil.info(LOGGER,"【choosePayStyle】zrpPayRequest={}",JsonEntityTransform.Object2Json(zrpPayRequest));
			DataTransferObject zrpPayDto = JsonEntityTransform.json2DataTransferObject(cityTemplateService.listZrpPayStyle(JsonEntityTransform.Object2Json(zrpPayRequest)));
			if (zrpPayDto.getCode() == DataTransferObject.ERROR){
				return ResponseDto.responseDtoFail(zrpPayDto.getMsg());
			}
			LogUtil.info(LOGGER,"【choosePayStyle】zrpPayDto={}",zrpPayDto.toJsonString());
			List<ConfValueVo> list = zrpPayDto.parseData("list", new TypeReference<List<ConfValueVo>>() {});
			if (Check.NuNCollection(list)){
				//服务错误提示信息
				LogUtil.info(LOGGER,"【choosePayStyle】获取服务付款方式为空");
				return ResponseDto.responseDtoErrorEnum(ErrorEnum.MSG_FAIL);
			}
			List<PayItemVo> payList = new ArrayList<>();
			for (ConfValueVo confValueVo : list){
				PayItemVo payItemVo = new PayItemVo();
				payItemVo.setName(confValueVo.getName());
				payItemVo.setCode(confValueVo.getCode());
				PaymentCycleEnum paymentCycleEnum = PaymentCycleEnum.getPaymentCycleEnumByCode(String.valueOf(confValueVo.getCode()));
				payItemVo.setDesc(paymentCycleEnum.getDesc(String.valueOf(orginalPrice),confValueVo.getValue()));
				payList.add(payItemVo);
			}
			resultMap.put("payList",payList);
			resultMap.put("contractId",rentContractEntity.getContractId());
			resultMap.put("contractCode",rentContractEntity.getConRentCode());
			resultMap.put("projectInfo",header);
			return ResponseDto.responseOK(resultMap);
		} catch (Exception e) {
			LogUtil.info(LOGGER,"【choosePayStyle】异常={}",e);
			return ResponseDto.responseDtoFail("服务错误");
		}
	}

	/**
	 * 保存付款方式
	 * @author jixd
	 * @created 2017年09月13日 17:12:08
	 * @param
	 * @return
	 */
	@POST
	@Path("/savePayStyle/v1")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@ApiOperation(value = "example",response = ResponseDto.class)
	public ResponseDto savePayStyle(@FormParam("contractId") String contractId,@FormParam("payCode") String payCode){
		LogUtil.info(LOGGER,"【savePayStyle】参数");
		if (com.zra.common.utils.Check.NuNStr(contractId)){
			return ResponseDto.responseDtoFail("合同号为空");
		}
		if (com.zra.common.utils.Check.NuNStr(payCode)){
			return ResponseDto.responseDtoFail("请选择支付方式");
		}
		try{
			DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(rentContractService.updatePayCodeByContractId(contractId, payCode));
			if (resultDto.getCode() == DataTransferObject.ERROR){
				LogUtil.info(LOGGER,"【savePayStyle】更新失败dto={}",resultDto.toJsonString());
				return ResponseDto.responseDtoFail(resultDto.getMsg());
			}
			return ResponseDto.responseDtoForData(resultDto);
		}catch (Exception e){
			LogUtil.error(LOGGER,"【savePayStyle】保存异常e={}",e);
			return ResponseDto.responseDtoFail("服务错误");
		}
	}


	/**
	 * 查询款项详情
	 * @param uid 用户标识
	 * @param contractId 合同标识
	 * @author cuigh6
	 * @Date 2017/09/28
	 * @return json
	 */
	@POST
	@Path("/contract/payment/detail/v1")
	@Produces("application/json")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@ApiOperation(value = "查询款项详情", notes = ZraApiConst.CON_NEED_PARAM
			+ "<p>contractId(String)-合同标识;<p>uid(String)-用户标识;", response = ResponseDto.class)
	public ResponseDto getPaymentDetail(@FormParam("uid") String uid, @FormParam("contractId") String contractId) {
		LogUtil.info(LOGGER, "【getPaymentDetail】参数=【contractId={},uid={}】", contractId, uid);
		if (Check.NuNStr(contractId) || Check.NuNStr(uid)) {
			return ResponseDto.responseDtoErrorEnum(ErrorEnum.MSG_PARAM_NULL);
		}

		try {
			String detailStr = this.paymentService.getPaymentDetail(uid, contractId);
			DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(detailStr);
			if (dto.getCode() == DataTransferObject.ERROR) {
				return ResponseDto.responseDtoFail(dto.getMsg());
			}
			return ResponseDto.responseOK(dto.getData());
		} catch (Exception e) {
			LOGGER.error("【获取款项详情】出错！error={};contractId={}", e, contractId);
			return ResponseDto.responseDtoErrorEnum(ErrorEnum.MSG_FAIL);
		}
	}

	/**
	 * 查询账单明细
	 * @param contractId 合同标识
	 * @param type 类型 (实时计算=0 or 查询财务系统=1)
	 * @author cuigh6
	 * @Date 2017/09/29
	 * @return json
	 */
	@POST
	@Path("/contract/payment/items/v1")
	@Produces("application/json")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@ApiOperation(value = "查询账单明细", notes = ZraApiConst.CON_NEED_PARAM
			+ "<p>contractId(String)-合同标识;<p>type(Integer)-类型;", response = ResponseDto.class)
	public ResponseDto getPaymentItems(@FormParam("contractId") String contractId,@FormParam("type") Integer type) {
		LogUtil.info(LOGGER, "【getPaymentItems】参数=【contractId={},type={}】", contractId, type);
		if (Check.NuNStr(contractId) || Check.NuNObj(type)) {
			return ResponseDto.responseDtoErrorEnum(ErrorEnum.MSG_PARAM_NULL);
		}

		try {
			String itemsStr = this.paymentService.findBillListByContractId(contractId, type);
			DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(itemsStr);
			if (dto.getCode() == DataTransferObject.ERROR) {
				return ResponseDto.responseDtoFail(dto.getMsg());
			}
			return ResponseDto.responseOK(dto.getData());
		} catch (Exception e) {
			LOGGER.error("【查询账单明细】出错！error={};contractId={}", e, contractId);
			return ResponseDto.responseDtoErrorEnum(ErrorEnum.MSG_FAIL);
		}
	}

	/**
	 * 查询历史生活费用账单
	 * @param contractId 合同标识
	 * @author cuigh6
	 * @Date 2017/10/30
	 * @return json
	 */
	@POST
	@Path("/contract/history/lifeFee/list/v1")
	@Produces("application/json")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@ApiOperation(value = "查询生活费用历史账单", notes = ZraApiConst.CON_NEED_PARAM
			+ "<p>contractId(String)-合同标识;", response = ResponseDto.class)
	public ResponseDto getHistoryLifeFeeList(@FormParam("contractId") String contractId) {
		LogUtil.info(LOGGER, "【getHistoryLifeFeeList】参数=【contractId={}】", contractId);
		if (Check.NuNStr(contractId)) {
			return ResponseDto.responseDtoErrorEnum(ErrorEnum.MSG_PARAM_NULL);
		}

		try {
			String itemsStr = this.paymentService.findHistoryLifeFeeList(contractId);
			DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(itemsStr);
			if (dto.getCode() == DataTransferObject.ERROR) {
				return ResponseDto.responseDtoFail(dto.getMsg());
			}
			return ResponseDto.responseOK(dto.getData());
		} catch (Exception e) {
			LOGGER.error("【查询生活费用历史账单】出错！error={};contractId={}", e, contractId);
			return ResponseDto.responseDtoErrorEnum(ErrorEnum.MSG_FAIL);
		}
	}


	/**
	 * @description: 根据合同id获取用户电子验签明文信息
	 * @author: lusp
	 * @date: 2017/9/12 20:03
	 * @params: contractId
	 * @return: ResponseDto
	 */
	@POST
	@Path("queryCheckSignCusInfo/v1")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "获取用户电子验签明文信息", notes = "contractId(String)-合同id", response = ResponseDto.class)
	public ResponseDto queryCheckSignCusInfo(@Valid @NotNull @FormParam("contractId") String contractId) {
		try {
			RentContractEntity rentContractEntity = new RentContractEntity();
			rentContractEntity.setContractId(contractId);
			String resultJson = checkSignService.findCheckSignCusInfoVoByUid(JsonEntityTransform.Object2Json(rentContractEntity));
			LogUtil.info(LOGGER,"【queryCheckSignCusInfo】根据合同id获取用户电子验签信息结果:{}", resultJson);
			DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(resultJson);
			if(dto.getCode() == DataTransferObject.ERROR){
				LogUtil.error(LOGGER,"【queryCheckSignCusInfo】根据合同id获取用户电子验签信息失败！errMsg:{}", dto.getMsg());
				return ResponseDto.responseDtoErrorEnum(ErrorEnum.MSG_FAIL);
			}
			CheckSignCusInfoVo checkSignCusInfoVo = SOAResParseUtil.getValueFromDataByKey(resultJson,"obj",CheckSignCusInfoVo.class);
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append(checkSignCusInfoVo.getContractId())
					     .append("_")
					     .append(checkSignCusInfoVo.getCustomerUid())
						 .append("_")
						 .append(checkSignCusInfoVo.getCertType())
						 .append("_")
						 .append(checkSignCusInfoVo.getCertNum())
						 .append("_")
					 	 .append(checkSignCusInfoVo.getConCycleCode())
						 .append("_")
						 .append(checkSignCusInfoVo.getConStartDate())
						 .append("_")
						 .append(checkSignCusInfoVo.getConEndDate())
						 .append("_")
					     .append(checkSignCusInfoVo.getRoomId());
			LogUtil.info(LOGGER,"【queryCheckSignCusInfo】方法执行结束");
			return ResponseDto.responseOK(stringBuilder.toString());
		} catch (Exception e) {
			LogUtil.error(LOGGER,"【queryCheckSignCusInfo】出错！", e);
			return ResponseDto.responseDtoErrorEnum(ErrorEnum.MSG_FAIL);
		}
	}

	/**
	 * @description: 验证签名密文是否有效（不走网关）
	 * @author: lusp
	 * @date: 2017/9/13 16:03
	 * @params: contractId,signMessage
	 * @return: ResponseDto
	 */
	@POST
	@Path("verifySignature/v1")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "根据密文信息进行验签", notes = "contractId(String)-合同id,signMessage(String)-密文", response = ResponseDto.class)
	public AppResult verifySignature(@Valid @NotNull @FormParam("p") String p,@Valid @NotNull @FormParam("sign") String sign) {
		if (StrUtils.isNullOrBlank(p)){
			return AppResult.toFail(null, ErrorEnum.MSG_PARAM_NULL);
		}
		try {
			VerifySignatureDto dto = jacksonReadValue(p, VerifySignatureDto.class);
			LogUtil.info(LOGGER,"【verifySignature】入参！contractId:{},signMessage:{}", dto.getContractId(),dto.getSignMessage());
			ItrusUtil itrusUtil = new ItrusUtil();
			ItrusUtilPortType itrusUtilPortType = itrusUtil.getItrusUtilHttpPort();
			String signMessage = dto.getSignMessage();
			String contractId = dto.getContractId();
			signMessage = signMessage.replace("\n","");
			signMessage = signMessage.replace("\r","");
			JSONObject paramJson = new JSONObject();
			paramJson.put("Message","");
			paramJson.put("signMessage",signMessage);
			/**
			 *
			 * {
			 "Original": {
			 "cert_num": 256794,
			 "cert_type": "台湾居民来往通行证",
			 "end_date": "2018-08-25",
			 "house_code": "BJZRGZ071606531",
			 "name": "陈培仁",
			 "payment": "季付",
			 "start_date": "2017-08-26",
			 "uid": "4bcc0447-bbd0-4f32-a079-3b0725026101"
			 },
			 "CN": "陈培仁",
			 "OU0": "技术中心",
			 "O": "北京自如友家资产管理有限公司",
			 "SN": "05903879",
			 "flag": "true",
			 "result": "证书可用。",
			 "SerialNumber": "4D77E39118DC9BD0C79AE29EF8CE98A988726F45"
			 }*/
			String signJsonStr = itrusUtilPortType.verify(paramJson.toJSONString());
			LogUtil.info(LOGGER,"【verifySignature】验签返回值！resultJson{}", signJsonStr);
			JSONObject jsonObj = JSONObject.parseObject(signJsonStr);
			Boolean flag = jsonObj.getBoolean("flag");
			//获取加密原文，校验原文中的重要信息，防止有人重复使用有效密串作弊
			String responseContractId = null;
			String originalStr = jsonObj.getString("Original");
			if(!Check.NuNStrStrict(originalStr)&&originalStr.contains("_")){
				responseContractId = originalStr.substring(0,originalStr.indexOf("_"));
			}
			if(!flag||!contractId.equals(responseContractId)){
				LogUtil.info(LOGGER,"【verifySignature】验签失败！contractId:{},signMessage:{},signJsonStr:{}", contractId, signMessage, signJsonStr);
				return AppResult.toFail(ErrorEnum.SIGN_CHECK_FAIL);
			}
			return AppResult.toSuccess(null);
		} catch (Exception e) {
			LogUtil.error(LOGGER,"【verifySignature】出错！", e);
			return AppResult.toFail(ErrorEnum.MSG_FAIL);
		}
	}

	/**
	 * jackson将json串解析成类
	 *
	 * @Author: wangxm113
	 * @CreateDate: 2016-07-29
	 */
	private <T> T jacksonReadValue(String p, Class<T> className) throws Exception {
		String decryptP = DESUtils.decrypt(p);// 解密
		return new ObjectMapper()
				.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false)// 解析未知参数
				.readValue(decryptP, className);
	}


	/**
	 * @description: 保存电子验签异常信息（无纸化错误日志保存接口）
	 * @author: lusp
	 * @date: 2017/9/25 17:03
	 * @params: contractId
	 * @return: ResponseDto
	 */
	@POST
	@Path("saveErrorLog/v1")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "保存无纸化错误日志", notes = "contractId(String)-合同id,errMsg(String)-错误消息", response = ResponseDto.class)
	public ResponseDto saveErrorLog(@Valid @NotNull @FormParam("contractId") String contractId, @Valid @NotNull @FormParam("errMsg") String errMsg) {
		try {
			CheckSignErrorLogEntity checkSignErrorLogEntity = new CheckSignErrorLogEntity();
			checkSignErrorLogEntity.setContractId(contractId);
			checkSignErrorLogEntity.setErrMsg(errMsg);
			String resultJson = checkSignService.saveCheckSignErrorLog(JsonEntityTransform.Object2Json(checkSignErrorLogEntity));
			DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(resultJson);
			if(dto.getCode() == DataTransferObject.ERROR){
				LogUtil.info(LOGGER,"【saveErrorLog】保存错误日志失败！errMsg:{}", dto.getMsg());
				return ResponseDto.responseDtoErrorEnum(ErrorEnum.MSG_FAIL);
			}
			return ResponseDto.responseDtoForData(dto);
		} catch (Exception e) {
			LogUtil.error(LOGGER,"【saveErrorLog】出错！", e);
			return ResponseDto.responseDtoErrorEnum(ErrorEnum.MSG_FAIL);
		}
	}

	/**
	 * @description: 提交合同
	 * @author: lusp
	 * @date: 2017/10/9 10:47
	 * @params: contractId,contractTotalMoney
	 * @return: ResponseDto
	 */
	@POST
	@Path("submitContract/v1")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "提交合同", notes = "contractId(String)-合同id,contractTotalMoney(String):合同总金额", response = ResponseDto.class)
	public ResponseDto submitContract(@Valid @NotNull @FormParam("contractId") String contractId,@Valid @NotNull @FormParam("contractTotalMoney") String contractTotalMoney){
		LogUtil.info(LOGGER,"【submitContract】方法开始,contractId:{}",contractId);
		RentContractEntity rentContractEntity = null;
		try {
			//1.首先判断合同状态，必须是合同待签约的状态时，才能正常提交合同，其余都为"非法操作"
			LogUtil.info(LOGGER, "【submitContract】第一步判断合同状态开始,contractId:{}", contractId);
			String contractJson = rentContractService.findContractBaseByContractId(contractId);
			DataTransferObject contractDto = JsonEntityTransform.json2DataTransferObject(contractJson);
			if (contractDto.getCode() == DataTransferObject.ERROR) {
				LogUtil.error(LOGGER, "【submitContract】根据合同id查询合同信息失败！errMsg:{}", contractDto.getMsg());
				return ResponseDto.responseDtoErrorEnum(ErrorEnum.MSG_FAIL);
			}
			rentContractEntity = SOAResParseUtil.getValueFromDataByKey(contractJson, "rentContractEntity", RentContractEntity.class);
			if (Check.NuNObj(rentContractEntity)) {
				LogUtil.error(LOGGER, "【submitContract】根据合同id查询合同信息为空！contractId:{}", contractId);
				return ResponseDto.responseDtoErrorEnum(ErrorEnum.MSG_FAIL);
			}
			if (!rentContractEntity.getConStatusCode().equals(ContractStatusEnum.WQY.getStatus())) {
				LogUtil.info(LOGGER, "【submitContract】提交合同非法操作！contractId:{}", contractId);
				return ResponseDto.responseDtoErrorEnum(ErrorEnum.MSG_FAIL);
			}
			//2.校验合同总金额
			LogUtil.info(LOGGER, "【submitContract】第二步校验合同总金额开始,contractId:{}", contractId);
			if (contractTotalMoney.startsWith(BillMsgConstant.RMB)) {
				contractTotalMoney = contractTotalMoney.substring(1);
			}
			String paymentTermsJson = paymentService.getContractTerms(JsonEntityTransform.Object2Json(rentContractEntity));
			DataTransferObject paymentTermsDto = JsonEntityTransform.json2DataTransferObject(paymentTermsJson);
			if (paymentTermsDto.getCode() == DataTransferObject.ERROR) {
				LogUtil.error(LOGGER, "【submitContract】根据合同id计算合同总金额失败！contractId:{},errMsg:{}", contractId, paymentTermsDto.getMsg());
				return ResponseDto.responseDtoErrorEnum(ErrorEnum.MSG_FAIL);
			}
			PaymentTermsDto termsDto = SOAResParseUtil.getValueFromDataByKey(paymentTermsJson, "paymentTermsDto", PaymentTermsDto.class);
			BigDecimal contractTotalMoneyBigDecimal = termsDto.getTotalMoney();
			if (!contractTotalMoney.equals(contractTotalMoneyBigDecimal.toString())) {
				LogUtil.info(LOGGER, "【submitContract】提交合同校验总金额不相等！contractId:{},contractTotalMoneyParam:{},contractTotalMoneyBigDecimal:{}", contractId, contractTotalMoney, contractTotalMoneyBigDecimal);
				return ResponseDto.responseDtoErrorEnum(ErrorEnum.MSG_FAIL);
			}
			//3.锁房、保存出房记录
			LogUtil.info(LOGGER, "【submitContract】第三步锁房和保存出房记录开始,contractId:{}", contractId);
			RoomRentInfoEntity roomRentInfoEntity = new RoomRentInfoEntity();
			roomRentInfoEntity.setFprojectid(rentContractEntity.getProjectId());
			roomRentInfoEntity.setFroomid(rentContractEntity.getRoomId());
			roomRentInfoEntity.setFcontractid(rentContractEntity.getContractId());
			roomRentInfoEntity.setFstartdate(rentContractEntity.getConStartDate());
			roomRentInfoEntity.setFenddate(rentContractEntity.getConEndDate());
			roomRentInfoEntity.setFrentprice(rentContractEntity.getFactualprice() == null ? null : rentContractEntity.getFactualprice().doubleValue());
			roomRentInfoEntity.setFcreatorid(rentContractEntity.getFhandlezocode());
			roomRentInfoEntity.setFcreatorname(rentContractEntity.getFhandlezo());
			roomRentInfoEntity.setCityid(rentContractEntity.getCityid());

			//如果是续约合同,只保存出房记录、不进行锁房,新签的是锁房加保存出房记录
			String saveAndUpdateJson = null;
			if(ContractSignTypeEnum.RENEW.getValue().equals(rentContractEntity.getFsigntype())){
				saveAndUpdateJson = roomService.saveRoomRentInfo(JsonEntityTransform.Object2Json(roomRentInfoEntity));
			}else{
				saveAndUpdateJson = roomService.updateRoomInfoAndSaveRentInfo(JsonEntityTransform.Object2Json(roomRentInfoEntity));
			}
			DataTransferObject saveAndUpdateDto = JsonEntityTransform.json2DataTransferObject(saveAndUpdateJson);
			if (saveAndUpdateDto.getCode() == DataTransferObject.ERROR) {
				LogUtil.error(LOGGER, "【submitContract】锁房、保存出房记录失败！paramJson:{},errMsg:{}", JsonEntityTransform.Object2Json(roomRentInfoEntity), saveAndUpdateDto.getMsg());
				return ResponseDto.responseDtoFail(saveAndUpdateDto.getMsg());
			}
		}catch (Exception e) {
			LogUtil.error(LOGGER,"【submitContract】锁房和保存出房记录出现异常,contractId:{},e:{}",contractId, e);
			return ResponseDto.responseDtoFail("后台api报错！");
		}

		//4.更新合同状态和合同提交时间
		try {
			LogUtil.info(LOGGER, "【submitContract】第四步,更新合同状态和合同提交时间开始,contractId:{}", contractId);
			String updateContractStatusJson = rentContractService.updateContractInfoForSubmit(rentContractEntity.getContractId());
			DataTransferObject updateContractStatusDto = JsonEntityTransform.json2DataTransferObject(updateContractStatusJson);
			if (updateContractStatusDto.getCode() == DataTransferObject.ERROR) {
				LogUtil.error(LOGGER, "【submitContract】根据合同id更新合同状态为待支付失败！contractId:{},errMsg:{}", contractId, updateContractStatusDto.getMsg());
				//回滚锁房和保存出房记录操作
				roomService.updateRoomInfoAndDeleteRentInfo(rentContractEntity.getRoomId());
				return ResponseDto.responseDtoErrorEnum(ErrorEnum.MSG_FAIL);
			}
		}catch (Exception e) {
			LogUtil.error(LOGGER,"【submitContract】更新合同状态和合同提交时间出现异常,contractId:{},e:{}",contractId, e);
			//回滚锁房和保存出房记录操作
			roomService.updateRoomInfoAndDeleteRentInfo(rentContractEntity.getRoomId());
			return ResponseDto.responseDtoFail("后台api报错！");
		}

		//5.同步合同到财务
		try {
			LogUtil.info(LOGGER, "【submitContract】第五步,同步合同到财务开始,contractId:{}", contractId);
			String syncContractJson = callFinanceService.syncContract(contractId);
			LogUtil.info(LOGGER,"【submitContract】第五步，同步合同到财务结束,contractId:{},resultJson:{}",contractId,syncContractJson);
			DataTransferObject syncContractDto = JsonEntityTransform.json2DataTransferObject(syncContractJson);
			if (syncContractDto.getCode() == DataTransferObject.ERROR) {
				LogUtil.error(LOGGER, "【submitContract】根据合同id同步合同到财务失败！contractId:{},errMsg:{}", contractId, syncContractDto.getMsg());
				//回滚锁房和保存出房记录操作
				roomService.updateRoomInfoAndDeleteRentInfo(rentContractEntity.getRoomId());
				//回滚合同状态为未签约
				rentContractService.rollBackContractInfoForAppSubmit(rentContractEntity.getContractId());
				return ResponseDto.responseDtoErrorEnum(ErrorEnum.MSG_FAIL);
			}
		}catch (Exception e) {
			LogUtil.error(LOGGER,"【submitContract】同步合同到财务出现异常,contractId:{},e:{}",contractId, e);
			//回滚锁房和保存出房记录操作
			roomService.updateRoomInfoAndDeleteRentInfo(rentContractEntity.getRoomId());
			//回滚合同状态为未签约
			rentContractService.rollBackContractInfoForAppSubmit(rentContractEntity.getContractId());
			return ResponseDto.responseDtoFail("后台api报错！");
		}

		//6.获取应收账单编号，同步应收账单到财务
		try {
			LogUtil.info(LOGGER,"【submitContract】第六步，同步应收账单开始,contractId:{}",contractId);
			String createReceiptBillJson = callFinanceService.createReceiptBill(rentContractEntity.getContractId());
			LogUtil.info(LOGGER,"【submitContract】第六步，同步应收账单结束,contractId:{},resultJson:{}",contractId,createReceiptBillJson);
			DataTransferObject createReceiptBillDto = JsonEntityTransform.json2DataTransferObject(createReceiptBillJson);
			if(createReceiptBillDto.getCode()==DataTransferObject.ERROR){
				LogUtil.error(LOGGER,"【submitContract】根据合同id同步应收账单到财务失败！contractId:{},errMsg:{}", contractId,createReceiptBillDto.getMsg());
				//回滚锁房和保存出房记录操作
				roomService.updateRoomInfoAndDeleteRentInfo(rentContractEntity.getRoomId());
				//回滚合同状态为未签约
				rentContractService.rollBackContractInfoForAppSubmit(rentContractEntity.getContractId());
				return ResponseDto.responseDtoErrorEnum(ErrorEnum.MSG_FAIL);
			}
			DataTransferObject dto = new DataTransferObject();
			dto.setMsg("提交成功");
			LogUtil.info(LOGGER,"【submitContract】执行完毕返回,contractId：{}",contractId);
			return ResponseDto.responseOK(dto);
		}catch (Exception e){
			LogUtil.error(LOGGER,"【submitContract】同步应收到财务出现异常,contractId:{},e:{}",contractId, e);
			//回滚锁房和保存出房记录操作
			roomService.updateRoomInfoAndDeleteRentInfo(rentContractEntity.getRoomId());
			//回滚合同状态为未签约
			rentContractService.rollBackContractInfoForAppSubmit(rentContractEntity.getContractId());
			return ResponseDto.responseDtoFail("后台api报错！");
		}
	}

	/**
	 * 查询账单支付详情
	 * @param period 期数
	 * @param contractId 合同标识
	 * @author cuigh6
	 * @Date 2017/10/10
	 * @return json
	 */
	@POST
	@Path("/bill/payment/detail/v1")
	@Produces("application/json")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@ApiOperation(value = "查询账单支付详情", notes = ZraApiConst.CON_NEED_PARAM
			+ "<p>contractId(String)-合同标识;<p>period(int)-期数;", response = ResponseDto.class)
	public ResponseDto getBillPayDetail(@FormParam("period") Integer period, @FormParam("contractId") String contractId) {
		LogUtil.info(LOGGER, "【getBillPayDetail】参数=【contractId={},period={}】", contractId, period);
		if (Check.NuNStr(contractId) || Check.NuNObj(period)) {
			return ResponseDto.responseDtoErrorEnum(ErrorEnum.MSG_PARAM_NULL);
		}

		try {
			String detailStr = this.paymentService.findBillPayDetail(contractId, period);
			LOGGER.info("【getBillPayDetail】查询账单支付详情返回:{}", detailStr);
			DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(detailStr);
			if (dto.getCode() == DataTransferObject.ERROR) {
				return ResponseDto.responseDtoFail(dto.getMsg());
			}
			return ResponseDto.responseOK(dto.getData().get("payDetail"));
		} catch (Exception e) {
			LOGGER.error("【getBillPayDetail】查询账单支付详情出错！error={};contractId={}", e, contractId);
			return ResponseDto.responseDtoErrorEnum(ErrorEnum.MSG_FAIL);
		}
	}

	/**
	 * <p>合同条款页文本</p>
	 * @author xiangb
	 * @created 2017年10月12日
	 * @param
	 * @return
	 */
	@POST
	@Path("/provision/v1")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@ApiOperation(value = "查询合同条款页", notes = ZraApiConst.CON_NEED_PARAM
			+ "<p>contractId(String)-合同标识", response = ResponseDto.class)
	public ResponseDto getContractHtml(@FormParam("contractId") String contractId) {
		LogUtil.info(LOGGER, "【getContractProvision】参数:{}", contractId);
		try {
			String contractHtmlJson = checkSignService.getContractHtml(contractId);
			return ResponseDto.responseDtoForData(JsonEntityTransform.json2DataTransferObject(contractHtmlJson));
		}catch (Exception e) {
			LOGGER.error("【getContractProvision】出错:{}", e);
			return ResponseDto.responseDtoErrorEnum(ErrorEnum.MSG_FAIL);
		}
	}
	
	
	/**
	 * <p>支付回调接口</p>
	 * @author xiangb
	 * @created 2017年10月12日
	 * @param
	 * @return
	 */
	@POST
	@Path("/paymentCallback/v1")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "支付回调接口", notes = ZraApiConst.CON_NEED_PARAM 
			+ "<p>param(String)-回调的参数",response = Response.class)
	public Response paymentCallback(Map<String,Object> param){
		LogUtil.info(LOGGER, "【paymentCallback】参数:{}", JsonEntityTransform.Object2Json(param));
		Map<String,String> returnMap = new HashMap<>();
		/**包括cwOrderId,和param，先按下面的格式解析
		 {
    		"list":[
		        {
		            "billAmount":"939000", 实收金额
		            "billNum":"khfz52508817", 应收账单号
		            "costCode":"khfz",  费用项
		            "receivedAmount":"939000" 应收金额
		        }
		    ],
		    "type":"1"— 类型 1 普通支付 2 批量代扣
		}
		 */
		try {
			long startTime= System.currentTimeMillis();
			if (Check.NuNObj(param)) {
				returnMap.put("status", "error");
				returnMap.put("error_message", "参数为空");
				return Response.ok().entity(returnMap).build();
			}
			String callFinanceStr = callFinanceService.paymentCallback(JsonEntityTransform.Object2Json(param));
			LogUtil.info(LOGGER,"【paymentCallback】更新合同执行时间:{}", System.currentTimeMillis()-startTime);
			LogUtil.info(LOGGER,"【paymentCallback】更新合同状态返回：{}",callFinanceStr);
			DataTransferObject callFinanceObj = JsonEntityTransform.json2DataTransferObject(callFinanceStr);
			if(callFinanceObj.getCode() == DataTransferObject.ERROR){
				LogUtil.error(LOGGER,"【paymentCallback】更新合同状态出错:{}",callFinanceObj.getMsg());
				returnMap.put("status", "error");
				returnMap.put("error_message", "系统错误");
				return Response.ok().entity(returnMap).build();
			}
			returnMap.put("status", "success");
			returnMap.put("error_message", "回调成功");
			LogUtil.info(LOGGER,"【paymentCallback】回调成功，参数为：{}",JsonEntityTransform.Object2Json(param));
			LogUtil.info(LOGGER,"【paymentCallback】这个方法执行时间:{}", System.currentTimeMillis()-startTime);
			return Response.ok().entity(returnMap).build();
		} catch (Exception e) {
			LogUtil.error(LOGGER,"【paymentCallback】回调参数为：{},出错:{}", JsonEntityTransform.Object2Json(param),e);
			returnMap.put("status", "error");
			returnMap.put("error_message", "系统错误");
			return Response.ok().entity(returnMap).build();
		}
	}

	/**
	  * @description: 收款单回调（后台签约财务回调）
	  * @author: lusp
	  * @date: 2017/11/8 下午 20:39
	  * @params: param
	  * @return: Response
	  */
	@POST
	@Path("/receiptBillCallback/v1")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "收款单回调接口", notes = ZraApiConst.CON_NEED_PARAM
			+ "<p>param(String)-回调的参数",response = Response.class)
	public Response receiptBillCallback(Map<String,Object> param){
		LogUtil.info(LOGGER, "【receiptBillCallback】param:{}", JsonEntityTransform.Object2Json(param));
		Map<String,String> returnMap = new HashMap<>();
		DataTransferObject dto = new DataTransferObject();
		try {
			if (Check.NuNObj(param)) {
				returnMap.put("status", "error");
				returnMap.put("error_message", "参数为空");
				return Response.ok().entity(returnMap).build();
			}
			List<Map<String,Object>> list =(List<Map<String,Object>>)param.get("list");
			List<String> billNumList =new ArrayList<String>();
			for(Map<String,Object> map :list){
				billNumList.add((String)map.get("billNum"));
			}
			String resultJson = callFinanceService.receiptBillCallbackUpdateStatus(JsonEntityTransform.Object2Json(billNumList));
			dto = JsonEntityTransform.json2DataTransferObject(resultJson);
			if(dto.getCode()==DataTransferObject.ERROR){
				returnMap.put("status", "error");
				returnMap.put("error_message", "系统错误");
				return Response.ok().entity(returnMap).build();
			}
			returnMap.put("status", "success");
			returnMap.put("error_message", "回调成功");
			LogUtil.info(LOGGER,"【receiptBillCallback】回调成功");
			return Response.ok().entity(returnMap).build();
		} catch (Exception e) {
			LogUtil.error(LOGGER,"【receiptBillCallback】e:{},param:{}",e,JsonEntityTransform.Object2Json(param));
			returnMap.put("status", "error");
			returnMap.put("error_message", "系统错误");
			return Response.ok().entity(returnMap).build();
		}
	}

	/**
	 * 查询支付页面详情
	 * @param period 期数
	 * @param contractId 合同标识
	 * @author cuigh6
	 * @Date 2017/10/12
	 * @return json
	 */
	@POST
	@Path("/pay/page/detail/v1")
	@Produces("application/json")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@ApiOperation(value = "查询支付页面详情", notes = ZraApiConst.CON_NEED_PARAM
			+ "<p>contractId(String)-合同标识;<p>period(int)-期数;<p>billType(String)-账单类型 1001-life 1007-room;", response = ResponseDto.class)
	public ResponseDto getPayPageInfo(@FormParam("period") Integer period, @FormParam("contractId") String contractId,
	                                  @FormParam("billType") String billType) {
		LogUtil.info(LOGGER, "【getPayPageInfo】参数=【contractId={},period={},billType={}】", contractId, period, billType);
		if (Check.NuNStr(contractId)) {
			return ResponseDto.responseDtoErrorEnum(ErrorEnum.MSG_PARAM_NULL);
		}

		try {
			String detailStr = this.paymentService.findPayPageDetail(contractId, period, billType);
			LOGGER.info("【findPayPageDetail】返回:{}",detailStr);
			DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(detailStr);
			if (dto.getCode() == DataTransferObject.ERROR) {
				return ResponseDto.responseDtoFail(dto.getMsg());
			}
			return ResponseDto.responseOK(dto.getData().get("billDetail"));
		} catch (Exception e) {
			LOGGER.error("【findPayPageDetail】查询支付页面详情出错！error={};contractId={}", e, contractId);
			return ResponseDto.responseDtoErrorEnum(ErrorEnum.MSG_FAIL);
		}
	}

	/**
	 * 查询个人中心待支付列表
	 * @param uid 用户标识
	 * @return
	 * @author cuigh6
	 * @Date 2017年11月13日
	 */
	@POST
	@Path("/contract/pendingPay/list/v1")
	@Produces("application/json")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@ApiOperation(value = "查询个人中心待支付列表", notes = "<p>uid(String)-用户标识", response = ResponseDto.class)
	public ResponseDto getPendingPayList(@FormParam("uid") String uid) {
		LogUtil.info(LOGGER, "【getPendingPayList】参数=【uid={}】", uid);
		if (Check.NuNStr(uid)) {
			return ResponseDto.responseDtoErrorEnum(ErrorEnum.MSG_PARAM_NULL);
		}
		try {
			String detailStr = this.paymentService.getMustPayBillList(uid);
			LOGGER.info("【getMustPayBillList】返回:{}",detailStr);
			DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(detailStr);
			if (dto.getCode() == DataTransferObject.ERROR) {
				return ResponseDto.responseDtoFail(dto.getMsg());
			}
			return ResponseDto.responseOK(dto.getData());
		} catch (Exception e) {
			LOGGER.error("【getPendingPayList】查询个人中心待支付列表出错！error={};uid={}", e, uid);
			return ResponseDto.responseDtoErrorEnum(ErrorEnum.MSG_FAIL);
		}
	}

	/**
	 * 关闭合同接口
	 * @param contractId 合同标识
	 * @param type 关闭类型
	 * @author cuigh6
	 * @Date 2017/10/17
	 * @return json
	 */
	@POST
	@Path("/close/v1")
	@Produces("application/json")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@ApiOperation(value = "查询支付页面详情", notes = ZraApiConst.CON_NEED_PARAM
			+ "<p>contractId(String)-合同标识;", response = ResponseDto.class)
	public ResponseDto closeContract(@FormParam("contractId") String contractId,@FormParam("type") Integer type) {
		LogUtil.info(LOGGER, "【closeContract】参数=【contractId={}】", contractId);
		if (Check.NuNStr(contractId)) {
			return ResponseDto.responseDtoErrorEnum(ErrorEnum.MSG_PARAM_NULL);
		}

		try {
			String detailStr = this.rentContractService.closeContract(contractId,type);
			DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(detailStr);
			if (dto.getCode() == DataTransferObject.ERROR) {
				return ResponseDto.responseDtoFail(dto.getMsg());
			}
			return ResponseDto.responseOK();
		} catch (Exception e) {
			LOGGER.error("【closeContract】关闭合同接口！error={};contractId={}", e, contractId);
			return ResponseDto.responseDtoErrorEnum(ErrorEnum.MSG_FAIL);
		}
	}

	private boolean isShowBill(String conCycleCode,String payDateStr){
		if(Check.NuNStr(conCycleCode) || Check.NuNStr(payDateStr)){
			return false;
		}
		try{
			boolean isShow = false;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date payDate = sdf.parse(payDateStr);
			Date now = new Date();;
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(now);
			//付款周期：1 月付 3 季付 6 半年付 12 年付 9 一次性付清（短租）
			if(conCycleCode.equals(PaymentCycleEnum.YF.getCode())){//月付，距离支付日期前10天展示
				calendar.add(Calendar.DATE, 10);
				now = calendar.getTime();
				if(payDate.after(now)){
					isShow  = true;
				}
			}else if(conCycleCode.equals(PaymentCycleEnum.JF.getCode())){//季付，距离支付日期前30天展示
				calendar.add(Calendar.DATE, 30);
				now = calendar.getTime();
				if(payDate.after(now)){
					isShow  = true;
				}
			}else if(conCycleCode.equals(PaymentCycleEnum.BNF.getCode())){//半年付，距离支付日期前60天展示
				calendar.add(Calendar.DATE, 60);
				now = calendar.getTime();
				if(payDate.after(now)){
					isShow  = true;
				}
			}else{
				isShow  = false;
			}
			return isShow;
		}catch(Exception e){
			LogUtil.info(LOGGER, "【isShowBill】出错：{}", e);
			return false;
		}
	}
	/**
     * <P>根据合同ID判断签约人信息是否符合限制</p>
	 *
	 * @author xiangb
	 * @created 2017年10月23日
	 * @param
	 * @return
     */
    @POST
    @Path("/valid/signPerson/v1")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @ApiOperation(value = "判断年龄是否超过签约限制", notes = ZraApiConst.CON_NEED_PARAM
            + "<br/> contractId(String)-合同ID", response = ResponseDto.class)
	public ResponseDto validSignPerson(@FormParam("contractId") String contractId) {
    	LogUtil.info(LOGGER, "【validSignPerson】入参：{}", contractId);
    	try{
    		if (Check.NuNStr(contractId)) {
    			return ResponseDto.responseDtoErrorEnum(ErrorEnum.MSG_PARAM_NULL);
    		}
    		String ageStr = rentCheckinService.validSignPerson(contractId);
    		LogUtil.info(LOGGER, "【validSignPerson】验证年龄是否小于40岁返回：{}", ageStr);
    		DataTransferObject ageObject = JsonEntityTransform.json2DataTransferObject(ageStr);
    		if(ageObject.getCode() == DataTransferObject.SUCCESS){
    			return ResponseDto.responseOK();
    		}else{
    			return ResponseDto.responseDtoFail(ageObject.getMsg());
    		}
    		
    	}catch(Exception e){
    		LogUtil.info(LOGGER, "【validSignPerson】出错!{}", e);
			return ResponseDto.responseDtoErrorEnum(ErrorEnum.MSG_FAIL);
    	}
	}


	/**
	 * 验证是否存在未完成的生活费用账单
	 *
	 * @param contractCode 合同号
	 * @return json
	 * @author cuigh6
	 * @Date 2017年11月5日
	 *
	 */
	@POST
	@Path("/valid/notFinishLifeBill/v1")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@ApiOperation(value = "判断年龄是否超过签约限制", notes ="<br/> contractCode(String)-合同号", response = ResponseDto.class)
	public ResponseDto validNotFinishLifeBill(@FormParam("contractCode") String contractCode) {
		LogUtil.info(LOGGER, "【validNotFinishLifeBill】入参：{}", contractCode);
		try{
			if (Check.NuNStr(contractCode)) {
				return ResponseDto.responseDtoErrorEnum(ErrorEnum.MSG_PARAM_NULL);
			}
			String result = rentContractService.isExistNotFinishedBill(contractCode, DocumentTypeEnum.LIFE_FEE.getCode());
			LogUtil.info(LOGGER, "【isExistNotFinishedBill】验证是否有未完成的生活费用账单返回结果：{}", result);
			DataTransferObject resultObject = JsonEntityTransform.json2DataTransferObject(result);
			if(resultObject.getCode() == DataTransferObject.SUCCESS){
				return ResponseDto.responseOK();
			}else{
				return ResponseDto.responseDtoFail(resultObject.getMsg());
			}

		}catch(Exception e){
			LogUtil.info(LOGGER, "【isExistNotFinishedBill】出错!", e);
			return ResponseDto.responseDtoErrorEnum(ErrorEnum.MSG_FAIL);
		}
	}
	
	@POST
    @Path("/invalidContract/list/v1")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @ApiOperation(value = "失效合同列表页", notes = ZraApiConst.CON_NEED_PARAM,
    				response = ResponseDto.class)
	public ResponseDto listInvalidContractList(@FormParam("uid") String uid){
		LogUtil.info(LOGGER, "【listInvalidContractList】入参：{}", uid);
		if(Check.NuNStr(uid)){
			return ResponseDto.responseDtoErrorEnum(ErrorEnum.MSG_PARAM_NULL);
		}
		try{
			String contracts = rentContractService.findInvalidContractList(uid);
    		LogUtil.info(LOGGER, "【listInvalidContractList】查询合同列表返回：{}", contracts);
    		List<RentContractListVo> contractList = null;
    		DataTransferObject contractsObj = JsonEntityTransform.json2DataTransferObject(contracts);
    		if(contractsObj.getCode() == DataTransferObject.ERROR){
    			return ResponseDto.responseDtoFail(contractsObj.getMsg());
    		}
    		contractList = contractsObj.parseData("rentContractList", new TypeReference<List<RentContractListVo>>() {
			});
    		Map<String,Object> resultMap = new HashMap<>();
    		resultMap.put("contractList", contractList);
    		return ResponseDto.responseOK(resultMap);
		}catch(Exception e){
			LogUtil.info(LOGGER, "【listInvalidContractList】出错：{}", e);
			return ResponseDto.responseDtoErrorEnum(ErrorEnum.MSG_FAIL);
		}
	}
	@GET
    @Path("/provision/pdf/v1")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @ApiOperation(value = "合同PDF文件接口", notes = ZraApiConst.CON_NEED_PARAM
    		 + "<br/> contractId(String)-合同ID;<br>uid(String)-用户uid",response = ResponseDto.class)
	public Response provisionPdf(@QueryParam("contractId") String contractId,@QueryParam("uid") String uid){
		LogUtil.info(LOGGER, "【provisionPdf】入参：contractId:{},uid:{}", contractId,uid);
		if(Check.NuNStr(contractId) || Check.NuNStr(uid)){
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		File file = null;
		try{
			Map<String,String> param = new HashMap<String, String>(); 
			param.put("contractId", contractId);
			param.put("uid", uid);
			String contractPdfJson = checkSignService.getContractPdf(JsonEntityTransform.Object2Json(param));
			LogUtil.info(LOGGER, "【provisionPdf】查询合同PDF返回：{}", contractPdfJson);
			DataTransferObject contractPdfObj = JsonEntityTransform.json2DataTransferObject(contractPdfJson);
			String filePath = contractPdfUrl;
			String fileName = contractId +".pdf";
			if(contractPdfObj.getCode() == DataTransferObject.SUCCESS){
				LogUtil.info(LOGGER, "【provisionPdf】查询合同PDF返回成功！");
				filePath += fileName;
				String contractPage = (String) contractPdfObj.getData().get("contractPage");
				file = new File(filePath);
				if(file.exists()){
					file.delete();
				}
				EspUtil.base64StringToPDF(contractPage, filePath);
				LogUtil.info(LOGGER, "【provisionPdf】文件生成成功！");
				fileName = URLEncoder.encode(fileName, "UTF-8");
				
				if(file.exists()){
					 String mt = new MimetypesFileTypeMap().getContentType(file);
					return Response.ok(file,mt)
		            .header("Content-disposition","attachment;filename=" +fileName)
		            .header("Cache-Control", "no-cache").build();
				}else{
					LogUtil.info(LOGGER, "【provisionPdf】文件不存在");
					return Response.status(Response.Status.NOT_FOUND).build();
				}
			}else{
				LogUtil.error(LOGGER, "【provisionPdf】查询合同PDF出错：{}", contractPdfObj.getMsg());
				return Response.status(Response.Status.NOT_FOUND).build();
			}
		}catch(Exception e){
			LogUtil.error(LOGGER, "【provisionPdf】出错：{}", e);
			return Response.status(Response.Status.NOT_FOUND).build();
		}
	}

	/**
	  * @description: 手动调用生成pdf和签章服务方法
	  * @author: lusp
	  * @date: 2018/1/17 下午 17:16
	  * @params:
	  * @return:
	  */
	@POST
	@Path("callGeneratePDFContractAndSignature/v1")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "手动签章", notes = "contractId(String)-合同id", response = ResponseDto.class)
	public ResponseDto callGeneratePDFContractAndSignature(@Valid @NotNull @FormParam("contractId") String contractId) {
		try {
			LogUtil.info(LOGGER, "【callGeneratePDFContractAndSignature】调用开始，contractId:{}", contractId);
			String resultjson = checkSignService.generatePDFContractAndSignature(contractId);
			LogUtil.info(LOGGER, "【callGeneratePDFContractAndSignature】contractId:{},resultJson:{}", contractId, resultjson);
			DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(resultjson);
			return ResponseDto.responseDtoForData(dto);
		} catch (Exception e) {
			LogUtil.info(LOGGER, "【系统异常】e:{}", e);
			return ResponseDto.responseDtoErrorEnum(ErrorEnum.MSG_FAIL);
		}
	}

}
