package com.ziroom.zrp.service.houses.proxy;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.asura.framework.base.entity.BaseEntity;
import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.exception.SOAParseException;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.SOAResParseUtil;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.services.common.utils.CloseableHttpUtil;
import com.ziroom.zrp.houses.entity.BuildingInfoEntity;
import com.ziroom.zrp.houses.entity.CostStandardEntity;
import com.ziroom.zrp.houses.entity.ProjectEntity;
import com.ziroom.zrp.houses.entity.RoomInfoEntity;
import com.ziroom.zrp.service.houses.api.SmartPlatformService;
import com.ziroom.zrp.service.houses.dto.WattRechargeDto;
import com.ziroom.zrp.service.houses.dto.smartplatform.*;
import com.ziroom.zrp.service.houses.dto.smartplatform.waterwatt.*;
import com.ziroom.zrp.service.houses.entity.MemterRechargeVo;
import com.ziroom.zrp.service.houses.entity.smartplatform.lock.AddPasswordVo;
import com.ziroom.zrp.service.houses.entity.smartplatform.lock.GetLockInfoVo;
import com.ziroom.zrp.service.houses.entity.smartplatform.lock.TemporaryPasswordVo;
import com.ziroom.zrp.service.houses.entity.smartplatform.waterwatt.ElectricMeterStateVo;
import com.ziroom.zrp.service.houses.entity.smartplatform.waterwatt.*;
import com.ziroom.zrp.service.houses.service.BuildingInfoServiceImpl;
import com.ziroom.zrp.service.houses.service.ProjectServiceImpl;
import com.ziroom.zrp.service.houses.service.RoomInfoServiceImpl;
import com.ziroom.zrp.service.houses.valenum.*;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>只封装对智能平台的数据对接，没有其他业务参与</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author zhangyl2
 * @version 1.0
 * @Date Created in 2017年12月11日
 * @since 1.0
 */
@Slf4j
@Component("houses.smartPlatformServiceProxy")
public class SmartPlatformServiceProxy implements SmartPlatformService {

    // 智能平台
    // wiki：
    // http://wiki.ziroom.com/pages/viewpage.action?pageId=337674251

    private static final Logger LOGGER = LoggerFactory.getLogger(SmartPlatformServiceProxy.class);

    private static final String logPre = "[SmartPlatformServiceProxy]智能平台-";

    @Value("${smart.platform.host}")
    private String smartPlatformHost;

    @Resource(name = "houses.projectServiceImpl")
    private ProjectServiceImpl  projectServiceImpl;

    @Resource(name = "houses.buildingInfoServiceImpl")
    private BuildingInfoServiceImpl buildingInfoServiceImpl;

    @Resource(name = "houses.roomInfoServiceImpl")
    private RoomInfoServiceImpl  roomInfoServiceImpl;

    /**
     *
     * 初始化智能平台维度数据(非后台研发人员勿用)
     * wiki：http://wiki.ziroom.com/pages/viewpage.action?pageId=333348876
     *
     * @author zhangyl2
     * @created 2018年02月07日 15:30
     * @param
     * @return
     */
    @Override
    public String initSmartHireContract(String paramJson) {
        SmartPlatformApiEnum smartPlatformApiEnum = SmartPlatformApiEnum.SAVEHIRECONTRACT;
        SmartPlatformSaveHireContractDto p = JSONObject.parseObject(paramJson, SmartPlatformSaveHireContractDto.class);
        return getSmartPlatformApiResult(smartPlatformApiEnum.getApi(), smartPlatformApiEnum.getDesc(), p.toJsonStr(), null);
    }

