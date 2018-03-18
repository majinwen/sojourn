package com.ziroom.zrp.service.trading.proxy.commonlogic;

import com.alibaba.fastjson.JSON;
import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.SOAResParseUtil;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.services.basedata.api.inner.CityTemplateService;
import com.ziroom.minsu.services.basedata.entity.entityenum.ServiceLineEnum;
import com.ziroom.minsu.services.common.utils.DateSplitUtil;
import com.ziroom.minsu.valenum.zrpenum.ContractTradingEnum002;
import com.ziroom.zrp.houses.entity.EmployeeEntity;
import com.ziroom.zrp.houses.entity.ProjectEntity;
import com.ziroom.zrp.houses.entity.RoomInfoEntity;
import com.ziroom.zrp.service.houses.api.EmployeeService;
import com.ziroom.zrp.service.houses.api.ProjectService;
import com.ziroom.zrp.service.houses.api.RoomService;
import com.ziroom.zrp.service.trading.dto.ContractRoomDto;
import com.ziroom.zrp.service.trading.dto.PaymentTermsDto;
import com.ziroom.zrp.service.trading.dto.PersonalInfoDto;
import com.ziroom.zrp.service.trading.dto.customer.Cert;
import com.ziroom.zrp.service.trading.dto.customer.Profile;
import com.ziroom.zrp.service.trading.dto.finance.*;
import com.ziroom.zrp.service.trading.entity.SyncContractVo;
import com.ziroom.zrp.service.trading.pojo.ContractPojo;
import com.ziroom.zrp.service.trading.pojo.PaymentBaseDataPojo;
import com.ziroom.zrp.service.trading.pojo.RoomPojo;
import com.ziroom.zrp.service.trading.proxy.calculation.PaymentMethod;
import com.ziroom.zrp.service.trading.service.FinReceiBillServiceImpl;
import com.ziroom.zrp.service.trading.service.PaymentBillServiceImpl;
import com.ziroom.zrp.service.trading.service.RentContractServiceImpl;
import com.ziroom.zrp.service.trading.service.RentEpsCustomerServiceImpl;
import com.ziroom.zrp.service.trading.utils.CustomerLibraryUtil;
import com.ziroom.zrp.service.trading.utils.builder.DataTransferObjectBuilder;
import com.ziroom.zrp.service.trading.utils.factory.PaymentMethodFactory;
import com.ziroom.zrp.service.trading.valenum.CertTypeEnum;
import com.ziroom.zrp.service.trading.valenum.ContractSignTypeEnum;
import com.ziroom.zrp.service.trading.valenum.ContractStatusEnum;
import com.ziroom.zrp.service.trading.valenum.SyncToFinEnum;
import com.ziroom.zrp.service.trading.valenum.finance.FinaContractStatusEnum;
import com.ziroom.zrp.trading.entity.*;
import com.zra.common.exception.FinServiceException;
import com.zra.common.exception.ZrpServiceException;
import com.zra.common.utils.DateUtilFormate;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>财务处理通用逻辑</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd
 * @version 1.0
 * @Date Created in 2018年01月05日 18:22
 * @since 1.0
 */
@Component("trading.financeCommonLogic")
public class FinanceCommonLogic {

    private static final Logger LOGGER = LoggerFactory.getLogger(FinanceCommonLogic.class);

    @Resource(name="trading.financeBaseCall")
    private FinanceBaseCall financeBaseCall;

    @Resource(name = "trading.paymentBillServiceImpl")
    private PaymentBillServiceImpl paymentBillServiceImpl;

    @Resource(name = "houses.roomService")
    private RoomService roomService;

    @Resource(name = "basedata.cityTemplateService")
    private CityTemplateService cityTemplateService;

    @Resource(name = "trading.rentContractServiceImpl")
    private RentContractServiceImpl rentContractServiceImpl;

    @Resource(name="houses.projectService")
    private ProjectService projectService;

    @Resource(name="houses.employeeService")
    private EmployeeService employeeService;

    @Resource(name="trading.rentEpsCustomerServiceImpl")
    private RentEpsCustomerServiceImpl rentEpsCustomerServiceImpl;

