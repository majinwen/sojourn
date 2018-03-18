package com.zra.house.entity.dto;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * 首页搜索结果
 * <p>
 * Author: wangxm113
 * CreateDate: 2016/7/29.
 */
public class ProjectListDto {
    @ApiModelProperty(value = "是否有搜索结果,0-没有;1-有")
    private String ifSearchSuc;
    @ApiModelProperty(value = "项目筛选和房型筛选，根据筛选条件没有查到内容时显示的文案")
    private String searchFailMsg;
    @ApiModelProperty(value = "符合条件的项目数")
    private Integer projectAcc;
    @ApiModelProperty(value = "符合条件的户型数")
    private Integer htAcc;
    @ApiModelProperty(value = "项目列表")
    private List<ProjectAndHouseDto> projectList;

    public String getSearchFailMsg() {
        return searchFailMsg;
    }

    public void setSearchFailMsg(String searchFailMsg) {
        this.searchFailMsg = searchFailMsg;
    }

    public String getIfSearchSuc() {
        return ifSearchSuc;
    }

    public void setIfSearchSuc(String ifSearchSuc) {
        this.ifSearchSuc = ifSearchSuc;
    }

    public Integer getProjectAcc() {
        return projectAcc;
    }

    public void setProjectAcc(Integer projectAcc) {
        this.projectAcc = projectAcc;
    }

    public Integer getHtAcc() {
        return htAcc;
    }

    public void setHtAcc(Integer htAcc) {
        this.htAcc = htAcc;
    }

    public List<ProjectAndHouseDto> getProjectList() {
        return projectList;
    }

    public void setProjectList(List<ProjectAndHouseDto> projectList) {
        this.projectList = projectList;
    }
}
