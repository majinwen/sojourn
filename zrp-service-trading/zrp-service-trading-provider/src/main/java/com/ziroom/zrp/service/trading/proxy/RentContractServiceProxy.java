package com.ziroom.zrp.service.trading.proxy;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.exception.SOAParseException;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.*;
import com.asura.framework.utils.LogUtil;
import com.ziroom.zrp.houses.entity.*;
import com.ziroom.zrp.service.houses.api.CityService;
import com.ziroom.zrp.service.houses.api.HouseTypeService;
import com.ziroom.zrp.service.houses.api.ProjectService;
import com.ziroom.zrp.service.houses.api.RoomService;
import com.ziroom.zrp.service.houses.dto.RentRoomInfoDto;
import com.ziroom.zrp.service.houses.dto.RoomStmartDto;
import com.ziroom.zrp.service.houses.dto.SignInviteDto;
import com.ziroom.zrp.service.houses.entity.RoomContractSmartVo;
import com.ziroom.zrp.service.houses.valenum.RoomSmartLockBindEnum;
import com.ziroom.zrp.service.houses.valenum.SmartPlatformWaterWattPayTypeEnum;
import com.ziroom.zrp.service.trading.api.RentContractService;
import com.ziroom.zrp.service.trading.builder.contract.ContractCostResultDtoBuilder;
import com.ziroom.zrp.service.trading.dto.*;
import com.ziroom.zrp.service.trading.dto.contract.ContractManageDto;
import com.ziroom.zrp.service.trading.dto.contract.ContractSearchPageDto;
import com.ziroom.zrp.service.trading.dto.customer.Cert;
import com.ziroom.zrp.service.trading.dto.customer.Education;
import com.ziroom.zrp.service.trading.dto.customer.Extend;
import com.ziroom.zrp.service.trading.dto.customer.Profile;
import com.ziroom.zrp.service.trading.dto.finance.*;
import com.ziroom.zrp.service.trading.dto.waterwatt.ModifyWattPayTypeDto;
import com.ziroom.zrp.service.trading.entity.RentContractRoomVo;
import com.ziroom.zrp.service.trading.entity.SyncContractVo;
import com.ziroom.zrp.service.trading.pojo.ContractFirstVo;
import com.ziroom.zrp.service.trading.pojo.LeaseCyclePojo;
import com.ziroom.zrp.service.trading.proxy.commonlogic.*;
import com.ziroom.zrp.service.trading.service.*;
import com.ziroom.zrp.service.trading.utils.CardNoUtil;
import com.ziroom.zrp.service.trading.utils.CustomerLibraryUtil;
import com.ziroom.zrp.service.trading.utils.HtmltoPDF;
import com.ziroom.zrp.service.trading.utils.RentHeadInfoUtil;
import com.ziroom.zrp.service.trading.utils.builder.DataTransferObjectBuilder;
import com.ziroom.zrp.service.trading.utils.factory.LeaseCycleFactory;
import com.ziroom.zrp.service.trading.valenum.*;
import com.ziroom.zrp.service.trading.valenum.delivery.DeliveryStateEnum;
import com.ziroom.zrp.service.trading.valenum.finance.DocumentTypeEnum;
import com.ziroom.zrp.service.trading.valenum.finance.VerificateStatusEnum;
import com.ziroom.zrp.service.trading.valenum.waterwatt.WaterwattClearingTypeEnum;
import com.ziroom.zrp.trading.entity.*;
import com.zra.common.constant.BillMsgConstant;
import com.zra.common.constant.ContractMsgConstant;
import com.zra.common.constant.ContractValueConstant;
import com.zra.common.exception.ZrpServiceException;
import com.zra.common.utils.DateUtilFormate;
import com.zra.common.utils.ZraConst;
import com.zra.common.vo.base.BaseFieldVo;
import com.zra.common.vo.contract.PreContractVo;
import com.zra.common.vo.contract.ProjectInfoVo;
import com.zra.common.vo.contract.RentContractListVo;
import com.zra.common.vo.pay.RentBillInfoVo;
import com.zra.common.vo.perseon.SignPersonInfoVo;
import com.zra.common.vo.perseon.SignSubjectVo;
import com.zra.common.vo.room.RoomInfoVo;
import com.zra.common.vo.room.SignRoomInfoVo;

import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;


/**
 * <p>合同逻辑实现</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd
 * @version 1.0
 * @Date Created in 2017年09月07日 20:06
 * @since 1.0
 */
@Component("trading.rentContractServiceProxy")
public class RentContractServiceProxy implements RentContractService{

    private static final Logger LOGGER = LoggerFactory.getLogger(RentContractServiceProxy.class);

    @Resource(name = "trading.rentContractServiceImpl")
    private RentContractServiceImpl rentContractServiceImpl;

    @Resource(name = "trading.rentCheckinPersonServiceImpl")
    private RentCheckinPersonServiceImpl rentCheckinPersonServiceImpl;

    @Resource(name = "houses.roomService")
    private RoomService roomService;
    @Resource(name="houses.projectService")
    private ProjectService projectService;
    @Resource(name="houses.houseTypeService")
    private HouseTypeService houseTypeService;

    @Resource(name="houses.cityService")
    private CityService cityService;

    @Resource(name = "trading.paymentBillServiceImpl")
    private PaymentBillServiceImpl paymentBillServiceImpl;

    @Resource(name = "trading.callFinanceServiceProxy")
    private CallFinanceServiceProxy callFinanceServiceProxy;@Resource(name = "trading.financeBaseCall")
	private FinanceBaseCall financeBaseCall;
    @Resource(name = "trading.paymentServiceProxy")
    private PaymentServiceProxy paymentServiceProxy;

    @Resource(name = "trading.finReceiBillDetailServiceImpl")
    private FinReceiBillDetailServiceImpl receiBillDetailServiceImpl;

	@Resource(name = "trading.rentItemDeliveryServiceImpl")
	private RentItemDeliveryServiceImpl rentItemDeliveryServiceImpl;

	@Resource(name = "trading.rentContractActivityServiceImpl")
	private RentContractActivityServiceImpl rentContractActivityServiceImpl;

	@Resource(name = "trading.rentContractLogic")
	private RentContractLogic rentContractLogic;

	@Resource(name = "trading.smartLockLogic")
	private SmartLockLogic smartLockLogic;

	@Resource(name="trading.financeCommonLogic")
	private FinanceCommonLogic financeCommonLogic;

	@Resource(name = "trading.waterClearingLogic")
	private WaterClearingLogic waterClearingLogic;

	@Resource(name="trading.intellectPlatformLogic")
	private IntellectPlatformLogic intellectPlatformLogic;

    @Value("#{'${min_pay_amount}'.trim()}")
    private String minPayAmount;
    @Value("#{'${pic_url}'.trim()}")
    private String pic_url;
    @Value("#{'${zra_pay_systemId}'.trim()}")
    private String zraPaySystemId;
    @Value("#{'${pay_overtime_condition_money}'.trim()}")
    private String payOverTimeConditionMoney;
    @Value("#{'${less_money_overtime_hours}'.trim()}")
    private String lessMoneyOvertimeHours;
    @Value("#{'${more_money_overtime_hours}'.trim()}")
    private String moreMoneyOvertimeHours;
    @Value("#{'${delivery_overtime_hours}'.trim()}")
    private String deliveryOvertimeHours;
    @Value("#{'${contract_pdf_url}'.trim()}")
    private String contractPdfUrl;
    @Value("#{'${CUSTOMER_SERVICE_TELEPHONE}'.trim()}")
    private String CUSTOMER_SERVICE_TELEPHONE;
    @Value("#{'${zrams_getImage_url}'.trim()}")
    private String ZRAMS_GET_IMAGE;

    @Override
    public String findLatelyContractByUid(String uid){
        DataTransferObject dto = new DataTransferObject();
        if(Check.NuNStr(uid)){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("参数为空！");
            return dto.toJsonString();
        }
        try{
        	//SQL返回的只有个人签约的合同，企业签约先不展示。
            List<RentContractAndDetailEntity> contracts = rentContractServiceImpl.listContractAndDetailByUid(uid);
            if(Check.NuNCollection(contracts)){
    			return dto.toJsonString();//未查询到合同
			}
            LogUtil.info(LOGGER, "【findLatelyContractByUid】入参:{}", uid);
    		/**
    		 * 展示顺序，待签约-待支付-待物业交割,交割后的合同（包括后台签约的，不展示后台待物业交割前的合同）
    		 */
    		RentContractAndDetailEntity rentContractAndDetailEntity = null;
    		Optional<RentContractAndDetailEntity> optional = null;
    		optional = contracts.stream().filter(
					v ->ContractStatusEnum.WQY.getStatus().equals(v.getConStatusCode())
					&& ContractSourceEnum.APP.getCode() == Integer.valueOf(v.getFsource()))
					.findFirst();//展示最新未签约的合同
			if (optional.isPresent()) {
				rentContractAndDetailEntity = optional.get();
				LogUtil.info(LOGGER, "[findLatelyContractByUid]查询待签约的合同为：{}", JsonEntityTransform.Object2Json(rentContractAndDetailEntity));
			}
			if(Check.NuNObj(rentContractAndDetailEntity)){
				optional = contracts.stream().filter(
					v ->ContractStatusEnum.DZF.getStatus().equals(v.getConStatusCode())
					&& ContractSourceEnum.APP.getCode() == Integer.valueOf(v.getFsource()))
					.findFirst();//展示最新待支付的合同
				if (optional.isPresent()) {
					rentContractAndDetailEntity = optional.get();
					LogUtil.info(LOGGER, "[findLatelyContractByUid]查询待支付的合同为：{}", JsonEntityTransform.Object2Json(rentContractAndDetailEntity));
				}
			}
			int payCount = 0;//总待缴账单数
			int liftCount = 0;//生活费用待缴账单数
			ReceiptBillResponse fzbillResponse = null;//最近一期房租账单
			if(Check.NuNObj(rentContractAndDetailEntity)){
				optional = contracts.stream().filter(
						v ->ContractStatusEnum.YQY.getStatus().equals(v.getConStatusCode())
						&& DeliveryStateEnum.WJG.getCode() == v.getDeliveryState()
						&& ContractSourceEnum.APP.getCode() == Integer.valueOf(v.getFsource()))
						.findFirst();//展示最新待交割的合同
				// 查询待物业交割的合同下是否有生活费用
				if (optional.isPresent()) {
					rentContractAndDetailEntity = optional.get();
					LogUtil.info(LOGGER, "[findLatelyContractByUid]查询待物业交割的合同为：{}", JsonEntityTransform.Object2Json(rentContractAndDetailEntity));
				}
				if(!Check.NuNObj(rentContractAndDetailEntity)){
					String response = this.findWaitforPaymentList(rentContractAndDetailEntity.getConRentCode());
					LogUtil.info(LOGGER,"【findLatelyContractByUid】待物业交割的合同查询【{}】合同账单返回：{}",
							rentContractAndDetailEntity.getConRentCode(), response);
					DataTransferObject responseDto = JsonEntityTransform.json2DataTransferObject(response);
					if (responseDto.getCode() == DataTransferObject.SUCCESS) {
						if (!Check.NuNObj(responseDto.getData().get("liftCount"))) {
							liftCount = (int)responseDto.getData().get("liftCount");
							if (!Check.NuNObj(responseDto.getData().get("allCount"))) {
								payCount = (int)responseDto.getData().get("allCount");
							}
						}
					}
				}
			}
			//待审核的个人卡片不显示
			List<RentContractAndDetailEntity> contractYqys = null;
			Map<String,List<ReceiptBillResponse>> map  = new HashMap<String, List<ReceiptBillResponse>>();
			if(Check.NuNObj(rentContractAndDetailEntity)){
				contractYqys = contracts.stream().filter(
						v ->(ContractStatusEnum.YQY.getStatus().equals(v.getConStatusCode())
						|| ContractStatusEnum.YDQ.getStatus().equals(v.getConStatusCode())
						|| ContractStatusEnum.XYZ.getStatus().equals(v.getConStatusCode())
						|| ContractStatusEnum.YXY.getStatus().equals(v.getConStatusCode())
						|| ContractStatusEnum.JYZ.getStatus().equals(v.getConStatusCode()))
						&& ContractAuditStatusEnum.YTG.getStatus().equals(v.getConAuditState())
						&& DeliveryStateEnum.JJG.getCode() == v.getDeliveryState())
						.collect(Collectors.toList());//获取所有已签约的合同(包括后台的合同)
				if (!Check.NuNCollection(contractYqys)) {
					LogUtil.info(LOGGER, "[findLatelyContractByUid]查询所有履约中的的合同个数为：{}", contractYqys.size());
					//删除已到期且已续约的合同
					List<RentContractAndDetailEntity> contractYdqs = contractYqys.stream().filter(v -> ContractStatusEnum.YDQ.getStatus().equals(v.getConStatusCode())
							&& ContractIsRenewEnum.YXY.getCode() == v.getIsRenew()).collect(Collectors.toList());
					contractYqys.removeAll(contractYdqs);

					if(!Check.NuNCollection(contractYqys)){
						//获取合同号
						Set<String> contracatYqyCodes = contractYqys.stream().map(RentContractAndDetailEntity::getConRentCode).collect(Collectors.toSet());
						//根据合同号去财务批量查询应收账单
						ReceiptBillContractsRequest receiptBillContractsRequest = new ReceiptBillContractsRequest();
				        receiptBillContractsRequest.setOutContractList(new ArrayList<String>(contracatYqyCodes));
				        String paramJson = JsonEntityTransform.Object2Json(receiptBillContractsRequest);
				        String result = this.callFinanceServiceProxy.getReceiptBillByContracts(paramJson);
				        LogUtil.info(LOGGER,"[findLatelyContractByUid]查询财务应收账单返回：{}" , result);
				        DataTransferObject listBillObj = JsonEntityTransform.json2DataTransferObject(result);
				        List<ReceiptBillResponse> listBill = null;
				        if (listBillObj.getCode() == DataTransferObject.SUCCESS) {
				        	listBill = listBillObj.parseData("data", new TypeReference<List<ReceiptBillResponse>>() {
							});
				        }else{
				        	LogUtil.error(LOGGER,"[findLatelyContractByUid]查询财务应收账单异常：{}", listBillObj.getMsg());
				        	dto.setErrCode(DataTransferObject.ERROR);
				            dto.setMsg("系统异常！");
				            return dto.toJsonString();
				        }

						if(!Check.NuNCollection(listBill)){
							//获取所有未核销的账单
							listBill = listBill.stream().filter(v -> !(VerificateStatusEnum.DONE.getCode() == v.getVerificateStatus())).collect(Collectors.toList());
					        //把相同合同号的应收账单放到一个map中。
					        for(ReceiptBillResponse receiptBillResponse:listBill){
					        	List<ReceiptBillResponse> listReceiptBill = map.get(receiptBillResponse.getOutContractCode());
					        	if(Check.NuNCollection(listReceiptBill)){
					        		listReceiptBill = new ArrayList<ReceiptBillResponse>();
					        		listReceiptBill.add(receiptBillResponse);
					        		map.put(receiptBillResponse.getOutContractCode(), listReceiptBill);
					        	}else{
					        		listReceiptBill.add(receiptBillResponse);
					        		map.put(receiptBillResponse.getOutContractCode(), listReceiptBill);
					        	}
					        }
						}
					}else{
						LogUtil.info(LOGGER, "[findLatelyContractByUid]查询所有履约中的的合同为空,uid:{}", uid);
						return dto.toJsonString();
					}
					//遍历每个合同，如果有生活费用账单的提前返回，只有房租账单的，根据选择的付款方式，按要求返回
					for (RentContractAndDetailEntity contractYqy : contractYqys) {
						//获取当前合同的待缴账单
						List<ReceiptBillResponse> listBill = map.get(contractYqy.getConRentCode());
						ReceiptBillResponse fzbillResponseYqy = new ReceiptBillResponse();
							if(!Check.NuNCollection(listBill)){
								Integer interimLiftCount = 0;
								Integer interimPayCount = 0;
								Map<String,Integer> countMap = new HashMap<String, Integer>();
								countMap.put("liftCount", interimLiftCount);
								countMap.put("allCount", interimPayCount);
								rentContractLogic.findWaitforPaymentListUtil(listBill, countMap, fzbillResponseYqy, null);
								interimLiftCount = countMap.get("liftCount");
								interimPayCount = countMap.get("allCount");

								if (interimLiftCount > 0) {
									liftCount = interimLiftCount;
									payCount = interimPayCount;
									fzbillResponse = fzbillResponseYqy;
									rentContractAndDetailEntity = contractYqy;
									break;// 有生活费用账单退出循环
								}
								if (!Check.NuNObj(fzbillResponseYqy) && !Check.NuNStr(fzbillResponseYqy.getBillNum())) {
									//只有房租订单，返回租金日期最早的合同。
									if (!Check.NuNObj(fzbillResponse)) {
										if (!Check.NuNStr(fzbillResponse.getPreCollectionDate())) {
											if (DateUtilFormate.formatDateStringToDate(fzbillResponse.getPreCollectionDate()).getTime()
												< DateUtilFormate.formatDateStringToDate(fzbillResponseYqy.getPreCollectionDate()).getTime()) {
												fzbillResponse = fzbillResponseYqy;//离当前日期最近的账单
												liftCount = interimLiftCount;
												payCount = interimPayCount;
												rentContractAndDetailEntity = contractYqy;
											}
										}
									} else {
										fzbillResponse = fzbillResponseYqy;
										liftCount = interimLiftCount;
										payCount = interimPayCount;
										rentContractAndDetailEntity = contractYqy;
									}
							}
						}else{
							//没有账单的情况，距离合同到期前30天展示
							if(Check.NuNObj(rentContractAndDetailEntity)){
								rentContractAndDetailEntity = contractYqy;
							}
						}
					}
				}
			}
			if(Check.NuNObj(rentContractAndDetailEntity)){
				LogUtil.info(LOGGER, "[findLatelyContractByUid]未查询到需要显示的合同返回空,uid为：{}", uid);
	            return dto.toJsonString();
			}
			Integer hasMore = 0;
			if(contracts.size() > 1){//如果多余一个合同，显示查看更多按钮
				hasMore = 1;
			}

    		if(!Check.NuNObj(rentContractAndDetailEntity)){
    			Map<String,Object> resultMap = new HashMap<>();
    			resultMap.put("hasMore", hasMore);//TODO 有效合同多余一个才显示更多按钮。需要更改判断条件
    			resultMap.put("contractId", rentContractAndDetailEntity.getContractId());//合同ID
    			resultMap.put("contractCode", rentContractAndDetailEntity.getConRentCode());//合同码
    			String roomStr = roomService.getRoomByFid(rentContractAndDetailEntity.getRoomId());
    			LogUtil.info(LOGGER, "【findLatelyContractByUid】查询房间信息返回：{}", roomStr);
    			DataTransferObject roomDto = JsonEntityTransform.json2DataTransferObject(roomStr);
    			if(roomDto.getCode() == DataTransferObject.ERROR){
    				LogUtil.info(LOGGER, "【findLatelyContractByUid】查询房间信息报错：{}", roomDto.getMsg());
    				dto.setErrCode(DataTransferObject.ERROR);
    	            dto.setMsg("查询房间信息异常");
    	            return dto.toJsonString();
    			}
    			RoomInfoEntity roomInfo = roomDto.parseData("roomInfo", new TypeReference<RoomInfoEntity>() {
				});
    			if(Check.NuNObj(roomInfo)){
    				dto.setErrCode(DataTransferObject.ERROR);
    	            dto.setMsg("查询房间信息异常");
    	            return dto.toJsonString();
    			}
    			String rentTile = "";
    			if(!Check.NuNStr(rentContractAndDetailEntity.getProName())
    					&& !Check.NuNStr(roomInfo.getFroomnumber())
    					&& !Check.NuNObj(roomInfo.getFfloornumber())
    					&& !Check.NuNStr(roomInfo.getFdirection())){
    				rentTile = rentContractAndDetailEntity.getProName()
    		    			+roomInfo.getFroomnumber()+" "
    		    			+roomInfo.getFfloornumber()+ContractMsgConstant.FLOOR_MSG
    		    			+DirectionEnum.getDirection(Integer.valueOf(roomInfo.getFdirection()))+ContractMsgConstant.DIRECTION_MSG;
    			}
    			if(Check.NuNStr(rentTile)){//一般不会发生的情况，房间信息缺失的时候只显示项目名称
    				rentTile = rentContractAndDetailEntity.getProName();
    			}
    			resultMap.put("rentTitle", rentTile);//项目名称
    			String rentTime = "";
    			if(!Check.NuNObj(rentContractAndDetailEntity.getConStartDate()) && !Check.NuNObj(rentContractAndDetailEntity.getConEndDate())){
    				rentTime = DateUtilFormate.formatDateToString(rentContractAndDetailEntity.getConStartDate(),DateUtilFormate.DATEFORMAT_4)
    						+ContractMsgConstant.FROM_TO_MSG+DateUtilFormate.formatDateToString(rentContractAndDetailEntity.getConEndDate(),DateUtilFormate.DATEFORMAT_4);
    			}
    			resultMap.put("rentTime", rentTime);//租期
    			//提示信息1
    			String expireDate = "";
    			String expireDateInfo = "";
    			String warnInfo = "";
    			String operationCode = "";
    			String operation = "";
    			if(ContractStatusEnum.WQY.getStatus().equals(rentContractAndDetailEntity.getConStatusCode())){
    				if(!Check.NuNObj(rentContractAndDetailEntity.getConSignDate())){
    					operationCode = OperationEnum.QY.getCode() + "";
    	    			operation = OperationEnum.QY.getName();
    					expireDate = DateUtilFormate.formatDateToString(rentContractAndDetailEntity.getConSignDate(),DateUtilFormate.DATEFORMAT_4);
    	    			expireDateInfo = ContractMsgConstant.SIGN_TIME_EFFECTIVE;
    	    			warnInfo = String.format(ContractMsgConstant.WARN_SIGN_CONTRACT, DateUtilFormate.formatDateToString(rentContractAndDetailEntity.getConSignDate(),
    	    					DateUtilFormate.DATEFORMAT_4));//提示信息
    				}
    			}else if(ContractStatusEnum.DZF.getStatus().equals(rentContractAndDetailEntity.getConStatusCode())){
    				if(!Check.NuNObj(rentContractAndDetailEntity.getSubmitContractTime())){
    					if(rentContractAndDetailEntity.getFirstPeriodMoney().compareTo(new BigDecimal(payOverTimeConditionMoney))>=0){//首次支付金额大于5万，72小时
    						Date date = DateUtilFormate.addHours(rentContractAndDetailEntity.getSubmitContractTime(),Integer.valueOf(moreMoneyOvertimeHours));
    						expireDate = DateUtilFormate.formatDateToString(date, DateUtilFormate.DATEFORMAT_6);
    					}else{//小于5万，支付时效48小时。
    						Date date = DateUtilFormate.addHours(rentContractAndDetailEntity.getSubmitContractTime(),Integer.valueOf(lessMoneyOvertimeHours));
    						expireDate = DateUtilFormate.formatDateToString(date, DateUtilFormate.DATEFORMAT_6);
    					}
    					expireDateInfo = ContractMsgConstant.PAY_TIME_MSG;
    					warnInfo = ContractMsgConstant.PAY_TIME_WARN_MSG;//提示信息
    					operationCode = OperationEnum.ZF.getCode() + "";
    	    			operation = OperationEnum.ZF.getName();
    				}
    			}else if(ContractStatusEnum.YQY.getStatus().equals(rentContractAndDetailEntity.getConStatusCode())
    					&& rentContractAndDetailEntity.getDeliveryState() == DeliveryStateEnum.WJG.getCode()){
    				if(!Check.NuNObj(rentContractAndDetailEntity.getFirstPayTime())){
    					Date date = DateUtilFormate.addHours(rentContractAndDetailEntity.getFirstPayTime(),Integer.valueOf(deliveryOvertimeHours));
						expireDate = DateUtilFormate.formatDateToString(date, DateUtilFormate.DATEFORMAT_6);
    				}
    				expireDateInfo = ContractMsgConstant.DELIVERY_TIME_MSG_CARD;
    				if(liftCount > 0){
    					warnInfo =  ContractMsgConstant.DELIVERY_NEED_PAY_MSG_CARD;//提示信息
    				}else{
    					warnInfo = ContractMsgConstant.DELIVERY_DEFAULT_COMPLETE_CARD;//提示信息
    				}
    				operationCode = OperationEnum.WYJG.getCode() + "";
	    			operation = OperationEnum.WYJG.getName();
    			}else{//是否有待支付合同或者距离合同到期30天显示
    				if(payCount > liftCount && liftCount > 0){//既有房租又有生活费用
    					expireDateInfo = String.format(ContractMsgConstant.RENT_DZF_CARE_INFO, payCount);
    					warnInfo = String .format(ContractMsgConstant.PAY_LIFE_COST_FIRST_MSG, fzbillResponse.getPreCollectionDate());
    					operationCode = OperationEnum.ZFLB_SH.getCode() + "";
    	    			operation = OperationEnum.ZFLB_SH.getName();
    				}else if(payCount == liftCount && liftCount > 0){//只有生活费用
    					expireDateInfo = String.format(ContractMsgConstant.RENT_DZF_CARE_INFO, payCount);
    					warnInfo = ContractMsgConstant.PAY_LIFE_COST_CAN_RENEW;
    					operationCode = OperationEnum.ZFLB_SH.getCode() + "";
    	    			operation = OperationEnum.ZFLB_SH.getName();
    				}else if(payCount > 0 && liftCount == 0){//只有房租
    					if(rentContractLogic.isShowBill(rentContractAndDetailEntity.getConCycleCode(),fzbillResponse.getPreCollectionDate())){
    						expireDateInfo = String.format(ContractMsgConstant.RENT_DZF_CARE_INFO, payCount);
        					warnInfo = String.format(ContractMsgConstant.OVER_TIME_WARN_MSG, fzbillResponse.getPreCollectionDate());
        					operationCode = OperationEnum.ZFLB_FZ.getCode() + "";
        	    			operation = OperationEnum.ZFLB_FZ.getName();
    					}else{
    						return dto.toJsonString();
    					}
    				}else if(payCount ==0){//无账单,距离合同到期前30天展示
    					Date endDate = rentContractAndDetailEntity.getConEndDate();
    					if(!Check.NuNObj(endDate)){
    						//获取当前日期和合同截止日期的相差天数
    						int days = DateUtil.getDatebetweenOfDayNum(DateUtilFormate.formatStringToDate(DateUtilFormate.formatDateToString(new Date(), DateUtilFormate.DATEFORMAT_4), DateUtilFormate.DATEFORMAT_4), endDate);
    						//合同结束日期小于30天到合同到期日加一天可以显示续租按钮
    						if(days < ContractValueConstant.RENT_XZ_TIME_THIRTY && days >= ContractValueConstant.RENT_XZ_TIME_ZERO){
    							if(days >= 0){
    								expireDateInfo = String.format(ContractMsgConstant.CONTRACT_OVERTIME_DATE_MSG, days+1);
    							}else{
    								expireDateInfo = ContractMsgConstant.CONTRACT_TODAY_OVERTIME_MSG;
    							}
    							//日租小于30天的不能续租
            	            	if(LeaseCycleEnum.DAY.getCode().equals(rentContractAndDetailEntity.getConType())
            	            			&& rentContractAndDetailEntity.getConRentYear() < ContractValueConstant.RENT_XZ_TIME_THIRTY){
            	            		operationCode = null;
            	            		operation = null;
            	            	}else{
            	            		operationCode = OperationEnum.XY.getCode() + "";
                	    			operation = OperationEnum.XY.getName();
            	            	}
            	            	//查询续约合同
    							RentContractEntity reNewRentContractEntity = rentContractServiceImpl.findRenewContractByPreRentCode(rentContractAndDetailEntity.getConRentCode());
    							if(Check.NuNObj(reNewRentContractEntity)){
    								warnInfo = ContractMsgConstant.ADVANCE_RENEW_DISCOUNT_MSG;
    							}else{
    								if(!(String.valueOf(ContractSourceEnum.APP.getCode()).equals(reNewRentContractEntity.getFsource()))){
    									//客户后台续约提示用户“您已通过管家进行续约，如需帮助请联系管家”
    									warnInfo = ContractMsgConstant.ADVANCE_HAVE_RENEW_INBACK_MSG;
    									operationCode = null;
                	            		operation = null;
    								}else{
    									//这种情况不存在，如果管家发送续约邀请，个人中心会优先展示待签约的合同
    									warnInfo = ContractMsgConstant.ADVANCE_CAN_RENEW_MSG;
    								}
    							}
    						}else{
    							//合同日期不在30天内的不显示
    							return dto.toJsonString();
    						}
    					}
    				}
    			}
    			resultMap.put("expireDate", expireDate);//超时时间
				resultMap.put("expireDateInfo", expireDateInfo);//超时提示信息
				resultMap.put("warnInfo", warnInfo);//提示信息
				resultMap.put("operationCode", operationCode);//操作码
				resultMap.put("operation", operation);//操作名称
				String phone = null;
				//未签约完成前联系管家的手机号为发送签约的管家
				if(ContractStatusEnum.WQY.getStatus().equals(rentContractAndDetailEntity.getConStatusCode())
					|| ContractStatusEnum.DZF.getStatus().equals(rentContractAndDetailEntity.getConStatusCode())
					|| (ContractStatusEnum.YQY.getStatus().equals(rentContractAndDetailEntity.getConStatusCode())
							&& DeliveryStateEnum.WJG.getCode() == rentContractAndDetailEntity.getDeliveryState())
						){
					phone = rentContractLogic.getEmployeePhone(rentContractAndDetailEntity.getProjectId(),rentContractAndDetailEntity.getFhandlezocode());
				}else{
					//签约完成后，联系管家为项目排班的电话
					phone = rentContractLogic.getEmployeePhone(rentContractAndDetailEntity.getProjectId(),null);
				}
				if(Check.NuNStr(phone)){
					LogUtil.error(LOGGER, "[findLatelyContractByUid]查询管家手机号异常，uid:{}", uid);
					return dto.toJsonString();
				}
    			resultMap.put("handleZOPhone", phone);//ZO手机号
    			dto.putValue("resultMap", resultMap);
    			return dto.toJsonString();
    		}else{
    			//没有需要展示的合同
    			return dto.toJsonString();
    		}
        }catch(Exception e){
        	LogUtil.info(LOGGER, "【findLatelyContractByUid】入参:{}", uid);
            LogUtil.error(LOGGER, "【findLatelyContractByUid】出错:{}", e);
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("系统异常！");
            return dto.toJsonString();
        }
    }

