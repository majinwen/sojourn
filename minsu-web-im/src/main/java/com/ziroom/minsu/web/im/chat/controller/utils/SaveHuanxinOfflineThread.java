package com.ziroom.minsu.web.im.chat.controller.utils;

import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.message.HuanxinImOfflineEntity;
import com.ziroom.minsu.services.message.api.inner.HuanxinImRecordService;

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
 * @author loushuai
 * @since 1.0
 * @version 1.0
 */
public class SaveHuanxinOfflineThread implements Runnable{
    /**
     * 日志对象
     */
    private static Logger LOGGER = LoggerFactory.getLogger(SaveMsgOfflineThread.class);



    private HuanxinImRecordService huanxinImRecordService;
    private HuanxinImOfflineEntity huanxinImOffline;

    public SaveHuanxinOfflineThread(HuanxinImRecordService huanxinImRecordService,HuanxinImOfflineEntity huanxinImOffline) {
        this.huanxinImRecordService = huanxinImRecordService;
        this.huanxinImOffline = huanxinImOffline;
    }

    @Override
    public void run() {
       if (!Check.NuNObj(huanxinImRecordService) && !Check.NuNObj(huanxinImOffline)){
           String result = huanxinImRecordService.saveHuanxinOffline(JsonEntityTransform.Object2Json(huanxinImOffline));
           LogUtil.info(LOGGER,"保存离线消息结果result={}",result);
       }
    }
}