    @Resource(name="trading.finReceiBillServiceImpl")
    private FinReceiBillServiceImpl finReceiBillServiceImpl;

    /**
     * 创建 付款单
     * 来源：1.关闭合同 2.作废合同  3.在线支付超时关闭合同退款
     * @param rentContractEntity
     * @param isOnline
     * @return
     */
    public DataTransferObject createFinPayVoucher (RentContractEntity rentContractEntity,boolean isOnline){
        DataTransferObject dto = new DataTransferObject();
        List<PaymentBillEntity> paymentBillEntityList = new ArrayList<>();
        List<PaymentBillDetailEntity> paymentBillDetailEntityList = new ArrayList<>();
        List<PayVoucherReqDto> reqDtos = this.buildPayVoucherData(rentContractEntity, paymentBillEntityList, paymentBillDetailEntityList,isOnline);
        LogUtil.info(LOGGER,"请求创建付款单数据request={}", JsonEntityTransform.Object2Json(reqDtos));
        if (!Check.NuNCollection(reqDtos)) {
            //保存付款单
            Integer saveAffect = this.paymentBillServiceImpl.savePaymentBill(paymentBillEntityList, paymentBillDetailEntityList);
            if (saveAffect < 1) {
                LogUtil.info(LOGGER, "【关闭合同】保存付款单,更改影响行数:{}", saveAffect);
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("保存出错");
                return dto;
            }
            try {
                financeBaseCall.createPayVouchers(reqDtos);
            } catch (FinServiceException e) {
                e.printStackTrace();
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg(e.getMessage());
                return dto;
            }
            //更改付款单同步财务状态
            int affectRecord = this.paymentBillServiceImpl.updatePaymentSyncStatus(paymentBillEntityList);
            LogUtil.info(LOGGER, "【关闭合同】同步失败,更改影响行数:{}", affectRecord);
            return dto;

        }
        return dto;
    }



