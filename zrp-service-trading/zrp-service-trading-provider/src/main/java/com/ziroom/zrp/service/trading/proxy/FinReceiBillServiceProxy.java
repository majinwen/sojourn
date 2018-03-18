package com.ziroom.zrp.service.trading.proxy;

import com.alibaba.fastjson.JSON;
import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.exception.SOAParseException;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.SOAResParseUtil;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.asura.framework.utils.LogUtil;
import com.google.common.collect.Lists;
import com.ziroom.zrp.houses.entity.EmployeeEntity;
import com.ziroom.zrp.houses.entity.ProjectEntity;
import com.ziroom.zrp.houses.entity.RoomInfoEntity;
import com.ziroom.zrp.service.houses.api.ProjectService;
import com.ziroom.zrp.service.houses.api.RoomService;
import com.ziroom.zrp.service.houses.valenum.MeterTypeEnum;
import com.ziroom.zrp.service.trading.api.FinReceiBillService;
import com.ziroom.zrp.service.trading.dto.ReceivableBillAndDetailDto;
import com.ziroom.zrp.service.trading.dto.finance.BillDto;
import com.ziroom.zrp.service.trading.dto.finance.FinReceiBillDetailDto;
import com.ziroom.zrp.service.trading.dto.finance.FinReceiBillDto;
import com.ziroom.zrp.service.trading.dto.finance.ModifyReceiptBillRequest;
import com.ziroom.zrp.service.trading.pojo.SynFinBillDetailPojo;
import com.ziroom.zrp.service.trading.proxy.commonlogic.FinanceBaseCall;
import com.ziroom.zrp.service.trading.proxy.commonlogic.FinanceCommonLogic;
import com.ziroom.zrp.service.trading.proxy.commonlogic.HousesCommonLogic;
import com.ziroom.zrp.service.trading.proxy.commonlogic.IntellectPlatformLogic;
import com.ziroom.zrp.service.trading.service.FinReceiBillDetailServiceImpl;
import com.ziroom.zrp.service.trading.service.FinReceiBillServiceImpl;
import com.ziroom.zrp.service.trading.service.RentContractServiceImpl;
import com.ziroom.zrp.service.trading.utils.BeanValidator;
import com.ziroom.zrp.service.trading.utils.builder.DataTransferObjectBuilder;
import com.ziroom.zrp.service.trading.valenum.ContractDataVersionEnum;
import com.ziroom.zrp.service.trading.valenum.CustomerTypeEnum;
import com.ziroom.zrp.service.trading.valenum.FeeItemEnum;
import com.ziroom.zrp.service.trading.valenum.base.ValidEnum;
import com.ziroom.zrp.service.trading.valenum.finance.CostCodeEnum;
import com.ziroom.zrp.service.trading.valenum.finance.DocumentTypeEnum;
import com.ziroom.zrp.trading.entity.FinReceiBillDetailEntity;
import com.ziroom.zrp.trading.entity.FinReceiBillEntity;
import com.ziroom.zrp.trading.entity.RentContractEntity;
import com.zra.common.exception.ZrpServiceException;
import com.zra.common.utils.DateUtilFormate;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>应收账单代理</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author cuigh6
 * @version 1.0
 * @Date Created in 2017年11月15日 15:04
 * @since 1.0
 */
@Component("trading.finReceiBillServiceProxy")
public class FinReceiBillServiceProxy implements FinReceiBillService{

	private static final Logger LOGGER = LoggerFactory.getLogger(FinReceiBillServiceProxy.class);

	@Resource(name = "trading.callFinanceServiceProxy")
	private CallFinanceServiceProxy callFinanceServiceProxy;

	@Resource(name = "trading.finReceiBillServiceImpl")
	private FinReceiBillServiceImpl finReceiBillService;

	@Resource(name = "trading.finReceiBillDetailServiceImpl")
	private FinReceiBillDetailServiceImpl finReceiBillDetailService;

	@Resource(name = "trading.rentContractServiceImpl")
	private RentContractServiceImpl rentContractService;

	@Resource(name = "houses.roomService")
	private RoomService roomService;

	@Resource(name="trading.financeBaseCall")
	private FinanceBaseCall financeBaseCall;

	@Resource(name="trading.financeCommonLogic")
	private FinanceCommonLogic financeCommonLogic;

