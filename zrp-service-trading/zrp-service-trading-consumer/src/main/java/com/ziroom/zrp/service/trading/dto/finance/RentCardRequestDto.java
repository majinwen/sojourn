package com.ziroom.zrp.service.trading.dto.finance;

import java.io.Serializable;

/**
 * <p>租金卡查询入参</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author lusp
 * @version 1.0
 * @Date Created in 2017年11月27日 10:22
 * @since 1.0
 */

public class RentCardRequestDto extends CmsCommonRequestDto implements Serializable{

    private static final long serialVersionUID = -9180931875588441195L;

    /**
     * 客户uid
     */
    private String uid;

    /**
     * 卡券类型ID
     */
    private String base_id;

    /**
     * 券类型ID
     */
    private String coupon_main_id;

    /**
     * 券状态
     */
    private Integer coupon_status;

    /**
     * 券类型
     */
    private Integer coupon_type;

    /**
     * 	业务线id
     */
    private Integer service_line_id;

    /**
     * 页数
     */
    private Integer page;

    /**
     * 每页个数
     */
    private Integer pageNum;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getBase_id() {
        return base_id;
    }

    public void setBase_id(String base_id) {
        this.base_id = base_id;
    }

    public String getCoupon_main_id() {
        return coupon_main_id;
    }

    public void setCoupon_main_id(String coupon_main_id) {
        this.coupon_main_id = coupon_main_id;
    }

    public Integer getCoupon_status() {
        return coupon_status;
    }

    public void setCoupon_status(Integer coupon_status) {
        this.coupon_status = coupon_status;
    }

    public Integer getCoupon_type() {
        return coupon_type;
    }

    public void setCoupon_type(Integer coupon_type) {
        this.coupon_type = coupon_type;
    }

    public Integer getService_line_id() {
        return service_line_id;
    }

    public void setService_line_id(Integer service_line_id) {
        this.service_line_id = service_line_id;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }
}
