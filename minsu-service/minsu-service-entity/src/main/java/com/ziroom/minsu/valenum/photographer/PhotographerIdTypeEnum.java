
package com.ziroom.minsu.valenum.photographer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * (0=其他 1=身份证 2=护照 3=军官证 13=港澳居民来往通行证 6=台湾居民来往通行证)
 * <p>客户学历枚举</p>
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
public enum PhotographerIdTypeEnum {

	OTHER(0,"其他"),
	ID(1,"身份证"),
	PASSPORT(2,"护照"),
	GA_PASSPORT(13,"港澳居民来往通行证"),
	tw_PASSPORT(6,"台湾居民来往通行证");
	
	PhotographerIdTypeEnum(int code,String name){
		this.code  = code;
		this.name = name;
	}
	
	/**
	 * code值
	 */
	private  int code;
	
	/**
	 * z中文含义
	 */
	private String name;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

    public static PhotographerIdTypeEnum getCustomerIdTypeByCode(int code) {
        for (PhotographerIdTypeEnum statu : PhotographerIdTypeEnum.values()) {
            if (statu.getCode() == code) {
                return statu;
            }
        }
        return null;
    }
    
    public static List<PhotographerIdTypeEnum> getCustomerIdTypeEnums(){
    	List<PhotographerIdTypeEnum> idTypeList = new ArrayList<PhotographerIdTypeEnum>();
    	for (PhotographerIdTypeEnum statu : PhotographerIdTypeEnum.values()) {
    		if(statu.getCode() != 0){
    			idTypeList.add(statu);	
    		}
    	}
       return idTypeList;	
    }
    
    public static Map<Integer, String> getCustomerIdTypeMap(){
    	Map<Integer, String> map = new HashMap<Integer, String>();
    	for (PhotographerIdTypeEnum statu : PhotographerIdTypeEnum.values()) {
    		if(statu.getCode() != 0){
    			map.put(statu.getCode(), statu.getName());
    		}
    	}
       return map;	
    }
	 
}
