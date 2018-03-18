package com.zra.push.logic;

import org.slf4j.Logger;
import com.apollo.logproxy.slf4j.LoggerFactoryProxy;

import com.zra.common.dto.push.PushDto;
import com.zra.common.security.Md5Utils;
import com.zra.common.utils.StrUtils;

import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;

/**
 * @author cuiyh9
 * @deprecated
 */
class JPushUtils {

    private static final Logger LOG = LoggerFactoryProxy.getLogger(JPushUtils.class);
    
    static  boolean sendMessage(final PushDto pushDto) {
        boolean flag = false;
        JPushClient jpushClient = new JPushClient("d6715f002743f7352152ef38", "f45a5da23625b3cc0ec2bb91", 3);
        PushPayload payload = buildPushObject(pushDto);
        try {
            PushResult result = jpushClient.sendPush(payload);
            flag = true;
        } catch (Exception e) {
            LOG.error("push fail", e);
            flag = false;
        }
        return flag;
    }
    
    private static PushPayload buildPushObject(final PushDto pushDto) {
        String uid = pushDto.getUidList().get(0);
        
        String md5Uid = Md5Utils.md5s(uid);
        
        if(StrUtils.isNullOrBlank(md5Uid)){
            return null;
        }
        return PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.alias(md5Uid))
                .setMessage(buildMessage(pushDto))
                .build();
    }
    
    private static Message  buildMessage(final PushDto pushDto){
        String title = pushDto.getTitle();
        String msgContent = pushDto.getContent();
        String openUrl = pushDto.getOpenUrl();
        return Message.newBuilder().setTitle(title).setMsgContent(msgContent).addExtra("content", openUrl).build();
    }
    
}
