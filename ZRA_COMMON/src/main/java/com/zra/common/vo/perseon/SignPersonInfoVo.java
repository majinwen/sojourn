package com.zra.common.vo.perseon;

import com.zra.common.vo.base.BasePersonVo;

/**
 * <p>签约人信息（包括个人和代理人信息）</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author xiangb
 * @version 1.0
 * @Date Created in 2017年9月14日
 * @since 1.0
 */
public class SignPersonInfoVo extends BasePersonVo{
	/**
	 * 签约人性别
	 */
	private String sex;
	/**
	 * 签约人手机号
	 */
	private String phone;
	
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	
	
	
	

}
