package com.ziroom.zrp.service.trading.proxy.commonlogic;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.exception.SOAParseException;
import com.asura.framework.base.util.*;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.valenum.common.YesOrNoEnum;
import com.ziroom.zrp.houses.entity.ProjectEntity;
import com.ziroom.zrp.houses.entity.RoomInfoEntity;
import com.ziroom.zrp.houses.entity.RoomInfoExtEntity;
import com.ziroom.zrp.service.houses.api.ProjectService;
import com.ziroom.zrp.service.houses.api.RoomService;
import com.ziroom.zrp.service.houses.api.SmartPlatformService;
import com.ziroom.zrp.service.houses.dto.smartplatform.waterwatt.WaterHistoryWaterMeterDto;
import com.ziroom.zrp.service.houses.entity.smartplatform.waterwatt.HistoryWaterMeterVo;
import com.ziroom.zrp.service.trading.dto.ContractRoomDto;
import com.ziroom.zrp.service.trading.dto.finance.BillDto;
import com.ziroom.zrp.service.trading.dto.smarthydropower.SettleWaterClearingRecordDto;
import com.ziroom.zrp.service.trading.service.*;
import com.ziroom.zrp.service.trading.valenum.ReceiBillStateEnum;
import com.ziroom.zrp.service.trading.valenum.finance.CostCodeEnum;
import com.ziroom.zrp.service.trading.valenum.finance.DocumentTypeEnum;
import com.ziroom.zrp.service.trading.valenum.surrender.ExpenseItemEnum;
import com.ziroom.zrp.service.trading.valenum.waterwatt.*;
import com.ziroom.zrp.trading.entity.*;
import com.zra.common.exception.FinServiceException;
import com.zra.common.exception.WaterClearingException;
import com.zra.common.utils.DateUtilFormate;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * <p>水费各种清算流程需要的逻辑方法</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author zhangyl2
 * @Date Created in 2018年03月05日
 * @version 1.0
 * @since 1.0
 */
@Component("trading.waterClearingLogic")
public class WaterClearingLogic {

    private static final Logger LOGGER = LoggerFactory.getLogger(WaterClearingLogic.class);

    @Resource(name = "houses.smartPlatformService")
    private SmartPlatformService smartPlatformService;

    @Resource(name="houses.projectService")
    private ProjectService projectService;

    @Resource(name="trading.rentContractServiceImpl")
    private RentContractServiceImpl rentContractServiceImpl;

    @Resource(name = "trading.intellectWatermeterReadServiceImpl")
    private IntellectWaterMeterReadServiceImpl intellectWaterMeterReadService;

    @Resource(name = "trading.intellectWatermeterClearServiceImpl")
    private IntellectWaterMeterClearServiceImpl intellectWaterMeterClearService;

    @Resource(name="trading.financeBaseCall")
    private FinanceBaseCall financeBaseCall;

    @Resource(name="trading.financeCommonLogic")
    private FinanceCommonLogic financeCommonLogic;

    @Resource(name = "trading.rentContractLogic")
    private RentContractLogic rentContractLogic;

    @Resource(name="trading.finReceiBillDetailServiceImpl")
    private FinReceiBillDetailServiceImpl finReceiBillDetailServiceImpl;

    @Resource(name = "houses.roomService")
    private RoomService roomService;

    @Resource(name = "trading.rentItemDeliveryServiceImpl")
    private RentItemDeliveryServiceImpl rentItemDeliveryServiceImpl;

    @Resource(name="trading.housesCommonLogic")
    private HousesCommonLogic housesCommonLogic;

    @Resource(name="trading.surMeterDetailServiceImpl")
    private SurMeterDetailServiceImpl surMeterDetailService;

