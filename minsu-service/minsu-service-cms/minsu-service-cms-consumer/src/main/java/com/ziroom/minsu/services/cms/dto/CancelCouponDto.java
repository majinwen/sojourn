package com.ziroom.minsu.services.cms.dto;

import com.asura.framework.base.entity.BaseEntity;

/**
 * <p></p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author yanb
 * @version 1.0
 * @Date Created in 2017年11月06日 11:38
 * @since 1.0
 */
public class CancelCouponDto extends BaseEntity {
    /**
     * 序列化ID
     */
    private static final long serialVersionUID = -8817684898430568587L;

    /**
     * 优惠券号
     */
    private String couponSn;

    /**
     * 备注
     */
    private String remark;

    /**
     * 操作人ID
     */
    private String empCode;

    /**
     * 操作人姓名
     */
    private String empName;

    public String getCouponSn() {
        return couponSn;
    }

    public void setCouponSn(String couponSn) {
        this.couponSn = couponSn;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getEmpCode() {
        return empCode;
    }

    public void setEmpCode(String empCode) {
        this.empCode = empCode;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }
}
