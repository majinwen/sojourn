package com.ziroom.zrp.service.trading.proxy;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.*;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.valenum.common.YesOrNoEnum;
import com.ziroom.zrp.houses.entity.RoomInfoEntity;
import com.ziroom.zrp.houses.entity.RoomInfoExtEntity;
import com.ziroom.zrp.service.houses.api.RoomService;
import com.ziroom.zrp.service.trading.api.WaterClearingService;
import com.ziroom.zrp.service.trading.dto.smarthydropower.SettleWaterClearingRecordDto;
import com.ziroom.zrp.service.trading.proxy.commonlogic.RentContractLogic;
import com.ziroom.zrp.service.trading.proxy.commonlogic.WaterClearingLogic;
import com.ziroom.zrp.service.trading.service.IntellectWaterMeterClearServiceImpl;
import com.ziroom.zrp.service.trading.service.IntellectWaterMeterReadServiceImpl;
import com.ziroom.zrp.service.trading.service.RentContractServiceImpl;
import com.ziroom.zrp.service.trading.valenum.waterwatt.*;
import com.ziroom.zrp.trading.entity.*;
import com.zra.common.exception.FinServiceException;
import com.zra.common.exception.WaterClearingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Set;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>水费清算服务</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author zhangyl2
 * @version 1.0
 * @Date Created in 2018年01月31日
 * @since 1.0
 */
@Component("trading.waterClearingServiceProxy")
public class WaterClearingServiceProxy implements WaterClearingService {

    private static final Logger LOGGER = LoggerFactory.getLogger(WaterClearingServiceProxy.class);

    @Resource(name = "trading.intellectWatermeterReadServiceImpl")
    private IntellectWaterMeterReadServiceImpl intellectWaterMeterReadService;

    @Resource(name = "trading.rentContractServiceImpl")
    private RentContractServiceImpl rentContractService;

    @Resource(name = "trading.rentContractLogic")
    private RentContractLogic rentContractLogic;

    @Resource(name = "trading.waterClearingLogic")
    private WaterClearingLogic waterClearingLogic;

    @Resource(name = "houses.roomService")
    private RoomService roomService;

    @Resource(name = "trading.intellectWatermeterClearServiceImpl")
    private IntellectWaterMeterClearServiceImpl intellectWaterMeterClearService;

    /**
     *
     * 后台手动录入清算一个房间下的所有合同水费并生成账单
     *
     * @author zhangyl2
     * @created 2018年02月26日 18:42
     * @param
     * @return
     */
    @Override
    public String manualClearingAllContractInRoom(String paramJson){
        LogUtil.info(LOGGER, "【manualClearingAllContractInRoom】入参:paramJson:{}", paramJson);
        DataTransferObject dto = new DataTransferObject();
        IntellectWatermeterReadEntity readEntity = JsonEntityTransform.json2Object(paramJson, IntellectWatermeterReadEntity.class);
        if(Check.NuNObj(readEntity) ||
                Check.NuNObjs(readEntity.getFid(),
                        readEntity.getProjectId(),
                        readEntity.getRoomId(),
                        readEntity.getMeterReading(),
                        readEntity.getReadTime(),
                        readEntity.getHandleId(),
                        readEntity.getHandleName(),
                        readEntity.getHandleTime())){
            return errDto(dto, "【manualClearingAllContractInRoom】参数错误 ");
        }

        try {
            waterClearingLogic.clearingByRoom(readEntity.getProjectId(),
                    readEntity.getRoomId(),
                    readEntity.getMeterReading(),
                    WaterwattClearingTypeEnum.RENGONG,
                    null);

            // 查房间下的有效合同
            List<RentContractEntity> rentContractEntityList = rentContractLogic.getRoomValidContractList(readEntity.getRoomId());

            for(RentContractEntity rentContractEntity : rentContractEntityList){
                // 结算费用
                SettleWaterClearingRecordDto settleWaterClearingRecordDto = new SettleWaterClearingRecordDto();
                settleWaterClearingRecordDto.setContractId(rentContractEntity.getContractId());
                settleWaterClearingRecordDto.setSettlementType(WaterwattSettlementTypeEnum.MANUAL.getCode());
                settleWaterClearingRecordDto.setCreateType(1);
                waterClearingLogic.settleAndGenerationReceivable(settleWaterClearingRecordDto);
            }

            // 更新抄表记录的状态为成功
            IntellectWatermeterReadEntity intellectWatermeterReadEntity = new IntellectWatermeterReadEntity();
            intellectWatermeterReadEntity.setFid(readEntity.getFid());
            intellectWatermeterReadEntity.setReadStatus(WaterwattReadStatusEnum.SUCCESS.getCode());
            intellectWatermeterReadEntity.setHandleId(readEntity.getHandleId());
            intellectWatermeterReadEntity.setHandleName(readEntity.getHandleName());
            intellectWatermeterReadEntity.setHandleTime(readEntity.getHandleTime());
            intellectWaterMeterReadService.updateIntellectWatermeterRead(intellectWatermeterReadEntity);

        } catch (Exception e) {
            LogUtil.error(LOGGER, "【manualClearingAllContractInRoom】异常：e={}", e);
            dto.setErrCode(DataTransferObject.ERROR);
            return errDto(dto, "【manualClearingAllContractInRoom】异常 " + e);
        }

        return dto.toJsonString();
    }

