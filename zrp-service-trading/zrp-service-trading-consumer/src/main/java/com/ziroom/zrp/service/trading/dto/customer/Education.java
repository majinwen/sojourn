package com.ziroom.zrp.service.trading.dto.customer;

import java.io.Serializable;
/**
 * <p>查询友家个人信息</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author xiangb
 * @version 1.0
 * @Date Created in 2017年9月21日
 * @since 1.0
 */
public class Education implements Serializable{
	
	private static final long serialVersionUID = -8282594485045109679L;
	/**
	 * 毕业年份
	 */
	private String graduation_year;	
	/**
	 * 入学时间
	 */
	private String enrollment_year;
 	/**
	 * 教育类型
	 */
	private int education_type;
 	/**
	 * 学历
	 */
 	private int education;
 	/**
	 * 学校
	 */
 	private String school;
	public String getGraduation_year() {
		return graduation_year;
	}
	public void setGraduation_year(String graduation_year) {
		this.graduation_year = graduation_year;
	}
	public String getEnrollment_year() {
		return enrollment_year;
	}
	public void setEnrollment_year(String enrollment_year) {
		this.enrollment_year = enrollment_year;
	}
	public int getEducation_type() {
		return education_type;
	}
	public void setEducation_type(int education_type) {
		this.education_type = education_type;
	}
	public int getEducation() {
		return education;
	}
	public void setEducation(int education) {
		this.education = education;
	}
	public String getSchool() {
		return school;
	}
	public void setSchool(String school) {
		this.school = school;
	}
}