    /**
     * 新增智能平台出房合同
     *
     * @param
     * @return
     * @author zhangyl2
     * @created 2017年12月12日 16:05
     */
    @Override
    public String addRentContract(String paramJson) {
        SmartPlatformApiEnum smartPlatformApiEnum = SmartPlatformApiEnum.ADDRENTCONTRACT;
        SmartPlatformAddRentContractDto p = JsonEntityTransform.json2Entity(paramJson, SmartPlatformAddRentContractDto.class);
        if(Check.NuNObjs(p.getRentContractCode(),
                p.getHireContractCode(),
                p.getRentContractStartDate(),
                p.getRentContractEndDate(),
                p.getUid(),
                p.getVillage(),
                p.getUserName(),
                p.getUserPhone(),
                p.getCityCode(),
                p.getCityName(),
                p.getPositionRank1(),
                p.getPositionRank2(),
                p.getRoomCode())){

            LOGGER.error("新增智能平台出房合同-参数缺失");
            DataTransferObject dto = new DataTransferObject();
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("参数缺失");
        }
        return getSmartPlatformApiResult(smartPlatformApiEnum.getApi(), smartPlatformApiEnum.getDesc(), p.toJsonStr(), null);
    }

    /**
     * 退租
     *
     * @param paramJson
     * @return
     * @author zhangyl2
     * @created 2017年12月12日 17:21
     */
    @Override
    public String backRent(String paramJson) {
        SmartPlatformApiEnum smartPlatformApiEnum = SmartPlatformApiEnum.BACKRENT;
        SmartPlatformBackRentDto p = JsonEntityTransform.json2Entity(paramJson, SmartPlatformBackRentDto.class);
        if(Check.NuNObjs(p.getRentContractCode(),
                p.getHireContractCode())){

            LOGGER.error("退租-参数缺失");
            DataTransferObject dto = new DataTransferObject();
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("参数缺失");
        }
        return getSmartPlatformApiResult(smartPlatformApiEnum.getApi(), smartPlatformApiEnum.getDesc(), p.toJsonStr(), null);
    }

    /**
     * 续约
     *
     * @param paramJson
     * @return
     * @author zhangyl2
     * @created 2017年12月12日 17:56
     */
    @Override
    public String continueAbout(String paramJson) {
        SmartPlatformApiEnum smartPlatformApiEnum = SmartPlatformApiEnum.CONTINUEABOUT;
        SmartPlatformContinueAboutDto p = JsonEntityTransform.json2Entity(paramJson, SmartPlatformContinueAboutDto.class);
        if(Check.NuNObjs(p.getRentContractCode(),
                p.getHireContractCode(),
                p.getLogRentContractCode(),
                p.getRentContractStartDate(),
                p.getRentContractEndDate())){

            LOGGER.error("续约-参数缺失");
            DataTransferObject dto = new DataTransferObject();
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("参数缺失");
        }
        return getSmartPlatformApiResult(smartPlatformApiEnum.getApi(), smartPlatformApiEnum.getDesc(), p.toJsonStr(), null);
    }

    /**
     * 更换入住人信息
     *
     * @param paramJson
     * @return
     * @author zhangyl2
     * @created 2017年12月12日 17:58
     */
    @Override
    public String changeOccupants(String paramJson) {
        SmartPlatformApiEnum smartPlatformApiEnum = SmartPlatformApiEnum.CHANGEOCCUPANTS;
        SmartPlatformChangeOccupantsDto p = JsonEntityTransform.json2Entity(paramJson, SmartPlatformChangeOccupantsDto.class);
        if(Check.NuNObjs(p.getRentContractCode(),
                p.getHireContractCode(),
                p.getUid(),
                p.getUserName(),
                p.getUserPhone())){

            LOGGER.error("更换入住人信息-参数缺失");
            DataTransferObject dto = new DataTransferObject();
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("参数缺失");
        }
        return getSmartPlatformApiResult(smartPlatformApiEnum.getApi(), smartPlatformApiEnum.getDesc(), paramJson, null);
    }

    /**
     * 获取智能锁设备状态
     *
     * @param paramJson
     * @return
     * @author zhangyl2
     * @created 2017年12月12日 17:59
     */
    @Override
    public String getLockInfo(String paramJson) {
        SmartPlatformApiEnum smartPlatformApiEnum = SmartPlatformApiEnum.GETLOCKINFO;
        SmartPlatformGetLockInfoDto p = JsonEntityTransform.json2Entity(paramJson, SmartPlatformGetLockInfoDto.class);
        if(Check.NuNObjs(p.getHireContractCode(),
                p.getOpsUser(),
                p.getOpsUserIdentifier(),
                p.getOpsUserType(),
                p.getPositionRank1(),
                p.getPositionRank2())){

            LOGGER.error("获取智能锁设备状态-参数缺失");
            DataTransferObject dto = new DataTransferObject();
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("参数缺失");
        }
        return getSmartPlatformApiResult(smartPlatformApiEnum.getApi(), smartPlatformApiEnum.getDesc(), paramJson, GetLockInfoVo.class);
    }