    /**
     * 构建付款单数据
     *
     * @param rentContractEntity 合同对象
     * @param isOnline
     * @return PayVoucherReqDto
     * @author cuigh6
     * @Date 2017年10月16日
     */
    private List<PayVoucherReqDto> buildPayVoucherData(RentContractEntity rentContractEntity, List<PaymentBillEntity> paymentBillEntityList,
                                                       List<PaymentBillDetailEntity> paymentBillDetailEntityList, boolean isOnline) {
        ReceiptBillListRequestDto receiptBillListRequestDto = new ReceiptBillListRequestDto();
        receiptBillListRequestDto.setContractCode(rentContractEntity.getConRentCode());
        receiptBillListRequestDto.setReceiptStatus("0");// 已收款状态
        List<ReceiptBillListResponseDto> payDetailList = null;
        try {
            payDetailList = financeBaseCall.getReceiptBillListByContract(receiptBillListRequestDto);
        } catch (FinServiceException e) {
            e.printStackTrace();
        }
        if (payDetailList == null){
            return null;
        }
        List<PayVoucherResponse> hasPayVouchers = new ArrayList<>();
        if (isOnline){
            try {
                hasPayVouchers = financeBaseCall.getPayVouchers(rentContractEntity.getConRentCode());
            } catch (FinServiceException e) {
                e.printStackTrace();
            }
        }

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
            if (!Check.NuNCollection(hasPayVouchers)){
                boolean isBreak = Boolean.FALSE.booleanValue();
                for (int j = 0; j < hasPayVouchers.size(); j++) {
                    String paySerialNum = hasPayVouchers.get(j).getPaySerialNum();
                    if (paySerialNum.equals(responseDto.getPaySerialNum())){
                        isBreak = Boolean.TRUE.booleanValue();
                        break;
                    }
                }
                if (isBreak){
                    continue;
                }
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
     * 获取计算金额的基础数据
     *
     * @param rentContractEntity 合同对象
     * @return
     * @author cuigh6
     */
    public DataTransferObject getPaymentItems(RentContractEntity rentContractEntity) throws Exception {
        LogUtil.info(LOGGER, "【getPaymentItems】参数={}", JSON.toJSONString(rentContractEntity));
        DataTransferObject dto = new DataTransferObject();
        if (Check.NuNObj(rentContractEntity) || Check.NuNStr(rentContractEntity.getConCycleCode())
                || Check.NuNStr(rentContractEntity.getConType())
                || Check.NuNObj(rentContractEntity.getRoomSalesPrice())) {
            dto.setMsg("关键参数缺失");
            dto.setErrCode(DataTransferObject.ERROR);
            return dto;
        }
        // 查询房租价格 (houses)
        // 根据付款方式 查询 折扣 (minsu)
        // 根据合同ID 查询 租住周期
        PaymentBaseDataPojo paymentBaseDataPojo = new PaymentBaseDataPojo();
        ContractPojo contractPojo = new ContractPojo();
        RoomPojo roomPojo = new RoomPojo();
        contractPojo.setConType(rentContractEntity.getConType());
        contractPojo.setConCycleCode(rentContractEntity.getConCycleCode());
        contractPojo.setRoomSalesPrice(rentContractEntity.getRoomSalesPrice());
        contractPojo.setProjectId(rentContractEntity.getProjectId());
        contractPojo.setConStartDate(rentContractEntity.getConStartDate());
        contractPojo.setConEndDate(rentContractEntity.getConEndDate());
        contractPojo.setConRentYear(rentContractEntity.getConRentYear());
        contractPojo.setSignType(rentContractEntity.getFsigntype());
        contractPojo.setCustomerType(rentContractEntity.getCustomerType());
        contractPojo.setUid(rentContractEntity.getCustomerUid());
        contractPojo.setRoomId(rentContractEntity.getRoomId());
        contractPojo.setContractId(rentContractEntity.getContractId());

        // 房间信息
        try {
            String roomStr = roomService.getRoomByFid(rentContractEntity.getRoomId());
            DataTransferObject roomObject = JsonEntityTransform.json2DataTransferObject(roomStr);
            if (roomObject.getCode() == DataTransferObject.ERROR) {
                LogUtil.error(LOGGER, "【getRoomByFid】请求结果={}", roomStr);
                dto.setMsg(roomObject.getMsg());
                dto.setErrCode(DataTransferObject.ERROR);
            }
            RoomInfoEntity roomInfoEntity = roomObject.parseData("roomInfo", new TypeReference<RoomInfoEntity>() {
            });
            contractPojo.setRentType(roomInfoEntity.getRentType());
            roomPojo.setHouseId(roomInfoEntity.getHousetypeid());

        } catch (Exception e) {
            LogUtil.error(LOGGER, "【getRoomByFid】roomId={},error:{}", rentContractEntity.getRoomId(), e);
            dto.setMsg("系统错误");
            dto.setErrCode(DataTransferObject.ERROR);
            return dto;
        }

        // 折扣信息
        String enumCode = ContractTradingEnum002.getByCode(Integer.parseInt(rentContractEntity.getConCycleCode())).getValue();
        try {
            String paymentMethodStr = cityTemplateService.getTextValueForCommon(ServiceLineEnum.ZRP.getCode(), enumCode);
            DataTransferObject PaymentMethodObject = JsonEntityTransform.json2DataTransferObject(paymentMethodStr);
            if (PaymentMethodObject.getCode() == DataTransferObject.ERROR) {
                LogUtil.error(LOGGER, "【getTextValueForCommon】请求结果={}", paymentMethodStr);
                dto.setMsg(PaymentMethodObject.getMsg());
                dto.setErrCode(DataTransferObject.ERROR);
            }
            String discount = PaymentMethodObject.parseData("textValue", new TypeReference<String>() {
            });
            contractPojo.setDiscount(new BigDecimal(discount));
        } catch (Exception e) {
            LogUtil.error(LOGGER, "【getTextValueForCommon】从特洛伊获取配置:enumCode={} ,error:{}", enumCode, e);
            throw new Exception("调用特洛伊系统获取折扣不同,请联系相关人员");
        }

        // 如果为续约 查询第一份合同的开始时间 和续约次数
        if (rentContractEntity.getFsigntype().equals(ContractSignTypeEnum.RENEW.getValue())) {
            RentContractEntity rentContractEntity1;
            Date firstSignStartDate;
            Date lastSignEndDate = null;
            String preRentCode = rentContractEntity.getPreConRentCode();
            int count = 0;
            do {
                rentContractEntity1 = rentContractServiceImpl.findValidContractByRentCode(preRentCode);
                preRentCode = rentContractEntity1 == null ? null : rentContractEntity1.getPreConRentCode();
                firstSignStartDate = rentContractEntity1 == null ? new Date() : rentContractEntity1.getConStartDate();
                if (count == 0) {
                    lastSignEndDate = rentContractEntity1 == null ? null : rentContractEntity1.getConEndDate();
                    contractPojo.setDepositPrice(rentContractEntity1 == null ? null : rentContractEntity1.getConDeposit());//续约上一份合同的押金
                    contractPojo.setPreConType(rentContractEntity1 == null ? null :rentContractEntity1.getConType());
                    count++;
                }
            } while (!Check.NuNStr(preRentCode));
            // 查询用户的第一次连续签约一年以上的日期
            firstSignStartDate = getFirstSignDateOfUser(rentContractEntity, firstSignStartDate);
            contractPojo.setFirstSignStartDate(firstSignStartDate);
            contractPojo.setLastSignEndDate(lastSignEndDate);
            LogUtil.info(LOGGER, "#连续续约总时常#-#开始日期#:{}-#结束日期#:{}", firstSignStartDate, lastSignEndDate);
        }

        paymentBaseDataPojo.setContractPojo(contractPojo);
        paymentBaseDataPojo.setRoomPojo(roomPojo);

        PaymentMethod paymentMethod = PaymentMethodFactory.createPaymentMethod(paymentBaseDataPojo.getContractPojo().getConCycleCode());
        PaymentTermsDto pay = paymentMethod.calculate(paymentBaseDataPojo);
        pay.setDepositPrice(contractPojo.getDepositPrice() == null ? BigDecimal.ZERO : BigDecimal.valueOf(contractPojo.getDepositPrice()));
        dto.putValue("items", pay);
        return dto;
    }

    /**
     * 查询用户的第一次连续签约一年以上的日期
     * @param rentContractEntity 合同对象
     * @param firstSignStartDate 续租第一次签约日
     * @return
     * @author cuigh6
     * @Date 2017年12月15日
     */
    private Date getFirstSignDateOfUser(RentContractEntity rentContractEntity, Date firstSignStartDate) throws Exception{
        List<RentContractEntity> rentContractEntities = this.rentContractServiceImpl.listContractByUid(rentContractEntity.getCustomerUid());
        List<RentContractEntity> collect = rentContractEntities.stream()
                .filter(v ->v.getConStatusCode()!=null && (v.getConStatusCode().equals(ContractStatusEnum.YQY.getStatus())
                        ||v.getConStatusCode().equals(ContractStatusEnum.YDQ.getStatus())
                        ||v.getConStatusCode().equals(ContractStatusEnum.YTZ.getStatus())
                        ||v.getConStatusCode().equals(ContractStatusEnum.JYZ.getStatus())
                        ||v.getConStatusCode().equals(ContractStatusEnum.XYZ.getStatus())
                        ||v.getConStatusCode().equals(ContractStatusEnum.YXY.getStatus())
                )).collect(Collectors.toList());
        int size = collect.size();
        while (size>0) {
            for (RentContractEntity contractEntity : collect) {
                try {
                    if (ContractStatusEnum.YQY.getStatus().equals(contractEntity.getConStatusCode())) {
                        if (DateSplitUtil.getTomorrow(new Date()).compareTo(firstSignStartDate) >= 0&& contractEntity.getConStartDate().compareTo(firstSignStartDate)<=0) {
                            LogUtil.info(LOGGER, "#老客户连续合同号#:{},#合同状态#:{}", contractEntity.getConRentCode(), contractEntity.getConStatusCode());
                            firstSignStartDate = contractEntity.getConStartDate();
                            continue;
                        }
                    }else if (ContractStatusEnum.YDQ.getStatus().equals(contractEntity.getConStatusCode())){
                        if (DateSplitUtil.getTomorrow(contractEntity.getConEndDate()).compareTo(firstSignStartDate) >= 0&& contractEntity.getConStartDate().compareTo(firstSignStartDate)<=0) {
                            LogUtil.info(LOGGER, "#老客户连续合同号#:{},#合同状态#:{}", contractEntity.getConRentCode(), contractEntity.getConStatusCode());
                            firstSignStartDate = contractEntity.getConStartDate();
                            continue;
                        }
                    } else if (ContractStatusEnum.YTZ.getStatus().equals(contractEntity.getConStatusCode())) {
                        if (DateSplitUtil.getTomorrow(contractEntity.getConReleaseDate()).compareTo(firstSignStartDate) >= 0&& contractEntity.getConStartDate().compareTo(firstSignStartDate)<=0) {
                            LogUtil.info(LOGGER, "#老客户连续合同号#:{},#合同状态#:{}", contractEntity.getConRentCode(), contractEntity.getConStatusCode());
                            firstSignStartDate = contractEntity.getConStartDate();
                            continue;
                        }
                    }else if (ContractStatusEnum.JYZ.getStatus().equals(contractEntity.getConStatusCode())) {
                        if (DateSplitUtil.getTomorrow(contractEntity.getConReleaseDate()).compareTo(firstSignStartDate) >= 0&& contractEntity.getConStartDate().compareTo(firstSignStartDate)<=0) {
                            LogUtil.info(LOGGER, "#老客户连续合同号#:{},#合同状态#:{}", contractEntity.getConRentCode(), contractEntity.getConStatusCode());
                            firstSignStartDate = contractEntity.getConStartDate();
                            continue;
                        }
                    }else if (ContractStatusEnum.XYZ.getStatus().equals(contractEntity.getConStatusCode())) {
                        if (DateSplitUtil.getTomorrow(new Date()).compareTo(firstSignStartDate) >= 0&& contractEntity.getConStartDate().compareTo(firstSignStartDate)<=0) {
                            LogUtil.info(LOGGER, "#老客户连续合同号#:{},#合同状态#:{}", contractEntity.getConRentCode(), contractEntity.getConStatusCode());
                            firstSignStartDate = contractEntity.getConStartDate();
                            continue;
                        }
                    }else if (ContractStatusEnum.YXY.getStatus().equals(contractEntity.getConStatusCode())) {
                        if (DateSplitUtil.getTomorrow(new Date()).compareTo(firstSignStartDate) >= 0&& contractEntity.getConStartDate().compareTo(firstSignStartDate)<=0) {
                            LogUtil.info(LOGGER, "#老客户连续合同号#:{},#合同状态#:{}", contractEntity.getConRentCode(), contractEntity.getConStatusCode());
                            firstSignStartDate = contractEntity.getConStartDate();
                            continue;
                        }
                    }
                } catch (Exception e) {
                    LogUtil.error(LOGGER, "【查询续约时长异常】合同id:{}", contractEntity.getContractId(), e);
                    throw new ZrpServiceException("计算续约时长出现错误");
                }
            }
            size--;
        }
        return firstSignStartDate;
    }

    /**
      * @description: 同步合同到财务
      * @author: lusp
      * @date: 2018/1/11 下午 15:33
      * @params: contractId
      * @return: String
      */
    public String syncContractToFina(String contractId) {
        LogUtil.info(LOGGER, "【syncContractToFina】contractId:{}", contractId);
        DataTransferObject dto = new DataTransferObject();
        try {
            if (Check.NuNStr(contractId)) {
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("合同id为空");
                return dto.toJsonString();
            }
            //1.查询合同信息(根据合同id),校验是否同步财务成功
            RentContractEntity rentContractEntity = rentContractServiceImpl.findContractBaseByContractId(contractId);
            //2查询合同子表，获取合同物业交割状态，供判断同步财务合同状态使用
            ContractRoomDto contractRoomDto = new ContractRoomDto();
            contractRoomDto.setContractId(rentContractEntity.getContractId());
            contractRoomDto.setRoomId(rentContractEntity.getRoomId());
            RentDetailEntity rentDetailEntity = rentContractServiceImpl.findRentDetailById(contractRoomDto);
            //3.查询project信息，供组装SyncContractVo使用
            String projectJson = projectService.findProjectById(rentContractEntity.getProjectId());
            DataTransferObject projectDto = JsonEntityTransform.json2DataTransferObject(projectJson);
            if(projectDto.getCode()==DataTransferObject.ERROR){
                LogUtil.error(LOGGER,"【syncContractToFina】调用projectService服务查询项目信息失败,errMsg:{}",projectDto.getMsg());
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("调用projectService服务查询项目信息失败");
                return dto.toJsonString();
            }
            ProjectEntity projectEntity = SOAResParseUtil.getValueFromDataByKey(projectJson,"projectEntity",ProjectEntity.class);
            if(Check.NuNObj(projectEntity)){
                LogUtil.error(LOGGER,"【syncContractToFina】调用projectService服务查询项目信息为空,projectId:{}",rentContractEntity.getProjectId());
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("该合同的项目信息异常");
                return dto.toJsonString();
            }
            //4.查询房间信息
            String roomInfoJson = roomService.getRoomByFid(rentContractEntity.getRoomId());
            DataTransferObject roomInfoDto = JsonEntityTransform.json2DataTransferObject(roomInfoJson);
            if (roomInfoDto.getCode() == DataTransferObject.ERROR) {
                LogUtil.error(LOGGER, "【syncContractToFina】 根据roomId查询房间信息失败,param:{},errorMsg:{}", rentContractEntity.getRoomId(), roomInfoDto.getMsg());
                return roomInfoDto.toJsonString();
            }
            RoomInfoEntity roomInfoEntity = SOAResParseUtil.getValueFromDataByKey(roomInfoJson, "roomInfo", RoomInfoEntity.class);
            if (Check.NuNObj(roomInfoEntity) || Check.NuNStrStrict(roomInfoEntity.getFroomnumber())) {
                LogUtil.error(LOGGER, "【syncContractToFina】 根据roomId查询房间信息为空或froomnumber为空,roomId:{},roomInfoEntity:{},froomnumber:{}", rentContractEntity.getRoomId(), roomInfoEntity, roomInfoEntity.getFroomnumber());
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("房间信息为空");
                return dto.toJsonString();
            }
            //5.查询同步财务合同信息
            SyncContractVo syncContractVo = rentContractServiceImpl.findSyncContractVoById(rentContractEntity.getContractId());
            if (Check.NuNObj(syncContractVo) || Check.NuNStrStrict(syncContractVo.getRoomId())) {
                LogUtil.error(LOGGER, "【syncContractToFina】 根据contractId查询合同信息为空或roomId为空,contractId:{},syncContractVo:{},roomId:{}", rentContractEntity.getContractId(), syncContractVo, syncContractVo.getRoomId());
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("合同信息为空");
                return dto.toJsonString();
            }
            //6.获取应该同步给财务的合同状态
            String finContractStatus = FinaContractStatusEnum.getByStatusCodeAndAuditState(rentContractEntity.getConStatusCode(),rentDetailEntity.getDeliveryState(),rentContractEntity.getConAuditState());
            if(Check.NuNStrStrict(finContractStatus)){
                LogUtil.error(LOGGER, "【syncContractToFina】 获取财务合同状态异常，没有对应枚举,contractStatus:{},deliveryStatusCode:{},auditStatus:{}", rentContractEntity.getConStatusCode(),rentDetailEntity.getDeliveryState(),rentContractEntity.getConAuditState());
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("获取财务合同状态异常，没有对应枚举");
                return dto.toJsonString();
            }
            //7.填充项目信息
            syncContractVo.setStatusCode(finContractStatus);
            syncContractVo.setHireContractCode(projectEntity.getFContractNumber());
            syncContractVo.setHouseCode(projectEntity.getFContractNumber());
            syncContractVo.setCompanyCode(projectEntity.getFcompanyid());
            syncContractVo.setRatingAddress(projectEntity.getFaddress());
            //8.填充房间有关信息
            syncContractVo.setRoomCode(roomInfoEntity.getFroomnumber());
            syncContractVo.setHouseSourceCode(roomInfoEntity.getFroomnumber());
            if(roomInfoEntity.getRentType()==1){
                syncContractVo.setHouseType(6);
            }else if(roomInfoEntity.getRentType()==2){
                syncContractVo.setHouseType(7);
            }
            //9.调用友家客户库查询用户信息
            PersonalInfoDto personalInfoDto = CustomerLibraryUtil.findAuthInfoFromCustomer(syncContractVo.getCustomerUid());
            if(Check.NuNObj(personalInfoDto)){
                LogUtil.error(LOGGER, "【syncContractToFina】根据customerUid调用友家客户库查询客户信息失败,customerUid:{}", syncContractVo.getCustomerUid());
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("客户信息不存在");
                return dto.toJsonString();
            }
            Profile profile = personalInfoDto.getProfile();
            Cert cert = personalInfoDto.getCert();
            if(Check.NuNObj(profile)||Check.NuNObj(cert)||Check.NuNStrStrict(profile.getUser_name())||Check.NuNStrStrict(profile.getPhone())||Check.NuNStrStrict(cert.getCert_type())||Check.NuNStrStrict(cert.getCert_num())){
                LogUtil.error(LOGGER, "【syncContractToFina】根据customerUid调用友家客户库查询客户信息失败,customerUid:{},personalInfoDto:{}", syncContractVo.getCustomerUid(),JsonEntityTransform.Object2Json(personalInfoDto));
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("客户信息不完整");
                return dto.toJsonString();
            }
            syncContractVo.setCustomerName(profile.getUser_name());
            syncContractVo.setCustomerPhone(profile.getPhone());
            syncContractVo.setCertType(cert.getCert_type());
            syncContractVo.setCertNum(cert.getCert_num());
            //10.如果签约类型为企业，证件类型传营业执照或者组织机构代码，同时设置父全同号
            if(!Check.NuNObj(syncContractVo.getCustomerType())&&syncContractVo.getCustomerType()==3){
                //企业签约客户姓名传公司名称  @Author:lusp  @Date:2018/3/1
                syncContractVo.setCustomerName(rentContractEntity.getCustomerName());
                RentEpsCustomerEntity rentEpsCustomerEntity = rentEpsCustomerServiceImpl.findRentEpsCustomerById(syncContractVo.getCustomerId());
                if(Check.NuNObj(rentEpsCustomerEntity)){
                    LogUtil.error(LOGGER, "【syncContractToFina】根据customerId查询企业客户信息为空,customerId:{}", syncContractVo.getCustomerId());
                    dto.setErrCode(DataTransferObject.ERROR);
                    dto.setMsg("企业签约企业信息不存在");
                    return dto.toJsonString();
                }
                if(!Check.NuNStrStrict(rentEpsCustomerEntity.getBusinessLicense())){
                    syncContractVo.setCertNum(rentEpsCustomerEntity.getBusinessLicense());
                    syncContractVo.setCertType(String.valueOf(CertTypeEnum.BUSINESSLICENCE.getCode()));
                }else{
                    if(!Check.NuNStrStrict(rentEpsCustomerEntity.getCode())){
                        syncContractVo.setCertNum(rentEpsCustomerEntity.getCode());
                        syncContractVo.setCertType(String.valueOf(CertTypeEnum.ORGANIZATIONCODE.getCode()));
                    }else{
                        LogUtil.error(LOGGER, "【syncContractToFina】根据customerId查询企业客户信息异常,组织机构代码和营业执照同时为空,customerId:{}", syncContractVo.getCustomerId());
                        dto.setErrCode(DataTransferObject.ERROR);
                        dto.setMsg("企业签约企业信息异常,组织机构代码和营业执照同时为空");
                        return dto.toJsonString();
                    }
                }
                syncContractVo.setParentContractCode(rentContractEntity.getSurParentRentCode());
            }
            //11.判断是否是续约
            if(rentContractEntity.getFsigntype().equals("1")){
                syncContractVo.setIsResign(1);
                syncContractVo.setTransferType(2);
            }else {
                syncContractVo.setIsResign(0);
            }
            //12.查询管家信息
            String employeeJson = employeeService.findEmployeeByCode(rentContractEntity.getFhandlezocode());
            DataTransferObject employeeDto = JsonEntityTransform.json2DataTransferObject(employeeJson);
            if(DataTransferObject.ERROR==employeeDto.getCode()){
                LogUtil.error(LOGGER, "【syncContractToFina】 根据管家系统号查询管家信息失败,employeeCode:{},errorMsg:{}",rentContractEntity.getFhandlezocode(),employeeDto.getMsg());
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("系统错误");
                return dto.toJsonString();
            }
            EmployeeEntity employeeEntity = SOAResParseUtil.getValueFromDataByKey(employeeJson,"employeeEntity",EmployeeEntity.class);
            if(Check.NuNObj(employeeEntity)){
                LogUtil.error(LOGGER, "【syncContractToFina】 根据管家系统号查询管家信息为空,employeeCode:{}",rentContractEntity.getFhandlezocode());
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("管家信息为空");
                return dto.toJsonString();
            }
            syncContractVo.setAgentCode(employeeEntity.getFcode());
            syncContractVo.setAgentName(employeeEntity.getFname());
            syncContractVo.setAgentPhone(employeeEntity.getFmobile());
            //13.调用财务接口,如果已经同步给财务则调用更新接口
            if(rentContractEntity.getIsSyncToFin()== SyncToFinEnum.SUCCESS.getCode()){
                LogUtil.info(LOGGER,"【syncContractToFina】同步合同到财务已经成功,调用更新合同接口更新合同,contractId:{}",rentContractEntity.getContractId());
                financeBaseCall.updateContract(syncContractVo);
            }else{
                financeBaseCall.syncContract(syncContractVo);
            }
            //修改合同表中的is_sync_to_fin 为 1 ,表示同步财务成功
            RentContractEntity updateSyncToFin = new RentContractEntity();
            updateSyncToFin.setContractId(contractId);
            updateSyncToFin.setIsSyncToFin(SyncToFinEnum.SUCCESS.getCode());
            rentContractServiceImpl.updateSyncToFinByContractId(updateSyncToFin);
            return dto.toJsonString();
        } catch (FinServiceException e) {
            //修改合同表中的is_sync_to_fin 为 2 ,表示同步财务失败
            RentContractEntity updateSyncToFin = new RentContractEntity();
            updateSyncToFin.setContractId(contractId);
            updateSyncToFin.setIsSyncToFin(SyncToFinEnum.FAIL.getCode());
            rentContractServiceImpl.updateSyncToFinByContractId(updateSyncToFin);
            e.printStackTrace();
            return DataTransferObjectBuilder.buildErrorJsonStr(e.getMessage());
        } catch (Exception e) {
            LogUtil.error(LOGGER, "【syncContractToFina】 e:{},contractId={}, ", e,contractId);
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("系统错误");
            return dto.toJsonString();
        }
    }

    /**
     * @description: 生成应收账单编号（生成规则和zrams保持一致）
     * @author: lusp
     * @date: 2017/10/13 下午 20:24
     * @params: projectCode
     * @return: String
     */
    public String genReceiBillCode(String projectCode) {
        SimpleDateFormat SDF = new SimpleDateFormat("yyyyMMdd");
        String dataString = SDF.format(new Date()).substring(2);
        return "YS" + projectCode + dataString + getPayBillSeq();
    }


    /**
     * @description: 获取数据库中应收账单序列号，并只取四位（和zrams之前保持一致）
     * @author: lusp
     * @date: 2017/10/13 下午 20:23
     * @params:
     * @return: String
     */
    private String getPayBillSeq(){
        int payBillSeq = finReceiBillServiceImpl.selectPayBillSeq();
        //4位数值
        return new DecimalFormat("0000").format(payBillSeq);
    }


}
