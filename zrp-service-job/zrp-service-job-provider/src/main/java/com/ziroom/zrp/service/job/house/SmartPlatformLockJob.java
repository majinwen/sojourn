package com.ziroom.zrp.service.job.house;

import com.asura.framework.base.context.ApplicationContext;
import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.quartz.job.AsuraJob;
import com.asura.framework.utils.LogUtil;
import com.ziroom.zrp.houses.entity.IntellectSmartLockEntity;
import com.ziroom.zrp.service.houses.api.IntellectSmartLockService;
import com.ziroom.zrp.service.houses.api.SmartPlatformService;
import com.ziroom.zrp.service.houses.valenum.IntellectSmartlockSourceTypeEnum;
import com.ziroom.zrp.service.houses.valenum.SmartStatusEnum;
import org.codehaus.jackson.type.TypeReference;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>智能平台接口失败补偿任务</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author zhangyl2
 * @Date Created in 2017年12月20日
 * @version 1.0
 * @since 1.0
 */
public class SmartPlatformLockJob extends AsuraJob {

    public static final Logger LOGGER = LoggerFactory.getLogger(SmartPlatformLockJob.class);

    private static final String logPre = "SmartPlatformLockJob智能锁接口补偿任务-";

    @Override
    public void run(JobExecutionContext jobExecutionContext) {
        try {
            LogUtil.info(LOGGER, logPre + "开始执行.....");

            SmartPlatformService smartPlatformService = (SmartPlatformService) ApplicationContext.getContext().getBean("job.smartPlatformService");

            IntellectSmartLockService intellectSmartLockService = (IntellectSmartLockService) ApplicationContext.getContext().getBean("job.intellectSmartLockService");
            List<Integer> sourceTypes = new ArrayList<>();
            sourceTypes.add(IntellectSmartlockSourceTypeEnum.ADDRENTCONTRACT.getCode());
            sourceTypes.add(IntellectSmartlockSourceTypeEnum.BACKRENT.getCode());
            sourceTypes.add(IntellectSmartlockSourceTypeEnum.CONTINUEABOUT.getCode());
            sourceTypes.add(IntellectSmartlockSourceTypeEnum.CHANGEOCCUPANTS.getCode());
            DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(intellectSmartLockService.getFailSmartLockRecord(JsonEntityTransform.Object2Json(sourceTypes)));
            List<IntellectSmartLockEntity> list = dto.parseData("list", new TypeReference<List<IntellectSmartLockEntity>>() {
            });

            if(!Check.NuNCollection(list)){
                LogUtil.info(LOGGER, logPre + "补偿数据 list={}", list);

                DataTransferObject smartPlatformDto;
                for (IntellectSmartLockEntity entity : list) {
                    String param = entity.getParamStr();
                    Integer sourceType = entity.getSourceType();

                    if (IntellectSmartlockSourceTypeEnum.ADDRENTCONTRACT.getCode() == sourceType) {
                        // 新增智能平台出房合同
                        smartPlatformDto = JsonEntityTransform.json2DataTransferObject(smartPlatformService.addRentContract(param));

                    } else if (IntellectSmartlockSourceTypeEnum.BACKRENT.getCode() == sourceType) {
                        // 退租
                        smartPlatformDto = JsonEntityTransform.json2DataTransferObject(smartPlatformService.backRent(param));

                    } else if (IntellectSmartlockSourceTypeEnum.CONTINUEABOUT.getCode() == sourceType) {
                        // 续约
                        smartPlatformDto = JsonEntityTransform.json2DataTransferObject(smartPlatformService.continueAbout(param));

                    } else if (IntellectSmartlockSourceTypeEnum.CHANGEOCCUPANTS.getCode() == sourceType) {
                        // 更换入住人信息
                        smartPlatformDto = JsonEntityTransform.json2DataTransferObject(smartPlatformService.changeOccupants(param));

                    } else {
                        continue;
                    }

                    // 成功则更新记录状态
                    if(DataTransferObject.SUCCESS == smartPlatformDto.getCode()){
                        IntellectSmartLockEntity updateEntity = new IntellectSmartLockEntity();
                        updateEntity.setFid(entity.getFid());
                        updateEntity.setStatus(SmartStatusEnum.SEND_SUCCESS.getCode());
                        intellectSmartLockService.updateIntellectSmartLockEntity(updateEntity.toJsonStr());
                    }else{
                        LogUtil.error(LOGGER, logPre + "补偿失败 entity={}, smartPlatformDto={}", entity.toJsonStr(), smartPlatformDto.toJsonString());
                    }
                }
            }

            LogUtil.info(LOGGER, logPre + "执行完毕.....");
        } catch (Exception e) {
            LogUtil.error(LOGGER, logPre + "error:{}", e);
        }
    }
}