    /**
     * 下发有效期密码
     *
     * @param paramJson
     * @return
     * @author zhangyl2
     * @created 2017年12月12日 18:01
     */
    @Override
    public String addPassword(String paramJson) {
        SmartPlatformApiEnum smartPlatformApiEnum = SmartPlatformApiEnum.ADDPASSWORD;
        SmartPlatformAddPasswordDto p = JsonEntityTransform.json2Entity(paramJson, SmartPlatformAddPasswordDto.class);
        if(Check.NuNObjs(p.getHireContractCode(),
                p.getOpsUser(),
                p.getOpsUserIdentifier(),
                p.getOpsUserType(),
                p.getUserName(),
                p.getUserPhone(),
                p.getPositionRank1(),
                p.getPositionRank2(),
                p.getPasswordType(),
                p.getUserIdentify(),
                p.getIsSendSms(),
                p.getPermissionBegin(),
                p.getPermissionEnd(),
                p.getBackUrl())){

            LOGGER.error("下发有效期密码-参数缺失");
            DataTransferObject dto = new DataTransferObject();
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("参数缺失");
        }
        return getSmartPlatformApiResult(smartPlatformApiEnum.getApi(), smartPlatformApiEnum.getDesc(), paramJson, AddPasswordVo.class);
    }

    /**
     * 获取临时密码
     *
     * @param paramJson
     * @return
     * @author zhangyl2
     * @created 2017年12月12日 18:02
     */
    @Override
    public String temporaryPassword(String paramJson) {
        SmartPlatformApiEnum smartPlatformApiEnum = SmartPlatformApiEnum.TEMPORARYPASSWORD;
        SmartPlatformTemporaryPasswordDto p = JsonEntityTransform.json2Entity(paramJson, SmartPlatformTemporaryPasswordDto.class);
        if(Check.NuNObjs(p.getHireContractCode(),
                p.getOpsUser(),
                p.getOpsUserIdentifier(),
                p.getOpsUserType(),
                p.getPositionRank1(),
                p.getPositionRank2())){

            LOGGER.error("获取临时密码-参数缺失");
            DataTransferObject dto = new DataTransferObject();
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("参数缺失");
        }
        return getSmartPlatformApiResult(smartPlatformApiEnum.getApi(), smartPlatformApiEnum.getDesc(), paramJson, TemporaryPasswordVo.class);
    }

    // 下面是
    // 智能平台水电表接口
    // wiki：
    // http://wiki.ziroom.com/pages/viewpage.action?pageId=341835936

    /**
     * 获取电表详情接口
     *
     * @param paramJson
     * @return
     * @author zhangyl2
     * @created 2018年01月11日 18:22
     */
    @Override
    public String electricMeterState(String paramJson) {
        try {
            WattElectricMeterStateDto p = JsonEntityTransform.json2Entity(paramJson, WattElectricMeterStateDto.class);
            DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(checkWaterWattCommonDtoAndExecute(p, ElectricMeterStateVo.class));
            if(DataTransferObject.SUCCESS == dto.getCode()){
                ElectricMeterStateVo vo = dto.parseData("data", new org.codehaus.jackson.type.TypeReference<ElectricMeterStateVo>() {
                });
                if(!Check.NuNObj(vo)){
                    if((!Check.NuNObj(vo.getOverdraft()) && vo.getOverdraft() > 0) ||
                            ((!Check.NuNObj(vo.getPowerTotal()) && vo.getPowerTotal() > 0))){

                        vo.setPayType(SmartPlatformWaterWattPayTypeEnum.PREPAYMENT.getZyuCode());
                    }else{
                        vo.setPayType(SmartPlatformWaterWattPayTypeEnum.AFTERPAYMENT.getZyuCode());
                    }
                    dto.putValue("data", vo);
                }
            }
            return dto.toJsonString();
        } catch (BusinessException e) {
            LogUtil.error(LOGGER, "【electricMeterState】paramJson={},e={}", paramJson, e);
            return errorDto(e.getMessage());
        }
    }

