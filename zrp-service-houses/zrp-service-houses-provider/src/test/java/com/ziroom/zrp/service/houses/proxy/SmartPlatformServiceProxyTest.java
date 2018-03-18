package com.ziroom.zrp.service.houses.proxy;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.exception.SOAParseException;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.SOAResParseUtil;
import com.ziroom.zrp.service.houses.base.BaseTest;
import com.ziroom.zrp.service.houses.dto.WattRechargeDto;
import com.ziroom.zrp.service.houses.dto.smartplatform.*;

import com.ziroom.zrp.service.houses.dto.smartplatform.waterwatt.*;
import com.ziroom.zrp.service.houses.entity.smartplatform.waterwatt.HistoryWaterMeterVo;
import com.ziroom.zrp.service.houses.entity.smartplatform.waterwatt.ServiceIdBaseVo;
import com.ziroom.zrp.service.houses.valenum.CompanyEnum;
import com.ziroom.zrp.service.houses.valenum.SmartPlatformWaterWattPayTypeEnum;

import lombok.extern.slf4j.Slf4j;
import org.codehaus.jackson.type.TypeReference;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * <p>智能平台服务</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author zhangyl2
 * @Date Created in 2017年12月19日
 * @version 1.0
 * @since 1.0
 */
@Slf4j
public class SmartPlatformServiceProxyTest extends BaseTest {

    @Resource(name = "houses.smartPlatformServiceProxy")
    private SmartPlatformServiceProxy smartPlatformServiceProxy;

    @Test
    public void initSmartHireContractTest() {
        SmartPlatformSaveHireContractDto p = new SmartPlatformSaveHireContractDto();
        p.setHireContractCode("8a9099cb576ba5c101576ea29c8a0027");
        p.setHouseSourceCode("上海其灵自如寓");

        CompanyEnum companyEnum = CompanyEnum.ZIROOM_BJ_ZRA_HOTEL;
        p.setCityCode(Check.NuNObj(companyEnum) ? "" : companyEnum.getCode());
        p.setCityName(Check.NuNObj(companyEnum) ? "" : companyEnum.getName());

        p.setAddress("上海市闵行区都会路368号");
        p.setHireContractStartTime("2016-03-31 00:00:00");
        p.setHireContractEndTime("2024-03-29 00:00:00");

        SmartPlatformSaveHireContractDto.Device device = p.new Device();
        device.setDeviceType("ZR0011");
        device.setPositionRank1("8a9099cb576ba5c101576ea29c8a0027");
        device.setPositionRank2("8a90a3ab600af58e016049be91e220f1");
        device.setPositionRankName("101");

        p.addDevice(device);
        String result = smartPlatformServiceProxy.initSmartHireContract(p.toJsonStr());
        System.out.println(result);
    }

    @Test
    public void saveRoomSmartLockTest() {
        SmartPlatformAddRentContractDto param = new SmartPlatformAddRentContractDto();
        param.setRentContractCode("111");
        param.setHireContractCode("BJW11900002");
        param.setRentContractStartDate("2017-12-16 00:00:00");
        param.setRentContractEndDate("2018-12-16 00:00:00");
        param.setUid("111");
        param.setVillage("111");
        param.setUserName("张三");
        param.setUserPhone("18500000000");
        param.setPositionRank1("BJW11900002");
        param.setPositionRank2("202");
        param.setRoomCode("202");
        String result = smartPlatformServiceProxy.addRentContract(param.toJsonStr());
        System.out.println(result);
    }

    @Test
    public void backRentTest() {
        SmartPlatformBackRentDto param = new SmartPlatformBackRentDto();
        param.setRentContractCode("111");
        param.setHireContractCode("BJW11900002");
        String result = smartPlatformServiceProxy.backRent(param.toJsonStr());
        System.out.println(result);
    }

    @Test
    public void continueAboutTest() {
        SmartPlatformContinueAboutDto param = new SmartPlatformContinueAboutDto();
        param.setRentContractCode("222");
        param.setHireContractCode("BJW11900002");
        param.setLogRentContractCode("111");
        param.setRentContractStartDate("2017-12-16 00:00:00");
        param.setRentContractEndDate("2018-12-16 00:00:00");
        String result = smartPlatformServiceProxy.continueAbout(param.toJsonStr());
        System.out.println(result);
    }

