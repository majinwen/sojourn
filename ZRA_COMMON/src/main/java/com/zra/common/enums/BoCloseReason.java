package com.zra.common.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by wangws21 on 2016/8/9.
 * 商机关闭原因
 */
public enum BoCloseReason {

    /*
    *  特别说明
    *  新建商机 键值在10和19之间
    *  待约看 键值在20和29之间
    *  待带看 键值在30和39之间
    *  待回访 键值在40和49之间
    * */
	
	/*
	* 这样处理特别方便 后台管理系统 在不同阶段显示不同的关闭原因  回显也特别方便、 而且可以区分关闭的阶段
	* 不好的地方就是 如果要做关闭原因统计的话，同一个原因可对应多个键值，不过暂时没有统计原因的需求O(∩_∩)O
	* 
	* 统计原因的话需要特别注意！！！！
	*/

    /*新建商机 键值在10和19之间*/
	
	
	XJ_GB_1((byte)11,"预算不合适"),
    XJ_GB_2((byte)12,"很久之后才入住"),
    XJ_GB_3((byte)13,"距离、交通不合适"),
    XJ_GB_4((byte)14,"咨询和本项目销售无关的事"),
    XJ_GB_5((byte)15,"重复约看"),
    XJ_GB_6((byte)16,"对公寓、房间不满意"),
    XJ_GB_7((byte)17,"用户个人原因取消带看行程"),
    XJ_GB_8((byte)18,"已签约其他房源"),
    XJ_GB_9((byte)19,"其他"),

    /*待约看 键值在20和29之间*/
    YK_GB_1((byte)21,"预算不合适"),
    YK_GB_2((byte)22,"很久之后才入住"),
    YK_GB_3((byte)23,"距离、交通不合适"),
    YK_GB_4((byte)24,"咨询和本项目销售无关的事"),
    YK_GB_5((byte)25,"重复约看"),
    YK_GB_6((byte)26,"其他"),

    /*待带看 键值在30和39之间*/
    DK_GB_1((byte)31,"预算不合适"),
    DK_GB_2((byte)32,"很久之后才入住"),
    DK_GB_3((byte)33,"距离、交通不合适"),
    DK_GB_4((byte)34,"对公寓、房间不满意"),
    DK_GB_5((byte)35,"用户个人原因取消带看行程"),
    DK_GB_6((byte)36,"其他"),


    /*待回访 键值在40和49之间*/
    HF_GB_1((byte)41,"已签约其他房源"),
    HF_GB_2((byte)42,"预算不合适"),
    HF_GB_3((byte)43,"很久之后才入住"),
    HF_GB_4((byte)44,"对公寓、房间不满意"),
    HF_GB_5((byte)45,"其他");
	
    private Byte index;  //数据库实际存此值
    private String value;   //描述
    
    /*枚举Map 保持顺序*/
    protected static final Map<Byte, String> enum2Map = new LinkedHashMap();
    static {
        for (BoCloseReason sourceType : EnumSet.allOf(BoCloseReason.class)) {
            enum2Map.put(sourceType.getIndex(), sourceType.getValue());
        }
    }

    /*用于关闭原因显示 不需要顺序*/
    protected static final Map<String, String> enum2StringMap = new HashMap();
    static {
        for (BoCloseReason sourceType : EnumSet.allOf(BoCloseReason.class)) {
            enum2StringMap.put(String.valueOf(sourceType.getIndex()), sourceType.getValue());
        }
    }
    
    private BoCloseReason(Byte index, String value){
        this.index = index;
        this.value = value;
    }
    
    public Byte getIndex() {
        return index;
    }

    public String getValue() {
        return value;
    }

	public static Map<Byte, String> getEnum2map() {
		return enum2Map;
	}

	public static Map<String, String> getEnum2stringmap() {
		return enum2StringMap;
	}
}
