package com.zra.common.dto.kanban;

/**
 * Author: wangxm113
 * CreateDate: 2016/12/23.
 */
public class DBSJQueryDto {
    private String projectId;
    private String zoId;
    private Integer type;//0：待约看；1：待带看；2：待回访

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getZoId() {
        return zoId;
    }

    public void setZoId(String zoId) {
        this.zoId = zoId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
