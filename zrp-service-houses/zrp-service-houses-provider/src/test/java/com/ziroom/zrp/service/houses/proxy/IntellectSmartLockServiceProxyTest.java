package com.ziroom.zrp.service.houses.proxy;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.zrp.houses.entity.IntellectSmartLockEntity;
import com.ziroom.zrp.service.houses.base.BaseTest;
import com.ziroom.zrp.service.houses.dto.RoomSmartLockDto;
import com.ziroom.zrp.service.houses.dto.smartplatform.SmartPlatformChangeOccupantsDto;
import com.ziroom.zrp.service.houses.entity.IntellectSmartLockVo;
import com.ziroom.zrp.service.houses.valenum.*;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.jackson.type.TypeReference;
import org.junit.Test;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p></p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author yd
 * @version 1.0
 * @Date Created in 2017年12月06日 20:50
 * @since 1.0
 */
@Slf4j
public class IntellectSmartLockServiceProxyTest extends BaseTest {

    @Resource(name = "houses.intellectSmartLockServiceProxy")
    private IntellectSmartLockServiceProxy intellectSmartLockServiceProxy;


    @Test
    public void saveRoomSmartLockTest(){
        IntellectSmartLockEntity roomSmartLock = new IntellectSmartLockEntity();
        SmartPlatformChangeOccupantsDto param = new SmartPlatformChangeOccupantsDto();
        param.setRentContractCode("111");
        param.setHireContractCode("BJW11900002");
        param.setUid("222");
        param.setUserName("李四");
        param.setUserPhone("18500000001");

        roomSmartLock.setContractId("1111");
        roomSmartLock.setProjectId("20");
        roomSmartLock.setRoomId("1001497");
        roomSmartLock.setUserType(SmartUserTypeEnum.SYS.getCode());
        roomSmartLock.setUserCode("60001029");
        roomSmartLock.setUserName("jjjj");
        roomSmartLock.setPwdType(SmartLockPwdTypeEnum.PWD_NORMAL.getCode());
        roomSmartLock.setPhone("18701482472");
        roomSmartLock.setHandlerType(HandlerTypeEnum.EMP.getCode());
        roomSmartLock.setHandlerCode("20223709");
        roomSmartLock.setHandlerName("杨东");
        roomSmartLock.setStatus(SmartStatusEnum.SEND_SUCCESS.getCode());
        roomSmartLock.setStartTime(new Date());
        roomSmartLock.setEndTime(new Date(roomSmartLock.getStartTime().getTime()+24*60*60*1000));
        roomSmartLock.setParamStr(param.toJsonStr());
        roomSmartLock.setSourceType(IntellectSmartlockSourceTypeEnum.CHANGEOCCUPANTS.getCode());
        String re = intellectSmartLockServiceProxy.saveRoomSmartLock(JsonEntityTransform.Object2Json(roomSmartLock));

        System.out.println(re);
    }

    @Test
    public void pagingTest(){

        RoomSmartLockDto stmartDto = RoomSmartLockDto.builder().build();

        String result = intellectSmartLockServiceProxy.pagingSmartLock(JsonEntityTransform.Object2Json(stmartDto));

        Assert.notNull(result, "intellectSmartLockServiceProxy.pagingSmartLock return null!!");

        DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(result);

        Assert.isTrue(dto.getCode() == 0, String.format("service exception : %s",dto.getMsg()));

        List<IntellectSmartLockVo> entities = dto.parseData("list", new TypeReference<List<IntellectSmartLockVo>>() {});

        Assert.notEmpty(entities,"List<IntellectSmartLockVo> is empty");

        entities.stream().forEach(entity -> {
            log.info(entity.toJsonStr());
        });

    }

    @Test
    public void getFailSmartLockRecordTest(){
        List<Integer> sourceTypes = new ArrayList<>();
        sourceTypes.add(IntellectSmartlockSourceTypeEnum.ADDRENTCONTRACT.getCode());
        sourceTypes.add(IntellectSmartlockSourceTypeEnum.BACKRENT.getCode());
        sourceTypes.add(IntellectSmartlockSourceTypeEnum.CONTINUEABOUT.getCode());
        sourceTypes.add(IntellectSmartlockSourceTypeEnum.CHANGEOCCUPANTS.getCode());
        String result = intellectSmartLockServiceProxy.getFailSmartLockRecord(JsonEntityTransform.Object2Json(sourceTypes));
        System.out.println(result);
    }

    @Test
    public void updateIntellectSmartLockEntityTest(){
        IntellectSmartLockEntity updateEntity = new IntellectSmartLockEntity();
        updateEntity.setFid("8a9e989c6068926c016068926c700000");
        updateEntity.setStatus(SmartStatusEnum.SEND_SUCCESS.getCode());
        String result = intellectSmartLockServiceProxy.updateIntellectSmartLockEntity(updateEntity.toJsonStr());
        System.out.println(result);
    }


}