    @Override
    public String findContractListByUid(String uid){
        LogUtil.info(LOGGER, "【findContractListByUid】入参:{}", uid);
        DataTransferObject dto = new DataTransferObject();
        if(Check.NuNStr(uid)){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("参数为空！");
            return dto.toJsonString();
        }
        try{
            List<RentContractListVo> rentContractList = new ArrayList<>();
            List<RentContractAndDetailEntity> contracts = rentContractServiceImpl.listContractAndDetailByUid(uid);
            //排序，未签约--待支付，带交割，待审核，履约中，已到期未解约，未续约
            contracts = rentContractLogic.sortContractList(contracts);
            if(Check.NuNCollection(contracts)){
            	LogUtil.info(LOGGER, "【findContractListByUid】合同列表为空返回");
            	return dto.toJsonString();//无合同列表
            }
            LogUtil.info(LOGGER, "【findContractListByUid】查询合同列表返回合同个数:{}", contracts.size());
            //去掉到期且已续约的合同
            List<RentContractEntity> contractYxys = contracts.stream().filter(v -> ContractStatusEnum.YDQ.getStatus().equals(v.getConStatusCode()) && ContractIsRenewEnum.YXY.getCode() == v.getIsRenew()).collect(Collectors.toList());
            contracts.removeAll(contractYxys);
            Map<String,List<ReceiptBillResponse>> map  = new HashMap<String, List<ReceiptBillResponse>>();
            if (!Check.NuNCollection(contracts)) {
					Set<String> contracatYqyCodes = contracts.stream().map(RentContractAndDetailEntity::getConRentCode).collect(Collectors.toSet());
					//根据合同号去财务批量查询应收账单
					ReceiptBillContractsRequest receiptBillContractsRequest = new ReceiptBillContractsRequest();
			        receiptBillContractsRequest.setOutContractList(new ArrayList<String>(contracatYqyCodes));
			        String paramJson = JsonEntityTransform.Object2Json(receiptBillContractsRequest);
			        String result = this.callFinanceServiceProxy.getReceiptBillByContracts(paramJson);
			        LogUtil.info(LOGGER,"[findContractListByUid]查询财务应收账单返回：{}" , result);
			        DataTransferObject listBillObj = JsonEntityTransform.json2DataTransferObject(result);
			        List<ReceiptBillResponse> listBill = null;
			        if (listBillObj.getCode() == DataTransferObject.SUCCESS) {
			        	listBill = listBillObj.parseData("data", new TypeReference<List<ReceiptBillResponse>>() {
						});
			        }else{
			        	LogUtil.error(LOGGER,"[findContractListByUid]查询财务应收账单异常：{}", listBillObj.getMsg());
			        	dto.setErrCode(DataTransferObject.ERROR);
			            dto.setMsg("系统异常！");
			            return dto.toJsonString();
			        }

					if(!Check.NuNCollection(listBill)){
						//获取所有未核销的账单
						listBill = listBill.stream().filter(v -> !(VerificateStatusEnum.DONE.getCode() == v.getVerificateStatus())).collect(Collectors.toList());
				        //把相同合同号的应收账单放到一个map中。
				        for(ReceiptBillResponse receiptBillResponse:listBill){
				        	List<ReceiptBillResponse> listReceiptBill = map.get(receiptBillResponse.getOutContractCode());
				        	if(Check.NuNCollection(listReceiptBill)){
				        		listReceiptBill = new ArrayList<ReceiptBillResponse>();
				        		listReceiptBill.add(receiptBillResponse);
				        		map.put(receiptBillResponse.getOutContractCode(), listReceiptBill);
				        	}else{
				        		listReceiptBill.add(receiptBillResponse);
				        		map.put(receiptBillResponse.getOutContractCode(), listReceiptBill);
				        	}
				        }
					}
	            for(RentContractAndDetailEntity rentContractEntity:contracts){
	                RentContractListVo rentContractListVo = new RentContractListVo();
	                rentContractListVo.setContractId(rentContractEntity.getContractId());
	                rentContractListVo.setContractCode(rentContractEntity.getConRentCode());
	                //特殊状态的
	                if(!Check.NuNObj(rentContractEntity.getShowEnum())
	                		&& ConstatusShowEnum.YDQ.getCode() == rentContractEntity.getShowEnum()){

	                		//已到期的合同，到合同截止日第二天可以显示续约按钮。
		                	Date endDate = rentContractEntity.getConEndDate();
							if(!Check.NuNObj(endDate)){
								int days = DateUtil.getDatebetweenOfDayNum(DateUtilFormate.formatStringToDate(DateUtilFormate.formatDateToString(new Date(), DateUtilFormate.DATEFORMAT_4), DateUtilFormate.DATEFORMAT_4), endDate);
								if(days < ContractValueConstant.RENT_XZ_TIME_THIRTY && days >= ContractValueConstant.RENT_XZ_TIME_ZERO){//合同结束日期小于30天显示去续约
									rentContractListVo.setOperation(OperationEnum.XY.getName());
		                            rentContractListVo.setOperationCode(OperationEnum.XY.getCode());
								}
							}

	                	}
	                rentContractListVo.setConstatus(ConstatusShowEnum.getNameByCode(rentContractEntity.getShowEnum()));
	                rentContractListVo.setConstatusCode(rentContractEntity.getShowEnum());
	                // 查询ZO手机号信息
	                if((ConstatusShowEnum.DQY.getCode() == rentContractEntity.getShowEnum()
	                		|| ConstatusShowEnum.DZF.getCode() == rentContractEntity.getShowEnum()
	                		|| ConstatusShowEnum.DWYJG.getCode() == rentContractEntity.getShowEnum()
	                		|| ConstatusShowEnum.DRZ.getCode() == rentContractEntity.getShowEnum()
	                		|| ConstatusShowEnum.SHZ.getCode() == rentContractEntity.getShowEnum())
	                		&& !Check.NuNStr(rentContractEntity.getFhandlezocode())){//合同相应的管家不为空
	                		String phone = rentContractLogic.getEmployeePhone(rentContractEntity.getProjectId(),rentContractEntity.getFhandlezocode());
	                		if(!Check.NuNStr(phone)){
	    	                    	rentContractListVo.setHandleZOPhone(phone);
	    	                } else {
	    	                	LogUtil.info(LOGGER, "【findContractListByUid】查询ZO信息失败，contractId:{}",rentContractEntity.getContractId());
	    	                    dto.setErrCode(DataTransferObject.ERROR);
	    	                    dto.setMsg("系统异常");
	    	                    return dto.toJsonString();
	    	                }
	    		        }else{
	    		        	String phone = rentContractLogic.getEmployeePhone(rentContractEntity.getProjectId(),null);
	                		if(!Check.NuNStr(phone)){
	    	                    	rentContractListVo.setHandleZOPhone(phone);
	    	                } else {
	    	                	LogUtil.info(LOGGER, "【findContractListByUid】查询ZO信息失败，contractId:{}",rentContractEntity.getContractId());
	    	                    dto.setErrCode(DataTransferObject.ERROR);
	    	                    dto.setMsg("系统异常");
	    	                    return dto.toJsonString();
	    	                }
	    		        }
	                //合同操作赋值
	                if(!Check.NuNObj(rentContractEntity.getOperationEnum())){
	                	rentContractListVo.setOperation(OperationEnum.getNameByCode(rentContractEntity.getOperationEnum()));
	                    rentContractListVo.setOperationCode(rentContractEntity.getOperationEnum());
	                }
	                //是否显示联系管家赋值
	                if(!Check.NuNObj(rentContractEntity.getShowContactZO())){
	                	rentContractListVo.setShowContactZO(rentContractEntity.getShowContactZO());
	                }
	                //特殊状态提示信息
	                if (ContractStatusEnum.WQY.getStatus().equals(rentContractEntity.getConStatusCode())) {//未签约
	                	if (!Check.NuNObj(rentContractEntity.getConSignDate())) {
	                		rentContractListVo.setContractCodeInfo(String.format(ContractMsgConstant.SIGN_CONTRACT_TIME_MSG
	                        ,DateUtilFormate.formatDateToString(rentContractEntity.getConSignDate(),DateUtilFormate.DATEFORMAT_4)));
	                    }
	                }else{
	                	if(!(OperationEnum.ZF.getName().equals(rentContractListVo.getOperation())
	                        	|| OperationEnum.WYJG.getName().equals(rentContractListVo.getOperation()))
	                        	&& ContractAuditStatusEnum.YTG.getStatus().equals(rentContractEntity.getConAuditState())){//审核通过的才显示支付按钮
	                		//其他情况都查询是否有待支付账单
		                	List<ReceiptBillResponse> listBillz = map.get(rentContractEntity.getConRentCode());
			                	if(!Check.NuNCollection(listBillz)){
			                		int liftCount = 0;
				                    int payCount = 0;
				                    Map<String,Integer> countMap = new HashMap<String, Integer>();
									countMap.put("liftCount", liftCount);
									countMap.put("allCount", payCount);
									rentContractLogic.findWaitforPaymentListUtil(listBillz, countMap, null, null);
									liftCount = countMap.get("liftCount");
									payCount = countMap.get("allCount");
				                    //是否有待支付合同
				    				if(payCount > liftCount && liftCount > 0){//既有房租又有生活费用
				    					rentContractListVo.setOperation(OperationEnum.ZFLB_SH.getName());
				                        rentContractListVo.setOperationCode(OperationEnum.ZFLB_SH.getCode());
				    				}else if(payCount == liftCount && liftCount > 0){//只有生活费用
				    					rentContractListVo.setOperation(OperationEnum.ZFLB_SH.getName());
				                        rentContractListVo.setOperationCode(OperationEnum.ZFLB_SH.getCode());
				    				}else if(payCount > 0 && liftCount == 0){//只有房租
				    					rentContractListVo.setOperation(OperationEnum.ZFLB_FZ.getName());
				                        rentContractListVo.setOperationCode(OperationEnum.ZFLB_FZ.getCode());
				    				}else if(payCount ==0){//无费用项
				    					Date endDate = rentContractEntity.getConEndDate();
				    					if(!Check.NuNObj(endDate)){
				    						int days = DateUtil.getDatebetweenOfDayNum(DateUtilFormate.formatStringToDate(DateUtilFormate.formatDateToString(new Date(), DateUtilFormate.DATEFORMAT_4), DateUtilFormate.DATEFORMAT_4), endDate);
				    						if(days < ContractValueConstant.RENT_XZ_TIME_THIRTY && days >= ContractValueConstant.RENT_XZ_TIME_ZERO){//合同结束日期小于30天显示去续约
				    							rentContractListVo.setOperation(OperationEnum.XY.getName());
				                                rentContractListVo.setOperationCode(OperationEnum.XY.getCode());
				    						}
				    					}
				    				}
		                	}
	                	}
	                }
	                //查询续约合同
	                //去续约的合同回传续约合同的ID
	                if(OperationEnum.XY.getName().equals(rentContractListVo.getOperation())){
	                	//日租小于30天的不能续租
	                	if(LeaseCycleEnum.DAY.getCode().equals(rentContractEntity.getConType())
	                			&& rentContractEntity.getConRentYear() < ContractValueConstant.RENT_XZ_TIME_THIRTY){
	                		if(OperationEnum.XY.getCode() == rentContractListVo.getOperationCode()){
	                			rentContractListVo.setOperation(null);
		                		rentContractListVo.setOperationCode(null);
	                		}
	                	}
	                	RentContractEntity renewContractEntity = rentContractServiceImpl.findRenewContractByPreRentCode(rentContractEntity.getConRentCode());
	                	if(!Check.NuNObj(renewContractEntity) && String.valueOf(ContractSourceEnum.APP.getCode()).equals(renewContractEntity.getFsource())){
	                		rentContractListVo.setRenewContractId(renewContractEntity.getContractId());
	                	}
	                }
	                rentContractLogic.buildRentContractListVo(rentContractListVo, rentContractEntity);
	                rentContractList.add(rentContractListVo);
	            }
            }
            LogUtil.info(LOGGER, "【findContractListByUid】出参:{}", JsonEntityTransform.Object2Json(rentContractList));
            dto.putValue("rentContractList", rentContractList);
            return dto.toJsonString();
        }catch(Exception e){
            LogUtil.error(LOGGER, "【findContractListByUid】出错:{}", e);
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("系统异常！");
            return dto.toJsonString();
        }
    }

    @Override
    public String findContractHeaderInfo(String contractId) {
        LogUtil.info(LOGGER, "【findContractHeaderInfo】参数contractId={}", contractId);
        DataTransferObject dto = new DataTransferObject();
        try{
			RentContractEntity rentContractEntity = rentContractServiceImpl.findContractBaseByContractId(contractId);
			if (Check.NuNObj(rentContractEntity)) {
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("合同不存在");
				return dto.toJsonString();
			}
			DataTransferObject projectDto = JsonEntityTransform.json2DataTransferObject(projectService.findProjectById(rentContractEntity.getProjectId()));
			if (projectDto.getCode() == DataTransferObject.ERROR) {
				return projectDto.toJsonString();
			}
			ProjectEntity projectEntity = projectDto.parseData("projectEntity", new TypeReference<ProjectEntity>() {
			});

			// 房间信息
			String roomStr = roomService.getRoomByFid(rentContractEntity.getRoomId());
			DataTransferObject roomObject = JsonEntityTransform.json2DataTransferObject(roomStr);
			if (roomObject.getCode() == DataTransferObject.ERROR) {
				LogUtil.error(LOGGER, "【getRoomByFid】请求结果={}", roomStr);
				dto.setMsg(roomObject.getMsg());
				dto.setErrCode(DataTransferObject.ERROR);
			}
			RoomInfoEntity roomInfoEntity = roomObject.parseData("roomInfo", new TypeReference<RoomInfoEntity>() {
			});

			ProjectInfoVo rentHeadInfo = RentHeadInfoUtil.getRentHeadInfo(rentContractEntity, projectEntity,roomInfoEntity);
			String roomSalesPrice = rentHeadInfo.getRoomSalesPrice();
			roomSalesPrice = roomSalesPrice.substring(roomSalesPrice.indexOf(BillMsgConstant.RMB)+1, roomSalesPrice.indexOf("/"));
			dto.putValue("header", rentHeadInfo);
			dto.putValue("rentContractEntity", rentContractEntity);
			dto.putValue("originalPrice",Double.parseDouble(roomSalesPrice));

			LogUtil.info(LOGGER, "【findContractHeaderInfo】返回结果={}", dto.toJsonString());
			return dto.toJsonString();
		}catch (Exception e){
        	LogUtil.error(LOGGER,"【findContractHeaderInfo】 error={}",e);
        	return DataTransferObjectBuilder.buildErrorJsonStr("服务错误");
		}
    }





