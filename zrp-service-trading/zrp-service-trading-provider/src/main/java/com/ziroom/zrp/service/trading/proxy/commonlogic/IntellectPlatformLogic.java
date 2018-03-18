package com.ziroom.zrp.service.trading.proxy.commonlogic;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.SOAResParseUtil;
import com.ziroom.zrp.houses.entity.RoomInfoExtEntity;
import com.ziroom.zrp.service.houses.api.ProjectService;
import com.ziroom.zrp.service.houses.api.RoomService;
import com.ziroom.zrp.service.houses.api.SmartPlatformService;
import com.ziroom.zrp.service.houses.dto.smartplatform.waterwatt.WattElectricMeterStateDto;
import com.ziroom.zrp.service.houses.dto.smartplatform.waterwatt.WattMeterChargingDto;
import com.ziroom.zrp.service.houses.dto.smartplatform.waterwatt.WattSetRoomPayTypeDto;
import com.ziroom.zrp.service.houses.entity.smartplatform.waterwatt.ElectricMeterStateVo;
import com.ziroom.zrp.service.houses.entity.smartplatform.waterwatt.ServiceIdBaseVo;
import com.ziroom.zrp.service.houses.valenum.WattRechargeStatuEnum;
import com.ziroom.zrp.service.trading.dto.waterwatt.ModifyWattPayTypeDto;
import com.ziroom.zrp.trading.entity.IntellectWattMeterSnapshotEntity;
import com.ziroom.zrp.trading.entity.RentContractEntity;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>智能设备 接口调用</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author yd
 * @version 1.0
 * @Date Created in 2018年01月23日 16:07
 * @since 1.0
 */
@Slf4j
@Component("trading.intellectPlatformLogic")
public class IntellectPlatformLogic {

    @Resource(name="houses.smartPlatformService")
    private SmartPlatformService smartPlatformService;

    @Resource(name="houses.projectService")
    private ProjectService projectService;

    @Resource(name = "houses.roomService")
    private RoomService roomService;

    @Resource(name = "trading.rentContractLogic")
    private RentContractLogic rentContractLogic;


    /**
     * 充值电费——生成电表充值记录t_intellect_watt_meter_snapshot
     * @author yd
     * @created
     * @param
     * @return
     */
    public IntellectWattMeterSnapshotEntity  rechargeElectricityBill(IntellectWattMeterSnapshotEntity wattMeter,WattMeterChargingDto param){
        //TODO  测试
        param.setHireContractCode("17");
        param.setPositionRank1("17");
        param.setPositionRank2("1001819");
        param.setAmount(13);
        param.setTradeNum("dfdfdfdfd");

        if (Check.NuNObj(wattMeter)){
            wattMeter = new IntellectWattMeterSnapshotEntity();
        }
        Integer tryTimes = wattMeter.getTryTimes();
        try {
            log.info("【智能充电开始】入参p={}",JsonEntityTransform.Object2Json(param));
            DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(smartPlatformService.meterCharging(JsonEntityTransform.Object2Json(param)));
            log.info("【智能充电返回】结果dto={}",dto.toJsonString());
            tryTimes = tryTimes == null ? 0:tryTimes + 1;
            if (dto.getCode() == DataTransferObject.ERROR){
                wattMeter.setStatu(WattRechargeStatuEnum.WAITTING_RECHARGE.getCode());
                wattMeter.setRemark(dto.getMsg());
            }
            if (dto.getCode() == DataTransferObject.SUCCESS){
                ServiceIdBaseVo data = dto.parseData("data", new TypeReference<ServiceIdBaseVo>() {});
                wattMeter.setStatu(WattRechargeStatuEnum.RECHARGEING.getCode());
                wattMeter.setServiceId(data.getServiceId());
            }
        }catch (Exception e){
            log.error("【充值电费异常，请关注】p={},e={}",param.toJsonStr(),e);
            wattMeter.setStatu(WattRechargeStatuEnum.WAITTING_RECHARGE.getCode());
            tryTimes = tryTimes == null ? 0:tryTimes + 1;
            wattMeter.setRemark("充值电费异常");
        }
        wattMeter.setTryTimes(tryTimes);
        return  wattMeter;
    }

    /**
     * 获取水电费 标准价
     * @author yd
     * @created
     * @param
     * @return
     */
    private  String findCostStandardByProjectId(String projectId){
        return  projectService.findCostStandardByProjectId(projectId);
    }