    /**
     * 获取水表详情接口
     *
     * @param paramJson
     * @return
     * @author zhangyl2
     * @created 2018年01月15日 21:02
     */
    @Override
    public String waterMeterState(String paramJson) {
        try {
            WattSetRoomPayTypeDto p = JsonEntityTransform.json2Entity(paramJson, WattSetRoomPayTypeDto.class);
            DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(checkWaterWattCommonDtoAndExecute(p, WaterMeterStateVo.class));
            if(DataTransferObject.SUCCESS == dto.getCode()){
                WaterMeterStateVo vo = dto.parseData("data", new org.codehaus.jackson.type.TypeReference<WaterMeterStateVo>() {
                });
                if(!Check.NuNObj(vo)){
                    // 水表都是后付费
                    vo.setPayType(SmartPlatformWaterWattPayTypeEnum.AFTERPAYMENT.getZyuCode());
                    dto.putValue("data", vo);
                }
            }
            return dto.toJsonString();
        } catch (BusinessException e) {
            LogUtil.error(LOGGER, "【waterMeterState】paramJson={},e={}", paramJson, e);
            return errorDto(e.getMessage());
        }
    }

    /**
     * 抄表电表(异步)
     * 暂时不用
     * 暂时不用
     * 暂时不用
     *
     * @param paramJson
     * @return
     * @author zhangyl2
     * @created 2018年01月15日 21:02
     */
    @Override
    public String readElectricMeter(String paramJson) {
        try {
            WattReadElectricMeterDto p = JsonEntityTransform.json2Entity(paramJson, WattReadElectricMeterDto.class);
            return checkWaterWattCommonDtoAndExecute(p, ServiceIdBaseVo.class);
        } catch (BusinessException e) {
            LogUtil.error(LOGGER, "【readElectricMeter】paramJson={},e={}", paramJson, e);
            return errorDto(e.getMessage());
        }
    }

    /**
     *
     * 最新抄表电表记录
     *
     * @author zhangyl2
     * @created 2018年01月31日 15:54
     * @param
     * @return
     */
    @Override
    public String readNewestElectricMeter(String paramJson) {
        try {
            DataTransferObject dto = new DataTransferObject();

            WattHistoryElectricMeterDto p = JsonEntityTransform.json2Entity(paramJson, WattHistoryElectricMeterDto.class);
            String jsonStr = checkWaterWattCommonDtoAndExecute(p, HistoryElectricMeterVo.class);
            if(!SOAResParseUtil.checkSOAReturnSuccess(jsonStr)){
                return jsonStr;
            }
            List<HistoryElectricMeterVo> list = SOAResParseUtil.getListValueFromDataByKey(jsonStr, "list", HistoryElectricMeterVo.class);
            if(Check.NuNCollection(list)){
                LOGGER.error("【readNewestElectricMeter】数据为空！");
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("抄表数据为空");
            }else{
                dto.putValue("historyElectricMeterVo", list.get(0));
            }
            return dto.toJsonString();
        } catch (BusinessException | SOAParseException e) {
            LogUtil.error(LOGGER, "【readNewestElectricMeter】paramJson={},e={}", paramJson, e);
            return errorDto(e.getMessage());
        }
    }

