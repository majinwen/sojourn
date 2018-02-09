package com.ziroom.minsu.troy.message.vo;

import com.ziroom.minsu.entity.message.MsgAdvisoryFollowupEntity;

/**
 * <p></p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author wangwentao 2017/5/27
 * @version 1.0
 * @since 1.0
 */
public class MsgAdvisoryFollowLogVO extends MsgAdvisoryFollowupEntity{

    private static final long serialVersionUID = 1836546034385795082L;
    private Integer index;

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }
}
