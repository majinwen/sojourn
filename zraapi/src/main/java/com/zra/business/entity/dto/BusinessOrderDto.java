package com.zra.business.entity.dto;

import com.zra.common.dto.appbase.AppBaseDto;

import javax.ws.rs.QueryParam;

/**
 * Author: wangxm113
 * CreateDate: 2016/8/2.
 */
public class BusinessOrderDto extends AppBaseDto {
    @QueryParam("uuid")
    private String uuid;
    @QueryParam("name")
    private String name;
    @QueryParam("phone")
    private String phone;
    @QueryParam("company")
    private String company;
    @QueryParam("expectTime")
    private String expectTime;
    @QueryParam("expectInStartTime")
    private String expectInStartTime;
    @QueryParam("message")
    private String message;
    @QueryParam("projectId")
    private String projectId;
    @QueryParam("htId")
    private String htId;
    @QueryParam("nationality")
    private String nationality;
    @QueryParam("sourceOfBu")
    private Byte sourceOfBu;
    @QueryParam("comeSource")
    private String comeSource;  //add by xiaona 2016年11月1日  表示M站的渠道来源记录
    @QueryParam("passport_token")
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Byte getSourceOfBu() {
        return sourceOfBu;
    }

    public void setSourceOfBu(Byte sourceOfBu) {
        this.sourceOfBu = sourceOfBu;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
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

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getExpectTime() {
        return expectTime;
    }

    public void setExpectTime(String expectTime) {
        this.expectTime = expectTime;
    }

    public String getExpectInStartTime() {
        return expectInStartTime;
    }

    public void setExpectInStartTime(String expectInStartTime) {
        this.expectInStartTime = expectInStartTime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getHtId() {
        return htId;
    }

    public void setHtId(String htId) {
        this.htId = htId;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

//	public String getSourceZra() {
//		return sourceZra;
//	}
//
//	public void setSourceZra(String sourceZra) {
//		this.sourceZra = sourceZra;
//	}

    public String getComeSource() {
        return comeSource;
    }

    public void setComeSource(String comeSource) {
        this.comeSource = comeSource;
    }
}
