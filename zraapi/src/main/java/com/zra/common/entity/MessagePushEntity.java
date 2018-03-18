package com.zra.common.entity;

import java.io.Serializable;
import java.util.HashMap;

/**
 * <p>推送消息实体</p>
 * <p>属性名称与接口定义属性名称一致</p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author renxw
 * @version 1.0
 * @date 2016/8/10 20:27
 * @since 1.0
 */
public class MessagePushEntity implements Serializable {

    private static final long serialVersionUID = -2789941527183801414L;
    /**
     * 推送token 推送平台申请
     */
    private String token;

    /**
     * 推送的内容
     */
    private String content;

    /**
     * 推送对象，alias, 用户UID数组
     */
    private String[] alias;
    

    /**
     * 推送的url，个推 【客端点击消息跳转的URL】
     *
     * @return
     */
    private String sendUrl;

    /**
     * 推送的title，个推
     */
    private String title;

    /**
     * 发送对象，all 所有, sign 单个
     *
     * @return
     */
    private String target;
    
    /**
     *添加推送平台 
     */
    private String[] platform;

    /**
     * extras 【极光】这里自定义 JSON 格式的 Key/Value 信息，以供业务使用
     *
     * @return
     */
    private HashMap extras;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String[] getAlias() {
        return alias;
    }

    public void setAlias(String[] alias) {
        this.alias = alias;
    }

    public String getSendUrl() {
        return sendUrl;
    }

    public void setSendUrl(String sendUrl) {
        this.sendUrl = sendUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }
    

    public String[] getPlatform() {
        return platform;
    }

    public void setPlatform(String[] platform) {
        this.platform = platform;
    }

    public HashMap getExtras() {
        return extras;
    }

    public void setExtras(HashMap extras) {
        this.extras = extras;
    }
}
