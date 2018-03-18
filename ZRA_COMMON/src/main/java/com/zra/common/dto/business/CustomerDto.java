package com.zra.common.dto.business;

import java.util.Date;

/**
 * 商机客户dto
 * @author wangws21 2016-8-2
 */
public class CustomerDto {
    /*客户业务id*/
    private String cusBid;
    /*客户库uuid*/
    private String cusUuid;
    /*城市id*/
    private String cityId;
    /*姓名*/
    private String name;
    /*手机号*/
    private String phone;
    /*性别  1女  2男*/
    private Byte gender;
    /*年龄*/
    private Integer age;
    /*工作单位*/
    private String company;
    /*工作地址*/
    private String address;

    public String getCusBid() {
        return cusBid;
    }

    public void setCusBid(String cusBid) {
        this.cusBid = cusBid == null ? null : cusBid.trim();
    }

    public String getCusUuid() {
        return cusUuid;
    }

    public void setCusUuid(String cusUuid) {
        this.cusUuid = cusUuid == null ? null : cusUuid.trim();
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId == null ? null : cityId.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public Byte getGender() {
        return gender;
    }

    public void setGender(Byte gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company == null ? null : company.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }
}