    /**
     * 设置房间电表付费模式
     *
     * @param paramJson
     * @return
     * @author zhangyl2
     * @created 2018年01月15日 21:02
     */
    @Override
    public String setRoomPayType(String paramJson) {
        try {
            WattSetRoomPayTypeDto p = JsonEntityTransform.json2Entity(paramJson, WattSetRoomPayTypeDto.class);
            //必须要有付费模式
            if(!Check.NuNObj(p.getPayType()) &&
                    (SmartPlatformWaterWattPayTypeEnum.PREPAYMENT.getCode() == p.getPayType() ||
                            SmartPlatformWaterWattPayTypeEnum.AFTERPAYMENT.getCode() == p.getPayType())){
                return checkWaterWattCommonDtoAndExecute(p, null);
            }else{
                return errorDto("必要参数缺失");
            }
        } catch (BusinessException e) {
            LogUtil.error(LOGGER, "【setRoomPayType】paramJson={},e={}", paramJson, e);
            return errorDto(e.getMessage());
        }
    }

    /**
     * 设置房间电表电表透支额度
     *
     * @param paramJson
     * @return
     * @author zhangyl2
     * @created 2018年01月15日 21:02
     */
    @Override
    public String meterOverdraftQuota(String paramJson) {
        try {
            WattMeterOverdraftQuotaDto p = JsonEntityTransform.json2Entity(paramJson, WattMeterOverdraftQuotaDto.class);
            //必须要有透支额度
            if(!Check.NuNObj(p.getOverdraft())){
                return checkWaterWattCommonDtoAndExecute(p, ServiceIdBaseVo.class);
            }else{
                return errorDto("必要参数缺失");
            }
        } catch (BusinessException e) {
            LogUtil.error(LOGGER, "【meterOverdraftQuota】paramJson={},e={}", paramJson, e);
            return errorDto(e.getMessage());
        }
    }

    /**
     * 电表充电(异步)
     *
     * @param paramJson
     * @return
     * @author zhangyl2
     * @created 2018年01月15日 21:02
     */
    @Override
    public String meterCharging(String paramJson) {
        try {
            WattMeterChargingDto p = JsonEntityTransform.json2Entity(paramJson, WattMeterChargingDto.class);
            if(!Check.NuNObjs(p.getAmount(), p.getTradeNum())){
                return checkWaterWattCommonDtoAndExecute(p, ServiceIdBaseVo.class);
            }else{
                return errorDto("必要参数缺失");
            }
        } catch (BusinessException e) {
            LogUtil.error(LOGGER, "【meterCharging】paramJson={},e={}", paramJson, e);
            return errorDto(e.getMessage());
        }
    }

    /**
     * 电表清零(异步)
     *
     * @param paramJson
     * @return
     * @author zhangyl2
     * @created 2018年01月15日 21:02
     */
    @Override
    public String electricMeterReset(String paramJson) {
        try {
            WattElectricMeterResetDto p = JsonEntityTransform.json2Entity(paramJson, WattElectricMeterResetDto.class);
            return checkWaterWattCommonDtoAndExecute(p, ServiceIdBaseVo.class);
        } catch (BusinessException e) {
            LogUtil.error(LOGGER, "【electricMeterReset】paramJson={},e={}", paramJson, e);
            return errorDto(e.getMessage());
        }
    }

    /**
     * 水表抄表(异步)
     * 暂时不用
     * 暂时不用
     * 暂时不用
     *
     * @param paramJson
     * @return
     * @author zhangyl2
     * @created 2018年01月15日 21:02
     */
    @Override
    public String readWaterMeter(String paramJson) {
        try {
            WaterReadWaterMeterDto p = JsonEntityTransform.json2Entity(paramJson, WaterReadWaterMeterDto.class);
            return checkWaterWattCommonDtoAndExecute(p, ServiceIdBaseVo.class);
        } catch (BusinessException e) {
            LogUtil.error(LOGGER, "【readWaterMeter】paramJson={},e={}", paramJson, e);
            return errorDto(e.getMessage());
        }
    }

