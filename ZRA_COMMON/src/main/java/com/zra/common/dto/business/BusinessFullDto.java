package com.zra.common.dto.business;

/**
 * 商机和处理结果实体
 *      包含商机 客户 处理结果信息
 * Created by wangws21 on 2016/8/1.
 */
public class BusinessFullDto {

    private String userId;

    private String cityId;

    /*商机实体*/
    private BusinessDto business;

    /*客户信息*/
    private CustomerDto customer;

    /*处理结果信息*/
    private BusinessResultDto businessResult;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public BusinessDto getBusiness() {
        return business;
    }

    public void setBusiness(BusinessDto business) {
        this.business = business;
    }

    public CustomerDto getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerDto customer) {
        this.customer = customer;
    }

    public BusinessResultDto getBusinessResult() {
        return businessResult;
    }

    public void setBusinessResult(BusinessResultDto businessResult) {
        this.businessResult = businessResult;
    }
}