    /**
     * 抄表-私有方法
     *
     * 必要参数：
     * projectId
     * roomId
     * waterwattReadTypeEnum
     *
     * 如果抄表类型是 新签、解约、到期、续约 时, 合同号contractId必传
     *
     * @param
     * @return
     * @author zhangyl2
     * @created 2018年02月01日 11:32
     */
    public Double readWaterMeter(String projectId,
                                  String roomId,
                                  WaterwattReadTypeEnum waterwattReadTypeEnum,
                                  String contractId) throws Exception {
        String logPre = "[readWaterMeter]抄表-";

        LogUtil.info(LOGGER, logPre + "projectId={}, roomId={}, waterwattReadTypeEnum={}, contractId={}", projectId, roomId, waterwattReadTypeEnum, contractId);

        /**
         * 必要参数：
         * projectId
         * roomId
         * readType
         */
        if(Check.NuNObjs(projectId, roomId, waterwattReadTypeEnum)){
            LogUtil.error(LOGGER, logPre + "参数错误");
            throw new WaterClearingException(logPre + "参数错误");
        }

        /**
         * 如果抄表类型是 新签、解约、到期、续约 时
         * 合同号必传
         */
        if(WaterwattReadTypeEnum.XINQIAN.equals(waterwattReadTypeEnum) ||
                WaterwattReadTypeEnum.JIEYUE.equals(waterwattReadTypeEnum) ||
                WaterwattReadTypeEnum.DAOQI.equals(waterwattReadTypeEnum) ||
                WaterwattReadTypeEnum.XUYUE.equals(waterwattReadTypeEnum)){
            if(Check.NuNStr(contractId)){
                LogUtil.error(LOGGER, logPre + "参数错误");
                throw new WaterClearingException(logPre + "参数错误");
            }
        }

        // 查询二级房间的roomId,以及roomext信息
        String roomJson = roomService.getRoomByFid(roomId);
        DataTransferObject roomObj = JsonEntityTransform.json2DataTransferObject(roomJson);
        RoomInfoEntity roomInfo = roomObj.parseData("roomInfo", new TypeReference<RoomInfoEntity>() {
        });
        roomId = Check.NuNStr(roomInfo.getParentId()) ? roomId : roomInfo.getParentId();
        RoomInfoExtEntity roomInfoExtEntity = SOAResParseUtil.getValueFromDataByKey(
                roomService.getRoomInfoExtByRoomId(roomId),
                "roomExt",
                RoomInfoExtEntity.class);

        // 需要绑定智能水表
        if (Check.NuNObj(roomInfoExtEntity) || roomInfoExtEntity.getIsBindWatermeter() == YesOrNoEnum.NO.getCode()) {
            return null;
        }

        // 最近一次抄表
        IntellectWatermeterReadEntity lastRead = intellectWaterMeterReadService.getNewestWatermeterRead(projectId, roomId);
        // 最新抄表示数在今天的, 直接使用
        if(!Check.NuNObj(lastRead)){
            String now = DateUtilFormate.formatDateToString(new Date(), DateUtilFormate.DATEFORMAT_4);
            if(now.equals(DateUtilFormate.formatDateToString(lastRead.getReadTime(), DateUtilFormate.DATEFORMAT_4))){
                return lastRead.getMeterReading();
            }
        }

        // 智能平台抄表
        WaterHistoryWaterMeterDto p = new WaterHistoryWaterMeterDto();
        p.setHireContractCode(projectId);
        p.setPositionRank1(projectId);
        p.setPositionRank2(roomId);

        DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(smartPlatformService.readNewestWaterMeter(p.toJsonStr()));
        IntellectWatermeterReadEntity readEntity = new IntellectWatermeterReadEntity();
        if(DataTransferObject.SUCCESS == dto.getCode()){
            HistoryWaterMeterVo vo = dto.parseData("historyWaterMeterVo", new TypeReference<HistoryWaterMeterVo>() {
            });
            // 智能平台的最新抄表示数在12h内的
            if(!Check.NuNObj(vo) && vo.getCtime().after(DateUtilFormate.addHours(-12))){
                readEntity.setMeterReading(vo.getDevice_amount());
                readEntity.setReadStatus(WaterwattReadStatusEnum.SUCCESS.getCode());
                readEntity.setReadTime(vo.getCtime());
            }else{
                LogUtil.error(LOGGER, logPre + "智能平台抄表记录太老！p={}, dto={}", p, dto);
                readEntity.setReadStatus(WaterwattReadStatusEnum.FAIL.getCode());
            }
        }else{
            LogUtil.error(LOGGER, logPre + "智能平台抄表异常！p={},dto={}", p, dto);
            readEntity.setReadStatus(WaterwattReadStatusEnum.FAIL.getCode());
        }

        // 填充上次记录的数据
        if(!Check.NuNObj(lastRead)){
            readEntity.setPreMeterReading(lastRead.getMeterReading());
            readEntity.setPreReadTime(lastRead.getReadTime());
        }

        // 插入抄表记录表
        readEntity.setProjectId(projectId);
        readEntity.setRoomId(roomId);
        readEntity.setReadType(waterwattReadTypeEnum.getCode());
        readEntity.setContractId(contractId);
        intellectWaterMeterReadService.insertIntellectWatermeterRead(readEntity);

        if(Check.NuNObj(readEntity.getMeterReading())){
            throw new WaterClearingException(logPre + "智能平台抄表异常");
        }else{
            return readEntity.getMeterReading();
        }
    }

