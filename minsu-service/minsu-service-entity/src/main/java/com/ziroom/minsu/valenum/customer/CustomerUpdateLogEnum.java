/**
 * @FileName: CustomerUpdateLogEnum.java
 * @Package com.ziroom.minsu.valenum.customer
 * 
 * @author loushuai
 * @created 2017年8月4日 下午4:19:21
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.valenum.customer;

import com.asura.framework.base.util.Check;
import com.ziroom.minsu.valenum.house.HouseUpdateLogEnum;

/**
 * <p>房东修改 记录信息
 * 说明: 所有需要 记录的字段 需要在此 添加  否则 不保存入库
 * </p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author loushuai
 * @since 1.0
 * @version 1.0
 */
public enum CustomerUpdateLogEnum {

	//客户扩展描述信息
	Customer_Pic_Msg_Pic_Base_Url("Customer_Pic_Msg","picBaseUrl","图片访问基础url",0,"com.ziroom.minsu.entity.customer.CustomerPicMsgEntity.picBaseUrl"),
	
	//客户扩展描述信息
	Customer_Base_Msg_Ext_Introduce("Customer_Base_Msg_Ext","customerIntroduce","用户的自我介绍",1,"com.ziroom.minsu.entity.customer.CustomerBaseMsgExtEntity.customerIntroduce"),
	
	//客户昵称
	Customer_Base_Msg_NickName("Customer_Base_Msg","nickName","昵称",0,"com.ziroom.minsu.entity.customer.CustomerBaseMsgEntity.nickName");
	/**
	 * @return the fieldPath
	 */
	public String getFieldPath() {
		return fieldPath;
	}

	/**
	 * @param fieldPath the fieldPath to set
	 */
	public void setFieldPath(String fieldPath) {
		this.fieldPath = fieldPath;
	}

	CustomerUpdateLogEnum(String tableName,String fieldName,String fieldDesc,int isText ,String fieldPath){

		this.tableName = tableName;
		this.fieldName = fieldName;
		this.fieldDesc = fieldDesc;
		this.isText = isText;

	}

	/**
	 * 表明
	 */
	public String tableName;

	/**
	 * 域名称
	 */
	public String fieldName;

	/**
	 * 域描述
	 */
	public String fieldDesc;
	
	/**
	 * 是否是大字段
	 */
	public int isText;
	
	/**
	 * 属性全路径
	 */
	public String fieldPath; 
	


	/**
	 * @return the isText
	 */
	public int getIsText() {
		return isText;
	}

	/**
	 * @param isText the isText to set
	 */
	public void setIsText(int isText) {
		this.isText = isText;
	}

	/**
	 * @return the tableName
	 */
	public String getTableName() {
		return tableName;
	}

	/**
	 * @param tableName the tableName to set
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	/**
	 * @return the fieldName
	 */
	public String getFieldName() {
		return fieldName;
	}

	/**
	 * @param fieldName the fieldName to set
	 */
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	/**
	 * @return the fieldDesc
	 */
	public String getFieldDesc() {
		return fieldDesc;
	}

	/**
	 * @param fieldDesc the fieldDesc to set
	 */
	public void setFieldDesc(String fieldDesc) {
		this.fieldDesc = fieldDesc;
	}



	/**
	 * 
	 * 根据 表明 和 域名 获取对应枚举
	 *
	 * @author yd
	 * @created 2017年7月4日 下午7:21:43
	 *
	 * @param tableName
	 * @param fieldName
	 * @return
	 */
	public static CustomerUpdateLogEnum getCustomerUpdateLogEnum(String tableName,String fieldName){

		if(!Check.NuNStr(tableName)&&!Check.NuNStr(fieldName)){
            for (CustomerUpdateLogEnum customerUpdateLogEnum : CustomerUpdateLogEnum.values()) {
				if(tableName.equals(customerUpdateLogEnum.getTableName())
						&&fieldName.equals(customerUpdateLogEnum.getFieldName())){
					return customerUpdateLogEnum;
				}
			}
		}
		return null;
	}

}
