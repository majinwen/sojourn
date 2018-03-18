package com.ziroom.zrp.service.trading.proxy;

/*
 * <P>调用财务接口实现</P>
 * <P>
 * <PRE>
 * <BR> 修改记录
 * <BR>------------------------------------------
 * <BR> 修改日期       修改人        修改内容
 * </PRE>
 * 
 * @Author lusp
 * @Date Create in 2017年09月 21日 11:46
 * @Version 1.0
 * @Since 1.0
 */

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.*;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.services.basedata.api.inner.SmsTemplateService;
import com.ziroom.minsu.services.basedata.dto.SmsRequest;
import com.ziroom.minsu.services.common.constant.SysConst;
import com.ziroom.minsu.services.common.thread.pool.SendThreadPool;
import com.ziroom.minsu.services.common.utils.CloseableHttpUtil;
import com.ziroom.minsu.services.common.utils.ValueUtil;
import com.ziroom.minsu.valenum.msg.CreaterTypeEnum;
import com.ziroom.zrp.houses.entity.EmployeeEntity;
import com.ziroom.zrp.houses.entity.ProjectEntity;
import com.ziroom.zrp.service.houses.api.EmployeeService;
import com.ziroom.zrp.service.houses.api.HouseSignInviteRecordService;
import com.ziroom.zrp.service.houses.api.ProjectService;
import com.ziroom.zrp.service.houses.dto.smartplatform.waterwatt.WattElectricMeterStateDto;
import com.ziroom.zrp.service.houses.dto.smartplatform.waterwatt.WattMeterChargingDto;
import com.ziroom.zrp.service.houses.entity.smartplatform.waterwatt.ElectricMeterStateVo;
import com.ziroom.zrp.service.trading.api.CallFinanceService;
import com.ziroom.zrp.service.trading.dto.ContractRoomDto;
import com.ziroom.zrp.service.trading.dto.PaymentBillsDto;
import com.ziroom.zrp.service.trading.dto.PaymentTermsDto;
import com.ziroom.zrp.service.trading.dto.RoomRentBillDto;
import com.ziroom.zrp.service.trading.dto.finance.*;
import com.ziroom.zrp.service.trading.entity.LifeItemVo;
import com.ziroom.zrp.service.trading.entity.SyncContractVo;
import com.ziroom.zrp.service.trading.proxy.commonlogic.FinanceBaseCall;
import com.ziroom.zrp.service.trading.proxy.commonlogic.FinanceCommonLogic;
import com.ziroom.zrp.service.trading.proxy.commonlogic.IntellectPlatformLogic;
import com.ziroom.zrp.service.trading.service.*;
import com.ziroom.zrp.service.trading.utils.builder.DataTransferObjectBuilder;
import com.ziroom.zrp.service.trading.valenum.*;
import com.ziroom.zrp.service.trading.valenum.finance.*;
import com.ziroom.zrp.trading.entity.*;
import com.zra.common.enums.BillTypeEnum;
import com.zra.common.enums.SmsTemplateCodeEnum;
import com.zra.common.exception.FinServiceException;
import com.zra.common.utils.DateUtilFormate;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

