/**
 * @FileName: HouseUpdateLogEnum.java
 * @Package com.ziroom.minsu.valenum.house
 * 
 * @author yd
 * @created 2017年7月3日 下午2:30:40
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.valenum.house;

import com.asura.framework.base.util.Check;

/**
 * <p>房源修改 记录信息
 * 说明: 所有需要 记录的字段 需要在此 添加  否则 不保存入库
 * </p>
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
public enum HouseUpdateLogEnum {

	//房源基本 信息
	House_Base_Msg_House_Sn("House_Base_Msg","houseSn","房源编码",0,"com.ziroom.minsu.entity.house.HouseBaseMsgEntity.houseSn"),
	House_Base_Msg_House_Name("House_Base_Msg","houseName","房源名称",0,"com.ziroom.minsu.entity.house.HouseBaseMsgEntity.houseName"),
	House_Base_Msg_Lease_Price("House_Base_Msg","leasePrice","房源日价格（分）",0,"com.ziroom.minsu.entity.house.HouseBaseMsgEntity.leasePrice"),
	House_Base_Msg_House_Area("House_Base_Msg","houseArea","房源面积（平方米）",0,"com.ziroom.minsu.entity.house.HouseBaseMsgEntity.houseArea"),
	House_Base_Msg_Room_Num("House_Base_Msg","roomNum","卧室数量",0,"com.ziroom.minsu.entity.house.HouseBaseMsgEntity.roomNum"),
	House_Base_Msg_Hall_Num("House_Base_Msg","hallNum","客厅数量",0,"com.ziroom.minsu.entity.house.HouseBaseMsgEntity.hallNum"),
	House_Base_Msg_Toilet_Num("House_Base_Msg","toiletNum","厕所数量",0,"com.ziroom.minsu.entity.house.HouseBaseMsgEntity.toiletNum"),
	House_Base_Msg_Kitchen_Num("House_Base_Msg","kitchenNum","厨房数量",0,"com.ziroom.minsu.entity.house.HouseBaseMsgEntity.kitchenNum"),
	House_Base_Msg_Balcony_Num("House_Base_Msg","balconyNum","阳台数量",0,"com.ziroom.minsu.entity.house.HouseBaseMsgEntity.balconyNum"),
	House_Base_Msg_House_Addr("House_Base_Msg","houseAddr","房源详细地址",0,"com.ziroom.minsu.entity.house.HouseBaseMsgEntity.houseAddr"),
	House_Base_Msg_house_Cleaning_Fees("House_Base_Msg","houseCleaningFees","房源保洁费",0,"com.ziroom.minsu.entity.house.HouseBaseMsgEntity.houseCleaningFees"),
	House_Base_Msg_House_Type("House_Base_Msg","houseType","房源类型",0,"com.ziroom.minsu.entity.house.HouseBaseMsgEntity.houseType"),

	//房间基本信息
	House_Room_Msg_Room_Name("House_Room_Msg","roomName","房间名称",0,"com.ziroom.minsu.entity.house.HouseRoomMsgEntity.roomName"),
	House_Room_Msg_bed_Num("House_Room_Msg","bedNum","床位数量",0,"com.ziroom.minsu.entity.house.HouseRoomMsgEntity.bedNum"),
	House_Room_Msg_Room_Area("House_Room_Msg","roomArea","房间面积",0,"com.ziroom.minsu.entity.house.HouseRoomMsgEntity.roomArea"),
	House_Room_Msg_Room_Price("House_Room_Msg","roomPrice","房间日价格（分）",0,"com.ziroom.minsu.entity.house.HouseRoomMsgEntity.roomPrice"),
	House_Room_Msg_Check_In_Limit("House_Room_Msg","checkInLimit","入住人数限制",0,"com.ziroom.minsu.entity.house.HouseRoomMsgEntity.checkInLimit"),
	House_Room_Msg_Room_Cleaning_Fees("House_Room_Msg","roomCleaningFees","房间保洁费",0,"com.ziroom.minsu.entity.house.HouseRoomMsgEntity.roomCleaningFees"),
	House_Room_Msg_Default_Pic_Fid("House_Room_Msg","defaultPicFid","封面照片",0,"com.ziroom.minsu.entity.house.HouseRoomMsgEntity.defaultPicFid"),

	
	//房源扩展信息
	House_Base_Ext_Deposit_Rules_Code("House_Base_Ext","depositRulesCode","押金规则code",0,"com.ziroom.minsu.entity.house.HouseBaseExtEntity.depositRulesCode"),
	House_Base_Ext_House_Street("House_Base_Ext","houseStreet","街道地址",0,"com.ziroom.minsu.entity.house.HouseBaseExtEntity.houseStreet"),
	House_Base_Ext_Order_Type("House_Base_Ext","orderType","下单类型(1:实时下单,2:普通下单)",0,"com.ziroom.minsu.entity.house.HouseBaseExtEntity.orderType"),
	House_Base_Ext_Min_Day("House_Base_Ext","minDay","最少入住天数",0,"com.ziroom.minsu.entity.house.HouseBaseExtEntity.minDay"),
	House_Base_Ext_Discount_Rules_Code("House_Base_Ext","discountRulesCode","优惠规则code",0,"com.ziroom.minsu.entity.house.HouseBaseExtEntity.discountRulesCode"),
	House_Base_Ext_Check_In_Limit("House_Base_Ext","checkInLimit","入住人数限制",0,"com.ziroom.minsu.entity.house.HouseBaseExtEntity.checkInLimit"),
	House_Base_Ext_Check_Out_Rules_Code("House_Base_Ext","checkOutRulesCode","退订政策code",0,"com.ziroom.minsu.entity.house.HouseBaseExtEntity.checkOutRulesCode"),
	House_Base_Ext_Check_In_Time("House_Base_Ext","checkInTime","入住时间",0,"com.ziroom.minsu.entity.house.HouseBaseExtEntity.checkInTime"),
	House_Base_Ext_Check_Out_Time("House_Base_Ext","checkOutTime","退订时间",0,"com.ziroom.minsu.entity.house.HouseBaseExtEntity.checkOutTime"),
	House_Base_Ext_Full_Discount("House_Base_Ext","fullDiscount","整租折扣率",0,"com.ziroom.minsu.entity.house.HouseBaseExtEntity.fullDiscount"),
	House_Base_Ext_Detail_Address("House_Base_Ext","detailAddress","楼号-门牌号",0,"com.ziroom.minsu.entity.house.HouseBaseExtEntity.detailAddress"),
	House_Base_Ext_House_Quality_Grade("House_Base_Ext","houseQualityGrade","房源品质",0,"com.ziroom.minsu.entity.house.HouseBaseExtEntity.houseQualityGrade"),
	House_Base_Ext_Rent_Room_Num("House_Base_Ext","rentRoomNum","要出租房间的数量（合租）",0,"com.ziroom.minsu.entity.house.HouseBaseExtEntity.rentRoomNum"),
	House_Base_Ext_Is_Together_Landlord("House_Base_Ext","isTogetherLandlord","是否与房东同住 0：否，1：是",0,"com.ziroom.minsu.entity.house.HouseBaseExtEntity.isTogetherLandlord"),
	House_Base_Ext_Default_Pic_Fid("House_Base_Ext","defaultPicFid","封面照片",0,"com.ziroom.minsu.entity.house.HouseBaseExtEntity.defaultPicFid"),
	
	//房源物理信息
	House_Phy_Msg_Nation_Code("House_Phy_Msg","nationCode","国家code",0,"com.ziroom.minsu.entity.house.HousePhyMsgEntity.nationCode"),
	House_Phy_Msg_Province_Code("House_Phy_Msg","provinceCode","省code",0,"com.ziroom.minsu.entity.house.HousePhyMsgEntity.provinceCode"),
	House_Phy_Msg_City_Code("House_Phy_Msg","cityCode","市code",0,"com.ziroom.minsu.entity.house.HousePhyMsgEntity.cityCode"),
	House_Phy_Msg_Area_Code("House_Phy_Msg","areaCode","区域code",0,"com.ziroom.minsu.entity.house.HousePhyMsgEntity.areaCode"),
	
	//房源配置信息
    House_Conf_Msg_Dic_Code("House_Conf_Msg","dicCode","配置项code",0,"com.ziroom.minsu.entity.house.HouseConfMsgEntity.dicCode"),
    House_Conf_Msg_Dic_Val("House_Conf_Msg","dicVal","配置项值",0,"com.ziroom.minsu.entity.house.HouseConfMsgEntity.dicVal"),
    
    //房间扩展信息
    House_Room_Ext_Order_Type("House_Room_Ext","orderType","下单类型 1：立即预订，2：申请预订",0,"com.ziroom.minsu.entity.house.HouseRoomExtEntity.orderType"),
    House_Room_Ext_Check_Out_Rules_Code("House_Room_Ext","checkOutRulesCode","退订政策code",0,"com.ziroom.minsu.entity.house.HouseRoomExtEntity.checkOutRulesCode"),
    House_Room_Ext_Room_Rules("House_Room_Ext","roomRules","房间房屋守则",0,"com.ziroom.minsu.entity.house.HouseRoomExtEntity.roomRules"),
    House_Room_Ext_Min_Day("House_Room_Ext","minDay","最少入住天数",0,"com.ziroom.minsu.entity.house.HouseRoomExtEntity.minDay"),
    House_Room_Ext_Check_In_Time("House_Room_Ext","checkInTime","入住时间",0,"com.ziroom.minsu.entity.house.HouseRoomExtEntity.checkInTime"),
    House_Room_Ext_Check_Out_Time("House_Room_Ext","checkOutTime","退订时间",0,"com.ziroom.minsu.entity.house.HouseRoomExtEntity.checkOutTime"),

	//房源描述信息
    House_Desc_House_Around_Desc("House_Desc","houseAroundDesc","周边状况",1,"com.ziroom.minsu.entity.house.HouseDescEntity.houseAroundDesc"),
    House_Desc_House_Rules("House_Desc","houseRules","房屋守则",1,"com.ziroom.minsu.entity.house.HouseDescEntity.houseRules"),
    House_Desc_Addtional_Info("House_Desc","addtionalInfo","房源审核补充信息",1,"com.ziroom.minsu.entity.house.HouseDescEntity.addtionalInfo"),
	House_Desc_House_Desc("House_Desc","houseDesc","房源描述",1,"com.ziroom.minsu.entity.house.HouseDescEntity.houseDesc");
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

	HouseUpdateLogEnum(String tableName,String fieldName,String fieldDesc,int isText ,String fieldPath){

		this.tableName = tableName;
		this.fieldName = fieldName;
		this.fieldDesc = fieldDesc;
		this.isText = isText;
		this.fieldPath=fieldPath;

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
	public static HouseUpdateLogEnum getHouseUpdateLogEnum(String tableName,String fieldName){

		if(!Check.NuNStr(tableName)&&!Check.NuNStr(fieldName)){
            for (HouseUpdateLogEnum houseUpdateLogEnum : HouseUpdateLogEnum.values()) {
				if(tableName.equals(houseUpdateLogEnum.getTableName())
						&&fieldName.equals(houseUpdateLogEnum.getFieldName())){
					return houseUpdateLogEnum;
				}
			}
		}
		return null;
	}

}
