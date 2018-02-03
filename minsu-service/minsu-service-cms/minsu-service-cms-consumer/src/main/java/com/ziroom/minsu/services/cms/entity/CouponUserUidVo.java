package com.ziroom.minsu.services.cms.entity;

import com.asura.framework.base.entity.BaseEntity;

/**
 * <p>获取优惠券uid信息</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd
 * @version 1.0
 * @Date Created in 2017年06月16日 18:18
 * @since 1.0
 */
public class CouponUserUidVo extends BaseEntity {
    private static final long serialVersionUID = -1617413456058901906L;

    public String uid;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