    /**
     *
     * 获取项目配置的水费单价
     *
     * @author zhangyl2
     * @created 2018年02月28日 17:13
     * @param
     * @return
     */
    public Double getUnitPrice(String projectId) throws WaterClearingException {
        String costStr = projectService.findCostStandardByProjectId(projectId);

        Double unitPrice = null;
        if(SOAResParseUtil.checkSOAReturnSuccess(costStr)){
            unitPrice = SOAResParseUtil.getDoubleFromDataByKey(costStr, "waterPrice");
        }

        if(Check.NuNObj(unitPrice) || unitPrice <= 0){
            throw new WaterClearingException("获取项目配置的水费单价异常");
        }

        return unitPrice;
    }

    /**
     *
     * 生成合同清算记录
     *
     * @author zhangyl2
     * @created 2018年02月28日 11:40
     * @param
     * @return
     */
    private void clearingByContractId(String contractId,
                                     Double clearingReading,
                                     Double unitPrice,
                                     Integer shareFactor,
                                     Integer clearingType) throws WaterClearingException {

        IntellectWatermeterClearEntity clearEntity = new IntellectWatermeterClearEntity();
        clearEntity.setContractId(contractId);
        clearEntity.setClearingReading(clearingReading);
        clearEntity.setUnitPrice(unitPrice);
        clearEntity.setShareFactor(shareFactor);
        clearEntity.setClearingType(clearingType);

        if(Check.NuNObjs(contractId,
                clearingReading,
                unitPrice,
                shareFactor,
                clearingType)){
            LogUtil.error(LOGGER, "【clearingByContractId】参数异常，clearEntity={},clearingType={}", clearEntity, clearingType);
            throw new WaterClearingException("【clearingByContractId】参数异常");
        }

        if(clearingReading < 0 || unitPrice <= 0){
            LogUtil.error(LOGGER, "【clearingByContractId】参数异常，clearingReading={},unitPrice={}", clearingReading, unitPrice);
            throw new WaterClearingException("【clearingByContractId】参数异常");
        }

        // 查询上次的清算，以其清算示数作为本次的起始示数
        IntellectWatermeterClearEntity lastClearEntity = intellectWaterMeterClearService.getLastClearingReading(contractId);
        if(Check.NuNObj(lastClearEntity)){
            clearEntity.setStartReading(clearingReading);
        }else{
            if(lastClearEntity.getClearingReading() > clearingReading){
                LogUtil.error(LOGGER, "【clearingByContractId】生成清算异常：上次清算示数大于本次清算示数");
                throw new WaterClearingException("【clearingByContractId】生成清算异常：上次清算示数大于本次清算示数");
            }
            clearEntity.setStartReading(lastClearEntity.getClearingReading());
        }

        // 本次清算金额 分
        int sumMoney = (int) (unitPrice * (clearingReading - clearEntity.getStartReading()) * 100);
        clearEntity.setSumMoney(sumMoney);
        clearEntity.setSettlementStatus(WaterwattSettlementStatusEnum.WJS.getCode());

        // 插入清算表
        int count = intellectWaterMeterClearService.insertIntellectWatermeterClear(clearEntity);
        if(count < 1){
            throw new WaterClearingException("【clearingByContractId】插入清算表异常");
        }
    }