    /**
     * <p>查询合同实体信息</p>
     * @author xiangb
     * @created 2017年9月12日
     * @param contractId 合同标识
     * @return
     */
    @Override
    public String findContractBaseByContractId(String contractId) {
        LogUtil.info(LOGGER,"【findContractBaseByContractId】参数={}",contractId);
        DataTransferObject dto = new DataTransferObject();
        if(Check.NuNStr(contractId)){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("参数为空");
            return dto.toJsonString();
        }
        try{
            RentContractEntity rentContractEntity = rentContractServiceImpl.findContractBaseByContractId(contractId);
            LogUtil.info(LOGGER, "【findContractBaseByContractId】查询合同详情返回={}", JsonEntityTransform.Object2Json(rentContractEntity));
            if(Check.NuNObj(rentContractEntity)){
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("未查询到对应的合同信息");
                return dto.toJsonString();
            }
            dto.putValue("rentContractEntity", rentContractEntity);
            return dto.toJsonString();
        }catch(Exception e){
            LogUtil.error(LOGGER,"【findContractBaseByContractId】报错：{}",e);
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("系统异常！");
            return dto.toJsonString();
        }
    }
    /**
     * <p>根据合同ID查询合同详情</p>
     * @author xiangb
     * @created 2017年9月12日
     * @param param
     * @return
     */
    @Override
    public String findContractByContractId(String param) {
        LogUtil.info(LOGGER, "【findContractByContractId】参数={}", param);
        JSONObject paramJson = JSONObject.parseObject(param);
        String contractId = paramJson.getString("contractId");
        String deCode = paramJson.getString("deCode");

        DataTransferObject dto = new DataTransferObject();
        RentContractDetailDto contractDto = new RentContractDetailDto();
        if (Check.NuNStr(contractId) || Check.NuNStr(deCode)) {
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("参数为空");
            return dto.toJsonString();
        }
        try {
            RentContractEntity rentContractEntity = rentContractServiceImpl.findContractBaseByContractId(contractId);
            LogUtil.info(LOGGER, "【findContractByContractId】查询合同详情返回={}",JsonEntityTransform.Object2Json(rentContractEntity));
            if (Check.NuNObj(rentContractEntity)) {
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("未查询到合同信息！");
                return dto.toJsonString();
            }
            // 对出参赋值
            BeanUtils.copyProperties(rentContractEntity, contractDto);

            // 查询物业交割信息
            ContractRoomDto contractRoomDto = new ContractRoomDto();
            contractRoomDto.setContractId(rentContractEntity.getContractId());
            contractRoomDto.setRoomId(rentContractEntity.getRoomId());
            RentDetailEntity rentDetailEntity = rentContractServiceImpl.findRentDetailById(contractRoomDto);
            //合同显示状态查询
            ConstatusShowEnum showEnum = rentContractLogic.getConstatusShowEnum(rentContractEntity.getConStatusCode(),rentContractEntity.getConAuditState(),
                    Check.NuNObj(rentDetailEntity) ? 0 : rentDetailEntity.getDeliveryState());
            if (Check.NuNObj(showEnum)) {
                //合同显示状态为空时前台不展示
            	LogUtil.info(LOGGER, "【findContractByContractId】未查询到显示状态【contractId】:{}",rentDetailEntity.getContractId());
            	return dto.toJsonString();
            }
            //特殊状态的
            if(ConstatusShowEnum.YGB.equals(showEnum)){
            	if(!Check.NuNObj(rentContractEntity.getCloseType())){
            		if(rentContractEntity.getCloseType() == ContractCloseTypeEnum.ZO_CLOSE.getCode()){
                		return dto.toJsonString();//管家关闭的合同前台不显示。
                	}
            	}
            }else if(ConstatusShowEnum.LXZ.equals(showEnum)){
            	//还未到合同开始日期，显示待入住状态
            	if(rentContractEntity.getConStartDate().getTime() > new Date().getTime()){
            		showEnum = ConstatusShowEnum.DRZ;
            	}
            }
            if(!Check.NuNObj(showEnum)){
            	//合同显示状态
                contractDto.setConStatusCode(showEnum.getCode());
                contractDto.setConStatus(showEnum.getName());
            }

            // 查询房间信息,房型信息，项目信息
            String rentRoomInfo = roomService.getRentRoomInfoByRoomId(rentContractEntity.getRoomId());
            LogUtil.info(LOGGER,"【findContractByContractId】查询房间信息，房型信息，项目信息返回:{}",rentRoomInfo);
            DataTransferObject rentRoomObject = JsonEntityTransform.json2DataTransferObject(rentRoomInfo);
            if (rentRoomObject.getCode() == DataTransferObject.ERROR) {
            	dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("查询房间信息失败！");
                return dto.toJsonString();
            }
            RentRoomInfoDto rentRoomDto = rentRoomObject.parseData("rentRoom", new TypeReference<RentRoomInfoDto>() {});
            //合同头信息
            ProjectInfoVo projectInfo = new ProjectInfoVo();
            projectInfo.setProName(rentRoomDto.getProName());
            projectInfo.setProAddress(rentRoomDto.getProAddress());
            if (!Check.NuNStr(rentRoomDto.getProHeadFigureUrl())) {
                projectInfo.setProHeadFigureUrl(pic_url+ rentRoomDto.getProHeadFigureUrl());
            }
            if (!Check.NuNStr(rentContractEntity.getConType())
                    && !Check.NuNObj(rentContractEntity.getRoomSalesPrice())) {
	            // 房间信息
	            String roomStr = roomService.getRoomByFid(rentContractEntity.getRoomId());
	            DataTransferObject roomObject = JsonEntityTransform.json2DataTransferObject(roomStr);
	            if (roomObject.getCode() == DataTransferObject.ERROR) {
		            LogUtil.error(LOGGER, "【findContractByContractId】请求结果={}", roomStr);
		            dto.setMsg(roomObject.getMsg());
		            dto.setErrCode(DataTransferObject.ERROR);
	            }
	            RoomInfoEntity roomInfoEntity = roomObject.parseData("roomInfo", new TypeReference<RoomInfoEntity>() {
	            });
	            LeaseCyclePojo leaseCyclePojo = new LeaseCyclePojo(rentContractEntity.getConType(),rentContractEntity.getProjectId(),roomInfoEntity.getRentType());
	            String roomSalesPrice = null;
	            if(Check.NuNObj(rentContractEntity.getFactualprice())){//实际出房价为空，取计划出房价
	    			roomSalesPrice = LeaseCycleFactory.createLeaseCycle(leaseCyclePojo).calculateActualRoomPrice(rentContractEntity.getConRentYear(), rentContractEntity.getRoomSalesPrice());
	    		}else{
	    			BigDecimal actualPrice = new BigDecimal(rentContractEntity.getFactualprice()).setScale(0, BigDecimal.ROUND_HALF_UP);
	    			if(LeaseCycleEnum.DAY.getCode().equals(rentContractEntity.getConType())){
        				roomSalesPrice = String.format("%s%s/日",
            					BillMsgConstant.RMB, actualPrice);
        			}else{
        				roomSalesPrice = String.format("%s%s/月",
            					BillMsgConstant.RMB, actualPrice);
        			}
	    		}
                projectInfo.setRoomSalesPrice(roomSalesPrice);
            }
            contractDto.setProjectInfo(projectInfo);

            if (!Check.NuNStr(rentRoomDto.getRoomNumber())
                    && !Check.NuNObj(rentRoomDto.getFloorNumber())
                    && !Check.NuNStr(rentRoomDto.getRoomDirection())) {
                String roomInfo = "";
                roomInfo = rentRoomDto.getRoomNumber()
                        + ContractMsgConstant.ROOM_NUM_MSG + ContractMsgConstant.VERTICAL_MSG + rentRoomDto.getFloorNumber()+ ContractMsgConstant.FLOOR_MSG+ContractMsgConstant.VERTICAL_MSG
                        + DirectionEnum.getDirection(Integer.valueOf(rentRoomDto.getRoomDirection()))+ ContractMsgConstant.DIRECTION_MSG;
                if(Check.NuNStr(roomInfo)){
                	roomInfo = rentRoomDto.getRoomNumber()+ ContractMsgConstant.ROOM_NUM_MSG;
                }
                contractDto.setRoomInfo(roomInfo);
            }
            String phone = null;
            if(ConstatusShowEnum.DQY.equals(showEnum)
            		|| ConstatusShowEnum.DZF.equals(showEnum)
            		|| ConstatusShowEnum.DWYJG.equals(showEnum)
            		|| ConstatusShowEnum.DRZ.equals(showEnum)
            		|| ConstatusShowEnum.SHZ.equals(showEnum)){
            	phone = rentContractLogic.getEmployeePhone(rentContractEntity.getProjectId(),rentContractEntity.getFhandlezocode());
		        }else{
		        	phone = rentContractLogic.getEmployeePhone(rentContractEntity.getProjectId(),null);
		        }
            if(!Check.NuNStr(phone)){
    			contractDto.setHandleZOPhone(phone);
            } else {
            	LogUtil.info(LOGGER, "【findContractByContractId】查询ZO信息失败，contractId:{}",rentContractEntity.getContractId());
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("系统异常");
                return dto.toJsonString();
            }
            //是否可关闭合同
           //待支付状态和未签约状态可关闭合同
            if (ContractStatusEnum.DZF.getStatus().equals(rentContractEntity.getConStatusCode())
            		|| ContractStatusEnum.WQY.getStatus().equals(rentContractEntity.getConStatusCode())) {
            	//提交合同页面不显示关闭合同
            	if(ContractValueConstant.DECODE_QY.equals(deCode)){
            		contractDto.setCloseContract(ContractValueConstant.CLOSE_CONTRACT_NO);
            	}else{
            		contractDto.setCloseContract(ContractValueConstant.CLOSE_CONTRACT_YES);
            	}
            } else {
                contractDto.setCloseContract(ContractValueConstant.CLOSE_CONTRACT_NO);
            }
            // 租期赋值
            if (!Check.NuNObj(rentContractEntity.getConStartDate())
                    && !Check.NuNObj(rentContractEntity.getConEndDate())) {
                contractDto.setRentTime(DateUtilFormate.formatDateToString(rentContractEntity.getConStartDate(),DateUtilFormate.DATEFORMAT_4)
                		+ ContractMsgConstant.FROM_TO_MSG + DateUtilFormate.formatDateToString(rentContractEntity.getConEndDate(),DateUtilFormate.DATEFORMAT_4));
            }
            // 未提交合同前显示的合同详情
            if (ContractStatusEnum.WQY.getStatus().equals(
                    rentContractEntity.getConStatusCode())
                    && ContractValueConstant.DECODE_XQ.equals(deCode)) {
                // 未提交合同时的详情页提示信息
                if (!Check.NuNObj(rentContractEntity.getConSignDate())) {
                    contractDto.setHeadCarefulInfo(ContractMsgConstant.SIGN_CONTRACT_TIME_INFO);
                    contractDto.setHeadCarefulDate(DateUtilFormate.formatDateToString(rentContractEntity.getConSignDate(),DateUtilFormate.DATEFORMAT_4));
                }
                contractDto.setOperation(OperationEnum.QY.getName());
                contractDto.setOperationCode(OperationEnum.QY.getCode());
                contractDto.setContactZO(ContractMsgConstant.CONTACT_ZO);
                dto.putValue("contractDto", contractDto);
                LogUtil.info(LOGGER,"【findContractByContractId】出参:{}",JSONObject.toJSONString(contractDto));
                return dto.toJsonString();
            }else if (ContractStatusEnum.YGB.getStatus().equals(
                    rentContractEntity.getConStatusCode())
                    && (Check.NuNObj(rentContractEntity.getIsSyncToFin()) || !(SyncToFinEnum.SUCCESS.getCode() == rentContractEntity.getIsSyncToFin()))){//用户关闭的且未提交合同只显示以上信息
            	 if(Check.NuNStr(rentContractEntity.getConRentCode())){
            		 contractDto.setHeadCarefulInfo(ContractMsgConstant.RENT_HAS_CLOSED_CARE_INFO);
            	 }else{
            		 contractDto.setHeadCarefulInfo(rentContractEntity.getConRentCode());
            	 }
                 dto.putValue("contractDto", contractDto);
                 LogUtil.info(LOGGER,"【findContractByContractId】出参:{}",JSONObject.toJSONString(contractDto));
                 return dto.toJsonString();
            }

            // 签约人信息
            RentCheckinPersonEntity rentCheckinPersonEntity = rentCheckinPersonServiceImpl
                    .findCheckinPersonByContractId(rentContractEntity.getContractId());
            LogUtil.info(LOGGER,"【findContractByContractId】查询签约人信息返回:{}",JSONObject.toJSONString(rentCheckinPersonEntity));
            SignPersonInfoVo personInfo = new SignPersonInfoVo();
            if (!Check.NuNObj(rentCheckinPersonEntity)) {
            	//签约主体信息
                if (!Check.NuNObj(rentCheckinPersonEntity.getCertType())) {
                    personInfo.setCertTypeName(CertTypeEnum.getByCode(
                            rentCheckinPersonEntity.getCertType()).getName());// 证件类型
                    personInfo.setCertType(CertTypeEnum.getByCode(
                            rentCheckinPersonEntity.getCertType()).getCode());// 证件类型
                }
                personInfo.setName(rentCheckinPersonEntity.getName());// 姓名
                //已签约的合同隐藏手机号和证件号，手机号中间4位为*，证件号只保留前两位和后两位
            	personInfo.setPhone(rentCheckinPersonEntity.getPhoneNum());// 手机号
                personInfo.setCertNo(rentCheckinPersonEntity.getCertNum());// 证件号
                personInfo.setSex(GenderEnum.getNameByCode(rentCheckinPersonEntity.getSex()));// 性别
                contractDto.setSignPerson(personInfo);
            }
            // 付款方式
            PaymentCycleEnum paymentCycleEnum = PaymentCycleEnum
                    .getPaymentCycleEnumByCode(rentContractEntity.getConCycleCode());
            if (!Check.NuNObj(paymentCycleEnum)) {
                contractDto.setPayItem(paymentCycleEnum.getShowName());
            }

            //前合同信息
            if(!Check.NuNStr(rentContractEntity.getPreConRentCode())){
            	//签约流程中不显示前合同号
            	if (!(ContractStatusEnum.WQY.getStatus().equals(rentContractEntity.getConStatusCode())
            			|| ContractStatusEnum.DZF.getStatus().equals(rentContractEntity.getConStatusCode()))) {
            		RentContractEntity prerentContractEntity = rentContractServiceImpl.findContractByRentCode(rentContractEntity.getPreConRentCode());
                    LogUtil.info(LOGGER, "【findContractByContractId】查询前合同返回={}", JsonEntityTransform.Object2Json(rentContractEntity));
                    if(Check.NuNObj(prerentContractEntity)){
                        LogUtil.info(LOGGER, "【findContractByContractId】没有前一个合同的异常");
                        dto.setErrCode(DataTransferObject.ERROR);
                        dto.setMsg("系统异常");
                        return dto.toJsonString();
                    }
                    PreContractVo preContractInfo = new PreContractVo();
                    preContractInfo.setPreContractId(prerentContractEntity.getContractId());
                    preContractInfo.setPreContractCode(prerentContractEntity.getConRentCode());
                    preContractInfo.setText(ContractMsgConstant.LOOK_PRE_CONTRACT);
                    contractDto.setPreContractInfo(preContractInfo);
                }
            }
            // 账单总额
            RentBillInfoVo billInfo = new RentBillInfoVo();
            billInfo.setName(ContractMsgConstant.LOOK_DETAIL);
            billInfo.setDesc(ContractMsgConstant.RENT_DETAIL_PROMPT_INFO);
            contractDto.setBillInfo(billInfo);

            if (ContractStatusEnum.WQY.getStatus().equals(rentContractEntity.getConStatusCode())
                    && deCode.equals(ContractValueConstant.DECODE_QY)) {//提交合同页
                // 提交合同页面显示提示信息
                contractDto.setTailCarefulInfo(ContractMsgConstant.RENT_DETAIL_CARE_INFO);
                contractDto.setHeadCarefulInfo(ContractMsgConstant.SIGN_CONTRACT_TIME_INFO);
                if(!Check.NuNObj(rentContractEntity.getConSignDate())){
                	contractDto.setHeadCarefulDate(DateUtilFormate.formatDateToString(rentContractEntity.getConSignDate(),
                    		DateUtilFormate.DATEFORMAT_4) + ContractMsgConstant.SIGN_CONTRACT_TIME);
                }
                //提交合同页显示账单总金额
                DataTransferObject paymentItems = financeCommonLogic.getPaymentItems(rentContractEntity);
                PaymentTermsDto items = paymentItems.parseData("items", new TypeReference<PaymentTermsDto>() {
                });
                LogUtil.info(LOGGER, "【findContractByContractId】查询账单总额返回：{}",
                        JsonEntityTransform.Object2Json(items));
                if(!Check.NuNObj(items)){
                	billInfo.setValue(items.getTotalMoney());//只有提交合同页面返回总金额
                }
                // 提交合同页不显示联系管家
                contractDto.setContractLinkType(ContractValueConstant.LINK_TYPE_HTML);
                contractDto.setOperation(OperationEnum.TJHT.getName());
                contractDto.setOperationCode(OperationEnum.TJHT.getCode());
            } else if (ContractStatusEnum.DZF.getStatus().equals(
            		rentContractEntity.getConStatusCode())) {//待支付
                contractDto.setContractCode(rentContractEntity.getConRentCode());
                contractDto.setOperation(OperationEnum.ZF.getName());
                contractDto.setOperationCode(OperationEnum.ZF.getCode());
                contractDto.setContactZO(ContractMsgConstant.CONTACT_ZO);
                contractDto.setContractLinkType(ContractValueConstant.LINK_TYPE_HTML);
            } else if (ContractStatusEnum.YQY.getStatus().equals(
                    rentContractEntity.getConStatusCode()) //待物业交割
                    && (Check.NuNObj(rentDetailEntity) || rentDetailEntity.getDeliveryState() == DeliveryStateEnum.WJG.getCode())) {
                contractDto.setContractCode(rentContractEntity.getConRentCode());
                contractDto.setContractLinkType(ContractValueConstant.LINK_TYPE_HTML);
                contractDto.setContactZO(ContractMsgConstant.CONTACT_ZO);
                contractDto.setOperation(OperationEnum.WYJG.getName());
                contractDto.setOperationCode(OperationEnum.WYJG.getCode());
            }else if (ContractStatusEnum.YQY.getStatus().equals(
                    rentContractEntity.getConStatusCode()) //审核中
                    && (!Check.NuNObj(rentDetailEntity) && rentDetailEntity.getDeliveryState() == DeliveryStateEnum.JJG.getCode())
                    && !ContractAuditStatusEnum.YTG.getStatus().equals(rentContractEntity.getConAuditState())) {
                contractDto.setContractCode(rentContractEntity.getConRentCode());
                contractDto.setContractLinkType(ContractValueConstant.LINK_TYPE_PDF);// 显示合同pdf
                contractDto.setContractLinkUrl(contractPdfUrl+rentContractEntity.getConRentCode()+HtmltoPDF.pdfSuffix);
                contractDto.setContactZO(ContractMsgConstant.CONTACT_ZO);
            }else if(ContractStatusEnum.YQY.getStatus().equals(
                    rentContractEntity.getConStatusCode()) //履约中
                    && (!Check.NuNObj(rentDetailEntity) && rentDetailEntity.getDeliveryState() == DeliveryStateEnum.JJG.getCode())
                    && ContractAuditStatusEnum.YTG.getStatus().equals(rentContractEntity.getConAuditState())){
            	//已签约的合同隐藏手机号和证件号，手机号中间4位为*，证件号只保留前两位和后两位
            	if(!Check.NuNObj(rentCheckinPersonEntity)){
            		personInfo.setPhone(CardNoUtil.phoneNoHide(rentCheckinPersonEntity.getPhoneNum()));// 手机号
                    personInfo.setCertNo(CardNoUtil.cardNoHide(rentCheckinPersonEntity.getCertNum()));// 证件号
            	}
            	//履约中的有多种情况
                contractDto.setContractCode(rentContractEntity.getConRentCode());
//                //判断是否需要支付
//                this.isNeedPayUtil(rentContractEntity, contractDto,billInfo);

                contractDto.setContractLinkType(ContractValueConstant.LINK_TYPE_PDF);
                contractDto.setContractLinkUrl(contractPdfUrl+rentContractEntity.getConRentCode()+HtmltoPDF.pdfSuffix);
                contractDto.setContactZO(ContractMsgConstant.CONTACT_ZO);
            }else if(ContractStatusEnum.YDQ.getStatus().equals(
                    rentContractEntity.getConStatusCode()) //已到期
                    || ContractStatusEnum.JYZ.getStatus().equals(
                    rentContractEntity.getConStatusCode())//解约中
                    ){
            	//已签约的合同隐藏手机号和证件号，手机号中间4位为*，证件号只保留前两位和后两位
            	if(!Check.NuNObj(rentCheckinPersonEntity)){
            		personInfo.setPhone(CardNoUtil.phoneNoHide(rentCheckinPersonEntity.getPhoneNum()));// 手机号
            		personInfo.setCertNo(CardNoUtil.cardNoHide(rentCheckinPersonEntity.getCertNum()));// 证件号
            	}
            	//已到期未续约的合同，在合同到期日第二天也可以续约
            	if(ContractStatusEnum.YDQ.getStatus().equals(
                        rentContractEntity.getConStatusCode()) &&
                        !(rentContractEntity.getIsRenew() == ContractIsRenewEnum.YXY.getCode())){
            		Date endDate = rentContractEntity.getConEndDate();
					if(!Check.NuNObj(endDate)){
						int days = DateUtil.getDatebetweenOfDayNum(DateUtilFormate.formatStringToDate(DateUtilFormate.formatDateToString(new Date(), DateUtilFormate.DATEFORMAT_4), DateUtilFormate.DATEFORMAT_4), endDate);
						if(days >= ContractValueConstant.RENT_XZ_TIME_ZERO){//合同结束日期小于30天显示去续约
							contractDto.setOperation(OperationEnum.XY.getName());//续约
			                contractDto.setOperationCode(OperationEnum.XY.getCode());
						}
					}

            	}
                contractDto.setContractCode(rentContractEntity.getConRentCode());
                contractDto.setContractLinkType(ContractValueConstant.LINK_TYPE_PDF);
                contractDto.setContractLinkUrl(contractPdfUrl+rentContractEntity.getConRentCode()+HtmltoPDF.pdfSuffix);
            }else if (ContractStatusEnum.YXY.getStatus().equals(
                    rentContractEntity.getConStatusCode())){//已续约
            		contractDto.setContactZO(ContractMsgConstant.CONTACT_ZO);
            	//已签约的合同隐藏手机号和证件号，手机号中间4位为*，证件号只保留前两位和后两位
            	if(!Check.NuNObj(rentCheckinPersonEntity)){
            		personInfo.setPhone(CardNoUtil.phoneNoHide(rentCheckinPersonEntity.getPhoneNum()));// 手机号
            		personInfo.setCertNo(CardNoUtil.cardNoHide(rentCheckinPersonEntity.getCertNum()));// 证件号
            	}
            	 contractDto.setContractCode(rentContractEntity.getConRentCode());
                 contractDto.setContractLinkType(ContractValueConstant.LINK_TYPE_PDF);
                 contractDto.setContractLinkUrl(contractPdfUrl+rentContractEntity.getConRentCode()+HtmltoPDF.pdfSuffix);
            }else if (ContractStatusEnum.YTZ.getStatus().equals(
                    rentContractEntity.getConStatusCode())){//已退租
            	//已签约的合同隐藏手机号和证件号，手机号中间4位为*，证件号只保留前两位和后两位
            	if(!Check.NuNObj(rentCheckinPersonEntity)){
            		personInfo.setPhone(CardNoUtil.phoneNoHide(rentCheckinPersonEntity.getPhoneNum()));// 手机号
            		personInfo.setCertNo(CardNoUtil.cardNoHide(rentCheckinPersonEntity.getCertNum()));// 证件号
            	}
            		contractDto.setContractCode(rentContractEntity.getConRentCode());
            		contractDto.setContractLinkType(ContractValueConstant.LINK_TYPE_PDF);// 显示合同pdf
                    contractDto.setContractLinkUrl(contractPdfUrl+rentContractEntity.getConRentCode()+HtmltoPDF.pdfSuffix);
            }else if (ContractStatusEnum.YGB.getStatus().equals(
                    rentContractEntity.getConStatusCode())){//已关闭
            	 //已签约的合同隐藏手机号和证件号，手机号中间4位为*，证件号只保留前两位和后两位
            	if(!Check.NuNObj(rentCheckinPersonEntity)){
            		personInfo.setPhone(CardNoUtil.phoneNoHide(rentCheckinPersonEntity.getPhoneNum()));// 手机号
            		personInfo.setCertNo(CardNoUtil.cardNoHide(rentCheckinPersonEntity.getCertNum()));// 证件号
            	}
            	 if(!Check.NuNStr(rentContractEntity.getConRentCode())){
            		contractDto.setContractCode(rentContractEntity.getConRentCode());
            		contractDto.setContractLinkType(ContractValueConstant.LINK_TYPE_HTML);// 显示合同html
            	 }else{
                     contractDto.setHeadCarefulInfo(ContractMsgConstant.RENT_HAS_CLOSED_CARE_INFO);
                     //关闭的合同没有合同编号时不能跳转合同h5页面
                     contractDto.setBillInfo(null);//没有合同号的已关闭的合同不显示账单详情项
            	 }
            }else if (ContractStatusEnum.XYZ.getStatus().equals(
                    rentContractEntity.getConStatusCode())){//续约中
            	 //已签约的合同隐藏手机号和证件号，手机号中间4位为*，证件号只保留前两位和后两位
            	if(!Check.NuNObj(rentCheckinPersonEntity)){
            		personInfo.setPhone(CardNoUtil.phoneNoHide(rentCheckinPersonEntity.getPhoneNum()));// 手机号
            		personInfo.setCertNo(CardNoUtil.cardNoHide(rentCheckinPersonEntity.getCertNum()));// 证件号
            	}
            	 contractDto.setOperation(OperationEnum.XY.getName());
                 contractDto.setOperationCode(OperationEnum.XY.getCode());

                 contractDto.setContactZO(ContractMsgConstant.CONTACT_ZO);
            	 contractDto.setContractCode(rentContractEntity.getConRentCode());
                 contractDto.setContractLinkType(ContractValueConstant.LINK_TYPE_PDF);
                 contractDto.setContractLinkUrl(contractPdfUrl+rentContractEntity.getConRentCode()+HtmltoPDF.pdfSuffix);
            }
            //判断是否需要支付
            if(!(OperationEnum.ZF.getName().equals(contractDto.getOperation())
                	|| OperationEnum.WYJG.getName().equals(contractDto.getOperation()))
                	&& ContractAuditStatusEnum.YTG.getStatus().equals(rentContractEntity.getConAuditState())){
            	this.isNeedPayUtil(rentContractEntity, contractDto,billInfo);
            }
            //去续约的合同回传续约合同的ID
            if(OperationEnum.XY.getName().equals(contractDto.getOperation())){
            	//日租小于30天的不能续租
            	if(LeaseCycleEnum.DAY.getCode().equals(rentContractEntity.getConType())
            			&& rentContractEntity.getConRentYear() < ContractValueConstant.RENT_XZ_TIME_THIRTY){
            		contractDto.setOperation(null);
            		contractDto.setOperationCode(null);
            	}
            	RentContractEntity renewContractEntity = rentContractServiceImpl.findRenewContractByPreRentCode(rentContractEntity.getConRentCode());
            	if(!Check.NuNObj(renewContractEntity)){
            		contractDto.setRenewContractId(renewContractEntity.getContractId());
            	}
            }
            //add by lusp  2017/12/29  如果合同签约类型不是app签约，将contractLinkType设置为3（给予提示"您已签署纸质合同，所有信息均以纸质合同为准"）
			if(Integer.valueOf(rentContractEntity.getFsource())!=ContractSourceEnum.APP.getCode()){
				contractDto.setContractLinkType(ContractValueConstant.LINK_TYPE_PROMPT);
			}
            LogUtil.info(LOGGER, "【findContractByContractId】查询合同详情出参={}",
                    JsonEntityTransform.Object2Json(contractDto));
            dto.putValue("contractDto", contractDto);
            return dto.toJsonString();
        } catch (Exception e) {
            LogUtil.error(LOGGER, "【findContractByContractId】出错：{}", e);
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("系统异常！");
            return dto.toJsonString();
        }
    }
    /**
     * 判断合同是否可签约
     * @author xiangb
     * @created 2017年9月13日
     * @param contractId 合同标识
     * @return 获取‘signStatus’值， 0：可签约，1：房间已出租，2：合同已失效
     */
    @Override
    public String validContract(String contractId) {
        LogUtil.info(LOGGER,"【validContract】入参={}",contractId);
        DataTransferObject dto = new DataTransferObject();
        if(Check.NuNStr(contractId)){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("参数为空！");
            return dto.toJsonString();
        }
        try{
            RentContractEntity rentContractEntity = rentContractServiceImpl.findContractBaseByContractId(contractId);
            LogUtil.info(LOGGER, "【validContract】查询合同详情返回={}", JsonEntityTransform.Object2Json(rentContractEntity));
            if(Check.NuNObj(rentContractEntity)){
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("合同不存在");
                return dto.toJsonString();
            }

            //判断合同为未签约状态
            if(!ContractStatusEnum.WQY.getStatus().equals(rentContractEntity.getConStatusCode())){
            	if(ContractStatusEnum.DZF.getStatus().equals(rentContractEntity.getConStatusCode())){
            		LogUtil.info(LOGGER, "【validContract】合同为待支付状态：contractId:{}", contractId);
                    dto.setErrCode(DataTransferObject.ERROR);
                    dto.setMsg(ContractMsgConstant.CONTRACT_STATUS_ERROR_MSG);
                    return dto.toJsonString();
            	}
            	LogUtil.info(LOGGER, "【validContract】合同不是未签约状态：contractId:{}", contractId);
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg(ContractMsgConstant.CONTRACT_STATUS_CHANGE);
                return dto.toJsonString();
            }
            String nowStr = "";
            String conSignDateStr = "";
            Date conSignDate = rentContractEntity.getConSignDate();//预计签约日期
            Date now = new Date();//当前时间
            //是否是续约合同，续约合同查询待支付账单
            if(!Check.NuNStr(rentContractEntity.getPreConRentCode()) && ContractSignTypeEnum.RENEW.getValue().equals(rentContractEntity.getFsigntype())){
            	//判断签约时间是否超时
                //consignDate不是当天的时候要大于当前时间（续约） 
                if(!Check.NuNObj(conSignDate)){
                	nowStr = DateUtilFormate.formatDateToString(now, DateUtilFormate.DATEFORMAT_4);
                	conSignDateStr = DateUtilFormate.formatDateToString(conSignDate, DateUtilFormate.DATEFORMAT_4);
                	if(!(nowStr.equals(conSignDateStr))){//等于当天的不用判断，不等于当天的要大于当天时间
                		if(conSignDate.getTime() < now.getTime()){
                			LogUtil.info(LOGGER, "【validContract】签约已超时,contractId:{}", contractId);
                			dto.setErrCode(DataTransferObject.ERROR);
                			if(Check.NuNStr(rentContractEntity.getPreConRentCode())){
                				dto.setMsg(ContractMsgConstant.CONTRACT_TIMEOUT);//新签
                			}else{
                				dto.setMsg(ContractMsgConstant.RENEW_CONTRACT_TIMEOUT);//续约
                			}
                            return dto.toJsonString();
                		}
                	}
                }else{
                	LogUtil.info(LOGGER, "【validContract】未查询到conSignDate的值,contractId:{}", contractId);
                	dto.setErrCode(DataTransferObject.ERROR);
                    dto.setMsg("系统异常");
                    return dto.toJsonString();
                }
                //判断前合同是否有待支付账单，没有待支付账单则可签约
                RentContractEntity prerentContractEntity = rentContractServiceImpl.findContractByRentCode(rentContractEntity.getPreConRentCode());
                LogUtil.info(LOGGER, "【validContract】查询前合同返回={}", JsonEntityTransform.Object2Json(prerentContractEntity));
                if(Check.NuNObj(prerentContractEntity)){
                    LogUtil.info(LOGGER, "【validContract】没有前一个合同的异常，contractId:{}",contractId);
                    dto.setErrCode(DataTransferObject.ERROR);
                    dto.setMsg("系统异常");
                    return dto.toJsonString();
                }
                int liftCount = 0;
                int payCount = 0;
                String response = this.findWaitforPaymentList(prerentContractEntity.getConRentCode());
				LogUtil.info(LOGGER,"【validContract】：查询前合同账单返回：{}", response);
				DataTransferObject responseDto = JsonEntityTransform.json2DataTransferObject(response);
				if (responseDto.getCode() == DataTransferObject.SUCCESS) {
					if (!Check.NuNObj(responseDto.getData().get("liftCount"))) {
						liftCount = (int)responseDto.getData().get("liftCount");
					}
					if (!Check.NuNObj(responseDto.getData().get("allCount"))) {
						payCount = (int)responseDto.getData().get("allCount");
					}
				}else{
					LogUtil.error(LOGGER,"【validContract】：查询前合同账单异常：{}", responseDto.getMsg());
					dto.setErrCode(DataTransferObject.ERROR);
                    dto.setMsg("系统异常！");
                    return dto.toJsonString();
				}
                if(payCount > 0){
                	 dto.setErrCode(DataTransferObject.ERROR);
                     dto.setMsg(ContractMsgConstant.RENEW_PAY_REMIND);
                     return dto.toJsonString();
                }
                return dto.toJsonString();//续约的不用判断房间状态
            }else{//新签合同要在签约日期才能签约
            	 if(!Check.NuNObj(conSignDate)){
                 	nowStr = DateUtilFormate.formatDateToString(now, DateUtilFormate.DATEFORMAT_4);
                 	conSignDateStr = DateUtilFormate.formatDateToString(conSignDate, DateUtilFormate.DATEFORMAT_4);
                 	if(!(nowStr.equals(conSignDateStr))){//不等于当天的不可签约（新签时）
                 		LogUtil.info(LOGGER, "【validContract】查询到conSignDate的值不是当天时间,contractId:{}", contractId);
                     	dto.setErrCode(DataTransferObject.ERROR);
                        dto.setMsg(ContractMsgConstant.CONTRACT_TIME_BEFORE);
                        return dto.toJsonString();
                 	}
                 }else{
                 	LogUtil.error(LOGGER, "【validContract】未查询到conSignDate的值,contractId:{}", contractId);
                 	dto.setErrCode(DataTransferObject.ERROR);
                    dto.setMsg("系统异常");
                    return dto.toJsonString();
                 }
            	 //判断房间状态
            	 String room = roomService.getRoomByFid(rentContractEntity.getRoomId());
                 LogUtil.info(LOGGER, "【查询房间详情】返回={}", room);
                 DataTransferObject roomDto = JsonEntityTransform.json2DataTransferObject(room);
                 if(roomDto.getCode() == DataTransferObject.ERROR){
                     dto.setErrCode(DataTransferObject.ERROR);
                     dto.setMsg("未查询到房间信息");
                     return dto.toJsonString();
                 }
                 RoomInfoEntity roomInfoEntity = roomDto.parseData("roomInfo", new TypeReference<RoomInfoEntity>() {
                 });
                 //判断房间状态为可预订或者待租中
                 if (RoomStatusEnum.validRoomStatus(roomInfoEntity.getFcurrentstate())) {
                     return dto.toJsonString();
                 } else {
                     dto.setErrCode(DataTransferObject.ERROR);
                     dto.setMsg(ContractMsgConstant.CONTRACT_ROOM_STATUS_CHANGE);
                     return dto.toJsonString();
                 }
            }
        }catch(Exception e){
        	LogUtil.error(LOGGER,"判断合同是否可签约error={}",e);
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("系统异常！");
            return dto.toJsonString();
        }
    }

