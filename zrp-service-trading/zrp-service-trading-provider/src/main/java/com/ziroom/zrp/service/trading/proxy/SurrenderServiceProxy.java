package com.ziroom.zrp.service.trading.proxy;

import com.alibaba.dubbo.common.json.JSON;
import com.alibaba.fastjson.JSONObject;
import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.utils.LogUtil;
import com.ziroom.zrp.houses.entity.RoomInfoEntity;
import com.ziroom.zrp.service.houses.api.RoomService;
import com.ziroom.zrp.service.houses.api.SmartPlatformService;
import com.ziroom.zrp.service.trading.api.SurrenderService;
import com.ziroom.zrp.service.trading.dto.PersonalInfoDto;
import com.ziroom.zrp.service.trading.dto.finance.PayVoucherResponse;
import com.ziroom.zrp.service.trading.dto.finance.ReceiptBillRequest;
import com.ziroom.zrp.service.trading.dto.finance.ReceiptBillResponse;
import com.ziroom.zrp.service.trading.dto.finance.ReceiptInfoResponse;
import com.ziroom.zrp.service.trading.dto.surrender.*;
import com.ziroom.zrp.service.trading.entity.SurrenderCostSumBodyVo;
import com.ziroom.zrp.service.trading.entity.SyncContractVo;
import com.ziroom.zrp.service.trading.pojo.CalNeedPojo;
import com.ziroom.zrp.service.trading.pojo.CalReturnPojo;
import com.ziroom.zrp.service.trading.pojo.CalSurrenderPojo;
import com.ziroom.zrp.service.trading.proxy.commonlogic.FinanceBaseCall;
import com.ziroom.zrp.service.trading.service.RentContractActivityServiceImpl;
import com.ziroom.zrp.service.trading.service.RentContractServiceImpl;
import com.ziroom.zrp.service.trading.service.RentEpsCustomerServiceImpl;
import com.ziroom.zrp.service.trading.service.SurMeterDetailServiceImpl;
import com.ziroom.zrp.service.trading.service.SurrendBackRecordServiceImpl;
import com.ziroom.zrp.service.trading.service.SurrenderCostServiceImpl;
import com.ziroom.zrp.service.trading.service.SurrenderServiceImpl;
import com.ziroom.zrp.service.trading.utils.CustomerLibraryUtil;
import com.ziroom.zrp.service.trading.utils.surrender.CalculateInterface;
import com.ziroom.zrp.service.trading.utils.surrender.SurrenderReceiCalFactory;
import com.ziroom.zrp.service.trading.valenum.ContractStatusEnum;
import com.ziroom.zrp.service.trading.valenum.CustomerTypeEnum;
import com.ziroom.zrp.service.trading.valenum.finance.CostCodeEnum;
import com.ziroom.zrp.service.trading.valenum.finance.DocumentTypeEnum;
import com.ziroom.zrp.service.trading.valenum.finance.PayVoucherAuditFlagEnum;
import com.ziroom.zrp.service.trading.valenum.finance.PayVoucherType;
import com.ziroom.zrp.service.trading.valenum.finance.ReceiptMethodEnum;
import com.ziroom.zrp.service.trading.valenum.finance.VerificateStatusEnum;
import com.ziroom.zrp.service.trading.valenum.surrender.AuditTypeEnum;
import com.ziroom.zrp.service.trading.valenum.surrender.ExpenseItemEnum;
import com.ziroom.zrp.service.trading.valenum.surrender.QueryConStatusEnum;
import com.ziroom.zrp.service.trading.valenum.surrender.SurrendAuditStatusEnum;
import com.ziroom.zrp.service.trading.valenum.surrender.SurrenderApplyStatusEnum;
import com.ziroom.zrp.service.trading.valenum.surrender.SurrenderAuditResultEnum;
import com.ziroom.zrp.trading.entity.RentContractEntity;
import com.ziroom.zrp.trading.entity.RentDetailEntity;
import com.ziroom.zrp.trading.entity.RentEpsCustomerEntity;
import com.ziroom.zrp.trading.entity.SurMeterDetailEntity;
import com.ziroom.zrp.trading.entity.SurrendBackRecordEntity;
import com.ziroom.zrp.trading.entity.SurrenderCostEntity;
import com.ziroom.zrp.trading.entity.SurrenderCostItemEntity;
import com.ziroom.zrp.trading.entity.SurrenderEntity;
import com.zra.common.exception.FinServiceException;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>解约</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @Author wangxm113
 * @Date 2017年10月11日 17时26分
 * @Version 1.0
 * @Since 1.0
 */
