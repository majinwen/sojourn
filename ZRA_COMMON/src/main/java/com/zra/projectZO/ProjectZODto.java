package com.zra.projectZO;

/**
 * 管家Dto
 *
 * @author tianxf9
 */
public class ProjectZODto {

    //管家所属项目id
    private String projectId;
    //管家名字
    private String projectZOName;
    //管家id
    private String projectZOId;
    
    //管家电话
    private String zrojectZoPhone;
    //管家员工编号
    private String projectZOCode;

    public String getProjectZOName() {
        return projectZOName;
    }

    public void setProjectZOName(String projectZOName) {
        this.projectZOName = projectZOName;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getProjectZOId() {
        return projectZOId;
    }

    public void setProjectZOId(String projectZOId) {
        this.projectZOId = projectZOId;
    }

    public String getZrojectZoPhone() {
        return zrojectZoPhone;
    }

    public void setZrojectZoPhone(String zrojectZoPhone) {
        this.zrojectZoPhone = zrojectZoPhone;
    }

	public String getProjectZOCode() {
		return projectZOCode;
	}

	public void setProjectZOCode(String projectZOCode) {
		this.projectZOCode = projectZOCode;
	}
    
    
}
