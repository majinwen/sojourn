package com.zra.business.entity.dto;

import com.zra.common.dto.appbase.AppBaseDto;

/**
 * <p></p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author renxw
 * @version 1.0
 * @date 2016/8/5 13:42
 * @since 1.0
 */
public class BusinessListReqDto extends AppBaseDto {

    /**
     * 用户
     */
    private String uid;
    /**
     * 状态（1，进行中；0，已完成）
     */
    private Integer state;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}
