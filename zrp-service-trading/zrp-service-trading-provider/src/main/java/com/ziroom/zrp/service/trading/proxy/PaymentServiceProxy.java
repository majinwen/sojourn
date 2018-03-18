package com.ziroom.zrp.service.trading.proxy;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.DateUtil;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.valenum.common.YesOrNoEnum;
import com.ziroom.zrp.houses.entity.ProjectEntity;
import com.ziroom.zrp.houses.entity.RoomInfoEntity;
import com.ziroom.zrp.service.houses.api.ProjectService;
import com.ziroom.zrp.service.houses.api.RoomService;
import com.ziroom.zrp.service.trading.api.PaymentService;
import com.ziroom.zrp.service.trading.dto.PaymentTermsDto;
import com.ziroom.zrp.service.trading.dto.RoomRentBillDto;
import com.ziroom.zrp.service.trading.dto.finance.ReceiptBillListRequestDto;
import com.ziroom.zrp.service.trading.dto.finance.ReceiptBillListResponseDto;
import com.ziroom.zrp.service.trading.dto.finance.ReceiptBillResponse;
import com.ziroom.zrp.service.trading.pojo.LeaseCyclePojo;
import com.ziroom.zrp.service.trading.proxy.commonlogic.FinanceCommonLogic;
import com.ziroom.zrp.service.trading.service.FinReceiBillDetailServiceImpl;
import com.ziroom.zrp.service.trading.service.FinReceiBillServiceImpl;
import com.ziroom.zrp.service.trading.service.IntellectWaterMeterBillLogServiceImpl;
import com.ziroom.zrp.service.trading.service.RentContractServiceImpl;
import com.ziroom.zrp.service.trading.utils.RentHeadInfoUtil;
import com.ziroom.zrp.service.trading.utils.factory.LeaseCycleFactory;
import com.ziroom.zrp.service.trading.valenum.*;
import com.ziroom.zrp.service.trading.valenum.finance.CostCodeEnum;
import com.ziroom.zrp.service.trading.valenum.finance.DocumentTypeEnum;
import com.ziroom.zrp.service.trading.valenum.finance.VerificateStatusEnum;
import com.ziroom.zrp.trading.entity.FinReceiBillDetailEntity;
import com.ziroom.zrp.trading.entity.IntellectWaterMeterBillLogEntity;
import com.ziroom.zrp.trading.entity.RentContractActivityEntity;
import com.ziroom.zrp.trading.entity.RentContractBillEntity;
import com.ziroom.zrp.trading.entity.RentContractEntity;
import com.zra.common.constant.BillMsgConstant;
import com.zra.common.constant.ContractMsgConstant;
import com.zra.common.constant.ContractValueConstant;
import com.zra.common.utils.DateTool;
import com.zra.common.utils.DateUtilFormate;
import com.zra.common.vo.base.BaseFieldColorVo;
import com.zra.common.vo.base.BaseFieldVo;
import com.zra.common.vo.bill.*;
import com.zra.common.vo.contract.ProjectInfoVo;
import com.zra.common.vo.pay.*;

import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>款项计算</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author cuigh6
 * @version 1.0
 * @Date Created in 2017年09月12日 15:45
 * @since 1.0
 */
@Component("trading.paymentServiceProxy")
public class PaymentServiceProxy implements PaymentService {

	private static final Logger LOGGER = LoggerFactory.getLogger(PaymentServiceProxy.class);

	@Resource(name = "trading.rentContractServiceImpl")
	private RentContractServiceImpl rentContractService;

	@Resource(name = "houses.roomService")
	private RoomService roomService;

	@Resource(name = "houses.projectService")
	private ProjectService projectService;

	@Resource(name = "trading.rentContractServiceImpl")
	private RentContractServiceImpl rentContractServiceImpl;

	@Resource(name = "trading.callFinanceServiceProxy")
	private CallFinanceServiceProxy callFinanceServiceProxy;

	@Resource(name = "trading.finReceiBillDetailServiceImpl")
	private FinReceiBillDetailServiceImpl receiBillDetailServiceImpl;

	@Resource(name="trading.finReceiBillServiceImpl")
	private FinReceiBillServiceImpl finReceiBillService;

	@Resource(name="trading.financeCommonLogic")
	private FinanceCommonLogic financeCommonLogic;
	
	@Resource(name="trading.intellectWaterMeterBillLogServiceImpl")
	private IntellectWaterMeterBillLogServiceImpl intellectWaterMeterBillLogService;

	@Value("#{'${pic_url}'.trim()}")
	private String picUrl;
	@Value("#{'${pay_overtime_condition_money}'.trim()}")
	private String payOverTimeConditionMoney;
	@Value("#{'${more_money_overtime_hours}'.trim()}")
	private String moreMoneyOvertimeHours;
	@Value("#{'${less_money_overtime_hours}'.trim()}")
	private String lessMoneyOvertimeHours;
	@Value("#{'${min_pay_amount}'.trim()}")
	private String minPayAmount;
	@Value("#{'${zra_pay_systemId}'.trim()}")
	private String zraPaySystemId;
	@Value("#{'${pic_url}'.trim()}")
	private String pic_url;

