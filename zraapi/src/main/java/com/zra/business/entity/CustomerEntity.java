package com.zra.business.entity;

import java.util.Date;

/**
 * Author: wangxm113
 * CreateDate: 2016/8/3.
 */
public class CustomerEntity {
    private Integer id;
    private String cusBid;
    private String cusUuid;
    private String createrId;
    private Date createTime;
    private String updaterId;
    private Date updateTime;
    private Byte isDel;
    private String cityId;
    private String name;
    private String phone;
    private Byte gender;
    private Integer age;
    private String company;
    private String address;
    private String nationality;
    
    /**
     * wangws21 2017-1-16 添加客源量字段.<br>
     * 创建商机时，判断30天内有没有同项目同手机号的商机，没有的话客源量标示为1，有的话标示为0，在查询时间段内，筛选出所有标记为1的商机量即为客源量
     */
    private Integer kylFlag; 

    public CustomerEntity() {
	}

	public CustomerEntity(String cusBid, String updaterId, Date updateTime, String name, String phone, Byte gender,
			Integer age, String company, String address) {
		this.cusBid = cusBid;
		this.updaterId = updaterId;
		this.updateTime = updateTime;
		this.name = name;
		this.phone = phone;
		this.gender = gender;
		this.age = age;
		this.company = company;
		this.address = address;
	}

	public CustomerEntity(String cusBid, String createrId, Date createTime,
			String updaterId, Date updateTime, String cityId, String name, String phone, Byte gender,
			Integer age, String company, String address) {
		this.cusBid = cusBid;
		this.createrId = createrId;
		this.createTime = createTime;
		this.updaterId = updaterId;
		this.updateTime = updateTime;
		this.cityId = cityId;
		this.name = name;
		this.phone = phone;
		this.gender = gender;
		this.age = age;
		this.company = company;
		this.address = address;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCusBid() {
        return cusBid;
    }

    public void setCusBid(String cusBid) {
        this.cusBid = cusBid;
    }

    public String getCusUuid() {
        return cusUuid;
    }

    public void setCusUuid(String cusUuid) {
        this.cusUuid = cusUuid;
    }

    public String getCreaterId() {
        return createrId;
    }

    public void setCreaterId(String createrId) {
        this.createrId = createrId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdaterId() {
        return updaterId;
    }

    public void setUpdaterId(String updaterId) {
        this.updaterId = updaterId;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Byte getIsDel() {
        return isDel;
    }

    public void setIsDel(Byte isDel) {
        this.isDel = isDel;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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
        this.company = company;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

    public Integer getKylFlag() {
        return kylFlag;
    }

    public void setKylFlag(Integer kylFlag) {
        this.kylFlag = kylFlag;
    }
}
