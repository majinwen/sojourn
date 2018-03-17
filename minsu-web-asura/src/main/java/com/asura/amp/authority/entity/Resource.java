package com.asura.amp.authority.entity;

import com.asura.framework.base.entity.BaseEntity;

/**
 * <p>
 * 资源信息
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
public class Resource extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5075518994356826896L;
	/*
	 * 自增id
	 */
	private Integer resId;
	/*
	 * 资源名称
	 */
	private String resName;
	/*
	 * 父编号
	 */
	private Integer parentId;
	/*
	 * 资源级数
	 */
	private Integer resLevel;
	/*
	 * 资源访问地址
	 */
	private String resUrl;
	/*
	 * 资源访问地址携带参数, 暂时不用
	 */
	private String resUrlParam;
	/*
	 * 资源类型, 01:菜单 02:功能
	 */
	private String resType;
	/*
	 * 资源显示序号
	 */
	private Integer resOrder;
	/*
	 * 资源功能类型, 01:查询 02:增加 03:修改 04:删除, 暂时不用
	 */
	private String resFunctionType;
	/*
	 * 资源描述
	 */
	private String resDesc;
	/*
	 * 是否是管理员默认菜单
	 */
	private Integer isAdminMenu ;
	/*
	 * 是否是叶子
	 */
	private boolean leaf;

	/**
	 * @return the resName
	 */
	public String getResName() {
		return resName;
	}

	/**
	 * @param resName
	 *            the resName to set
	 */
	public void setResName(String resName) {
		this.resName = resName;
	}

	/**
	 * @return the resUrl
	 */
	public String getResUrl() {
		return resUrl;
	}

	/**
	 * @param resUrl
	 *            the resUrl to set
	 */
	public void setResUrl(String resUrl) {
		this.resUrl = resUrl;
	}

	/**
	 * @return the resUrlParam
	 */
	public String getResUrlParam() {
		return resUrlParam;
	}

	/**
	 * @param resUrlParam
	 *            the resUrlParam to set
	 */
	public void setResUrlParam(String resUrlParam) {
		this.resUrlParam = resUrlParam;
	}

	/**
	 * @return the resType
	 */
	public String getResType() {
		return resType;
	}

	/**
	 * @param resType
	 *            the resType to set
	 */
	public void setResType(String resType) {
		this.resType = resType;
	}

	/**
	 * @return the resOrder
	 */
	public Integer getResOrder() {
		return resOrder;
	}

	/**
	 * @param resOrder
	 *            the resOrder to set
	 */
	public void setResOrder(Integer resOrder) {
		this.resOrder = resOrder;
	}

	/**
	 * @return the resFunctionType
	 */
	public String getResFunctionType() {
		return resFunctionType;
	}

	/**
	 * @param resFunctionType
	 *            the resFunctionType to set
	 */
	public void setResFunctionType(String resFunctionType) {
		this.resFunctionType = resFunctionType;
	}

	/**
	 * @return the resDesc
	 */
	public String getResDesc() {
		return resDesc;
	}

	/**
	 * @param resDesc
	 *            the resDesc to set
	 */
	public void setResDesc(String resDesc) {
		this.resDesc = resDesc;
	}

	/**
	 * @return the isAdminMenu
	 */
	public Integer getIsAdminMenu() {
		return isAdminMenu;
	}

	/**
	 * @param isAdminMenu
	 *            the isAdminMenu to set
	 */
	public void setIsAdminMenu(Integer isAdminMenu) {
		this.isAdminMenu = isAdminMenu;
	}

	/**
	 * @return the resId
	 */
	public Integer getResId() {
		return resId;
	}

	/**
	 * @param resId
	 *            the resId to set
	 */
	public void setResId(Integer resId) {
		this.resId = resId;
	}

	/**
	 * @return the parentId
	 */
	public Integer getParentId() {
		return parentId;
	}

	/**
	 * @param parentId the parentId to set
	 */
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	/**
	 * @return the resLevel
	 */
	public Integer getResLevel() {
		return resLevel;
	}

	/**
	 * @param resLevel the resLevel to set
	 */
	public void setResLevel(Integer resLevel) {
		this.resLevel = resLevel;
	}

	/**
	 * @return the leaf
	 */
	public boolean isLeaf() {
		return leaf;
	}

	/**
	 * @param leaf the leaf to set
	 */
	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}

	

}
