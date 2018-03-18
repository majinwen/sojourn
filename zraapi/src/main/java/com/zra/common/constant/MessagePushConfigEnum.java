package com.zra.common.constant;

/**
 * <p>推送配置枚举</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author renxw
 * @version 1.0
 * @date 2016/8/11 10:14
 * @since 1.0
 * @deprecated 这一块需要从配置文件中取，不断环境配置可能会不同
 */
public enum MessagePushConfigEnum {

    MESSAGE_PUSH_HOST("zra", "message-push-host", "http://api.push.t.ziroom.com/push", "消息推送API接口地址"),
    MOVE_PUSH_CHANNEL("zra", "move-push-channel", "move-jg-push-token", "消息推送推送渠道（move-gt-push-token：个推 move-jg-push-token：极光）"),
    MOVE_GT_PUSH_TOKEN("zra", "move-gt-push-token", "未申请……", "消息推送推送平台申请的token(个推平台)"),
    MOVE_JG_PUSH_TOKEN("zra", "move-jg-push-token", "cf1cb7cfcfcc4a53adf0ad1ab84a67ba", "消息推送推送平台申请的token(极光平台)");

    private String type;
    private String code;
    private String defaultValue;
    private String notes;

    MessagePushConfigEnum(String type, String code, String defaultValue, String notes) {
        this.type = type;
        this.code = code;
        this.defaultValue = defaultValue;
        this.notes = notes;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code the code to set
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @return the defaultValue
     */
    public String getDefaultValue() {
        return defaultValue;
    }

    /**
     * @param defaultValue the defaultValue to set
     */
    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    /**
     * @return the notes
     */
    public String getNotes() {
        return notes;
    }

    /**
     * @param notes the notes to set
     */
    public void setNotes(String notes) {
        this.notes = notes;
    }
}