    @Override
    public String updatePayCodeByContractId(String contractId, String payCode) {
        LogUtil.info(LOGGER,"【updatePayCodeByContractId】参数 contractId={},payCode={}",contractId,payCode);
        DataTransferObject dto = new DataTransferObject();
        if (Check.NuNStr(contractId)){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("合同号为空");
            return dto.toJsonString();
        }
        if (Check.NuNObj(payCode)){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("支付方式为空");
            return dto.toJsonString();
        }
        int count = rentContractServiceImpl.updatePayCodeByContractId(contractId, payCode);
        dto.putValue("count",count);
        return dto.toJsonString();
    }


    @Override
    public String saveContractBySignInvite(String signInviteInfo) {
        LogUtil.info(LOGGER,"【saveContractBySignInvite:{}", signInviteInfo);
        DataTransferObject dto = new DataTransferObject();

        try {

			if (Check.NuNStr(signInviteInfo)){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("签约邀请内容为空");
				return dto.toJsonString();
			}

			SignInviteDto signInviteDto = JsonEntityTransform.json2Object(signInviteInfo, SignInviteDto.class);
			// Validate
			signInviteDto.setSource(ContractSourceEnum.APP.getCode());

			DataTransferObject projectDto = JsonEntityTransform.json2DataTransferObject(projectService.findProjectById(signInviteDto.getProjectId()));
			if (projectDto.getCode() == DataTransferObject.ERROR) {
				return DataTransferObjectBuilder.buildErrorJsonStr("系统错误,未查询到项目信息!");
			}

			ProjectEntity projectEntity = projectDto.parseData("projectEntity", new TypeReference<ProjectEntity>() {
			});


			String cityResult = cityService.findById(projectEntity.getCityid());
			DataTransferObject cityDto = JsonEntityTransform.json2DataTransferObject(cityResult);
			CityEntity cityEntity = null;
			if (DataTransferObject.SUCCESS == projectDto.getCode()) {
				cityEntity = cityDto.parseData("data", new TypeReference<CityEntity>() {
				});
			} else {
				return DataTransferObjectBuilder.buildErrorJsonStr("系统错误,查询城市为空");
			}


			//查询房间信息并以roomId为key，转为map形式
			List<RoomInfoEntity>  roomList = queryRoomList(signInviteDto);

			Map<String,RoomInfoEntity> roomMap = transferToMap(roomList);


			// key:roomId,value:contractId;房间号合同号对应关系，后面在保存邀约记录时需要
			Map<String,String> map = new HashMap<>();
			List<RentContractEntity> rentContractList = transferToRentContractEntityList(signInviteDto, roomMap, projectEntity, cityEntity);

			rentContractList.forEach( rentContractEntity -> {

				map.put(rentContractEntity.getRoomId(), rentContractEntity.getContractId());
			});

			rentContractServiceImpl.saveContractFromSignInvite(rentContractList,roomMap, signInviteDto);

			dto.putValue("data",map);
			return dto.toJsonString();

        } catch (Exception e) {
			LogUtil.error(LOGGER,"saveContractBySignInvite error={}", e);
			return DataTransferObjectBuilder.buildErrorJsonStr("saveContractBySignInvite 异常");
        }

    }

    @Override
    public String saveContractByFirst(String paramJson) {
		LogUtil.info(LOGGER,"【saveContractByFirst】:{}", paramJson);


		try {
			if (Check.NuNStrStrict(paramJson)) {
				return DataTransferObjectBuilder.buildErrorJsonStr("参数为空!");
			}


			ContractFirstDto  contractFirstDto = JsonEntityTransform.json2Object(paramJson, ContractFirstDto.class);
			//是否为续约
			boolean isRenew = ContractSignTypeEnum.RENEW.getValue().equals(contractFirstDto.getSignType());

			if (isRenew && Check.NuNStrStrict(contractFirstDto.getPreContractIds())) {
				return DataTransferObjectBuilder.buildErrorJsonStr("续约前合号不可为空!");
			}

			List<RentContractRoomVo> rentContractRoomVoList = new ArrayList<>();

			String roomIds =  contractFirstDto.getRoomIds();

			// key :合同号; value: 前合同结束日期
			Map<String, Date> preContractEndDate = new HashMap<>();
			//续约查询出前合同信息 并放到RentRoomVo列表中
			//有一种我要看不懂下面代码的感觉，其实很简单就是拼装RentRoomVo列表，要求支持新签和续约
			if (!Check.NuNStrStrict(contractFirstDto.getPreContractIds())) {
				if (contractFirstDto.getPreContractIds().startsWith(",")) {
					contractFirstDto.setPreContractIds(contractFirstDto.getPreContractIds().replaceFirst(",", ""));
				}
				List<String> preContractList = Arrays.asList(contractFirstDto.getPreContractIds().split(","));
				LogUtil.info(LOGGER,"【saveContractByFirst】preContractList:{},{}", preContractList.size(),preContractList);
				List<RentContractEntity> rentContractEntityList = rentContractServiceImpl.findContractListByContractIds(preContractList);
				if (rentContractEntityList == null || rentContractEntityList.size() == 0) {
					return DataTransferObjectBuilder.buildErrorJsonStr("未查询到前合同");
				}
				LogUtil.info(LOGGER,"【saveContractByFirst】rentContractEntityList:{}", rentContractEntityList.size());
				StringJoiner sjRoom = new StringJoiner(",");
				rentContractEntityList.forEach( rentContractEntity -> {
					preContractEndDate.put(rentContractEntity.getConRentCode(), rentContractEntity.getConEndDate());
					LogUtil.info(LOGGER,"【saveContractByFirst】forEach:{}", rentContractEntity.getRoomId());
					sjRoom.add(rentContractEntity.getRoomId());
					RentContractRoomVo rentContractRoomVo = new RentContractRoomVo();
					rentContractRoomVo.setContractId(rentContractEntity.getContractId());
					rentContractRoomVo.setConRentCode(rentContractEntity.getConRentCode());
					rentContractRoomVo.setProjectId(rentContractEntity.getProjectId());
					rentContractRoomVo.setRoomId(rentContractEntity.getRoomId());

					rentContractRoomVo.setSurParentRentId(rentContractEntity.getSurParentRentId());
					rentContractRoomVoList.add(rentContractRoomVo);
				});
				roomIds = sjRoom.toString();
				rentContractEntityList = null;
			}

			String roomListResult = roomService.getRoomListByRoomIds(roomIds);
			LogUtil.info(LOGGER,"【saveContractByFirst】getRoomListByRoomIds:{},{}", roomIds, roomListResult);
			DataTransferObject roomListDto =  JsonEntityTransform.json2DataTransferObject(roomListResult);
			List<RoomInfoEntity> roomInfoEntityList = null;
			if (DataTransferObject.SUCCESS == roomListDto.getCode()) {

				roomInfoEntityList = SOAResParseUtil.getListValueFromDataByKey(roomListResult, "roomInfoList", RoomInfoEntity.class);

				if (roomInfoEntityList == null || roomInfoEntityList.size() == 0) {
					return DataTransferObjectBuilder.buildErrorJsonStr("房间查询结果为空!");
				}
			}



			//将roomInfoEntityList 与 rentContractRoomVoList 进行拼装
			if (rentContractRoomVoList.size() == 0) {
				roomInfoEntityList.forEach(roomInfoEntity -> {
					RentContractRoomVo rentContractRoomVo = new RentContractRoomVo();
					rentContractRoomVo.setProjectId(roomInfoEntity.getProjectid());
					rentContractRoomVo.setRoomId(roomInfoEntity.getFid());
					rentContractRoomVo.setRoomArea(roomInfoEntity.getFroomarea());
					rentContractRoomVo.setLongPrice(roomInfoEntity.getFlongprice());
					rentContractRoomVo.setType(roomInfoEntity.getFtype());
					rentContractRoomVo.setRoomNumber(roomInfoEntity.getFroomnumber());
					rentContractRoomVoList.add(rentContractRoomVo);
				});
			}else {
				Map<String,RoomInfoEntity> roomInfoEntityMap = new HashMap<>();
				for (RoomInfoEntity roomInfoEntity : roomInfoEntityList) {
					roomInfoEntityMap.put(roomInfoEntity.getFid(), roomInfoEntity);
				}
				for (RentContractRoomVo rentContractRoomVo : rentContractRoomVoList) {
					LogUtil.info(LOGGER,"【saveContractByFirst】rentContractRoomVo.getRoomId:{},",rentContractRoomVo.getRoomId());
					RoomInfoEntity roomInfoEntity = roomInfoEntityMap.get(rentContractRoomVo.getRoomId());
					LogUtil.info(LOGGER,"【saveContractByFirst】roomInfoEntity:{},",roomInfoEntity);
					rentContractRoomVo.setRoomArea(roomInfoEntity.getFroomarea());
					rentContractRoomVo.setLongPrice(roomInfoEntity.getFlongprice());
					rentContractRoomVo.setType(roomInfoEntity.getFtype());
					rentContractRoomVo.setRoomNumber(roomInfoEntity.getFroomnumber());
				}
				roomInfoEntityMap = null;
			}
			roomInfoEntityList = null;




			String projectResult = projectService.findProjectById(rentContractRoomVoList.get(0).getProjectId());

			DataTransferObject projectDto = JsonEntityTransform.json2DataTransferObject(projectResult);

			ProjectEntity projectEntity = null;
			if (DataTransferObject.SUCCESS == projectDto.getCode()) {
				projectEntity = projectDto.parseData("projectEntity", new TypeReference<ProjectEntity>() {

				});
			} else {
				return DataTransferObjectBuilder.buildErrorJsonStr("查询项目为空");
			}

			String cityResult = cityService.findById(projectEntity.getCityid());
			DataTransferObject cityDto = JsonEntityTransform.json2DataTransferObject(cityResult);
			CityEntity cityEntity = null;
			if (DataTransferObject.SUCCESS == projectDto.getCode()) {
				cityEntity = cityDto.parseData("data", new TypeReference<CityEntity>() {
				});
			} else {
				return DataTransferObjectBuilder.buildErrorJsonStr("查询城市为空");
			}
			ContractFirstVo contractFirstVo  = rentContractServiceImpl.saveContractFist(projectEntity, rentContractRoomVoList, contractFirstDto, cityEntity, preContractEndDate);
			DataTransferObject dto = new DataTransferObject();
			dto.putValue("data", contractFirstVo.getSurParentRentId());
			dto.putValue("contractIds", contractFirstVo.getContractIdList());
			return dto.toJsonString();
		} catch (Exception e) {
			LogUtil.error(LOGGER,"保存合同信息有误error={}", e);
			return DataTransferObjectBuilder.buildErrorJsonStr("系统异常,请重新操作或查看再操作");
		}

	}


    /**
     * 查询房间列表
     * @author cuiyuhui
     * @created
     * @param
     * @return
     */
    private List<RoomInfoEntity> queryRoomList(SignInviteDto signInviteDto) throws SOAParseException {
        String roomInfoStr = roomService.getRoomListByRoomIds(signInviteDto.getRoomIds());
        List<RoomInfoEntity> roomInfoEntityLis = SOAResParseUtil.getListValueFromDataByKey(roomInfoStr, "roomInfoList", RoomInfoEntity.class);
//		DataTransferObject dataTransferObject = JsonEntityTransform.json2DataTransferObject(roomInfoStr);
//		List<RoomInfoEntity> roomInfoEntityLis = (List<RoomInfoEntity>)dataTransferObject.getData().get("roomInfoList");
        return roomInfoEntityLis;
    }

    /**
     * 将roomList转为Map，方便以后获取是床位还是房间
     * @author cuiyuhui
     * @created
     * @param
     * @return
     */
    private Map<String,RoomInfoEntity> transferToMap(List<RoomInfoEntity> roomList) {
        Map<String, RoomInfoEntity> roomMap = new HashMap<>();
        roomList.forEach( room -> {
            roomMap.put(room.getFid(), room);
        });
        return roomMap;
    }

    /**
     * 将签约邀请转为合同list
     * @author cuiyuhui
     * @created
     * @param
     * @return
     */
    private List<RentContractEntity> transferToRentContractEntityList(SignInviteDto signInviteDto, Map<String,RoomInfoEntity> roomMap, ProjectEntity projectEntity, CityEntity cityEntity) {
        Date currentDate = new Date();
        String roomIds = signInviteDto.getRoomIds();
        List<String> roomIdList = Arrays.asList(roomIds.split(","));
        List<RentContractEntity> rentContractList = new ArrayList<>();
        roomIdList.forEach( roomId -> {

            RoomInfoEntity roomInfoEntity = roomMap.get(roomId);
            RentContractEntity rentContract = new RentContractEntity();
            rentContract.setContractId(UUIDGenerator.hexUUID());
            rentContract.setProjectId(signInviteDto.getProjectId());
            rentContract.setPreConRentCode(signInviteDto.getPreConRentCode());
            rentContract.setRoomSalesPrice(roomInfoEntity.getFlongprice());
            rentContract.setConType(signInviteDto.getConType());
            rentContract.setConSignDate(signInviteDto.getConStartDate());
            rentContract.setConStartDate(signInviteDto.getConStartDate());
//            Date conEndDate = null;
//            if (LeaseCycleEnum.YEAR.getCode().equals(signInviteDto.getConType())) { //年租
//                conEndDate = DateUtilFormate.addYear(currentDate, signInviteDto.getRentPeriod());
//            } else if (LeaseCycleEnum.MONTH.getCode().equals(signInviteDto.getConType())) { //月租
//                conEndDate = DateUtilFormate.addMonth(currentDate, signInviteDto.getRentPeriod());
//            } else if (LeaseCycleEnum.DAY.getCode().equals(signInviteDto.getConType())) { //日租
//                conEndDate = DateUtilFormate.addDate(currentDate, signInviteDto.getRentPeriod());
//            } else {
//                //应该抛出异常
//                conEndDate = null;
//            }
            rentContract.setConEndDate(signInviteDto.getConEndDate());
            rentContract.setConStatusCode(ContractStatusEnum.WQY.getStatus());
            rentContract.setConAuditState(ContractAuditStatusEnum.DSH.getStatus());
            //年租转为存储月份
            if (LeaseCycleEnum.YEAR.getCode().equals(signInviteDto.getConType())) {
                rentContract.setConRentYear(signInviteDto.getRentPeriod() * 12 );
            } else {
                rentContract.setConRentYear(signInviteDto.getRentPeriod());
            }

            rentContract.setProName(signInviteDto.getProjectName());
            rentContract.setRoomId(roomId);
            rentContract.setHouseRoomNo(roomInfoEntity.getFroomnumber());
            rentContract.setCustomerType(CustomerTypeEnum.PERSON.getCode());
            rentContract.setCustomerMobile(signInviteDto.getPhone());
            rentContract.setFcreatetime(currentDate);
            rentContract.setCreaterid(signInviteDto.getEmployeeId());
            rentContract.setUpdaterid(signInviteDto.getEmployeeId());
            rentContract.setFisdel(ZraConst.NOT_DEL_INT);
            rentContract.setFvalid(ZraConst.VALID_INT);
            rentContract.setCityid(roomInfoEntity.getCityid());
            rentContract.setCustomerUid(signInviteDto.getCustomerUid());
            rentContract.setFhandlezo(signInviteDto.getHandZo());
            rentContract.setFhandlezocode(signInviteDto.getHandZoCode());

            //新签
            if (Check.NuNStr(signInviteDto.getPreContractId())) {
                rentContract.setFsigntype(ContractSignTypeEnum.NEW.getValue());
                //续约
            } else {
                rentContract.setFsigntype(ContractSignTypeEnum.RENEW.getValue());
                rentContract.setPreConRentCode(signInviteDto.getPreConRentCode());
            }



            rentContract.setFcontractcategory(String.valueOf(ContractCategoryEnum.CF.getCode()));
            rentContract.setFsource(String.valueOf(signInviteDto.getSource()));
            rentContract.setConTplVersion("1");//TODO CUIYH9

//			20180216
			String contractCode = rentContractServiceImpl.contractCode(rentContract.getConType(),rentContract.getCustomerType(),projectEntity.getFcode(),  cityEntity);
			rentContract.setConRentCode(contractCode);
            rentContractList.add(rentContract);
        });

        return rentContractList;
    }

