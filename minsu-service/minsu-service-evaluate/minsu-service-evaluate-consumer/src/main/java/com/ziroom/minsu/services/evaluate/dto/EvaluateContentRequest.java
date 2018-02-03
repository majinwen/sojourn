package com.ziroom.minsu.services.evaluate.dto;

import com.ziroom.minsu.entity.evaluate.EvaluateOrderEntity;
/**
 * <p>评价请求</p>
 *
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd
 * @since 1.0
 * @version 1.0
 */
public class EvaluateContentRequest extends EvaluateOrderEntity {
    /**
     * 评价内容
     */
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
