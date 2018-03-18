package com.ziroom.zrp.service.job.trading;

import com.asura.framework.base.context.ApplicationContext;
import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.SOAResParseUtil;
import com.asura.framework.quartz.job.AsuraJob;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.services.common.thread.pool.SendThreadPool;
import com.ziroom.zrp.houses.entity.RoomInfoEntity;
import com.ziroom.zrp.service.trading.api.WaterClearingService;
import com.ziroom.zrp.trading.entity.IntellectWatermeterReadEntity;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * <p>智能水表月结账单job</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author lusp
 * @version 1.0
 * @Date Created in 2018/2/27 11:39
 * @since 1.0
 */

public class WaterMonthlySettleJob extends AsuraJob{

    public static final Logger LOGGER = LoggerFactory.getLogger(WaterMonthlySettleJob.class);

    /**
     * @description: 智能水表月结账单任务
     *              1.遍历所有有智能水表以及有效合同的房间
     *              2.抄表；遍历房间下的所有合同、生成清算单、结算清算单并生成应收账单
     * @author: lusp
     * @date: 2018/2/27 上午 11:41
     * @params: jobExecutionContext
     * @return:
     */
    @Override
    public void run(JobExecutionContext jobExecutionContext) {
        String logPre = "【WaterMonthlySettleJob】智能水表月结任务-";
        LogUtil.info(LOGGER, logPre+"开始执行.....");
        Thread task = new Thread(){
            @Override
            public void run() {
                try {
                    WaterClearingService waterClearingService = (WaterClearingService) ApplicationContext.getContext().getBean("job.waterClearingService");
                    DataTransferObject dto = null;
                    String readWaterMeterAndsettleMonthlyJson = null;
                    //1.查询有有效合同并且存在智能水表设备的房间列表
                    String roomInfoEntitiesJson = waterClearingService.getAllRoomOfValidContractAndExistIntellectWaterMeter();
                    dto = JsonEntityTransform.json2DataTransferObject(roomInfoEntitiesJson);
                    if(dto.getCode()==DataTransferObject.ERROR){
                        LogUtil.error(LOGGER, logPre+"获取房间列表失败, error:{}", dto.getMsg());
                        return;
                    }
                    List<RoomInfoEntity> roomInfoEntities = SOAResParseUtil.getListValueFromDataByKey(roomInfoEntitiesJson,"roomInfoEntities",RoomInfoEntity.class);

                    for(RoomInfoEntity roomInfoEntity:roomInfoEntities){
                        //2.抄表；遍历房间下的所有合同、生成清算单、结算清算单并生成应收账单
                        IntellectWatermeterReadEntity readEntity = new IntellectWatermeterReadEntity();
                        readEntity.setProjectId(roomInfoEntity.getProjectid());
                        readEntity.setRoomId(roomInfoEntity.getFid());
                        readEntity.setReadType(0);//抄表类型  定时
                        readWaterMeterAndsettleMonthlyJson = waterClearingService.readWaterMeterAndsettleMonthly(JsonEntityTransform.Object2Json(readEntity));
                        dto = JsonEntityTransform.json2DataTransferObject(readWaterMeterAndsettleMonthlyJson);
                        if(DataTransferObject.ERROR==dto.getCode()){
                            LogUtil.error(LOGGER, logPre+"遍历房间时出现异常, error:{}", dto.getMsg());
                            return;
                        }
                    }
                }catch (Exception e){
                    LogUtil.error(LOGGER, logPre+"error:{}", e);
                }
            }
        };
        SendThreadPool.execute(task);
        LogUtil.info(LOGGER, logPre+"执行完毕.....");
    }

}
