package com.ziroom.minsu.web.im.chat.controller.utils;

import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.message.MsgBaseLogEntity;
import com.ziroom.minsu.entity.message.MsgBaseOfflineEntity;
import com.ziroom.minsu.services.message.api.inner.MsgBaseService;
import com.ziroom.minsu.services.message.dto.AppChatRecordsDto;
import com.ziroom.minsu.services.message.dto.AppChatRecordsExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>保存离线消息记录</p>
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
public class SaveMsgOfflineThread implements Runnable{
    /**
     * 日志对象
     */
    private static Logger LOGGER = LoggerFactory.getLogger(SaveMsgOfflineThread.class);



    private MsgBaseService msgBaseService;
    private MsgBaseOfflineEntity msgBaseOfflineEntity;

    public SaveMsgOfflineThread(MsgBaseService msgBaseService,MsgBaseOfflineEntity msgBaseOfflineEntity) {
        this.msgBaseService = msgBaseService;
        this.msgBaseOfflineEntity = msgBaseOfflineEntity;
    }

    @Override
    public void run() {
       if (!Check.NuNObj(msgBaseService) && !Check.NuNObj(msgBaseOfflineEntity)){
           String result = msgBaseService.saveMsgOffline(JsonEntityTransform.Object2Json(msgBaseOfflineEntity));
           LogUtil.info(LOGGER,"保存离线消息结果result={}",result);
       }
    }
}