    /**
     *
     * 清算这个房间下的所有有效合同
     *
     * projectId 必传
     * roomId 必传(可以是外房间、内房间，会判断取parentId)
     * clearingReading 可不传，方法内会判空自动抄表
     * waterwattClearingTypeEnum 必传，这个清算类型，对于自身合同与同租的合同进行了类型整合，直接传枚举即可，具体的code方法内会判断
     * contractId 如果清算类型是 新签、解约、到期、续约 时, 合同号contractId必传，即为新签、解约、到期、续约的那个合同号
     *
     * @author zhangyl2
     * @created 2018年03月01日 11:11
     * @param
     * @return
     */
    public void clearingByRoom(String projectId,
                               String roomId,
                               Double clearingReading,
                               WaterwattClearingTypeEnum waterwattClearingTypeEnum,
                               String contractId) throws Exception {

        if(Check.NuNObjs(projectId,
                roomId,
                waterwattClearingTypeEnum)){
            throw new WaterClearingException("参数错误");
        }

        // 查询二级房间的roomId,以及roomext信息
        String roomJson = roomService.getRoomByFid(roomId);
        DataTransferObject roomObj = JsonEntityTransform.json2DataTransferObject(roomJson);
        RoomInfoEntity roomInfo = roomObj.parseData("roomInfo", new TypeReference<RoomInfoEntity>() {
        });
        roomId = Check.NuNStr(roomInfo.getParentId()) ? roomId : roomInfo.getParentId();
        RoomInfoExtEntity roomInfoExtEntity = SOAResParseUtil.getValueFromDataByKey(
                roomService.getRoomInfoExtByRoomId(roomId),
                "roomExt",
                RoomInfoExtEntity.class);

        // 需要绑定智能水表
        if (Check.NuNObj(roomInfoExtEntity) || roomInfoExtEntity.getIsBindWatermeter() == YesOrNoEnum.NO.getCode()) {
            return;
        }

        // 抄表
        if(Check.NuNObj(clearingReading) || clearingReading < 0){
            // 根据清算类型，确定其抄表类型
            WaterwattReadTypeEnum waterwattReadTypeEnum;
            if(WaterwattClearingTypeEnum.DINGSHI.equals(waterwattClearingTypeEnum)){
                waterwattReadTypeEnum = WaterwattReadTypeEnum.DINGSHI;
            }else if(WaterwattClearingTypeEnum.XINQIAN.equals(waterwattClearingTypeEnum)){
                waterwattReadTypeEnum = WaterwattReadTypeEnum.XINQIAN;
            }else if(WaterwattClearingTypeEnum.JIEYUE.equals(waterwattClearingTypeEnum)){
                waterwattReadTypeEnum = WaterwattReadTypeEnum.JIEYUE;
            }else if(WaterwattClearingTypeEnum.DAOQI.equals(waterwattClearingTypeEnum)){
                waterwattReadTypeEnum = WaterwattReadTypeEnum.DAOQI;
            }else if(WaterwattClearingTypeEnum.XUYUE.equals(waterwattClearingTypeEnum)){
                waterwattReadTypeEnum = WaterwattReadTypeEnum.XUYUE;
            }else if(WaterwattClearingTypeEnum.RENGONG.equals(waterwattClearingTypeEnum)){
                waterwattReadTypeEnum = WaterwattReadTypeEnum.RENGONG;
            }else{
                throw new WaterClearingException("参数错误");
            }
            clearingReading = readWaterMeter(projectId, roomId, waterwattReadTypeEnum, contractId);
        }

        // 查询水费单价
        Double unitPrice = getUnitPrice(projectId);

        // 查房间下的有效合同
        List<RentContractEntity> rentContractEntityList = rentContractLogic.getRoomValidContractList(roomId);

        // 跟合同相关的清算需要区分他是合同本身，还是同租的合同
        // 如A合同新签，触发清算，则A合同的清算类型为 新签，其他的同租合同清算类型为 新签清算
        int clearingType;

        for(RentContractEntity rentContractEntity : rentContractEntityList) {

            // 确定分摊因子
            int shareFactor = rentContractEntityList.size();

            // 该合同
            if(!Check.NuNStr(contractId) && contractId.equals(rentContractEntity.getContractId())){
                // 该合同自身的清算类型
                clearingType = waterwattClearingTypeEnum.getOneSelfClearingCode();

            }else{
                // 同租的合同清算类型
                clearingType = waterwattClearingTypeEnum.getOtherClearingCode();

                if(WaterwattClearingTypeEnum.XINQIAN.equals(waterwattClearingTypeEnum)){
                    // 新签
                    // 同租的合同分摊因子要-1
                    shareFactor = rentContractEntityList.size() - 1;
                }
            }

            // 生成清算记录
            clearingByContractId(rentContractEntity.getContractId(),
                    clearingReading,
                    unitPrice,
                    shareFactor,
                    clearingType);
        }
    }

