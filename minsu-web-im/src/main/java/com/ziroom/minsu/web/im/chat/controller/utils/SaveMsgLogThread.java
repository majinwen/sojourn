package com.ziroom.minsu.web.im.chat.controller.utils;

import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.minsu.entity.message.MsgBaseLogEntity;
import com.ziroom.minsu.services.message.api.inner.MsgBaseService;
import com.ziroom.minsu.services.message.dto.AppChatRecordsDto;
import com.ziroom.minsu.services.message.dto.AppChatRecordsExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>异步保存记录</p>
 *
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author yd
 * @since 1.0
 * @version 1.0
 */
public class SaveMsgLogThread implements Runnable{
    /**
     * 日志对象
     */
    private static Logger LOGGER = LoggerFactory.getLogger(SaveMsgLogThread.class);



    private MsgBaseService msgBaseService;
    private AppChatRecordsDto appChatRecordsDto;
    private AppChatRecordsExt appChatRecordsExt;

    public SaveMsgLogThread(AppChatRecordsDto appChatRecordsDto, AppChatRecordsExt appChatRecordsExt, MsgBaseService msgBaseService) {
        this.msgBaseService = msgBaseService;
        this.appChatRecordsDto = appChatRecordsDto;
        this.appChatRecordsExt = appChatRecordsExt;
    }

    @Override
    public void run() {
       if (!Check.NuNObj(appChatRecordsDto) && !Check.NuNObj(appChatRecordsExt)){
           MsgBaseLogEntity msgBaseLogEntity = new MsgBaseLogEntity();
           msgBaseLogEntity.setLandlordUid(appChatRecordsDto.getLandlordUid());
           msgBaseLogEntity.setTenantUid(appChatRecordsDto.getTenantUid());
           msgBaseLogEntity.setHuanxinMsgId(appChatRecordsExt.getHuanxinMsgId());
           msgBaseLogEntity.setHouseFid(Check.NuNStrStrict(appChatRecordsExt.getFid())?"0":appChatRecordsExt.getFid());
           msgBaseLogEntity.setRentWay(Check.NuNObj(appChatRecordsExt.getRentWay())?0:appChatRecordsExt.getRentWay());
           msgBaseLogEntity.setMsgSenderType(appChatRecordsDto.getMsgSentType());
           msgBaseLogEntity.setMsgRealContent(appChatRecordsDto.getMsgContent());
           msgBaseLogEntity.setMsgContentExt(JsonEntityTransform.Object2Json(appChatRecordsExt));
           
	       //填充图片相关字段值
           msgBaseLogEntity.setType(appChatRecordsDto.getType());
           msgBaseLogEntity.setUrl(appChatRecordsDto.getUrl());
           msgBaseLogEntity.setFilename(appChatRecordsDto.getFilename());
           msgBaseLogEntity.setFileLength(appChatRecordsDto.getFileLength());
           msgBaseLogEntity.setSecret(appChatRecordsDto.getSecret());
           msgBaseLogEntity.setSize(appChatRecordsDto.getSize());
   		
           msgBaseService.saveMsgLog(JsonEntityTransform.Object2Json(msgBaseLogEntity));
       }
    }
}
