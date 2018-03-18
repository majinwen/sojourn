package com.zra.common.constant;

/**
 * <p>消息推送方式枚举</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author renxw
 * @version 1.0
 * @date 2016/8/11 13:05
 * @since 1.0
 */
public enum MessagePushTargetEnum {

    SEND_TO_ALL(1, "all", "所有用户"),
    SEND_TO_T(2, "t", "透传（不显示，应用处理）"),
    SEND_TO_SIGIN(3, "sigin", "单个用户");

    private int code;

    private String target;

    private String note;

    public int getCode() {
        return code;
    }

    public String getTarget() {
        return target;
    }

    public String getNote() {
        return note;
    }

    MessagePushTargetEnum(int code, String target, String note) {
        this.code = code;
        this.target = target;
        this.note = note;
    }

    public static MessagePushTargetEnum findTarget(int code) {
        for (MessagePushTargetEnum sendTarget : MessagePushTargetEnum.values()) {
            if (sendTarget.getCode() == code) {
                return sendTarget;
            }
        }
        return null;
    }
}
