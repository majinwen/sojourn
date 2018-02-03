package com.ziroom.minsu.services.customer.dto;

import com.ziroom.minsu.services.common.dto.PageRequest;

/**
 * <p>电话分页</p>
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
public class TelExtensionDto extends PageRequest {

    private static final long serialVersionUID = 5445654564564L;

    /** uid */
    private String uid;

    /** 房东名称 */
    private String landName;

    /** 房东电话 */
    private String landPhone;

    /**
     * @see com.ziroom.minsu.valenum.customer.ExtStatusEnum
     * 状态 */
    private Integer extStatus;

    /** 绑定座机 */
    private String ziroomPhone;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getLandName() {
        return landName;
    }

    public void setLandName(String landName) {
        this.landName = landName;
    }

    public String getLandPhone() {
        return landPhone;
    }

    public void setLandPhone(String landPhone) {
        this.landPhone = landPhone;
    }

    public Integer getExtStatus() {
        return extStatus;
    }

    public void setExtStatus(Integer extStatus) {
        this.extStatus = extStatus;
    }

    public String getZiroomPhone() {
        return ziroomPhone;
    }

    public void setZiroomPhone(String ziroomPhone) {
        this.ziroomPhone = ziroomPhone;
    }
}