    @Override
    public String readWaterMeterAndsettleMonthly(String paramJson) {
        String logPre = "【readWaterMeterAndsettleMonthly】房间抄表结算-";
        LogUtil.info(LOGGER, logPre+"入参:paramJson:{}", paramJson);
        DataTransferObject dto = new DataTransferObject();
        try {
            IntellectWatermeterReadEntity readEntity = JsonEntityTransform.json2Entity(paramJson,IntellectWatermeterReadEntity.class);
            // 1.抄表，并对房间下的合同进行清算，生成清算单
            waterClearingLogic.clearingByRoom(readEntity.getProjectId(),readEntity.getRoomId(), null, WaterwattClearingTypeEnum.DINGSHI,null);

            //2.遍历房间下的所有合同,调用结算方法对每一个合同进行结算、生成应收账单
            List<RentContractEntity> rentContractEntities = rentContractLogic.getRoomValidContractList(readEntity.getRoomId());
            for(RentContractEntity r:rentContractEntities){
                SettleWaterClearingRecordDto settleDto = new SettleWaterClearingRecordDto();
                settleDto.setContractId(r.getContractId());
                settleDto.setCreateType(0);
                settleDto.setCreateId("-1");
                settleDto.setSettlementType(WaterwattSettlementTypeEnum.JOB.getCode());
                waterClearingLogic.settleAndGenerationReceivable(settleDto);
            }
        }catch (WaterClearingException e){
            LogUtil.error(LOGGER, logPre+"error:{},paramJson:{}", e, paramJson);
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg(e.getMessage());
        }catch (FinServiceException e){
            LogUtil.error(LOGGER, logPre+"error:{},paramJson:{}", e, paramJson);
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg(e.getMessage());
        }catch (Exception e){
            LogUtil.error(LOGGER, logPre+"error:{},paramJson:{}", e, paramJson);
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("系统异常");
        }
        return dto.toJsonString();
    }