@Component("trading.surrenderServiceProxy")
public class SurrenderServiceProxy implements SurrenderService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SurrenderServiceProxy.class);

    @Resource(name = "trading.surrenderServiceImpl")
    private SurrenderServiceImpl surrenderServiceImpl;

    @Resource(name = "trading.callFinanceServiceProxy")
    private CallFinanceServiceProxy callFinanceServiceProxy;

    @Resource(name = "trading.surMeterDetailServiceProxy")
    private SurMeterDetailServiceProxy surMeterDetailServiceProxy;

    @Resource(name = "trading.surrenderCostServiceProxy")
    private SurrenderCostServiceProxy surrenderCostServiceProxy;

    @Resource(name = "trading.surrenderCostItemServiceProxy")
    private SurrenderCostItemServiceProxy surrenderCostItemServiceProxy;

    @Resource(name = "trading.rentContractServiceProxy")
    private RentContractServiceProxy rentContractServiceProxy;

    @Resource(name = "houses.roomService")
    private RoomService roomService;

    @Resource(name = "trading.rentContractActivityServiceImpl")
    private RentContractActivityServiceImpl rentContractActivityService;
    
    @Resource(name = "trading.rentContractServiceImpl")
    private RentContractServiceImpl rentContractServiceImpl;
    
    @Resource(name = "trading.surrenderCostServiceImpl")
    private SurrenderCostServiceImpl surrenderCostServiceImpl;
    
    @Value("#{'${surrender_apply_time}'.trim()}")
    private String surrenderApplyTime;
    
    @Resource(name = "trading.surMeterDetailServiceImpl")
    private SurMeterDetailServiceImpl surMeterDetailServiceImpl;
    
    @Resource(name="trading.surrendBackRecordServiceImpl")
    private SurrendBackRecordServiceImpl surrendBackRecordServiceImpl;
    
    @Resource(name="trading.rentEpsCustomerServiceImpl")
	private RentEpsCustomerServiceImpl rentEpsCustomerServiceImpl;
    
    @Resource(name="houses.smartPlatformService")
    private SmartPlatformService smartPlatformService;
    
    @Resource(name="trading.financeBaseCall")
    private FinanceBaseCall financeBaseCall;

    /**
     * 解约协议：费用结算页<br/>
     * 个人费用结算页+企业费用结算页+企业费用结算页的修改每个房间费用信息<p/>
     * 此方法估计只有上帝和我能看懂。。还好，我注释详细 ^_^
     *
     * @Author: wangxm113
     * @Date: 2017年10月12日 15时08分53秒
     */
    @Override
    public String getCostItemAccount(String paramStr) {
        LogUtil.info(LOGGER, "[解约费用结算]计算入参：{}", paramStr);
        DataTransferObject dto = new DataTransferObject();
        if (paramStr == null || paramStr.isEmpty()) {
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("参数不能为空！");
            return dto.toJsonString();
        }
        CostItemAccountDto paramDto;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            paramDto = objectMapper.readValue(paramStr, CostItemAccountDto.class);
            String surParentId = paramDto.getSurParentId();
            String contractId = paramDto.getContractId();
            List<CalSurrenderPojo> searchPojoList = new ArrayList<>();
            //==========================================================================================================
            //1、获取计算所需信息
            //合同id为空且父解约协议id不为空，说明可能需要计算多个合同
            if ((contractId == null || contractId.isEmpty()) && surParentId != null && !surParentId.isEmpty()) {
                //查询父解约协议下对应的所有的合同
                LogUtil.info(LOGGER, "[解约费用结算]父解约协议id={},查询父合同下所有信息……", surParentId);
                searchPojoList = surrenderServiceImpl.getSurAndSurCostInfo(surParentId);
                searchPojoList.forEach(p -> {
                    BigDecimal needTZJGPCF = surrenderServiceImpl.getTZJGPCF(p.getContractId());
                    p.setNeedPayTZJGPCF(needTZJGPCF);
                });
            } else if (contractId != null && !contractId.isEmpty()) {//合同id不为空，说明只需计算指定的合同
                LogUtil.info(LOGGER, "[解约费用结算]查询指定合同id={}下所有信息……", contractId);
                CalSurrenderPojo searchPojo = surrenderServiceImpl.getSurAndSurCostInfoByConId(contractId);
                BigDecimal needTZJGPCF = surrenderServiceImpl.getTZJGPCF(contractId);
                searchPojo.setNeedPayTZJGPCF(needTZJGPCF);
                searchPojoList.add(searchPojo);
            }
            //==========================================================================================================
            //2、计算,得到的是每个合同的每个费用项（即每个房间/床位的每个费用项）
            List<SurrenderCostItemEntity> needUpdateList = new ArrayList<>();//需要更新旧数据的集合（水电费）
            List<String> needDeleteList = new ArrayList<>();//需要删除旧数据集合（surrendercost_id）
            List<SurrenderCostSumBodyVo> result = calSurrenderCost(searchPojoList, paramDto, needUpdateList, needDeleteList);
            LogUtil.info(LOGGER, "[解约费用结算]计算结果={}", JsonEntityTransform.Object2Json(result));
            //更新tsurrendercostitem、删除tsurrendercost（需要保证如果costItem没有数据，cost也应该没有）
            surrenderCostServiceProxy.deleteCostAndUpdateCostItem(needDeleteList, needUpdateList);
            dto.putValue("list", result);
            return dto.toJsonString();
        } catch (Exception e) {
            LogUtil.error(LOGGER, "[解约费用结算]计算出错！错误信息={}", e);
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("系统异常！");
            return dto.toJsonString();
        }
    }

    /**
     * 解约协议：费用结算页<br/>
     * 计算所有合同,得到的是每个合同的每个费用项（即每个房间/床位的每个费用项）
     *
     * @Author: wangxm113
     * @Date: 2017年10月12日 17时45分55秒
     */
    private List<SurrenderCostSumBodyVo> calSurrenderCost(List<CalSurrenderPojo> searchPojoList,
                                                          CostItemAccountDto paramDto,
                                                          List<SurrenderCostItemEntity> needUpdateList,
                                                          List<String> needDeleteList) throws Exception {
        List<SurrenderCostSumBodyVo> result = new ArrayList<>();//所有合同的所有费用项
        //得到的是每个合同的每个费用项（即每个房间/床位的每个费用项）
        for (CalSurrenderPojo searchPojo : searchPojoList) {
            LogUtil.info(LOGGER, "[解约费用结算]合同id：{}，此次循环的参数", searchPojo.getContractId(), searchPojo.toString());
            //==========================================================================================================
            boolean isDelFlag = false;//是否需要重新计算、且删除旧的数据标识
            if (!searchPojo.getSurType().equals(searchPojo.getCostSurType())) {
                LogUtil.info(LOGGER, "[解约费用结算]合同id：{}，由于退租类型改变，重新计算并删除旧数据", searchPojo.getContractId());
                isDelFlag = true;
            } else if (searchPojo.getCustomerType() != CustomerTypeEnum.PERSON.getCode()
                    && Integer.valueOf(searchPojo.getResponsibility()).intValue() != paramDto.getResNo()) {
                //因为有可能修改了单个合同的费用交割信息，如果修改了违约责任方不强制重新计算的话，后果不堪设想 O_O
                LogUtil.info(LOGGER, "[解约费用结算]合同id：{}，由于是企业客户且违约责任方改变，重新计算并删除旧数据", searchPojo.getContractId());
                isDelFlag = true;
            } else if (paramDto.getAnew() == 1) {//是否刷新重新计算（0：不需要；1：需要）
                LogUtil.info(LOGGER, "[解约费用结算]合同id：{}，由于管家点击'重新计算'，重新计算并删除旧数据", searchPojo.getContractId());
                isDelFlag = true;
            }
            //==========================================================================================================
            //第一种情况（优先级最高）：需要重新计算、删除旧的数据
            if (isDelFlag) {//需要删除
                LogUtil.info(LOGGER, "[解约费用结算]合同id：{}，执行第一种情况。", searchPojo.getContractId());
                List<SurrenderCostSumBodyVo> perContractCostItemList = calPerContractCostItem(searchPojo, paramDto);
                needDeleteList.add(searchPojo.getSurrenderCostId());//需要删除旧的数据
                result.addAll(perContractCostItemList);
            } else {
                //第二种情况：证明已经生成过解约费用明细（tsurrendercostitem）,此时需要以退租水电交割表（tsurmeterdetail）中的水电交割费用为准
                if (searchPojo.getItemCount() > 0) {
                    LogUtil.info(LOGGER, "[解约费用结算]合同id：{}，执行第二种情况。", searchPojo.getContractId());
                    //==================================================================================================
                    //1、查询此合同已有的解约费用明细（tsurrendercostitem）
                    List<SurrenderCostSumBodyVo> costItemList = surrenderCostItemServiceProxy
                            .getCostItemsBySurCostId(searchPojo.getSurrenderCostId());
                    LogUtil.info(LOGGER, "[解约费用结算]合同id：{}，对应的解约费用项明细有{}项。", searchPojo.getContractId(), costItemList.size());
                    //2、查询此子解约协议（即此合同）退租水电交割表的水电交割费用
                    SurMeterDetailEntity surMeterDetailEntity = surMeterDetailServiceProxy
                            .getSDPriceBySurrenderId(searchPojo.getSurrenderId());
                    if (surMeterDetailEntity == null) {
                        LogUtil.info(LOGGER, "[解约费用结算]合同id：{}，对应的解约物业交割信息没有查到！", searchPojo.getContractId());
                        surMeterDetailEntity = new SurMeterDetailEntity();
                        surMeterDetailEntity.setFsurwaterprice(0D);
                        surMeterDetailEntity.setFsurelecprice(0D);
                    }
                    BigDecimal standardSF = BigDecimal.valueOf(surMeterDetailEntity.getFsurwaterprice());
                    BigDecimal standardDF = BigDecimal.valueOf(surMeterDetailEntity.getFsurelecprice());
                    searchPojo.setStandardSF(standardSF);
                    searchPojo.setStandardDF(standardDF);
                    LogUtil.info(LOGGER, "[解约费用结算]合同id：{}，物业交割水费：{}、电费：{}。", searchPojo.getContractId(), standardSF, standardDF);
                    //退租交割赔偿费，历史问题，需要替换
                    BigDecimal needPayTZJGPCF = searchPojo.getNeedPayTZJGPCF();
                    //3、替换
                    costItemList.stream()
                            .filter(item -> {
                                String itemId = item.getExpenseItemId();
                                return itemId.equals(ExpenseItemEnum.SF.getId()) || itemId.equals(ExpenseItemEnum.DF.getId())
                                        || itemId.equals(ExpenseItemEnum.TZJGPCFY.getId());
                            })
                            .forEach(item -> {
                                String itemId = item.getExpenseItemId();
                                SurrenderCostItemEntity needUpdate = new SurrenderCostItemEntity();
                                needUpdate.setFid(item.getSurCostItemId());
                                if (itemId.equals(ExpenseItemEnum.SF.getId())) {
                                    if (standardSF.compareTo(BigDecimal.ZERO) < 0) {//为负数 设置已缴为水费数据绝对值
                                        item.setActualNum(BigDecimal.ZERO);
                                        item.setOriginalNum(standardSF.abs());
                                        item.setRefundNum(standardSF.abs().negate());
                                        needUpdate.setForiginalnum(standardSF.abs());
                                        needUpdate.setFactualnum(BigDecimal.ZERO);
                                        needUpdate.setFrefundnum(standardSF.abs().negate());
                                    } else {
                                        item.setActualNum(standardSF.abs());
                                        item.setOriginalNum(BigDecimal.ZERO);
                                        item.setRefundNum(standardSF.abs());
                                        needUpdate.setForiginalnum(BigDecimal.ZERO);
                                        needUpdate.setFactualnum(standardSF.abs());
                                        needUpdate.setFrefundnum(standardSF.abs());
                                    }
                                } else if (itemId.equals(ExpenseItemEnum.DF.getId())) {
                                    if (standardDF.compareTo(BigDecimal.ZERO) < 0) {//为负数 设置已缴为电费费数据绝对值
                                        item.setActualNum(BigDecimal.ZERO);
                                        item.setOriginalNum(standardDF.abs());
                                        item.setRefundNum(standardDF.abs().negate());
                                        needUpdate.setFid(item.getSurCostItemId());
                                        needUpdate.setForiginalnum(standardDF.abs());
                                        needUpdate.setFactualnum(BigDecimal.ZERO);
                                        needUpdate.setFrefundnum(standardDF.abs().negate());
                                    } else {
                                        item.setActualNum(standardDF.abs());
                                        item.setOriginalNum(BigDecimal.ZERO);
                                        item.setRefundNum(standardDF.abs());
                                        needUpdate.setFid(item.getSurCostItemId());
                                        needUpdate.setForiginalnum(BigDecimal.ZERO);
                                        needUpdate.setFactualnum(standardDF.abs());
                                        needUpdate.setFrefundnum(standardDF.abs());
                                    }
                                } else if (itemId.equals(ExpenseItemEnum.TZJGPCFY.getId())) {
                                    item.setOriginalNum(needPayTZJGPCF);
                                    item.setRefundNum(item.getActualNum().subtract(needPayTZJGPCF));
                                    needUpdate.setFid(item.getSurCostItemId());
                                    needUpdate.setForiginalnum(needPayTZJGPCF);
                                    needUpdate.setFactualnum(BigDecimal.ZERO);
                                    needUpdate.setFrefundnum(needPayTZJGPCF.negate());
                                }
                                needUpdateList.add(needUpdate);//更新已有数据
                            });
                    costItemList.forEach(item -> {
                        String itemId = item.getExpenseItemId();
                        if (itemId.equals(ExpenseItemEnum.FZ.getId())) {
                            item.setOrder(1);
                        } else if (itemId.equals(ExpenseItemEnum.FWF.getId())) {
                            item.setOrder(2);
                        } else if (itemId.equals(ExpenseItemEnum.YJ.getId())) {
                            item.setOrder(3);
                        } else if (itemId.equals(ExpenseItemEnum.TCF.getId())) {
                            item.setOrder(4);
                        } else if (itemId.equals(ExpenseItemEnum.SF.getId())) {
                            item.setOrder(5);
                        } else if (itemId.equals(ExpenseItemEnum.DF.getId())) {
                            item.setOrder(6);
                        } else if (itemId.equals(ExpenseItemEnum.TZJGPCFY.getId())) {
                            item.setOrder(7);
                        } else if (itemId.equals(ExpenseItemEnum.WYJ.getId())) {
                            item.setOrder(8);
                        } else if (itemId.equals(ExpenseItemEnum.YQWYJ.getId())) {
                            item.setOrder(9);
                        } else if (itemId.equals(ExpenseItemEnum.QTFY.getId())) {
                            item.setOrder(10);
                        }
                    });
                    result.addAll(costItemList);
                } else {
                    //==================================================================================================
                    LogUtil.info(LOGGER, "[解约费用结算]合同id：{}，执行第三种情况。", searchPojo.getContractId());
                    List<SurrenderCostSumBodyVo> perContractCostItemList = calPerContractCostItem(searchPojo, paramDto);
                    result.addAll(perContractCostItemList);
                }
            }
        }
        return result;
    }

    /**
     * 解约协议：费用结算页<br/>
     * 计算,合同的每个费用项（即每个房间/床位的每个费用项）
     *
     * @Author: wangxm113
     * @Date: 2017年10月13日 20时01分18秒
     */
    private List<SurrenderCostSumBodyVo> calPerContractCostItem(CalSurrenderPojo searchPojo,
                                                                CostItemAccountDto paramDto) throws Exception,FinServiceException {
        List<SurrenderCostSumBodyVo> result = new ArrayList<>();
        //==============================================================================================================
        //1、计算已缴费用
        //1-1、指定合同，从财务查询合同对应的所有应收账单
        ReceiptBillRequest receiptBillRequest = new ReceiptBillRequest();
        receiptBillRequest.setOutContractCode(searchPojo.getConRentCode());
        DataTransferObject billDto = JsonEntityTransform.json2DataTransferObject(callFinanceServiceProxy
                .getReceivableBillInfo(JSONObject.toJSONString(receiptBillRequest)));
        List<ReceiptBillResponse> listBill = billDto.parseData("listStr", new TypeReference<List<ReceiptBillResponse>>() {
        });
        Map<String, Integer> havaSumMap = new HashMap<>();
        Integer yqwyjSum = 0;
        Map<String, Integer> reduceMap = new HashMap<>();
        //1-2、查询这些应收对应的所有的实收信息 计算已缴费用中付款渠道为优惠券(yhq)的收款单，并计算总金额
        if (listBill.size() > 0) {
            List<String> billNumList = listBill.stream()
                    .map(ReceiptBillResponse::getBillNum)
                    .collect(Collectors.toList());
            String receiptInfo = callFinanceServiceProxy.getReceiptInfo(JsonEntityTransform.Object2Json(billNumList));
            DataTransferObject receiptDto = JsonEntityTransform.json2DataTransferObject(receiptInfo);
            List<ReceiptInfoResponse> receiptInfoList = receiptDto.parseData("list", new TypeReference<List<ReceiptInfoResponse>>() {
            });

            //1-3、将所有实收以费用项编码分组
            Map<String, List<ReceiptInfoResponse>> collect = receiptInfoList.stream()
                    .collect(Collectors.groupingBy(ReceiptInfoResponse::getCostCode));
            //1-4、按费用编码计算优惠券使用金额总和
            collect.forEach((key, value) -> {
                int sum = value.stream().
                        flatMap(child -> child.getReceiptList().stream())
                        .filter(child -> ReceiptMethodEnum.YHQ.getCode().equals(child.getReceiptMothed()))
                        .map(ReceiptInfoResponse.ReceiptListBean::getAmount)
                        .map(Integer::valueOf)
                        .reduce(0, Integer::sum);
                reduceMap.put(key, sum);
            });
            //1-5、按费用项分类求每个费用项已缴金额总和
            havaSumMap = listBill.stream()
                    .collect(Collectors.groupingBy(
                            ReceiptBillResponse::getCostCode,
                            Collectors.reducing(0,
                                    ReceiptBillResponse::getReceivedBillAmount,
                                    Integer::sum)));
            //1-6、计算逾期违约金应缴总金额
            yqwyjSum = listBill.stream()
                    .filter(p -> CostCodeEnum.KHYQWYJ.getCode().equals(p.getCostCode()))
                    .map(ReceiptBillResponse::getReceiptBillAmount)
                    .reduce(0, Integer::sum);
        }
        
        //2-2查询合同表信息 edit by xiangb 置前
        String contractResult = rentContractServiceProxy.findContractBaseByContractId(searchPojo.getContractId());
        DataTransferObject contractDto = JsonEntityTransform.json2DataTransferObject(contractResult);
        RentContractEntity rentContractEntity = contractDto.parseData("rentContractEntity",
                new TypeReference<RentContractEntity>() {
                });
        
        CalNeedPojo calNeedPojo = new CalNeedPojo();
        calNeedPojo.setSearchPojo(searchPojo);
        SurrenderCostSumBodyVo subResultOfFZ = new SurrenderCostSumBodyVo(searchPojo.getSurrenderId(), searchPojo.getSurrenderCostId(), searchPojo.getContractId(),
                searchPojo.getConRentCode(), searchPojo.getRoomId(), searchPojo.getHouseRoomNo(), null, ExpenseItemEnum.FZ.getId(),
                ExpenseItemEnum.FZ.getName(), BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, 1);
        SurrenderCostSumBodyVo subResultOfFWF = new SurrenderCostSumBodyVo(searchPojo.getSurrenderId(), searchPojo.getSurrenderCostId(), searchPojo.getContractId(),
                searchPojo.getConRentCode(), searchPojo.getRoomId(), searchPojo.getHouseRoomNo(), null, ExpenseItemEnum.FWF.getId(),
                ExpenseItemEnum.FWF.getName(), BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, 2);
        SurrenderCostSumBodyVo subResultOfYJ = new SurrenderCostSumBodyVo(searchPojo.getSurrenderId(), searchPojo.getSurrenderCostId(), searchPojo.getContractId(),
                searchPojo.getConRentCode(), searchPojo.getRoomId(), searchPojo.getHouseRoomNo(), null, ExpenseItemEnum.YJ.getId(),
                ExpenseItemEnum.YJ.getName(), BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, 3);
        SurrenderCostSumBodyVo subResultOfTCF = new SurrenderCostSumBodyVo(searchPojo.getSurrenderId(), searchPojo.getSurrenderCostId(), searchPojo.getContractId(),
                searchPojo.getConRentCode(), searchPojo.getRoomId(), searchPojo.getHouseRoomNo(), null, ExpenseItemEnum.TCF.getId(),
                ExpenseItemEnum.TCF.getName(), BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, 4);
        SurrenderCostSumBodyVo subResultOfSF = new SurrenderCostSumBodyVo(searchPojo.getSurrenderId(), searchPojo.getSurrenderCostId(), searchPojo.getContractId(),
                searchPojo.getConRentCode(), searchPojo.getRoomId(), searchPojo.getHouseRoomNo(), null, ExpenseItemEnum.SF.getId(),
                ExpenseItemEnum.SF.getName(), BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, 5);
        SurrenderCostSumBodyVo subResultOfDF = new SurrenderCostSumBodyVo(searchPojo.getSurrenderId(), searchPojo.getSurrenderCostId(), searchPojo.getContractId(),
                searchPojo.getConRentCode(), searchPojo.getRoomId(), searchPojo.getHouseRoomNo(), null, ExpenseItemEnum.DF.getId(),
                ExpenseItemEnum.DF.getName(), BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, 6);
        SurrenderCostSumBodyVo subResultOfTZJGPCF = new SurrenderCostSumBodyVo(searchPojo.getSurrenderId(), searchPojo.getSurrenderCostId(), searchPojo.getContractId(),
                searchPojo.getConRentCode(), searchPojo.getRoomId(), searchPojo.getHouseRoomNo(), null, ExpenseItemEnum.TZJGPCFY.getId(),
                ExpenseItemEnum.TZJGPCFY.getName(), BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, 7);
        SurrenderCostSumBodyVo subResultOfWYJ = new SurrenderCostSumBodyVo(searchPojo.getSurrenderId(), searchPojo.getSurrenderCostId(), searchPojo.getContractId(),
                searchPojo.getConRentCode(), searchPojo.getRoomId(), searchPojo.getHouseRoomNo(), null, ExpenseItemEnum.WYJ.getId(),
                ExpenseItemEnum.WYJ.getName(), BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, 8);
        SurrenderCostSumBodyVo subResultOfYQWYJ = new SurrenderCostSumBodyVo(searchPojo.getSurrenderId(), searchPojo.getSurrenderCostId(), searchPojo.getContractId(),
                searchPojo.getConRentCode(), searchPojo.getRoomId(), searchPojo.getHouseRoomNo(), null, ExpenseItemEnum.YQWYJ.getId(),
                ExpenseItemEnum.YQWYJ.getName(), BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, 9);
        SurrenderCostSumBodyVo subResultOfQT = new SurrenderCostSumBodyVo(searchPojo.getSurrenderId(), searchPojo.getSurrenderCostId(), searchPojo.getContractId(),
                searchPojo.getConRentCode(), searchPojo.getRoomId(), searchPojo.getHouseRoomNo(), null, ExpenseItemEnum.QTFY.getId(),
                "其它", BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, 10);
        result.add(subResultOfFZ);
        result.add(subResultOfFWF);
        result.add(subResultOfYJ);
        result.add(subResultOfTCF);
        result.add(subResultOfSF);
        result.add(subResultOfDF);
        result.add(subResultOfTZJGPCF);
        result.add(subResultOfWYJ);
        result.add(subResultOfYQWYJ);
        result.add(subResultOfQT);

        subResultOfYQWYJ.setOriginalNum(BigDecimal.valueOf(yqwyjSum).divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_HALF_UP));

        for (Map.Entry<String, Integer> entry:havaSumMap.entrySet()){
            String key = entry.getKey();
            Integer value = entry.getValue();

            BigDecimal receivedBillAmount = BigDecimal.valueOf(value)
                    .divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_HALF_UP);
            //1-7、已缴金额减去优惠券支付的
            BigDecimal yhq = new BigDecimal(reduceMap.get(key) == null ? 0 : reduceMap.get(key))
                    .divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_HALF_UP);
            if (yhq.compareTo(BigDecimal.ZERO) > 0) {
                LogUtil.info(LOGGER, "[解约费用结算]合同id：{}，计算{}已缴费用时，共支付{}元，其中使用优惠券{}元",
                        searchPojo.getContractId(), key, receivedBillAmount, yhq);
                receivedBillAmount = receivedBillAmount.subtract(yhq);
            }
            //1-8、填充各项费用已缴金额（只计算房租、服务费、押金， 其它已缴都记为 0）
            if (key.equals(CostCodeEnum.KHFZ.getCode())) {//已缴房租
                subResultOfFZ.setActualNum(receivedBillAmount);
                calNeedPojo.setHavePayFZ(receivedBillAmount);
            } else if (key.equals(CostCodeEnum.KHFWF.getCode())) {//已缴服务费
                subResultOfFWF.setActualNum(receivedBillAmount);
                calNeedPojo.setHavePayFWF(receivedBillAmount);
            } else if (key.equals(CostCodeEnum.KHYJ.getCode())) {//已缴押金
            	subResultOfYJ.setActualNum(receivedBillAmount);
            	//add by xiangb 存在续约合同的，且续约合同已退租情况的，查询财务付款单
                if(!Check.NuNObj(rentContractEntity)){
                	List<PayVoucherResponse> payVoucehers = null;
                    payVoucehers = financeBaseCall.getPayVouchers(rentContractEntity.getConRentCode());
                    LogUtil.info(LOGGER, "[解约费用结算]合同id：{}，财务查询付款单返回：{}", searchPojo.getContractId(), JsonEntityTransform.Object2Json(payVoucehers));
                    if(!Check.NuNObj(payVoucehers)){
                    	 Optional<PayVoucherResponse> payVoucheryjzk = payVoucehers.stream().filter(p -> p.getPaymentTypeCode().equals(PayVoucherType.YJZK.getValue()) && String.valueOf(PayVoucherAuditFlagEnum.SHTG.getCode()).equals(p.getAuditFlag())).findFirst();
                         if(payVoucheryjzk.isPresent()){
//                             List<PayDetail> detailList = payVoucheryjzk.get().getDetailList();
//                             double sum = detailList.stream().mapToDouble(PayDetail::getAmount).sum();
                         	double sum = payVoucheryjzk.get().getTotal();
                             LogUtil.info(LOGGER, "[解约费用结算]合同id：{}，财务查询押金转款金额：{}", searchPojo.getContractId(), sum);
                     		subResultOfYJ.setActualNum(receivedBillAmount.subtract(new BigDecimal(sum)));
                         }
                    }
                }

                //} else if (key.equals(CostCodeEnum.TCF.getCode())) {//已缴停车费
                //subResultOfTCF.setActualNum(receivedBillAmount);
                //} else if (key.equals(CostCodeEnum.ZRYSF.getCode())) {//已缴水费
                //移除已缴水电费数据,因为这里的已缴水电费只关注最后一次的情况
                //} else if (key.equals(CostCodeEnum.ZRYDF.getCode())) {//已缴电费
                //移除已缴水电费数据,因为这里的已缴水电费只关注最后一次的情况
                //} else if (key.equals(CostCodeEnum.TZJGPCF.getCode())) {//已缴退租交割赔偿费用
                //subResultOfTZJGPCF.setActualNum(receivedBillAmount);
                //} else if (key.equals(CostCodeEnum.KHWYJ.getCode())) {//已缴违约金
                //subResultOfWYJ.setActualNum(receivedBillAmount);
            } else if (key.equals(CostCodeEnum.KHYQWYJ.getCode())) {//已缴逾期违约金
                subResultOfYQWYJ.setActualNum(receivedBillAmount);
            }
            //else {//其他费用
            //subResultOfQT.setActualNum(subResultOfQT.getActualNum().add(receivedBillAmount));
            //}

        }

        //==============================================================================================================
        //2、指定合同，计算应缴费用
        //2-1判断是否参加过海燕计划，因为新的优惠活动（在特洛伊系统中）不再是当前表结构，所以单独查询，方便以后修改
        boolean havePlanOfHaiYanOfQiLing = false;
        Integer i = rentContractActivityService.havePlanOfHaiYanOfQiLing(paramDto.getContractId(), "haiyan_plan_qiling");
        if (i != null && i > 0) {
            havePlanOfHaiYanOfQiLing = true;
        }
        calNeedPojo.setHavePlanOfHaiYanOfQiLing(havePlanOfHaiYanOfQiLing);
        
        //2-2查询合同表信息 edit by xiangb 置前