    /**
     * @description: 私有方法
     *             结算水表清算记录,并生成应收账单
     *             参数：contractId(合同id)、settlementType(结算方式)、createType(创建类型)、createId(创建者id)
     * @author: lusp
     * @date: 2018/2/26 上午 9:50
     * @params: settleWaterClearingRecordDto
     * @return: Boolean
     */
    public void settleAndGenerationReceivable(SettleWaterClearingRecordDto settleWaterClearingRecordDto) throws WaterClearingException, SOAParseException, FinServiceException {
        LogUtil.info(LOGGER, "【settleAndGenerationReceivable】入参:paramJson:{}", settleWaterClearingRecordDto.toJsonStr());
        if(Check.NuNObj(settleWaterClearingRecordDto)||Check.NuNStrStrict(settleWaterClearingRecordDto.getContractId())){
            LogUtil.error(LOGGER, "【settleAndGenerationReceivable】合同id为空,paramJson:{}", settleWaterClearingRecordDto.toJsonStr());
            throw new WaterClearingException("合同id为空");
        }
        //1.查询合同信息和项目信息
        RentContractEntity rentContractEntity = rentContractServiceImpl.findContractBaseByContractId(settleWaterClearingRecordDto.getContractId());
        if(Check.NuNObj(rentContractEntity)){
            LogUtil.error(LOGGER, "【settleAndGenerationReceivable】合同实体不存在,contractId:{}", settleWaterClearingRecordDto.getContractId());
            throw new WaterClearingException("合同实体不存在");
        }
        String projectJson = projectService.findProjectById(rentContractEntity.getProjectId());
        DataTransferObject projectDto = JsonEntityTransform.json2DataTransferObject(projectJson);
        ProjectEntity projectEntity = SOAResParseUtil.getValueFromDataByKey(projectJson,"projectEntity",ProjectEntity.class);
        if(projectDto.getCode()==DataTransferObject.ERROR||Check.NuNObj(projectEntity)){
            LogUtil.error(LOGGER,"【settleAndGenerationReceivable】调用projectService服务查询项目信息失败,resultJson:{}",projectJson);
            throw new WaterClearingException("调用projectService服务查询项目信息失败");
        }

        //2.查询合同的智能水表清算记录,并计算起始抄表数和总价格
        List<IntellectWatermeterClearEntity> intellectWatermeterClearEntities = intellectWaterMeterClearService.findIntellectWatermeterClearByContractId(settleWaterClearingRecordDto.getContractId());
        if(Check.NuNCollection(intellectWatermeterClearEntities)){
            LogUtil.error(LOGGER,"【settleAndGenerationReceivable】查询未结算的清算记录为空,intellectWatermeterClearEntities:{}",JsonEntityTransform.Object2Json(intellectWatermeterClearEntities));
            throw new WaterClearingException("未结算的清算记录为空");
        }
        Double startReading = intellectWatermeterClearEntities.stream().mapToDouble(IntellectWatermeterClearEntity::getStartReading).summaryStatistics().getMin();
        Double endReading = intellectWatermeterClearEntities.stream().mapToDouble(IntellectWatermeterClearEntity::getClearingReading).summaryStatistics().getMax();
        Long price = intellectWatermeterClearEntities.stream().mapToInt(IntellectWatermeterClearEntity::getSumMoney).summaryStatistics().getSum();

        String billFid = UUIDGenerator.hexUUID();//应收账单fid
        Date currentDate = new Date();
        String currentDateStr = DateUtil.dateFormat(currentDate,"yyyy-MM-dd");
        Double oughtTotalAmount = BigDecimal.valueOf(price).divide(BigDecimal.valueOf(100),2, RoundingMode.HALF_UP).doubleValue();
        String createId = Check.NuNStrStrict(settleWaterClearingRecordDto.getContractId())?"-1":settleWaterClearingRecordDto.getCreateId();
        String finbillNum = financeBaseCall.callFinaCreateBillNum(DocumentTypeEnum.LIFE_FEE.getCode());
        BigDecimal useReadingBigDecimal = new BigDecimal(0.00);
        for(IntellectWatermeterClearEntity i:intellectWatermeterClearEntities){
            useReadingBigDecimal = useReadingBigDecimal.add(BigDecimal.valueOf(i.getClearingReading()).subtract(BigDecimal.valueOf(i.getStartReading())).divide(BigDecimal.valueOf(i.getShareFactor().doubleValue())));
        }
        Double useReading = useReadingBigDecimal.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();

        //3.组装应收账单主表记录
        FinReceiBillEntity finReceiBillEntity = new FinReceiBillEntity();
        finReceiBillEntity.setFid(billFid);
        finReceiBillEntity.setContractId(rentContractEntity.getContractId());
        finReceiBillEntity.setBillNumber(financeCommonLogic.genReceiBillCode(projectEntity.getFcode()));
        finReceiBillEntity.setBillState(ReceiBillStateEnum.WSK.getCode());
        finReceiBillEntity.setBillType(1);
        finReceiBillEntity.setGenWay(Integer.valueOf(WaterwattSettlementTypeEnum.JOB.getCode()).equals(settleWaterClearingRecordDto.getSettlementType())?0:1);
        finReceiBillEntity.setPlanGatherDate(currentDate);
        finReceiBillEntity.setStartCycle(currentDate);
        finReceiBillEntity.setEndCycle(currentDate);
        finReceiBillEntity.setOughtTotalAmount(oughtTotalAmount);
        finReceiBillEntity.setCreateId(createId);
        finReceiBillEntity.setCityId(rentContractEntity.getCityid());
        finReceiBillEntity.setIsCalcWyj(1);//不计算违约金

        //4.组装同步财务生成应收账单入参
        BillDto billDto = new BillDto();
        billDto.setBillNum(finbillNum);
        billDto.setDocumentType(DocumentTypeEnum.LIFE_FEE.getCode());
        billDto.setUid(rentContractEntity.getCustomerUid());
        billDto.setUsername(rentContractEntity.getCustomerName());
        billDto.setPreCollectionDate(currentDateStr);
        billDto.setStartTime(currentDateStr);
        billDto.setEndTime(currentDateStr);
        billDto.setHouseCode(rentContractEntity.getHouseRoomNo());
        billDto.setHouseId(rentContractEntity.getProjectId());
        billDto.setHouseKeeperCode(rentContractEntity.getFhandlezocode());
        billDto.setCostCode(CostCodeEnum.ZRYSF.getCode());
        billDto.setDocumentAmount(price.intValue());
        List<BillDto> billList = new ArrayList<>();
        billList.add(billDto);

        //5.组装应收账单明细表记录
        FinReceiBillDetailEntity finReceiBillDetailEntity = new FinReceiBillDetailEntity();
        finReceiBillDetailEntity.setFid(UUIDGenerator.hexUUID());
        finReceiBillDetailEntity.setBillFid(billFid);
        finReceiBillDetailEntity.setExpenseItemId(Integer.parseInt(ExpenseItemEnum.SF.getId()));
        finReceiBillDetailEntity.setOughtAmount(oughtTotalAmount);
        finReceiBillDetailEntity.setRoomId(rentContractEntity.getRoomId());
        finReceiBillDetailEntity.setCreateId(createId);
        finReceiBillDetailEntity.setCityId(rentContractEntity.getCityid());
        finReceiBillDetailEntity.setUpdateId(rentContractEntity.getFhandlezocode());
        finReceiBillDetailEntity.setBillType(DocumentTypeEnum.LIFE_FEE.getCode());
        finReceiBillDetailEntity.setBillNum(finbillNum);

        //6.组装智能水表应收账单记录
        IntellectWaterMeterBillLogEntity intellectWaterMeterBillLogEntity = new IntellectWaterMeterBillLogEntity();
        intellectWaterMeterBillLogEntity.setFid(UUIDGenerator.hexUUID());
        intellectWaterMeterBillLogEntity.setStartReading(startReading);
        intellectWaterMeterBillLogEntity.setEndReading(endReading);
        intellectWaterMeterBillLogEntity.setUseReading(useReading);
        intellectWaterMeterBillLogEntity.setStartDate(intellectWatermeterClearEntities.get(0).getCreateTime());
        intellectWaterMeterBillLogEntity.setEndDate(intellectWatermeterClearEntities.get(intellectWatermeterClearEntities.size()-1).getCreateTime());
        intellectWaterMeterBillLogEntity.setPrice(price.intValue());
        intellectWaterMeterBillLogEntity.setShareFactor(intellectWatermeterClearEntities.get(intellectWatermeterClearEntities.size()-1).getShareFactor());
        intellectWaterMeterBillLogEntity.setBillFid(billFid);
        intellectWaterMeterBillLogEntity.setType(settleWaterClearingRecordDto.getCreateType());
        intellectWaterMeterBillLogEntity.setCreateId(createId);

        //7.保存本地数据
        intellectWaterMeterClearService.saveReceivableInfo(finReceiBillEntity, finReceiBillDetailEntity, intellectWaterMeterBillLogEntity);

        //8.同步财务应收账单,并修改本地记录同步财务状态
        DataTransferObject callFinaDto = financeBaseCall.callFinaCreateReceiptBill(rentContractEntity.getConRentCode(),billList);
        LogUtil.info(LOGGER,"【settleAndGenerationReceivable】同步应收返回str={}",callFinaDto.toJsonString());
        FinReceiBillDetailEntity updatefinReceiBillDetailEntity = new FinReceiBillDetailEntity();
        if(callFinaDto.getCode()==DataTransferObject.ERROR){
            LogUtil.error(LOGGER,"【settleAndGenerationReceivable】同步应收到财务失败,finbillNum:{}",finbillNum);
            //更新应收账单明细表的同步状态和失败原因
            updatefinReceiBillDetailEntity.setBillNum(finbillNum);
            updatefinReceiBillDetailEntity.setStatus(2);
            updatefinReceiBillDetailEntity.setFailMsg(Check.NuNStrStrict(callFinaDto.getMsg())?"同步财务异常":callFinaDto.getMsg());
            finReceiBillDetailServiceImpl.updateFinReceiBillDetailByBillNum(updatefinReceiBillDetailEntity);
            throw new WaterClearingException("同步应收到财务失败");
        }
        updatefinReceiBillDetailEntity.setBillNum(finbillNum);
        updatefinReceiBillDetailEntity.setStatus(1);
        finReceiBillDetailServiceImpl.updateFinReceiBillDetailByBillNum(updatefinReceiBillDetailEntity);

        //9.修改本地智能水表清算记录的结算状态为已结算
        intellectWaterMeterClearService.updateSettlementStatusBatch(intellectWatermeterClearEntities, WaterwattSettlementStatusEnum.YJS.getCode());
        LogUtil.info(LOGGER,"【settleAndGenerationReceivable】方法执行结束,contractId:{}",settleWaterClearingRecordDto.getContractId());
    }

