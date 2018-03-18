package com.zra.common.enums;

import java.util.EnumSet;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by wangws21 on 2016/8/3.
 * 商机处理结果类别
 */
public enum BoHandResult {

    /*
    *  特别说明
    *  新建商机 键值在10和19之间
    *  待约看 键值在20和29之间
    *  待带看 键值在30和39之间
    *  待回访 键值在40和49之间
    *
    *  以0结尾的为关闭商机
    *  以1结尾的为完成商机
    * */


    /*新建商机 键值在10和19之间*/
    XJ_DQR_SJ((byte)14,"待和用户确认约看时间"),
    XJ_QR_YK((byte)13,"确认约看"),
    XJ_DHF((byte)12,"已带看，待进一步跟进回访"),
    XJ_YQY((byte)11,"已签约，完成商机"),
    XJ_GB_SJ((byte)10,"关闭商机"),  /*带原因*/

    /*待约看 键值在20和29之间*/
    YK_QR_YK((byte)25,"确认约看"),
    //add by xiaona --2016年10月19日  商机增加新的待沟通项
    YK_DGJ_YK((byte)26,"用户尚未确定约看时间，待继续跟进"),
    YK_DGJ((byte)24,"暂时联系不上用户，待继续跟进"),
    YK_QX_GB((byte)23,"用户取消约看，关闭商机"),
    YK_GB_SJ((byte)22,"联系用户多次未果，关闭商机"),
    /**
     * 特例：约看阶段商机<br>
     * 约看列表页关闭能选择原因  商机处理页只有22，23关闭商机选项<br>
     * 最后解决方式：处理页能不动，外层关闭处理页看到原因
     */
    YK_GB_RS((byte)20,"关闭商机"),

    /*待带看 键值在30和39之间*/
    DK_TC_SJ((byte)33,"用户更改带看时间"),
    DK_DHF((byte)32,"已带看，待进一步跟进回访"),
    DK_YQY((byte)31,"已签约，完成商机"),
    DK_GB_SJ((byte)30,"关闭商机"),  /*带原因*/


    /*待回访 键值在40和49之间*/
    HF_DHF((byte)42,"用户尚未决策，待回访跟进"),
    HF_YQY((byte)41,"已签约，完成商机"),
    HF_GB_SJ((byte)40,"关闭商机"),  /*带原因*/

    /*完成 键值在50和59之间*/
    WC_DHF((byte)50,"用户自主关闭商机");


    private Byte index;  //数据库实际存此值
    private String value;   //描述
    /*枚举Map 保持顺序*/
    protected static final Map<Byte, String> enum2Map = new LinkedHashMap();
    static {
        for (BoHandResult sourceType : EnumSet.allOf(BoHandResult.class)) {
            enum2Map.put(sourceType.getIndex(), sourceType.getValue());
        }
    }
    
    private BoHandResult(Byte index, String value){
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
}