    /**
     * <p>查询签约主体信息</p>
     * @author xiangb
     * @created 2017年9月21日
     * @param
     * @return
     */
    public String findCheckinPerson(String contractId) {
        LogUtil.info(LOGGER, "【findCheckinPerson】入参：{}", contractId);
        DataTransferObject dto = new DataTransferObject();
        if (Check.NuNStr(contractId)) {
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("参数为空！");
            return dto.toJsonString();
        }
        //主要逻辑：根据合同ID查询合同信息和uid，用uid去友家查询用户信息，
        //根据合同ID查询签约人表是否有签约人信息,没有则保存签约人信息，有的话不做更新；
        //保存签约人姓名到合同表
        try{
            SignSubjectVo signSubject = new SignSubjectVo();
            SignPersonInfoVo personInfo = new SignPersonInfoVo();
            ProjectInfoVo projectInfo = null;
            signSubject.setSignPerson(personInfo);
            // 根据合同ID查询合同基本信息
            RentContractEntity rentContractEntity = rentContractServiceImpl.findContractBaseByContractId(contractId);
            LOGGER.info("【findCheckinPerson】：查询合同信息返回{}", JSONObject.toJSON(rentContractEntity));
            if(Check.NuNObj(rentContractEntity)){
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("未查询到合同信息");
                return dto.toJsonString();
            }
            String projectObject = projectService.findProjectById(rentContractEntity.getProjectId());
            LogUtil.info(LOGGER, "【findCheckinPerson】查询项目信息返回:{}", projectObject);
            ProjectEntity projectEntity = null;
            DataTransferObject projectDto = JsonEntityTransform.json2DataTransferObject(projectObject);
            if(projectDto.getCode() == DataTransferObject.SUCCESS){
                projectEntity = projectDto.parseData("projectEntity", new TypeReference<ProjectEntity>() {
                });
            }else{
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("未查询到项目信息");
                return dto.toJsonString();
            }
			// 房间信息
	        String roomStr = roomService.getRoomByFid(rentContractEntity.getRoomId());
	        DataTransferObject roomObject = JsonEntityTransform.json2DataTransferObject(roomStr);
	        if (roomObject.getCode() == DataTransferObject.ERROR) {
		        LogUtil.error(LOGGER, "【getRoomByFid】请求结果={}", roomStr);
		        dto.setMsg(roomObject.getMsg());
		        dto.setErrCode(DataTransferObject.ERROR);
		        return dto.toJsonString();
	        }
	        RoomInfoEntity roomInfoEntity = roomObject.parseData("roomInfo", new TypeReference<RoomInfoEntity>() {
	        });
	        projectInfo = RentHeadInfoUtil.getRentHeadInfo(rentContractEntity, projectEntity, roomInfoEntity);

            signSubject.setProjectInfo(projectInfo);

            signSubject.setContractId(rentContractEntity.getContractId());
            signSubject.setContractCode(rentContractEntity.getConRentCode());
            String phone = rentContractLogic.getEmployeePhone(rentContractEntity.getProjectId(),rentContractEntity.getFhandlezocode());
    		if(!Check.NuNStr(phone)){
    			signSubject.setHandleZOPhone(phone);
            } else {
            	LogUtil.error(LOGGER, "【findCheckinPerson】查询ZO信息失败，contractId:{}",rentContractEntity.getContractId());
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("系统异常");
                return dto.toJsonString();
            }

            RentCheckinPersonEntity rentCheckinPersonEntity = null;
            //从合同信息中获取uid
            String uid = rentContractEntity.getCustomerUid();
            //通过uid去友家查询客户信息
            PersonalInfoDto personal = CustomerLibraryUtil.findAuthInfoFromCustomer(uid);
            LogUtil.info(LOGGER, "【findCheckinPerson】友家查询用户信息返回：{}", JSONObject.toJSON(personal));
            if(!Check.NuNObj(personal)){
                Cert cert = personal.getCert();
                Profile profile = personal.getProfile();
                //TODO 未对profile做空验证
                //未对空进行验证，考虑调用接口的时候客户已经做过身份认证
                personInfo.setName(cert.getReal_name());
                personInfo.setCertNo(cert.getCert_num());
                if(!Check.NuNObj(cert.getCert_type())){
                    personInfo.setCertTypeName(CertTypeEnum.getByCode(Integer.valueOf(cert.getCert_type())).getName());
                    personInfo.setCertType(CertTypeEnum.getByCode(Integer.valueOf(cert.getCert_type())).getCode());
                }
                if (!Check.NuNObj(profile.getGender())) {
                    personInfo.setSex(GenderEnum.getNameByCode(profile.getGender()));
                }
                personInfo.setPhone(profile.getPhone());
                //更新合同中签约人姓名和签约人手机号
                rentContractServiceImpl.updateContractCustomerName(rentContractEntity.getContractId(),cert.getReal_name(),profile.getPhone());
                //保存入住人信息到本地 这时候只保存实名认证信息 身份证号，手机号，姓名，性别，
                //需要先看入住人表中是否已有这个人的数据，一个合同绑定一个入住人，通过合同ID查询是否有数据
                rentCheckinPersonEntity = rentCheckinPersonServiceImpl.findCheckinPersonByContractId(rentContractEntity.getContractId());
                LogUtil.info(LOGGER, "【findCheckinPerson】查询入住人信息返回:{}", JSONObject.toJSONString(rentCheckinPersonEntity));
                boolean isNull = false;//保存或更新
                if(Check.NuNObj(rentCheckinPersonEntity)){
                    rentCheckinPersonEntity = new RentCheckinPersonEntity();
                    isNull = true;
                }
                rentCheckinPersonEntity.setSex(profile.getGender());
                rentCheckinPersonEntity.setUid(rentContractEntity.getCustomerUid());
                rentCheckinPersonEntity.setName(cert.getReal_name());
                if(!Check.NuNStr(cert.getCert_type())){
                    rentCheckinPersonEntity.setCertType(CertTypeEnum.getByCode(Integer.valueOf(cert.getCert_type())).getCode());//证件类型映射
                }else{
                    rentCheckinPersonEntity.setCertType(1);//先按默认身份证处理
                }
				rentCheckinPersonEntity.setCustomerFrom(profile.getOrigin());//客户来源
                rentCheckinPersonEntity.setPhoneNum(profile.getPhone());
                rentCheckinPersonEntity.setCertPic1(cert.getUser_cert1());
                rentCheckinPersonEntity.setCertPic2(cert.getUser_cert2());
                rentCheckinPersonEntity.setCertNum(cert.getCert_num());
                rentCheckinPersonEntity.setContractId(rentContractEntity.getContractId());
                if(isNull){//只有为空的时候保存
                	int isSuccess = 0;
                	isSuccess = rentCheckinPersonServiceImpl.saveCheckinPerson(rentCheckinPersonEntity);
                	if(1 != isSuccess){//因为会影响后续签约，先报出异常。
                        LogUtil.error(LOGGER, "【findCheckinPerson】保存入住人信息出错！");
                        dto.setErrCode(DataTransferObject.ERROR);
                        dto.setMsg("系统异常！");
                        return dto.toJsonString();
                    }
                }else if(SyncToFinEnum.SUCCESS.getCode() != rentContractEntity.getIsSyncToFin()){//未提交合同做更新操作
                	rentCheckinPersonServiceImpl.updateCheckinPerson(rentCheckinPersonEntity);//更新签约人信息
                }
                dto.putValue("signSubject", signSubject);
                return dto.toJsonString();
            } else {
            	LogUtil.error(LOGGER, "【findCheckinPerson】出错,查询友家返回为空，contractId:{}",contractId);
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("系统异常！");
                return dto.toJsonString();
            }
        }catch(Exception e){
            LogUtil.error(LOGGER, "【findCheckinPerson】出错：{}", e);
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("系统异常！");
            return dto.toJsonString();
        }
    }

    /**
     * <p>通过合同IDcontractId查找入住人信息</p>
     * @author xiangb
     * @created 2017年9月28日
     * @param contractId
     * @return
     */
    public String findCheckinPersonEntityByContractId(String contractId){
        LogUtil.info(LOGGER, "【findCheckinPersonEntityByContractId】入参:{}", contractId);
        DataTransferObject dto = new DataTransferObject();
        if (Check.NuNStr(contractId)) {
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("参数为空！");
            return dto.toJsonString();
        }
        try{
            RentCheckinPersonEntity rentCheckinPersonEntity = this.rentCheckinPersonServiceImpl.findCheckinPersonByContractId(contractId);
            LogUtil.info(LOGGER, "【findCheckinPersonEntityByContractId】查询签约人信息返回:{}", JSONObject.toJSON(rentCheckinPersonEntity));
            if(Check.NuNObj(rentCheckinPersonEntity)){
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("未查询到入住人信息！");
                return dto.toJsonString();
            }
            dto.putValue("rentCheckinPersonEntity", rentCheckinPersonEntity);
            return dto.toJsonString();
        }catch(Exception e){
            LogUtil.error(LOGGER, "【findCheckinPersonEntityByContractId】出错:{}", e);
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("系统异常！");
            return dto.toJsonString();
        }
    }
    /**
     * <p>获取签约房间信息</p>
     * @author xiangb
     * @created 2017年9月25日
     * @param
     * @return
     */
    @Override
    public String findRentRoomInfo(String contractId) {
        LogUtil.info(LOGGER, "【findRentRoomInfo】入参:{}", contractId);
        DataTransferObject dto = new DataTransferObject();
        if(Check.NuNStr(contractId)){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("参数为空！");
            return dto.toJsonString();
        }
        try{
            SignRoomInfoVo signRoomInfo = new SignRoomInfoVo();
            ProjectInfoVo  projectInfo = new ProjectInfoVo();
            // 根据合同ID查询合同基本信息
            RentContractEntity rentContractEntity = rentContractServiceImpl.findContractBaseByContractId(contractId);
            LogUtil.info(LOGGER, "【findRentRoomInfo】查询合同信息返回:{}", JSONObject.toJSON(rentContractEntity));
            if(Check.NuNObj(rentContractEntity)){
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("未查询到合同信息");
                return dto.toJsonString();
            }
            signRoomInfo.setContractId(rentContractEntity.getContractId());
            signRoomInfo.setContractCode(rentContractEntity.getConRentCode());
            if(!Check.NuNObj(rentContractEntity.getConStartDate()) && !Check.NuNObj(rentContractEntity.getConEndDate())){
                signRoomInfo.setRentTime(DateUtilFormate.formatDateToString(rentContractEntity.getConStartDate(),DateUtilFormate.DATEFORMAT_4)+
                		ContractMsgConstant.FROM_TO_MSG+DateUtilFormate.formatDateToString(rentContractEntity.getConEndDate(),DateUtilFormate.DATEFORMAT_4));
            }

            //目前在这里查询签约人资质信息，
            //通过uid去友家查询客户信息
            RentCheckinPersonEntity rentCheckinPersonEntity = null;
            PersonalInfoDto personal = CustomerLibraryUtil.findAuthInfoFromCustomer(rentContractEntity.getCustomerUid());
            LogUtil.info(LOGGER, "【findRentRoomInfo】友家查询用户信息返回：{}", JSONObject.toJSON(personal));
            if(!Check.NuNObj(personal)){
                Extend extend = personal.getExtend();
                Education education = personal.getEducation();
                //需要先看入住人表中是否已有这个人的数据，一个合同绑定一个入住人，通过合同ID查询是否有数据
                rentCheckinPersonEntity = rentCheckinPersonServiceImpl.findCheckinPersonByContractId(rentContractEntity.getContractId());
                LogUtil.info(LOGGER, "【findRentRoomInfo】查询入住人信息返回:{}", JSONObject.toJSONString(rentCheckinPersonEntity));
                if(!Check.NuNObj(rentCheckinPersonEntity) && !Check.NuNObj(extend)){//只有不为空的时候更新
                    rentCheckinPersonEntity.setJob(extend.getIndustry());
                    rentCheckinPersonEntity.setOrganization(extend.getWork_name());
                    rentCheckinPersonEntity.setWorkAddress(extend.getWork_address());
                    rentCheckinPersonEntity.setEmergencyContact(extend.getUrgency_name());
					rentCheckinPersonEntity.setSocialProof(extend.getSocial_proof());
                    rentCheckinPersonEntity.setEmcyCntPhone(extend.getUrgency_phone());
                    rentCheckinPersonEntity.setRelationship(extend.getUrgency_relation());
                    if(!Check.NuNStr(extend.getAccessory_url())){
                    	String url = String.format(ZRAMS_GET_IMAGE, extend.getAccessory_url(),rentContractEntity.getCustomerUid());
                    	rentCheckinPersonEntity.setSocialProofPic(url);
                    	rentContractEntity.setFsocialcertpic(url);
                    }
                    //用uuid去存储平台查询图片地址
//                    FileInfoResponse fileInfoResponse = storageLogic.findPicUrlByuuid(extend.getAccessory_url());
//                    if(!Check.NuNObj(fileInfoResponse)){
//                    	rentCheckinPersonEntity.setSocialProofPic(fileInfoResponse.getFile().getUrl());
//                    }
                    if(!Check.NuNObj(education)){
                    	rentCheckinPersonEntity.setDegree(DegreeEnum.getCodeMapping(education.getEducation()).getCode());
                    }
                    //更新签约人资质信息
                    rentCheckinPersonServiceImpl.updateCheckinPerson(rentCheckinPersonEntity);
                    //更新合同中社会资质照片地址
                    if(!Check.NuNStr(extend.getAccessory_url())){
                    	rentContractServiceImpl.updateBaseContractById(rentContractEntity);
                    }
                }
            } else {
                LogUtil.error(LOGGER, "【findRentRoomInfo】查询友家客户库异常:{}",JSONObject.toJSON(personal));
            }
            String phone = rentContractLogic.getEmployeePhone(rentContractEntity.getProjectId(),rentContractEntity.getFhandlezocode());
    		if(!Check.NuNStr(phone)){
    			signRoomInfo.setHandleZOPhone(phone);
            } else {
            	LogUtil.info(LOGGER, "【findRentRoomInfo】查询ZO信息失败，contractId:{}",rentContractEntity.getContractId());
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("系统异常");
                return dto.toJsonString();
            }
            //查询项目信息
            String projectObject = projectService.findProjectById(rentContractEntity.getProjectId());
            LogUtil.info(LOGGER, "【findRentRoomInfo】查询项目信息返回:{}", projectObject);
            ProjectEntity projectEntity = null;
            DataTransferObject projectDto = JsonEntityTransform.json2DataTransferObject(projectObject);
            if(projectDto.getCode() == DataTransferObject.SUCCESS){
                projectEntity = projectDto.parseData("projectEntity", new TypeReference<ProjectEntity>() {
                });
            }else{
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("未查询到项目信息");
                return dto.toJsonString();
            }
            //拼装合同的头信息
	        // 房间信息
	        String roomStr = roomService.getRoomByFid(rentContractEntity.getRoomId());
	        DataTransferObject roomObject = JsonEntityTransform.json2DataTransferObject(roomStr);
	        if (roomObject.getCode() == DataTransferObject.ERROR) {
		        LogUtil.error(LOGGER, "【getRoomByFid】请求结果={}", roomStr);
		        dto.setMsg(roomObject.getMsg());
		        dto.setErrCode(DataTransferObject.ERROR);
	        }
	        RoomInfoEntity roomInfoEntity = roomObject.parseData("roomInfo", new TypeReference<RoomInfoEntity>() {
	        });
	        projectInfo = RentHeadInfoUtil.getRentHeadInfo(rentContractEntity, projectEntity, roomInfoEntity);
	        signRoomInfo.setProjectInfo(projectInfo);
            //查询房间信息
            RoomInfoVo roomInfo = new RoomInfoVo();
            if(!Check.NuNObj(roomInfoEntity.getFfloornumber())){
                roomInfo.setFloorNumber(roomInfoEntity.getFfloornumber().toString()+ContractMsgConstant.FLOOR_MSG);
            }
//                roomInfo.setHouseTypeName(rentRoomInfo.getHouseTypeName());
            roomInfo.setHouseTypeName(roomInfoEntity.getFroomnumber());//先传房间号，后期改下名称
            if(!Check.NuNObj(roomInfoEntity.getFdirection())){
                roomInfo.setRoomDirection(DirectionEnum.getDirection(Integer.valueOf(roomInfoEntity.getFdirection()))+ContractMsgConstant.DIRECTION_MSG);
            }
            signRoomInfo.setRoomInfo(roomInfo);
            dto.putValue("signRoomInfo", signRoomInfo);
            LogUtil.info(LOGGER, "【findRentRoomInfo】确认房间信息出参：{}", dto.toJsonString());
            return dto.toJsonString();
        }catch(Exception e){
            LogUtil.error(LOGGER, "【findRentRoomInfo】出错:{}", e);
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("系统异常！");
            return dto.toJsonString();
        }
    }



	@Override
	public String getBatchParentContractCode(String paramJson) {
		LogUtil.info(LOGGER, "【getBatchParentContractCode】请求入参:{}", paramJson);
		DataTransferObject dto = new DataTransferObject();
		if (Check.NuNStr(paramJson)) {
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("请求参数不能为空");
			return dto.toJsonString();
		}
		List<String> contractCodes = JsonEntityTransform.json2ObjectList(paramJson, String.class);
		List<RentContractEntity> rentContractEntities = this.rentContractServiceImpl.getBatchParentContractCode(contractCodes);
		dto.putValue("list", rentContractEntities);
		return dto.toJsonString();
	}

	@Override
	public String getRenewPreContract(String currentContractId) {
		LogUtil.info(LOGGER,"【getRenewParentContract】currentRentCode={}",currentContractId);
		DataTransferObject dto = new DataTransferObject();
		if (Check.NuNStr(currentContractId)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("参数为空");
			return dto.toJsonString();
		}
		RentContractEntity rentContractEntity = rentContractServiceImpl.findContractBaseByContractId(currentContractId);
		if (Check.NuNObj(rentContractEntity)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("合同不存在");
			return dto.toJsonString();
		}
		if (rentContractEntity.getFsigntype().equals(ContractSignTypeEnum.RENEW.getValue())){
			rentContractEntity = rentContractServiceImpl.findContractByRentCode(rentContractEntity.getPreConRentCode());
		}

		if (Check.NuNObj(rentContractEntity)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("根父合同为空");
			return dto.toJsonString();
		}
		dto.putValue("preContract",rentContractEntity);
		return dto.toJsonString();
	}

	/**
     * <p>获取操作的枚举类</p>
     * @author xiangb
     * @created 2017年10月11日
     * @param
     * @return
     */
    private static OperationEnum getOperationEnum(String conStatus,Integer deliverStatus){
        if(Check.NuNStr(conStatus)) return null;
        if(ContractStatusEnum.WQY.getStatus().equals(conStatus)){
            return OperationEnum.QY;
        }else if(ContractStatusEnum.DZF.getStatus().equals(conStatus)){
            return OperationEnum.ZF;
        }else if(ContractStatusEnum.YQY.getStatus().equals(conStatus) && deliverStatus == 0){
            return OperationEnum.WYJG;
        }else if(ContractStatusEnum.YQY.getStatus().equals(conStatus) && deliverStatus == 1){
            return null;
        }else{
            return null;
        }
    }

    @Override
    public String updateContractInfoForSubmit(String contractId) {
        LogUtil.info(LOGGER, "【updateDzfByContractId】入参contractId:{}", contractId);
        DataTransferObject dto = new DataTransferObject();
        try {
            if(Check.NuNStrStrict(contractId)){
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("合同id为空");
                return dto.toJsonString();
            }
			//1.查询合同信息（根据合同id)
			RentContractEntity rentContractEntity = rentContractServiceImpl.findContractBaseByContractId(contractId);
			if(Check.NuNObj(rentContractEntity)){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("合同不存在");
				return dto.toJsonString();
			}
			//2.调用计算费用方法,查询各种费用信息
			DataTransferObject itemsDto = financeCommonLogic.getPaymentItems(rentContractEntity);
			PaymentTermsDto paymentTermsDto = (PaymentTermsDto)itemsDto.getData().get("items");
			if(Check.NuNObj(paymentTermsDto)||Check.NuNCollection(paymentTermsDto.getRoomRentBillDtos())){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("该合同的应收账单不存在");
				return dto.toJsonString();
			}
			List<RoomRentBillDto> roomRentBillDtos = paymentTermsDto.getRoomRentBillDtos();
			List<RentContractActivityEntity> rentContractActivityEntities = paymentTermsDto.getActList();
			Double activitySumMoney = 0.00;
			if(!Check.NuNCollection(rentContractActivityEntities)){
				for(RentContractActivityEntity rentContractActivityEntity:rentContractActivityEntities){
					activitySumMoney += rentContractActivityEntity.getDiscountAccount();
				}
			}

			//3.组装合同修改信息
			//①合同状态"未签约"->"待支付"、 ②签约时间、③提交合同时间、④应缴服务费、⑤合同实际出房价：合同签约的价格、
			//⑥活动金额、⑦首期账单总金额、⑧合同押金、⑨实缴服务费、⑩服务费折扣、注意:如果合同没有合同号,则生成合同号
			RentContractEntity updateRentContractEntity = new RentContractEntity();//合同主表
			updateRentContractEntity.setContractId(contractId);
			updateRentContractEntity.setConStatusCode(ContractStatusEnum.DZF.getStatus());
			updateRentContractEntity.setPreConStatusCode(ContractStatusEnum.WQY.getStatus());
			updateRentContractEntity.setConSignDate(new Date());//签约时间
			updateRentContractEntity.setSubmitContractTime(new Date());//提交合同时间
			updateRentContractEntity.setConCommission(paymentTermsDto.getOriginServicePrice().doubleValue());//应缴服务费
			updateRentContractEntity.setFactualprice(paymentTermsDto.getRentPrice().doubleValue());//合同实际出房价：合同签约的价格
			updateRentContractEntity.setActivitymoney(activitySumMoney.toString());//活动金额
			for(RoomRentBillDto roomRentBillDto:roomRentBillDtos) {
				if (roomRentBillDto.getPeriod() == 1) {
					updateRentContractEntity.setFirstPeriodMoney(roomRentBillDto.getPeriodTotalMoney());//首期账单总金额
					updateRentContractEntity.setConDeposit(roomRentBillDto.getDepositPrice().doubleValue());//合同押金
					updateRentContractEntity.setFserviceprice(roomRentBillDto.getServicePrice().doubleValue());//实缴服务费
					updateRentContractEntity.setFdiscountserprice((paymentTermsDto.getOriginServicePrice().subtract(roomRentBillDto.getServicePrice())).doubleValue());//服务费折扣
					break;
				}
			}
			//app签约同时合同号为空时,生成合同号
			if(ContractSourceEnum.APP.getCode() == Integer.valueOf(rentContractEntity.getFsource())&&Check.NuNStrStrict(rentContractEntity.getConRentCode())) {
                LogUtil.info(LOGGER,"【updateContractInfoForSubmit】合同号为空,开始生成合同号,contractId:{}",rentContractEntity.getContractId());
                //查询project信息和CityEntity信息,供生成合同号使用
				String projectJson = projectService.findProjectById(rentContractEntity.getProjectId());
				DataTransferObject projectDto = JsonEntityTransform.json2DataTransferObject(projectJson);
				ProjectEntity projectEntity = SOAResParseUtil.getValueFromDataByKey(projectJson,"projectEntity",ProjectEntity.class);
				if(projectDto.getCode()==DataTransferObject.ERROR||Check.NuNObj(projectEntity)){
					LogUtil.error(LOGGER,"【updateContractInfoForSubmit】调用projectService服务查询项目信息失败,projectId:{},resultJson:{}",rentContractEntity.getProjectId(),projectJson);
					dto.setErrCode(DataTransferObject.ERROR);
					dto.setMsg("调用projectService服务查询项目信息失败");
					return dto.toJsonString();
				}
				String cityJson = cityService.findById(projectEntity.getCityid());
				DataTransferObject cityDto = JsonEntityTransform.json2DataTransferObject(cityJson);
				CityEntity cityEntity = SOAResParseUtil.getValueFromDataByKey(cityJson,"data",CityEntity.class);
				if(cityDto.getCode()==DataTransferObject.ERROR||Check.NuNObj(cityEntity)){
					LogUtil.error(LOGGER,"【updateContractInfoForSubmit】调用cityService服务查询城市信息失败,cityId:{},resultJson:{}",projectEntity.getCityid(),cityJson);
					dto.setErrCode(DataTransferObject.ERROR);
					dto.setMsg("调用cityService服务查询城市信息失败");
					return dto.toJsonString();
				}
				String contractCode = rentContractServiceImpl.contractCode(rentContractEntity.getConType(),rentContractEntity.getCustomerType(),projectEntity.getFcode(),cityEntity);
				updateRentContractEntity.setConRentCode(contractCode);//合同号
			}

			//4.如果为后台提交合同, ①修改合同状态为不可修改、
			// ②修改物业交割状态为"已交割"
			if(ContractSourceEnum.ZRAMS.getCode()==Integer.valueOf(rentContractEntity.getFsource())){
				updateRentContractEntity.setIsPossibleModify(ContractIsModifyEnum.BKXG.getCode());
			}

			//5.组装合同子表信息
			//①合同押金、 ②应缴服务费、③实缴服务费、④应缴服务费、⑤合同实际出房价：合同签约的价格、
			RentDetailEntity updateRentDetailEntity = new RentDetailEntity();//合同子表
			updateRentDetailEntity.setContractId(rentContractEntity.getContractId());
			updateRentDetailEntity.setMustDeposit(paymentTermsDto.getDepositPrice().doubleValue());//合同押金
			updateRentDetailEntity.setMustCommission(paymentTermsDto.getOriginServicePrice().doubleValue());//应缴服务费
			updateRentDetailEntity.setDiscountCommission(paymentTermsDto.getServicePrice().doubleValue());//实缴服务费
			updateRentDetailEntity.setPersonDataStatus(2);
			//5.保存入库
			rentContractServiceImpl.updateFinReceiBillFields(rentContractEntity,updateRentContractEntity,updateRentDetailEntity);//保存入库
        }catch (Exception e){
            LogUtil.error(LOGGER, "【updateContractInfoForSubmit】 e:{},contractId={}", e, contractId);
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("系统错误");
        }
        return dto.toJsonString();
    }