	@Override
	public String getPaymentDetail(String uid, String contractId) {
		LogUtil.info(LOGGER, "【getPaymentDetail】参数=uid:{},contractId:{}", uid, contractId);
		DataTransferObject dto = new DataTransferObject();
		if (Check.NuNStr(uid) || Check.NuNStr(contractId)) {
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("参数为空");
			return dto.toJsonString();
		}

		ProjectInfoVo projectInfoVo = new ProjectInfoVo();
		BillInfoVo billInfoVo = new BillInfoVo();
		PayInfoVo payInfoVo = new PayInfoVo();
		List<BaseFieldVo> costList = new ArrayList<>(4);
		ActivityInfoVo activityInfoVo = new ActivityInfoVo();

		// 项目信息组合
		RentContractEntity rentContractEntity = this.rentContractService.findContractBaseByContractId(contractId);

		ProjectEntity projectEntity;

		try {
			//查询签约信息
			String projectEntityStr = projectService.findProjectById(rentContractEntity.getProjectId());
			DataTransferObject projectObject = JsonEntityTransform.json2DataTransferObject(projectEntityStr);
			if (projectObject.getCode() == DataTransferObject.ERROR) {
				LogUtil.error(LOGGER, "【findProjectDetail】请求结果={}", projectEntityStr);
				dto.setMsg("系统错误");
				dto.setErrCode(DataTransferObject.ERROR);
				return dto.toJsonString();
			}
			projectEntity = projectObject.parseData("projectEntity", new TypeReference<ProjectEntity>() {
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
			//计算租金的静态类
			LeaseCyclePojo leaseCyclePojo = new LeaseCyclePojo(rentContractEntity.getConType(),rentContractEntity.getProjectId(),roomInfoEntity.getRentType());
			String roomSalesPrice = LeaseCycleFactory.createLeaseCycle(leaseCyclePojo).calculateActualRoomPrice(rentContractEntity.getConRentYear(), roomInfoEntity.getFlongprice());
			projectInfoVo.setRoomSalesPrice(roomSalesPrice);
			projectInfoVo.setProName(projectEntity.getFname());
			projectInfoVo.setProHeadFigureUrl(picUrl + projectEntity.getFHeadFigureUrl());
			projectInfoVo.setProAddress(projectEntity.getFaddress());
		} catch (Exception e) {
			LogUtil.error(LOGGER, "【findProjectDetail】 error:{},paramJson={}", e, rentContractEntity.getProjectId());
			dto.setMsg("系统错误");
			dto.setErrCode(DataTransferObject.ERROR);
			return dto.toJsonString();
		}

		dto.putValue("projectInfo", projectInfoVo);

		PaymentTermsDto paymentTermsDto = null;


		try {
			DataTransferObject result = financeCommonLogic.getPaymentItems(rentContractEntity);
			if (result.getCode() == DataTransferObject.ERROR) {
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg(result.getMsg());
				return dto.toJsonString();
			}
			paymentTermsDto = (PaymentTermsDto) result.getData().get("items");

			billInfoVo.setName(BillMsgConstant.BILL_ALL_TEXT);
			billInfoVo.setValue(BillMsgConstant.RMB + paymentTermsDto.getTotalMoney());
			billInfoVo.setDescription(BillMsgConstant.BILL_ALL_DESCRIPTION);
			dto.putValue("billInfo", billInfoVo);

			//支付信息组合  获取第一期账单
			List<RoomRentBillDto> roomRentBillDtos = paymentTermsDto.getRoomRentBillDtos();
			if (Check.NuNObj(roomRentBillDtos)) {
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("支付方式不支持,请重新选择");
				return dto.toJsonString();
			}
			RoomRentBillDto roomRentBillDto = roomRentBillDtos.stream().filter(t -> t.getPeriod() == 1).findFirst().orElse(new RoomRentBillDto());

			payInfoVo.setName(BillMsgConstant.BILL_FIRST_PAY_AMOUNT_TEXT);
			payInfoVo.setValue(BillMsgConstant.RMB + roomRentBillDto.getPeriodTotalMoney());

			BaseFieldVo costInfo = new BaseFieldVo();
			if (isDayLease(rentContractEntity.getConType())) {
				costInfo.setName(String.format(BillMsgConstant.BILL_RENT_PRICE_DAY_TEXT, roomRentBillDto.getRentPrice(), roomRentBillDto.getRentCount()));
			} else {
				costInfo.setName(String.format(BillMsgConstant.BILL_RENT_PRICE_Month_TEXT, roomRentBillDto.getRentPrice(), roomRentBillDto.getRentCount()));
			}
			costInfo.setValue(BillMsgConstant.RMB + roomRentBillDto.getPeriodTotalRentPrice().toString());
			costList.add(costInfo);

			if (rentContractEntity.getFsigntype().equals(ContractSignTypeEnum.RENEW.getValue())) {
				BaseFieldVo costInfo3 = new BaseFieldVo();
				costInfo3.setName(BillMsgConstant.BILL_DEPOSIT_TEXT);// 当前合同押金
				costInfo3.setValue(BillMsgConstant.RMB + roomRentBillDto.getDepositPrice().setScale(0,BigDecimal.ROUND_HALF_UP));
				BaseFieldVo costInfo4 = new BaseFieldVo();
				costInfo4.setName(BillMsgConstant.BILL_PRE_DEPOSIT_TEXT);// 原合同押金
				costInfo4.setValue("-" + BillMsgConstant.RMB + paymentTermsDto.getDepositPrice().setScale(0, BigDecimal.ROUND_HALF_UP));
				costList.add(costInfo3);
				costList.add(costInfo4);
			}else{
				BaseFieldVo costInfo1 = new BaseFieldVo();
				costInfo1.setName(BillMsgConstant.BILL_DEPOSIT_TEXT);// 当前合同押金
				costInfo1.setValue(BillMsgConstant.RMB + roomRentBillDto.getDepositPrice().toString());
				costList.add(costInfo1);
			}


			BaseFieldVo costInfo2 = new BaseFieldVo();
			costInfo2.setName(BillMsgConstant.BILL_SERVICE_TEXT);
			costInfo2.setValue(BillMsgConstant.RMB + paymentTermsDto.getOriginServicePrice().toString());
			costList.add(costInfo2);

			payInfoVo.setList(costList);
			dto.putValue("payInfo", payInfoVo);

			BaseFieldVo totalPayInfo = new BaseFieldVo();
			totalPayInfo.setName("总款");
			totalPayInfo.setValue(BillMsgConstant.RMB + roomRentBillDto.getPeriodTotalMoney());
			dto.putValue("totalPayInfo", totalPayInfo);


			List<RentContractActivityEntity> actList = paymentTermsDto.getActList();
			List<BaseFieldVo> baseFieldVos = new ArrayList<>();
			actList.forEach((RentContractActivityEntity a) -> {
				BaseFieldVo baseFieldVo = new BaseFieldVo();
				baseFieldVo.setName(a.getActivityName());
				baseFieldVo.setValue("-" + BillMsgConstant.RMB + BigDecimal.valueOf(a.getDiscountAccount()).setScale(0, BigDecimal.ROUND_HALF_UP));
				baseFieldVos.add(baseFieldVo);
			});
			if (!actList.isEmpty()) {
				activityInfoVo.setName("优惠活动");
			}
			activityInfoVo.setList(baseFieldVos);

			// 活动信息组合
			dto.putValue("activityInfo", activityInfoVo);

		} catch (Exception e) {
			LogUtil.error(LOGGER, "【getPaymentDetail】 error:{},paramJson={}", e, contractId);
			dto.setMsg(e.getMessage());
			dto.setErrCode(DataTransferObject.ERROR);
		}
		return dto.toJsonString();
	}

	@Override
	public String getPaymentDetailForPC(String paramJson) {
		LogUtil.info(LOGGER, "【getPaymentDetailForPC】参数:{}", paramJson);
		DataTransferObject dto = new DataTransferObject();
		RentContractEntity entity = JsonEntityTransform.json2Entity(paramJson, RentContractEntity.class);

		if (Check.NuNObj(entity)) {
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("参数为空");
			return dto.toJsonString();
		}
		if (Check.NuNStr(entity.getContractId())) {
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("合同标识为空");
			return dto.toJsonString();
		}
		if (Check.NuNStr(entity.getConType())) {
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("租赁方式为空");
			return dto.toJsonString();
		}
		if (Check.NuNStr(entity.getConCycleCode())) {
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("付款方式为空");
			return dto.toJsonString();
		}
		if (Check.NuNObj(entity.getConStartDate())) {
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("租赁开始日期为空");
			return dto.toJsonString();
		}
		if (Check.NuNObj(entity.getConEndDate())) {
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("租赁结束日期为空");
			return dto.toJsonString();
		}
		if (Check.NuNObj(entity.getConRentYear())) {
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("租赁时长ConRentYear为空");
			return dto.toJsonString();
		}

		try {
			// 项目信息组合
			RentContractEntity rentContractEntity = this.rentContractService.findContractBaseByContractId(entity.getContractId());

			rentContractEntity.setContractId(entity.getContractId());
			rentContractEntity.setConType(entity.getConType());
			rentContractEntity.setConCycleCode(entity.getConCycleCode());
			rentContractEntity.setConRentYear(entity.getConRentYear());
			rentContractEntity.setConStartDate(entity.getConStartDate());
			rentContractEntity.setConEndDate(entity.getConEndDate());

			dto = financeCommonLogic.getPaymentItems(rentContractEntity);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "【getPaymentDetailForPC】error:{}", e);
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg(e.getMessage());
		}

		return dto.toJsonString();
	}

	/**
	 * 查询账单支付成功详情
	 * <p>
	 *     1.查询合同详情
	 *     2.查询支付记录
	 * </p>
	 * @param contractId 合同标识
	 * @param period 期数
	 * @return json
	 * @author cuigh6
	 * @Date 2017年10月
	 */
	@Override
	public String findBillPayDetail(String contractId, Integer period) {
		LogUtil.info(LOGGER, "【findBillPayDetail】参数contractId={},period={}", contractId, period);
		DataTransferObject dto = new DataTransferObject();
		if (Check.NuNStr(contractId)) {
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("合同标识为空");
			return dto.toJsonString();
		}
		if (Check.NuNObj(period)) {
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("期数为空");
			return dto.toJsonString();
		}

		try {
			// 查询合同详情
			RentContractEntity rentContractEntity = rentContractServiceImpl.findContractBaseByContractId(contractId);

			DataTransferObject projectDto = JsonEntityTransform.json2DataTransferObject(projectService.findProjectById(rentContractEntity.getProjectId()));// 项目详情
			if (projectDto.getCode() == DataTransferObject.ERROR) {
				return projectDto.toJsonString();
			}
			ProjectEntity projectEntity = projectDto.parseData("projectEntity", new TypeReference<ProjectEntity>() {
			});
			ReceiptBillListRequestDto receiptBillListRequestDto = new ReceiptBillListRequestDto();
			receiptBillListRequestDto.setContractCode(rentContractEntity.getConRentCode());
			receiptBillListRequestDto.setPeriods(String.valueOf(period));

			String result = this.callFinanceServiceProxy.getReceiptBillListByContract(JsonEntityTransform.Object2Json(receiptBillListRequestDto));
			DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(result);
			if (resultDto.getCode() == DataTransferObject.ERROR) {
				return resultDto.toJsonString();
			}
			List<ReceiptBillListResponseDto> responseDtos = resultDto.parseData("list", new TypeReference<List<ReceiptBillListResponseDto>>() {
			});

			int totalAmount = 0;
			int i = 1;
			PayDetailVo payDetailVo = new PayDetailVo();
			PayNumDetailVo payNumDetailVo;
			List<PayNumDetailVo> list = new ArrayList<>();

			responseDtos.sort((o1, o2) -> { // 根据支付日期排序 正序
				if (DateUtilFormate.formatStringToDate(o1.getPayTime()).before(DateUtilFormate.formatStringToDate(o2.getPayTime()))) {
					return -1;
				}
				return 1;
			});

			for (ReceiptBillListResponseDto responseDto : responseDtos) {
				totalAmount += responseDto.getTotalAmount();
				payNumDetailVo = new PayNumDetailVo();
				payNumDetailVo.setPayAmount(String.format(BillMsgConstant.RMB_CHINESE, BigDecimal.valueOf((double) (responseDto.getTotalAmount() / 100)).setScale(0,BigDecimal.ROUND_HALF_UP)));
				payNumDetailVo.setPayNum(String.format("第%d次", i));
				if (responseDto.getReceiptStatus() == 0) {
					payNumDetailVo.setPayStatus("支付成功");
				}else{
					payNumDetailVo.setPayStatus("确认中");
				}
				payNumDetailVo.setPayDate(DateUtilFormate.formatDateToString(DateUtilFormate.formatStringToDate(responseDto.getPayTime()), DateUtilFormate.DATEFORMAT_4));
				list.add(payNumDetailVo);
				i++;
			}

			payDetailVo.setContractCode(rentContractEntity.getConRentCode());
			payDetailVo.setPeriod("第" + period + "期");
			payDetailVo.setReceivableAmount(String.format(BillMsgConstant.RMB_CHINESE, BigDecimal.valueOf(totalAmount).divide(BigDecimal.valueOf(100),0,BigDecimal.ROUND_HALF_UP)));
			payDetailVo.setReceivedAmount(String.format(BillMsgConstant.RMB_CHINESE, BigDecimal.valueOf(totalAmount).divide(BigDecimal.valueOf(100),0,BigDecimal.ROUND_HALF_UP)));
			payDetailVo.setReceivedNum(String.format("%d次", responseDtos.size()));
			payDetailVo.setProjectAddress(projectEntity.getFaddress());
			payDetailVo.setTips("您已支付完该期全部租金");
			payDetailVo.setList(list);
			dto.putValue("payDetail", payDetailVo);
		} catch (Exception e) {
			LogUtil.info(LOGGER, "【findBillPayDetail】查询账单支付详情报错:{}", e);
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("系统错误");
			return dto.toJsonString();
		}
		return dto.toJsonString();
	}


	/**
	 * 查询支付页面详情(包括房租支付和生活费用支付)
	 * <p>
	 * 1.查询合同
	 * 2.查询项目
	 * 3.查询应收账单信息(生活费用,房租,逾期违约金)
	 * 4.总款
	 * 5.已支付款
	 * 6.剩余款
	 * 7.本次支付
	 * </p>
	 *
	 * @param contractId 合同标识
	 * @param period     期数
	 * @param billType 账单类型
	 * @return json
	 * @author cuigh6
	 * @Date 2017年10月
	 */
	@Override
	public String findPayPageDetail(String contractId, Integer period, String billType) {
		LogUtil.info(LOGGER, "【findPayPageDetail】参数contractId={},period={},billType={}", contractId, period,billType);
		DataTransferObject dto = new DataTransferObject();
		if (Check.NuNStr(contractId)) {
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("合同标识为空");
			return dto.toJsonString();
		}

		/*初始化*/
		BillDetailVo billDetailVo = new BillDetailVo();
		ShouldPayDateVo shouldPayDateVo = null;

		BaseFieldColorVo baseFieldColorVo;
		List<BaseFieldColorVo> payCostItems = new ArrayList<>();
		PayCostItemsVo payCostItemsVo = new PayCostItemsVo();

		HadPayInfoVo havePayInfo = null;
		List<PayRecordVo> payNumList =null;
		BaseFieldVo leftPayInfo =null;
		NeedPayInfo needPayInfo = new NeedPayInfo();
		ProjectInfoVo projectInfoVo = null;
		StringJoiner billNumList = null;// 应收账单列表
		ActivityInfoVo activityInfoVo = null;

		try {
			/*查询合同信息*/
			RentContractEntity rentContractEntity = this.rentContractServiceImpl.findContractBaseByContractId(contractId);
			billDetailVo.setContractCode(rentContractEntity.getConRentCode());
			//判断合同状态
			if (isValidContractStatus(rentContractEntity)) {
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("合同状态不正确");
				return dto.toJsonString();
			}

            /*查询项目信息*/
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
			projectInfoVo = RentHeadInfoUtil.getRentHeadInfo(rentContractEntity, projectEntity, roomInfoEntity);
			/*查询应收账单信息*/
			billNumList = new StringJoiner(",");
			int totalMoney = 0;// 总款
			int leftMoney = 0; // 剩余金额
			if (DocumentTypeEnum.LIFE_FEE.getCode().equals(billType)) {// 查询生活费用账单
				// 查询生活费用账单
				List<ReceiptBillResponse> lifeReceiptBillList = this.
						callFinanceServiceProxy.getBillListByType(rentContractEntity.getConRentCode(), DocumentTypeEnum.LIFE_FEE.getCode(),null);
				//List<ReceiptBillResponse> lifeReceiptBillList = JsonEntityTransform.json2List("[ { \"billNum\":\"10012016081200001\", \"outContractCode\":\"BJ101160629066\", \"documentType\":\"1001\", \"periods\":1, \"costCode\":\"zrysf\", \"uid\":\"100219238483\", \"preCollectionDate\":\"2017-05-19\", \"billsycleStarttime\":\"2017-05-19\", \"billsycleEndtime\":\"2017-05-19\", \"verificateDate\":\"2017-05-19\", \"receiptBillAmount\":1200, \"receivedBillAmount\":1000, \"verificateStatus\":1 }, { \"billNum\":\"10012016081200001\", \"outContractCode\":\"BJ101160629066\", \"documentType\":\"1001\", \"periods\":1, \"costCode\":\"zrydf\", \"uid\":\"100219238483\", \"preCollectionDate\":\"2017-05-19\", \"billsycleStarttime\":\"2017-05-19\", \"billsycleEndtime\":\"2017-05-19\", \"verificateDate\":\"2017-05-19\", \"receiptBillAmount\":1200, \"receivedBillAmount\":1000, \"verificateStatus\":1 }]", ReceiptBillResponse.class);
				if (lifeReceiptBillList == null) {
					dto.setErrCode(DataTransferObject.ERROR);
					dto.setMsg("系统错误");
					return dto.toJsonString();
				}
				List<ReceiptBillResponse> notReceiptBillList = lifeReceiptBillList.stream().filter(v -> v.getVerificateStatus() != VerificateStatusEnum.DONE.getCode()).collect(Collectors.toList());

				for (ReceiptBillResponse v : notReceiptBillList) {
					billNumList.add(v.getBillNum());// 账单号列表 逗号隔开
					totalMoney += v.getReceiptBillAmount();
					baseFieldColorVo = new BaseFieldColorVo();
					CostCodeEnum costItem = CostCodeEnum.getByCode(v.getCostCode());
					baseFieldColorVo.setName(costItem == null ? "未知" : costItem.getName());// 费用项名称
					baseFieldColorVo.setValue(BillMsgConstant.RMB + BigDecimal.valueOf(v.getReceiptBillAmount()).divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_HALF_UP));// 金额
					baseFieldColorVo.setColor("#FF999999");// 颜色值
					payCostItems.add(baseFieldColorVo);
				}

				payCostItemsVo.setList(payCostItems);
				billDetailVo.setBillType(DocumentTypeEnum.LIFE_FEE.getCode());
				leftMoney = totalMoney;
			} else {// 查询房租账单(包括逾期违约金)
				if (Check.NuNObj(period)) {
					dto.setErrCode(DataTransferObject.ERROR);
					dto.setMsg("期数不能为空");
					return dto.toJsonString();
				}
				shouldPayDateVo = new ShouldPayDateVo();

				//查询房租账单
				List<ReceiptBillResponse> receivableBillList = this.
						callFinanceServiceProxy.getBillListByType(rentContractEntity.getConRentCode(), DocumentTypeEnum.RENT_FEE.getCode(), period);
				//List<ReceiptBillResponse> receivableBillList = JsonEntityTransform.json2List("[ { \"billNum\":\"10012016081200001\", \"outContractCode\":\"BJ101160629066\", \"documentType\":\"1001\", \"periods\":1, \"costCode\":\"khfz\", \"uid\":\"100219238483\", \"preCollectionDate\":\"2017-05-19\", \"billsycleStarttime\":\"2017-05-19\", \"billsycleEndtime\":\"2017-08-19\", \"verificateDate\":\"2017-05-19\", \"receiptBillAmount\":5000, \"receivedBillAmount\":1000, \"verificateStatus\":1 }, { \"billNum\":\"10012016081200002\", \"outContractCode\":\"BJ101160629066\", \"documentType\":\"1001\", \"periods\":1, \"costCode\":\"khfwf\", \"uid\":\"100219238483\", \"preCollectionDate\":\"2017-05-19\", \"billsycleStarttime\":\"2017-05-19\", \"billsycleEndtime\":\"2017-08-19\", \"verificateDate\":\"2017-05-19\", \"receiptBillAmount\":5000, \"receivedBillAmount\":1000, \"verificateStatus\":1 }]", ReceiptBillResponse.class);
				if (receivableBillList == null) {
					dto.setErrCode(DataTransferObject.ERROR);
					dto.setMsg("系统错误");
					return dto.toJsonString();
				}

				//查询逾期违约金账单
				List<ReceiptBillResponse> overdueReceiptBillList = this.
						callFinanceServiceProxy.getBillListByType(rentContractEntity.getConRentCode(), DocumentTypeEnum.OVERDUE_FEE.getCode(),period);
				//List<ReceiptBillResponse> overdueReceiptBillList = JsonEntityTransform.json2List("[]", ReceiptBillResponse.class);
				if (overdueReceiptBillList == null) {
					dto.setErrCode(DataTransferObject.ERROR);
					dto.setMsg("系统错误");
					return dto.toJsonString();
				}
				boolean hasServiceFee = false;
				for (ReceiptBillResponse v : receivableBillList) {
					billNumList.add(v.getBillNum());// 账单号列表 逗号隔开
					totalMoney += v.getReceiptBillAmount();
					leftMoney += v.getReceiptBillAmount() - v.getReceivedBillAmount();
					baseFieldColorVo = new BaseFieldColorVo();
					CostCodeEnum costItem = CostCodeEnum.getByCode(v.getCostCode());
					if (CostCodeEnum.KHFZ.getCode().equals(v.getCostCode())) {//租金特殊处理
						if (rentContractEntity.getConType().equals(LeaseCycleEnum.DAY.getCode())) {
							baseFieldColorVo.setName(String.format(BillMsgConstant.BILL_RENT_PRICE_DAY_TEXT,
									BigDecimal.valueOf(rentContractEntity.getFactualprice()).setScale(0, BigDecimal.ROUND_HALF_UP), rentContractEntity.getConRentYear()));
						} else {
							int month = DateTool.getMonthSpace(v.getBillsycleStarttime(), v.getBillsycleEndtime());
							baseFieldColorVo.setName(String.format(BillMsgConstant.BILL_RENT_PRICE_Month_TEXT,
									BigDecimal.valueOf(rentContractEntity.getFactualprice()).setScale(0, BigDecimal.ROUND_HALF_UP), month));
						}
					} else {
						baseFieldColorVo.setName(costItem == null ? "未知" : costItem.getName());// 费用项名称
					}
					// 如果为客户服务费 则直接取本地 原始服务费
					if(CostCodeEnum.KHFWF.getCode().equals(v.getCostCode())){
						hasServiceFee = true;
						baseFieldColorVo.setValue(BillMsgConstant.RMB + BigDecimal.valueOf(rentContractEntity.getConCommission()).setScale(0,BigDecimal.ROUND_HALF_UP));// 金额
					}else{
						baseFieldColorVo.setValue(BillMsgConstant.RMB + BigDecimal.valueOf(v.getReceiptBillAmount()).divide(BigDecimal.valueOf(100),0,BigDecimal.ROUND_HALF_UP));// 金额
					}
					baseFieldColorVo.setColor("#FF999999");// 颜色值
					payCostItems.add(baseFieldColorVo);
					shouldPayDateVo.setValue(v.getPreCollectionDate());//应缴费日期
				}

				if (period == 1 && !hasServiceFee) {
					baseFieldColorVo = new BaseFieldColorVo();
					baseFieldColorVo.setColor("#FF999999");// 颜色值
					baseFieldColorVo.setName(CostCodeEnum.KHFWF.getName());
					baseFieldColorVo.setValue(BillMsgConstant.RMB + BigDecimal.valueOf(rentContractEntity.getConCommission()).setScale(0,BigDecimal.ROUND_HALF_UP));// 金额
					payCostItems.add(baseFieldColorVo);
				}


				shouldPayDateVo.setPenaltyWarn(0);
                /*是否存在逾期违约金*/
				if (!overdueReceiptBillList.isEmpty()) {
					billNumList.add(overdueReceiptBillList.get(0).getBillNum());// 账单号列表 逗号隔开
					totalMoney += overdueReceiptBillList.get(0).getReceiptBillAmount();
					leftMoney += overdueReceiptBillList.get(0).getReceiptBillAmount() - overdueReceiptBillList.get(0).getReceivedBillAmount();
					baseFieldColorVo = new BaseFieldColorVo();
					CostCodeEnum costItem = CostCodeEnum.getByCode(overdueReceiptBillList.get(0).getCostCode());
					baseFieldColorVo.setName(costItem == null ? "未知" : costItem.getName());// 费用项名称
					baseFieldColorVo.setValue(BillMsgConstant.RMB + BigDecimal.valueOf((double) overdueReceiptBillList.get(0).getReceiptBillAmount() / 100).setScale(0, BigDecimal.ROUND_HALF_UP));// 金额
					baseFieldColorVo.setColor("#FFFC6C6C");// 颜色值
					payCostItems.add(baseFieldColorVo);
					shouldPayDateVo.setDesc(BillMsgConstant.BILL_OVERDUE_TIP_TEXT);
					shouldPayDateVo.setPenaltyWarn(1);// 1- 有警告标识 0 没有警告标识
				}
				shouldPayDateVo.setName(BillMsgConstant.BILL_SHOULD_PAY_DATE_TEXT);
				//已支付金额
				havePayInfo = new HadPayInfoVo();
				payNumList = new ArrayList<>();
				havePayInfo.setName(BillMsgConstant.BILL_HAVE_PAY_INFO_TEXT);
				havePayInfo.setValue("-" + BillMsgConstant.RMB + BigDecimal.valueOf((totalMoney - leftMoney)).divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_HALF_UP));

				ReceiptBillListRequestDto receiptBillListRequestDto = new ReceiptBillListRequestDto();
				receiptBillListRequestDto.setContractCode(rentContractEntity.getConRentCode());
				receiptBillListRequestDto.setPeriods(String.valueOf(period));
				receiptBillListRequestDto.setReceiptStatus("0");// 查询支付成功的收款单

				String result = this.callFinanceServiceProxy.getReceiptBillListByContract(JsonEntityTransform.Object2Json(receiptBillListRequestDto));
				DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(result);
				if (resultDto.getCode() == DataTransferObject.ERROR) {
					return resultDto.toJsonString();
				}
				List<ReceiptBillListResponseDto> responseDtos = resultDto.parseData("list", new TypeReference<List<ReceiptBillListResponseDto>>() {
				});
				//List<ReceiptBillListResponseDto> responseDtos = JsonEntityTransform.json2List("[ { \"outContractCode\": \"BJZYCW81706290014\", \"companyCode\": 5005, \"uid\": \"24d1fcef-0543-7aab-ba46-be629b235dfe\", \"paySerialNum\": \"88888888\", \"receiptNum\": \"BJZYCW817062900141498722614465\", \"receiptMothed\": \"xxzf\", \"payTime\": \"2017-06-29 00:00:00\", \"paymentTypeCode\": \"yhzz_0125\", \"remark\": \"\", \"payer\": \"王婷婷\", \"confirmStatus\": 1, \"totalAmount\": 1000, \"beatBackReason\": \"\", \"receiptStatus\": 0, \"annexList\": [ \"http://10.16.34.46:8080/group2/M00/07/99/ChAiLllcRNmAM4CUAAAoy3Pxx9o143.jpg\" ], \"receiptList\": [ { \"costCode\": \"khyj\", \"amount\": 296000, \"billNum\": \"10072017062901649\" }, { \"costCode\": \"khfwf\", \"amount\": 301900, \"billNum\": \"10072017062901648\" }, { \"costCode\": \"khfz\", \"amount\": 1776000, \"billNum\": \"10072017062901647\" } ] }, { \"outContractCode\": \"BJZYCW81706290014\", \"companyCode\": 5005, \"uid\": \"24d1fcef-0543-7aab-ba46-be629b235dfe\", \"paySerialNum\": \"88888888\", \"receiptNum\": \"BJZYCW817062900141498722614465\", \"receiptMothed\": \"xxzf\", \"payTime\": \"2017-06-29 00:00:00\", \"paymentTypeCode\": \"yhzz_0125\", \"remark\": \"\", \"payer\": \"王婷婷\", \"confirmStatus\": 1, \"totalAmount\": 1000, \"beatBackReason\": \"\", \"receiptStatus\": 0, \"annexList\": [ \"http://10.16.34.46:8080/group2/M00/07/99/ChAiLllcRNmAM4CUAAAoy3Pxx9o143.jpg\" ], \"receiptList\": [ { \"costCode\": \"khyj\", \"amount\": 296000, \"billNum\": \"10072017062901649\" }, { \"costCode\": \"khfwf\", \"amount\": 301900, \"billNum\": \"10072017062901648\" }, { \"costCode\": \"khfz\", \"amount\": 1776000, \"billNum\": \"10072017062901647\" } ] } ]", ReceiptBillListResponseDto.class);
				int i = 1;
				for (ReceiptBillListResponseDto responseDto : responseDtos) {
					PayRecordVo vo = new PayRecordVo();
					vo.setName(String.format("第%d次", i));
					vo.setValue(BillMsgConstant.RMB + BigDecimal.valueOf((double) (responseDto.getTotalAmount())).divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_HALF_UP));
					vo.setPaystatus("支付成功");
					payNumList.add(vo);
					i++;
				}
				havePayInfo.setList(payNumList);
				if (totalMoney - leftMoney == 0) {
					havePayInfo = null;
				}
				/*剩余款*/
				leftPayInfo = new BaseFieldVo();
				leftPayInfo.setName("剩余款");
				leftPayInfo.setValue(BillMsgConstant.RMB + BigDecimal.valueOf(leftMoney).divide(BigDecimal.valueOf(100),2, BigDecimal.ROUND_HALF_UP));
				/*账单类型*/
				billDetailVo.setBillType(DocumentTypeEnum.RENT_FEE.getCode());

				if (period == 1) {
					// 获取活动信息 首期展示活动信息
					List<RentContractActivityEntity> contractActivityList = this.rentContractServiceImpl.getContractActivityList(rentContractEntity.getContractId());
					List<BaseFieldVo> activityList = new ArrayList<>();
					contractActivityList.forEach(v->{
						BaseFieldVo activity = new BaseFieldVo();
						activity.setName(v.getActivityName());
						activity.setValue("-" + BillMsgConstant.RMB + BigDecimal.valueOf(v.getDiscountAccount()).setScale(0, BigDecimal.ROUND_HALF_UP));
						activityList.add(activity);
					});

					activityInfoVo = new ActivityInfoVo();
					activityInfoVo.setList(activityList);
					activityInfoVo.setName("优惠活动");
					if (activityList.isEmpty()) {
						activityInfoVo = null;
					}
					shouldPayDateVo.setDesc(BillMsgConstant.BILL_FIRST_PAY_TIP_TEXT); // 首次提示
					// 首期支付超时时间
					LocalDateTime localDateTime = LocalDateTime.ofInstant(rentContractEntity.getSubmitContractTime().toInstant(), ZoneId.systemDefault());
					LocalDateTime realTime;
					if (leftMoney >= Integer.parseInt(payOverTimeConditionMoney)) {// 72小时
						realTime = localDateTime.plusHours(Integer.parseInt(moreMoneyOvertimeHours));
					}else{
						realTime = localDateTime.plusHours(Integer.parseInt(lessMoneyOvertimeHours));
					}
					billDetailVo.setPayEndTime(Date.from(realTime.atZone(ZoneId.systemDefault()).toInstant()));
					shouldPayDateVo.setValue(realTime.format(DateTimeFormatter.ofPattern(DateUtilFormate.DATEFORMAT_4)));//应缴费日期
					shouldPayDateVo.setDesc(BillMsgConstant.BILL_NO_FIRST_PAY_TIP_TEXT);// 提示
					shouldPayDateVo.setTime(realTime.format(DateTimeFormatter.ofPattern(DateUtilFormate.DATEFORMAT_6)));

				}
			}
			/*总款*/
			payCostItemsVo.setName("总款");
			payCostItemsVo.setValue(BillMsgConstant.RMB + BigDecimal.valueOf((double) totalMoney).divide(BigDecimal.valueOf(100),2, BigDecimal.ROUND_HALF_UP));
			payCostItems.sort((o1, o2) -> { // 对费用项排序
				if (o1.getName().compareTo(o2.getName()) > 0) {
					return -1;
				}else{
					return 1;
				}
			});
			payCostItemsVo.setList(payCostItems);

