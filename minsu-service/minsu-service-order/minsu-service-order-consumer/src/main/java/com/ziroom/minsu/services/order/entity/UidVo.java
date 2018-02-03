package com.ziroom.minsu.services.order.entity;

import com.asura.framework.base.entity.BaseEntity;

/**
 * <p></p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author lishaochuan on 2016/12/22 10:05
 * @version 1.0
 * @since 1.0
 */
public class UidVo extends BaseEntity {

    private static final long serialVersionUID = 8898259269978727568L;

    private String userUid;

    public String getUserUid() {
        return userUid;
    }

    public void setUserUid(String userUid) {
        this.userUid = userUid;
    }
}
