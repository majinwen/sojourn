package com.ziroom.minsu.services.cms.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>自如寓 活动请求类</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd
 * @version 1.0
 * @Date Created in 2017年10月16日 14:18
 * @since 1.0
 */
public class ZrpActRequest implements Serializable {

    private static final long serialVersionUID = -8483752900129288846L;
    /**
     * 客户类型
     */
    private Integer customerType;
    /**
     * 签约类型
     */
    private Integer signType;
    /**
     * 用户uid
     */
    private String uid;
    /**
     * 项目Id
     */
    private String projectId;
    /**
     * 户型id
     */
    private String layoutId;
    /**
     * 房间id
     */
    private String roomId;
    /**
     * 出租天数
     */
    private Integer rentDay;
    /**
     * 原服务费
     */
    private BigDecimal originCommonFee;
    /**
     * 服务费
     */
    private BigDecimal commonFee;

    public Integer getCustomerType() {
        return customerType;
    }

    public void setCustomerType(Integer customerType) {
        this.customerType = customerType;
    }

    public Integer getSignType() {
        return signType;
    }

    public void setSignType(Integer signType) {
        this.signType = signType;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getLayoutId() {
        return layoutId;
    }

    public void setLayoutId(String layoutId) {
        this.layoutId = layoutId;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public BigDecimal getCommonFee() {
        return commonFee;
    }

    public void setCommonFee(BigDecimal commonFee) {
        this.commonFee = commonFee;
    }

    public Integer getRentDay() {
        return rentDay;
    }

    public void setRentDay(Integer rentDay) {
        this.rentDay = rentDay;
    }

    public BigDecimal getOriginCommonFee() {
        return originCommonFee;
    }

    public void setOriginCommonFee(BigDecimal originCommonFee) {
        this.originCommonFee = originCommonFee;
    }
}
