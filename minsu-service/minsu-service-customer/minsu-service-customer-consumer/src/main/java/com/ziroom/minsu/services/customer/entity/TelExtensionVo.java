package com.ziroom.minsu.services.customer.entity;

import com.ziroom.minsu.entity.customer.TelExtensionEntity;

/**
 * <p>房东绑定的试图</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/5/6.
 * @version 1.0
 * @since 1.0
 */
public class TelExtensionVo extends TelExtensionEntity {

    /** 房东名称 */
    private String landName;

    /** 房东电话 */
    private String landPhone;

    public String getLandPhone() {
        return landPhone;
    }

    public void setLandPhone(String landPhone) {
        this.landPhone = landPhone;
    }

    public String getLandName() {
        return landName;
    }

    public void setLandName(String landName) {
        this.landName = landName;
    }
}
