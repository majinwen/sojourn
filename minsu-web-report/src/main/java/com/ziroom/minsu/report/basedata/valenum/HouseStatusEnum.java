/**
 * @FileName: HouseStatusEnum.java
 * @Package com.ziroom.minsu.valenum.house
 * 
 * @author bushujie
 * @created 2016年4月2日 下午8:56:30
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.report.basedata.valenum;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * <p>房源状态</p>
 * 
 * <PRE>
 * <BR> 房源显示状态更改，pc先改，后面M站更新，更新完后可以删除中间的显示状态
 * <BR>-----------------------------------------------
 * <BR>	修改日期	2016-08-12		修改人	jixd	修改内容
 * </PRE>
 * 
 * @author bushujie
 * @since 1.0
 * @version 1.0
 */
public enum HouseStatusEnum {
	// 房源状态(10:待发布,11:已发布,20:管家审核通过,21:管家审核未通过,30:品质审核未通过,40:上架,41:下架,50:强制下架)
	DFB(10,"待发布",1,"待发布",1,"待发布",true),
	YFB(11,"已发布",2,"审核中",2,"审核中",true),
	@Deprecated
	XXSHTG(20,"管家审核通过",2,"审核中",3,"审核中",false),
	@Deprecated
	XXSHWTG(21,"管家审核未通过",3,"审核未通过",4,"审核未通过",false),
	ZPSHWTG(30,"品质审核未通过",2,"审核中",3,"审核未通过",true),
	SJ(40,"上架",4,"已上架",5,"已上架",true),
	XJ(41,"下架",5,"已下架",6,"已下架",true),
	QZXJ(50,"强制下架",6,"强制下架",7,"强制下架",true);
	
	/** code */
	private int code;
	
	/** 名称 */
	private String name;
	/*展示code*/
	private int showCode;
	/*展示名称*/
	private String showName;
	/**
	 * 展示状态
	 */
	private int showStatus;
	/**
	 * 展示状态名称
	 */
	private String showStatusName;
	
	/** 是否初始化进入Map标志 */
	private boolean isPushToMap;
	
	//根据房源code获取房源枚举
	public static HouseStatusEnum getHouseStatusByCode(int houseStatus) {
        for (final HouseStatusEnum status : HouseStatusEnum.values()) {
            if (status.getCode() == houseStatus) {
                return status;
            }
        }
        return null;
    }
	
	/**
	 * 所有状态集合
	 */
	private static final Map<Integer,String> enumMap = new LinkedHashMap<Integer,String>();
	
	/**
	 * 有效状态集合
	 */
	private static final Map<Integer,String> validEnumMap = new LinkedHashMap<Integer,String>();
	
	static {
		for (HouseStatusEnum houseStatusEnum : HouseStatusEnum.values()) {  
			enumMap.put(houseStatusEnum.getCode(), houseStatusEnum.getName());
			
			if (houseStatusEnum.getIsPushToMap()) {
				validEnumMap.put(houseStatusEnum.getCode(), houseStatusEnum.getName());
			}
        }  
	}
	
	HouseStatusEnum(int code,String name,int showCode,String showName,int showStatus,String showStatusName, boolean isPushToMap){
		 this.code = code;
	     this.name = name;
	     this.showCode = showCode;
	     this.showName = showName;
	     this.showStatus = showStatus;
	     this.showStatusName = showStatusName;
	     this.isPushToMap = isPushToMap;
	}

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
    
	public int getShowCode() {
		return showCode;
	}

	public void setShowCode(int showCode) {
		this.showCode = showCode;
	}

	public String getShowName() {
		return showName;
	}

	public void setShowName(String showName) {
		this.showName = showName;
	}

	public static Map<Integer,String> getEnumMap() {
    	return enumMap;
    }

	public int getShowStatus() {
		return showStatus;
	}

	public void setShowStatus(int showStatus) {
		this.showStatus = showStatus;
	}

	public String getShowStatusName() {
		return showStatusName;
	}

	public void setShowStatusName(String showStatusName) {
		this.showStatusName = showStatusName;
	}
	
    public boolean getIsPushToMap() {
    	return isPushToMap;
    }

	public static Map<Integer,String> getValidEnumMap() {
		return validEnumMap;
	}
    
}
