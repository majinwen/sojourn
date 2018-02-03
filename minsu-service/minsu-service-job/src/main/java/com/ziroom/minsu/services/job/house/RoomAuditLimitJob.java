package com.ziroom.minsu.services.job.house;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.codehaus.jackson.type.TypeReference;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.asura.framework.base.context.ApplicationContext;
import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.quartz.job.AsuraJob;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.customer.CustomerBaseMsgEntity;
import com.ziroom.minsu.services.customer.api.inner.CustomerInfoService;
import com.ziroom.minsu.services.customer.api.inner.TelExtensionService;
import com.ziroom.minsu.services.house.api.inner.HouseJobService;
import com.ziroom.minsu.services.house.entity.RoomLandlordVo;
import com.ziroom.minsu.valenum.customer.AuditStatusEnum;

/**
 * 
 * <p>超过审核时限房间自动上架定时任务</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author liujun
 * @since 1.0
 * @version 1.0
 */
public class RoomAuditLimitJob extends AsuraJob {

    private static final Logger LOGGER = LoggerFactory.getLogger(RoomAuditLimitJob.class);
    
	@Resource(name="customer.customerInfoService")
	private CustomerInfoService customerInfoService;
	
    @Resource(name="customer.telExtensionService")
    private TelExtensionService telExtensionService;
    
    /**
     * 每天凌晨0点30分执行
     * 0 30 0 * *
     * @author liujun
     * @param jobExecutionContext
     */
    @Override
    public void run(JobExecutionContext jobExecutionContext){
        LogUtil.info(LOGGER, "RoomAuditLimitJob 开始执行.....");
        long d1 = new Date().getTime();
        
        // 0 30 0 * *
        try {
            HouseJobService houseJobService = (HouseJobService) ApplicationContext.getContext().getBean("job.houseJobService");
            String resultJson = houseJobService.findOverAuditLimitRoomList();
            
            DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(resultJson);
            if(dto.getCode() == DataTransferObject.ERROR){
            	LogUtil.error(LOGGER, "houseJobService.findOverAuditLimitRoomList接口调用失败,结果:{}", resultJson);
            	return;
            }
            
            List<RoomLandlordVo> roomList = dto.parseData("list", new TypeReference<List<RoomLandlordVo>>(){});
            Iterator<RoomLandlordVo> it = roomList.iterator();
            while (it.hasNext()) {
            	RoomLandlordVo roomLandlordVo = it.next();
            	if(Check.NuNStr(roomLandlordVo.getLandlordUid())){
            		LogUtil.error(LOGGER, "房东uid为空,houseRoomFid={}", roomLandlordVo.getFid());
            		it.remove();
            		continue;
            	}
            	
            	String customerJson = customerInfoService.getCustomerInfoByUid(roomLandlordVo.getLandlordUid());
            	DataTransferObject customerDto = JsonEntityTransform.json2DataTransferObject(customerJson);
            	if(customerDto.getCode() == DataTransferObject.ERROR){
					LogUtil.error(LOGGER, "customerInfoService.getCustomerInfoByUid接口调用失败,landlordUid={}",
							roomLandlordVo.getLandlordUid());
					it.remove();
					continue;
            	}
            	
				CustomerBaseMsgEntity customerBaseMsg = customerDto.parseData("customerBase",
						new TypeReference<CustomerBaseMsgEntity>() {});
				if(Check.NuNObj(customerBaseMsg)){
					LogUtil.error(LOGGER, "房东信息不存在,landlordUid={}",
							roomLandlordVo.getLandlordUid());
					it.remove();
					continue;
				}
				
				if(customerBaseMsg.getAuditStatus() != AuditStatusEnum.COMPLETE.getCode()){
					LogUtil.info(LOGGER, "房东认证信息未通过,landlordUid={}",
							roomLandlordVo.getLandlordUid());
					it.remove();
					continue;
				}
			}
            
            String landlordJson = houseJobService.roomAuditLimit(JsonEntityTransform.Object2Json(roomList));
            DataTransferObject landlordDto = JsonEntityTransform.json2DataTransferObject(landlordJson);
            Set<String> landlordSet = landlordDto.parseData("set", new TypeReference<Set<String>>(){});
			LogUtil.info(LOGGER, "自动上架房东列表:{},size={}", JsonEntityTransform.Object2Json(landlordSet),
					landlordSet.size());
			for (String landlord : landlordSet) {
				telExtensionService.bindZiroomPhoneAsynchronous(landlord, "system");
			}
            long d2 = new Date().getTime();
            LogUtil.info(LOGGER, "RoomAuditLimitJob 执行结束,耗时{}毫秒", d2-d1);
        }catch (Exception e){
            LogUtil.error(LOGGER,"error:{}",e);
        }
    }

}
