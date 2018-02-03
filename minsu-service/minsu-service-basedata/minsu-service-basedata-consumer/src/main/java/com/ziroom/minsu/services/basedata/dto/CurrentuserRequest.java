/**
 * @FileName: CurrentuserRequest.java
 * @Package com.ziroom.minsu.services.basedata.logic
 * 
 * @author bushujie
 * @created 2016年3月9日 上午10:02:43
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.basedata.dto;


import java.util.List;

import com.ziroom.minsu.services.common.dto.PageRequest;

/**
 * <p>后台用户查询参数</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期	2016/08/02	修改人		赵龙	  修改内容	添加 roleName查询条件
 * </PRE>
 * 
 * @author bushujie
 * @since 1.0
 * @version 1.0
 */
public class CurrentuserRequest extends PageRequest{
	/**
	 * 序列id
	 */
	private static final long serialVersionUID = 5791071836355521283L;
	/**
	 * 账户名称查询
	 */
	private String userName;
	/**
	 * 员工名称查询
	 */
	private String fullName;
	/**
	 * 状态查询
	 */
	private Integer accountStatus;
    /**
     * 角色名称查询
     */
    private String roleName;
    
    /**
     * 用户手机号
     */
    private String empMobile;
    
    /**
     * 员工fids
     */
    private List<String> employeeFids;
    
    
    
	/**
	 * @return the employeeFids
	 */
	public List<String> getEmployeeFids() {
		return employeeFids;
	}
	/**
	 * @param employeeFids the employeeFids to set
	 */
	public void setEmployeeFids(List<String> employeeFids) {
		this.employeeFids = employeeFids;
	}
	/**
	 * @return the empMobile
	 */
	public String getEmpMobile() {
		return empMobile;
	}
	/**
	 * @param empMobile the empMobile to set
	 */
	public void setEmpMobile(String empMobile) {
		this.empMobile = empMobile;
	}
	/**
	 * @return the roleName
	 */
	public String getRoleName() {
		return roleName;
	}
	/**
	 * 
	 * @param roleName the roleName to set
	 */
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * @return the fullName
	 */
	public String getFullName() {
		return fullName;
	}
	/**
	 * @param fullName the fullName to set
	 */
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	/**
	 * @return the accountStatus
	 */
	public Integer getAccountStatus() {
		return accountStatus;
	}
	/**
	 * @param accountStatus the accountStatus to set
	 */
	public void setAccountStatus(Integer accountStatus) {
		this.accountStatus = accountStatus;
	}
}