//        String contractResult = rentContractServiceProxy.findContractBaseByContractId(searchPojo.getContractId());
//        DataTransferObject contractDto = JsonEntityTransform.json2DataTransferObject(contractResult);
//        RentContractEntity rentContractEntity = contractDto.parseData("rentContractEntity",
//                new TypeReference<RentContractEntity>() {
//                });
        
        /*if (LeaseCycleEnum.DAY.getCode().equals(rentContractEntity.getConType())) {
            rentContractEntity.setFactualprice(rentContractEntity.getFactualprice() * 30);
        }*/
        
        calNeedPojo.setContract(rentContractEntity);
        calNeedPojo.setResNo(paramDto.getResNo());
        //2-3调用工厂模式计算应缴金额
        CalculateInterface calculateInterface = SurrenderReceiCalFactory.createComputationalFormula(calNeedPojo);
        CalReturnPojo calReturn = calculateInterface.calculate(calNeedPojo);
        LogUtil.info(LOGGER, "[解约费用结算]合同id：{}，计算结果{}", searchPojo.getContractId(), calReturn.toString());
        //1)、应缴房租信息
        BigDecimal needPayFZ = calReturn.getNeedPayFZ().setScale(2, BigDecimal.ROUND_HALF_UP);
        needPayFZ = needPayFZ.compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO : needPayFZ;
        subResultOfFZ.setOriginalNum(needPayFZ);
        //2)、应缴服务费
        BigDecimal needPayFWF = calReturn.getNeedPayFWF().setScale(2, BigDecimal.ROUND_HALF_UP);
        needPayFWF = needPayFWF.compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO : needPayFWF;
        subResultOfFWF.setOriginalNum(needPayFWF);
        //3)、应缴应缴押金 
        subResultOfYJ.setOriginalNum(BigDecimal.ZERO);
        //4)、应缴停车费
        subResultOfTCF.setOriginalNum(BigDecimal.ZERO);
        //5)、6)、查询水电交割信息 获取应缴水电交割信息数据
        BigDecimal standardSF = searchPojo.getStandardSF();
        BigDecimal standardDF = searchPojo.getStandardDF();
        if (standardSF.compareTo(BigDecimal.ZERO) < 0) {
            subResultOfSF.setActualNum(BigDecimal.ZERO);
            subResultOfSF.setOriginalNum(standardSF.abs());
        } else {
            subResultOfSF.setActualNum(standardSF);
            subResultOfSF.setOriginalNum(BigDecimal.ZERO);
        }
        if (standardDF.compareTo(BigDecimal.ZERO) < 0) {
            subResultOfDF.setActualNum(BigDecimal.ZERO);
            subResultOfDF.setOriginalNum(standardDF.abs());
        } else {
            subResultOfDF.setActualNum(standardDF);
            subResultOfDF.setOriginalNum(BigDecimal.ZERO);
        }
        //7)、应缴退租交割赔偿费用
        subResultOfTZJGPCF.setOriginalNum(searchPojo.getNeedPayTZJGPCF());
        //8)、应缴违约金
        BigDecimal needPayWYJ = calReturn.getNeedPayWYJ().setScale(2, BigDecimal.ROUND_HALF_UP);
        subResultOfWYJ.setOriginalNum(needPayWYJ);
        //9)、应缴其他费用
        subResultOfQT.setOriginalNum(BigDecimal.ZERO);

        //==============================================================================================================
        //3、统一计算应退费用
        result.forEach(subResult -> subResult.setRefundNum(subResult.getActualNum().subtract(subResult.getOriginalNum())));
        //==============================================================================================================
        return result;
    }

    //******************************************************************************************************************
    //******************************************************************************************************************
    //******************************************************************************************************************

    /**
     * 解约协议：费用结算页点击下一步（包括企业和个人）、企业单个合同的保存<br/>
     *
     * @Author: wangxm113
     * @Date: 2017年10月17日 13时49分54秒
     */
    @Override
    public String saveSurrenderCost(String paramStr) {
        LogUtil.info(LOGGER, "[解约费用结算下一步]入参{}", paramStr);
        DataTransferObject dto = new DataTransferObject();
        ObjectMapper objectMapper = new ObjectMapper();
        SurrenderCostNextDto paramDto;
        try {
            paramDto = objectMapper.readValue(paramStr, SurrenderCostNextDto.class);
            //==========================================================================================================
            //1、查询父解约协议下对应的子解约协议信息
            Map<String, List<SurrenderCostSumBodyVo>> map = new HashMap<>();
            String surParentId = paramDto.getSurParentId();
            String contractId = paramDto.getContractId();
            List<SurrenderEntity> surrenderIdList = new ArrayList<>();
//            contractId = null;
            if (surParentId != null && !surParentId.isEmpty() && (contractId == null || contractId.isEmpty())) {//企业的下一步
                surrenderIdList = surrenderServiceImpl.getSurListByParentId(surParentId);
            } else if (contractId != null && !contractId.isEmpty()) {//个人的下一步或者企业单个合同的保存
                SurrenderEntity surrenderEntity = surrenderServiceImpl.getSurrenderByConId(contractId);
                surrenderIdList.add(surrenderEntity);
            }
            surrenderIdList.forEach(p -> {
                CostItemAccountDto calParamDto = new CostItemAccountDto();
                calParamDto.setAnew(0);//不强制重新计算
                calParamDto.setContractId(p.getContractId());
                calParamDto.setResNo(Integer.valueOf(paramDto.getFresponsibility()));
                List<SurrenderCostSumBodyVo> responseList;
                if (paramDto.getNeedReCal() == 0) {//是否需要重新计算（0：不需要；1：需要）
                    //保存页面的费用明细（个人的下一步+批量解约保存单个合同时）
                    responseList = paramDto.getCostItemEntityList();
                } else {
                    //去计算费用明细
                    String calResponse = getCostItemAccount(JSONObject.toJSONString(calParamDto));
                    DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(calResponse);
                    responseList = resultDto.parseData("list",
                            new TypeReference<List<SurrenderCostSumBodyVo>>() {
                            });
                }
                map.put(p.getContractId(), responseList);
            });
            //==========================================================================================================
            //2、更新tsurrender、保存/更新tsurrendercost和tsurrendercostitem、生成应收账单或付款单
            surrenderServiceImpl.updateSurAndOperSurCostAndCostItem(surrenderIdList, paramDto, map);

            return dto.toJsonString();
        } catch (Exception e) {
            LogUtil.error(LOGGER, "[费用结算下一步]保存出错！错误信息={}", e);
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("系统异常！");
            return dto.toJsonString();
        }
    }

    //******************************************************************************************************************
    //******************************************************************************************************************
    //******************************************************************************************************************

    /**
     * 作废解约协议
     *
     * @Author: wangxm113
     * @Date: 2017年10月24日 10时50分33秒
     */
    @Override
    public String cancelSurrender(String paramStr) {
        LogUtil.info(LOGGER, "[作废解约协议]入参{}", paramStr);
        DataTransferObject dto = new DataTransferObject();
        ObjectMapper objectMapper = new ObjectMapper();
        CancelSurrenderDto paramDto;
        try {
            paramDto = objectMapper.readValue(paramStr, CancelSurrenderDto.class);
            //==========================================================================================================
            List<SyncContractVo> list = surrenderServiceImpl.updateForCancelSurrender(paramDto);

            /*list.forEach(p -> {
                String synResponseStr = callFinanceServiceProxy.updateContract(JsonEntityTransform.Object2Json(p));
                DataTransferObject syncContractDto = JsonEntityTransform.json2DataTransferObject(synResponseStr);
                if (syncContractDto.getCode() == DataTransferObject.ERROR) {
                    LogUtil.error(LOGGER, "[作废解约协议]同步财务合同状态出错！");
                }
            });*/
            return dto.toJsonString();
        } catch (Exception e) {
            LogUtil.error(LOGGER, "[作废解约协议]出错！错误信息={}", e);
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("作废解约协议出错！");
            return dto.toJsonString();
        }
    }

    //******************************************************************************************************************
    //******************************************************************************************************************
    //******************************************************************************************************************

    /**
     * 解约-最后一步：确认解约
     *
     * @Author: wangxm113
     * @Date: 2017年10月24日 13时49分54秒
     */
    @Override
    public String doSurrender(String paramStr) {
        LogUtil.info(LOGGER, "[确认解约]入参：{}", paramStr);
        DataTransferObject dto = new DataTransferObject();
        ObjectMapper objectMapper = new ObjectMapper();
        CancelSurrenderDto paramDto;
        try {
            paramDto = objectMapper.readValue(paramStr, CancelSurrenderDto.class);
            //==========================================================================================================
            String surParentId = paramDto.getSurParentId();
            String contractId = paramDto.getContractId();
            //1、校验是否已经作废
            List<SurrenderEntity> list = new ArrayList<>();
            if (surParentId != null && !surParentId.isEmpty()) {
                list = surrenderServiceImpl.getSurListByParentId(surParentId);
            } else {
            	SurrenderEntity surrenderByConId = surrenderServiceImpl.getSurrenderByConId(contractId);
                list.add(surrenderByConId);
            }
            if (list.isEmpty()) {
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("该解约协议已经作废或删除！");
                return dto.toJsonString();
            }
            //==========================================================================================================
            //2、将合同状态更改为[已退租]、判断是否需要释放房间、将解约协议更改状态为【已提交】
            List<String> releaseRoomList = surrenderServiceImpl.updateForDoSurrender(list, paramDto);
            //==========================================================================================================
            //3、释放房间
            String responseStr = roomService.updateRoomInfoForRelease(JsonEntityTransform.Object2Json(releaseRoomList));
            DataTransferObject dataTransferObject = JsonEntityTransform.json2DataTransferObject(responseStr);
            if (dataTransferObject.getCode() == DataTransferObject.ERROR) {
                //回滚
                surrenderServiceImpl.rollBackForDoSurrender(list, paramDto);
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("释放房间出错！");
                return dto.toJsonString();
            }
            //==========================================================================================================
            //4、同步合同到财务
            SyncContractVo syncContractVo;
            for (SurrenderEntity surrenderEntity : list) {
                String oldDataContractId = rentContractServiceProxy.finOldDataContractId(surrenderEntity.getContractId());
                syncContractVo = new SyncContractVo();
                syncContractVo.setCrmContractId(oldDataContractId);
                syncContractVo.setRentContractCode(surrenderEntity.getConRentCode());
                syncContractVo.setStatusCode(ContractStatusEnum.YTZ.getStatus());
                String synResponseStr = callFinanceServiceProxy.updateContract(JsonEntityTransform.Object2Json(syncContractVo));
                DataTransferObject syncContractDto = JsonEntityTransform.json2DataTransferObject(synResponseStr);
                if (syncContractDto.getCode() == DataTransferObject.ERROR) {
                    //回滚
                    surrenderServiceImpl.rollBackForDoSurrender(list, paramDto);
                    dto.setErrCode(DataTransferObject.ERROR);
                    dto.setMsg(syncContractDto.getMsg());
                    return dto.toJsonString();
                }
            }
            return dto.toJsonString();
        } catch (Exception e) {
            LogUtil.error(LOGGER, "[确认解约]出错！错误信息={}", e);
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("确认解约出错！");
            return dto.toJsonString();
        }
    }

    //******************************************************************************************************************
    //******************************************************************************************************************
    //******************************************************************************************************************

    /**
     * 获取批量解约的合同、房间信息
     *
     * @Author: wangxm113
     * @Date: 2017年11月04日 17时28分52秒
     */
    @Override
    public String getSurRoomList(String paramStr) {
        DataTransferObject dto = new DataTransferObject();
        try {
            LogUtil.info(LOGGER, "[企业解约协议详情-物业交割]入参：{}", paramStr);
            ObjectMapper objectMapper = new ObjectMapper();
            SurRoomListDto paramDto;
            paramDto = objectMapper.readValue(paramStr, SurRoomListDto.class);
            //==========================================================================================================
            PagingResult<SurRoomListReturnDto> roomList = surrenderServiceImpl.getSurRoomList(paramDto);
            Map<String, Object> map = new HashMap<>();
            map.put("total", roomList.getTotal());
            map.put("list", roomList.getRows());
            dto.putValue("list", map);
            return dto.toJsonString();
        } catch (Exception e) {
            LogUtil.error(LOGGER, "[企业解约协议详情-物业交割]出错！错误信息={}", e);
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("系统异常！");
            return dto.toJsonString();
        }
    }

    //******************************************************************************************************************
    //******************************************************************************************************************
    //******************************************************************************************************************

    /**
     * 修改解约协议时点提交审核
     *
     * @Author: wangxm113
     * @Date: 2017年11月07日 15时35分10秒
     */
    @Override
    public String editCommitAudit(String paramStr) {
        DataTransferObject dto = new DataTransferObject();
        try {
            LogUtil.info(LOGGER, "[修改解约协议-提交审核]入参：{}", paramStr);
            ObjectMapper objectMapper = new ObjectMapper();
            SurRoomListDto paramDto;
            try {
                paramDto = objectMapper.readValue(paramStr, SurRoomListDto.class);
            } catch (IOException e) {
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("参数转SurRoomListDto出错！");
                return dto.toJsonString();
            }
            //==========================================================================================================
            //1、获取解约协议
            String surParentId = paramDto.getSurParentId();
            String contractId = paramDto.getContractId();
            List<SurrenderEntity> surList = new ArrayList<>();
            if (surParentId != null && !surParentId.isEmpty()) {
                surList = surrenderServiceImpl.getSurListByParentId(surParentId);
            } else if (contractId != null && !contractId.isEmpty()) {
                SurrenderEntity surrender = surrenderServiceImpl.getSurrenderByConId(contractId);
                surList.add(surrender);
            }
            //==========================================================================================================
            //2、还原tsurrender表、还原tsurrendercost表
            Integer i = surrenderServiceImpl.editCommitAudit(contractId, surParentId);
            if (i <= 0) {
                LogUtil.error(LOGGER, "[修改解约协议-提交审核]还原tsurrender、tsurrendercost表出错！");
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("还原tsurrender、tsurrendercost表出错！");
                return dto.toJsonString();
            }
            //==========================================================================================================
            //3、同步合同到财务
            SyncContractVo syncContractVo = new SyncContractVo();
            for (SurrenderEntity surrenderEntity : surList) {
                syncContractVo.setCrmContractId(surrenderEntity.getContractId());
                syncContractVo.setRentContractCode(surrenderEntity.getConRentCode());
                syncContractVo.setStatusCode(ContractStatusEnum.YTZ.getStatus());
                String synResponseStr = callFinanceServiceProxy.updateContract(JsonEntityTransform.Object2Json(syncContractVo));
                /*DataTransferObject syncContractDto = JsonEntityTransform.json2DataTransferObject(synResponseStr);
                if (syncContractDto.getCode() == DataTransferObject.ERROR) {
                    LogUtil.error(LOGGER, "[修改解约协议-提交审核]同步财务合同状态出错！");
                    dto.setErrCode(DataTransferObject.ERROR);
                    dto.setMsg("同步财务合同状态出错！");
                    return dto.toJsonString();
                }*/
            }
            return dto.toJsonString();
        } catch (Exception e) {
            LogUtil.error(LOGGER, "[修改解约协议-提交审核]出错！错误信息={}", e);
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("系统异常！");
            return dto.toJsonString();
        }
    }
    /**
     * <p>保存解约协议</p>
     * @author xiangb
     * @created 2017年10月30日
     * @param
     * @return
     */
    @Override
	public String saveSurrender(String paramJson) {
		 LogUtil.info(LOGGER, "【saveSurrender】入参：{}", paramJson);
		 DataTransferObject dto = new DataTransferObject();
		 if(Check.NuNStr(paramJson)){
			 dto.setErrCode(DataTransferObject.ERROR);
			 dto.setMsg("参数为空");
			 return dto.toJsonString();
		 }
		 try{
			 SurrenderEntity surrender = JsonEntityTransform.json2Entity(paramJson,
					 SurrenderEntity.class);
			 int isSuccess = surrenderServiceImpl.saveSurrender(surrender);
			 if(isSuccess > 0){
				 return dto.toJsonString();
			 }else{
				 dto.setErrCode(DataTransferObject.ERROR);
				 dto.setMsg("保存失败");
				 return dto.toJsonString();
			 }
		 }catch(Exception e){
			 LogUtil.info(LOGGER, "【saveSurrender】出错：{}", e);
			 dto.setErrCode(DataTransferObject.ERROR);
			 dto.setMsg("系统异常");
			 return dto.toJsonString();
		 }
	}
    /**
     * <p>1.查询合同当前状态</p>
     * <p>2.判断当前合同是否有欠款</p>
     * <p>3.申请是否逾期</p>
     * @author xiangb
     * @created 2017年11月8日
     * @param
     * @return
     */
	@Override
	public String queryConStatus(String contractId) {
		LogUtil.info(LOGGER, "【queryConStatus】入参：{}", contractId);
		DataTransferObject dto = new DataTransferObject();
		if(Check.NuNStr(contractId)){
			 dto.setErrCode(DataTransferObject.ERROR);
			 dto.setMsg("合同号为空");
			 return dto.toJsonString();
		}
		try{
			RentContractEntity rentContractEntity = rentContractServiceImpl.findContractBaseByContractId(contractId);
			LogUtil.info(LOGGER, "【queryConStatus】查询合同返回：{}", JsonEntityTransform.Object2Json(rentContractEntity));
			if(Check.NuNObj(rentContractEntity)){
				 dto.setErrCode(DataTransferObject.ERROR);
				 dto.setMsg("未查询到合同信息");
				 return dto.toJsonString();
			}
			if(CustomerTypeEnum.PERSON.getCode() == rentContractEntity.getCustomerType()){
				//查询合同状态
				if(!(ContractStatusEnum.JYZ.getStatus().equals(rentContractEntity.getConStatusCode()) || ContractStatusEnum.YTZ.getStatus().equals(rentContractEntity.getConStatusCode()))){
					//查询是否有欠款，
					String waitForPayStr = rentContractServiceProxy.findWaitforPaymentList(rentContractEntity.getConRentCode());
					LogUtil.info(LOGGER, "【queryConStatus】查询合同是否有欠款返回：{}",waitForPayStr);
					DataTransferObject waitForPayObj = JsonEntityTransform.json2DataTransferObject(waitForPayStr);
					if(Check.NuNObj(waitForPayObj) || waitForPayObj.getCode() == DataTransferObject.ERROR){
						 LogUtil.info(LOGGER, "【queryConStatus】查询合同是否有欠款异常,contractId:{}",contractId);
						 dto.setErrCode(DataTransferObject.ERROR);
						 dto.setMsg("系统错误");
						 return dto.toJsonString();
					}
					Object o = waitForPayObj.getData().get("allCount");
					int allCount = Check.NuNObj(o)?0:(int)o;
					if(allCount > 0){
						 LogUtil.info(LOGGER, "【queryConStatus】合同有欠款,,contractId:{}",contractId);
						 dto.setErrCode(DataTransferObject.ERROR);
						 dto.setMsg(QueryConStatusEnum.QIANKUAN.getName());
						 return dto.toJsonString();
					}
					//判断解约申请是否逾期
					Integer i = surrenderServiceImpl.updateSurrenderApplyTime(Integer.valueOf(surrenderApplyTime), contractId);
					if(i > 0){
						 LogUtil.info(LOGGER, "【queryConStatus】申请超时,contractId:{}",contractId);
						 dto.setErrCode(DataTransferObject.ERROR);
						 dto.setMsg(QueryConStatusEnum.APPLYEXPIRE.getName());
						 return dto.toJsonString();
					}
					//解约正常返回
					LogUtil.info(LOGGER, "【queryConStatus】解约正常返回,contractId:{}",contractId);
					return dto.toJsonString();
				}else{
					 LogUtil.info(LOGGER, "【queryConStatus】合同状态为解约中,contractId:{}",contractId);
					 dto.setErrCode(DataTransferObject.ERROR);
					 dto.setMsg(QueryConStatusEnum.RUNNING.getName());
					 return dto.toJsonString();
				}
			}else{
				List<RentContractEntity> rentContracts = null;
				SurrenderEntity surrenderEntity = surrenderServiceImpl.findSurrenderById(rentContractEntity.getFsurrenderid());
				if(!Check.NuNObj(surrenderEntity) && !Check.NuNStr(surrenderEntity.getSurParentId())){
					List<SurrenderEntity> surrenders = surrenderServiceImpl.getSurListByParentId(surrenderEntity.getSurParentId());
					List<String> contracts = surrenders.stream().map(SurrenderEntity::getContractId).collect(Collectors.toList());
					rentContracts = rentContractServiceImpl.findContractListByContractIds(contracts);
					if(!Check.NuNCollection(rentContracts)){
						for(RentContractEntity newRentContract:rentContracts){
							//查询合同状态
							if(!(ContractStatusEnum.JYZ.getStatus().equals(newRentContract.getConStatusCode()) || ContractStatusEnum.YTZ.getStatus().equals(newRentContract.getConStatusCode()))){
								//查询是否有欠款，
								String waitForPayStr = rentContractServiceProxy.findWaitforPaymentList(newRentContract.getConRentCode());
								LogUtil.info(LOGGER, "【queryConStatus】查询合同是否有欠款返回：{}",waitForPayStr);
								DataTransferObject waitForPayObj = JsonEntityTransform.json2DataTransferObject(waitForPayStr);
								if(Check.NuNObj(waitForPayObj) || waitForPayObj.getCode() == DataTransferObject.ERROR){
									 LogUtil.info(LOGGER, "【queryConStatus】查询合同是否有欠款异常,contractId:{}",contractId);
									 dto.setErrCode(DataTransferObject.ERROR);
									 dto.setMsg("系统错误");
									 return dto.toJsonString();
								}
								Object o = waitForPayObj.getData().get("allCount");
								int allCount = Check.NuNObj(o)?0:(int)o;
								if(allCount > 0){
									 LogUtil.info(LOGGER, "【queryConStatus】合同有欠款,,contractId:{}",newRentContract.getContractId());
									 dto.setErrCode(DataTransferObject.ERROR);
									 dto.setMsg(QueryConStatusEnum.QIANKUAN.getName());
									 return dto.toJsonString();
								}
								//判断解约申请是否逾期
								Integer i = surrenderServiceImpl.updateSurrenderApplyTime(Integer.valueOf(surrenderApplyTime), contractId);
								if(i > 0){
									 LogUtil.info(LOGGER, "【queryConStatus】申请超时,contractId:{}",newRentContract.getContractId());
									 dto.setErrCode(DataTransferObject.ERROR);
									 dto.setMsg(QueryConStatusEnum.APPLYEXPIRE.getName());
									 return dto.toJsonString();
								}
								//解约正常返回
								LogUtil.info(LOGGER, "【queryConStatus】解约正常返回,contractId:{}",newRentContract.getContractId());
								continue;
							}else{
								 LogUtil.info(LOGGER, "【queryConStatus】合同状态为解约中,contractId:{}",newRentContract.getContractId());
								 dto.setErrCode(DataTransferObject.ERROR);
								 dto.setMsg(QueryConStatusEnum.RUNNING.getName());
								 return dto.toJsonString();
							}
						}
						return dto.toJsonString();
					}else{
						 LogUtil.error(LOGGER, "【queryConStatus】contractId:{}查询父合同为空", contractId);
						 dto.setErrCode(DataTransferObject.ERROR);
						 dto.setMsg("系统异常");
						 return dto.toJsonString();	
					}
				}else{
					 LogUtil.error(LOGGER, "【queryConStatus】contractId:{}查询父合同为空", contractId);
					 dto.setErrCode(DataTransferObject.ERROR);
					 dto.setMsg(QueryConStatusEnum.HAVEDELETE.getName());
					 return dto.toJsonString();	
				}
			}
		}catch(Exception e){
			 LogUtil.error(LOGGER, "【queryConStatus】contractId:{}出错：{},",contractId, e);
			 dto.setErrCode(DataTransferObject.ERROR);
			 dto.setMsg("系统异常");
			 return dto.toJsonString();
		 }
	}
	/**
     * <p>根据合同ID查询申请解约房间</p>
     * @author xiangb
     * @created 2017年11月13日
     * @param
     * @return
     */
	@Override
	public String querySurrenderHouse(String contractId) {
		LogUtil.info(LOGGER, "【querySurrenderHouse】入参：{}", contractId);
		DataTransferObject dto = new DataTransferObject();
		if(Check.NuNStr(contractId)){
			 dto.setErrCode(DataTransferObject.ERROR);
			 dto.setMsg("合同ID为空！");
			 return dto.toJsonString();
		}
		SurrenderEntity  surrenderEntity = surrenderServiceImpl.getSurrenderByConId(contractId);
		if(Check.NuNObj(surrenderEntity)){
			LogUtil.info(LOGGER, "【querySurrenderHouse】解约协议不存在：{}", contractId);
			 dto.setErrCode(DataTransferObject.ERROR);
			 dto.setMsg("解约协议不存在！");
			 return dto.toJsonString();
		}
		if(Check.NuNStr(surrenderEntity.getSurParentId())){
			 LogUtil.info(LOGGER, "【querySurrenderHouse】解约协议不存在父解约协议号：{}", contractId);
			 dto.setErrCode(DataTransferObject.ERROR);
			 dto.setMsg("该合同不存在父解约协议号！");
			 return dto.toJsonString();
		}
		List<SurrenderEntity> surrenderEntityList = surrenderServiceImpl.getSurListByParentId(surrenderEntity.getSurParentId());
		String roomIds = surrenderEntityList.stream().map(SurrenderEntity::getRoomId).collect(Collectors.joining(","));
		String roomListStr = roomService.getRoomListByRoomIds(roomIds);
		List<RoomInfoEntity>  roomInfoEntityList = null;
		DataTransferObject roomListObj = JsonEntityTransform.json2DataTransferObject(roomListStr);
		if(roomListObj.getCode() == DataTransferObject.ERROR){
			 LogUtil.info(LOGGER, "【querySurrenderHouse】查询解约房间出错：{}", roomListStr);
			 dto.setErrCode(DataTransferObject.ERROR);
			 dto.setMsg("查询解约房间出错");
			 return dto.toJsonString();
		}
		roomInfoEntityList = roomListObj.parseData("roomInfoList", new TypeReference<List<RoomInfoEntity>>() {
		});
		if(Check.NuNCollection(roomInfoEntityList)){
			 LogUtil.info(LOGGER, "【querySurrenderHouse】查询解约房间列表为空：{}", contractId);
			 dto.setErrCode(DataTransferObject.ERROR);
			 dto.setMsg("查询解约房间出错");
			 return dto.toJsonString();
		}
		dto.putValue("roomInfoEntityList", roomInfoEntityList);
		return dto.toJsonString();
	}
	/**
     * <p>更新解约协议</p>
     * @author xiangb
     * @created 2017年11月14日
     * @param
     * @return
     */
	@Override
    public String updateSurrender(String surrender){
		LogUtil.info(LOGGER, "【updateSurrender】入参：{}", surrender);
    	DataTransferObject dto = new DataTransferObject();
    	try{
    		SurrenderEntity surrenderEntity = JsonEntityTransform.json2Entity(surrender, SurrenderEntity.class);
    		this.surrenderServiceImpl.updateSurrender(surrenderEntity);
    		return dto.toJsonString();
    	}catch(Exception e){
    		LogUtil.info(LOGGER, "【updateSurrender】出错：{}", e);
    		dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("系统异常");
			return dto.toJsonString();
    	}
    	
    }
	
	/**
     * <p>根据ID查询解约协议</p>
     * @author xiangb
     * @created 2017年11月16日
     * @param
     * @return
     */
    public String findSurrenderById(String surrenderId){
    	LogUtil.info(LOGGER, "【findSurrenderById】入参：{}", surrenderId);
    	DataTransferObject dto = new DataTransferObject();
    	try{
    		SurrenderEntity surrender = surrenderServiceImpl.findSurrenderById(surrenderId);
    		LogUtil.info(LOGGER, "【findSurrenderById】查询解约协议返回：{}", JsonEntityTransform.Object2Json(surrender));
    		if(Check.NuNObj(surrender)){
    			dto.setErrCode(DataTransferObject.ERROR);
    			dto.setMsg("未查询到解约协议");
    			return dto.toJsonString();
    		}
    		dto.putValue("surrender", surrender);
    		return dto.toJsonString();
    	}catch(Exception e){
    		LogUtil.info(LOGGER, "【findSurrenderById】出错：{}", e);
    		dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("系统异常");
			return dto.toJsonString();
    	}
    }
    
    /**
     * 退租水电交割表的水电交割费用
     *
     * @Author: wangxm113
     * @Date: 2017年11月04日 17时18分12秒
     */
    @Override
    public String getSDPriceBySurrenderId(String surrenderId){
    	LogUtil.info(LOGGER, "【getSDPriceBySurrenderId】入参：{}", surrenderId);
    	DataTransferObject dto = new DataTransferObject();
    	try{
    		SurMeterDetailEntity surMeterDetailEntity = surMeterDetailServiceImpl.getSDPriceBySurrenderId(surrenderId);
    		LogUtil.info(LOGGER, "【getSDPriceBySurrenderId】查询退租水电交割实体返回：{}", JsonEntityTransform.Object2Json(surMeterDetailEntity));
    		if(Check.NuNObj(surMeterDetailEntity)){
    			dto.setErrCode(DataTransferObject.ERROR);
    			dto.setMsg("未查询到退租水电交割实体");
    			return dto.toJsonString();
    		}
    		dto.putValue("surMeterDetailEntity", surMeterDetailEntity);
    		return dto.toJsonString();
    	}catch(Exception e){
    		LogUtil.error(LOGGER, "【getSDPriceBySurrenderId】出错：{}", e);
    		dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("系统异常");
			return dto.toJsonString();
    	}
    }

    /**
     * <p>保存或者更新退租水电交割</p>
     * @author xiangb
     * @created 2017年11月20日
     * @param
     * @return
     */
    @Override
    public String saveOrUpdateSurMeterDetail(String surMeterDetail){
    	LogUtil.info(LOGGER, "【saveOrUpdateSurMeterDetail】入参：{}", surMeterDetail);
    	DataTransferObject dto = new DataTransferObject();
    	try{
    		SurMeterDetailEntity surMeterDetailEntity = JsonEntityTransform.json2Entity(surMeterDetail,SurMeterDetailEntity.class);
    		if(Check.NuNObj(surMeterDetailEntity)){
    			dto.setErrCode(DataTransferObject.ERROR);
    			dto.setMsg("入参异常");
    			return dto.toJsonString();
    		}
    		// 如果存在房间装修信息则更新，否则新增
            if (Check.NuNStr(surMeterDetailEntity.getFid())) {
                surMeterDetailServiceImpl.saveSurMeterDetailEntity(surMeterDetailEntity);
            } else {
            	surMeterDetailServiceImpl.updateSurMeterDetailEntity(surMeterDetailEntity);
            }
    		return dto.toJsonString();
    	}catch(Exception e){
    		LogUtil.info(LOGGER, "【saveOrUpdateSurMeterDetail】出错：{}", e);
    		dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("系统异常");
			return dto.toJsonString();
    	}
    }
    /**
     * <p>根据合同ID和解约ID查询</p>
     * @author xiangb
     * @created 2017年11月21日
     * @param
     * @return
     */
    @Override
    public String selectSurrenderCostByParam(String param){
    	LogUtil.info(LOGGER, "【selectSurrenderCostByParam】入参：{}", param);
        DataTransferObject dto = new DataTransferObject();
        try{
        	Map<String,String> map = (Map<String, String>) JsonEntityTransform.json2Map(param);
            String contractId = map.get("contractId");
            String surrenderId = map.get("surrenderId");
            if(Check.NuNStr(contractId) || Check.NuNStr(surrenderId)){
            	dto.setErrCode(DataTransferObject.ERROR);
            	dto.setMsg("参数为空");
            	return dto.toJsonString();
            }
            List<SurrenderCostEntity> costEntitys =  surrenderCostServiceImpl.selectSurrenderCostByParam(contractId, surrenderId);
            if(!Check.NuNCollection(costEntitys)){
            	dto.putValue("surrenderCostEntity", costEntitys.get(0));
            }
            return dto.toJsonString();
        }catch(Exception e){
        	LogUtil.error(LOGGER, "【selectSurrenderCostByParam】异常：{}", e);
        	dto.setErrCode(DataTransferObject.ERROR);
        	dto.setMsg("系统异常");
        	return dto.toJsonString();
        } 
    }
    /**
     * <p>根据解约ID查询</p>
     * @author xiangb
     * @created 2017年11月21日
     * @param
     * @return
     */
    @Override
    public String findSurrenderCostByFid(String surrendercostId){
    	LogUtil.info(LOGGER, "【findSurrenderCostByFid】入参：{}", surrendercostId);
        DataTransferObject dto = new DataTransferObject();
        try{
            if(Check.NuNStr(surrendercostId)){
            	dto.setErrCode(DataTransferObject.ERROR);
            	dto.setMsg("参数为空");
            	return dto.toJsonString();
            }
            SurrenderCostEntity costEntity =  surrenderCostServiceImpl.findSurrenderCostByFid(surrendercostId);
            if(!Check.NuNObj(costEntity)){
            	dto.putValue("surrenderCostEntity", costEntity);
            }
            return dto.toJsonString();
        }catch(Exception e){
        	LogUtil.error(LOGGER, "【findSurrenderCostByFid】异常：{}", e);
        	dto.setErrCode(DataTransferObject.ERROR);
        	dto.setMsg("系统异常");
        	return dto.toJsonString();
        } 
    }
    /**
     * <p>查询已缴租期的最大值</p>
     * @author xiangb
     * @created 2017年11月21日
     * @param
     * @return
     */
    @Override
    public String findMaxDate(String conRentCode){
    	LogUtil.info(LOGGER, "【findMaxDate】入参：{}", conRentCode);
        DataTransferObject dto = new DataTransferObject();
        if(Check.NuNStr(conRentCode)){
        	dto.setErrCode(DataTransferObject.ERROR);
        	dto.setMsg("参数为空");
        	return dto.toJsonString();
        }
        try{
        	//查询房租的应收账单
        	List<ReceiptBillResponse> receivableBillList = callFinanceServiceProxy.getBillListByType(conRentCode, DocumentTypeEnum.RENT_FEE.getCode(), null);
        	//选出已核销的截止日期最大的日期。
        	receivableBillList = receivableBillList.stream().filter(v -> v.getVerificateStatus() == VerificateStatusEnum.DONE.getCode()).collect(Collectors.toList());
//提示ASM5异常        	.sorted(Comparator.comparing(ReceiptBillResponse::getBillsycleEndtime).reversed()).collect(Collectors.toList());
        	ReceiptBillResponse receiptBill = null;
        	for(ReceiptBillResponse receiptBillResponse :receivableBillList){
        		if(Check.NuNObj(receiptBill)){
        			receiptBill = receiptBillResponse;
        		}else{
        			if(receiptBillResponse.getBillsycleEndtime().compareTo(receiptBill.getBillsycleEndtime())>0){
        				receiptBill =receiptBillResponse;
        			}
        		}
        	}
        	if(!Check.NuNObj(receiptBill)){
        		dto.putValue("maxDate", receiptBill.getBillsycleEndtime());
        	}
        	return dto.toJsonString();
        }catch(Exception e){
        	LogUtil.error(LOGGER, "【findMaxDate】异常：{}", e);
        	dto.setErrCode(DataTransferObject.ERROR);
        	dto.setMsg("系统异常");
        	return dto.toJsonString();
        }
    }
    /**
     * <p>更新解约费用结算单</p>
     * @author xiangb
     * @created 2017年11月21日
     * @param
     * @return
     */
    @Override
    public String updateBySurId(String surrendercost){
    	LogUtil.info(LOGGER, "【updateBySurId】入参：{}", surrendercost);
        DataTransferObject dto = new DataTransferObject();
        try{
        	SurrenderCostEntity surrenderCostEntity = JsonEntityTransform.json2Entity(surrendercost, SurrenderCostEntity.class);
            if(Check.NuNObj(surrenderCostEntity)){
            	dto.setErrCode(DataTransferObject.ERROR);
            	dto.setMsg("参数为空");
            	return dto.toJsonString();
            }
            surrenderCostServiceImpl.updateBySurId(surrenderCostEntity);
            return dto.toJsonString();
        }catch(Exception e){
        	LogUtil.error(LOGGER, "【updateBySurId】异常：{}", e);
        	dto.setErrCode(DataTransferObject.ERROR);
        	dto.setMsg("系统异常");
        	return dto.toJsonString();
        } 
    }
    
    /**
     * <p>保存或更新解约审核不通过原因</p>
     * @author xiangb
     * @created 2017年11月21日
     * @param
     * @return
     */
    @Override
    public String saveOrUpdateSurrrendBackRecord(String surrendBackRecord){
    	LogUtil.info(LOGGER, "【saveOrUpdateSurrrendBackRecord】入参：{}", surrendBackRecord);
        DataTransferObject dto = new DataTransferObject();
        try{
        	SurrendBackRecordEntity surrendBackRecordEntity = JsonEntityTransform.json2Entity(surrendBackRecord, SurrendBackRecordEntity.class);
            if(Check.NuNObj(surrendBackRecordEntity)){
            	dto.setErrCode(DataTransferObject.ERROR);
            	dto.setMsg("参数为空");
            	return dto.toJsonString();
            }
            if(Check.NuNStr(surrendBackRecordEntity.getFid())){
            	surrendBackRecordServiceImpl.saveSurrendBackRecord(surrendBackRecordEntity);
            }else{
            	surrendBackRecordServiceImpl.updateSurrendBackRecord(surrendBackRecordEntity);
            }
            return dto.toJsonString();
        }catch(Exception e){
        	LogUtil.error(LOGGER, "【saveOrUpdateSurrrendBackRecord】异常：{}", e);
        	dto.setErrCode(DataTransferObject.ERROR);
        	dto.setMsg("系统异常");
        	return dto.toJsonString();
        } 
    }
    /**
     * <p>审核解约协议</p>
     * @author xiangb
     * @created 2017年11月21日
     * @param
     * @return
     */
    @Override
	public String confirmSurrAudit(String param) {
		LogUtil.info(LOGGER, "【confirmSurrAudit】入参：{}", param);
        DataTransferObject dto = new DataTransferObject();
        try{
        	Map<String,String> map = (Map<String, String>) JsonEntityTransform.json2Map(param);
			String surrenderId = map.get("surrenderId");
			String auditResult = map.get("auditResult");
			String noAuditReason = map.get("noAuditReason");
			String auditType = map.get("auditType");
			String employeeId = map.get("employeeId");
			String employeeName = map.get("employeeName");
			String surrenderIds = "";
			List<SurrenderEntity> surrenderList = new ArrayList<SurrenderEntity>();
			List<SurrendBackRecordEntity> surrenderBackRecordList = new ArrayList<SurrendBackRecordEntity>();
			List<SurrenderCostEntity> surrenderCostList = new ArrayList<SurrenderCostEntity>();
			SurrenderEntity surrenderNew = surrenderServiceImpl.findSurrenderById(surrenderId);
			List<SurrenderEntity> surrenderListNew = null;
			if(!Check.NuNObj(surrenderNew) && !Check.NuNStr(surrenderNew.getSurParentId())){
				surrenderListNew = surrenderServiceImpl.getSurListByParentId(surrenderNew.getSurParentId());
			}
			if(Check.NuNCollection(surrenderListNew)){
				surrenderListNew = new ArrayList<SurrenderEntity>();
				surrenderListNew.add(surrenderNew);
			}
			surrenderIds = surrenderListNew.stream().map(SurrenderEntity::getSurrenderId).collect(Collectors.joining(","));
			if(surrenderListNew.size() > 0){
				for(SurrenderEntity surrender : surrenderListNew){
					//租务审核
					if(String.valueOf(AuditTypeEnum.ZW.getType()).equals(auditType)){
						//根据解约协议号查询解约协议实体
						//设置租务首次审核日期
						if(surrender.getZwFirstAuditDate() == null){
							surrender.setZwFirstAuditDate(new Date());
						}
						//审核未通过
						if(SurrenderAuditResultEnum.WTG.getStatus().equals(auditResult)){
							//设置审核未通过状态
							surrender.setFrentauditstatus(SurrendAuditStatusEnum.SHBH.getStatus());
							//设置审核时间
							surrender.setFrentauditdate(new Date());
							//设置修改时间
							surrender.setFupdatetime(new Date());
							//设置审核人
							surrender.setFauditor(employeeId);
							surrender.setFauditorname(employeeName);
							surrenderList.add(surrender);
							//保存审核未通过原因
							SurrendBackRecordEntity surrendBackRecord = new SurrendBackRecordEntity();
							surrendBackRecord.setSurrenderid(surrenderId);
							surrendBackRecord.setFsender(employeeId);
							surrendBackRecord.setFsendername(employeeName);
							surrendBackRecord.setFsenddate(new Date());
							surrendBackRecord.setFbacktype(String.valueOf(AuditTypeEnum.ZW.getType()));
							surrendBackRecord.setFsendreason(noAuditReason);
							surrenderBackRecordList.add(surrendBackRecord);
						}else{ 
							//设置审核通过状态
							surrender.setFrentauditstatus(SurrendAuditStatusEnum.SHTG.getStatus());
							//设置审核时间
							surrender.setFrentauditdate(new Date());
							//设置修改时间
							surrender.setFupdatetime(new Date());
							//设置审核人
							surrender.setFauditor(employeeId);
							//设置租务审核通过日期
							surrender.setZwApproveDate(new Date());
							surrender.setFauditorname(employeeName);
							surrenderList.add(surrender);
						}
					}else if(String.valueOf(AuditTypeEnum.CW.getType()).equals(auditType)){//财务审核
						//根据解约协议号查询解约协议实体
						SurrenderCostEntity surrenderCost = null;
						String surrenderCostStr = this.findSurrenderCostByFid(surrender.getSurrendercostId());
						LogUtil.info(LOGGER,"【confirmSurrAudit】查询解约费用结算单返回："+surrenderCostStr);
						DataTransferObject surrenderCostObj = JsonEntityTransform.json2DataTransferObject(surrenderCostStr);
						if(surrenderCostObj.getCode() == DataTransferObject.SUCCESS){
							surrenderCost = surrenderCostObj.parseData("surrenderCostEntity", new TypeReference<SurrenderCostEntity>() {
							});				}
						if(surrenderCost==null){
							LogUtil.info(LOGGER,"调用SurrenderMgtServiceImpl-confirmSurrAudit方法异常surrenderCost实体为空"+surrenderCost);
							dto.setErrCode(DataTransferObject.ERROR);
				        	dto.setMsg("未查询到费用结算单");
				        	return dto.toJsonString();
						}
						//设置财务首次审核日期
						if(surrender.getCwFirstAuditDate() == null){
							surrender.setCwFirstAuditDate(new Date());
						}
						//审核未通过
						if(SurrenderAuditResultEnum.WTG.getStatus().equals(auditResult)){
							//设置审核未通过状态
							surrenderCost.setFfinanceauditstatus(Integer.valueOf(SurrendAuditStatusEnum.SHBH.getStatus()));
							//设置审核时间
							surrenderCost.setFfinanceauditdate(new Date());
							//设置审核人
							surrenderCost.setFinanceauditorid(employeeId);
							surrenderCost.setFinanceauditorname(employeeName);
							surrenderCostList.add(surrenderCost);
							//保存审核未通过原因
							SurrendBackRecordEntity surrendBackRecord = new SurrendBackRecordEntity();
							surrendBackRecord.setSurrenderid(surrenderId);
							surrendBackRecord.setFsender(employeeId);
							surrendBackRecord.setFsendername(employeeName);
							surrendBackRecord.setFsenddate(new Date());
							surrendBackRecord.setFbacktype(String.valueOf(AuditTypeEnum.CW.getType()));
							surrendBackRecord.setFsendreason(noAuditReason);
							surrenderBackRecordList.add(surrendBackRecord);
							//设置修改时间
							surrender.setFupdatetime(new Date());
							surrenderList.add(surrender);
						}else{ 
							//设置审核通过状态
							surrenderCost.setFfinanceauditstatus(Integer.valueOf(SurrendAuditStatusEnum.SHTG.getStatus()));
							//设置审核时间
							surrenderCost.setFfinanceauditdate(new Date());
							//设置审核人
							surrenderCost.setFinanceauditorid(employeeId);
							surrenderCost.setFinanceauditorname(employeeName);
							surrenderCostList.add(surrenderCost);
							//设置修改时间
							surrender.setFupdatetime(new Date());

							//设置财务审核通过日期
							surrender.setCwApproveDate(new Date());
							surrenderList.add(surrender);
						}
					}
				}
			}
			LogUtil.info(LOGGER, "【confirmSurrAudit】更新信息surrenderListNew：{}，surrenderBackRecordList：{}，surrenderCostList：{}", 
					JsonEntityTransform.Object2Json(surrenderListNew),JsonEntityTransform.Object2Json(surrenderBackRecordList),JsonEntityTransform.Object2Json(surrenderCostList));
			surrenderServiceImpl.updateSurrenderAudit(surrenderListNew, surrenderBackRecordList, surrenderCostList);
			dto.putValue("surrenderIds", surrenderIds);
			return dto.toJsonString();
        }catch(Exception e){
        	LogUtil.error(LOGGER, "【confirmSurrAudit】异常：{}", e);
        	dto.setErrCode(DataTransferObject.ERROR);
        	dto.setMsg("系统异常");
        	return dto.toJsonString();
        }
	}
    
    /**
     * <p>根据参数查询审核驳回信息</p>
     * @author xiangb
     * @created 2017年11月21日
     * @param
     * @return
     */
    public String findSurrendBackRecordEntityByParam(String param){
    	LogUtil.info(LOGGER, "【findSurrendBackRecordEntityByParam】入参：{}", param);
        DataTransferObject dto = new DataTransferObject();
        try{
        	List<SurrendBackRecordEntity> surrendBackRecordEntitys = null;
        	Map<String,String> map = (Map<String, String>) JsonEntityTransform.json2Map(param);
        	String surrenderId = map.get("surrenderId");
        	String type = map.get("type");
        	surrendBackRecordEntitys = surrendBackRecordServiceImpl.findSurrendBackRecordEntityByParam(surrenderId, type);
        	if(!Check.NuNCollection(surrendBackRecordEntitys)){
        		dto.putValue("surrendBackRecordEntitys", surrendBackRecordEntitys);
        	}
            return dto.toJsonString();
        }catch(Exception e){
        	LogUtil.error(LOGGER, "【findSurrendBackRecordEntityByParam】异常：{}", e);
        	dto.setErrCode(DataTransferObject.ERROR);
        	dto.setMsg("系统异常");
        	return dto.toJsonString();
        } 
    }
    /**
     * 根据父解约协议获取解约实体
     *
     * @Author: xiangbin
     * @Date: 2017年11月29日
     */
	@Override
	public String getSurListByParentId(String surParentId) {
		LogUtil.info(LOGGER, "【getSurListByParentId】入参：{}", surParentId);
        DataTransferObject dto = new DataTransferObject();
        try{
        	List<SurrenderEntity> list = surrenderServiceImpl.getSurListByParentId(surParentId);
        	LogUtil.info(LOGGER, "【getSurListByParentId】查询解约列表返回：{}", JsonEntityTransform.Object2Json(list));
        	if(!Check.NuNCollection(list)){
        		dto.putValue("list", list);
        	}
            return dto.toJsonString();
        }catch(Exception e){
        	LogUtil.error(LOGGER, "【getSurListByParentId】异常：{}", e);
        	dto.setErrCode(DataTransferObject.ERROR);
        	dto.setMsg("系统异常");
        	return dto.toJsonString();
        }
	}

	@Override
	public String saveSurrenderType(String surrenderEntityStr) {
		LogUtil.info(LOGGER, "【saveSurrenderType】入参：{}", surrenderEntityStr);
        DataTransferObject dto = new DataTransferObject();
        try{
        	SurrenderEntity surrenderEntity = JsonEntityTransform.json2Entity(surrenderEntityStr, SurrenderEntity.class);
        	String surParentId = surrenderEntity.getSurParentId();
        	String surrenderId = surrenderEntity.getSurrenderId();
        	String surType = surrenderEntity.getFsurtype();
            boolean isTrue  = false;
            if(Check.NuNStr(surParentId)){
            	LogUtil.info(LOGGER, "【saveSurrenderType】surParentId为空：{}", surrenderEntityStr);
            	isTrue = saveSurrenderUtil(surrenderEntity,surType);
            }else{
            	LogUtil.info(LOGGER, "【saveSurrenderType】surParentId不为空：{}", surrenderEntityStr);
            	List<SurrenderEntity> list =surrenderServiceImpl.getSurListByParentId(surParentId);
            	LogUtil.info(LOGGER, "【saveSurrenderType】根据父解约协议id查询后返回：{}", JsonEntityTransform.Object2Json(list));
            	if(!Check.NuNCollection(list)){
            		for(SurrenderEntity surrender:list){
            			isTrue = saveSurrenderUtil(surrender,surType);
            			if(isTrue){
            				continue;
            			}else{
            				break;
            			}
            		}
            		
            	}
            }
            if(isTrue){
            	LogUtil.info(LOGGER, "【saveSurrenderType】保存正常：{}",surrenderEntityStr);
            	return dto.toJsonString();
            }else{
            	LogUtil.error(LOGGER, "【saveSurrenderType】保存异常：{}",surrenderEntityStr);
            	dto.setErrCode(DataTransferObject.ERROR);
            	dto.setMsg("系统异常");
            	return dto.toJsonString();
            }
        }catch(Exception e){
        	LogUtil.error(LOGGER, "【saveSurrenderType】异常：{}", e);
        	dto.setErrCode(DataTransferObject.ERROR);
        	dto.setMsg("系统异常");
        	return dto.toJsonString();
        }
	}
	
	//解约协议
    public boolean saveSurrenderUtil(SurrenderEntity surrender,String surType){
    	try {
	    	String contractId = surrender.getContractId();
	    	String surrenderId = surrender.getSurrenderId();
	    	//设置解约协议申请状态为已点击解约
	        surrender.setFapplystatus(SurrenderApplyStatusEnum.SURRSTAGE.getCode());//申请阶段结束，进入解约阶段
	        surrender.setFsurtype(surType);
	        //保存客户姓名
	        RentContractEntity contract = rentContractServiceImpl.findContractBaseByContractId(contractId);
	        if (contract != null) {
	            //获取客户姓名
	            String customerName = contract.getCustomerName();
	            if (StringUtils.isNotBlank(customerName)) {
	                surrender.setFtenantname(customerName);
	            } else {
	                Integer customerType = contract.getCustomerType();
	                /*如果签约的客户是个人的时候，此时在合同中拿不到的时候查询客户库，否则的话查询企业的客户库即可*/
	                if (!Check.NuNObj(customerType) && customerType == CustomerTypeEnum.PERSON.getCode()) {
	                    /**
	                     * 根据customerUid进行查询--xiaona--2016-9-5 16:48:09
	                     */
	                    String customerUid = contract.getCustomerUid();
	//                PersonalCustomer customer = CustomerApiUtil.searchCustomer(mobile);
	                    //PersonalCustomer customer = CustomerApiUtil.searchCustomerByCustomerUid(customerUid);
	                    try {
	//                        PersonalCustomer customer = ZiroomCustomerApiUtil.getCusInfoByUid(customerUid);
	                    	PersonalInfoDto customer = CustomerLibraryUtil.findAuthInfoFromCustomer(customerUid);
	                        if (customer != null) {
	                            String userName = customer.getCert().getReal_name();
	                            surrender.setFtenantname(userName);
	                        } else {
	                        	LOGGER.error("[saveSurrenderUtil]读取用户信息失败:surrenderId:"+surrender.getSurrenderId());
	                            return false;
	                        }
	                    } catch (Exception e) {
	                        LOGGER.error("[saveSurrenderUtil]读取用户信息失败:surrenderId:"+surrender.getSurrenderId()+"异常为：", e);
	                        return false;
	                    }
	                } else {
	                    String customerUid = contract.getCustomerUid();
	                    if (StringUtils.isNotBlank(customerUid)) {
	//                        epscustomer = this.customerService.getCustomerById(customerid);
	                         RentEpsCustomerEntity epscustomer =  rentEpsCustomerServiceImpl.findRentEpsCustomerByCustomerUid(customerUid);
	                        if (epscustomer != null && !Check.NuNObj(epscustomer.getContacter())) {
	                            surrender.setFtenantname(epscustomer.getContacter());
	                        }
	                    }
	                }
	            }
	        }
	        //更新解约协议
            if (!Check.NuNStr(surrenderId)) {
            	surrenderServiceImpl.updateSurrender(surrender);
            }
            
            return true;
        } catch (Exception e) {
            LOGGER.error("解约类型保存失败！异常：", e);
            return false;
        }
    }
    /**
     * <p>根据合同ID查询解约协议</p>
     * @author xiangb
     * @created 2017年12月10日
     * @param
     * @return
     */
    @Override
    public String findSurrenderByContractId(String contractId){
    	LogUtil.info(LOGGER, "【findSurrenderByContractId】入参：{}", contractId);
        DataTransferObject dto = new DataTransferObject();
        try{
        	SurrenderEntity surrenderEntity = surrenderServiceImpl.getSurrenderByConId(contractId);
        	if(Check.NuNObj(surrenderEntity)){
        		LogUtil.error(LOGGER, "【findSurrenderByContractId】未查询到解约协议，contractId：{}", contractId);
            	dto.setErrCode(DataTransferObject.ERROR);
            	dto.setMsg("没有对应的解约协议");
            	return dto.toJsonString();	
        	}
        	dto.putValue("surrenderEntity", surrenderEntity);
        	return dto.toJsonString();
        }catch(Exception e){
        	LogUtil.error(LOGGER, "【findSurrenderByContractId】异常：{}", e);
        	dto.setErrCode(DataTransferObject.ERROR);
        	dto.setMsg("系统异常");
        	return dto.toJsonString();	
        }
	}
    /**
     * <p>根据合同ID查询是否有续约合同（包括企业）</p>
     * @author xiangb
     * @created 2017年12月10日
     * @param
     * @return
     */
	@Override
	public String findReNewContractByContractId(String contractId) {
		LogUtil.info(LOGGER, "【findReNewContractByContractId】入参：{}", contractId);
        DataTransferObject dto = new DataTransferObject();
        try{
        	RentContractEntity rentContractEntity =	rentContractServiceImpl.findContractBaseByContractId(contractId);
        	LogUtil.info(LOGGER, "【findReNewContractByContractId】：查询合同信息返回：{}", JsonEntityTransform.Object2Json(rentContractEntity));
        	if(Check.NuNObj(rentContractEntity)){
        		dto.setErrCode(DataTransferObject.ERROR);
            	dto.setMsg("未查询到合同信息");
        		return dto.toJsonString();
        	}

        		RentContractEntity renewRentContractEntity = rentContractServiceImpl.findRenewContractByPreRentCode(rentContractEntity.getConRentCode());
        		LogUtil.info(LOGGER, "【findReNewContractByContractId】查询续约合同信息返回：{}", JsonEntityTransform.Object2Json(renewRentContractEntity));
        		if(!Check.NuNObj(renewRentContractEntity)){
        			dto.putValue("renewRentContractEntity", renewRentContractEntity);
        		}
        		return dto.toJsonString();
            
//        	List<RentContractEntity> contracts = rentContractServiceImpl.listContractBySurParentRentId(rentContractEntity.getSurParentRentId());
//        	LogUtil.info(LOGGER, "【findReNewContractByContractId】企业合同查询子合同信息返回：{}", JsonEntityTransform.Object2Json(contracts));
//        	if(Check.NuNCollection(contracts)){
//        		dto.setErrCode(DataTransferObject.ERROR);
//            	dto.setMsg("未查询到子合同信息");
//        		return dto.toJsonString();
//        	}
//        	for(RentContractEntity contract:contracts){
//        		RentContractEntity renewRentContractEntity = rentContractServiceImpl.findRenewContractByPreRentCode(contract.getConRentCode());
//        		LogUtil.info(LOGGER, "【findReNewContractByContractId】子合同查询续约合同信息返回：{}", JsonEntityTransform.Object2Json(renewRentContractEntity));
//        		if(!Check.NuNObj(renewRentContractEntity)){
//        			dto.putValue("renewRentContractEntity", renewRentContractEntity);
//        			dto.putValue("count", 1);
//        		}
//        	}
//        	return dto.toJsonString();
        }catch(Exception e){
        	LogUtil.error(LOGGER, "【findReNewContractByContractId】异常：{}", e);
        	dto.setErrCode(DataTransferObject.ERROR);
        	dto.setMsg("系统异常");
        	return dto.toJsonString();
        }
	}

	@Override
	public String doLineSurrender(String contractId) {
        LogUtil.info(LOGGER, "[线下解约]入参：{}", contractId);
        DataTransferObject dto = new DataTransferObject();
        try {
			//1、将合同状态更改为[已退租]
            RentContractEntity rentContractEntityParam = new RentContractEntity();
            rentContractEntityParam.setContractId(contractId);
            rentContractEntityParam.setConStatusCode(ContractStatusEnum.YTZ.getStatus());
            rentContractEntityParam.setConReleaseDate(new Date());
            int i = rentContractServiceImpl.updateContractToTargetStatus(rentContractEntityParam);
			if(i<=0) {
			    dto.setErrCode(DataTransferObject.ERROR);
			    dto.setMsg("线下解约更改合同状态出错！");
			    return dto.toJsonString();
			}
			RentContractEntity rentContractEntity = rentContractServiceImpl.findContractBaseByContractId(contractId);
			List<RentDetailEntity> rentDetailEntitys = rentContractServiceImpl.findRentDetailByContractId(contractId);
			LogUtil.info(LOGGER, "[线下解约]更新trentcontract！contractId={}，更新结果：{}条。", contractId, i);
			//2、释放房间
			List<String> releaseRoomList = new ArrayList<>();
			List<SyncContractVo> syncContractVos = new ArrayList<>();
			for(RentDetailEntity detail:rentDetailEntitys) {
				SyncContractVo syncContractVo = new SyncContractVo();
				syncContractVo.setCrmContractId(detail.getId());
				syncContractVo.setRentContractCode(rentContractEntity.getConRentCode()+"+"+detail.getRoomCode());
				syncContractVo.setStatusCode(ContractStatusEnum.YTZ.getStatus());
				syncContractVos.add(syncContractVo);
				releaseRoomList.add(detail.getRoomId());
			}
			String responseStr = roomService.updateRoomInfoForRelease(JsonEntityTransform.Object2Json(releaseRoomList));
			DataTransferObject dataTransferObject = JsonEntityTransform.json2DataTransferObject(responseStr);
			if (dataTransferObject.getCode() == DataTransferObject.ERROR) {
			    dto.setErrCode(DataTransferObject.ERROR);
			    dto.setMsg("释放房间出错！");
			    //回滚合同状态
                rentContractEntityParam.setConReleaseDate(null);
                rentContractEntityParam.setConStatusCode(ContractStatusEnum.YQY.getStatus());
			    rentContractServiceImpl.updateContractToTargetStatus(rentContractEntityParam);
			    return dto.toJsonString();
			}
			//3、同步财务合同状态
			List<SyncContractVo> syncContractVoSucs = new ArrayList<>();
			for(SyncContractVo syncContractVo:syncContractVos) {
			    String synResponseStr = callFinanceServiceProxy.updateContract(JsonEntityTransform.Object2Json(syncContractVo));
			    DataTransferObject syncContractDto = JsonEntityTransform.json2DataTransferObject(synResponseStr);
			    if (syncContractDto.getCode() == DataTransferObject.ERROR) {
			        dto.setErrCode(DataTransferObject.ERROR);
			        dto.setMsg(syncContractDto.getMsg());
			        //回滚合同状态
                    rentContractEntityParam.setConReleaseDate(null);
                    rentContractEntityParam.setConStatusCode(ContractStatusEnum.YQY.getStatus());
			        int rollBackRows = rentContractServiceImpl.updateContractToTargetStatus(rentContractEntityParam);
			        if(rollBackRows<=0) {
			        	LogUtil.info(LOGGER, "[线下解约]回滚合同contractId={},失败！", contractId);
			        }
			        //回滚房间状态
			        String rollbackStr = roomService.updateRoomStateRent(JsonEntityTransform.Object2Json(releaseRoomList));
			        DataTransferObject rollbackObject = JsonEntityTransform.json2DataTransferObject(responseStr);
			        if (dataTransferObject.getCode() == DataTransferObject.ERROR) {
			        	LogUtil.info(LOGGER, "[线下解约]合同contractId={},回滚房间状态失败",contractId);
			        }
			        //回滚已经同步财务的合同
			        for(SyncContractVo syncContractVoSuc:syncContractVoSucs) {
			        	syncContractVoSuc.setStatusCode("ysh");
			        	 String rollConStr = callFinanceServiceProxy.updateContract(JsonEntityTransform.Object2Json(syncContractVoSuc));
			             DataTransferObject rollConObject = JsonEntityTransform.json2DataTransferObject(rollConStr);
			             if(rollConObject.getCode() == DataTransferObject.ERROR) {
			            	 LogUtil.info(LOGGER, "[线下解约]合同rentContractCode={},回滚财务合同状态为已签约失败!",syncContractVoSuc.getRentContractCode());
			             }
			        }
			        return dto.toJsonString();
			    }else {
			    	syncContractVoSucs.add(syncContractVo);
			    }
				
			}
			
		} catch (Exception e) {
            LogUtil.error(LOGGER, "[线下解约]出错！错误信息={}", e);
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("系统异常！");
            return dto.toJsonString();
		}
		return dto.toJsonString();
	}
    
}