	@Resource(name="trading.housesCommonLogic")
	private HousesCommonLogic housesCommonLogic;

	@Resource(name="trading.intellectPlatformLogic")
	private IntellectPlatformLogic intellectPlatformLogic;

    @Resource(name="houses.projectService")
    private ProjectService projectService;




	/**
	 * 查询最近31天要收款的应收账单的合同号 通过日期
	 * @param projectIds 项目标识
	 * @return
	 * @author cuigh6
	 * @Date 2017年11月
	 */
	public String getReceiptContractByDate(String projectIds,String startDate,String endDate){
		LogUtil.info(LOGGER, "【getReceiptContractByDate】请求参数：projectIds={}", projectIds);
		DataTransferObject dto = new DataTransferObject();
		List<String> contractCodes = finReceiBillService.getReceiptContractByDate(projectIds,startDate,endDate);
		dto.putValue("contractCodes", contractCodes);
		return dto.toJsonString();
	}

	/**
	 * 保存应收账单和明细
	 * @param paramJson 账单和明细
	 * @return
	 * @author cuigh6
	 * @Date 2017年11月
	 */
	public String saveFinReceiBillAndDetail(String paramJson) {
		LogUtil.info(LOGGER, "【saveFinReceiBillAndDetail】请求参数：paramJson={}", paramJson);
		DataTransferObject dto = new DataTransferObject();
		FinReceiBillEntity entity = null;
		List<FinReceiBillDetailEntity> detailEntities = null;
		try {
			// 解析参数
			ReceivableBillAndDetailDto receivableBillAndDetailDto = JsonEntityTransform.json2Object(paramJson, ReceivableBillAndDetailDto.class);
			entity = receivableBillAndDetailDto.getBillEntity();
			detailEntities = receivableBillAndDetailDto.getDetailEntities();
			int i = this.finReceiBillService.saveFinReceiBillAndDetail(entity, detailEntities);
			LogUtil.info(LOGGER, "【saveFinReceiBillAndDetail】保存应收账单和明细影响行数:{}", i);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "【saveFinReceiBillAndDetail】保存应收账单报错：param={} error=", paramJson, e);
			dto.setMsg("保存应收账单报错");
			dto.setErrCode(DataTransferObject.ERROR);
			return dto.toJsonString();
		}

