package com.zra.common.utils;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.JsonEntityTransform;
import com.zra.common.entity.MessagePushEntity;
import org.slf4j.Logger;
import com.apollo.logproxy.slf4j.LoggerFactoryProxy;

import java.util.Map;

/**
 * <p>消息推送工具类</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author renxw
 * @version 1.0
 * @date 2016/8/10 20:22
 * @since 1.0
 * @deprecated 不要随便使用该类
 */
public class MessagePushUtil {

    private static final Logger LOGGER = LoggerFactoryProxy.getLogger(MessagePushUtil.class);

    /**
     *
     * @param url
     * @param message
     * @return
     */
    public static String push(String url, MessagePushEntity message) {
        DataTransferObject dto = new DataTransferObject();
        if (message == null) {
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("请求发送的消息为空。");
            return dto.toJsonString();
        }
        try {
            LOGGER.info("请求消息推送，参数：{}", JsonEntityTransform.Object2Json(message));
            DataTransferObject dataTransferObject = HttpClientUtil.getInstance().postJSON(url, JsonEntityTransform.Object2Json(message));
            LOGGER.info("请求消息推送，结果：{}", dataTransferObject.toJsonString());
            return parseResponse(dataTransferObject);
        } catch (Exception e) {
            // 调用失败视作失败
            LOGGER.error("请求消息推送接口，异常：{}", e);
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("请求消息推送接口异常。");
            return dto.toJsonString();
        }
    }

    /**
     * 结果解析
     *
     * @param dataTransferObject
     *         请求返回内容
     *
     * @return 结果
     */
    private static String parseResponse(DataTransferObject dataTransferObject) {
        DataTransferObject dto = new DataTransferObject();
        if (null == dataTransferObject) {
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("请求消息推送接口异常。");
            return dto.toJsonString();
        }
        if (dataTransferObject.getCode() != DataTransferObject.SUCCESS) {
            return dto.toJsonString();
        }
        String responseJson = (String) dataTransferObject.getData().get("data");
        LOGGER.info("消息推送API返回结果：{}", dataTransferObject.toJsonString());
        Map<?, ?> json2Map = JsonEntityTransform.json2Map(responseJson);
        if (Check.NuNMap(json2Map)) {
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("请求消息推送接口异常。");
            return dto.toJsonString();
        }
        String responseStatus = (String) json2Map.get("status");
        if (responseStatus.equals("1")) {
            LOGGER.warn("请求消息推送接口异常.error:{}", json2Map.get("message"));
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("请求消息推送接口异常。");
            return dto.toJsonString();
        }
        return dto.toJsonString();
    }
}