    /**
     *
     * 后台签约清算
     * 个人、企业
     *
     * @author zhangyl2
     * @created 2018年03月13日 20:29
     * @param
     * @return
     */
    public void clearNewContract(RentContractEntity rentContractEntity) throws Exception {
        // 获取物业交割时的水表示数
        ContractRoomDto contractRoomDto = new ContractRoomDto();
        contractRoomDto.setContractId(rentContractEntity.getContractId());
        contractRoomDto.setRoomId(rentContractEntity.getRoomId());
        Double clearingReading = rentItemDeliveryServiceImpl.findMeterDetailById(contractRoomDto).getFwatermeternumber();

        clearingByRoom(rentContractEntity.getProjectId(),
                rentContractEntity.getRoomId(),
                clearingReading,
                WaterwattClearingTypeEnum.XINQIAN,
                rentContractEntity.getContractId());
    }
    /**
     *
     * 解约最后一步清算
     *
     * @author xiangb
     * @created 2018年3月16日10:30:58
     * @param
     * @return
     */
    public void clearSurrenderContract(RentContractEntity rentContractEntity) throws Exception {
        Double clearingReading = null;
        // 获取解约物业交割时的水表示数
        SurMeterDetailEntity surMeterDetailEntity = surMeterDetailService.getSDPriceBySurrenderId(rentContractEntity.getFsurrenderid());
        if(Check.NuNObj(surMeterDetailEntity) || Check.NuNObj(surMeterDetailEntity.getFwaternumber())){
            LogUtil.info(LOGGER,  "[clearSurrenderContract]异常：未查询到水电交割信息，contractId:{}", rentContractEntity.getContractId());
            throw new WaterClearingException("未查询到水电交割信息");
        }
        clearingReading = surMeterDetailEntity.getFwaternumber();
        clearingByRoom(rentContractEntity.getProjectId(),
                rentContractEntity.getRoomId(),
                clearingReading,
                WaterwattClearingTypeEnum.JIEYUE,
                rentContractEntity.getContractId());
    }

