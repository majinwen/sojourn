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
 * @date 2016/8/9 16:46
 * @since 1.0
 */
public class BusinessReqDto extends AppBaseDto {

    /**
     * 商机业务id
     */
    private String bid;

    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }
}