    @Override
    public String rollBackContractInfoForAppSubmit(String contractId) {
        LogUtil.info(LOGGER, "【rollBackContractInfoForAppSubmit】入参contractId:{}", contractId);
        DataTransferObject dto = new DataTransferObject();
        try {
            if(Check.NuNStrStrict(contractId)){
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("合同id为空");
                return dto.toJsonString();
            }
            rentContractServiceImpl.rollBackContractInfoForAppSubmit(contractId);
        }catch (Exception e){
            LogUtil.error(LOGGER, "【rollBackContractInfoForAppSubmit】 contractId={}, error:{}", contractId, e);
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("系统错误");
        }
        return dto.toJsonString();
    }

	@Override
	public String findRentRoomsInfoByParentId(String parentId) {
		LogUtil.info(LOGGER,"【findRentRoomsInfoByParentId】入参param:{}", parentId);
		if (Check.NuNStrStrict(parentId)) {
			return DataTransferObjectBuilder.buildErrorJsonStr("parentId 为空");
		}
		RentRoomDto rentRoomDto = new RentRoomDto(parentId, BussTypeEnum.RENEWALSIGN.getCode());
		try {
			List<RentContractRoomVo> rentContractRoomVoList = this.findRentRoomsInfo(rentRoomDto);
			return DataTransferObjectBuilder.buildOkJsonStr("data", rentContractRoomVoList);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "【findRentRoomsInfoByParentId】 rentRoomDto: , error:{}", rentRoomDto.toJsonStr(), e);
			return DataTransferObjectBuilder.buildErrorJsonStr("系统异常: findRentRoomsInfoByParentId ");
		}

	}

	/**
	 * 根据父合同id查询合同房间信息
	 * @author cuiyuhui
	 * @created
	 * @param
	 * @return
	 */
	@Override
	public String findRentRoomsInfoFromSurrender(String paramJson) {
		LogUtil.info(LOGGER,"【findRentRoomsInfoFromSurrender】入参param:{}", paramJson);
		if (Check.NuNStrStrict(paramJson)) {
			return DataTransferObjectBuilder.buildErrorJsonStr("parentId 为空");
		}

		RentRoomDto rentRoomDto = JsonEntityTransform.json2Object(paramJson, RentRoomDto.class);

		if (Check.NuNStrStrict(rentRoomDto.getSurParentRentId()) || rentRoomDto.getBussType() == null) {
			return DataTransferObjectBuilder.buildErrorJsonStr("findRentRoomsInfoFromSurrender 入参param");
		}
		try {
			List<RentContractRoomVo> rentContractRoomVoList = this.findRentRoomsInfo(rentRoomDto);
			return DataTransferObjectBuilder.buildOkJsonStr("data", rentContractRoomVoList);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "【findRentRoomsInfoFromSurrender】 rentRoomDto: , error:{}", rentRoomDto.toJsonStr(), e);
			return DataTransferObjectBuilder.buildErrorJsonStr("系统异常: findRentRoomsInfoFromSurrender ");
		}
	}

	/**
	 *
	 * @author cuiyuhui
	 * @created
	 * @param
	 * @return
	 */
	private List<RentContractRoomVo> findRentRoomsInfo(RentRoomDto rentRoomDto) {
		List<RentDetailEntity> rentDetailEntityList = rentContractServiceImpl.findRentDetailsByRentRoomDto(rentRoomDto);
		String roomIds = rentDetailEntityList.stream().map(RentDetailEntity::getRoomId).collect(Collectors.joining(","));
		String roomListStr = roomService.getRoomListByRoomIds(roomIds);
		List<RoomInfoEntity>  roomInfoEntityList = null;
		try {
			roomInfoEntityList  = SOAResParseUtil.getListValueFromDataByKey(roomListStr, "roomInfoList", RoomInfoEntity.class);
		} catch (SOAParseException e) {
			throw new ZrpServiceException();
		}
		List<RentContractRoomVo> rentContractRoomVoList = mergeObject(rentRoomDto.getSurParentRentId(), rentDetailEntityList, roomInfoEntityList);
		return rentContractRoomVoList;
	}


	@Override
	public String findOneRentContractByParentId(String surParentRentId) {
		LogUtil.info(LOGGER,"findOneRentContractByParentId 入参 surParentRentId{}", surParentRentId);

		try {
			if (Check.NuNStrStrict(surParentRentId)) {
				return DataTransferObjectBuilder.buildErrorJsonStr("父合同号为空");
			}

			RentContractEntity rentContractEntity = rentContractServiceImpl.findOneRentContractByParentId(surParentRentId);

			//此处就是想学习一下Optional怎么使用 -- 一定不是这么使用的
			Optional<RentContractEntity> rentContractEntityOptional = Optional.ofNullable(rentContractEntity);
			if (rentContractEntityOptional.isPresent()) {
				return DataTransferObjectBuilder.buildOkJsonStr("data", rentContractEntityOptional.get());
			} else {
				return DataTransferObjectBuilder.buildErrorJsonStr("没有查询到有效合同");
			}
		} catch (Exception e) {
			LogUtil.error(LOGGER, "surParentRentId ={},error={}", surParentRentId, e);
			return DataTransferObjectBuilder.buildErrorJsonStr("系统异常 surParentRentId :" + surParentRentId);
		}



    }

    /**
     * 将子表信息与房间信息合为一处
     * @author cuiyuhui
     * @created
     * @param
     * @return
     */
    private List<RentContractRoomVo> mergeObject(String parentId, List<RentDetailEntity> rentDetailEntityList,  List<RoomInfoEntity>  roomInfoEntityList) {

        Map<String , RoomInfoEntity> roomInfoEntityMap = new HashMap<>();
        roomInfoEntityList.forEach( roomInfoEntity -> {
            roomInfoEntityMap.put(roomInfoEntity.getFid(), roomInfoEntity);
        });

        List<RentContractRoomVo> rentContractRoomVoList = new ArrayList<>();
        rentDetailEntityList.forEach( rentDetailEntity -> {
            String roomId = rentDetailEntity.getRoomId();
            RoomInfoEntity roomInfoEntity = roomInfoEntityMap.get(roomId);

            String surParentRentId =  rentDetailEntity.getContractId();
            String contractId = rentDetailEntity.getContractId();

            String roomNumber = roomInfoEntity.getFroomnumber();
            RentContractRoomVo rentContractRoomVo = new RentContractRoomVo(surParentRentId, contractId, roomId, roomNumber);
            rentContractRoomVoList.add(rentContractRoomVo);
        });
        return rentContractRoomVoList;
    }

    /**
     * 关闭合同接口
     * <p>
     *     1.未签约合同 直接关闭合同
     *     2.待支付合同 已经部分支付 生成付款单 关闭合同和释放房间 同步合同状态到财务;未支付的 关闭合同和释放房间 同步合同状态到财务.
     *     3.发送短信 通知和 推送消息
     * </p>
     * @param contractId 合同id
     * @param closeType 合同关闭类型
     * @return json
     * @author cuigh6
     * @Date 2017年10月13日
     */
    @Override
    public String closeContract(String contractId, Integer closeType) {
	    LogUtil.info(LOGGER, "【closeContract】参数={},{}", contractId, closeType);
	    DataTransferObject dto = new DataTransferObject();
	    if (Check.NuNStr(contractId)) {
		    dto.setErrCode(DataTransferObject.ERROR);
		    dto.setMsg("合同标识为空");
		    return dto.toJsonString();
	    }
	    try {
        	/*查询合同信息*/
		    RentContractEntity rentContractEntity = this.rentContractServiceImpl.findContractBaseByContractId(contractId);

		    if (rentContractEntity.getFsigntype().equals(ContractSignTypeEnum.RENEW.getValue())) {// 续约合同关闭 更改原合同状态为已签约
			    RentContractEntity rentContractEntity1 = this.rentContractServiceImpl.findValidContractByRentCode(rentContractEntity.getPreConRentCode());
			    int preAffect = this.rentContractServiceImpl.updateContractStatus(rentContractEntity1.getContractId(), ContractStatusEnum.YQY.getStatus(), null);
			    LogUtil.info(LOGGER, "【关闭合同】:contractId={},affect={},续约合同更改前合同状态", rentContractEntity1.getContractId(), preAffect);
		    }

		    //判断合同状态 如果为未签约 则直接更改合同状态为 已关闭
		    if (ContractStatusEnum.WQY.getStatus().equals(rentContractEntity.getConStatusCode())) {
				/*直接更改合同状态为 已关闭*/
			    releaseRoomAndCloseContract(ContractStatusEnum.YGB.getStatus(), closeType, rentContractEntity);
			    return dto.toJsonString();
		    }
			/*如果合同状态为 待支付 情况 判断是否支付部分金额 if-支付部分金额 生成付款单 关闭合同,释放房间 if-未支付 关闭合同 释放房间*/
		    if (ContractStatusEnum.DZF.getStatus().equals(rentContractEntity.getConStatusCode())) {
				/*查询合同首次应收账单是否为全部核销*/
			    List<ReceiptBillResponse> receiptBillResponses = this.callFinanceServiceProxy.getBillListByType(rentContractEntity.getConRentCode(), DocumentTypeEnum.RENT_FEE.getCode(), 1);

			    if (receiptBillResponses.stream().allMatch(v -> v.getVerificateStatus() == VerificateStatusEnum.NO.getCode())) {// 所有都是未核销状态 直接关闭合同释放房间
				    LogUtil.info(LOGGER, "【关闭合同】:contractId={},账单全部为未核销状态", contractId);
				    releaseRoomAndCloseContract(ContractStatusEnum.YGB.getStatus(), closeType, rentContractEntity);
				    DataTransferObject updateContractResult = updateFinanceContractStatus(rentContractEntity);
				    if (updateContractResult.getCode() == DataTransferObject.ERROR) {
					    LogUtil.error(LOGGER, "【关闭合同】同步合同状态为#已关闭#到财务失败,contractId={}", rentContractEntity.getContractId());
					    return updateContractResult.toJsonString();
				    }
			    } else if (receiptBillResponses.stream().allMatch(v -> v.getVerificateStatus() == VerificateStatusEnum.DONE.getCode())) {// 所有都是已核销,不做任何处理,说明用户已经全部支付了.
				    LogUtil.info(LOGGER, "【关闭合同】:contractId={},账单全部为已核销状态,不做任何处理", contractId);
				    if (rentContractEntity.getFsigntype().equals(ContractSignTypeEnum.RENEW.getValue())) {// 续约合同关闭 更改原合同状态为已签约
					    RentContractEntity rentContractEntity1 = this.rentContractServiceImpl.findValidContractByRentCode(rentContractEntity.getPreConRentCode());
					    int preAffect = this.rentContractServiceImpl.updateContractStatus(rentContractEntity1.getContractId(), ContractStatusEnum.YXY.getStatus(), null);
					    LogUtil.info(LOGGER, "【关闭合同】:contractId={},affect={},续约合同更改前合同状态", rentContractEntity1.getContractId(), preAffect);
				    }
				    dto.setErrCode(DataTransferObject.ERROR);
				    dto.setMsg("账单全部为已核销状态,不做任何处理");
				    return dto.toJsonString();
			    } else if (receiptBillResponses.stream().anyMatch(v -> v.getVerificateStatus() == VerificateStatusEnum.PART.getCode() || v.getVerificateStatus() == VerificateStatusEnum.DONE.getCode())) { //生成付款单
				    LogUtil.info(LOGGER, "【关闭合同】:contractId={},账单存在未核销和部分核销,需要退款处理", contractId);
				    //构建付款单数据 保存数据
				    List<PaymentBillEntity> paymentBillEntityList = new ArrayList<>();
				    List<PaymentBillDetailEntity> paymentBillDetailEntityList = new ArrayList<>();
				    List<PayVoucherReqDto> reqDtos = this.buildPayVoucherData(rentContractEntity, paymentBillEntityList, paymentBillDetailEntityList);
				    if (!Check.NuNCollection(reqDtos)) {
					    //保存付款单
					    Integer saveAffect = this.paymentBillServiceImpl.savePaymentBill(paymentBillEntityList, paymentBillDetailEntityList);
					    if (saveAffect < 1) {
						    LogUtil.info(LOGGER, "【关闭合同】保存付款单,更改影响行数:{}", saveAffect);
						    return DataTransferObjectBuilder.buildErrorJsonStr("保存付款单失败");
					    }
					    DataTransferObject createResult = this.callFinanceServiceProxy.createPayVouchers(JsonEntityTransform.Object2Json(reqDtos));
					    if (createResult.getCode() == DataTransferObject.ERROR) {
						    //更改付款单同步财务状态
						    int affectRecord = this.paymentBillServiceImpl.updatePaymentSyncStatus(paymentBillEntityList);
						    LogUtil.info(LOGGER, "【关闭合同】同步失败,更改影响行数:{}", affectRecord);
						    return createResult.toJsonString();
					    }
				    }
				    //更改财务合同状态
				    DataTransferObject updateContractResult = updateFinanceContractStatus(rentContractEntity);
				    if (updateContractResult.getCode() == DataTransferObject.ERROR) {
					    LogUtil.error(LOGGER, "【关闭合同】同步合同状态为#已关闭#到财务失败,contractId={}", rentContractEntity.getContractId());
					    return updateContractResult.toJsonString();
				    }
				    this.releaseRoomAndCloseContract(ContractStatusEnum.YGB.getStatus(), closeType, rentContractEntity);//释放房间
					//判断当前房间下有没有有效合同，若没有，设置电表为后付费
					ModifyWattPayTypeDto modifyWattPayTypeDto = new ModifyWattPayTypeDto();
					modifyWattPayTypeDto.setRoomId(rentContractEntity.getRoomId());
					modifyWattPayTypeDto.setProjectId(rentContractEntity.getProjectId());
					modifyWattPayTypeDto.setPayType(SmartPlatformWaterWattPayTypeEnum.AFTERPAYMENT.getCode());
					DataTransferObject modifyDto = intellectPlatformLogic.checkValidContractAndModifyWattPayType(JsonEntityTransform.Object2Json(modifyWattPayTypeDto));
					if(DataTransferObject.ERROR==modifyDto.getCode()){
						LogUtil.error(LOGGER, "【关闭合同】调用校验房间是否有合同以及修改智能电表付费方式失败,param={}", JsonEntityTransform.Object2Json(modifyWattPayTypeDto));
					}
			    }
			    return dto.toJsonString();
		    }

		    dto.setMsg("合同状态不正确");
		    dto.setErrCode(DataTransferObject.ERROR);
	    } catch (Exception e) {
		    LogUtil.error(LOGGER, "[关闭合同]异常错误error={}", e);
		    dto.setErrCode(DataTransferObject.ERROR);
		    dto.setMsg("服务错误");
	    }
	    return dto.toJsonString();
    }

	/**
	 * 更改财务合同状态 为已关闭
	 * @param rentContractEntity 合同对象
	 * @return dto
	 * @author cuigh6
	 * @Date 2017年11月6日
	 */
	private DataTransferObject updateFinanceContractStatus(RentContractEntity rentContractEntity) {
		//更改财务合同状态
		String result = "";
		SyncContractVo syncContractVo = new SyncContractVo();
		syncContractVo.setRentContractCode(rentContractEntity.getConRentCode());
		syncContractVo.setCrmContractId(rentContractEntity.getContractId());
		syncContractVo.setStatusCode(ContractStatusEnum.YGB.getStatus());
		//兼容历史 合同数据  合同号=原合同号+房间号 crmid=rent_rentdetail fid字段
		if (rentContractEntity.getDataVersion() == ContractDataVersionEnum.OLD.getCode()){
			List<RentDetailEntity> listDetails = rentContractServiceImpl.findRentDetailByContractId(rentContractEntity.getContractId());
			if (!Check.NuNCollection(listDetails)){
				for (RentDetailEntity rentDetailEntity : listDetails){
					syncContractVo.setRentContractCode(rentContractEntity.getConRentCode() + "+" + rentDetailEntity.getRoomCode());
					syncContractVo.setCrmContractId(rentDetailEntity.getId());
					result = this.callFinanceServiceProxy.updateContract(JsonEntityTransform.Object2Json(syncContractVo));
				}
			}

		}else{
			result = this.callFinanceServiceProxy.updateContract(JsonEntityTransform.Object2Json(syncContractVo));

		}
		return JsonEntityTransform.json2DataTransferObject(result);
	}


	/**
     * <p>
     *     作废合同 已签约待审核的合同 可以作废
     * </p>
     * @param contractId 合同id
     * @param closeType 关闭类型
     * @return
     * @author cuigh6
     * @Date 2017年10月20日
     */
	@Override
    public String invalidContract(String contractId,Integer closeType) {
        LogUtil.info(LOGGER, "【invalidContract】参数={}", contractId);
        DataTransferObject dto = new DataTransferObject();
        if (Check.NuNStr(contractId)) {
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("合同标识为空");
            return dto.toJsonString();
        }
        /*查询合同信息*/
		RentContractEntity rentContractEntity = this.rentContractServiceImpl.findContractBaseByContractId(contractId);
		if (rentContractEntity.getFsigntype().equals(ContractSignTypeEnum.RENEW.getValue())) {// 续约合同关闭 更改原合同状态为已签约
			RentContractEntity rentContractEntity1 = this.rentContractServiceImpl.findValidContractByRentCode(rentContractEntity.getPreConRentCode());
			int preAffect = this.rentContractServiceImpl.updateContractStatus(rentContractEntity1.getContractId(), ContractStatusEnum.YQY.getStatus(), null);
			LogUtil.info(LOGGER, "【关闭合同】:contractId={},affect={},续约合同更改前合同状态", rentContractEntity1.getContractId(), preAffect);
		}
		try {
			//合同已签约 待审核状态
			if (ContractStatusEnum.YQY.getStatus().equals(rentContractEntity.getConStatusCode()) &&
					rentContractEntity.getConAuditState().equals(ContractAuditStatusEnum.YHBH.getStatus())) {
				//构建付款单数据 保存到数据库中
				List<PaymentBillEntity> paymentBillEntityList = new ArrayList<>();
				List<PaymentBillDetailEntity> paymentBillDetailEntityList = new ArrayList<>();
				List<PayVoucherReqDto> reqDtos = this.buildPayVoucherData(rentContractEntity, paymentBillEntityList, paymentBillDetailEntityList);
				if (!Check.NuNCollection(reqDtos)) {
					//保存付款单
					Integer saveAffect = this.paymentBillServiceImpl.savePaymentBill(paymentBillEntityList, paymentBillDetailEntityList);
					if (saveAffect < 1) {
						LogUtil.info(LOGGER, "【作废合同】保存付款单,更改影响行数:{}", saveAffect);
						return DataTransferObjectBuilder.buildErrorJsonStr("保存付款单失败");
					}
					DataTransferObject createResult = this.callFinanceServiceProxy.createPayVouchers(JsonEntityTransform.Object2Json(reqDtos));
					if (createResult.getCode() == DataTransferObject.ERROR) {
						//更改付款单同步财务状态
						int affectRecord = this.paymentBillServiceImpl.updatePaymentSyncStatus(paymentBillEntityList);
						LogUtil.info(LOGGER, "【作废合同】同步失败,更改影响行数:{}", affectRecord);
						return createResult.toJsonString();
					}
				}
				//更改财务合同状态
				DataTransferObject syncStatus = this.updateFinanceContractStatus(rentContractEntity);
				if (syncStatus.getCode() == DataTransferObject.ERROR){
					LogUtil.error(LOGGER,"同步财务状态错误error={}",syncStatus.getMsg());
				}

				this.releaseRoomAndCloseContract(ContractStatusEnum.YZF.getStatus(), closeType, rentContractEntity);//释放房间
			}
			return dto.toJsonString();
		} catch (Exception e) {
			LogUtil.error(LOGGER, "【作废合同】contractId={}", rentContractEntity.getContractId(), e);
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("作废合同失败");
			return dto.toJsonString();
		}
	}


    /**
     * 释放房间和关闭合同
     * @param status 状态
     * @param closeType 关闭类型
     * @param rentContractEntity
	 * @author cuigh6
     * @Date 2017年10月19日
     */
    private void releaseRoomAndCloseContract(String status, Integer closeType, RentContractEntity rentContractEntity) {
		String contractId = rentContractEntity.getContractId();
		String roomId = rentContractEntity.getRoomId();
		List<String> releaseRoomIds = new ArrayList<>();
        try {
			/*直接更改合同状态为 已关闭*/
            int affect = this.rentContractServiceImpl.updateContractStatus(contractId, status, closeType);//todo 分布式事务处理
            LogUtil.info(LOGGER, "【更改合同状态为已关闭】参数={},affect={}", contractId, affect);
            //兼容历史企业数据 关闭合同后 查询所有房间释放
			if (rentContractEntity.getCustomerType() == CustomerTypeEnum.ENTERPRICE.getCode() && rentContractEntity.getDataVersion() == ContractDataVersionEnum.OLD.getCode()){
				List<RentDetailEntity> listDetails = rentContractServiceImpl.findRentDetailByContractId(rentContractEntity.getContractId());
				if (!Check.NuNCollection(listDetails)){
					for (RentDetailEntity rentDetailEntity : listDetails){
						releaseRoomIds.add(rentDetailEntity.getRoomId());
					}
				}
			}else{
				releaseRoomIds.add(roomId);
			}

			for (String room : releaseRoomIds){
				// 判断房间是否存在有效的合同
				List<RentContractEntity> rentContractEntities = this.rentContractServiceImpl.findValidContractByRoomId(room);
				if (rentContractEntities.size() == 0) {
					// 释放房间 更改房间状态 锁定->待租中
					this.roomService.updateRoomInfoAndDeleteRentInfo(roomId);
				}else {
					LogUtil.info(LOGGER, "【关闭合同释放房间】房间存在有效合同不能释放={},合同信息={}", contractId, JSON.toJSONString(rentContractEntities));
				}
			}
        } catch (Exception e) {
	        LogUtil.error(LOGGER, "【释放房间错误】contractId={},error={}", contractId, e);
        }
    }

    /**
     * 构建付款单数据
     *
     * @param rentContractEntity 合同对象
     * @return PayVoucherReqDto
     * @author cuigh6
     * @Date 2017年10月16日
     */
    private List<PayVoucherReqDto> buildPayVoucherData(RentContractEntity rentContractEntity, List<PaymentBillEntity> paymentBillEntityList, List<PaymentBillDetailEntity> paymentBillDetailEntityList) {
        ReceiptBillListRequestDto receiptBillListRequestDto = new ReceiptBillListRequestDto();
        receiptBillListRequestDto.setContractCode(rentContractEntity.getConRentCode());
        receiptBillListRequestDto.setReceiptStatus("0");// 已收款状态
        String result = this.callFinanceServiceProxy.getReceiptBillListByContract(JsonEntityTransform.Object2Json(receiptBillListRequestDto));
        DataTransferObject resultObject = JsonEntityTransform.json2DataTransferObject(result);
        if (resultObject.getCode() == DataTransferObject.ERROR) {
            return null;
        }
        List<ReceiptBillListResponseDto> payDetailList = resultObject.parseData("list", new TypeReference<List<ReceiptBillListResponseDto>>() {
        });
        List<PayVoucherReqDto> payVoucherReqDtoList = new ArrayList<>();
        PayVoucherReqDto reqDto;
        PaymentBillEntity paymentBillEntity;
        int i =1;
        for (ReceiptBillListResponseDto responseDto : payDetailList) {
	        if (responseDto.getPaymentTypeCode().equals("yjzkzz")) {// 如果支付方式为押金转款 不退款(处理续约押金转款情况)
		        continue;
	        }
	        if (responseDto.getReceiptMothed().equals("xxzf")) {// 如果付款渠道为线下支付 不退款
		        continue;
	        }
	        reqDto = new PayVoucherReqDto();
            reqDto.setPaySerialNum(responseDto.getPaySerialNum());
            reqDto.setOutContract(rentContractEntity.getConRentCode());
            reqDto.setSourceCode(responseDto.getPaymentTypeCode());
            reqDto.setPayTime(responseDto.getPayTime());
            reqDto.setIsCheckContract(1);
            reqDto.setAccountFlag(2);
            reqDto.setBusId(rentContractEntity.getConRentCode() + "_" + System.currentTimeMillis()+i);
	        i++;
	        reqDto.setBillType("C");//单据类型
            reqDto.setUid(rentContractEntity.getCustomerUid());
	        reqDto.setRecievedAccount(rentContractEntity.getCustomerMobile());

            //构建保存数据
            paymentBillEntity = new PaymentBillEntity();

            String fid = UUIDGenerator.hexUUID();
            paymentBillEntity.setFid(fid);
            paymentBillEntity.setPaySerialNum(responseDto.getPaySerialNum());
            paymentBillEntity.setOutContract(rentContractEntity.getConRentCode());
            paymentBillEntity.setSourceCode(responseDto.getPaymentTypeCode());
            paymentBillEntity.setPayTime(DateUtilFormate.formatStringToDate(responseDto.getPayTime()));
            paymentBillEntity.setAccountFlag(2);
            paymentBillEntity.setBillType("C");
            paymentBillEntity.setUid(rentContractEntity.getCustomerUid());
            paymentBillEntity.setAuditFlag(4);
            paymentBillEntity.setPayFlag(2);
            paymentBillEntity.setGenWay(0);// 自动生成
            paymentBillEntity.setBusId(rentContractEntity.getConRentCode() + "_" + System.currentTimeMillis());
            paymentBillEntity.setCreateDate(new Date());
            paymentBillEntity.setPanymentTypeCode(reqDto.getPanymentTypeCode());// zhdjzye
            paymentBillEntity.setMarkCollectCode("yzrk");
	        paymentBillEntity.setRecievedAccount(rentContractEntity.getCustomerMobile());// 自如账号
	        paymentBillEntityList.add(paymentBillEntity);

            PaymentBillDetailEntity paymentBillDetailEntity;

            List<PayVoucherDetailReqDto> list = new ArrayList<>();
            for (Map<String, Object> map : responseDto.getReceiptList()) {
                PayVoucherDetailReqDto detailReqDto = new PayVoucherDetailReqDto();
                detailReqDto.setCostCode((String) map.get("costCode"));
                detailReqDto.setRefundAmount(-BigDecimal.valueOf((int) map.get("amount")).divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_HALF_UP).doubleValue());
                list.add(detailReqDto);

                paymentBillDetailEntity = new PaymentBillDetailEntity();
                paymentBillDetailEntity.setBillFid(fid);
                paymentBillDetailEntity.setCostCode((String) map.get("costCode"));
                paymentBillDetailEntity.setCreateDate(new Date());
                paymentBillDetailEntity.setFid(UUIDGenerator.hexUUID());
                paymentBillDetailEntity.setRefundAmount(-detailReqDto.getRefundAmount());
                paymentBillDetailEntityList.add(paymentBillDetailEntity);
            }
            reqDto.setPayVouchersDetail(list);
            payVoucherReqDtoList.add(reqDto);
        }
        return payVoucherReqDtoList;
    }
    /**
	 * <p>根据出房合同号查询合同实体信息</p>
	 * @author xiangb
	 * @created 2017年10月17日
	 * @param
	 * @return
	 */
    @Override
    public String findContractByCode(String contractCode) {
        LogUtil.info(LOGGER, "【findContractByCode】参数={}", contractCode);
        DataTransferObject dto = new DataTransferObject();
        if (Check.NuNStr(contractCode)) {
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("参数为空");
            return dto.toJsonString();
        }
        try {
            RentContractEntity rentContractEntity = rentContractServiceImpl
                    .findContractByRentCode(contractCode);
            LogUtil.info(LOGGER, "【findContractByCode】查询合同详情返回={}",
                    JsonEntityTransform.Object2Json(rentContractEntity));
            if (Check.NuNObj(rentContractEntity)) {
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("未查询到合同信息！");
                return dto.toJsonString();
            }
            dto.putValue("rentContractEntity", rentContractEntity);
            return dto.toJsonString();
        }catch(Exception e){
            LogUtil.error(LOGGER, "【findContractByCode】异常:{}",e);
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("系统异常！");
            return dto.toJsonString();
        }
    }
    /**
	 * <p>更新合同状态</p>
	 * @author xiangb
	 * @created 2017年10月17日
	 * @param contractId sourceStatus原状态 targetStatus 目标状态
	 * @return
	 */
    @Override
    public String updateContractStatus(String contractId, String sourceStatus,
                                       String targetStatus) {
        LogUtil.info(LOGGER, "【updateContractStatus】参数:contractId:{},sourceStatus:{},targetStatus:{}", contractId,sourceStatus,targetStatus);
        DataTransferObject dto = new DataTransferObject();
        if (Check.NuNStr(contractId) || Check.NuNStr(targetStatus)) {
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("参数为空");
            return dto.toJsonString();
        }
        try {
	        RentContractEntity rentContractEntity = new RentContractEntity();
	        rentContractEntity.setContractId(contractId);
	        rentContractEntity.setPreConStatusCode(sourceStatus);
	        rentContractEntity.setConStatusCode(targetStatus);
	        rentContractServiceImpl.updateContractToTargetStatus(rentContractEntity);
            return dto.toJsonString();
        }catch(Exception e){
            LogUtil.error(LOGGER, "【updateContractStatus】异常:{}",e);
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("更新合同状态异常！");
            return dto.toJsonString();
        }
    }
    /**
	 * <p>获取所有待支付账单及生活账单个数和房租账单个数</p>
	 * @author xiangb
	 * @created 2017年10月19日
	 * @param contractCode
	 * @return
	 */
    @Override
    public String findWaitforPaymentList(String contractCode) {
        LogUtil.info(LOGGER, "【findWaitforPaymentList】参数:contractCode:{}",
                contractCode);
        DataTransferObject dto = new DataTransferObject();
        if (Check.NuNStr(contractCode)) {
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("参数为空");
            return dto.toJsonString();
        }
        try {
            //查询全部未支付账单
            List<ReceiptBillResponse> listBill = this.callFinanceServiceProxy.getBillListByType(contractCode, null,null);
            int liftCount = 0;//未支付生活费用和逾期违约金个数
            int allCount = 0;//待支付账单总个数
            ReceiptBillResponse fzbillResponse = new ReceiptBillResponse();//最近一期房租账单
            List<ReceiptBillResponse> shfyResponse = new ArrayList<>();//生活费用账单
            	//遍历并赋值
            	if(!Check.NuNCollection(listBill)){
            		Map<String,Integer> countMap = new HashMap<String, Integer>();
    				countMap.put("liftCount", liftCount);
    				countMap.put("allCount", allCount);
    				rentContractLogic.findWaitforPaymentListUtil(listBill, countMap, fzbillResponse, shfyResponse);
    				liftCount = countMap.get("liftCount");
    				allCount = countMap.get("allCount");
            	}

                if(!Check.NuNObj(fzbillResponse) && !Check.NuNStr(fzbillResponse.getBillNum())){
                    dto.putValue("fzbillResponse", fzbillResponse);
                }
                if(liftCount > 0){
                    dto.putValue("liftCount", liftCount);
                    dto.putValue("shfyResponse", shfyResponse);
                }
                if(allCount > 0){
                    dto.putValue("allCount", allCount);
                }
                return dto.toJsonString();
        } catch (Exception e) {
            LogUtil.error(LOGGER, "【findWaitforPaymentList】出错:{}", e);
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("系统异常");
            return dto.toJsonString();
        }
    }
    /**
	 * 获取合同支持的出租方式(年租、月租、日租);
	 * 最终也是为了房间支持的出租方式
	 * @author cuiyuhui
	 * @created
	 * @param
	 * @return
	 */
	@Override
	public String findSupportConTypeByParentId(String surParentRentId) {
		LogUtil.info(LOGGER,"findSupportConTypeByParentId {}", surParentRentId);
		if (Check.NuNStrStrict(surParentRentId)) {
			return DataTransferObjectBuilder.buildErrorJsonStr("传入父合同id为空");
		}

		List<RentContractEntity> contractEntityList = this.rentContractServiceImpl.findContractListByParentId(surParentRentId);
		String roomIds  = contractEntityList.stream().map(RentContractEntity::getRoomId).collect(Collectors.joining(","));
		String roomStr = this.roomService.getRoomListByRoomIds(roomIds);
		DataTransferObject roomDto = JsonEntityTransform.json2DataTransferObject(roomStr);

		try {
			//目的是为了拿到房间中出租类型(年租，月租，日租)的最小值.小于且等于[最小值]的出租类型，即多个房间可支持的出租类型
			List<RoomInfoEntity> roomInfoEntityList  = SOAResParseUtil.getListValueFromDataByKey(roomStr, "roomInfoList" , RoomInfoEntity.class);
			int minShortRent = roomInfoEntityList.stream().mapToInt( roomInfoEntity -> Integer.valueOf(roomInfoEntity.getFshortrent())).min().getAsInt();

			//获取所有房间共同支持的出租类型
			List<BaseFieldVo> baseFieldVoList  = Arrays.stream(LeaseCycleEnum.values())
					.filter(leaseCycleEnum ->  Integer.valueOf(leaseCycleEnum.getCode()) <= minShortRent)
					.map(leaseCycleEnum -> new BaseFieldVo(leaseCycleEnum.getName(), leaseCycleEnum.getCode()))
					.collect(Collectors.toList());
			return DataTransferObjectBuilder.buildOkJsonStr(baseFieldVoList);

		} catch (Exception e) {
			LogUtil.error(LOGGER,"roomIds:" +roomIds, e);
		}

		return DataTransferObjectBuilder.buildErrorJsonStr("查询错误!");

	}

	/**
	 * 签业签过程中中，合同信息查询
	 * @author cuiyuhui
	 * @created
	 * @param paramJson
	 * @return
	 */
	@Override
	public String findWqyEpsContractPageInfo(String paramJson) {
		try {
			LogUtil.info(LOGGER,"[findWqyEpsContractPageInfo]{}", paramJson);
			if (Check.NuNStrStrict(paramJson)) {
				return DataTransferObjectBuilder.buildErrorJsonStr("查询参数为空");
			}

			ContractParamDto contractParamDto = JsonEntityTransform.json2Entity(paramJson, ContractParamDto.class);
			List<RentContractEntity> rentContractEntityList = rentContractServiceImpl.findContractListByParentId(contractParamDto.getSurParentRentId());


			String roomIds = rentContractEntityList.stream().map(RentContractEntity::getRoomId).collect(Collectors.joining(","));
			List<RoomInfoEntity> roomInfoEntityList = this.findRoomList(roomIds);
			Map<String, RoomInfoEntity> roomInfoEntityMap =  roomInfoEntityList.stream().collect(Collectors.toMap(RoomInfoEntity::getFid, roomInfoEntity -> roomInfoEntity));

			String houseTypeIds = roomInfoEntityList.stream().map(RoomInfoEntity::getHousetypeid).distinct().collect(Collectors.joining(","));
			List<HouseTypeEntity> houseTypeEntityList = findHouseTypeList(houseTypeIds);
			Map<String,HouseTypeEntity> houseTypeEntityMap =  houseTypeEntityList.stream().collect(Collectors.toMap(HouseTypeEntity::getFid, houseTypeEntity -> houseTypeEntity));

			Map<String, PaymentTermsDto> paymentTermsDtoMap = new HashMap<>();
			List<RentContractActivityEntity> actList = new ArrayList<>();
			for(RentContractEntity rentContractEntity : rentContractEntityList) {
				rentContractEntity.setConType(contractParamDto.getConType());
				rentContractEntity.setConCycleCode(contractParamDto.getConCycleCode());
				rentContractEntity.setConStartDate(contractParamDto.getConStartDate());
				rentContractEntity.setConEndDate(contractParamDto.getConEndDate());
				rentContractEntity.setConRentYear(contractParamDto.getConRentYear());
				String result = paymentServiceProxy.getPaymentDetailForPC(rentContractEntity.toJsonStr());
				DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(result);
				if (dto.getCode() == DataTransferObject.ERROR) {
					return DataTransferObjectBuilder.buildErrorJsonStr("查询账单失败!msg:" + dto.getMsg() +",contractId"  + rentContractEntity.getContractId());
				}
				LogUtil.info(LOGGER,"[getPaymentDetailForPC result]{}", result);
				PaymentTermsDto paymentTermsDto = dto.parseData("items", new TypeReference<PaymentTermsDto>() {

				});

				paymentTermsDtoMap.put(rentContractEntity.getContractId(), paymentTermsDto);
				actList.addAll(paymentTermsDto.getActList());
			}


			ContractCostResultDto contractCostResultDto = new ContractCostResultDtoBuilder()
					.buildContractRoomCostResultDtoList(rentContractEntityList, roomInfoEntityMap, houseTypeEntityMap, paymentTermsDtoMap)
					.buildActivityEntityList(actList)
					.buildContractCostResultDto()
					.returnResult();


			return DataTransferObjectBuilder.buildOkJsonStr(contractCostResultDto);
		} catch (Exception e) {
			LogUtil.error(LOGGER,"findWqyEpsContractPageInfo {} {} ", paramJson,e);
			return DataTransferObjectBuilder.buildErrorJsonStr("后台出现异常");
		}

	}

	/**
	 * 封装查询房间方法
	 * @author cuiyuhui
	 * @created
	 * @param
	 * @return
	 */
	private List<RoomInfoEntity> findRoomList(String roomIds) {
        String result = roomService.getRoomListByRoomIds(roomIds);
        DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(result);
        try {
            List<RoomInfoEntity> roomInfoEntityList = SOAResParseUtil.getListValueFromDataByKey(result, "roomInfoList", RoomInfoEntity.class);
            return roomInfoEntityList;
        } catch (SOAParseException e) {
            LogUtil.error(LOGGER,"findRoomList:{} {}", roomIds, e);
        }
        return null;
    }

    private List<HouseTypeEntity> findHouseTypeList(String houseTypeIds) {
        String result  = houseTypeService.findHouseTypeListByIds(houseTypeIds);
        DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(result);
        try {
            List<HouseTypeEntity> houseTypeEntityList = SOAResParseUtil.getListValueFromDataByKey(result, "data", HouseTypeEntity.class);
            return houseTypeEntityList;
        } catch (SOAParseException e) {
            LogUtil.error(LOGGER,"findHouseTypeList" +houseTypeIds,e);
        }
        return null;
    }

    @Override
    public String rollBackContractInfoForZramsSubmit(String paramJson) {
        LogUtil.info(LOGGER, "【rollBackContractInfoForZramsSubmit】入参:{}", paramJson);
        DataTransferObject dto = new DataTransferObject();
        try {
            RentContractEntity rentContractEntity = JsonEntityTransform.json2Entity(paramJson,RentContractEntity.class);
            if(Check.NuNObj(rentContractEntity)){
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("参数为空");
                return dto.toJsonString();
            }
            rentContractServiceImpl.rollBackContractInfoForZramsSubmit(rentContractEntity);
        } catch (Exception e) {
            LogUtil.error(LOGGER, "【rollBackContractInfoForZramsSubmit】 error:{},param={}", e, paramJson);
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("系统错误");
        }
        return dto.toJsonString();
    }

    @Override
    public String updateBaseContractById(String entityStr) {
        LogUtil.info(LOGGER,"【updateBaseContractById】入参={}",entityStr);
        DataTransferObject dto = new DataTransferObject();
        RentContractEntity rentContractEntity = JsonEntityTransform.json2Entity(entityStr, RentContractEntity.class);
        if (Check.NuNObj(rentContractEntity)){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("参数为空");
            return dto.toJsonString();
        }
        int i = rentContractServiceImpl.updateBaseContractById(rentContractEntity);
        if(i>0){
        	return dto.toJsonString();
        }else{
        	dto.setErrCode(DataTransferObject.ERROR);
        	dto.setMsg("更新合同失败");
        	return dto.toJsonString();
        }

    }



    /**
     * 查询存在的有效的签约邀请生成的合同
     * @author cuiyuhui
     * @created
     * @param
     * @return
     */
    @Override
    public String findWqyContractInviteByRoomIds(String paramJson) {
        LogUtil.info(LOGGER,"【findWqyContractInviteByRoomIds】入参={}",paramJson);
        try {
			if (Check.NuNStrStrict(paramJson)) {
				return DataTransferObjectBuilder.buildErrorJsonStr("findWqyContractInviteByRoomIds参数为空");
			}
			List<String> roomIdList = Arrays.asList(paramJson.split(","));
			if (roomIdList == null || roomIdList.isEmpty()) {
				return DataTransferObjectBuilder.buildErrorJsonStr("findWqyContractInviteByRoomIds无法拆分房间");
			}

            List<RentContractEntity> rentContractEntityList = this.rentContractServiceImpl.findWqyContractInviteByRoomIds(roomIdList);
            return DataTransferObjectBuilder.buildOkJsonStr(rentContractEntityList);
        } catch (Exception e) {
            LogUtil.error(LOGGER,"findWqyContractInviteByRoomIds:" + paramJson, e);
			return DataTransferObjectBuilder.buildErrorJsonStr("查询出错,错误原因异常");
        }
    }

	/**
	 * 判断是否可以续约(个人和企业通用)
	 *
	 * @param conRentCode 合同号
	 * @param billType    账单类型
	 * @return
	 * @author cuigh6
	 * @Date 2017年10月
	 */
	@Override
    public String isExistNotFinishedBill(String conRentCode,String billType) {
        LogUtil.info(LOGGER, "【verifyCanRenew】参数:conRentCode={}", conRentCode);
        DataTransferObject dto = new DataTransferObject();
        List<ReceiptBillResponse> receivableBillList = callFinanceServiceProxy.getBillListByType(conRentCode, billType, null);
        for (ReceiptBillResponse r : receivableBillList) {
	        if (r.getVerificateStatus() != 1) {// 1 为已收款
		        dto.putValue("data", r.getBillNum());
		        dto.setErrCode(100);// 状态码为100 则 有未完成的应收账单
		        dto.setMsg("您需要支付待缴生活费用后支付房租");
		        return dto.toJsonString();
	        }
        }

        return dto.toJsonString();
    }

    /**
	 * <p>批量保存解约协议及更新合同中的解约申请日期</p>
	 * @author xiangb
	 * @created 2017年11月2日
	 */
	@Override
	public String saveSurrenderAndUpdateRentContract(String param) {
		 LogUtil.info(LOGGER, "【saveSurrenderAndUpdateRentContract】参数:{}", param);
	     try{
	    	 List<SurrenderEntity> surrenders = JsonEntityTransform.json2List(param, SurrenderEntity.class);
	    	 rentContractServiceImpl.saveSurrenderAndUpdateRentContract(surrenders);
	    	 return DataTransferObjectBuilder.buildOkJsonStr(null);
	     }catch(Exception e){
	    	 LogUtil.info(LOGGER, "【saveSurrenderAndUpdateRentContract】出错:{}", e);
	    	 return DataTransferObjectBuilder.buildErrorJsonStr("保存失败");
	     }
	}
	/**
	 * <p>查询失效合同列表</p>
	 * @author xiangb
	 * @created 2017年11月5日
	 */
	@Override
    public String findInvalidContractList(String uid){
        LogUtil.info(LOGGER, "【findInvalidContractList】入参:{}", uid);
        DataTransferObject dto = new DataTransferObject();
        if(Check.NuNStr(uid)){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("参数为空！");
            return dto.toJsonString();
        }
        try{
            List<RentContractListVo> rentContractList = new ArrayList<>();
            List<RentContractAndDetailEntity> contracts = rentContractServiceImpl.listContractAndDetailByUid(uid);
            LogUtil.info(LOGGER, "【findInvalidContractList】查询合同列表返回:{}", JSONObject.toJSONString(contracts));
            //获取已关闭，已续租到期，已退租到期的合同,履行中已到期的合同
            contracts = rentContractLogic.sortInvalidContractList(contracts);
            if(Check.NuNCollection(contracts)){
            	return dto.toJsonString();
            }
            for(RentContractAndDetailEntity rentContractEntity:contracts){
                RentContractListVo rentContractListVo = new RentContractListVo();
                rentContractListVo.setContractId(rentContractEntity.getContractId());
                if(Check.NuNStr(rentContractEntity.getConRentCode())){
                	rentContractListVo.setContractCode(ContractMsgConstant.RENT_HAS_CLOSED_CARE_INFO);
                }else{
                	rentContractListVo.setContractCode(rentContractEntity.getConRentCode());
                }
                ConstatusShowEnum showEnum = rentContractLogic.getConstatusShowEnum(rentContractEntity.getConStatusCode(), rentContractEntity.getConAuditState(),rentContractEntity.getDeliveryState());
               //特殊状态的
                if(ContractStatusEnum.YGB.getStatus().equals(rentContractEntity.getConStatusCode())){
                	if(!Check.NuNObj(rentContractEntity.getCloseType())){
                		if(rentContractEntity.getCloseType() == ContractCloseTypeEnum.ZO_CLOSE.getCode()){
                    		continue;//管家关闭的合同前台不显示。
                    	}
                	}
                }else if(ContractStatusEnum.YDQ.getStatus().equals(rentContractEntity.getConStatusCode())){
                	if(!(ContractIsRenewEnum.YXY.getCode() == rentContractEntity.getIsRenew())){
                		continue;//非续约已到期的显示在有效合同列表
                	}
                }
                //合同状态赋值
                if(!Check.NuNObj(showEnum)){
                    rentContractListVo.setConstatus(showEnum.getName());
                    rentContractListVo.setConstatusCode(showEnum.getCode());
                }
                rentContractLogic.buildRentContractListVo(rentContractListVo, rentContractEntity);
                rentContractList.add(rentContractListVo);
            }
            LogUtil.info(LOGGER, "【findInvalidContractList】出参:{}", JsonEntityTransform.Object2Json(rentContractList));
            dto.putValue("rentContractList", rentContractList);
            return dto.toJsonString();
        }catch(Exception e){
            LogUtil.info(LOGGER, "【findInvalidContractList】出错:{}", e);
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("系统异常！");
            return dto.toJsonString();
        }
    }
	/**
	 *
	 * 根据父合同id查询 RentContractEntity 列表
	 * @author cuiyuhui
	 * @created
	 * @param
	 * @return
	 */
	@Override
	public String findRentContractListByParentId(String surParentRentId) {
		LogUtil.info(LOGGER, "【findRentContractListByParentId】入参:{}", surParentRentId);
		try {
			if (Check.NuNStrStrict(surParentRentId)) {
				return DataTransferObjectBuilder.buildErrorJsonStr("参数为空");
			}
			List<RentContractEntity> rentContractEntityList  = this.rentContractServiceImpl.findContractListByParentId(surParentRentId);
			return DataTransferObjectBuilder.buildOkJsonStr(rentContractEntityList);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "surParentRentId :{}", surParentRentId, e);
			return DataTransferObjectBuilder.buildErrorJsonStr("系统异常  findRentContractListByParentId surParentRentId :" + surParentRentId);
		}

	}
	/**
	  * @description: 根据父合同号批量查询合同号
	  * @author: lusp
	  * @date: 2017/11/25 下午 17:40
	  * @params: parentCode
	  * @return: String
	  */
	@Override
	public String getBatchContractCodeByParentCode(String parentCode) {
		LogUtil.info(LOGGER, "【getBatchContractCodeByParentCode】入参:{}", parentCode);
		DataTransferObject dto = new DataTransferObject();
		try {
			if(Check.NuNObj(parentCode)){
				LogUtil.error(LOGGER, "【getBatchContractCodeByParentCode】 参数为空");
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("参数为空");
				return dto.toJsonString();
			}
			List<String> conRentCodes = rentContractServiceImpl.getBatchContractCodeByParentCode(parentCode);
			dto.putValue("conRentCodes",conRentCodes);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "【getBatchContractCodeByParentCode】 error:{},parentCode={}", e, parentCode);
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("系统错误");
		}
		return dto.toJsonString();
	}

	@Override
	public String getCodesByParentCodeOnCondition(String parentCode) {
		LogUtil.info(LOGGER, "【getCodesByParentCodeOnCondition】入参:{}", parentCode);
		DataTransferObject dto = new DataTransferObject();
		try {
			if(Check.NuNObj(parentCode)){
				LogUtil.error(LOGGER, "【getCodesByParentCodeOnCondition】 参数为空");
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("参数为空");
				return dto.toJsonString();
			}
			List<String> conRentCodes = null;
			//@Author:lusp  @Date:2018/1/3  增加企业老数据兼容，企业老数据合同号需是主合同号+房间号
			RentContractEntity parentRentContractEntity = rentContractServiceImpl.findValidContractByRentCode(parentCode);
			if(!Check.NuNObj(parentRentContractEntity)&&parentRentContractEntity.getCustomerType()==CustomerTypeEnum.ENTERPRICE.getCode()&&parentRentContractEntity.getDataVersion()==0){
				List<RentDetailEntity> rentDetailEntities = rentContractServiceImpl.findRentDetailByContractId(parentRentContractEntity.getContractId());
				if(Check.NuNCollection(rentDetailEntities)){
					LogUtil.error(LOGGER, "【getCodesByParentCodeOnCondition】 企业合同老数据对应合同明细为空");
					dto.setErrCode(DataTransferObject.ERROR);
					dto.setMsg("参数为空");
					return dto.toJsonString();
				}
				for(RentDetailEntity rentDetailEntity:rentDetailEntities){
					conRentCodes.add(parentRentContractEntity.getContractId()+"+"+rentDetailEntity.getRoomCode());
				}
			}else{
				conRentCodes = rentContractServiceImpl.getCodesByParentCodeOnCondition(parentCode);
			}
			dto.putValue("conRentCodes",conRentCodes);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "【getCodesByParentCodeOnCondition】 error:{},parentCode={}", e, parentCode);
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("系统错误");
		}
		return dto.toJsonString();
	}

	/**
	 * 获取合同参与的活动
	 * @param contractId 合同标识
	 * @return
	 * @author cuigh6
	 * @Date 2017年11月
	 */
	public String getContractActivityList(String contractId) {
		LogUtil.info(LOGGER, "【getContractActivityList】入参:contractId={}", contractId);
		DataTransferObject dto = new DataTransferObject();
		if (Check.NuNStr(contractId)) {
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("合同标识不能为空");
			return dto.toJsonString();
		}

		List<RentContractActivityEntity> contractActivityList = this.rentContractServiceImpl.getContractActivityList(contractId);
		dto.putValue("list", contractActivityList);
		return dto.toJsonString();
	}

	/**
	 * 查询合同列表 根据page
	 * @author jixd
	 * @created 2017年11月27日 18:36:03
	 * @param
	 * @return
	 */
	@Override
	public String listContractByPage(String param){
		LogUtil.info(LOGGER,"【listContractByPage】参数 param={}",param);
		DataTransferObject dto = new DataTransferObject();
		if (Check.NuNStr(param)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("参数为空");
			return dto.toJsonString();
		}
		try{
			ContractSearchPageDto contractSearchPageDto = JsonEntityTransform.json2Object(param, ContractSearchPageDto.class);
			PagingResult<ContractManageDto> pagingResult = rentContractServiceImpl.listContractByPage(contractSearchPageDto);
			List<ContractManageDto> rows = pagingResult.getRows();
			//来源查询合同列表   否则直接返回查询数据
			if (contractSearchPageDto.getOper() == 0){
				//查看是否已同步财务收款数据数据
				for (ContractManageDto contractManageDto : rows){
					String conStatusCode = contractManageDto.getConStatusCode();
					Integer customerType = contractManageDto.getCustomerType();
					String fsource = contractManageDto.getFsource();
					int isFinishBill = 1;//0未完成 1已完成核销
					int isFinishRecei = 0;
					if(!Check.NuNStr(conStatusCode) && !Check.NuNObj(customerType) && !Check.NuNObj(fsource) && fsource.equals(String.valueOf(ContractSourceEnum.ZRAMS.getCode()))){
						if ((conStatusCode.equals(ContractStatusEnum.DZF.getStatus()) && customerType == CustomerTypeEnum.PERSON.getCode() && contractManageDto.getIsPossibleModify() == ContractIsModifyEnum.BKXG.getCode())
								|| (conStatusCode.equals(ContractStatusEnum.DZF.getStatus()) && customerType == CustomerTypeEnum.ENTERPRICE.getCode())){
								ReceiptBillRequest receiptBillRequest = new ReceiptBillRequest();
								receiptBillRequest.setOutContractCode(contractManageDto.getConRentCode());
							Map<String, Object> receivableMap = financeBaseCall.getReceivableBillInfo(receiptBillRequest);
							List<ReceiptBillResponse> listBill = (List<ReceiptBillResponse>) receivableMap.get("listStr");
							if (Check.NuNCollection(listBill)){
								isFinishBill = 0;
							}else{
								// 过滤第一期的账单和 生活费用账单
								boolean fin = listBill.stream()
										.filter(v -> (v.getPeriods() == null && v.getDocumentType().equals(DocumentTypeEnum.LIFE_FEE.getCode()) || v.getPeriods() == 1))
										.allMatch(b -> b.getVerificateStatus() == VerificateStatusEnum.DONE.getCode());
								if (!fin){
									isFinishBill = 0;
								}
							}
							contractManageDto.setIsFinishBill(isFinishBill);
						}

						if (conStatusCode.equals(ContractStatusEnum.DZF.getStatus()) && customerType == CustomerTypeEnum.ENTERPRICE.getCode()){
							//查看收款问题
							List<FinReceiBillDetailEntity> finReceiBills = receiBillDetailServiceImpl.listFinReceiBillDetailByContractId(contractManageDto.getContractId(), DocumentTypeEnum.RENT_FEE.getCode(), 1);
							List<FinReceiBillDetailEntity> lifeReceiBill = receiBillDetailServiceImpl.listFinReceiBillDetailByContractId(contractManageDto.getContractId(), DocumentTypeEnum.LIFE_FEE.getCode(), null);
							if (!Check.NuNCollection(finReceiBills) && !Check.NuNCollection(lifeReceiBill)){
								finReceiBills.addAll(lifeReceiBill);
							}
							boolean b = finReceiBills.stream().allMatch(p -> (!Check.NuNObj(p.getStatus()) && p.getStatus() == SyncToFinEnum.SUCCESS.getCode()));
							if (b){
								isFinishRecei = 1;
							}
							LogUtil.info(LOGGER,"查看是否同步财务数据完成 b={}",b);

						}
					}
					contractManageDto.setIsFinishRecei(isFinishRecei);
				}
			}else if (contractSearchPageDto.getOper() == 1){
				for (ContractManageDto contractManageDto : rows){
					//导出数据带活动列表
					List<RentContractActivityEntity> contractActivityList = rentContractActivityServiceImpl.getContractActivityList(contractManageDto.getContractId());
					if (!Check.NuNCollection(contractActivityList)){
						String actNames = contractActivityList.stream().map(RentContractActivityEntity::getActivityName).collect(Collectors.joining(","));
						contractManageDto.setActNames(actNames);
					}
				}
			}
			dto.putValue("count",pagingResult.getTotal());
			dto.putValue("list",rows);
		}catch (Exception e){
			LogUtil.error(LOGGER,"error={}",e);
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("服务错误");
		}
		return dto.toJsonString();

	}
	/**
	  * @description: 客户签字，修改合同状态为已签约，如果为续约合同，修改签合同状态为已续约，修改房间状态为已出租
	  * @author: lusp
	  * @date: 2017/12/5 下午 16:03
	  * @params: contractId
	  * @return: String
	  */
	@Override
	public String customerSignatureForMs(String contractId) {
		LogUtil.info(LOGGER, "【customerSignatureForMs】入参contractId:{}", contractId);
		DataTransferObject dto = new DataTransferObject();
		try {
			if(Check.NuNStrStrict(contractId)){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("参数为空");
				return dto.toJsonString();
			}
			RentContractEntity rentContractEntity = rentContractServiceImpl.findContractBaseByContractId(contractId);
			if(Check.NuNObj(rentContractEntity)){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("合同实体不存在");
				return dto.toJsonString();
			}
			int num = 0;
			RentContractEntity updateRentContractEntity = new RentContractEntity();
			if(rentContractEntity.getFsigntype().equals(ContractSignTypeEnum.RENEW.getValue())){
				//修改合同状态为已签约和前合同状态为已续约
				num = rentContractServiceImpl.updateContractAndPreStatus(contractId,rentContractEntity.getRoomId(),rentContractEntity.getPreConRentCode());
				this.waterClearingLogic.clearDelayRenewContract(rentContractEntity);

			}else{
				updateRentContractEntity.setContractId(contractId);
				updateRentContractEntity.setConStatusCode(ContractStatusEnum.YQY.getStatus());
				updateRentContractEntity.setPreConStatusCode(ContractStatusEnum.DZF.getStatus());
				updateRentContractEntity.setFirstPayTime(new Date());
				updateRentContractEntity.setConSignDate(new Date());
				num = rentContractServiceImpl.updateBaseContractById(updateRentContractEntity);//修改合同状态为已签约

                // 清算水费流程
                waterClearingLogic.clearNewContract(rentContractEntity);

			}
			if(num!=1){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("非法操作");
				return dto.toJsonString();
			}
			//修改房间状态为已出租
			RoomInfoEntity roomInfoEntity = new RoomInfoEntity();
			roomInfoEntity.setFid(rentContractEntity.getRoomId());
			roomInfoEntity.setFcurrentstate(com.ziroom.zrp.service.houses.valenum.RoomStatusEnum.YCZ.getCode());
			String roomResultJson = roomService.updateRoomInfo(JsonEntityTransform.Object2Json(roomInfoEntity));
			dto = JsonEntityTransform.json2DataTransferObject(roomResultJson);
			Integer affect = SOAResParseUtil.getIntFromDataByKey(roomResultJson,"affect");
			if(DataTransferObject.ERROR==dto.getCode()||Check.NuNObj(affect)||affect!=1){
				//回滚合同状态
				if(rentContractEntity.getFsigntype().equals(ContractSignTypeEnum.RENEW.getValue())){
					rentContractServiceImpl.rollBackContractAndPreStatus(contractId,rentContractEntity.getPreConRentCode(),rentContractEntity.getRoomId());
				}else{
					updateRentContractEntity.setContractId(rentContractEntity.getContractId());
					updateRentContractEntity.setConStatusCode(ContractStatusEnum.DZF.getStatus());
					updateRentContractEntity.setPreConStatusCode(ContractStatusEnum.YQY.getStatus());
//					updateRentContractEntity.setFirstPayTime(null);
//					updateRentContractEntity.setConSignDate(null);
					rentContractServiceImpl.updateBaseContractById(updateRentContractEntity);
				}
				LogUtil.error(LOGGER, "【updateYqyAndRoomYczByContractId】 contractId={},error:{}",contractId, dto.toJsonString());
				return dto.toJsonString();
			}
			// 查询房间信息
			String roomInfo = this.roomService.getRoomByFid(rentContractEntity.getRoomId());
			DataTransferObject roomObj = JsonEntityTransform.json2DataTransferObject(roomInfo);
			if (roomObj.getCode()== DataTransferObject.ERROR) {
				LogUtil.error(LOGGER, "查询房间信息报错");
			}
			RoomInfoEntity room = roomObj.parseData("roomInfo", new TypeReference<RoomInfoEntity>() {
			});
			//复制前合同的物业交割信息
			if(ContractSignTypeEnum.RENEW.getValue().equals(rentContractEntity.getFsigntype())&&ContractSourceEnum.APP.getCode()==Integer.valueOf(rentContractEntity.getFsource())){
				int count = rentItemDeliveryServiceImpl.copyPreContractDeliveryInfo(rentContractEntity.getContractId());
				LogUtil.info(LOGGER, "【updateYqyAndRoomYczByContractId】复制前合同物业交割信息返回：{}", count);
				if(count <= 0){
					//回滚合同状态
					rentContractServiceImpl.rollBackContractAndPreStatus(contractId,rentContractEntity.getPreConRentCode(),rentContractEntity.getRoomId());
					LogUtil.error(LOGGER, "【updateYqyAndRoomYczByContractId】复制前合同物业交割信息失败，contractId:{}",contractId);
					dto.setErrCode(DataTransferObject.ERROR);
					dto.setMsg("系统异常！");
					return dto.toJsonString();
				}
			}
			//智能锁
			if (ContractSignTypeEnum.RENEW.getValue().equals(rentContractEntity.getFsigntype())) {
				if (room.getIsBindLock() == RoomSmartLockBindEnum.YBD.getCode() && smartLockLogic.isOpenSmartLock()) {
					// 调用智能锁接口 延长密码有效期
					this.smartLockLogic.renewAddSmartLockPwd(rentContractEntity);
				}
			}else{
				if (room.getIsBindLock() == RoomSmartLockBindEnum.YBD.getCode() && smartLockLogic.isOpenSmartLock()) {
					// 调用智能锁接口 新增密码
					this.smartLockLogic.addSmartLockPwd(rentContractEntity, room);
				}
			}

			dto.putValue("conRentCode",rentContractEntity.getConRentCode());
		}catch (Exception e){
			LogUtil.error(LOGGER, "【updateYqyAndRoomYczByContractId】 contractId={},error:{}", contractId, e);
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("系统错误");
		}
		return dto.toJsonString();
	}


	//查询应收账单，给合同操作按钮赋值 xiangb 2017年12月24日
  	public void isNeedPayUtil(RentContractEntity rentContractEntity,RentContractDetailDto contractDto, RentBillInfoVo billInfo){
  		if(ContractStatusEnum.DZF.getStatus() != rentContractEntity.getConStatusCode()){
  			//已关闭，已作废，已退租的合同不显示支付
  			if(ContractStatusEnum.YGB.getStatus().equals(rentContractEntity.getConStatusCode())
  					|| ContractStatusEnum.YTZ.getStatus().equals(rentContractEntity.getConStatusCode())
  				    || ContractStatusEnum.YZF.getStatus().equals(rentContractEntity.getConStatusCode())){
  				return;
  			}
  			//查询待支付账单
  	        String waitForPayStr = findWaitforPaymentList(rentContractEntity.getConRentCode());
  	        LogUtil.info(LOGGER, "【findContractByContractId】查询待支付账单：{}", waitForPayStr);
  	        DataTransferObject waitForPayObj = JsonEntityTransform.json2DataTransferObject(waitForPayStr);
  	        int liftCount = 0;
  	        int payCount = 0;
  	        if(waitForPayObj.getCode() == DataTransferObject.SUCCESS){
  	        	if (!Check.NuNObj(waitForPayObj.getData().get("liftCount"))) {
  					liftCount = (int)waitForPayObj.getData().get("liftCount");
  				}
  	        	if (!Check.NuNObj(waitForPayObj.getData().get("allCount"))) {
  					payCount = (int)waitForPayObj.getData().get("allCount");
  				}

  	        }
  	        //是否有待支付合同
  			if(payCount > liftCount && liftCount > 0){//既有房租又有生活费用
  				billInfo.setDesc(String.format(ContractMsgConstant.RENT_DZF_CARE_INFO,liftCount+1));
  	        	billInfo.setColor(ContractValueConstant.COLOR_OF_ORANGER);//颜色值（深橙色）
  				contractDto.setOperation(OperationEnum.ZFLB_SH.getName());
  	            contractDto.setOperationCode(OperationEnum.ZFLB_SH.getCode());
  			}else if(payCount == liftCount && liftCount > 0){//只有生活费用
  				billInfo.setDesc(String.format(ContractMsgConstant.RENT_DZF_CARE_INFO,liftCount));
  	        	billInfo.setColor(ContractValueConstant.COLOR_OF_ORANGER);//颜色值（深橙色）
  				contractDto.setOperation(OperationEnum.ZFLB_SH.getName());
  	            contractDto.setOperationCode(OperationEnum.ZFLB_SH.getCode());
  			}else if(payCount > 0 && liftCount == 0){//只有房租
  				billInfo.setDesc(String.format(ContractMsgConstant.RENT_DZF_CARE_INFO,1));
  	        	billInfo.setColor(ContractValueConstant.COLOR_OF_ORANGER);//颜色值（深橙色）
  				contractDto.setOperation(OperationEnum.ZFLB_FZ.getName());
  	            contractDto.setOperationCode(OperationEnum.ZFLB_FZ.getCode());
  			}else if(payCount ==0){//无费用项
  				if(ContractStatusEnum.YQY.getStatus().equals(rentContractEntity.getConStatusCode())){//履约中
  					Date endDate = rentContractEntity.getConEndDate();
  					if(!Check.NuNObj(endDate)){
  						int days = DateUtil.getDatebetweenOfDayNum(DateUtilFormate.formatStringToDate(DateUtilFormate.formatDateToString(new Date(), DateUtilFormate.DATEFORMAT_4), DateUtilFormate.DATEFORMAT_4), endDate);
  						if(days < ContractValueConstant.RENT_XZ_TIME_THIRTY && days >= ContractValueConstant.RENT_XZ_TIME_ZERO){//合同结束日期小于30天显示去续约
  							contractDto.setOperation(OperationEnum.XY.getName());//续约
  			                contractDto.setOperationCode(OperationEnum.XY.getCode());
  						}
  					}
  				}
  			}
  		}
  	}
	/**
	  * @description: 根据合同id查询合同信息以及物业交割状态
	  * @author: lusp
	  * @date: 2017/12/20 下午 17:24
	  * @params: contractId
	  * @return: String
	  */
	@Override
	public String findContractAndDetailsByContractId(String contractId) {
		LogUtil.info(LOGGER, "【findContractAndDetailsByContractId】contractId:{}", contractId);
		DataTransferObject dto = new DataTransferObject();
		try {
			if(Check.NuNStrStrict(contractId)){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("参数为空");
				return dto.toJsonString();
			}
			List<RentContractAndDetailEntity> rentContractAndDetailEntities = rentContractServiceImpl.findContractAndDetailsByContractId(contractId);
			dto.putValue("rentContractAndDetailEntities",rentContractAndDetailEntities);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "【findContractAndDetailsByContractId】 contractId={},error:{}", contractId, e);
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("系统错误");
		}
		return dto.toJsonString();
	}

    @Override
    public String finOldDataContractId(String contractId) {
        LogUtil.info(LOGGER, "【finOldDataContractId】contractId:{}", contractId);
        return rentContractServiceImpl.finOldDataContractId(contractId);
    }

	/**
	 * 查询原合同是否存在续约合同
	 * @param contractCode 合同号
	 * @return
	 * @author cuigh6
	 * @Date 2017年12月22日
	 */
	@Override
	public String findRenewContractByPreRentCode(String contractCode) {
		LogUtil.info(LOGGER, "【findRenewContractByPreRentCode】contractCode:{}", contractCode);
		DataTransferObject dto = new DataTransferObject();
		try {
			if(Check.NuNStrStrict(contractCode)){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("参数为空");
				return dto.toJsonString();
			}
			RentContractEntity rentContractEntity = rentContractServiceImpl.findRenewContractByPreRentCode(contractCode);
			dto.putValue("rentContractEntity",rentContractEntity);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "【findRenewContractByPreRentCode】 contractCode={},error:{}", contractCode, e);
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("系统错误");
		}
		return dto.toJsonString();
	}

	@Override
	public String findContractListByParentCode(String surParentRentCode) {
		LogUtil.info(LOGGER, "【findContractListByParentCode】surParentRentCode:{}", surParentRentCode);
		DataTransferObject dto = new DataTransferObject();
		try {
			if(Check.NuNStrStrict(surParentRentCode)){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("参数为空");
				return dto.toJsonString();
			}
			List<RentContractEntity> rentContractEntities = rentContractServiceImpl.findContractListByParentCode(surParentRentCode);
			dto.putValue("rentContractEntities",rentContractEntities);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "【findRenewContractByPreRentCode】 e:{},surParentRentCode={}", e, surParentRentCode);
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("系统错误");
		}
		return dto.toJsonString();
	}

	/**
	 *  查询当前房间的最近的合同
	 * @author yd
	 * @created
	 * @param
	 * @return
	 */
	@Override
	public String findCurrentContract(String paramsJson) {
		LogUtil.info(LOGGER, "【findCurrentContract】paramsJson:{}", paramsJson);
		DataTransferObject dto = new DataTransferObject();
		try {

			RoomStmartDto roomStmartDto = JsonEntityTransform.json2Object(paramsJson,RoomStmartDto.class);
			if(Check.NuNStr(roomStmartDto.getRoomId())&&Check.NuNStr(roomStmartDto.getUserName())){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("参数为空");
				return dto.toJsonString();
			}
			List<RoomContractSmartVo> listRoomContractSmartVo = rentContractServiceImpl.findCurrentContract(roomStmartDto);
			dto.putValue("listRoomContractSmartVo",listRoomContractSmartVo);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "【findCurrentContract】 e:{},paramsJson={}", e, paramsJson);
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("系统错误");
		}
		return dto.toJsonString();
	}

	/**
	 * 续约延长房间智能锁密码
	 * @param paramJson
	 * @return
	 * @author cuigh6
	 * @Date 2018年1月26日
	 */
	@Override
	public String renewAddSmartLock(String paramJson) {
		LogUtil.info(LOGGER, "【renewAddSmartLock】paramsJson:{}", paramJson);
		DataTransferObject dto = new DataTransferObject();
		try {
			RentContractEntity rentContractEntity = JsonEntityTransform.json2Entity(paramJson, RentContractEntity.class);
			this.smartLockLogic.renewAddSmartLockPwd(rentContractEntity);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "【renewAddSmartLockPwd】, paramsJson={},e:{}", paramJson, e);
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("系统错误");
		}
		return dto.toJsonString();
	}

	/**
	 * 查询房间当前有效的合同列表
	 * @param roomId 房间标识
	 * @return
	 * @author cuigh6
	 * @Date 2018年2月8日
	 */
	@Override
	public String getRoomValidContractList(String roomId) {
		LogUtil.info(LOGGER, "【getRoomValidContractList】roomId={}", roomId);
		DataTransferObject dto = new DataTransferObject();
		try {
			List<RentContractEntity> roomValidContractList = this.rentContractLogic.getRoomValidContractList(roomId);
			dto.putValue("contractList", roomValidContractList);
			return dto.toJsonString();
		} catch (Exception e) {
			LogUtil.error(LOGGER, "【getRoomValidContractList】查询有效的合同列表 roomId={}", roomId, e);
			dto.setMsg("查询有效合同异常");
			dto.setErrCode(DataTransferObject.ERROR);
		}
		return dto.toJsonString();
	}

	/**
	 * <p>根据合同号查询有效合同实体信息（对于旧数据合同号相同，有关闭合同的情况）</p>
	 * @author xiangb
	 * @created 2018年1月15日
	 * @param
	 * @return
	 */
    @Override
    public String findValidContractByRentCode(String contractCode) {
        LogUtil.info(LOGGER, "【findValidContractByRentCode】参数={}", contractCode);
        DataTransferObject dto = new DataTransferObject();
        if (Check.NuNStr(contractCode)) {
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("参数为空");
            return dto.toJsonString();
        }
        try {
            RentContractEntity rentContractEntity = rentContractServiceImpl
                    .findValidContractByRentCode(contractCode);
            LogUtil.info(LOGGER, "【findValidContractByRentCode】查询合同详情返回={}",
                    JsonEntityTransform.Object2Json(rentContractEntity));
            if (Check.NuNObj(rentContractEntity)) {
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("未查询到合同信息！");
                return dto.toJsonString();
            }
            dto.putValue("rentContractEntity", rentContractEntity);
            return dto.toJsonString();
        }catch(Exception e){
            LogUtil.info(LOGGER, "【findValidContractByRentCode】异常:{}",e);
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("系统异常！");
            return dto.toJsonString();
        }
    }
}