    /**
     * 最新抄表水表记录
     *
     * @param paramJson
     * @return
     * @author zhangyl2
     * @created 2018年01月15日 21:02
     */
    @Override
    public String readNewestWaterMeter(String paramJson) {
        try {
            DataTransferObject dto = new DataTransferObject();

            WaterHistoryWaterMeterDto p = JsonEntityTransform.json2Entity(paramJson, WaterHistoryWaterMeterDto.class);
            String jsonStr = checkWaterWattCommonDtoAndExecute(p, HistoryWaterMeterVo.class);
            if(!SOAResParseUtil.checkSOAReturnSuccess(jsonStr)){
                return jsonStr;
            }
            List<HistoryWaterMeterVo> list = SOAResParseUtil.getListValueFromDataByKey(jsonStr, "list", HistoryWaterMeterVo.class);
            if(Check.NuNCollection(list)){
                LOGGER.error("【readNewesWaterMeter】数据为空！");
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("抄表数据为空");
            }else{
                dto.putValue("historyWaterMeterVo", list.get(0));
            }
            return dto.toJsonString();
        } catch (BusinessException | SOAParseException e) {
            LogUtil.error(LOGGER, "【readNewestElectricMeter】paramJson={},e={}", paramJson, e);
            return errorDto(e.getMessage());
        }
    }




    /**
     *
     * 校验水电基础dto必要信息
     *
     * 随后将其当做参数执行调用智能平台
     *
     * @author zhangyl2
     * @created 2018年01月16日 11:26
     * @param
     * @return
     */
    private <T extends BaseEntity> String checkWaterWattCommonDtoAndExecute(WaterWattCommonDto p, Class<T> clazz){
        if(!Check.NuNObjs(p.getHireContractCode(),
                p.getPositionRank1(),
                p.getPositionRank2()) &&
                SmartPlatformWaterWattDeivceTypeEnum.checkLegalCode(p.getDeviceType()) &&
                SmartPlatformWaterWattOperationMarkedEnum.checkLegalCode(p.getOperationMarked()) &&
                HouseTypeEnum.checkLegalCode(p.getHouseType())){

            //校验通过，则执行调用
            String desc = SmartPlatformWaterWattOperationMarkedEnum.getDescBycode(p.getOperationMarked());
            return getSmartPlatformApiResult(SmartPlatformApiEnum.EQUIPMENT.getApi(), desc, p.toJsonStr(), clazz);
        }else{
            return errorDto("必要参数缺失");
        }
    }

    /**
     * 通用调用智能平台接口方法
     * uri-接口地址
     * paramJson-接口所需参数
     *
     * @param
     * @return
     * @author zhangyl2
     * @created 2017年12月12日 17:49
     */
    private <T extends BaseEntity> String getSmartPlatformApiResult(String uri, String desc, String paramJson, Class<T> clazz) {
        String apiStr = String.format("uri=%s, desc=%s, paramJson=%s", uri, desc, paramJson);
        LogUtil.info(LOGGER, logPre + "调用开始 {}", apiStr);

        String result = "";

        try {
            result = CloseableHttpUtil.sendFormPost(smartPlatformHost + uri, JSONObject.parseObject(paramJson, new TypeReference<Map<String, String>>(){}));

            if (Check.NuNStr(result)) {
                LogUtil.error(LOGGER, logPre + "返回空！{}", apiStr);
                return errorDto("智能平台服务异常");
            } else {
                LogUtil.info(LOGGER, logPre + "返回结果 {}, result={}", apiStr, result);

                JSONObject jsonObject = JSONObject.parseObject(result);

                Integer code = jsonObject.getInteger("code");
                if (!Check.NuNObj(code) && code == 200) {
                    code = DataTransferObject.SUCCESS;
                } else {
                    code = DataTransferObject.ERROR;
                    LogUtil.error(LOGGER, logPre + "返回异常！{}, result={}", apiStr, result);
                }

                Map<String, Object> data = new HashMap<>();
                Object dataJson = jsonObject.get("data");
                if (!Check.NuNObj(clazz) && !Check.NuNObj(dataJson)) {
                    if(dataJson instanceof JSONObject){
                        data.put("data", JsonEntityTransform.json2Entity(((JSONObject) dataJson).toJSONString(), clazz));
                    }else if(dataJson instanceof JSONArray){
                        data.put("list", JsonEntityTransform.json2List(((JSONArray) dataJson).toJSONString(), clazz));
                    }
                }

                String msg = jsonObject.getString("message");

                DataTransferObject dto = new DataTransferObject(code, msg, data);

                return dto.toJsonString();
            }
        } catch (Exception e) {
            LogUtil.error(LOGGER, logPre + "解析异常！{}, result={} e={}", apiStr, result, e);
            return errorDto("智能平台服务异常");
        }
    }

