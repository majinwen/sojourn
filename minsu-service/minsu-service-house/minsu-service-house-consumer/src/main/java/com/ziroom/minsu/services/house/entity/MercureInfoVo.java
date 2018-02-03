package com.ziroom.minsu.services.house.entity;

import java.io.Serializable;
import java.util.LinkedList;


/**
 * 
 * <p>房源详情-美居介绍信息</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author yd
 * @since 1.0
 * @version 1.0
 */
public class MercureInfoVo implements Serializable{

	/**
	 * 序列id
	 */
	private static final long serialVersionUID = 1475586713634101440L;

	/**
	 * 美居介绍 标题
	 */
	private String mercureTitle;

	/**
	 * 描述标题
	 */
	private String mercureDesTitle;

	/**
	 * 房源描述——展示房源信息
	 */
	private String mercureDesHouseInfo;

	/**
	 * 房源描述
	 */
	private String houseDesc;

	/**
	 * 房源周边情况标题
	 */
	private  String houseAroundTitle;

	/**
	 * 房源周边信息
	 */
	private String houseAroundDesc;

	/**
	 * @return the mercureTitle
	 */
	public String getMercureTitle() {
		return mercureTitle;
	}

	/**
	 * @param mercureTitle the mercureTitle to set
	 */
	public void setMercureTitle(String mercureTitle) {
		this.mercureTitle = mercureTitle;
	}

	/**
	 * @return the mercureDesTitle
	 */
	public String getMercureDesTitle() {
		return mercureDesTitle;
	}

	/**
	 * @param mercureDesTitle the mercureDesTitle to set
	 */
	public void setMercureDesTitle(String mercureDesTitle) {
		this.mercureDesTitle = mercureDesTitle;
	}


	/**
	 * @return the mercureDesHouseInfo
	 */
	public String getMercureDesHouseInfo() {
		return mercureDesHouseInfo;
	}

	/**
	 * @param mercureDesHouseInfo the mercureDesHouseInfo to set
	 */
	public void setMercureDesHouseInfo(String mercureDesHouseInfo) {
		this.mercureDesHouseInfo = mercureDesHouseInfo;
	}

	/**
	 * @return the houseDesc
	 */
	public String getHouseDesc() {
		return houseDesc;
	}

	/**
	 * @param houseDesc the houseDesc to set
	 */
	public void setHouseDesc(String houseDesc) {
		this.houseDesc = houseDesc;
	}

	/**
	 * @return the houseAroundTitle
	 */
	public String getHouseAroundTitle() {
		return houseAroundTitle;
	}

	/**
	 * @param houseAroundTitle the houseAroundTitle to set
	 */
	public void setHouseAroundTitle(String houseAroundTitle) {
		this.houseAroundTitle = houseAroundTitle;
	}

	/**
	 * @return the houseAroundDesc
	 */
	public String getHouseAroundDesc() {
		return houseAroundDesc;
	}

	/**
	 * @param houseAroundDesc the houseAroundDesc to set
	 */
	public void setHouseAroundDesc(String houseAroundDesc) {
		this.houseAroundDesc = houseAroundDesc;
	}
	
	
}