    /**
     * 后台续约
     * @author cuiyuhui
     * @created
     * @param renewRentContractEntity 新续约的合同
     * @return
     */
    public void clearDelayRenewContract(RentContractEntity renewRentContractEntity) {
        try {

            boolean isSupportIntellectWater = housesCommonLogic.isSupportIntellectWater(renewRentContractEntity.getRoomId());
            if (isSupportIntellectWater) {
                // 不支持智能水表
                return ;
            }

            RentContractEntity rentContractEntity = this.rentContractServiceImpl.findRenewContractByPreRentCode(renewRentContractEntity.getConRentCode());
            Date preEndDate = rentContractEntity.getConEndDate();
            Date renewSignDate = rentContractEntity.getConSignDate();

            // 延后续约
            if (renewSignDate.after(preEndDate)) {
                clearRenewContract(rentContractEntity, renewRentContractEntity);
            }


        } catch (Exception e) {
            LogUtil.error(LOGGER, "", e);
        }

    }

    /**
     * 已到期续约合同清理
     * @author cuiyuhui
     * @created
     * @param
     * @return
     */
    public void clearYdqRenewContract(RentContractEntity rentContractEntity) {
        try {
            // TODO cuiyh9 预计解决日期:2018/03/15 企业合同房间号为-1时如何处理
            boolean isSupportIntellectWater = housesCommonLogic.isSupportIntellectWater(rentContractEntity.getRoomId());
            if (isSupportIntellectWater) {
                return ;
            }
            // 续约合同可能没有, clearRenewContract需要进行判断
            RentContractEntity renewRentContractEntity = this.rentContractServiceImpl.findRenewContractByPreRentCode(rentContractEntity.getConRentCode());
            clearRenewContract(rentContractEntity, renewRentContractEntity);
        } catch (Exception e) {
            LogUtil.error(LOGGER, e.getMessage(), e);
        }
    }

