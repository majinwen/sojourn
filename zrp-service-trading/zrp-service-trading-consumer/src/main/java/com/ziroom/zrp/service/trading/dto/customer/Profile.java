package com.ziroom.zrp.service.trading.dto.customer;

import java.io.Serializable;
/**
 * <p>对接友家个人基本信息</p>
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
public class Profile implements Serializable{
	
	private static final long serialVersionUID = -1541133106582668985L;
	
	/**
	 * 昵称
	 */
	private String nick_name;
	/**
	 * 原昵称字段
	 */
	private String user_name;
	/**
	 * 性别，女1男2
	 */
	private int gender;
	/**
	 * 生日
	 */
	private String birth;
	/**
	 * 民族
	 */
	private String nation;
	/**
	 * 兴趣，例 ["\u4f53\u80b2","\u5531\u6b4c"]
	 */
	private String[] interest;
	/**
	 * 外网可访问的url
	 */
	private String head_img;
	/**
	 * 国籍
	 */
	private String nationality;
	/**
	 * 籍贯
	 */
	private String location;
	/**
	 * 1未婚,2结婚,3离异
	 */
	private int marriage;
	/**
	 * 工作
	 */
	private String job;
	/**
	 * 专业
	 */
	private String professional;
	/**
	 * 职位
	 */
	private String position;
	/**
	 * 客户来源:0其他,1自如网,2店面接待,3社区开发,4,400,5热线,6自如客户推荐,7朋友介绍,8大客户渠道
	 */
	private int origin;
	/**
	 * 公司
	 */
	private String company;
	
	private String city;
	
	private String business;
	
	/**
	 * 手机号
	 */
	private String phone;
	
	public String getNick_name() {
		return nick_name;
	}
	public void setNick_name(String nick_name) {
		this.nick_name = nick_name;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public int getGender() {
		return gender;
	}
	public void setGender(int gender) {
		this.gender = gender;
	}
	public String getBirth() {
		return birth;
	}
	public void setBirth(String birth) {
		this.birth = birth;
	}
	public String getNation() {
		return nation;
	}
	public void setNation(String nation) {
		this.nation = nation;
	}
	public String[] getInterest() {
		return interest;
	}
	public void setInterest(String[] interest) {
		this.interest = interest;
	}
	public String getHead_img() {
		return head_img;
	}
	public void setHead_img(String head_img) {
		this.head_img = head_img;
	}
	public String getNationality() {
		return nationality;
	}
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public int getMarriage() {
		return marriage;
	}
	public void setMarriage(int marriage) {
		this.marriage = marriage;
	}
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	public String getProfessional() {
		return professional;
	}
	public void setProfessional(String professional) {
		this.professional = professional;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public int getOrigin() {
		return origin;
	}
	public void setOrigin(int origin) {
		this.origin = origin;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getBusiness() {
		return business;
	}
	public void setBusiness(String business) {
		this.business = business;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
}
