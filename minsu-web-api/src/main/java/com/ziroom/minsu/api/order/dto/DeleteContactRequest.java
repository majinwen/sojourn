package com.ziroom.minsu.api.order.dto;

import com.ziroom.minsu.api.common.dto.BaseParamDto;

/**
 * <p></p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author lishaochuan on 2016/12/2 10:14
 * @version 1.0
 * @since 1.0
 */
public class DeleteContactRequest{

    /**
     * 联系人fid
     */
    private String fid;

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }
}
