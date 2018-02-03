package com.ziroom.minsu.valenum.house;

import com.asura.framework.base.util.Check;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 
 * <p>下单类型枚举</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author liujun
 * @since 1.0
 * @version 1.0
 */
public enum OrderTypeEnum {

	CURRENT(1,"立即预订",true,"用户可直接预订您的房源，不需要经过您的同意。为避免因纠纷而导致经济损失，房源日历需保持最新状态。同时，打开立即预订可以帮助提高排名"),
	ORDINARY(2,"申请预订",true,"用户需要提前发送预订申请，您同意申请后用户才可以完成预订");

    /** code */
    private int code;
    
    /** 名称 */
    private String name;
    
    /** 是否初始化进入Map标志 */
    private boolean isPushToMap;
    
    /** 描述信息 */
    private String defautExplain;
    

	private static final Map<Integer,String> enumMap = new LinkedHashMap<Integer,String>();
	
	static {
		for (OrderTypeEnum orderTypeEnum : OrderTypeEnum.values()) {
			if(orderTypeEnum.getIsPushToMap()){
				enumMap.put(orderTypeEnum.getCode(), orderTypeEnum.getName());  
			}
        }  
	}

    /**
     * 获取
     * @param code
     * @return
     */
    public static OrderTypeEnum getOrderTypeByCode(Integer code) {
        if(Check.NuNObj(code)){
            return null;
        }
        for (final OrderTypeEnum type : OrderTypeEnum.values()) {
            if (type.getCode() == code) {
                return type;
            }
        }
        return null;
    }

	OrderTypeEnum(int code, String name, boolean isPushToMap,String defautExplain) {
        this.code = code;
        this.name = name;
        this.isPushToMap = isPushToMap;
        this.defautExplain = defautExplain;
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
    
	public String getDefautExplain() {
		return defautExplain;
	}

	public void setDefautExplain(String defautExplain) {
		this.defautExplain = defautExplain;
	}

	public static Map<Integer,String> getEnumMap() {
    	return enumMap;
    }
	
}
