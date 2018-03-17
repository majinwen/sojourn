/**
 * @FileName: SysConfig.java
 * @Package com.asura.management.sysconfig.entity
 * 
 * @author zhangshaobin
 * @created 2013-1-16 下午5:20:41
 * 
 * Copyright 2011-2015 ausra
 */
package com.asura.amp.sysconfig.entity;

import java.sql.Timestamp;

import com.asura.framework.base.entity.BaseEntity;

/**
 * <p>系统配置项Entity</p>
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
public class SysConfig extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3239097820978411484L;

	private Integer id;

	/**
	 * 类型
	 */
	private String type;

	/**
	 * CODE
	 */
	private String code;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 值
	 */
	private String value;

	/**
	 * 描述
	 */
	private String notes;

	/**
	 * zk对应值
	 */
	private String zkValue;

	private int isDelete;

	private Timestamp updateDate;

	/**
	 * @return 获得zk值
	 */
	public String getZkValue() {
		return zkValue;
	}

	/**
	 * @param 设置zk值
	 */
	public void setZkValue(String zkValue) {
		this.zkValue = zkValue;
	}

	/**
	 * 获取类型值
	 *
	 * @author zhangshaobin
	 * @created 2013-4-22 下午3:47:25
	 *
	 * @return
	 */
	public String getType() {
		return type;
	}

	/**
	 * 设置类型值
	 *
	 * @author zhangshaobin
	 * @created 2013-4-22 下午3:48:43
	 *
	 * @param type
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * 获取CODE值
	 *
	 * @author zhangshaobin
	 * @created 2013-4-22 下午3:47:39
	 *
	 * @return
	 */
	public String getCode() {
		return code;
	}

	/**
	 * 设置CODE值
	 *
	 * @author zhangshaobin
	 * @created 2013-4-22 下午3:48:56
	 *
	 * @param code
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * 获取名称值
	 *
	 * @author zhangshaobin
	 * @created 2013-4-22 下午3:47:50
	 *
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置名称值
	 *
	 * @author zhangshaobin
	 * @created 2013-4-22 下午3:49:09
	 *
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 获取配置项值
	 *
	 * @author zhangshaobin
	 * @created 2013-4-22 下午3:48:00
	 *
	 * @return
	 */
	public String getValue() {
		return value;
	}

	/**
	 * 设置配置项值
	 *
	 * @author zhangshaobin
	 * @created 2013-4-22 下午3:49:26
	 *
	 * @param value
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * @return the isDelete
	 */
	public int getIsDelete() {
		return isDelete;
	}

	/**
	 * @param isDelete the isDelete to set
	 */
	public void setIsDelete(int isDelete) {
		this.isDelete = isDelete;
	}

	/**
	 * @return the updateDate
	 */
	public Timestamp getUpdateDate() {
		return updateDate;
	}

	/**
	 * @param updateDate the updateDate to set
	 */
	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
	}

	/**
	 * @return the notes
	 */
	public String getNotes() {
		return notes;
	}

	/**
	 * @param notes the notes to set
	 */
	public void setNotes(String notes) {
		this.notes = notes;
	}

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

}
