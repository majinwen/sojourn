package com.zra.common.vo.perseon;

import com.zra.common.vo.contract.ProjectInfoVo;

public class SignSubjectVo{
	
	/**
 	 * 合同标识
 	 */
    private String contractId;
    /**
 	 * 合同码
 	 */
    private String contractCode;
    /**
     * 签约ZO手机号
     */
    private String handleZOPhone;
    /**
     * 项目基本信息
     */
    private ProjectInfoVo projectInfo;
    /**
     * 个人签约时显示个人信息
     */
	private SignPersonInfoVo signPerson;
	
	public String getContractId() {
		return contractId;
	}
	public void setContractId(String contractId) {
		this.contractId = contractId;
	}
	public String getHandleZOPhone() {
		return handleZOPhone;
	}
	public void setHandleZOPhone(String handleZOPhone) {
		this.handleZOPhone = handleZOPhone;
	}
	public ProjectInfoVo getProjectInfo() {
		return projectInfo;
	}
	public void setProjectInfo(ProjectInfoVo projectInfo) {
		this.projectInfo = projectInfo;
	}
	public SignPersonInfoVo getSignPerson() {
		return signPerson;
	}
	public void setSignPerson(SignPersonInfoVo signPerson) {
		this.signPerson = signPerson;
	}
	public String getContractCode() {
		return contractCode;
	}
	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}
	
}