    @Override
    public String getAllRoomOfValidContractAndExistIntellectWaterMeter() {
        LogUtil.info(LOGGER, "【getAllRoomOfValidContractAndExistIntellectWaterMeter】方法开始");
        DataTransferObject dto = new DataTransferObject();
        try {
            List<RoomInfoEntity> roomInfoEntities = rentContractLogic.getAllRoomOfValidContractAndExistIntellectWaterMeter();
            dto.putValue("roomInfoEntities",roomInfoEntities);
        } catch (Exception e) {
            LogUtil.error(LOGGER, "【getAllRoomOfValidContractAndExistIntellectWaterMeter】 error:{}", e);
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("系统错误");
        }
        return dto.toJsonString();
    }
    /**
     * @description: 调用水表抄表，获取最新抄表的值
     * @author: xiangb
     * @date: 2018年3月9日10:44:12
     * @params: param
     * @return: String
     */
    @Override
    public String surrenderMeterReading(String param) {
        LogUtil.info(LOGGER, "【surrenderMeterReading】入参：{}",param);
        DataTransferObject dto = new DataTransferObject();
        Double readValue = null;//最新抄表示数
        Double preValue = null;//未清算的起始示数
        BigDecimal payMoney = null;//未清算待支付金额
        try{
            IntellectWatermeterReadEntity readEntity = JsonEntityTransform.json2Object(param, IntellectWatermeterReadEntity.class);
            String roomId = readEntity.getRoomId();
            String projectId = readEntity.getProjectId();
            String contractId = readEntity.getContractId();
            Integer readType = readEntity.getReadType();
            RoomInfoExtEntity roomInfoExtEntity = SOAResParseUtil.getValueFromDataByKey(roomService.getRoomInfoExtByRoomId(roomId),"roomExt",RoomInfoExtEntity.class);
            if(!Check.NuNObj(roomInfoExtEntity) && roomInfoExtEntity.getIsBindWatermeter()==YesOrNoEnum.YES.getCode()){
                readValue = waterClearingLogic.readWaterMeter(projectId,roomId,WaterwattReadTypeEnum.valueOf(readType),contractId);
            }else{
                return errDto(dto,"未绑定智能设备");
            }
            // 查询清算表，获取所有未清算的记录，获取待支付总金额和电表起始示数
            List<IntellectWatermeterClearEntity> lastClearEntitys = intellectWaterMeterClearService.findIntellectWatermeterClearByContractId(contractId);
            if(!Check.NuNCollection(lastClearEntitys)){
                lastClearEntitys = lastClearEntitys.stream().sorted((p1,p2) -> p1.getCreateTime().compareTo(p2.getCreateTime())).collect(Collectors.toList());
                preValue = lastClearEntitys.get(0).getStartReading();
            }else{//如果清算记录都已经结算过了，取最新的一次的清算记录的清算示数
                IntellectWatermeterClearEntity intellectWatermeterClearEntity = intellectWaterMeterClearService.getLastClearingReading(contractId);
                if(!Check.NuNObj(intellectWatermeterClearEntity)){
                    preValue = intellectWatermeterClearEntity.getClearingReading();
                }
            }
            payMoney = new BigDecimal(readValue).subtract(new BigDecimal(preValue)).multiply(new BigDecimal(waterClearingLogic.getUnitPrice(projectId))).setScale(2, BigDecimal.ROUND_HALF_UP);
        }catch (Exception e){
            LogUtil.error(LOGGER, "【surrenderMeterReading】 error:{}", e);
            return errDto(dto,"系统错误");
        }
        dto.putValue("readValue",readValue);
        dto.putValue("preValue",preValue == null?0.0:preValue);
        dto.putValue("payMoney",payMoney);//水电费都取最新的价格进行计算
        return dto.toJsonString();
    }

    /**
     * @description: 解约最后一步，智能水表生成清算单并置为已结算
     * @author: xiangb
     * @date: 2018年3月15日20:39:37
     * @params: param
     * @return: String
     */
    @Override
    public String surrenderMeterCleaning(String contractId){
        LogUtil.info(LOGGER, "【surrenderMeterCleaning】入参：{}",contractId);
        DataTransferObject dto = new DataTransferObject();
        if(Check.NuNStr(contractId)){
            return errDto(dto,"参数为空");
        }
        try{
            //判断contractId还是surParentId；
            List<RentContractEntity> contractEntities = new ArrayList<>();
            RentContractEntity rentContractEntity = rentContractService.findContractBaseByContractId(contractId);
            if(Check.NuNObj(rentContractEntity)){
                contractEntities = rentContractService.listContractBySurParentRentId(contractId);
                if(Check.NuNCollection(contractEntities)){
                    LogUtil.error(LOGGER,"【surrenderMeterCleaning】合同id：{}未查询到有效的合同信息", contractId);
                    return errDto(dto,"未查询到有效的合同信息");
                }
            }
            //只有一个合同的情况，查询解约记录中的房间水表示数，调用生成清算记录，根据合同id修改清算记录为已结清
            if(!Check.NuNObj(rentContractEntity)){
                waterClearingLogic.clearSurrenderContract(rentContractEntity);
                intellectWaterMeterClearService.updateClearStatusToYJS(rentContractEntity.getContractId());
            }
            //父合同id的情况，判断是否有同一个房间下的合同，同一个房间下的合同只生成一次结算，然后遍历合同号修改清算记录为已结清。
            if(!Check.NuNCollection(contractEntities)){
                Set<String> roomSet = new HashSet<>();
                for(RentContractEntity contract:contractEntities){
                    if(!roomSet.contains(contract.getRoomId())){
                        roomSet.add(contract.getRoomId());
                        waterClearingLogic.clearSurrenderContract(contract);
                    }
                    intellectWaterMeterClearService.updateClearStatusToYJS(contract.getContractId());
                }
            }
        }catch (Exception e){
            LogUtil.error(LOGGER,"【surrenderMeterCleaning】异常：{}", e);
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("系统异常");
            return dto.toJsonString();
        }
        return dto.toJsonString();
    }

    private String errDto(DataTransferObject dto,String errMsg){
        dto.setErrCode(DataTransferObject.ERROR);
        dto.setMsg(errMsg);
        return dto.toJsonString();
    }
}
