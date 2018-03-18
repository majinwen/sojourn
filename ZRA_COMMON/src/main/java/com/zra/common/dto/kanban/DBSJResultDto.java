package com.zra.common.dto.kanban;

/**
 * Author: wangxm113
 * CreateDate: 2016/12/23.
 */
public class DBSJResultDto {
    private String customerName;
    private String phone;
    private String dkDate;
    private String busiId;

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDkDate() {
        return dkDate;
    }

    public void setDkDate(String dkDate) {
        this.dkDate = dkDate;
    }

    public String getBusiId() {
        return busiId;
    }

    public void setBusiId(String busiId) {
        this.busiId = busiId;
    }
}