		// 查询合同信息
		RentContractEntity rentContractEntity = this.rentContractService.findContractBaseByContractId(entity.getContractId());
		//同步应收账单
		dto = syncReceiptBillToFinByReceiptList(rentContractEntity,entity, detailEntities);
		if (dto.getCode() == DataTransferObject.ERROR) {// 删除应收账单
			entity.setIsValid(ValidEnum.NO.getCode());
			int affect = this.finReceiBillService.updateFinReceiBillAndSaveDetails(entity, null);
			LogUtil.error(LOGGER, "【updateFinReceiBillAndSaveDetails】同步财务应收失败 更新主账单状态：param={} affect=", entity.getFid(), affect);
		}
		return dto.toJsonString();
	}


	/**
	 * 同步应收账单到财务
	 * @author cuiyuhui
	 * @created
	 * @param
	 * @return
	 */
	public String syncReceiptBillToFinByContractId(String contractId) {
		RentContractEntity rentContractEntity = this.rentContractService.findContractBaseByContractId(contractId);
		DataTransferObject dto = this.syncReceiptBillToFinByEntity(rentContractEntity);
		return dto.toJsonString();
	}

	/**
	 * 根据应收明细查询
	 * @author cuiyuhui
	 * @created
	 * @param
	 * @return
	 */
	@Override
	public String syncReceiptBillToFinByReceiDetailId(String receiDetailId) {
		FinReceiBillDetailEntity finReceiBillDetailEntity = this.finReceiBillDetailService.selectByReceiBillDetailFid(receiDetailId);
		return syncReceiptBillToFinByFinReceiEntity(finReceiBillDetailEntity);
	}

	/**
	 * 定时任务同步合同数据
	 * @author cuiyuhui
	 * @created
	 * @param
	 * @return
	 */
	@Override
	public String asyncRetrySyncReceiptBillToFin() {
		LogUtil.info(LOGGER,"begin retrySyncEntSubContractToFin");
		long start = System.currentTimeMillis();
		int defaultLimit = 50;
		int defaultHours = -6;
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.HOUR_OF_DAY, defaultHours);
		//分页查询
		Date queryDate = calendar.getTime();
		PageBounds pageBounds=new PageBounds();

		int page = 1;
		int size = 0;

		do {
			pageBounds.setPage(page);
			pageBounds.setLimit(defaultLimit);
			PagingResult<SynFinBillDetailPojo> pagingResult = this.finReceiBillDetailService.findFinBillDetailNotSyncToFin(queryDate, pageBounds);
			List<SynFinBillDetailPojo> synFinBillDetailPojoList = pagingResult.getRows();
			if (synFinBillDetailPojoList == null || synFinBillDetailPojoList.size() == 0) {
				break;
			}

			for (SynFinBillDetailPojo synFinBillDetailPojo : synFinBillDetailPojoList) {
				RentContractEntity rentContractEntity = this.rentContractService.findContractBaseByContractId(synFinBillDetailPojo.getContractId());
				if (rentContractEntity.getCustomerType() != CustomerTypeEnum.ENTERPRICE.getCode()) {
					continue;
				}
				this.syncReceiptBillToFinByReceiDetailId(synFinBillDetailPojo.getBillDetailFid());
				LogUtil.info(LOGGER,"deal syncReceiptBillToFinByReceiDetailId conRentCode : {}", rentContractEntity.getConRentCode());
				size++;
			}
			if (synFinBillDetailPojoList.size() < defaultLimit) {
				break;
			}
			page ++;
		} while (true);

		long end = System.currentTimeMillis();

		LogUtil.info(LOGGER, "end retrySyncEntSubContractToFin size:{},queryDate:{},time:{}", size, queryDate, (end - start));
		//查询时间为6小时以外需要同步的数据 -- 告警? TODO cuiyh9 系统稳定后添加
		return null;
	}

	/**
	 * 同步应账单和合同信息到财务
	 * @author cuiyuhui
	 * @created
	 * @param
	 * @return
	 */
	private String syncReceiptBillToFinByFinReceiEntity(FinReceiBillDetailEntity finReceiBillDetailEntity) {

		List<FinReceiBillDetailEntity> finReceiBillDetailEntityList = new ArrayList<>();
		finReceiBillDetailEntityList.add(finReceiBillDetailEntity);

		FinReceiBillEntity finReceiBillEntity = this.finReceiBillService.selectByFid(finReceiBillDetailEntity.getBillFid());
		RentContractEntity rentContractEntity = this.rentContractService.findContractBaseByContractId(finReceiBillEntity.getContractId());

		if (rentContractEntity.getIsSyncToFin() == null ||  rentContractEntity.getIsSyncToFin() != 1) {
			String result = financeCommonLogic.syncContractToFina(rentContractEntity.getContractId());
			LogUtil.info(LOGGER,"syncReceiptBillToFinByFinReceiEntity syncContract result contractId:{},result:{}", rentContractEntity.getContractId(), result);
			DataTransferObject contractDto = JsonEntityTransform.json2DataTransferObject(result);
			if (contractDto.getCode() == DataTransferObject.SUCCESS) {
				DataTransferObject dto = syncReceiptBillToFinByEntity(rentContractEntity);
				return dto.toJsonString();
			}
			return contractDto.toJsonString();
		} else {
			DataTransferObject dto = syncReceiptBillToFinByReceiptList(rentContractEntity, finReceiBillEntity, finReceiBillDetailEntityList);
			return dto.toJsonString();
		}
	}

	/**
	 * 同步应收账单合同到财务
	 * @author cuiyuhui
	 * @created
	 * @param
	 * @return
	 */
	protected DataTransferObject syncReceiptBillToFinByEntity(RentContractEntity rentContractEntity) {

		String contractId = rentContractEntity.getContractId();
		List<DataTransferObject> dtoList = new ArrayList<>();
		try {
			List<FinReceiBillEntity> finReceiBillEntityList = this.finReceiBillService.selectByContractId(contractId);
			for (FinReceiBillEntity finReceiBillEntity :  finReceiBillEntityList ) {
				List<FinReceiBillDetailEntity> finReceiBillDetailEntityList = this.finReceiBillDetailService.selectByBillFid(finReceiBillEntity.getFid());
				DataTransferObject dto = syncReceiptBillToFinByReceiptList(rentContractEntity,finReceiBillEntity, finReceiBillDetailEntityList);
				dtoList.add(dto);
			}

			//这段代码目标是尽可能的给调用方一个清晰的返回结果
			DataTransferObject resultDto = new DataTransferObject();
			resultDto.setErrCode(DataTransferObject.SUCCESS);
			for (int i = 0; i<dtoList.size(); i ++) {
				DataTransferObject tempDto = dtoList.get(i);
				if (tempDto.getCode() == DataTransferObject.ERROR) {
					resultDto.setErrCode(DataTransferObject.ERROR);
				}
				resultDto.putValue(""+i, tempDto);

			}

			return resultDto;
		} catch (Exception e) {
			LogUtil.error(LOGGER, "callFinaCreateReceiptBill :{}, {}", rentContractEntity.getContractId(), rentContractEntity.getConRentCode(), e);
			DataTransferObject expDto = DataTransferObjectBuilder.buildError("调用syncReceiptBillToFinByReceiptList抛出异常, contractId:" + rentContractEntity.getContractId());
			return expDto;
		}

	}

	/**
	 * 直接同步应收账单
	 * @author cuiyuhui
	 * @created
	 * @param
	 * @return
	 */
	public DataTransferObject syncReceiptBillToFinByReceiptList(RentContractEntity rentContractEntity, FinReceiBillEntity finReceiBillEntity, List<FinReceiBillDetailEntity> finReceiBillDetailEntityList) {

		StringJoiner sj = new StringJoiner(",");
		if (finReceiBillDetailEntityList == null || finReceiBillDetailEntityList.size() == 0) {
			return DataTransferObjectBuilder.buildError("应收账单不允许为空");
		}
		for (FinReceiBillDetailEntity finReceiBillDetailEntity :  finReceiBillDetailEntityList) {
			sj.add(finReceiBillDetailEntity.getFid());
		}

		List<BillDto> billList = new ArrayList<>();
		StringJoiner billNumList = new StringJoiner(",");
		String roomId = "";
		Map<String, String> cacheMap = new HashMap<>(16);
		List<FinReceiBillDetailEntity> synFinReceiBillDetailEntities = finReceiBillDetailEntityList.stream().filter(finReceiBillDetailEntity -> finReceiBillDetailEntity.getStatus() == null || finReceiBillDetailEntity.getStatus() != 1).collect(Collectors.toList());
		for (FinReceiBillDetailEntity finReceiBillDetailEntity : synFinReceiBillDetailEntities) {
			BillDto billDto = new BillDto();
			billDto.setUid(rentContractEntity.getCustomerUid());
			// 老的企业合同 随着迭代会成为废弃代码
			if (rentContractEntity.getDataVersion() == ContractDataVersionEnum.OLD.getCode()&& rentContractEntity.getCustomerType()==CustomerTypeEnum.ENTERPRICE.getCode()) {
				billDto.setUid("ZRADEFAULTUSER");
			}
			billDto.setUsername(rentContractEntity.getCustomerName());
			billDto.setHouseCode(rentContractEntity.getHouseRoomNo());
			billDto.setHouseId(rentContractEntity.getProjectId());
			try {
				//此处之所以使用cache,是为了考虑到应收账单创建时，应收明细的创建人都为同一个
				String houseKeeperCode = getZoCodeByEmployeeId(finReceiBillDetailEntity.getCreateId(), cacheMap);
				billDto.setHouseKeeperCode(houseKeeperCode);
			} catch (ZrpServiceException e) {
				LogUtil.error(LOGGER, "【getZoCodeByEmployeeId】获取员工号异常 employeeId:{}, recei_bill_detail_id:{}", finReceiBillDetailEntity.getCreateId(), finReceiBillDetailEntity.getFid(), e);
				DataTransferObject dto =  DataTransferObjectBuilder.buildError(e.getMessage());
				return dto;
			}

			roomId = finReceiBillDetailEntity.getRoomId();
			if (finReceiBillDetailEntity.getExpenseItemId() == FeeItemEnum.RENT_FEE.getItemFid()) {
				//房租
				billDto.setDocumentType(DocumentTypeEnum.RENT_FEE.getCode());
				billDto.setCostCode(CostCodeEnum.KHFZ.getCode());

			} else if (finReceiBillDetailEntity.getExpenseItemId() == FeeItemEnum.SERVICE_FEE.getItemFid()) {
				//服务费
				billDto.setDocumentType(DocumentTypeEnum.SERVICE_FEE.getCode());
				billDto.setCostCode(CostCodeEnum.KHFWF.getCode());

			} else if (finReceiBillDetailEntity.getExpenseItemId() == FeeItemEnum.DEPOSIT_FEE.getItemFid()) {
				//押金
				billDto.setDocumentType(DocumentTypeEnum.DEPOSIT_FEE.getCode());
				billDto.setCostCode(CostCodeEnum.KHYJ.getCode());

			} else if (finReceiBillDetailEntity.getExpenseItemId() == FeeItemEnum.OVERDUE_FEE.getItemFid()){
				//违约金
				billDto.setDocumentType(DocumentTypeEnum.OVERDUE_FEE.getCode());
				billDto.setCostCode(CostCodeEnum.KHYQWYJ.getCode());

			} else {
				billDto.setDocumentType(DocumentTypeEnum.LIFE_FEE.getCode());
				//水费 电费
				billDto.setCostCode(CostCodeEnum.getById(finReceiBillDetailEntity.getExpenseItemId()).getCode());
			}

			Integer oughtAmount = new BigDecimal(String.valueOf(finReceiBillDetailEntity.getOughtAmount())).multiply(new BigDecimal(100)).intValue();
			billDto.setDocumentAmount(oughtAmount);
			billDto.setPreCollectionDate(DateUtilFormate.formatDateToString(finReceiBillEntity.getPlanGatherDate(), DateUtilFormate.DATEFORMAT_4));
			billDto.setStartTime(DateUtilFormate.formatDateToString(finReceiBillEntity.getStartCycle(), DateUtilFormate.DATEFORMAT_4));
			billDto.setEndTime(DateUtilFormate.formatDateToString(finReceiBillEntity.getEndCycle(), DateUtilFormate.DATEFORMAT_4));
			billDto.setPeriods(finReceiBillEntity.getPayNum());


			String billNum = finReceiBillDetailEntity.getBillNum();
			//应收账单号为空 重新 生成应收账单编号
			if (Check.NuNStrStrict(billNum)) {
				try {
					billNum = financeBaseCall.callFinaCreateBillNum(billDto.getDocumentType());
					billNumList.add(billNum);
					//回写应收账单编号
					finReceiBillDetailEntity.setBillNum(billNum);
					finReceiBillDetailEntity.setStatus(0);
					int size = this.finReceiBillDetailService.updateFinReceiBillStatusAndBillNum(finReceiBillDetailEntity);
					//没有更新成功，可能出现的情况是应收账单编号已经生成了,多线程竞争问题
					if (size == 0) {
						LogUtil.warn(LOGGER, "【callFinaCreateBillNum】账单编号已经生成,合同号 {}", rentContractEntity.getConRentCode());
						DataTransferObject dto =  DataTransferObjectBuilder.buildOk("账单编号已经生成,合同号:" + rentContractEntity.getConRentCode());
						return dto;
					}
				} catch (Exception e) {
					LogUtil.error(LOGGER, "【callFinaCreateBillNum】生成账单编号异常：账单fid:{}", finReceiBillDetailEntity.getFid(), e);
					DataTransferObject dto =  DataTransferObjectBuilder.buildError("调用callFinaCreateBillNum异常");
					return dto;
				}
			}
			billDto.setBillNum(billNum);
			billList.add(billDto);
		}
		String contractCode = rentContractEntity.getConRentCode();
		try {
			// 老的企业合同 随着迭代会成为废弃代码
			if (rentContractEntity.getDataVersion() == ContractDataVersionEnum.OLD.getCode()&& rentContractEntity.getCustomerType()==CustomerTypeEnum.ENTERPRICE.getCode()) {
				// 查询房间号
				String roomStr = this.roomService.getRoomByFid(roomId);
				DataTransferObject dataTransferObject = JsonEntityTransform.json2DataTransferObject(roomStr);
				if (dataTransferObject.getCode() == DataTransferObject.ERROR) {
					LogUtil.error(LOGGER, "旧企业合同同步应收查询房间号异常:{}", dataTransferObject.getMsg());
				}
				RoomInfoEntity roomInfoEntity = dataTransferObject.parseData("roomInfo", new TypeReference<RoomInfoEntity>() {
				});
				contractCode = contractCode + "+" + roomInfoEntity.getFroomnumber();
				billList.forEach(v -> v.setHouseCode(roomInfoEntity.getFroomnumber()));//老的企业合同 房间号
			}
			DataTransferObject dto = financeBaseCall.callFinaCreateReceiptBill(contractCode, billList);
			if (dto.getCode() == DataTransferObject.SUCCESS) {
				synFinReceiBillDetailEntities.forEach(v->{
					v.setStatus(1);
					this.finReceiBillDetailService.updateFinReceiBillStatus(v);
				});
			}
			dto.putValue("billNums", billNumList.toString());
			dto.putValue("contractCode", contractCode);
			return dto;
		} catch (Exception e) {
			LogUtil.error(LOGGER,"{}" + rentContractEntity.getConRentCode(),e);
			DataTransferObject dto =  DataTransferObjectBuilder.buildError("调用callFinaCreateReceiptBill异常");
			dto.putValue("data", sj);
			return dto;
		}

	}

	/**
	 * 根据员工id获取员工号。有缓存.调用方自行捕获异常来处理
	 * @author cuiyuhui
	 * @created
	 * @param employeeId
	 * @param cacheMap 缓存map,在循环遍历时，循环外定义临时缓存,每次取时，先判断缓存是否存在
	 * @return
	 */
	public  String getZoCodeByEmployeeId(String employeeId, Map<String, String> cacheMap) {
		boolean isUseCached = (cacheMap != null);
		String zoCode = null;

		if (isUseCached) {
			zoCode = cacheMap.get(employeeId);
		}

		if (Check.NuNStrStrict(zoCode)) {
			EmployeeEntity employeeEntity = housesCommonLogic.getHousesEmployByEmployeeId(employeeId);
			zoCode = employeeEntity.getFcode();
			if (cacheMap != null) {
				cacheMap.put(employeeId, zoCode);
			}
		}

		return zoCode;
	}

	/**
	 * 修改应收账单
	 * @param paramJson 参数
	 * @return
	 * @author cuigh6
	 * @Date 2017年11月
	 */
	public String updateFinReceivableBill(String paramJson) {
		LogUtil.info(LOGGER, "【updateFinReceivableBill】请求入参：paramJson={}", paramJson);
		DataTransferObject dto = new DataTransferObject();
		try {
			ModifyReceiptBillRequest modifyReceiptBillRequest = JsonEntityTransform.json2Object(paramJson, ModifyReceiptBillRequest.class);
			String result = this.callFinanceServiceProxy.modifyReceiptBill(JsonEntityTransform.Object2Json(modifyReceiptBillRequest));
			DataTransferObject resultObject = JsonEntityTransform.json2DataTransferObject(result);
			if (resultObject.getCode() == DataTransferObject.ERROR){
				LogUtil.error(LOGGER,"【modifyReceiptBill】请求结果,dto={}",resultObject.toJsonString());
				return resultObject.toJsonString();
			}
			FinReceiBillDetailEntity finReceiBillDetailEntity = new FinReceiBillDetailEntity();
			finReceiBillDetailEntity.setBillNum(modifyReceiptBillRequest.getBillNum());
			finReceiBillDetailEntity.setOughtAmount(BigDecimal.valueOf(modifyReceiptBillRequest.getDocumentAmount())
					.divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_HALF_UP).doubleValue());
			int affect = finReceiBillDetailService.updateFinReceiBillDetailByBillNum(finReceiBillDetailEntity);
			LogUtil.info(LOGGER, "修改应收账单 影响行数:{}", affect);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "【updateFinReceivableBill】程序报错,paramJson={}", paramJson, e);
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("程序报错");
			dto.toJsonString();
		}
		return dto.toJsonString();
	}

	/* (non-Javadoc)
	 * @see com.ziroom.zrp.service.trading.api.FinReceiBillService#getFinReceiBillByContractId(java.lang.String)
	 */
	@Override
	public String getFinReceiBillByContractId(String paramJson) {
		LogUtil.info(LOGGER, "【getFinReceiBillByContractId】请求入参：paramJson={}", paramJson);
		DataTransferObject dto = new DataTransferObject();
		try {
			Map<String, Object> paramMap=(Map<String, Object>) JsonEntityTransform.json2Map(paramJson);
			if(Check.NuNObj(paramMap.get("contractId"))){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("合同id不能为空");
				dto.toJsonString();
			}
			if(Check.NuNObj(paramMap.get("itemId"))){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("费用id不能为空");
				dto.toJsonString();
			}
			List<FinReceiBillDetailEntity> list=finReceiBillDetailService.getFinReceiBillByContractId(paramMap.get("contractId").toString(), (int)paramMap.get("itemId"));
			dto.putValue("list", list);
		} catch (Exception e){
			LogUtil.error(LOGGER, "【getFinReceiBillByContractId】请求入参：paramJson={}", paramJson, e);
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("程序报错");
			dto.toJsonString();
		}
		return dto.toJsonString();
	}

	/**
	 *  保存应收账单（水表，电表）
	 *
	 *	Logic list:
	 * 		1. check: verify 应收金额（paymentAmount） = 单价（unitPrice）* 充值示数（reading）
	 * 		2. change: dto ===》 entity
	 * 		3. db: insert by transaction
	 * 		4. finance: sync get 财务编号(f_bill_num)等
	 *
	 * @param finReceiBillDto 应收账单
	 * @return
	 */
	@Override
	public String saveFinanceReceivableOfWaterWatt(FinReceiBillDto finReceiBillDto) {

		LogUtil.info(LOGGER, "【saveFinanceReceivableOfWaterWatt】请求参数：finReceiBillDto:【{}】", JSON.toJSONString(finReceiBillDto));

		DataTransferObject dto = new DataTransferObject();

		List<FinReceiBillDetailDto> finReceiBillDetailDtos = finReceiBillDto.getFinReceiBillDetailDtos();

		// 1. 简单校验
		BeanValidator.validate(finReceiBillDto);

		// 查询合同信息
		RentContractEntity rentContractEntity = this.rentContractService.findContractBaseByContractId(finReceiBillDto.getContractId());
		if (rentContractEntity == null) {
			LogUtil.error(LOGGER, "saveFinanceReceivableOfWaterWatt==>没有找到合同信息,contractId : {}", finReceiBillDto.getContractId());
			dto.setMsg("没有合同信息");
			dto.setErrCode(DataTransferObject.ERROR);
			return dto.toJsonString();
			//throw new BusinessException(String.format("saveFinanceReceivableOfWaterWatt==>没有找到合同信息,contractId : [%s]", finReceiBillDto.getContractId()));
		}

		// 合同信息填充应收账单
		finReceiBillDto.setProjectId(rentContractEntity.getProjectId());
		finReceiBillDto.setCityId(rentContractEntity.getCityid());

		// 生成主表应收账单编号
        ProjectEntity projectEntity = null;
        String projectJson = null;
        try {
            projectJson = projectService.findProjectById(rentContractEntity.getProjectId());
            if (DataTransferObject.SUCCESS == JsonEntityTransform.json2DataTransferObject(projectJson).getCode()) {
                projectEntity = SOAResParseUtil.getValueFromDataByKey(projectJson,"projectEntity",ProjectEntity.class);
            }
        } catch (SOAParseException e) {
            LogUtil.error(LOGGER,"【createReceiptBill】调用projectService服务查询项目信息失败,resultJson:{}",projectJson);
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("获取项目信息失败");
        }
        if(Check.NuNObj(projectEntity)){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("获取项目信息失败");
            return dto.toJsonString();
        }
		finReceiBillDto.setBillNumber(financeCommonLogic.genReceiBillCode(projectEntity.getFcode()));

		// 校验金钱
		if (!verifyPaymentAmout(finReceiBillDto)) {
			dto.setMsg("金额校验失败.");
			dto.setErrCode(DataTransferObject.ERROR);
			return dto.toJsonString();
		}


		// 2. 获取应收账单实体
		FinReceiBillEntity billEntity = new FinReceiBillEntity();
		final List<FinReceiBillDetailEntity> detailEntities = Lists.newArrayList();

			BeanUtils.copyProperties(finReceiBillDto, billEntity);

			finReceiBillDetailDtos.forEach(detail -> {
			FinReceiBillDetailEntity detailEntity = new FinReceiBillDetailEntity();
			BeanUtils.copyProperties(detail, detailEntity);
			detailEntities.add(detailEntity);
		});

		// 3. 保存
		int insertFlag;
		try {
			insertFlag= this.finReceiBillService.saveFinReceiBillAndDetail(billEntity, detailEntities);
			LogUtil.info(LOGGER, "【saveFinanceReceivableOfWaterWatt】保存应收账单和明细影响行数:{}", insertFlag);
		} catch (Exception e) {
			dto.setMsg("保存应收账单报错");
			dto.setErrCode(DataTransferObject.ERROR);
			LOGGER.error("【saveFinanceReceivableOfWaterWatt】保存应收账单异常", e);
			LogUtil.error(LOGGER, "finReceiBillDto:【{}】", JSON.toJSONString(finReceiBillDto), e);

			dto.setMsg("保存应收账单异常");
			dto.setErrCode(DataTransferObject.ERROR);
			return dto.toJsonString();
			//throw new BusinessException("保存应收账单失败");
		}
		if (insertFlag < 1) {
			dto.setMsg("保存应收账单失败");
			dto.setErrCode(DataTransferObject.ERROR);
			return dto.toJsonString();
			//throw new BusinessException("保存应收账单失败");
		}

		// 4. 同步财务：应收账单
		dto = syncReceiptBillToFinByReceiptList(rentContractEntity, billEntity, detailEntities);

		if (dto.getCode() == DataTransferObject.ERROR) {
			LogUtil.error(LOGGER, "【saveFinanceReceivableOfWaterWatt】同步财务应收失败 应收账单：【{}】, 合同：【{}】", JSON.toJSONString(billEntity), JSON.toJSONString(rentContractEntity));
			// 删除应收账单
			billEntity.setIsValid(ValidEnum.NO.getCode());
			int affectCount = this.finReceiBillService.updateFinReceiBillAndSaveDetails(billEntity, null);

			LogUtil.error(LOGGER, "【saveFinanceReceivableOfWaterWatt】同步财务应收失败 更新主账单为无效状态：影响条数 = 【{}】", affectCount);
		}
		return dto.toJsonString();
	}

	/**
	 * 校验金额
	 *
	 * @param finReceiBillDto
	 * 		目前水电表，主子应收账单金额想同
	 * @return
	 * 		true : 金额正确
	 * 		false : 校验失败
	 */
	private boolean verifyPaymentAmout(FinReceiBillDto finReceiBillDto) {

		final String projectId = finReceiBillDto.getProjectId();
		final Double showReading = finReceiBillDto.getReading(); // 客户端上报==》充值示数
		final Double oughtTotalAmount = finReceiBillDto.getOughtTotalAmount(); // 客户端上报==》应收金额总计

		// 电费单价
		Double unitPrice;

		// 目前只支持 预付费（电费）充值
		switch (MeterTypeEnum.valueOf(finReceiBillDto.getDeviceTypeName())) {
			case ELECTRICITY:
				unitPrice = intellectPlatformLogic.findWattCostStandard(projectId);
				break;
			case WATER:
				throw new RuntimeException(String.format("后付费设备：水表暂不支持充值！"));
			default:
				throw new RuntimeException(String.format("未知设备类型, 请重试"));
		}

		if (unitPrice == null || unitPrice == 0D) {
			throw new RuntimeException(String.format("项目id: [%s] 获取单价 : [%s] 失败", projectId, unitPrice));
		}

		// 此处比较采用相减
		if (Math.abs(oughtTotalAmount - (showReading * unitPrice)) < 0.1D){
			return true;
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see com.ziroom.zrp.service.trading.api.FinReceiBillService#getFinReceiBillByFid(java.lang.String)
	 */
	@Override
	public String getFinReceiBillByFid(String billFid) {
		FinReceiBillEntity bill= finReceiBillService.selectByFid(billFid);
		DataTransferObject dto=new DataTransferObject();
		dto.putValue("obj", bill);
		return dto.toJsonString();
	}
}