package com.ziroom.minsu.valenum.house;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * <p>出租方式</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/4/1.
 * @version 1.0
 * @since 1.0
 */
public enum RentWayEnum {

    HOUSE(0,"整套出租",true,"整套房子","用户可以预订整个空间，包括客厅、厨房等公用空间"),

    ROOM(1,"独立房间",true,"独立房间","用户可以预订一个独立房间，同时和其他客人分享客厅、厨房等公用空间"),

    BED(2,"床位",false,"床位",""),

    VIRTUAL(3,"整套出租",false,"整套出租",""),

    HALL(4,"共享客厅",true,"共享客厅","用户可以预订客厅及客厅中的沙发床，同时与其他客人分享客厅、厨房等公用空间");

    /** code */
    private int code;
    
    /** 名称 */
    private String name;
    
    /** 是否初始化进入Map标志 */
    private boolean isPushToMap;
    
    /**app端 出租方式名称*/
    private String appRentWayName;
    
	/**
     * 出租方式描述 app使用
     */
    private String rentWayDesc;

    private static final Map<Integer,String> enumMap = new LinkedHashMap<Integer,String>();
	
	static {
		for (RentWayEnum valEnum : RentWayEnum.values()) {
			if(valEnum.getIsPushToMap()){
				enumMap.put(valEnum.getCode(), valEnum.getName());  
			}
        }  
	}

    RentWayEnum(int code, String name, boolean isPushToMap,String appRentWayName,String rentWayDesc) {
        this.code = code;
        this.name = name;
        this.isPushToMap = isPushToMap;
        this.appRentWayName = appRentWayName;
        this.rentWayDesc=rentWayDesc;
    }

    /**
     * 获取
     * @param code
     * @return
     */
    public static RentWayEnum getRentWayByCode(int code) {
        for (final RentWayEnum rentWayEnum : RentWayEnum.values()) {
            if (rentWayEnum.getCode() == code) {
                return rentWayEnum;
            }
        }
        return null;
    }
    
    

    /**
	 * @return the appRentWayName
	 */
	public String getAppRentWayName() {
		return appRentWayName;
	}

	/**
	 * @param appRentWayName the appRentWayName to set
	 */
	public void setAppRentWayName(String appRentWayName) {
		this.appRentWayName = appRentWayName;
	}

	public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
    
    public boolean getIsPushToMap() {
    	return isPushToMap;
    }

	public static Map<Integer,String> getEnumMap() {
    	return enumMap;
    }
	
    /**
	 * @return the rentWayDesc
	 */
	public String getRentWayDesc() {
		return rentWayDesc;
	}

}