    /**
     * 清算续约合同,目前只需要处理水表
     * 入口:
     * 1. 合同到期
     * 2. 合同延后续约
     * 逻辑:
     * 1.抄表，生成原合同清算单。
     * 2.生成续约合同的初始清算单。
     * 3.原合同未结算的清算单标为已转移
     * @author cuiyuhui
     * @created
     * @param rentContractEntity 合同实体
     * @return
     */
    public void clearRenewContract(RentContractEntity rentContractEntity, RentContractEntity renewRentContractEntity) throws Exception {
        String contractId = rentContractEntity.getContractId();
        String projectId = rentContractEntity.getProjectId();
        //1. 抄表， 生成原合同清算单
        this.clearingByRoom(projectId,
                rentContractEntity.getRoomId(),
                null,
                WaterwattClearingTypeEnum.DAOQI,
                contractId);

        // 不存在续约合同不执行任何操作。
        if (renewRentContractEntity == null) {
            return;
        }

        // 2. 生成续约合同的初始清算单。
        // 查询出当前一个合同未清算的所有清算单
        List<IntellectWatermeterClearEntity>  intellectWatermeterClearEntities = intellectWaterMeterClearService.findIntellectWatermeterClearByContractId(contractId);
        if (intellectWatermeterClearEntities == null || intellectWatermeterClearEntities.size() == 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("异常情况:未查询表当前合同的水清算单.合同id:");
            sb.append(contractId);
            throw new WaterClearingException(sb.toString());
        }

        intellectWatermeterClearEntities.sort((IntellectWatermeterClearEntity c1, IntellectWatermeterClearEntity c2) -> {
            if (c1.getId() > c2.getId()) {
                return 1;
            }  else if (c1.getId().intValue() == c2.getId().intValue()) {
                return 0;
            } else {
                return -1;
            }
        });

        int sumMoney = 0;
        for (IntellectWatermeterClearEntity a : intellectWatermeterClearEntities) {
            sumMoney += a.getSumMoney();
        }

        IntellectWatermeterClearEntity first =  intellectWatermeterClearEntities.get(0);
        IntellectWatermeterClearEntity last =  intellectWatermeterClearEntities.get(intellectWatermeterClearEntities.size() - 1);



        // 保存清算单到续约合同
        int distributionFactor =  rentContractLogic.getDistributionFactor(renewRentContractEntity.getContractId());
        Double unitPrice = getUnitPrice(projectId);
        Double startReading = first.getStartReading();
        Double clearingReading = last.getClearingReading();


        IntellectWatermeterClearEntity clearEntity = new IntellectWatermeterClearEntity();
        clearEntity.setContractId(renewRentContractEntity.getContractId());
        clearEntity.setStartReading(startReading);
        clearEntity.setClearingReading(clearingReading);
        clearEntity.setUnitPrice(unitPrice);
        clearEntity.setShareFactor(distributionFactor);
        clearEntity.setClearingType(WaterwattClearingTypeEnum.XUYUE.getOneSelfClearingCode());
        clearEntity.setSumMoney(sumMoney);
        clearEntity.setSettlementStatus(WaterwattSettlementStatusEnum.WJS.getCode());

        int count = intellectWaterMeterClearService.insertIntellectWatermeterClear(clearEntity);

        // 3. 原合同未结算的清算单表为已转移
        if (count == 0 ) {
            StringBuilder sb = new StringBuilder();
            sb.append("异常情况:续约合同未能插入清算表:");
            sb.append(renewRentContractEntity.getContractId());
            throw new WaterClearingException(sb.toString());
        } else {
            intellectWaterMeterClearService.updateIntellectWatermeterClearStatus(contractId);
        }
    }
}
