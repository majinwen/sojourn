package com.asura.amp.authority.entity;

import java.util.List;

import com.asura.framework.base.entity.BaseEntity;

/**
 * <p>
 * 操作员信息
 * </p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author zhangshaobin
 * @since 1.0
 * @version 1.0
 */
public class Operator extends BaseEntity {
	/*
	 * 序列号 
	 */
	private static final long serialVersionUID = -9122884053467285265L;
	/*
	 * 操作员id 自增
	 */
	private int operatorId;
	/*
	 * 操作员名字
	 */
	private String operatorName;
	/*
	 * 登录账号
	 */
	private String loginName;
	/*
	 * 登录密码
	 */
	private String loginPwd;
	/*
	 * 工号
	 */
	private String jobNum;
	/*
	 * 电话
	 */
	private String telephone;
	/*
	 * 操作员状态 0:失效 1:正常 2:删除
	 */
	private int operatorStatus;
	/*
	 * 操作员邮箱
	 */
	private String operatorEmail;
	/*
	 * 操作员类型 1：操作员 2：系统管理员
	 */
	private int operatorType;
	/*
	 * 操作员所属部门 1:采购部 3:财务部 4:运营部 5:技术部 6:法务部(项目中暂时用不到)
	 */
	private int operatorDepartment;

	/*
	 * 用户所拥有的角色
	 */
	private List<Role> roles;

	/**
	 * @return the roles
	 */
	public List<Role> getRoles() {
		return roles;
	}

	/**
	 * @param roles
	 *            the roles to set
	 */
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	/**
	 * @return the operatorId
	 */
	public int getOperatorId() {
		return operatorId;
	}

	/**
	 * @param operatorId
	 *            the operatorId to set
	 */
	public void setOperatorId(int operatorId) {
		this.operatorId = operatorId;
	}

	/**
	 * @return the operatorName
	 */
	public String getOperatorName() {
		return operatorName;
	}

	/**
	 * @param operatorName
	 *            the operatorName to set
	 */
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	/**
	 * @return the loginName
	 */
	public String getLoginName() {
		return loginName;
	}

	/**
	 * @param loginName
	 *            the loginName to set
	 */
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	/**
	 * @return the loginPwd
	 */
	public String getLoginPwd() {
		return loginPwd;
	}

	/**
	 * @param loginPwd
	 *            the loginPwd to set
	 */
	public void setLoginPwd(String loginPwd) {
		this.loginPwd = loginPwd;
	}

	/**
	 * @return the jobNum
	 */
	public String getJobNum() {
		return jobNum;
	}

	/**
	 * @param jobNum
	 *            the jobNum to set
	 */
	public void setJobNum(String jobNum) {
		this.jobNum = jobNum;
	}

	/**
	 * @return the telephone
	 */
	public String getTelephone() {
		return telephone;
	}

	/**
	 * @param telephone
	 *            the telephone to set
	 */
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	/**
	 * @return the operatorStatus
	 */
	public int getOperatorStatus() {
		return operatorStatus;
	}

	/**
	 * @param operatorStatus
	 *            the operatorStatus to set
	 */
	public void setOperatorStatus(int operatorStatus) {
		this.operatorStatus = operatorStatus;
	}

	/**
	 * @return the operatorEmail
	 */
	public String getOperatorEmail() {
		return operatorEmail;
	}

	/**
	 * @param operatorEmail
	 *            the operatorEmail to set
	 */
	public void setOperatorEmail(String operatorEmail) {
		this.operatorEmail = operatorEmail;
	}

	/**
	 * @return the operatorType
	 */
	public int getOperatorType() {
		return operatorType;
	}

	/**
	 * @param operatorType
	 *            the operatorType to set
	 */
	public void setOperatorType(int operatorType) {
		this.operatorType = operatorType;
	}

	/**
	 * @return the operatorDepartment
	 */
	public int getOperatorDepartment() {
		return operatorDepartment;
	}

	/**
	 * @param operatorDepartment
	 *            the operatorDepartment to set
	 */
	public void setOperatorDepartment(int operatorDepartment) {
		this.operatorDepartment = operatorDepartment;
	}

}