    @Test
    public void changeOccupantsTest() {
        SmartPlatformChangeOccupantsDto param = new SmartPlatformChangeOccupantsDto();
        param.setRentContractCode("111");
        param.setHireContractCode("BJW11900002");
        param.setUid("222");
        param.setUserName("李四");
        param.setUserPhone("18500000001");
        String result = smartPlatformServiceProxy.changeOccupants(param.toJsonStr());
        System.out.println(result);
    }

    @Test
    public void getLockInfoTest() {
        SmartPlatformGetLockInfoDto param = new SmartPlatformGetLockInfoDto();
        param.setHireContractCode("17");
        param.setOpsUser("李四");
        param.setOpsUserIdentifier("222");
        param.setOpsUserType("2");
        param.setPositionRank1("17");
        param.setPositionRank2("1001821");
        String result = smartPlatformServiceProxy.getLockInfo(param.toJsonStr());
        System.out.println(result);
    }

    @Test
    public void addPasswordTest() {
        SmartPlatformAddPasswordDto param = new SmartPlatformAddPasswordDto();
        param.setHireContractCode("17");
        param.setOpsUser("张扬乐");
        param.setOpsUserIdentifier("60001029");
        param.setOpsUserType("2");
        param.setUserName("张扬乐");
        param.setUserPhone("18500866372");
        param.setPassword("556348");
        param.setPositionRank1("17");
        param.setPositionRank2("1001821");
        param.setPasswordType("2");
        param.setUserIdentify("60001029");
        param.setIsSendSms("1");
        param.setPermissionBegin("2017-12-23 00:00:00");
        param.setPermissionEnd("2018-12-19 00:00:00");

        param.setBackUrl("http://zyu.d.ziroom.com/api/smartCallback/issuePassCallback");

        String result = smartPlatformServiceProxy.addPassword(param.toJsonStr());


        System.out.println(result);
    }


    @Test
    public void temporaryPasswordTest() {
        SmartPlatformTemporaryPasswordDto param = new SmartPlatformTemporaryPasswordDto();
        param.setHireContractCode("17");
        param.setOpsUser("李四");
        param.setOpsUserIdentifier("222");
        param.setOpsUserType("2");
        param.setPositionRank1("17");
        param.setPositionRank2("1001821");

        String result = smartPlatformServiceProxy.temporaryPassword(param.toJsonStr());
        System.out.println(result);
    }

    /**
     *
     * 获取电表详情接口
     *
     * @author zhangyl2
     * @created 2018年01月16日 10:41
     * @param
     * @return
     */
    @Test
    public void electricMeterStateTest() {
        WattElectricMeterStateDto param = new WattElectricMeterStateDto();
        param.setHireContractCode("8a9099cb576ba5c101576ea29c8a0027");
        param.setPositionRank1("8a9099cb576ba5c101576ea29c8a0027");
        param.setPositionRank2("8a90a3ab57e7054a0157e7f830cd0ec0");
        String result = smartPlatformServiceProxy.electricMeterState(param.toJsonStr());
        System.out.println(result);
    }

    /**
     *
     * 获取水表详情接口
     *
     * @author zhangyl2
     * @created 2018年01月16日 10:41
     * @param
     * @return
     */
    @Test
    public void waterMeterStateTest() {
        WaterMeterStateDto param = new WaterMeterStateDto();
        param.setHireContractCode("8a9099cb576ba5c101576ea29c8a0027");
        param.setPositionRank1("8a9099cb576ba5c101576ea29c8a0027");
        param.setPositionRank2("8a90a3ab57e7054a0157e7f830cd0ec0");
        String result = smartPlatformServiceProxy.waterMeterState(param.toJsonStr());
        System.out.println(result);
    }

    /**
     *
     * 设置房间电表付费模式
     *
     * @author zhangyl2
     * @created 2018年01月16日 10:41
     * @param
     * @return
     */
    @Test
    public void setRoomPayTypeTest() {
        WattSetRoomPayTypeDto param = new WattSetRoomPayTypeDto();
        param.setHireContractCode("8a9099cb576ba5c101576ea29c8a0027");
        param.setPositionRank1("8a9099cb576ba5c101576ea29c8a0027");
        param.setPositionRank2("8a90a3ab57e7054a0157e7f830cd0ec0");
        param.setPayType(SmartPlatformWaterWattPayTypeEnum.PREPAYMENT.getCode());
        param.setOverdraft(10);
        String result = smartPlatformServiceProxy.setRoomPayType(param.toJsonStr());
        System.out.println(result);
    }

