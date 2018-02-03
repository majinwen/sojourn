package com.ziroom.minsu.services.cms.dto;

import com.ziroom.minsu.services.common.dto.PageRequest;

/**
 * <p>
 * 对外提供的优惠券查询请求参数
 * </p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author afi
 * @since 1.0
 * @version 1.0
 */
public class OutCouponRequest extends PageRequest {

	/**
	 * 序列id
	 */
	private static final long serialVersionUID = 674418242340830260L;


    /** 优惠券状态1-可用；2-过期；3-全部 */
    private String status;

    private String uid;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