    /**
     * 查询电费标准价格
     * @author yd
     * @created
     * @param
     * @return
     */
    public Double findWattCostStandard(String projectId){

        Double electricityPrice = 0.0;

        try {
            DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(findCostStandardByProjectId(projectId));

            if (dto.getCode() == DataTransferObject.ERROR){
                log.error("【查询智能电费错误】projectId={},dto={}",projectId,dto.toJsonString());
                return electricityPrice;
            }

            electricityPrice = dto.parseData("electricityPrice", new TypeReference<Double>() {
            });
        }catch (Exception e){
            log.error("【查询智能电费异常】projectId={},electricityPrice={},e={}",projectId,electricityPrice,e);
            throw new RuntimeException("查询智能电费异常");
        }

        return  electricityPrice;

    }

    /**
     * 获取电表示数
     * @param wattElectricMeterStateDto
     * @return
     */
    public ElectricMeterStateVo getElectricMeterState(WattElectricMeterStateDto wattElectricMeterStateDto){
        String resultDto = smartPlatformService.electricMeterState(JsonEntityTransform.Object2Json(wattElectricMeterStateDto));
        DataTransferObject dataTransferObject = JsonEntityTransform.json2DataTransferObject(resultDto);
        if (dataTransferObject.getCode()  == DataTransferObject.SUCCESS){
            ElectricMeterStateVo data = dataTransferObject.parseData("data", new TypeReference<ElectricMeterStateVo>() {});
            return data;
        }
        return null;
    }

    public DataTransferObject checkValidContractAndModifyWattPayType(String paramJson) {
        String logPre = "【checkValidContractAndModifyWattPayType】校验房间是否有有效合同并修改智能电表付费方式-";
        log.info(logPre+" paramJson={}",paramJson);
        DataTransferObject dto = new DataTransferObject();
        try {
            ModifyWattPayTypeDto modifyWattPayTypeDto = JsonEntityTransform.json2Object(paramJson,ModifyWattPayTypeDto.class);
            if(Check.NuNObj(modifyWattPayTypeDto)||Check.NuNStrStrict(modifyWattPayTypeDto.getRoomId())||Check.NuNObj(modifyWattPayTypeDto.getPayType())){
                log.error(logPre+"参数不能为空:paramJson:{}",paramJson);
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("参数为空");
                return dto;
            }
            //1.首先判断房间下是否有智能电表
            String roomExtJson = roomService.getRoomInfoExtByRoomId(modifyWattPayTypeDto.getRoomId());
            DataTransferObject roomExtDto = JsonEntityTransform.json2DataTransferObject(roomExtJson);
            RoomInfoExtEntity roomExt = SOAResParseUtil.getValueFromDataByKey(roomExtJson,"roomExt",RoomInfoExtEntity.class);
            if(DataTransferObject.ERROR==roomExtDto.getCode()||Check.NuNObj(roomExt)){
                log.error(logPre+"根据roomId查询房间扩展信息失败,roomId:{}",modifyWattPayTypeDto.getRoomId());
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("查询房间扩展信息失败");
                return dto;
            }
            if(roomExt.getIsBindAmmeter()==0){
                log.info(logPre+"该房间没有智能电表,roomId:{}",modifyWattPayTypeDto.getRoomId());
                return dto;
            }
            //2.判断当前房间是否有有效合同
            List<RentContractEntity> rentContractEntities = rentContractLogic.getRoomValidContractList(modifyWattPayTypeDto.getRoomId());
            if(!Check.NuNCollection(rentContractEntities)){
                log.info(logPre+"该房间有有效合同,不需要修改智能电表付费方式,roomId:{}",modifyWattPayTypeDto.getRoomId());
                return dto;
            }
            //3.修改智能电表的付费方式
            WattSetRoomPayTypeDto param = new WattSetRoomPayTypeDto();
            param.setHireContractCode(modifyWattPayTypeDto.getProjectId());
            param.setPositionRank1(modifyWattPayTypeDto.getProjectId());
            param.setPositionRank2(modifyWattPayTypeDto.getRoomId());
            param.setPayType(modifyWattPayTypeDto.getPayType());
            String setRoomPayTypeJson = smartPlatformService.setRoomPayType(param.toJsonStr());
            DataTransferObject setRoomPayTypeDto = JsonEntityTransform.json2DataTransferObject(setRoomPayTypeJson);
            if(Check.NuNStrStrict(setRoomPayTypeJson)||DataTransferObject.ERROR==setRoomPayTypeDto.getCode()){
                log.error(logPre+"调用修改智能电表付费方式方法失败,param:{}",param.toJsonStr());
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("调用修改智能电表付费方式方法失败");
                return dto;
            }
        }catch (Exception e){
            log.error(logPre+" error={}",e);
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("服务错误");
        }
        return dto;
    }

}