@Slf4j
@Component("trading.callFinanceServiceProxy")
public class CallFinanceServiceProxy implements CallFinanceService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CallFinanceServiceProxy.class);

    @Value("#{'${finance_sys_code}'.trim()}")
    private String sysCode;

    @Value("#{'${finance_bussinessUnitCode}'.trim()}")
    private String bussinessUnitCode;

    @Value("#{'${finance_receiptBill_url}'.trim()}")
    private String finance_receiptBill_url;

    @Value("#{'${finance_update_contract_url}'.trim()}")
    private String updateContractUrl;

    @Value("#{'${finance_createReceiptBill_url}'.trim()}")
    private String createReceiptBillUrl;

    @Value("#{'${finance_modifyReceiptBill_url}'.trim()}")
    private String modifyReceiptBillUrl;

    @Value("#{'${finance_contract_receiptList_url}'.trim()}")
    private String getContractReceiptListUrl;

    @Value("#{'${finace_receipt_url}'.trim()}")
    private String finace_receipt_url;

    @Value("#{'${finance_create_pay_voucher_url}'.trim()}")
    private String getCreatePayVoucherUrl;

    @Value("#{'${finance_getReceiptInfo_url}'.trim()}")
    private String getReceiptInfo;

    @Value("#{'${finance_getReceiptBillByContracts_url}'.trim()}")
    private String getReceiptBillByContracts;

    @Value("#{'${finance_receipt_method_url}'.trim()}")
    private String financeReceiptMethodUrl;

    @Value("#{'${finance_receivable_by_condition_url}'.trim()}")
    private String financeReceivableByConditionUrl;

    @Value("#{'${fzck_list_url}'.trim()}")
    private String FZCKListUrl;

    @Value("#{'${followup_record_url}'.trim()}")
    private String followupRecordUrl;

    @Value("#{'${record_list_url}'.trim()}")
    private String recordListUrl;

    @Value("#{'${save_followup_url}'.trim()}")
    private String saveFollowupUrl;

    @Value("#{'${fzck_count_url}'.trim()}")
    private String FZCKCountUrl;

    @Value("#{'${finance_ZRA_receipt_bill_url}'.trim()}")
    private String ZRAReceiptBillUrl;

    @Value("#{'${finance_createReceipt_url}'.trim()}")
    private String finance_createReceipt_url;

    @Value("#{'${finance_updateReceipt_url}'.trim()}")
    private String finance_updateReceipt_url;

    @Value("#{'${finance_queryReceipt_url}'.trim()}")
    private String finance_queryReceipt_url;

    @Value("#{'${receipt_bill_call_url}'.trim()}")
    private String receiptBillCallUrl;

    @Resource(name="houses.projectService")
    private ProjectService projectService;

    @Resource(name="trading.rentContractServiceImpl")
    private RentContractServiceImpl rentContractServiceImpl;

    @Resource(name="trading.finReceiBillServiceImpl")
    private FinReceiBillServiceImpl finReceiBillServiceImpl;

    @Resource(name="trading.finReceiBillDetailServiceImpl")
    private FinReceiBillDetailServiceImpl finReceiBillDetailServiceImpl;

    @Resource(name="trading.rentItemDeliveryServiceImpl")
    private RentItemDeliveryServiceImpl rentItemDeliveryServiceImpl;

    @Resource(name = "trading.receiptServiceImpl")
    private ReceiptServiceImpl receiptServiceImpl;

    @Resource(name="trading.financeCommonLogic")
    private FinanceCommonLogic financeCommonLogic;

    @Resource(name="trading.financeBaseCall")
    private FinanceBaseCall financeBaseCall;

    @Resource(name="houses.employeeService")
    private EmployeeService employeeService;

    @Resource(name = "houses.houseSignInviteRecordService")
	private HouseSignInviteRecordService houseSignInviteRecordService;
    
    @Resource(name ="trading.rentContractServiceProxy")
    private RentContractServiceProxy rentContractServiceProxy;
    
    @Resource(name="basedata.smsTemplateService")
    private SmsTemplateService smsTemplateService;

    @Resource(name="trading.checkSignServiceProxy")
    private CheckSignServiceProxy checkSignServiceProxy;

    @Resource(name="trading.intellectPlatformLogic")
    private IntellectPlatformLogic intellectPlatformLogic;

    @Resource(name="trading.intellectWattMeterSnapshotImpl")
    private IntellectWattMeterSnapshotImpl intellectWattMeterSnapshotImpl;


    @Override
    public String createBillNum(String billType) {
        DataTransferObject dto = new DataTransferObject();
        try {
            String billNum = financeBaseCall.callFinaCreateBillNum(billType);
            if(Check.NuNStrStrict(billNum)){
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("生成应收账单失败");
                return dto.toJsonString();
            }
            dto.putValue("billNum", billNum);
        } catch (Exception e) {
            LogUtil.error(LOGGER, "【getReceivableBillNum】 error:{}", e);
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("系统错误");
        }
        return dto.toJsonString();
    }

    @Override
    public String createBillNumBatch(String billType,int total) {
        DataTransferObject dto = new DataTransferObject();
        try {
            JSONArray billNums = financeBaseCall.callFinaCreateBillNumBatch(billType,total);
            if(Check.NuNCollection(billNums)){
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("批量生成应收账单失败");
                return dto.toJsonString();
            }
            dto.putValue("billNums", billNums);
        } catch (Exception e) {
            LogUtil.error(LOGGER, "【createBillNumBatch】 error:{}", e);
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("系统错误");
        }
        return dto.toJsonString();
    }

    @Override
    public String syncContract(String contractId) {
        return financeCommonLogic.syncContractToFina(contractId);
    }

    /**
     * <p>查询应收账单  请求示例</p>
     * <code>
     * ReceiptBillRequest receiptBillRequest = new ReceiptBillRequest();
     * receiptBillRequest.setOutContractCode(rentContractEntity.getConRentCode());
     * receiptBillRequest.setBillNum(DocumentTypeEnum.LIFE_FEE.getCode());
     * </code>
     * <code>
     *  {
     *   "billNum":"10012017090120011"  //String类型 账单编号（账单编号与出房合同号不能同时为空）
     *   "outContractCode":"BJ101160629066"  //String类型 出房合同号
     *   "documentType":"1001"  //String类型 非必填 账单类型（1001：生活费用；1007：友家收款计划；1009：友家出房违约金）
     *   "periods":1  //Integer类型 非必填 期数
     *   }
     * </code>
     *
     * <p>wiki地址 http://wiki.ziroom.com/pages/viewpage.action?pageId=187465806</p>
     *
     * @author jixd
     * @created 2017年11月06日 16:35:24
     * @param
     * @return
     */
    @Override
    public String getReceivableBillInfo(String paramJson) {
        LogUtil.info(LOGGER,"【getReceiptBillInfo】参数={}",paramJson);
        if (Check.NuNStr(paramJson)){
            return DataTransferObjectBuilder.buildErrorJsonStr("参数为空");
        }
        try{
            ReceiptBillRequest receiptBillRequest = JsonEntityTransform.json2Object(paramJson, ReceiptBillRequest.class);
            Map<String, Object> resultMap = financeBaseCall.getReceivableBillInfo(receiptBillRequest);
            DataTransferObject dataTransferObject = DataTransferObjectBuilder.buildOk("listStr", resultMap.get("listStr"));
            dataTransferObject.putValue("isPay",resultMap.get("isPay"));
            dataTransferObject.putValue("isEmpty",resultMap.get("isEmpty"));
            return dataTransferObject.toJsonString();
        }catch (Exception e){
            if (e instanceof FinServiceException){
                return DataTransferObjectBuilder.buildErrorJsonStr(e.getMessage());
            }else{
                return DataTransferObjectBuilder.buildErrorJsonStr("服务错误");
            }
        }
    }

    /**
     *
     *  更新财务合同接口
     *  入参：SyncContractVo
     *
     * <p>wiki 地址：http://wiki.ziroom.com/pages/viewpage.action?pageId=175505503</p>
     * <p>接口地址：http://zfdataline.ziroom.com/api/contract/updateContract</p>
     * @param paramJson
     * @return
     */
    @Override
    public String updateContract(String paramJson) {
        LogUtil.info(LOGGER, "【updateContract】入参:{}", paramJson);
        SyncContractVo syncContractVo = JsonEntityTransform.json2Object(paramJson, SyncContractVo.class);
        try {
            financeBaseCall.updateContract(syncContractVo);
        } catch (FinServiceException e) {
        	LogUtil.error(LOGGER, "【updateContract】异常:{}", e);
            return DataTransferObjectBuilder.buildErrorJsonStr(e.getMessage());
        }
        return DataTransferObjectBuilder.buildOkJsonStr("");
    }

    @Override
    public String updateContractForOldEsp(String contractId,String conRentCode, String statusCode) {
        LogUtil.info(LOGGER, "【updateContractForOldEsp】入参:contractId{},statusCode:{}", contractId,statusCode);
        List<RentDetailEntity> rentDetailEntities = rentContractServiceImpl.findRentDetailByContractId(contractId);
        if(Check.NuNCollection(rentDetailEntities)){
            LogUtil.error(LOGGER, "【updateContractForOldEsp】查询合同子表为空,contractId{},statusCode:{}", contractId,statusCode);
            return DataTransferObjectBuilder.buildErrorJsonStr("合同子表为空");
        }
        for(RentDetailEntity rentDetailEntity:rentDetailEntities){
            SyncContractVo syncContractVo = new SyncContractVo();
            syncContractVo.setCrmContractId(rentDetailEntity.getId());
            syncContractVo.setRentContractCode(conRentCode+"+"+rentDetailEntity.getRoomCode());
            syncContractVo.setStatusCode(statusCode);
            try {
                financeBaseCall.updateContract(syncContractVo);
            } catch (FinServiceException e) {
                e.printStackTrace();
                return DataTransferObjectBuilder.buildErrorJsonStr(e.getMessage());
            }
        }
        return DataTransferObjectBuilder.buildOkJsonStr("");
    }

    @Override
    public String createReceiptBill(String contractId) {
        LogUtil.info(LOGGER, "【createReceiptBill】入参contractId:{}", contractId);
        DataTransferObject dto = new DataTransferObject();
        try {
            if (Check.NuNStrStrict(contractId)) {
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("合同id为空");
                return dto.toJsonString();
            }
            RentContractEntity rentContractEntity = rentContractServiceImpl.findContractBaseByContractId(contractId);
            if(Check.NuNObj(rentContractEntity)){
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("合同信息不存在");
                return dto.toJsonString();
            }
            DataTransferObject itemsDto = financeCommonLogic.getPaymentItems(rentContractEntity);
            PaymentTermsDto paymentTermsDto = (PaymentTermsDto)itemsDto.getData().get("items");
            if(Check.NuNObj(paymentTermsDto)||Check.NuNCollection(paymentTermsDto.getRoomRentBillDtos())){
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("该合同的应收账单不存在");
                return dto.toJsonString();
            }
            List<BillDto> billList = new ArrayList<>();//用于同步应收账单到财务的应收集合
            List<RentContractActivityEntity> rentContractActivityEntities = new ArrayList<>();//活动信息集合,用于保存合同优惠活动
            List<FinReceiBillEntity> finReceiBillEntities = new ArrayList<>();//应收账单集合，用于保存合同应收账单
            List<FinReceiBillDetailEntity> finReceiBillDetailEntities = new ArrayList<>();//应收明细账单集合，用于保存合同应收账单明细
            List<String> billNums = new ArrayList<>();//用于更新应收账单明细表的财务应收账单编号
            rentContractActivityEntities = paymentTermsDto.getActList();//优惠活动集合
            //查询project信息，供生成应收账单编号使用
            String projectJson = projectService.findProjectById(rentContractEntity.getProjectId());
            DataTransferObject projectDto = JsonEntityTransform.json2DataTransferObject(projectJson);
            ProjectEntity projectEntity = SOAResParseUtil.getValueFromDataByKey(projectJson,"projectEntity",ProjectEntity.class);
            if(projectDto.getCode()==DataTransferObject.ERROR||Check.NuNObj(projectEntity)){
                LogUtil.error(LOGGER,"【createReceiptBill】调用projectService服务查询项目信息失败,resultJson:{}",projectJson);
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("调用projectService服务查询项目信息失败");
                return dto.toJsonString();
            }
            //查询管家信息
            String employeeJson = employeeService.findEmployeeByCode(rentContractEntity.getFhandlezocode());
            DataTransferObject employeeDto = JsonEntityTransform.json2DataTransferObject(employeeJson);
            EmployeeEntity employeeEntity = SOAResParseUtil.getValueFromDataByKey(employeeJson,"employeeEntity",EmployeeEntity.class);
            if(employeeDto.getCode()==DataTransferObject.ERROR||Check.NuNObj(employeeEntity)){
                LogUtil.error(LOGGER,"【createReceiptBill】调用employeeService服务查询管家信息失败,handlezocode:{},resultJson:{}",rentContractEntity.getFhandlezocode(),employeeJson);
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("调用employeeService服务查询管家信息失败");
                return dto.toJsonString();
            }
            LogUtil.info(LOGGER,"【createReceiptBill】保存本地应收信息");
            List<RoomRentBillDto> roomRentBillDtos = paymentTermsDto.getRoomRentBillDtos();
            //获取应收账单总数,然后调用财务批量生成接口生成编号
            int BillNumTotal = getBillNumTotal(roomRentBillDtos);
            //批量获取财务房租、服务费、押金的应收账单编号(因为现在三种账单都是"1007",所以可以一起批量获取,以后若不一样要分开处理)
            JSONArray billNumArray = financeBaseCall.callFinaCreateBillNumBatch(DocumentTypeEnum.RENT_FEE.getCode(),BillNumTotal);
            int index = 0;//用于从应收账单数组中取出应收编号
            for(RoomRentBillDto roomRentBillDto:roomRentBillDtos){
                FinReceiBillEntity finReceiBillEntity = new FinReceiBillEntity();//用于保存应收账单实体
                String finReceiBillFid = UUIDGenerator.hexUUID();
                finReceiBillEntity.setFid(finReceiBillFid);
                finReceiBillEntity.setContractId(contractId);

                finReceiBillEntity.setBillNumber(financeCommonLogic.genReceiBillCode(projectEntity.getFcode()));
                finReceiBillEntity.setBillState(ReceiBillStateEnum.WSK.getCode());
                finReceiBillEntity.setBillType(0);
                finReceiBillEntity.setGenWay(1);
                finReceiBillEntity.setPayNum(roomRentBillDto.getPeriod());
                finReceiBillEntity.setOughtTotalAmount(roomRentBillDto.getPeriodTotalMoney().doubleValue());
                finReceiBillEntity.setPlanGatherDate(DateUtilFormate.formatDateStringToDate(roomRentBillDto.getStartDate()));
                finReceiBillEntity.setStartCycle(DateUtilFormate.formatDateStringToDate(roomRentBillDto.getStartDate()));
                finReceiBillEntity.setEndCycle(DateUtilFormate.formatDateStringToDate(roomRentBillDto.getEndDate()));
                finReceiBillEntity.setCityId(rentContractEntity.getCityid());
                finReceiBillEntities.add(finReceiBillEntity);

                /***************组装每一项应收账单集合****************/
                List<BillDto> billDtos = assembleBillDto(roomRentBillDto);
                if(Check.NuNCollection(billDtos)){
                    dto.setErrCode(DataTransferObject.ERROR);
                    dto.setMsg("该合同的应收账单集合为空");
                    return dto.toJsonString();
                }
                for (BillDto billDto: billDtos){
                    billDto.setUid(rentContractEntity.getCustomerUid());
                    billDto.setUsername(rentContractEntity.getCustomerName());
                    billDto.setHouseCode(rentContractEntity.getHouseRoomNo());
                    billDto.setHouseId(rentContractEntity.getProjectId());
                    billDto.setHouseKeeperCode(rentContractEntity.getFhandlezocode());
                    billDto.setBillNum(billNumArray.getString(index));
                    index++;

                    FinReceiBillDetailEntity finReceiBillDetailEntity = new FinReceiBillDetailEntity();//用于保存应收账单明细实体
                    finReceiBillDetailEntity.setBillFid(finReceiBillFid);
                    finReceiBillDetailEntity.setExpenseItemId(FeeItemEnum.getByCostCodeEnumCode(billDto.getCostCode()).getItemFid());
                    finReceiBillDetailEntity.setOughtAmount(billDto.getDocumentAmount().doubleValue()/100);
                    finReceiBillDetailEntity.setCityId(finReceiBillEntity.getCityId());
                    finReceiBillDetailEntity.setRoomId(rentContractEntity.getRoomId());
                    finReceiBillDetailEntity.setBillNum(billDto.getBillNum());
                    finReceiBillDetailEntity.setBillType(billDto.getDocumentType());
                    finReceiBillDetailEntities.add(finReceiBillDetailEntity);
                    billNums.add(billDto.getBillNum());
                }
                billList.addAll(billDtos);
            }
            LogUtil.info(LOGGER,"【createReceiptBill】保存应收信息结束");

            /************************如果为后台个人新签，则需要同步水电费用和生活费用***********************/
            if(ContractSourceEnum.ZRAMS.getCode()==Integer.valueOf(rentContractEntity.getFsource()) && "0".equals(rentContractEntity.getFsigntype())){
                ContractRoomDto contractRoomDto = new ContractRoomDto();
                contractRoomDto.setContractId(rentContractEntity.getContractId());
                contractRoomDto.setRoomId(rentContractEntity.getRoomId());
                MeterDetailEntity meterDetailEntity = rentItemDeliveryServiceImpl.findMeterDetailById(contractRoomDto);
                List<LifeItemVo> lifeItemVos = new ArrayList<>();
                lifeItemVos = rentItemDeliveryServiceImpl.findLifeItemByContractIdAndRoomId(contractRoomDto);
                if(!Check.NuNObj(meterDetailEntity.getFpayelectricprice()) && meterDetailEntity.getFpayelectricprice()> 0){
                    LifeItemVo lifeItemEleVo = new LifeItemVo();
                    lifeItemEleVo.setPaymentAmount(meterDetailEntity.getFpayelectricprice());
                    lifeItemEleVo.setExpenseItemId(String.valueOf(CostCodeEnum.ZRYDF.getZraId()));
                    lifeItemVos.add(lifeItemEleVo);
                }
                if(!Check.NuNObj(meterDetailEntity.getFpaywaterprice()) && meterDetailEntity.getFpaywaterprice()> 0){
                    LifeItemVo lifeItemWaterVo = new LifeItemVo();
                    lifeItemWaterVo.setPaymentAmount(meterDetailEntity.getFpaywaterprice());
                    lifeItemWaterVo.setExpenseItemId(String.valueOf(CostCodeEnum.ZRYSF.getZraId()));
                    lifeItemVos.add(lifeItemWaterVo);
                }
                if(!Check.NuNCollection(lifeItemVos)){
                    FinReceiBillEntity finReceiBillLifeItemEntity = new FinReceiBillEntity();//生活费用
                    String startTime = DateUtil.dateFormat(rentContractEntity.getSubmitContractTime(),"yyyy-MM-dd");
                    Date endDate = DateUtilFormate.addHours(rentContractEntity.getSubmitContractTime(),48);
                    String endTime = DateUtil.dateFormat(endDate,"yyyy-MM-dd");
                    String finReceiBillLifeItemFid = UUIDGenerator.hexUUID();
                    finReceiBillLifeItemEntity.setFid(finReceiBillLifeItemFid);
                    finReceiBillLifeItemEntity.setContractId(rentContractEntity.getContractId());
                    finReceiBillLifeItemEntity.setBillNumber(financeCommonLogic.genReceiBillCode(projectEntity.getFcode()));
                    finReceiBillLifeItemEntity.setBillState(ReceiBillStateEnum.WSK.getCode());
                    finReceiBillLifeItemEntity.setBillType(1);
                    finReceiBillLifeItemEntity.setGenWay(1);
                    finReceiBillLifeItemEntity.setPlanGatherDate(rentContractEntity.getSubmitContractTime());
                    finReceiBillLifeItemEntity.setStartCycle(rentContractEntity.getSubmitContractTime());
                    finReceiBillLifeItemEntity.setEndCycle(endDate);
                    finReceiBillLifeItemEntity.setCreateId(employeeEntity.getFid());
                    finReceiBillLifeItemEntity.setCityId(rentContractEntity.getCityid());
                    double totalPay = 0.0;
                    //批量获取财务生活费用应收账单编号
                    JSONArray lifeBillNumArray = financeBaseCall.callFinaCreateBillNumBatch(DocumentTypeEnum.LIFE_FEE.getCode(),lifeItemVos.size());
                    int indexLife = 0;//用于从应收账单数组中取出应收编号(生活费用)
                    for(LifeItemVo lifeItemVo:lifeItemVos){
                        BillDto billDto = new BillDto();
                        billDto.setBillNum(lifeBillNumArray.getString(indexLife));
                        indexLife++;
                        billDto.setDocumentType(DocumentTypeEnum.LIFE_FEE.getCode());
                        billDto.setUid(rentContractEntity.getCustomerUid());
                        billDto.setUsername(rentContractEntity.getCustomerName());
                        billDto.setPreCollectionDate(startTime);
                        billDto.setStartTime(startTime);
                        billDto.setEndTime(endTime);
                        billDto.setHouseCode(rentContractEntity.getHouseRoomNo());
                        billDto.setHouseId(rentContractEntity.getProjectId());
                        billDto.setHouseKeeperCode(rentContractEntity.getFhandlezocode());
                        billDto.setCostCode(CostCodeEnum.getById(Integer.parseInt(lifeItemVo.getExpenseItemId())).getCode());
                        billDto.setDocumentAmount(BigDecimal.valueOf(lifeItemVo.getPaymentAmount()).multiply(BigDecimal.valueOf(100)).intValue());
                        billList.add(billDto);
                        totalPay += lifeItemVo.getPaymentAmount();

                        FinReceiBillDetailEntity finReceiBillDetailLifeItemEntity = new FinReceiBillDetailEntity();
                        finReceiBillDetailLifeItemEntity.setFid(UUIDGenerator.hexUUID());
                        finReceiBillDetailLifeItemEntity.setBillFid(finReceiBillLifeItemFid);
                        finReceiBillDetailLifeItemEntity.setExpenseItemId(Integer.parseInt(lifeItemVo.getExpenseItemId()));
                        finReceiBillDetailLifeItemEntity.setOughtAmount(lifeItemVo.getPaymentAmount());
                        finReceiBillDetailLifeItemEntity.setRoomId(rentContractEntity.getRoomId());
                        finReceiBillDetailLifeItemEntity.setCreateId(employeeEntity.getFid());
                        finReceiBillDetailLifeItemEntity.setCityId(rentContractEntity.getCityid());
                        finReceiBillDetailLifeItemEntity.setUpdateId(rentContractEntity.getFhandlezocode());
                        finReceiBillDetailLifeItemEntity.setBillType(DocumentTypeEnum.LIFE_FEE.getCode());
                        finReceiBillDetailLifeItemEntity.setBillNum(billDto.getBillNum());
                        finReceiBillDetailEntities.add(finReceiBillDetailLifeItemEntity);
                        billNums.add(billDto.getBillNum());
                    }
                    finReceiBillLifeItemEntity.setOughtTotalAmount(totalPay);
                    finReceiBillEntities.add(finReceiBillLifeItemEntity);
                }
            }
            /************************如果为后台个人新签，则需要同步水电费用和生活费用***********************/

            /**
             * 1.更新合同表中的和应收账单有关的财务数据
             * 2.保存合同优惠活动
             * 3.保存合同应收账单
             * 4.保存合同应收账单明细
             */
            rentContractServiceImpl.updateContractAndSaveActivityFinReceiInfo(rentContractActivityEntities,finReceiBillEntities,finReceiBillDetailEntities);
            LogUtil.info(LOGGER,"【createReceiptBill】更新合同信息");
            DataTransferObject callFinaDto = financeBaseCall.callFinaCreateReceiptBill(rentContractEntity.getConRentCode(),billList);
            LogUtil.info(LOGGER,"【createReceiptBill】同步应收返回str={}",callFinaDto.toJsonString());
            if(callFinaDto.getCode()==DataTransferObject.ERROR){
                //更新应收账单明细表的同步状态和失败原因
                for (String billNum:billNums){
                    FinReceiBillDetailEntity finReceiBillDetailEntity = new FinReceiBillDetailEntity();
                    finReceiBillDetailEntity.setBillNum(billNum);
                    finReceiBillDetailEntity.setStatus(2);
                    finReceiBillDetailEntity.setFailMsg(Check.NuNStrStrict(callFinaDto.getMsg())?"同步财务异常":callFinaDto.getMsg());
                    finReceiBillDetailServiceImpl.updateFinReceiBillDetailByBillNum(finReceiBillDetailEntity);
                }
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("同步应收到财务失败");
                LogUtil.info(LOGGER,"【createReceiptBill】同步应收失败返回={}",dto.toJsonString());
                return dto.toJsonString();
            }
            //更新应收账单明细表的同步状态为成功
            for (String billNum:billNums){
                FinReceiBillDetailEntity finReceiBillDetailEntity = new FinReceiBillDetailEntity();
                finReceiBillDetailEntity.setBillNum(billNum);
                finReceiBillDetailEntity.setStatus(1);
                finReceiBillDetailServiceImpl.updateFinReceiBillDetailByBillNum(finReceiBillDetailEntity);
            }
            LogUtil.info(LOGGER,"【createReceiptBill】同步财务保存本地结束");
        } catch (Exception e) {
            LogUtil.error(LOGGER, "【createReceiptBill】 contractId={},error:{}",contractId,e);
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("系统错误");
        }
        LogUtil.info(LOGGER, "【createReceiptBill】 方法结束返回值：{}", dto.toJsonString());
        return dto.toJsonString();
    }

    /**
      * @description: 获取应收账单总数
      * @author: lusp
      * @date: 2017/11/15 下午 14:09
      * @params: roomRentBillDtos
      * @return: int
      */
    private int getBillNumTotal(List<RoomRentBillDto> roomRentBillDtos) {
        int i = 0;
        for(RoomRentBillDto roomRentBillDto:roomRentBillDtos){
            if(!Check.NuNObj(roomRentBillDto.getDepositPrice()) && roomRentBillDto.getDepositPrice().compareTo(new BigDecimal(0))==1){
                i++;
            }
            if(!Check.NuNObj(roomRentBillDto.getServicePrice())&& roomRentBillDto.getServicePrice().compareTo(new BigDecimal(0))==1){
                i++;
            }
            if(!Check.NuNObj(roomRentBillDto.getRentPrice())&& roomRentBillDto.getRentPrice().compareTo(new BigDecimal(0))==1) {
                i++;
            }
        }
        return i;
    }

    /**
     * @description: 修改应收账单到财务
     *  {
     *     "billNum":"1001201701010003"  //String类型 账单编号
     *     "documentAmount":100  //Integer类型 必填 费用金额（单位分）
     *     "endTime":"2016-08-01"  //String类型 非必填 账单周期结束时间 yyyy-MM-dd
     *     "isDel":1 //Integer类型 非必填（0未删除，1逻辑删除 不填不对该字段状态进行修改）
     *  }
     * @author: lusp
     * @date: 2017/11/7 下午 12:50
     * @params: paramJson
     * @return: String
     */
    @Override
    public String modifyReceiptBill(String paramJson){
        LogUtil.info(LOGGER, "【modifyReceiptBill】入参:{}", paramJson);
        DataTransferObject dto = new DataTransferObject();
        try {
            ModifyReceiptBillRequest billDto = JsonEntityTransform.json2Object(paramJson, ModifyReceiptBillRequest.class);
            if (Check.NuNObj(billDto)) {
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("实体不存在");
                return dto.toJsonString();
            }
            String result = CloseableHttpUtil.sendPost(modifyReceiptBillUrl, JsonEntityTransform.Object2Json(billDto));
            LogUtil.info(LOGGER,"【modifyReceiptBill】财务更新收款返回结果result={}",result);
            JSONObject jsonObject = JSONObject.parseObject(result);
            if (Check.NuNObj(jsonObject)) {
                LogUtil.error(LOGGER, "【modifyReceiptBill】 更新应收账单到财务返回json为空");
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("系统错误");
                return dto.toJsonString();
            }
            Integer code = jsonObject.getInteger("code");
            if (Check.NuNObj(code) || code != ResponseCodeEnum.SUCCESS.getCode() ) {
                LogUtil.error(LOGGER, "【updateContract】 调用财务更新合同到财务失败，result:{},message:{}", result, jsonObject.getString("message"));
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg(jsonObject.getString("message"));
                return dto.toJsonString();
            }
        } catch (Exception e) {
            LogUtil.error(LOGGER, "【updateContract】 error:{},paramJson={}", e, paramJson);
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("系统错误");
        }
        return dto.toJsonString();
    }

    /**
     * 查询收款单列表 通过合同号和期数
     * <p>
     *     wiki地址:http://wiki.ziroom.com/pages/viewpage.action?pageId=215777392
     *     请求地址:http://zfreceipt.t.ziroom.com/api/zryj/getReceiptListByContract
     * </p>
     * @author cuigh6
     * @Date 2017年10月10日
     * @param paramJson { "periods":"1",期数 null为全部 "confirmStatus":"1", 审核状态：3 未提交 0 未审核，1 审核通过，2 审核未通过 null为全部 "receiptStatus":"0",收款状态:0已收款,1未收款 2打回 null为全部 "verificateStatus":"1",应收账单核销状态 0未核销,1已核销,2部分核销 null为全部 "contractCode":"BJZYCW81707040014"出房合同号 必传 }
     * @return
     */
    @Override
    public String getReceiptBillListByContract(String paramJson) {
        LogUtil.info(LOGGER, "【getReceiptBillListByContract】入参:{}", paramJson);
        DataTransferObject dto = new DataTransferObject();
        try {
            //调用财务接口
            String result = CloseableHttpUtil.sendPost(getContractReceiptListUrl, paramJson);
            LogUtil.info(LOGGER, "【getReceiptBillListByContract】调用财务查询合同收款单列表返回:{}", result);
            JSONObject responseJson = JSONObject.parseObject(result);
            if (Check.NuNObj(responseJson)) {
                LogUtil.error(LOGGER, "【getReceiptBillListByContract】 调用财务查询合同收款单列表返回json为空");
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("系统错误");
                return dto.toJsonString();
            }
            int code = responseJson.getInteger("code");
            if (Check.NuNObj(code) || code != ResponseCodeEnum.SUCCESS.getCode()) {
                LogUtil.error(LOGGER, "【getReceiptBillListByContract】 调用财务查询合同收款单列表，message:{}", responseJson.getString("message"));
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg(responseJson.getString("message"));
                return dto.toJsonString();
            }
            dto.putValue("list", responseJson.getJSONArray("data") == null ? new ArrayList<>() : responseJson.getJSONArray("data"));
        } catch (Exception e) {
            LogUtil.error(LOGGER, "【getReceiptBillListByContract】 error:{},param={}", e, paramJson);
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("系统错误");
        }
        return dto.toJsonString();
    }


    /**
     * @description: 根据roomRentBillDto组装BillDto
     * @author: lusp
     * @date: 2017/9/27 15:19
     * @params: roomRentBillDto
     * @return: BillDto
     */
    private List<BillDto> assembleBillDto(RoomRentBillDto roomRentBillDto){
        if(Check.NuNObj(roomRentBillDto)){
            return null;
        }
        List<BillDto> billDtos = new ArrayList<>();
        if(!Check.NuNObj(roomRentBillDto.getDepositPrice()) && roomRentBillDto.getDepositPrice().compareTo(new BigDecimal(0))==1){
            BillDto billDto1 = new BillDto();
            billDto1.setDocumentType(DocumentTypeEnum.DEPOSIT_FEE.getCode());
            billDto1.setCostCode(CostCodeEnum.KHYJ.getCode());
            billDto1.setDocumentAmount(roomRentBillDto.getDepositPrice().multiply(new BigDecimal(100)).intValue());
            billDto1.setPreCollectionDate(roomRentBillDto.getStartDate());
            billDto1.setStartTime(roomRentBillDto.getStartDate());
            billDto1.setEndTime(roomRentBillDto.getEndDate());
            billDto1.setPeriods(roomRentBillDto.getPeriod());
            billDtos.add(billDto1);
        }
        if(!Check.NuNObj(roomRentBillDto.getServicePrice())&& roomRentBillDto.getServicePrice().compareTo(new BigDecimal(0))==1){
            BillDto billDto2 = new BillDto();
            billDto2.setDocumentType(DocumentTypeEnum.SERVICE_FEE.getCode());
            billDto2.setCostCode(CostCodeEnum.KHFWF.getCode());
            billDto2.setDocumentAmount(roomRentBillDto.getServicePrice().multiply(new BigDecimal(100)).intValue());
            billDto2.setPreCollectionDate(roomRentBillDto.getStartDate());
            billDto2.setStartTime(roomRentBillDto.getStartDate());
            billDto2.setEndTime(roomRentBillDto.getEndDate());
            billDto2.setPeriods(roomRentBillDto.getPeriod());
            billDtos.add(billDto2);
        }
        if(!Check.NuNObj(roomRentBillDto.getRentPrice())&& roomRentBillDto.getRentPrice().compareTo(new BigDecimal(0))==1) {
            BillDto billDto3 = new BillDto();
            billDto3.setDocumentType(DocumentTypeEnum.RENT_FEE.getCode());
            billDto3.setCostCode(CostCodeEnum.KHFZ.getCode());
            billDto3.setDocumentAmount(roomRentBillDto.getPeriodTotalRentPrice().multiply(new BigDecimal(100)).intValue());
            billDto3.setPreCollectionDate(roomRentBillDto.getStartDate());
            billDto3.setStartTime(roomRentBillDto.getStartDate());
            billDto3.setEndTime(roomRentBillDto.getEndDate());
            billDto3.setPeriods(roomRentBillDto.getPeriod());
            billDtos.add(billDto3);
        }
        return billDtos;
    }

    /**
     * 获取应收账单列表 根据合同号和账单类型
     *
     * @param rentCode 合同号
     * @param type     账单类型
     * @param period 期数
     * @return
     * @author cuigh6
     * @Date 2017年10月11日
     */
    public List<ReceiptBillResponse> getBillListByType(String rentCode, String type, Integer period) {
        ReceiptBillRequest receiptBillRequest = new ReceiptBillRequest();
        receiptBillRequest.setOutContractCode(rentCode);
        receiptBillRequest.setDocumentType(type);
        receiptBillRequest.setPeriods(period);


        String receiptBillListStr = this.getReceivableBillInfo(JsonEntityTransform.Object2Json(receiptBillRequest));
        DataTransferObject receiptBillListObject = JsonEntityTransform.json2DataTransferObject(receiptBillListStr);
        if (receiptBillListObject.getCode() == DataTransferObject.ERROR) {
            return null;
        }
        return receiptBillListObject.parseData("listStr", new TypeReference<List<ReceiptBillResponse>>() {
        });
    }

    @Override
	public String getReceiptInfoByBill(String billNoList){
		LogUtil.info(LOGGER, "【getReceiptInfoByBill】入参：{}", billNoList);
		DataTransferObject dto = new DataTransferObject();
		if(Check.NuNStr(billNoList)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("参数为空");
			return dto.toJsonString();
		}
		String result = CloseableHttpUtil.sendPost(finace_receipt_url, billNoList);
        JSONObject jsonObject = JSONObject.parseObject(result);
        if (Check.NuNObj(jsonObject)) {
            LogUtil.error(LOGGER, "【getReceiptInfoByBill】 调用财务查询实收信息返回json为空");
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("系统错误");
            return dto.toJsonString();
        }
        Integer code = jsonObject.getInteger("code");
        if (Check.NuNObj(code) || code != ResponseCodeEnum.SUCCESS.getCode()) {
            LogUtil.error(LOGGER, "【getReceiptInfoByBill】 调用财务查询实收信息失败，message:{}", jsonObject.getString("message"));
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("系统错误");
            return dto.toJsonString();
        }
        JSONArray data = jsonObject.getJSONArray("data");
        dto.putValue("data", data);
        return dto.toJsonString();
	}

    /**
     * 生成付款单接口
     * <p>
     *   wiki地址:http://wiki.ziroom.com/pages/viewpage.action?pageId=173178925
     * </p>
     *
     * @param paramJson [{"paySerialNum":"sf","outContractCode":"BJWL15901797","uid":"1002112","payVouchersDetail":[{"costCode":"sf","refundAmount":-200}]}]
     * @return json
     * @author cuigh6
     * @Date 2017年10月16日
     */
    public DataTransferObject createPayVouchers(String paramJson) {
        LogUtil.info(LOGGER, "【createPayVouchers】入参:{}", paramJson);
        DataTransferObject dto = new DataTransferObject();
        try {
            //调用财务接口
            String result = CloseableHttpUtil.sendPost(getCreatePayVoucherUrl, paramJson);
            LogUtil.info(LOGGER,"【createPayVouchers】创建付款单返回结果:{}",result);
            JSONObject responseJson = JSONObject.parseObject(result);
            if (Check.NuNObj(responseJson)) {
                LogUtil.error(LOGGER, "【createPayVouchers】 调用财务创建付款单返回json为空");
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("系统错误");
                return dto;
            }
            int code = responseJson.getInteger("code");
            if (Check.NuNObj(code) || code != ResponseCodeEnum.SUCCESS.getCode()) {
                LogUtil.error(LOGGER, "【createPayVouchers】 调用财务创建付款单，message:{}", responseJson.getString("message"));
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg(responseJson.getString("message"));
                return dto;
            }
        } catch (Exception e) {
            LogUtil.error(LOGGER, "【getReceiptBillListByContract】 error:{},param={}", e, paramJson);
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("系统错误");
        }
        return dto;
    }

    /**
     * 查询实收信息（根据账单编号）<br/>
     * url=http://zfreceipt.t.ziroom.com/api/zryj/getReceiptInfo
     * wiki=http://wiki.ziroom.com/pages/viewpage.action?pageId=187465832
     *
     * @Author: wangxm113
     * @Date: 2017年10月15日 15时33分34秒
     */
    @Override
    public String getReceiptInfo(String paramJson) {
        LogUtil.info(LOGGER, "【getReceiptInfo】入参:{}", paramJson);
        DataTransferObject dto = new DataTransferObject();
        try {
            //调用财务接口
            String result = CloseableHttpUtil.sendPost(getReceiptInfo, paramJson);
            JSONObject responseJson = JSONObject.parseObject(result);
            if (Check.NuNObj(responseJson)) {
                LogUtil.error(LOGGER, "【getReceiptInfo】 调用财务查询实收信息返回json为空");
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("系统错误");
                return dto.toJsonString();
            }
            int code = responseJson.getInteger("code");
            if (Check.NuNObj(code) || code != ResponseCodeEnum.SUCCESS.getCode()) {
                LogUtil.error(LOGGER, "【getReceiptInfo】 调用财务查询实收信息，message:{}", responseJson.getString("message"));
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg(responseJson.getString("message"));
                return dto.toJsonString();
            }
            dto.putValue("list",responseJson.getJSONArray("data"));
        } catch (Exception e) {
            LogUtil.error(LOGGER, "【getReceiptInfo】 error:{},param={}", e, paramJson);
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("系统错误");
        }
        return dto.toJsonString();
    }

    /**
     *
     * 根据合同号列表查询应收账单信息接口<br/>
     * url=http://zfreceipt.t.ziroom.com//api/tbReceivableBill/getReceiptBillByContracts
     * wiki=http://wiki.ziroom.com/pages/viewpage.action?pageId=298549353
     *
     * @author cuiyuhui
     * @created
     * @param
     * @return
     */
    @Override
    public String getReceiptBillByContracts(String paramJson) {

        String resultJson = CloseableHttpUtil.sendPost(getReceiptBillByContracts, paramJson);
        if (Check.NuNStr(resultJson)){
            LogUtil.error(LOGGER,"【getReceiptBillByContracts】获取应收账单超时,param={}",paramJson);
            return DataTransferObjectBuilder.buildErrorJsonStr("服务错误");
        }

        LogUtil.info(LOGGER,"getReceiptBillByContracts 财务应收返回结果={}",resultJson);

        JSONObject jsonObj = JSONObject.parseObject(resultJson);
        int code = jsonObj.getIntValue("code");
        if (code != ResponseCodeEnum.SUCCESS.getCode()){
            return DataTransferObjectBuilder.buildErrorJsonStr(jsonObj.getString("message"));
        }
        JSONArray data = jsonObj.getJSONArray("data");
        List<ReceiptBillResponse> listBill = JSONArray.parseArray(jsonObj.getString("data"), ReceiptBillResponse.class);
        return DataTransferObjectBuilder.buildOkJsonStr(listBill);
    }

    /**
     * 查询收款方式 从财务系统
     * <p>
     *     url:http://zfbaseinfo.t.ziroom.com/api/offlineReceiptBank/getOfflineReceiptBank
     *     wiki:http://wiki.ziroom.com/pages/viewpage.action?pageId=246874182
     * </p>
     * @param companyCode 公司编码
     * @return json
     * @author cuigh6
     * @Date 2017年11月1日
     */
    @Override
    public String getReceiptMethod(String companyCode) {
        Map<String, String> param = new HashMap<>();
        param.put("companyCode", companyCode);
        param.put("sourceCode", sysCode);
        String resultJson = CloseableHttpUtil.sendFormPost(financeReceiptMethodUrl, param);
        if (Check.NuNStr(resultJson)){
            LogUtil.error(LOGGER,"【getReceiptMethod】获取收款方式超时,param={}",JsonEntityTransform.Object2Json(param));
            return DataTransferObjectBuilder.buildErrorJsonStr("服务错误");
        }
        LogUtil.info(LOGGER,"getReceiptMethod 获取收款方式返回结果={}",resultJson);
        Map<?, ?> map = JsonEntityTransform.json2Map(resultJson);
        Integer code = (Integer) map.get("code");
        if (code != DataTransferObject.SUCCESS) {
            return DataTransferObjectBuilder.buildErrorJsonStr((String) map.get("message"));
        }
        return DataTransferObjectBuilder.buildOkJsonStr(map.get("data"));
    }



    /**
     * 根据条件获取应收账单列表
     * <p>
     *    wiki地址: http://wiki.ziroom.com/pages/viewpage.action?pageId=304906242
     *    url: http:zfreceipt.ziroom.com/api/tbReceivableBill/getZRARecBill
     * </p>
     * @param paramJson 参数
     * @return
     * @author cuigh6
     * @Date 2017年11月14日
     */
    public String getReceivableBillListByCondition(String paramJson) {
        LogUtil.info(LOGGER,"【getReceivableBillListByCondition】参数={}",paramJson);
        DataTransferObject dto = new DataTransferObject();
        if (Check.NuNStr(paramJson)){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("参数为空");
            return dto.toJsonString();
        }
        String resultJson = CloseableHttpUtil.sendPost(financeReceivableByConditionUrl, paramJson);
        if (Check.NuNStr(resultJson)){
            LogUtil.error(LOGGER,"【getReceivableBillListByCondition】获取应收账单列表超时,param={}",paramJson);
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("服务错误");
            return dto.toJsonString();
        }
        LogUtil.info(LOGGER,"获取应收账单列表返回结果={}",resultJson);

        JSONObject jsonObj = JSONObject.parseObject(resultJson);
        int code = jsonObj.getIntValue("code");
        if (code != ResponseCodeEnum.SUCCESS.getCode()){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg(jsonObj.getString("message"));
            return dto.toJsonString();
        }
        JSONObject data = jsonObj.getJSONObject("data");
        dto.putValue("totalCount", data.get("totalCount"));
        dto.putValue("resList", data.get("resList"));
        return dto.toJsonString();
    }

    /**
     * 查询催收工单列表（新）<br/>
     * url:http://urge.t.ziroom.com/api/zra/getFollowList<br/>
     * wiki:http://wiki.ziroom.com/pages/viewpage.action?pageId=315621415
     *
     * @Author: wangxm113
     * @Date: 2017年11月10日 14时37分07秒
     */
    @Override
    public String getFzckList(String projectId) {
        LogUtil.info(LOGGER, "【getFzckList】入参={}", projectId);
        try {
            if (projectId == null || projectId.isEmpty()) {
                return DataTransferObjectBuilder.buildErrorJsonStr("入参不能为空！");
            }
            LogUtil.info(LOGGER, "【getFzckList】查询催收工单数量参数={}", projectId);
            String fzckCount = getFzckCount(projectId);
            DataTransferObject json2Object = JsonEntityTransform.json2Object(fzckCount, DataTransferObject.class);
            if (json2Object.getCode() != DataTransferObject.ERROR) {
                Object data = json2Object.getData().get("data");
                if (data == null) {//如果催收条数为0
                    return DataTransferObjectBuilder.buildOkJsonStr(null);
                } else {
                    fzckCount = String.valueOf(data);
                }
            }
            LogUtil.info(LOGGER, "【getFzckList】查询催收工单数量返回结果={}", fzckCount);
            FZCKRequestDto fzckRequestDto = new FZCKRequestDto();
            fzckRequestDto.setHouseId(projectId);
            fzckRequestDto.setPageNum(0);
            fzckRequestDto.setPageSize(Integer.valueOf(fzckCount));
            String requestStr = JsonEntityTransform.Object2Json(fzckRequestDto);
            LogUtil.info(LOGGER, "【getFzckList】查询催收工单列表请求参数={}, URL={}", requestStr, FZCKListUrl);
            String result = CloseableHttpUtil.sendPost(FZCKListUrl, requestStr);
            LogUtil.info(LOGGER, "【getFzckList】查询催收工单列表返回结果={}", result);
            JSONObject responseJson = JSONObject.parseObject(result);
            if (responseJson == null) {
                LogUtil.error(LOGGER, "【getFzckList】查询催收工单列表失败,param={}", requestStr);
                return DataTransferObjectBuilder.buildErrorJsonStr("服务错误");
            }
            int code = responseJson.getInteger("code");
            if (code != 0) {
                LogUtil.error(LOGGER, "【getFzckList】查询催收工单列表失败，message:{}", responseJson.getString("message"));
                return DataTransferObjectBuilder.buildErrorJsonStr(responseJson.getString("message"));
            }
            return DataTransferObjectBuilder.buildOkJsonStr(responseJson.getString("data"));
        } catch (Exception e) {
            LogUtil.error(LOGGER, "【getFzckList】查询催收工单列表失败,error:{}", e);
            return DataTransferObjectBuilder.buildErrorJsonStr("系统错误");
        }
    }

    /**
     * 催收系统工单详情接口<br/>
     * url: http://urge.t.ziroom.com/api/zra/getFollowDetail<br/>
     * wiki: http://wiki.ziroom.com/pages/viewpage.action?pageId=315621417
     *
     * @Author: wangxm113
     * @Date: 2017年11月10日 10时36分43秒
     */
    @Override
    public String getFollowupRecord(String billId) {
        LogUtil.info(LOGGER, "【getFollowupRecord】入参={}", billId);
        try {
            if (billId == null || billId.isEmpty()) {
                return DataTransferObjectBuilder.buildErrorJsonStr("入参不能为空！");
            }
            Map<String, Object> param = new HashMap<>();
            param.put("id", billId);
            String requestParam = JsonEntityTransform.Object2Json(param);
            LogUtil.info(LOGGER, "【getFollowupRecord】查询工单详情请求参数={}, URL={}", requestParam, followupRecordUrl);
            String resultJson = CloseableHttpUtil.sendPost(followupRecordUrl, param);
            LogUtil.info(LOGGER, "【getFollowupRecord】查询工单详情返回结果={}", resultJson);
            JSONObject responseJson = JSONObject.parseObject(resultJson);
            if (responseJson == null) {
                LogUtil.error(LOGGER, "【getFollowupRecord】查询工单详情失败,param={}", requestParam);
                return DataTransferObjectBuilder.buildErrorJsonStr("服务错误");
            }
            int code = responseJson.getInteger("code");
            if (code != 0) {
                LogUtil.error(LOGGER, "【getFollowupRecord】查询工单详情失败，message:{}", responseJson.getString("message"));
                return DataTransferObjectBuilder.buildErrorJsonStr(responseJson.getString("message"));
            }
            return DataTransferObjectBuilder.buildOkJsonStr(responseJson.getString("data"));
        } catch (Exception e) {
            LogUtil.error(LOGGER, "【getFollowupRecord】查询工单详情失败,error:{}", e);
            return DataTransferObjectBuilder.buildErrorJsonStr("系统错误");
        }
    }

    /**
     * 某合同下的跟进记录<br/>
     * url:http://urge.t.ziroom.com/api/zra/getFollowRecord<br/>
     * wiki:http://wiki.ziroom.com/pages/viewpage.action?pageId=315621419
     *
     * @Author: wangxm113
     * @Date: 2017年11月10日 14时10分17秒
     */
    @Override
    public String getRecordList(String contractCode) {
        LogUtil.info(LOGGER, "【getRecordList】入参={}", contractCode);
        try {
            if (contractCode == null || contractCode.isEmpty()) {
                return DataTransferObjectBuilder.buildErrorJsonStr("入参不能为空！");
            }
            Map<String, Object> param = new HashMap<>();
            param.put("contractCode", contractCode);
            String reqeusParam = JsonEntityTransform.Object2Json(param);
            LogUtil.info(LOGGER, "【getRecordList】查询某合同下的跟进记录请求参数={}, URL={}", reqeusParam, recordListUrl);
            String resultJson = CloseableHttpUtil.sendPost(recordListUrl, param);
            LogUtil.info(LOGGER, "【getRecordList】查询某合同下的跟进记录返回结果={}", resultJson);
            JSONObject responseJson = JSONObject.parseObject(resultJson);
            if (responseJson == null) {
                LogUtil.error(LOGGER, "【getRecordList】查询某合同下的跟进记录失败,param={}", reqeusParam);
                return DataTransferObjectBuilder.buildErrorJsonStr("服务错误");
            }
            int code = responseJson.getInteger("code");
            if (code != 0 && code != 100000) {
                LogUtil.error(LOGGER, "【getRecordList】查询某合同下的跟进记录失败，message:{}", responseJson.getString("message"));
                return DataTransferObjectBuilder.buildErrorJsonStr(responseJson.getString("message"));
            }
            return DataTransferObjectBuilder.buildOkJsonStr(responseJson.getString("data"));
        } catch (Exception e) {
            LogUtil.error(LOGGER, "【getRecordList】查询某合同下的跟进记录失败,error:{}", e);
            return DataTransferObjectBuilder.buildErrorJsonStr("系统错误");
        }
    }

    /**
     * 获取催收工单信息的数目<br/>
     * url:http://urge.t.ziroom.com/api/zra/getFollowListCount<br/>
     * wiki:http://wiki.ziroom.com/pages/viewpage.action?pageId=315621527
     *
     * @Author: wangxm113
     * @Date: 2017年11月10日 16时29分50秒
     */
    @Override
    public String getFzckCount(String projectId) {
        LogUtil.info(LOGGER, "【getFzckCount】入参={}", projectId);
        try {
            if (projectId == null || projectId.isEmpty()) {
                return DataTransferObjectBuilder.buildErrorJsonStr("入参不能为空！");
            }
            Map<String, Object> param = new HashMap<>();
            param.put("houseId", projectId);
            String requestStr = JsonEntityTransform.Object2Json(param);
            LogUtil.info(LOGGER, "【getFzckCount】查询催收工单信息的数目请求参数={}, URL={}", requestStr, FZCKCountUrl);
            String result = CloseableHttpUtil.sendPost(FZCKCountUrl, requestStr);
            LogUtil.info(LOGGER, "【getFzckCount】查询催收工单信息的数目返回结果={}", result);
            JSONObject responseJson = JSONObject.parseObject(result);
            if (responseJson == null) {
                LogUtil.error(LOGGER, "【getFzckCount】查询催收工单信息的数目失败,param={}", requestStr);
                return DataTransferObjectBuilder.buildErrorJsonStr("服务错误");
            }
            int code = responseJson.getInteger("code");
            if (code != 0) {
                LogUtil.error(LOGGER, "【getFzckCount】查询催收工单信息的数目失败，message:{}", responseJson.getString("message"));
                return DataTransferObjectBuilder.buildErrorJsonStr(responseJson.getString("message"));
            }
            return DataTransferObjectBuilder.buildOkJsonStr(responseJson.getString("data"));
        } catch (Exception e) {
            LogUtil.error(LOGGER, "【getFzckCount】查询催收工单信息的数目失败,error:{}", e);
            return DataTransferObjectBuilder.buildErrorJsonStr("系统错误");
        }
    }

    /**
     * 新增催收跟进记录<br/>
     * url:http://urge.t.ziroom.com/api/zra/saveFollowRecord<br/>
     * wiki:http://wiki.ziroom.com/pages/viewpage.action?pageId=316145711
     *
     * @Author: wangxm113
     * @Date: 2017年11月10日 16时43分41秒
     */
    @Override
    public String saveFollowupRecord(String paramStrDto) {
        LogUtil.info(LOGGER, "【saveFollowupRecord】入参={}", paramStrDto);
        try {
            if (paramStrDto == null || paramStrDto.isEmpty()) {
                return DataTransferObjectBuilder.buildErrorJsonStr("入参不能为空！");
            }

            LogUtil.info(LOGGER, "【saveFollowupRecord】新增催收跟进记录请求参数={}, URL={}", paramStrDto, saveFollowupUrl);
            String result = CloseableHttpUtil.sendPost(saveFollowupUrl, paramStrDto);
            LogUtil.info(LOGGER, "【saveFollowupRecord】新增催收跟进记录返回结果={}", result);
            JSONObject responseJson = JSONObject.parseObject(result);
            if (responseJson == null) {
                LogUtil.error(LOGGER, "【saveFollowupRecord】新增催收跟进记录失败,param={}", paramStrDto);
                return DataTransferObjectBuilder.buildErrorJsonStr("服务错误");
            }
            int code = responseJson.getInteger("code");
            if (code != 0) {
                LogUtil.error(LOGGER, "【saveFollowupRecord】新增催收跟进记录失败，message:{}", responseJson.getString("message"));
                return DataTransferObjectBuilder.buildErrorJsonStr(responseJson.getString("message"));
            }
            return DataTransferObjectBuilder.buildOkJsonStr("success");
        } catch (Exception e) {
            LogUtil.error(LOGGER, "【saveFollowupRecord】新增催收跟进记录失败,param={}", paramStrDto);
            return DataTransferObjectBuilder.buildErrorJsonStr("服务错误");
        }
    }

    @Override
    public String getZRAReceiptBillListForMS(String paramJson) {
        LogUtil.info(LOGGER, "【getZRAReceiptBillListForMS】入参:{}", paramJson);
        DataTransferObject dto = new DataTransferObject();
        try {
            PaymentBillsDto paymentBillsDto = JsonEntityTransform.json2Object(paramJson,PaymentBillsDto.class);
            if(Check.NuNObj(paymentBillsDto)){
                LogUtil.error(LOGGER, "【getZRAReceiptBillListForMS】 参数为空");
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("参数为空");
                return dto.toJsonString();
            }
            if(Check.NuNStrStrict(paymentBillsDto.getProjectId())){
                LogUtil.error(LOGGER, "【getZRAReceiptBillListForMS】 项目id为空");
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("参数为空");
                return dto.toJsonString();
            }
            //根据项目id查询公司code
            String projectJson = projectService.findProjectById(paymentBillsDto.getProjectId());
            DataTransferObject projectDto = JsonEntityTransform.json2DataTransferObject(projectJson);
            ProjectEntity projectEntity = SOAResParseUtil.getValueFromDataByKey(projectJson,"projectEntity",ProjectEntity.class);
            if(DataTransferObject.ERROR==projectDto.getCode()||Check.NuNObj(projectEntity)){
                LogUtil.error(LOGGER, "【getZRAReceiptBillListForMS】 项目不存在");
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("项目不存在");
                return dto.toJsonString();
            }
            List<String> conRentCodes = new ArrayList<>();
            if(Check.NuNStrStrict(paymentBillsDto.getConRentCode())&&Check.NuNStrStrict(paymentBillsDto.getParentConRentCode())){
                conRentCodes = finReceiBillServiceImpl.getZRAReceiptContractCode(paymentBillsDto);
            }else if(!Check.NuNStrStrict(paymentBillsDto.getParentConRentCode())){
                //@Author:lusp  @Date:2018/1/3  增加企业老数据兼容，企业老数据合同号需是主合同号+房间号
                List<RentContractEntity> parentRentContractEntitys = rentContractServiceImpl.findContractListByParentCode(paymentBillsDto.getParentConRentCode());
                RentContractEntity parentRentContractEntity = null;
                if(!Check.NuNCollection(parentRentContractEntitys)){
                	parentRentContractEntity = parentRentContractEntitys.get(0);
                }
                if(!Check.NuNObj(parentRentContractEntity)&&parentRentContractEntity.getCustomerType()==CustomerTypeEnum.ENTERPRICE.getCode()&&parentRentContractEntity.getDataVersion()==0){
                    List<RentDetailEntity> rentDetailEntities = rentContractServiceImpl.findRentDetailByContractId(parentRentContractEntity.getContractId());
                    if(Check.NuNCollection(rentDetailEntities)){
                        LogUtil.error(LOGGER, "【getZRAReceiptBillListForMS】 企业合同老数据对应合同明细为空");
                        dto.setErrCode(DataTransferObject.ERROR);
                        dto.setMsg("参数为空");
                        return dto.toJsonString();
                    }
                    for(RentDetailEntity rentDetailEntity:rentDetailEntities){
                        conRentCodes.add(parentRentContractEntity.getConRentCode()+"+"+rentDetailEntity.getRoomCode());
                    }
                }else{
                    conRentCodes = rentContractServiceImpl.getBatchContractCodeByParentCode(paymentBillsDto.getParentConRentCode());
                }
            }else {
                conRentCodes.add(paymentBillsDto.getConRentCode());
            }
            if(Check.NuNCollection(conRentCodes)){
                LogUtil.info(LOGGER, "【getZRAReceiptBillListForMS】 需要收款的合同号为空");
                return dto.toJsonString();
            }
            ZRAReceiptBillDto zraReceiptBillDto = new ZRAReceiptBillDto();
            BeanUtils.copyProperties(paymentBillsDto,zraReceiptBillDto);
            zraReceiptBillDto.setOutContractList(conRentCodes);
            zraReceiptBillDto.setBussinessUnitCode(bussinessUnitCode);
            zraReceiptBillDto.setCompanyCode(projectEntity.getFcompanyid());
            if(!Check.NuNStr(paymentBillsDto.getState())){
            	zraReceiptBillDto.setReceiptStatus(Integer.parseInt(paymentBillsDto.getState()));
            }
            String resultStr = getZRAReceiptBill(JsonEntityTransform.Object2Json(zraReceiptBillDto));
            return resultStr;
        } catch (Exception e) {
            LogUtil.error(LOGGER, "【getZRAReceiptBillListForMS】 param={},error:{}", paramJson, e);
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("系统错误");
            return dto.toJsonString();
        }
    }

    @Override
    public String getZRAReceiptBill(String paramJson) {
        LogUtil.info(LOGGER, "【getZRAReceiptBill】入参:{}", paramJson);
        DataTransferObject dto = new DataTransferObject();
        try {
            ZRAReceiptBillDto zraReceiptBillDto = JsonEntityTransform.json2Object(paramJson,ZRAReceiptBillDto.class);
            if(Check.NuNObj(zraReceiptBillDto)){
                LogUtil.error(LOGGER, "【getZRAReceiptBill】 参数为空");
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("参数为空");
                return dto.toJsonString();
            }
            if(Check.NuNCollection(zraReceiptBillDto.getOutContractList())){
                LogUtil.error(LOGGER, "【getZRAReceiptBill】 合同号集合为空");
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("合同号集合为空");
                return dto.toJsonString();
            }
            //调用财务接口,查询收款单列表
            LogUtil.info(LOGGER, "【getZRAReceiptBill】 请求财务入参paramJson:{}",JsonEntityTransform.Object2Json(zraReceiptBillDto));
            String resultStr = CloseableHttpUtil.sendPost(ZRAReceiptBillUrl, JsonEntityTransform.Object2Json(zraReceiptBillDto));
            JSONObject responseJson = JSONObject.parseObject(resultStr);
            if (Check.NuNObj(responseJson)) {
                LogUtil.error(LOGGER, "【getZRAReceiptBill】 调用财务查询收款单列表返回json为空");
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("系统错误");
                return dto.toJsonString();
            }
            int code = responseJson.getInteger("code");
            if (Check.NuNObj(code) || code != ResponseCodeEnum.SUCCESS.getCode()) {
                LogUtil.error(LOGGER, "【getZRAReceiptBill】 调用财务查询收款单列表失败，message:{}", responseJson.getString("message"));
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg(responseJson.getString("message"));
                return dto.toJsonString();
            }
            JSONObject data = responseJson.getJSONObject("data");
            dto.putValue("totalCount", data.get("totalCount"));
            dto.putValue("resList", data.get("resList"));
        } catch (Exception e) {
            LogUtil.error(LOGGER, "【getZRAReceiptBill】 error:{},param={}", e, paramJson);
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("系统错误");
        }
        return dto.toJsonString();
    }

    @Override
    public String createReceipt(String param) {
        LogUtil.info(LOGGER,"【createReceipt】入参={}",param);
        ReceiptCreateRequest receiptCreateRequest = JsonEntityTransform.json2Object(param, ReceiptCreateRequest.class);
        DataTransferObject dto = new DataTransferObject();
        if (Check.NuNObj(receiptCreateRequest)){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("参数为空");
            return dto.toJsonString();
        }
        receiptCreateRequest.setSysCode(sysCode);
        receiptCreateRequest.setIsCheckContract(1);
        receiptCreateRequest.setIsContract(0);
        try{
            receiptCreateRequest.getReceiptList().forEach(v -> v.setCallUrl(receiptBillCallUrl));//todo 添加地址
            LogUtil.info(LOGGER,"【createReceipt】创建收款单 api接口:{},jsonStr:{}",finance_createReceipt_url,JsonEntityTransform.Object2Json(receiptCreateRequest));
            String resultJson = CloseableHttpUtil.sendPost(finance_createReceipt_url, JsonEntityTransform.Object2Json(receiptCreateRequest));
            LogUtil.info(LOGGER,"【createReceipt】创建收款单 财务返回结果={}",resultJson);
            ReceiptCreateResponse receiptCreateResponse = JsonEntityTransform.json2Object(resultJson, ReceiptCreateResponse.class);
            int isSyncFinnace = SyncToFinEnum.SUCCESS.getCode();
            if (receiptCreateResponse.getCode() != ResponseCodeEnum.SUCCESS.getCode()){
                LogUtil.error(LOGGER,"【createReceipt】创建收款单失败,财务返回结果={}",resultJson);
                isSyncFinnace = SyncToFinEnum.FAIL.getCode();
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg(receiptCreateResponse.getMessage());
                return dto.toJsonString();
            }
            List<ReceiptCreateRequest.Receipt> receiptList = receiptCreateRequest.getReceiptList();
            List<ReceiptEntity> receiptEntities = new ArrayList<>();
            for (ReceiptCreateRequest.Receipt receipt : receiptList){
                ReceiptEntity receiptEntity = new ReceiptEntity();
                receiptEntity.setBillNum(receipt.getBillNum());
                receiptEntity.setIsSyncFinance(isSyncFinnace);
                receiptEntities.add(receiptEntity);
            }
            int count = receiptServiceImpl.updateBatchByBillNum(receiptEntities);
            LogUtil.info(LOGGER,"更新结果count={}",count);
        }catch (Exception e){
            LogUtil.error(LOGGER,"【createReceipt】创建收款单异常 error={}",e);
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("服务错误");
        }
        return dto.toJsonString();
    }

    @Override
    public String receiptBillCallbackUpdateStatus(String paramJson) {
        LogUtil.info(LOGGER, "【receiptBillCallbackUpdateStatus】请求入参：paramJson={}", paramJson);
        DataTransferObject dto = new DataTransferObject();
        try {
            List<String> billNumList = JsonEntityTransform.json2ObjectList(paramJson,String.class);
            if(Check.NuNCollection(billNumList)){
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("参数为空");
                return dto.toJsonString();
            }
            Set<String> receiptBillNums = new HashSet<>();
            for (String billNum:billNumList) {
                // 查询财务应收账单信息
                ReceiptBillRequest receiptBillRequest = new ReceiptBillRequest();
                receiptBillRequest.setBillNum(billNum);
                String receiveBillJson = getReceivableBillInfo(JsonEntityTransform.Object2Json(receiptBillRequest));
                LogUtil.info(LOGGER, "【receiptBillCallbackUpdateStatus】查询财务应收账单信息返回：{}", receiveBillJson);
                dto = JsonEntityTransform.json2DataTransferObject(receiveBillJson);
                if(dto.getCode() == DataTransferObject.ERROR){
                    LogUtil.error(LOGGER, "【receiptBillCallbackUpdateStatus】查询财务应收账单出错,Msg:{}", dto.getMsg());
                    dto.setErrCode(DataTransferObject.ERROR);
                    dto.setMsg("参数为空");
                    return dto.toJsonString();
                }
                List<ReceiptBillResponse> receiptBillResponses = SOAResParseUtil.getListValueFromDataByKey(receiveBillJson,"listStr",ReceiptBillResponse.class);
                for(ReceiptBillResponse receiptBillResponse:receiptBillResponses){
                    if(VerificateStatusEnum.DONE.getCode() == receiptBillResponse.getVerificateStatus()){
                        //查询对应的收款单编号
                        List<String> strings = receiptServiceImpl.findReceiNumsByBillNum(billNum);
                        for(String receiptBillNum:strings){
                            receiptBillNums.add(receiptBillNum);
                        }
                    }
                }
            }
            //更改收款单本地收款单为已收款
            if(!Check.NuNCollection(receiptBillNums)){
                List<ReceiptEntity> tradingReceiptEntities = new ArrayList<>();
                for(String receiptBillNum:receiptBillNums){
                    ReceiptEntity tradingReceiptEntity = new ReceiptEntity();
                    tradingReceiptEntity.setBillNum(receiptBillNum);
                    tradingReceiptEntity.setReceiptStatus(ReceiptStatusEnum.YSK.getCode());
                    tradingReceiptEntities.add(tradingReceiptEntity);
                }
                receiptServiceImpl.updateBatchByBillNum(tradingReceiptEntities);
            }
        } catch (Exception e) {
            LogUtil.error(LOGGER, "【receiptBillCallbackUpdateStatus】系统错误,paramJson={},e:{}", paramJson, e);
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("系统错误");
        }
        return dto.toJsonString();
    }

    /**
     *  支付成功回调
     *
     *  支付成功回调——客户充值智能电费
     *
     *   1. 根据应收账单编号，确认为智能电表充值
     *
     *   2. 去智能平台充值电表，成功
     *
     *   3. 修改应收账单状态
     *
     *   4. 落地充值电表记录信息到自如寓库，成功
     *
     *   5. 以上失败，处理失败流程，并返回失败信息
     *
     * @author yd
     * @created  
     * @param 
     * @return 
     */
	@Override
	public String paymentCallback(String paramJson) {
		LogUtil.info(LOGGER, "【paymentCallback】请求入参：paramJson={}", paramJson);
        DataTransferObject dto = new DataTransferObject();
        try{
        	if(Check.NuNStr(paramJson)){
        		dto.setErrCode(DataTransferObject.ERROR);
	            dto.setMsg("参数为空！");
	            return dto.toJsonString();
        	}

            ArrayList<String> callCostCodes = new ArrayList<>();
            JSONObject callBackObj = JSONObject.parseObject(paramJson);
            JSONArray receiptArr = callBackObj.getJSONArray("list");
            if (Check.NuNObj(receiptArr) || receiptArr.size() <= 0){
                LogUtil.error(LOGGER,"paymentCallback 返回账单数据为空");
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("账单列表为空");
                return dto.toJsonString();
            }


            String billNum = receiptArr.getJSONObject(0).getString("billNum");
            boolean isPayFinishOnce = Boolean.TRUE.booleanValue();
            for (int i = 0; i < receiptArr.size(); i++) {
                JSONObject receiptObj = receiptArr.getJSONObject(i);
                callCostCodes.add(receiptObj.getString("costCode"));
                isPayFinishOnce = receiptObj.getString("billAmount").equals(receiptObj.getString("receivedAmount")) ? isPayFinishOnce : Boolean.FALSE.booleanValue();
            }

            List<String> finLifeFeeCode = CostCodeEnum.getFinLifeFeeCode();
            //去交集
            Collection<String> intersections = CollectionUtils.intersection(finLifeFeeCode, callCostCodes);
            if (!Check.NuNCollection(intersections)){
                LogUtil.info(LOGGER,"支付的是生活费用不需要处理,智能电费除外");
                if(intersections.contains(CostCodeEnum.ZRYDF.getCode())){
                    //智能电费处理流程
                   intellectWattPaymentCallBack(paramJson,dto);
                }
               return dto.toJsonString();
            }

            ReceiptBillRequest receiptBillRequest = new ReceiptBillRequest();
            receiptBillRequest.setBillNum(billNum);
            DataTransferObject oneDto = JsonEntityTransform.json2DataTransferObject(this.getReceivableBillInfo(JSONObject.toJSONString(receiptBillRequest)));
            if (oneDto.getCode() == DataTransferObject.ERROR){
                LogUtil.error(LOGGER,"查询应收账单错误，dto={}",oneDto.toJsonString());
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("查询应收账单错误！");
                return dto.toJsonString();
            }

            List<ReceiptBillResponse> receiptBills = oneDto.parseData("listStr", new TypeReference<List<ReceiptBillResponse>>() {});
            Integer periods = receiptBills.get(0).getPeriods();
            if (periods.intValue() != BillPeriodsEnum.FIRST.getCode()){
                LogUtil.info(LOGGER,"付款非首期不处理直接返回");
                return dto.toJsonString();
            }
            //出房合同号
            String outContractCode = receiptBills.get(0).getOutContractCode();
            RentContractEntity rentContractEntity = rentContractServiceImpl.findContractByRentCode(outContractCode);
            if (Check.NuNObj(rentContractEntity)){
                LogUtil.info(LOGGER,"合同为空，contractCode={}",outContractCode);
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("自如寓合同为空");
                return dto.toJsonString();
            }
            String conStatusCode = rentContractEntity.getConStatusCode();
            //合同关闭处理  合同作废 生成付款单
            if (conStatusCode.equals(ContractStatusEnum.YGB.getStatus()) || conStatusCode.equals(ContractStatusEnum.YZF.getStatus())){
                LogUtil.info(LOGGER,"合同已关闭，需要生成付款单退款");
                DataTransferObject finPayVoucher = financeCommonLogic.createFinPayVoucher(rentContractEntity,Boolean.TRUE.booleanValue());
                return finPayVoucher.toJsonString();
            }
            //当次回调数据如果已收齐，则需要查询财务首期账单是否收齐
            if (isPayFinishOnce){
                receiptBillRequest.setBillNum(null);
                receiptBillRequest.setOutContractCode(outContractCode);
                receiptBillRequest.setPeriods(BillPeriodsEnum.FIRST.getCode());
                DataTransferObject firstDto = JsonEntityTransform.json2DataTransferObject(this.getReceivableBillInfo(JSONObject.toJSONString(receiptBillRequest)));
                if (firstDto.getCode() == DataTransferObject.ERROR){
                    LogUtil.error(LOGGER,"查询应收账单错误，dto={}",firstDto.toJsonString());
                    dto.setErrCode(DataTransferObject.ERROR);
                    dto.setMsg("查询应收账单错误！");
                    return dto.toJsonString();
                }
                int isPay = (int)firstDto.getData().get("isPay");
                if (isPay == VerificateStatusEnum.DONE.getCode()){
                    //首次账单已支付完成
                    //更新合同状态  同步财务状态
                    String updateStr = rentContractServiceProxy.customerSignatureForMs(rentContractEntity.getContractId());
                    //续约合同时,异步调用生成pdf方法,生成pdf文件
                    if(ContractSignTypeEnum.RENEW.getValue().equals(rentContractEntity.getFsigntype())){
                        Thread task = new Thread(){
                            @Override
                            public void run() {
                                checkSignServiceProxy.generatePDFContractAndSignature(rentContractEntity.getContractId());
                            }
                        };
                        SendThreadPool.execute(task);
                    }
                    LogUtil.info(LOGGER, "[paymentCallback]更新合同状态返回：{}", updateStr);
                    DataTransferObject updateObj = JsonEntityTransform.json2DataTransferObject(updateStr);
                    if(updateObj.getCode() == DataTransferObject.ERROR){
                        LogUtil.error(LOGGER, "[paymentCallback]更新合同状态失败：{}", updateStr);
                        dto.setErrCode(DataTransferObject.ERROR);
                        dto.setMsg("系统错误");
                        return dto.toJsonString();
                    }
                    //同步财务
                    List<RentContractAndDetailEntity> rentContractAndDetailEntities = rentContractServiceImpl.findContractAndDetailsByContractId(rentContractEntity.getContractId());
                    if(Check.NuNCollection(rentContractAndDetailEntities)){
                        LogUtil.error(LOGGER, "[paymentCallback]查询合同子表记录为空conRentCode:{}", rentContractEntity.getContractId());
                        dto.setErrCode(DataTransferObject.ERROR);
                        dto.setMsg("系统错误");
                        return dto.toJsonString();
                    }
                    RentContractAndDetailEntity rentContractAndDetailEntity = rentContractAndDetailEntities.get(0);
                    SyncContractVo syncContractVo = new SyncContractVo();
                    syncContractVo.setCrmContractId(rentContractEntity.getContractId());
                    syncContractVo.setStatusCode(FinaContractStatusEnum.getByStatusCodeAndAuditState(rentContractAndDetailEntity.getConStatusCode(), rentContractAndDetailEntity.getDeliveryState(),rentContractAndDetailEntity.getConAuditState()));
                    syncContractVo.setRentContractCode(rentContractEntity.getConRentCode());
                    LogUtil.info(LOGGER, "【paymentCallback】更新财务合同状态入参param:{}", JsonEntityTransform.Object2Json(syncContractVo));
                    financeBaseCall.updateContract(syncContractVo);

                    //更新邀约记录
                    String signInviteStr = houseSignInviteRecordService.updateIsDealByContractId(rentContractEntity.getContractId());
                    DataTransferObject signInviteObj = JsonEntityTransform.json2DataTransferObject(signInviteStr);
                    if(signInviteObj.getCode() == DataTransferObject.ERROR){
                        LogUtil.error(LOGGER, "【paymentCallback】更新邀约记录失败，出房合同号：{},异常信息：{}",outContractCode,signInviteObj.getMsg());
                        dto.setErrCode(DataTransferObject.ERROR);
                        dto.setMsg("系统错误");
                        return dto.toJsonString();
                    }
                    //查询管家电话
                    DataTransferObject employeeDto = JsonEntityTransform.json2DataTransferObject(employeeService.findEmployeeByCode(rentContractEntity.getFhandlezocode()));
                    EmployeeEntity employeeEntity = employeeDto.parseData("employeeEntity", new TypeReference<EmployeeEntity>() {});
                    if(!Check.NuNObj(employeeEntity) && !Check.NuNStr(employeeEntity.getFmobile())){
                        //发送短信给管家
                        SmsRequest smsRequest = new SmsRequest();
                        smsRequest.setMobile(employeeEntity.getFmobile());
                        Map<String, String> paramsMap=new HashMap<>();
                        paramsMap.put(SysConst.MOBILE_NATION_CODE_KEY, "86");
                        paramsMap.put("{1}",rentContractEntity.getProName());
                        paramsMap.put("{2}",rentContractEntity.getHouseRoomNo());
                        smsRequest.setParamsMap(paramsMap);
                        smsRequest.setSmsCode(ValueUtil.getStrValue(SmsTemplateCodeEnum.HAS_PAY_NEED_DELIVERY_ZO_MSG.getCode()));
                        LogUtil.info(LOGGER, "【paymentCallback】发送短息给管家 mobile:{},projectName:{},roomName:{}",employeeEntity.getFmobile(),rentContractEntity.getProName(),rentContractEntity.getHouseRoomNo());
                        smsTemplateService.sendSmsByCode(JsonEntityTransform.Object2Json(smsRequest));
                    }


                }

            }

    		return dto.toJsonString();
        }catch(Exception e){
        	LogUtil.error(LOGGER, "[paymentCallback]异常：{}", e);
			dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("系统错误");
        	return dto.toJsonString();
        }
	}


	/**
	 *  智能电表支付成功 回调
     *
     *  1. 根据应收账单编号 查询智能电费账单
     *
     *  2. 是智能电费，去充值电费，并做充值记录
     *
     *   说明： 如果
	 * @author yd
	 * @created
	 * @param
	 * @return
	 */
	private void intellectWattPaymentCallBack(String paramJson,DataTransferObject dto){
	    try {
            JSONObject callBackObj = JSONObject.parseObject(paramJson);
            JSONArray receiptArr = callBackObj.getJSONArray("list");

            for (int i = 0; i < receiptArr.size(); i++) {
                JSONObject receiptObj = receiptArr.getJSONObject(i);
                String costCode = receiptObj.getString("costCode");
                if (!Check.NuNStr(costCode) && costCode.equals(CostCodeEnum.ZRYDF.getCode())){
                    log.info("【电表费用逻辑处理】receiptObj={}",receiptObj.toJSONString());
                    String billNum = receiptObj.getString("billNum");
                    FinReceiBillDetailEntity finReceiBillDetailEntity  = this.finReceiBillDetailServiceImpl.getReceiBillDetailByFBillNum(billNum);
                    if(finReceiBillDetailEntity == null||Check.NuNStr(finReceiBillDetailEntity.getBillFid())){
                        log.error("【异常应收账单明细,请关注】billNum={}",billNum);
                        continue;
                    }
                    FinReceiBillEntity finReceiBillEntity = this.finReceiBillServiceImpl.selectByFid(finReceiBillDetailEntity.getBillFid());
                    if (finReceiBillEntity == null){
                        log.error("【异常应收账单,请关注】billFid={}",finReceiBillDetailEntity.getBillFid());
                        continue;
                    }
                    if (finReceiBillDetailEntity.getIsSmart() != 1){
                        log.info("【非智能电费不做处理】billType={}",finReceiBillEntity.getBillType());
                        continue;
                    }
                    finReceiBillEntity.setBillState(ReceiBillStateEnum.YSK.getCode());
                    finReceiBillServiceImpl.updateFinReceiBillByFid(finReceiBillEntity);
                    //合同
                    RentContractEntity rentContractEntity  = this.rentContractServiceImpl.findContractBaseByContractId(finReceiBillEntity.getContractId());

                    if (rentContractEntity == null){
                        log.error("【智能电费充值，合同已不存在,请关注】contractId={}",finReceiBillEntity.getContractId());
                        continue;
                    }

                    //实收金额
                    Integer receivedAmount = receiptObj.getInteger("receivedAmount");

                    if (receivedAmount<=0){
                        log.error("【智能电费充值，实收金额不合法,请关注】receivedAmount={}",receivedAmount);
                        continue;
                    }

                    //电费单价
                    Double wattPrice =  this.intellectPlatformLogic.findWattCostStandard(rentContractEntity.getProjectId());

                    if (wattPrice<=0){
                        log.error("【智能电费充值，电费单价不合法,请关注】wattPrice={}",wattPrice);
                        continue;
                    }

                    WattMeterChargingDto p = new WattMeterChargingDto();
                    p.setHireContractCode(rentContractEntity.getConRentCode());
                    p.setPositionRank1(rentContractEntity.getProjectId());
                    p.setPositionRank2(rentContractEntity.getRoomId());
                    p.setTradeNum(finReceiBillDetailEntity.getBillNum());

                    // 充电量 = 实收金额 / 电费单价
                    BigDecimal amount = BigDecimal.valueOf(receivedAmount);
                    amount = amount.divide(BigDecimal.valueOf(100));
                    amount = amount.divide(BigDecimal.valueOf(wattPrice),2,BigDecimal.ROUND_HALF_UP);
                    p.setAmount(amount.floatValue());
                    log.info("【智能电表充值:充电量 = 实收金额 / 电费单价】实收金额:{}分,电费单价:{}元,充电量:{}",receivedAmount,wattPrice,p.getAmount());

                    IntellectWattMeterSnapshotEntity intellectWattMeterSnapshot =  this.intellectPlatformLogic.rechargeElectricityBill(null,p);

                    intellectWattMeterSnapshot.setBillFid(finReceiBillEntity.getFid());
                    intellectWattMeterSnapshot.setProjectId(rentContractEntity.getProjectId());
                    intellectWattMeterSnapshot.setRoomId(rentContractEntity.getRoomId());
                    intellectWattMeterSnapshot.setCreateType(CreaterTypeEnum.ZIROOM_USER.getCode());
                    intellectWattMeterSnapshot.setCreateId(rentContractEntity.getCustomerUid());
                    intellectWattMeterSnapshot.setAmount(amount.doubleValue());
                    intellectWattMeterSnapshot.setPrice(wattPrice);
                    try{
                        WattElectricMeterStateDto wattElectricMeterStateDto = new WattElectricMeterStateDto();
                        wattElectricMeterStateDto.setHireContractCode(rentContractEntity.getConRentCode());
                        wattElectricMeterStateDto.setPositionRank1(rentContractEntity.getProjectId());
                        wattElectricMeterStateDto.setPositionRank2(rentContractEntity.getRoomId());
                        ElectricMeterStateVo electricMeterState = intellectPlatformLogic.getElectricMeterState(wattElectricMeterStateDto);
                        if (electricMeterState != null){
                            intellectWattMeterSnapshot.setStartReading(electricMeterState.getConsumeAmount());
                        }
                    }catch (Exception e){
                        //防御性容错
                        log.error("查询电表错误");
                    }
                    int a = this.intellectWattMeterSnapshotImpl.insertIntellectWattMeterSnapshot(intellectWattMeterSnapshot);
                    if (a<=0){
                        log.error("【智能电费充值记录保存失败】请关注intellectWattMeterSnapshot={}",intellectWattMeterSnapshot.toJsonStr());
                        continue;
                    }
                }
            }
        }catch (Exception e){
            log.error("【智能电费充值异常】请关注paramJson={},e={}",paramJson,e);
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("智能电费充值异常");
        }


    }

    @Override
    public String updateReceipt(String paramJson) {
        LogUtil.info(LOGGER, "【updateReceipt】入参:{}", paramJson);
        DataTransferObject dto = new DataTransferObject();
        try {
            ReceiptUpdateRequest receiptUpdateRequest = JsonEntityTransform.json2Object(paramJson, ReceiptUpdateRequest.class);
            if (Check.NuNObj(receiptUpdateRequest)) {
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("实体不存在");
                return dto.toJsonString();
            }
            String result = CloseableHttpUtil.sendPost(finance_updateReceipt_url, JsonEntityTransform.Object2Json(receiptUpdateRequest));
            LogUtil.info(LOGGER,"【updateReceipt】财务修改收款单返回结果result={}",result);
            JSONObject jsonObject = JSONObject.parseObject(result);
            if (Check.NuNObj(jsonObject)) {
                LogUtil.error(LOGGER, "【updateReceipt】 修改收款单到财务返回json为空");
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("系统错误");
                return dto.toJsonString();
            }
            Integer code = jsonObject.getInteger("code");
            if (Check.NuNObj(code) || code != ResponseCodeEnum.SUCCESS.getCode() ) {
                LogUtil.error(LOGGER, "【updateContract】 调用财务修改收款单失败，result:{},message:{}", result, jsonObject.getString("message"));
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg(jsonObject.getString("message"));
                return dto.toJsonString();
            }
        } catch (Exception e) {
            LogUtil.error(LOGGER, "【updateContract】 error:{},paramJson={}", e, paramJson);
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("系统错误");
        }
        return dto.toJsonString();
    }

    @Override
    public String checkContractAudit(String conRentCode) {
        LogUtil.info(LOGGER, "【checkContractAudit】方法开始,conRentCode:{}", conRentCode);
        DataTransferObject dto = new DataTransferObject();
        try {
            Boolean isAudit = financeBaseCall.callFinaCheckContractAudit(conRentCode);
            dto.putValue("isAudit",isAudit);
        } catch (Exception e) {
            LogUtil.error(LOGGER, "【checkContractAudit】 error:{},conRentCode={}", e, conRentCode);
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("系统错误");
        }
        return dto.toJsonString();
    }

    @Override
    public String checkContractAuditForEspOld(String contractId, String conRentCode) {
        LogUtil.info(LOGGER, "【checkContractAuditForEspOld】入参:contractId{},conRentCode:{}", contractId,conRentCode);
        DataTransferObject dto = new DataTransferObject();
        try {
            List<RentDetailEntity> rentDetailEntities = rentContractServiceImpl.findRentDetailByContractId(contractId);
            if(Check.NuNCollection(rentDetailEntities)){
                LogUtil.error(LOGGER, "【checkContractAuditForEspOld】查询合同子表为空,contractId{}", contractId);
                return DataTransferObjectBuilder.buildErrorJsonStr("合同子表为空");
            }
            Boolean isAudit = true;
            for(RentDetailEntity rentDetailEntity:rentDetailEntities){
                isAudit = financeBaseCall.callFinaCheckContractAudit(conRentCode+"+"+rentDetailEntity.getRoomCode());
                if(!isAudit){
                    break;
                }
            }
            dto.putValue("isAudit",isAudit);
        }catch (Exception e){
            LogUtil.error(LOGGER, "【checkContractAuditForEspOld】 error:{},contractId{},conRentCode={}", e, contractId,conRentCode);
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("系统错误");
        }
        return dto.toJsonString();
    }

    @Override
    public String getReceiptListByBillnum(String paramJson) {
        LogUtil.info(LOGGER, "【getReceiptListByBillnum】入参:{}", paramJson);
        DataTransferObject dto = new DataTransferObject();
        try {
            ReceiptQueryRequest receiptQueryRequest = JsonEntityTransform.json2Object(paramJson, ReceiptQueryRequest.class);
            if (Check.NuNObj(receiptQueryRequest)) {
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("实体不存在");
                return dto.toJsonString();
            }
            String resultJson = CloseableHttpUtil.sendPost(finance_queryReceipt_url, JsonEntityTransform.Object2Json(receiptQueryRequest));
            LogUtil.info(LOGGER,"【getReceiptListByBillnum】查询财务收款单返回结果resultJson:{}",resultJson);
            ReceiptQueryResponse receiptQueryResponse = JsonEntityTransform.json2Object(resultJson, ReceiptQueryResponse.class);
            if (Check.NuNObj(receiptQueryResponse)) {
                LogUtil.error(LOGGER, "【getReceiptListByBillnum】 查询财务收款单返回json为空");
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("系统错误");
                return dto.toJsonString();
            }
            if (Check.NuNObj(receiptQueryResponse.getCode()) || receiptQueryResponse.getCode() != ResponseCodeEnum.SUCCESS.getCode() ) {
                LogUtil.error(LOGGER, "【getReceiptListByBillnum】 调用财务查询收款单失败，resultJson:{},message:{}", resultJson, receiptQueryResponse.getMessage());
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg(receiptQueryResponse.getMessage());
                return dto.toJsonString();
            }
            dto.putValue("receiptQueryResponse",receiptQueryResponse);
        } catch (Exception e) {
            LogUtil.error(LOGGER, "【getReceiptListByBillnum】 error:{},paramJson={}", e, paramJson);
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("系统错误");
        }
        return dto.toJsonString();
    }
}
