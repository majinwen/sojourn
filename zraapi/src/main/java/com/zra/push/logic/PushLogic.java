package com.zra.push.logic;

import java.util.*;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.JsonEntityTransform;
import com.zra.common.utils.HttpClientUtil;
import org.slf4j.Logger;
import com.apollo.logproxy.slf4j.LoggerFactoryProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zra.common.constant.MessagePushTargetEnum;
import com.zra.common.dto.push.PushDto;

import com.zra.common.entity.MessagePushEntity;
import com.zra.common.security.Md5Utils;

import com.zra.common.utils.KeyGenUtils;
import com.zra.common.utils.PropUtils;
import com.zra.common.utils.StrUtils;
import com.zra.push.entity.PushLog;
import com.zra.push.service.PushService;


/**
 * 推送业务逻辑类.
 * @author cuiyh9
 */
@Component
public class PushLogic {


    /**
     *日志记录类.
     */
    private static final Logger LOGGER = LoggerFactoryProxy.getLogger(PushLogic.class);

    /**
     *push service实体.
     */
    @Autowired
    private PushService pushService;

    /**
     * 推送消息.
     * @param pushDto PushDto
     * @return boolean
     */
    public final boolean push(final PushDto pushDto) {
        LOGGER.info("push:"+JSON.toJSONString(pushDto));
        if(!validatePushDto(pushDto)){
            LOGGER.info("push validate false");
            return false;
        }
        
//        boolean flag = pushByPlat(pushDto);
//        boolean flag = pushByPlat(pushDto);
        DataTransferObject dataTransferObject = pushByPlat(pushDto);
        boolean flag = false;
        String serNo = "";
        if(dataTransferObject != null && 200 == dataTransferObject.getCode() &&
                dataTransferObject.getData()!=null){
            String jsonStr = (String)dataTransferObject.getData().get("data");
            if(StrUtils.isNotNullOrBlank(jsonStr)){
                JSONObject  jo = JSON.parseObject(jsonStr);
                String status = (String)jo.get("status");
                if("0".equals(status)){
                    flag = true;
                    serNo = (String)jo.get("data");
                }
                
            }
        }
//        boolean flag = JPushUtils.sendMessage(pushDto);
        String pushId = KeyGenUtils.genKey();
        for(String uid : pushDto.getUidList()){
            Integer pushResult = 0;
            if(flag){
                pushResult = 1;
            }
            String pushFailReason = "";
            PushLog pushLog = new PushLog(pushId, uid, pushDto.getTitle(), pushDto.getContent(), pushDto.getOpenUrl(), new Date(), pushResult,
                    pushFailReason, pushDto.getSystemId(), pushDto.getFuncFlag(),serNo);
            pushService.savePushLog(pushLog);
        }
        return flag;
	}
    
    
    private boolean validatePushDto(final PushDto pushDto) {
        if(pushDto.getUidList()==null
                || pushDto.getUidList().size() == 0
                || StrUtils.isNullOrBlank(pushDto.getTitle())
                || StrUtils.isNullOrBlank(pushDto.getContent())
                || StrUtils.isNullOrBlank(pushDto.getOpenUrl())) {
            return false;
        }
        return true;
    }
    
    /**
     * 通过自如推送平台推送消息.
     * @return
     */
    private  DataTransferObject pushByPlat(final PushDto pushDto){

        if(pushDto.getUidList()==null
                || pushDto.getUidList().size() == 0
                || StrUtils.isNullOrBlank(pushDto.getTitle())
                || StrUtils.isNullOrBlank(pushDto.getContent())) {
            LOGGER.info("push validate pushByPlat false");
            return null;
        }
        List<String> md5List = new ArrayList<String>();
        for(String uid: pushDto.getUidList()){
            md5List.add(Md5Utils.md5s(uid));
        }
        if(md5List.size() == 0){
            LOGGER.info("push validate md5List false");
            return null;
        }
                
          String pushUrl = PropUtils.getString("PUSH_URL");
          String pushToken = PropUtils.getString("PUSH_TOKEN");
//          MessagePushContentEntity messagePushContent = new MessagePushContentEntity();
//          messagePushContent.setId("testId");
//          messagePushContent.setCode("testCode");
//          messagePushContent.setTitle(pushDto.getTitle());
//          messagePushContent.setContent(pushDto.getContent());
//          messagePushContent.setMessageType(1);
//          messagePushContent.setTarget(2);
//          messagePushContent.setSendTime(System.currentTimeMillis());
          MessagePushEntity messagePush = new MessagePushEntity();
          messagePush.setToken(pushToken);
          messagePush.setTitle(pushDto.getTitle());
          messagePush.setContent(pushDto.getContent());
//          messagePush.setContent(JsonEntityTransform.Object2Json(messagePushContent));
          messagePush.setAlias(md5List.toArray(new String[]{}));
          messagePush.setTarget(MessagePushTargetEnum.SEND_TO_SIGIN.getTarget());
          
          //打开URL
//          Extras ex = new Extras();
//          ex.setContent(pushDto.getOpenUrl());
//          messagePush.setExtras(JsonEntityTransform.Object2Json(ex));
        HashMap<String, Object> map = new HashMap<>();
//        map.put("content", pushDto.getOpenUrl());
        map.put("ziroom_msg_id", pushDto.getSystemId());
        map.put("msg_body_type", "apartment_notify");
        map.put("msg_sub_type", pushDto.getFuncFlag());
        map.put("push_time", String.valueOf(new Date().getTime()));
        map.put("msg_has_response", "1");

        messagePush.setExtras(map);
          
          //只给android用户推送
          messagePush.setPlatform(new String[]{"android", "ios"});
          try {
              LOGGER.info("请求消息推送，参数：{}", JsonEntityTransform.Object2Json(messagePush));
              DataTransferObject dataTransferObject = HttpClientUtil.getInstance().postJSON(pushUrl, JsonEntityTransform.Object2Json(messagePush));
              LOGGER.info("请求消息推送dataTransferObject:{}", dataTransferObject.toJsonString());
              return dataTransferObject;
          } catch (Exception e) {
              // 调用失败视作失败
              LOGGER.error("请求消息推送接口，异常：{}", e);
             return null;
          }
    }
    
    class  Extras{
        
        /**
         *下发的URL 
         */
        private String content;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
        
        
    }
    
    public static void main(String[] args) {
        
    }
}