    /**
     *
     * 设置房间电表电表透支额度
     *
     * @author zhangyl2
     * @created 2018年01月16日 10:41
     * @param
     * @return
     */
    @Test
    public void meterOverdraftQuotaTest() {
        WattMeterOverdraftQuotaDto param = new WattMeterOverdraftQuotaDto();
        param.setHireContractCode("8a9099cb576ba5c101576ea29c8a0027");
        param.setPositionRank1("8a9099cb576ba5c101576ea29c8a0027");
        param.setPositionRank2("8a90a3ab57e7054a0157e7f830cd0ec0");
        param.setOverdraft(12);
        String result = smartPlatformServiceProxy.meterOverdraftQuota(param.toJsonStr());
        System.out.println(result);
    }

    /**
     *
     * 电表充电(有异步回调)
     *
     * @author zhangyl2
     * @created 2018年01月16日 10:41
     * @param
     * @return
     */
    @Test
    public void meterChargingTest() {
        WattMeterChargingDto param = new WattMeterChargingDto();
        param.setHireContractCode("8a9099cb576ba5c101576ea29c8a0027");
        param.setPositionRank1("8a9099cb576ba5c101576ea29c8a0027");
        param.setPositionRank2("8a90a3ab57e7054a0157e7f830cd0ec0");
        param.setAmount(13);
        //TODO
        param.setTradeNum("dfdfdfdfd");

        DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(smartPlatformServiceProxy.meterCharging(JsonEntityTransform.Object2Json(param)));
        log.info("【智能充电返回】结果dto={}",dto.toJsonString());

        if (dto.getCode() == DataTransferObject.SUCCESS){
            ServiceIdBaseVo data = dto.parseData("data", new TypeReference<ServiceIdBaseVo>() {
            });
            System.out.println(data.toJsonStr());

        }
    }

    /**
     *
     * 电表清零(有异步回调)
     *
     * @author zhangyl2
     * @created 2018年01月16日 10:41
     * @param
     * @return
     */
    @Test
    public void electricMeterResetTest() {
        WattElectricMeterResetDto param = new WattElectricMeterResetDto();
        param.setHireContractCode("8a9099cb576ba5c101576ea29c8a0027");
        param.setPositionRank1("8a9099cb576ba5c101576ea29c8a0027");
        param.setPositionRank2("8a90a3ab57e7054a0157e7f830cd0ec0");
        String result = smartPlatformServiceProxy.electricMeterReset(param.toJsonStr());
        System.out.println(result);
    }

    /**
     *
     * 最新抄表水表记录
     *
     * @author zhangyl2
     * @created 2018年01月16日 10:41
     * @param
     * @return
     */
    @Test
    public void historyWaterMeterTest() {
        WaterHistoryWaterMeterDto param = new WaterHistoryWaterMeterDto();
        param.setHireContractCode("8a9099cb576ba5c101576ea29c8a0027");
        param.setPositionRank1("8a9099cb576ba5c101576ea29c8a0027");
        param.setPositionRank2("8a90a3ab57e7054a0157e7f830cd0ec0");

        String result = smartPlatformServiceProxy.readNewestWaterMeter(param.toJsonStr());
        System.out.println(result);
        try {
            HistoryWaterMeterVo vo = SOAResParseUtil.getValueFromDataByKey(result, "historyWaterMeterVo", HistoryWaterMeterVo.class);

            //读数
            System.out.println(vo.getDevice_amount());
        } catch (SOAParseException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * 最新抄表电表记录
     *
     * @author yangd74
     * @created 2018年01月16日 10:41
     * @param
     * @return
     */
    @Test
    public void readNewestElectricMeterTest() {

        WattHistoryElectricMeterDto param = new WattHistoryElectricMeterDto();
        param.setHireContractCode("8a9099cb576ba5c101576ea29c8a0027");
        param.setPositionRank1("8a9099cb576ba5c101576ea29c8a0027");
        param.setPositionRank2("8a90a3ab57e7054a0157e7f830cd0ec0");
        String result = smartPlatformServiceProxy.readNewestElectricMeter(param.toJsonStr());
        System.out.println(result);
    }

    /**
     *
     * 查询电表充值页面信息
     *
     * @author zhangyl2
     * @created 2018年01月30日 11:58
     * @param
     * @return
     */
    @Test
    public void queryWattRechargeInfoTest(){

        WattRechargeDto wattRechargeDto = new WattRechargeDto();
        wattRechargeDto.setProjectId("17");
        wattRechargeDto.setRoomId("1001967");
        String json = this.smartPlatformServiceProxy.queryWattRechargeInfo(JsonEntityTransform.Object2Json(wattRechargeDto));
        System.out.println(json);
    }
}
