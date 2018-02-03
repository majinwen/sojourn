package com.ziroom.minsu.services.search.vo;

import com.asura.framework.base.entity.BaseEntity;

/**
 * <p>消息的权重信息</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/8/18.
 * @version 1.0
 * @since 1.0
 */
public class WeightMessageVo extends BaseEntity{

    private static final long serialVersionUID = 332429879809946703L;

    /** 消息数量  */
    private Integer messageNum;

    /** 已经回复的消息数量  */
    private Integer replyNum;

    public Integer getMessageNum() {
        return messageNum;
    }

    public void setMessageNum(Integer messageNum) {
        this.messageNum = messageNum;
    }

    public Integer getReplyNum() {
        return replyNum;
    }

    public void setReplyNum(Integer replyNum) {
        this.replyNum = replyNum;
    }
}