    /**
     * 返回异常dto
     *
     * @param
     * @return
     * @author zhangyl2
     * @created 2017年12月12日 17:15
     */
    private String errorDto(String msg) {
        DataTransferObject dto = new DataTransferObject();
        dto.setErrCode(DataTransferObject.ERROR);
        dto.setMsg(msg);
        return dto.toJsonString();
    }



    /**
     * 查询电表充值页面信息
     *
     * @param paramJson
     * @return
     * @author yd
     * @created
     */
    @Override
    public String queryWattRechargeInfo(String paramJson) {

        DataTransferObject dto = new DataTransferObject();

        WattRechargeDto  wattRechargeDto = JsonEntityTransform.json2Entity(paramJson,WattRechargeDto.class);

        if (wattRechargeDto == null){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("参数不存在");
            return  dto.toJsonString();
        }

        if (Check.NuNStr(wattRechargeDto.getProjectId())){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("项目号不存在");
            return  dto.toJsonString();
        }

        if (Check.NuNStr(wattRechargeDto.getRoomId())){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("房间号不存在");
            return  dto.toJsonString();
        }



        ProjectEntity projectEntity= this.projectServiceImpl.findProjectById(wattRechargeDto.getProjectId());

        if (projectEntity == null){
            log.info("【电表充值信息查询——项目不存在】projectId={}",wattRechargeDto.getProjectId());
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("项目不存在");
            return  dto.toJsonString();
        }

        RoomInfoEntity roomInfoEntity  = this.roomInfoServiceImpl.getRoomInfoByFid(wattRechargeDto.getRoomId());
        if (roomInfoEntity == null){
            log.info("【电表充值信息查询——房间不存在】projectId={},roomId={}",wattRechargeDto.getProjectId(),wattRechargeDto.getRoomId());
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("房间不存在");
            return  dto.toJsonString();
        }

        BuildingInfoEntity buildingInfoEntity = this.buildingInfoServiceImpl.findBuildingInfoByFid(roomInfoEntity.getBuildingid());

        if (buildingInfoEntity == null){
            log.info("【电表充值信息查询——楼栋不存在】projectId={},roomId={},buildingid={}",wattRechargeDto.getProjectId(),wattRechargeDto.getRoomId(),roomInfoEntity.getBuildingid());
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("楼栋不存在");
            return  dto.toJsonString();
        }

        List<CostStandardEntity> list = projectServiceImpl.findCostStandardByProjectId(wattRechargeDto.getProjectId());

        if (Check.NuNCollection(list)){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("电表单价未设置");
            return  dto.toJsonString();
        }


        log.info("【房间地址】fName={},fbuildingname={},ffloornumber={},froomnumber={}",projectEntity.getFname(),buildingInfoEntity.getFbuildingname(),
                buildingInfoEntity.getFfloornumber(),roomInfoEntity.getFfloornumber());
        StringBuffer address = new StringBuffer(projectEntity.getFname());
        address.append(buildingInfoEntity.getFbuildingname()+"-")
                .append(buildingInfoEntity.getFfloornumber()+"-")
                .append(roomInfoEntity.getFfloornumber()+"房间");

        double electricityPrice = 0.0;
        for (CostStandardEntity costStandardEntity : list){
            if (costStandardEntity.getFmetertype().equals(String.valueOf(MeterTypeEnum.ELECTRICITY.getCode()))){
                electricityPrice = costStandardEntity.getFprice();
                break;
            }
        }
        MemterRechargeVo memterRechargeVo = new  MemterRechargeVo();
        memterRechargeVo.setAddress(address.toString());
        memterRechargeVo.setPrice(String.valueOf(electricityPrice)+" 元/kwh");
        dto.putValue("memterRechargeVo",memterRechargeVo);
        return dto.toJsonString();
    }


}
