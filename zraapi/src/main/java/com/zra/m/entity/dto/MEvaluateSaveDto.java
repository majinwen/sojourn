package com.zra.m.entity.dto;

import javax.ws.rs.QueryParam;
import java.util.List;

/**
 * Author: wangxm113
 * CreateDate: 2017/3/14.
 */
public class MEvaluateSaveDto {
    @QueryParam("passport_token")
    private String token;
    @QueryParam("proId")
    private String proId;
    @QueryParam("uuid")
    private String uuid;
    @QueryParam("zoId")
    private String zoId;
    @QueryParam("positiveList")
    private List<String> positiveList;
    @QueryParam("negativeList")
    private List<String> negativeList;
    @QueryParam("content")
    private String content;

    public String getProId() {
        return proId;
    }

    public void setProId(String proId) {
        this.proId = proId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getZoId() {
        return zoId;
    }

    public void setZoId(String zoId) {
        this.zoId = zoId;
    }

    public List<String> getPositiveList() {
        return positiveList;
    }

    public void setPositiveList(List<String> positiveList) {
        this.positiveList = positiveList;
    }

    public List<String> getNegativeList() {
        return negativeList;
    }

    public void setNegativeList(List<String> negativeList) {
        this.negativeList = negativeList;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