			/*本次支付*/
			needPayInfo.setName("本次支付");
			needPayInfo.setValue(BillMsgConstant.RMB + BigDecimal.valueOf(leftMoney).divide(BigDecimal.valueOf(100),2, BigDecimal.ROUND_HALF_UP));
			needPayInfo.setNeedPay(leftMoney);
			needPayInfo.setIsChangePay(DocumentTypeEnum.LIFE_FEE.getCode().equals(billType) ? "0" : leftMoney > Integer.parseInt(minPayAmount) ? "1" : "0");// 判断支付的金额 是否是最小金额 最小金额不可以修改
			needPayInfo.setIsChangePayDesc("可自定义编辑付款金额");
			needPayInfo.setMinPayMoney(minPayAmount);
			needPayInfo.setMinPayMoneyDesc(String.format(BillMsgConstant.BILL_MIN_PAY_MONEY_TEXT, BigDecimal.valueOf(Double.parseDouble(minPayAmount)).divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_HALF_UP)));

			billDetailVo.setSignType(Integer.parseInt(rentContractEntity.getFsigntype()));// 0 新签 1-续约
		} catch (Exception e) {
			LogUtil.error(LOGGER, "【findPayPageDetail】错误 contract:{},error:{}", contractId, e);
			dto.setMsg("系统错误");
			dto.setErrCode(DataTransferObject.ERROR);
			return dto.toJsonString();
		}

		billDetailVo.setContractId(contractId);
		billDetailVo.setShouldPayDate(shouldPayDateVo);// 应缴文本信息
		billDetailVo.setProjectInfo(projectInfoVo);// 项目信息
		billDetailVo.setPayInfo(payCostItemsVo);//支付费用项信息
		billDetailVo.setActivityInfo(activityInfoVo); // 活动信息
		billDetailVo.setHavePayInfo(havePayInfo);// 已经支付的金额
		billDetailVo.setLeftPayInfo(leftPayInfo);// 剩余款
		billDetailVo.setThisTimeNeedPay(needPayInfo);// 本次支付的金额
		billDetailVo.setBillNumList(billNumList.toString());// 账单列表
		billDetailVo.setSystemId(zraPaySystemId);// 收银台系统标识

		dto.putValue("billDetail", billDetailVo);
		return dto.toJsonString();
	}

	/**
	 * 下单校验(供财务使用)
	 *
	 * @param paramJson {"billNums":"123123131,1231313"} 逗号隔开
	 * @return json
	 * @author cuigh6
	 * @Date 2017年10月
	 */
	@Override
	public String validPayForFinance(String paramJson) {
		LogUtil.info(LOGGER, "【validPayForFinance】参数={}", paramJson);
		DataTransferObject dto = new DataTransferObject();
		Map map = JsonEntityTransform.json2Object(paramJson, Map.class);
		String billNums = (String) map.get("billNums");
		if (Check.NuNObj(billNums)) {
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("账单参数为空");
			return dto.toJsonString();
		}
		String[] strings = billNums.split(",");
		RentContractBillEntity entity = receiBillDetailServiceImpl.getContractInfoForValid(strings[0]);
		if (Check.NuNObj(entity)) {
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("没有对应的合同信息");
			return dto.toJsonString();
		}
        if (ContractValueConstant.QIBAO_PROJECT_ID.equals(entity.getProjectId())){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg(ContractMsgConstant.CONTRACT_STOP_PAY_MSG);
            return dto.toJsonString();
        }
		// 房租账单类型
		if (DocumentTypeEnum.RENT_FEE.getCode().equals(entity.getFBillType())) {
			if (ContractStatusEnum.YGB.getStatus().equals(entity.getConStatusCode()) || ContractStatusEnum.YTZ.getStatus().equals(entity.getConStatusCode())) {
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("合同状态为已关闭或者已退租");
				return dto.toJsonString();
			}
		}else{// 生活费用类型
			if (ContractStatusEnum.JYZ.getStatus().equals(entity.getConStatusCode())) {
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("合同解约中,不可支付");
				return dto.toJsonString();
			}
		}
		return dto.toJsonString();
	}

	/**
	 * 查询历史支付的生活费用账单
	 * @param contractId 合同号
	 * @return
	 * @author cuigh6
	 * @Date 2017年10月
	 */
	@Override
	public String findHistoryLifeFeeList(String contractId) {
		LogUtil.info(LOGGER, "【findHistoryLifeFeeList】入参contractId={}", contractId);
		DataTransferObject dto = new DataTransferObject();
		if (Check.NuNStr(contractId)) {
			dto.setMsg("合同标识为空");
			dto.setErrCode(DataTransferObject.ERROR);
			return dto.toJsonString();
		}
		try {
			// 项目信息组合
			RentContractEntity rentContractEntity = this.rentContractServiceImpl.findContractBaseByContractId(contractId);
			// 查询生活费用账单
			List<ReceiptBillResponse> lifeReceiptBillList = this.callFinanceServiceProxy.getBillListByType(rentContractEntity.getConRentCode(), DocumentTypeEnum.LIFE_FEE.getCode(),null);
			if (lifeReceiptBillList == null) {
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("系统错误");
				return dto.toJsonString();
			}
			List<LifeBillInfoVo> lifeBillInfoVos = this.buildLifeFeeBillList(lifeReceiptBillList, rentContractEntity, 2);
			dto.putValue("liftList", lifeBillInfoVos);
		} catch (Exception e) {
			LogUtil.info(LOGGER,"【findHistoryLifeFeeList】出错：{}",e);
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("系统错误");
			return dto.toJsonString();
		}
		return dto.toJsonString();
	}
	/**
	 * 查询待支付账单列表
	 * @param uid 用户标识
	 * @return json
	 * @author cuigh6
	 * @Date 2017年11月11日
	 */
	public String getMustPayBillList(String uid) {
		LogUtil.info(LOGGER, "【getMustPayBillList】请求参数:uid={}", uid);
		DataTransferObject dto = new DataTransferObject();
		List<PendingPayRoomRentBillVo> roomRentList = new ArrayList<>();
		List<PendingPayLifeBillVo> lifeBillList = new ArrayList<>();
		try {
			//查询合同列表
			List<RentContractEntity> rentContractEntities = this.rentContractServiceImpl.listContractByUid(uid);
			List<RentContractEntity> collect = rentContractEntities.stream()
					.filter(v ->ContractStatusEnum.DZF.getStatus().equals(v.getConStatusCode())
							|| ContractStatusEnum.YQY.getStatus().equals(v.getConStatusCode())
							|| ContractStatusEnum.YXY.getStatus().equals(v.getConStatusCode())
							|| ContractStatusEnum.XYZ.getStatus().equals(v.getConStatusCode())
							|| ContractStatusEnum.JYZ.getStatus().equals(v.getConStatusCode()))
					.collect(Collectors.toList());
			collect.forEach(rentContractEntity->{// 循环合同
				//查询项目信息
				String projectById = this.projectService.findProjectById(rentContractEntity.getProjectId());
				DataTransferObject dataTransferObject = JsonEntityTransform.json2DataTransferObject(projectById);
				ProjectEntity projectEntity = dataTransferObject.parseData("projectEntity", new TypeReference<ProjectEntity>() {
				});
				//查询房间信息
				String roomResult = this.roomService.getRoomByFid(rentContractEntity.getRoomId());
				DataTransferObject roomResultObject = JsonEntityTransform.json2DataTransferObject(roomResult);
				RoomInfoEntity roomInfoEntity = roomResultObject.parseData("roomInfo", new TypeReference<RoomInfoEntity>() {
				});

				try {
					DataTransferObject receivableBillFromFinance = this.getReceivableBillFromFinance(rentContractEntity);
					List<ReceivableBillInfoVo> receivableBillInfoVos = (List<ReceivableBillInfoVo>) receivableBillFromFinance.getData().get("roomRentList");
					List<LifeBillInfoVo> lifeBillInfoVos = (List<LifeBillInfoVo>) receivableBillFromFinance.getData().get("liftList");
					List<ReceivableBillInfoVo> ReceivableBillInfoList = receivableBillInfoVos.stream().filter(v -> v.getOperationCode() == 1).collect(Collectors.toList());
					ReceivableBillInfoList.forEach(a->{
						PendingPayRoomRentBillVo pendingPayRoomRentBillVo = new PendingPayRoomRentBillVo();
						pendingPayRoomRentBillVo.setProjectName(rentContractEntity.getProName());
						pendingPayRoomRentBillVo.setDirection(DirectionEnum.getDirection(Integer.parseInt(roomInfoEntity.getFdirection()))+ ContractMsgConstant.DIRECTION_MSG);
						pendingPayRoomRentBillVo.setFloorNum(roomInfoEntity.getFfloornumber() + ContractMsgConstant.FLOOR_MSG);
						pendingPayRoomRentBillVo.setProHeadFigureUrl(pic_url + projectEntity.getFHeadFigureUrl());
						pendingPayRoomRentBillVo.setRoomNum(roomInfoEntity.getFroomnumber());
						pendingPayRoomRentBillVo.setBillCycle(a.getBillCycle());
						pendingPayRoomRentBillVo.setReceivableAmount(a.getReceivableAmount());
						pendingPayRoomRentBillVo.setPaymentDate(a.getPaymentDate());
						pendingPayRoomRentBillVo.setBillStatus(a.getBillStatus());
						pendingPayRoomRentBillVo.setPeriod(a.getPeriod());
						pendingPayRoomRentBillVo.setBillStatusTxt(a.getBillStatusTxt());
						pendingPayRoomRentBillVo.setOperation(a.getOperation());
						pendingPayRoomRentBillVo.setOperationCode(a.getOperationCode());
						pendingPayRoomRentBillVo.setContractCode(rentContractEntity.getConRentCode());
						pendingPayRoomRentBillVo.setContractId(rentContractEntity.getContractId());
						roomRentList.add(pendingPayRoomRentBillVo);
					});
					StringJoiner costNameList = new StringJoiner(",");
					PendingPayLifeBillVo lifeBill = new PendingPayLifeBillVo();
					Integer[] totalAmount = {0};
					lifeBillInfoVos.forEach(v->{
						costNameList.add(v.getName());
						lifeBill.setCreateDate(v.getList().stream().filter(s->s.getName().equals(BillMsgConstant.BILL_LIFE_LIST_CREATE_DATE_TEXT)).findFirst().get().getValue());
						totalAmount[0] += v.getAmount();
					});
					if (!lifeBillInfoVos.isEmpty()) {
						lifeBill.setCostNameList(costNameList.toString());
						lifeBill.setAmount(BillMsgConstant.RMB + BigDecimal.valueOf(totalAmount[0]).divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_HALF_UP));
						lifeBill.setBillStatus(BillStatusForAppEnum.DZF.getCode());
						lifeBill.setOperation(BillOperateForAppEnum.QZF.getName());
						lifeBill.setProjectName(rentContractEntity.getProName());
						lifeBill.setDirection(DirectionEnum.getDirection(Integer.parseInt(roomInfoEntity.getFdirection()))+ContractMsgConstant.DIRECTION_MSG);
						lifeBill.setFloorNum(roomInfoEntity.getFfloornumber() + ContractMsgConstant.FLOOR_MSG);
						lifeBill.setProHeadFigureUrl(pic_url + projectEntity.getFHeadFigureUrl());
						lifeBill.setRoomNum(roomInfoEntity.getFroomnumber());
						lifeBill.setContractCode(rentContractEntity.getConRentCode());
						lifeBill.setOperationCode(BillOperateForAppEnum.QZF.getCode());
						lifeBill.setContractId(rentContractEntity.getContractId());
						lifeBillList.add(lifeBill);
					}
				} catch (Exception e) {
					LogUtil.error(LOGGER, "【getMustPayBillList】请求异常:", e);
					dto.setMsg("系统错误");
					dto.setErrCode(DataTransferObject.ERROR);
				}
			});
		} catch (Exception e) {
			LogUtil.error(LOGGER, "【getMustPayBillList】请求异常:", e);
			dto.setMsg("系统错误");
			dto.setErrCode(DataTransferObject.ERROR);
		}
		dto.putValue("roomRentList", roomRentList);
		dto.putValue("lifeBillList", lifeBillList);
		return dto.toJsonString();
	}

	/**
	 * 查询账单列表(房租账单和生活费用账单)
	 * <p>
	 *     1.当type=0时,实时计算账单 2.当type=1时,从财务查询
	 * </p>
	 * @param contractId 合同Id
	 * @param type 提交合同前0 提交合同后为1
	 * @return
	 * @author cuigh6
	 * @Date 2017年10月
	 */
	@Override
	public String findBillListByContractId(String contractId, Integer type) {
		LogUtil.info(LOGGER, "【findBillListByContractId】参数=【contractId:{},type:{}】", contractId, type);
		DataTransferObject dto = new DataTransferObject();
		if (Check.NuNStr(contractId)) {
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("合同号为空");
			return dto.toJsonString();
		}
		if (Check.NuNObj(type)) {
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("类型为空");
			return dto.toJsonString();
		}
		try {
			// 项目信息组合
			RentContractEntity rentContractEntity = this.rentContractServiceImpl.findContractBaseByContractId(contractId);
            /*//判断合同状态
            if (isValidContractStatus(rentContractEntity)) {
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("合同状态不正确");
                return dto.toJsonString();
            }*/
			// 判断是否查询财务系统
			if (type == 0) {// 实时计算
				List<ReceivableBillInfoVo> receivableBillInfoVos = new ArrayList<>();
				DataTransferObject paymentItems = financeCommonLogic.getPaymentItems(rentContractEntity);
				PaymentTermsDto items = paymentItems.parseData("items", new TypeReference<PaymentTermsDto>() {
				});
				items.getRoomRentBillDtos().forEach(v -> {
					ReceivableBillInfoVo vo = new ReceivableBillInfoVo();
					vo.setPeriod(v.getPeriod());
					vo.setBillCycle(String.format(BillMsgConstant.BILL_ROOM_RENT_LIST_CYCLE_TEXT, v.getStartDate(), v.getEndDate()));
					vo.setPaymentDate(v.getStartDate());
					vo.setReceivableAmount(BillMsgConstant.RMB + v.getPeriodTotalMoney());
					vo.setOperationCode(BillOperateForAppEnum.WCZ.getCode());
					vo.setBillStatus(BillStatusForAppEnum.DZF.getCode());
					vo.setBillStatusTxt("待支付");
					receivableBillInfoVos.add(vo);
				});
				dto.putValue("roomRentList", receivableBillInfoVos);
				List<LifeBillInfoVo> lifeBillInfoVos = new ArrayList<>();
				dto.putValue("liftList", lifeBillInfoVos);
				return dto.toJsonString();
			} else {// 查询财务系统
				DataTransferObject receivableBillFromFinance = this.getReceivableBillFromFinance(rentContractEntity);
				return receivableBillFromFinance.toJsonString();
			}
		} catch (Exception e) {
			LogUtil.error(LOGGER, "【findBillListByContractId】error:{}", e);
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("系统错误");
			return dto.toJsonString();
		}
	}


	/**
	 * 获取账单列表 从财务系统
	 * <p>
	 *     1.查询房租账单&查询逾期违约金账单
	 *     2.遍历房租账单 根据期数分组 计算应收总金额和实收总金额
	 *     3.遍历逾期违约金 如果存在逾期违约金 计算金额
	 *     4.为应收账单 确认操作和状态
	 *     5.过滤已支付的账单 对剩余账单根据 期数*状态 排序 将第一个账单更改为去支付
	 *     6.将已支付账单 根据期数排序后 添加到账单列表中
	 *     7.查询生活费用账单
	 * </p>
	 * @param rentContractEntity 合同对象
	 * @return dto
	 * @author cuigh6
	 * @Date 2017/09/28
	 */
	private DataTransferObject getReceivableBillFromFinance(RentContractEntity rentContractEntity) throws Exception {
		DataTransferObject dto = new DataTransferObject();
		if(Check.NuNObj(rentContractEntity)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("合同为空");
			return dto;
		}
		//查询房租账单
		List<ReceiptBillResponse> receivableBillList = this.
				callFinanceServiceProxy.getBillListByType(rentContractEntity.getConRentCode(), DocumentTypeEnum.RENT_FEE.getCode(), null);
		if (receivableBillList == null) {
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("系统错误");
			return dto;
		}

		//查询逾期违约金账单
		List<ReceiptBillResponse> overdueReceiptBillList = this.
				callFinanceServiceProxy.getBillListByType(rentContractEntity.getConRentCode(), DocumentTypeEnum.OVERDUE_FEE.getCode(),null);
		if (overdueReceiptBillList == null) {
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("系统错误");
			return dto;
		}
		Map<Integer, ReceivableBillInfoVo> map = new HashMap<>();

		ReceivableBillInfoVo receivableBillInfoVo;
		// 对应收账单 根据期数分组
		for (ReceiptBillResponse receiptBillResponse : receivableBillList) {
			receivableBillInfoVo = new ReceivableBillInfoVo();
			if (map.get(receiptBillResponse.getPeriods()) == null) {
				receivableBillInfoVo.setPeriod(receiptBillResponse.getPeriods());
				receivableBillInfoVo.setPaymentDate(receiptBillResponse.getPreCollectionDate());
				receivableBillInfoVo.setBillCycle(String.format(BillMsgConstant.BILL_ROOM_RENT_LIST_CYCLE_TEXT, receiptBillResponse.getBillsycleStarttime(), receiptBillResponse.getBillsycleEndtime()));
				receivableBillInfoVo.setReceivableAmount(String.valueOf(receiptBillResponse.getReceiptBillAmount() - receiptBillResponse.getReceivedBillAmount()));
				receivableBillInfoVo.setReceivedAmount(String.valueOf(receiptBillResponse.getReceivedBillAmount()));
				receivableBillInfoVo.setBillStatus(receiptBillResponse.getVerificateStatus() != 1 ? BillStatusForAppEnum.DZF.getCode() : BillStatusForAppEnum.YZF.getCode());// 1-已核销
				map.put(receiptBillResponse.getPeriods(), receivableBillInfoVo);
			} else {
				ReceivableBillInfoVo o = map.get(receiptBillResponse.getPeriods());
				Integer s = receiptBillResponse.getVerificateStatus() != 1 ? BillStatusForAppEnum.DZF.getCode() : BillStatusForAppEnum.YZF.getCode();// 1-已核销
				o.setBillStatus(o.getBillStatus() == BillStatusForAppEnum.DZF.getCode() ? BillStatusForAppEnum.DZF.getCode() : s);
				o.setReceivableAmount(String.valueOf(Integer.parseInt(o.getReceivableAmount()) + receiptBillResponse.getReceiptBillAmount() - receiptBillResponse.getReceivedBillAmount()));
				o.setReceivedAmount(String.valueOf(Integer.parseInt(o.getReceivedAmount()) + receiptBillResponse.getReceivedBillAmount()));
			}
		}

		//遍历逾期违约金
		overdueReceiptBillList.forEach(v -> {
			ReceivableBillInfoVo receivableBillInfoVo1 = map.get(v.getPeriods());
			if (receivableBillInfoVo1 != null) {
				Integer s = v.getVerificateStatus() != 1 ? BillStatusForAppEnum.YYQ.getCode() : BillStatusForAppEnum.YZF.getCode();
				receivableBillInfoVo1.setBillStatus(receivableBillInfoVo1.getBillStatus() == BillStatusForAppEnum.DZF.getCode() ?
						BillStatusForAppEnum.YYQ.getCode() : s);
				receivableBillInfoVo1.setReceivableAmount(String.valueOf(Integer.parseInt(receivableBillInfoVo1.getReceivableAmount()) + v.getReceiptBillAmount() - v.getReceivedBillAmount()));
				receivableBillInfoVo1.setReceivedAmount(String.valueOf(Integer.parseInt(receivableBillInfoVo1.getReceivedAmount()) + v.getReceivedBillAmount()));
			}
		});

		List<ReceivableBillInfoVo> receivableBillInfoVos = new ArrayList<>();
		// 添加支付状态 和支付操作 将map 放入list 中
		map.forEach((k, v) -> {
			if (v.getBillStatus() == BillStatusForAppEnum.YZF.getCode()) {
				v.setBillStatusTxt(BillStatusForAppEnum.YZF.getName());
				v.setOperation(BillOperateForAppEnum.ZFXQ.getName());
				v.setOperationCode(BillOperateForAppEnum.ZFXQ.getCode());
			}
			if (v.getBillStatus() == BillStatusForAppEnum.DZF.getCode()) {
				v.setBillStatusTxt(BillStatusForAppEnum.DZF.getName());
				v.setOperation("");
				v.setOperationCode(BillOperateForAppEnum.WCZ.getCode());
			}
			if (v.getBillStatus() == BillStatusForAppEnum.YYQ.getCode()) {
				v.setBillStatusTxt(BillStatusForAppEnum.YYQ.getName());
				v.setOperation("");
				v.setOperationCode(BillOperateForAppEnum.QZF.getCode());
			}

			v.setReceivableAmount(BillMsgConstant.RMB + String.valueOf(BigDecimal.valueOf(Double.parseDouble(v.getReceivableAmount())).divide(BigDecimal.valueOf(100), 0, BigDecimal.ROUND_HALF_UP)));// 将分转换成元
			v.setReceivedAmount(BillMsgConstant.RMB + String.valueOf(BigDecimal.valueOf(Double.parseDouble(v.getReceivedAmount())).divide(BigDecimal.valueOf(100), 0, BigDecimal.ROUND_HALF_UP)));// 将分转换成元
			receivableBillInfoVos.add(v);
		});

		// 过滤已支付的账单 并从列表中移除
		List<ReceivableBillInfoVo> YZFBill = new ArrayList<>();
		for (int i = receivableBillInfoVos.size() - 1; i >= 0; i--) {
			ReceivableBillInfoVo v = receivableBillInfoVos.get(i);
			if (v.getBillStatus() == BillStatusForAppEnum.YZF.getCode()) {
				YZFBill.add(v);
				receivableBillInfoVos.remove(v);
			}
		}
		//按照状态*期数 排序 此时 列表中不存在已支付的账单
		receivableBillInfoVos.sort((o1, o2) -> {
			if (o1.getPeriod() * o1.getBillStatus() < o2.getPeriod() * o2.getBillStatus()) {
				return -1;
			} else {
				return 1;
			}
		});
		if (!receivableBillInfoVos.isEmpty()
				&&(!rentContractEntity.getConStatusCode().equals(ContractStatusEnum.YGB.getStatus())
				&&!rentContractEntity.getConStatusCode().equals(ContractStatusEnum.YZF.getStatus())
				&&!rentContractEntity.getConStatusCode().equals(ContractStatusEnum.YTZ.getStatus()))
				&& (receivableBillInfoVos.get(0).getPeriod()==1
				|| rentContractEntity.getConAuditState().equals(ContractAuditStatusEnum.YTG.getStatus()))) {
			receivableBillInfoVos.get(0).setOperationCode(BillOperateForAppEnum.QZF.getCode());// 将第一个账单置为 去支付
			receivableBillInfoVos.get(0).setOperation(BillOperateForAppEnum.QZF.getName());
		}
		YZFBill.sort((o1, o2) -> {
			if (o1.getPeriod() < o2.getPeriod()) {
				return -1;
			} else {
				return 1;
			}
		});// 将已支付的账单 按期数排序 添加到列表中
		receivableBillInfoVos.addAll(YZFBill);

		dto.putValue("roomRentList", receivableBillInfoVos);

		// 查询生活费用账单
		List<ReceiptBillResponse> lifeReceiptBillList = this.callFinanceServiceProxy.getBillListByType(rentContractEntity.getConRentCode(), DocumentTypeEnum.LIFE_FEE.getCode(),null);
		if (lifeReceiptBillList == null) {
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("系统错误");
			return dto;
		}

		List<LifeBillInfoVo> lifeBillInfoVos = this.buildLifeFeeBillList(lifeReceiptBillList, rentContractEntity, 1);
		dto.putValue("liftList", lifeBillInfoVos);
		dto.putValue("systemId", zraPaySystemId);
		dto.putValue("roomRentTip","");
		dto.putValue("lifeFeeTip","如果您有待支付生活费用账单,将无法支付房租");
		dto.putValue("contractCode", rentContractEntity.getConRentCode());
		return dto;
	}

	/**
	 * 查询生活费用账单
	 * @param lifeReceiptBillList 生活费用 应收账单
	 * @param rentContractEntity 合同对象
	 * @param type 类型1- 待支付的 账单 2-已支付账单
	 * @return
	 * @author cuigh6
	 * @Date 2017年10月
	 */
	private List<LifeBillInfoVo> buildLifeFeeBillList(List<ReceiptBillResponse> lifeReceiptBillList,RentContractEntity rentContractEntity,Integer type) {
		List<LifeBillInfoVo> lifeBillInfoVos = new ArrayList<>(4);
		lifeReceiptBillList.forEach(v -> {
			LifeBillInfoVo lifeBillInfoVo = new LifeBillInfoVo();
			//查询应收账单详情
			FinReceiBillDetailEntity fiBillDetailEntity=receiBillDetailServiceImpl.getReceiBillDetailByFBillNum(v.getBillNum());
			if(!Check.NuNObj(fiBillDetailEntity)){
				//智能水表增加账单详情信息
				IntellectWaterMeterBillLogEntity waterBillLog=intellectWaterMeterBillLogService.getIntellectWaterMeterBillLogBybillFid(fiBillDetailEntity.getBillFid());
				//赋值初始不可删除标识
				lifeBillInfoVo.setDelCode(YesOrNoEnum.NO.getCode());
				List<BaseFieldVo> voList = new ArrayList<>();
				if (v.getVerificateStatus() != 1 && type == 1) {// 待支付账单
	
					lifeBillInfoVo.setName(CostCodeEnum.getByCode(v.getCostCode()).getName());
	
					lifeBillInfoVo.setValue(String.format(BillMsgConstant.RMB_CHINESE, BigDecimal.valueOf(v.getReceiptBillAmount()-v.getReceivedBillAmount()).divide(BigDecimal.valueOf(100)).setScale(2, BigDecimal.ROUND_HALF_UP)));
	
					BaseFieldVo vo1 = new BaseFieldVo();
					vo1.setName(BillMsgConstant.BILL_LIFE_LIST_CONTRACT_CODE_TEXT);
					vo1.setValue(v.getOutContractCode());
					BaseFieldVo vo2 = new BaseFieldVo();
					vo2.setName(BillMsgConstant.BILL_LIFE_LIST_ROOM_TEXT);
					vo2.setValue(rentContractEntity.getHouseRoomNo());
					BaseFieldVo vo3 = new BaseFieldVo();
					vo3.setName(BillMsgConstant.BILL_LIFE_LIST_CREATE_DATE_TEXT);
					FinReceiBillDetailEntity entity = receiBillDetailServiceImpl.getReceiBillDetailByFBillNum(v.getBillNum());
					vo3.setValue(DateUtilFormate.formatDateToString(entity == null ? new Date() : entity.getCreateTime(), DateUtilFormate.DATEFORMAT_4));// 处理查询不到日期
					BaseFieldVo vo4 = new BaseFieldVo();
					vo4.setName(BillMsgConstant.BILL_LIFE_LIST_PAYMENT_DATE_TEXT);
					vo4.setValue(v.getPreCollectionDate());
					lifeBillInfoVo.setOperationCode(BillOperateForAppEnum.QZF.getCode());
					lifeBillInfoVo.setBillNum(v.getBillNum());
					lifeBillInfoVo.setAmount(v.getReceiptBillAmount()-v.getReceivedBillAmount());
					voList.add(vo1);
					voList.add(vo2);
					voList.add(vo3);
					voList.add(vo4);
					//赋值可删除标识
					if(FeeItemEnum.POWER_FEE.getItemFid()==fiBillDetailEntity.getExpenseItemId()&&YesOrNoEnum.YES.getCode()==fiBillDetailEntity.getIsSmart()){
						lifeBillInfoVo.setDelCode(YesOrNoEnum.YES.getCode());
						lifeBillInfoVo.setDelDesc("作废账单");
					}
				} else if (v.getVerificateStatus() == 1 && type == 2) {//已支付账单列表
					lifeBillInfoVo.setName(CostCodeEnum.getByCode(v.getCostCode()).getName());
					lifeBillInfoVo.setValue(String.format(BillMsgConstant.RMB_CHINESE, BigDecimal.valueOf(v.getReceiptBillAmount()).divide(BigDecimal.valueOf(100)).setScale(2, BigDecimal.ROUND_HALF_UP)));
	
					BaseFieldVo vo1 = new BaseFieldVo();
					vo1.setName(BillMsgConstant.BILL_LIFE_LIST_CONTRACT_CODE_TEXT);
					vo1.setValue(v.getOutContractCode());
					BaseFieldVo vo2 = new BaseFieldVo();
					vo2.setName(BillMsgConstant.BILL_LIFE_LIST_ROOM_TEXT);
					vo2.setValue(rentContractEntity.getHouseRoomNo());
					BaseFieldVo vo3 = new BaseFieldVo();
					vo3.setName(BillMsgConstant.BILL_LIFE_LIST_CREATE_DATE_TEXT);
					FinReceiBillDetailEntity entity = receiBillDetailServiceImpl.getReceiBillDetailByFBillNum(v.getBillNum());
					vo3.setValue(DateUtilFormate.formatDateToString(entity == null ? new Date() : entity.getCreateTime(), DateUtilFormate.DATEFORMAT_4));// 处理查询不到日期
					BaseFieldVo vo4 = new BaseFieldVo();
					vo4.setName(BillMsgConstant.BILL_LIFE_LIST_PAYMENT_DATE_TEXT);
					vo4.setValue(v.getPreCollectionDate());
					lifeBillInfoVo.setOperationCode(BillOperateForAppEnum.ZFXQ.getCode());
					voList.add(vo1);
					voList.add(vo2);
					voList.add(vo3);
					voList.add(vo4);
				}
				//新增展示字段
				if (!Check.NuNObj(waterBillLog)&&FeeItemEnum.WATER_FEE.getItemFid()==fiBillDetailEntity.getExpenseItemId()&&YesOrNoEnum.YES.getCode()==fiBillDetailEntity.getIsSmart()) {
					BaseFieldVo vo5 = new BaseFieldVo();
					vo5.setName("水表示数");
					vo5.setValue(waterBillLog.getStartReading()+"m³-"+waterBillLog.getEndReading()+"m³");
					BaseFieldVo vo6 = new BaseFieldVo();
					vo6.setName("分摊户数");
					vo6.setValue(waterBillLog.getShareFactor()+"");
					BaseFieldVo vo7 = new BaseFieldVo();
					vo7.setName("分摊示数");
					vo7.setValue(waterBillLog.getUseReading()+"m³");
					BaseFieldVo vo8 = new BaseFieldVo();
					vo8.setName("账单周期");
					vo8.setValue(DateUtil.dateFormat(waterBillLog.getStartDate(), "yyyy/MM/dd")+"-"+DateUtil.dateFormat(waterBillLog.getEndDate(), "yyyy/MM/dd"));
					voList.add(vo5);
					voList.add(vo6);
					voList.add(vo7);
					voList.add(vo8);
				}
				lifeBillInfoVo.setList(voList);
				lifeBillInfoVos.add(lifeBillInfoVo);
			}
		});
		return lifeBillInfoVos;
	}
	/**
	 * 是无效的合同
	 *
	 * @param rentContractEntity 合同对象
	 * @return
	 */
	private boolean isValidContractStatus(RentContractEntity rentContractEntity) {
		return Check.NuNObj(rentContractEntity)
				|| rentContractEntity.getConStatusCode().equals(ContractStatusEnum.YGB.getStatus())
				|| rentContractEntity.getConStatusCode().equals(ContractStatusEnum.YZF.getStatus());
	}

	/**
	 * 日租
	 *
	 * @param conType 租住类型
	 * @return
	 */
	private static boolean isDayLease(String conType) {
		return Objects.equals(conType, LeaseCycleEnum.DAY.getCode());
	}

	@Override
	public String getContractTerms(String paramJson) {
		DataTransferObject dto = new DataTransferObject();
		try {
			RentContractEntity rentContractEntity = JsonEntityTransform.json2Entity(paramJson,RentContractEntity.class);
			DataTransferObject itemsDto = financeCommonLogic.getPaymentItems(rentContractEntity);
			PaymentTermsDto paymentTermsDto = (PaymentTermsDto)itemsDto.getData().get("items");
			if(Check.NuNObj(paymentTermsDto)||Check.NuNCollection(paymentTermsDto.getRoomRentBillDtos())){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("该合同的应收账单不存在");
				return dto.toJsonString();
			}
			dto.putValue("paymentTermsDto",paymentTermsDto);
		}catch (Exception e){
			LogUtil.error(LOGGER, "【getContractTerms】 error:{},paramJson={}", e, paramJson);
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("系统错误");
		}
		return dto.toJsonString();
	}
}
